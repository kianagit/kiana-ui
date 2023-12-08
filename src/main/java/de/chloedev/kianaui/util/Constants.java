package de.chloedev.kianaui.util;

import net.fabricmc.loader.api.FabricLoader;

public class Constants {

    /**
     * Realistically, it should be impossible to call this when this mod isn't loaded, so it Shouldn't throw errors...
     */
    public static final String MOD_VERSION = FabricLoader.getInstance().getModContainer("kianaui").orElseThrow().getMetadata().getVersion().getFriendlyString();
}
