package com.vidmax.player.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vidmax.player.data.model.SongItem

@Database(entities = [SongItem::class], version = 1, exportSchema = false)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun songHistoryDao(): SongHistoryDao
}
