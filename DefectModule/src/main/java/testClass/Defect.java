package testClass;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseClass.BaseUI;

public class Defect extends BaseUI {

	// Select Created Defect
	public void selectCreatedDefect() {
		try {
			// Creating Test report for "openProject" function
			logger = report.createTest("Select created Defect");
			WebDriverWait waits = new WebDriverWait(driver, 30);

			// Click the created Defect
			WebElement def_id = waits.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='" + defect_id + "']")));
			
			def_id.click();
			Thread.sleep(4000);
			// Print if the Test Case Passed
			reportPass("Defect Selected");

		} catch (Exception e) {
			// Print if the Test Case Failed
			e.printStackTrace();
			reportFail("Defects wasn't selected" + e.getMessage());
		}
	}

	// Automate Workflow
	public void createWorkflow() throws Exception {
		try {
			// Creating Test report for "openProject" function
			logger = report.createTest("Workflow - Create Workflow, assign work and route");

			Thread.sleep(4000);
			WebDriverWait waits = new WebDriverWait(driver, 30);

			// Switch frame
			driver.switchTo().frame(driver.findElement(By.xpath(prop.getProperty("iFrame"))));

			// Click "Workflow"
			WebElement workflow = waits
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("Workflow-btn"))));
			workflow.click();

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

			// Switch frame
			driver.switchTo().frame(driver.findElement(By.xpath(prop.getProperty("Workflow-iFrame"))));

			// Select Performer
			WebElement performer = waits.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("Workflow-performer"))));
			Select s_performer = new Select(performer);
			s_performer.selectByVisibleText("Chandhu M U");

			// Select Approver
			WebElement approver = driver.findElement(By.xpath(prop.getProperty("Workflow-approver")));
			Select s_approver = new Select(approver);
			s_approver.selectByVisibleText("Chandhu M U");

			// Switch back to frame
			driver.switchTo().defaultContent();

			// Switch frame
			driver.switchTo().frame(driver.findElement(By.xpath(prop.getProperty("iFrame"))));

			// Click Save
			driver.findElement(By.xpath(prop.getProperty("Workflow-Save"))).click();

			// Click on Route Button
			WebElement route = waits
					.until(ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty("Workflow-Route"))));
			route.click();
			Thread.sleep(4000);

			// Switch to pop up window
			ArrayList<String> lst = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(lst.get(1));

			// Enter Description
			driver.findElement(By.xpath(prop.getProperty("Route-comments"))).sendKeys("Defect identified");

			// Click Submit
			driver.findElement(By.xpath(prop.getProperty("Rout-submit"))).click();

			// Switch window
			driver.switchTo().window(lst.get(0));

			Thread.sleep(5000);

			// Print if the Test Case Passed
			reportPass("Workflow created and routed successfully");

		} catch (Exception e) {
			e.printStackTrace();
			reportFail("Workflow wasn't created" + e.getMessage());
		}
	}

	// Automate Comments
	public void createComments() throws Exception {
		try {
			// Creating Test report for "openProject" function
			logger = report.createTest("Comments - Enter Comments");

			Thread.sleep(3000);

			// Switch frame
			driver.switchTo().frame(driver.findElement(By.xpath(prop.getProperty("iFrame"))));

			WebDriverWait waits = new WebDriverWait(driver, 30);

			// Click "Comments"
			WebElement comments = waits
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("Comments-btn"))));
			comments.click();
			Thread.sleep(3000);

			// Switch frame
			driver.switchTo().frame(driver.findElement(By.xpath(prop.getProperty("Comments-iFrame"))));

			// Enter Comments
			driver.findElement(By.xpath(prop.getProperty("Comments-addComment"))).sendKeys("Defect-Comments");

			// Click Ok
			driver.findElement(By.xpath(prop.getProperty("Comments-Ok-btn"))).click();

			// Switch back to frame
			driver.switchTo().defaultContent();

			// Print if the Test Case Passed
			reportPass("Comments added in the text box");

		} catch (Exception e) {
			// Print if the Test Case Failed
			e.printStackTrace();
			reportFail("Comments not added" + e.getMessage());
		}
	}

	// Automate Attachments
	public void createAttachments() throws Exception {
		try {
			// Creating Test report for "openProject" function
			logger = report.createTest("Attachments - Add Attachments");

			Thread.sleep(2000);

			// Switch to Frame
			driver.switchTo().frame(driver.findElement(By.xpath(prop.getProperty("iFrame"))));

			// Click Attachments
			driver.findElement(By.id(prop.getProperty("Attachments-btn"))).click();
			Thread.sleep(2000);

			// Switch frame
			driver.switchTo().frame(driver.findElement(By.xpath(prop.getProperty("Attachments-iFrame"))));

			// Click AddFile
			driver.findElement(By.id(prop.getProperty("Attachments-addFile"))).click();

			// Robot class to handle local folder
			Robot r = new Robot();

			// copy the text into clipboard memory
			StringSelection filepath = new StringSelection(System.getProperty("user.dir")+"\\Readme\\Mainspring-Defect.txt");
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(filepath, null);

			Thread.sleep(2000);
			r.keyPress(KeyEvent.VK_CONTROL);
			Thread.sleep(2000);
			r.keyPress(KeyEvent.VK_V);
			Thread.sleep(2000);
			r.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(2000);
			r.keyPress(KeyEvent.VK_ENTER);
			Thread.sleep(2000);

			// Click check box
			driver.findElement(By.xpath(prop.getProperty("Attachments-checkbox"))).click();

			// Click delete
			driver.findElement(By.id(prop.getProperty("Attachments-deleteFile"))).click();

			// Click Ok for alert box
			Alert confirm = driver.switchTo().alert();
			confirm.accept();

			Thread.sleep(3000);
			
			// Click AddFile
			driver.findElement(By.id(prop.getProperty("Attachments-addFile"))).click();

			// copy the text into clipboard memory
			StringSelection filepath1 = new StringSelection(System.getProperty("user.dir")+"\\Readme\\Mainspring-Defect.txt");
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(filepath1, null);

			Thread.sleep(2000);
			r.keyPress(KeyEvent.VK_CONTROL);
			Thread.sleep(2000);
			r.keyPress(KeyEvent.VK_V);
			Thread.sleep(2000);
			r.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(2000);
			r.keyPress(KeyEvent.VK_ENTER);
			Thread.sleep(2000);

			// Switch back frame
			driver.switchTo().defaultContent();

			// Print if the Test Case Passed
			reportPass("Attachment file added and deleted successfully");

		} catch (Exception e) {
			// Print if the Test Case Failed
			e.printStackTrace();
			reportFail("Attachment Failed" + e.getMessage());
		}
	}

	// Automate Previous Version
	public void previousVersion() throws Exception {
		try {
			// Creating Test report for "openProject" function
			logger = report.createTest("Previous Version - Compare version");

			Thread.sleep(2000);

			// Switch frame
			driver.switchTo().frame(driver.findElement(By.xpath(prop.getProperty("iFrame"))));

			// Click on Previous Version
			driver.findElement(By.xpath(prop.getProperty("PreviousVersion-btn"))).click();

			// Switch frame
			driver.switchTo().frame(driver.findElement(By.xpath(prop.getProperty("PreviousVersion-iFrame"))));

			// Click on current version
			driver.findElement(By.xpath(prop.getProperty("PreviousVersion-currentVersion"))).click();

			// Click on previous version
			driver.findElement(By.xpath(prop.getProperty("PreviousVersion-previousVersion"))).click();

			// Click on compare version button
			driver.findElement(By.xpath(prop.getProperty("PreviousVersion-compareVersion-btn"))).click();

			// Window handles
			ArrayList<String> lst = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(lst.get(1));

			// Click Submit
			driver.findElement(By.xpath(prop.getProperty("PreviousVersion-submit-btn"))).click();
			driver.switchTo().window(lst.get(0));

			// Switch back frame
			driver.switchTo().defaultContent();

			// Print if the Test Case Passed
			reportPass("Version compared successfully");

		} catch (Exception e) {
			// Print if the Test Case Failed
			e.printStackTrace();
			reportFail("Previous Version Failed" + e.getMessage());
		}
	}

	// Automate Activity Log
	public void activityLog() throws InterruptedException {
		try {
			// Creating Test report for "openProject" function
			logger = report.createTest("Activity Log - List of Activity");

			Thread.sleep(4000);

			// Switch frame
			driver.switchTo().frame(driver.findElement(By.xpath(prop.getProperty("iFrame"))));

			// Click on "Activity Log"
			driver.findElement(By.xpath(prop.getProperty("ActivityLog-btn"))).click();

			// Switch Frame
			driver.switchTo().frame(driver.findElement(By.xpath(prop.getProperty("ActivityLog-iFrame"))));
			
			// Print the List of Activity made
			List<WebElement> lst1 = driver.findElements(By.xpath(prop.getProperty("ActivityLog-Lists")));
			
			for (WebElement x : lst1) {
				reportInfo(x.getText());
			}
			
			//Click on "Current Version"
			WebDriverWait waits=new WebDriverWait(driver, 30);
			WebElement currentVersion=waits.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("ActivityLog-currentVersion"))));
			
			currentVersion.click();

			Thread.sleep(4000);
			
			//Handle Windows
			ArrayList<String> lst=new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(lst.get(1));
			WebElement return_button=waits.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("ActivityLog-submit-btn"))));
			return_button.click();
			driver.switchTo().window(lst.get(0));

			// Switch back Frame
			driver.switchTo().defaultContent();

			// Print if the Test Case Passed
			reportPass("Activity printed.");

		} catch (Exception e) {
			// Print if the Test Case Failed
			e.printStackTrace();
			reportFail("Unable to invoke the activityLog" + e.getMessage());
		}
	}
}
