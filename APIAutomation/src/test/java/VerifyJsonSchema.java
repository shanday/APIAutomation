import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import io.restassured.path.json.JsonPath;
import java.util.List;
public class VerifyJsonSchema {

    @Test
    public void testJsonSchema() {
        Response response = RestAssured.given().get("https://reqres.in/api/users");
        String responseBody = response.getBody().asString();
        response.then().assertThat().statusCode(200);

        //a. Verify that the amount of data returned
        response.then().assertThat().body("data.size()", equalTo(response.path("per_page")));

        //b. Print all returned “first_name” of list
        JsonPath jsonPath = new JsonPath(responseBody);
        List<String> firstNames = jsonPath.getList("data.first_name");
        System.out.println("First Name: " + firstNames);
    }
}
