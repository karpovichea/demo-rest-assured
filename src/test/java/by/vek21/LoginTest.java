package by.vek21;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest {
    @BeforeAll
    static void beforeAll() {
        LoginRequest.initRequestSpecification();
    }

    @Test
    @DisplayName("Login with unregistered email")
    public void testLoginWithUnregisteredEmail() {
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
    @DisplayName("Login with incorrect password")
    public void testLoginWithIncorrectPassword() {
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
    @DisplayName("Login with empty email and password")
    public void testLoginWithEmptyEmailAndPassword() {
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
    @DisplayName("Login with invalid email format")
    public void testLoginWithInvalidEmailFormat() {
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
    @DisplayName("Login with password of invalid length")
    public void testLoginWithInvalidPasswordFormat() {
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
    @DisplayName("Login with invalid request body")
    public void testLoginWithInvalidBody() {
        String body = "{";

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
