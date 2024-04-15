package courier;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestLogSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;


public class ScooterServiceCourierImpl implements ScooterServiceCourier {
    public static RequestSpecification requestSpecification =
            new RequestSpecBuilder()
                    .log(LogDetail.ALL)
                    .addHeader("Content-type", "application/json")
                    .setBaseUri("https://qa-scooter.praktikum-services.ru/")
                    .build();

    public ScooterServiceCourierImpl(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    private static final String CREATE_USER_ENDPOINT = "/api/v1/courier";
    private static final String LOGIN_COURIER_ENDPOINT = "/api/v1/courier/login";
    private static final String DELETE_COURIER_ENDPOINT = "/api/v1/courier/{id}";

    private static final String BATH_PATH = "https://qa-scooter.praktikum-services.ru/";


    @Override
    @Step("Send POST request to /api/v1/courier")
    public Response createCourierTest(Courier courier) {
        return given()
//                .log().all()
//                .header("Content-type", "application/json")
                //.baseUri(BATH_PATH)
                .spec(requestSpecification)
                .body(courier)
                .post(CREATE_USER_ENDPOINT);
    }


    @Override
    @Step("Send POST request to /api/v1/courier/login")
    public Response loginWithReturnResponse(CourierLoginPasswd courierLP) {
        return given()
//                .log().all()
//                .header("Content-type", "application/json")
//                .baseUri(BATH_PATH)
                .spec(requestSpecification)
                .body(courierLP)
                .post(LOGIN_COURIER_ENDPOINT);
    }

    @Override
    @Step("Delete courier with id. Send DELETE request to /api/v1/courier/:id")
    public Response deleteCourierTest(String id) {
        return given()
//                .log().all()
//                .header("Content-type", "application/json")
//                .baseUri(BATH_PATH)
                .spec(requestSpecification)
                .delete(DELETE_COURIER_ENDPOINT, id);
//            response.then().log().all().assertThat().body("ok", equalTo(true))
//                    .and()
//                    .statusCode(200);
    }
}
