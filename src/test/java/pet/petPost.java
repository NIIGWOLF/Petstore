package pet;

import functions.pet.PetDeleteFun;
import functions.pet.PetPostFun;
import io.restassured.module.jsv.JsonSchemaValidator;
import model.Pat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utilsFun.UtilFiles;
import utilsFun.UtilJson;

import static utilsFun.UtilJson.*;

public class petPost extends PetPostFun {
    Pat requestBody = new Pat();
    Pat responseBody;

    @AfterEach
    public void afterMethod() {
        PetDeleteFun.sendRequest(responseBody.id);
    }

    @ParameterizedTest
    @MethodSource("petWithoutFieldSuccessDataProvider")
    public void testComplete(String testData) {
        // изменение тела запроса согласно тестовым данным
        var mapTestData = UtilJson.parseJsonToHashMapJsonElement(testData);
        String strRequestBody = updateJsonValuesWithJPaths(mapTestData, serialize(requestBody), true);

        responseBody = sendRequest(strRequestBody)
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(UtilFiles.readFile("pet/petPost/schemas/petSchema.json")))
                .extract().body().as(Pat.class);

        // проверка на соответсвие модели
        var ignoreList = new java.util.ArrayList<>(mapTestData.keySet().stream().filter(x -> x.startsWith("$")).toList());
        ignoreList.add("$.id");
        UtilJson.checkJsonToJson(requestBody, responseBody, ignoreList);
        System.out.println(serialize(responseBody));
    }
}
