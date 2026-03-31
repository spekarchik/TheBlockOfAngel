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
- **Lymonite Horse Armor** now stacks to 1.


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
- Fixed an issue where the **Lodestone** could drop unexpectedly during **Builder** downgrading.
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


## 📦 Version 2.12.0

### 📝 Improvements

- **Allays** spawned by **Evoker's Amulet** now despawn (if they’re not holding an item and haven’t been renamed):
  - after 5 minutes of being farther than 16 blocks from the player;
  - immediately if they fly farther than 60 blocks;
  - immediately when the player changes dimension, teleports, or exits the world.

- **Allays** spawned by **Evoker's Amulet** that *are holding an item*:
  - cannot be despawned, but are tracked to be removed later (once they are no longer holding an item);
  - remain tracked even after the player who summoned them leaves and rejoins the game (tracked allays are saved to disk in the `tracked_allays.dat` file);
  - can be excluded from the tracking system and remain in the world forever under certain conditions:
    - if the allay was unreachable (in an unloaded chunk of the same dimension) when the player’s world was loaded.
    - if the allay wasn't loaded within 1 second after the player joined the world.
    - or potentially, in other rare cases.

- Tooltip updated: **Evoker Amulet** has been renamed to **Evoker's Amulet**.

- Improved Belarusian localization: **Allays** are now called **Спакаёўка**, as originally intended.


## 📦 Version 2.11.2

### 🐞 Fixes
- Fixed a bug where the **Saltpeter Block** didn't drop anything when broken.


## 📦 Version 2.11.1

### 🐞 Fixes
- Fixed a bug where right-clicking the Smithing Table with armor in hand could accidentally equip the armor while also opening the custom menu.


## 📦 Version 2.11.0

### ⚒️ Structural Changes
- The former **Block of Angel** has been split into two separate blocks:
  - **The Dormant Block of Angel** – crafted item, inert by default.
  - **The Block of Angel** – activated version, only obtainable by using an **Echo Shard**.
- Functionality remains unchanged: once activated, the block despawns any monsters within a 70-block radius. Echo Shard is **still not consumed** on activation.

### ⚙️ Rod of Angel Crafting Adjusted
- The **Smithing Table** recipe for **The Rod of Angel** now explicitly requires the **activated** version of the block.
- This change **closes a progression loophole**:
  - Previously, it was possible to craft the Rod using an unactivated block and bypass the need to explore an Ancient City.
  - Now, players must obtain an **Echo Shard** before crafting the Rod, ensuring **intended progression** through all major danger zones.

### 🎯 Intent
This refactor reinforces the original vision:
- The **Echo Shard** is a required milestone;
- The **Rod of Angel** is a reward for completing key encounters, not a shortcut through them;
- **Crafting is no longer a bypass — it's a culmination.**


## 📦 Version 2.10.1

### 📝 Improvements
- When a player with full health puts on leggings that increase maximum health, their health bar will now remain full (with the bonus). Previously, it stayed at the original value.
- If the player had missing health, the health value remains unchanged as before.


## 📦 Version 2.10.0

### 🛠️ Changed
- **Soaring Spore Essence** no longer removes effects — it only applies Glowing and Slow Falling.
- **Marine Crystal** now cleanses all effects from mobs when used in mainhand.
- Both **Soaring Spore Essence** and **Marine Crystal** do not affect players.
- **Soaring Spore Essence** and **Marine Crystal** tooltips have been updated to reflect the changes.


## 📦 Version 2.9.1

### 🐞 Fixes
- **Magnetic rods** now cause exhaustion *only* when a block is affected in Magnetic Mode (no more stamina loss from blocked ores).


## 📦 Version 2.9.0 - The Lucky Update 😄

### 📝 Improvements
- The **Luck** effect can now prevent death (25% chance per level), teleporting the player to their saved respawn point with regeneration and temporary damage protection.
- Armor tooltips are now split into three pages:
  - **Shift** shows general information
  - **Alt** shows stats and upgrade materials
  - **Ctrl** shows additional lore
- Each armor piece now displays the item required for its upgrade directly in its tooltip.
- Updated chestplate names to better reflect their special effects and bonuses, e.g., using "with Slow Falling" for clarity.


## 📦 Version 2.8.3

### 🛠️ Changed
- **Suspicious Sand (Warm Ocean)**: reduced the drop weight of the wooden hoe from 2 to 1 to maintain vanilla drop balance after adding custom items.


## 📦 Version 2.8.2

### 🐞 Fixes
- **Planter**, **Builder**, and **Track Layer** no longer cause player exhaustion when offhand items are not actually consumed.


## 📦 Version 2.8.1

### 📝 Improvements
- Harvested crops now drop toward the player when using a **Planter**. Drops are slightly randomized for more natural movement.
- **Planter**: planting and bonemealing behavior improved for smoother farming.
  - Can now bonemeal crops by clicking either the crop or the farmland.
  - Bonemealing no longer stops at empty farmland — skips it and continues.
  - Planting now works when clicking on already planted crops.
  - Grass and flowers are automatically cleared (without drops) during planting.


## 📦 Version 2.8.0

### 🛠️ Changed
- **Rendelite** and **Superyte** armor are now fire-resistant — they no longer take damage from fire.

### 🐞 Fixes
- **Aerite armor** can no longer be enchanted via the *Enchanting Table*.
- **Rendelite Helmet with Night Vision** can now be enchanted via the *Enchanting Table*.

