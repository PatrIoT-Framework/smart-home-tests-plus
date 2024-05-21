import org.junit.jupiter.api.Test;

import java.net.PortUnreachableException;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class FireplaceTest {

    @Test
    public void fireplace_get_returns_200_with_correct_label_and_type() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/fireplace1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200).assertThat()
                .body("label", equalTo("fireplace1"))
                .body("enabled", equalTo(false))
                .body("status", equalTo("extinguished"));
    }

    @Test
    public void fireplace_get_returns_404_with_incorrect_label() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/fireplace2")
                .then()
                .log().all()
                .log().body()
                .statusCode(404)
                .assertThat()
                .body("status", equalTo("NOT_FOUND"));
    }

    @Test
    public void fireplace_get_returns_404_with_incorrect_devType() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/Fireplacerrr/fireplace1")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void fireplace_get_returns_404_with_empty_label() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void update_fireplace_returns_200_with_correct_label_and_devType() {
        Map<String, Object> fireplace = Map.of("label","fireplace1","deviceType","Fireplace" ,"enabled",true, "status", "on_fire");

        with().body(fireplace)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/fireplace1")
                .then()
                .log().all()
                .statusCode(200).assertThat()
                .body("label", equalTo("fireplace1"))
                .body("enabled", equalTo(true))
                .body("status", equalTo("on_fire"));

        get("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/fireplace1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200).assertThat()
                .body("label", equalTo("fireplace1"))
                .body("enabled", equalTo(true))
                .body("status", equalTo("on_fire"));

        fireplace= Map.of("label", "fireplace1","deviceType","Fireplace", "enabled", false, "status", "extinguished");

        with().body(fireplace)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/fireplace1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200).assertThat()
                .body("label", equalTo("fireplace1"))
                .body("enabled", equalTo(false))
                .body("status", equalTo("extinguished"));

        get("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/fireplace1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200).assertThat()
                .body("label", equalTo("fireplace1"))
                .body("enabled", equalTo(false))
                .body("status", equalTo("extinguished"));
    }

    @Test
    public void update_fireplace_returns_200_with_minimal_body() {
        Map<String, Object> fireplace = Map.of("label","fireplace1","deviceType","Fireplace");

        with().body(fireplace)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/fireplace1")
                .then()
                .log().all()
                .statusCode(200).assertThat()
                .body("label", equalTo("fireplace1"))
                .body("enabled", equalTo(false))
                .body("status", equalTo("extinguished"));

        get("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/fireplace1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200).assertThat()
                .body("label", equalTo("fireplace1"))
                .body("enabled", equalTo(false))
                .body("status", equalTo("extinguished"));
    }

    @Test
    public void update_fireplace_returns_404_with_incorrect_label() {
        Map<String, Object> fireplace = Map.of("label","fireplace2","deviceType","Fireplace" ,"enabled",true);

        with().body(fireplace)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/fireplace2")
                .then()
                .log().all()
                .log().body()
                .statusCode(404)
                .assertThat()
                .body("status", equalTo("NOT_FOUND"));
    }

    @Test
    public void update_fireplace_returns_404_with_incorrect_devType() {
        Map<String, Object> fireplace = Map.of("label","fireplace1","deviceType","Fireplace" ,"enabled",true);

        with().body(fireplace)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/fireplaceeee/fireplace1")
                .then()
                .log().all()
                .log().body()
                .statusCode(404)
                .assertThat()
                .body("error", equalTo("Not Found"));
    }

    @Test
    public void update_fireplace_returns_404_with_empty_label() {
        Map<String, Object> fireplace = Map.of("label","","deviceType","Fireplace" ,"enabled",true);

        with().body(fireplace)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void update_fireplace_returns_400_with_incorrect_status_type() {
        Map<String, Object> fireplace = Map.of("label","fireplace1","deviceType","Fireplace" ,"enabled",true, "status", 1);

        with().body(fireplace)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/fireplace1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400)
                .assertThat();
    }

    @Test
    public void update_fireplace_returns_400_with_incorrect_status() {
        Map<String, Object> fireplace = Map.of("label","fireplace1","deviceType","Fireplace" ,"enabled",true, "status", "on_fireee");

        with().body(fireplace)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/fireplace1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400)
                .assertThat();
    }

    @Test
    public void update_fireplace_returns_400_with_incorrect_enabled_type() {
        Map<String, Object> fireplace = Map.of("label","fireplace1","deviceType","Fireplace" ,"enabled", 1, "status", "on_fire");

        with().body(fireplace)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/fireplace1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400)
                .assertThat();
    }

   @Test
   public void update_fireplace_returns_400_with_incorrect_label_type() {
        Map<String, Object> fireplace = Map.of("label",1,"deviceType","Fireplace" ,"enabled",true, "status", "on_fire");

        with().body(fireplace)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/fireplace1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400)
                .assertThat();
    }

    @Test
    public void update_fireplace_returns_400_with_incorrect_devType_type() {
        Map<String, Object> fireplace = Map.of("label","fireplace1","deviceType",1 ,"enabled",true, "status", "on_fire");

        with().body(fireplace)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/fireplace1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400)
                .assertThat();
    }

    @Test
    public void update_fireplace_returns_400_with_empty_body() {
        Map<String, Object> fireplace = Map.of();

        with().body(fireplace)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/fireplace1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400)
                .assertThat();
   }


    @Test
    public void fireplace_post_returns_404() {
        Map<String, Object> fireplace = Map.of("label","fireplace1","deviceType","Fireplace" ,"enabled",true);

        with().body(fireplace)
                .when().post("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/fireplace1")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void fireplace_delete_returns_404() {
        with()
                .when().delete("http://localhost:8080/api/v0.1/gateway/house1/device/fireplace/fireplace1")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }


}
