package script.fixarus.mixin;

import dev.cammiescorner.icarus.common.powers.WingsPower;
import dev.cammiescorner.icarus.core.util.DefaultWingsValues;
import dev.cammiescorner.icarus.core.util.WingsValues;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;
@Mixin(targets = "dev/cammiescorner/icarus/core/integration/IcarusOrigins", remap = false)
public abstract class RepairMixin {
    @Inject(method = "getWingValues", at = @At("HEAD"), cancellable = true)
    private static void correct_mistake(CallbackInfoReturnable<Function<Entity, WingsValues>> cir){
        cir.setReturnValue(entity -> {
            if(!PowerHolderComponent.KEY.get(entity).getPowers(WingsPower.class).isEmpty()){
                return PowerHolderComponent.KEY.get(entity).getPowers(WingsPower.class).get(0);
            }
            return DefaultWingsValues.INSTANCE;
        });
    }

    @Inject(method = "hasWings", at = @At("HEAD"), cancellable = true)
    private static void man(Entity entity, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(!PowerHolderComponent.KEY.get(entity).getPowers(WingsPower.class).isEmpty());
    }
}
