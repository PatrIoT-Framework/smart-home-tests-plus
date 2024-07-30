import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class RGBTest {

    @Test
    public void rgb_get_returns_200_with_correct_label_and_type() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200).assertThat()
                .body("label", equalTo("rgb1"))
                .body("enabled", equalTo(false))
                .body("red", instanceOf(Integer.class))
                .body("green", instanceOf(Integer.class))
                .body("blue", instanceOf(Integer.class));
    }

    @Test
    public void rgb_get_returns_404_with_incorrect_label() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb2")
                .then()
                .log().all()
                .log().body()
                .statusCode(404)
                .assertThat()
                .body("status", equalTo("NOT_FOUND"));
    }

    @Test
    public void rgb_get_returns_404_with_incorrect_devType() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/RGBrrr/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void rgb_get_returns_404_with_empty_label() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void update_rgb_returns_200_with_correct_label_and_devType() {
        Map<String, Object> rgb = Map.of("label", "rgb1", "deviceType", "RGBLight", "enabled", true, "red", 5);

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("label", equalTo("rgb1"))
                .body("enabled", equalTo(true));

        get("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("label", equalTo("rgb1"))
                .body("enabled", equalTo(true))
                .body("red", equalTo(5));

        rgb = Map.of("label", "rgb1","deviceType","RGBLight", "enabled", false, "blue", 99);

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("label", equalTo("rgb1"))
                .body("enabled", equalTo(false))
                .body("blue", equalTo(99));

        get("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("label", equalTo("rgb1"))
                .body("enabled", equalTo(false))
                .body("blue", equalTo(99));
    }

    @Test
    public void update_rgb_returns_404_with_incorrect_label() {
        Map<String, Object> rgb = Map.of("label", "rgb2", "deviceType", "RGBLight", "enabled", true );

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb2")
                .then()
                .log().all()
                .log().body()
                .statusCode(404)
                .assertThat()
                .body("status", equalTo("NOT_FOUND"));
    }

    @Test
    public void update_rgb_returns_400_with_incorrect_body() {
        Map<String, Object> rgb = Map.of("deviceType", "RGBLight", "enabled", true);

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void update_rgb_returns_404_with_incorrect_devType() {
        Map<String, Object> rgb = Map.of("label", "rgb1", "deviceType", "rgb", "enabled", true );

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/RGBrrr/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void update_rgb_returns_404_with_empty_label() {
        Map<String, Object> rgb = Map.of("label", "", "deviceType", "RGBLight", "enabled", true );

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void update_rgb_returns_400_with_red_out_of_range() {
        Map<String, Object> rgb = Map.of("label", "rgb1", "deviceType", "RGBLight", "enabled", true, "red", 256);

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);

        rgb = Map.of("label", "rgb1", "deviceType", "RGBLight", "enabled", true, "red", -1);

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }

    @Test
    public  void update_rgb_returns_400_with_wrong_red_type() {
        Map<String, Object> rgb = Map.of("label", "rgb1", "deviceType", "RGBLight", "enabled", true, "red", "red");

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void update_rgb_returns_400_with_green_out_of_range() {
        Map<String, Object> rgb = Map.of("label", "rgb1", "deviceType", "RGBLight", "enabled", true, "green", 256);

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);

        rgb = Map.of("label", "rgb1", "deviceType", "RGBLight", "enabled", true, "green", -1);

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void update_rgb_returns_400_with_wrong_green_type() {
        Map<String, Object> rgb = Map.of("label", "rgb1", "deviceType", "RGBLight", "enabled", true, "green", "green");

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void update_rgb_returns_400_with_blue_out_of_range() {
        Map<String, Object> rgb = Map.of("label", "rgb1", "deviceType", "RGBLight", "enabled", true, "blue", 256);

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);

        rgb = Map.of("label", "rgb1", "deviceType", "RGBLight", "enabled", true, "blue", -1);

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void update_rgb_returns_400_with_wrong_blue_type() {
        Map<String, Object> rgb = Map.of("label", "rgb1", "deviceType", "RGBLight", "enabled", true, "blue", "blue");

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void update_rgb_returns_400_with_enabled_not_boolean() {
        Map<String, Object> rgb = Map.of("label", "rgb1", "deviceType", "RGBLight", "enabled", "true", "red", 5);

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void update_rgb_returns_400_with_switchedOn_not_boolean() {
        Map<String, Object> rgb = Map.of("label", "rgb1", "deviceType", "RGBLight", "enabled", true, "red", 5, "switchedOn", "true");

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void update_rgb_returns_400_with_devType_not_string() {
        Map<String, Object> rgb = Map.of("label", "rgb1", "deviceType", 1, "enabled", true, "red", 5);

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void update_rgb_returns_400_with_label_not_string() {
        Map<String, Object> rgb = Map.of("label", 1, "deviceType", "RGBLight", "enabled", true, "red", 5);

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void update_rgb_returns_400_with_red_not_number() {
        Map<String, Object> rgb = Map.of("label", "rgb1", "deviceType", "RGBLight", "enabled", true, "red", "red");

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void update_rgb_returns_400_with_green_not_number() {
        Map<String, Object> rgb = Map.of("label", "rgb1", "deviceType", "RGBLight", "enabled", true, "green", "green");

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void update_rgb_returns_400_with_blue_not_number() {
        Map<String, Object> rgb = Map.of("label", "rgb1", "deviceType", "RGBLight", "enabled", true, "blue", "blue");

        with().body(rgb)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
    }


    @Test
    public void rgb_post_returns_404() {
        Map<String, Object> rgb = Map.of("label", "rgb1", "deviceType", "RGBLight", "enabled", true);

        with().body(rgb)
                .when().post("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void rgb_delete_returns_404() {
        with()
                .when().delete("http://localhost:8080/api/v0.1/gateway/house1/device/rgb/rgb1")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }


}
