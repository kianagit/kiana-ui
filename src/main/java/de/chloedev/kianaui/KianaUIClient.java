package de.chloedev.kianaui;

import de.chloedev.kianalibfabric.event.EventManager;
import de.chloedev.kianaui.event.EventListener;
import net.fabricmc.api.ClientModInitializer;

public class KianaUIClient implements ClientModInitializer {

    private static KianaUIClient INSTANCE;

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        EventManager.register(new EventListener());
    }

    public static KianaUIClient getInstance() {
        return INSTANCE;
    }
}
