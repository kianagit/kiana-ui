package de.chloedev.kianaui;

import de.chloedev.kianalibfabric.event.EventManager;
import de.chloedev.kianaui.event.EventListener;
import de.chloedev.kianaui.option.Options;
import de.chloedev.kianaui.util.DevUtil;
import net.fabricmc.api.ClientModInitializer;

public class KianaUIClient {

    private static KianaUIClient INSTANCE;
    private Options options;

    public void init(){
        EventManager.register(new EventListener());
        this.options = new Options();
        DevUtil.debug("Initializing Mod...");
    }

    public Options getOptions() {
        return options;
    }

    public static KianaUIClient getInstance() {
        if(INSTANCE == null) INSTANCE = new KianaUIClient();
        return INSTANCE;
    }
}
