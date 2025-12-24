package asia.draxon.attackcounter.mixin;

import asia.draxon.attackcounter.AttackCounterClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    
    @Inject(method = "attack", at = @At("TAIL"))
    private void onAttack(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        
        if (player.getWorld().isClient && target instanceof LivingEntity) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == player && AttackCounterClient.getConfig().isEnabled()) {
                // Track damage dealt (approximation based on attack damage)
                float damage = (float) player.getAttributeValue(
                    net.minecraft.entity.attribute.EntityAttributes.ATTACK_DAMAGE
                );
                
                // Add weapon damage if holding one
                if (!player.getMainHandStack().isEmpty()) {
                    // The actual damage will be calculated by the game
                    // We're just tracking hits here
                }
            }
        }
    }
    
    @Inject(method = "damage", at = @At("HEAD"))
    private void onDamage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        
        if (player.getWorld().isClient) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == player && AttackCounterClient.getConfig().isEnabled()) {
                // This will be called on the client side
                // We'll track received hits through the combat tracker
            }
        }
    }
}