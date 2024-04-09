package ordertest;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import orders.OrderDetails;
import orders.ScooterServiceOrder;
import orders.ScooterServiceOrderImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderTest {
    public static ResponseSpecification responseSpecification =
            new ResponseSpecBuilder()
                    .log(LogDetail.ALL)
                    .build();
    private OrderDetails orderDetails;
    private ScooterServiceOrder orderMethods;
    private final String FIRST_NAME;
    private final String LAST_NAME;
    private final String ADDRESS;
    private final int METRO_STATION;
    private final String PHONE;
    private final int RENTAL_TIME;
    private final String DELIVERY_DATE;
    private final String COMMENT;
    private final String[] COLOR;

    public OrderTest(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, String color) {
        this.FIRST_NAME = firstName;
        this.LAST_NAME = lastName;
        this.ADDRESS = address;
        this.METRO_STATION = metroStation;
        this.PHONE = phone;
        this.RENTAL_TIME = rentTime;
        this.DELIVERY_DATE = deliveryDate;
        this.COMMENT = comment;
        this.COLOR = color.split(",");
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"Петр", "Петров", "Мира ул", 4, "89119119123", 4, "2020-06-06", "Жду", "BLACK, GREY"},
                {"Петр", "Петров", "Мира ул", 4, "89119119123", 4, "2020-06-06", "Жду", "GREY"},
                {"Петр", "Петров", "Мира ул", 4, "89119119123", 4, "2020-06-06", "Жду", "BLACK"},
                {"Петр", "Петров", "Мира ул", 4, "89119119123", 4, "2020-06-06", "Жду", ""},
        };
    }

    @Before
    public void setUp() {
        orderDetails = new OrderDetails(FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENTAL_TIME, DELIVERY_DATE, COMMENT, COLOR);
        orderMethods = new ScooterServiceOrderImpl(ScooterServiceOrderImpl.requestSpecification);

    }

    @Test
    @DisplayName("Checking order creation with color field")
    @Description("Send POST request to /api/v1/orders")
    public void checkTrackInResponse() {
        Response response = orderMethods.createTheOrder(orderDetails);
        response.then().spec(responseSpecification).assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }

    @After public void cleanUp() {//удаление созданного заказа
        Response response = orderMethods.createTheOrder(orderDetails);
        int trackNumber = response.then().extract().body().path("track");
        orderMethods.cancelOrder(trackNumber);
    }

}
