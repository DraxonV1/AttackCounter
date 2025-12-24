package asia.draxon.attackcounter;

import asia.draxon.attackcounter.config.AttackCounterConfig;
import asia.draxon.attackcounter.hud.CombatHud;
import asia.draxon.attackcounter.tracker.CombatTracker;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttackCounterClient implements ClientModInitializer {
    public static final String MOD_ID = "attackcounter";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    private static KeyBinding toggleKey;
    public static CombatTracker combatTracker; // Made public for mixin access
    private static CombatHud combatHud;
    private static AttackCounterConfig config;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing AttackCounter...");
        
        // Initialize config
        config = AttackCounterConfig.load();
        
        // Initialize tracker and HUD
        combatTracker = new CombatTracker();
        combatHud = new CombatHud(combatTracker);
        
        // Register key binding for toggling
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.attackcounter.toggle",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_K,
            "category.attackcounter"
        ));
        
        // Register attack entity callback
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (world.isClient && entity instanceof LivingEntity && config.isEnabled()) {
                combatTracker.onPlayerAttack(player, (LivingEntity) entity);
            }
            return ActionResult.PASS;
        });
        
        // Register HUD rendering
        HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
            if (config.isEnabled()) {
                combatHud.render(drawContext, renderTickCounter);
            }
        });
        
        // Register client tick for key handling and updates
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (toggleKey.wasPressed()) {
                config.toggleEnabled();
                config.save();
                
                if (client.player != null) {
                    client.player.sendMessage(
                        net.minecraft.text.Text.literal("AttackCounter " + 
                            (config.isEnabled() ? "§aEnabled" : "§cDisabled")), 
                        true
                    );
                }
            }
            
            if (config.isEnabled()) {
                combatTracker.tick();
            }
        });
        
        LOGGER.info("AttackCounter initialized successfully!");
    }
    
    public static AttackCounterConfig getConfig() {
        return config;
    }
}