package teamtask;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJson {
	Object expectedResponseBody;
	Object body;
	String expectedStatusCode;
	String method;
	String requestUrl;
	
	void parseEmployeeObject(JSONObject request) {
		this.expectedResponseBody = (Object) request.get("expectedResponseBody");
		this.body = (Object) request.get("body");
		this.expectedStatusCode = (String) request.get("expectedStatusCode");
		System.out.println("expectedStatusCode " + this.expectedStatusCode);
		String url = (String) request.get("url");
		System.out.println("url " + url);
		
		String[] urlParts = url.split(" ");
		this.method = urlParts[0];
		System.out.println("method " + method);
		this.requestUrl = urlParts[1];
		System.out.println("requestUrl " + requestUrl);
	}

	public JSONArray readJsonFile(String filePath) {
		JSONArray requestList = null;

		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader(filePath)) {
			Object obj = jsonParser.parse(reader);

			requestList = (JSONArray) obj;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return requestList;
	}
}
