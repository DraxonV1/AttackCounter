# AttackCounter

A Minecraft Fabric client-side mod that tracks and displays real-time combat statistics including combo hits, damage dealt, and damage received.

![Minecraft Version](https://img.shields.io/badge/Minecraft-1.21.5-brightgreen)
![Mod Loader](https://img.shields.io/badge/Mod%20Loader-Fabric-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## Features

### Combat Tracking
- **Combo Counter**: Tracks consecutive hits on entities with configurable reset time
- **Hit Statistics**: Monitors both hits dealt to enemies and hits received from them
- **Damage Tracking**: Records precise damage values for both offensive and defensive combat
- **Real-time Display**: Shows statistics in an elegant HUD above your hotbar

### Customization
All aspects of the mod can be configured through the `attackcounter.json` config file:

- **Toggle Display**: Enable or disable the mod with a keybind (default: K)
- **Combo Settings**: Adjust the time window for maintaining combos
- **Display Duration**: Control how long statistics remain visible after combat
- **Selective Information**: Choose which stats to display (combo, hits, damage)
- **Position Adjustment**: Fine-tune HUD vertical positioning

### User Experience
- **Fade Effects**: Smooth fade-out animation when display timer expires
- **Color-Coded**: Easy-to-read color scheme (gold for combos, green for offense, red for defense)
- **Non-Intrusive**: Positioned above hotbar, similar to action bar text
- **Performance Friendly**: Lightweight client-side implementation

## Installation

### Requirements
- **Minecraft**: Version 1.21.5 or compatible
- **Fabric Loader**: Version 0.16.10 or newer
- **Fabric API**: Version 0.128.2 or newer
- **Java**: Version 21 or newer

### Steps
1. Download and install [Fabric Loader](https://fabricmc.net/use/)
2. Download [Fabric API](https://modrinth.com/mod/fabric-api) for your Minecraft version
3. Download the latest AttackCounter release
4. Place both the Fabric API and AttackCounter `.jar` files in your `.minecraft/mods` folder
5. Launch Minecraft with the Fabric profile

## Usage

### Basic Controls
- **Toggle Mod**: Press `K` (default, rebindable in controls settings)
- **View Stats**: Engage in combat to see your statistics displayed

### Configuration
The config file is located at `.minecraft/config/attackcounter.json`

```json
{
  "enabled": true,
  "comboResetTime": 60,
  "displayDuration": 100,
  "showCombo": true,
  "showHitsDealt": true,
  "showHitsReceived": true,
  "showDamageDealt": true,
  "showDamageReceived": true,
  "hudOffsetY": 0
}
```

#### Configuration Options
| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `enabled` | boolean | true | Master toggle for the mod |
| `comboResetTime` | integer | 60 | Ticks before combo resets (60 = 3 seconds) |
| `displayDuration` | integer | 100 | Ticks to show stats after combat (100 = 5 seconds) |
| `showCombo` | boolean | true | Display combo counter |
| `showHitsDealt` | boolean | true | Display hits dealt to enemies |
| `showHitsReceived` | boolean | true | Display hits received from enemies |
| `showDamageDealt` | boolean | true | Display damage dealt |
| `showDamageReceived` | boolean | true | Display damage received |
| `hudOffsetY` | integer | 0 | Vertical offset in pixels (positive = down, negative = up) |

Changes to the config file take effect after restarting Minecraft or reloading resources.

## Building from Source

### Prerequisites
- JDK 21 or newer
- Git

### Build Instructions
```bash
# Clone the repository
git clone https://github.com/DraxonV1/AttackCounter.git
cd AttackCounter

# Build the mod (Linux/Mac)
./gradlew build

# Build the mod (Windows)
gradlew.bat build
```

The built `.jar` file will be located in `build/libs/`

## Compatibility

### Known Compatible Mods
- Most client-side HUD mods
- Combat enhancement mods
- Performance optimization mods (Sodium, Lithium, etc.)

### Potential Conflicts
- Mods that heavily modify combat mechanics may affect accuracy
- Other combat stat trackers may display conflicting information
- Custom HUD mods with similar positioning might overlap

## Troubleshooting

### Stats Not Displaying
1. Ensure the mod is enabled (press K to toggle)
2. Verify Fabric API is installed
3. Check that you're in survival/adventure mode
4. Confirm you're dealing/receiving damage

### Config Not Saving
1. Check file permissions on the `.minecraft/config` folder
2. Ensure the config file is valid JSON
3. Look for errors in the game log

### Performance Issues
- Try reducing `displayDuration` to decrease render time
- Disable unused statistics in the config
- Check for conflicts with other HUD-modifying mods

## Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues on GitHub.

### Development Setup
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

### Code Style
- Follow existing code conventions
- Use meaningful variable names
- Comment complex logic
- Update documentation as needed

## Support

- **Issues**: [GitHub Issues](https://github.com/DraxonV1/AttackCounter/issues)
- **Homepage**: [attackcounter.mc.draxon.asia](https://attackcounter.mc.draxon.asia)
- **Source Code**: [GitHub Repository](https://github.com/DraxonV1/AttackCounter)

## License

This project is licensed under the MIT License. See the LICENSE file for details.

## Credits

**Author**: DraxonV1

**Frameworks & Libraries**:
- [Fabric](https://fabricmc.net/) - Mod loader and API
- [Mixin](https://github.com/SpongePowered/Mixin) - Java bytecode manipulation

## Changelog

For detailed version history and changes, please refer to the releases page on GitHub.

---

**Enjoy tracking your combat prowess!** ⚔️