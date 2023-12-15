package de.chloedev.kianaui.mixin;

import de.chloedev.kianaui.ui.widget.TabScreenButtonWidget;
import de.chloedev.kianaui.ui.widget.TabScreenWidget;
import net.minecraft.client.gui.tab.TabManager;
import net.minecraft.client.gui.widget.TabButtonWidget;
import net.minecraft.client.gui.widget.TabNavigationWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(TabNavigationWidget.class)
public class MixinTabNavigationWidget {

    @Shadow
    @Final
    private TabManager tabManager;

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/GridWidget;add(Lnet/minecraft/client/gui/widget/Widget;II)Lnet/minecraft/client/gui/widget/Widget;"))
    public void test(Args args) {
        if (((TabNavigationWidget) (Object) this) instanceof TabScreenWidget) {
            TabButtonWidget old = args.get(0);
            args.set(0, new TabScreenButtonWidget(this.tabManager, old.getTab(), old.getWidth(), old.getHeight()));
        }
    }
}
