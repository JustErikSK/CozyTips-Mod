package net.withrage.cozytips.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class CozyTipsConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("cozytips.json");

    private static CozyTipsConfig config = new CozyTipsConfig();

    public static void load() {
        try {
            if (Files.notExists(CONFIG_PATH)) {
                saveDefault();
            }

            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                CozyTipsConfig loaded = GSON.fromJson(reader, CozyTipsConfig.class);
                if (loaded != null) {
                    config = loaded;
                }
            }

            validate();
        } catch (Exception e) {
            System.err.println("[CozyTips] Failed to load config, using defaults.");
            e.printStackTrace();
            config = new CozyTipsConfig();
        }
    }

    private static void saveDefault() throws IOException {
        Files.createDirectories(CONFIG_PATH.getParent());

        try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
            GSON.toJson(new CozyTipsConfig(), writer);
        }
    }

    private static void validate() {
        if (config.cycleTimeMs < 1000) {
            config.cycleTimeMs = 1000;
        }

        if (config.title == null) {
            config.title = "Fun Fact";
        }

        if (config.tips == null) {
            config.tips = new java.util.ArrayList<>();
        }
    }

    public static CozyTipsConfig getConfig() {
        return config;
    }
}
