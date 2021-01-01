package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utilities.ExcelUtility;
import utilities.ExtentManager;

//import mishal.academy.base.TestBase;

public class TestBase {
	/*
	 * Initialize the following in this class which will be a super class WebDriver
	 * Properties ExtentReports Logs Mail ExcelDriver DataBase
	 */
	public static Properties config;
	public static Properties OR;
	public FileInputStream fis;
	public static WebDriver driver;
	public static Logger log = LogManager.getLogger(TestBase.class);// LogManager.getLogger(TestBase.class);
	public static Date d = new Date();
	// public String strDate=d.toString().replaceAll(" ", "_").replaceAll(":", "_");
	static String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
			+ "resources" + File.separator + "excel" + File.separator + "testdata.xlsx";
	protected static ExcelUtility eu = new ExcelUtility(path);
	public static WebDriverWait webWait;
	public ChromeOptions options;
	protected static ExtentReports rep = ExtentManager.createExtentInstance();
	public static ThreadLocal<ExtentTest> testThread = new ThreadLocal<ExtentTest>();
	public static String browser;

	@BeforeSuite
	public void setUp() {
		options = new ChromeOptions();
		options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
		System.setProperty("current.date", d.toString().replaceAll(" ", "_").replaceAll(":", "_"));
		if (driver == null) {

			config = new Properties();
			String pathConfig = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
					+ File.separator + "resources" + File.separator + "properties" + File.separator
					+ "Config.properties";
			try {
				fis = new FileInputStream(pathConfig);
				config.load(fis);
				log.info("Config loaded successfully");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			OR = new Properties();
			String pathOR = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
					+ File.separator + "resources" + File.separator + "properties" + File.separator + "OR.properties";
			try {
				fis = new FileInputStream(pathOR);
				OR.load(fis);
				log.info("OR loaded successfully");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty()){
			 browser=System.getenv("browser");
			 }else {
				 browser=config.getProperty("browser");
			 }
			
			config.setProperty("browser", browser);

			if (config.getProperty("browser").equalsIgnoreCase("chrome")) {

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
								+ File.separator + "resources" + File.separator + "executables" + File.separator
								+ "chromedriver.exe");
				driver = new ChromeDriver(options);
				log.info("Chrome Driver  initialized");

			} else if (config.getProperty("browser").equalsIgnoreCase("ie")) {

				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
								+ File.separator + "resources" + File.separator + "executables" + File.separator
								+ "IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				log.info("IE driver  initialized");
			}

			driver.manage().window().maximize();
			log.info("Browser maximized");
			driver.manage().timeouts().implicitlyWait(Long.valueOf(config.getProperty("implicitwait")),
					TimeUnit.SECONDS);
			webWait = new WebDriverWait(driver, 6);
			driver.get(config.getProperty("testsiteurl"));
			log.info(config.getProperty("testsiteurl") + ": URL  opened");

		}

	}

	public WebElement getElement(String loc) {
		try {
			if (loc.endsWith("_CSS")) {
				return driver.findElement(By.cssSelector(OR.getProperty(loc)));
			} else if (loc.endsWith("_XPATH")) {
				return driver.findElement(By.xpath(OR.getProperty(loc))); // if cannot find will throw exception
			} else if (loc.endsWith("_ID")) {
				return driver.findElement(By.id(OR.getProperty(loc)));
			}
			log.info("Type of locator not handled :" + loc + "Please provide CSS,XPATH OR ID");
			return null;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			testThread.get().info(loc + " not found");
			log.info(loc + " not found");
			return null;

		}
	}

	public boolean isElementPresent(String loc) {

		if (getElement(loc) != null) {
			testThread.get().info(loc + " is present ");
			log.info(loc + " is present ");
			return true;
		} else {
			return false;
		}
	}

	public boolean click(String loc) {

		if (getElement(loc) != null) {
			getElement(loc).click();
			testThread.get().info(loc + " is clicked successully ");
			log.info(loc + " is clicked successully ");
			return true;
		} else
			return false;
	}

	public boolean sendKeys(String loc, String keys) {
		if (getElement(loc) != null) {
			getElement(loc).sendKeys(keys);
			testThread.get().info("Successfully entered " + keys + " in  " + loc + " ");
			log.info("Successfully entered " + keys + " in  " + loc + " ");
			return true;
		} else
			return false;
	}

	public boolean selectDropdown(String elementLocator, String dropdownValue) {
		if (getElement(elementLocator) != null) {
			WebElement dropdown = getElement(elementLocator);
			Select selectElement = new Select(dropdown);
			selectElement.selectByVisibleText(dropdownValue);
			testThread.get().info("Successfully selected value " + dropdownValue + " in the dropdown " + elementLocator);
			log.info("Successfully selected value " + dropdownValue + " in the dropdown " + elementLocator);
			return true;
		} else {
			return false;
		}

	}
	
	public void skipCase(String methodName) {
		log.error("The test "+methodName+" is marked as N hence skipped");
		testThread.get().skip("The test "+methodName+" is marked as N hence skipped");
		throw new SkipException("The test "+methodName+" is marked as N hence skipped");
	}

	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			testThread.get().info("TestCase Completed");
			log.info("TestCase Completed");
			log.exit();
			 driver.quit();

		}
	}
}
