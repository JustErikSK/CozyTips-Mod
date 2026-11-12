package net.withrage.cozytips.util;

public final class TipPositionUtils {

    private static final int BOTTOM_MARGIN = 8;

    private TipPositionUtils() {
    }

    public static int calculateY(
            int screenHeight,
            int titleHeight,
            int tipHeight,
            int yOffset
    ) {
        int totalHeight = titleHeight + tipHeight;

        int baseY = screenHeight - BOTTOM_MARGIN - totalHeight;

        return baseY - yOffset;
    }
}
