package ordertest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import order.ScooterServiceOrder;
import order.ScooterServiceOrderImpl;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {
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
    private ScooterServiceOrder orderMethods;

    @Test
    @DisplayName("Сhecking that the response returns a list of orders")
    @Description("Send GET request to /api/v1/orders")
    public void returnOrderListTest() {
        orderMethods = new ScooterServiceOrderImpl(REQUEST_SPECIFICATION);
        Response response = orderMethods.returnTheListOfOrders();
        response.then().spec(RESPONSE_SPECIFICATION).assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}
