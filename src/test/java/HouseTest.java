import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HouseTest {

    @Test
    public void house_returns_200_with_correct_name_and_address() {
        get("http://localhost:8080/api/v0.1/gateway/house")
            .then()
            .statusCode(200).assertThat()
            .body("name", hasItem("house1"))
            .body("address", hasItem("http://smart-home:8081"));
    }


    @Test
    public void house_returns_404_with_incorrect_name() {
        get("http://localhost:8080/api/v0.1/gateway/house2")
            .then()
            .statusCode(404)
            .assertThat()
                .body("error", equalTo("House not Found"));
    }

    @Test
    public void house_returns_404_with_empty_name() {
        get("http://localhost:8080/api/v0.1/gateway/")
            .then()
            .statusCode(404);
    }

    @Test
    public void create_house_returns_201_with_correct_name_and_address() {
        Map<String, String> house = new HashMap<>();
        house.put("name", "house2");
        house.put("address", "http://smart-home:8081");

        with().body(house)
            .when().post("http://localhost:8080/api/v0.1/gateway/house")
            .then()
            .statusCode(200)
            .assertThat()
                .body("name", equalTo("house2"))
                .body("address", equalTo("http://smart-home:8081"));

        delete("http://localhost:8080/api/v0.1/gateway/house2")
        .then()
                .statusCode(204);
    }

    @Test
    public void create_house_returns_409_with_duplicate_name() {
        Map<String, String> house = new HashMap<>();
        house.put("name", "house2");
        house.put("address", "http://smart-home:8081");


        with().body(house).when().post("http://localhost:8080/api/v0.1/gateway/house")
        .then().statusCode(200);

        with().body(house)
            .when().post("http://localhost:8080/api/v0.1/gateway/house")
            .then()
            .statusCode(409)
            .assertThat()
                .body("error", equalTo("House already exists"));

        delete("http://localhost:8080/api/v0.1/gateway/house2")
                .then()
                .statusCode(204);
    }

    @Test
    public void create_house_returns_400_with_empty_name() {
        Map<String, String> house = new HashMap<>();
        house.put("name", "");
        house.put("address", "http://smart-home:8081");

        with().body(house)
            .when().post("http://localhost:8080/api/v0.1/gateway/house")
            .then()
            .statusCode(400)
            .assertThat()
                .body("error", equalTo("Bad Request"));
    }

    @Test
    public void create_house_returns_400_with_empty_address() {
        Map<String, String> house = new HashMap<>();
        house.put("name", "house2");
        house.put("address", "");

        with().body(house)
            .when().post("http://localhost:8080/api/v0.1/gateway/house")
            .then()
            .statusCode(400)
            .assertThat()
                .body("error", equalTo("Bad Request"));
    }

    @Test
    public void create_house_returns_400_with_wrong_address() {
        Map<String, String> house = new HashMap<>();
        house.put("name", "house2");
        house.put("address", "aaa");

        with().body(house)
            .when().post("http://localhost:8080/api/v0.1/gateway/house")
            .then()
            .statusCode(400)
            .assertThat()
                .body("error", equalTo("Bad Request"));
    }

    @Test
    public void update_house_returns_200_with_correct_name_and_address() {
        Map<String, String> house = new HashMap<>();
        house.put("name", "house2");
        house.put("address", "http://smart-home:8081");

        with().body(house)
            .when().put("http://localhost:8080/api/v0.1/gateway/house1")
            .then()
            .statusCode(200)
            .assertThat()
                .body("name", equalTo("house2"))
                .body("address", equalTo("http://smart-home:8081"));

        house.put("name", "house1");
        house.put("address", "http://smart-home:8081");

        with().body(house)
            .when().put("http://localhost:8080/api/v0.1/gateway/house2")
            .then()
            .statusCode(200)
            .assertThat()
                .body("name", equalTo("house1"))
                .body("address", equalTo("http://smart-home:8081"));
    }


}
