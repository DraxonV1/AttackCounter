package asia.draxon.attackcounter.hud;

import asia.draxon.attackcounter.AttackCounterClient;
import asia.draxon.attackcounter.tracker.CombatTracker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;

public class CombatHud {
    private final CombatTracker tracker;
    
    public CombatHud(CombatTracker tracker) {
        this.tracker = tracker;
    }
    
    public void render(DrawContext context, RenderTickCounter tickCounter) {
        if (!tracker.shouldDisplay()) {
            return;
        }
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) {
            return;
        }
        
        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();
        
        // Position above hotbar (same as action bar)
        int baseY = screenHeight - 59 + AttackCounterClient.getConfig().getHudOffsetY();
        
        StringBuilder text = new StringBuilder();
        var config = AttackCounterClient.getConfig();
        
        // Build text based on config
        if (config.isShowCombo() && tracker.getCombo() > 0) {
            text.append("§6Combo: §e").append(tracker.getCombo());
        }
        
        if (config.isShowHitsDealt() && tracker.getHitsDealt() > 0) {
            if (text.length() > 0) text.append(" §7| ");
            text.append("§aHits: §f").append(tracker.getHitsDealt());
        }
        
        if (config.isShowHitsReceived() && tracker.getHitsReceived() > 0) {
            if (text.length() > 0) text.append(" §7| ");
            text.append("§cReceived: §f").append(tracker.getHitsReceived());
        }
        
        if (config.isShowDamageDealt() && tracker.getDamageDealt() > 0) {
            if (text.length() > 0) text.append(" §7| ");
            text.append("§aDmg: §f").append(String.format("%.1f", tracker.getDamageDealt()));
        }
        
        if (config.isShowDamageReceived() && tracker.getDamageReceived() > 0) {
            if (text.length() > 0) text.append(" §7| ");
            text.append("§cDmg: §f").append(String.format("%.1f", tracker.getDamageReceived()));
        }
        
        if (text.length() == 0) {
            return;
        }
        
        Text displayText = Text.literal(text.toString());
        int textWidth = client.textRenderer.getWidth(displayText);
        int x = (screenWidth - textWidth) / 2;
        
        // Add fade effect when timer is low
        int alpha = 255;
        if (tracker.getDisplayTimer() < 20) {
            alpha = (int) (255 * (tracker.getDisplayTimer() / 20.0f));
        }
        
        // Draw shadow background
        context.fill(x - 2, baseY - 2, x + textWidth + 2, baseY + 10, 
            (alpha / 2) << 24);
        
        // Draw text
        context.drawTextWithShadow(
            client.textRenderer,
            displayText,
            x,
            baseY,
            0xFFFFFF | (alpha << 24)
        );
    }
}