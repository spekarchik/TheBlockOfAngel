## 📦 Version 3.3.0

### 📝 Improvements
- Rain regeneration exhaustion rebalanced for **Lymonite and Superyte Armor**.
  Hunger was draining too quickly while Lymonite and Superyte Armor regenerated health in rain.
  Exhaustion gain has been reduced **4×**.


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

### 🐞 Fixed
- Fixed an issue where mod armor disappeared when completely broken.

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

### 📝 Improvements
- Drinking milk no longer resets extra hearts from *Health Boost* armor.
  - Extra hearts now remain filled (if your main health is full).


## 📦 Version 2.13.2

### 🐞 Fixed
- Restored the original behavior: using *shovels* on **Farmland** now correctly transforms it into **Dirt Path**.
  - The sound effect when performing the action is now played globally.
  - The new behavior has been added to the tooltip of affected *shovels*.
  
### 🔊 Sound Improvements
- Many sound effects that were previously local (audible only to the player) are now global and can be heard by nearby players.

### 🛠️ Other Changes
- Default **Jump Boost** key *KEY_J* has been changed to *KEY_C*


## 📦 Version 2.13.1

### 📝 Improvements
- Enhanced the **Track Layer** staff:
  - Now supports placing *wool carpets* and *slabs* from the offhand.
  - Updated tooltip to reflect the expanded list of compatible blocks.
- Enhanced the **Builder** staff:
  - Can now place *falling blocks*, as long as there is a solid block beneath them.
  - Updated tooltip to reflect the expanded list of compatible blocks.


## 📦 Version 2.13.0

### 📝 Improvements
- Refined **Limonite Sword** behavior for better predictability:
  - Now **cacti spawn only when the sword is used on sand while sneaking**  
  - In all other cases, the sword places **cobwebs**, avoiding unexpected cactus spawning
- Updated tooltip text to clearly explain the new conditional behavior.
- Improved sword effects behavior:
  - *Cacti* do **not** generate if there are adjacent blocks, preventing block overlap.
  - *Cactus* self-destruction timer now runs slower, ensuring no item drops are left behind as intended.
  - *Cobweb* and *fire* effects are now generated considering height differences within ±1 block, avoiding generation in mid-air or invalid positions.
- Changed sword effect activation rules regarding the offhand:
  - Effects now activate only if the second hand is empty or holds a Totem of Undying
  - Prevents accidental activation when holding other items (e.g. blocks or tools) in the offhand
  - This avoids unintended effects, like accidentally setting fire while trying to place torches
- Added a cooldown mechanic to the **Bios Diamond** and **End Sapphire** items to prevent immediate reuse of their effects.
  - **Bios Diamond** applies an *Absorption* effect lasting 2-10 seconds with a randomized amplifier, followed by a *cooldown effect* that lasts *5 seconds* to enforce a delay between uses.
  - **End Sapphire** applies a *Levitation* effect with a reduced duration range of *2-10 seconds* and a reduced amplifier range of *0-20*, followed by a *cooldown effect* lasting *5 seconds* to avoid spamming.
  - Cooldown effects are separate for different items to allow independent cooldown timers.
  - Added *cooldown* effect icons to reflect the cooldown system more clearly.


## 📦 Version 2.12.1

### 🐞 Fixed
- Blocks no longer drop experience when broken by enhanced tools with *Silk Touch* enchantment (e.g. tools that break multiple blocks at once).


## 📦 Version 2.12.0-beta

- Initial publishing
