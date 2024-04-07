package couriertest;

import courier.Courier;
import courier.CourierLoginPasswd;
import courier.ScooterServiceCourier;
import courier.ScooterServiceCourierImpl;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestLogSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.in;

public class CreatingCourierTest {
    private static final RequestSpecification REQUEST_SPECIFICATION =
            new RequestSpecBuilder()
                    .log(LogDetail.ALL)
                    .addHeader("Content-type", "application/json")
                    .setBaseUri("https://qa-scooter.praktikum-services.ru/")
                    .build();
    private static final ResponseSpecification RESPONSE_SPECIFICATION =
            new ResponseSpecBuilder()
                    .log(LogDetail.ALL)
                    .build();
    private Courier courier;
    private CourierLoginPasswd courierLP;
    private ScooterServiceCourier courierAll;
    @Before
    public void setUp() {
        courier = new Courier(("skorokhod" + new Random().nextInt(300)), "12345", "Peter");
        courierLP = new CourierLoginPasswd(courier.getLogin(), courier.getPassword());
        courierAll = new ScooterServiceCourierImpl(REQUEST_SPECIFICATION);
    }

    @Test
    @DisplayName("Сreating a courier")
    @Description("Send POST request to /api/v1/courier")
    public void createCourierTest() {
        Response response = courierAll.createCourierTest(courier);
        response.then().spec(RESPONSE_SPECIFICATION).assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

    @After
    public void cleanUp() {
        Response response = courierAll.loginWithReturnResponse(courierLP);
        int id = response.then().extract().body().path("id");
        String cId = String.valueOf(id);
        courierAll.deleteCourierTest(cId);
    }
}
