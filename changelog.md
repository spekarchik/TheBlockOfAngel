## 📦 Version 2.12.1-beta

### 🐞 Fixed
- Blocks no longer drop experience when broken by enhanced tools with *Silk Touch* enchantment (e.g. tools that break multiple blocks at once).


## 📦 Version 2.12.0-beta

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


## 📦 Version 2.11.2-beta

### 🐞 Fixes
- Fixed a bug where the **Saltpeter Block** didn't drop anything when broken.


## 📦 Version 2.11.1-beta

### 🐞 Fixes
- Fixed a bug where right-clicking the Smithing Table with armor in hand could accidentally equip the armor while also opening the custom menu.


## 📦 Version 2.11.0-beta

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


## 📦 Version 2.10.1-beta

### 📝 Improvements
- When a player with full health puts on leggings that increase maximum health, their health bar will now remain full (with the bonus). Previously, it stayed at the original value.
- If the player had missing health, the health value remains unchanged as before.


## 📦 Version 2.10.0-beta

### 🛠️ Changed
- **Soaring Spore Essence** no longer removes effects — it only applies Glowing and Slow Falling.
- **Marine Crystal** now cleanses all effects from mobs when used in mainhand.
- Both **Soaring Spore Essence** and **Marine Crystal** do not affect players.
- **Soaring Spore Essence** and **Marine Crystal** tooltips have been updated to reflect the changes.

### 🐞 Fixes
- Fixed tooltip formatting: some phrases that should be italicized were rendered in plain text.


## 📦 Version 2.9.2-beta

### 🛠️ Changed
- Adapted to NeoForge 21.6.10-beta.


## 📦 Version 2.9.1-beta

- Initial publishing
