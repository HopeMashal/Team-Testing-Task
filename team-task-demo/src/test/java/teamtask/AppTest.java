package teamtask;

import java.util.List;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;


public class AppTest 
{
    static String JSONpath;
    static String CSVpath;
    
    @Parameters({"JSONpath","CSVpath"})
    @BeforeSuite
    public void beforeSuite(String JSONpath,String CSVpath){
        AppTest.JSONpath = JSONpath;
        AppTest.CSVpath = CSVpath;
    }

    @DataProvider
    public static Object[][] getDataCSV() throws Exception{
        List<String[]> lines = ReadCsvFile.readAllLines(AppTest.CSVpath);
        lines.remove(0);
        Object[][] data = new Object[lines.size()][lines.get(0).length];
        int index = 0;
        for(String[] line: lines){
            data[index] = line;
            index++;
        }
        return data;
    }


    @Test(dataProvider = "getDataCSV")
    public void testDataCSV(String Route,String ExStatus){
        Response response = RestAssured.get("https://jsonplaceholder.typicode.com" + Route);
        Assert.assertEquals(Integer.parseInt(ExStatus), response.getStatusCode());
    }


    @DataProvider
    public static Object[][] getDataJSON() throws Exception{
        List<Object[]> lines = ReadJson.parseList(AppTest.JSONpath);
        Object[][] data = new Object[lines.size()][lines.get(0).length];
        int index = 0;
        for(Object[] line: lines){
            data[index] = line;
            index++;
        }
        return data;
    }


    @Test(dataProvider = "getDataJSON")
    public void testDataJSON(String Method,String Route,Object Body,String ExStatus,Object ExBody){
        if(Method.equals("POST")){
            RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
            RequestSpecification request = RestAssured.given().body(Body); 
	        request.header("Content-Type", "application/json"); 
	        Response response = request.post(Route); 
	        ResponseBody body = response.getBody();
            Assert.assertEquals(Integer.parseInt(ExStatus), response.getStatusCode());
            Assert.assertEquals(new Gson().toJson(ExBody), body.asString());
        } else if(Method.equals("PUT")){
            RestAssured.baseURI ="https://jsonplaceholder.typicode.com"; 
            RequestSpecification request = RestAssured.given().body(Body); 
            request.header("Content-Type", "application/json"); 
            Response response = request.put(Route); 
            ResponseBody body = response.getBody();
            Assert.assertEquals(Integer.parseInt(ExStatus), response.getStatusCode());
            Assert.assertEquals(new Gson().toJson(ExBody), body.asString());
        } else if(Method.equals("DELETE")){
            Response response = RestAssured.delete("https://jsonplaceholder.typicode.com"+Route);
            Assert.assertEquals(Integer.parseInt(ExStatus), response.getStatusCode());
            Assert.assertEquals(new Gson().toJson(ExBody), response.asString());
        }
    }
}
