package de.chloedev.kianaui.event;

import de.chloedev.kianalibfabric.event.EventHandler;
import de.chloedev.kianalibfabric.event.impl.client.ClientScreenChangeEvent;
import de.chloedev.kianaui.ui.screen.KianaTitleScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;

public class EventListener {

    @EventHandler
    public void onScreenChange(ClientScreenChangeEvent e) {
        if ((e.getNewScreen() == null && MinecraftClient.getInstance().world == null) || e.getNewScreen() instanceof TitleScreen) {
            e.setNewScreen(new KianaTitleScreen());
        }
    }
}
