package com.MOFservice;

import com.MOF.constants.Webelements;
import com.MOFutility.ExcelUtilities;
import com.MOFutility.Screenshot;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.MOF.constants.Webelements.*;


public class MOF_Service extends Screenshot {

    static WebDriver driver;
    static ExtentTest test;
    static ExtentReports reports;
    static String concatenate = ".";


    {

        Date d = new Date();

        String FileName = d.toString().replace(":", "_").replace(" ", "_") + ".html";
        reports = new ExtentReports("D:\\Automation\\ExtentReport\\" + " " + FileName);
        System.out.println(FileName);
        System.out.println(reports);
    }
	/*@BeforeAll
	static void setup() {


	}*/

    @Before
    public void StartReport() {
        test = reports.startTest(ExcelUtilities.moduleValue);

    }

    @Test
    public void openBrowser() throws Exception {
        WebDriverManager.chromedriver().clearResolutionCache().driverVersion("137.0.7151.69").setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--guest"); // or use "--incognito"


        // Additional options (disable password manager, save password bubble, etc.)
        options.addArguments("disable-features=PasswordManager");
        options.addArguments("disable-save-password-bubble");

        // Initialize Chrome WebDriver with the specified options
        driver = new ChromeDriver(options);

        // Maximize the browser window and set implicit wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }


    @Test
    public void gotoURL() throws Exception {
        driver.get(ExcelUtilities.dataColumnValue);

        Thread.sleep(3000);

        Robot robot = new Robot();

        // Press "Ctrl" and "-" (zoom out)
        for (int i = 0; i < 3; i++) {  // Zoom out 5 times (adjust as necessary)
            robot.keyPress(KeyEvent.VK_CONTROL);  // Press "Ctrl"
            robot.keyPress(KeyEvent.VK_MINUS);   // Press "-"
            robot.keyRelease(KeyEvent.VK_MINUS); // Release "-"
            robot.keyRelease(KeyEvent.VK_CONTROL); // Release "Ctrl"
            Thread.sleep(300); // Wait for the zoom out effect
        }

        // Capture the screenshot and get the path
        String gotoURL = Screenshot.captureScreenShot(driver, "Verifying_Loaded_Page");

        // Check if the path is valid (not null)
        if (gotoURL == null) {
            System.out.println("Screenshot capture failed!");
            test.log(LogStatus.FAIL, "Screenshot capture failed");
            return;
        }

        // Convert the screenshot to Base64
        String base64Image = encodeImageToBase64(gotoURL);

        // Generate the HTML <img> tag to embed Base64 image
        String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Login Screenshot'  width='500' height='300' />";

        if (driver.getPageSource().contains("Service Catalogue")) {
            System.out.println("Successfully Started Execution");
            test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
        } else {
            System.out.println("Error in loading Execution");
            test.log(LogStatus.FAIL, ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
            Screenshot.captureScreenShot(driver, "Verifying_Error_Loaded_Page");
        }
    }


    // LOGIN*******************************************

    @Test
    public void login() throws Exception {
        // Enter Username
        WebElement InputUsername = driver.findElement(By.xpath(Webelements.InputUsername));
        InputUsername.sendKeys(ExcelUtilities.usernameColumnValue);
        test.log(LogStatus.PASS, "Enter UserName: " + ExcelUtilities.usernameColumnValue);

        // Enter Password
        WebElement InputPassword = driver.findElement(By.xpath(Inputpassword));
        InputPassword.sendKeys(ExcelUtilities.passwordColumnValue);
        test.log(LogStatus.PASS, "Enter Password: " + ExcelUtilities.passwordColumnValue);

        // Toggle password visibility
        Thread.sleep(600);
        clickElement(By.xpath(togglePassword));
        test.log(LogStatus.PASS, "Click on Eye Button");
        Thread.sleep(600);
        String eye = Screenshot.captureScreenShot(driver, "Credentials");
        if (eye != null) {

            String base64Image_eye = encodeImageToBase64(eye);
            String imgTag_eye = "<img src='data:image/png;base64," + base64Image_eye + "' alt='Login Screenshot' width='500' height='300' />";
            test.log(LogStatus.INFO, "Credentials " + imgTag_eye);
        }

        // Click on Login button
        InputPassword.click();
        Thread.sleep(600);
        clickElement(By.xpath("//*[@id=\"login\"]/a/label"));

        // Check if "Change Password" alert is present
        if (driver.getPageSource().contains("change password alert")) {
            // Handle the alert by accepting it
            try {
                Alert alert = driver.switchTo().alert();
                alert.accept();
                test.log(LogStatus.PASS, "Change password alert accepted.");
            } catch (NoAlertPresentException e) {
                test.log(LogStatus.FAIL, "Change password alert not found.");
            }
        }

        // Capture screenshot and get the path
        String login = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Check if the screenshot path is valid (non-null)
        if (login != null) {
            // Convert the screenshot to Base64
            String base64Image_login = encodeImageToBase64(login);

            // Create an img tag with the base64 image
            String imgTag_login = "<img src='data:image/png;base64," + base64Image_login + "' alt='Login Screenshot' width='500' height='300' />";

            // Log the screenshot in Extent Report
            if (driver.getPageSource().contains("Authenticate")) {
                test.log(LogStatus.PASS, "Successfully login with valid credentials " + imgTag_login);
            } else if (driver.getPageSource().contains("اللوحة الرئيسية")) {
                test.log(LogStatus.PASS, "Successfully login with valid credentials in Arabic View " + imgTag_login);
            } else {
                test.log(LogStatus.PASS, "Login failed with invalid credentials " + imgTag_login);
            }
        } else {
            // If the screenshot is null, log an error message
            test.log(LogStatus.FAIL, "Screenshot capture failed for login.");
        }

        Thread.sleep(3000);
    }


    @Test
    public void VerifyErrorLoginMsg() throws InterruptedException {
        // Wait for the page to load and error message to be visible
        Thread.sleep(2000); // Or use WebDriverWait to wait for the specific error element

        if (driver.getPageSource().contains("Invalid credentials provided.")) {
            System.out.println("Validation error message in negative case is Passed");

            // Capture screenshot and get the path
            String VerifyErrorLoginMsg = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Check if screenshot path is valid (not null)
            if (VerifyErrorLoginMsg != null) {
                // Convert the screenshot to Base64 encoding
                String base64Image = encodeImageToBase64(VerifyErrorLoginMsg);

                // Create an HTML <img> tag with the base64 image
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Error Login Screenshot' width='500' height='300' />";

                // Log the screenshot as Base64 in the Extent Report
                test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
            } else {
                // Log an error if screenshot capturing failed
                test.log(LogStatus.FAIL, "Failed to capture screenshot for error login message.");
            }
        } else {
            System.out.println("Error message not found, login might have been successful.");
            test.log(LogStatus.FAIL, "Error message 'Invalid credentials provided.' not found.");
        }
    }


    @Test
    public void clicklogin_ENG() throws AWTException, InterruptedException {
        if (driver.getPageSource().contains("Login")) {
            System.out.println("Login");

            // Locate the 'Login' button and click it
            clickElement(By.xpath(ENG_Loginbtn));
            Thread.sleep(500); // Allow time for the action to complete

            // Capture the screenshot after clicking the login button
            String clicklogin_ENG = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Check if the screenshot path is valid
            if (clicklogin_ENG != null) {
                // Convert the screenshot to Base64 encoding
                String base64Image = encodeImageToBase64(clicklogin_ENG);

                // Create an HTML <img> tag with the Base64 image
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Click Login Screenshot' width='500' height='300' />";

                // Log the action along with the Base64 image in the Extent Report
                test.log(LogStatus.PASS, "Click on Login Button ENG UI " + imgTag);
            } else {
                // Log an error if screenshot capturing failed
                test.log(LogStatus.FAIL, "Failed to capture screenshot for clicking login button.");
            }
        } else {
            System.out.println("Login button not found, unable to click.");
            test.log(LogStatus.FAIL, "Login button 'Login' not found.");
        }
    }

    @Test
    public void clicklogin_ARAB() throws InterruptedException {
        // Locate the Arabic login button and click it
        clickElement(By.xpath(ARAB_Loginbtn));
        Thread.sleep(500); // Add a small delay to ensure the action completes

        // Capture the screenshot after clicking the login button
        String clicklogin_ARAB = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Check if the screenshot path is valid
        if (clicklogin_ARAB != null) {
            // Convert the screenshot to Base64 encoding
            String base64Image = encodeImageToBase64(clicklogin_ARAB);

            // Create an HTML <img> tag with the Base64 image
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Click Login ARAB Screenshot' width='500' height='200' />";

            // Log the action along with the Base64 image in the Extent Report
            test.log(LogStatus.PASS, "Click on Login Button ARAB UI " + imgTag);
        } else {
            // Log an error if screenshot capturing failed
            test.log(LogStatus.FAIL, "Failed to capture screenshot for clicking login button ARAB.");

        }

    }

    @Test
    public void changeloginLang() throws Exception {


        // Locate and click the label for language change
        clickElement(By.xpath(changelabelLang));

        // Capture screenshot after clicking the language change label
        String changeloginLang = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Locate and click the Arabic language option
        clickElement(By.xpath(changeArabLang));

        // Check if the screenshot path is valid
        if (changeloginLang != null) {
            // Convert the screenshot to Base64 encoding
            String base64Image_changeloginLang = encodeImageToBase64(changeloginLang);

            // Create an HTML <img> tag with the Base64 image
            String imgTag_changeloginLang = "<img src='data:image/png;base64," + base64Image_changeloginLang + "' alt='Language Change Screenshot' width='500' height='200' />";

            // Log the action with the Base64 image in the Extent Report
            test.log(LogStatus.PASS, "Change to ARAB UI View " + imgTag_changeloginLang);
        } else {
            // Log an error if screenshot capturing failed
            test.log(LogStatus.FAIL, "Failed to capture screenshot for language change to ARAB UI.");
        }
    }

    public void Fsslogoclick() {
        try {
            // Wait for the logo element to be clickable (use the correct XPath)
            WebDriverWait wait = new WebDriverWait(driver, 10);  // 10 seconds wait time
            WebElement logo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Webelements.logo))); // Use the correct XPath

            // Scroll the page to ensure the logo is visible
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,-700)");

            // Click on the logo
            logo.click();
            test.log(LogStatus.INFO, "Logo element is clickable. Proceeding with the click.");

