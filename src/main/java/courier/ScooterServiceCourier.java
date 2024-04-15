package courier;

import io.restassured.response.Response;

public interface ScooterServiceCourier {
    Response createCourierTest(Courier courier);
//    String loginTest(CourierLoginPasswd courierLP);
    Response loginWithReturnResponse(CourierLoginPasswd courierLP);
    Response deleteCourierTest(String id);
}
