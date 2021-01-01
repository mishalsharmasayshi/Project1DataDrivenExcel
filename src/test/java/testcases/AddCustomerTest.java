package testcases;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import testcases.BankManagerLoginTest;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.TestBase;
import utilities.ExcelUtility;
import utilities.ScreenShots;

public class AddCustomerTest extends TestBase {
	@Test(dataProviderClass=utilities.DataProvider1.class,dataProvider="dp1")
	public void addCustomerTest(Hashtable<String,String> htable) throws IOException, InterruptedException, HeadlessException, AWTException {
		String methodName =new Throwable().getStackTrace()[0].getMethodName();
		if(!TestRunner.testRunner(methodName)) {
			skipCase(methodName);
		}
		if(htable.get("Runner").equalsIgnoreCase("Y")) {
		click("addCustBtn_CSS");
		sendKeys("firstName_CSS",htable.get("FirstName"));
		sendKeys("lastName_CSS",htable.get("LastName"));
		sendKeys("postCode_XPATH",htable.get("PostCode"));
		Thread.sleep(500);
		ScreenShots.takeScreenshot();
		click("addCustBtnA_CSS");
		Alert a=webWait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(a.getText().contains("Customer added successfully"),"Not added successfully");
		Thread.sleep(500);
		BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		ImageIO.write(image, "jpg", new File(ScreenShots.getScreenshotPath()));
		a.accept();
		}else {
			log.info("Run mode for data: "+htable.get("FirstName")+" "+htable.get("LastName")+" not set to Y ");
			testThread.get().info("Run mode for data: "+htable.get("FirstName")+" "+htable.get("LastName")+" not set to Y ");
			throw new SkipException("Run mode for data: "+htable.get("Customer")+" not set to Y ");
		}
	}
	
	
}
