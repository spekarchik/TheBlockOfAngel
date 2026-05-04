## 📦 Version 3.8.0

### 📝 Improvements
- Reworked **Enhanced Tools** mining behavior in *Enhanced Mode*.
  - Tools can now mine multiple blocks at once, regardless of block state differences.
  - Blocks with block entities are still mined individually.
  - *Axes* and *hoes* only affect blocks of the same type.
  - *Pickaxes* only affect full blocks when mining multiple blocks.
- Improved **Helmets with Creeper Detector**:
  - The player hears a periodic sound when a creeper is within 17 blocks (existing behavior).
  - When a creeper is within 10 blocks, the sound becomes more frequent.


## 📦 Version 3.7.0

### 📝 Improvements
- Improved compatibility with other mods by removing all remaining JSON loot tables.
  The mod no longer overrides any loot tables and does not rely on loot content.


## 📦 Version 3.6.1

### 📝 Improvements
- **Magnetic Rods** now detect *amethyst geodes* using **Amethyst blocks** instead of **Smooth Basalt** and **Calcite**.


## 📦 Version 3.5.3

### 📝 Improvements
- Added more details to the **Diamite Armor** tooltip.


## 📦 Version 3.5.2

### 📝 Improvements
- **Key bindings** are now grouped under a separate category.


## 📦 Version 3.5.0

### ✨ New
- Added an advancement system.
- ℹ️ **Cracked End Stone is now best mined with a pickaxe** (was shovel).
- **Cracked End Stone** no longer requires a correct tool to drop (**Cracked Obsidian** still requires one).
- Breaking **Cracked End Stone** and **Cracked Obsidian** now give less XP.
- Sounds for **Cracked End Stone** and **Cracked Obsidian** changed to more appropriate ones.

### 📝 Improvements
- **Block of Angel** now warms up over an in-game day (12s on *Peaceful* difficulty) after being placed in the world or awakened with *Echo Shard*.
  - While warming up, it does not remove monsters
  - New animation added for the warmed-up state
  - Tooltips updated for both **Block of Angel** and **Dormant Block of Angel**

### ⚙️ Others
- Added console commands for testing:
  - `dayLock [night|cancel]` — set clear weather, time (day or night), and freeze the weather & day/night cycle.
  - `xp500 [level]` — set the player's experience to 500 levels, or to the specified level if provided.
  - `resetProgress` — reset player's advancements and unlocked recipes.
- Added `basic` parameter for `enchantMax` and `enchantArmorMax` console commands:
  - `enchantMax basic` – apply basic enchantments to the main-hand item
  - `enchantArmorMax basic` – apply basic enchantments to worn armor
  - basic enchantments exclude *Mending*, *Thorns*, *Knockback*, *Flame*, *Sweeping Edge*, *Fortune*, and other enchantments that may interfere with testing


## 📦 Version 3.4.0

### 📝 Improvements
- Recipes:
  - **Aerite Plate** recipe updated: replaced **Basalt-Blaze Fiber** with **Resin Clump**, removing the need to visit the *Nether*.
- Sword durability:
  - Increased durability of **Enhanced Superyte Sword** from 525 to 1020.
  - Durability of **Primary Superyte Sword** adjusted to 510 (previously 525).
- **Diamite Armor** now breaks more blocks under the player:
  - Cracked Deepslate Bricks
  - Cracked Deepslate Tiles
  - Cracked Nether Bricks
  - Cracked Polished Blackstone Bricks
  - Cracked Stone Bricks
  - Infested Cracked Stone Bricks
- Animals no longer take damage from explosions caused by the rider's **Diamite or Superyte Sword**.
- Tooltips:
  - Tooltips globally redesigned: durability indicators (●○) added for all items with durability, item stats simplified and clarified, icons added, and descriptions made more readable.
  - **End Rod** (mod item) has been renamed to **Ender Rod** to prevent confusion with the vanilla End Rod.
- Textures:
  - Improved the texture of **Lymonite Horse Armor**.


## 📦 Version 3.3.0

### 📝 Improvements
- Rain regeneration exhaustion rebalanced for **Lymonite and Superyte Armor**.
  Hunger was draining too quickly while Lymonite and Superyte Armor regenerated health in rain.
  Exhaustion gain has been reduced **4×**.
