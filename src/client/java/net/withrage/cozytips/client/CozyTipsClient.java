package net.withrage.cozytips.client;

import net.fabricmc.api.ClientModInitializer;
import net.withrage.cozytips.client.config.CozyTipsConfigManager;
import net.withrage.cozytips.client.tip.TipManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CozyTipsClient implements ClientModInitializer {
	public static final String MOD_ID = "cozytips";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		CozyTipsConfigManager.load();
		TipManager.reload();
	}
}