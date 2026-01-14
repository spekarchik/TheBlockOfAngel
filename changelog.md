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
