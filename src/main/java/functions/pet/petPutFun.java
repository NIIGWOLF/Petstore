package functions.pet;


import io.restassured.response.Response;
import utilsFun.UtilFiles;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.Iterator;

import static io.restassured.RestAssured.given;


public class petPutFun {

    public static Response sendRequest(String requestBody){
        return given()
                .baseUri("https://petstore.swagger.io/v2")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .put("/pet")
                .then()
                .extract().response();
    }

    public static Iterator<Object[]> petWithoutFieldSuccessDataProvider() {
        var file = UtilFiles.readFile("pet/petPut/testData/validSuccessDP.json");
        JsonArray json = new Gson()
                .fromJson(file, JsonArray.class);
        ArrayList<Object[]> array = new ArrayList<>();
        json.asList().forEach(x -> array.add(new Object[] {x.toString()}));
        return array.iterator();
    }
}
