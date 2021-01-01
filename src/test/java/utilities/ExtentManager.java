package utilities;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import base.TestBase;

public class ExtentManager  {
	static ExtentReports extRep;
	public ExtentManager() {

	}

	public static ExtentReports createExtentInstance() {
		
		
		String path = System.getProperty("user.dir")+File.separator+"target"+File.separator+"surefire-reports"
		+File.separator+"html"+File.separator+"extent.html";
		ExtentSparkReporter esr = new ExtentSparkReporter(path);
		esr.config().setDocumentTitle("Extent Report for Proj1DDE");
		esr.config().setEncoding("utf-8");
		esr.config().setTheme(Theme.STANDARD);
		esr.config().setReportName("Banking Extent Report for proj1 DDE");
		extRep=new ExtentReports();
		extRep.attachReporter(esr);
		extRep.setSystemInfo("Report Generator", "Mishal");
		extRep.setSystemInfo("Build_No", "22322");
		extRep.setSystemInfo("Organisation Name", "WoltersKluwer");
		
		return extRep;
		
	}

}
