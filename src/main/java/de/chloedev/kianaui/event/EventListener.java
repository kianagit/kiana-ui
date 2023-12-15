package de.chloedev.kianaui.event;

import de.chloedev.kianalibfabric.event.EventHandler;
import de.chloedev.kianalibfabric.event.impl.client.ClientScreenChangeEvent;
import de.chloedev.kianaui.ui.screen.KianaOptionsScreen;
import de.chloedev.kianaui.ui.screen.KianaTitleScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class EventListener {

    @EventHandler
    public void onScreenChange(ClientScreenChangeEvent e) {
        // Allow opening the vanilla-screens by holding shift while opening.
        if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_SHIFT)) {
            return;
        }
        // Replace vanilla-screens with the modified ones
        if ((e.getNewScreen() == null && MinecraftClient.getInstance().world == null) || e.getNewScreen() instanceof TitleScreen) {
            e.setNewScreen(new KianaTitleScreen());
        } else if (e.getNewScreen() instanceof OptionsScreen) {
            e.setNewScreen(new KianaOptionsScreen());
        }
    }
}