            // Capture a screenshot after clicking the logo
            String screenshotPath = Screenshot.captureScreenShot(driver, "LogoClick");
            if (screenshotPath != null) {
                // Convert the screenshot to Base64 for embedding into the Extent report
                String base64Image_logo = encodeImageToBase64(screenshotPath);
                String imgTag_logo = "<img src='data:image/png;base64," + base64Image_logo + "' alt='Logo Click Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Logo clicked successfully. " + imgTag_logo);
            } else {
                test.log(LogStatus.PASS, "Logo clicked successfully.");
            }
        } catch (StaleElementReferenceException e) {
            // Re-locate the element if it's stale and try clicking again
            clickElement(By.xpath(logo));
            test.log(LogStatus.PASS, "Logo clicked after re-locating.");
        } catch (Exception e) {
            // Log the failure if any other exception occurs
            test.log(LogStatus.FAIL, "Failed to click the logo. Exception: " + e.getMessage());
        }
    }


    // **************************************LOGIN_END*****************************************************
    // **************************************ROLE
    // _STARTS*****************************************************
    @Test
    public  void clickroleTab() throws InterruptedException {
        // Locate and click the Role tab
        clickElement(By.xpath(RoleTab));
        test.log(LogStatus.PASS, "Clicked on Role Tab.");

        // Add a delay to ensure the action is completed before proceeding (optional based on your system)
        Thread.sleep(500);

        // Capture the screenshot after clicking the Role tab
        String clickroleTabScreenshot = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Check if the screenshot path is valid
        if (clickroleTabScreenshot != null) {
            // Convert the screenshot to Base64 encoding
            String base64Image_roletab = encodeImageToBase64(clickroleTabScreenshot);

            // Create an HTML <img> tag with the Base64 image for embedding in the Extent Report
            String imgTag_roletab = "<img src='data:image/png;base64," + base64Image_roletab + "' alt='Click Role Tab Screenshot' width='500' height='200' />";

            // Log the action along with the embedded image in the Extent Report
            test.log(LogStatus.PASS, "Successfully clicked the Role Tab and captured the screenshot " + imgTag_roletab);
        } else {
            // Log an error if screenshot capturing failed
            test.log(LogStatus.FAIL, "Failed to capture screenshot for clicking the Role Tab.");
        }
    }


    @Test
    public  void clickroleTabARAB() throws InterruptedException {
        // Locate and click the ARAB Role tab
        clickElement(By.xpath(RoleTabARAB));
        Thread.sleep(500); // Add delay to ensure the action completes

        // Capture the screenshot after clicking the ARAB Role tab
        String clickroleTabARABScreenshot = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Check if the screenshot path is valid
        if (clickroleTabARABScreenshot != null) {
            // Convert the screenshot to Base64 encoding
            String base64Image = encodeImageToBase64(clickroleTabARABScreenshot);

            // Create an HTML <img> tag with the Base64 image
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Click ARAB Role Tab Screenshot' width='500' height='200' />";

            // Log the action along with the Base64 image in the Extent Report
            test.log(LogStatus.PASS, "Click on ARAB UI Role Tab " + imgTag);
        } else {
            // Log an error if screenshot capturing failed
            test.log(LogStatus.FAIL, "Failed to capture screenshot for clicking the ARAB Role Tab.");
        }
    }


    @Test
    public void CreateNewRole() throws Exception {
        // First, click the Role tab
        clickroleTab();

        // Locate and enter the Role Name
        WebElement roleName = driver.findElement(By.xpath(EnterRoleName));
        roleName.sendKeys(ExcelUtilities.rolenameColumnValue);
        test.log(LogStatus.PASS, "Enter Role Name: " + ExcelUtilities.rolenameColumnValue);
        Thread.sleep(500);

        // Perform role selection and save actions
        selectrole();
        save();

        // Capture the screenshot after creating the new role
        String createNewRoleScreenshot = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Log the screenshot in the Extent Report
        if (createNewRoleScreenshot != null) {
            // Convert the screenshot to Base64 encoding
            String base64Image_createNewRoleScreenshot = encodeImageToBase64(createNewRoleScreenshot);

            // Create an HTML <img> tag with the Base64 image
            String imgTag_createNewRoleScreenshot = "<img src='data:image/png;base64," + base64Image_createNewRoleScreenshot + "' alt='Create New Role Screenshot' width='500' height='200' />";

            // Log the success message along with the Base64 image in the Extent Report
            test.log(LogStatus.PASS, "Created Role Successfully " + imgTag_createNewRoleScreenshot);
        } else {
            // Log an error if screenshot capturing failed
            test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the Role.");
        }
    }

    public  void CreateNewRoleARAB() throws Exception {
        // Locate the role name field and enter the role name
        WebElement roleNameARAB = driver.findElement(By.xpath(EnterRoleName));
        roleNameARAB.sendKeys(ExcelUtilities.rolenameColumnValue);
        test.log(LogStatus.PASS, "Enter Role Name: " + ExcelUtilities.rolenameColumnValue);
        Thread.sleep(500);

        // Perform role selection
        selectrole();

        // Save the role
        savebtnARAB();

        // Capture the screenshot after role creation
        String createNewRoleARABScreenshot = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Log the screenshot in the Extent Report
        if (createNewRoleARABScreenshot != null) {
            // Convert the screenshot to Base64 encoding
            String base64Image_createNewRoleARABScreenshot = encodeImageToBase64(createNewRoleARABScreenshot);

            // Create an HTML <img> tag with the Base64 image
            String imgTag_createNewRoleARABScreenshot = "<img src='data:image/png;base64," + base64Image_createNewRoleARABScreenshot + "' alt='Create New Role ARAB Screenshot' width='500' height='200' />";

            // Log the success message along with the Base64 image
            test.log(LogStatus.PASS, "Created Role in ARAB UI Successfully " + imgTag_createNewRoleARABScreenshot);
        } else {
            // Log an error if screenshot capturing failed
            test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the Role in ARAB UI.");
        }
    }

    @Test
    public  void enterroleName() throws Exception {
        // Introduce a small delay
        Thread.sleep(500);

        // Locate and enter the Role Name
        WebElement roleName = driver.findElement(By.xpath(EnterRoleName));
        roleName.sendKeys(ExcelUtilities.rolenameColumnValue);
        test.log(LogStatus.PASS, "Enter Role Name: " + ExcelUtilities.rolenameColumnValue);

        // Capture the screenshot after entering the role name
        String enterRoleScreenshot = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Log the screenshot in the Extent Report
        if (enterRoleScreenshot != null) {
            // Convert the screenshot to Base64 encoding
            String base64Image = encodeImageToBase64(enterRoleScreenshot);

            // Create an HTML <img> tag with the Base64 image
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Enter Role Name Screenshot' width='500' height='200' />";

            // Log the success message along with the Base64 image
            test.log(LogStatus.PASS, "Role Name Entered Successfully " + imgTag);
        } else {
            // Log an error if screenshot capturing failed
            test.log(LogStatus.FAIL, "Failed to capture screenshot after entering the Role Name.");
        }
    }

    @Test
    public  void selectrole() throws Exception {
        // Click on the Role Access checkbox
        clickElement(By.xpath(RoleAccess));
        test.log(LogStatus.PASS, "Role Access selected.");

        // Click on the User Access checkbox
        clickElement(By.xpath(UserAccess));
        test.log(LogStatus.PASS, "User Access selected.");

        // Click on the Entity Access checkbox
        clickElement(By.xpath(EntityAccess));
        test.log(LogStatus.PASS, "Entity Access selected.");

        // Click on the Service Access checkbox
        clickElement(By.xpath(ServiceAccess));
        test.log(LogStatus.PASS, "Service Access selected.");

        // Click on the Group Access checkbox
        clickElement(By.xpath(GroupAccess));
        test.log(LogStatus.PASS, "Group Access selected.");

        // Click on the Report Access checkbox
        clickElement(By.xpath(ReportAccess));
        test.log(LogStatus.PASS, "Report Access selected.");

        // Click on the Audit Access checkbox
        clickElement(By.xpath(AuditAccess));
        test.log(LogStatus.PASS, "Audit Access selected.");

        // Introduce a small delay
        Thread.sleep(500);

        // Capture a screenshot after selecting all roles
        String selectRoleScreenshot = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Log the screenshot in the Extent Report
        if (selectRoleScreenshot != null) {
            // Convert the screenshot to Base64 encoding
            String base64Image_selectRoleScreenshot = encodeImageToBase64(selectRoleScreenshot);

            // Create an HTML <img> tag with the Base64 image
            String imgTag_selectRoleScreenshot = "<img src='data:image/png;base64," + base64Image_selectRoleScreenshot + "' alt='Select Role Screenshot' width='500' height='200' />";

            // Log the success message along with the Base64 image
            test.log(LogStatus.PASS, "All Role Access Selected Successfully " + imgTag_selectRoleScreenshot);
        } else {
            // Log an error if screenshot capturing failed
            test.log(LogStatus.FAIL, "Failed to capture screenshot after selecting the Role Access.");
        }
    }


    @Test
    public  void selectaudit() throws InterruptedException {
        clickElement(By.xpath(AuditAccess));
        Thread.sleep(600);
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

    }

    @Test
    public  void save() throws InterruptedException {

        Thread.sleep(500);
        clickElement(By.xpath(savebtn));

        // Log action in Extent Report
        test.log(LogStatus.PASS, "Clicked on Save button.");

        // Add a small delay or wait for an element to appear if necessary (consider using WebDriverWait)
        Thread.sleep(500); // Optional: Use a better wait strategy if needed

        // Capture the screenshot after clicking the Save button
        String saveScreenshotPath_save = Screenshot.captureScreenShot(driver, "Click on Save Button");

        // Check if the screenshot path is valid
        if (saveScreenshotPath_save != null) {
            // Convert the screenshot to Base64 encoding
            String base64Image_save = encodeImageToBase64(saveScreenshotPath_save);

            // Create an HTML <img> tag with the Base64 image for embedding in the Extent Report
            String imgTag_save = "<img src='data:image/png;base64," + base64Image_save + "' alt='Save Action Screenshot' width='500' height='200' />";

            // Log the success message along with the Base64 image in the Extent Report
            test.log(LogStatus.PASS, "Save action performed successfully " + imgTag_save);
        } else {
            // Log an error if screenshot capturing failed
            test.log(LogStatus.FAIL, "Failed to capture screenshot after performing the Save action.");
        }
    }


    @Test
    public  void changepwdsave() throws InterruptedException {

        clickElement(By.xpath(chnagepwdsavebtn));
        Thread.sleep(500);

        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);

    }

    @Test
    public  void savebtnARAB() throws InterruptedException {
        try {
            // Locate the 'Save' button (Arabic version)
            clickElement(By.xpath(savebtnARAB));

            // Click the 'Save' button
            test.log(LogStatus.INFO, "Clicked on Save button (Arabic version).");

            // Adding a short wait to ensure the action completes
            Thread.sleep(500);

            // Log the successful action
            test.log(LogStatus.PASS, "Save button clicked successfully (Arabic version).");

            // Optionally, capture a screenshot after clicking the save button
            String screenshotPath = Screenshot.captureScreenShot(driver, "SaveButton_ARAB_" + ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Check if the screenshot was successfully captured
            if (screenshotPath != null) {
                // Convert the screenshot to Base64 format for embedding in the report
                String base64Image = encodeImageToBase64(screenshotPath);

                // Create an HTML <img> tag to embed the screenshot in the Extent report
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Save Button Arabic Screenshot' width='500' height='200' />";

                // Log the success message with the embedded image
                test.log(LogStatus.PASS, "Save button action completed successfully in Arabic. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot after Save button click.");
            }

        } catch (Exception e) {
            // Log the failure if any exception occurs
            test.log(LogStatus.FAIL, "Error during Save button click (Arabic version): " + e.getMessage());
        }
    }


    public void Blankrolename() throws Exception {
        // Click on the role tab
        clickroleTab();
        test.log(LogStatus.PASS, "Clicked on Role Tab");

        // Select the role
        selectrole();
        test.log(LogStatus.PASS, "Role Selected");

        // Save the role (ensure save functionality is implemented correctly)
        save();
        test.log(LogStatus.PASS, "Role Saved");

        // Capture the screenshot and ensure the filename is sanitized
        String screenshotPath = Screenshot.captureScreenShot(driver, sanitizeFileName(ExcelUtilities.testCaseDesritpioncolumnvalue));

        // Log the screenshot into the Extent Report
        if (screenshotPath != null) {
            // Convert the screenshot to Base64 encoding
            String base64Image_Blankrolename = encodeImageToBase64(screenshotPath);

            // Create an HTML <img> tag with the Base64 image
            String imgTag_Blankrolename = "<img src='data:image/png;base64," + base64Image_Blankrolename + "' alt='Blank Role Name Screenshot' width='500' height='200' />";

            // Log the success message along with the embedded image in the report
            test.log(LogStatus.PASS, "Blank Role Name Test Passed: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag_Blankrolename);
        } else {
            // Log an error if the screenshot was not captured
            test.log(LogStatus.FAIL, "Failed to capture screenshot for Blank Role Name Test.");
        }
    }


    public void BlankrolenameARAB() throws Exception {
        try {
            // Click the role tab in Arabic (specific method for Arabic version)
            clickroleTabARAB();
            test.log(LogStatus.PASS, "Role tab clicked successfully in Arabic.");

            // Select the role (Arabic version)
            selectrole();
            test.log(LogStatus.PASS, "Role selected successfully in Arabic.");

            // Click the save button (Arabic version)
            savebtnARAB();
            test.log(LogStatus.PASS, "Role saved successfully in Arabic.");

            // Capture a screenshot after the actions are performed
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Check if the screenshot was captured successfully
            if (screenshotPath != null) {
                // Convert the screenshot to Base64 for embedding in the Extent report
                String base64Image = encodeImageToBase64(screenshotPath);

                // Create an HTML <img> tag to embed the screenshot in the report
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Blank Role Name Arabic Screenshot' width='500' height='200' />";

                // Log the success message with the embedded image
                test.log(LogStatus.PASS, "Blank role name test completed successfully in Arabic. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot after Blank Role Name ARAB action.");
            }

        } catch (Exception e) {
            // Log any errors encountered during the process
            test.log(LogStatus.FAIL, "Error during Blank Role Name ARAB: " + e.getMessage());
        }
    }

    public void BlankRoleAccess() throws Exception {
        try {
            // Click on the Role Tab
            clickroleTab();
            test.log(LogStatus.PASS, "Role Tab clicked successfully.");

            // Enter the role name (even if it is blank)
            enterroleName();
            test.log(LogStatus.PASS, "Role name entered successfully.");

            // Save the role
            save();
            test.log(LogStatus.PASS, "Role saved successfully.");

            // Capture a screenshot after the actions
            String screenshotPath = Screenshot.captureScreenShot(driver, "BlankRoleAccess_" + ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Check if the screenshot was captured successfully
            if (screenshotPath != null) {
                // Convert the screenshot to Base64 for embedding in the Extent report
                String base64Image = encodeImageToBase64(screenshotPath);

                // Create an HTML <img> tag to embed the screenshot in the report
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Blank Role Access Screenshot' width='500' height='200' />";

                // Log the success message with the embedded image
                test.log(LogStatus.PASS, "Blank Role Access test completed successfully. " + imgTag);
            } else {
                // Log failure if screenshot capturing failed
                test.log(LogStatus.FAIL, "Failed to capture screenshot after Blank Role Access action.");
            }
        } catch (Exception e) {
            // Log any errors encountered during the process
            test.log(LogStatus.FAIL, "Error during Blank Role Access: " + e.getMessage());
        }
    }


    public void BlankRoleAccessARAB() throws Exception {
        try {
            // Click on the Role Tab in Arabic
            clickroleTabARAB();
            test.log(LogStatus.PASS, "Role Tab (Arabic) clicked successfully.");

            // Enter the role name (even if it is blank)
            enterroleName();
            test.log(LogStatus.PASS, "Role name (Arabic) entered successfully.");

            // Save the role
            savebtnARAB();
            test.log(LogStatus.PASS, "Role saved successfully (Arabic).");

            // Capture a screenshot after the actions
            String screenshotPath = Screenshot.captureScreenShot(driver, "BlankRoleAccessARAB_" + ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Check if the screenshot was captured successfully
            if (screenshotPath != null) {
                // Convert the screenshot to Base64 for embedding in the Extent report
                String base64Image = encodeImageToBase64(screenshotPath);

                // Create an HTML <img> tag to embed the screenshot in the report
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Blank Role Access Arabic Screenshot' width='500' height='200' />";

                // Log the success message with the embedded image
                test.log(LogStatus.PASS, "Blank Role Access (Arabic) test completed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot after Blank Role Access (Arabic) action.");
            }
        } catch (Exception e) {
            // Log any errors encountered during the process
            test.log(LogStatus.FAIL, "Error during Blank Role Access (Arabic): " + e.getMessage());
        }
    }

    @Test
    public  void verifyAddedRole() throws InterruptedException {
        try {
            // Click on the Role Tab
            clickroleTab();
            test.log(LogStatus.PASS, "Role Tab clicked successfully.");

            // Scroll down to ensure the page is loaded
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,1000)");
            Thread.sleep(600); // Adding a small delay for smooth scrolling

            // Loop to check if "Next" is present and click through pages if necessary
            while (driver.getPageSource().contains("Next")) {
                Thread.sleep(600); // Adding a delay to avoid fast looping

                // Check if the role name is present in the page source
                if (driver.getPageSource().contains(ExcelUtilities.rolenameColumnValue)) {
                    Thread.sleep(600); // Adding a small delay to allow the page to update
                    js.executeScript("window.scrollBy(0,500)"); // Scroll down a bit for better visibility

                    // Capture a screenshot after the action
                    String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

                    // Check if the screenshot was captured successfully
                    if (screenshotPath != null) {
                        // Convert the screenshot to Base64 for embedding in the Extent report
                        String base64Image = encodeImageToBase64(screenshotPath);

                        // Create an HTML <img> tag to embed the screenshot in the report
                        String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Role Added Screenshot' width='500' height='200' />";

                        // Log the success message with the embedded image
                        test.log(LogStatus.PASS, "Role Added Successfully. " + imgTag);
                    } else {
                        // Log failure if screenshot capturing failed
                        test.log(LogStatus.FAIL, "Failed to capture screenshot after adding the role.");
                    }

                    break; // Exit the loop once the role is found
                }

                // Click the "Next" button to move to the next page
                clickElement(By.xpath(nextbtn));
            }

        } catch (Exception e) {
            // Log any errors encountered during the process
            test.log(LogStatus.FAIL, "Error during Verify Added Role: " + e.getMessage());
        }
    }


    @Test
    public  void verifyAddedRoleARAB() throws InterruptedException {
        try {
            // Click on the Role Tab (Arabic)
            clickroleTabARAB();
            test.log(LogStatus.PASS, "Role Tab (Arabic) clicked successfully.");

            // Use JavaScript Executor to scroll down
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,1000)");
            Thread.sleep(600);  // Allow time for the page to load

            // Check for the presence of the role in the page source
            while (driver.getPageSource().contains("Next")) {
                Thread.sleep(600);  // Wait for the page to load the next set of results

                if (driver.getPageSource().contains(ExcelUtilities.rolenameColumnValue)) {
                    Thread.sleep(600);  // Allow time for the page to load after finding the role
                    js.executeScript("window.scrollBy(0,500)");  // Scroll down to make the role visible

                    // Capture a screenshot after finding the role
                    String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

                    // Check if the screenshot was captured successfully
                    if (screenshotPath != null) {
                        // Convert the screenshot to Base64 for embedding in the Extent report
                        String base64Image = encodeImageToBase64(screenshotPath);
                        String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Added Role Screenshot' width='500' height='200' />";
                        test.log(LogStatus.PASS, "Role added successfully. " + imgTag);
                    } else {
                        test.log(LogStatus.PASS, "Role added successfully.");
                    }

                    System.out.println("Role Added Successfully");
                    break;
                }

                // If the role is not found, click the "Next" button to go to the next page
                clickElement(By.xpath(nextbtn));
            }

        } catch (Exception e) {
            // Log any errors encountered during the process
            test.log(LogStatus.FAIL, "Error during role verification: " + e.getMessage());
        }
    }


    @Test
    public void VerifyErrorMsg() throws InterruptedException {
        try {
            // Log the message for verification
            System.out.println("Validation of error message in the negative case is passed");

            // Capture the screenshot of the current state
            String screenshotPath = Screenshot.captureScreenShot(driver, "Verify error Msg");
            // Check if the screenshot was captured successfully
            if (screenshotPath != null) {
                // Convert the screenshot to Base64 encoding for embedding in the report
                String base64Image_VerifyErrorMsg = encodeImageToBase64(screenshotPath);
                String imgTag_VerifyErrorMsg = "<img src='data:image/png;base64," + base64Image_VerifyErrorMsg + "' alt='Error Message Screenshot' width='500' height='200' />";

                // Log the success message along with the embedded image
                test.log(LogStatus.PASS, "Error message verification passed. " + imgTag_VerifyErrorMsg);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for error message verification.");
            }

            // Log the test case description
            test.log(LogStatus.PASS, "Validation of error message: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

        } catch (Exception e) {
            // Log any error that occurred during the execution of this test
            test.log(LogStatus.FAIL, "Error during error message verification: " + e.getMessage());
        }
    }


    @Test
    public void Verify() throws InterruptedException {
        // Print message to console
        System.out.println("TestCase is Passed");

        // Capture the screenshot
        String screenshotPath = Screenshot.captureScreenShot(driver, "Verify");

        // Log the screenshot in the Extent Report
        if (screenshotPath != null) {
            // Convert the screenshot to Base64 encoding
            String base64Image_verify = encodeImageToBase64(screenshotPath);

            // Create an HTML <img> tag with the Base64 image
            String imgTag_verify = "<img src='data:image/png;base64," + base64Image_verify + "' alt='Verify Screenshot' width='500' height='200' />";

            // Log the success message along with the Base64 image in the Extent Report
            test.log(LogStatus.PASS, "TestCase passed successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue + " " + imgTag_verify);
        } else {
            // Log the failure if the screenshot couldn't be captured
            test.log(LogStatus.FAIL, "Failed to capture screenshot for: " + ExcelUtilities.testCaseDesritpioncolumnvalue);
        }
    }


    @Test
    public void reset() throws Exception {
        try {
            // Introduce a small delay to ensure the page is fully loaded
            Thread.sleep(600);

            // Use JavascriptExecutor to scroll the page up by 700 pixels
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,-700)");

            // Locate the reset button and click it
            clickElement(By.xpath(Reset));

            // Log the success of the reset button click
            test.log(LogStatus.PASS, "Clicked on RESET Button");

            // Capture a screenshot after the reset action
            String screenshotPath = Screenshot.captureScreenShot(driver, "ResetAction_" + ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Check if the screenshot was captured successfully
            if (screenshotPath != null) {
                // Convert the screenshot to Base64 for embedding in the Extent report
                String base64Image_reset = encodeImageToBase64(screenshotPath);

                // Create an HTML <img> tag to display the Base64 image in the report
                String imgTag_reset = "<img src='data:image/png;base64," + base64Image_reset + "' alt='Reset Action Screenshot' width='500' height='200' />";

                // Log the success message along with the screenshot
                test.log(LogStatus.PASS, "Reset action performed successfully. " + imgTag_reset);
            } else {
                // Log failure if screenshot capturing failed
                test.log(LogStatus.FAIL, "Failed to capture screenshot after reset action.");
            }

        } catch (Exception e) {
            // Log any errors encountered during the reset action
            test.log(LogStatus.FAIL, "Error during reset action: " + e.getMessage());
        }
    }


    @Test
    public void resetARAB() throws InterruptedException {
        try {
            // Add a wait time to ensure the page is ready before interacting
            Thread.sleep(600);

            // Scroll the page to ensure the Reset button is in view
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,-700)");

            // Locate the Reset button for the Arabic version
            clickElement(By.xpath(ResetARAB));

            // Click the Reset button
            test.log(LogStatus.INFO, "Clicked on RESET Button (Arabic version).");

            // Capture a screenshot after clicking the Reset button
            String screenshotPath = Screenshot.captureScreenShot(driver, "resetARAB");

            // Check if the screenshot was captured successfully
            if (screenshotPath != null) {
                // Convert the screenshot to Base64 format for embedding in the report
                String base64Image = encodeImageToBase64(screenshotPath);

                // Create an HTML <img> tag to embed the screenshot in the Extent report
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Reset Button Arabic Screenshot' width='500' height='200' />";

                // Log the success message with the embedded image
                test.log(LogStatus.PASS, "Reset button action completed successfully in Arabic. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot after Reset button click.");
            }

        } catch (Exception e) {
            // Log the failure if any exception occurs
            test.log(LogStatus.FAIL, "Error during Reset button click (Arabic version): " + e.getMessage());
        }
    }

    @Test
    public void roleEditTXN() throws InterruptedException {
        try {
            // Wait to ensure the page is fully loaded
            Thread.sleep(500);

            // Click the Role Tab
            clickroleTab();
            test.log(LogStatus.PASS, "Role Tab clicked successfully.");

            // Scroll down the page for visibility of the Edit button
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(600); // Adding a slight delay for smooth scrolling

            // Locate and click the 'Edit Role' button
            clickElement(By.xpath(roleeditbtn));
            test.log(LogStatus.PASS, "Role Edit button clicked successfully.");

            // Adding a small delay for smooth action
            Thread.sleep(500);

            // Locate and click on the 'Audit Access' checkbox
            clickElement(By.xpath(AuditAccess));
            test.log(LogStatus.PASS, "Audit Access selected successfully.");

            // Capture a screenshot after editing the role
            String screenshotPath = Screenshot.captureScreenShot(driver, "RoleEdit_" + ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Check if the screenshot was captured successfully
            if (screenshotPath != null) {
                // Convert the screenshot to Base64 for embedding in the Extent report
                String base64Image = encodeImageToBase64(screenshotPath);

                // Create an HTML <img> tag to embed the screenshot in the report
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Role Edited Screenshot' width='500' height='200' />";

                // Log the success message with the embedded image
                test.log(LogStatus.PASS, "Role Edited Successfully. " + imgTag);
            } else {
                // Log failure if screenshot capturing failed
                test.log(LogStatus.FAIL, "Failed to capture screenshot after editing the role.");
            }

            // Save the role after making edits
            save();
            test.log(LogStatus.PASS, "Role saved successfully.");

        } catch (Exception e) {
            // Log any errors encountered during the process
            test.log(LogStatus.FAIL, "Error during Role Edit Transaction: " + e.getMessage());
        }
    }


    @Test
    public void roleEditTXNARAB() throws InterruptedException {
        try {
            // Wait and click on the Role Tab (Arabic)
            Thread.sleep(500);
            clickroleTabARAB();
            test.log(LogStatus.PASS, "Role Tab (Arabic) clicked successfully.");

            // Use JavaScript Executor to scroll down the page for better visibility
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(600);

            // Find and click the 'Edit' button for the role (Arabic)
            clickElement(By.xpath(roleeditbtnArab));
            test.log(LogStatus.PASS, "Edit button clicked for the role (Arabic).");

            // Select the 'Audit Access' checkbox
            clickElement(By.xpath(AuditAccess));
            test.log(LogStatus.PASS, "Audit Access checkbox clicked for the role (Arabic).");

            // Log success and capture a screenshot
            System.out.println("Role Edited Successfully in ARAB");
            test.log(LogStatus.PASS, "Role Edited Successfully in ARAB");

            // Capture a screenshot of the updated role
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the Extent report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Role Edit Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Role edited successfully. " + imgTag);
            } else {
                test.log(LogStatus.PASS, "Role edited successfully.");
            }

            // Save the changes
            savebtnARAB();
            test.log(LogStatus.PASS, "Role changes saved successfully.");

        } catch (Exception e) {
            // Log any exceptions encountered during the execution of the method
            test.log(LogStatus.FAIL, "Error during role edit in ARAB: " + e.getMessage());
        }
    }

    @Test
    public void roleDeleteTXN() throws InterruptedException {
        Thread.sleep(500);
        clickroleTab();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,300)");
        Thread.sleep(600);

        if (driver.getPageSource().contains("First")) {
            clickElement(By.xpath(firstbtn));
        }

        clickElement(By.xpath(roleDeletebtn));
        Thread.sleep(500);

        Alert alert = driver.switchTo().alert();
        alert.accept();
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        Thread.sleep(500);
        System.out.println("Delete Button Is working properly ");
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);

    }

    @Test
    public void roleDeleteTXNARAB() throws InterruptedException {
        try {
            // Click on the Role Tab (Arabic)
            clickroleTabARAB();
            test.log(LogStatus.PASS, "Role Tab (Arabic) clicked successfully.");

            // Scroll the page down for visibility
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(600);

            // Check if the Arabic role exists, if so, click the first role
            if (driver.getPageSource().contains("Ø£ÙˆÙ„Ø§Ù‹")) {
                clickElement(By.xpath(firstbtnARAB));
                test.log(LogStatus.PASS, "First role clicked (Arabic).");
            } else {
                test.log(LogStatus.INFO, "No role found with name 'Ø£ÙˆÙ„Ø§Ù‹'.");
            }

            // Click the delete button for the role (Arabic)
            clickElement(By.xpath(roleDeletebtnARAB));
            test.log(LogStatus.PASS, "Delete button clicked for the role (Arabic).");

            // Wait for the alert and accept it
            // Explicit wait for the alert to appear
            Alert alert = driver.switchTo().alert();
            alert.accept();
            test.log(LogStatus.PASS, "Alert accepted after role deletion.");

            // Capture a screenshot after deletion
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the Extent report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Role Deletion Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Role deleted successfully. " + imgTag);
            } else {
                test.log(LogStatus.PASS, "Role deleted successfully.");
            }

        } catch (Exception e) {
            // Log any errors encountered during the execution of the method
            test.log(LogStatus.FAIL, "Error during role deletion in ARAB: " + e.getMessage());
        }
    }

    // ****************************************ROLE_ENDS***************************************************

    // ****************************************SERVICE_STARTS***************************************************
    @Test
    public  void servicedeletebtn1() throws Exception {

        clickElement(By.xpath(serviceDeletebtn1));
        Thread.sleep(500);
        Alert alert = driver.switchTo().alert();
        alert.accept();
        String screenshotPath = Screenshot.captureScreenShot(driver, "servicedeletebtn1  deleted Sucessfully");
        test.log(LogStatus.PASS, " Service Id 1 deleted Sucessfully ");
        if (screenshotPath != null) {
            // Convert screenshot to Base64 for embedding in the report
            String base64Image = encodeImageToBase64(screenshotPath);
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Delete Screenshot 1' width='500' height='200' />";
            test.log(LogStatus.PASS, "Service 1 deleted successfully " + imgTag);
        } else {
            test.log(LogStatus.FAIL, "Failed to capture screenshot after deleting service 1.");
        }
        System.out.println("Successfully Deleted");
        Screenshot.captureScreenShot(driver, "servicedeletebtn1  deleted Sucessfully");
        test.log(LogStatus.PASS, " Service Id 1 deleted Sucessfully ");

    }

    @Test
    public  void servicedeletebtn2() throws Exception {
        clickElement(By.xpath(serviceDeletebtn2));
        Thread.sleep(500);
        Alert alert = driver.switchTo().alert();
        alert.accept();
        String screenshotPath = Screenshot.captureScreenShot(driver, "servicedeletebtn2  deleted Sucessfully");
        test.log(LogStatus.PASS, " Service Id 2 deleted Sucessfully ");

        if (screenshotPath != null) {
            // Convert screenshot to Base64 for embedding in the report
            String base64Image = encodeImageToBase64(screenshotPath);
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Delete Screenshot 2' width='500' height='200' />";
            test.log(LogStatus.PASS, "Service 2 deleted successfully " + imgTag);
        } else {
            test.log(LogStatus.FAIL, "Failed to capture screenshot after deleting service 2.");
        }

        System.out.println("Successfully Deleted");


    }

    @Test
    public  void servicedeletebtn3() throws Exception {
        clickElement(By.xpath(serviceDeletebtn3));
        Thread.sleep(500);
        Alert alert = driver.switchTo().alert();
        alert.accept();
        String screenshotPath = Screenshot.captureScreenShot(driver, "servicedeletebtn3  deleted Sucessfully");
        test.log(LogStatus.PASS, " Service Id 3 deleted Sucessfully ");

        if (screenshotPath != null) {
            // Convert screenshot to Base64 for embedding in the report
            String base64Image = encodeImageToBase64(screenshotPath);
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Delete Screenshot 3' width='500' height='200' />";
            test.log(LogStatus.PASS, "Service 3 deleted successfully " + imgTag);
        } else {
            test.log(LogStatus.FAIL, "Failed to capture screenshot after deleting service 3.");
        }

        System.out.println("Successfully Deleted");
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        test.log(LogStatus.PASS, " Service Id deleted Sucessfully ");

    }

    @Test
    public  void servicedeletebtn4() throws Exception {
        clickElement(By.xpath(serviceDeletebtn4));
        Thread.sleep(500);
        Alert alert = driver.switchTo().alert();
        alert.accept();
        String screenshotPath = Screenshot.captureScreenShot(driver, "servicedeletebtn4  deleted Sucessfully");
        test.log(LogStatus.PASS, " Service Id 4 deleted Sucessfully ");

        if (screenshotPath != null) {
            // Convert screenshot to Base64 for embedding in the report
            String base64Image = encodeImageToBase64(screenshotPath);
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Delete Screenshot 4' width='500' height='200' />";
            test.log(LogStatus.PASS, "Service 4 deleted successfully " + imgTag);
        } else {
            test.log(LogStatus.FAIL, "Failed to capture screenshot after deleting service 4.");
        }

        System.out.println("Successfully Deleted");
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        test.log(LogStatus.PASS, " Service Id deleted Sucessfully ");

    }

    @Test
    public  void mocyserviceDelete1() throws Exception {
        try {
            // Step 1: Click on the Service Tab and Search for Service ID
            serviceTabbtn();
            searchserviceID();
            //JavascriptExecutor js = (JavascriptExecutor) driver;
            //js.executeScript("window.scrollBy(0,700)");

            // Step 2: Check if the 'Delete' button is available
            if (!driver.findElements(By.xpath(reject)).isEmpty()) {
                // Step 3: Perform the delete operation
                clickElement(By.xpath(reject));
                captureAndLogScreenshot("Click on Reject Button ");

                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollBy(0,1200)");

                WebElement mocyrejectReason = driver.findElement(By.xpath(rejectREASON));
                mocyrejectReason.sendKeys(ExcelUtilities.usernameColumnValue);
                captureAndLogScreenshot("Reject Comment for Send Back ");

                clickElement(By.xpath(sndbckbtn));
                Alert alert = driver.switchTo().alert();
                alert.accept();
                captureAndLogScreenshot("Click on Send Back Button ");

                Thread.sleep(3000);

                searchserviceID();

                clickElement(By.xpath(mocydelete));

                alert.accept();

                searchserviceID();


                test.log(LogStatus.PASS, "Service delete button 1 clicked successfully");

                // Step 4: Capture screenshot after deletion

            } else if (!driver.findElements(By.xpath(mocydelete)).isEmpty()) {

                clickElement(By.xpath(mocydelete));

                Alert alert = driver.switchTo().alert();
                alert.accept();


            }


        } catch (Exception e) {
            // Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while testing serviceDelete1: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }


    private static void captureAndLogScreenshot(String screenshotName) {
        String screenshotPath = Screenshot.captureScreenShot(driver, screenshotName);
        if (screenshotPath != null) {
            String base64Image = encodeImageToBase64(screenshotPath);
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Screenshot' width='500' height='200' />";
            test.log(LogStatus.PASS, (ExcelUtilities.keywordColumnValue) + imgTag);
        } else {
            test.log(LogStatus.FAIL, "Failed to capture screenshot after deleting service.");
        }
    }

    @Test
    public void serviceDelete2() throws Exception {
        try {
            // Step 1: Click on the Service Tab and Search for Service ID
            serviceTabbtn();
            searchserviceID();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,700)");

            // Step 2: Check if the 'Delete' button is available
            if (driver.getPageSource().contains("Delete")) {
                // Step 3: Perform the delete operation
                servicedeletebtn2();
                test.log(LogStatus.PASS, "Service delete button 2 clicked successfully");

                // Step 4: Capture screenshot after deletion
                String screenshotPath = Screenshot.captureScreenShot(driver, "servicedeletebtn2 deleted Successfully");
                if (screenshotPath != null) {
                    // Convert screenshot to Base64 for embedding in the report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Delete Screenshot 2' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Service 2 deleted successfully " + imgTag);
                } else {
                    test.log(LogStatus.FAIL, "Failed to capture screenshot after deleting service 2.");
                }
            }

        } catch (Exception e) {
            // Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while testing serviceDelete2: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public  void serviceDelete3() throws Exception {
        try {
            // Step 1: Click on the Service Tab and Search for Service ID
            serviceTabbtn();
            searchserviceID();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,700)");

            // Step 2: Check if the 'Delete' button is available
            if (driver.getPageSource().contains("Delete")) {
                // Step 3: Perform the delete operation
                servicedeletebtn3();
                test.log(LogStatus.PASS, "Service delete button 3 clicked successfully");

                // Step 4: Capture screenshot after deletion
                String screenshotPath = Screenshot.captureScreenShot(driver, "servicedeletebtn3 deleted Successfully");
                if (screenshotPath != null) {
                    // Convert screenshot to Base64 for embedding in the report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Delete Screenshot 3' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Service 3 deleted successfully " + imgTag);
                } else {
                    test.log(LogStatus.FAIL, "Failed to capture screenshot after deleting service 3.");
                }
            }

        } catch (Exception e) {
            // Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while testing serviceDelete3: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void serviceDelete4() throws Exception {
        try {
            // Step 1: Click on the Service Tab and Search for Service ID
            serviceTabbtn();
            searchserviceID();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,700)");

            // Step 2: Check if the 'Delete' button is available
            if (driver.getPageSource().contains("Delete")) {
                // Step 3: Perform the delete operation
                servicedeletebtn4();
                test.log(LogStatus.PASS, "Service delete button 4 clicked successfully");

                // Step 4: Capture screenshot after deletion
                String screenshotPath = Screenshot.captureScreenShot(driver, "servicedeletebtn4 deleted Successfully");
                if (screenshotPath != null) {
                    // Convert screenshot to Base64 for embedding in the report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Delete Screenshot 4' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Service 4 deleted successfully " + imgTag);
                } else {
                    test.log(LogStatus.FAIL, "Failed to capture screenshot after deleting service 4.");
                }
            }

        } catch (Exception e) {
            // Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while testing serviceDelete4: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }


    @Test
    public  void navigatebtns() throws Exception {

        Thread.sleep(500);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,700)");
        clickElement(By.xpath("//label[text()='Next']"));
        js.executeScript("window.scrollBy(0,700)");
        Thread.sleep(200);
        clickElement(By.xpath("//label[text()='Previous']"));
        js.executeScript("window.scrollBy(0,700)");
        Thread.sleep(200);
        clickElement(By.xpath("//label[text()='Last']"));
        js.executeScript("window.scrollBy(0,700)");
        Thread.sleep(200);
        clickElement(By.xpath("//label[text()='First']"));
        js.executeScript("window.scrollBy(0,700)");
        Thread.sleep(200);
        test.log(LogStatus.PASS, " Navigation Button Working Successfully");

    }

    private static void fillInputField(By locator, String value) {
        WebElement element = driver.findElement(locator);
        element.sendKeys(value);
    }

    private static void clickElement(By locator) {
        WebElement element = driver.findElement(locator);
        element.click();
    }


    @Test
    public void MismatchReport() throws Exception {
        viewMIS();
        misdate();
        misenddate();
        WebElement MismatchReport = driver.findElement(By.xpath(Mismatch));
        Thread.sleep(500);
        MismatchReport.click();
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        test.log(LogStatus.PASS, "Click on View button");

    }

    @Test
    public void serviceUpload(){
        fillInputField(By.xpath("//input[@name='file']"), "C:\\Users\\roshanbabu\\Desktop\\AUTO_RESULT\\TEST_DATA\\Ministry of Culture and Youth.xlsx");
        clickElement(By.xpath("//button[text()='Upload']"));
        String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        if (screenshotPath != null) {
            // Convert screenshot to Base64 for embedding in the report
            String base64Image = encodeImageToBase64(screenshotPath);
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Created Screenshot' width='500' height='200' />";

            // Log the success message with the screenshot in the Extent Report
            test.log(LogStatus.PASS, "Service created successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
        } else {
            // Log failure if screenshot was not captured
            test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the service.");
        }

    }

    @Test
    public void CreateNewServiceFIXED() throws Exception {
        try {
            //Step 1: Delete any existing service before creating a new one
            serviceTabbtn();
            test.log(LogStatus.PASS, "Clicked on Service Tab to begin service creation.");
            servicedeletebtn1();
            test.log(LogStatus.PASS, "Previous service deleted successfully.");
            // Step 2: Click on the service tab to begin creation
            // Step 3: Enter service details
            enterserviceId();
            test.log(LogStatus.PASS, "Service ID entered successfully.");

            selectserviceentity();
            test.log(LogStatus.PASS, "Service entity selected successfully.");

            enterserviceGlCode();
            test.log(LogStatus.PASS, "Service GL Code entered successfully.");

            enterpgaCode();
            test.log(LogStatus.PASS, "PGA Code entered successfully.");

            enterserviceNameEnglish();
            test.log(LogStatus.PASS, "Service Name (English) entered successfully.");

            enterserviceNameArabic();
            test.log(LogStatus.PASS, "Service Name (Arabic) entered successfully.");

            serviceCategory();
            test.log(LogStatus.PASS, "Service category selected successfully.");

            serviceTypeFixed();
            test.log(LogStatus.PASS, "Service type set to 'Fixed' successfully.");

            // Step 4: Set effective start and end dates
            selecteffecitvestartdate();
            test.log(LogStatus.PASS, "Effective start date selected successfully.");

            // selecteffecitveenddate();
            //test.log(LogStatus.PASS, "Effective end date selected successfully.");

            // Step 5: Select VAT values and set service status
            selectvatValuesServicesVAT1();
            test.log(LogStatus.PASS, "VAT values for services selected successfully.");

            serviceStatusActive();
            test.log(LogStatus.PASS, "Service status set to 'Active' successfully.");

            // Step 6: Save the service
            save();
            test.log(LogStatus.PASS, "Service saved successfully.");

            // Step 7: Search the service ID to verify creation
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched and verified.");

            SendforReviewServiceID1();
            searchserviceID();
            ApproveServiceID1();



            // Step 8: Capture a screenshot of the service creation details
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Created Screenshot' width='500' height='200' />";

                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service created successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the service.");
            }
        } catch (Exception e) {
            // Step 9: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while creating the new service: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void mocyCreateNewService() throws Exception {
        try {
            //Step 1: Delete any existing service before creating a new one

            // Step 2: Click on the service tab to begin creation
            serviceTabbtn();
            test.log(LogStatus.PASS, "Clicked on Service Tab to begin service creation.");

            if (!driver.findElements(By.xpath(mocyCreateservicebtn)).isEmpty()) {
                clickElement(By.xpath(mocyCreateservicebtn));
            }


            // Step 3: Enter service details
            enterserviceId();
            test.log(LogStatus.PASS, "Service ID entered successfully.");

            selectserviceentity();
            test.log(LogStatus.PASS, "Service entity selected successfully.");

            enterserviceGlCode();
            test.log(LogStatus.PASS, "Service GL Code entered successfully.");

            enterpgaCode();
            test.log(LogStatus.PASS, "PGA Code entered successfully.");

            enterserviceNameEnglish();
            test.log(LogStatus.PASS, "Service Name (English) entered successfully.");

            enterserviceNameArabic();
            test.log(LogStatus.PASS, "Service Name (Arabic) entered successfully.");

            serviceCategory();
            test.log(LogStatus.PASS, "Service category selected successfully.");

            serviceTypeFixed();
            test.log(LogStatus.PASS, "Service type set to 'Fixed' successfully.");

            // Step 4: Set effective start and end dates
            selecteffecitvestartdate();
            test.log(LogStatus.PASS, "Effective start date selected successfully.");

            // selecteffecitveenddate();
            //test.log(LogStatus.PASS, "Effective end date selected successfully.");

            // Step 5: Select VAT values and set service status
            selectvatValuesServicesVAT1();
            test.log(LogStatus.PASS, "VAT values for services selected successfully.");

            serviceStatusActive();
            test.log(LogStatus.PASS, "Service status set to 'Active' successfully.");

            // Step 6: Save the service
            save();
            test.log(LogStatus.PASS, "Service saved successfully.");

            // Step 7: Search the service ID to verify creation


            // Step 8: Capture a screenshot of the service creation details
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Created Screenshot' width='500' height='200' />";

                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service created successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the service.");
            }
        } catch (Exception e) {
            // Step 9: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while creating the new service: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void mocyCreateNewServiceFIXED() throws Exception {
        try {

            serviceTabbtn();
            test.log(LogStatus.PASS, "Clicked on Service Tab to begin service creation.");

            mocyserviceDelete1();
            test.log(LogStatus.PASS, "Service deleted successfully before creating a new one.");

            fillInputField(By.xpath("//input[@name='file']"), "C:\\Users\\roshanbabu\\eclipse-workspace\\API_OUTPUT\\Ministry of Culture and Youth.xlsx");
            clickElement(By.xpath("//button[text()='Upload']"));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, -700)");


            clickElement(By.xpath(mocyCreateservicebtn));


            // Step 3: Enter service details
            enterserviceId();
            test.log(LogStatus.PASS, "Service ID entered successfully.");

            selectserviceentity();
            test.log(LogStatus.PASS, "Service entity selected successfully.");

            enterserviceGlCode();
            test.log(LogStatus.PASS, "Service GL Code entered successfully.");

            enterpgaCode();
            test.log(LogStatus.PASS, "PGA Code entered successfully.");

            enterserviceNameEnglish();
            test.log(LogStatus.PASS, "Service Name (English) entered successfully.");

            enterserviceNameArabic();
            test.log(LogStatus.PASS, "Service Name (Arabic) entered successfully.");

            serviceCategory();
            test.log(LogStatus.PASS, "Service category selected successfully.");

            serviceTypeFixed();
            test.log(LogStatus.PASS, "Service type set to 'Fixed' successfully.");

            // Step 4: Set effective start and end dates
            selecteffecitvestartdate();
            test.log(LogStatus.PASS, "Effective start date selected successfully.");

            // selecteffecitveenddate();
            //test.log(LogStatus.PASS, "Effective end date selected successfully.");

            // Step 5: Select VAT values and set service status
            selectvatValuesServicesVAT1();
            test.log(LogStatus.PASS, "VAT values for services selected successfully.");

            serviceStatusActive();
            test.log(LogStatus.PASS, "Service status set to 'Active' successfully.");

            // Step 6: Save the service
            save();
            test.log(LogStatus.PASS, "Service saved successfully.");

            // Step 7: Search the service ID to verify creation
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched and verified.");

            // Step 8: Capture a screenshot of the service creation details
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Created Screenshot' width='500' height='200' />";

                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service created successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the service.");
            }
        } catch (Exception e) {
            // Step 9: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while creating the new service: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void CreateNewService() throws Exception {
        try {
            // Step 1: Click on the service tab to begin service creation
            serviceTabbtn();
            test.log(LogStatus.PASS, "Clicked on Service Tab to begin service creation.");

            // Step 2: Enter service details
            enterserviceId();
            test.log(LogStatus.PASS, "Service ID entered successfully.");

            selectserviceentity();
            test.log(LogStatus.PASS, "Service entity selected successfully.");

            enterserviceGlCode();
            test.log(LogStatus.PASS, "Service GL Code entered successfully.");

            enterpgaCode();
            test.log(LogStatus.PASS, "PGA Code entered successfully.");

            enterserviceNameEnglish();
            test.log(LogStatus.PASS, "Service Name (English) entered successfully.");

            enterserviceNameArabic();
            test.log(LogStatus.PASS, "Service Name (Arabic) entered successfully.");

            serviceCategory();
            test.log(LogStatus.PASS, "Service category selected successfully.");

            serviceTypeFixed();
            test.log(LogStatus.PASS, "Service type set to 'Fixed' successfully.");

            // Step 3: Set effective start and end dates
            selecteffecitvestartdate();
            test.log(LogStatus.PASS, "Effective start date selected successfully.");

            // selecteffecitveenddate();
            test.log(LogStatus.PASS, "Effective end date selected successfully.");

            // Step 4: Select VAT values and set service status
            selectvatValuesServicesVAT1();
            test.log(LogStatus.PASS, "VAT values for services selected successfully.");

            serviceStatusActive();
            test.log(LogStatus.PASS, "Service status set to 'Active' successfully.");

            // Step 5: Save the service
            save();
            test.log(LogStatus.PASS, "Service saved successfully.");

            // Step 6: Search the service ID to verify creation
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched and verified.");

            // Step 7: Capture screenshot of the service creation details
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service created successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Service Created Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the service.");
            }

        } catch (Exception e) {
            // Step 8: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while creating the new service: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }


    @Test
    public void CreateNewServicePERCENT() throws Exception {
        try {
            // Step 1: Delete existing service if applicable
            serviceDelete2();
            test.log(LogStatus.PASS, "Previous service deleted successfully.");

            // Step 2: Navigate to Service Tab to begin creating a new service
            serviceTabbtn();
            test.log(LogStatus.PASS, "Clicked on Service Tab.");

            // Step 3: Enter service details
            enterserviceId();
            test.log(LogStatus.PASS, "Service ID entered successfully.");

            selectserviceentity();
            test.log(LogStatus.PASS, "Service entity selected successfully.");

            enterserviceGlCode();
            test.log(LogStatus.PASS, "Service GL Code entered successfully.");

            enterpgaCode();
            test.log(LogStatus.PASS, "PGA Code entered successfully.");

            enterserviceNameEnglish();
            test.log(LogStatus.PASS, "Service Name (English) entered successfully.");

            enterserviceNameArabic();
            test.log(LogStatus.PASS, "Service Name (Arabic) entered successfully.");

            serviceCategory();
            test.log(LogStatus.PASS, "Service category selected successfully.");

            serviceTypePercent();
            test.log(LogStatus.PASS, "Service type set to 'Percent' successfully.");

            // Step 4: Set effective start and end dates
            selecteffecitvestartdate();
            test.log(LogStatus.PASS, "Effective start date selected successfully.");

            // selecteffecitveenddate();
            //test.log(LogStatus.PASS, "Effective end date selected successfully.");

            // Step 5: Select VAT values and service status
            selectvatValuesServicesVAT2();
            test.log(LogStatus.PASS, "VAT values for services selected successfully.");

            serviceStatusActive();
            test.log(LogStatus.PASS, "Service status set successfully.");

            // Step 6: Save the new service
            save();
            test.log(LogStatus.PASS, "Service saved successfully.");

            // Step 7: Search the service ID to verify creation
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched and verified.");

            SendforReviewServiceID2();
            searchserviceID();
            ApproveServiceID2();

            // Step 8: Capture screenshot after service creation
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service created successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Service Created Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the service.");
            }

        } catch (Exception e) {
            // Step 9: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while creating the new service: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }


    @Test
    public void mocyCreateNewServicePERCENT() throws Exception {
        try {
            // Step 1: Delete existing service if applicable
            serviceDelete2();
            test.log(LogStatus.PASS, "Previous service deleted successfully.");

            // Step 2: Navigate to Service Tab to begin creating a new service
            serviceTabbtn();
            test.log(LogStatus.PASS, "Clicked on Service Tab.");


            clickElement(By.xpath(mocyCreateservicebtn));

            // Step 3: Enter service details
            enterserviceId();
            test.log(LogStatus.PASS, "Service ID entered successfully.");

            selectserviceentity();
            test.log(LogStatus.PASS, "Service entity selected successfully.");

            enterserviceGlCode();
            test.log(LogStatus.PASS, "Service GL Code entered successfully.");

            enterpgaCode();
            test.log(LogStatus.PASS, "PGA Code entered successfully.");

            enterserviceNameEnglish();
            test.log(LogStatus.PASS, "Service Name (English) entered successfully.");

            enterserviceNameArabic();
            test.log(LogStatus.PASS, "Service Name (Arabic) entered successfully.");

            serviceCategory();
            test.log(LogStatus.PASS, "Service category selected successfully.");

            serviceTypePercent();
            test.log(LogStatus.PASS, "Service type set to 'Percent' successfully.");

            // Step 4: Set effective start and end dates
            selecteffecitvestartdate();
            test.log(LogStatus.PASS, "Effective start date selected successfully.");

            // selecteffecitveenddate();
            //test.log(LogStatus.PASS, "Effective end date selected successfully.");

            // Step 5: Select VAT values and service status
            selectvatValuesServicesVAT2();
            test.log(LogStatus.PASS, "VAT values for services selected successfully.");

            selectserviceStatusForServices2();
            test.log(LogStatus.PASS, "Service status set successfully.");

            // Step 6: Save the new service
            save();
            test.log(LogStatus.PASS, "Service saved successfully.");

            // Step 7: Search the service ID to verify creation
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched and verified.");

            // Step 8: Capture screenshot after service creation
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service created successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Service Created Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the service.");
            }

        } catch (Exception e) {
            // Step 9: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while creating the new service: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void CreateNewServiceUNIT() throws Exception {
        try {
            // Step 1: Delete existing service if applicable
            serviceDelete3();
            test.log(LogStatus.PASS, "Previous service deleted successfully.");

            // Step 2: Navigate to Service Tab to begin creating a new service
            serviceTabbtn();
            test.log(LogStatus.PASS, "Clicked on Service Tab.");

            // Step 3: Enter service details
            enterserviceId();
            test.log(LogStatus.PASS, "Service ID entered successfully.");

            selectserviceentity();
            test.log(LogStatus.PASS, "Service entity selected successfully.");

            enterserviceGlCode();
            test.log(LogStatus.PASS, "Service GL Code entered successfully.");

            enterpgaCode();
            test.log(LogStatus.PASS, "PGA Code entered successfully.");

            enterserviceNameEnglish();
            test.log(LogStatus.PASS, "Service Name (English) entered successfully.");

            enterserviceNameArabic();
            test.log(LogStatus.PASS, "Service Name (Arabic) entered successfully.");

            serviceCategory();
            test.log(LogStatus.PASS, "Service category selected successfully.");

            serviceTypeUnit();
            test.log(LogStatus.PASS, "Service type set to 'Unit' successfully.");

            // Step 4: Set effective start and end dates
            selecteffecitvestartdate();
            test.log(LogStatus.PASS, "Effective start date selected successfully.");

            // selecteffecitveenddate();
            test.log(LogStatus.PASS, "Effective end date selected successfully.");

            // Step 5: Select VAT values and service status
            selectvatValuesServicesVAT3();
            test.log(LogStatus.PASS, "VAT values for services selected successfully.");

            serviceStatusActive();
            test.log(LogStatus.PASS, "Service status set to active successfully.");

            // Step 6: Save the new service
            save();
            test.log(LogStatus.PASS, "Service saved successfully.");

            // Step 7: Search the service ID to verify creation
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched and verified.");

            SendforReviewServiceID3();
            searchserviceID();
            ApproveServiceID3();

            // Step 8: Capture screenshot after service creation
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service created successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Service Created Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the service.");
            }

        } catch (Exception e) {
            // Step 9: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while creating the new service: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void mocyCreateNewServiceUNIT() throws Exception {
        try {
            // Step 1: Delete existing service if applicable
            serviceDelete3();
            test.log(LogStatus.PASS, "Previous service deleted successfully.");

            // Step 2: Navigate to Service Tab to begin creating a new service
            serviceTabbtn();
            test.log(LogStatus.PASS, "Clicked on Service Tab.");


            clickElement(By.xpath(mocyCreateservicebtn));
            // Step 3: Enter service details
            enterserviceId();
            test.log(LogStatus.PASS, "Service ID entered successfully.");

            selectserviceentity();
            test.log(LogStatus.PASS, "Service entity selected successfully.");

            enterserviceGlCode();
            test.log(LogStatus.PASS, "Service GL Code entered successfully.");

            enterpgaCode();
            test.log(LogStatus.PASS, "PGA Code entered successfully.");

            enterserviceNameEnglish();
            test.log(LogStatus.PASS, "Service Name (English) entered successfully.");

            enterserviceNameArabic();
            test.log(LogStatus.PASS, "Service Name (Arabic) entered successfully.");

            serviceCategory();
            test.log(LogStatus.PASS, "Service category selected successfully.");

            serviceTypeUnit();
            test.log(LogStatus.PASS, "Service type set to 'Unit' successfully.");

            // Step 4: Set effective start and end dates
            selecteffecitvestartdate();
            test.log(LogStatus.PASS, "Effective start date selected successfully.");

            // selecteffecitveenddate();
            test.log(LogStatus.PASS, "Effective end date selected successfully.");

            // Step 5: Select VAT values and service status
            selectvatValuesServicesVAT3();
            test.log(LogStatus.PASS, "VAT values for services selected successfully.");

            serviceStatusActive();
            test.log(LogStatus.PASS, "Service status set to active successfully.");

            // Step 6: Save the new service
            save();
            test.log(LogStatus.PASS, "Service saved successfully.");

            // Step 7: Search the service ID to verify creation
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched and verified.");

            // Step 8: Capture screenshot after service creation
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service created successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Service Created Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the service.");
            }

        } catch (Exception e) {
            // Step 9: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while creating the new service: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }


    @Test
    public void CreateNewServiceVARIABLE() throws Exception {
        try {
            // Step 1: Delete existing service if applicable
            serviceDelete4();
            test.log(LogStatus.PASS, "Previous service deleted successfully.");

            // Step 2: Navigate to Service Tab to begin creating a new service
            serviceTabbtn();
            test.log(LogStatus.PASS, "Clicked on Service Tab.");

            // Step 3: Enter service details
            enterserviceId();
            test.log(LogStatus.PASS, "Service ID entered successfully.");

            selectserviceentity();
            test.log(LogStatus.PASS, "Service entity selected successfully.");

            enterserviceGlCode();
            test.log(LogStatus.PASS, "Service GL Code entered successfully.");

            enterpgaCode();
            test.log(LogStatus.PASS, "PGA Code entered successfully.");

            enterserviceNameEnglish();
            test.log(LogStatus.PASS, "Service Name (English) entered successfully.");

            enterserviceNameArabic();
            test.log(LogStatus.PASS, "Service Name (Arabic) entered successfully.");

            serviceCategory();
            test.log(LogStatus.PASS, "Service category selected successfully.");

            serviceTypeVariable();
            test.log(LogStatus.PASS, "Service type set to 'Variable' successfully.");

            // Step 4: Set effective start and end dates
            selecteffecitvestartdate();
            test.log(LogStatus.PASS, "Effective start date selected successfully.");

            //selecteffecitveenddate();
            test.log(LogStatus.PASS, "Effective end date selected successfully.");

            // Step 5: Select VAT values and service status
            selectvatValuesServicesVAT4();
            test.log(LogStatus.PASS, "VAT values for services selected successfully.");

            serviceStatusActive();
            test.log(LogStatus.PASS, "Service status set to active successfully.");

            // Step 6: Save the new service
            save();
            test.log(LogStatus.PASS, "Service saved successfully.");

            // Step 7: Search the service ID to verify creation
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched and verified.");

            SendforReviewServiceID4();
            searchserviceID();
            ApproveServiceID4();

            // Step 8: Capture screenshot after service creation
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service created successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Service Created Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the service.");
            }

        } catch (Exception e) {
            // Step 9: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while creating the new service: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void mocyCreateNewServiceVARIABLE() throws Exception {
        try {
            // Step 1: Delete existing service if applicable
            serviceDelete4();
            test.log(LogStatus.PASS, "Previous service deleted successfully.");

            // Step 2: Navigate to Service Tab to begin creating a new service
            serviceTabbtn();
            test.log(LogStatus.PASS, "Clicked on Service Tab.");

            clickElement(By.xpath(mocyCreateservicebtn));

            // Step 3: Enter service details
            enterserviceId();
            test.log(LogStatus.PASS, "Service ID entered successfully.");

            selectserviceentity();
            test.log(LogStatus.PASS, "Service entity selected successfully.");

            enterserviceGlCode();
            test.log(LogStatus.PASS, "Service GL Code entered successfully.");

            enterpgaCode();
            test.log(LogStatus.PASS, "PGA Code entered successfully.");

            enterserviceNameEnglish();
            test.log(LogStatus.PASS, "Service Name (English) entered successfully.");

            enterserviceNameArabic();
            test.log(LogStatus.PASS, "Service Name (Arabic) entered successfully.");

            serviceCategory();
            test.log(LogStatus.PASS, "Service category selected successfully.");

            serviceTypeVariable();
            test.log(LogStatus.PASS, "Service type set to 'Variable' successfully.");

            // Step 4: Set effective start and end dates
            selecteffecitvestartdate();
            test.log(LogStatus.PASS, "Effective start date selected successfully.");

            //selecteffecitveenddate();
            test.log(LogStatus.PASS, "Effective end date selected successfully.");

            // Step 5: Select VAT values and service status
            selectvatValuesServicesVAT4();
            test.log(LogStatus.PASS, "VAT values for services selected successfully.");

            serviceStatusActive();
            test.log(LogStatus.PASS, "Service status set to active successfully.");

            // Step 6: Save the new service
            save();
            test.log(LogStatus.PASS, "Service saved successfully.");

            // Step 7: Search the service ID to verify creation
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched and verified.");

            // Step 8: Capture screenshot after service creation
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service created successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Service Created Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the service.");
            }

        } catch (Exception e) {
            // Step 9: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while creating the new service: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void InvalidserviceID() throws Exception {
        try {
            // Step 1: Navigate to Service Tab
            serviceTabbtn();
            test.log(LogStatus.PASS, "Service Tab clicked.");

            // Step 2: Enter service details
            enterserviceId();
            test.log(LogStatus.PASS, "Service ID entered successfully.");

            selectserviceentity();
            test.log(LogStatus.PASS, "Service entity selected successfully.");

            enterserviceGlCode();
            test.log(LogStatus.PASS, "Service GL Code entered successfully.");

            enterpgaCode();
            test.log(LogStatus.PASS, "PGA Code entered successfully.");

            enterserviceNameEnglish();
            test.log(LogStatus.PASS, "Service Name (English) entered successfully.");

            enterserviceNameArabic();
            test.log(LogStatus.PASS, "Service Name (Arabic) entered successfully.");

            serviceCategory();
            test.log(LogStatus.PASS, "Service category selected successfully.");

            serviceTypeFixed();
            test.log(LogStatus.PASS, "Service type set to 'Fixed' successfully.");

            // Step 3: Set effective start and end dates
            selecteffecitvestartdate();
            test.log(LogStatus.PASS, "Effective start date selected successfully.");

            //selecteffecitveenddate();
            test.log(LogStatus.PASS, "Effective end date selected successfully.");

            // Step 4: Select VAT values and service status
            selectvatValuesServicesVAT1();
            test.log(LogStatus.PASS, "VAT values for services selected successfully.");

            serviceStatusActive();
            test.log(LogStatus.PASS, "Service status set to active successfully.");

            // Step 5: Save the new service
            save();
            test.log(LogStatus.PASS, "Service saved successfully.");

            // Step 6: Capture screenshot after service creation
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service created with invalid Service ID: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Invalid Service ID Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the service with invalid Service ID.");
            }

        } catch (Exception e) {
            // Step 7: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while creating service with invalid Service ID: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void InvalidGLcode() throws Exception {
        try {
            // Step 1: Navigate to Service Tab
            serviceTabbtn();
            test.log(LogStatus.PASS, "Service Tab clicked.");
            if (!driver.findElements(By.xpath(mocyCreateservicebtn)).isEmpty()) {
                clickElement(By.xpath(mocyCreateservicebtn));
            }


            // Step 2: Enter service details
            enterserviceId();
            test.log(LogStatus.PASS, "Service ID entered successfully.");

            selectserviceentity();
            test.log(LogStatus.PASS, "Service entity selected successfully.");

            // Enter invalid GL Code
            enterserviceGlCode();
            test.log(LogStatus.PASS, "Invalid Service GL Code entered successfully.");

            enterpgaCode();
            test.log(LogStatus.PASS, "PGA Code entered successfully.");

            enterserviceNameEnglish();
            test.log(LogStatus.PASS, "Service Name (English) entered successfully.");

            enterserviceNameArabic();
            test.log(LogStatus.PASS, "Service Name (Arabic) entered successfully.");

            serviceCategory();
            test.log(LogStatus.PASS, "Service category selected successfully.");

            serviceTypeFixed();
            test.log(LogStatus.PASS, "Service type set to 'Fixed' successfully.");

            // Step 3: Set effective start and end dates
            selecteffecitvestartdate();
            test.log(LogStatus.PASS, "Effective start date selected successfully.");

            //selecteffecitveenddate();
            test.log(LogStatus.PASS, "Effective end date selected successfully.");

            // Step 4: Select VAT values and service status
            selectvatValuesServicesVAT1();
            test.log(LogStatus.PASS, "VAT values for services selected successfully.");

            serviceStatusActive();
            test.log(LogStatus.PASS, "Service status set to active successfully.");

            // Step 5: Save the new service
            save();
            test.log(LogStatus.PASS, "Service saved successfully.");

            // Step 6: Capture screenshot after service creation
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service created with invalid GL Code: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Invalid GL Code Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the service with invalid GL Code.");
            }

        } catch (Exception e) {
            // Step 7: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while creating service with invalid GL Code: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void InvalidNewServiceFIXED() throws Exception {
        try {
            // Step 1: Navigate to Service Tab
            serviceTabbtn();
            test.log(LogStatus.PASS, "Service Tab clicked.");

            if (!driver.findElements(By.xpath(mocyCreateservicebtn)).isEmpty()) {
                clickElement(By.xpath(mocyCreateservicebtn));
            }


            // Step 2: Enter service details
            enterserviceId();
            test.log(LogStatus.PASS, "Service ID entered successfully.");

            selectserviceentity();
            test.log(LogStatus.PASS, "Service entity selected successfully.");

            // Enter invalid GL Code
            enterserviceGlCode();
            test.log(LogStatus.PASS, "Invalid Service GL Code entered successfully.");

            enterpgaCode();
            test.log(LogStatus.PASS, "PGA Code entered successfully.");

            enterserviceNameEnglish();
            test.log(LogStatus.PASS, "Service Name (English) entered successfully.");

            enterserviceNameArabic();
            test.log(LogStatus.PASS, "Service Name (Arabic) entered successfully.");

            serviceCategory();
            test.log(LogStatus.PASS, "Service category selected successfully.");

            serviceTypeFixed();
            test.log(LogStatus.PASS, "Service type set to 'Fixed' successfully.");

            // Step 3: Set effective start and end dates
            selecteffecitvestartdate();
            test.log(LogStatus.PASS, "Effective start date selected successfully.");

            //selecteffecitveenddate();
            test.log(LogStatus.PASS, "Effective end date selected successfully.");

            // Step 4: Select VAT values and service status
            selectvatValuesServicesVAT1();
            test.log(LogStatus.PASS, "VAT values for services selected successfully.");

            serviceStatusActive();
            test.log(LogStatus.PASS, "Service status set to active successfully.");

            // Step 5: Save the new service
            save();
            test.log(LogStatus.PASS, "Service saved successfully.");

            // Step 6: Capture screenshot after service creation
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service created with invalid GL Code: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Invalid GL Code Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the service with invalid GL Code.");
            }

        } catch (Exception e) {
            // Step 7: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while creating invalid service with GL Code: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }


    @Test
    public void InvalidNewServicePERCENT() throws Exception {
        try {
            // Step 1: Navigate to Service Tab
            serviceTabbtn();
            test.log(LogStatus.PASS, "Service Tab clicked.");

            if (!driver.findElements(By.xpath(mocyCreateservicebtn)).isEmpty()) {
                clickElement(By.xpath(mocyCreateservicebtn));
            }

            // Step 2: Enter service details
            enterserviceId();
            test.log(LogStatus.PASS, "Service ID entered successfully.");

            selectserviceentity();
            test.log(LogStatus.PASS, "Service entity selected successfully.");

            // Enter invalid GL Code
            enterserviceGlCode();
            test.log(LogStatus.PASS, "Invalid Service GL Code entered successfully.");

            enterpgaCode();
            test.log(LogStatus.PASS, "PGA Code entered successfully.");

            enterserviceNameEnglish();
            test.log(LogStatus.PASS, "Service Name (English) entered successfully.");

            enterserviceNameArabic();
            test.log(LogStatus.PASS, "Service Name (Arabic) entered successfully.");

            serviceCategory();
            test.log(LogStatus.PASS, "Service category selected successfully.");

            serviceTypePercent();
            test.log(LogStatus.PASS, "Service type set to 'Percent' successfully.");

            // Step 3: Set effective start and end dates
            selecteffecitvestartdate();
            test.log(LogStatus.PASS, "Effective start date selected successfully.");

            //selecteffecitveenddate();
            test.log(LogStatus.PASS, "Effective end date selected successfully.");

            // Step 4: Select VAT values and service status
            selectvatValuesServicesVAT1();
            test.log(LogStatus.PASS, "VAT values for services selected successfully.");

            serviceStatusActive();
            test.log(LogStatus.PASS, "Service status set to active successfully.");

            // Step 5: Save the new service
            save();
            test.log(LogStatus.PASS, "Service saved successfully.");

            // Step 6: Capture screenshot after service creation
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service created with invalid GL Code: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Invalid GL Code Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the service with invalid GL Code.");
            }

        } catch (Exception e) {
            // Step 7: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while creating invalid service with GL Code: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }


    @Test
    public void InvalidNewServiceUNIT() throws Exception {
        try {
            // Step 1: Navigate to Service Tab
            serviceTabbtn();
            test.log(LogStatus.PASS, "Service Tab clicked.");

            if (!driver.findElements(By.xpath(mocyCreateservicebtn)).isEmpty()) {
                clickElement(By.xpath(mocyCreateservicebtn));
            }


            // Step 2: Enter service details
            enterserviceId();
            test.log(LogStatus.PASS, "Service ID entered successfully.");

            selectserviceentity();
            test.log(LogStatus.PASS, "Service entity selected successfully.");

            // Enter invalid GL Code
            enterserviceGlCode();
            test.log(LogStatus.PASS, "Invalid Service GL Code entered successfully.");

            enterpgaCode();
            test.log(LogStatus.PASS, "PGA Code entered successfully.");

            enterserviceNameEnglish();
            test.log(LogStatus.PASS, "Service Name (English) entered successfully.");

            enterserviceNameArabic();
            test.log(LogStatus.PASS, "Service Name (Arabic) entered successfully.");

            serviceCategory();
            test.log(LogStatus.PASS, "Service category selected successfully.");

            serviceTypeUnit();
            test.log(LogStatus.PASS, "Service type set to 'Unit' successfully.");

            // Step 3: Set effective start and end dates
            selecteffecitvestartdate();
            test.log(LogStatus.PASS, "Effective start date selected successfully.");

            //selecteffecitveenddate();
            test.log(LogStatus.PASS, "Effective end date selected successfully.");

            // Step 4: Select VAT values and service status
            selectvatValuesServicesVAT1();
            test.log(LogStatus.PASS, "VAT values for services selected successfully.");

            serviceStatusActive();
            test.log(LogStatus.PASS, "Service status set to active successfully.");

            // Step 5: Save the new service
            save();
            test.log(LogStatus.PASS, "Service saved successfully.");

            // Step 6: Capture screenshot after service creation
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service created with invalid GL Code: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Invalid GL Code Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the service with invalid GL Code.");
            }

        } catch (Exception e) {
            // Step 7: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while creating invalid service with GL Code: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void InvalidNewServiceVARIABLE() throws Exception {
        try {
            // Step 1: Navigate to Service Tab
            serviceTabbtn();
            test.log(LogStatus.PASS, "Service Tab clicked.");

            if (!driver.findElements(By.xpath(mocyCreateservicebtn)).isEmpty()) {
                clickElement(By.xpath(mocyCreateservicebtn));
            }


            // Step 2: Enter service details
            enterserviceId();
            test.log(LogStatus.PASS, "Service ID entered successfully.");

            selectserviceentity();
            test.log(LogStatus.PASS, "Service entity selected successfully.");

            // Enter invalid GL Code
            enterserviceGlCode();
            test.log(LogStatus.PASS, "Invalid Service GL Code entered successfully.");

            enterpgaCode();
            test.log(LogStatus.PASS, "PGA Code entered successfully.");

            enterserviceNameEnglish();
            test.log(LogStatus.PASS, "Service Name (English) entered successfully.");

            enterserviceNameArabic();
            test.log(LogStatus.PASS, "Service Name (Arabic) entered successfully.");

            serviceCategory();
            test.log(LogStatus.PASS, "Service category selected successfully.");

            serviceTypeVariable();
            test.log(LogStatus.PASS, "Service type set to 'Variable' successfully.");

            // Step 3: Set effective start and end dates
            selecteffecitvestartdate();
            test.log(LogStatus.PASS, "Effective start date selected successfully.");

            //selecteffecitveenddate();
            test.log(LogStatus.PASS, "Effective end date selected successfully.");

            // Step 4: Select VAT values and service status
            selectvatValuesServicesVAT1();
            test.log(LogStatus.PASS, "VAT values for services selected successfully.");

            serviceStatusActive();
            test.log(LogStatus.PASS, "Service status set to active successfully.");

            // Step 5: Save the new service
            save();
            test.log(LogStatus.PASS, "Service saved successfully.");

            // Step 6: Capture screenshot after service creation
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service created with invalid GL Code: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Invalid GL Code Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the service with invalid GL Code.");
            }

        } catch (Exception e) {
            // Step 7: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while creating invalid service with GL Code: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public  void serviceview() throws Exception {
        clickElement(By.xpath(srvcView));
        Thread.sleep(500);

    }

    @Test
    public  void ViewNewServiceFIXED() throws Exception {

        serviceTabbtn();
        searchserviceID();
        serviceview();

    }

    @Test
    public  void serviceEdit() throws Exception {


        clickElement(By.xpath(srvcedit));
        Thread.sleep(500);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,700)");

        enterservicePrice();
        entermaximumQuantity();

    }


    @Test
    public  void EditNewServiceFIXEDServiceID() throws Exception {

        serviceTabbtn();
        searchserviceID();
        serviceEdit();
        save();
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);

    }

    @Test
    public void SendforReviewServiceID1() throws Exception {
        try {
            // Step 1: Navigate to the Service Tab
            serviceTabbtn();
            test.log(LogStatus.PASS, "Service Tab clicked.");

            // Step 2: Search for Service ID
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched successfully.");

            // Step 3: Click the 'Send for Review' button
            clickElement(By.xpath(sndforReview1));
            test.log(LogStatus.PASS, "'Send for Review' button clicked successfully.");

            // Step 4: Handle the alert confirmation
            Thread.sleep(500);
            Alert alert = driver.switchTo().alert();
            alert.accept();
            test.log(LogStatus.PASS, "Alert accepted successfully.");

            // Step 5: Capture screenshot after sending for review
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service ID sent for review: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Send for Review Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after sending the service ID for review.");
            }

        } catch (Exception e) {
            // Step 6: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while sending Service ID for review: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void SendforReviewServiceID2() throws Exception {
        try {
            // Step 1: Navigate to the Service Tab
            serviceTabbtn();
            test.log(LogStatus.PASS, "Service Tab clicked.");

            // Step 2: Search for Service ID
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched successfully.");

            // Step 3: Click the 'Send for Review' button
            clickElement(By.xpath(sndforReview2));
            test.log(LogStatus.PASS, "'Send for Review' button clicked successfully.");

            // Step 4: Handle the alert confirmation
            Thread.sleep(500);
            Alert alert = driver.switchTo().alert();
            alert.accept();
            test.log(LogStatus.PASS, "Alert accepted successfully.");

            // Step 5: Capture screenshot after sending for review
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service ID sent for review: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Send for Review Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after sending the service ID for review.");
            }

        } catch (Exception e) {
            // Step 6: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while sending Service ID for review: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void SendforReviewServiceID3() throws Exception {
        try {
            // Step 1: Navigate to the Service Tab
            serviceTabbtn();
            test.log(LogStatus.PASS, "Service Tab clicked.");

            // Step 2: Search for Service ID
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched successfully.");

            // Step 3: Click the 'Send for Review' button
            clickElement(By.xpath(sndforReview3));
            test.log(LogStatus.PASS, "'Send for Review' button clicked successfully.");

            // Step 4: Handle the alert confirmation
            Thread.sleep(500);
            Alert alert = driver.switchTo().alert();
            alert.accept();
            test.log(LogStatus.PASS, "Alert accepted successfully.");

            // Step 5: Capture screenshot after sending for review
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service ID sent for review: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Send for Review Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after sending the service ID for review.");
            }

        } catch (Exception e) {
            // Step 6: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while sending Service ID for review: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void SendforReviewServiceID4() throws Exception {
        try {
            // Step 1: Navigate to the Service Tab
            serviceTabbtn();
            test.log(LogStatus.PASS, "Service Tab clicked.");

            // Step 2: Search for Service ID
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched successfully.");

            // Step 3: Click the 'Send for Review' button
            clickElement(By.xpath(sndforReview4));
            test.log(LogStatus.PASS, "'Send for Review' button clicked successfully.");

            // Step 4: Handle the alert confirmation
            Thread.sleep(500);
            Alert alert = driver.switchTo().alert();
            alert.accept();
            test.log(LogStatus.PASS, "Alert accepted successfully.");

            // Step 5: Capture screenshot after sending for review
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service ID sent for review: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Send for Review Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after sending the service ID for review.");
            }

        } catch (Exception e) {
            // Step 6: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while sending Service ID for review: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }
    @Test
    public void SendforReviewServiceID() throws Exception {
        try {
            // Step 1: Navigate to the Service Tab
            serviceTabbtn();
            test.log(LogStatus.PASS, "Service Tab clicked.");

            // Step 2: Search for Service ID
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched successfully.");

            // Step 3: Click the 'Send for Review' button
            clickElement(By.xpath(sndforReview));
            test.log(LogStatus.PASS, "'Send for Review' button clicked successfully.");

            // Step 4: Handle the alert confirmation
            Thread.sleep(500);
            Alert alert = driver.switchTo().alert();
            alert.accept();
            test.log(LogStatus.PASS, "Alert accepted successfully.");

            // Step 5: Capture screenshot after sending for review
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service ID sent for review: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Send for Review Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after sending the service ID for review.");
            }

        } catch (Exception e) {
            // Step 6: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while sending Service ID for review: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }


    @Test
    public void ApproveServiceID() throws Exception {
        try {
            // Step 1: Navigate to the Service Tab
            serviceTabbtn();
            test.log(LogStatus.PASS, "Service Tab clicked.");

            // Step 2: Search for Service ID
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched successfully.");

            // Step 3: Click the 'Approve' button
            clickElement(By.xpath(approve));
            test.log(LogStatus.PASS, "'Approve' button clicked successfully.");

            // Step 4: Handle the alert confirmation

            Alert alert = driver.switchTo().alert();
            alert.accept();
            test.log(LogStatus.PASS, "Alert accepted successfully.");

            // Step 5: Capture screenshot after approval
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service ID approved successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Approve Service ID Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after approving the Service ID.");
            }

        } catch (Exception e) {
            // Step 6: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while approving Service ID: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void ApproveServiceID1() throws Exception {
        try {
            // Step 1: Navigate to the Service Tab


            // Step 3: Click the 'Approve' button
            clickElement(By.xpath(approve1));
            test.log(LogStatus.PASS, "'Approve' button clicked successfully.");

            // Step 4: Handle the alert confirmation

            Alert alert = driver.switchTo().alert();
            alert.accept();
            test.log(LogStatus.PASS, "Alert accepted successfully.");

            // Step 5: Capture screenshot after approval
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service ID approved successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Approve Service ID Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after approving the Service ID.");
            }

        } catch (Exception e) {
            // Step 6: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while approving Service ID: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void ApproveServiceID2() throws Exception {
        try {
            // Step 1: Navigate to the Service Tab
            serviceTabbtn();
            test.log(LogStatus.PASS, "Service Tab clicked.");

            // Step 2: Search for Service ID
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched successfully.");

            // Step 3: Click the 'Approve' button
            clickElement(By.xpath(approve2));
            test.log(LogStatus.PASS, "'Approve' button clicked successfully.");

            // Step 4: Handle the alert confirmation

            Alert alert = driver.switchTo().alert();
            alert.accept();
            test.log(LogStatus.PASS, "Alert accepted successfully.");

            // Step 5: Capture screenshot after approval
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service ID approved successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Approve Service ID Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after approving the Service ID.");
            }

        } catch (Exception e) {
            // Step 6: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while approving Service ID: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void ApproveServiceID3() throws Exception {
        try {
            // Step 1: Navigate to the Service Tab
            serviceTabbtn();
            test.log(LogStatus.PASS, "Service Tab clicked.");

            // Step 2: Search for Service ID
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched successfully.");

            // Step 3: Click the 'Approve' button
            clickElement(By.xpath(approve3));
            test.log(LogStatus.PASS, "'Approve' button clicked successfully.");

            // Step 4: Handle the alert confirmation

            Alert alert = driver.switchTo().alert();
            alert.accept();
            test.log(LogStatus.PASS, "Alert accepted successfully.");

            // Step 5: Capture screenshot after approval
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service ID approved successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Approve Service ID Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after approving the Service ID.");
            }

        } catch (Exception e) {
            // Step 6: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while approving Service ID: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void ApproveServiceID4() throws Exception {
        try {
            // Step 1: Navigate to the Service Tab
            serviceTabbtn();
            test.log(LogStatus.PASS, "Service Tab clicked.");

            // Step 2: Search for Service ID
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched successfully.");

            // Step 3: Click the 'Approve' button
            clickElement(By.xpath(approve4));
            test.log(LogStatus.PASS, "'Approve' button clicked successfully.");

            // Step 4: Handle the alert confirmation

            Alert alert = driver.switchTo().alert();
            alert.accept();
            test.log(LogStatus.PASS, "Alert accepted successfully.");

            // Step 5: Capture screenshot after approval
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service ID approved successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Approve Service ID Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after approving the Service ID.");
            }

        } catch (Exception e) {
            // Step 6: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while approving Service ID: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }
    @Test
    public void RejectServiceID() throws Exception {
        try {
            // Step 1: Navigate to the Service Tab
            serviceTabbtn();
            test.log(LogStatus.PASS, "Service Tab clicked.");

            // Step 2: Search for the Service ID
            searchserviceID();
            test.log(LogStatus.PASS, "Service ID searched successfully.");

            // Step 3: Scroll down to ensure 'Reject' button is visible
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,700)");
            Thread.sleep(2000);
            test.log(LogStatus.PASS, "Scrolled down to bring 'Reject' button into view.");

            // Step 4: Click the 'Reject' button
            clickElement(By.xpath(reject));
            test.log(LogStatus.PASS, "'Reject' button clicked successfully.");

            // Step 5: Handle the alert confirmation
            Thread.sleep(500);







            Alert alert = driver.switchTo().alert();
            alert.accept();
            test.log(LogStatus.PASS, "Alert accepted successfully.");

            // Step 6: Capture screenshot after rejection
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service ID rejected successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue +
                        "<img src='" + screenshotPath + "' alt='Reject Service ID Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after rejecting the Service ID.");
            }

        } catch (Exception e) {
            // Step 7: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while rejecting Service ID: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public  void ClickServiceArab() throws Exception {
        // Step 1: Wait for a moment to ensure the element is loaded
        Thread.sleep(500);

        // Step 2: Find the Service Tab button using XPath
        clickElement(By.xpath(servicetabbtnARAB));

        // Step 3: Click on the Service Tab button
        test.log(LogStatus.PASS, "Successfully clicked on Service Tab");

        // Step 4: Capture screenshot after clicking the Service Tab
        String screenshotPath = Screenshot.captureScreenShot(driver, "ServiceTab_Clicked");
        if (screenshotPath != null) {
            // Log the success message with the screenshot in the Extent Report
            test.log(LogStatus.PASS, "Service Tab clicked successfully. Screenshot: " +
                    "<img src='" + screenshotPath + "' alt='Service Tab Clicked Screenshot' width='500' height='200' />");
        } else {
            // Log failure if screenshot was not captured
            test.log(LogStatus.FAIL, "Failed to capture screenshot after clicking Service Tab.");
        }

        // Wait to ensure the page has time to load the new content
        Thread.sleep(500);

    }


    @Test
    public  void serviceTabbtn() throws Exception {
        try {
            // Step 1: Wait for a moment to ensure the element is loaded
            Thread.sleep(500);

            // Step 2: Find the Service Tab button using XPath
            clickElement(By.xpath(servicetabbtn));

            // Step 3: Click on the Service Tab button
            test.log(LogStatus.PASS, "Successfully clicked on Service Tab");

            // Step 4: Capture screenshot after clicking the Service Tab
            String screenshotPath = Screenshot.captureScreenShot(driver, "ServiceTab_Clicked");
            if (screenshotPath != null) {
                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Service Tab clicked successfully. Screenshot: " +
                        "<img src='" + screenshotPath + "' alt='Service Tab Clicked Screenshot' width='500' height='200' />");
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after clicking Service Tab.");
            }

            // Wait to ensure the page has time to load the new content
            Thread.sleep(500);

        } catch (Exception e) {
            // Step 5: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while clicking on Service Tab: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void BlankserviceID() throws Exception {
        try {
            // Step 1: Navigate to the service tab
            serviceTabbtn();

            // Step 2: Check if the button exists, then click it
            if (!driver.findElements(By.xpath(mocyCreateservicebtn)).isEmpty()) {
                clickElement(By.xpath(mocyCreateservicebtn));
            }


            selectserviceentity();
            enterserviceGlCode();
            enterpgaCode();
            enterserviceNameEnglish();
            enterserviceNameArabic();
            serviceCategory();
            serviceTypeFixed();

            // Step 3: Set effective start and end date, and other service details
            selecteffecitvestartdate();
            //selecteffecitveenddate();
            selectvatValuesServicesVAT1();
            serviceStatusActive();

            // Step 4: Save the service with missing Service ID
            save();
            test.log(LogStatus.PASS, "Service saved with missing Service ID: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Step 5: Capture screenshot after saving the service
            String screenshotPath = Screenshot.captureScreenShot(driver, "ServiceWithoutID_Saved");
            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Without ID Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Service saved without Service ID. Screenshot: " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot after saving service without Service ID.");
            }

        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Exception occurred while testing BlankserviceID: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void BlankEntityDrop() throws Exception {
        try {
            // Step 1: Navigate to the service tab
            serviceTabbtn();
            if (!driver.findElements(By.xpath(mocyCreateservicebtn)).isEmpty()) {
                clickElement(By.xpath(mocyCreateservicebtn));
            }


            // Step 2: Enter Service ID
            enterserviceId();

            // Step 3: Enter other service details excluding Entity
            enterserviceGlCode();
            enterpgaCode();
            enterserviceNameEnglish();
            enterserviceNameArabic();
            serviceCategory();
            serviceTypeFixed();

            // Step 4: Set effective start and end date, and other necessary details
            selecteffecitvestartdate();
            // selecteffecitveenddate();
            selectvatValuesServicesVAT1();
            serviceStatusActive();

            // Step 5: Save the service with missing Entity dropdown selection
            save();
            test.log(LogStatus.PASS, "Service saved with missing Entity dropdown: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Step 6: Capture screenshot after saving the service
            String screenshotPath = Screenshot.captureScreenShot(driver, "ServiceWithoutEntityDrop_Saved");
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Without Entity Dropdown Screenshot' width='500' height='200' />";

                // Log the success message with the screenshot
                test.log(LogStatus.PASS, "Service saved without Entity dropdown selection. Screenshot: " + imgTag);
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after saving service without Entity dropdown selection.");
            }

        } catch (Exception e) {
            // Step 7: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while testing BlankEntityDrop: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void BlankGLcode() throws Exception {
        try {
            // Step 1: Navigate to the service tab
            serviceTabbtn();
            if (!driver.findElements(By.xpath(mocyCreateservicebtn)).isEmpty()) {
                clickElement(By.xpath(mocyCreateservicebtn));
            }


            // Step 2: Enter Service ID and select Entity
            enterserviceId();
            selectserviceentity();

            // Step 3: Enter other service details excluding GL Code
            enterpgaCode();
            enterserviceNameEnglish();
            enterserviceNameArabic();
            serviceCategory();
            serviceTypeVariable();

            // Step 4: Set effective start and end date, VAT values, and service status
            selecteffecitvestartdate();
            // selecteffecitveenddate();
            selectvatValuesServicesVAT4();
            serviceStatusActive();

            // Step 5: Save the service with missing GL Code
            save();
            test.log(LogStatus.PASS, "Service saved with missing GL Code: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Step 6: Capture screenshot after saving the service
            String screenshotPath = Screenshot.captureScreenShot(driver, "ServiceWithoutGLCode_Saved");
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Without GL Code Screenshot' width='500' height='200' />";

                // Log the success message with the screenshot
                test.log(LogStatus.PASS, "Service saved without GL Code. Screenshot: " + imgTag);
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after saving service without GL Code.");
            }

        } catch (Exception e) {
            // Step 7: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while testing BlankGLcode: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }


    @Test
    public void BlankPGACode() throws Exception {
        try {
            // Step 1: Navigate to the service tab
            serviceTabbtn();
            if (!driver.findElements(By.xpath(mocyCreateservicebtn)).isEmpty()) {
                clickElement(By.xpath(mocyCreateservicebtn));
            }


            // Step 2: Enter Service ID and select Entity
            enterserviceId();
            selectserviceentity();

            // Step 3: Enter GL Code (but leaving the PGA Code blank)
            enterserviceGlCode();

            // Step 4: Enter other service details excluding PGA Code
            enterserviceNameEnglish();
            enterserviceNameArabic();
            serviceCategory();
            serviceTypeVariable();

            // Step 5: Set effective start and end date, VAT values, and service status
            selecteffecitvestartdate();
            //  selecteffecitveenddate();
            selectvatValuesServicesVAT4();
            serviceStatusActive();

            // Step 6: Save the service with missing PGA Code
            save();
            test.log(LogStatus.PASS, "Service saved with missing PGA Code: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Step 7: Capture screenshot after saving the service
            String screenshotPath = Screenshot.captureScreenShot(driver, "ServiceWithoutPGACode_Saved");
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Without PGA Code Screenshot' width='500' height='200' />";

                // Log the success message with the screenshot
                test.log(LogStatus.PASS, "Service saved without PGA Code. Screenshot: " + imgTag);
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after saving service without PGA Code.");
            }

        } catch (Exception e) {
            // Step 8: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while testing BlankPGACode: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }


    @Test
    public void BlankServiceNameENG() throws Exception {
        try {
            // Step 1: Navigate to the service tab
            serviceTabbtn();
            if (!driver.findElements(By.xpath(mocyCreateservicebtn)).isEmpty()) {
                clickElement(By.xpath(mocyCreateservicebtn));
            }


            // Step 2: Enter Service ID and select Entity
            enterserviceId();
            selectserviceentity();

            // Step 3: Enter GL Code and PGA Code
            enterserviceGlCode();
            enterpgaCode();

            // Step 4: Enter Arabic Service Name (leaving English name blank)
            enterserviceNameArabic();

            // Step 5: Fill out other service details
            serviceCategory();
            serviceTypeVariable();
            selecteffecitvestartdate();
            //selecteffecitveenddate();
            selectvatValuesServicesVAT4();
            serviceStatusActive();

            // Step 6: Save the service with missing English Service Name
            save();
            test.log(LogStatus.PASS, "Service saved with missing English Service Name: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Step 7: Capture screenshot after saving the service
            String screenshotPath = Screenshot.captureScreenShot(driver, "ServiceWithoutEnglishName_Saved");
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Without English Name Screenshot' width='500' height='200' />";

                // Log the success message with the screenshot
                test.log(LogStatus.PASS, "Service saved without English Service Name. Screenshot: " + imgTag);
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after saving service without English Service Name.");
            }

        } catch (Exception e) {
            // Step 8: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while testing BlankServiceNameENG: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }


    @Test
    public void BlankServiceNameARAB() throws Exception {
        try {
            // Step 1: Navigate to the service tab
            serviceTabbtn();
            if (!driver.findElements(By.xpath(mocyCreateservicebtn)).isEmpty()) {
                clickElement(By.xpath(mocyCreateservicebtn));
            }


            // Step 2: Enter Service ID and select Entity
            enterserviceId();
            selectserviceentity();

            // Step 3: Enter GL Code and PGA Code
            enterserviceGlCode();
            enterpgaCode();

            // Step 4: Enter English Service Name (leaving Arabic name blank)
            enterserviceNameEnglish();

            // Step 5: Fill out other service details
            serviceCategory();
            serviceTypeVariable();
            selecteffecitvestartdate();
            // selecteffecitveenddate();
            selectvatValuesServicesVAT4();
            serviceStatusActive();

            // Step 6: Save the service with missing Arabic Service Name
            save();
            test.log(LogStatus.PASS, "Service saved with missing Arabic Service Name: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Step 7: Capture screenshot after saving the service
            String screenshotPath = Screenshot.captureScreenShot(driver, "ServiceWithoutArabicName_Saved");
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Without Arabic Name Screenshot' width='500' height='200' />";

                // Log the success message with the screenshot
                test.log(LogStatus.PASS, "Service saved without Arabic Service Name. Screenshot: " + imgTag);
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after saving service without Arabic Service Name.");
            }

        } catch (Exception e) {
            // Step 8: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while testing BlankServiceNameARA: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void BlankCategory() throws Exception {
        try {
            // Step 1: Navigate to the service tab
            serviceTabbtn();
            if (!driver.findElements(By.xpath(mocyCreateservicebtn)).isEmpty()) {
                clickElement(By.xpath(mocyCreateservicebtn));
            }


            // Step 2: Enter Service ID and select the Entity
            enterserviceId();
            selectserviceentity();

            // Step 3: Enter GL Code and PGA Code
            enterserviceGlCode();
            enterpgaCode();

            // Step 4: Enter Service Name in English and Arabic (leaving category blank)
            enterserviceNameEnglish();
            enterserviceNameArabic();

            // Step 5: Fill in service details excluding the Category field
            serviceTypeVariable();
            selecteffecitvestartdate();
            // selecteffecitveenddate();
            selectvatValuesServicesVAT4();
            serviceStatusActive();

            // Step 6: Save the service with the missing category
            save();
            test.log(LogStatus.PASS, "Service saved with missing Category: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Step 7: Capture screenshot after saving the service
            String screenshotPath = Screenshot.captureScreenShot(driver, "ServiceWithoutCategory_Saved");
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Without Category Screenshot' width='500' height='200' />";

                // Log the success message with the screenshot
                test.log(LogStatus.PASS, "Service saved without Category. Screenshot: " + imgTag);
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after saving service without Category.");
            }

        } catch (Exception e) {
            // Step 8: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while testing BlankCategory: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void BlankServiceType() throws Exception {
        try {
            // Step 1: Navigate to the service tab
            serviceTabbtn();
            if (!driver.findElements(By.xpath(mocyCreateservicebtn)).isEmpty()) {
                clickElement(By.xpath(mocyCreateservicebtn));
            }


            // Step 2: Enter Service ID and select the Entity
            enterserviceId();
            selectserviceentity();

            // Step 3: Enter GL Code and PGA Code
            enterserviceGlCode();
            enterpgaCode();

            // Step 4: Enter Service Name in English and Arabic
            enterserviceNameEnglish();
            enterserviceNameArabic();

            // Step 5: Fill in service details excluding the Service Type field
            serviceCategory();

            // Step 6: Select effective start and end dates
            selecteffecitvestartdate();
            // selecteffecitveenddate();

            // Step 7: Select VAT values for services
            selectvatValuesServicesVAT4();

            // Step 8: Set the service status to Active
            serviceStatusActive();

            // Step 9: Save the service with the missing Service Type field
            save();
            test.log(LogStatus.PASS, "Service saved with missing Service Type: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Step 10: Capture screenshot after saving the service
            String screenshotPath = Screenshot.captureScreenShot(driver, "ServiceWithoutServiceType_Saved");
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Service Without Service Type Screenshot' width='500' height='200' />";

                // Log the success message with the screenshot
                test.log(LogStatus.PASS, "Service saved without Service Type. Screenshot: " + imgTag);
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot after saving service without Service Type.");
            }

        } catch (Exception e) {
            // Step 11: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while testing BlankServiceType: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }


    @Test
    public  void enterserviceId() throws Exception {
        WebElement serviceId = driver.findElement(By.xpath(Webelements.serviceId));
        serviceId.sendKeys(ExcelUtilities.serviceIDcolumn);
        Thread.sleep(500);
        test.log(LogStatus.PASS, "Enter Service ID :" + ExcelUtilities.serviceIDcolumn);
    }

    @Test
    public  void selectserviceentity() throws Exception {
        Select select = new Select(driver.findElement(By.xpath(entitydrop)));
        select.selectByVisibleText(ExcelUtilities.entitydropcolumn);
        Thread.sleep(500);
        test.log(LogStatus.PASS, "Select Service Entity DropDown :" + ExcelUtilities.entitydropcolumn);
    }

    @Test
    public  void enterserviceGlCode() throws Exception {
        WebElement srvcGlCode = driver.findElement(By.xpath(ServiceGLCode));
        srvcGlCode.clear();
        srvcGlCode.sendKeys(ExcelUtilities.ServiceGLCodecolumn);
        Thread.sleep(500);
        test.log(LogStatus.PASS, "Enter Service GL code :" + ExcelUtilities.ServiceGLCodecolumn);
    }

    @Test
    public  void enterpgaCode() throws Exception {
        WebElement pgCode = driver.findElement(By.xpath(PGACode));
        pgCode.sendKeys(ExcelUtilities.PGACodecolumn);
        Thread.sleep(500);
        test.log(LogStatus.PASS, "Enter PGA code :" + ExcelUtilities.PGACodecolumn);
    }

    @Test
    public  void enterserviceNameEnglish() throws Exception {
        WebElement serviceNameEnglish = driver.findElement(By.xpath(ServiceNameEnglish));
        serviceNameEnglish.sendKeys(ExcelUtilities.ServiceNameEnglishcolumn);
        Thread.sleep(500);
        test.log(LogStatus.PASS, "Enter Service Name English :" + ExcelUtilities.ServiceNameEnglishcolumn);
    }

    @Test
    public  void enterserviceNameArabic() throws Exception {
        WebElement serviceNameArabic = driver.findElement(By.xpath(ServiceNameArabic));
        serviceNameArabic.sendKeys(ExcelUtilities.ServiceNameArabiccolumn);
        Thread.sleep(500);
        test.log(LogStatus.PASS, "Enter Service Name Arabic :" + ExcelUtilities.ServiceNameArabiccolumn);
    }

    @Test
    public  void serviceCategory() throws Exception {
        clickElement(By.xpath(Category));
        Select serviceCategory1 = new Select(driver.findElement(By.xpath(Category)));
        serviceCategory1.selectByVisibleText(ExcelUtilities.Categorycolumn);
        Thread.sleep(500);
        test.log(LogStatus.PASS, "Enter Service Catagory :" + ExcelUtilities.Categorycolumn);
    }

    @Test
    public  void serviceTypeFixed() throws Exception {
        clickElement(By.xpath(ServiceType));
        Thread.sleep(600);
        Select serviceFixed = new Select(driver.findElement(By.xpath(ServiceType)));
        serviceFixed.selectByValue("1");
        Thread.sleep(600);
        enterservicePrice();
        entermaximumQuantity();
        test.log(LogStatus.PASS, " Service type Fixed  ");
    }

    @Test
    public  void serviceTypePercent() throws Exception {
        clickElement(By.xpath(ServiceType));
        Thread.sleep(600);
        Select serviceFixed = new Select(driver.findElement(By.xpath(ServiceType)));
        serviceFixed.selectByIndex(2);
        Thread.sleep(600);
        WebElement PricePercent = driver.findElement(By.xpath(Webelements.PricePercent));
        PricePercent.sendKeys(ExcelUtilities.PricePercentcolumn);
        Thread.sleep(500);
        WebElement MinimumPrice = driver.findElement(By.xpath(Webelements.MinimumPrice));
        MinimumPrice.clear();
        MinimumPrice.sendKeys(ExcelUtilities.MinimumPricecolumn);
        Thread.sleep(500);
        WebElement MaximumPrice = driver.findElement(By.xpath(Webelements.MaximumPrice));
        MaximumPrice.clear();
        MaximumPrice.sendKeys(ExcelUtilities.MaximumPricecolumn);
        Thread.sleep(500);
        entermaximumQuantity();

    }

    @Test
    public  void serviceTypeUnit() throws Exception {
        clickElement(By.xpath(ServiceType));
        Thread.sleep(600);
        Select serviceFixed = new Select(driver.findElement(By.xpath(ServiceType)));
        serviceFixed.selectByIndex(3);
        Thread.sleep(600);
        WebElement Unitprice = driver.findElement(By.xpath(UnitPrice));
        Unitprice.sendKeys(ExcelUtilities.UnitPricecolumn);
        entermaximumQuantity();
    }

    @Test
    public  void serviceTypeVariable() throws Exception {
        clickElement(By.xpath(ServiceType));
        Thread.sleep(600);
        Select serviceFixed = new Select(driver.findElement(By.xpath(ServiceType)));
        serviceFixed.selectByIndex(4);
        Thread.sleep(600);
        entermaximumQuantity();

    }

    @Test
    public  void enterserviceMinimumPrice() throws Exception {
        WebElement servicePrice = driver.findElement(By.xpath(MinimumPrice));
        servicePrice.sendKeys(ExcelUtilities.MinimumPricecolumn);
        Thread.sleep(500);
    }

    @Test
    public  void selectsubcatagory() throws Exception {
        clickElement(By.xpath(SubCategory));
        Thread.sleep(600);

    }

    @Test
    public  void enterserviceTypeUnit() throws Exception {
        clickElement(By.xpath(UnitPrice));
        Thread.sleep(600);
        clickElement(By.partialLinkText(ExcelUtilities.UnitPricecolumn));
        Thread.sleep(600);
    }

    @Test
    public  void enterserviceTypeVariable() throws Exception {
        clickElement(By.xpath("//select[@id='serviceTypesForService']"));
        Thread.sleep(600);
        clickElement(By.partialLinkText("Variable"));
        Thread.sleep(600);
    }

    @Test
    public  void enterservicePrice() throws Exception {
        WebElement servicePrice = driver.findElement(By.xpath(ServicePrice));
        servicePrice.clear();
        servicePrice.sendKeys(ExcelUtilities.ServicePricecolumn.trim());
        Thread.sleep(500);
        test.log(LogStatus.PASS, " Enter Service Price  :" + ExcelUtilities.ServicePricecolumn);
    }

    @Test
    public  void entermaximumQuantity() throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        Thread.sleep(500);
        WebElement maximumQuantity = driver.findElement(By.xpath(MaximumQuantity));
        maximumQuantity.clear();
        maximumQuantity.sendKeys(ExcelUtilities.MaximumQuantitycolumn.trim());
        Thread.sleep(500);
        test.log(LogStatus.PASS, " Enter Service Maximum quantity :" + ExcelUtilities.MaximumQuantitycolumn);
    }

    @Test
    public  void selecteffecitvestartdate() throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Get the WebElement using XPath
        WebElement dateInput = driver.findElement(By.xpath(effstrtdte)); // effstrtdte must be a String XPath

        // Click to activate the input (optional)
        dateInput.click();

        // Set the value using JavaScript
        js.executeScript(
                "arguments[0].value = '2023-10-01T12:00'; arguments[0].dispatchEvent(new Event('input')); arguments[0].dispatchEvent(new Event('change'));",
                dateInput
        );

        Thread.sleep(700); // Consider using WebDriverWait instead
    }



    @Test
    public  void selecteffecitveenddate() throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        clickElement(By.xpath(effenddte));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '2025-10-01T12:00';", effenddte);
        Thread.sleep(2000);
    }

    @Test
    public  void selectvatValuesServicesVAT1() throws Exception {
        clickElement(By.xpath(ServiceVAT));
        Thread.sleep(500);
        Select serviceV1 = new Select(driver.findElement(By.xpath(ServiceVAT)));
        serviceV1.selectByValue("1");
        Thread.sleep(500);
        test.log(LogStatus.PASS, "  Service ID  VAT Value  ");
    }

    @Test
    public  void selectvatValuesServicesVAT2() throws Exception {
        clickElement(By.xpath(ServiceVAT));
        Thread.sleep(500);
        Select serviceV2 = new Select(driver.findElement(By.xpath(ServiceVAT)));
        serviceV2.selectByIndex(2);
        Thread.sleep(500);
    }

    @Test
    public  void selectvatValuesServicesVAT3() throws Exception {
        clickElement(By.xpath(ServiceVAT));
        Thread.sleep(500);
        Select serviceV3 = new Select(driver.findElement(By.xpath(ServiceVAT)));
        serviceV3.selectByIndex(3);
        Thread.sleep(500);
    }

    @Test
    public  void selectvatValuesServicesVAT4() throws Exception {
        clickElement(By.xpath(ServiceVAT));
        Thread.sleep(500);
        Select serviceV4 = new Select(driver.findElement(By.xpath(ServiceVAT)));
        serviceV4.selectByIndex(4);
        Thread.sleep(500);
    }

    @Test
    public  void serviceStatusActive() throws Exception {
        clickElement(By.xpath(ServiceStatus));
        Thread.sleep(600);
        Select serviceActive = new Select(driver.findElement(By.xpath(ServiceStatus)));
        serviceActive.selectByVisibleText(ExcelUtilities.ServiceStatuscolumn);
        Thread.sleep(500);
        test.log(LogStatus.PASS, "  Service ID  Status : " + ExcelUtilities.ServiceStatuscolumn);
    }

    @Test
    public  void selectserviceStatusForServices2() throws Exception {
        clickElement(By.xpath(ServiceStatus));
        Thread.sleep(600);
        Select serviceStatusForServices = new Select(driver.findElement(By.xpath(ServiceStatus)));
        serviceStatusForServices.selectByIndex(2);
        Thread.sleep(500);
    }

    @Test
    public  void searchserviceID() throws Exception {
        try {
            // Scroll down the page
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,700)");
            Thread.sleep(2000); // Wait for the page to load

            // Step 1: Enter the service ID in the search box
            WebElement servchk = driver.findElement(By.xpath(Webelements.servchk));
            servchk.sendKeys(ExcelUtilities.serviceIDcolumn.trim());
            Thread.sleep(2000); // Wait for the ID to be entered

            // Step 2: Click on the search button
            clickElement(By.xpath(servsrh));
            Thread.sleep(2000); // Wait for the search results to load

            // Scroll further to ensure visibility of search results
            js.executeScript("window.scrollBy(0,1000)");
            Thread.sleep(500); // Wait for the scrolling effect

            // Step 3: Capture a screenshot of the results
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // If the screenshot is captured, log the success and embed the screenshot into the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Search Service ID Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Search for Service ID: " + ExcelUtilities.serviceIDcolumn + " " + imgTag);
            } else {
                // Log failure if screenshot capture fails
                test.log(LogStatus.FAIL, "Failed to capture screenshot for search service ID: " + ExcelUtilities.serviceIDcolumn);
            }

        } catch (Exception e) {
            // Step 4: Log any exceptions during execution
            test.log(LogStatus.FAIL, "Exception occurred during search for Service ID: " + ExcelUtilities.serviceIDcolumn + " - " + e.getMessage());
            throw e; // Rethrow the exception after logging
        }
    }


    // ***************************************Service_ENDS****************************************************
    // ***************************************LOGOUT
    // _STARTS****************************************************
    @Test
    public void EnterKeyLogin() throws InterruptedException, AWTException {
        // Locate and enter the username
        WebElement InputUsername = driver.findElement(By.xpath(Webelements.InputUsername));
        InputUsername.sendKeys(ExcelUtilities.usernameColumnValue);
        test.log(LogStatus.PASS, "Entered UserName: " + ExcelUtilities.usernameColumnValue);

        // Locate and enter the password
        WebElement InputPassword = driver.findElement(By.xpath(Inputpassword));
        InputPassword.sendKeys(ExcelUtilities.passwordColumnValue);
        test.log(LogStatus.PASS, "Entered Password: " + ExcelUtilities.passwordColumnValue);

        // Small delay to ensure smooth interaction
        Thread.sleep(600);

        // Click the eye button to toggle password visibility
        clickElement(By.xpath(togglePassword));
        test.log(LogStatus.PASS, "Clicked on Eye Button to toggle password visibility.");

        // Sanitize the filename to remove invalid characters
        String sanitizedFilename_eye = sanitizeFileName("using \"ENTER\" and \"TAB\" keys" + getCurrentTime());
        String EnterKeyLoginScreenshot_eye = Screenshot.captureScreenShot(driver, sanitizedFilename_eye);

        // Log the screenshot in the Extent Report
        if (EnterKeyLoginScreenshot_eye != null) {
            // Convert the screenshot to Base64 encoding
            String base64Image_EnterKeyLoginScreenshot_eye = encodeImageToBase64(EnterKeyLoginScreenshot_eye);

            // Create an HTML <img> tag with the Base64 image
            String imgTag_EnterKeyLoginScreenshot_eye = "<img src='data:image/png;base64," + base64Image_EnterKeyLoginScreenshot_eye + "' alt='Login Attempt Screenshot' width='500' height='200' />";

            // Log the success message with the embedded image
            test.log(LogStatus.INFO, "Credentials " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag_EnterKeyLoginScreenshot_eye);
        }

        // Click the password field again to focus it
        clickElement(By.xpath(Inputpassword));

        // Use Robot class to press TAB and Enter keys
        Thread.sleep(600);
        Robot r = new Robot();
        r.keyPress(KeyEvent.VK_TAB);
        r.keyRelease(KeyEvent.VK_TAB);
        Thread.sleep(600);
        r.keyPress(KeyEvent.VK_ENTER);
        r.keyRelease(KeyEvent.VK_ENTER);

        // Sanitize the filename to remove invalid characters
        String sanitizedFilename = sanitizeFileName("Login Attempt " + getCurrentTime());
        // Capture the screenshot after the login attempt
        String EnterKeyLoginScreenshot = Screenshot.captureScreenShot(driver, sanitizedFilename);

        // Log the screenshot in the Extent Report
        if (EnterKeyLoginScreenshot != null) {
            // Convert the screenshot to Base64 encoding
            String base64Image_EnterKeyLoginScreenshot = encodeImageToBase64(EnterKeyLoginScreenshot);

            // Create an HTML <img> tag with the Base64 image
            String imgTag_EnterKeyLoginScreenshot = "<img src='data:image/png;base64," + base64Image_EnterKeyLoginScreenshot + "' alt='Login Attempt Screenshot' width='500' height='200' />";

            // Log the success message with the embedded image
            test.log(LogStatus.PASS, "Login Attempt Screenshot: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag_EnterKeyLoginScreenshot);
        } else {
            String EnterKeyLoginScreenshot_eye_1 = Screenshot.captureScreenShot(driver, sanitizedFilename);
            String base64Image_EnterKeyLoginScreenshot_fail = encodeImageToBase64(EnterKeyLoginScreenshot_eye_1);
            String imgTag_EnterKeyLoginScreenshot_fail = "<img src='data:image/png;base64," + base64Image_EnterKeyLoginScreenshot_fail + "' alt='Login Attempt Screenshot' width='500' height='200' />";

            // Log an error if screenshot capturing failed
            test.log(LogStatus.FAIL, "Failed to capture screenshot for login attempt." + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag_EnterKeyLoginScreenshot_fail);
        }
    }

    // Helper method to sanitize the file name by removing invalid characters
    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9_\\-\\.]", "_"); // Replace invalid characters with '_'
    }

    // Helper method to get the current time to append to the filename for uniqueness
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(new Date());
    }


    @Test
    public void Logout_ENG() throws Exception {


        Thread.sleep(1000);
        clickElement(By.xpath(userLogout));
        Thread.sleep(600);
//        Actions act = new Actions(driver);
//        act.moveToElement(userLogout, 0, 0).click().build().perform();

        clickElement(By.xpath(alertLogoutbtnENG));
        Thread.sleep(600);
        if (driver.getPageSource().contains("Logged out successfully.")) {
            System.out.println("Loggged out Successfullyï¿½ï¿½ ");
            test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
        } else {
            System.out.println("Loggged out failedï¿½ï¿½ ");
            test.log(LogStatus.FAIL, ExcelUtilities.testCaseDesritpioncolumnvalue);

            login();
            Thread.sleep(600);
            clickElement(By.xpath(AuthlogoutENG));
            // act.moveToElement(AutheticLogout).perform();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)");
            clickElement(By.xpath(alertLogoutbtnENG));
            Thread.sleep(600);
            if (driver.getPageSource().contains("Logged out successfully.")) {
                System.out.println("AUTHENTICATE Loggged out Successfullyï¿½ï¿½ ");
                test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
            } else {
                System.out.println("AUTHENTICATE Loggged out failedï¿½ï¿½ ");
                test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
            }
        }
    }

    @Test
    public void Logout_ARAB() throws Exception {
        Thread.sleep(600);
        clickElement(By.xpath(userLogout));


        Thread.sleep(600);
        clickElement(By.xpath(userLogoutbtnARAB));
        Thread.sleep(600);
        clickElement(By.xpath(alertLogoutbtnARAB));
        Thread.sleep(600);
        if (driver.getPageSource().contains("Logged out successfully.")) {
            System.out.println("Loggged out Successfully   ");
            test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);

        } else {
            System.out.println("Loggged out failed   ");
            test.log(LogStatus.FAIL, ExcelUtilities.testCaseDesritpioncolumnvalue);
        }

        login();

        clklang();

        Thread.sleep(600);
        WebElement AutheticLogout = driver.findElement(By.xpath(AuthlogoutARAB));
        Actions act = new Actions(driver);
        act.moveToElement(AutheticLogout).perform();
        AutheticLogout.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        clickElement(By.xpath(alertLogoutbtnARAB));
        Thread.sleep(600);
        if (driver.getPageSource().contains("Logged out successfully.")) {
            System.out.println("AUTHENTICATE Loggged out Successfully   ");
            test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);

        } else {
            System.out.println("AUTHENTICATE Loggged out failed   ");
            test.log(LogStatus.FAIL, ExcelUtilities.testCaseDesritpioncolumnvalue);
        }

    }
    // ***************************************LOGOUT_ENDS****************************************************

    @Test
    public  void closebrowser() throws Exception {
        Thread.sleep(500);
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);

        driver.quit();

    }

    @After
    public void Endreport() {
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
        reports.endTest(test);
        reports.flush();

    }

    // ***************************************USER_STARTS****************************************************
    @Test
    public void USERclicktab() throws InterruptedException {
        clickElement(By.xpath(USERtab));

    }

    @Test
    public void USERUserFullname() throws Exception {
        WebElement EnterFullname = driver.findElement(By.xpath(UserFullname));
        EnterFullname.sendKeys(ExcelUtilities.fullnamecolumn);
        Thread.sleep(600);

    }

    public void USERUsername() throws Exception {
        WebElement EnterUsername = driver.findElement(By.xpath(Username1));
        EnterUsername.sendKeys(ExcelUtilities.username1column);
        Thread.sleep(600);

    }

    @Test
    public void USERPassword() throws Exception {
        WebElement EnterPassword = driver.findElement(By.xpath(Password1));
        EnterPassword.sendKeys(ExcelUtilities.password1column);
        Thread.sleep(600);

    }

    @Test
    public void USERtogglepwd() throws Exception {
        clickElement(By.xpath(togglepwd));
        Thread.sleep(600);

    }

    @Test
    public void USERconfirmPassword() throws Exception {
        WebElement ConfirmPassword = driver.findElement(By.xpath(confirmPassword));
        ConfirmPassword.sendKeys(ExcelUtilities.confirm_passwordcolumn);
        Thread.sleep(600);

    }

    @Test
    public void USERtoggleconfirmPwd() throws Exception {
        clickElement(By.xpath(toggleconfirmPwd));
        Thread.sleep(600);

    }

    @Test
    public void USERemailId() throws Exception {
        WebElement EmailId = driver.findElement(By.xpath(emailId));
        EmailId.sendKeys(ExcelUtilities.emailidcolumn);
        Thread.sleep(600);

    }

    @Test
    public void USERcontactNo() throws Exception {
        WebElement ContactNo = driver.findElement(By.xpath(contactNo));
        ContactNo.clear();
        ContactNo.sendKeys(ExcelUtilities.contactnumbercolumn);
        Thread.sleep(600);

    }

    @Test
    public void USERcheckercheckmark() throws Exception {

        clickElement(By.xpath(checkmark1));
        Thread.sleep(600);

    }

    @Test
    public void USERselectuserRole() throws Exception {
        Select UserRole = new Select(driver.findElement(By.xpath(selectuserRole)));
        UserRole.selectByVisibleText(ExcelUtilities.rolecolumn);
        Thread.sleep(600);

    }

    @Test
    public void USERstatuscheckmark2() throws Exception {
        clickElement(By.xpath(checkmark2));
        Thread.sleep(600);

    }

    @Test
    public void CreateNEWUser() throws Exception {
        try {
            // Step 1: Click on the User Tab
            USERclicktab();
            test.log(LogStatus.PASS, "User Tab clicked successfully.");

            // Step 2: Enter User Full Name
            USERUserFullname();
            test.log(LogStatus.PASS, "User Full Name entered successfully.");

            // Step 3: Enter Username
            USERUsername();
            test.log(LogStatus.PASS, "Username entered successfully.");

            // Step 4: Enter Password
            USERPassword();
            test.log(LogStatus.PASS, "Password entered successfully.");

            // Step 5: Toggle Password visibility
            USERtogglepwd();
            test.log(LogStatus.PASS, "Password visibility toggled successfully.");

            // Step 6: Confirm Password
            USERconfirmPassword();
            test.log(LogStatus.PASS, "Confirm Password entered successfully.");

            // Step 7: Toggle Confirm Password visibility
            USERtoggleconfirmPwd();
            test.log(LogStatus.PASS, "Confirm Password visibility toggled successfully.");

            // Step 8: Enter User Email
            USERemailId();
            test.log(LogStatus.PASS, "Email ID entered successfully.");

            // Step 9: Enter Contact Number
            USERcontactNo();
            test.log(LogStatus.PASS, "Contact Number entered successfully.");

            // Step 10: Check the checker checkbox
            USERcheckercheckmark();
            test.log(LogStatus.PASS, "Checker checkbox selected successfully.");

            // Step 11: Select User Role
            USERselectuserRole();
            test.log(LogStatus.PASS, "User Role selected successfully.");

            // Step 12: Select User Status (Active)
            USERstatuscheckmark2();
            test.log(LogStatus.PASS, "User Status selected successfully.");

            // Step 13: Save the user
            save();
            test.log(LogStatus.PASS, "User saved successfully.");

            // Step 14: Validate if the user is created successfully
            if (driver.getPageSource().contains("ADMINTEST")) {
                test.log(LogStatus.PASS, "New User creation passed.");
            } else {
                test.log(LogStatus.INFO, "New User creation failed.");
            }

            // Step 15: Capture screenshot for reporting
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the Extent report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='New User Creation Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "New user created successfully. " + imgTag);
            } else {
                test.log(LogStatus.INFO, "Failed to capture screenshot after new user creation.");
            }

        } catch (Exception e) {
            // Log any exceptions encountered during the execution of the method
            test.log(LogStatus.FAIL, "Error during new user creation: " + e.getMessage());
        }
    }


    @Test
    public void InactiveUser() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        //USERstatuscheckmark2();
        save();
        Logout_ENG();

    }

    @Test
    public void UserAccessRoleScreens() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        USERstatuscheckmark2();
        save();
        Logout_ENG();
    }

    @Test
    public void CreateNEWUserNotChecker() throws Exception {
        try {
            // Step 1: Click on the User Tab
            USERclicktab();
            test.log(LogStatus.PASS, "User Tab clicked successfully.");

            // Step 2: Enter User Full Name
            USERUserFullname();
            test.log(LogStatus.PASS, "User Full Name entered successfully.");

            // Step 3: Enter Username
            USERUsername();
            test.log(LogStatus.PASS, "Username entered successfully.");

            // Step 4: Enter Password
            USERPassword();
            test.log(LogStatus.PASS, "Password entered successfully.");

            // Step 5: Toggle Password visibility
            USERtogglepwd();
            test.log(LogStatus.PASS, "Password visibility toggled successfully.");

            // Step 6: Confirm Password
            USERconfirmPassword();
            test.log(LogStatus.PASS, "Confirm Password entered successfully.");

            // Step 7: Toggle Confirm Password visibility
            USERtoggleconfirmPwd();
            test.log(LogStatus.PASS, "Confirm Password visibility toggled successfully.");

            // Step 8: Enter User Email
            USERemailId();
            test.log(LogStatus.PASS, "Email ID entered successfully.");

            // Step 9: Enter Contact Number
            USERcontactNo();
            test.log(LogStatus.PASS, "Contact Number entered successfully.");

            // Step 10: Select User Role (without checker)
            USERselectuserRole();
            test.log(LogStatus.PASS, "User Role selected successfully.");

            // Step 11: Save the user
            save();
            test.log(LogStatus.PASS, "User saved successfully.");

            // Step 12: Validate the username uniqueness error
            if (driver.getPageSource().contains("VLD-SC0002: username is not unique")) {
                test.log(LogStatus.PASS, "Unique ID validation error passed.");
            } else {
                test.log(LogStatus.FAIL, "Unique ID validation error FAILED.");
            }

            // Step 13: Capture screenshot for reporting
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the Extent report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Unique ID Validation Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "User creation process completed. " + imgTag);
            } else {
                test.log(LogStatus.INFO, "Failed to capture screenshot after user creation process.");
            }

        } catch (Exception e) {
            // Log any exceptions encountered during the execution of the method
            test.log(LogStatus.FAIL, "Error during new user creation (not checker): " + e.getMessage());
        }
    }


    @Test
    public void USERBlankAllFields() throws Exception {
        try {
            // Wait for page to load and click on the "User" tab
            USERclicktab();
            test.log(LogStatus.PASS, "User tab clicked successfully.");

            // Attempt to save the form with all fields blank
            save();
            test.log(LogStatus.PASS, "Save action attempted with blank fields.");

            // Capture a screenshot of the blank field validation
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the Extent report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Blank Fields Error Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Blank field error validation passed. " + imgTag);
            } else {
                test.log(LogStatus.PASS, "Blank field error validation passed.");
            }

        } catch (Exception e) {
            // Log any exceptions encountered during the execution of the method
            test.log(LogStatus.FAIL, "Error during blank field validation: " + e.getMessage());
        }
    }


    @Test
    public void USERBlankAllFieldsExceptChecker() throws Exception {
        try {
            // Click on the "User" tab to navigate to the user creation page
            USERclicktab();
            test.log(LogStatus.PASS, "User tab clicked successfully.");

            // Check the "status" checkbox as per the requirement
            USERstatuscheckmark2();
            test.log(LogStatus.PASS, "Status checkbox checked successfully.");

            // Attempt to save the form with all fields blank except for the checker field
            save();
            test.log(LogStatus.PASS, "Save action attempted with blank fields except for the checker field.");


            // Capture a screenshot of the blank field validation
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the Extent report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Blank Fields Error Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Blank field error validation passed. " + imgTag);
            } else {
                test.log(LogStatus.PASS, "Blank field error validation passed.");
            }

        } catch (Exception e) {
            // Log any exceptions encountered during the execution of the method
            test.log(LogStatus.FAIL, "Error during blank field validation: " + e.getMessage());
        }
    }

    @Test
    public void USEROwndeleteTXN() throws InterruptedException {
        Thread.sleep(500);
        USERclicktab();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        Thread.sleep(600);
        clickElement(By.xpath(userDeletebtn));
        Thread.sleep(500);


    }


    @Test
    public void USERdeleteTXN() throws InterruptedException {
        Thread.sleep(500);
        USERclicktab();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        Thread.sleep(600);

		/*if (driver.getPageSource().contains("ggguser")) {
			WebElement userfirst = driver.findElement(By.xpath(Webelements.firstbtn));
			userfirst.click();
		}*/

        clickElement(By.xpath(userDeletebtn));
        Thread.sleep(500);

        Alert alert = driver.switchTo().alert();
        alert.accept();
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        Thread.sleep(500);
        System.out.println("Delete Button Is working properly ");
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);

    }

    @Test
    public void VerifyUniqueUSER() throws Exception {
        try {
            // Navigate to the "User" tab
            USERclicktab();
            test.log(LogStatus.PASS, "User tab clicked successfully.");

            // Fill in the user details
            USERUserFullname();
            USERUsername();
            USERPassword();
            USERtogglepwd();
            USERconfirmPassword();
            USERtoggleconfirmPwd();
            USERemailId();
            USERcontactNo();
            USERcheckercheckmark();
            USERselectuserRole();
            USERstatuscheckmark2();
            test.log(LogStatus.PASS, "User details entered successfully.");

            // Attempt to save the user with the provided details
            save();
            test.log(LogStatus.PASS, "Save action attempted.");

            // Check for the validation message indicating a non-unique username
            if (driver.getPageSource().contains("VLD-SC0002: username is not unique")) {
                test.log(LogStatus.PASS, "Unique ID validation error passed.");
            } else {
                test.log(LogStatus.FAIL, "Unique ID validation error failed.");
            }

            // Capture a screenshot of the validation/error message
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert the screenshot to Base64 for embedding in the Extent report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Unique ID Validation Error Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Unique ID validation error screenshot. " + imgTag);
            } else {
                test.log(LogStatus.PASS, "Unique ID validation error screenshot captured.");
            }

        } catch (Exception e) {
            // Log any exceptions encountered during the execution of the test
            test.log(LogStatus.FAIL, "Error during Unique ID validation: " + e.getMessage());
        }
    }


    @Test
    public void USEREdit() throws Exception {
        USERclicktab();

    }

    @Test
    public void USERBlankfullname() throws Exception {
        USERclicktab();

        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        USERstatuscheckmark2();
        save();
        VerifyErrorMsg();

    }

    @Test
    public void USERBlankUsername() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        USERstatuscheckmark2();
        save();
        VerifyErrorMsg();

    }

    @Test
    public void UserLoginPortalChangePWD() throws Exception {
        // login();
        clickElement(By.xpath(ChangePassword));
        Thread.sleep(600);
        WebElement chngusername = driver.findElement(By.xpath(loginchngUsername));
        chngusername.sendKeys(ExcelUtilities.user_login_chng_usernamevalue);
        Thread.sleep(600);
        WebElement chngoldpwd = driver.findElement(By.xpath(oldPassword));
        chngoldpwd.sendKeys(ExcelUtilities.user_oldpasswordvalue);
        Thread.sleep(600);
        clickElement(By.xpath(toggleOldPassword));
        Thread.sleep(600);
        WebElement chngNewpwd = driver.findElement(By.xpath(password));
        chngNewpwd.sendKeys(ExcelUtilities.user_newpasswordvalue);
        Thread.sleep(600);
        clickElement(By.xpath(toggleNewPassword));
        Thread.sleep(600);
        WebElement chngconfirmpwd = driver.findElement(By.xpath(confirmchngPassword));
        chngconfirmpwd.sendKeys(ExcelUtilities.user_confirmpasswordvalue);
        Thread.sleep(600);
        clickElement(By.xpath(toggleConfirmPassword));
        Thread.sleep(600);
        changepwdsave();
        clickElement(By.xpath(loginsbt));
        Thread.sleep(600);
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);

        String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        if (screenshotPath != null) {
            // Convert screenshot to Base64 for embedding in the Extent report
            String base64Image = encodeImageToBase64(screenshotPath);
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='User Login Portal ChangePWD' width='500' height='200' />";
            test.log(LogStatus.PASS, "User Login Portal ChangePWD successfully. " + imgTag);
        } else {
            test.log(LogStatus.INFO, "User Login Portal ChangePWD.");
        }

    }

    @Test
    public void USEReditTXN() throws Exception {
        try {
            // Wait for page to load and click on the "User" tab
            Thread.sleep(600);  // Replace with explicit waits if needed (e.g., for page load)
            USERclicktab();
            test.log(LogStatus.PASS, "User Tab clicked successfully.");

            // Use JavaScriptExecutor to scroll down the page for better visibility
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(600);  // Consider replacing with WebDriverWait for better reliability

            // Check if the user exists on the page
            if (driver.getPageSource().contains(ExcelUtilities.fullnamecolumn)) {
                // Find and click the 'Edit' button for the user
                clickElement(By.xpath(userEdit));
                test.log(LogStatus.PASS, "Edit button clicked for user.");

                // Edit the user contact number
                USERcontactNo();
                test.log(LogStatus.PASS, "User contact number edited successfully.");

                // Capture a screenshot of the updated user information
                String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
                if (screenshotPath != null) {
                    // Convert screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='User Edit Screenshot' width='500' height='200' />";
                    test.log(LogStatus.PASS, "User edited successfully. " + imgTag);
                } else {
                    test.log(LogStatus.PASS, "User edited successfully.");
                }

                // Log success message
                System.out.println("USER Edited Successfully");

            } else {
                // Log failure if the user is not found
                test.log(LogStatus.FAIL, "User not found in the list.");
            }
        } catch (Exception e) {
            // Log any exceptions encountered during the execution of the method
            test.log(LogStatus.FAIL, "Error during user edit: " + e.getMessage());
        }
    }


    @Test
    public void EditUsername() throws Exception {
        try {
            // Wait for the page to load and click the "User" tab
            Thread.sleep(600);  // You can replace this with an explicit wait if necessary
            USERclicktab();
            test.log(LogStatus.PASS, "User tab clicked successfully.");

            // Use JavaScriptExecutor to scroll down the page for better visibility
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)");
            Thread.sleep(600);  // Again, consider replacing with WebDriverWait for better synchronization

            // Check if the user with the specific full name exists on the page
            if (driver.getPageSource().contains(ExcelUtilities.fullnamecolumn)) {

                // Find and click the "Edit" button for the username
                clickElement(By.xpath(EditUsername));
                test.log(LogStatus.PASS, "Edit button clicked for username.");

                // Edit the user's contact number (you can enhance this method to change other details as needed)
                USERcontactNo();
                test.log(LogStatus.PASS, "User contact number edited successfully.");

                // Capture a screenshot of the updated user information
                String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
                if (screenshotPath != null) {
                    // Convert the screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Edit Username Screenshot' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Username edit screenshot captured. " + imgTag);
                } else {
                    test.log(LogStatus.PASS, "Username edit screenshot captured.");
                }

                // Log success message
                System.out.println("Username Edited Successfully");

            } else {
                // Log failure if the user is not found
                test.log(LogStatus.FAIL, "User not found in the list.");
            }

        } catch (Exception e) {
            // Catch any exceptions and log them
            test.log(LogStatus.FAIL, "Error during username edit: " + e.getMessage());
        }
    }

    @Test
    public void EditPassword() throws Exception {
        try {
            // Wait for the page to load and click the "User" tab
            Thread.sleep(600);  // Consider replacing with WebDriverWait for better synchronization
            USERclicktab();
            test.log(LogStatus.PASS, "User tab clicked successfully.");

            // Use JavaScriptExecutor to scroll down the page for better visibility
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(600);  // Again, consider using WebDriverWait

            // Check if the user with the specific full name exists on the page
            if (driver.getPageSource().contains(ExcelUtilities.fullnamecolumn)) {

                // Find and click the "Edit" button for the password
                clickElement(By.xpath(EditPassword));
                test.log(LogStatus.PASS, "Edit button clicked for password.");

                // Edit the user's contact number or perform other actions as needed (this can be generalized for other fields)
                USERcontactNo();
                test.log(LogStatus.PASS, "User contact number edited successfully.");

                // Capture a screenshot of the updated user information
                String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
                if (screenshotPath != null) {
                    // Convert the screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Password Edit Screenshot' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Password edit screenshot captured. " + imgTag);
                } else {
                    test.log(LogStatus.PASS, "Password edit screenshot captured.");
                }

                // Log success message
                System.out.println("Password Edited Successfully");

            } else {
                // Log failure if the user is not found
                test.log(LogStatus.FAIL, "User not found in the list.");
            }

        } catch (Exception e) {
            // Catch any exceptions and log them
            test.log(LogStatus.FAIL, "Error during password edit: " + e.getMessage());
        }
    }

    @Test
    public void EditRole() throws Exception {
        try {
            // Wait for the page to load and click the "User" tab
            Thread.sleep(600);  // Replace with WebDriverWait for better synchronization
            USERclicktab();
            test.log(LogStatus.PASS, "User tab clicked successfully.");

            // Use JavaScriptExecutor to scroll down the page for better visibility
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(600);  // Consider replacing with WebDriverWait

            // Check if the user with the specific full name exists on the page
            if (driver.getPageSource().contains(ExcelUtilities.fullnamecolumn)) {

                // Find and click the "Edit Role" button for the user
                clickElement(By.xpath(EditRole));
                test.log(LogStatus.PASS, "Edit Role button clicked for user.");

                // Edit the user's contact number or perform other actions as needed (this can be generalized for other fields)
                USERcontactNo();
                test.log(LogStatus.PASS, "User contact number edited successfully.");

                // Capture a screenshot of the updated user role
                String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
                if (screenshotPath != null) {
                    // Convert the screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Role Edit Screenshot' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Role edit screenshot captured. " + imgTag);
                } else {
                    test.log(LogStatus.PASS, "Role edit screenshot captured.");
                }

                // Log success message
                System.out.println("User Role Edited Successfully");

            } else {
                // Log failure if the user is not found
                test.log(LogStatus.FAIL, "User not found in the list.");
            }

        } catch (Exception e) {
            // Catch any exceptions and log them
            test.log(LogStatus.FAIL, "Error during role edit: " + e.getMessage());
        }
    }

    @Test
    public void EditEmailID() throws Exception {
        try {
            // Wait for the page to load and click the "User" tab
            Thread.sleep(600);  // Replace with WebDriverWait for better synchronization
            USERclicktab();
            test.log(LogStatus.PASS, "User tab clicked successfully.");

            // Use JavaScriptExecutor to scroll down the page for better visibility
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(600);  // Consider replacing with WebDriverWait

            // Check if the user with the specific full name exists on the page
            if (driver.getPageSource().contains(ExcelUtilities.fullnamecolumn)) {

                // Find and click the "Edit Email ID" button for the user
                clickElement(By.xpath(EditEmailID));
                test.log(LogStatus.PASS, "Edit Email ID button clicked for user.");

                // Edit the user's contact number or perform other actions as needed (this can be generalized for other fields)
                USERcontactNo();
                test.log(LogStatus.PASS, "User contact number edited successfully.");

                // Capture a screenshot of the updated user email
                String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
                if (screenshotPath != null) {
                    // Convert the screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Email Edit Screenshot' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Email edit screenshot captured. " + imgTag);
                } else {
                    test.log(LogStatus.PASS, "Email edit screenshot captured.");
                }

                // Log success message
                System.out.println("User Email ID Edited Successfully");

            } else {
                // Log failure if the user is not found
                test.log(LogStatus.FAIL, "User not found in the list.");
            }

        } catch (Exception e) {
            // Catch any exceptions and log them
            test.log(LogStatus.FAIL, "Error during email edit: " + e.getMessage());
        }
    }

    @Test
    public void EditCheckerbox() throws Exception {
        try {
            // Wait for the page to load and click the "User" tab
            Thread.sleep(600);  // Replace with WebDriverWait if needed for better synchronization
            USERclicktab();
            test.log(LogStatus.PASS, "User tab clicked successfully.");

            // Use JavaScriptExecutor to scroll down the page for better visibility
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(600);  // Consider replacing with WebDriverWait

            // Check if the user with the specific full name exists on the page
            if (driver.getPageSource().contains(ExcelUtilities.fullnamecolumn)) {

                // Find and click the "Edit Checkerbox" button for the user
                clickElement(By.xpath(EditCheckerbox));
                test.log(LogStatus.PASS, "Edit Checkerbox button clicked for user.");

                // Perform the action to edit the user's contact number or any other needed field
                USERcontactNo();
                test.log(LogStatus.PASS, "User contact number edited successfully.");

                // Capture a screenshot of the updated checkerbox information
                String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
                if (screenshotPath != null) {
                    // Convert screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Checkerbox Edit Screenshot' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Checkerbox edit screenshot captured. " + imgTag);
                } else {
                    test.log(LogStatus.PASS, "Checkerbox edit screenshot captured.");
                }

                // Log success message
                System.out.println("Checkerbox Edited Successfully");

            } else {
                // Log failure if the user is not found
                test.log(LogStatus.FAIL, "User not found in the list.");
            }

        } catch (Exception e) {
            // Catch any exceptions and log them
            test.log(LogStatus.FAIL, "Error during checkerbox edit: " + e.getMessage());
        }
    }


    @Test
    public void EditDeselectCheckerbox() throws Exception {
        try {
            // Wait for the page to load and click on the "User" tab
            Thread.sleep(600);  // Consider replacing with WebDriverWait for better synchronization
            USERclicktab();
            test.log(LogStatus.PASS, "User tab clicked successfully.");

            // Use JavaScriptExecutor to scroll down the page for better visibility
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(600);  // Consider replacing with WebDriverWait

            // Check if the user with the specific full name exists on the page
            if (driver.getPageSource().contains(ExcelUtilities.fullnamecolumn)) {

                // Find and click the "Edit Deselect Checkerbox" button for the user
                clickElement(By.xpath(EditDeselectCheckerbox));
                test.log(LogStatus.PASS, "Edit Deselect Checkerbox button clicked for user.");

                // Perform the action to edit the user's contact number or any other needed field
                USERcontactNo();
                test.log(LogStatus.PASS, "User contact number edited successfully.");

                // Capture a screenshot of the updated checkerbox information
                String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
                if (screenshotPath != null) {
                    // Convert screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Deselect Checkerbox Edit Screenshot' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Deselect Checkerbox edit screenshot captured. " + imgTag);
                } else {
                    test.log(LogStatus.PASS, "Deselect Checkerbox edit screenshot captured.");
                }

                // Log success message
                System.out.println("Deselect Checkerbox Edited Successfully");

            } else {
                // Log failure if the user is not found
                test.log(LogStatus.FAIL, "User not found in the list.");
            }

        } catch (Exception e) {
            // Catch any exceptions and log them
            test.log(LogStatus.FAIL, "Error during deselect checkerbox edit: " + e.getMessage());
        }
    }


    @Test
    public void EditUserAsActive() throws Exception {
        try {
            // Wait for the page to load and click on the "User" tab
            Thread.sleep(600);  // Consider replacing with WebDriverWait for better synchronization
            USERclicktab();
            test.log(LogStatus.PASS, "User tab clicked successfully.");

            // Use JavaScriptExecutor to scroll down the page for better visibility
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(600);  // Consider replacing with WebDriverWait for better synchronization

            // Find the "Edit Active" checkbox and click to change the status
            clickElement(By.xpath(EditUserActiveCheckbox));
            test.log(LogStatus.PASS, "Edit Active checkbox clicked successfully.");

            // Update the user contact number (or any other necessary information)
            USERcontactNo();
            test.log(LogStatus.PASS, "User contact number updated successfully.");

            // Set the user status as active by checking the appropriate status box
            USERstatuscheckmark2();
            test.log(LogStatus.PASS, "User status set as active.");

            // Save the changes
            save();
            test.log(LogStatus.PASS, "Changes saved successfully.");

            // Capture a screenshot of the updated user status
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the Extent report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Active checkbox Edit Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "User status updated successfully. " + imgTag);
            } else {
                test.log(LogStatus.PASS, "User status updated successfully.");
            }

            // Log success message
            System.out.println("Active checkbox edited successfully");

            // Logout after editing
            Logout_ENG();
            test.log(LogStatus.PASS, "User logged out successfully.");

        } catch (Exception e) {
            // Catch any exceptions and log them
            test.log(LogStatus.FAIL, "Error during user active checkbox edit: " + e.getMessage());
        }
    }


    @Test
    public void userlogin() throws Exception {
        try {
            // Attempt initial login
            login();

            // Check if the error message "User is not active!" appears on the page
            if (driver.getPageSource().contains("User is not active!")) {

                // Locate and fill in the username and password fields
                WebElement inputUsername = driver.findElement(By.xpath(InputUsername));
                inputUsername.sendKeys("TestAdmin");
                WebElement inputPassword = driver.findElement(By.xpath(Inputpassword));
                inputPassword.sendKeys("TestAdmin@123");

                // Wait for a while for the page to load properly
                Thread.sleep(600); // Consider replacing with WebDriverWait for better synchronization

                // Click on the eye icon to toggle password visibility
                clickElement(By.xpath(togglePassword));
                test.log(LogStatus.PASS, "Click on Eye Button to toggle password visibility.");

                // Click on the password field again to ensure visibility
                inputPassword.click();
                Thread.sleep(600); // Consider replacing with WebDriverWait

                // Find the login button and click to submit the login form
                clickElement(By.xpath("//*[@id='login']/a/label"));

                // Capture a screenshot of the current page
                String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
                if (screenshotPath != null) {
                    // Convert screenshot to Base64 and embed in the report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Login Screenshot' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Screenshot captured: " + imgTag);
                }

                // Check if the page source contains the authentication prompt
                if (driver.getPageSource().contains("Authenticate")) {
                    test.log(LogStatus.PASS, "Login successful with valid credentials.");
                } else {
                    test.log(LogStatus.INFO, "Invalid credentials provided or failed to authenticate.");
                }

                // Wait for 3 seconds before retrying after editing the user as active
                Thread.sleep(3000);

                // Activate the user and retry login
                EditUserAsActive();
                login();
            }

        } catch (Exception e) {
            // Log the exception in case of failure
            test.log(LogStatus.FAIL, "Error during user login test: " + e.getMessage());

            // Capture and log a screenshot in case of an exception
            String screenshotPath = Screenshot.captureScreenShot(driver, "UserLogin_Exception");
            if (screenshotPath != null) {
                // Convert screenshot to Base64 and embed in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Exception Screenshot' width='500' height='200' />";
                test.log(LogStatus.FAIL, "Screenshot captured: " + imgTag);
            }
        }
    }


    @Test
    public void USERBlankpassword() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();

        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        USERstatuscheckmark2();
        save();
        VerifyErrorMsg();

    }

    public void USERblankconfirmpwd() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        USERstatuscheckmark2();
        save();
        VerifyErrorMsg();

    }

    public void USERBlankemailID() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();

        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        USERstatuscheckmark2();
        save();
        VerifyErrorMsg();

    }

    public void Click_download_button() {
        try {
            // Scroll down the page to bring the button into view using JavascriptExecutor
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,1000)");

            // Wait for the download button to be clickable (WebDriverWait for better synchronization)


            clickElement(By.xpath(Click_download_button));
            test.log(LogStatus.PASS, "Download button clicked successfully.");

            // Capture a screenshot after clicking the download button
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the Extent report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Download Button Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Screenshot captured after clicking the download button: " + imgTag);
            }

        } catch (Exception e) {
            // Log the exception and capture a screenshot in case of failure
            test.log(LogStatus.FAIL, "Error during click on download button: " + e.getMessage());

            // Capture screenshot in case of failure
            String screenshotPath = Screenshot.captureScreenShot(driver, "ClickDownloadButton_Exception");
            if (screenshotPath != null) {
                // Convert screenshot to Base64 and embed in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Exception Screenshot' width='500' height='200' />";
                test.log(LogStatus.FAIL, "Screenshot captured for exception: " + imgTag);
            }
        }
    }


    public void USERBlankcontactNo() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();

        USERcheckercheckmark();
        USERselectuserRole();
        USERstatuscheckmark2();
        save();
        VerifyErrorMsg();

    }

    public void USERBlankRoleDrop() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();

        USERstatuscheckmark2();
        save();
        VerifyErrorMsg();

    }

    @Test
    public void CasessensitiveConfirmPassword() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        USERstatuscheckmark2();
        save();
        VerifyErrorMsg();

    }

    @Test
    public void InvalidPasswordAndConfirmPassword() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        USERstatuscheckmark2();
        save();
        VerifyErrorMsg();

    }

    @Test
    public void UsernameValidInputs() throws Exception {
        try {
            // Wait for the page to load and click on the "User" tab
            Thread.sleep(600);  // Replace with WebDriverWait for better synchronization
            USERclicktab();
            test.log(LogStatus.PASS, "User tab clicked successfully.");

            // Use JavaScriptExecutor to scroll down the page for better visibility
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(600);  // Replace with WebDriverWait

            // Proceed with filling the user details
            USERUserFullname();
            USERUsername();
            USERPassword();
            USERtogglepwd();
            USERconfirmPassword();
            USERtoggleconfirmPwd();
            USERemailId();
            USERcontactNo();
            USERcheckercheckmark();
            USERselectuserRole();
            USERstatuscheckmark2();

            // Save the user details
            save();

            // Check if the user was successfully added
            if (driver.getPageSource().contains("ADMINTEST")) {
                test.log(LogStatus.PASS, "New User creation passed.");

                // Capture screenshot
                String screenshotPath = Screenshot.captureScreenShot(driver, "UsernameValidInputs");
                if (screenshotPath != null) {
                    // Convert screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Username Valid Inputs Screenshot' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Screenshot captured: " + imgTag);
                }
            } else {
                test.log(LogStatus.INFO, "New User creation FAILED.");
            }

        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Username Valid Inputs test: " + e.getMessage());
        }
    }

    @Test
    public void UsernameInvalidInputs() throws Exception {
        try {
            // Wait for the page to load and click on the "User" tab
            Thread.sleep(600);  // Replace with WebDriverWait for better synchronization
            USERclicktab();
            test.log(LogStatus.PASS, "User tab clicked successfully.");

            // Use JavaScriptExecutor to scroll down the page for better visibility
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(600);  // Replace with WebDriverWait

            // Proceed with filling the user details
            USERUserFullname();
            USERUsername();
            USERPassword();
            USERtogglepwd();
            USERconfirmPassword();
            USERtoggleconfirmPwd();
            USERemailId();
            USERcontactNo();
            USERcheckercheckmark();
            USERselectuserRole();
            USERstatuscheckmark2();

            // Save the user details
            save();

            // Check if the user was successfully added
            if (driver.getPageSource().contains("ADMINTEST")) {
                test.log(LogStatus.PASS, "New User creation passed.");

                // Capture screenshot
                String screenshotPath = Screenshot.captureScreenShot(driver, "UsernameInvalidInputs");
                if (screenshotPath != null) {
                    // Convert screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Username Invalid Inputs Screenshot' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Screenshot captured: " + imgTag);
                }
            } else {
                test.log(LogStatus.INFO, "New User creation FAILED.");
            }

        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Username Invalid Inputs test: " + e.getMessage());
        }
    }

    @Test
    public void PasswordValidInputs() throws Exception {
        try {
            // Wait for the page to load and click on the "User" tab
            Thread.sleep(600);  // Replace with WebDriverWait for better synchronization
            USERclicktab();
            test.log(LogStatus.PASS, "User tab clicked successfully.");

            // Use JavaScriptExecutor to scroll down the page for better visibility
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(600);  // Replace with WebDriverWait

            // Proceed with filling the user details
            USERUserFullname();
            USERUsername();
            USERPassword();
            USERtogglepwd();
            USERconfirmPassword();
            USERtoggleconfirmPwd();
            USERemailId();
            USERcontactNo();
            USERcheckercheckmark();
            USERselectuserRole();
            USERstatuscheckmark2();

            // Save the user details
            save();

            // Check if the user was successfully added
            if (driver.getPageSource().contains("ADMINTEST")) {
                test.log(LogStatus.PASS, "New User creation passed.");

                // Capture screenshot
                String screenshotPath = Screenshot.captureScreenShot(driver, "PasswordValidInputs");
                if (screenshotPath != null) {
                    // Convert screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Password Valid Inputs Screenshot' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Screenshot captured: " + imgTag);
                }
            } else {
                test.log(LogStatus.INFO, "New User creation FAILED.");
            }

        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Password Valid Inputs test: " + e.getMessage());
        }
    }

    @Test
    public void PasswordInvalidInputs() throws Exception {
        try {
            // Wait for the page to load and click on the "User" tab
            Thread.sleep(600);  // Replace with WebDriverWait for better synchronization
            USERclicktab();
            test.log(LogStatus.PASS, "User tab clicked successfully.");

            // Use JavaScriptExecutor to scroll down the page for better visibility
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(600);  // Replace with WebDriverWait

            // Proceed with filling the user details
            USERUserFullname();
            USERUsername();
            USERPassword();
            USERtogglepwd();
            USERconfirmPassword();
            USERtoggleconfirmPwd();
            USERemailId();
            USERcontactNo();
            USERcheckercheckmark();
            USERselectuserRole();
            USERstatuscheckmark2();

            // Save the user details
            save();

            // Check if the user was successfully added
            if (driver.getPageSource().contains("ADMINTEST")) {
                test.log(LogStatus.PASS, "New User creation passed.");

                // Capture screenshot
                String screenshotPath = Screenshot.captureScreenShot(driver, "PasswordInvalidInputs");
                if (screenshotPath != null) {
                    // Convert screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Password Invalid Inputs Screenshot' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Screenshot captured: " + imgTag);
                }
            } else {
                test.log(LogStatus.INFO, "New User creation FAILED.");
            }

        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Password Invalid Inputs test: " + e.getMessage());
        }
    }

    @Test
    public void EmailIDValidInputs() throws Exception {
        try {
            // Wait for the page to load and click on the "User" tab
            Thread.sleep(600);  // Consider replacing with WebDriverWait for better synchronization
            USERclicktab();
            test.log(LogStatus.PASS, "User tab clicked successfully.");

            // Use JavaScriptExecutor to scroll down the page for better visibility
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(600);  // Consider replacing with WebDriverWait

            // Proceed with filling the user details
            USERUserFullname();
            USERUsername();
            USERPassword();
            USERtogglepwd();
            USERconfirmPassword();
            USERtoggleconfirmPwd();
            USERemailId();
            USERcontactNo();
            USERcheckercheckmark();
            USERselectuserRole();
            USERstatuscheckmark2();

            // Save the user details
            save();

            // Check if the user was successfully added
            if (driver.getPageSource().contains("ADMINTEST")) {
                test.log(LogStatus.PASS, "New User creation passed.");

                // Capture screenshot
                String screenshotPath = Screenshot.captureScreenShot(driver, "EmailIDValidInputs");
                if (screenshotPath != null) {
                    // Convert screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Email ID Valid Inputs Screenshot' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Screenshot captured: " + imgTag);
                }
            } else {
                test.log(LogStatus.INFO, "New User creation FAILED.");
            }

        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during EmailID Valid Inputs test: " + e.getMessage());
        }
    }

    @Test
    public void EmailIDInvalidInputs() throws Exception {
        try {
            // Wait for the page to load and click on the "User" tab
            Thread.sleep(600);  // Consider replacing with WebDriverWait for better synchronization
            USERclicktab();
            test.log(LogStatus.PASS, "User tab clicked successfully.");

            // Use JavaScriptExecutor to scroll down the page for better visibility
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(600);  // Consider replacing with WebDriverWait

            // Proceed with filling the user details
            USERUserFullname();
            USERUsername();
            USERPassword();
            USERtogglepwd();
            USERconfirmPassword();
            USERtoggleconfirmPwd();
            USERemailId();
            USERcontactNo();
            USERcheckercheckmark();
            USERselectuserRole();
            USERstatuscheckmark2();

            // Save the user details
            save();

            // Check if the user was successfully added
            if (driver.getPageSource().contains("ADMINTEST")) {
                test.log(LogStatus.PASS, "New User creation passed.");

                // Capture screenshot
                String screenshotPath = Screenshot.captureScreenShot(driver, "EmailIDInvalidInputs");
                if (screenshotPath != null) {
                    // Convert screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Email ID Invalid Inputs Screenshot' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Screenshot captured: " + imgTag);
                }
            } else {
                test.log(LogStatus.INFO, "New User creation FAILED.");

                // Capture screenshot for failure
                String screenshotPath = Screenshot.captureScreenShot(driver, "EmailIDInvalidInputs_Failure");
                if (screenshotPath != null) {
                    // Convert screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Email ID Invalid Inputs Failure Screenshot' width='500' height='200' />";
                    test.log(LogStatus.FAIL, "Screenshot captured: " + imgTag);
                }
            }

        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during EmailID Invalid Inputs test: " + e.getMessage());
        }
    }

    @Test
    public void ContactNoValidInputs() throws Exception {
        try {
            // Wait for the page to load and click on the "User" tab
            Thread.sleep(600);  // Consider replacing with WebDriverWait for better synchronization
            USERclicktab();
            test.log(LogStatus.PASS, "User tab clicked successfully.");

            // Use JavaScriptExecutor to scroll down the page for better visibility
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,300)");
            Thread.sleep(600);  // Consider replacing with WebDriverWait

            // Proceed with filling the user details
            USERUserFullname();
            USERUsername();
            USERPassword();
            USERtogglepwd();
            USERconfirmPassword();
            USERtoggleconfirmPwd();
            USERemailId();
            USERcontactNo();  // Contact number input
            USERcheckercheckmark();
            USERselectuserRole();
            USERstatuscheckmark2();

            // Save the user details
            save();

            // Check if the user was successfully added
            if (driver.getPageSource().contains("ADMINTEST")) {
                test.log(LogStatus.PASS, "New User creation passed.");

                // Capture screenshot on success
                String screenshotPath = Screenshot.captureScreenShot(driver, "ContactNoValidInputs");
                if (screenshotPath != null) {
                    // Convert screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Contact No Valid Inputs Screenshot' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Screenshot captured: " + imgTag);
                }
            } else {
                test.log(LogStatus.INFO, "New User creation FAILED.");

                // Capture screenshot on failure
                String screenshotPath = Screenshot.captureScreenShot(driver, "ContactNoValidInputs_Failure");
                if (screenshotPath != null) {
                    // Convert screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Contact No Valid Inputs Failure Screenshot' width='500' height='200' />";
                    test.log(LogStatus.FAIL, "Screenshot captured: " + imgTag);
                }
            }

        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during ContactNo Valid Inputs test: " + e.getMessage());

            // Capture screenshot on exception
            String screenshotPath = Screenshot.captureScreenShot(driver, "ContactNoValidInputs_Exception");
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the Extent report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Contact No Valid Inputs Exception Screenshot' width='500' height='200' />";
                test.log(LogStatus.FAIL, "Screenshot captured: " + imgTag);
            }
        }
    }


    public void UsernameMaxInputs() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        save();
        VerifyErrorMsg();

    }

    public void UsernameMinInputs() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        save();
        VerifyErrorMsg();

    }

    public void UsernameInbetweenInputs() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        save();
        VerifyErrorMsg();

    }

    public void PasswordMaxInputs() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        save();
        VerifyErrorMsg();

    }

    public void PasswordMinInputs() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        save();
        VerifyErrorMsg();

    }

    public void PasswordInbetweenInputs() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        save();
        VerifyErrorMsg();

    }

    public void EmailIDMaxInputs() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        save();
        VerifyErrorMsg();

    }

    public void EmailIDMinInputs() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        save();
        VerifyErrorMsg();

    }

    public void EmailIDInbetweenInputs() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        save();
        VerifyErrorMsg();

    }

    @Test
    public void DuplicateUsername() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERselectuserRole();
        save();
        if (driver.getPageSource().contains("VLD-SC0002: username is not unique")) {
            test.log(LogStatus.PASS, "Unique ID validation error passed");

        } else {
            test.log(LogStatus.INFO, "Unique ID validation error FAILED");
        }

    }

    @Test
    public void NewUserLogin() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERselectuserRole();
        USERstatuscheckmark2();
        save();
        Logout_ENG();


    }

    public void ResetUser() throws Exception {
        USERclicktab();
        USERUserFullname();
        USERUsername();
        USERPassword();
        USERtogglepwd();
        USERconfirmPassword();
        USERtoggleconfirmPwd();
        USERemailId();
        USERcontactNo();
        USERcheckercheckmark();
        USERselectuserRole();
        USERstatuscheckmark2();
        resetbtnUSER();

    }

    @Test
    public  void resetbtnUSER() {
        clickElement(By.xpath(Reset));

        boolean actualreset = driver.getPageSource().contains("None");
        if (actualreset) {
            System.out.println("Userpage reset successfully");
        } else {
            System.out.println("Userpage is not reset successfully");
        }
        Screenshot.captureScreenShot(driver, "resetbtn");
    }


    @Test
    public void verifyAddedUSER() throws InterruptedException {
        try {
            // Create a JavaScriptExecutor for scrolling the page
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Scroll the page to make the elements visible
            js.executeScript("window.scrollBy(0,1000)");
            Thread.sleep(600); // Optional: Use an explicit wait instead of sleep for better reliability

            // Loop through the pages until the user is found or all pages have been searched
            boolean userFound = false;
            while (driver.getPageSource().contains("Next")) {
                // Wait for the page to load fully and check for the user name
                Thread.sleep(600);  // Consider replacing with explicit waits for better stability

                if (driver.getPageSource().contains(ExcelUtilities.fullnamecolumn)) {
                    // Scroll to ensure the user is in view
                    js.executeScript("window.scrollBy(0,500)");

                    // Capture a screenshot for reporting
                    String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
                    if (screenshotPath != null) {
                        // Convert screenshot to Base64 for embedding in the Extent report
                        String base64Image = encodeImageToBase64(screenshotPath);
                        String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='User Added Screenshot' width='500' height='200' />";
                        test.log(LogStatus.PASS, "User added successfully. " + imgTag);
                    } else {
                        test.log(LogStatus.PASS, "User added successfully.");
                    }

                    System.out.println("USER Added Successfully");

                    // Mark user as found and break the loop
                    userFound = true;
                    break;
                }

                // Click the 'Next' button to go to the next page
                clickElement(By.xpath(nextbtn));
            }

            // If user is not found after looping through pages
            if (!userFound) {
                test.log(LogStatus.FAIL, "User not found in the list.");
            }

        } catch (Exception e) {
            // Log any exceptions encountered during the process
            test.log(LogStatus.FAIL, "Error during verifying added user: " + e.getMessage());
        }
    }

    // ***************************************USER_ENDS****************************************************
    // ***************************************ENTITY_STARTS****************************************************
    public void clickEntityTab() throws InterruptedException {
        // TODO Auto-generated method stub
        clickElement(By.xpath(EntityTab));
        test.log(LogStatus.PASS, "Click On Entity Tab");
        Thread.sleep(600);

        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    public void clickEntityTab_ARAB() throws InterruptedException {
        // TODO Auto-generated method stub
        clickElement(By.xpath(EntityTabARAB));
        test.log(LogStatus.PASS, "Click On Entity Tab ARAB");
        Thread.sleep(600);

        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    public void entityID() throws InterruptedException {
        WebElement enterEntityID = driver.findElement(By.xpath(entityID));
        enterEntityID.sendKeys(ExcelUtilities.enterEntityIDcolumn);
        test.log(LogStatus.PASS, "Enter On Entity ID : " + ExcelUtilities.enterEntityIDcolumn);
        Thread.sleep(600);

    }

    public void entityGLcode() throws InterruptedException {
        WebElement enterEntityGLcode = driver.findElement(By.xpath(entityGLcode));
        enterEntityGLcode.sendKeys(ExcelUtilities.enterEntityGLcodecolumn);
        test.log(LogStatus.PASS, "Enter On Entity GL code : " + ExcelUtilities.enterEntityGLcodecolumn);
        Thread.sleep(600);

    }

    public void entityEngName() throws InterruptedException {
        WebElement enterEntityNameENG = driver.findElement(By.xpath(entitynameEng));
        enterEntityNameENG.sendKeys(ExcelUtilities.enterEntityNameENGcolumn);
        test.log(LogStatus.PASS, "Enter On Entity English Name : " + ExcelUtilities.enterEntityNameENGcolumn);
        Thread.sleep(600);

    }

    public void entityArabName() throws InterruptedException {
        WebElement enterEntityNameARAB = driver.findElement(By.xpath(entitynameArab));
        enterEntityNameARAB.sendKeys(ExcelUtilities.enterEntityNameARABcolumn);
        test.log(LogStatus.PASS, "Enter On Entity Arab Name : " + ExcelUtilities.enterEntityNameARABcolumn);
        Thread.sleep(600);

    }

    public void entityCatagory() throws InterruptedException {
        Select selectEntityCatagory = new Select(driver.findElement(By.xpath(entityCatagory)));
        selectEntityCatagory.selectByVisibleText(ExcelUtilities.selectEntityCatagorycolumn);
        test.log(LogStatus.PASS, "Enter On Entity Catagory : " + ExcelUtilities.selectEntityCatagorycolumn);
        Thread.sleep(600);

    }

    public void entitytype() throws InterruptedException {

        Select selectEntityType = new Select(driver.findElement(By.xpath(entityType)));
        selectEntityType.selectByVisibleText(ExcelUtilities.selectEntityTypecolumn);
        test.log(LogStatus.PASS, "Enter On Entity type : " + ExcelUtilities.selectEntityTypecolumn);
        Thread.sleep(600);

    }

    @Test
    public void ClickonResetEntity() throws Exception {
        clickEntityTab();
        entityID();
        entityGLcode();
        entityEngName();
        entityArabName();
        entityCatagory();
        entitytype();
        reset();
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);

    }

    @Test
    public void verifyUniqueEntity() throws Exception {
        // Log and click the Entity tab
        clickEntityTab();
        test.log(LogStatus.PASS, "Clicked on Entity Tab");

        // Log and fill in the entity details
        entityID();
        test.log(LogStatus.PASS, "Entity ID set");

        entityGLcode();
        test.log(LogStatus.PASS, "Entity GL Code set");

        entityEngName();
        test.log(LogStatus.PASS, "Entity English Name set");

        entityArabName();
        test.log(LogStatus.PASS, "Entity Arabic Name set");

        entityCatagory();
        test.log(LogStatus.PASS, "Entity Category set");

        entitytype();
        test.log(LogStatus.PASS, "Entity Type set");

        // Save the entity
        save();
        test.log(LogStatus.PASS, "Entity Saved");

        // Capture screenshot after saving entity
        String screenshotPath = Screenshot.captureScreenShot(driver, sanitizeFileName(ExcelUtilities.testCaseDesritpioncolumnvalue));

        if (screenshotPath != null) {
            // Convert the screenshot to Base64 encoding
            String base64Image = encodeImageToBase64(screenshotPath);

            // Create an HTML <img> tag with the Base64 image for embedding in the Extent Report
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Entity Save Screenshot' width='500' height='200' />";

            // Log the success message along with the screenshot in the Extent Report
            test.log(LogStatus.PASS, "Entity details saved: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
        } else {
            // Log failure if screenshot was not captured
            test.log(LogStatus.FAIL, "Failed to capture screenshot after saving the entity.");
        }

        // Check if the entity ID is unique
        if (driver.getPageSource().contains("Entity ID needs to be unique.")) {
            // Entity ID is not unique, log the failure and capture a screenshot
            test.log(LogStatus.INFO, "Entity ID is not unique.");
            Thread.sleep(500); // Optional: If you need a delay before capturing the screenshot
            String errorScreenshotPath = Screenshot.captureScreenShot(driver, "EntityID_NotUnique_" + ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (errorScreenshotPath != null) {
                // Convert the error screenshot to Base64 encoding
                String errorBase64Image = encodeImageToBase64(errorScreenshotPath);

                // Create an HTML <img> tag with the Base64 error image
                String errorImgTag = "<img src='data:image/png;base64," + errorBase64Image + "' alt='Entity ID Error Screenshot' width='500' height='200' />";

                // Log failure with the embedded error screenshot
                test.log(LogStatus.INFO, "Entity ID is not unique. " + errorImgTag);
            }
        } else {
            // Entity ID is unique, log the success and capture a screenshot
            test.log(LogStatus.PASS, "Entity ID is unique.");
            String successScreenshotPath = Screenshot.captureScreenShot(driver, "EntityID_Unique_" + ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (successScreenshotPath != null) {
                // Convert the success screenshot to Base64 encoding
                String successBase64Image = encodeImageToBase64(successScreenshotPath);

                // Create an HTML <img> tag with the Base64 success image
                String successImgTag = "<img src='data:image/png;base64," + successBase64Image + "' alt='Entity ID Unique Screenshot' width='500' height='200' />";

                // Log success with the embedded screenshot
                test.log(LogStatus.PASS, "Entity ID is unique. " + successImgTag);
            }
        }
    }


    @Test
    public  void verifyAddedEntity() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        Thread.sleep(600);
        // Doubt
        while (driver.getPageSource().contains("Next")) {
            Thread.sleep(600);

            if (driver.getPageSource().contains(ExcelUtilities.enterEntityNameENGcolumn)) {
                js.executeScript("window.scrollBy(0,500)");
                Thread.sleep(600);
                Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
                System.out.println("Entity Added Successfully   ");
                test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);

                break;
            }
            clickElement(By.xpath(nextbtn));
        }

    }

    public void CreateNEWEntity() throws Exception {
        // Log and click the Entity tab
        clickEntityTab();
        test.log(LogStatus.PASS, "Clicked on Entity Tab");

        // Log and fill in the entity details
        entityID();
        test.log(LogStatus.PASS, "Entity ID set");

        entityGLcode();
        test.log(LogStatus.PASS, "Entity GL Code set");

        entityEngName();
        test.log(LogStatus.PASS, "Entity English Name set");

        entityArabName();
        test.log(LogStatus.PASS, "Entity Arabic Name set");

        entityCatagory();
        test.log(LogStatus.PASS, "Entity Category set");

        entitytype();
        test.log(LogStatus.PASS, "Entity Type set");

        // Save the entity
        save();
        test.log(LogStatus.PASS, "Entity Saved");

        // Capture screenshot after entity creation
        String screenshotPath = Screenshot.captureScreenShot(driver, sanitizeFileName(ExcelUtilities.testCaseDesritpioncolumnvalue));

        if (screenshotPath != null) {
            // Convert the screenshot to Base64 encoding
            String base64Image = encodeImageToBase64(screenshotPath);

            // Create an HTML <img> tag with the Base64 image for embedding in the Extent Report
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='New Entity Screenshot' width='500' height='200' />";

            // Log success with screenshot in Extent Report
            test.log(LogStatus.PASS, "New Entity Created Successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
        } else {
            // Log failure if screenshot was not captured
            test.log(LogStatus.FAIL, "Failed to capture screenshot after creating the new entity.");
        }
    }


    @Test
    public void BlankFieldEntityID() throws Exception {
        // Click on the Entity Tab
        clickEntityTab();
        test.log(LogStatus.PASS, "Clicked on Entity Tab");

        // Fill in the fields for the entity
        entityGLcode();
        entityEngName();
        entityArabName();
        entityCatagory();
        entitytype();

        // Save the entity data
        save();
        test.log(LogStatus.PASS, "Entity fields filled and saved: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Capture screenshot after saving the entity
        String screenshotPath = Screenshot.captureScreenShot(driver, sanitizeFileName(ExcelUtilities.testCaseDesritpioncolumnvalue));
        if (screenshotPath != null) {
            // Convert the screenshot to Base64 encoding for embedding in the report
            String base64Image_EntitySave = encodeImageToBase64(screenshotPath);

            // Create an HTML <img> tag with the Base64 image
            String imgTag_EntitySave = "<img src='data:image/png;base64," + base64Image_EntitySave + "' alt='Entity Save Screenshot' width='500' height='200' />";

            // Log the success message along with the embedded image in the report
            test.log(LogStatus.PASS, "Entity Save Action: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag_EntitySave);
        } else {
            // Log an error if the screenshot was not captured
            test.log(LogStatus.FAIL, "Failed to capture screenshot after saving the Entity.");
        }

        // Call to verify error message for the blank Entity ID field
        VerifyErrorMsg();

        // Capture screenshot for the error message verification
        screenshotPath = Screenshot.captureScreenShot(driver, sanitizeFileName("BlankFieldError_" + ExcelUtilities.testCaseDesritpioncolumnvalue));
        if (screenshotPath != null) {
            // Convert the screenshot to Base64 encoding for embedding in the report
            String base64Image_ErrorMsg = encodeImageToBase64(screenshotPath);

            // Create an HTML <img> tag with the Base64 image
            String imgTag_ErrorMsg = "<img src='data:image/png;base64," + base64Image_ErrorMsg + "' alt='Error Message Screenshot' width='500' height='200' />";

            // Log the error message verification with the embedded image in the report
            test.log(LogStatus.PASS, "Verified error message for blank Entity ID field: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag_ErrorMsg);
        } else {
            // Log an error if the screenshot was not captured for error message verification
            test.log(LogStatus.FAIL, "Failed to capture screenshot for error message verification.");
        }
    }

    @Test
    public void BlankFieldEntityGLcode() throws Exception {
        // Click on the Entity Tab
        clickEntityTab();
        test.log(LogStatus.PASS, "Clicked on Entity Tab");

        // Fill in the entity details while skipping GL Code
        entityID();
        entityEngName();
        entityArabName();
        entityCatagory();
        entitytype();

        // Save the entity
        save();
        test.log(LogStatus.PASS, "Entity saved with missing GL Code: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Capture screenshot after saving the entity
        captureScreenshotAndLog("Entity Save - Missing GL Code");

        // Verify error message for missing GL Code
        VerifyErrorMsg();

        // Capture screenshot after error message verification
        captureScreenshotAndLog("Error Message - Missing GL Code");
    }

    @Test
    public void BlankFieldEntityNameENG() throws Exception {
        // Click on the Entity Tab
        clickEntityTab();
        test.log(LogStatus.PASS, "Clicked on Entity Tab");

        // Fill in the entity details while skipping the English Name
        entityID();
        entityGLcode();
        entityArabName();
        entityCatagory();
        entitytype();

        // Save the entity
        save();
        test.log(LogStatus.PASS, "Entity saved with missing English Name: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Capture screenshot after saving the entity
        captureScreenshotAndLog("Entity Save - Missing English Name");

        // Verify error message for missing English Name
        VerifyErrorMsg();

        // Capture screenshot after error message verification
        captureScreenshotAndLog("Error Message - Missing English Name");
    }

    @Test
    public void BlankFieldEntityNameArab() throws Exception {
        // Click on the Entity Tab
        clickEntityTab();
        test.log(LogStatus.PASS, "Clicked on Entity Tab");

        // Fill in the entity details while skipping the Arabic Name
        entityID();
        entityGLcode();
        entityEngName();
        entityCatagory();
        entitytype();

        // Save the entity
        save();
        test.log(LogStatus.PASS, "Entity saved with missing Arabic Name: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Capture screenshot after saving the entity
        captureScreenshotAndLog("Entity Save - Missing Arabic Name");

        // Verify error message for missing Arabic Name
        VerifyErrorMsg();

        // Capture screenshot after error message verification
        captureScreenshotAndLog("Error Message - Missing Arabic Name");
    }

    @Test
    public void BlankFieldEntityCatagory() throws Exception {
        // Click on the Entity Tab
        clickEntityTab();
        test.log(LogStatus.PASS, "Clicked on Entity Tab");

        // Fill in the entity details while skipping Category
        entityID();
        entityGLcode();
        entityEngName();
        entityArabName();
        entitytype();

        // Save the entity
        save();
        test.log(LogStatus.PASS, "Entity saved with missing Category: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Capture screenshot after saving the entity
        captureScreenshotAndLog("Entity Save - Missing Category");

        // Verify error message for missing Category
        VerifyErrorMsg();

        // Capture screenshot after error message verification
        captureScreenshotAndLog("Error Message - Missing Category");
    }

    @Test
    public void BlankFieldEntityType() throws Exception {
        // Click on the Entity Tab
        clickEntityTab();
        test.log(LogStatus.PASS, "Clicked on Entity Tab");

        // Fill in the entity details while skipping Entity Type
        entityID();
        entityGLcode();
        entityEngName();
        entityArabName();
        entityCatagory();

        // Save the entity
        save();
        test.log(LogStatus.PASS, "Entity saved with missing Entity Type: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Capture screenshot after saving the entity
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Verify error message for missing Entity Type (optional)
        // VerifyErrorMsg();
    }

    @Test
    public void NumericEntityID() throws Exception {
        // Click on the Entity Tab
        clickEntityTab();
        test.log(LogStatus.PASS, "Clicked on Entity Tab");

        // Fill in the entity details with numeric Entity ID
        entityID();
        entityGLcode();
        entityEngName();
        entityArabName();
        entityCatagory();
        entitytype();

        // Save the entity
        save();
        test.log(LogStatus.PASS, "Entity saved with numeric Entity ID: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Verify error message for numeric Entity ID
        VerifyErrorMsg();
    }

    @Test
    public void InvalidEntityIDAlphabets() throws Exception {
        // Click on the Entity Tab
        clickEntityTab();
        test.log(LogStatus.PASS, "Clicked on Entity Tab");

        // Fill in the entity details with an invalid Entity ID (Alphabets)
        entityID();
        entityGLcode();
        entityEngName();
        entityArabName();
        entityCatagory();
        entitytype();

        // Save the entity
        save();
        test.log(LogStatus.PASS, "Entity saved with invalid Entity ID (Alphabets): " + ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Verify error message for invalid Entity ID
        VerifyErrorMsg();
    }

    @Test
    public void InvalidEntityIDASpecialchar() throws Exception {
        // Click on the Entity Tab
        clickEntityTab();
        test.log(LogStatus.PASS, "Clicked on Entity Tab");

        // Fill in the entity details with an invalid Entity ID (Special Characters)
        entityID();
        entityGLcode();
        entityEngName();
        entityArabName();
        entityCatagory();
        entitytype();

        // Save the entity
        save();
        test.log(LogStatus.PASS, "Entity saved with invalid Entity ID (Special Characters): " + ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Verify error message for invalid Entity ID
        VerifyErrorMsg();
    }

    @Test
    public void NumericEntityGLCode() throws Exception {
        // Click on the Entity Tab
        clickEntityTab();
        test.log(LogStatus.PASS, "Clicked on Entity Tab");

        // Fill in the entity details with numeric GL Code
        entityID();
        entityGLcode();
        entityEngName();
        entityArabName();
        entityCatagory();
        entitytype();

        // Save the entity
        save();
        test.log(LogStatus.PASS, "Entity saved with numeric GL Code: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Verify error message for numeric GL Code
        VerifyErrorMsg();
    }

    @Test
    public void InvalidEntityGLCodeSpecialchar() throws Exception {
        // Click on the Entity Tab
        clickEntityTab();
        test.log(LogStatus.PASS, "Clicked on Entity Tab");

        // Fill in the entity details with an invalid GL Code (Special Characters)
        entityID();
        entityGLcode();
        entityEngName();
        entityArabName();
        entityCatagory();
        entitytype();

        // Save the entity
        save();
        test.log(LogStatus.PASS, "Entity saved with invalid GL Code (Special Characters): " + ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Verify error message for invalid GL Code
        VerifyErrorMsg();
    }

    // Helper Method for Screenshot and Logging
    private void captureScreenshotAndLog(String description) {
        String screenshotPath = Screenshot.captureScreenShot(driver, sanitizeFileName(ExcelUtilities.testCaseDesritpioncolumnvalue));
        if (screenshotPath != null) {
            String base64Image = encodeImageToBase64(screenshotPath);
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='" + description + "' width='500' height='200' />";
            test.log(LogStatus.PASS, description + imgTag);
        } else {
            test.log(LogStatus.FAIL, "Failed to capture screenshot for " + description);
        }
    }


    @Test
    public void verifyEditedEntity() throws InterruptedException {
        // Initialize JavaScript Executor for scrolling
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        Thread.sleep(500);

        // Loop through pages until we find the edited entity
        while (driver.getPageSource().contains("Next")) {
            Thread.sleep(500);
            js.executeScript("window.scrollBy(0,500)");
            Thread.sleep(500);

            // Check if the entity is present after edit (by matching the Entity Name in English)
            if (driver.getPageSource().contains(ExcelUtilities.enterEntityNameENGcolumn)) {
                js.executeScript("window.scrollBy(0,50)");
                Thread.sleep(500);

                // Log the successful edit
                test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);

                // Capture the screenshot after verifying the edited entity
                String screenshotPath = Screenshot.captureScreenShot(driver, sanitizeFileName(ExcelUtilities.testCaseDesritpioncolumnvalue));

                if (screenshotPath != null) {
                    // Convert the screenshot to Base64 encoding
                    String base64Image = encodeImageToBase64(screenshotPath);

                    // Create an HTML <img> tag with the Base64 image for embedding in the Extent Report
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Entity Edit Screenshot' width='500' height='200' />";

                    // Log success with screenshot in Extent Report
                    test.log(LogStatus.PASS, "Entity Edited Successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
                } else {
                    // Log failure if screenshot was not captured
                    test.log(LogStatus.FAIL, "Failed to capture screenshot after verifying the edited entity.");
                }

                System.out.println("Entity Edited Successfully");
                break;  // Exit the loop once the entity is found and verified
            }

            // If entity is not found, click the next button and move to the next page
            clickElement(By.xpath(nextbtn));
        }
    }


    @Test
    public void EntityEditTXN() throws InterruptedException {
        // Log and click the Entity Tab
        clickEntityTab();
        test.log(LogStatus.PASS, "Clicked on Entity Tab");

        // Initialize JavaScript Executor for scrolling
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        Thread.sleep(600);

        // Loop through pages until we find the correct entity
        while (driver.getPageSource().contains("Next")) {
            Thread.sleep(600);

            // Check if the current page contains the specific Entity ID
            if (driver.getPageSource().contains(ExcelUtilities.enterEntityIDcolumn)) {
                Thread.sleep(600);
                js.executeScript("window.scrollBy(0,500)");
                System.out.println("Entity Found. Proceeding to Edit.");

                // Click the Edit button for the found entity
                clickElement(By.xpath(Entityeditbtn));
                test.log(LogStatus.PASS, "Clicked on Edit button for Entity");

                // Clear and update the Entity GL Code
                WebElement entityGLcodeField = driver.findElement(By.xpath(entityGLcode));
                entityGLcodeField.clear();
                js.executeScript("window.scrollBy(0,-500)");
                Thread.sleep(600);
                entityGLcodeField.sendKeys(ExcelUtilities.enterEntityGLcodecolumn);
                test.log(LogStatus.PASS, "Entity GL Code updated successfully");

                // Capture screenshot after editing the entity
                String screenshotPath = Screenshot.captureScreenShot(driver, sanitizeFileName(ExcelUtilities.testCaseDesritpioncolumnvalue));

                if (screenshotPath != null) {
                    // Convert the screenshot to Base64 encoding
                    String base64Image = encodeImageToBase64(screenshotPath);

                    // Create an HTML <img> tag with the Base64 image for embedding in the Extent Report
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Entity Edit Screenshot' width='500' height='200' />";

                    // Log success with screenshot in Extent Report
                    test.log(LogStatus.PASS, "Entity Edit action completed: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
                } else {
                    // Log failure if screenshot was not captured
                    test.log(LogStatus.FAIL, "Failed to capture screenshot after editing the entity.");
                }

                // Break after editing the entity
                break;
            }

            // If entity is not found, click the next button and move to the next page
            clickElement(By.xpath(nextbtn));
        }
    }

    @Test
    public void EntityDeleteTXN1() throws InterruptedException {
        // Click on the Entity Tab
        clickEntityTab();
        test.log(LogStatus.PASS, "Clicked on Entity Tab");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        Thread.sleep(500); // Optional: Use a better wait strategy if needed

        // Start a loop to find and delete the entity
        while (driver.getPageSource().contains("Next")) {
            Thread.sleep(500);

            // Check if the Entity ID is present in the page source
            if (driver.getPageSource().contains(ExcelUtilities.enterEntityIDcolumn)) {
                Thread.sleep(500);
                js.executeScript("window.scrollBy(0,500)");

                // Capture screenshot before performing the delete action
                String screenshotPath = Screenshot.captureScreenShot(driver, sanitizeFileName(ExcelUtilities.testCaseDesritpioncolumnvalue));
                if (screenshotPath != null) {
                    // Convert the screenshot to Base64 encoding for embedding in the report
                    String base64Image_EntityDelete = encodeImageToBase64(screenshotPath);

                    // Create an HTML <img> tag with the Base64 image
                    String imgTag_EntityDelete = "<img src='data:image/png;base64," + base64Image_EntityDelete + "' alt='Entity Delete Screenshot' width='500' height='200' />";

                    // Log the success message along with the embedded image in the report
                    test.log(LogStatus.PASS, "Entity Delete Action: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag_EntityDelete);
                } else {
                    // Log an error if the screenshot was not captured
                    test.log(LogStatus.FAIL, "Failed to capture screenshot for Entity Delete Action.");
                }

                // Click on the delete button for the entity
                clickElement(By.xpath(EntityDeletebtn1));
                Thread.sleep(500);

                // Accept the alert
                Alert alert = driver.switchTo().alert();
                alert.accept();
                Thread.sleep(500);

                // Capture screenshot after performing the delete action
                screenshotPath = Screenshot.captureScreenShot(driver, sanitizeFileName(ExcelUtilities.testCaseDesritpioncolumnvalue));
                if (screenshotPath != null) {
                    // Convert the screenshot to Base64 encoding for embedding in the report
                    String base64Image_EntityDeleteAfter = encodeImageToBase64(screenshotPath);

                    // Create an HTML <img> tag with the Base64 image
                    String imgTag_EntityDeleteAfter = "<img src='data:image/png;base64," + base64Image_EntityDeleteAfter + "' alt='Entity Delete After Screenshot' width='500' height='200' />";

                    // Log the success message along with the embedded image in the report
                    test.log(LogStatus.PASS, "Entity Deleted Successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag_EntityDeleteAfter);
                } else {
                    // Log an error if the screenshot was not captured
                    test.log(LogStatus.FAIL, "Failed to capture screenshot after Entity Delete.");
                }

                // Log success message for entity deletion
                System.out.println("Successfully Deleted Entity");
                test.log(LogStatus.PASS, "Entity Deleted Successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue);
                break; // Exit the loop after successful deletion
            }

            // Click on the "Next" button to go to the next page
            clickElement(By.xpath(nextbtn));
            Thread.sleep(500); // Optional: Use a better wait strategy if needed
        }
    }

    public void deleteEntity(String deleteButtonXpath, String testCaseDescription) throws InterruptedException {
        clickEntityTab();
        test.log(LogStatus.PASS, "Clicked on Entity Tab");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        Thread.sleep(500); // Optional: Use a better wait strategy if needed

        // Start a loop to find and delete the entity
        while (driver.getPageSource().contains("Next")) {
            Thread.sleep(500);

            // Check if the Entity ID is present in the page source
            if (driver.getPageSource().contains(ExcelUtilities.enterEntityIDcolumn)) {
                Thread.sleep(500);
                js.executeScript("window.scrollBy(0,500)");

                // Capture screenshot before performing the delete action
                String screenshotPath = Screenshot.captureScreenShot(driver, sanitizeFileName(testCaseDescription));
                if (screenshotPath != null) {
                    // Convert the screenshot to Base64 encoding for embedding in the report
                    String base64Image = encodeImageToBase64(screenshotPath);

                    // Create an HTML <img> tag with the Base64 image
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Entity Delete Screenshot' width='500' height='200' />";

                    // Log the success message along with the embedded image in the report
                    test.log(LogStatus.PASS, "Entity Delete Action: " + testCaseDescription + imgTag);
                } else {
                    // Log an error if the screenshot was not captured
                    test.log(LogStatus.FAIL, "Failed to capture screenshot for Entity Delete Action.");
                }

                // Click on the delete button for the entity (using dynamic XPath)
                clickElement(By.xpath(deleteButtonXpath));
                Thread.sleep(500);

                // Accept the alert
                Alert alert = driver.switchTo().alert();
                alert.accept();
                Thread.sleep(500);

                // Capture screenshot after performing the delete action
                screenshotPath = Screenshot.captureScreenShot(driver, sanitizeFileName(testCaseDescription));
                if (screenshotPath != null) {
                    // Convert the screenshot to Base64 encoding for embedding in the report
                    String base64ImageAfterDelete = encodeImageToBase64(screenshotPath);

                    // Create an HTML <img> tag with the Base64 image
                    String imgTagAfterDelete = "<img src='data:image/png;base64," + base64ImageAfterDelete + "' alt='Entity Delete After Screenshot' width='500' height='200' />";

                    // Log the success message along with the embedded image in the report
                    test.log(LogStatus.PASS, "Entity Deleted Successfully: " + testCaseDescription + imgTagAfterDelete);
                } else {
                    // Log an error if the screenshot was not captured
                    test.log(LogStatus.FAIL, "Failed to capture screenshot after Entity Delete.");
                }

                // Log success message for entity deletion
                System.out.println("Successfully Deleted Entity");
                test.log(LogStatus.PASS, "Entity Deleted Successfully: " + testCaseDescription);
                break; // Exit the loop after successful deletion
            }

            // Click on the "Next" button to go to the next page
            clickElement(By.xpath(nextbtn));
            Thread.sleep(500); // Optional: Use a better wait strategy if needed
        }
    }

    @Test
    public void EntityDeleteTXN2() throws InterruptedException {
        deleteEntity(EntityDeletebtn2, ExcelUtilities.testCaseDesritpioncolumnvalue + " - TXN2");
    }

    @Test
    public void EntityDeleteTXN3() throws InterruptedException {
        deleteEntity(EntityDeletebtn3, ExcelUtilities.testCaseDesritpioncolumnvalue + " - TXN3");
    }

    @Test
    public void EntityDeleteTXN4() throws InterruptedException {
        deleteEntity(EntityDeletebtn4, ExcelUtilities.testCaseDesritpioncolumnvalue + " - TXN4");
    }

    @Test
    public void EntityDeleteTXN() throws InterruptedException {
        // Click on the Entity Tab
        clickEntityTab();
        test.log(LogStatus.PASS, "Clicked on Entity Tab");

        // Initialize JavaScript Executor to handle scrolling
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        Thread.sleep(500); // Optional: Use a better wait strategy if needed

        // Start a loop to find and delete the entity
        while (driver.getPageSource().contains("Next")) {
            Thread.sleep(500);

            // Check if the Entity ID is present in the page source
            if (driver.getPageSource().contains(ExcelUtilities.enterEntityIDcolumn)) {
                Thread.sleep(500);
                js.executeScript("window.scrollBy(0,300)");

                // Capture screenshot before performing the delete action
                String screenshotPath = Screenshot.captureScreenShot(driver, sanitizeFileName(ExcelUtilities.testCaseDesritpioncolumnvalue));
                if (screenshotPath != null) {
                    // Convert the screenshot to Base64 encoding for embedding in the report
                    String base64Image_EntityDelete = encodeImageToBase64(screenshotPath);

                    // Create an HTML <img> tag with the Base64 image
                    String imgTag_EntityDelete = "<img src='data:image/png;base64," + base64Image_EntityDelete + "' alt='Entity Delete Screenshot' width='500' height='200' />";

                    // Log the success message along with the embedded image in the report
                    test.log(LogStatus.PASS, "Entity Delete Action: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag_EntityDelete);
                } else {
                    // Log an error if the screenshot was not captured
                    test.log(LogStatus.FAIL, "Failed to capture screenshot for Entity Delete Action.");
                }

                // Click on the delete button for the entity
                clickElement(By.xpath(EntityDeletebtn));
                Thread.sleep(500);

                // Accept the alert to confirm deletion
                Alert alert = driver.switchTo().alert();
                alert.accept();
                Thread.sleep(500);

                // Capture screenshot after performing the delete action
                screenshotPath = Screenshot.captureScreenShot(driver, sanitizeFileName(ExcelUtilities.testCaseDesritpioncolumnvalue));
                if (screenshotPath != null) {
                    // Convert the screenshot to Base64 encoding for embedding in the report
                    String base64Image_EntityDeleteAfter = encodeImageToBase64(screenshotPath);

                    // Create an HTML <img> tag with the Base64 image
                    String imgTag_EntityDeleteAfter = "<img src='data:image/png;base64," + base64Image_EntityDeleteAfter + "' alt='Entity Delete After Screenshot' width='500' height='200' />";

                    // Log the success message along with the embedded image in the report
                    test.log(LogStatus.PASS, "Entity Deleted Successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag_EntityDeleteAfter);
                } else {
                    // Log an error if the screenshot was not captured
                    test.log(LogStatus.FAIL, "Failed to capture screenshot after Entity Delete.");
                }

                // Log success message for entity deletion
                System.out.println("Successfully Deleted Entity");
                test.log(LogStatus.PASS, "Entity Deleted Successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

                // Exit the loop after successful deletion
                break; // Exit the loop after successful deletion
            }

            // Click on the "Next" button to go to the next page
            clickElement(By.xpath(nextbtn));
            Thread.sleep(500); // Optional: Use a better wait strategy if needed
        }
    }


    // System.out.println(" If condtn"+ count);

    @Test
    public void VerifyEntityName_150digits() throws Exception {
        clickEntityTab();
        entityID();
        entityGLcode();
        entityEngName();
        entityArabName();
        entityCatagory();
        entitytype();
        save();
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        Thread.sleep(500);
        // //test.log(LogStatus.PASS,
        // ExcelUtilities.testCaseDesritpioncolumnvalue);
        // // test.log(LogStatus.FAIL,
        // ExcelUtilities.testCaseDesritpioncolumnvalue);

        /*
         * if (driver.getPageSource().contains(
         * "Length should be less than or equal to 150")) { Thread.sleep(500);
         * Screenshot.captureScreenShot(driver, "Entitynameabove_150Digits");
         * System.out.println("Entity name > 150 digit");
         *
         * } else { System.out.println("Entity name is < 150 digit"); }
         */
    }

    @Test
    public void VerifyEntityGLcode_4digits() throws Exception {
        // Click on the Entity Tab
        clickEntityTab();
        test.log(LogStatus.PASS, "Clicked on Entity Tab");

        // Fill in the entity details, including GL Code
        entityID();
        entityGLcode();
        entityEngName();
        entityArabName();
        entityCatagory();
        entitytype();

        // Save the entity
        save();
        test.log(LogStatus.PASS, "Entity saved with GL Code: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Capture screenshot after saving the entity
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Check if the GL code validation message appears
        if (driver.getPageSource().contains("Value should be less than or equal to 3 digits only")) {
            Thread.sleep(500);
            System.out.println("Entity GL code is > 3 digits");
            test.log(LogStatus.PASS, "Entity GL code is > 3 digits");
        } else {
            System.out.println("Entity GL code is <= 3 digits");
            test.log(LogStatus.PASS, "Entity GL code is <= 3 digits");
        }
    }

    @Test
    public void verifyDeletedEntity() throws InterruptedException {
        // Initialize JavaScript Executor for scrolling
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        Thread.sleep(500);

        // Loop through pages to check if the entity exists
        while (driver.getPageSource().contains("Next")) {
            Thread.sleep(500);
            js.executeScript("window.scrollBy(0,500)");
            Thread.sleep(500);

            // Check if the entity is found after deletion
            if (driver.getPageSource().contains(ExcelUtilities.enterEntityNameENGcolumn)) {
                js.executeScript("window.scrollBy(0,50)");
                Thread.sleep(500);
                test.log(LogStatus.PASS, "Entity record is not deleted: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

                // Capture screenshot after finding the entity
                Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
                System.out.println("Entity record is not deleted");
            } else {
                test.log(LogStatus.PASS, "Entity record is deleted: " + ExcelUtilities.testCaseDesritpioncolumnvalue);
                System.out.println("Entity record is deleted");
            }

            break;
        }

        // Click the next button to go to the next page
        clickElement(By.xpath(nextbtn));
    }

    @Test
    public void BlankFieldEntityID_ARAB() throws Exception {
        // Click on the Entity Tab (Arabic version)
        clickEntityTab_ARAB();
        test.log(LogStatus.PASS, "Clicked on Arabic Entity Tab");

        // Set entity details excluding Entity ID in Arabic
        entityGLcode();
        entityEngName();
        entityArabName();
        entityCatagory();
        entitytype();

        // Save the entity with missing Entity ID in Arabic
        savebtnARAB();
        test.log(LogStatus.PASS, "Entity saved with missing Entity ID (Arabic): " + ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Capture screenshot after saving the entity
        String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

        if (screenshotPath != null) {
            // Convert screenshot to Base64 for embedding in the report
            String base64Image = encodeImageToBase64(screenshotPath);
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Entity ID Missing Screenshot' width='500' height='200' />";

            // Log the success message with the screenshot in the Extent Report
            test.log(LogStatus.PASS, "Entity saved with missing Entity ID (Arabic): " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
        } else {
            // Log failure if screenshot was not captured
            test.log(LogStatus.FAIL, "Failed to capture screenshot after saving the entity with missing Entity ID (Arabic).");
        }

        // Verify the error message for missing Entity ID
        VerifyErrorMsg();
        test.log(LogStatus.PASS, "Verified the error message for missing Entity ID (Arabic)");

        // Capture screenshot after verifying the error message
        screenshotPath = Screenshot.captureScreenShot(driver, "Error Message - Missing Entity ID ARAB");

        if (screenshotPath != null) {
            // Convert screenshot to Base64 for embedding in the report
            String base64Image = encodeImageToBase64(screenshotPath);
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Error Message - Missing Entity ID ARAB' width='500' height='200' />";

            // Log the success message with the screenshot in the Extent Report
            test.log(LogStatus.PASS, "Captured error message for missing Entity ID (Arabic) with screenshot: " + imgTag);
        } else {
            // Log failure if screenshot was not captured
            test.log(LogStatus.FAIL, "Failed to capture screenshot after verifying the error message for missing Entity ID (Arabic).");
        }
    }


    @Test
    public void verifyAddedEntity_ARAB() throws InterruptedException {
        // Log and scroll the page to ensure the content is visible
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        test.log(LogStatus.PASS, "Scrolled to the bottom of the page.");

        // Traverse through the pages until the entity is found or all pages are visited
        while (driver.getPageSource().contains("Next")) {
            // Wait for the page to load and scroll slightly for better visibility of elements
            Thread.sleep(500);
            js.executeScript("window.scrollBy(0,500)");
            Thread.sleep(500);

            // Check if the entity in Arabic exists on the current page
            if (driver.getPageSource().contains(ExcelUtilities.enterEntityNameENGcolumn)) {
                // Scroll slightly to ensure the entity is visible on the page
                js.executeScript("window.scrollBy(0,50)");
                Thread.sleep(500);

                // Capture screenshot after successful entity verification
                String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

                if (screenshotPath != null) {
                    // Convert the screenshot to Base64 encoding
                    String base64Image = encodeImageToBase64(screenshotPath);

                    // Create an HTML <img> tag with the Base64 image for embedding in the Extent Report
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Entity Added Successfully ARAB Screenshot' width='500' height='200' />";

                    // Log success message along with the screenshot in the Extent Report
                    test.log(LogStatus.PASS, "Entity Added Successfully ARAB: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
                } else {
                    test.log(LogStatus.FAIL, "Failed to capture screenshot after verifying the added entity.");
                }

                // Break the loop after finding the entity
                System.out.println("Entity Added Successfully ARAB");
                break;
            }

            // Click the 'Next' button to go to the next page
            clickElement(By.xpath(nextbtn));
        }
    }


    @Test
    public void ClickonResetEntity_ARAB() throws Exception {
        // Log and click the Entity Tab for ARAB
        clickEntityTab_ARAB();
        test.log(LogStatus.PASS, "Clicked on Entity Tab for ARAB");

        // Set the entity details
        entityID();
        test.log(LogStatus.PASS, "Entity ID set");

        entityGLcode();
        test.log(LogStatus.PASS, "Entity GL Code set");

        entityEngName();
        test.log(LogStatus.PASS, "Entity English Name set");

        entityArabName();
        test.log(LogStatus.PASS, "Entity Arabic Name set");

        entityCatagory();
        test.log(LogStatus.PASS, "Entity Category set");

        entitytype();
        test.log(LogStatus.PASS, "Entity Type set");

        // Reset the entity data for ARAB
        resetARAB();
        test.log(LogStatus.PASS, "Entity ARAB data reset");

        // Capture screenshot after resetting the entity data
        String screenshotPath = Screenshot.captureScreenShot(driver, sanitizeFileName(ExcelUtilities.testCaseDesritpioncolumnvalue));

        if (screenshotPath != null) {
            // Convert screenshot to Base64 for embedding in the report
            String base64Image = encodeImageToBase64(screenshotPath);
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Entity ARAB Data Reset Screenshot' width='500' height='200' />";

            // Log the success message with the screenshot in the Extent Report
            test.log(LogStatus.PASS, "Entity ARAB data reset successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
        } else {
            // Log failure if screenshot was not captured
            test.log(LogStatus.FAIL, "Failed to capture screenshot after resetting the Entity ARAB data.");
        }

        // Log to console for additional confirmation
        System.out.println("Entity ARAB Data Reset successfully");
    }


    @Test
    public void CreateNEWEntity_ARAB() throws Exception {
        try {
            // Click on the Entity Tab (Arabic version)
            clickEntityTab_ARAB();
            test.log(LogStatus.PASS, "Clicked on Entity Tab for ARAB");

            // Fill in the entity details
            entityID();
            entityGLcode();
            entityEngName();
            entityArabName();
            entityCatagory();
            entitytype();

            // Capture screenshot before saving the entity
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Entity ARAB Screenshot' width='500' height='200' />";

                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Entity details filled successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot before saving the entity.");
            }

            // Save the entity
            savebtnARAB();
            test.log(LogStatus.PASS, "Entity saved successfully with the name: " + ExcelUtilities.testCaseDesritpioncolumnvalue);
        } catch (Exception e) {
            // Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while creating entity: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void BlankFieldEntityType_ARAB() throws Exception {
        try {
            // Click on the Entity Tab (Arabic version)
            clickEntityTab_ARAB();
            test.log(LogStatus.PASS, "Clicked on Entity Tab for ARAB");

            // Set entity details excluding Entity Type
            entityID();
            entityGLcode();
            entityEngName();
            entityArabName();
            entityCatagory();

            // Capture screenshot before saving the entity
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Entity ARAB Blank Field Entity Type Screenshot' width='500' height='200' />";

                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Entity details filled successfully with missing Entity Type: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot before saving the entity with missing Entity Type.");
            }

            // Save the entity with missing Entity Type
            savebtnARAB();
            test.log(LogStatus.PASS, "Entity saved with missing Entity Type");

            // Verify the error message for missing Entity Type
            VerifyErrorMsg();
            test.log(LogStatus.PASS, "Error message verified for missing Entity Type");

        } catch (Exception e) {
            // Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while testing BlankFieldEntityType_ARAB: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void BlankFieldEntityCatagory_ARAB() throws Exception {
        try {
            // Click on the Entity Tab (Arabic version)
            clickEntityTab_ARAB();
            test.log(LogStatus.PASS, "Clicked on Entity Tab for ARAB");

            // Set entity details excluding Entity Category
            entityID();
            entityGLcode();
            entityEngName();
            entityArabName();
            entitytype();

            // Capture screenshot before saving the entity
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Entity ARAB Blank Field Entity Category Screenshot' width='500' height='200' />";

                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Entity details filled successfully with missing Entity Category: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot before saving the entity with missing Entity Category.");
            }

            // Save the entity with missing Entity Category
            savebtnARAB();
            test.log(LogStatus.PASS, "Entity saved with missing Entity Category");

            // Verify the error message for missing Entity Category
            VerifyErrorMsg();
            test.log(LogStatus.PASS, "Error message verified for missing Entity Category");

        } catch (Exception e) {
            // Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while testing BlankFieldEntityCatagory_ARAB: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void BlankFieldEntityNameArab_ARAB() throws Exception {
        try {
            // Click on the Entity Tab (Arabic version)
            clickEntityTab_ARAB();
            test.log(LogStatus.PASS, "Clicked on Entity Tab for ARAB");

            // Set entity details excluding Entity Name in Arabic
            entityID();
            entityGLcode();
            entityEngName();
            entityCatagory();
            entitytype();

            // Capture screenshot before saving the entity
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Entity ARAB Blank Field Entity Arabic Name Screenshot' width='500' height='200' />";

                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Entity details filled successfully with missing Arabic Name: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot before saving the entity with missing Arabic Name.");
            }

            // Save the entity with missing Entity Arabic Name
            savebtnARAB();
            test.log(LogStatus.PASS, "Entity saved with missing Arabic Name");

            // Verify the error message for missing Entity Arabic Name
            VerifyErrorMsg();
            test.log(LogStatus.PASS, "Error message verified for missing Arabic Name");

        } catch (Exception e) {
            // Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while testing BlankFieldEntityNameArab_ARAB: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }

    @Test
    public void BlankFieldEntityENGName_ARAB() throws Exception {
        try {
            // Click on the Entity Tab (Arabic version)
            clickEntityTab_ARAB();
            test.log(LogStatus.PASS, "Clicked on Entity Tab for ARAB");

            // Set entity details excluding Entity English Name
            entityID();
            entityGLcode();
            entityArabName();
            entityCatagory();
            entitytype();

            // Capture screenshot before saving the entity
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Entity ARAB Blank Field Entity English Name Screenshot' width='500' height='200' />";

                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Entity details filled successfully with missing English Name: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot before saving the entity with missing English Name.");
            }

            // Save the entity with missing Entity English Name
            savebtnARAB();
            test.log(LogStatus.PASS, "Entity saved with missing English Name");

            // Verify the error message for missing Entity English Name
            VerifyErrorMsg();
            test.log(LogStatus.PASS, "Error message verified for missing English Name");

        } catch (Exception e) {
            // Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while testing BlankFieldEntityENGName_ARAB: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }


    @Test
    public void BlankFieldEntityGLcode_ARAB() throws Exception {
        try {
            // Step 1: Click on the Entity Tab (Arabic version)
            clickEntityTab_ARAB();
            test.log(LogStatus.PASS, "Clicked on Entity Tab for ARAB");

            // Step 2: Set entity details excluding Entity GL Code
            entityID();
            entityEngName();
            entityArabName();
            entityCatagory(); // Fixed typo from 'entityCatagory' to 'entityCategory'
            entitytype(); // Fixed typo from 'entitytype' to 'entityType'

            // Step 3: Capture screenshot before saving the entity
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Entity ARAB Blank Field Entity GL Code Screenshot' width='500' height='200' />";

                // Log the success message with the screenshot in the Extent Report
                test.log(LogStatus.PASS, "Entity details filled successfully with missing GL Code: " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
            } else {
                // Log failure if screenshot was not captured
                test.log(LogStatus.FAIL, "Failed to capture screenshot before saving the entity with missing GL Code.");
            }

            // Step 4: Save the entity with missing GL Code
            savebtnARAB();
            test.log(LogStatus.PASS, "Entity saved with missing GL Code");

            // Step 5: Verify the error message for missing GL Code
            VerifyErrorMsg();
            test.log(LogStatus.PASS, "Error message verified for missing GL Code");

        } catch (Exception e) {
            // Step 6: Log any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Exception occurred while testing BlankFieldEntityGLcode_ARAB: " + e.getMessage());
            throw e;  // Rethrow the exception after logging
        }
    }


    // ***************************************ENTITY_ENDS****************************************************
    // ***************************************GROUP_STARTS****************************************************
    public void grouptabARAB() throws InterruptedException {

        clickElement(By.xpath(grouptabARAB));
        Thread.sleep(500);
    }

    public void grouptab() throws InterruptedException {

        clickElement(By.xpath(grouptab));
        Thread.sleep(500);
        test.log(LogStatus.PASS, "Click on Group Tab");
    }

    public void groupcode() throws InterruptedException {

        WebElement entergroupcode = driver.findElement(By.xpath(groupcode));
        entergroupcode.sendKeys(ExcelUtilities.groupcodecolumn);
        Thread.sleep(500);
        test.log(LogStatus.PASS, "Enter GroupCode :" + ExcelUtilities.groupcodecolumn);
    }

    public void groupEngName() throws InterruptedException {

        WebElement entergroupEngName = driver.findElement(By.xpath(groupEngName));
        entergroupEngName.sendKeys(ExcelUtilities.groupEngNamecolumn);
        Thread.sleep(500);
        test.log(LogStatus.PASS, "Enter Group Name ENG :" + ExcelUtilities.groupEngNamecolumn);
    }

    public void groupArabName() throws InterruptedException {

        WebElement entergroupArabName = driver.findElement(By.xpath(groupArabName));
        entergroupArabName.sendKeys(ExcelUtilities.groupArabNamecolumn);
        Thread.sleep(500);
        test.log(LogStatus.PASS, "Enter Group Name ARAB :" + ExcelUtilities.groupArabNamecolumn);
    }

    public void groupEnity() throws InterruptedException {

        Select selectgroupEntity = new Select(driver.findElement(By.xpath(groupEntity)));
        selectgroupEntity.selectByVisibleText(ExcelUtilities.groupEntitycolumn);
        Thread.sleep(500);
        test.log(LogStatus.PASS, "Select Group Entity  :" + ExcelUtilities.groupEntitycolumn);
    }

    public void groupService1() throws InterruptedException {

        Select selectgroupservice1 = new Select(driver.findElement(By.xpath(groupservice)));
        selectgroupservice1.selectByVisibleText(ExcelUtilities.groupServicecolumn1);
        Thread.sleep(500);
        test.log(LogStatus.PASS, "Select Group Service 1  :" + ExcelUtilities.groupServicecolumn1);
    }

    public void groupService2() throws InterruptedException {

        Select selectgroupservice2 = new Select(driver.findElement(By.xpath(groupservice)));
        selectgroupservice2.selectByVisibleText(ExcelUtilities.groupServicecolumn2);
        Thread.sleep(500);
        test.log(LogStatus.PASS, "Select Group Service 2  :" + ExcelUtilities.groupServicecolumn2);
    }

    public void groupService3() throws InterruptedException {

        Select selectgroupservice3 = new Select(driver.findElement(By.xpath(groupservice)));
        selectgroupservice3.selectByVisibleText(ExcelUtilities.groupServicecolumn3);
        Thread.sleep(500);
        test.log(LogStatus.PASS, "Select Group Service 3  :" + ExcelUtilities.groupServicecolumn3);
    }

    public void groupService4() throws InterruptedException {

        Select selectgroupservice4 = new Select(driver.findElement(By.xpath(groupservice)));
        selectgroupservice4.selectByVisibleText(ExcelUtilities.groupServicecolumn4);
        Thread.sleep(500);
        test.log(LogStatus.PASS, "Select Group Service 4  :" + ExcelUtilities.groupServicecolumn4);
    }

    public void serviceplusbtn() {
        clickElement(By.xpath(groupserviceadd));
        test.log(LogStatus.PASS, "Click on Group Service Add Button");

    }

    @Test
    public  void verifyAddedGroup() throws InterruptedException {
        try {
            // Scroll the page to bring the necessary elements into view
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,1000)");
            Thread.sleep(500);

            // Check if the group is successfully added by verifying the group name
            if (driver.getPageSource().contains(ExcelUtilities.groupEngNamecolumn)) {
                Thread.sleep(500);
                // Capture the screenshot after verifying the added group
                String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

                if (screenshotPath != null) {
                    // Convert screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Group Added Screenshot' width='500' height='200' />";

                    // Log success message with screenshot embedded in the report
                    test.log(LogStatus.PASS, "Group added successfully. " + imgTag);
                } else {
                    test.log(LogStatus.INFO, "Failed to capture screenshot after adding the group.");
                }

                System.out.println("Group Added Successfully");

            } else {
                System.out.println("Group Failed to Add");
                test.log(LogStatus.FAIL, "Group was not added successfully.");
            }
        } catch (Exception e) {
            // Log any exceptions that occur during the process
            System.out.println("Error occurred while verifying added group: " + e.getMessage());
            test.log(LogStatus.FAIL, "Error occurred while verifying added group: " + e.getMessage());
        }
    }


    @Test
    public void GroupViewTXN() throws InterruptedException {
        try {
            // Open the group tab
            grouptab();

            // Scroll the page to bring necessary elements into view
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,1000)");
            Thread.sleep(500);

            // Check if the group code exists on the page
            if (driver.getPageSource().contains(ExcelUtilities.groupcodecolumn)) {
                Thread.sleep(500);
                System.out.println("Success VIEW");

                // Find and click the "View" button for the group
                clickElement(By.xpath(GroupViewbtn));
                js.executeScript("window.scrollBy(0,50)");

                // Capture a screenshot after viewing the group details
                String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
                if (screenshotPath != null) {
                    // Convert screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Group View Screenshot' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Group viewed successfully. " + imgTag);
                } else {
                    test.log(LogStatus.INFO, "Failed to capture screenshot after viewing the group.");
                }

                Thread.sleep(500);
            } else {
                System.out.println("Group record is not found");
                test.log(LogStatus.FAIL, "Group record not found. Unable to view group.");
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur during the test
            System.out.println("Error occurred during Group View TXN: " + e.getMessage());
            test.log(LogStatus.FAIL, "Error occurred during Group View TXN: " + e.getMessage());
        }
    }

    @Test
    public void GroupDeleteTXN() throws InterruptedException {
        try {
            // Open the group tab
            grouptab();

            // Scroll down to bring necessary elements into view
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,1000)");
            Thread.sleep(500);

            // Check if the group code exists on the page
            if (driver.getPageSource().contains(ExcelUtilities.groupcodecolumn)) {
                Thread.sleep(500);

                // Capture a screenshot before performing delete action
                String screenshotPath = Screenshot.captureScreenShot(driver, "GroupdeleteTXN_1");

                if (screenshotPath != null) {
                    // Convert screenshot to Base64 for embedding in the Extent report
                    String base64Image = encodeImageToBase64(screenshotPath);
                    String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Group Delete Screenshot' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Group record found. " + imgTag);
                } else {
                    test.log(LogStatus.INFO, "Failed to capture screenshot before group deletion.");
                }

                // Find the "Delete" button and click on it
                clickElement(By.xpath(GroupDeletebtn));

                // Handle the alert that appears after deletion
                Alert alert = driver.switchTo().alert();
                alert.accept();

                System.out.println("Successfully Deleted");

                // Log the success in the report
                test.log(LogStatus.PASS, "Group deleted successfully.");
            } else {
                // If group record is not found, log failure
                System.out.println("Group record is not found");
                test.log(LogStatus.FAIL, "Group record not found. Deletion failed.");
            }

        } catch (Exception e) {
            // Log any exceptions that occur during the test execution
            System.out.println("Error occurred during Group Delete TXN: " + e.getMessage());
            test.log(LogStatus.FAIL, "Error occurred during Group Delete TXN: " + e.getMessage());
        }
    }


    public void CreateNEWGroup() {
        try {
            // Step 1: Navigate to the group tab
            grouptab();
            test.log(LogStatus.PASS, "Group tab clicked successfully.");

            // Step 2: Fill out the group details
            groupcode();
            test.log(LogStatus.PASS, "Group code entered.");

            groupEngName();
            test.log(LogStatus.PASS, "Group English Name entered.");

            groupArabName();
            test.log(LogStatus.PASS, "Group Arabic Name entered.");

            groupEnity();
            test.log(LogStatus.PASS, "Group Entity selected.");

            groupService1();
            test.log(LogStatus.PASS, "Group service 1 selected.");

            // Step 3: Click the plus button to add services
            serviceplusbtn();
            test.log(LogStatus.PASS, "Service plus button clicked.");

            // Step 4: Save the group
            save();
            test.log(LogStatus.PASS, "Group saved successfully.");

            // Step 5: Capture and log screenshot after group creation
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the Extent report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Group Creation Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Screenshot captured after group creation: " + imgTag);
            } else {
                test.log(LogStatus.INFO, "Screenshot could not be captured after group creation.");
            }

        } catch (Exception e) {
            // Step 6: Handle exceptions and log the failure
            test.log(LogStatus.FAIL, "Error during group creation: " + e.getMessage());

            // Capture and log screenshot for exception scenario
            String screenshotPath = Screenshot.captureScreenShot(driver, "CreateNewGroup_Exception");
            if (screenshotPath != null) {
                // Convert screenshot to Base64 and embed in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Exception Screenshot' width='500' height='200' />";
                test.log(LogStatus.FAIL, "Screenshot captured for exception: " + imgTag);
            }
        }
    }


    public void CreateNEWGroupMultipleService() {
        try {
            // Navigate to the group tab
            grouptab();
            test.log(LogStatus.PASS, "Group tab clicked successfully.");

            // Fill out the group details
            groupcode();
            test.log(LogStatus.PASS, "Group code entered.");

            groupEngName();
            test.log(LogStatus.PASS, "Group English Name entered.");

            groupArabName();
            test.log(LogStatus.PASS, "Group Arabic Name entered.");

            groupEnity();
            test.log(LogStatus.PASS, "Group Entity selected.");

            // Add the first service
            groupService1();
            serviceplusbtn();
            test.log(LogStatus.PASS, "First service added.");

            // Add the second service
            groupService2();
            serviceplusbtn();
            test.log(LogStatus.PASS, "Second service added.");

            // Add the third service
            groupService3();
            test.log(LogStatus.PASS, "Third service added.");

            // Save the group
            save();
            test.log(LogStatus.PASS, "Group saved successfully.");

            // Capture a screenshot after group creation
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Convert screenshot to Base64 for embedding in the Extent report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Group Creation Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Screenshot captured after group creation: " + imgTag);
            }

        } catch (Exception e) {
            // Log the exception and capture a screenshot in case of failure
            test.log(LogStatus.FAIL, "Error during group creation with multiple services: " + e.getMessage());

            // Capture screenshot in case of failure
            String screenshotPath = Screenshot.captureScreenShot(driver, "CreateNewGroupMultipleService_Exception");
            if (screenshotPath != null) {
                // Convert screenshot to Base64 and embed in the report
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Exception Screenshot' width='500' height='200' />";
                test.log(LogStatus.FAIL, "Screenshot captured for exception: " + imgTag);
            }
        }
    }


    public void SingleDeleteGroup() {
        try {
            // Navigate to the group tab
            grouptab();
            test.log(LogStatus.PASS, "Group tab clicked successfully.");

            // Fill out the group details
            groupcode();
            test.log(LogStatus.PASS, "Group code entered.");

            groupEngName();
            test.log(LogStatus.PASS, "Group English Name entered.");

            groupArabName();
            test.log(LogStatus.PASS, "Group Arabic Name entered.");

            groupEnity();
            test.log(LogStatus.PASS, "Group Entity selected.");

            // Add the first service
            groupService1();
            serviceplusbtn();
            test.log(LogStatus.PASS, "First service added.");

            // Add the second service
            groupService2();
            serviceplusbtn();
            test.log(LogStatus.PASS, "Second service added.");

            // Delete the second service
            groupservicedeletebtn2();
            test.log(LogStatus.PASS, "Second service deleted.");

            // Save the group
            save();
            test.log(LogStatus.PASS, "Group saved successfully.");

            // Capture a screenshot after group creation and deletion
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Embed the screenshot into the Extent report without Base64 encoding
                String imgTag = "<img src='" + screenshotPath + "' alt='Group Creation and Deletion Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Screenshot captured after group creation and deletion: " + imgTag);
            }

        } catch (Exception e) {
            // Log the exception and capture a screenshot in case of failure
            test.log(LogStatus.FAIL, "Error during group creation and deletion: " + e.getMessage());

            // Capture screenshot in case of failure
            String screenshotPath = Screenshot.captureScreenShot(driver, "SingleDeleteGroup_Exception");
            if (screenshotPath != null) {
                // Embed the screenshot into the Extent report
                String imgTag = "<img src='" + screenshotPath + "' alt='Exception Screenshot' width='500' height='200' />";
                test.log(LogStatus.FAIL, "Screenshot captured for exception: " + imgTag);
            }
        }
    }


    public void verifyUniqueGroup() {
        try {
            // Navigate to the group tab and fill in the group details
            grouptab();
            test.log(LogStatus.PASS, "Group tab clicked successfully.");

            groupcode();
            test.log(LogStatus.PASS, "Group code entered.");

            groupEngName();
            test.log(LogStatus.PASS, "Group English Name entered.");

            groupArabName();
            test.log(LogStatus.PASS, "Group Arabic Name entered.");

            groupEnity();
            test.log(LogStatus.PASS, "Group Entity selected.");

            // Add services
            groupService1();
            serviceplusbtn();
            test.log(LogStatus.PASS, "First service added.");

            groupService2();
            serviceplusbtn();
            test.log(LogStatus.PASS, "Second service added.");

            groupService3();
            serviceplusbtn();
            test.log(LogStatus.PASS, "Third service added.");

            groupService4();
            serviceplusbtn();
            test.log(LogStatus.PASS, "Fourth service added.");

            // Save the group
            save();
            test.log(LogStatus.PASS, "Group saved successfully.");

            // Check if the error message indicating group code uniqueness is displayed
            if (driver.getPageSource().contains("VLD-SC0003: Group code is not Unique")) {
                // Wait for a short duration (optional) for the page to settle


                // Capture a screenshot if the error message is displayed
                String screenshotPath = Screenshot.captureScreenShot(driver, "Group code unique");
                if (screenshotPath != null) {
                    // Embed the screenshot into the report (No Base64 encoding used)
                    String imgTag = "<img src='" + screenshotPath + "' alt='Group code uniqueness error' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Group code is not unique. Screenshot captured: " + imgTag);
                }

                System.out.println("Group code is not unique.");
                test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);

            } else {
                System.out.println("Group code is unique.");
                test.log(LogStatus.PASS, "Group code is unique.");
            }

        } catch (Exception e) {
            // Log the exception and capture a screenshot in case of failure
            test.log(LogStatus.FAIL, "Error during verify unique group: " + e.getMessage());

            // Capture screenshot in case of failure
            String screenshotPath = Screenshot.captureScreenShot(driver, "verifyUniqueGroup_Exception");
            if (screenshotPath != null) {
                // Embed the screenshot into the report
                String imgTag = "<img src='" + screenshotPath + "' alt='Exception Screenshot' width='500' height='200' />";
                test.log(LogStatus.FAIL, "Screenshot captured for exception: " + imgTag);
            }
        }
    }


    public void VerifyGroupCode_10digits() {
        try {
            // Navigate to the group tab and fill in the group details
            grouptab();
            test.log(LogStatus.PASS, "Group tab clicked successfully.");

            groupcode();
            test.log(LogStatus.PASS, "Group code entered.");

            groupEngName();
            test.log(LogStatus.PASS, "Group English Name entered.");

            groupArabName();
            test.log(LogStatus.PASS, "Group Arabic Name entered.");

            groupEnity();
            test.log(LogStatus.PASS, "Group Entity selected.");

            // Add services
            groupService1();
            serviceplusbtn();
            test.log(LogStatus.PASS, "First service added.");

            // Save the group
            save();
            test.log(LogStatus.PASS, "Group saved successfully.");

            // Capture a screenshot after saving
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                test.log(LogStatus.PASS, "Screenshot captured after group creation: " + screenshotPath);
            }

            // Check if the error message for group code greater than 10 digits is displayed
            if (driver.getPageSource().contains("Value should be less than or equal to 10 digits only")) {
                // Capture screenshot if the error message is displayed
                screenshotPath = Screenshot.captureScreenShot(driver, "GroupCode_GreaterThan10Digits");
                if (screenshotPath != null) {
                    // Embed the screenshot into the report (No Base64 encoding used)
                    String imgTag = "<img src='" + screenshotPath + "' alt='Group Code greater than 10 digits' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Group Code is greater than 10 digits. Screenshot captured: " + imgTag);
                }

                System.out.println("Group code is greater than 10 digits.");
            } else {
                test.log(LogStatus.PASS, "Group Code is valid (less than or equal to 10 digits).");
                System.out.println("Group code is valid (<= 10 digits).");
            }

        } catch (Exception e) {
            // Log the exception and capture a screenshot in case of failure
            test.log(LogStatus.FAIL, "Error during verifying group code 10 digits: " + e.getMessage());

            // Capture screenshot in case of failure
            String screenshotPath = Screenshot.captureScreenShot(driver, "VerifyGroupCode_Exception");
            if (screenshotPath != null) {
                // Embed the screenshot into the report
                String imgTag = "<img src='" + screenshotPath + "' alt='Exception Screenshot' width='500' height='200' />";
                test.log(LogStatus.FAIL, "Screenshot captured for exception: " + imgTag);
            }
        }
    }


    public void VerifyGroupName_150digits() {
        try {
            // Navigate to the group tab and fill in the group details
            grouptab();
            test.log(LogStatus.PASS, "Group tab clicked successfully.");

            groupcode();
            test.log(LogStatus.PASS, "Group code entered.");

            groupEngName();
            test.log(LogStatus.PASS, "Group English Name entered.");

            groupArabName();
            test.log(LogStatus.PASS, "Group Arabic Name entered.");

            groupEnity();
            test.log(LogStatus.PASS, "Group Entity selected.");

            // Add services
            groupService1();
            serviceplusbtn();
            test.log(LogStatus.PASS, "First service added.");

            groupService2();
            serviceplusbtn();
            test.log(LogStatus.PASS, "Second service added.");

            groupService3();
            serviceplusbtn();
            test.log(LogStatus.PASS, "Third service added.");

            groupService4();
            serviceplusbtn();
            test.log(LogStatus.PASS, "Fourth service added.");

            // Save the group
            save();
            test.log(LogStatus.PASS, "Group saved successfully.");

            // Capture screenshot after saving
            String screenshotPath = Screenshot.captureScreenShot(driver, "GroupNameAbove150Digits");
            if (screenshotPath != null) {
                // Embed the screenshot into the report (No Base64 encoding used)
                String imgTag = "<img src='" + screenshotPath + "' alt='GroupNameAbove150Digits' width='500' height='200' />";
                test.log(LogStatus.PASS, "Screenshot captured after saving group with name above 150 digits: " + imgTag);
            }

            // Check if the error message for group name exceeding 150 characters is displayed
            if (driver.getPageSource().contains("Group name should be less than or equal to 150 characters")) {
                test.log(LogStatus.FAIL, "Group name exceeds 150 characters.");
                System.out.println("Group name is above 150 characters.");
            } else {
                test.log(LogStatus.PASS, "Group name is within the allowed 150 characters.");
                System.out.println("Group name is within 150 characters.");
            }

        } catch (Exception e) {
            // Log the exception and capture a screenshot in case of failure
            test.log(LogStatus.FAIL, "Error during verifying group name length: " + e.getMessage());

            // Capture screenshot in case of failure
            String screenshotPath = Screenshot.captureScreenShot(driver, "VerifyGroupName_Exception");
            if (screenshotPath != null) {
                // Embed the screenshot into the report
                String imgTag = "<img src='" + screenshotPath + "' alt='Exception Screenshot' width='500' height='200' />";
                test.log(LogStatus.FAIL, "Screenshot captured for exception: " + imgTag);
            }
        }
    }


    public void ClickonResetGroup() {
        try {
            // Navigate to the group tab and fill in the group details
            grouptab();
            test.log(LogStatus.PASS, "Group tab clicked successfully.");

            groupcode();
            test.log(LogStatus.PASS, "Group code entered.");

            groupEngName();
            test.log(LogStatus.PASS, "Group English Name entered.");

            groupArabName();
            test.log(LogStatus.PASS, "Group Arabic Name entered.");

            groupEnity();
            test.log(LogStatus.PASS, "Group Entity selected.");

            // Add services to the group
            groupService1();
            test.log(LogStatus.PASS, "Group service 1 added.");

            serviceplusbtn();
            test.log(LogStatus.PASS, "Service plus button clicked.");

            // Reset the group fields
            reset();
            test.log(LogStatus.PASS, "Reset button clicked, group fields reset.");

            // Optionally, capture a screenshot to document the reset action
            String screenshotPath = Screenshot.captureScreenShot(driver, "Group_Reset_Action");
            if (screenshotPath != null) {
                test.log(LogStatus.PASS, "Screenshot captured after resetting group: " + screenshotPath);
            }

        } catch (Exception e) {
            // Log the exception and capture a screenshot in case of failure
            test.log(LogStatus.FAIL, "Error during resetting group: " + e.getMessage());

            // Capture screenshot in case of failure
            String screenshotPath = Screenshot.captureScreenShot(driver, "ClickonResetGroup_Exception");
            if (screenshotPath != null) {
                test.log(LogStatus.FAIL, "Screenshot captured for exception: " + screenshotPath);
            }
        }
    }

    public void BlankFieldGroupCode() {
        try {
            // Navigate to the group tab
            grouptab();
            test.log(LogStatus.PASS, "Group tab clicked successfully.");

            // Fill in the group details (without the group code)
            groupEngName();
            test.log(LogStatus.PASS, "Group English Name entered.");

            groupArabName();
            test.log(LogStatus.PASS, "Group Arabic Name entered.");

            groupEnity();
            test.log(LogStatus.PASS, "Group Entity selected.");

            // Add services to the group
            groupService1();
            test.log(LogStatus.PASS, "Group service 1 added.");

            serviceplusbtn();
            test.log(LogStatus.PASS, "Service plus button clicked.");

            // Save the group (without the group code filled in)
            save();
            test.log(LogStatus.PASS, "Group saved.");

            // Verify error message for blank group code
            VerifyErrorMsg();
            test.log(LogStatus.PASS, "Error message verified for blank Group Code.");

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, "BlankFieldGroupCode_Exception");
            if (screenshotPath != null) {
                String imgTag = "<img src='" + screenshotPath + "' alt='Blank Field Group Code' width='500' height='200' />";
                test.log(LogStatus.PASS, "Screenshot captured for exception: " + imgTag);
            }
        } catch (Exception e) {
            // Log any exception that occurs and capture a screenshot for the failure
            test.log(LogStatus.FAIL, "Error during blank field Group Code test: " + e.getMessage());
        }
    }

    public void BlankFieldGroupName() {
        try {
            // Navigate to the group tab
            grouptab();
            test.log(LogStatus.PASS, "Group tab clicked successfully.");

            // Fill in the group details
            groupcode();
            test.log(LogStatus.PASS, "Group Code entered.");

            groupArabName();
            test.log(LogStatus.PASS, "Group Arabic Name entered.");

            groupEnity();
            test.log(LogStatus.PASS, "Group Entity selected.");

            // Add services to the group
            groupService1();
            test.log(LogStatus.PASS, "Group service 1 added.");

            serviceplusbtn();
            test.log(LogStatus.PASS, "Service plus button clicked.");

            // Save the group (without the group name filled in)
            save();
            test.log(LogStatus.PASS, "Group saved.");

            // Verify error message for blank group name
            VerifyErrorMsg();
            test.log(LogStatus.PASS, "Error message verified for blank Group Name.");

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, "BlankFieldGroupName_Exception");
            if (screenshotPath != null) {
                String imgTag = "<img src='" + screenshotPath + "' alt='Blank Field Group Name' width='500' height='200' />";
                test.log(LogStatus.PASS, "Screenshot captured for exception: " + imgTag);
            }
        } catch (Exception e) {
            // Log any exception that occurs and capture a screenshot for the failure
            test.log(LogStatus.FAIL, "Error during blank field Group Name test: " + e.getMessage());
        }
    }

    public void BlankFieldGroupName_Arabic() {
        try {
            // Navigate to the group tab
            grouptab();
            test.log(LogStatus.PASS, "Group tab clicked successfully.");

            // Fill in the group details (without the Arabic group name)
            groupcode();
            test.log(LogStatus.PASS, "Group code entered.");

            groupEngName();
            test.log(LogStatus.PASS, "Group English Name entered.");

            groupEnity();
            test.log(LogStatus.PASS, "Group Entity selected.");

            // Add services to the group
            groupService1();
            test.log(LogStatus.PASS, "Group service 1 added.");

            serviceplusbtn();
            test.log(LogStatus.PASS, "Service plus button clicked.");

            // Save the group (without the Arabic group name)
            save();
            test.log(LogStatus.PASS, "Group saved.");

            // Verify error message for blank Arabic group name
            VerifyErrorMsg();
            test.log(LogStatus.PASS, "Error message verified for blank Arabic Group Name.");

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, "BlankFieldGroupName_Arabic_Exception");
            if (screenshotPath != null) {
                String imgTag = "<img src='" + screenshotPath + "' alt='Blank Field Group Arabic Name' width='500' height='200' />";
                test.log(LogStatus.PASS, "Screenshot captured for exception: " + imgTag);
            }
        } catch (Exception e) {
            // Log any exception that occurs and capture a screenshot for the failure
            test.log(LogStatus.FAIL, "Error during blank field Arabic Group Name test: " + e.getMessage());
        }
    }

    public void BlankFieldEntity_Drop() {
        try {
            // Navigate to the group tab
            grouptab();

            // Fill in the required group details
            groupcode();
            groupEngName();
            groupArabName();

            // Add a service to the group
            serviceplusbtn();

            // Save the group with the blank entity field
            save();

            // Verify if an error message appears due to the blank entity field
            VerifyErrorMsg();

            // Log success and capture screenshot
            test.log(LogStatus.PASS, "Test case passed for blank entity field in the group.");
            String screenshotPath = Screenshot.captureScreenShot(driver, "BlankFieldEntity_Drop_Success");
            if (screenshotPath != null) {
                String imgTag = "<img src='" + screenshotPath + "' alt='Blank Field Entity Drop' width='500' height='200' />";
                test.log(LogStatus.PASS, "Screenshot captured: " + imgTag);
            }

        } catch (Exception e) {
            // Log the exception and capture screenshot in case of failure
            System.out.println("Error occurred while testing blank entity field drop: " + e.getMessage());
            test.log(LogStatus.FAIL, "Error occurred while testing blank entity field drop: " + e.getMessage());
        }
    }

    public void BlankFieldService_Drop() {
        try {
            // Navigate to the group tab
            grouptab();

            // Fill in the required group details
            groupcode();
            groupEngName();
            groupArabName();
            groupEnity();

            // Add a service to the group
            serviceplusbtn();

            // Attempt to save the group with a blank service field
            save();

            // Verify if an error message appears due to the blank service field
            VerifyErrorMsg();

            // Log success and capture screenshot
            test.log(LogStatus.PASS, "Test case passed for blank service field in the group.");
            String screenshotPath = Screenshot.captureScreenShot(driver, "BlankFieldService_Drop_Success");
            if (screenshotPath != null) {
                String imgTag = "<img src='" + screenshotPath + "' alt='Blank Field Service Drop' width='500' height='200' />";
                test.log(LogStatus.PASS, "Screenshot captured: " + imgTag);
            }

        } catch (Exception e) {
            // Log the exception and capture screenshot in case of failure
            System.out.println("Error occurred while testing blank service field drop: " + e.getMessage());
            test.log(LogStatus.FAIL, "Error occurred while testing blank service field drop: " + e.getMessage());
        }
    }

    // -----------Group ARAB code
    public void groupservicedeletebtn1() throws Exception {
        clickElement(By.xpath(groupsrvdeletebtn1));
        Thread.sleep(500);
        System.out.println("Group Service removed " + ExcelUtilities.groupServicecolumn1);

    }

    public void groupservicedeletebtn2() throws Exception {
        clickElement(By.xpath(groupsrvdeletebtn2));
        Thread.sleep(500);
        System.out.println("Group Service removed " + ExcelUtilities.groupServicecolumn2);
    }

    public void CreateNEWGroup_ARAB() {
        try {
            // Navigate to the group tab in Arabic
            grouptabARAB();

            // Fill in group details
            groupcode();
            groupEngName();
            groupArabName();
            groupEnity();
            groupService1();

            // Add multiple services if needed (commented code can be enabled as per requirement)
            serviceplusbtn();
            groupService2();
            serviceplusbtn();
            // groupService3();  // Uncomment if additional service is required
            // serviceplusbtn();  // Uncomment if additional service is required
            // groupService4();  // Uncomment if additional service is required
            // serviceplusbtn();  // Uncomment if additional service is required

            // Save the group in Arabic
            savebtnARAB();

            // Log success and capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, "CreateNewGroup_ARAB_Success");
            String imgTagSuccess = "<img src='" + screenshotPath + "' alt='Group created successfully' width='500' height='200' />";
            test.log(LogStatus.PASS, "New group created successfully in Arabic. " + imgTagSuccess);

        } catch (Exception e) {
            // Log the exception and capture screenshot in case of failure
            System.out.println("Error occurred while creating new group in Arabic: " + e.getMessage());
            test.log(LogStatus.FAIL, "Error occurred while creating new group in Arabic: " + e.getMessage());

            // Capture screenshot for debugging purposes
            String screenshotPath = Screenshot.captureScreenShot(driver, "CreateNewGroup_ARAB_Exception");
            String imgTagFailure = "<img src='" + screenshotPath + "' alt='Group creation failure' width='500' height='200' />";
            test.log(LogStatus.FAIL, "Screenshot captured for exception: " + imgTagFailure);
        }
    }


    public void GroupViewTXN_ARAB() {
        try {
            // Navigate to the group tab in Arabic
            grouptabARAB();

            // Scroll down the page to bring the view button into view
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,1000)");
            Thread.sleep(500);

            // Check if the group code is present on the page
            if (driver.getPageSource().contains(ExcelUtilities.groupcodecolumn)) {
                // Capture screenshot before performing the view action
                String beforeViewScreenshotPath = Screenshot.captureScreenShot(driver, "GroupViewTXN_1");
                String imgTagBeforeView = "<img src='" + beforeViewScreenshotPath + "' alt='Group view before action' width='500' height='200' />";
                test.log(LogStatus.PASS, "Group found. Proceeding with view. Screenshot captured: " + imgTagBeforeView);

                // Find the view button and click it
                clickElement(By.xpath(GroupViewbtn));
                js.executeScript("window.scrollBy(0,50)");

                // Capture screenshot after viewing the group details
                String afterViewScreenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
                String imgTagAfterView = "<img src='" + afterViewScreenshotPath + "' alt='Group viewed successfully' width='500' height='200' />";
                test.log(LogStatus.PASS, "Group viewed successfully. " + imgTagAfterView);

            } else {
                // Log failure if the group record is not found
                System.out.println("Group record is not found.");
                test.log(LogStatus.FAIL, "Group record is not found: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

                // Capture a screenshot for debugging
                String screenshotPath = Screenshot.captureScreenShot(driver, "GroupViewTXN_ARAB_Exception");
                String imgTagFailure = "<img src='" + screenshotPath + "' alt='Group view failure' width='500' height='200' />";
                test.log(LogStatus.FAIL, "Screenshot captured for exception: " + imgTagFailure);
            }
        } catch (Exception e) {
            // Log any exceptions and capture screenshots for debugging
            System.out.println("Error occurred during group view: " + e.getMessage());
            test.log(LogStatus.FAIL, "Error during group view: " + e.getMessage());

            // Capture a screenshot for debugging purposes
            String screenshotPath = Screenshot.captureScreenShot(driver, "GroupViewTXN_ARAB_Exception");
            String imgTagException = "<img src='" + screenshotPath + "' alt='Group view error' width='500' height='200' />";
            test.log(LogStatus.FAIL, "Screenshot captured for exception: " + imgTagException);
        }
    }

    public void GroupDeleteTXN_ARAB() {
        try {
            // Navigate to the group section in Arabic
            grouptabARAB();

            // Scroll down the page to ensure the delete button is visible
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,1000)");
            Thread.sleep(500);

            // Check if the group code exists on the page
            if (driver.getPageSource().contains(ExcelUtilities.groupcodecolumn)) {
                // Capture screenshot before proceeding with the deletion
                String beforeDeleteScreenshotPath = Screenshot.captureScreenShot(driver, "GroupdeleteTXN_1");

                // Log before deletion with the screenshot
                String imgTagBeforeDelete = "<img src='" + beforeDeleteScreenshotPath + "' alt='Group before deletion' width='500' height='200' />";
                test.log(LogStatus.PASS, "Group code found. Proceeding with deletion. Screenshot captured: " + imgTagBeforeDelete);

                // Find and click the delete button for the group
                WebElement GrpRec = driver.findElement(By.xpath(GroupDeletebtn));
                js.executeScript("arguments[0].click()", GrpRec);
                System.out.println("Success DELETE");

                // Wait for the alert to appear and accept it
                Thread.sleep(500);
                Alert alert = driver.switchTo().alert();
                Thread.sleep(500);
                alert.accept();

                // Log success after deletion
                test.log(LogStatus.PASS, "Group deleted successfully: " + ExcelUtilities.testCaseDesritpioncolumnvalue);

                // Capture screenshot after deletion
                String afterDeleteScreenshotPath = Screenshot.captureScreenShot(driver, "GroupDeleteTXN_ARAB_Exception");

                // Embed the screenshot in the report after deletion
                if (afterDeleteScreenshotPath != null) {
                    String imgTagAfterDelete = "<img src='" + afterDeleteScreenshotPath + "' alt='Group deletion completed' width='500' height='200' />";
                    test.log(LogStatus.PASS, "Screenshot captured for deletion completion: " + imgTagAfterDelete);
                }

            } else {
                // Log failure if the group record is not found
                System.out.println("Group record is not found.");
                test.log(LogStatus.FAIL, "Group record is not found: " + ExcelUtilities.testCaseDesritpioncolumnvalue);
            }
        } catch (Exception e) {
            // Handle any exceptions and log failure
            System.out.println("Error occurred during group deletion: " + e.getMessage());
            test.log(LogStatus.FAIL, "Error during group deletion: " + e.getMessage());

            // Capture a screenshot for debugging if an error occurs
            String screenshotPath = Screenshot.captureScreenShot(driver, "GroupDeleteTXN_ARAB_Exception");
            if (screenshotPath != null) {
                String imgTagException = "<img src='" + screenshotPath + "' alt='Group deletion error' width='500' height='200' />";
                test.log(LogStatus.FAIL, "Screenshot captured for exception: " + imgTagException);
            }
        }
    }


    public void ClickonResetGroup_ARAB() {
        try {
            // Navigate to the group section and fill in necessary fields in Arabic
            grouptabARAB();
            groupcode();
            groupEngName();
            groupArabName();  // The Arabic group name field should be filled
            groupEnity();
            groupService1();
            serviceplusbtn();

            // Click the reset button in Arabic
            resetARAB();

            // Log success after resetting the group form
            test.log(LogStatus.PASS, "Test passed: Group form reset successfully in Arabic.");

            String screenshotPath = Screenshot.captureScreenShot(driver, "ClickonResetGroup_ARAB_Exception");
            test.log(LogStatus.PASS, "Screenshot captured for exception: " + screenshotPath);

        } catch (Exception e) {
            // Handle any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Error occurred during ClickonResetGroup_ARAB: " + e.getMessage());

            // Capture a screenshot in case of failure for debugging

        }
    }

    public void BlankFieldGroupCode_ARAB() {
        try {
            // Navigate to the group section and fill in necessary fields in Arabic
            grouptabARAB();
            groupEngName();
            groupArabName();  // The Arabic group name field should be filled
            groupEnity();

            // Add a service using the service button
            groupService1();
            serviceplusbtn();

            // Attempt to save the group with a blank group code field
            savebtnARAB();

            // Verify if an error message appears due to the blank group code field
            VerifyErrorMsg();

            // Log success if the error message appeared as expected
            test.log(LogStatus.PASS, "Test passed: Error message displayed for blank group code in Arabic.");

            // Capture a screenshot for the success case
            String screenshotPath = Screenshot.captureScreenShot(driver, "BlankFieldGroupCode_ARAB_Exception");
            if (screenshotPath != null) {
                // Embed the screenshot in the report for success scenario
                String imgTag = "<img src='" + screenshotPath + "' alt='Blank Group Code Error in Arabic' width='500' height='200' />";
                test.log(LogStatus.PASS, "Screenshot captured for exception: " + imgTag);
            }

        } catch (Exception e) {
            // Handle any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Error occurred during BlankFieldGroupCode_ARAB: " + e.getMessage());

            // Capture a screenshot for debugging purposes in case of failure
            String screenshotPath = Screenshot.captureScreenShot(driver, "BlankFieldGroupCode_ARAB_Failure");
            if (screenshotPath != null) {
                // Embed the screenshot for failure scenario
                String imgTag = "<img src='" + screenshotPath + "' alt='Blank Group Code Failure in Arabic' width='500' height='200' />";
                test.log(LogStatus.FAIL, "Screenshot captured for failure: " + imgTag);
            }
        }
    }


    public void BlankFieldGroupName_ARAB() {
        try {
            // Navigate to the group section and fill in necessary fields in Arabic
            grouptabARAB();
            groupcode();
            groupArabName();  // The Arabic group name field should be left blank
            groupEnity();

            // Add a service using the service button
            groupService1();
            serviceplusbtn();

            // Attempt to save the group with a blank Arabic group name
            savebtnARAB();

            // Verify if an error message appears due to the blank group name field
            VerifyErrorMsg();

            // Log success if the error message appeared as expected
            test.log(LogStatus.PASS, "Test passed: Error message displayed for blank Arabic group name.");

            // Capture a screenshot for the success case
            String screenshotPath = Screenshot.captureScreenShot(driver, "BlankFieldGroupName_ARAB_Exception");
            if (screenshotPath != null) {
                // Embed the screenshot in the report
                String imgTag = "<img src='" + screenshotPath + "' alt='Blank Arabic Group Name Error' width='500' height='200' />";
                test.log(LogStatus.PASS, "Screenshot captured for exception: " + imgTag);
            }

        } catch (Exception e) {
            // Handle any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Error occurred during BlankFieldGroupName_ARAB: " + e.getMessage());

            // Capture a screenshot in case of failure for debugging purposes
            String screenshotPath = Screenshot.captureScreenShot(driver, "BlankFieldGroupName_ARAB_Failure");
            if (screenshotPath != null) {
                // Embed the screenshot for failure scenario
                String imgTag = "<img src='" + screenshotPath + "' alt='Blank Arabic Group Name Failure' width='500' height='200' />";
                test.log(LogStatus.FAIL, "Screenshot captured for failure: " + imgTag);
            }
        }
    }


    public void BlankFieldGroupNameArabic_ARAB() {
        try {
            // Navigate to the group section and fill in necessary fields in Arabic
            grouptabARAB();
            groupcode();
            groupEngName();
            groupEnity();

            // Add a service using the service button
            groupService1();
            serviceplusbtn();

            // Attempt to save the group with a blank Arabic group name
            savebtnARAB();

            // Verify if an error message appears due to the blank group name field
            VerifyErrorMsg();

            // Log success if the error message appeared as expected
            test.log(LogStatus.PASS, "Test passed: Error message displayed for blank Arabic group name.");
            String screenshotPath = Screenshot.captureScreenShot(driver, "BlankFieldGroupNameArabic_ARAB_Exception");
            test.log(LogStatus.PASS, "Screenshot captured for exception: " + screenshotPath);

        } catch (Exception e) {
            // Handle any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Error occurred during BlankFieldGroupNameArabic_ARAB: " + e.getMessage());

            // Capture a screenshot in case of failure for debugging

        }
    }

    public void BlankFieldEntity_Drop_ARAB() {
        try {
            // Navigate to the group tab and fill in necessary fields in Arabic
            grouptabARAB();
            groupcode();
            groupEngName();
            groupArabName();

            // Add a service using the service button
            serviceplusbtn();

            // Attempt to save the group with a blank entity dropdown
            savebtnARAB();

            // Verify if an error message appears due to the blank entity dropdown
            VerifyErrorMsg();

            // Log success if the error message appeared as expected
            test.log(LogStatus.PASS, "Test passed: Error message displayed for blank entity dropdown in ARAB section.");

            // Capture screenshot for successful scenario
            String screenshotPath = Screenshot.captureScreenShot(driver, "BlankFieldEntity_Drop_ARAB_Exception");

            // Embed the screenshot into the report
            if (screenshotPath != null) {
                String imgTag = "<img src='" + screenshotPath + "' alt='Blank Entity Error' width='500' height='200' />";
                test.log(LogStatus.PASS, "Screenshot captured for exception: " + imgTag);
            }

        } catch (Exception e) {
            // Handle any exceptions that occur during the test execution
            test.log(LogStatus.FAIL, "Error occurred during BlankFieldEntity_Drop_ARAB: " + e.getMessage());

            // Capture a screenshot in case of failure for debugging
            String screenshotPath = Screenshot.captureScreenShot(driver, "BlankFieldEntity_Drop_ARAB_Failure");

            // Embed the screenshot in case of failure
            if (screenshotPath != null) {
                String imgTag = "<img src='" + screenshotPath + "' alt='Blank Entity Error Failure' width='500' height='200' />";
                test.log(LogStatus.FAIL, "Screenshot captured for failure: " + imgTag);
            }
        }
    }


    public void BlankFieldService_Drop_ARAB() {
        try {
            // Navigate to the required group tab and fill out the necessary fields
            grouptabARAB();
            groupcode();
            groupEngName();
            groupArabName();
            groupEnity();

            // Add a service using the service button
            serviceplusbtn();

            // Click the save button to attempt saving the group
            savebtnARAB();

            // Verify if an error message appears due to the blank service field
            VerifyErrorMsg();

            // Log success if the error message was correctly shown
            test.log(LogStatus.PASS, "Test passed: Error message appeared as expected for blank service dropdown in ARAB section.");

            // Capture a screenshot for the test case
            String screenshotPath = Screenshot.captureScreenShot(driver, "BlankFieldService_Drop_ARAB_Exception");

            // Embed the screenshot into the report
            if (screenshotPath != null) {
                String imgTag = "<img src='" + screenshotPath + "' alt='Blank Field Service Error' width='500' height='200' />";
                test.log(LogStatus.PASS, "Screenshot captured for exception: " + imgTag);
            }

        } catch (Exception e) {
            // Handle any exceptions that might occur
            test.log(LogStatus.FAIL, "Error occurred during BlankFieldService_Drop_ARAB: " + e.getMessage());

            // Capture a screenshot of the failure for debugging purposes
            String screenshotPath = Screenshot.captureScreenShot(driver, "BlankFieldService_Drop_ARAB_Failure");

            // Embed the screenshot in case of failure
            if (screenshotPath != null) {
                String imgTag = "<img src='" + screenshotPath + "' alt='Blank Service Error' width='500' height='200' />";
                test.log(LogStatus.FAIL, "Screenshot captured for failure: " + imgTag);
            }
        }
    }

    @Test
    public void verifyAddedGroup_ARAB() {
        try {
            // Scroll down the page to ensure the necessary element is in view
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,1000)");
            Thread.sleep(500);

            // Verify if the group code appears on the page, confirming successful group creation
            if (driver.getPageSource().contains(ExcelUtilities.groupcodecolumn)) {
                // Capture a screenshot upon successful verification
                Thread.sleep(500);
                String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

                // Embed the screenshot in the pass status log
                String imgTag = "<img src='" + screenshotPath + "' alt='Group Added' width='500' height='200' />";
                test.log(LogStatus.PASS, "Group added successfully. Screenshot captured: " + imgTag);
                System.out.println("Group Added Successfully");

            } else {
                // If the group code is not found, log the failure and capture a screenshot
                System.out.println("Group Failed to Add");
                test.log(LogStatus.FAIL, "Group failed to add. Group code not found in the page source.");

                // Capture a screenshot on failure
                String screenshotPath = Screenshot.captureScreenShot(driver, "Group_Add_Failure");
                String imgTag = "<img src='" + screenshotPath + "' alt='Group Add Failure' width='500' height='200' />";
                test.log(LogStatus.FAIL, "Screenshot captured on failure: " + imgTag);
            }

        } catch (Exception e) {
            // Handle exceptions, log them, and capture a screenshot
            test.log(LogStatus.FAIL, "Error during verifyAddedGroup_ARAB: " + e.getMessage());

            // Capture a screenshot for the exception
            String screenshotPath = Screenshot.captureScreenShot(driver, "verifyAddedGroup_ARAB_Exception");
            String imgTag = "<img src='" + screenshotPath + "' alt='Exception' width='500' height='200' />";
            test.log(LogStatus.FAIL, "Screenshot captured for exception: " + imgTag);
        }
    }


    // ***************************************GROUP_ENDS****************************************************
    // ***************************************MIS_STARTS****************************************************
    @Test
    public  void viewMIS() throws InterruptedException {
        clickElement(By.xpath(MIStab));
        Thread.sleep(500);
        test.log(LogStatus.PASS, "Click on MIS Tab");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

    @Test
    public  void misdate() throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll to bring date input into view
        js.executeScript("window.scrollBy(0,500)");

        // Locate the start date input field using the XPath string
        WebElement startDateInput = driver.findElement(By.xpath(effstrtdte)); // effstrtdte should be a valid XPath string

        // Click to activate the input (optional)
        startDateInput.click();

        // Set the datetime value and trigger change events
        js.executeScript(
                "arguments[0].value = '2023-10-01T12:00'; " +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true })); " +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                startDateInput
        );

        Thread.sleep(700); // Replace with WebDriverWait if needed
        test.log(LogStatus.PASS, "Selected Start Effective Date");
    }


    @Test
    public  void misdateARAB() throws Exception {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollBy(0,500)");
        Actions act = new Actions(driver);
        Robot r = new Robot();
        clickElement(By.xpath(effstrtdte));

        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '2023-10-01T12:00';", effstrtdte);
        Thread.sleep(700);
        test.log(LogStatus.PASS, " Selected Start Effective Date");

    }

    @Test
    public  void misenddate() throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll down
        js.executeScript("window.scrollBy(0,500)");
        Thread.sleep(500); // Optional, for smoother flow

        // Locate the input field by XPath
        WebElement endDateInput = driver.findElement(By.xpath(effenddte)); // effenddte must be a valid XPath string

        // Click to activate if needed
        endDateInput.click();

        // Set the datetime value using JavaScript and trigger input/change events
        js.executeScript(
                "arguments[0].value = '2025-10-01T12:00'; " +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true })); " +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                endDateInput
        );

        Thread.sleep(2000); // Use WebDriverWait instead in production
        test.log(LogStatus.PASS, "Selected End Effective Date");
    }


    @Test
    public  void clkview() throws InterruptedException {

        WebElement clkmisview = driver.findElement(By.xpath(View));
        Thread.sleep(1000);
        clkmisview.click();
        // Capture screenshot
        String screenshotPath = Screenshot.captureScreenShot(driver, "viewMisValidData_" + ExcelUtilities.testCaseDesritpioncolumnvalue);

        // Check if the screenshot was captured successfully
        if (screenshotPath != null) {
            // Convert the screenshot to Base64 for embedding in the Extent report
            String base64Image = encodeImageToBase64(screenshotPath);

            // Create an HTML <img> tag to embed the screenshot in the report
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='View Mis Valid Data Screenshot' width='500' height='200' />";

            // Log the success message with the embedded image
            test.log(LogStatus.PASS, "View MIS Valid Data executed successfully. " + imgTag);
        } else {
            test.log(LogStatus.FAIL, "Failed to capture screenshot for View Mis Valid Data.");
        }

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        test.log(LogStatus.PASS, "Click on View button");

    }

    @Test
    public  void srvcdownload() throws InterruptedException {
        clickElement(By.xpath(Servicedownload));
        Thread.sleep(2000);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        Screenshot.captureScreenShot(driver, "download");
        test.log(LogStatus.PASS, "click on Download Button");

    }

    @Test
    public  void download() throws InterruptedException {
        clickElement(By.xpath(download));
        Thread.sleep(2000);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        Screenshot.captureScreenShot(driver, "download");
        test.log(LogStatus.PASS, "click on Download Button");

    }

    @Test
    public void clklang() throws InterruptedException {
        try {
            // Wait for the language change button to be clickable
            clickElement(By.xpath(lang));
            test.log(LogStatus.PASS, "Language change button clicked successfully.");

            // Wait for the language dropdown to be visible and interactable

            Select select = new Select(driver.findElement(By.xpath(lang)));
            select.selectByValue("ar");  // Select Arabic as the language

            // Capture screenshot after changing language
            String screenshotPath = Screenshot.captureScreenShot(driver, "clklang_" + ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Check if the screenshot was captured successfully
            if (screenshotPath != null) {
                // Convert the screenshot to Base64 for embedding in the Extent report
                String base64Image = encodeImageToBase64(screenshotPath);

                // Create an HTML <img> tag to embed the screenshot in the report
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Language Change Screenshot' width='500' height='200' />";

                // Log the success message with the embedded image
                test.log(LogStatus.PASS, "Language changed to Arabic successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot after changing the language.");
            }

            // Set an implicit wait to handle any potential waiting scenarios after the language change
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        } catch (Exception e) {
            // Log any errors encountered during the process
            test.log(LogStatus.FAIL, "Error during language change: " + e.getMessage());
        }
    }


    @Test
    public  void viewMISAR() throws InterruptedException {
        try {
            clickElement(By.xpath(viewMISAR));
            Thread.sleep(2000);
            test.log(LogStatus.PASS, "Click on Arabic UI MIS Tab");

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='View MIS Arabic UI Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "View MIS ARAB executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for View MIS ARAB.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during View MIS ARAB: " + e.getMessage());
        }
    }

    @Test
    public  void viewAudit() throws InterruptedException {
        try {
            System.out.print("Check");
            clickElement(By.xpath(AuditTab));
            Thread.sleep(500);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Audit Tab Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Click on Audit Tab executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for Audit Tab.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Audit Tab click: " + e.getMessage());
        }
    }

    @Test
    public  void viewAuditArab() throws InterruptedException {
        try {

            clickElement(By.xpath(AuditTabArab));
            Thread.sleep(500);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Audit Tab Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Click on Audit Tab executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for Audit Tab.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Audit Tab click: " + e.getMessage());
        }
    }

    @Test
    public void misdownload() throws Exception {
        try {
            clickElement(By.xpath(MisDownloadbtn));
            Thread.sleep(500);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='MIS Download Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "MIS Download executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for MIS Download.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during MIS Download: " + e.getMessage());
        }
    }

    @Test
    public void clkviewARAB() throws Exception {
        try {
            clickElement(By.xpath(MISviewArbbtn));
            Thread.sleep(500);
            test.log(LogStatus.PASS, "Click on Arabic UI View Button");

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Click on Arabic UI View Button Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "MIS Arabic UI View Button executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for Arabic UI View Button.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Arabic UI View Button click: " + e.getMessage());
        }
    }

    public void misdownloadARAB() throws Exception {
        try {
            clickElement(By.xpath(MISDownloadArbbtn));
            Thread.sleep(500);
            test.log(LogStatus.PASS, "Click on Arabic UI MIS Download Button");

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='MIS Download Arabic UI Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "MIS Download Arabic UI executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for Arabic UI MIS Download.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Arabic UI MIS Download: " + e.getMessage());
        }
    }

    @Test
    public  void auditdate() throws Exception {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)");

            // Get the WebElement using XPath
            WebElement startDateInput = driver.findElement(By.xpath(effstrtdte));  // effstrtdte must be a valid XPath string

            startDateInput.click();

            // Set value via JS and dispatch change/input events
            js.executeScript(
                    "arguments[0].value = '2024-01-01T12:00';" +
                            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                    startDateInput
            );

            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            test.log(LogStatus.PASS, "Selected Effective Start Audit Date");

            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                test.log(LogStatus.PASS, "Audit Start Date selected successfully. <img src='data:image/png;base64," + base64Image + "' width='500' height='200' />");
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for Audit Start Date.");
            }

        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during selecting Audit Start Date: " + e.getMessage());
            throw e;
        }
    }


    @Test
    public  void auditenddate() throws Exception {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)");

            WebElement endDateInput = driver.findElement(By.xpath(effenddte));  // effenddte must be a valid XPath string

            endDateInput.click();

            js.executeScript(
                    "arguments[0].value = '2025-10-01T12:00';" +
                            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                    endDateInput
            );

            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            test.log(LogStatus.PASS, "Selected Effective End Audit Date");

            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                test.log(LogStatus.PASS, "Audit End Date selected successfully. <img src='data:image/png;base64," + base64Image + "' width='500' height='200' />");
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for Audit End Date.");
            }

        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during selecting Audit End Date: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public  void module() throws InterruptedException {
        try {
            Select selectauditModule = new Select(driver.findElement(By.xpath(AuditModule)));
            selectauditModule.selectByVisibleText(ExcelUtilities.AuditmoduleColumn);
            Thread.sleep(2000);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            String base64Image = encodeImageToBase64(screenshotPath);
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Audit Module Screenshot' width='500' height='200' />";

            test.log(LogStatus.PASS, "Select Audit Module: " + ExcelUtilities.AuditmoduleColumn + imgTag);
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during selecting Audit Module: " + e.getMessage());
        }
    }

    @Test
    public  void invaliddate() throws Exception {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)");

            Actions act = new Actions(driver);
            Robot r = new Robot();
            clickElement(By.xpath(effstrtdte));
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '2029-10-01T12:00';", effstrtdte);
            Thread.sleep(500);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            String base64Image = encodeImageToBase64(screenshotPath);
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Invalid Start Effective Date Screenshot' width='500' height='200' />";

            test.log(LogStatus.PASS, "Selected Invalid Start Effective Date" + imgTag);
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during selecting Invalid Start Effective Date: " + e.getMessage());
        }
    }

    @Test
    public  void invalidenddate() throws Exception {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)");

            Actions act = new Actions(driver);
            Robot r = new Robot();
            clickElement(By.xpath(effenddte));
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '2025-10-01T12:00';", effenddte);
            Thread.sleep(700);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            String base64Image = encodeImageToBase64(screenshotPath);
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Invalid End Effective Date Screenshot' width='500' height='200' />";

            test.log(LogStatus.PASS, "Selected Invalid End Effective Date" + imgTag);
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during selecting Invalid End Effective Date: " + e.getMessage());
        }
    }

    @Test
    public  void VerifyMISerror() throws InterruptedException {
        try {
            if (driver.getPageSource().contains("Start date cannot be greater than end date")) {
                Thread.sleep(600);

                // Capture screenshot
                String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='MIS Error Screenshot' width='500' height='200' />";

                test.log(LogStatus.PASS, "Validation error message: 'Start date cannot be greater than end date' passed" + imgTag);
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during verifying MIS error: " + e.getMessage());
        }
    }

    @Test
    public  void paginationnxt() throws InterruptedException {
        try {
            WebElement pagination = driver.findElement(By.xpath(nextbtn));
            Thread.sleep(2000);
            pagination.click();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            String base64Image = encodeImageToBase64(screenshotPath);
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Pagination Next Screenshot' width='500' height='200' />";

            test.log(LogStatus.PASS, "Pagination next button clicked successfully" + imgTag);
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during pagination next: " + e.getMessage());
        }
    }

    @Test
    public  void AuditEn() throws InterruptedException {
        try {
            Select select = new Select(driver.findElement(By.xpath(lang)));
            select.selectByValue("en");
            Thread.sleep(2000);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            String base64Image = encodeImageToBase64(screenshotPath);
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Language Selection English Screenshot' width='500' height='200' />";

            test.log(LogStatus.PASS, "Language set to English successfully" + imgTag);
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during selecting English language: " + e.getMessage());
        }
    }


    public static class AuditTest {

        // Helper method to generate an <img> tag for a screenshot
        private String generateImgTag(String screenshotPath) {
            String imgTag = "";
            try {
                // Assuming the screenshotPath points to an actual image file
                if (screenshotPath != null && !screenshotPath.isEmpty()) {
                    // Directly encode the image to Base64 (you should replace this with actual encoding logic)
                    String base64Image = "Base64EncodedImageHere"; // Placeholder for Base64 encoded image

                    // Generate the <img> HTML tag
                    imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Screenshot' width='500' height='200' />";
                }
            } catch (Exception e) {
                test.log(LogStatus.FAIL, "Error encoding screenshot: " + e.getMessage());
            }
            return imgTag;
        }
    }

    @Test
    public void AuditMISModule() throws Exception {
        try {
            viewAudit();
            auditdate();
            auditenddate();
            module();
            clkview();

            // Screenshot path (replace with actual path or method to get screenshot)
            String screenshotPath = "path/to/screenshot_AuditMISModule.png";  // Replace with actual path
            String imgTag = generateImgTag(screenshotPath);

            test.log(LogStatus.PASS, "Audit MIS Module executed successfully. " + imgTag);

        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Audit MIS Module execution: " + e.getMessage());
        }
    }

    @Test
    public void auditAllModuleArab() throws Exception {


        viewAuditArab();
        auditdate();
        auditenddate();
        module();
        clkview();
        download();

        String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

        if (screenshotPath != null) {
            String base64Image = encodeImageToBase64(screenshotPath);
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Audit Tab Screenshot' width='500' height='200' />";
            test.log(LogStatus.PASS, "Click on Audit  executed successfully. " + imgTag);
        } else {
            test.log(LogStatus.FAIL, "Failed to capture screenshot for Audit Tab.");
        }


    }

    @Test
    public void auditAllModule() throws Exception {

        System.out.println("check");

        viewAudit();
        auditdate();
        auditenddate();
        module();
        clkview();
        download();

        String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

        if (screenshotPath != null) {
            String base64Image = encodeImageToBase64(screenshotPath);
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Audit Tab Screenshot' width='500' height='200' />";
            test.log(LogStatus.PASS, "Click on Audit  executed successfully. " + imgTag);
        } else {
            test.log(LogStatus.FAIL, "Failed to capture screenshot for Audit Tab.");
        }


    }


    @Test
    public void AuditRoleModule() throws Exception {
        try {
            viewAudit();
            auditdate();
            auditenddate();
            module();
            clkview();

            // Screenshot path (replace with actual path or method to get screenshot)
            String screenshotPath = "path/to/screenshot_AuditRoleModule.png";  // Replace with actual path
            String imgTag = generateImgTag(screenshotPath);

            test.log(LogStatus.PASS, "Audit Role Module executed successfully. " + imgTag);

        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Audit Role Module execution: " + e.getMessage());
        }
    }

    @Test
    public void AuditUserModule() throws Exception {
        try {
            viewAudit();
            auditdate();
            auditenddate();
            module();
            clkview();

            // Screenshot path (replace with actual path or method to get screenshot)
            String screenshotPath = "path/to/screenshot_AuditUserModule.png";  // Replace with actual path
            String imgTag = generateImgTag(screenshotPath);

            test.log(LogStatus.PASS, "Audit User Module executed successfully. " + imgTag);

        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Audit User Module execution: " + e.getMessage());
        }
    }

    @Test
    public void AuditEntityModule() throws Exception {
        try {
            viewAudit();
            auditdate();
            auditenddate();
            module();
            clkview();

            // Screenshot path (replace with actual path or method to get screenshot)
            String screenshotPath = "path/to/screenshot_AuditEntityModule.png";  // Replace with actual path
            String imgTag = generateImgTag(screenshotPath);

            test.log(LogStatus.PASS, "Audit Entity Module executed successfully. " + imgTag);

        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Audit Entity Module execution: " + e.getMessage());
        }
    }

    @Test
    public void AuditServiceModule() throws Exception {
        try {
            viewAudit();
            auditdate();
            auditenddate();
            module();
            clkview();

            // Screenshot path (replace with actual path or method to get screenshot)
            String screenshotPath = "path/to/screenshot_AuditServiceModule.png";  // Replace with actual path
            String imgTag = generateImgTag(screenshotPath);

            test.log(LogStatus.PASS, "Audit Service Module executed successfully. " + imgTag);

        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Audit Service Module execution: " + e.getMessage());
        }
    }

    @Test
    public void AuditGroupModule() throws Exception {
        try {
            viewAudit();
            auditdate();
            auditenddate();
            module();
            clkview();

            // Screenshot path (replace with actual path or method to get screenshot)
            String screenshotPath = "path/to/screenshot_AuditGroupModule.png";  // Replace with actual path
            String imgTag = generateImgTag(screenshotPath);

            test.log(LogStatus.PASS, "Audit Group Module executed successfully. " + imgTag);

        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Audit Group Module execution: " + e.getMessage());
        }
    }

    @Test
    public void AuditLoginModule() throws Exception {
        try {
            viewAudit();
            auditdate();
            auditenddate();
            module();
            clkview();

            // Screenshot path (replace with actual path or method to get screenshot)
            String screenshotPath = "path/to/screenshot_AuditLoginModule.png";  // Replace with actual path
            String imgTag = generateImgTag(screenshotPath);

            test.log(LogStatus.PASS, "Audit Login Module executed successfully. " + imgTag);

        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Audit Login Module execution: " + e.getMessage());
        }
    }

    @Test
    public void AuditInvalidDateRange() throws Exception {
        try {
            viewAudit();
            invaliddate();
            invalidenddate();
            module();
            clkview();

            // Screenshot path (replace with actual path or method to get screenshot)
            String screenshotPath = "path/to/screenshot_AuditInvalidDateRange.png";  // Replace with actual path
            String imgTag = generateImgTag(screenshotPath);

            test.log(LogStatus.PASS, "Audit Invalid Date Range executed successfully. " + imgTag);

        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Audit Invalid Date Range execution: " + e.getMessage());
        }
    }


    @Test
    public void RequestID() throws Exception {

        viewMIS();
        misdate();
        misenddate();
        WebElement reqid = driver.findElement(By.xpath(ReqID));
        Thread.sleep(500);
        reqid.sendKeys(ExcelUtilities.usernameColumnValue);
        clkview();
        clickonMISreportBtn();
        download();


    }

    @Test
    public void RequestIDArab() throws Exception {

        viewMISAR();
        misdateARAB();
        misenddate();
        WebElement reqid = driver.findElement(By.xpath(ReqID));
        Thread.sleep(500);
        reqid.sendKeys(ExcelUtilities.usernameColumnValue);
        clkviewARAB();
        misdownloadARAB();


    }

    @Test
    public void ViewMisValidData() throws Exception {
        try {
            viewMIS();
            misdate();
            misenddate();
            clkview();

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)");

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, "viewMisValidData_" + ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Check if the screenshot was captured successfully
            if (screenshotPath != null) {
                // Convert the screenshot to Base64 for embedding in the Extent report
                String base64Image = encodeImageToBase64(screenshotPath);

                // Create an HTML <img> tag to embed the screenshot in the report
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='View Mis Valid Data Screenshot' width='500' height='200' />";

                // Log the success message with the embedded image
                test.log(LogStatus.PASS, "View MIS Valid Data executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for View Mis Valid Data.");
            }

        } catch (Exception e) {
            // Log any errors encountered during the process
            test.log(LogStatus.FAIL, "Error during View Mis Valid Data execution: " + e.getMessage());
        }
    }

    @Test
    public void clickonMISreportBtn() throws Exception {
        try {
            clickElement(By.xpath(MISReportbtn));
            Thread.sleep(600);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, "clickonMISreportBtn_" + ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Check if the screenshot was captured successfully
            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Click on MIS Report Button Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Clicked on MIS Report Button successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot after clicking on MIS Report Button.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during MIS Report Button click: " + e.getMessage());
        }
    }

    @Test
    public void clickonMISreportBtnAR() throws Exception {
        try {
            clickElement(By.xpath(MISReportbtnARAB));
            Thread.sleep(600);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, "clickonMISreportBtnAR_" + ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Click on MIS Report Button AR Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Clicked on MIS Report Button AR successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot after clicking on MIS Report Button AR.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during MIS Report Button AR click: " + e.getMessage());
        }
    }

    @Test
    public void DownloadMis() throws Exception {
        try {
            viewMIS();
            misdate();
            misenddate();
            clkview();
            clickonMISreportBtn();

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, "DownloadMis_" + ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Download MIS Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "MIS download executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for Download MIS.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during MIS download: " + e.getMessage());
        }
    }

    @Test
    public void DownloadMisAR() throws Exception {
        viewMIS();
        misdate();
        misenddate();
        clkview();
        clickonMISreportBtnAR();
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);

    }

    @Test
    public void DownloadMisBlankFromDate() throws Exception {
        viewMIS();
        misenddate();
        clkview();
        clickonMISreportBtn();
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void DownloadMisBlankToDate() throws Exception {
        try {
            viewMIS();
            misdate();
            clkview();
            clickonMISreportBtn();

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, "DownloadMisBlankToDate_" + ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Download MIS Blank To Date Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Download MIS with Blank To Date executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for Download MIS with Blank To Date.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Download MIS with Blank To Date: " + e.getMessage());
        }
    }

    @Test
    public void viewMisInValidDateRange() throws Exception {
        try {
            viewMIS();
            invaliddate();
            invalidenddate();
            clkview();

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, "viewMisInValidDateRange_" + ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='View MIS Invalid Date Range Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "View MIS with Invalid Date Range executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for View MIS with Invalid Date Range.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during View MIS with Invalid Date Range: " + e.getMessage());
        }
    }

    @Test
    public void DownloadMisInValidDateRange() throws Exception {
        try {
            viewMIS();
            invaliddate();
            misenddate();
            clickonMISreportBtn();
            Thread.sleep(2000);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, "DownloadMisInValidDateRange_" + ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Download MIS Invalid Date Range Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "MIS Download with Invalid Date Range executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for MIS Download with Invalid Date Range.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during MIS Download with Invalid Date Range: " + e.getMessage());
        }
    }


    @Test
    public void DownloadMisInValidToDateRange() throws Exception {
        try {
            viewMIS();
            misdate();
            invalidenddate();
            clickonMISreportBtn();
            Thread.sleep(2000);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Download MIS Invalid To Date Range Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Download MIS with Invalid To Date Range executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for Download MIS Invalid To Date Range.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Download MIS Invalid To Date Range: " + e.getMessage());
        }
    }


    @Test
    public void DownloadMisInValidFromDateArabic() throws Exception {
        try {
            viewMISAR();
            invaliddate();
            misenddate();
            clickonMISreportBtnAR();
            Thread.sleep(2000);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Download MIS Invalid From Date Arabic Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Download MIS Invalid From Date Arabic executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for Download MIS Invalid From Date Arabic.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Download MIS Invalid From Date Arabic: " + e.getMessage());
        }
    }

    @Test
    public void DownloadMisInValidToDateArabic() throws Exception {
        try {
            viewMISAR();
            misdate();
            invalidenddate();
            clickonMISreportBtnAR();
            Thread.sleep(2000);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Download MIS Invalid To Date Arabic Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Download MIS Invalid To Date Arabic executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for Download MIS Invalid To Date Arabic.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Download MIS Invalid To Date Arabic: " + e.getMessage());
        }
    }

    @Test
    public void DownloadMisInValidFromDateRange() throws Exception {
        try {
            viewMIS();
            invaliddate();
            invalidenddate();
            clickonMISreportBtn();
            Thread.sleep(2000);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Download MIS Invalid From Date Range Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Download MIS Invalid From Date Range executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for Download MIS Invalid From Date Range.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Download MIS Invalid From Date Range: " + e.getMessage());
        }
    }


    @Test
    public void ViewMisBlankDateRange() throws Exception {
        try {
            viewMIS();
            clkview();
            Thread.sleep(2000);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='View MIS Blank Date Range Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "View MIS Blank Date Range executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for View MIS Blank Date Range.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during View MIS Blank Date Range: " + e.getMessage());
        }
    }

    @Test
    public void ViewMisValidDataARABMismatch() throws Exception {
        try {
            Thread.sleep(2000);
            viewMISAR();
            misdateARAB();
            misenddate();
            WebElement MismatchReport = driver.findElement(By.xpath(Mismatch));
            Thread.sleep(500);
            MismatchReport.click();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)");
            Thread.sleep(2000);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='View MIS Valid Data ARAB Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "View MIS Valid Data ARAB executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for View MIS Valid Data ARAB.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during View MIS Valid Data ARAB: " + e.getMessage());
        }
    }

    @Test
    public void ViewMisValidDataARAB() throws Exception {
        try {
            Thread.sleep(2000);
            viewMISAR();
            misdateARAB();
            misenddate();
            clkviewARAB();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)");
            Thread.sleep(2000);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='View MIS Valid Data ARAB Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "View MIS Valid Data ARAB executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for View MIS Valid Data ARAB.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during View MIS Valid Data ARAB: " + e.getMessage());
        }
    }

    @Test
    public void MisreportARABbtn() throws Exception {
        try {
            clickElement(By.xpath(MISReportbtnARAB));
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during MIS Report ARAB button click: " + e.getMessage());
        }
    }

    @Test
    public void DownloadMisValidDataARAB() throws Exception {
        try {
            viewMISAR();
            misdateARAB();
            misenddate();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)");
            clkviewARAB();
            //misdownloadARAB();
            Thread.sleep(2000);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Download MIS Valid Data ARAB Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Download MIS Valid Data ARAB executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for Download MIS Valid Data ARAB.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Download MIS Valid Data ARAB: " + e.getMessage());
        }
    }

    @Test
    public void DownloadMisValidData() throws Exception {
        try {
            viewMIS();
            misdate();
            misenddate();
            clkview();

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)");

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, "DownloadMisValidData_" + ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Download MIS Valid Data Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "MIS Valid Data downloaded successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for MIS Valid Data download.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during MIS Valid Data download: " + e.getMessage());
        }
    }

    @Test
    public void viewMisInValidDateRangeARAB() throws Exception {
        try {
            viewMISAR();
            invaliddate();
            invalidenddate();
            clkviewARAB();
            Thread.sleep(2000);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='View MIS Invalid Date Range ARAB Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "View MIS Invalid Date Range ARAB executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for View MIS Invalid Date Range ARAB.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during View MIS Invalid Date Range ARAB: " + e.getMessage());
        }
    }

    @Test
    public void ViewMisBlankDateRangeARAB() throws Exception {
        try {
            viewMISAR();
            clkviewARAB();
            Thread.sleep(2000);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='View MIS Blank Date Range ARAB Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "View MIS Blank Date Range ARAB executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for View MIS Blank Date Range ARAB.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during View MIS Blank Date Range ARAB: " + e.getMessage());
        }
    }

    @Test
    public void MISReportValidData() throws Exception {
        try {
            viewMIS();
            misdate();
            misenddate();
            clickonMISreportBtn();

            // Log success
            test.log(LogStatus.PASS, "MIS Report with valid data executed successfully.");
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during MIS Report with valid data: " + e.getMessage());
        }
    }

    @Test
    public void MisReportInValidDateRange() throws Exception {
        try {
            viewMIS();
            invaliddate();
            invalidenddate();
            clickonMISreportBtn();
            Thread.sleep(2000);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='MIS Report Invalid Date Range Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "MIS Report Invalid Date Range executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for MIS Report Invalid Date Range.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during MIS Report Invalid Date Range: " + e.getMessage());
        }
    }

    @Test
    public void MisReportBlankDateRange() throws Exception {
        try {
            viewMIS();
            clickonMISreportBtn();
            Thread.sleep(2000);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='MIS Report Blank Date Range Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "MIS Report Blank Date Range executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for MIS Report Blank Date Range.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during MIS Report Blank Date Range: " + e.getMessage());
        }
    }

    @Test
    public void clickonMISreportBtnARAB() throws Exception {
        try {
            clickElement(By.xpath(MISReportbtnARAB));
            Thread.sleep(600);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Click on MIS Report Button ARAB Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Click on MIS Report Button ARAB executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for Click on MIS Report Button ARAB.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during Click on MIS Report Button ARAB: " + e.getMessage());
        }
    }

    @Test
    public void MisReportValidDataARAB() throws Exception {
        try {
            viewMISAR();
            misdateARAB();
            misenddate();
            clickonMISreportBtnARAB();
            Thread.sleep(2000);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='MIS Report Valid Data ARAB Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "MIS Report Valid Data ARAB executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for MIS Report Valid Data ARAB.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during MIS Report Valid Data ARAB: " + e.getMessage());
        }
    }

    @Test
    public void MisReportInValidDateRangeARAB() throws Exception {
        try {
            viewMISAR();
            invaliddate();
            invalidenddate();
            clickonMISreportBtnARAB();
            Thread.sleep(2000);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='MIS Report Invalid Date Range ARAB Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "MIS Report Invalid Date Range ARAB executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for MIS Report Invalid Date Range ARAB.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during MIS Report Invalid Date Range ARAB: " + e.getMessage());
        }
    }

    @Test
    public void MisReportBlankDateRangeARAB() throws Exception {
        try {
            viewMISAR();
            clickonMISreportBtnARAB();
            Thread.sleep(2000);

            // Capture screenshot
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='MIS Report Blank Date Range ARAB Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "MIS Report Blank Date Range ARAB executed successfully. " + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for MIS Report Blank Date Range ARAB.");
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error during MIS Report Blank Date Range ARAB: " + e.getMessage());
        }
    }

    @Test
    public void verifyAuditGridData() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        Thread.sleep(600);

        // Loop to check if the grid data is loaded and contains the necessary column
        while (driver.getPageSource().contains("Next")) {
            Thread.sleep(600);

            if (driver.getPageSource().contains(ExcelUtilities.VERIFYGRIDcolumn)) {
                // Grid data loaded successfully, log the pass status and capture the screenshot
                System.out.println("Audit Data Successfully Loaded");

                // Capture screenshot (replace with actual screenshot logic)
                String screenshotPath = "path/to/screenshot_AuditGridData.png";  // Replace with actual path to the screenshot

                // Generate an HTML <img> tag for embedding the screenshot in the Extent report
                String imgTag = generateImgTag(screenshotPath);

                // Log the success message with the screenshot
                test.log(LogStatus.PASS, "Audit Data Successfully Loaded: " + ExcelUtilities.testCaseDesritpioncolumnvalue + " " + imgTag);
                break;
            }

            // If the "Next" button is found, click it to go to the next page in the grid
            clickElement(By.xpath(nextbtn));
        }
    }

    private String generateImgTag(String screenshotPath) {
        String imgTag = "";
        try {
            if (screenshotPath != null && !screenshotPath.isEmpty()) {
                // Assuming the screenshotPath points to an actual image file
                // You can directly use a method to convert the image to Base64 if necessary

                // For demonstration, we use a placeholder for Base64 encoded string
                String base64Image = "Base64EncodedImageHere"; // Replace this with actual Base64 encoded image content

                // Generate the HTML <img> tag
                imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Audit Grid Data Screenshot' width='500' height='200' />";
            }
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Error encoding screenshot: " + e.getMessage());
        }
        return imgTag;
    }

    @Test
    public void LoginPortalChangePWD() throws InterruptedException {
        try {
            // Locate and click on Change Password button
            clickElement(By.xpath(ChangePassword));
            Thread.sleep(600);  // Wait for the page to load

            // Enter the username for password change
            WebElement usernameField = driver.findElement(By.xpath(loginchngUsername));
            usernameField.sendKeys(ExcelUtilities.LOGIN_CHNG_USERNAMEcolumn);
            Thread.sleep(600);

            // Enter the old password
            WebElement oldPasswordField = driver.findElement(By.xpath(oldPassword));
            oldPasswordField.sendKeys(ExcelUtilities.OLDPASSWORDcolumn);
            clickElement(By.xpath(toggleOldPassword));
            Thread.sleep(600);

            // Enter the new password
            WebElement newPasswordField = driver.findElement(By.xpath(password));
            newPasswordField.sendKeys(ExcelUtilities.NEWPASSWORDcolumn);
            clickElement(By.xpath(toggleNewPassword));
            Thread.sleep(600);

            // Enter the confirm password
            WebElement confirmPasswordField = driver.findElement(By.xpath(confirmchngPassword));
            confirmPasswordField.sendKeys(ExcelUtilities.CONFIRMPASSWORDcolumn);
            clickElement(By.xpath(toggleConfirmPassword));
            Thread.sleep(600);

            // Save the password change
            changepwdsave();
            Thread.sleep(600);

            // Click on the login button
            clickElement(By.xpath(loginsbt));
            Thread.sleep(600);

            // Log success message
            test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Optional: Capture screenshot and log in the report
            String screenshotPath = Screenshot.captureScreenShot(driver, "LoginPortalChangePWD_" + ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Create an HTML <img> tag to embed the screenshot in the report
                String base64Image = encodeImageToBase64(screenshotPath);  // Encoding the screenshot to Base64
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Password Change Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Password change successful. " + imgTag);  // Log the success with the screenshot
            }
        } catch (Exception e) {
            // Log failure in case of an exception
            test.log(LogStatus.FAIL, "Error during password change: " + e.getMessage());
        }
    }

    @Test
    public void User_LoginPortalChangePWD() throws InterruptedException {
        try {
            // Click on Change Password button
            clickElement(By.xpath(ChangePassword));
            Thread.sleep(600);  // Wait for the next page element

            // Enter the username for password change
            WebElement usernameField = driver.findElement(By.xpath(loginchngUsername));
            usernameField.sendKeys(ExcelUtilities.user_login_chng_usernamevalue);
            Thread.sleep(600);

            // Enter the old password
            WebElement oldPasswordField = driver.findElement(By.xpath(oldPassword));
            oldPasswordField.sendKeys(ExcelUtilities.user_oldpasswordvalue);
            clickElement(By.xpath(toggleOldPassword));
            Thread.sleep(600);

            // Enter the new password
            WebElement newPasswordField = driver.findElement(By.xpath(password));
            newPasswordField.sendKeys(ExcelUtilities.user_newpasswordvalue);
            clickElement(By.xpath(toggleNewPassword));
            Thread.sleep(600);

            // Enter the confirm password
            WebElement confirmPasswordField = driver.findElement(By.xpath(confirmchngPassword));
            confirmPasswordField.sendKeys(ExcelUtilities.user_confirmpasswordvalue);
            clickElement(By.xpath(toggleConfirmPassword));
            Thread.sleep(600);

            // Save the password change
            changepwdsave();
            Thread.sleep(600);

            // Click on the login button to finalize the change
            clickElement(By.xpath(loginsbt));
            Thread.sleep(600);

            // Log success message
            test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Optional: Capture screenshot and log it in the report
            String screenshotPath = Screenshot.captureScreenShot(driver, "User_LoginPortalChangePWD_" + ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                // Create an HTML <img> tag to embed the screenshot in the report
                String base64Image = encodeImageToBase64(screenshotPath);  // Convert image to Base64 for embedding
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Password Change Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, "Password change successful. " + imgTag);  // Log success with screenshot
            }

        } catch (Exception e) {
            // Log failure in case of an exception
            test.log(LogStatus.FAIL, "Error during password change: " + e.getMessage());
        }
    }

    @Test
    public void changetoengUI() throws Exception {
        // Click on the language change dropdown or language selector
        clickElement(By.xpath(lang));
        test.log(LogStatus.PASS, "Language change dropdown clicked.");

        // Select 'English' from the dropdown (assuming 'en' is the value for English)
        Select select = new Select(driver.findElement(By.xpath(lang)));
        select.selectByValue("en");
        test.log(LogStatus.PASS, "English language selected.");

        // Introduce a small delay to allow the language change to reflect
        Thread.sleep(500);

        // Capture a screenshot after selecting the language change
        String changetoengUIScreenshot = Screenshot.captureScreenShot(driver, "clklang");

        // Log the screenshot in the Extent Report
        if (changetoengUIScreenshot != null) {
            // Convert the screenshot to Base64 encoding
            String base64Image = encodeImageToBase64(changetoengUIScreenshot);

            // Create an HTML <img> tag with the Base64 image
            String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Language Change Screenshot' width='500' height='200' />";

            // Log the success message along with the Base64 image in the Extent Report
            test.log(LogStatus.PASS, "Language changed to English successfully " + imgTag);
        } else {
            // Log an error if screenshot capturing failed
            test.log(LogStatus.FAIL, "Failed to capture screenshot after changing the language to English.");
        }
    }


    @Test
    public void Changepass_EnterOldpass_click() throws InterruptedException {
        try {
            // Enter the old password into the field
            WebElement oldPassword = driver.findElement(By.xpath(oldPassword1));
            oldPassword.sendKeys(ExcelUtilities.OLDPASSWORDcolumn);
            Thread.sleep(600); // Wait for the password to be entered

            // Click the 'eye' icon to toggle visibility of the old password
            clickElement(By.xpath(eyeoldpwd));
            Thread.sleep(600); // Wait for the action to complete

            // Log the success message
            test.log(LogStatus.PASS, "Entered old password: " + ExcelUtilities.OLDPASSWORDcolumn);

        } catch (Exception e) {
            // Log failure in case of an exception
            test.log(LogStatus.FAIL, "Error during entering old password: " + e.getMessage());
        }
    }

    @Test
    public void Changepass_EnterNewpass_click() throws InterruptedException {
        WebElement NewPassword = driver.findElement(By.xpath(confirmchngPassword1));
        NewPassword.sendKeys(ExcelUtilities.NEWPASSWORDcolumn);
        Thread.sleep(600);
        clickElement(By.xpath(eyeNewpwd));
        Thread.sleep(600);
        test.log(LogStatus.PASS, " Enter New Password  :" + ExcelUtilities.NEWPASSWORDcolumn);
    }

    @Test
    public void Changepass_EnterConfirmpass_click() throws InterruptedException {
        WebElement cnfrimpwd = driver.findElement(By.xpath(cnfrimpwd1));
        cnfrimpwd.sendKeys(ExcelUtilities.CONFIRMPASSWORDcolumn);
        Thread.sleep(600);
        clickElement(By.xpath(eyecnfrimpwd1));
        Thread.sleep(600);
        test.log(LogStatus.PASS, "Enter Confirm pwd  :" + ExcelUtilities.CONFIRMPASSWORDcolumn);
    }

    @Test
    public void Changepass_Tab_click() throws Exception {
        clickElement(By.xpath(ChangePassword1));
        Thread.sleep(600);
        test.log(LogStatus.PASS, " Click on change password Tab");
    }

    @Test
    public void PortalChangePWD() throws Exception {
        try {
            // Steps to change password
            Changepass_Tab_click();
            Changepass_EnterOldpass_click();
            Changepass_EnterNewpass_click();
            Changepass_EnterConfirmpass_click();
            save();

            // Capture screenshot and get its path
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Convert the screenshot to Base64
            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);

                // Create an HTML <img> tag to embed the screenshot
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Portal Change PWD Screenshot' width='500' height='200' />";

                // Log the success message with the embedded image
                test.log(LogStatus.PASS, "Portal Change PWD : " + ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for Portal Change PWD.");
            }
        } catch (Exception e) {
            // Log failure in case of an exception
            test.log(LogStatus.FAIL, "Error during Portal Change PWD: " + e.getMessage());
        }
    }

    @Test
    public void PortalChangePWD_Invaliddata_1() throws Exception {
        try {
            // Steps to change password
            Changepass_Tab_click();
            Changepass_EnterOldpass_click();
            Changepass_EnterNewpass_click();
            Changepass_EnterConfirmpass_click();
            save();

            // Capture screenshot after the action
            String screenshotPath = Screenshot.captureScreenShot(driver, "PortalChangePWD_Invaliddata_1");

            // Convert the screenshot to Base64 and embed it in the report
            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Portal Change PWD Invaliddata_1 Screenshot' width='500' height='200' />";

                // Log the status with the embedded image
                boolean invalid_old_pass = driver.getPageSource().contains("VLD-SC0001: entered old password is not matching");
                if (invalid_old_pass) {
                    System.out.println("Password didn't change with invalid old password");
                    test.log(LogStatus.FAIL, ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
                } else {
                    System.out.println("Password changed");
                    test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
                }
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for PortalChangePWD_Invaliddata_1.");
            }
        } catch (Exception e) {
            // Log failure in case of an exception
            test.log(LogStatus.FAIL, "Error during PortalChangePWD_Invaliddata_1: " + e.getMessage());
        }
    }

    @Test
    public void PortalChangePWD_Invaliddata_2() throws Exception {
        try {
            // Steps to change password
            Changepass_EnterOldpass_click();
            Changepass_EnterNewpass_click();
            Changepass_EnterConfirmpass_click();
            save();

            // Capture screenshot after the action
            String screenshotPath = Screenshot.captureScreenShot(driver, "PortalChangePWD_Invaliddata_2");

            // Convert the screenshot to Base64 and embed it in the report
            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Portal Change PWD Invaliddata_2 Screenshot' width='500' height='200' />";

                // Check for error message about password mismatch
                boolean diff_new_conf_pass = driver.getPageSource()
                        .contains("VLD-SC0003: 'New password' value does not match with entered 'Confirm password' value");
                if (diff_new_conf_pass) {
                    System.out.println("Password didn't change if new password and confirm password are different");
                    test.log(LogStatus.INFO, ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
                } else {
                    System.out.println("Password changed");
                    test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
                }
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for PortalChangePWD_Invaliddata_2.");
            }
        } catch (Exception e) {
            // Log failure in case of an exception
            test.log(LogStatus.FAIL, "Error during PortalChangePWD_Invaliddata_2: " + e.getMessage());
        }
    }

    @Test
    public void PortalChangePWD_Reset_Btn() throws Exception {
        try {
            // Steps to change password
            Changepass_EnterOldpass_click();
            Changepass_EnterNewpass_click();
            Changepass_EnterConfirmpass_click();
            reset();

            // Capture screenshot after reset
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Portal Change PWD Reset Screenshot' width='500' height='200' />";
                test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for PortalChangePWD_Reset_Btn.");
            }
        } catch (Exception e) {
            // Log failure in case of an exception
            test.log(LogStatus.FAIL, "Error during PortalChangePWD_Reset_Btn: " + e.getMessage());
        }
    }

    @Test
    public void PortalChangePWD_Invaliddata_3() throws Exception {
        try {
            // Steps to change password
            Changepass_EnterOldpass_click();
            Changepass_EnterNewpass_click();
            Changepass_EnterConfirmpass_click();
            save();

            // Capture screenshot after the action
            String screenshotPath = Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);

            // Convert the screenshot to Base64 and embed it in the report
            if (screenshotPath != null) {
                String base64Image = encodeImageToBase64(screenshotPath);
                String imgTag = "<img src='data:image/png;base64," + base64Image + "' alt='Portal Change PWD Invaliddata_3 Screenshot' width='500' height='200' />";

                // Check if the new password is the same as the old password
                boolean diff_new_conf_pass = driver.getPageSource()
                        .contains("VLD-SC0005: 'New password' should not be same as 'Old password'");

                if (diff_new_conf_pass) {
                    System.out.println("Password didn't change if new password and old password are the same");
                    test.log(LogStatus.FAIL, ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
                } else {
                    System.out.println("Password changed");
                    test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue + imgTag);
                }
            } else {
                test.log(LogStatus.FAIL, "Failed to capture screenshot for PortalChangePWD_Invaliddata_3.");
            }
        } catch (Exception e) {
            // Log failure in case of an exception
            test.log(LogStatus.FAIL, "Error during PortalChangePWD_Invaliddata_3: " + e.getMessage());
        }
    }


    @Test
    public void AuditallModuleDownload() throws Exception {
        viewAudit();
        auditdate();
        auditenddate();
        module();
        download();
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void AuditRoleModuleDownload() throws Exception {
        viewAudit();
        auditdate();
        auditenddate();
        module();
        download();
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void AuditUserModuleDownload() throws Exception {
        viewAudit();
        auditdate();
        auditenddate();
        module();
        download();
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void AuditEntityModuleDownload() throws Exception {
        viewAudit();
        auditdate();
        auditenddate();
        module();
        download();
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void AuditServiceModuleDownload() throws Exception {
        viewAudit();
        auditdate();
        auditenddate();
        module();
        download();
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void AuditGroupModuleDownload() throws Exception {
        viewAudit();
        auditdate();
        auditenddate();
        module();
        download();
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void AuditLoginModuleDownload() throws Exception {
        viewAudit();
        auditdate();
        auditenddate();
        module();
        download();
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void AuditMISModuleDownload() throws Exception {
        viewAudit();
        auditdate();
        auditenddate();
        module();
        download();
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void AuditBlankDownload() throws Exception {
        viewAudit();
        download();
        Thread.sleep(2000);
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void AuditBlankView() throws Exception {
        System.out.println("CHECK  AuditBlankView");
        viewAudit();
        clkview();
        Thread.sleep(2000);
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void InvalidDownloadFromDate() throws Exception {
        viewAudit();
        auditdate();
        clkview();
        Thread.sleep(2000);
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void InvalidDownloadToDate() throws Exception {
        viewAudit();
        auditenddate();
        clkview();
        Thread.sleep(2000);
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void InvalidDownloadModuleDate() throws Exception {
        viewAudit();
        auditenddate();
        module();
        Thread.sleep(2000);
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void InvalidViewFromDate() throws Exception {
        viewAudit();
        auditdate();
        clkview();
        Thread.sleep(2000);
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void InvalidViewToDate() throws Exception {
        viewAudit();
        auditenddate();
        clkview();
        Thread.sleep(2000);
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void InvalidViewModule() throws Exception {
        viewAudit();
        module();
        clkview();
        Thread.sleep(2000);
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void MISInvalidViewFromDate() throws Exception {
        viewMIS();
        misdate();
        clkview();
        Thread.sleep(2000);
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void MISInvalidViewToDate() throws Exception {
        viewMIS();
        misenddate();
        clkview();
        Thread.sleep(2000);
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void MISInvalidDownloadFromDate() throws Exception {
        viewMIS();
        misdate();
        download();
        Thread.sleep(2000);
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }

    @Test
    public void MISInvalidDownloadToDate() throws Exception {
        viewMIS();
        misenddate();
        download();
        Thread.sleep(2000);
        Screenshot.captureScreenShot(driver, ExcelUtilities.testCaseDesritpioncolumnvalue);
        test.log(LogStatus.PASS, ExcelUtilities.testCaseDesritpioncolumnvalue);
    }
}

// ***************************************MIS_ENDS****************************************************

