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
import java.util.function.Supplier;

public class IntegerSliderOption extends SimpleOption<Integer> {

    private final int minValue;
    private final int maxValue;

    public IntegerSliderOption(String text, int minValue, int maxValue, int defaultValue, ValueTextGetter<Integer> textGetter, Consumer<Integer> changeCallback) {
        super(text, SimpleOption.emptyTooltip(), textGetter, null, null, defaultValue, changeCallback);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.callbacks = new SliderCallbacks(this.minValue, this.maxValue);
        this.codec = new SliderCodec();
    }

    public IntegerSliderOption(String text, int minValue, int maxValue, int defaultValue, Consumer<Integer> changeCallback) {
        this(text, minValue, maxValue, defaultValue, (optionText, value) -> optionText.copy().append(": " + value), changeCallback);
    }

    public IntegerSliderOption(SimpleOption<Integer> parent, String text, ValueTextGetter<Integer> textGetter, Consumer<Integer> changeCallback) {
        this(text, ((Supplier<Integer>) () -> {
            if (parent.getCallbacks() instanceof MaxSuppliableIntCallbacks c) return c.minInclusive();
            else if (parent.getCallbacks() instanceof ValidatingIntSliderCallbacks c) return c.minInclusive();
            System.out.println("Min-Value not found. Defaulting to 0");
            return 0;
        }).get(), ((Supplier<Integer>) () -> {
            if (parent.getCallbacks() instanceof MaxSuppliableIntCallbacks c) return c.maxInclusive();
            else if (parent.getCallbacks() instanceof ValidatingIntSliderCallbacks c) return c.maxInclusive();
            System.out.println("Max-Value not found. Defaulting to 1");
            return 1;
        }).get(), parent.getValue(), textGetter, value -> {
            parent.setValue(value);
            parent.changeCallback.accept(value);
            MinecraftClient.getInstance().options.write();
            changeCallback.accept(value);
        });
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public class SliderCallbacks implements SimpleOption.SliderCallbacks<Integer> {

        private final int minValue;
        private final int maxValue;

        public SliderCallbacks(int minValue, int maxValue) {
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        @Override
        public double toSliderProgress(Integer value) {
            return MathHelper.map(value, this.minValue, this.maxValue, 0.0, 1.0);
        }

        @Override
        public Integer toValue(double value) {
            return MathHelper.floor(MathHelper.map(value, 0.0, 1.0, this.minValue, this.maxValue));
        }

        @Override
        public Optional<Integer> validate(Integer value) {
            return Optional.of(value);
        }

        @Override
        public Codec<Integer> codec() {
            return null;
        }

        @Override
        public Function<SimpleOption<Integer>, ClickableWidget> getWidgetCreator(TooltipFactory<Integer> tooltipFactory, GameOptions gameOptions, int x, int y, int width, Consumer<Integer> changeCallback) {
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            return o -> new SliderWidget(gameOptions, x, y, width, 20, o, this, tooltipFactory, changeCallback);
        }
    }

    public static class SliderCodec implements Codec<Integer> {
        /**
         * There's no need to encode/decode anything here, I couldn't care less about all of this stupid validation...
         */

        public <T> DataResult<Pair<Integer, T>> decode(DynamicOps<T> ops, T input) {
            return null;
        }

        public <T> DataResult<T> encode(Integer input, DynamicOps<T> ops, T prefix) {
            return null;
        }
    }

    public class SliderWidget extends OptionSliderWidget {
        private final SimpleOption<Integer> option;
        private final SliderCallbacks callbacks;
        private final TooltipFactory<Integer> tooltipFactory;
        private final Consumer<Integer> changeCallback;

        SliderWidget(GameOptions options, int x, int y, int width, int height, SimpleOption<Integer> option, SliderCallbacks callbacks, TooltipFactory<Integer> tooltipFactory, Consumer<Integer> changeCallback) {
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
        public void onClick(double mouseX, double mouseY) {
            super.onClick(mouseX, mouseY);
        }

        @Override
        public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            MinecraftClient client = MinecraftClient.getInstance();
            TextRenderer textRenderer = client.textRenderer;
            context.setShaderColor(1.0f, 1.0f, 1.0f, this.alpha);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            context.drawBorder(this.getX(), this.getY(), this.getWidth(), this.getHeight(), ColorUtil.of(0, 0, 0));
            context.fill(this.getX() + 2, this.getY() + 2, (int) ((this.getX() + 2) + ((this.width - 4) * this.value)), (this.getY() + this.getHeight()) - 2, ColorUtil.of(0, 0, 0, 150));
            context.drawCenteredTextWithShadow(textRenderer, this.getMessage(), this.getX() + (this.getWidth() / 2), this.getY() + (this.getHeight() / 2) - 4, ColorUtil.of(255, 255, 255));
        }

        @Override
        protected void setValueFromMouse(double mouseX) {
            this.setValue((mouseX - (double)(this.getX() + 2)) / (double)(this.width - 4));
        }
    }
}