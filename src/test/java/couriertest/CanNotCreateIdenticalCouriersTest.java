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

import static org.hamcrest.Matchers.equalTo;

public class CanNotCreateIdenticalCouriersTest {
    public static ResponseSpecification responseSpecification =
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
        courierAll = new ScooterServiceCourierImpl(ScooterServiceCourierImpl.requestSpecification);
    }
    @Test
    @DisplayName("Do not create couriers with the same login")
    @Description("Send POST request to /api/v1/courier")
    public void theSameLogin(){//этот тест падает, так как тест ошибки не соответствует тексту в документации
        courierAll.createCourierTest(courier);
        Response response = courierAll.createCourierTest(courier);
        response.then().spec(responseSpecification).assertThat().body("message", equalTo("Этот логин уже используется"))
                .and()
                .statusCode(409);
    }
    @After
    public void cleanUp() {
        Response response = courierAll.loginWithReturnResponse(courierLP);
        int id = response.then().extract().body().path("id");
        String cId = String.valueOf(id);
        courierAll.deleteCourierTest(cId);
    }
}
