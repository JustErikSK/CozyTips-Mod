package net.withrage.cozytips;

import net.fabricmc.api.ModInitializer;

import net.withrage.cozytips.config.CozyTipsConfigManager;
import net.withrage.cozytips.tip.TipManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CozyTips implements ModInitializer {
	public static final String MOD_ID = "cozytips";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CozyTipsConfigManager.load();
		TipManager.reload();
	}
}