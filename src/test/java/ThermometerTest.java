import groovy.xml.StreamingDOMBuilder;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class ThermometerTest {

    @Test
    public void thermometer_get_returns_200_with_correct_label_and_type() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/thermometer1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200).assertThat()
                .body("label", equalTo("thermometer1"))
                .body("unit", equalTo("C"))
                .body("enabled", equalTo(false))
                .body("temperature", instanceOf(Float.class));
    }

    @Test
    public void thermometer_get_returns_404_with_incorrect_label() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/thermometer2")
                .then()
                .log().all()
                .log().body()
                .statusCode(404)
                .assertThat()
                .body("status", equalTo("NOT_FOUND"));
    }

    @Test
    public void thermometer_get_returns_404_with_incorrect_devType() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/Thermometerrrr/thermometer1")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }


    @Test
    public void thermometer_get_returns_404_with_empty_label() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void update_thermometer_returns_200_with_correct_label_and_devType() {
        Map<String, Object> thermometer = Map.of("label","thermometer1","deviceType","Thermometer" ,"unit", "F", "enabled",true);

        with().body(thermometer)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/thermometer1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200).assertThat()
                .body("label", equalTo("thermometer1"))
                .body("unit", equalTo("F"))
                .body("enabled", equalTo(true))
                .body("temperature", instanceOf(Float.class));

        get("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/thermometer1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200).assertThat()
                .body("label", equalTo("thermometer1"))
                .body("unit", equalTo("F"))
                .body("enabled", equalTo(true))
                .body("temperature", instanceOf(Float.class));

        thermometer= Map.of("label", "thermometer1", "deviceType", "Thermometer","unit", "C", "enabled", false);

        with().body(thermometer)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/thermometer1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200).assertThat()
                .body("label", equalTo("thermometer1"))
                .body("unit", equalTo("C"))
                .body("enabled", equalTo(false))
                .body("temperature", instanceOf(Float.class));

        get("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200).assertThat()
                .body("label", equalTo("thermometer1"))
                .body("unit", equalTo("C"))
                .body("enabled", equalTo(false))
                .body("temperature", instanceOf(Float.class));
    }

    @Test
    public void update_thermometer_returns_200_with_minimal_body() {
        Map<String, Object> thermometer = Map.of("label","thermometer1","deviceType","Thermometer");

        with().body(thermometer)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/thermometer1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200).assertThat()
                .body("label", equalTo("thermometer1"))
                .body("unit", equalTo("C"))
                .body("enabled", equalTo(false))
                .body("temperature", instanceOf(Float.class));

        get("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/thermometer1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200).assertThat()
                .body("label", equalTo("thermometer1"))
                .body("unit", equalTo("C"))
                .body("enabled", equalTo(false))
                .body("temperature", instanceOf(Float.class));
    }


    @Test
    public void thermometer_update_returns_404_with_incorrect_label() {
        Map<String, Object> thermometer = Map.of("label","thermometer2","deviceType","Thermometer","unit", "F", "enabled",false);

        with().body(thermometer)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/thermometer2")
                .then()
                .log().all()
                .log().body()
                .statusCode(404)
                .assertThat()
                .body("status", equalTo("NOT_FOUND"));
    }

    @Test
    public void thermometer_update_returns_400_with_incorrect_body() {
        Map<String, Object> thermometer = Map.of("deviceType","Thermometer" ,"unit", "F", "enabled",false);

        with().body(thermometer)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/thermometer1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400)
                .assertThat();
    }

    @Test
    public void thermometer_updates_returns_400_with_wrong_unit() {
        Map<String, Object> thermometer = Map.of("label","thermometer1","deviceType","Thermometer" ,"unit", 1, "enabled",false);

        with().body(thermometer)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/thermometer1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400)
                .assertThat();
    }

    @Test
    public void thermometer_update_returns_404_with_incorrect_devType() {
        Map<String, Object> thermometer = Map.of("label","thermometer1","deviceType","Thermometer" ,"unit", "F", "enabled",false);

        with().body(thermometer)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/thermometerr/thermometer1")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void thermometer_update_returns_404_with_empty_label() {
        Map<String, Object> thermometer = Map.of("unit", "F", "enabled",false);

        with().body(thermometer)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }


    @Test
    public void thermometer_update_returns_400_with_wrong_unit_type() {
        Map<String, Object> thermometer = Map.of("label","thermometer1","deviceType","Thermometer" ,"unit", 1, "enabled",false);

        with().body(thermometer)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/thermometer1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void thermometer_update_returns_400_with_wrong_enabled_type() {
        Map<String, Object> thermometer = Map.of("label","thermometer1","deviceType","Thermometer" ,"unit", "F", "enabled","true");

        with().body(thermometer)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/thermometer1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void thermometer_update_returns_400_with_wrong_temperature_type() {
        Map<String, Object> thermometer = Map.of("label","thermometer1","deviceType","Thermometer" ,"unit", "F", "enabled",true, "temperature", "25.0");

        with().body(thermometer)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/thermometer1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }



    @Test
    public void thermometer_update_returns_400_with_wrong_label_type() {
        Map<String, Object> thermometer = Map.of("label",1,"deviceType","Thermometer" ,"unit", "F", "enabled",true);

        with().body(thermometer)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/thermometer1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void thermometer_update_returns_400_with_wrong_devType_type() {
        Map<String, Object> thermometer = Map.of("label","thermometer1","deviceType",1 ,"unit", "F", "enabled",true);

        with().body(thermometer)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/thermometer1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void thermometer_post_returns_404() {
        Map<String, Object> thermometer = Map.of("label","thermometer1","deviceType","Tshermometer" ,"unit", "F", "enabled",true);

        with().body(thermometer)
                .when().post("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/thermometer1")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void thermometer_delete_returns_404() {
        with()
                .when().delete("http://localhost:8080/api/v0.1/gateway/house1/device/thermometer/thermometer1")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }




}
