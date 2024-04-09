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
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.Matchers.equalTo;

public class DeleteCourierTest {
    public static ResponseSpecification responseSpecification =
            new ResponseSpecBuilder()
                    .log(LogDetail.ALL)
                    .build();
    private Courier courier;
    private CourierLoginPasswd courierLP;
    private ScooterServiceCourier courierAll;
    private String nonExistentId = "657654";
    private String withoutId = "";

    @Before
    public void setUp() {
        courier = new Courier(("skorokhod" + new Random().nextInt(300)), "12345", "Peter");
        courierLP = new CourierLoginPasswd(courier.getLogin(), courier.getPassword());
        courierAll = new ScooterServiceCourierImpl(ScooterServiceCourierImpl.requestSpecification);
    }

    @Test
    @DisplayName("Delete a courier")
    @Description("Send DELETE request to /api/v1/courier/:id")
    public void DeleteCourierSuccessful() {
        courierAll.createCourierTest(courier);
        Response response = courierAll.loginWithReturnResponse(courierLP);
        int id = response.then().extract().body().path("id");
        String cId = String.valueOf(id);
        Response responseForDelete = courierAll.deleteCourierTest(cId);
        responseForDelete.then().spec(responseSpecification).assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Delete a courier with non-existent id")
    @Description("Send DELETE request to /api/v1/courier/:id")
    public void DeleteCourierWithNonExistentId() {
        Response responseForDelete = courierAll.deleteCourierTest(nonExistentId);
        responseForDelete.then().spec(responseSpecification).assertThat().body("message", equalTo("Курьера с таким id нет."))
                .and()
                .statusCode(404);
    }
    @Test
    @DisplayName("Delete a courier without id")
    @Description("Send DELETE request to /api/v1/courier/:id")
    public void DeleteCourierWithoutId() {//тест падает, так как: ОР: код 400 Bad Request. и текст "Недостаточно данных для удаления курьера", а ФР:"code": 404, Not Found.
        Response response = courierAll.deleteCourierTest(withoutId);
        response.then().spec(responseSpecification).assertThat().body("message", equalTo("Недостаточно данных для удаления курьера"))
                .and()
                .statusCode(400);
    }
}
