package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SelenoidStatusTest {

    @Test
    void successStatusTest() {
        given()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200);
    }

    @Test
    void successStatusWithAuthTest() {
        given()
                .when()
                .get("https://user1:1234@selenoid.autotests.cloud:4444/wd/hub/status")
                .then()
                .statusCode(200);
    }

    @Test
    void successStatusWithBasicAuthTest() {
        given()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200);
    }

    @Test
    void successStatusWithOutGivenWhenTest() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200);
    }

    @Test
    void successStatusResponseTest() {
        Response response = given()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.autotests.cloud:4444/wd/hub/status")
                .then()
                .statusCode(200)
                .extract().response();

        //System.out.println(response);
        System.out.println(response.asString());
    }

    @Test
    void successStatusResponseWithLogTest() {
        given()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.autotests.cloud:4444/wd/hub/status")
                .then()
                .log().body()
                .statusCode(200)
                .extract().response();
    }

    @Test
    void successStatusReadyTest() {
        given()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.autotests.cloud:4444/wd/hub/status")
                .then()
                .log().body()
                .statusCode(200)
                .body("value.ready", is(true));
    }

    @Test
    void successStatusReadyAssertThatTest() {
        Boolean result = given()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.autotests.cloud:4444/wd/hub/status")
                .then()
                .log().body()
                .statusCode(200)
                .extract()
                .path("value.ready");

        assertThat(result, is(true));
    }

    @Test
    void successStatusReadyAssertThatResultTest() {
        Boolean result = given()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.autotests.cloud:4444/wd/hub/status")
                .then()
                .log().body()
                .statusCode(200)
                .extract()
                .path("value.ready");

        assertThat(result, is(true));
    }
}