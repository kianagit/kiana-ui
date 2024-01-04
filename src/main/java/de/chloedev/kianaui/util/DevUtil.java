package de.chloedev.kianaui.util;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DevUtil {

    private static final Logger DEV_LOGGER = LogManager.getLogger("ChloeUI");

    /**
     * Returns true if currently in a Development Environment.
     */
    public static boolean isDevEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    public static void debug(Object msg) {
        if (isDevEnvironment()) {
            DEV_LOGGER.info(msg.toString());
        }
    }
}
