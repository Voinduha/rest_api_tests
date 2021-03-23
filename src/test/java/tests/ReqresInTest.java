package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static utils.FileUtils.readStringFromFile;

public class ReqresInTest {

    @BeforeAll
    static void setup() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void successfulTest() {
        given()
                .when()
                .get("api/users?delay=3")
                .then()
                .log().body()
                .statusCode(200)
                .body("support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    void successfulLoginTest() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }")
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .log().body()
                .body("token", is(notNullValue()));
    }

    @Test
    void deleteUserTest() {
        given()
                .when()
                .delete("api/users/7")
                .then()
                .statusCode(204);
    }

    @Test
    void unSuccessfulLoginTest() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"email\": \"peter@klaven\" }")
                .when()
                .post("/api/login")
                .then()
                .statusCode(400)
                .log().body()
                .body("error", is("Missing password"));
    }

    @Test
    void successfulLoginWithDataInsideFileTest() {
        String data = readStringFromFile("./src/test/resources/login_data.txt");
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .log().body()
                .body("token", is(notNullValue()));
    }
}