package de.chloedev.kianaui.ui.widget;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.tab.TabManager;
import net.minecraft.client.gui.widget.TabButtonWidget;
import net.minecraft.client.gui.widget.TabNavigationWidget;

import java.util.List;

public class TabScreenWidget extends TabNavigationWidget {

    public TabScreenWidget(int x, TabManager tabManager, Tab... tabs) {
        super(x, tabManager, List.of(tabs));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        for (TabButtonWidget tabButtonWidget : this.tabButtons) {
            tabButtonWidget.render(context, mouseX, mouseY, delta);
        }
    }
}
