package de.chloedev.kianaui.mixin.widget;

import de.chloedev.kianalibfabric.util.ColorUtil;
import de.chloedev.kianaui.KianaUIClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CyclingButtonWidget.class)
public abstract class MixinCyclingButtonWidget<T> extends PressableWidget {
    @Shadow
    public abstract T getValue();

    @Shadow
    protected abstract T getValue(int offset);

    public MixinCyclingButtonWidget(int i, int j, int k, int l, Text text) {
        super(i, j, k, l, text);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int schemeColor = KianaUIClient.getInstance().getOptions().colorSchemeOption.getValue() == 1 ? ColorUtil.of(10, 10, 10) : ColorUtil.of(220, 220, 220);
        int textColor = KianaUIClient.getInstance().getOptions().colorSchemeOption.getValue() == 1 ? ColorUtil.of(255, 255, 255) : ColorUtil.of(50, 50, 50);
        context.drawBorder(this.getX(), this.getY(), this.getWidth(), this.getHeight(), schemeColor);
        if (this.getValue() instanceof Boolean b) {
            int i = (this.getX() + this.getWidth()) - (this.getHeight() - 4);
            int j = this.getY() + 4;
            int k = (this.getHeight() - 8);
            context.drawBorder(i, j, k, k, schemeColor);
            if (b) context.fill((i + 2), j + 2, (i + 2) + (k - 4), (j + 2) + (k - 4), schemeColor);
        } else context.fill(this.getX() + 3, this.getY() + 3, this.getX() + this.getWidth() - 3, this.getY() + this.getHeight() - 3, schemeColor);
        this.drawMessage(context, MinecraftClient.getInstance().textRenderer, textColor | MathHelper.ceil(this.alpha * 255.0f) << 24);
    }

    @Override
    public Text getMessage() {
        if (this.getValue() instanceof Boolean)
            return Text.literal(super.getMessage().getString().split(":")[0]);
        return super.getMessage();
    }
}
