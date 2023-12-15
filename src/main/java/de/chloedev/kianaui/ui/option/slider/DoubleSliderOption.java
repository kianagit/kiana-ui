package de.chloedev.kianaui.ui.option.slider;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import de.chloedev.kianalibfabric.util.ColorUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.OptionSliderWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.util.math.MathHelper;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class DoubleSliderOption extends SimpleOption<Double> {

    private final double minValue;
    private final double maxValue;

    public DoubleSliderOption(String text, double minValue, double maxValue, double defaultValue, ValueTextGetter<Double> textGetter, Consumer<Double> changeCallback) {
        super(text, SimpleOption.emptyTooltip(), textGetter, null, null, defaultValue, changeCallback);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.callbacks = new DoubleSliderOption.SliderCallbacks(this.minValue, this.maxValue);
        this.codec = new DoubleSliderOption.SliderCodec();
    }

    public DoubleSliderOption(String text, double minValue, double maxValue, double defaultValue, Consumer<Double> changeCallback) {
        this(text, minValue, maxValue, defaultValue, (optionText, value) -> optionText.copy().append(": " + value), changeCallback);
    }

    public DoubleSliderOption(SimpleOption<Double> parent, String text, ValueTextGetter<Double> textGetter, Consumer<Double> changeCallback) {
        this(text, 0.0D, 1.0D, parent.getValue(), textGetter, value -> {
            parent.changeCallback.accept(value);
            changeCallback.accept(value);
        });
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public class SliderCallbacks implements SimpleOption.SliderCallbacks<Double> {

        private final double minValue;
        private final double maxValue;

        public SliderCallbacks(double minValue, double maxValue) {
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        @Override
        public double toSliderProgress(Double value) {
            return MathHelper.map(value, this.minValue, this.maxValue, 0.0, 1.0);
        }

        @Override
        public Double toValue(double value) {
            return MathHelper.map(value, 0.0, 1.0, this.minValue, this.maxValue);
        }

        @Override
        public Optional<Double> validate(Double value) {
            return Optional.of(value);
        }

        @Override
        public Codec<Double> codec() {
            return null;
        }

        @Override
        public Function<SimpleOption<Double>, ClickableWidget> getWidgetCreator(TooltipFactory<Double> tooltipFactory, GameOptions gameOptions, int x, int y, int width, Consumer<Double> changeCallback) {
            return o -> new SliderWidget(gameOptions, x, y, width, 20, o, this, tooltipFactory, changeCallback);
        }
    }

    public static class SliderCodec implements Codec<Double> {
        /**
         * There's no need to encode/decode anything here, I couldn't care less about all of this stupid validation...
         */

        public <T> DataResult<Pair<Double, T>> decode(DynamicOps<T> ops, T input) {
            return null;
        }

        public <T> DataResult<T> encode(Double input, DynamicOps<T> ops, T prefix) {
            return null;
        }
    }

    public class SliderWidget extends OptionSliderWidget {
        private final SimpleOption<Double> option;
        private final SliderCallbacks callbacks;
        private final TooltipFactory<Double> tooltipFactory;
        private final Consumer<Double> changeCallback;

        SliderWidget(GameOptions options, int x, int y, int width, int height, SimpleOption<Double> option, SliderCallbacks callbacks, TooltipFactory<Double> tooltipFactory, Consumer<Double> changeCallback) {
            super(options, x, y, width, height, callbacks.toSliderProgress(option.getValue()));
            this.option = option;
            this.callbacks = callbacks;
            this.tooltipFactory = tooltipFactory;
            this.changeCallback = changeCallback;
            this.updateMessage();
        }

        @Override
        protected void updateMessage() {
            this.setMessage(this.option.textGetter.apply(this.option.getValue()));
            this.setTooltip(this.tooltipFactory.apply(this.callbacks.toValue(this.value)));
        }

        @Override
        protected void applyValue() {
            this.option.setValue(this.callbacks.toValue(this.value));
            this.options.write();
            this.changeCallback.accept(this.option.getValue());
        }

        @Override
        public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            MinecraftClient client = MinecraftClient.getInstance();
            TextRenderer textRenderer = client.textRenderer;
            context.setShaderColor(1.0f, 1.0f, 1.0f, this.alpha);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            // Draw Slider Border
            context.drawBorder(this.getX(), this.getY(), this.getWidth(), this.getHeight(), ColorUtil.of(0, 0, 0, 255));
            // Draw Progress
            context.fill(this.getX() + 2, this.getY() + 2, (int) ((this.getX() + 2) + ((this.width - 4) * this.value)), (this.getY() + this.getHeight()) - 2, ColorUtil.of(0, 0, 0, 150));
        }
    }
}