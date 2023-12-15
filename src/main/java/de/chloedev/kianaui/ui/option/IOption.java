package de.chloedev.kianaui.ui.option;

import com.mojang.serialization.Codec;
import net.minecraft.client.option.SimpleOption;

public interface IOption<T> {

    Callbacks<T> getOptionCallbacks();

    Codec<T> getOptionCodec();

    /**
     * Just for access reasons.
     */
    interface Callbacks<T> extends SimpleOption.Callbacks<T> {
    }
}