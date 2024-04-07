package couriertest;

import courier.Courier;
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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.equalTo;
@RunWith(Parameterized.class)
public class CannotBeCreatedWithoutLoginTest {
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
    private ScooterServiceCourier courierAll;
    private final String LOGIN;
    private final String PASSWORD;
    private final String FIRST_NAME;

    public CannotBeCreatedWithoutLoginTest(String LOGIN, String PASSWORD, String FIRST_NAME) {
        this.LOGIN = LOGIN;
        this.PASSWORD = PASSWORD;
        this.FIRST_NAME = FIRST_NAME;
    }

    @Before
    public void setUp() {
        courierAll = new ScooterServiceCourierImpl(REQUEST_SPECIFICATION);
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"", "12345", "Петр"},
                {"Peter", "", "Петр"},
                {"", "", "Петр"},
        };
    }
    @Test
    @DisplayName("Сreating a courier without login and/or without password")
    @Description("Send POST request to /api/v1/courier")
    public void withoutLoginTest() {
        courier = new Courier(LOGIN, PASSWORD, FIRST_NAME);
        Response response = courierAll.createCourierTest(courier);
        response.then().spec(RESPONSE_SPECIFICATION).assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
}