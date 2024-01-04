package de.chloedev.kianaui.ui.screen;

import de.chloedev.kianalibfabric.ui.widget.option.OptionListWidget;
import de.chloedev.kianaui.KianaUIClient;
import de.chloedev.kianaui.option.Options;
import de.chloedev.kianaui.ui.widget.Tab;
import de.chloedev.kianaui.ui.widget.TabScreenWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tab.TabManager;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.client.gui.widget.TabNavigationWidget;
import net.minecraft.text.Text;

public class KianaOptionsScreen extends Screen {

    private final Options options;
    private final TabManager tabManager = new TabManager(this::addDrawableChild, this::remove);
    private GridWidget grid;
    private TabNavigationWidget navigation;

    public KianaOptionsScreen() {
        super(Text.empty());
        this.options = KianaUIClient.getInstance().getOptions();
    }

    @Override
    protected void init() {
        this.navigation = new TabScreenWidget(this.width, this.tabManager,
                new GeneralTab(),
                new DisplayTab(),
                new GraphicsTab()
        );
        this.addDrawableChild(this.navigation);
        this.grid = new GridWidget().setColumnSpacing(10);
        this.navigation.selectTab(0, false);
        this.initTabNavigation();
    }

    @Override
    protected void initTabNavigation() {
        if (this.navigation != null && this.grid != null) {
            this.navigation.setWidth(this.width);
            this.navigation.init();
            this.grid.refreshPositions();
            SimplePositioningWidget.setPos(this.grid, 0, this.height - 36, this.width, 36);
            int i = this.navigation.getNavigationFocus().getBottom();
            ScreenRect screenRect = new ScreenRect(0, i, this.width, this.grid.getY() - i);
            this.tabManager.setTabArea(screenRect);
        }
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        super.resize(client, width, height);
        // Store the index of the currently active tab
        int i = this.navigation.tabs.indexOf(this.tabManager.getCurrentTab());
        // Re-Initialize the Screen - Without this, the widget-positions will be incorrectly aligned after resizing.
        KianaOptionsScreen.this.clearAndInit();
        // Re-Initializing will automatically focus the first tab. So we focus the previously stored index again.
        this.navigation.selectTab(i, false);
    }


    class GeneralTab extends Tab {
        public GeneralTab() {
            super(Text.literal("General"));
        }

        @Override
        public void init() {
            GridWidget.Adder adder = this.grid.createAdder(1);
            OptionListWidget list = new OptionListWidget(width / 4, 32, width / 2, height - 44, 24);
            options.generalOptions.forEach(list::addSingleOptionEntry);
            adder.add(list);
        }
    }

    class DisplayTab extends Tab {
        public DisplayTab() {
            super(Text.literal("Display"));
        }

        @Override
        public void init() {
            GridWidget.Adder adder = this.grid.createAdder(1);
            OptionListWidget list = new OptionListWidget(width / 4, 32, width / 2, height - 44, 24);
            options.displayOptions.forEach(list::addSingleOptionEntry);
            adder.add(list);
        }
    }

    class GraphicsTab extends Tab {
        public GraphicsTab() {
            super(Text.literal("Graphics"));
        }

        @Override
        public void init() {
            GridWidget.Adder adder = this.grid.createAdder(1);
            OptionListWidget list = new OptionListWidget(width / 4, 32, width / 2, height - 44, 24);
            options.graphicOptions.forEach(list::addSingleOptionEntry);
            adder.add(list);
        }
    }
}