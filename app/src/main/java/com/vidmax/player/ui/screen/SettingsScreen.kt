package com.vidmax.player.ui.screen

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha // 🔥 FIX: Ensure alpha is imported
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vidmax.player.R
import com.vidmax.player.ui.theme.*
import com.vidmax.player.viewmodel.DarkMode
import com.vidmax.player.viewmodel.LibraryViewModel
import com.vidmax.player.viewmodel.PlayerEngine

@Composable
fun SettingsScreen(viewModel: LibraryViewModel, onBack: () -> Unit) {
    val resumePlayback by viewModel.resumePlayback.collectAsState()
    val autoRotate by viewModel.autoRotate.collectAsState()
    val audioBoost by viewModel.audioBoost.collectAsState()
    val currentEngine by viewModel.playerEngine.collectAsState()

    val currentTheme by viewModel.appTheme.collectAsState()
    val darkMode by viewModel.darkMode.collectAsState()
    val amoledMode by viewModel.amoledMode.collectAsState()

    val context = LocalContext.current
    
    val isSystemDark = isSystemInDarkTheme()
    val isCurrentlyDark = when (darkMode) {
        DarkMode.Dark -> true
        DarkMode.Light -> false
        DarkMode.System -> isSystemDark
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding() 
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    .clickable { onBack() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = "Appearance",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .offset(x = (-22).dp),
                textAlign = TextAlign.Center
            )
        }

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {

            item { SettingsSectionHeader(title = "Theme") }
            
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .height(48.dp)
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(50))
                        .clip(RoundedCornerShape(50))
                ) {
                    val options = listOf(DarkMode.Dark, DarkMode.Light, DarkMode.System)
                    options.forEach { mode ->
                        val isSelected = darkMode == mode
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(if (isSelected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent)
                                .clickable { viewModel.setDarkMode(mode) },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check, 
                                        contentDescription = null, 
                                        modifier = Modifier.size(18.dp), 
                                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                    Spacer(Modifier.width(4.dp))
                                }
                                Text(
                                    text = mode.name,
                                    color = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            item { SettingsSectionHeader(title = "App Theme", paddingTop = 16.dp) }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 16.dp)
                ) {
                    items(AppTheme.values()) { theme ->
                        if (theme.isDynamic && Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return@items
                        
                        AppThemePreviewItem(
                            theme = theme,
                            isSelected = currentTheme == theme,
                            isDark = isCurrentlyDark,
                            isAmoled = amoledMode,
                            onClick = { viewModel.setAppTheme(theme) }
                        )
                    }
                }
            }

            item {
                SettingsToggleRow(
                    title = "AMOLED Black Mode",
                    subtitle = "Use pure black background for dark themes",
                    iconId = R.drawable.ic_brightness, 
                    checked = amoledMode,
                    enabled = isCurrentlyDark,
                    onCheckedChange = { viewModel.setAmoledMode(it) }
                )
                Divider(
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), 
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            item { SettingsSectionHeader(title = "Advanced player") }
            item {
                SettingsToggleRow(
                    title = "Volume Boost (200%)",
                    subtitle = "Amplify software sound beyond limits",
                    iconId = R.drawable.ic_wrench,
                    checked = audioBoost,
                    onCheckedChange = { viewModel.setAudioBoost(it) }
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item { SettingsSectionHeader(title = "Player engine") }
            item {
                DecoderOption(
                    title = "ExoPlayer (Media3)",
                    subtitle = "Default. Best for smooth playback",
                    iconId = R.drawable.ic_gear,
                    selected = currentEngine == PlayerEngine.EXO,
                    onClick = { viewModel.setPlayerEngine(PlayerEngine.EXO) }
                )
                DecoderOption(
                    title = "MPV Engine (HW)",
                    subtitle = "Superfast hardware-accelerated engine",
                    iconId = R.drawable.ic_gear,
                    selected = currentEngine == PlayerEngine.MPV,
                    onClick = { viewModel.setPlayerEngine(PlayerEngine.MPV) }
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item { SettingsSectionHeader(title = "Playback") }
            item {
                SettingsToggleRow(
                    title = "Resume Playback",
                    subtitle = "Continue from where you left off",
                    iconId = R.drawable.ic_play_arrow,
                    checked = resumePlayback,
                    onCheckedChange = { viewModel.setResumePlayback(it) }
                )
                SettingsToggleRow(
                    title = "Auto Rotate",
                    subtitle = "Rotate screen with video orientation",
                    iconId = R.drawable.ic_rotate,
                    checked = autoRotate,
                    onCheckedChange = { viewModel.setAutoRotate(it) }
                )
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Hatake2008"))
                                context.startActivity(intent)
                            }
                            .padding(12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_telegram),
                            contentDescription = "Telegram",
                            modifier = Modifier.size(46.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "@Hatake2008",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/HATAKE2008"))
                                context.startActivity(intent)
                            }
                            .padding(12.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_github),
                            contentDescription = "GitHub",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(46.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "HATAKE2008",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppThemePreviewItem(
    theme: AppTheme,
    isSelected: Boolean,
    isDark: Boolean,
    isAmoled: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isDark && isAmoled) Color.Black else if (isDark) theme.backgroundDark else theme.backgroundLight
    val primary = if (isDark) theme.primaryDark else theme.primaryLight
    val secondary = if (isDark) theme.secondaryDark else theme.secondaryLight
    
    val surfaceColor = if (isDark && isAmoled) Color(0xFF121212) else if (isDark) Color.White.copy(alpha = 0.1f) else Color.Black.copy(alpha = 0.05f)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(84.dp)
                .height(140.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(bgColor)
                .border(
                    width = if (isSelected) 3.dp else 1.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable { onClick() }
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxWidth(0.9f).height(12.dp).clip(RoundedCornerShape(50)).background(surfaceColor))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f).height(18.dp).clip(RoundedCornerShape(50)).background(primary))
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(modifier = Modifier.size(16.dp).clip(CircleShape).background(secondary))
                }
                
                Box(modifier = Modifier.fillMaxWidth(0.9f).height(12.dp).clip(RoundedCornerShape(50)).background(surfaceColor))
                
                Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(primary))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = theme.name,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun SettingsItemPill(
    title: String,
    subtitle: String,
    icon: @Composable () -> Unit,
    trailing: @Composable () -> Unit,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = if (enabled) 0.6f else 0.3f))
            .then(if (onClick != null && enabled) Modifier.clickable { onClick() } else Modifier)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background)
                .alpha(if (enabled) 1f else 0.5f),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f).alpha(if (enabled) 1f else 0.5f)) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Box(modifier = Modifier.alpha(if (enabled) 1f else 0.5f)) {
            trailing()
        }
    }
}

@Composable
private fun DecoderOption(
    title: String,
    subtitle: String,
    iconId: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    SettingsItemPill(
        title = title,
        subtitle = subtitle,
        icon = {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        trailing = {
            RadioButton(
                selected = selected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary,
                    unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        },
        onClick = onClick
    )
}

@Composable
private fun SettingsToggleRow(
    title: String,
    subtitle: String,
    iconId: Int,
    checked: Boolean,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) {
    SettingsItemPill(
        title = title,
        subtitle = subtitle,
        enabled = enabled,
        icon = {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        trailing = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.background,
                    checkedTrackColor = MaterialTheme.colorScheme.primary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    uncheckedTrackColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    )
}

@Composable
private fun SettingsSectionHeader(title: String, paddingTop: androidx.compose.ui.unit.Dp = 8.dp) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 8.dp, bottom = 8.dp, top = paddingTop)
    )
}
