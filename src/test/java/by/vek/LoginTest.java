package by.vek;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest {
    private static RequestSpecification requestSpecification;

    @BeforeAll
    static void beforeAll() {
        LoginRequest.initRequestSpecification();
    }

    @Test
    public void testLoginWithUnregisteredData() {
        String email = "emailunregistered@gmail.com";
        String password = "2345678";

        given()
                .spec(LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(email, password))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(200)
                .body("error", equalTo("Проверьте email"));
    }

    @Test
    public void testLoginWithWrongData() {
        String email = "email@gmail.com";
        String password = "111111";

        given()
                .spec(LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(email, password))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(200)
                .body("error", equalTo("Неправильный пароль"));
    }

    @Test
    public void testLoginWithEmptyData() {
        String email = "";
        String password = "";

        given()
                .spec(LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(email, password))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    public void testLoginWithInvalidEmail() {
        String email = "email.gmail.com";
        String password = "111111";

        given()
                .spec(LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(email, password))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(200)
                .body("error", equalTo("Ошибка валидации поля email"));
    }

    @Test
    public void testLoginWithInvalidPassword() {
        String email = "email@gmail.com";
        String password = "!!!12";

        given()
                .spec(LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(email, password))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(200)
                .body("error", equalTo("Длина поля password должна быть от 6 до 32 символов"));
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
                .statusCode(404);
    }
}
