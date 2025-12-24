package asia.draxon.attackcounter.tracker;

import asia.draxon.attackcounter.AttackCounterClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class CombatTracker {
    private int combo = 0;
    private int hitsDealt = 0;
    private int hitsReceived = 0;
    private float damageDealt = 0;
    private float damageReceived = 0;
    
    private int lastHitTick = 0;
    private int displayTimer = 0;
    private int currentTick = 0;
    
    private String lastTarget = "";
    
    public void onPlayerAttack(PlayerEntity player, LivingEntity target) {
        int comboResetTime = AttackCounterClient.getConfig().getComboResetTime();
        
        // Check if combo should continue
        if (currentTick - lastHitTick <= comboResetTime) {
            combo++;
        } else {
            combo = 1;
            hitsDealt = 0;
            damageDealt = 0;
        }
        
        hitsDealt++;
        lastHitTick = currentTick;
        displayTimer = AttackCounterClient.getConfig().getDisplayDuration();
        
        // Store target name for display
        lastTarget = target.getName().getString();
    }
    
    public void onPlayerReceiveHit(PlayerEntity player, float damage) {
        hitsReceived++;
        damageReceived += damage;
        displayTimer = AttackCounterClient.getConfig().getDisplayDuration();
    }
    
    public void tick() {
        currentTick++;
        
        // Check if combo should reset
        int comboResetTime = AttackCounterClient.getConfig().getComboResetTime();
        if (currentTick - lastHitTick > comboResetTime && combo > 0) {
            reset();
        }
        
        // Decrease display timer
        if (displayTimer > 0) {
            displayTimer--;
        }
    }
    
    public void reset() {
        combo = 0;
        hitsDealt = 0;
        hitsReceived = 0;
        damageDealt = 0;
        damageReceived = 0;
        displayTimer = 0;
        lastTarget = "";
    }
    
    // Getters
    public int getCombo() {
        return combo;
    }
    
    public int getHitsDealt() {
        return hitsDealt;
    }
    
    public int getHitsReceived() {
        return hitsReceived;
    }
    
    public float getDamageDealt() {
        return damageDealt;
    }
    
    public float getDamageReceived() {
        return damageReceived;
    }
    
    public boolean shouldDisplay() {
        return displayTimer > 0;
    }
    
    public int getDisplayTimer() {
        return displayTimer;
    }
    
    public String getLastTarget() {
        return lastTarget;
    }
    
    public void addDamageDealt(float damage) {
        this.damageDealt += damage;
        displayTimer = AttackCounterClient.getConfig().getDisplayDuration();
    }
}