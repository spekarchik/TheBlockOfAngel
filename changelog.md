## 📦 Version 2.14.5

### 🐞 Fixed
- Fixed a conflict where the **Energy Crystal** failed to apply the *Speed* effect if the player was wearing boots with built-in togglable *Speed*, even when the effect was turned off.


## 📦 Version 2.14.4

### 🐞 Fixed
- Fixed a bug where breaking an *Amethyst Cluster* with any pickaxe from the mod always dropped 2 *Amethyst Shards*, as if it was broken without a pickaxe.


## 📦 Version 2.14.3

### 🐞 Fixed
- Fixed armor effect toggles not disabling infinite-duration status effects other than *Levitation*.


## 📦 Version 2.14.2

### 🐞 Fixed
- Fixed overly restrictive logic that prevented dropping blocks adjacent to water when the player was inside it.

### 📝 Improvements
- Players can now drop blocks holding back lava while inside lava.


## 📦 Version 2.14.1

### 🐞 Fixed
- Fixed an issue where players wearing **Superyte Armor** could get stuck with an infinite *Levitation* effect when hit by a shulker.
  - This situation should no longer occur.
  - If it does, the armor now correctly disables infinite *Levitation* effects, even if they were not applied by the armor itself.


## 📦 Version 2.14.0

### 📝 Improvements
- Improved safety logic for **Aquarite** pickaxes and shovels:
  - Tools now prevent destruction of blocks that would cause suspicious blocks above to fall and break.
  - Tools now avoid breaking blocks if doing so would cause falling blocks to fall and uncover water above.
- Improved safety logic for **Rendelite** pickaxes and shovels:
  - Tools now avoid breaking blocks if doing so would cause falling blocks to fall and uncover lava above.
- Improved safety logic for **Superyte** pickaxes and shovels:
  - Tools now prevent destruction of blocks that would cause suspicious blocks above to fall and break.
  - Tools now avoid breaking blocks if doing so would cause falling blocks to fall and uncover water or lava above.


## 📦 Version 2.13.3

- Promoted from 'beta' to stable 'release'


## 📦 Version 2.13.3-beta

- Initial publishing
