package de.chloedev.kianaui.ui.option;

import com.mojang.serialization.Codec;
import de.chloedev.kianaui.ui.widget.ClickableTextWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ClickableOption extends Option<Void> {

    private final ClickAction action;

    public ClickableOption(String text, ClickAction action) {
        super(text);
        this.action = action;
    }

    @Override
    public IOption.Callbacks<Void> getOptionCallbacks() {
        return new IOption.Callbacks<>() {

            @Override
            public Function<SimpleOption<Void>, ClickableWidget> getWidgetCreator(TooltipFactory<Void> tooltipFactory, GameOptions gameOptions, int x, int y, int width, Consumer<Void> changeCallback) {
                return o -> new ClickableTextWidget(x, y, width, 20, Text.literal(ClickableOption.this.getText()), button -> changeCallback.accept(null));
            }

            public Optional<Void> validate(Void value) {
                return Optional.empty();
            }

            public Codec<Void> codec() {
                return getOptionCodec();
            }
        };
    }

    public interface ClickAction {
        void onClick(ClickableWidget widget);
    }
}

