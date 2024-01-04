package de.chloedev.kianaui.mixin.widget;

import de.chloedev.kianalibfabric.util.ColorUtil;
import de.chloedev.kianaui.KianaUIClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PressableWidget.class)
public abstract class MixinPressableWidget extends ClickableWidget {

    @Shadow
    public abstract void drawMessage(DrawContext context, TextRenderer textRenderer, int color);

    public MixinPressableWidget(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }

    @Inject(method = "renderWidget", at = @At("HEAD"), cancellable = true)
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        ci.cancel();
        int schemeColor = KianaUIClient.getInstance().getOptions().colorSchemeOption.getValue() == 1 ? ColorUtil.of(10, 10, 10) : ColorUtil.of(220, 220, 220);
        int textColor = KianaUIClient.getInstance().getOptions().colorSchemeOption.getValue() == 1 ? ColorUtil.of(255, 255, 255) : ColorUtil.of(50, 50, 50);
        context.drawBorder(this.getX(), this.getY(), this.getWidth(), this.getHeight(), schemeColor);
        context.fill(this.getX() + 3, this.getY() + 3, this.getX() + this.getWidth() - 3, this.getY() + this.getHeight() - 3, schemeColor);
        this.drawMessage(context, MinecraftClient.getInstance().textRenderer, textColor| MathHelper.ceil(this.alpha * 255.0f) << 24);

    }
}
