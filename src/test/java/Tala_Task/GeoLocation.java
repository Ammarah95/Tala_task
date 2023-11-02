package Tala_Task;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Library.Generic_Methods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GeoLocation {

	@Test
	public static void geoLocation() throws Exception {
		ExtentReports report = new ExtentReports("./Reports/ExtentReportResults.html");
		ExtentTest test = report.startTest("Test Name : GeoLocation");
		
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
			test.log(LogStatus.PASS, "Status Code : "+response.statusCode());
			test.log(LogStatus.PASS, JsonResponse);
			
		} else if(response.getStatusCode() == 400){
			test.log(LogStatus.FAIL,"Status Code : "+ response.getStatusCode());
			 test.log(LogStatus.FAIL,"Message from the Response :"+responsebody.get("error.message"));
			 test.log(LogStatus.FAIL,"response Status : "+ responsebody.get("error.status"));
			 test.log(LogStatus.FAIL,"Failure Reason : "+responsebody.get("error.errors[0].reason"));
		}
		else {
			test.log(LogStatus.FAIL,"Status Code : "+ response.getStatusCode());
			
			try {
				test.log(LogStatus.FAIL," Error Reason : "+responsebody.get("error.errors[0].reason"));
				test.log(LogStatus.FAIL," Error Message  : "+responsebody.get("error.errors[0].message"));
				test.log(LogStatus.FAIL," Response : "+response.asPrettyString());
				
			} catch (Exception e) {
				 test.log(LogStatus.FAIL,"Error : " + e.getMessage());
			}
		}
		report.endTest(test);
		report.flush();
	}

}