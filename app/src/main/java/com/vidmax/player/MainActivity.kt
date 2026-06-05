package com.vidmax.player

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge // 🔥 Edge to Edge ইম্পোর্ট
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import com.vidmax.player.ui.permission.PermissionScreen
import com.vidmax.player.ui.player.PlayerActivity
import com.vidmax.player.ui.screen.MainScreen
import com.vidmax.player.ui.screen.SplashScreen
import com.vidmax.player.ui.theme.VidMaxTheme
import com.vidmax.player.viewmodel.LibraryViewModel

class MainActivity : ComponentActivity() {

  private val libraryViewModel: LibraryViewModel by viewModels()
  private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

  override fun onCreate(savedInstanceState: Bundle?) {
    // 🔥 একদম স্ক্রিনের উপর থেকে (স্ট্যাটাস বারের নিচ থেকে) অ্যাপ শুরু করার জন্য
    enableEdgeToEdge()

    super.onCreate(savedInstanceState)

    permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            permissions ->
          val allGranted = permissions.entries.all { it.value }
          libraryViewModel.setPermissionGranted(allGranted)
        }

    val hasPermission: Boolean = checkStoragePermissions()
    libraryViewModel.setPermissionGranted(hasPermission)

    setContent {
      val currentTheme by libraryViewModel.appTheme.collectAsState()
      val permission by libraryViewModel.hasPermission.collectAsState()

      VidMaxTheme(appTheme = currentTheme) {
        // 🔥 স্প্ল্যাশ স্ক্রিন স্টেট
        var showSplash by remember { mutableStateOf(true) }

        if (showSplash) {
          SplashScreen(onSplashFinished = { showSplash = false })
        } else {
          // স্প্ল্যাশ শেষ হলে পারমিশন চেক করে মেইন অ্যাপে যাবে
          if (permission) {
            MainScreen(
                viewModel = libraryViewModel,
                onVideoClick = { videos, index ->
                  libraryViewModel.setRecentlyPlayedVideo(videos[index].title, videos[index].path)
                  PlayerActivity.start(this@MainActivity, videos.map { it.path }, index)
                })
          } else {
            PermissionScreen(onRequestPermission = { requestStoragePermissions() })
          }
        }
      }
    }
  }

  private fun checkStoragePermissions(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO) ==
          PackageManager.PERMISSION_GRANTED &&
          ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) ==
              PackageManager.PERMISSION_GRANTED
    } else {
      ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
          PackageManager.PERMISSION_GRANTED
    }
  }

  private fun requestStoragePermissions() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      permissionLauncher.launch(
          arrayOf(Manifest.permission.READ_MEDIA_VIDEO, Manifest.permission.READ_MEDIA_AUDIO))
    } else {
      permissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
    }
  }

  override fun onResume() {
    super.onResume()
    if (checkStoragePermissions()) {
      libraryViewModel.setPermissionGranted(true)
    }
  }
}
