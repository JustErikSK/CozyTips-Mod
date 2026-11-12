package net.withrage.cozytips.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeybindPlaceholderUtils {

    private static final Pattern KEYBIND_PATTERN =
            Pattern.compile("\\{keybind:([^}]+)}");

    public static Text resolve(String input) {
        MutableText result = Text.empty();

        if (input == null || input.isEmpty()) {
            return result;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        Matcher matcher = KEYBIND_PATTERN.matcher(input);

        int lastEnd = 0;

        while (matcher.find()) {
            result.append(Text.literal(
                    input.substring(lastEnd, matcher.start())
            ));

            String identifier = matcher.group(1).trim();
            KeyBinding matchingKey = null;

            if (client != null && client.options != null) {
                for (KeyBinding keyBinding : client.options.allKeys) {
                    if (keyBinding.getId().equals(identifier)) {
                        matchingKey = keyBinding;
                        break;
                    }
                }
            }

            if (matchingKey != null) {
                result.append(
                        matchingKey.getBoundKeyLocalizedText()
                                .copy()
                                .formatted(Formatting.BOLD)
                );
            } else {
                result.append(Text.literal(matcher.group()));
            }

            lastEnd = matcher.end();
        }
        result.append(Text.literal(input.substring(lastEnd)));
        return result;
    }
}