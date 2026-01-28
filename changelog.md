## 📦 Version 2.20.5

### 📝 Improvements
- Reduced the average amount of **Diamond Powder** obtained from Diamond armor when destroyed with a **Splash Destroying Potion**.  
  This change makes Diamond recovery less efficient and better balanced.
- Improved the magic exhaustion system:
  - Exhaustion from using the **Builder**, **Track Layer**, and **Planter** has been increased by 2×.
  - **Rod of Terra**, **Fire Rod**, **End Rod**, and **The Rod of Angel** now also cause exhaustion when destroying *Cobwebs*, *Glowstone*, and *Tuff*.
  - **End Rod** and **The Rod of Angel** now cause exhaustion when changing *the weather*.
  - Exhaustion from modded sword effects (generation of *cobwebs*, *cacti*, and *fire*) has been increased by 4×.
    Exhaustion caused by *explosion* generation remains unchanged.
  - Regular attacks with modded swords now also cause additional minor exhaustion.
  - **The Rod of Angel** now causes exhaustion for each monster it despawns while being held in the player’s hand.
  - All magic exhaustion effects are disabled when the player’s *food level* is **0**.
- **Cracked Obsidian** and **Cracked End Stone** now use smelting instead of blasting.
- **Lymonite armor** and **Superyte armor** now restore health under rain only up to 10 HP and now causes higher exhaustion (**1.6** vs **1.0** per second).
- **Lymonite armor** and **Superyte armor** now ignite attackers with a 40% chance instead of 100%.
  

## 📦 Version 2.19.3

### 📝 Improvements
- **Builder**, **Planter**, and **Track Layer** recipes now only work if the source rod is undamaged.


## 📦 Version 2.19.2

### 📝 Improvements
- **Fire Rod**: *Glowstone* now drops Blaze Powder on left-click instead of requiring right-click.
- **End Rod**: *Tuff* now drops Saltpeter on left-click instead of requiring right-click.
- **End Rod**: Changing the weather now consumes saturation and adds exhaustion.
- Tooltips updated to reflect the new behavior.
- Removed unnecessary Jump icon when **Aeryte Armor's** Super Jump effect is active.
- Improved ore handling for area mining.
  - In Enhanced Mode, pickaxes now treat regular and deepslate variants of the same ore as the same ore.
    - When mining ores near Y=0, mixed regular and deepslate ore veins are now mined more naturally.
    - Mining a deepslate ore will also mine matching regular ore blocks in the area.
    - Mining a regular ore still only mines regular ore blocks; deepslate variants remain untouched, since deepslate ore is harder than regular ore.

### 🐞 Fixed
- Fixed block interaction sounds not playing for the player in single-player mode when using magic rods.


## 📦 Version 2.18.0

### 📝 Improvements
- **Aeryte Armor**: *Speed* and *Super Jump* effects can now be toggled independently.
- **Superite Armor**: *Strength* and *Water Breathing* effects are now always active and no longer disabled by *Jump Boost*.
- **Energy Crystal** now applies *Speed IV* and *Haste I*.


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


## 📦 Version 2.15.0

### 📝 Improvements
- **Diamond Armor Upgrade Kit** and **Diamond Tool Upgrade Kit** now allow upgrading *iron* and *copper* gear to its diamond version.

### ✨ New
- Added **Iron Armor Upgrade Kit** and **Iron Tool Upgrade Kit**, allowing upgrades of *copper gear* to its iron version.
- Upgraded gear preserves its enchantments.


## 📦 Version 2.14.10

### 📝 Improvements
- Creative mode inventory: the mod tab icon was changed to **Inactivated Block of Angel**.
- The chance to breed a *rare axolotl* is now 100% on *Peacful difficulty* when the player worn in **Aquarite Armor**.


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


## 📦 Version 2.14.6

### 🐞 Fixed
- Armor trims updated according to **Correp Armor**
- Loot tables updated to account for changes in Minecraft 1.21.9


## 📦 Version 2.14.5
- Minecraft 1.21.9 is now supported.
