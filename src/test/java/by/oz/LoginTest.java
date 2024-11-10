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
    public void testLoginWithIncorrectPhoneCode() {
        String phone = "375112000000";

        given()
                .spec(LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(phone))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(422)
                .body("message", equalTo("Введите корректный номер мобильного телефона"));
    }

    @Test
    public void testLoginWithCorrectCodeButIncorrectPhoneLength() {
        String phone = "3752910010";

        given()
                .spec(LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(phone))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(422)
                .body("message", equalTo("Введите номер мобильного телефона белорусских операторов"));
    }

    @Test
    public void testLoginWithEmptyPhone() {
        String phone = "";

        given()
                .spec(LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(phone))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(422)
                .body("message", equalTo("Введите номер мобильного телефона белорусских операторов"));
    }

    @Test
    public void testLoginWithInvalidSymbolsInPhone() {
        String phone = "37529%123456";

        given()
                .spec(LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(phone))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(422)
                .body("message", equalTo("Введите номер мобильного телефона белорусских операторов"));
    }

    @Test
    public void testLoginWithInvalidBody() {
        String body = "";

        given()
                .spec(LoginRequest.requestSpecification)
                .body(body)
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(500)
                .body("error", equalTo("Произошла ошибка на сервисе авторизации."));
    }

    @Test
    public void testLoginWithValidPhone() {
        String phone = "375293000000";

        given()
                .spec(LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(phone))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(201)
                .body("token", notNullValue());
    }
}
