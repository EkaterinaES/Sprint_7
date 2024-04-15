package ordertest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import orders.ScooterServiceOrder;
import orders.ScooterServiceOrderImpl;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {
    public static ResponseSpecification responseSpecification =
            new ResponseSpecBuilder()
                    .log(LogDetail.ALL)
                    .build();
    private ScooterServiceOrder orderMethods;

    @Test
    @DisplayName("Сhecking that the response returns a list of orders")
    @Description("Send GET request to /api/v1/orders")
    public void returnOrderListTest() {
        orderMethods = new ScooterServiceOrderImpl(ScooterServiceOrderImpl.requestSpecification);
        Response response = orderMethods.returnTheListOfOrders();
        response.then().spec(responseSpecification).assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}
