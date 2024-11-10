package by.oz;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTest {
    @BeforeAll
    static void beforeAll() {
        LoginRequest.initRequestSpecification();
    }

    @Test
    public void testRejectPhoneWithIncorrectCode() {
        String invalidPhone = "375112000000";

        given()
                .spec(LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(invalidPhone))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(422)
                .body("message", equalTo("Введите корректный номер мобильного телефона"));
    }

    @Test
    public void testRejectPhoneWithCorrectCodeButIncorrectLength() {
        String invalidPhone = "3752910010";

        given()
                .spec(LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(invalidPhone))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(422)
                .body("message", equalTo("Введите номер мобильного телефона белорусских операторов"));
    }

    @Test
    public void testRejectEmptyPhone() {
        String invalidPhone = "";

        given()
                .spec(LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(invalidPhone))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(422)
                .body("message", equalTo("Введите номер мобильного телефона белорусских операторов"));
    }

    @Test
    public void testRejectPhoneWithInvalidSymbols() {
        String invalidPhone = "37529%123456";

        given()
                .spec(LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(invalidPhone))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(422)
                .body("message", equalTo("Введите номер мобильного телефона белорусских операторов"));
    }

    @Test
    public void testRejectRequestWithInvalidBody() {
        String invalidBody = "";

        given()
                .spec(LoginRequest.requestSpecification)
                .body(invalidBody)
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(500)
                .body("error", equalTo("Произошла ошибка на сервисе авторизации."));
    }

    @Test
    public void testSendSmsCodeWithValidPhone() {
        String validPhone = "375293000000";

        given()
                .spec(LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(validPhone))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(201)
                .body("token", notNullValue());
    }
}
