package de.chloedev.kianaui.ui.option;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import de.chloedev.kianalibfabric.util.ActionUtil;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

public abstract class Option<T> extends SimpleOption<T> implements IOption<T> {

    private final String text;

    public Option(String text) {
        super(text, emptyTooltip(), (optionText, value) -> Text.literal(text + "_1"), null, null, null, ActionUtil::doNothing);
        this.text = text;
        this.callbacks = this.getOptionCallbacks();
        this.codec = this.getOptionCodec();
    }


    @Override
    public Codec<T> getOptionCodec() {
        return new Codec<>() {
            @Override
            public <T1> DataResult<Pair<T, T1>> decode(DynamicOps<T1> ops, T1 input) {
                return null;
            }

            @Override
            public <T1> DataResult<T1> encode(T input, DynamicOps<T1> ops, T1 prefix) {
                return null;
            }
        };
    }

    @Deprecated
    @Override
    public Codec<T> getCodec() {
        return super.getCodec();
    }

    @Deprecated
    @Override
    public SimpleOption.Callbacks<T> getCallbacks() {
        return super.getCallbacks();
    }

    public String getText() {
        return text;
    }
}
