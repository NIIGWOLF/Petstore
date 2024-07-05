package tests.pet;

import functions.pet.petDeleteFun;
import functions.pet.petGetFun;
import functions.pet.petPostFun;
import io.restassured.module.jsv.JsonSchemaValidator;
import model.Pat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilsFun.UtilFiles;
import utilsFun.UtilJson;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class petGet extends petGetFun {
    Pat requestBody = new Pat();
    Pat responseBody;

    @BeforeEach
    public void beforeMethod() {
        responseBody = petPostFun.sendRequest(UtilJson.serialize(requestBody))
                .then()
                .extract().body().as(Pat.class);
    }

    @AfterEach
    public void afterMethod() {
        petDeleteFun.sendRequest(responseBody.id);
    }

    @Test
    public void testComplete() {
        var newResponseBody = sendRequest(responseBody.id)
                .then()
                .statusCode(200)
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(UtilFiles.readFile("pet/petGet/schemas/petSchema.json")))
                .extract().body().as(Pat.class);
        UtilJson.checkJsonToJson(newResponseBody, responseBody, List.of());
    }

    @Test
    public void testFailed404() {
        sendRequest(new BigDecimal(-1))
                .then()
                .statusCode(404)
                .assertThat()
                .body("code", equalTo(1))
                .body("type", equalTo("error"))
                .body("message", equalTo("Pet not found"));
    }
}
