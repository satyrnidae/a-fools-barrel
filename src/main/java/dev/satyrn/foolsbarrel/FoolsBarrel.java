package foolsbarrel;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FoolsBarrel implements ModInitializer {
    public static final Identifier BARREL_EYE_HOLES = Identifier.of("foolsbarrel", "textures/misc/barrel_eye_holes.png");

    public static final Logger log = LogManager.getLogger();

    @Override
    public void onInitialize() {
        log.info("Loading A Fool's Barrel...");
    }
}