- **Rod of Angel** no longer removes monsters when out of durability, even when placed as a block.
- **Lymonite, Aerite and Superyte Boots** no longer prevent the player from sinking in Powdered Snow when combined with **Diamite Armor** pieces.
- *Heavyness* effect now cancels *Dolphin's Grace* and prevents it from being applied. This improves the balance when the player combines **Aquarite Armor** with **Lymonite Leggings** and activates *regeneration* underwater.
- Additional improvements to armor cross-compatibility (when combining pieces from different sets).
- Various performance optimizations.

### ⚙️ Armor Mechanics Update
Reworked several armor weaknesses to make them more consistent and intuitive.

**Limonite Armor**
- Explosions and Wind Charge attacks now bypass armor protection.
- Durability now drains **10× faster** when damaged by these sources.

**Limonite Horse Armor**
- Explosions and Wind Charge attacks now bypass armor protection.

**Lapis Armor**
- Fire, Magma, and Lava now bypass armor protection and wear the armor **10× faster**.
- Freezing damage still deals **double damage**.

**Diamite Armor**
- Bites from spiders, silverfish, and bees now bypass armor protection.

**General Changes**
- Armor no longer increases incoming damage (except for freezing).
- Weaknesses now work by **bypassing armor protection** or **accelerating durability loss**, making armor behavior more predictable.

### ✨ New
- Added console commands for testing purposes:
  - `damageMainHand [half | <damageValue>]` – set damage of main-hand item
  - `damageArmor [half | <damageValue>]` – set damage of worn armor
  - `repairMainHand [half | <durabilityValue>]` – set durability of main-hand item
  - `repairArmor [half | <durabilityValue>]` – set durability of worn armor
  - `hp [<hpValue>]` – set player health
  - `food [<foodLevel>]` – set hunger level (resets saturation)
  - `enchantMax [all | clear]` – apply max compatible enchantments to main-hand item
  - `enchantArmorMax [all | clear]` – apply max compatible enchantments to worn armor
  
### 🐞 Fixed
- **Diamite and Superyte Armor** did not reduce damage from some explosion sources.
- **Lymonite Horse Armor** did not increase or bypass damage from some explosion sources.
- When wearing a full **Aerite Armor** set with *Slow Falling* activated by the armor, removing any piece except the chestplate did not deactivate the effect.


## 📦 Version 3.2.0

### 📝 Improvements
- **Lymonite Horse Armor** item texture adjusted: now aligned with vanilla horse armor.
- Downgrading recipes for animal armor have been added:
  - **Lymonite Horse Armor**
  - **Rendelite Wolf Armor**


### 🐞 Fixed
- Shift-crafting behavior in the *Smithing Table*:
  - Shift-crafting is now blocked for downgrading recipes using the **Downgrade Kit** to prevent incorrect ingredient delivery.
  - Shift-crafting no longer consumes **Animal Armor Handbooks** when crafting armor.


## 📦 Version 3.1.0

### 🐞 Fixed
- Fixed an issue where the Rod of Angel didn't despawn monsters when held by the player.

### 📝 Improvements
- **Horse Lymonite Armor** can now be found in *Jungle Temples* or *Woodland Mansions*.


## 📦 Version 3.0.2

### 📝 Improvements
- Minor performance optimizations.


## 📦 Version 3.0.1

### 📝 Improvements
- **Bucket of Blue Axolotl** renamed to **Bucket of Rare Axolotl**.
- Stack size adjustments:
  - **The Dormant Block of Angel** - reduced from 64 to 1.
  - **The Block of Angel** - reduced from 64 to 1.
  - **The Block of Devil** - reduced from 64 to 1.
  - **Bucket of Rare Axolotl** - reduced from 64 to 1.
  - **Animal Armor Handbook** - reduced from 64 to 4.
  - **Armor plates** - reduced from 64 to 16.


## 📦 Version 3.0.0

