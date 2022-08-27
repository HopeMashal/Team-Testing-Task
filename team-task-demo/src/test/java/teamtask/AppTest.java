package teamtask;

import java.util.List;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
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

/* 
    @DataProvider
    public static Object[][] getDataJSON() throws Exception{
        
    }


    @Test(dataProvider = "getDataJSON")
    public void testDataJSON(String Route,String ExStatus){
        
    } */
}
