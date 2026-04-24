package net.withrage.cozytips.tip;

import net.withrage.cozytips.config.CozyTipsConfig;
import net.withrage.cozytips.config.CozyTipsConfigManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TipManager {
    private static final Random RANDOM = new Random();

    private static List<String> tips = new ArrayList<>();
    private static String currentTip = "Animals are amazing!";
    private static long nextChangeTime = 0L;

    public static void reload() {
        CozyTipsConfig config = CozyTipsConfigManager.getConfig();
        tips = new ArrayList<>(config.tips);

        if (tips.isEmpty()) {
            currentTip = "Animals are amazing!";
        } else {
            currentTip = getRandomTip();
        }

        nextChangeTime = System.currentTimeMillis() + config.cycleTimeMs;
    }

    public static String getCurrentTip() {
        CozyTipsConfig config = CozyTipsConfigManager.getConfig();

        if (!config.enabled) {
            return "";
        }

        long now = System.currentTimeMillis();
        if (now >= nextChangeTime) {
            currentTip = tips.isEmpty() ? "Animals are amazing!" : getRandomTip();
            nextChangeTime = now + config.cycleTimeMs;
        }

        return currentTip;
    }

    private static String getRandomTip() {
        if (tips.isEmpty()) {
            return "Animals are amazing!";
        }

        return tips.get(RANDOM.nextInt(tips.size()));
    }
}
