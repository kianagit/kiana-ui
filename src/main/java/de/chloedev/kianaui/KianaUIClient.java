package de.chloedev.kianaui;

import de.chloedev.kianalibfabric.event.EventManager;
import de.chloedev.kianalibfabric.io.FileConfiguration;
import de.chloedev.kianaui.event.EventListener;
import de.chloedev.kianaui.option.Options;
import de.chloedev.kianaui.util.DevUtil;

import java.io.File;

public class KianaUIClient {

    private static KianaUIClient INSTANCE;
    private FileConfiguration fileConfig;
    private Options options;

    public void init() {
        DevUtil.debug("Initializing Mod...");
        EventManager.register(new EventListener());
        this.fileConfig = new FileConfiguration(new File("./config/kianaui/config.yml"));
        this.options = new Options();
    }

    public FileConfiguration getFileConfig() {
        return fileConfig;
    }

    public Options getOptions() {
        return options;
    }

    public static KianaUIClient getInstance() {
        if (INSTANCE == null) INSTANCE = new KianaUIClient();
        return INSTANCE;
    }
}
