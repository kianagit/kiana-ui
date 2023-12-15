package de.chloedev.kianaui.ui.widget;

import de.chloedev.kianalibfabric.util.ColorUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class ClickableTextWidget extends net.minecraft.client.gui.widget.ButtonWidget {

    public ClickableTextWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, Supplier::get);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int i = this.hovered ? 175 : 255;
        context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, this.getMessage(), this.getX() + (this.getWidth() / 2), this.getY() + (this.getHeight() / 4) + 1, ColorUtil.of(i, i, i));
    }
}