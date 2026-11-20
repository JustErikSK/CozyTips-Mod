package net.withrage.cozytips.client.tip;

import net.withrage.cozytips.client.config.CozyTipsConfig;
import net.withrage.cozytips.client.config.CozyTipsConfigManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TipManager {

    private static final Random RANDOM = new Random();
    private static final String FALLBACK_TIP = "Never Gonna Give You Up";

    private static List<String> tips = new ArrayList<>();
    private static String currentTip = FALLBACK_TIP;
    private static long nextChangeTime = 0L;
    private static int currentIndex = 0;

    public static void reload() {
        CozyTipsConfig config = CozyTipsConfigManager.getConfig();

        tips = config.tips == null
                ? new ArrayList<>()
                : new ArrayList<>(config.tips);

        if (tips.isEmpty()) {
            currentIndex = 0;
            currentTip = FALLBACK_TIP;
        } else if (config.randomOrder) {
            currentIndex = 0;
            currentTip = getRandomTip();
        } else {
            currentIndex = config.randomStart
                    ? RANDOM.nextInt(tips.size())
                    : 0;

            currentTip = tips.get(currentIndex);
        }

        nextChangeTime = System.currentTimeMillis()
                + Math.max(1, config.cycleTimeMs);
    }

    public static String getCurrentTip() {
        CozyTipsConfig config = CozyTipsConfigManager.getConfig();

        if (!config.enabled) {
            return "";
        }

        long now = System.currentTimeMillis();

        if (now >= nextChangeTime) {
            if (tips.isEmpty()) {
                currentTip = FALLBACK_TIP;
            } else if (config.randomOrder) {
                currentTip = getRandomTip();
            } else {
                currentIndex = (currentIndex + 1) % tips.size();
                currentTip = tips.get(currentIndex);
            }

            nextChangeTime = now + Math.max(1, config.cycleTimeMs);
        }

        return currentTip;
    }

    private static String getRandomTip() {
        if (tips.isEmpty()) {
            return FALLBACK_TIP;
        }

        return tips.get(RANDOM.nextInt(tips.size()));
    }
}
