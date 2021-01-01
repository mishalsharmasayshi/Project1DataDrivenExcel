package testcases;

import org.testng.annotations.Test;

import base.TestBase;

public class TestRunner extends TestBase {

	public TestRunner() {
		
	}
	
	public static boolean testRunner(String methodName) {
	String sheetName="TestRunner";
    for(int i=1;i<eu.getRowCount(sheetName);i++)
	{
    	if(eu.getCellValue(sheetName, i, 0).equalsIgnoreCase(methodName)) {
    		if(eu.getCellValue(sheetName, i, 1).equalsIgnoreCase("Y"))
    			return true;
    		else 
    			return false;
    			 
    	}
    	    	
	}
	log.info("Test "+methodName +" Case not present");
	return false;
	}

}
