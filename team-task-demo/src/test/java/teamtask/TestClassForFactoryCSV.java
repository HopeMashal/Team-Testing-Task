package teamtask;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestClassForFactoryCSV {
  private String Route;
	private String Status;
	
	public TestClassForFactoryCSV(String route, String status) {
		Route = route;
    Status = status;
	}
	
	@Test
	public void testDataCSV() {
    Response response = RestAssured.get("https://jsonplaceholder.typicode.com" + Route );
    assertEquals(response.getStatusCode(),Integer.parseInt(Status));
	}
}
