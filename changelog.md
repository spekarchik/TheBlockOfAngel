## 📦 Version 2.11.0

### ⚒️ Structural Changes
- The former **Block of Angel** has been split into two separate blocks:
  - **The Dormant Block of Angel** – crafted item, inert by default.
  - **The Block of Angel** – activated version, only obtainable by using an **Echo Shard**.
- Functionality remains unchanged: once activated, the block despawns any monsters within a 70-block radius. Echo Shard is **still not consumed** on activation.

### 🪄 Rod of Angel Crafting Adjusted
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

### 🛠️ Changed
- Tooltips for the **Block of Angel** and **Block of Devil** have been updated with **Breeze**, and their monster lists have been completely rearranged.
- Increased chances of **Smithing Upgrade Kits** generating in the following chests: **Buried Treasure**, **Nether Bridge**, **Underwater Ruin**, **Woodland Mansion**.
- **Green Diamond Ore**: light level increased from 3 to 6.


## 📦 Version 2.4.0

### 🛠️ Changed
- **Aquarite Helmet with Night Vision**: now uses **Elder Guardian Eye** for the Night Vision modification instead of **Sculk Sensor**.
- ⚠️ **Compatibility note**: Previously crafted **Aquarite Helmets with Night Vision** used **Sculk Sensor**. After this update, disassembling such helmets with a **Downgrade Smithing Kit** will return an **Elder Guardian Eye** instead of the original **Sculk Sensor**.

### ✨ Added
- **Rendelite Helmet with Night Vision** has been added. This is a modified version of the **Rendelite Helmet**, crafted using an **Elder Guardian Eye**.


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


## 📦 Version 2.1.0
- Initial publishing

