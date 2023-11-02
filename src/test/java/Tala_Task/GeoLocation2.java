package Tala_Task;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import Library.Generic_Methods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GeoLocation2 {

	@Test
	public static void geoLocation() throws Exception {
	
		RestAssured.baseURI = Generic_Methods.getProperty("baseURI");
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("homeMobileCountryCode", "310");
		requestParams.put("homeMobileNetworkCode", "410");
		requestParams.put("radioType", "gsm");
		requestParams.put("carrier", "Vodafone");
		requestParams.put("considerIp", "true");

		request.header("Content-Type", "application/json").queryParams("key",Generic_Methods.getProperty("key"));
		request.body(requestParams);

		Response response = request.post(Generic_Methods.getProperty("endpoint"));

		String JsonResponse = response.asString();

		JsonPath responsebody = new JsonPath(JsonResponse);
		if (response.statusCode() == 200) {
			Generic_Methods.logInfo(LogStatus.PASS, "Status Code : "+response.statusCode());
			Generic_Methods.logInfo(LogStatus.PASS, JsonResponse);
			
		} else if(response.getStatusCode() == 400){
			Generic_Methods.logInfo(LogStatus.FAIL,"Status Code : "+ response.getStatusCode());
			 Generic_Methods.logInfo(LogStatus.FAIL,"Message from the Response :"+responsebody.get("error.message"));
			 Generic_Methods.logInfo(LogStatus.FAIL,"response Status : "+ responsebody.get("error.status"));
			 Generic_Methods.logInfo(LogStatus.FAIL,"Failure Reason : "+responsebody.get("error.errors[0].reason"));
		}
		else {
			Generic_Methods.logInfo(LogStatus.FAIL,"Status Code : "+ response.getStatusCode());
			
			try {
				String error = responsebody.get("error.message");
				  Generic_Methods.logInfo(LogStatus.FAIL," Error : "+error);
			} catch (Exception e) {
				 Generic_Methods.logInfo(LogStatus.FAIL,"Error : " + e.getMessage());
			}
		}
		
	}

}