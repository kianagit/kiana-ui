package de.chloedev.kianaui.mixin.widget;

import de.chloedev.kianalibfabric.util.ColorUtil;
import de.chloedev.kianaui.KianaUIClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.OptionSliderWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SimpleOption.OptionSliderWidgetImpl.class)
public abstract class MixinOptionSliderWidgetImpl extends OptionSliderWidget {

    protected MixinOptionSliderWidgetImpl(GameOptions options, int x, int y, int width, int height, double value) {
        super(options, x, y, width, height, value);
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int schemeColor = KianaUIClient.getInstance().getOptions().colorSchemeOption.getValue() == 1 ? ColorUtil.of(10, 10, 10) : ColorUtil.of(220, 220, 220);
        int textColor = KianaUIClient.getInstance().getOptions().colorSchemeOption.getValue() == 1 ? ColorUtil.of(255, 255, 255) : ColorUtil.of(50, 50, 50);
        int i = (int) ((this.getX() + 3) + ((this.getWidth() - 6) * this.value));
        int j = this.getY();
        context.drawBorder(this.getX(), this.getY(), this.getWidth(), this.getHeight(), schemeColor);
        context.fill(this.getX() + 3, this.getY() + 3, i, this.getY() + this.getHeight() - 3, schemeColor);
        this.drawScrollableText(context, MinecraftClient.getInstance().textRenderer, 2, textColor | MathHelper.ceil(this.alpha * 255.0) << 24);
    }
}