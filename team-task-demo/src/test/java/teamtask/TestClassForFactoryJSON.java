package teamtask;

import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class TestClassForFactoryJSON {
  
  private String Method, Route, ExStatus;
  private Object Body,ExBody;

  public TestClassForFactoryJSON(String method, String route, Object body, String exStatus, Object exBody) {
    Method = method;
    Route = route;
    Body = body;
    ExStatus = exStatus;
    ExBody = exBody;
  }

  @Test
	public void testDataJSON() {
    if(Method.equals("POST")){
      RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
      RequestSpecification request = RestAssured.given().body(Body); 
      request.header("Content-Type", "application/json"); 
      Response response = request.post(Route); 
      ResponseBody body = response.getBody();
      assertEquals(response.getStatusCode(),Integer.parseInt(ExStatus));
      if(response.getStatusCode() >= 200 && response.getStatusCode() < 300){ 
        String ExBodyString = new Gson().toJson(ExBody);
        JSONObject AcBodyJSON = new JSONObject(body.asString());
        JSONAssert.assertEquals(ExBodyString,AcBodyJSON,false);
      }
    } else if(Method.equals("PUT")){
      RestAssured.baseURI ="https://jsonplaceholder.typicode.com"; 
      RequestSpecification request = RestAssured.given().body(Body); 
      request.header("Content-Type", "application/json"); 
      Response response = request.put(Route); 
      ResponseBody body = response.getBody();
      assertEquals(response.getStatusCode(),Integer.parseInt(ExStatus));
      if(response.getStatusCode() >= 200 && response.getStatusCode() < 300){ 
        String ExBodyString = new Gson().toJson(ExBody);
        JSONObject AcBodyJSON = new JSONObject(body.asString());
        JSONAssert.assertEquals(ExBodyString,AcBodyJSON,false);
      }
    } else if(Method.equals("DELETE")){
      Response response = RestAssured.delete("https://jsonplaceholder.typicode.com"+Route);
      assertEquals(response.getStatusCode(),Integer.parseInt(ExStatus));
      if(response.getStatusCode() >= 200 && response.getStatusCode() < 300){ 
        String ExBodyString = new Gson().toJson(ExBody);
        JSONObject AcBodyJSON = new JSONObject(response.asString());
        JSONAssert.assertEquals(ExBodyString,AcBodyJSON,false);
      }
    }
	}
}
