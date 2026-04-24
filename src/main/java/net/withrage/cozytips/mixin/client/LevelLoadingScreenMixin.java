package net.withrage.cozytips.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.withrage.cozytips.config.CozyTipsConfig;
import net.withrage.cozytips.config.CozyTipsConfigManager;
import net.withrage.cozytips.tip.TipManager;
import net.withrage.cozytips.util.ColorUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LevelLoadingScreen.class)
public class LevelLoadingScreenMixin {

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void cozytips$renderTip(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();

        if (client == null || client.font == null) {
            return;
        }

        CozyTipsConfig config = CozyTipsConfigManager.getConfig();

        if (!config.enabled) {
            return;
        }

        String tip = TipManager.getCurrentTip();

        if (tip == null || tip.isBlank()) {
            return;
        }

        Font font = client.font;

        int screenWidth = client.getWindow().getGuiScaledWidth();
        int screenHeight = client.getWindow().getGuiScaledHeight();

        int maxTextWidth = Math.min(420, screenWidth - 40);
        List<FormattedCharSequence> wrappedTip = font.split(Component.literal(tip), maxTextWidth);

        int titleColor = ColorUtils.parseColor(config.titleColor);
        int textColor = ColorUtils.parseColor(config.textColor);

        int lineHeight = 10;
        int titleHeight = 12;
        int totalTipHeight = wrappedTip.size() * lineHeight;

        int baseY = screenHeight - 70 - totalTipHeight;

        Component title = Component.literal(config.title);
        int titleX = (screenWidth - font.width(title)) / 2;

        graphics.text(font, title, titleX, baseY, titleColor, true);

        int tipStartY = baseY + titleHeight;

        for (int i = 0; i < wrappedTip.size(); i++) {
            FormattedCharSequence line = wrappedTip.get(i);
            int lineWidth = font.width(line);
            int lineX = (screenWidth - lineWidth) / 2;
            int lineY = tipStartY + (i * lineHeight);

            graphics.text(font, line, lineX, lineY, textColor, true);
        }
    }
}