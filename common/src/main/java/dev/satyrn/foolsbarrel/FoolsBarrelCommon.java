package dev.satyrn.foolsbarrel;

import dev.satyrn.foolsbarrel.sounds.FoolsBarrelSoundEvents;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class FoolsBarrelCommon {
    public static final String MOD_ID = "foolsbarrel";
	private static Logger LOGGER = LogManager.getLogger();

	private FoolsBarrelCommon() {
	}

    public static void init() {
        // Write common init code here.
		log(Level.INFO, "INIT: Registering sound events for A Fool's Barrel");
		FoolsBarrelSoundEvents.register();
    }

	public static void postInit() {
		log(Level.INFO, "POST_INIT: Registering dispenser behavior for A Fool's Barrel");
		DispenserBlock.registerBehavior(Items.BARREL, new OptionalDispenseItemBehavior() {
			@Override
			protected ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
				// TODO: Need mixin for what slot to set Barrel to
				this.setSuccess(ArmorItem.dispenseArmor(blockSource, itemStack));
				return itemStack;
			}
		});
	}

	public static void log(Level level, String msg, Object... fmt) {
		LOGGER.log(level, msg, fmt);
	}
}
