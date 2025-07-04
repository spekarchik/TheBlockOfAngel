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
