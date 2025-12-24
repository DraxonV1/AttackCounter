package asia.draxon.attackcounter.mixin;

import asia.draxon.attackcounter.AttackCounterClient;
import asia.draxon.attackcounter.tracker.CombatTracker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(
        method = "damage",
        at = @At("RETURN")
    )
    private void onDamageReturn(
            ServerWorld world,
            DamageSource source,
            float amount,
            CallbackInfoReturnable<Boolean> cir
    ) {
        // Only care if damage actually happened
        if (!cir.getReturnValue()) return;

        LivingEntity entity = (LivingEntity) (Object) this;

        // Client-side logic only
        if (!entity.getWorld().isClient()) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        if (!AttackCounterClient.getConfig().isEnabled()) return;

        CombatTracker tracker = AttackCounterClient.combatTracker;
        if (tracker == null) return;

        // Player dealing damage
        if (source.getAttacker() instanceof PlayerEntity attacker &&
            attacker == client.player) {

            tracker.addDamageDealt(amount);
        }

        // Player receiving damage
        if (entity == client.player) {
            tracker.onPlayerReceiveHit(client.player, amount);
        }
    }
}
