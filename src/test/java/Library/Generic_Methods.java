package Library;

import java.io.FileInputStream;
import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Generic_Methods {
	public static ExtentTest test;
	public static ExtentReports report;
	public static String path = "./src/test/java/Library";

	public static String getProperty(String Value) throws Exception {
		FileInputStream fis = new FileInputStream(path + "/Data.properties");
		Properties prop = new Properties();
		prop.load(fis);
		String data = prop.getProperty(Value);

		return data;
	}

	@BeforeClass
	public static void startTest() {
		report = new ExtentReports("./Reports/ExtentReportResults.html");
		test = report.startTest("GeoLocation");

	}

	public static void logInfo(LogStatus Status, String Message) {
		test.log(Status, Message);

	}

	@AfterClass
	public static void endTest() {
		report.endTest(test);
		report.flush();
	}
}
