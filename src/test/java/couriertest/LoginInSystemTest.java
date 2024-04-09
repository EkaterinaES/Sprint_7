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
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginInSystemTest {
    public static ResponseSpecification responseSpecification =
            new ResponseSpecBuilder()
                    .log(LogDetail.ALL)
                    .build();
    private Courier courier;
    private CourierLoginPasswd courierLP;
    private ScooterServiceCourier courierAll;
    private String courierId;

    @Before
    public void setUp() {
        courier = new Courier(("skorokhod" + new Random().nextInt(300)), "12345", "Peter");
        courierLP = new CourierLoginPasswd(courier.getLogin(), courier.getPassword());
        courierAll = new ScooterServiceCourierImpl(ScooterServiceCourierImpl.requestSpecification);
    }

    @Test
    @DisplayName("Login in system and get id")
    @Description("Send POST request to /api/v1/courier/login")
    public void loginTest() {//успешная авторизация возвращает id и код 200
        courierAll.createCourierTest(courier);
        Response response = courierAll.loginWithReturnResponse(courierLP);
        response.then().spec(responseSpecification).assertThat().body("id", notNullValue())//проверили, что id заполнен
                .and()
                .statusCode(200);
        int id = response.then().extract().body().path("id");//получили id из ответа
        this.courierId = String.valueOf(id);
    }

    @After
    public void cleanUp() {
        courierAll.deleteCourierTest(courierId);
    }
}
