package domain;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CardLoader {

    public List<Card> loadFromClasspath(Class<?> anchorClass, String resourcePath) throws IOException {
        try (InputStream in = anchorClass.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new FileNotFoundException("Resource not found: " + resourcePath);
            }
            JsonArray root = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                    .getAsJsonArray();
            List<Card> cards = new ArrayList<>();
            for (JsonElement element : root) {
                JsonObject o = element.getAsJsonObject();
                int level = o.get("level").getAsInt();
                TokenColor bonusColor = TokenColor.valueOf(o.get("bonusColor").getAsString());
                int prestigePoints = o.get("prestigePoints").getAsInt();
                Map<TokenColor, Integer> cost = new EnumMap<>(TokenColor.class);
                JsonObject costObj = o.getAsJsonObject("cost");
                for (Map.Entry<String, JsonElement> entry : costObj.entrySet()) {
                    cost.put(TokenColor.valueOf(entry.getKey()), entry.getValue().getAsInt());
                }
                cards.add(new Card(level, bonusColor, cost, prestigePoints));
            }
            return cards;
        }
    }
}
