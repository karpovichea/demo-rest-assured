package by.vek;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class LoginRequest {
    public static RequestSpecification requestSpecification;

    public static void initRequestSpecification() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://m.21vek.by")
                .setBasePath("/users/action/login/")
                .setContentType("application/json")
                .addHeader("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Mobile Safari/537.36")
                .build();
    }

    public static String getBody(String email, String password) {
        return "{\"User\":{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}}";
    }
}
