package by.yr;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class LoginRequest {
    public static RequestSpecification requestSpecification;

    public static void initRequestSpecification() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://api.y-r.by")
                .setBasePath("/api/v1/token")
                .setContentType("application/json")
                .addHeader("accept", "application/json")
                .build();
    }

    public static String getBody(String email, String password, Boolean saveFlag) {
        return "{\"email\":\"" + email + "\",\"password\":\"" + password + "\",\"remember\":" + saveFlag + "}";
    }
}
