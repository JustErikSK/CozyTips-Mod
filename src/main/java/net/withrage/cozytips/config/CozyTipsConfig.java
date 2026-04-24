package net.withrage.cozytips.config;

import java.util.ArrayList;
import java.util.List;

public class CozyTipsConfig {
    public boolean enabled = true;
    public int cycleTimeMs = 9000;
    public String title = "Tip:";
    public int titleColor = 0xFFE8C547;
    public int textColor = 0xFFFFFFFF;
    public int yOffset = -38;

    public List<String> tips = new ArrayList<>(List.of(
            "Octopuses have three hearts.",
            "A group of flamingos is called a flamboyance.",
            "Some frogs can survive being frozen.",
            "Owls can rotate their heads much farther than humans can.",
            "Sea otters hold hands while sleeping so they do not drift apart."
    ));
}
