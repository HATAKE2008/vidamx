<div align="center">

```
__   ___    _ __  __           
\ \ / (_)__| |  \/  |__ ___ __
 \ V /| / _` | |\/| / _` \ \ /
  \_/ |_\__,_|_|  |_\__,_/_\_\
```

🎬 **Free · Open Source · No Ads · No Tracking** 🎵  
*Powered by Media3 · Built in Kotlin · Designed with Material 3*

<br/>

[![Build](https://github.com/HATAKE2008/vidamx/actions/workflows/build.yml/badge.svg)](https://github.com/HATAKE2008/vidamx/actions/workflows/build.yml)
[![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)](./LICENSE)
[![Platform](https://img.shields.io/badge/Platform-Android-green?style=flat-square&logo=android)](https://android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-100%25-purple?style=flat-square&logo=kotlin)](https://kotlinlang.org)

[![Material3](https://img.shields.io/badge/Material-3-teal?style=flat-square&logo=materialdesign)](https://m3.material.io)
[![Ad-Free](https://img.shields.io/badge/Ads-None%20Ever-red?style=flat-square)](#)
[![Themes](https://img.shields.io/badge/Themes-28%20Offline-orange?style=flat-square)](#-themes)
[![GitHub](https://img.shields.io/badge/GitHub-HATAKE2008-black?style=flat-square&logo=github)](https://github.com/HATAKE2008/vidamx)

</div>

---

## ✨ Why VidMax?

> **No ads. No tracking. No bloat.**

A fast, beautiful media player for local video & music — plus a full-featured online music streaming experience built for privacy. Primary engine is **Jetpack Media3 (ExoPlayer)**, with **MPV** as a powerful secondary engine for broader codec support.

---

## 🎵 Online Music Streaming *(New!)*

Stream millions of songs — **no login, no premium required.**

| Feature | Detail |
|---|---|
| 🏠 Home Feed | Quick Picks, Daily Discover, Keep Listening, Forgotten Favorites |
| 🎤 Artist Discovery | Browse via `ChannelTabInfo` integration |
| 😌 Mood Playlists | Spotify-style curated mood screens |
| 🔍 Smart Search | Crossfade animations + auto-focus |
| 📻 Auto-Next Radio | Continuous Meld-style radio queues |
| 🖼️ Artwork | High-res art (`maxresdefault`) + shimmer skeletons |
| 📊 Trending | Last.fm API with iTunes RSS fallback |

---

## 🎬 Local Playback

### ▶️ Playback
- **Media3 + MPV** dual-engine support
- **Music & Video** — full-featured player, not just video
- **Modern Player & Wavy Player** UI styles
- **Animated seekbars** — Classic, Squiggly, or Wavy (sine wave)
- **Subtitles** — built-in SRT parser and more
- **Speed control** — 0.25× to 2×
- **Aspect ratio** — fit, fill, stretch and more
- **Shuffle & Repeat** — all modes including repeat-one

### 📚 Library
- **Folders view** — browse like your file manager
- **Playlists** — create and manage your own
- **Audio mode** — background playback from video files
- **Smart search** — instant lookup across your library
- **Sort & filter chips** — name, date, size, duration

### 🎨 UI & Experience
- Material 3 — expressive motion, modern typography, tonal elevation
- Mini player while browsing your library
- **28 handcrafted offline themes**
- Grid & List view toggle
- **No ads. Ever.**
- In-app update checker via GitHub Releases

---

## 🎨 Themes

28 fully offline themes. No dynamic color, no wallpaper dependency — works on any Android 5+ device.

> Switch from **Settings → Appearance → Theme**

| Theme | Color | Mood |
|---|---|---|
| Midnight | ![#0A0E1A](https://placehold.co/50x18/0A0E1A/0A0E1A) | Deep dark blue-black |
| AMOLED | ![#000000](https://placehold.co/50x18/000000/000000) | Pure black for OLED |
| Ocean | ![#071825](https://placehold.co/50x18/071825/40E0D0) | Deep teal depths |
| Forest | ![#071510](https://placehold.co/50x18/071510/4CAF50) | Rich dark greens |
| Rose | ![#1A0810](https://placehold.co/50x18/1A0810/FF80AB) | Soft rose glow |
| Amber | ![#1A1000](https://placehold.co/50x18/1A1000/FFB300) | Warm golden hour |
| Lavender | ![#150D1F](https://placehold.co/50x18/150D1F/CE93D8) | Dreamy purple |
| Neon Lime | ![#0A1A00](https://placehold.co/50x18/0A1A00/CCFF00) | Electric green energy |
| Jade Mist | ![#001A12](https://placehold.co/50x18/001A12/00E5A0) | Cool jade calm |
| Magenta Pulse | ![#1A0015](https://placehold.co/50x18/1A0015/FF00CC) | Vivid magenta burst |
| Deep Indigo | ![#080B1A](https://placehold.co/50x18/080B1A/5C6BC0) | Classic indigo depth |
| **+ 17 more** | | All included, all offline |

All themes use **WCAG-compliant contrast** — always readable, no eye strain.

---

## 🏗️ Architecture

```
UI Layer  (Jetpack Compose)
     │
ViewModel Layer
     │
Repository Layer
     │
Data / Services
(MPV · MediaSession · Last.fm · ContentResolver)
```

**Tech Stack**

| Layer | Technology |
|---|---|
| Language | Kotlin 100% |
| UI | Compose + Material 3 |
| Architecture | MVVM + Clean Architecture |
| Primary Engine | Jetpack Media3 (ExoPlayer) |
| Secondary Engine | MPV (JNI bridge) |
| Audio | MediaSession / AudioService |
| Build | Gradle (Kotlin DSL) |
| CI/CD | GitHub Actions |

---

## 📁 Project Structure

```
app/src/main/java/com/vidmax/player/
├── data/
│   ├── model/       ← VideoItem, AudioItem, OnlineSong
│   └── repository/  ← Video, Audio, OnlineMusic repos
├── service/
│   ├── PlaybackService.kt
│   └── AudioService.kt
├── ui/
│   ├── components/  ← MiniPlayer, SearchBar, SortChips
│   ├── online/      ← OnlineMusic, MoodPlaylist screens
│   ├── player/      ← PlayerActivity, AnimatedSlider
│   ├── screen/      ← Home, Folders, Playlist, Settings
│   └── theme/       ← Material 3 Color + Theme
├── utils/
│   └── SubtitleParser.kt, LastFmClient.kt
└── viewmodel/
    ├── LibraryViewModel.kt
    ├── MusicHomeViewModel.kt
    └── PlayerViewModel.kt
```

---

## 🚀 Getting Started

**Requirements:** Android Studio Hedgehog+, SDK 21+, NDK (for MPV)

```bash
git clone https://github.com/HATAKE2008/vidamx.git
cd vidamx
./gradlew assembleDebug
```

Or grab the latest APK from [**Releases →**](https://github.com/HATAKE2008/vidamx/releases)

---

## 🤝 Contributing

1. Fork → Feature branch → Commit → Pull Request
2. Follow existing Kotlin style and Material 3 guidelines

Found a bug? Open an [**Issue**](https://github.com/HATAKE2008/vidamx/issues) with device info, steps to reproduce, and logcat.

---

## 📜 License

MIT © 2026 HATAKE2008 — see [LICENSE](./LICENSE)

---

## 🙏 Acknowledgements

[Media3](https://developer.android.com/media/media3) · [MPV](https://mpv.io/) · [Jetpack Compose](https://developer.android.com/jetpack/compose) · [Material 3](https://m3.material.io/) · [Last.fm API](https://www.last.fm/api)

---

<div align="center">

Made with ❤️ in Kotlin

*Saved from a sketchy ad-filled app? Give it a* ⭐

</div>
