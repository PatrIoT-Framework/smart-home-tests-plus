import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.*;

public class DeviceTest {

    @Test
    public void device_returns_200_with_correct_name_and_address() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device")
            .then()
            .statusCode(200).assertThat()
                .log().all()
                .body("size()",is(4))
                .body("deviceType", hasItems("RGBLight", "Door", "Fireplace", "Thermometer"));
    }

    @Test
    public void device_returns_404_with_incorrect_name() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer2")
            .then()
            .statusCode(404)
            .assertThat()
                .body("status", equalTo("NOT_FOUND"));
    }

    @Test
    public void device_returns_200_for_rgb() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/rgb1")
            .then()
            .statusCode(200).assertThat()
                .body("label", equalTo("rgb1"))
                .body("deviceType", equalTo("RGBLight"))
                .body("enabled", equalTo(false))
                .body("red", instanceOf(Integer.class))
                .body("green", instanceOf(Integer.class))
                .body("blue", instanceOf(Integer.class));
    }

    @Test
    public void device_returns_200_for_door() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/door1")
            .then()
            .statusCode(200).assertThat()
                .body("label", equalTo("door1"))
                .body("deviceType", equalTo("Door"))
                .body("enabled", instanceOf(Boolean.class))
                .body("status", either(equalTo("opened")).or(equalTo("closed")));
    }

    @Test
    public void device_returns_200_for_fireplace() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace1")
            .then()
            .statusCode(200).assertThat()
                .body("label", equalTo("fireplace1"))
                .body("deviceType", equalTo("Fireplace"))
                .body("enabled", equalTo(false))
                .body("status", either(equalTo("extinguished")).or(equalTo("on_fire")));

    }

    @Test
    public  void device_returns_200_for_thermometer() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer1")
            .then()
            .statusCode(200).assertThat()
                .body("label", equalTo("thermometer1"))
                .body("deviceType", equalTo("Thermometer"))
                .body("enabled", equalTo(false))
                .body("unit", equalTo("C"))
                .body("temperature", instanceOf(Float.class));
    }

}
