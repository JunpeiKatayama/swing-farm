[日本語](README.ja.md)

# SwingFarm

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.x-green.svg)](https://minecraft.net)
[![Fabric](https://img.shields.io/badge/Fabric-Supported-orange.svg)](https://fabricmc.net)
[![License](https://img.shields.io/badge/License-CC0--1.0-blue.svg)](LICENSE)

**SwingFarm** is a client-side mod for Minecraft Java Edition 1.21.x. It automates the waiting time in mob farms by automatically attacking hostile mobs that appear in front of you.

## 🎯 Features

- **Auto-Attack System**: Automatically detects and attacks hostile mobs within 3 blocks in front of the player.
- **Shortcut Key**: Toggle the mod's functionality ON/OFF with `F8`.
- **Line-of-Sight Check**: Prevents attacking mobs through walls.
- **Attack Cooldown**: Implements a natural attack interval (1 second).
- **Safety Feature**: Only operates when a sword is equipped.

## 📋 Conditions for Operation

The mod will operate under the following conditions:

- The player is alive.
- A sword (SwordItem) is equipped in the main hand.
- A hostile mob is within 3 blocks in front of the player.
- The mob is visible to the player.
- The feature is enabled (toggled with `F8`).

## 🛠️ Requirements

- **Minecraft**: Java Edition 1.21.x
- **Mod Loader**: Fabric Loader 0.16.0 or higher
- **Dependencies**: Fabric API
- **Java**: 17 or higher

## 📦 Installation

1. Install [Fabric Loader](https://fabricmc.net/use/installer/).
2. Download [Fabric API](https://modrinth.com/mod/fabric-api).
3. Download the appropriate `.jar` file for your Minecraft version from the [Releases](../../releases).
4. Place the Fabric API and SwingFarm `.jar` files into your `.minecraft/mods` folder.
5. Launch Minecraft.

## 🎮 Usage

1. **Log in to Minecraft.**
2. **Equip a sword in your main hand.**
3. **Press `F8` to turn the feature ON.**
   - A message "Auto-attack: Enabled" will appear in the chat.
4. **Wait in front of a mob farm.**
5. **The mod will automatically attack hostile mobs as they appear.**
6. **Press `F8` to turn the feature OFF.**

### Keybind Configuration

- You can change the keybinding in **Options → Controls → Key Binds** under the "SwingFarm" category.
- The default is `F8`.

## 🎨 Screenshots

### Chat Messages

- 🟢 Enabled: `[SwingFarm] Auto-attack: Enabled`
- 🔴 Disabled: `[SwingFarm] Auto-attack: Disabled`

## 🔧 For Developers

### How to Build

```bash
git clone https://github.com/<username>/SwingFarm.git
cd SwingFarm
./gradlew build
```

### Technical Specifications

- **Event**: Uses `ClientTickEvents.END_CLIENT_TICK`.
- **Attack Range**: Within 3 blocks in front of the player.
- **Targeting**: Based on the `HostileEntity` class.
- **Line-of-Sight Check**: Uses raycasting.
- **Cooldown**: 20 ticks (1 second).

### Project Structure

```
SwingFarm/
├── src/
│   ├── main/resources/
│   │   ├── fabric.mod.json
│   │   └── assets/swingfarm/lang/
│   │       ├── en_us.json
│   │       └── ja_jp.json
│   └── client/java/com/example/swingfarm/
│       └── SwingFarmMod.java
├── build.gradle
├── gradle.properties
└── settings.gradle
```

## ⚠️ Disclaimer

- This mod is provided "as-is," and the author does not guarantee perpetual maintenance or individual support.
- As a **client-side mod**, it does not need to be installed on the server.
- Please check the terms of service before using on **PvP servers**.
- The use of **automation mods** may be prohibited on some servers.

## 🐛 Bug Reports & Feature Requests

If you find a bug or have a feature request, please report it via [Issues](../../issues).

### Information to Include in Reports

- Minecraft version
- Fabric Loader version
- Fabric API version
- SwingFarm version
- Detailed description of the issue

## 📜 License

This project is licensed under the [CC0-1.0 License](LICENSE).

## 🤝 Contributing

Pull requests and suggestions for improvement are welcome!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

**Happy Farming! 🗡️⚔️**
