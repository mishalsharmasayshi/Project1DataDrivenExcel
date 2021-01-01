package testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import java.util.Hashtable;

import base.TestBase;

public class OpenAccountTest extends TestBase{

	public OpenAccountTest() {
		
	} 
	@Test(dataProviderClass=utilities.DataProvider1.class,dataProvider="dp1")
	public void openAccountTest(Hashtable<String,String> hTable) {
		String methodName =new Throwable().getStackTrace()[0].getMethodName();
		if(!TestRunner.testRunner(methodName)) {
			skipCase(methodName);
		}
		
		if(hTable.get("Runner").equalsIgnoreCase("Y")) {
			click("openAccountBtn_CSS");
			selectDropdown("customerDropdown_ID",hTable.get("Customer"));
			selectDropdown("currency_Dropdown_XPATH",hTable.get("Currency"));
			click("process_Btn_CSS");
			Alert process_AddAccntAlert = webWait.until(ExpectedConditions.alertIsPresent());
			Assert.assertTrue(process_AddAccntAlert.getText().contains("Account created successfully with account Number :"),
					"Account not created successfully on click of the Process button");
			
			testThread.get().info(process_AddAccntAlert.getText());
			log.debug(process_AddAccntAlert.getText());
			process_AddAccntAlert.accept();
		} else {
			log.info("Run mode for data: "+hTable.get("Customer")+" not set to Y ");
			testThread.get().info("Run mode for data: "+hTable.get("Customer")+" not set to Y ");
			throw new SkipException("Run mode for data: "+hTable.get("Customer")+" not set to Y ");
		}
			
		
	}

}
