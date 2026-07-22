package com.vidmax.player.ui.screen

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.nestedscroll.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.vidmax.player.R
import com.vidmax.player.data.model.VideoItem
import com.vidmax.player.viewmodel.LibraryViewModel
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private data class NavItem(val label: String)

private val navItems = listOf(NavItem("Videos"), NavItem("Folders"), NavItem("Music"))

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(viewModel: LibraryViewModel, onVideoClick: (List<VideoItem>, Int) -> Unit) {
  val context = LocalContext.current
  var selectedTab by remember { mutableIntStateOf(0) }
  var isSettingsOpen by remember { mutableStateOf(false) }
  var isMusicPlayerOpen by remember { mutableStateOf(false) }

  val currentFolderPath by viewModel.currentFolderPath.collectAsState()
  val openedPlaylistTitle by viewModel.openedPlaylistTitle.collectAsState()

  val recentMusicTitle by viewModel.recentlyPlayedTitle.collectAsState()
  val recentMusicPath by viewModel.recentlyPlayedPath.collectAsState()
  val isAudioPlaying by viewModel.isAudioPlaying.collectAsState()

  // 🚀 প্লেব্যাকের প্রোগ্রেস ও বিস্তারিত ডাটা কালেকশন
  val currentPosition by viewModel.audioPosition.collectAsState()
  val duration by viewModel.audioDuration.collectAsState()
  val currentArtist by viewModel.currentAudioArtist.collectAsState()
  val favoritePaths by viewModel.favoriteAudioPaths.collectAsState()
  val isFavorite = favoritePaths.contains(recentMusicPath)

  val audioProgress = if (duration > 0) (currentPosition.toFloat() / duration.toFloat()).coerceIn(0f, 1f) else 0f

  var albumArtBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

  val isScrollingDown = remember { mutableStateOf(false) }

  val nestedScrollConnection = remember {
    object : NestedScrollConnection {
      override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        if (available.y < -15f) {
          isScrollingDown.value = true
        } else if (available.y > 15f) {
          isScrollingDown.value = false
        }
        return Offset.Zero
      }
    }
  }

  LaunchedEffect(recentMusicPath) {
    if (recentMusicPath.isNotEmpty()) {
      withContext(Dispatchers.IO) {
        try {
          val mmr = android.media.MediaMetadataRetriever()
          val uri: Uri =
              if (recentMusicPath.startsWith("/")) {
                Uri.fromFile(File(recentMusicPath))
              } else {
                Uri.parse(recentMusicPath)
              }
          mmr.setDataSource(context, uri)

          val pic = mmr.embeddedPicture
          if (pic != null) {
            val bmp = android.graphics.BitmapFactory.decodeByteArray(pic, 0, pic.size)
            albumArtBitmap = bmp.asImageBitmap()
          } else {
            albumArtBitmap = null
          }
          mmr.release()
        } catch (e: Exception) {
          albumArtBitmap = null
        }
      }
    } else {
      albumArtBitmap = null
    }
  }

  BackHandler(
      enabled =
          isMusicPlayerOpen ||
              isSettingsOpen ||
              openedPlaylistTitle.isNotEmpty() ||
              selectedTab != 0 ||
              currentFolderPath.isNotEmpty()
  ) {
        if (isMusicPlayerOpen) {
          isMusicPlayerOpen = false
        } else if (isSettingsOpen) {
          isSettingsOpen = false
        } else if (openedPlaylistTitle.isNotEmpty()) {
          viewModel.closePlaylist()
        } else if (currentFolderPath.isNotEmpty()) {
          viewModel.closeFolder()
        } else if (selectedTab != 0) {
          selectedTab = 0
        }
      }

  val handleVideoClick = { videos: List<VideoItem>, index: Int ->
    viewModel.pauseAudio()
    onVideoClick(videos, index)
  }

  val showMusicRecentBar =
      (selectedTab == 2 || openedPlaylistTitle.isNotEmpty()) && recentMusicTitle.isNotEmpty()

  Box(modifier = Modifier.fillMaxSize().nestedScroll(nestedScrollConnection)) {
    Scaffold(containerColor = MaterialTheme.colorScheme.background) { paddingValues ->
      Box(
          modifier = Modifier
                  .fillMaxSize()
                  .background(MaterialTheme.colorScheme.background)
                  .padding(paddingValues)
      ) {
            if (openedPlaylistTitle.isNotEmpty()) {
              PlaylistScreen(
                  viewModel = viewModel,
                  onBack = { viewModel.closePlaylist() },
                  onAudioClick = { audioList, index ->
                    viewModel.playAudioFromList(audioList, index)
                  }
              )
            } else {
              when (selectedTab) {
                0 -> HomeScreen(
                        viewModel = viewModel,
                        onVideoClick = handleVideoClick,
                        onSettingsClick = { isSettingsOpen = true }
                    )
                1 -> FoldersScreen(viewModel = viewModel, onVideoClick = handleVideoClick)
                2 -> MusicScreen(
                        viewModel = viewModel,
                        onSettingsClick = { isSettingsOpen = true },
                        onAudioClick = { audioList, index ->
                          viewModel.playAudioFromList(audioList, index)
                        },
                        onOpenFavorites = { viewModel.openFavorites() },
                        onOpenMyMix = { viewModel.openMyMix() }
                    )
              }
            }
          }
    }

    Column(modifier = Modifier.align(Alignment.BottomCenter)) {
      
      // 🔥 THEME AWARE MINI PLAYER BAR
      AnimatedVisibility(
          visible = showMusicRecentBar && !isScrollingDown.value,
          enter = slideInVertically(initialOffsetY = { it }),
          exit = slideOutVertically(targetOffsetY = { it })
      ) {
            Box(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp)
                        .padding(bottom = 8.dp)
                        .shadow(12.dp, RoundedCornerShape(50), spotColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.surfaceVariant) // থিম অনুযায়ী ব্যাকগ্রাউন্ড চেঞ্জ হবে
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.outlineVariant, // থিম অনুযায়ী বর্ডার কালার
                            RoundedCornerShape(50)
                        )
                        .clickable { isMusicPlayerOpen = true }
                        .padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                  Row(
                      verticalAlignment = Alignment.CenterVertically,
                      modifier = Modifier.fillMaxWidth()
                  ) {
                        
                        // 1️⃣ বামপাশের সার্কুলার আর্টওয়ার্ক + প্রোগ্রেস রিং + প্লে/পজ বাটন
                        Box(
                            modifier = Modifier
                                    .size(52.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                      viewModel.toggleAudio()
                                    },
                            contentAlignment = Alignment.Center
                        ) {
                              // প্রোগ্রেস রিং ব্যাকগ্রাউন্ড Track
                              CircularProgressIndicator(
                                  progress = { 1f },
                                  modifier = Modifier.fillMaxSize(),
                                  color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), // থিমের সাথে মানানসই
                                  strokeWidth = 3.dp,
                                  trackColor = Color.Transparent
                              )

                              // লাইভ প্লেব্যাক প্রোগ্রেস ইন্ডিকেটর Ring
                              CircularProgressIndicator(
                                  progress = { audioProgress },
                                  modifier = Modifier.fillMaxSize(),
                                  color = MaterialTheme.colorScheme.primary, // প্রাইমারি থিম কালার
                                  strokeWidth = 3.dp,
                                  strokeCap = StrokeCap.Round,
                                  trackColor = Color.Transparent
                              )

                              // গোল অ্যালবাম আর্টওয়ার্ক
                              Box(
                                  modifier = Modifier
                                          .size(42.dp)
                                          .clip(CircleShape)
                                          .background(MaterialTheme.colorScheme.surface),
                                  contentAlignment = Alignment.Center
                              ) {
                                    if (albumArtBitmap != null) {
                                      Image(
                                          bitmap = albumArtBitmap!!,
                                          contentDescription = "Album Art",
                                          contentScale = ContentScale.Crop,
                                          modifier = Modifier.fillMaxSize()
                                      )
                                    } else {
                                      Icon(
                                          painter = painterResource(id = R.drawable.ic_music_note),
                                          contentDescription = null,
                                          tint = MaterialTheme.colorScheme.primary,
                                          modifier = Modifier.size(20.dp)
                                      )
                                    }

                                    // পজ/প্লে আইকনের জন্য ডার্ক ওভারলে
                                    Box(
                                        modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.Black.copy(alpha = 0.35f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                          Icon(
                                              painter = painterResource(
                                                  id = if (isAudioPlaying) R.drawable.ic_pause else R.drawable.ic_play
                                              ),
                                              contentDescription = "Play/Pause",
                                              tint = Color.White, // ওভারলের ওপর সাদা আইকন সবসময় ভালো লাগে
                                              modifier = Modifier.size(18.dp)
                                          )
                                    }
                              }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        // 2️⃣ মাঝের টাইটেল ও আর্টিস্ট
                        Column(modifier = Modifier.weight(1f)) {
                          Text(
                              text = recentMusicTitle,
                              color = MaterialTheme.colorScheme.onSurface, // থিম অনুযায়ী টেক্সট কালার
                              fontSize = 14.sp,
                              fontWeight = FontWeight.Bold,
                              maxLines = 1,
                              overflow = TextOverflow.Ellipsis
                          )
                          Spacer(modifier = Modifier.height(2.dp))
                          Text(
                              text = currentArtist.ifEmpty { "Vibe Music" },
                              color = MaterialTheme.colorScheme.onSurfaceVariant, // সাব-টেক্সট কালার
                              fontSize = 12.sp,
                              maxLines = 1,
                              overflow = TextOverflow.Ellipsis
                          )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // 3️⃣ ডানপাশের সার্কুলার অ্যাকশন বাটন (Next এবং Heart)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                              // 🚀 Next Track Button (Removed Plus button)
                              Box(
                                  modifier = Modifier
                                          .size(40.dp)
                                          .clip(CircleShape)
                                          .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape)
                                          .clickable { viewModel.nextAudio() },
                                  contentAlignment = Alignment.Center
                              ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_skip_next),
                                        contentDescription = "Next Track",
                                        tint = MaterialTheme.colorScheme.onSurface, // থিম অনুযায়ী আইকন কালার
                                        modifier = Modifier.size(20.dp)
                                    )
                              }

                              // Favorite Heart Button
                              Box(
                                  modifier = Modifier
                                          .size(40.dp)
                                          .clip(CircleShape)
                                          .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape)
                                          .clickable { viewModel.toggleFavorite(recentMusicPath) },
                                  contentAlignment = Alignment.Center
                              ) {
                                    Icon(
                                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "Favorite",
                                        tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.size(20.dp)
                                    )
                              }
                        }
                  }
            }
      }

      // BOTTOM NAVIGATION BAR
      BoxWithConstraints(
          modifier = Modifier
                  .padding(horizontal = 18.dp)
                  .padding(bottom = 16.dp)
                  .fillMaxWidth()
                  .height(70.dp)
                  .shadow(16.dp, RoundedCornerShape(35.dp), spotColor = Color.Black.copy(alpha = 0.45f))
                  .clip(RoundedCornerShape(35.dp))
                  .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.96f))
                  .border(
                      1.2.dp,
                      MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                      RoundedCornerShape(35.dp)
                  )
                  .padding(6.dp)
      ) {
            val tabWidth = maxWidth / navItems.size

            val indicatorOffset by animateDpAsState(
                targetValue = tabWidth * selectedTab,
                animationSpec = spring(
                    dampingRatio = 0.75f,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "indicatorOffset"
            )

            Box(
                modifier = Modifier
                        .offset(x = indicatorOffset)
                        .width(tabWidth)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(28.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.18f))
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                  navItems.forEachIndexed { index, item ->
                    val isSelected = selectedTab == index

                    val contentColor by animateColorAsState(
                        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.65f),
                        animationSpec = tween(250),
                        label = "colorAnim"
                    )

                    val iconScale by animateFloatAsState(
                        targetValue = if (isSelected) 1.15f else 1.0f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                        label = "scaleAnim"
                    )

                    val iconRes = when (item.label) {
                      "Videos" -> R.drawable.ic_video_library
                      "Folders" -> R.drawable.ic_folder
                      "Music" -> R.drawable.ic_music_note
                      else -> R.drawable.ic_video_library
                    }

                    Box(
                        modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = {
                                      selectedTab = index
                                      if (index != 1) viewModel.closeFolder()
                                      viewModel.closePlaylist()
                                    }
                                ),
                        contentAlignment = Alignment.Center
                    ) {
                          Column(
                              horizontalAlignment = Alignment.CenterHorizontally,
                              verticalArrangement = Arrangement.Center
                          ) {
                                Icon(
                                    painter = painterResource(id = iconRes),
                                    contentDescription = item.label,
                                    tint = contentColor,
                                    modifier = Modifier.size(26.dp).scale(iconScale)
                                )

                                Spacer(modifier = Modifier.height(3.dp))

                                Text(
                                    text = item.label,
                                    fontSize = 12.sp,
                                    color = contentColor,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                              }
                        }
                  }
                }
          }
    }

    AnimatedVisibility(
        visible = isSettingsOpen,
        enter = slideInHorizontally(
            initialOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(350, easing = FastOutSlowInEasing)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(350, easing = FastOutSlowInEasing)
        ),
        modifier = Modifier.fillMaxSize().zIndex(5f)
    ) {
          Box(modifier = Modifier.fillMaxSize().clickable(enabled = false) {}) {
            SettingsScreen(viewModel = viewModel, onBack = { isSettingsOpen = false })
          }
        }

    AnimatedVisibility(
        visible = isMusicPlayerOpen,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing)
        ),
        modifier = Modifier.fillMaxSize().zIndex(10f)
    ) {
          Box(modifier = Modifier.fillMaxSize().clickable(enabled = false) {}) {
            MusicPlayerScreen(viewModel = viewModel, onBack = { isMusicPlayerOpen = false })
          }
        }
  }
}
