package net.withrage.cozytips.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
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

    @Inject(method = "render", at = @At("TAIL"))
    private void cozytips$renderTip(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.textRenderer == null) {
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

        TextRenderer textRenderer = client.textRenderer;

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        int maxTextWidth = Math.min(320, screenWidth - 40);

        List<OrderedText> wrappedTip = textRenderer.wrapLines(Text.literal(tip), maxTextWidth);

        int lineHeight = 10;
        int titleHeight = 12;
        int totalTipHeight = wrappedTip.size() * lineHeight;

        int titleColor = ColorUtils.parseColor(config.titleColor);
        int textColor = ColorUtils.parseColor(config.textColor);

        int baseY = screenHeight - 70 - totalTipHeight - config.yOffset;

        String title = config.title;
        int titleX = (screenWidth - textRenderer.getWidth(title)) / 2;

        context.drawText(textRenderer, Text.literal(title), titleX, baseY, titleColor, true);

        int tipStartY = baseY + titleHeight;

        for (int i = 0; i < wrappedTip.size(); i++) {
            OrderedText line = wrappedTip.get(i);
            int lineWidth = textRenderer.getWidth(line);
            int lineX = (screenWidth - lineWidth) / 2;
            int lineY = tipStartY + (i * lineHeight);

            context.drawText(textRenderer, line, lineX, lineY, textColor, true);
        }
    }
}