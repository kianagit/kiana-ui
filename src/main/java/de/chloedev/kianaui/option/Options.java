package de.chloedev.kianaui.option;

import de.chloedev.kianalibfabric.io.FileConfiguration;
import de.chloedev.kianaui.KianaUIClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.render.ChunkBuilderMode;
import net.minecraft.client.util.VideoMode;
import net.minecraft.text.Text;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Options {

    private final MinecraftClient client = MinecraftClient.getInstance();
    private final GameOptions gameOptions = client.options;
    FileConfiguration config = KianaUIClient.getInstance().getFileConfig();

    public final List<SimpleOption<?>> generalOptions = new ArrayList<>(), displayOptions = new ArrayList<>(), graphicOptions = new ArrayList<>(), otherOptions = new ArrayList<>();

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public final SimpleOption<Integer> fovOption = register(generalOptions, gameOptions.getFov());
    public final SimpleOption<Boolean> fullscreenOption = register(displayOptions, gameOptions.getFullscreen());
    public final SimpleOption<Integer> resolutionOption = register(displayOptions, new SimpleOption<>("options.fullscreen.resolution", SimpleOption.emptyTooltip(), (prefix, value) -> {
        if (client.getWindow().getMonitor() == null) return Text.translatable("options.fullscreen.unavailable");
        if (value == -1) return GameOptions.getGenericValueText(prefix, Text.translatable("options.fullscreen.current"));
        VideoMode videoMode = client.getWindow().getMonitor().getVideoMode((int) value);
        return GameOptions.getGenericValueText(prefix, Text.translatable("options.fullscreen.entry", videoMode.getWidth(), videoMode.getHeight(), videoMode.getRefreshRate(), videoMode.getRedBits() + videoMode.getGreenBits() + videoMode.getBlueBits()));
    }, new SimpleOption.ValidatingIntSliderCallbacks(-1, client.getWindow().getMonitor() != null ? client.getWindow().getMonitor().getVideoModeCount() - 1 : -1), client.getWindow().getMonitor() == null ? -1 : client.getWindow().getVideoMode().map(client.getWindow().getMonitor()::findClosestVideoModeIndex).orElse(-1), value -> {
        if (client.getWindow().getMonitor() != null) client.getWindow().setVideoMode(value == -1 ? Optional.empty() : Optional.of(client.getWindow().getMonitor().getVideoMode(value)));
    }));
    public final SimpleOption<GraphicsMode> graphicsModeOption = register(graphicOptions, new OptionWrapper<>(gameOptions.getGraphicsMode()).valueTextGetter(graphicsMode -> Text.literal(StringUtils.capitalize(graphicsMode.toString()))).wrap());
    public final SimpleOption<Integer> renderDistanceOption = register(graphicOptions, gameOptions.getViewDistance());
    public final SimpleOption<Integer> simulationDistanceOption = register(graphicOptions, gameOptions.getSimulationDistance());
    public final SimpleOption<Integer> biomeBlendOption = register(graphicOptions, gameOptions.getBiomeBlendRadius());
    public final SimpleOption<ChunkBuilderMode> chunkBuilderModeOption = register(graphicOptions, new OptionWrapper<>(gameOptions.getChunkBuilderMode()).key("Chunk Builder Mode").wrap());
    public final SimpleOption<Boolean> smoothLightingOption = register(graphicOptions, new OptionWrapper<>(gameOptions.getAo()).key("Ambient Occlusion").wrap());
    public final SimpleOption<Integer> colorSchemeOption = register(generalOptions, new SimpleOption<>("Color-Scheme", SimpleOption.emptyTooltip(), (prefix, value) -> {
        if (value == 0) return Text.literal("Light");
        if (value == 1) return Text.literal("Dark");
        return Text.literal("Error...");
    }, new SimpleOption.PotentialValuesBasedCallbacks<>(List.of(0, 1), null), gameOptions.getMonochromeLogo().getValue() ? 1 : 0, value -> {
        if (value == 0) gameOptions.getMonochromeLogo().setValue(false);
        else if (value == 1) gameOptions.getMonochromeLogo().setValue(true);
        else throw new IllegalArgumentException("Illegal Value for Option 'Color-Scheme'");
    }));

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Use the {@link #register} methods below to register any custom option to any of the categories above.
     * If using an index, this will add the option to the given index in the list.
     * If another mod also adds an option at the same index as yours, the option that was added last will be on top.
     */
    public <T> SimpleOption<T> register(List<SimpleOption<?>> list, int index, SimpleOption<T> option) {
        list.add(index, option);
        return option;
    }

    public <T> SimpleOption<T> register(List<SimpleOption<?>> list, SimpleOption<T> option) {
        list.add(option);
        return option;
    }
}