### ✨ New
- Animal armor has been added:
  - **Rendelite Wolf Armor**
    - Designed to protect wolfs in *Nether*.
    - Has properties similar to players' **Rendelite Armor**.
    - Has immunity from *Lava*, *Fire*, *Magma* blocks and *Wither* effect.
    - Behaves like **Armadillo Wolf Armor** (fully absorbs damage from most sources by losing durability).
    - When fully broken, any damage taken by an animal applies Glowing for 5 minutes to allow the player know and react.
    - Has almost 3 times higher durability than **Armadillo Wolf Armor** (11 vs 4).
    - Applies *Slowness IV* and *Heavy Jump IV* effects to the animal when contacting with water (directly or in rain).
    - Fully broken armor **does not disappear**: remains equipped but loses all protection and immunities.
    - Does not burn in Lava when dropped as an item.
  - **Lymonite Horse Armor**
    - All-purpose armor. But designed for snowy mountains.
    - Has properties similar to players' **Lymonite Armor**.
    - Protects the horse from sinking in *Powder Snow*.
    - Grants 100% freezing protection like **Leather Armor**.
    - Grants immunity to Poison effect and thorns damage (Sweet Berry Bushes, Cacti).
    - The defense is between **Diamond Horse Armor** and **Gold Horse Armor**: body defense 9 and toughness 1.
    - Applies Health Boost +4 hearts.
    - Weak against *Wither* effect and explosions: +50% damage, against *Wind Charge*: +200% damage.
- New items:
    - **Animal Armor Handbook: Horse** for crafting **Lymonite Horse Armor**.
    - **Animal Armor Handbook: Wolf** for crafting **Rendelite Wolf Armor**.
    - **Animal Armor Handbook: Marine – ?**
      - Describes a mysterious marine creature, unknown in some worlds.
      - In version 1.21.11 and later, it can be used to craft **Diamond Nautilus Armor**.
    - All can be found in *Stronghold libraries*.
    - Used in smithing recipes.
    - Are not consumed in crafting.

### 📝 Improvements
- Interactions using **Marine Crystal** and **Soaring Spore Essence** have been improved: now ignore the default animal interactions.
- Tooltip localizations improved.
- Minor corrections for Lymonite and Superyte armor.


## 📦 Version 2.21.6

### 📝 Improvements
- Many items are now fire resistant:
  - **Rendelite**, **Diamite** and **Superyte** gear and all related materials (powders, ingots, plates and blocks).
  - All crystals and **Vesicular Terracotta**.
  - **Fire Rod** and all upgraded variants, including magnetic versions and the **Builder Rod**.
  - **Rod Sensor**.
  - **The Block of Angel** (both *dormant* and *activated* versions) and **The Block of Devil**.
  - **Nether Bars**.


## 📦 Version 2.21.5

### 📝 Improvements
- When a player wearing **Rendelite Armor** comes into contact with water (directly or in rain):
  - *Nausea* and *Heavy Jump* effects now remove the armor’s *Jump Boost* effect (if active) and prevent it from being activated.
  - *Nausea* and *Heavy Jump* effects now remove the armor’s *Slow Falling* effect only while the player is on the ground.
- Minor **Rendelite Armor** tooltip correction.


## 📦 Version 2.21.4

### 📝 Improvements
- Using any type of **magnetic rods** in *magnetic mode* now increases damage.


## 📦 Version 2.21.3

### 🐞 Fixed
- Placing water by the **Aquarite Hoe**:
  - now also causes *exhaustion*, the same way as for **Marine Rod**.
  - the hoe does not place water again if the water source already exists in the position.
- Minor tooltip fixes for **Superyte armor**.

### 📝 Improvements
- **Lymonite armor**:
  - Bite damage resistance from *spiders*, *silverfish*, and *bees* reduced to 50% (was 80%).
  - Wind Charge damage vulnerability increased to +200% (was +50%).
  - Wither and blast damage vulnerability remains unchanged (+50%).
- **Superyte armor**:
  - Bite damage resistance from *spiders*, *silverfish*, and *bees* has been removed.


## 📦 Version 2.21.0

### 📝 Improvements
- **Aeryte armor**: *Slow Falling* and *Super Jump* switcheable effects now require the full armor set.


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
- Monsters stayed undead even after the **Block of Devil** was destroyed.

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
