package functions.pet;

import io.restassured.response.Response;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

public class petDeleteFun {

    public static Response sendRequest(BigDecimal id) {
        return given()
                .baseUri("https://petstore.swagger.io/v2")
                .delete("/pet/" + id)
                .then()
                .extract().response();
    }
}
