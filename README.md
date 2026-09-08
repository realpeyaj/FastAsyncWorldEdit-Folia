# FAWElia - FastAsyncWorldEdit for Folia

[![Platform](https://img.shields.io/badge/Platform-Folia%20%7C%20Paper-007acc?style=for-the-badge&logo=minecraft)](https://papermc.io/software/folia)
[![Minecraft Version](https://img.shields.io/badge/Minecraft-26.X%20(26.1%20--%2026.2)-green?style=for-the-badge)](https://www.minecraft.net/)
[![Java Version](https://img.shields.io/badge/Java-21%2B%20%2F%2025-orange?style=for-the-badge&logo=openjdk)](https://adoptium.net/)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg?style=for-the-badge)](https://www.gnu.org/licenses/gpl-3.0)

A high-performance fork of [FastAsyncWorldEdit](https://github.com/IntellectualSites/FastAsyncWorldEdit) with full, native [Folia](https://github.com/PaperMC/Folia) multi-threaded region architecture support, tailored specifically for Minecraft 26.X (26.1 and 26.2).

---

## Why FAWElia?

Upstream FAWE is optimized for single-threaded main-loop servers (Paper/Spigot) and throws `UnsupportedOperationException` or crashes with data races when run on Folia due to Folia's threaded region model. 

FAWElia re-architects FAWE's scheduling and NMS adapters to comply fully with Folia region boundaries, allowing you to manipulate millions of blocks, paste massive schematics, and run complex brushes across multi-region servers at peak TPS.

### Folia Innovations and Architecture

- **Region-Aware Scheduling**: All tile entity updates, block mutations, and beacon events dispatch to `Bukkit.getRegionScheduler()` anchored to exact world coordinates.
- **Thread-Safe Entity Handling**: Entity spawning and removals run via `entity.getScheduler()`, completely avoiding main-thread lockups.
- **Asynchronous Teleportation**: Player movements and unstuck operations leverage non-blocking `teleportAsync` without dangerous `.join()` stalls.
- **Direct Packet Dispatching**: Chunk visual refresh packets are sent concurrently to nearby players without depending on the single-threaded server tick executor.
- **Folia-Ready `//regen`**: World regeneration coordinates with Folia's regionized world initialization on the spawn chunk.
- **Zero External Dependencies**: Implemented using internal `FoliaUtil` and `PaperSupport` without third-party shims.
- **Streamlined 26.X Focus**: Legacy 1.21 modules and older Paperweight overhead are removed, drastically reducing compile times and allowing native builds with Java 25.

---

## Compatibility and Requirements

| Platform | Supported Versions | Notes |
|---|---|---|
| **Folia** | `26.2`, `26.1` | Native multi-threaded regionized execution |
| **Paper** | `26.2`, `26.1` | Full backwards compatibility with standard Paper |
| **Java** | `21`, `25` | Java 21 or Java 25 runtime required |

---

## Installation

1. Download the latest `FAWElia-*.jar` from [Releases](https://github.com/realpeyaj/FAWElia/releases) or Modrinth.
2. Place the `.jar` into your server's `plugins/` folder.
3. Start or restart your server.
4. Look for the startup confirmation in your console:
   ```log
   [INFO]: [FastAsyncWorldEdit] Enabling FastAsyncWorldEdit v...
   [INFO]: Using com.sk89q.worldedit.bukkit.adapter.impl.fawe.v26_2.PaperweightFaweAdapter as the Bukkit adapter
   ```

---

## Building from Source

Ensure you have Java 21 or Java 25 installed.

```bash
# Clone the repository
git clone https://github.com/realpeyaj/FAWElia.git
cd FAWElia

# Build the shaded Folia plugin JAR
./gradlew :worldedit-bukkit:shadowJar
```

The compiled JAR will be located at:
```
worldedit-bukkit/build/libs/FAWElia-2.15.5-SNAPSHOT.jar
```

---

## Features

All standard FastAsyncWorldEdit features are available on Folia:
- Over 200 commands and expansive brush/tool sets
- Unlimited `//undo` and `//redo` with per-world history
- Fast chunk placement (NMS) with direct level section writes
- Lazy copy (`//lazycopy`) for instant, low-memory clipboard handling
- Masks, patterns, and transforms for advanced procedural generation
- LZ4 and ZSTD compression for minimal memory footprint and fast disk caching

---

## Credits and License

- Original FastAsyncWorldEdit by [IntellectualSites](https://github.com/IntellectualSites/FastAsyncWorldEdit) and contributors.
- Original WorldEdit by sk89q and the EngineHub team.
- Licensed under the GNU General Public License v3.0 (GPLv3).
