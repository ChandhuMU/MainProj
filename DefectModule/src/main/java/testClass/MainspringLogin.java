package testClass;

import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseClass.BaseUI;

public class MainspringLogin extends BaseUI {

	public static String defectid;

	// MouseHover to BSF NA Sales project and Select "Defect" under "Execute" drop
	// down box.
	public void openProject() throws Exception {
		try {
			// Creating Test report for "openProject" function
			logger = report.createTest("Open Mainspring Application");

			Thread.sleep(20000);
			WebDriverWait wait = new WebDriverWait(driver, 35);

			// MouseHover in the left-corner
			WebElement menu = driver.findElement(By.className(prop.getProperty("homepage-menuHover-btn")));
			wait.until(ExpectedConditions.visibilityOf(menu));
			mouseHover(menu);

			// Click on MyProject
			driver.findElement(By.xpath(prop.getProperty("homepage-MyProject"))).click();

			Thread.sleep(3000);

			// Click on "Execute"
			WebElement projectElement = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("homepage-Execute"))));
			mouseHover(projectElement);

			Thread.sleep(3000);

			// Click "Defect" under Execute
			driver.findElement(By.xpath(prop.getProperty("homepage-Defect"))).click();

			WebDriverWait waits = new WebDriverWait(driver, 30);

			// Click on "+" to create defect
			WebElement addButton = waits.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(prop.getProperty("Defectpage-AddDefect-btn"))));
			addButton.click();

			// Print if the Test Case Passed
			reportPass("Mainspring Defect Module opened.");

		} catch (Exception e) {
			// Print if the Test Case Failed
			e.printStackTrace();
			reportFail("Project wasn't clicked" + e.getMessage());
		}
	}

	// Enter Mandatory field to create a Defect
	public void createDefect() {
		try {
			Row r = BaseUI.readExcel();

			// Creating Test report for "createDefect" function
			logger = report.createTest("Creating a Defect");

			// Switch frame
			driver.switchTo().frame(driver.findElement(By.xpath(prop.getProperty("iFrame"))));

			// Enter Name
			driver.findElement(By.xpath(prop.getProperty("Defectpage-Name")))
					.sendKeys((r.getCell(0)).getStringCellValue());

			// Enter Description
			driver.findElement(By.xpath(prop.getProperty("Defectpage-Description")))
					.sendKeys((r.getCell(1)).getStringCellValue());

			// Enter StepsToReproduce
			driver.findElement(By.xpath(prop.getProperty("Defectpage-StepToReproduce")))
					.sendKeys((r.getCell(2)).getStringCellValue());

			// Select Severity
			WebElement severity = driver.findElement(By.xpath(prop.getProperty("Defectpage-Severity")));
			Select s_severity = new Select(severity);
			s_severity.selectByValue("1001640");

			// Select Priority
			WebElement priority = driver.findElement(By.xpath(prop.getProperty("Defectpage-Priority")));
			Select s_priority = new Select(priority);
			s_priority.selectByValue("Medium");

			// Select Release
			WebElement release = driver.findElement(By.xpath(prop.getProperty("Defectpage-Release")));
			Select s_release = new Select(release);
			s_release.selectByValue("69514");

			// Select Sprint
			WebElement sprint = driver.findElement(By.xpath(prop.getProperty("Defectpage-Sprint")));
			Select s_sprint = new Select(sprint);
			s_sprint.selectByValue("70570");

			// Select Appraisal
			WebElement apprisal = driver.findElement(By.xpath(prop.getProperty("Defectpage-AppraisalType")));
			Select s_apprisal = new Select(apprisal);
			s_apprisal.selectByValue("1001638");

			// Select Report Source
			WebElement reportSource = driver.findElement(By.xpath(prop.getProperty("Defectpage-ReportingSource")));
			Select s_reportSelect = new Select(reportSource);
			s_reportSelect.selectByValue("1001655");

			// Select Detection Activity
			WebElement detectionActivity = driver.findElement(By.xpath(prop.getProperty("Defectpage-StandardCode")));
			Select s_detectionActivity = new Select(detectionActivity);
			s_detectionActivity.selectByValue("58220");

			// Enter Due Date
			driver.findElement(By.xpath(prop.getProperty("Defectpage-DueDate"))).sendKeys("31-Dec-2021");

			// Click on Save
			driver.findElement(By.xpath(prop.getProperty("Defectpage-Save"))).click();

			WebDriverWait waits = new WebDriverWait(driver, 30);

			// Capture the Generated Defect Id
			WebElement defectId = waits.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("Defectpage-DefectId"))));
			defectid = defectId.getText();

			reportInfo(defectid);
			// System.out.println(defectid);

			// Click Cancel
			driver.findElement(By.xpath(prop.getProperty("Defectpage-Cancel-btn"))).click();

			// Switch back to frame
			driver.switchTo().defaultContent();

			getdefectId(defectid);
			Thread.sleep(3000);
			// Print if the Test Case Passed
			reportPass("Defect created.");

		} catch (Exception e) {
			e.printStackTrace();

			// Print if the Test Case Failed
			reportFail("Add Defect link not clicked" + e.getMessage());
		}
	}
}
