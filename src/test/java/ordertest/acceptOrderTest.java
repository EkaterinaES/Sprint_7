package ordertest;

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
import order.OrderDetails;
import order.ScooterServiceOrder;
import order.ScooterServiceOrderImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.Matchers.equalTo;

public class acceptOrderTest {
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
    private OrderDetails orderDetails;
    private ScooterServiceOrder orderMethods;
    private final String FIRST_NAME = "Петр";
    private final String LAST_NAME = "Петров";
    private final String ADDRESS = "ул Мира 6";
    private final int METRO_STATION = 4;
    private final String PHONE = "89119119199";
    private final int RENTAL_TIME = 3;
    private final String DELIVERY_DATE = "2024-12-03";
    private final String COMMENT = "Жду";
    private final String[] COLOR = {"", ""};

    @Before
    public void setUp() {
        courier = new Courier(("skorokhod" + new Random().nextInt(300)), "12345", "Peter");
        courierLP = new CourierLoginPasswd(courier.getLogin(), courier.getPassword());
        courierAll = new ScooterServiceCourierImpl(REQUEST_SPECIFICATION);
        orderDetails = new OrderDetails(FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENTAL_TIME, DELIVERY_DATE, COMMENT, COLOR);
        orderMethods = new ScooterServiceOrderImpl(REQUEST_SPECIFICATION);
    }

    @Test
    @DisplayName("Accept order with id_order and id_courier")
    @Description("Send PUT request /api/v1/orders/accept/:id")
    public void acceptSuccessfulTest() {
        //создали курьера
        Response response = courierAll.createCourierTest(courier);
        //залогинились в системе для получения id курьера
        Response responseWithIdCourier = courierAll.loginWithReturnResponse(courierLP);
        //получили id в ответе
        int courierId = responseWithIdCourier.then().extract().body().path("id");
        //String courierId = String.valueOf(courierId);
        System.out.println(courierId);
        //создали заказ
        Response responseCreateOrder = orderMethods.createTheOrder(orderDetails);
        //получили номер заказа
        int orderId = responseCreateOrder.then().extract().body().path("track");
        System.out.println(orderId);
       //приняли заказ по id курьера и id заказа
       Response responseAcceptOrder = orderMethods.acceptOrder(orderId, courierId);
       responseAcceptOrder.then().spec(RESPONSE_SPECIFICATION).assertThat().body("ok", equalTo(true))
              .and()
               .statusCode(200);
    }
}
