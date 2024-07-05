package pet;

import functions.pet.PetDeleteFun;
import functions.pet.PetPostFun;
import utilsFun.UtilFiles;
import utilsFun.UtilJson;
import functions.pet.PetPutFun;
import io.restassured.module.jsv.JsonSchemaValidator;
import model.Pat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static utilsFun.UtilJson.*;
import static utilsFun.UtilJson.serialize;

public class petPut extends PetPutFun {
    Pat requestBody = new Pat();
    Pat responseBody;

    @BeforeEach
    public void beforeMethod() {
        responseBody = PetPostFun.sendRequest(UtilJson.serialize(requestBody))
                .then()
                .extract().body().as(Pat.class);
    }

    @AfterEach
    public void afterMethod() {
        PetDeleteFun.sendRequest(responseBody.id);
    }

    @ParameterizedTest
    @MethodSource("petWithoutFieldSuccessDataProvider")
    public void testComplete(String testData) {
        // изменение тела запроса согласно тестовым данным
        var mapTestData = parseJsonToHashMapJsonElement(testData);
        var newRequestBody = new Pat();
        newRequestBody.id = responseBody.id;
        String strRequestBody = updateJsonValuesWithJPaths(mapTestData, serialize(newRequestBody), true);

        var newResponseBody = sendRequest(strRequestBody)
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(UtilFiles.readFile("pet/petPut/schemas/petSchema.json")))
                .extract().body().as(Pat.class);

        // проверка на соответсвие модели
        var ignoreList = new java.util.ArrayList<>(mapTestData.keySet().stream().filter(x -> x.startsWith("$")).toList());
        ignoreList.add("$.id");
        UtilJson.checkJsonToJson(newRequestBody, newResponseBody, ignoreList);
        System.out.println(serialize(responseBody));
    }
}
