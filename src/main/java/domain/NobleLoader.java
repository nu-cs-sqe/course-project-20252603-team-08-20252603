package domain;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class NobleLoader {

    public List<Noble> loadFromClasspath(Class<?> anchorClass, String resourcePath) throws IOException {
        InputStream in = anchorClass.getResourceAsStream(resourcePath);
        JsonArray root = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonArray();
        List<Noble> nobles = new ArrayList<>();
        for (JsonElement element : root) {
            JsonObject o = element.getAsJsonObject();
            int prestigePoints = o.get("prestigePoints").getAsInt();
            Map<TokenColor, Integer> requirements = new EnumMap<>(TokenColor.class);
            JsonObject reqObj = o.getAsJsonObject("requirements");
            for (Map.Entry<String, JsonElement> entry : reqObj.entrySet()) {
                requirements.put(TokenColor.valueOf(entry.getKey()), entry.getValue().getAsInt());
            }
            nobles.add(new Noble(requirements, prestigePoints));
        }
        return nobles;
    }
}
