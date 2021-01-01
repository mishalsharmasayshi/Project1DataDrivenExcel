package rough;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class rough1 {
	
public static void main(String[] args) throws IOException {
Properties config = new Properties();
 String pathConfig=System.getProperty("user.dir")+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"properties"+File.separator+"Config.properties";
	FileInputStream fis= new FileInputStream(pathConfig);
   config.load(fis);
   System.out.println(config.getProperty("browser"));
   
   Properties OR = new Properties();
   String pathOr=System.getProperty("user.dir")+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"properties"+File.separator+"OR.properties";
   fis= new FileInputStream(pathOr);
   OR.load(fis);
   System.out.println(OR.getProperty("bmlBtn"));
  
	//C:\Users\Admin\eclipse-workspace\Project1DataDrivenExcel\src\test\resources\properties\Config.properties
	
}
	
}
