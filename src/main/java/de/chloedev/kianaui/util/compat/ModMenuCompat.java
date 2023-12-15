package de.chloedev.kianaui.util.compat;

import com.terraformersmc.modmenu.gui.ModsScreen;
import de.chloedev.kianaui.ui.widget.ClickableTextWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ModMenuCompat {

    public static ClickableTextWidget getModsButton(Screen screen, int x, int y) {
        return new ClickableTextWidget(x, y, 70, 20, Text.literal("Mods"), button -> MinecraftClient.getInstance().setScreen(new ModsScreen(screen)));
    }
}
