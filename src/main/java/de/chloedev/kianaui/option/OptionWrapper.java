package de.chloedev.kianaui.option;

import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

import java.util.function.Function;

/**
 * Provides the Ability to modify existing options, without creating a whole new option.
 * @param <T> The Value-Type
 */
public class OptionWrapper<T> {

    private final SimpleOption<T> option;

    public OptionWrapper(SimpleOption<T> option) {
        this.option = option;
    }

    /**
     * Modifies the Key.<br>
     * The Key is the Left-Value of the Option, for example "FOV: 90" would have "FOV" as key
     */
    public OptionWrapper<T> key(String key) {
        this.option.text = Text.translatable(key);
        return this;
    }

    /**
     * Modifies the Value Text-Getter.<br>
     * This can be useful to modify how the visual value is shown on values that don't have a proper #toString method.<br>
     * Example: <pre>
     * {@code valueTextGetter(gamemodeValue -> Text.literal(gamemodeValue.getFriendlyName()));}
     * </pre>
     * returns the friendlyname of the gamemode object, instead of the class representation.
     */
    public OptionWrapper<T> valueTextGetter(Function<T, Text> valueTextGetter) {
        this.option.textGetter = valueTextGetter;
        return this;
    }

    /**
     * Returns the modified final option
     */
    public SimpleOption<T> wrap() {
        return this.option;
    }
}
