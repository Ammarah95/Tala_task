package Library;

import java.io.FileInputStream;
import java.util.Properties;


public class Generic_Methods {
	
	public static String path = "./src/test/java/Library";

	public static String getProperty(String Value) throws Exception {
		FileInputStream fis = new FileInputStream(path + "/Data.properties");
		Properties prop = new Properties();
		prop.load(fis);
		String data = prop.getProperty(Value);

		return data;
	}


	
}
