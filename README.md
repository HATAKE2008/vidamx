<img src="https://capsule-render.vercel.app/api?type=waving&color=0A0E1A,071825&height=150&section=header&text=VidMax&fontSize=80&fontColor=40E0D0&fontAlignY=50&animation=twinkling&desc=Free%20·%20Open%20Source%20·%20No%20Ads%20·%20No%20Tracking&descAlignY=72&descSize=17&descColor=88cccc" width="100%"/>

<div align="center">

<br/>

<img src="https://skillicons.dev/icons?i=kotlin,androidstudio,github,gradle&theme=dark" height="36" />

<br/><br/>

[![Build](https://github.com/HATAKE2008/vidamx/actions/workflows/build.yml/badge.svg)](https://github.com/HATAKE2008/vidamx/actions/workflows/build.yml)
[![License](https://img.shields.io/badge/License-MIT-2563EB?style=flat-square&logo=opensourceinitiative&logoColor=white)](./LICENSE)
[![Platform](https://img.shields.io/badge/Android-SDK%2021+-3DDC84?style=flat-square&logo=android&logoColor=white)](https://android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-100%25-7F52FF?style=flat-square&logo=kotlin&logoColor=white)](https://kotlinlang.org)

[![Material3](https://img.shields.io/badge/Material%203-UI-009688?style=flat-square&logo=materialdesign&logoColor=white)](https://m3.material.io)
[![Ad-Free](https://img.shields.io/badge/Ads-None%20Ever-DC2626?style=flat-square&logo=adguard&logoColor=white)](#)
[![Themes](https://img.shields.io/badge/Themes-28%20Offline-EA580C?style=flat-square)](#-themes)
[![Streaming](https://img.shields.io/badge/Music-Streaming-FF0044?style=flat-square&logo=youtubemusic&logoColor=white)](#-online-music-streaming)

<br/>

</div>

---

## ✦ &nbsp;Why VidMax?

> **No ads. No tracking. No bloat.**

A fast, beautiful media player for local video & music — plus a full-featured online music streaming experience built for privacy. Primary engine: **Jetpack Media3 (ExoPlayer)**. Secondary: **MPV** for broader codec support.

---

## ♫ &nbsp;Online Music Streaming &nbsp;[![](https://img.shields.io/badge/-NEW-FF0044?style=flat-square&logo=youtubemusic&logoColor=white)](#)

Stream millions of songs — **no login, no premium required.**

| Feature | Detail |
|---|---|
| Home Feed | Quick Picks · Daily Discover · Keep Listening · Forgotten Favorites |
| Artist Discovery | Browse via `ChannelTabInfo` integration |
| Mood Playlists | Spotify-style curated mood screens |
| Smart Search | Crossfade animations + auto-focus |
| Auto-Next Radio | Continuous Meld-style radio queues |
| Artwork | High-res `maxresdefault` + shimmer loading skeletons |
| Trending | ![Last.fm](https://img.shields.io/badge/Last.fm-API-D51007?style=flat-square&logo=lastdotfm&logoColor=white) with iTunes RSS fallback |

---

## ▶ &nbsp;Local Playback

### Playback
▸ **Media3 + MPV** dual-engine support  
▸ **Music & Video** — full-featured media player  
▸ **Modern Player & Wavy Player** UI styles  
▸ **Animated seekbars** — Classic, Squiggly, or Wavy  
▸ **Subtitles** — built-in SRT parser  
▸ **Speed control** — 0.25× to 2×  
▸ **Aspect ratio** — fit, fill, stretch & more  
▸ **Shuffle & Repeat** — all modes  

### Library
▸ **Folders view** — browse like your file manager  
▸ **Playlists** — create and manage  
▸ **Audio mode** — background playback from video files  
▸ **Smart search** — instant library lookup  
▸ **Sort & filter chips** — name, date, size, duration  

### UI & Experience
▸ Material 3 — expressive motion, tonal elevation  
▸ Mini player while browsing  
▸ **28 handcrafted offline themes**  
▸ Grid & List view toggle  
▸ **No ads. Ever.**  
▸ In-app update checker via GitHub Releases  

---

## ◈ &nbsp;Themes

28 fully offline themes — works on any Android 5+ device. No dynamic color, no wallpaper dependency.

> Switch from **Settings → Appearance → Theme**

| Theme | Preview | Mood |
|---|---|---|
| Midnight | ![#0A0E1A](https://placehold.co/50x18/0A0E1A/0A0E1A) | Deep dark blue-black |
| AMOLED | ![#000000](https://placehold.co/50x18/000000/000000) | Pure black for OLED |
| Ocean | ![#071825](https://placehold.co/50x18/071825/40E0D0) | Deep teal depths |
| Forest | ![#071510](https://placehold.co/50x18/071510/4CAF50) | Rich dark greens |
| Rose | ![#1A0810](https://placehold.co/50x18/1A0810/FF80AB) | Soft rose glow |
| Amber | ![#1A1000](https://placehold.co/50x18/1A1000/FFB300) | Warm golden hour |
| Lavender | ![#150D1F](https://placehold.co/50x18/150D1F/CE93D8) | Dreamy purple |
| Neon Lime | ![#0A1A00](https://placehold.co/50x18/0A1A00/CCFF00) | Electric energy |
| Jade Mist | ![#001A12](https://placehold.co/50x18/001A12/00E5A0) | Cool jade calm |
| Magenta Pulse | ![#1A0015](https://placehold.co/50x18/1A0015/FF00CC) | Vivid magenta burst |
| Deep Indigo | ![#080B1A](https://placehold.co/50x18/080B1A/5C6BC0) | Classic depth |
| **+ 17 more** | | All included, all offline |

All themes use **WCAG-compliant contrast** — always readable, no eye strain.

---

## ⬡ &nbsp;Architecture

```
UI  (Jetpack Compose)
     │
ViewModel
     │
Repository
     │
Data & Services
(MPV · MediaSession · Last.fm · ContentResolver)
```

**Tech Stack**

| Technology | Role |
|---|---|
| ![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white) | Language — 100% |
| ![Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white) | UI toolkit + Material 3 |
| ![MVVM](https://img.shields.io/badge/MVVM-Clean%20Architecture-6366F1?style=flat-square) | Architecture pattern |
| ![Media3](https://img.shields.io/badge/Media3-ExoPlayer-0F9D58?style=flat-square&logo=google&logoColor=white) | Primary media engine |
| ![MPV](https://img.shields.io/badge/MPV-JNI%20Bridge-111111?style=flat-square) | Secondary media engine |
| ![Gradle](https://img.shields.io/badge/Gradle-Kotlin%20DSL-02303A?style=flat-square&logo=gradle&logoColor=white) | Build system |
| ![Actions](https://img.shields.io/badge/GitHub%20Actions-CI%2FCD-2088FF?style=flat-square&logo=githubactions&logoColor=white) | Automation |

---

## ⌗ &nbsp;Project Structure

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
│   └── SubtitleParser.kt  ·  LastFmClient.kt
└── viewmodel/
    ├── LibraryViewModel.kt
    ├── MusicHomeViewModel.kt
    └── PlayerViewModel.kt
```

---

## ↗ &nbsp;Getting Started

**Requirements**

![Studio](https://img.shields.io/badge/Android%20Studio-Hedgehog+-3DDC84?style=flat-square&logo=androidstudio&logoColor=white)
![SDK](https://img.shields.io/badge/SDK-21+-3DDC84?style=flat-square&logo=android&logoColor=white)
![NDK](https://img.shields.io/badge/NDK-Required%20for%20MPV-EA580C?style=flat-square&logo=android&logoColor=white)

```bash
git clone https://github.com/HATAKE2008/vidamx.git
cd vidamx
./gradlew assembleDebug
```

Or download the latest APK → [**Releases**](https://github.com/HATAKE2008/vidamx/releases)

---

## ⇄ &nbsp;Contributing

1. Fork → Feature branch → Commit → Pull Request
2. Follow existing Kotlin style and Material 3 guidelines

Found a bug? Open an [**Issue**](https://github.com/HATAKE2008/vidamx/issues) with device info, steps to reproduce, and logcat output.

---

## ◻ &nbsp;License

MIT © 2026 HATAKE2008 — see [LICENSE](./LICENSE)

---

## ◇ &nbsp;Acknowledgements

[![Media3](https://img.shields.io/badge/Jetpack%20Media3-Primary%20Engine-0F9D58?style=flat-square&logo=google&logoColor=white)](https://developer.android.com/media/media3)
[![MPV](https://img.shields.io/badge/MPV-Secondary%20Engine-111111?style=flat-square)](https://mpv.io/)
[![Compose](https://img.shields.io/badge/Compose-UI%20Toolkit-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Material3](https://img.shields.io/badge/Material%203-Design%20System-009688?style=flat-square&logo=materialdesign&logoColor=white)](https://m3.material.io/)
[![Lastfm](https://img.shields.io/badge/Last.fm-Metadata-D51007?style=flat-square&logo=lastdotfm&logoColor=white)](https://www.last.fm/api)

---

<img src="https://capsule-render.vercel.app/api?type=waving&color=0A0E1A,071825&height=100&section=footer" width="100%"/>

<div align="center">

Made with ♥ in Kotlin

*Saved from a sketchy ad-filled app? Give it a* ⭐

</div>