### 📝 Improvements
- **Marine Rod** and **Aquarite Hoe** can now place water between **Suspicious Gravel** blocks and can not longer place water between **Red Sand** and **Dirt Path** blocks.
- **Planter** can be enchanted with *Fortune* to harvest more crops.
- **Planter**, **Builder**, and **Track Layer** no longer consume items from the player's off-hand in *Creative mode*. These tools use the off-hand stack (e.g., seeds, bone meal, blocks, rails) to apply effects across multiple blocks, but in *Creative mode*, the stack will now remain unchanged.
- **Planter** now only drops crops matching the clicked plant type within its area of effect.


## 📦 Version 2.7.0

### 🛠️ Changed
- **Rednelite, Diamite, Aquarite, Lymonite Armor Upgrade kits**: now can be given by a leather worker as a gift when player has the *'Hero of the Village'* status.
- **Superyte Armor Upgrade kits**: now can be given by an armorer as a gift when player has the *'Hero of the Village'* status.
- **Rednelite, Diamite, Aquarite, Lymonite, Superyte Tool Upgrade kits**: now can be given by a weaponsmith or a toolsmith as a gift when player has the *'Hero of the Village'* status.
- **Armor and Tool Upgrade Kit** tooltips have been updated to reflect the changes.


## 📦 Version 2.6.0

### 🛠️ Changed
- Tannery chest loot table updated: now **any** armor upgrade kit can be generated  
  *(Since no armorer workshops in vanilla villages contain a chest, armor upgrade kits — including those for non-leather armor — can now be found in tannery chests instead.)*

### 📝 Improvements
- Added details regarding the SuperJump effect to Superite Armor tooltip  
  *(Clarifies that the first jump is normal, and Super Jump activates only on a second jump within 1 second.)*


## 📦 Version 2.5.1

### 🐞 Fixes
- Fixed the behavior of rods underwater: they can no longer plant underwater, and now correctly transform **Diamond Ore Block** into **Green Diamond Ore Block** when used on underwater blocks.
- Fixed the behavior of swords: they can no longer apply their effects (planting cacti, placing cobwebs, or setting fire) on blocks submerged in water or lava.


## 📦 Version 2.5.0

### ✨ Added
- **Breeze** can now be added to the **Block of Angel** filter using a **Breeze Rod** (to prevent this type of monster from despawning).
- **Breeze** can be spawned by using a **Breeze Rod** on the **Block of Devil**.
- **Creaking** can now be added to the **Block of Angel** filter using a **Resin Clump** (to prevent this type of monster from despawning).
- **Creaking** can be spawned by using a **Resin Clump** on the **Block of Devil**.

### 🛠️ Changed
- Tooltips for the **Block of Angel** and **Block of Devil** have been updated with **Breeze** and **Creaking**, and their monster lists have been completely rearranged.
- Increased chances of **Smithing Upgrade Kits** generating in the following chests: **Buried Treasure**, **Nether Bridge**, **Underwater Ruin**, **Woodland Mansion**.
- **Green Diamond Ore**: light level increased from 3 to 6.


## 📦 Version 2.4.0

### 🛠️ Changed
- **Aquarite Helmet with Night Vision**: now uses **Elder Guardian Eye** for the Night Vision modification instead of **Sculk Sensor**.
- ⚠️ **Compatibility note**: Previously crafted **Aquarite Helmets with Night Vision** used **Sculk Sensor**. After this update, disassembling such helmets with a **Downgrade Smithing Kit** will return an **Elder Guardian Eye** instead of the original **Sculk Sensor**.

### ✨ Added
- **Rendelite Helmet with Night Vision** has been added. This is a modified version of the **Rendelite Helmet**, crafted using an **Elder Guardian Eye**.


## 📦 Version 2.3.3

### 🐞 Fixes
- Fixed a bug where **Superite Chestplate** and **Aerite Chestplate** would take durability damage during flight. Now, these chestplates are no longer damaged while the player is flying.


## 📦 Version 2.3.2

### 📝 Improvements
- **Bios Diamond**, **End Sapphire**, **Evoker Amulet**, **Elder Guardian Eye**, **Golden Artefact**: updated usage sound from `PLAYER_LEVELUP` to `LEVER_CLICK` for a more tactile and fitting feedback.


## 📦 Version 2.3.1

### 📝 Improvements
- **Rod of Angel**: improved block drop behavior when broken
- **Rod of Angel**: now supports directional placement based on the player's facing, allowing orientation along X or Z axis


## 📦 Version 2.3.0

### ✨ New
- Added full support for Belarusian localization

### 📝 Improvements
- Revised and improved text in both English and Russian localizations
- Renamed `Ancient Canine` → `Ancient Tusk`  

### 🐞 Fixes
- Fixed several tooltip formatting and display bugs


## 📦 Version 2.2.0

### 🛠️ Changed
- Igloo chests: Increased the chance of finding an Ancient Canine to 90%, and raised the maximum stack size to 5.

## 📦 Version 2.1.0

### 🛠️ Changed
- **Ancient Canine** can now only be found in igloos (removed from Ancient Cities). You'll still need to visit an Ancient City to find an Echo Shard required to activate the Block of Angel.
- The rarity of certain items and blocks has been adjusted.
- Several recipes have been grouped together.

## 📦 Version 2.0.1

### ✨ Added
- Advancement-based recipe unlocking

### 🔥 Removed
- Several smelting recipes that no longer fit progression

### 🛠️ Changed
- **Pickaxe and shovel behavior:**
  - No longer convert blocks into cracked or mossy variants when using RMC
  - Now prevent drops of *suspicious* and *infested* blocks

### 📝 Fixed
- Tooltip corrections for improved clarity


## 📦 Version 1.1.6

### Renamed
- 'Angel Block Mod' renamed to 'The Block of Angel' to avoid confusion with another similarly named mod.
- 'Angel Block' renamed to 'The Block of Angel'
- 'Devil Block' renamed to 'The Block of Devil'

## 📦 Version 1.1.5

- Initial publishing

