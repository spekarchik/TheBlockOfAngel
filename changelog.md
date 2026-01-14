## 📦 Version 2.17.0

### 📝 Improvements
- Tooltips for **Upgrade and Downgrade Kits** have been extended with related *Smithing Table recipes*.
- The **Rod of Angel** recipe has been added to the **Block of Angel** tooltips.
- Information on how to upgrade **rods** to their *magnetic* versions has been added.
- Other tooltip improvements for better clarity.
- The effects applied by the **Elder Guardian Eye** and the **Energy Crystal** now have their own icons.

### 🐞 Fixed
- Fixed an issue where the **Rod Sensor** could drop unexpectedly during **Builder** downgrading.
- Resolved a conflict between *Night Vision* effects applied by the **Elder Guardian Eye** and helmets.
- Improved behavior related to a conflict between *Speed* effects applied by the **Energy Crystal** and boots.


## 📦 Version 2.16.0

### 📝 Improvements
- Improved **Elder Guardian Eye** usability:
  - *Night Vision* no longer uses a timer.
  - The effect is maintained while the **Eye** is held in either hand.


## 📦 Version 2.15.1

### 🐞 Fixed
- Fixed an issue where the **Planter** failed to continue planting on partially filled rows if the seed stack was smaller than the remaining farmland length.
  Planting range is now evaluated independently of seed count and stops only when seeds run out or a blocking block is encountered.


## 📦 Version 2.14.9

### 🐞 Fixed
- Now armor defense is calculated correctly based on its durability.


## 📦 Version 2.14.8

### 🐞 Fixed
- Monsters stayed undead if the **Block of Devil** was destroyed in any way other than by a player or an explosion.

### 📝 Improvements
- The **Block of Devil** no longer affects surrounding blocks on *Peaceful* difficulty.


## 📦 Version 2.14.7

### 📝 Improvements
- **Cracked Obsidian** texture updated, now appears more violet
- Helmets with **Creeper detector** improved: the *Glowing* effect disappears much faster after the detection conditions are no longer met


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
