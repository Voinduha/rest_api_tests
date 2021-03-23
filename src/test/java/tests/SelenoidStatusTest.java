package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class SelenoidStatusTest {

    @Test
    void successStatusTest() {
        given()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200);
    }
}
