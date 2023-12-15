package de.chloedev.kianaui.ui.option;

import com.mojang.serialization.Codec;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class CycleOption<T> extends Option<T> {

    public final List<T> values;

    public CycleOption(String text, T[] values, T defaultValue) {
        super(text);
        if (values.length == 0) {
            throw new IllegalArgumentException("Values can't be empty.");
        }
        this.values = new ArrayList<>(List.of(values));
        this.setValue(defaultValue);
    }

    @Override
    public IOption.Callbacks<T> getOptionCallbacks() {
        return new IOption.Callbacks<>() {
            public Function<SimpleOption<T>, ClickableWidget> getWidgetCreator(TooltipFactory<T> tooltipFactory, GameOptions gameOptions, int x, int y, int width, Consumer<T> changeCallback) {
                return o -> ButtonWidget.builder(Text.literal(getText() + ": " + getValueText()), button -> {
                    Collections.rotate(values, -1); // Get Next value into index
                    button.setMessage(Text.literal(getText() + ": " + getValueText()));
                }).dimensions(x, y, width, 20).build();
            }

            public Optional<T> validate(T value) {
                return Optional.of(values.get(0));
            }

            public Codec<T> codec() {
                return getOptionCodec();
            }
        };
    }

    @Override
    public T getValue() {
        return this.values.get(0);
    }

    @Override
    public void setValue(T value) {
        boolean b = false;
        for (T val : this.values) {
            if (val.equals(value)) {
                b = true;
                break;
            }
        }
        if (!b) {
            throw new IllegalArgumentException("Argument not found in value-list.");
        }
        while (this.values.get(0) != value) {
            Collections.rotate(this.values, -1);
        }
    }

    public String getValueText() {
        return this.getValue().toString();
    }
}
