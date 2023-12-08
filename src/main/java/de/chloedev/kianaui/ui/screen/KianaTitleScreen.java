package de.chloedev.kianaui.ui.screen;

import de.chloedev.kianaui.util.Constants;
import de.chloedev.kianaui.util.compat.ModMenuCompat;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.CreditsAndAttributionScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class KianaTitleScreen extends Screen {

    public KianaTitleScreen() {
        super(Text.literal("Title Screen"));
    }

    @Override
    protected void init() {
        boolean modMenuInstalled = FabricLoader.getInstance().isModLoaded("modmenu");
        int i = (this.width / 2) - (modMenuInstalled ? (183) : (146));
        int j = this.height - 45;
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Worlds"), button -> this.client.setScreen(new SelectWorldScreen(this))).dimensions(i, j, 70, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Servers"), button -> this.client.setScreen(new MultiplayerScreen(this))).dimensions(i += 74, j, 70, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Options"), button -> this.client.setScreen(new OptionsScreen(this, this.client.options))).dimensions(i += 74, j, 70, 20).build());
        if (modMenuInstalled) this.addDrawableChild(ModMenuCompat.getModsButton(this, i += 74, j));
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Quit"), button -> this.client.scheduleStop()).dimensions(i += 74, j, 70, 20).build());
        Text text = Text.translatable("title.credits");
        this.addDrawableChild(new PressableTextWidget(this.width - (this.textRenderer.getWidth(text)) - 2, this.height - 10, this.textRenderer.getWidth(text), 10, text, button -> this.client.setScreen(new CreditsAndAttributionScreen(this)), this.textRenderer));
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTexture(new Identifier("kianaui", "textures/ui/screen/background/kianatitlescreen.png"), 0, 0, this.width, this.height, 0, 0, 1920, 1080, 1920, 1080);
        context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        context.drawTexture(new Identifier("textures/gui/title/minecraft.png"), this.width / 2 - 128, 30, 0.0f, 0.0f, 256, 44, 256, 64);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawTextWithShadow(this.textRenderer, Text.literal("KianaUI " + Constants.MOD_VERSION), 1, this.height - 20, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, Text.literal("Minecraft " + SharedConstants.getGameVersion().getName()), 1, this.height - 10, 0xFFFFFF);
    }
}
