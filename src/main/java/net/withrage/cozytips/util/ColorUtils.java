package net.withrage.cozytips.util;

public class ColorUtils {

    public static int parseColor(String hex) {
        if (hex == null || hex.isBlank()) {
            return 0xFFFFFFFF;
        }

        hex = hex.trim();

        try {
            if (hex.startsWith("#")) {
                hex = hex.substring(1);
            }

            // #RRGGBB
            if (hex.length() == 6) {
                return (int) Long.parseLong("FF" + hex, 16);
            }

            // #AARRGGBB
            if (hex.length() == 8) {
                return (int) Long.parseLong(hex, 16);
            }

        } catch (Exception ignored) {}

        return 0xFFFFFFFF;
    }
}
