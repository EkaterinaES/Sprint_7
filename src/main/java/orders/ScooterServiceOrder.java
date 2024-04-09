package orders;

import io.restassured.response.Response;

public interface ScooterServiceOrder {
    Response createTheOrder(OrderDetails orderDetails);
    Response cancelOrder(int track);
    Response returnTheListOfOrders();
    Response acceptOrder(int orderId, int courierId);
    DetailsOfThtCreatedOrder receiveOrderByNumber(int track);
}
