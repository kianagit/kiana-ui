package de.chloedev.kianaui.util.compat;

import com.terraformersmc.modmenu.gui.ModsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ModMenuCompat {

    public static ButtonWidget getModsButton(Screen screen, int x, int y) {
        return ButtonWidget.builder(Text.literal("Mods"), button -> MinecraftClient.getInstance().setScreen(new ModsScreen(screen))).dimensions(x, y, 70, 20).build();
    }
}
