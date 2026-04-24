package net.withrage.cozytips.config;

import java.util.ArrayList;
import java.util.List;

public class CozyTipsConfig {
    public boolean enabled = true;
    public int cycleTimeMs = 5000;
    public String title = "Tip:";
    public int titleColor = 0xFFE8C547;
    public int textColor = 0xFFFFFFFF;
    public int yOffset = -38;

    public List<String> tips = new ArrayList<>(List.of(
            "Torches can prevent hostile mobs from spawning nearby.",
            "Sneaking lets you avoid falling off edges.",
            "Always carry a bucket of water.",
            "Beds let you skip the night and set your respawn point.",
            "Cure zombie villagers to get discounts on villager trades."
    ));
}
