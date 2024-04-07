package couriertest;

import courier.CourierLoginPasswd;
import courier.ScooterServiceCourier;
import courier.ScooterServiceCourierImpl;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginNotExistentCourierTest {
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
    private CourierLoginPasswd courierLP;
    private ScooterServiceCourier courierAll;
    private final String login = "NonExistent";
    private final String password = "NonExistent";


    @Before
    public void setUp() {
        courierLP = new CourierLoginPasswd(login, password);
        courierAll = new ScooterServiceCourierImpl(REQUEST_SPECIFICATION);
    }
        @Test
        @DisplayName("Login in system with non-existent login and password")
        @Description("Send POST request to /api/v1/courier/login")
        public void notExistentLoginAndPasswordTest () {
            Response response = courierAll.loginWithReturnResponse(courierLP);
            response.then().spec(RESPONSE_SPECIFICATION).assertThat().body("message", equalTo("Учетная запись не найдена"))
                    .and()
                    .statusCode(404);
        }
    }