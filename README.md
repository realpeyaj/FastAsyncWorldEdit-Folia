# FAWElia - FastAsyncWorldEdit for Folia

![Java](https://img.shields.io/badge/Java-21%20%7C%2025-orange.svg)
![Platform](https://img.shields.io/badge/Platform-Folia-blue.svg)
![Minecraft](https://img.shields.io/badge/Minecraft-26.X-brightgreen.svg)
![Version](https://img.shields.io/badge/Version-2.15.4-green.svg)
![License](https://img.shields.io/badge/License-GPLv3-blue.svg)
[![bStats](https://img.shields.io/bstats/servers/33922?color=blue)](https://bstats.org/plugin/bukkit/FAWElia/33922)

A high-performance fork of [FastAsyncWorldEdit](https://github.com/IntellectualSites/FastAsyncWorldEdit) with full, native [Folia](https://github.com/PaperMC/Folia) multi-threaded region architecture support, tailored specifically for Minecraft 26.X (26.1 and 26.2).

---

## Why FAWElia?

Upstream FAWE is optimized for single-threaded main-loop servers (Paper/Spigot) and throws `UnsupportedOperationException` or crashes with data races when run on Folia due to Folia's threaded region model. 

FAWElia re-architects FAWE's scheduling and NMS adapters to comply fully with Folia region boundaries, allowing you to manipulate millions of blocks, paste massive schematics, and run complex brushes across multi-region servers at peak TPS.

### Folia Feature Test Status

| Category | Feature / Command | Status | Example Command | Notes |
| :--- | :--- | :---: | :--- | :--- |
| **Basic Edits** | `//set <pattern>` | ✓ | `//set stone` | RegionScheduler flush working |
| **Basic Edits** | `//undo` & `//redo` | ✓ | `//undo` then `//redo` | Restores blocks and chunk packets cleanly |
| **Geometry** | `//sphere`, `//cyl`, `//pyramid` | ✓ | `//sphere stone 5` | Cross-chunk block placement verified |
| **Geometry** | `//walls`, `//faces` | ✓ | `//walls glass` | Boundary placement operational |
| **Selection** | `//pos1`, `//pos2`, `//wand` | ✓ | `//pos1` and `//pos2` | Coordinates and bounds set accurately |
| **Tools** | `//tree <type>` | ✓ | `//tree birch` | Snapshot buffer fixed, fully undoable |
| **Tools** | `/brush sphere`, `cyl` | ✓ | `/brush sphere stone 5` | Fixed async dispatch via AsyncNotifyKeyedQueue |
| **Tools** | `//sp single` | ✓ | `//sp single` | Drops scheduled on region thread |
| **Clipboard** | `//copy` & `//paste` (blocks) | ✓ | `//copy` then `//paste` | Static blocks copy and paste cleanly |
| **Clipboard** | `//copy` (with mobs nearby) | ✓ | `//copy` | Safe handling when mobs are unowned |
| **Clipboard** | `//copy -e` & `//paste -e` | ✓ | `//copy -e` then `//paste -e` | Direct handle reflection + region-safe spawn |
| **Clipboard** | `//rotate` & `//flip` | ✓ | `//rotate 90` then `//paste` | Geometric transformation on clipboard |
| **Clipboard** | `//stack` & `//move` | ✓ | `//stack 3 up` | Multi-chunk directional block shifting |
| **Regeneration** | `//regen` | ✓ | `//regen` | Folia world generation populator (`initWorldForFolia`) |
| **Schematics** | `//schem save` & `load` | ✓ | `//schem save test1` | Disk I/O, format parsing, and paste |
| **Biomes** | `//setbiome <biome>` | ✓ | `//setbiome desert` | Biome palette change across chunk borders |
| **Biomes** | `//biomeinfo` | ✓ | `//biomeinfo` | Coordinate-based biome inspection |
| **Navigation** | `//thru`, `//jumpto`, `//unstuck` | ✓ | `//thru` | Non-blocking player teleportation on Folia |
| **Navigation** | `//ascend`, `//descend` | ✓ | `//ascend` | Vertical safe location searching and teleport |
| **Masks & Filter** | `//replace <from> <to>` | ✓ | `//replace dirt stone` | Targeted block replacement |
| **Masks & Filter** | Masked brush | ✓ | `/brush sphere stone 5 -m grass_block` | Block filtering under brush placement |
| **Utilities** | `//drain`, `//fixwater` | ✓ | `//drain 15` | Fluid block search and region-safe clearing |
| **Utilities** | `//fixlava` | ✓ | `//fixlava 15` | Lava source generation and flow leveling |
| **Utilities** | `//snow` & `//thaw` | ✓ | `//snow 20` | Surface snow placement and melting |
| **Physics** | `//set sand` (falling blocks) | ✓ | `//set sand` | Gravity update checks across region borders |
| **Inspection** | `//distr`, `//count` | ✓ | `//distr` | Fast parallel block counting across chunks |
| **Brushes** | `/brush smooth` | ✓ | `/brush smooth 5 3` | Iterative terrain height smoothing |
| **Brushes** | `/brush gravity` | ✓ | `/brush gravity 5` | Gravity simulation brush |
| **Stress Test** | Large area edits (100k+ blocks) | ? | `//sphere stone 30` | Massive multi-region chunk queue flush |
| **Performance** | Concurrent multi-region edits | ? | 2 players running `//set` in different chunks | Thread isolation across independent Folia regions |

---

## Compatibility and Requirements

| Platform | Supported Versions | Notes |
|---|---|---|
| **Folia** | `26.2`, `26.1` | Native multi-threaded regionized execution |
| **Paper** | `26.2`, `26.1` | Full backwards compatibility with standard Paper |
| **Java** | `21`, `25` | Java 21 or Java 25 runtime required |

---

## Installation

1. Download the latest `FAWElia-*.jar` from [Releases](https://github.com/realpeyaj/FAWE-lia/releases) or Modrinth.
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
git clone https://github.com/realpeyaj/FAWE-lia.git
cd FAWE-lia

# Build the shaded Folia plugin JAR
./gradlew :worldedit-bukkit:shadowJar
```

The compiled JAR will be located at:
```
worldedit-bukkit/build/libs/FAWElia-2.15.4.jar
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
