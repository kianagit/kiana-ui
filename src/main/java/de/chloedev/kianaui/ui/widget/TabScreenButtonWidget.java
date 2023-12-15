package de.chloedev.kianaui.ui.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.tab.TabManager;
import net.minecraft.client.gui.widget.TabButtonWidget;
import net.minecraft.text.Text;

public class TabScreenButtonWidget extends TabButtonWidget {

    public TabScreenButtonWidget(TabManager tabManager, Tab tab, int width, int height) {
        super(tabManager, tab, width, height);
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        Text text = this.getMessage().copy().setStyle(this.getMessage().getStyle().withUnderline(this.isCurrentTab()));
        TabButtonWidget.drawScrollableText(
                context,
                textRenderer,
                text,
                this.getX() + 1,
                this.getY() + (this.isCurrentTab() ? 0 : 3),
                this.getX() + this.getWidth() - 1,
                this.getY() + this.getHeight(),
                this.active ? -1 : -6250336
        );
    }
}
