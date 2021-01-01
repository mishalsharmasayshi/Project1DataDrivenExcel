package testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import base.TestBase;

public class BankManagerLoginTest extends TestBase{
	
@Test
public void bankManagerLoginTest() {
	String methodName =new Throwable().getStackTrace()[0].getMethodName();
	if(!TestRunner.testRunner(methodName)) {
		skipCase(methodName);
	}
    click("bmlBtn_CSS");
	click("addCustBtn_CSS");
	
	
}
	
}
