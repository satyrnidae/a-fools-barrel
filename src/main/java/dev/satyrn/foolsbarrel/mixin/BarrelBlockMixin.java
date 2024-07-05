package foolsbarrel.mixin;

import net.minecraft.block.BarrelBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Equippable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BarrelBlock.class)
public abstract class BarrelBlockMixin implements Equippable {
    @Unique
    @Override
    public EquipmentSlot getPreferredSlot() {
        return EquipmentSlot.HEAD;
    }
}
