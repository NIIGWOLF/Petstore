package utilsFun;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;

public class UtilJson {
    private static Gson gson = new Gson();

    public static String serialize(Object modelClass) {
        return gson.toJson(modelClass);
    }


    public static <T> T deserialize(String json, Class<T> modelClass) {
        return gson.fromJson(json, modelClass);
    }


    public static String updateJsonValuesWithJPaths(HashMap<String, JsonElement> bodyParams, String mainBody, boolean deleteNull) {
        String body;
        try {
            DocumentContext context = JsonPath.parse(mainBody);
            for (var p : bodyParams.entrySet()) {
                if (p.getKey().startsWith("$.")) {
                    if (deleteNull) {
                        context = p.getValue().isJsonNull()
                                ? context.delete(p.getKey())
                                : contextSetValue(context, p.getKey(), p.getValue());
                    } else {
                        contextSetValue(context, p.getKey(), p.getValue());
                    }
                }
            }
            body = context.jsonString();
        } catch (PathNotFoundException e) {
            throw new Error(e.getMessage());
        }
        return body;
    }

    private static DocumentContext contextSetValue(DocumentContext context, String jsonPath, JsonElement jsonElement) {
        if (jsonElement.isJsonNull()) {
            return context.set(jsonPath, null);
        }
        if (jsonElement.isJsonArray()) {
            return context.set(jsonPath, new JsonArray());
        }
        JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
        if (jsonPrimitive.isString()) {
            return context.set(jsonPath, jsonPrimitive.getAsString());
        }
        if (jsonPrimitive.isNumber()) {
            return context.set(jsonPath, jsonPrimitive.getAsNumber());
        }
        throw new IllegalArgumentException("Тип JsonElement не определен");
    }


    public static HashMap<String, JsonElement> parseJsonToHashMapJsonElement(String json) {
        var jsonElement = new Gson().fromJson(json, JsonElement.class);
        return new HashMap<String, JsonElement>(jsonElement.getAsJsonObject().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    public static void checkJsonToJson(Object expected, Object actual, List<String> ignorefields) {
        assertThatJson(actual)
                .when(IGNORING_ARRAY_ORDER)
                .withTolerance(0)
                .whenIgnoringPaths(ignorefields.toArray(new String[0]))
                .isEqualTo(expected);
    }
}
