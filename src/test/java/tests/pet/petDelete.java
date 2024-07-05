package tests.pet;

import functions.pet.petDeleteFun;
import functions.pet.petPostFun;
import model.Pat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilsFun.UtilJson;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;

public class petDelete extends petDeleteFun {
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
        sendRequest(responseBody.id).then().assertThat()
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"))
                .body("message", equalTo(String.valueOf(responseBody.id)));
    }

    @Test
    public void testFailed404() {
        sendRequest(new BigDecimal(-1))
                .then()
                .statusCode(404);
    }
}
