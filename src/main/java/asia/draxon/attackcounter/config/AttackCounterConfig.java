package asia.draxon.attackcounter.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AttackCounterConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
        .getConfigDir()
        .resolve("attackcounter.json");
    
    private boolean enabled = true;
    private int comboResetTime = 60; // ticks (3 seconds)
    private int displayDuration = 100; // ticks (5 seconds)
    private boolean showCombo = true;
    private boolean showHitsDealt = true;
    private boolean showHitsReceived = true;
    private boolean showDamageDealt = true;
    private boolean showDamageReceived = true;
    private int hudOffsetY = 0; // Offset from default position
    
    public static AttackCounterConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                return GSON.fromJson(json, AttackCounterConfig.class);
            } catch (IOException e) {
                System.err.println("Failed to load AttackCounter config: " + e.getMessage());
            }
        }
        
        AttackCounterConfig config = new AttackCounterConfig();
        config.save();
        return config;
    }
    
    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(this));
        } catch (IOException e) {
            System.err.println("Failed to save AttackCounter config: " + e.getMessage());
        }
    }
    
    // Getters and setters
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public void toggleEnabled() {
        this.enabled = !this.enabled;
    }
    
    public int getComboResetTime() {
        return comboResetTime;
    }
    
    public void setComboResetTime(int comboResetTime) {
        this.comboResetTime = comboResetTime;
    }
    
    public int getDisplayDuration() {
        return displayDuration;
    }
    
    public void setDisplayDuration(int displayDuration) {
        this.displayDuration = displayDuration;
    }
    
    public boolean isShowCombo() {
        return showCombo;
    }
    
    public void setShowCombo(boolean showCombo) {
        this.showCombo = showCombo;
    }
    
    public boolean isShowHitsDealt() {
        return showHitsDealt;
    }
    
    public void setShowHitsDealt(boolean showHitsDealt) {
        this.showHitsDealt = showHitsDealt;
    }
    
    public boolean isShowHitsReceived() {
        return showHitsReceived;
    }
    
    public void setShowHitsReceived(boolean showHitsReceived) {
        this.showHitsReceived = showHitsReceived;
    }
    
    public boolean isShowDamageDealt() {
        return showDamageDealt;
    }
    
    public void setShowDamageDealt(boolean showDamageDealt) {
        this.showDamageDealt = showDamageDealt;
    }
    
    public boolean isShowDamageReceived() {
        return showDamageReceived;
    }
    
    public void setShowDamageReceived(boolean showDamageReceived) {
        this.showDamageReceived = showDamageReceived;
    }
    
    public int getHudOffsetY() {
        return hudOffsetY;
    }
    
    public void setHudOffsetY(int hudOffsetY) {
        this.hudOffsetY = hudOffsetY;
    }
}