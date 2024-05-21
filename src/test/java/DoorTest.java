import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class DoorTest {

    @Test
    public void door_get_returns_200_with_correct_label_and_type() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/door/door1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200).assertThat()
                .body("label", equalTo("door1"))
                .body("enabled", equalTo(false))
                .body("status", equalTo("closed"));
    }

    @Test
    public void door_get_returns_404_with_incorrect_label() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/door/door2")
                .then()
                .log().all()
                .log().body()
                .statusCode(404)
                .assertThat()
                .body("status", equalTo("NOT_FOUND"));
    }

    @Test
    public void door_get_returns_404_with_incorrect_devType() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/Doorrrr/door1")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void door_get_returns_404_with_empty_label() {
        get("http://localhost:8080/api/v0.1/gateway/house1/device/door/")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void update_door_returns_200_with_correct_label_and_devType() {
        Map<String, Object> door = Map.of("label","door1","deviceType","Door" ,"enabled",true, "status", "opened");

        with().body(door)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/door/door1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("label", equalTo("door1"))
                .body("enabled", equalTo(true))
                .body("status", equalTo("opened"));

        get("http://localhost:8080/api/v0.1/gateway/house1/device/door/door1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("label", equalTo("door1"))
                .body("enabled", equalTo(true))
                .body("status", equalTo("opened"));

        door= Map.of("label", "door1", "enabled", false, "status", "closed", "deviceType", "Door");

        with().body(door)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/door/door1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("label", equalTo("door1"))
                .body("enabled", equalTo(false))
                .body("status", equalTo("closed"));

        get("http://localhost:8080/api/v0.1/gateway/house1/device/door/door1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("label", equalTo("door1"))
                .body("enabled", equalTo(false))
                .body("status", equalTo("closed"));
    }

    @Test
    public void update_door_returns_200_with_minimal_body() {
        Map<String, Object> door = Map.of("label", "door1", "deviceType", "Door");

        with().body(door)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/door/door1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("label", equalTo("door1"))
                .body("enabled", equalTo(false))
                .body("status", equalTo("closed"));

        get("http://localhost:8080/api/v0.1/gateway/house1/device/door/door1")
                .then()
                .log().all()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("label", equalTo("door1"))
                .body("enabled", equalTo(false))
                .body("status", equalTo("closed"));

    }

    @Test
    public void door_update_returns_404_with_incorrect_label() {
        Map<String, Object> door = Map.of("label","door2","deviceType","Door" ,"enabled",true, "status", "opened");

        with().body(door)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/door/door2")
                .then()
                .log().all()
                .log().body()
                .statusCode(404)
                .assertThat()
                .body("status", equalTo("NOT_FOUND"));
    }

   @Test
   public void door_update_returns_400_with_incorrect_body_label_missing() {
         Map<String, Object> door = Map.of("deviceType","Door" ,"enabled",true, "status", "opened");

         with().body(door)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/door/door1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
   }

   @Test
   public void door_update_returns_400_with_incorrect_body_label_type() {
            Map<String, Object> door = Map.of("label",1,"deviceType","Door" ,"enabled",true, "status", "opened");

            with().body(door)
                    .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/door/door1")
                    .then()
                    .log().all()
                    .log().body()
                    .statusCode(400);
   }

   @Test
   public void door_update_returns_400_with_incorrect_body_deviceType_wrong_type() {
            Map<String, Object> door = Map.of("label","door1","deviceType",1 ,"enabled",true, "status", "opened");

            with().body(door)
                    .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/door/door1")
                    .then()
                    .log().all()
                    .log().body()
                    .statusCode(400);
   }

   @Test
   public void door_update_returns_400_with_incorrect_body_status_wrong_type() {
            Map<String, Object> door = Map.of("label","door1","deviceType","Door" ,"enabled",true, "status", 1);

            with().body(door)
                    .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/door/door1")
                    .then()
                    .log().all()
                    .log().body()
                    .statusCode(400);
   }

   @Test
   public void door_update_returns_400_with_incorrect_body_enabled_wrong_type() {
            Map<String, Object> door = Map.of("label","door1","deviceType","Door" ,"enabled",1, "status", "opened");

            with().body(door)
                    .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/door/door1")
                    .then()
                    .log().all()
                    .log().body()
                    .statusCode(400);
   }

   @Test
   public void door_update_returns_400_with_wrong_enable_not_boolean() {
         Map<String, Object> door = Map.of("label","door1","deviceType","Door" ,"enabled","true", "status", "opened");

         with().body(door)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/door/door1")
                .then()
                .log().all()
                .log().body()
                .statusCode(400);
   }

   @Test
   public void door_update_returns_400_with_wrong_status() {
    Map<String, Object> door = Map.of("label","door1","deviceType","Door" ,"enabled",true, "status", "open");

            with().body(door)
                    .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/door/door1")
                    .then()
                    .log().all()
                    .log().body()
                    .statusCode(400);
   }

   @Test
   public void door_update_returns_400_with_wrong_enable_type() {
    Map<String, Object> door = Map.of("label","door1","deviceType","Door" ,"enabled",1, "status", "opened");

            with().body(door)
                    .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/door/door1")
                    .then()
                    .log().all()
                    .log().body()
                    .statusCode(400);
   }



   @Test
    public void door_update_returns_404_with_incorrect_devType() {
        Map<String, Object> door = Map.of("label","door1","deviceType","Door" ,"enabled",true, "status", "opened");

        with().body(door)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/doorr/door1")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void door_update_returns_404_with_empty_label() {
        Map<String, Object> door = Map.of("deviceType","Door" ,"enabled",true, "status", "opened");

        with().body(door)
                .when().put("http://localhost:8080/api/v0.1/gateway/house1/device/door/")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void door_post_returns_404() {
        Map<String, Object> door = Map.of("label","door1","deviceType","Door" ,"enabled",true, "status", "opened");

        with().body(door)
                .when().post("http://localhost:8080/api/v0.1/gateway/house1/device/door/door1")
                .then()
                .log().all()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void door_delete_returns_404() {
        with()
                .when().delete("http://localhost:8080/api/v0.1/gateway/house1/device/door/door1")
                .then()
                .log().all()
                .statusCode(404);
    }




}
