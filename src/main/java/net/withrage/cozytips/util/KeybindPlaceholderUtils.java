package net.withrage.cozytips.util;

import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class KeybindPlaceholderUtils {

    private static final Pattern KEYBIND_PATTERN =
            Pattern.compile("\\{keybind:([^}]+)}");

    private KeybindPlaceholderUtils() {
    }

    public static Component resolve(String input) {
        MutableComponent result = Component.empty();

        if (input == null || input.isEmpty()) {
            return result;
        }

        Minecraft client = Minecraft.getInstance();
        Matcher matcher = KEYBIND_PATTERN.matcher(input);

        int lastEnd = 0;

        while (matcher.find()) {
            result.append(Component.literal(
                    input.substring(lastEnd, matcher.start())
            ));

            String identifier = matcher.group(1).trim();
            KeyMapping matchingKey = null;

            if (client != null && client.options != null) {
                for (KeyMapping keyMapping : client.options.keyMappings) {
                    if (keyMapping.getName().equals(identifier)) {
                        matchingKey = keyMapping;
                        break;
                    }
                }
            }

            if (matchingKey != null) {
                result.append(
                        matchingKey.getTranslatedKeyMessage()
                                .copy()
                                .withStyle(ChatFormatting.BOLD)
                );
            } else {
                result.append(Component.literal(matcher.group()));
            }

            lastEnd = matcher.end();
        }
        result.append(Component.literal(input.substring(lastEnd)));
        return result;
    }
}
