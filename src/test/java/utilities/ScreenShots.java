package utilities;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.TestBase;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;


public class ScreenShots extends TestBase {
 private static String screenshotPath;
	public ScreenShots() {
	}
	
	 static String genrateScreenshotPath() {
		Date d = new Date();
		String name = d.toString().replaceAll(":", "_").replaceAll(" ", "_");
		 screenshotPath = System.getProperty("user.dir")+File.separator+"screenshots"+File.separator+name+".jpg";
	    return screenshotPath;
	}
	
	public static void takeScreenshot() throws IOException {
	   File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE) ;
	   genrateScreenshotPath();
	   FileUtils.copyFile(src, new File(screenshotPath));
	   testThread.get().info("Screenshot taken successfully ");
	
	}
	
	static void entirePageScreenshot() throws IOException {
		Screenshot screenshot= new AShot().shootingStrategy
		(ShootingStrategies.viewportPasting(2000)).takeScreenshot(driver);
	    ImageIO.write(screenshot.getImage(), "jpg", new File(genrateScreenshotPath()));
		
		
	}
	
	static void elementScreenshot(WebElement element) throws IOException {
		Screenshot screenshot = new AShot().shootingStrategy
				(ShootingStrategies.viewportPasting(2000)).takeScreenshot(driver,element);

		ImageIO.write(screenshot.getImage(), "jpg", new File(genrateScreenshotPath()));
		
	}

	public static String getScreenshotPath() {
		return genrateScreenshotPath();
		
	}

	

}
