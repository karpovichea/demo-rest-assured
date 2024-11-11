package by.yr;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest {
    @BeforeAll
    static void beforeAll() {
        by.yr.LoginRequest.initRequestSpecification();
    }

    @Test
    @DisplayName("Login with unregistered email")
    public void testLoginWithUnregisteredEmail() {
        String email = "emailunregistered@gmail.com";
        String password = "2345678";
        boolean saveFlag = false;

        given()
                .spec(by.yr.LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(email, password, saveFlag))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(401)
                .body("message", equalTo("Проверьте корректность введенных данных"));
    }

    @Test
    @DisplayName("Login with incorrect password")
    public void testLoginWithIncorrectPassword() {
        String email = "kattykarpovich55@gmail.com";
        String password = "2345678";
        boolean saveFlag = true;

        given()
                .spec(by.yr.LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(email, password, saveFlag))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(401)
                .body("message", equalTo("Проверьте корректность введенных данных"));
    }

    @Test
    @DisplayName("Login with invalid email format")
    public void testLoginWithInvalidEmailFormat() {
        String email = "gmail.com";
        String password = "2345678";
        boolean saveFlag = true;

        given()
                .spec(by.yr.LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(email, password, saveFlag))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(422)
                .body("errors.email[0]", equalTo("Значение поля электронная почта должно быть действительным электронным адресом."));
    }

    @Test
    @DisplayName("Login with password of invalid length")
    public void testLoginWithInvalidPasswordFormat() {
        String email = "email@gmail.com";
        String password = "1";
        boolean saveFlag = true;

        given()
                .spec(by.yr.LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(email, password, saveFlag))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(422)
                .body("errors.password[0]", equalTo("Количество символов в поле пароль должно быть не меньше 6."));
    }

    @Test
    @DisplayName("Login with empty password")
    public void testLoginWithEmptyPassword() {
        String email = "email@gmail.com";
        String password = "";
        boolean saveFlag = true;

        given()
                .spec(by.yr.LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(email, password, saveFlag))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(422)
                .body("errors.password[0]", equalTo("Поле пароль обязательно для заполнения."));
    }

    @Test
    @DisplayName("Login with empty email")
    public void testLoginWithEmptyEmail() {
        String email = "";
        String password = "123456";
        boolean saveFlag = true;

        given()
                .spec(by.yr.LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(email, password, saveFlag))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(422)
                .body("errors.email[0]", equalTo("Поле электронная почта обязательно для заполнения."));
    }

    @Test
    @DisplayName("Login with empty email and password")
    public void testLoginWithEmptyEmailAndPassword() {
        String email = "";
        String password = "";
        boolean saveFlag = true;

        given()
                .spec(by.yr.LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(email, password, saveFlag))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(422)
                .body("errors.email[0]", equalTo("Поле электронная почта обязательно для заполнения."))
                .body("errors.password[0]", equalTo("Поле пароль обязательно для заполнения."));
    }

    @Test
    @DisplayName("Login with null data")
    public void testLoginWithNullData() {

        given()
                .spec(by.yr.LoginRequest.requestSpecification)
                .body(LoginRequest.getBody(null, null, null))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(422)
                .body("errors.email[0]", equalTo("Значение поля электронная почта должно быть действительным электронным адресом."))
                .body("errors.password[0]", equalTo("Количество символов в поле пароль должно быть не меньше 6."));
    }

    @Test
    @DisplayName("Login with invalid request body")
    public void testLoginWithInvalidBody() {
        String body = "{";

        given()
                .spec(by.yr.LoginRequest.requestSpecification)
                .body(body)
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(422)
                .body("errors.email[0]", equalTo("Поле электронная почта обязательно для заполнения."))
                .body("errors.password[0]", equalTo("Поле пароль обязательно для заполнения."));
    }

    @Test
    @DisplayName("Login with a missing header")
    public void testLoginWithMissingHeader() {
        RequestSpecification requestSpecificationWithMissingHeader = new RequestSpecBuilder()
                .setBaseUri("https://api.y-r.by")
                .setBasePath("/api/v1/token")
                .setContentType("application/json")
                .build();

        String email = "emailunregistered@gmail.com";
        String password = "2345678";
        boolean saveFlag = false;

        given()
                .spec(requestSpecificationWithMissingHeader)
                .body(LoginRequest.getBody(email, password, saveFlag))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(302);
    }

    @Test
    @DisplayName("Login with a missing contentType")
    public void testLoginWithMissingContentType() {
        RequestSpecification requestSpecificationWithMissingHeader = new RequestSpecBuilder()
                .setBaseUri("https://api.y-r.by")
                .setBasePath("/api/v1/token")
                .addHeader("accept", "application/json")
                .build();

        String email = "emailunregistered@gmail.com";
        String password = "2345678";
        boolean saveFlag = false;

        given()
                .spec(requestSpecificationWithMissingHeader)
                .body(LoginRequest.getBody(email, password, saveFlag))
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(422)
                .body("errors.email[0]", equalTo("Поле электронная почта обязательно для заполнения."))
                .body("errors.password[0]", equalTo("Поле пароль обязательно для заполнения."));
    }
}
