package de.chloedev.kianaui.option;

import de.chloedev.kianalibfabric.util.ActionUtil;
import de.chloedev.kianaui.ui.option.slider.IntegerSliderOption;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.Monitor;
import net.minecraft.client.util.VideoMode;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Options {


    public final List<SimpleOption<?>>
            generalOptions = new ArrayList<>(),
            displayOptions = new ArrayList<>(),
            graphicOptions = new ArrayList<>(),
            otherOptions = new ArrayList<>();

    public Options() {
        this.createOptions();
    }

    private void createOptions() {
        // General
        this.generalOptions.add(new IntegerSliderOption(MinecraftClient.getInstance().options.getFov(), "FOV", (optionText, value) -> optionText.copy().append(": " + value), ActionUtil::doNothing));

        // Display
        Window window = MinecraftClient.getInstance().getWindow();
        Monitor monitor = window.getMonitor();
        this.displayOptions.add(new IntegerSliderOption(new SimpleOption<>("options.fullscreen.resolution", SimpleOption.emptyTooltip(), (prefix, value) -> Text.empty(), new SimpleOption.ValidatingIntSliderCallbacks(-1, monitor != null ? monitor.getVideoModeCount() - 1 : -1), monitor == null ? -1 : window.getVideoMode().map(monitor::findClosestVideoModeIndex).orElse(-1), value -> {
            if (monitor == null) return;
            window.setVideoMode(value == -1 ? Optional.empty() : Optional.of(monitor.getVideoMode(value)));
        }), "Resolution", (optionText, value) -> {
            if (monitor == null) return Text.translatable("options.fullscreen.unavailable");
            if (value == -1) return GameOptions.getGenericValueText(optionText, Text.translatable("options.fullscreen.current"));
            VideoMode videoMode = monitor.getVideoMode(value);
            return GameOptions.getGenericValueText(optionText, Text.translatable("options.fullscreen.entry", videoMode.getWidth(), videoMode.getHeight(), videoMode.getRefreshRate(), videoMode.getRedBits() + videoMode.getGreenBits() + videoMode.getBlueBits()));
        }, ActionUtil::doNothing));
    }
}