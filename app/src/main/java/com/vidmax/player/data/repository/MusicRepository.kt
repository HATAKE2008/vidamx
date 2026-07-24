package com.vidmax.player.data.repository

import com.vidmax.player.data.local.SongHistoryDao
import com.vidmax.player.data.model.SongItem
import org.schabi.newpipe.extractor.ServiceList
import org.schabi.newpipe.extractor.stream.StreamInfo
import org.schabi.newpipe.extractor.stream.StreamInfoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicRepository @Inject constructor(
    private val historyDao: SongHistoryDao
) {

    /**
     * YouTube-এ গান সার্চ করার মেথড
     */
    suspend fun searchSongs(query: String): Result<List<SongItem>> = withContext(Dispatchers.IO) {
        runCatching {
            val extractor = ServiceList.YouTube.getSearchExtractor(query)
            extractor.fetchPage()
            
            extractor.initialPage.items
                .filterIsInstance<StreamInfoItem>()
                .map { item ->
                    SongItem(
                        videoId = item.url.substringAfter("v="),
                        title = item.name,
                        artist = item.uploaderName ?: "Unknown Artist",
                        thumbnailUrl = item.thumbnails.firstOrNull()?.url ?: "",
                        duration = item.duration
                    )
                }
        }
    }

    /**
     * গানের Audio Stream URL এক্সট্র্যাক্ট করার মেথড
     */
    suspend fun getAudioStreamUrl(videoId: String): Result<String> = withContext(Dispatchers.IO) {
        runCatching {
            val videoUrl = "https://www.youtube.com/watch?v=$videoId"
            val info = StreamInfo.getInfo(ServiceList.YouTube, videoUrl)
            
            // সর্বোচ্চ বিটরেটের অডিও স্ট্রিম ফিল্টার
            info.audioStreams
                .maxByOrNull { it.bitrate }
                ?.content ?: throw Exception("No audio stream found for $videoId")
        }
    }

    /**
     * হোম পেজের ক্যাটাগরি অনুযায়ী গান আনার জন্য মেথড
     */
    suspend fun getCategorySongs(categoryQuery: String): List<SongItem> {
        return searchSongs(categoryQuery).getOrDefault(emptyList())
    }

    /**
     * কোনো নির্দিষ্ট গানের Related/Recommended গান বের করার জন্য
     */
    suspend fun getRelatedSongs(videoId: String): Result<List<SongItem>> = withContext(Dispatchers.IO) {
        runCatching {
            val videoUrl = "https://www.youtube.com/watch?v=$videoId"
            val info = StreamInfo.getInfo(ServiceList.YouTube, videoUrl)
            
            info.relatedItems
                .filterIsInstance<StreamInfoItem>()
                .take(10)
                .map { item ->
                    SongItem(
                        videoId = item.url.substringAfter("v="),
                        title = item.name,
                        artist = item.uploaderName ?: "Unknown Artist",
                        thumbnailUrl = item.thumbnails.firstOrNull()?.url ?: "",
                        duration = item.duration
                    )
                }
        }
    }

    // Room Database Operations
    suspend fun saveToHistory(song: SongItem) {
        historyDao.insertSong(song.copy(playedAt = System.currentTimeMillis()))
    }

    fun getRecentlyPlayed(): Flow<List<SongItem>> {
        return historyDao.getRecentSongs()
    }

    suspend fun clearHistory() {
        historyDao.clearHistory()
    }
}
