package functions.pet;

import io.restassured.response.Response;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

public class petGetFun {

    public static Response sendRequest(BigDecimal id) {
        return given()
                .baseUri("https://petstore.swagger.io/v2")
                .get("/pet/" + id)
                .then()
                .extract().response();
    }
}
