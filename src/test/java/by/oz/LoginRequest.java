package by.oz;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class LoginRequest {

    public static RequestSpecification requestSpecification;

    public static void initRequestSpecification() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://auth.oz.by/api/v3")
                .setBasePath("/quickSignIn")
                .setContentType("application/json")
                .build();
    }

    public static String getBody(String phone) {
        return "{\"phone\":\"" + phone + "\"}";
    }
}
