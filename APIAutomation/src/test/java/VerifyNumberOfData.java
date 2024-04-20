import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.equalTo;

@RunWith(DataProviderRunner.class)
public class VerifyNumberOfData {

    @DataProvider
    public static Object[][] perPageValues() {
        return new Object[][] {
                {20}, // Test with per_page = 20
                {5},  // Test with per_page = 5
                {1}   // Test with per_page = 1
        };
    }

    @Test
    @UseDataProvider("perPageValues")
    public void testNumberOfData(int perPageNumber) {
        Response response = RestAssured.given().get("https://reqres.in/api/users?page=2&per_page=" + perPageNumber);
        int responsePage = response.path("page");
        int responseTotal = response.path("total");
        int responsePerPage = response.path("per_page");
        int start_index = (responsePage - 1) * responsePerPage;
        int end_index = Math.min(start_index + responsePerPage, responseTotal);
        int numberOfData =  Math.max(0, end_index - start_index);

        //Verify the amount of data returned
        response.then().assertThat().body("data.size()", equalTo(numberOfData));
    }
}