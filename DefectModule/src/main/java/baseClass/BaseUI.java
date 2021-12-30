package baseClass;

import java.io.File;

import java.io.FileInputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class BaseUI {

	public static WebDriver driver;
	public static Properties prop;
	public static ExtentHtmlReporter extenthtml;
	public static ExtentReports report;
	public static ExtentTest logger;
	public static String defect_id;

	// Initialize Extent Report
	@BeforeSuite
	public void reportinitialize() {
		System.out.println("Report Initialized");

		extenthtml = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "//Extent-Reports//" + FileName() + ".html");
		report = new ExtentReports();
		report.attachReporter(extenthtml);
		report.setSystemInfo("Host Name", "QEA21QE026-Team4");
		report.setSystemInfo("OS", "Windows 10");
		report.setSystemInfo("Browser", "Chrome");

		extenthtml.config().setDocumentTitle("Mainspring");
		extenthtml.config().setReportName("Defect Module");
		extenthtml.config().setTestViewChartLocation(ChartLocation.TOP);
		extenthtml.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
		extenthtml.config().setTheme(Theme.DARK);
	}

	// Name of Report and Screenshot
	public static String FileName() {
		Date d = new Date();
		return d.toString().replaceAll(":", "_").replaceAll(" ", "_");
	}

	// Manage Browser
	@BeforeTest
	public void invokeBrowser() throws Exception {
		prop = new Properties();
		FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "\\config\\config.properties");
		prop.load(file);

		String browser = prop.getProperty("browserName");
		if (browser.equalsIgnoreCase("Chrome")) {
			ChromeOptions option = new ChromeOptions();
			option.addArguments("--disable-notifications");
			driver = new ChromeDriver(option);
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
		} else if (browser.equalsIgnoreCase("firefox")) {
			FirefoxOptions option = new FirefoxOptions();
			option.addArguments("--disable-notifications");
			driver = new FirefoxDriver(option);
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\geckodriver.exe");
		} else {
			System.out.println("Missing Driver name");
			System.exit(0);
		}

		driver.manage().window().maximize();
	}

	// Open Website
	public void openURL() throws InterruptedException {
		driver.get(prop.getProperty("url"));
		Thread.sleep(20000);
	}

	// Invoke the created Defect Id
	public void getdefectId(String str) {
		defect_id = str;
	}

	// MouseHover
	public void mouseHover(WebElement target) {
		Actions action = new Actions(driver);
		action.moveToElement(target).build().perform();
	}

	// Take Screenshot
	public void takeScreenshot() {
		TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
		File sourceFile = takeScreenshot.getScreenshotAs(OutputType.FILE);
		File destinationFile = new File(System.getProperty("user.dir") + "//Screenshots//" + FileName() + ".png");

		try {
			FileUtils.copyFile(sourceFile, destinationFile);
			logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "//Screenshots//" + FileName() + ".png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Read value from Excel sheet
	public static Row readExcel() throws Exception {
		File file = new File("src/main/resources/defect-module.xlsx");
		FileInputStream f = new FileInputStream(file);
		Workbook book = WorkbookFactory.create(f);
		Sheet sheet = book.getSheetAt(0);
		Row row = sheet.getRow(1);
		return row;
	}

	// Report for Test Case Failure
	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenshot();
	}

	// Report for Test Case Pass
	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);
		takeScreenshot();
	}

	// Information of a Test Case
	public void reportInfo(String reportInfo) {
		logger.log(Status.INFO, reportInfo);
	}

	// Closing Browser
	@AfterTest
	public void closeBrowser() {
		driver.close();
	}

	// Save Extent-Report
	@AfterSuite
	public void tearDown() {
		report.flush();
	}
}
