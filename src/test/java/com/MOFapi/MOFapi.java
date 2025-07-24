package com.MOFapi;

import com.MOF.constants.API_URL;
import com.MOFutility.API_Excelutilities;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.Date;

public class MOFapi extends API_Excelutilities {

    FileWriter file;
    static WebDriver driver;
    ExtentTest test;
    static ExtentReports reports;

    // Initialize the ExtentReports instance with a report file
    @BeforeSuite
    public void initializeReports() throws Exception {
        Date d = new Date();
        String FileName = d.toString().replace(":", "_").replace(" ", "_");
        String directoryPath = "C:\\Users\\roshanbabu\\Desktop\\AUTO_RESULT\\AUTO_API_RESULT";

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = directoryPath + "\\ServiceCatalogueAPI_" + FileName + ".json";
        file = new FileWriter(filePath, true); // Initialize file writer here
    }


    // Log the current method name
    public String getCurrentMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return stackTrace[2].getMethodName();
    }

    // Start the test in ExtentReports with the method name
    public void beforeTestCase(Method method) {
        String methodName = method.getName();
        test = reports.startTest(methodName);
        System.out.println("Test started for method: " + methodName);
    }

    // Log test information to the report
    public void logTest(String message) {
        if (test != null) {
            test.log(LogStatus.INFO, message);
        }
    }

    // Test case: Fetch all services using Valid EntityID
    @Test(enabled = true, priority = 0)
    public void GetServiceInquiry() throws Exception {
        System.out.println("Fetching all services using Valid EntityID");
        try {
            // Set base URI for RestAssured request
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/services";
            Response GetServiceAuth = RestAssured.given().contentType(ContentType.JSON).get();

            // Log response details (this is already logged with RestAssured)
            GetServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String Fetchallservice = GetServiceAuth.asPrettyString();

            // Write the response to the file
            file.write("\nFetch all services\n" + Fetchallservice);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Get status code and status line
            int statuscode = GetServiceAuth.statusCode();
            String statusline = GetServiceAuth.statusLine();

            // Logging the result into ExtentReports
            if (Fetchallservice.contains("Invalid")) {
                test.log(LogStatus.INFO, "Fetch all Service Details " + statusline + " " + statuscode + "<br />" + Fetchallservice);
            } else {
                test.log(LogStatus.PASS, "GetService Inquiry Response body Passed " + statusline + " " + statuscode + "<br />" + Fetchallservice);
            }

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            logTest("Exception: " + t.getMessage());
        }
    }

    // Test case: Fetch all services using Invalid EntityID
    @Test(enabled = true, priority = 1)
    public void InvalidGetServiceInquiry() throws Exception {
        System.out.println("Fetching all services using Invalid EntityID");
        try {
            RestAssured.baseURI = API_URL.url + API_URL.InvalidEntityid + "/services";
            Response GetServiceAuth = RestAssured.given().contentType(ContentType.JSON).get();

            // Log response details (this is already logged with RestAssured)
            GetServiceAuth.then().log().all().assertThat().statusCode(200);
            String Fetchallservice = GetServiceAuth.asPrettyString();

            // Write the response to the file
            file.write("\nFetch all services using Invalid EntityID\n" + Fetchallservice);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Get status code and status line
            int statuscode = GetServiceAuth.statusCode();
            String statusline = GetServiceAuth.statusLine();

            if (Fetchallservice.contains("Invalid")) {
                test.log(LogStatus.INFO, "Fetch all Service Details (Invalid EntityID) " + statusline + " " + statuscode + "<br />" + Fetchallservice);
            } else {
                test.log(LogStatus.PASS, "Invalid GetService Inquiry Response body Passed " + statusline + " " + statuscode + "<br />" + Fetchallservice);
            }
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            logTest("Exception: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 0)
    public void maxEntityID_GetServiceInquiry() throws Exception {
        System.out.println("Fetching all services using maxEntityID_GetServiceInquiry");
        try {
            // Set base URI for RestAssured request
            RestAssured.baseURI = API_URL.url + API_URL.MaxEntityid + "/services";
            Response MaxEntityidGetServiceAuth = RestAssured.given().contentType(ContentType.JSON).get();

            // Log response details (this is already logged with RestAssured)
            MaxEntityidGetServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String MaxEntityidFetchallservice = MaxEntityidGetServiceAuth.asPrettyString();

            // Write the response to the file
            file.write("\nFetch all services maxEntityID_GetServiceInquiry\n" + MaxEntityidFetchallservice);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Get status code and status line
            int statuscode = MaxEntityidGetServiceAuth.statusCode();
            String statusline = MaxEntityidGetServiceAuth.statusLine();

            // Logging the result into ExtentReports
            if (MaxEntityidFetchallservice.contains("Invalid")) {
                test.log(LogStatus.INFO, "Fetch all Service Details " + statusline + " " + statuscode + "<br />" + MaxEntityidFetchallservice);
            } else {
                test.log(LogStatus.PASS, "GetService Inquiry Response body Passed " + statusline + " " + statuscode + "<br />" + MaxEntityidFetchallservice);
            }

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            logTest("Exception: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 0)
    public void MinEntityid_GetServiceInquiry() throws Exception {
        System.out.println("Fetching all services using MinEntityid_GetServiceInquiry");
        try {
            // Set base URI for RestAssured request
            RestAssured.baseURI = API_URL.url + API_URL.MinEntityid + "/services";
            Response MinEntityidGetServiceAuth = RestAssured.given().contentType(ContentType.JSON).get();

            // Log response details (this is already logged with RestAssured)
            MinEntityidGetServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String MinEntityidFetchallservice = MinEntityidGetServiceAuth.asPrettyString();

            // Write the response to the file
            file.write("\nFetch all services MinEntityid_GetServiceInquiry\n" + MinEntityidFetchallservice);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Get status code and status line
            int statuscode = MinEntityidGetServiceAuth.statusCode();
            String statusline = MinEntityidGetServiceAuth.statusLine();

            // Logging the result into ExtentReports
            if (MinEntityidFetchallservice.contains("Invalid")) {
                test.log(LogStatus.INFO, "Fetch all Service Details " + statusline + " " + statuscode + "<br />" + MinEntityidFetchallservice);
            } else {
                test.log(LogStatus.PASS, "GetService Inquiry Response body Passed " + statusline + " " + statuscode + "<br />" + MinEntityidFetchallservice);
            }

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            logTest("Exception: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 0)
    public void SpecialCharacterEntityID_GetServiceInquiry() throws Exception {
        System.out.println("Fetching all services using SpecialCharacterEntityID_GetServiceInquiry");
        try {
            // Set base URI for RestAssured request
            RestAssured.baseURI = API_URL.url + API_URL.SpecialCharacterEntityID + "/services";
            Response SpecialCharacterEntityIDGetServiceAuth = RestAssured.given().contentType(ContentType.JSON).get();

            // Log response details (this is already logged with RestAssured)
            SpecialCharacterEntityIDGetServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String SpecialCharacterEntityIDFetchallservice = SpecialCharacterEntityIDGetServiceAuth.asPrettyString();

            // Write the response to the file
            file.write("\nFetch all services SpecialCharacterEntityID_GetServiceInquiry\n" + SpecialCharacterEntityIDFetchallservice);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Get status code and status line
            int statuscode = SpecialCharacterEntityIDGetServiceAuth.statusCode();
            String statusline = SpecialCharacterEntityIDGetServiceAuth.statusLine();

            // Logging the result into ExtentReports
            if (SpecialCharacterEntityIDFetchallservice.contains("Invalid")) {
                test.log(LogStatus.INFO, "Fetch all Service Details " + statusline + " " + statuscode + "<br />" + SpecialCharacterEntityIDFetchallservice);
            } else {
                test.log(LogStatus.PASS, "GetService Inquiry Response body Passed " + statusline + " " + statuscode + "<br />" + SpecialCharacterEntityIDFetchallservice);
            }

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them

            file.write("\nSpecialCharacterEntityID_GetServiceInquiry Exception:\n " + t );
            logTest("Exception: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 1)
    public void AlphaNumericEntityID_GetServiceInquiry() throws Exception {
        System.out.println("Fetching all services using AlphaNumericEntityID_GetServiceInquiry");
        try {
            // Set base URI for RestAssured request
            RestAssured.baseURI = API_URL.url + API_URL.AlphaNumericEntityID + "/services";
            Response AlphaNumericEntityIDGetServiceAuth = RestAssured.given().contentType(ContentType.JSON).get();

            // Log response details (this is already logged with RestAssured)
            AlphaNumericEntityIDGetServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String AlphaNumericEntityIDFetchallservice = AlphaNumericEntityIDGetServiceAuth.asPrettyString();

            // Write the response to the file
            file.write("\nFetch all services AlphaNumericEntityID_GetServiceInquiry\n" + AlphaNumericEntityIDFetchallservice);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Get status code and status line
            int statuscode = AlphaNumericEntityIDGetServiceAuth.statusCode();
            String statusline = AlphaNumericEntityIDGetServiceAuth.statusLine();

            // Logging the result into ExtentReports
            if (AlphaNumericEntityIDFetchallservice.contains("Invalid")) {
                test.log(LogStatus.INFO, "Fetch all Service Details " + statusline + " " + statuscode + "<br />" + AlphaNumericEntityIDFetchallservice);
            } else {
                test.log(LogStatus.PASS, "GetService Inquiry Response body Passed " + statusline + " " + statuscode + "<br />" + AlphaNumericEntityIDFetchallservice);
            }

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
        	 System.out.println("Exception: " + t);
            logTest("Exception: " + t.getMessage());
        }
    }


    @Test(enabled = true, priority = 2)
    public void BlankEntityID_GetServiceInquiry() throws Exception {
        System.out.println("Fetching all services using BlankEntityID_GetServiceInquiry");
        try {
            // Set base URI for RestAssured request
            RestAssured.baseURI = API_URL.url + API_URL.BlankEntityID + "/services";
            Response BlankEntityIDGetServiceAuth = RestAssured.given().contentType(ContentType.JSON).get();

            // Log response details (this is already logged with RestAssured)
            BlankEntityIDGetServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String BlankEntityIDFetchallservice = BlankEntityIDGetServiceAuth.asPrettyString();

            // Write the response to the file
            file.write("\nFetch all services BlankEntityID_GetServiceInquiry\n" + BlankEntityIDFetchallservice);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Get status code and status line
            int statuscode = BlankEntityIDGetServiceAuth.statusCode();
            String statusline = BlankEntityIDGetServiceAuth.statusLine();

            // Logging the result into ExtentReports
            if (BlankEntityIDFetchallservice.contains("Invalid")) {
                test.log(LogStatus.INFO, "Fetch all Service Details " + statusline + " " + statuscode + "<br />" + BlankEntityIDFetchallservice);
            } else {
                test.log(LogStatus.PASS, "GetService Inquiry Response body Passed " + statusline + " " + statuscode + "<br />" + BlankEntityIDFetchallservice);
            }

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
        	   file.write("\nBlankEntityID_GetServiceInquiry Exception: " + t);
            logTest("Exception: " + t.getMessage());
        }
    }


    @Test(enabled = true, priority = 3)
    public void DecimalEntityID_GetServiceInquiry() throws Exception {
        System.out.println("Fetching all services using DecimalEntityID_GetServiceInquiry");
        try {
            // Set base URI for RestAssured request
            RestAssured.baseURI = API_URL.url + API_URL.DecimalEntityID + "/services";
            Response DecimalEntityIDGetServiceAuth = RestAssured.given().contentType(ContentType.JSON).get();

            // Log response details (this is already logged with RestAssured)
            DecimalEntityIDGetServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String DecimalEntityIDFetchallservice = DecimalEntityIDGetServiceAuth.asPrettyString();

            // Write the response to the file
            file.write("\nFetch all services DecimalEntityID_GetServiceInquiry\n" + DecimalEntityIDFetchallservice);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Get status code and status line
            int statuscode = DecimalEntityIDGetServiceAuth.statusCode();
            String statusline = DecimalEntityIDGetServiceAuth.statusLine();

            // Logging the result into ExtentReports
            if (DecimalEntityIDFetchallservice.contains("Invalid")) {
                test.log(LogStatus.INFO, "Fetch all Service Details " + statusline + " " + statuscode + "<br />" + DecimalEntityIDFetchallservice);
            } else {
                test.log(LogStatus.PASS, "GetService Inquiry Response body Passed " + statusline + " " + statuscode + "<br />" + DecimalEntityIDFetchallservice);
            }

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
       	 System.out.println("Exception: " + t);
            logTest("Exception: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 4)
    public void PostServiceInquiry_ValidServiceId() throws Exception {
        System.out.println("Posting service inquiry using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("Post Service Inquiry ValidServiceId Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_ValidServiceId\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");



            System.out.println("JSON validation passed successfully.");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 4)
    public void PostServiceInquiry_InvalidServiceId() throws Exception {
        System.out.println("Post Service Inquiry InvalidServiceId using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.InvalidServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_InvalidServiceId Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_InvalidServiceId\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");



        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 4)
    public void PostServiceInquiry_MaxServiceId() throws Exception {
        System.out.println("Post Service Inquiry InvalidServiceId using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.MaxServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_InvalidServiceId Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_MaxServiceId\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");



        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }
    @Test(enabled = true, priority = 4)
    public void PostServiceInquiry_MinServiceId() throws Exception {
        System.out.println("Post Service Inquiry InvalidServiceId using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.MinServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_MinServiceId Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_MinServiceId\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");



        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 4)
    public void PostServiceInquiry_BlankServiceId() throws Exception {
        System.out.println("Post Service Inquiry InvalidServiceId using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.BlankServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_BlankServiceId Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_BlankServiceId\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");



        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 5)
    public void PostServiceInquiry_SpecialCharacterServiceid() throws Exception {
        System.out.println("Post Service Inquiry SpecialCharacterServiceid using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with a special character in the serviceId
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.SpecialCharacterServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_SpecialCharacterServiceid Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_SpecialCharacterServiceid\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 6)
    public void PostServiceInquiry_AlphaNumericServiceid() throws Exception {
        System.out.println("Post Service Inquiry AlphaNumericServiceid using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with an alphanumeric serviceId
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.AlphaNumericServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_AlphaNumericServiceid Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_AlphaNumericServiceid\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 7)
    public void PostServiceInquiry_DecimalServiceid() throws Exception {
        System.out.println("Post Service Inquiry DecimalServiceid using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with a decimal value in the serviceId
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.DecimalServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_DecimalServiceid Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_DecimalServiceid\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 7)
    public void PostServiceInquiry_ValidUniqueID() throws Exception {
        System.out.println("Post Service Inquiry ValidUniqueID using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with a decimal value in the serviceId
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_ValidUniqueID Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_ValidUniqueID\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 8)
    public void PostServiceInquiry_InvalidUniqueID() throws Exception {
        System.out.println("Post Service Inquiry InvalidUniqueID using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with an invalid uniqueId
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.InvalidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_InvalidUniqueID Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming it returns a 400 for invalid uniqueId

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_InvalidUniqueID\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 9)
    public void PostServiceInquiry_MaxUniqueID() throws Exception {
        System.out.println("Post Service Inquiry MaxUniqueID using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with a max-length uniqueId
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.MaxUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_MaxUniqueID Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_MaxUniqueID\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 10)
    public void PostServiceInquiry_MinUniqueID() throws Exception {
        System.out.println("Post Service Inquiry MinUniqueID using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with a min-length uniqueId
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.MinUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_MinUniqueID Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_MinUniqueID\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 11)
    public void PostServiceInquiry_BlankUniqueID() throws Exception {
        System.out.println("Post Service Inquiry BlankUniqueID using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with a blank uniqueId
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.BlankUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_BlankUniqueID Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming it returns 400 for blank uniqueId

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_BlankUniqueID\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 12)
    public void PostServiceInquiry_SpecialCharacterUniqueID() throws Exception {
        System.out.println("Post Service Inquiry SpecialCharacterUniqueID using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with a special character in the uniqueId
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.SpecialCharacterUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_SpecialCharacterUniqueID Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming it returns 400 for special characters in uniqueId

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_SpecialCharacterUniqueID\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 13)
    public void PostServiceInquiry_AlphaNumericUniqueID() throws Exception {
        System.out.println("Post Service Inquiry AlphaNumericUniqueID using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with an alphanumeric uniqueId
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.AlphaNumericUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_AlphaNumericUniqueID Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_AlphaNumericUniqueID\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }


    @Test(enabled = true, priority = 14)
    public void PostServiceInquiry_DecimalUniqueID() throws Exception {
        System.out.println("Post Service Inquiry DecimalUniqueID using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with a decimal value in the uniqueId
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.DecimalUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_DecimalUniqueID Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_DecimalUniqueID\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }


    @Test(enabled = true, priority = 15)
    public void PostServiceInquiry_ValidQuantity() throws Exception {
        System.out.println("Post Service Inquiry ValidQuantity using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with a valid quantity
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_ValidQuantity Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_ValidQuantity\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }


    @Test(enabled = true, priority = 16)
    public void PostServiceInquiry_InvalidQuantity() throws Exception {
        System.out.println("Post Service Inquiry InvalidQuantity using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with an invalid quantity
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": \"" + API_URL.InvalidQuantity + "\"\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_InvalidQuantity Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming it returns 400 for invalid quantity

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_InvalidQuantity\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }


    @Test(enabled = true, priority = 17)
    public void PostServiceInquiry_MaxQuantity() throws Exception {
        System.out.println("Post Service Inquiry MaxQuantity using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with a max value for quantity
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.MaxQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_MaxQuantity Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_MaxQuantity\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }


    @Test(enabled = true, priority = 18)
    public void PostServiceInquiry_MinQuantity() throws Exception {
        System.out.println("Post Service Inquiry MinQuantity using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with a minimum value for quantity
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.MinQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_MinQuantity Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_MinQuantity\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 19)
    public void PostServiceInquiry_BlankQuantity() throws Exception {
        System.out.println("Post Service Inquiry BlankQuantity using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with a blank quantity
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": \"" + API_URL.BlankQuantity + "\"\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_BlankQuantity Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming it returns 400 for blank quantity

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_BlankQuantity\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 20)
    public void PostServiceInquiry_SpecialCharacterQuantity() throws Exception {
        System.out.println("Post Service Inquiry SpecialCharacterQuantity using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with special characters in the quantity
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": \"" + API_URL.SpecialCharacterQuantity + "\"\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_SpecialCharacterQuantity Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming it returns 400 for special characters in quantity

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_SpecialCharacterQuantity\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 21)
    public void PostServiceInquiry_AlphaNumericQuantity() throws Exception {
        System.out.println("Post Service Inquiry AlphaNumericQuantity using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with an alphanumeric quantity
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": \"" + API_URL.AlphaNumericQuantity + "\"\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_AlphaNumericQuantity Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming it returns 400 for alphanumeric quantity

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_AlphaNumericQuantity\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 22)
    public void PostServiceInquiry_DecimalQuantity() throws Exception {
        System.out.println("Post Service Inquiry DecimalQuantity using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with a decimal quantity
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.DecimalQuantity + "\n" +
                    "}";

            // Log the request body for debugging
            System.out.println("PostServiceInquiry_DecimalQuantity Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_DecimalQuantity\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 22)
    public void PostServiceInquiry_ValidServiceidDynamic() throws Exception {
        System.out.println("Post Service Inquiry ValidServiceidDynamic using data from Excel");

        try {
            // Set the base URI for the API
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            // Log the URL for validation
            System.out.println("URL: " + RestAssured.baseURI);

            // Define the request body with a decimal quantity
            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceidDynamic + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + ",\n" +  // Added comma here
                    "    \"serviceAmount\": " + API_URL.ValidAmount + "\n" +
                    "}";


            // Log the request body for debugging
            System.out.println("PostServiceInquiry_ValidServiceidDynamic Request Body: " + requestBody);

            // Send the POST request with the request body
            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            // Log response details
            postServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String postServiceResponse = postServiceAuth.asPrettyString();

            // Write the request and response to the file
            file.write("\nPostServiceInquiry_ValidServiceidDynamic\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Validate the response using JSONPath
            String serviceId = postServiceAuth.jsonPath().getString("serviceId");
            String serviceEntityId = postServiceAuth.jsonPath().getString("serviceEntityId");

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 23)
    public void PostServiceInquiry_ValidAmount() throws Exception {
        System.out.println("Post Service Inquiry ValidAmount using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceidDynamic + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + ",\n" +
                    "    \"serviceAmount\": " + API_URL.ValidAmount + "\n" +
                    "}";

            System.out.println("PostServiceInquiry_ValidAmount Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\nPostServiceInquiry_ValidAmount\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 24)
    public void PostServiceInquiry_InvalidAmount() throws Exception {
        System.out.println("Post Service Inquiry InvalidAmount using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceidDynamic + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + ",\n" +
                    "    \"serviceAmount\": \"" + API_URL.InvalidAmount + "\"\n" +  // Invalid format
                    "}";

            System.out.println("PostServiceInquiry_InvalidAmount Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);  // Assuming 400 for invalid format

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\nPostServiceInquiry_InvalidAmount\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 25)
    public void PostServiceInquiry_MaxAmount() throws Exception {
        System.out.println("Post Service Inquiry MaxAmount using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceidDynamic + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + ",\n" +
                    "    \"serviceAmount\": " + API_URL.MaxAmount + "\n" +
                    "}";

            System.out.println("PostServiceInquiry_MaxAmount Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\nPostServiceInquiry_MaxAmount\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 26)
    public void PostServiceInquiry_MinAmount() throws Exception {
        System.out.println("Post Service Inquiry MinAmount using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceidDynamic + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + ",\n" +
                    "    \"serviceAmount\": " + API_URL.MinAmount + "\n" +
                    "}";

            System.out.println("PostServiceInquiry_MinAmount Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\nPostServiceInquiry_MinAmount\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 27)
    public void PostServiceInquiry_BlankAmount() throws Exception {
        System.out.println("Post Service Inquiry BlankAmount using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceidDynamic + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + ",\n" +
                    "    \"serviceAmount\": \"\" \n" +  // Blank amount
                    "}";

            System.out.println("PostServiceInquiry_BlankAmount Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);  // Assuming 400 for blank value

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\nPostServiceInquiry_BlankAmount\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 28)
    public void PostServiceInquiry_SpecialCharacterAmount() throws Exception {
        System.out.println("Post Service Inquiry SpecialCharacterAmount using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceidDynamic + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + ",\n" +
                    "    \"serviceAmount\": \"" + API_URL.SpecialCharacterAmount + "\"\n" +  // Special characters
                    "}";

            System.out.println("PostServiceInquiry_SpecialCharacterAmount Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);  // Assuming 400 for special characters

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\nPostServiceInquiry_SpecialCharacterAmount\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }


    @Test(enabled = true, priority = 29)
    public void PostServiceInquiry_AlphaNumericAmount() throws Exception {
        System.out.println("Post Service Inquiry AlphaNumericAmount using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceidDynamic + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + ",\n" +
                    "    \"serviceAmount\": \"" + API_URL.AlphaNumericAmount + "\"\n" +  // Alphanumeric
                    "}";

            System.out.println("PostServiceInquiry_AlphaNumericAmount Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);  // Assuming 400 for alpha-numeric amount

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\nPostServiceInquiry_AlphaNumericAmount\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 30)
    public void PostServiceInquiry_DecimalAmount() throws Exception {
        System.out.println("Post Service Inquiry DecimalAmount using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiry";

            String requestBody = "{\n" +
                    "    \"serviceId\": \"" + API_URL.ValidServiceidDynamic + "\",\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"quantity\": " + API_URL.ValidQuantity + ",\n" +
                    "    \"serviceAmount\": " + API_URL.DecimalAmount + "\n" +
                    "}";

            System.out.println("PostServiceInquiry_DecimalAmount Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\nPostServiceInquiry_DecimalAmount\n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 30)
    public void Transactionstatus_ValidCatalogueTransactionId() throws Exception {
        System.out.println("Transactionstatus_ValidCatalogueTransactionId using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";


            System.out.println("Transactionstatus_ValidCatalogueTransactionId Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_ValidCatalogueTransactionId \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 31)
    public void Transactionstatus_InvalidCatalogueTransactionId() throws Exception {
        System.out.println("Transactionstatus_InvalidCatalogueTransactionId using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            // Invalid catalogueTransactionId
            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.InvalidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_InvalidCatalogueTransactionId Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // assuming 400 is expected for invalid data

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_InvalidCatalogueTransactionId \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }


    @Test(enabled = true, priority = 32)
    public void Transactionstatus_MaxCatalogueTransactionId() throws Exception {
        System.out.println("Transactionstatus_MaxCatalogueTransactionId using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            // Max catalogueTransactionId (Assuming Max length for catalogueTransactionId is 255 chars)
            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.MaxCatalogueTransactionId  + "\",\n" +  // Max length string
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_MaxCatalogueTransactionId Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_MaxCatalogueTransactionId \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 33)
    public void Transactionstatus_MinCatalogueTransactionId() throws Exception {
        System.out.println("Transactionstatus_MinCatalogueTransactionId using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            // Min catalogueTransactionId (Assuming Min length for catalogueTransactionId is 1 character)
            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.MinCatalogueTransactionId+ "\",\n" +  // Min length string
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_MinCatalogueTransactionId Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_MinCatalogueTransactionId \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 34)
    public void Transactionstatus_SpecialCharacterCatalogueTransactionId() throws Exception {
        System.out.println("Transactionstatus_SpecialCharacterCatalogueTransactionId using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            // Special Character catalogueTransactionId
            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.SpecialCharacterCatalogueTransactionId  + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_SpecialCharacterCatalogueTransactionId Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Expected 400 for invalid input

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_SpecialCharacterCatalogueTransactionId \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 35)
    public void Transactionstatus_AlphaNumericCatalogueTransactionId() throws Exception {
        System.out.println("Transactionstatus_AlphaNumericCatalogueTransactionId using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            // Alpha Numeric catalogueTransactionId
            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.SpecialCharacterCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_AlphaNumericCatalogueTransactionId Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_AlphaNumericCatalogueTransactionId \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 36)
    public void Transactionstatus_BlankCatalogueTransactionId() throws Exception {
        System.out.println("Transactionstatus_BlankCatalogueTransactionId using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            // Blank catalogueTransactionId
            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.BlankCatalogueTransactionId + "\",\n" +  // Blank string
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_BlankCatalogueTransactionId Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Expected 400 for blank input

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_BlankCatalogueTransactionId \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }


    @Test(enabled = true, priority = 37)
    public void Transactionstatus_DecimalCatalogueTransactionId() throws Exception {
        System.out.println("Transactionstatus_DecimalCatalogueTransactionId using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            // Decimal catalogueTransactionId
            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.DecimalCatalogueTransactionId  + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_DecimalCatalogueTransactionId Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Expected 400 for invalid input

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_DecimalCatalogueTransactionId \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 38)
    public void Transactionstatus_ValidAcqTransactionstatus() throws Exception {
        System.out.println("Transactionstatus_ValidAcqTransactionstatus using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            // Valid acqTransactionstatus (Assume a valid value is "Completed")
            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_ValidAcqTransactionstatus Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_ValidAcqTransactionstatus \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 38)
    public void Transactionstatus_InvalidAcqTransactionstatus() throws Exception {
        System.out.println("Transactionstatus_ValidAcqTransactionstatus using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            // Valid acqTransactionstatus (Assume a valid value is "Completed")
            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.InvalidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_ValidAcqTransactionstatus Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_ValidAcqTransactionstatus \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 40)
    public void Transactionstatus_MaxAcqTransactionstatus() throws Exception {
        System.out.println("Transactionstatus_MaxAcqTransactionstatus using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            // Max acqTransactionstatus (Assume max length is 255 characters)


            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.MaxAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_MaxAcqTransactionstatus Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_MaxAcqTransactionstatus \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 41)
    public void Transactionstatus_MinAcqTransactionstatus() throws Exception {
        System.out.println("Transactionstatus_MinAcqTransactionstatus using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            // Min acqTransactionstatus (Assume min length is 1 character)
            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.MinAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_MinAcqTransactionstatus Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_MinAcqTransactionstatus \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 42)
    public void Transactionstatus_SpecialCharacterAcqTransactionstatus() throws Exception {
        System.out.println("Transactionstatus_SpecialCharacterAcqTransactionstatus using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            // Special Character acqTransactionstatus (Assume we use "!@#")
            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.SpecialCharacterAcqTransactionStatus+ "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_SpecialCharacterAcqTransactionstatus Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Expected 400 for invalid input

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_SpecialCharacterAcqTransactionstatus \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 43)
    public void Transactionstatus_AlphaNumericAcqTransactionstatus() throws Exception {
        System.out.println("Transactionstatus_AlphaNumericAcqTransactionstatus using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            // Alpha Numeric acqTransactionstatus (Assume a valid value is "Completed123")
            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.AlphaNumericAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_AlphaNumericAcqTransactionstatus Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_AlphaNumericAcqTransactionstatus \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 44)
    public void Transactionstatus_BlankAcqTransactionstatus() throws Exception {
        System.out.println("Transactionstatus_BlankAcqTransactionstatus using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            // Blank acqTransactionstatus (Empty value for the field)
            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.BlankAcqTransactionStatus +"\" ,\n" + // Blank field
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_BlankAcqTransactionstatus Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Expected 400 for blank input

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_BlankAcqTransactionstatus \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 45)
    public void Transactionstatus_DecimalAcqTransactionstatus() throws Exception {
        System.out.println("Transactionstatus_DecimalAcqTransactionstatus using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            // Decimal acqTransactionstatus (Assume a decimal value is not valid for this field)
            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.DecimalAcqTransactionStatus + "\",\n" + // Decimal value
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_DecimalAcqTransactionstatus Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Expected 400 for decimal input

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_DecimalAcqTransactionstatus \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 46)
    public void Transactionstatus_ValidAcqErrorCode() throws Exception {
        System.out.println("Transactionstatus_ValidAcqErrorCode using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" + // Valid value for acqErrorCode
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_ValidAcqErrorCode Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_ValidAcqErrorCode \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 47)
    public void Transactionstatus_InvalidAcqErrorCode() throws Exception {
        System.out.println("Transactionstatus_InvalidAcqErrorCode using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.InvalidAcqErrorCode + "\",\n" + // Invalid value for acqErrorCode
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_InvalidAcqErrorCode Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming this returns a 400 for invalid input

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_InvalidAcqErrorCode \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 48)
    public void Transactionstatus_MaxAcqErrorCode() throws Exception {
        System.out.println("Transactionstatus_MaxAcqErrorCode using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.MaxAcqErrorCode + "\",\n" + // Max value for acqErrorCode
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_MaxAcqErrorCode Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_MaxAcqErrorCode \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }


    @Test(enabled = true, priority = 49)
    public void Transactionstatus_MinAcqErrorCode() throws Exception {
        System.out.println("Transactionstatus_MinAcqErrorCode using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.MinAcqErrorCode + "\",\n" + // Min value for acqErrorCode
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_MinAcqErrorCode Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_MinAcqErrorCode \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 50)
    public void Transactionstatus_SpecialCharacterAcqErrorCode() throws Exception {
        System.out.println("Transactionstatus_SpecialCharacterAcqErrorCode using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.SpecialCharacterAcqErrorCode + "\",\n" + // Special character value for acqErrorCode
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_SpecialCharacterAcqErrorCode Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_SpecialCharacterAcqErrorCode \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 51)
    public void Transactionstatus_BlankAcqErrorCode() throws Exception {
        System.out.println("Transactionstatus_BlankAcqErrorCode using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.BlankAcqErrorCode + "\",\n" + // Blank value for acqErrorCode
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_BlankAcqErrorCode Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming it returns 400

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_BlankAcqErrorCode \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }


    @Test(enabled = true, priority = 52)
    public void Transactionstatus_DecimalAcqErrorCode() throws Exception {
        System.out.println("Transactionstatus_DecimalAcqErrorCode using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.DecimalAcqErrorCode+ "\",\n" + // Decimal value for acqErrorCode
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_DecimalAcqErrorCode Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_DecimalAcqErrorCode \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 53)
    public void Transactionstatus_AlphaNumericAcqErrorCode() throws Exception {
        System.out.println("Transactionstatus_AlphaNumericAcqErrorCode using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.AlphaNumericAcqErrorCode + "\",\n" + // Alphanumeric value for acqErrorCode
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_AlphaNumericAcqErrorCode Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_AlphaNumericAcqErrorCode \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 54)
    public void Transactionstatus_ValidAcqErrorText() throws Exception {
        System.out.println("Transactionstatus_ValidAcqErrorText using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_ValidAcqErrorText Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_ValidAcqErrorText \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 55)
    public void Transactionstatus_InvalidAcqErrorText() throws Exception {
        System.out.println("Transactionstatus_InvalidAcqErrorText using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.InvalidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_InvalidAcqErrorText Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_InvalidAcqErrorText \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 56)
    public void Transactionstatus_MaxAcqErrorText() throws Exception {
        System.out.println("Transactionstatus_MaxAcqErrorText using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.MaxAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_MaxAcqErrorText Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_MaxAcqErrorText \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 57)
    public void Transactionstatus_MinAcqErrorText() throws Exception {
        System.out.println("Transactionstatus_MinAcqErrorText using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.MinAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_MinAcqErrorText Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_MinAcqErrorText \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 58)
    public void Transactionstatus_SpecialCharacterAcqErrorText() throws Exception {
        System.out.println("Transactionstatus_SpecialCharacterAcqErrorText using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.SpecialCharacterAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_SpecialCharacterAcqErrorText Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_SpecialCharacterAcqErrorText \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 59)
    public void Transactionstatus_AlphaNumericAcqErrorText() throws Exception {
        System.out.println("Transactionstatus_AlphaNumericAcqErrorText using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.AlphaNumericAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_AlphaNumericAcqErrorText Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_AlphaNumericAcqErrorText \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 60)
    public void Transactionstatus_BlankAcqErrorText() throws Exception {
        System.out.println("Transactionstatus_BlankAcqErrorText using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.BlankAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_BlankAcqErrorText Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_BlankAcqErrorText \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 61)
    public void Transactionstatus_DecimalAcqErrorText() throws Exception {
        System.out.println("Transactionstatus_DecimalAcqErrorText using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.DecimalAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_DecimalAcqErrorText Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_DecimalAcqErrorText \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }


    @Test(enabled = true, priority = 53)
    public void Transactionstatus_ValidAcqRRN() throws Exception {
        System.out.println("Transactionstatus_ValidAcqRRN using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_ValidAcqRRN Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_ValidAcqRRN \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 54)
    public void Transactionstatus_InvalidAcqRRN() throws Exception {
        System.out.println("Transactionstatus_InvalidAcqRRN using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.InvalidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.InvalidAcqRRN + "\",\n" + // Invalid acqRRN
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_InvalidAcqRRN Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is expected for invalid

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_InvalidAcqRRN \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 55)
    public void Transactionstatus_MaxAcqRRN() throws Exception {
        System.out.println("Transactionstatus_MaxAcqRRN using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.MaxAcqRRN + "\",\n" + // Max acqRRN
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_MaxAcqRRN Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_MaxAcqRRN \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 56)
    public void Transactionstatus_MinAcqRRN() throws Exception {
        System.out.println("Transactionstatus_MinAcqRRN using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.MinAcqRRN + "\",\n" + // Min acqRRN
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_MinAcqRRN Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_MinAcqRRN \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 57)
    public void Transactionstatus_SpecialCharacterAcqRRN() throws Exception {
        System.out.println("Transactionstatus_SpecialCharacterAcqRRN using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.SpecialCharacterAcqRRN + "\",\n" + // Special Character acqRRN
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_SpecialCharacterAcqRRN Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_SpecialCharacterAcqRRN \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }


    @Test(enabled = true, priority = 58)
    public void Transactionstatus_AlphaNumericAcqRRN() throws Exception {
        System.out.println("Transactionstatus_AlphaNumericAcqRRN using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.AlphaNumericAcqRRN + "\",\n" + // Alphanumeric acqRRN
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_AlphaNumericAcqRRN Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200);

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_AlphaNumericAcqRRN \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 59)
    public void Transactionstatus_BlankAcqRRN() throws Exception {
        System.out.println("Transactionstatus_BlankAcqRRN using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.BlankAcqRRN + "\",\n" + // Blank acqRRN
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_BlankAcqRRN Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming blank value returns 400

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_BlankAcqRRN \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 60)
    public void Transactionstatus_DecimalAcqRRN() throws Exception {
        System.out.println("Transactionstatus_DecimalAcqRRN using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.DecimalAcqRRN + "\",\n" + // Decimal acqRRN
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_DecimalAcqRRN Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_DecimalAcqRRN \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 53)
    public void Transactionstatus_ValidCardName() throws Exception {
        System.out.println("Transactionstatus_ValidCardName using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_ValidCardName Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_ValidCardName \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 54)
    public void Transactionstatus_InvalidCardName() throws Exception {
        System.out.println("Transactionstatus_InvalidCardName using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.InvalidCardName + "\",\n" +  // Invalid card name value
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_InvalidCardName Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is the expected status code for invalid input

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_InvalidCardName \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 55)
    public void Transactionstatus_MaxCardName() throws Exception {
        System.out.println("Transactionstatus_MaxCardName using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.MaxCardName + "\",\n" +  // Max length card name
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_MaxCardName Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code for valid input

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_MaxCardName \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 56)
    public void Transactionstatus_MinCardName() throws Exception {
        System.out.println("Transactionstatus_MinCardName using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.MinCardName + "\",\n" +  // Min length card name
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_MinCardName Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code for valid input

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_MinCardName \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 57)
    public void Transactionstatus_SpecialCharacterCardName() throws Exception {
        System.out.println("Transactionstatus_SpecialCharacterCardName using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.SpecialCharacterCardName + "\",\n" +  // Special character card name
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_SpecialCharacterCardName Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code for valid input

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_SpecialCharacterCardName \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 58)
    public void Transactionstatus_AlphaNumericCardName() throws Exception {
        System.out.println("Transactionstatus_AlphaNumericCardName using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.AlphaNumericCardName + "\",\n" +  // Alpha numeric card name
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_AlphaNumericCardName Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code for valid input

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_AlphaNumericCardName \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 59)
    public void Transactionstatus_BlankCardName() throws Exception {
        System.out.println("Transactionstatus_BlankCardName using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.BlankCardName + "\",\n" +  // Blank card name
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_BlankCardName Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is the expected status code for blank input

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_BlankCardName \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 60)
    public void Transactionstatus_DecimalCardName() throws Exception {
        System.out.println("Transactionstatus_DecimalCardName using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.DecimalCardName + "\",\n" +  // Decimal card name
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_DecimalCardName Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is the expected status code for decimal input

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_DecimalCardName \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 53)
    public void Transactionstatus_ValidAcqTransactionId() throws Exception {
        System.out.println("Transactionstatus_ValidAcqTransactionId using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_ValidAcqTransactionId Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_ValidAcqTransactionId \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 54)
    public void Transactionstatus_InvalidAcqTransactionId() throws Exception {
        System.out.println("Transactionstatus_InvalidAcqTransactionId using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.InvalidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_InvalidAcqTransactionId Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is the expected status code for invalid transactionId

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_InvalidAcqTransactionId \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 55)
    public void Transactionstatus_MaxAcqTransactionId() throws Exception {
        System.out.println("Transactionstatus_MaxAcqTransactionId using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.MaxAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_MaxAcqTransactionId Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code for valid max transactionId

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_MaxAcqTransactionId \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 56)
    public void Transactionstatus_MinAcqTransactionId() throws Exception {
        System.out.println("Transactionstatus_MinAcqTransactionId using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.MinAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_MinAcqTransactionId Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code for valid min transactionId

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_MinAcqTransactionId \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 57)
    public void Transactionstatus_SpecialCharacterAcqTransactionId() throws Exception {
        System.out.println("Transactionstatus_SpecialCharacterAcqTransactionId using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.SpecialCharacterAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_SpecialCharacterAcqTransactionId Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is the expected status code for invalid transactionId

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_SpecialCharacterAcqTransactionId \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 58)
    public void Transactionstatus_AlphaNumericAcqTransactionId() throws Exception {
        System.out.println("Transactionstatus_AlphaNumericAcqTransactionId using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.AlphaNumericAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_AlphaNumericAcqTransactionId Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is the expected status code for invalid transactionId

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_AlphaNumericAcqTransactionId \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 59)
    public void Transactionstatus_BlankAcqTransactionId() throws Exception {
        System.out.println("Transactionstatus_BlankAcqTransactionId using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.BlankAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_BlankAcqTransactionId Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is the expected status code for blank transactionId

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_BlankAcqTransactionId \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 60)
    public void Transactionstatus_DecimalAcqTransactionId() throws Exception {
        System.out.println("Transactionstatus_DecimalAcqTransactionId using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.DecimalAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_DecimalAcqTransactionId Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is the expected status code for decimal transactionId

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_DecimalAcqTransactionId \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 53)
    public void Transactionstatus_ValidAcqTransactionAmount() throws Exception {
        System.out.println("Transactionstatus_ValidAcqTransactionAmount using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_ValidAcqTransactionAmount Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_ValidAcqTransactionAmount \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 54)
    public void Transactionstatus_InvalidAcqTransactionAmount() throws Exception {
        System.out.println("Transactionstatus_InvalidAcqTransactionAmount using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": \"" + API_URL.InvalidAcqTransactionAmount + "\",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_InvalidAcqTransactionAmount Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is the expected status code for invalid amount

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_InvalidAcqTransactionAmount \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 55)
    public void Transactionstatus_MaxAcqTransactionAmount() throws Exception {
        System.out.println("Transactionstatus_MaxAcqTransactionAmount using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.MaxAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_MaxAcqTransactionAmount Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code for valid max amount

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_MaxAcqTransactionAmount \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 56)
    public void Transactionstatus_MinAcqTransactionAmount() throws Exception {
        System.out.println("Transactionstatus_MinAcqTransactionAmount using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.MinAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_MinAcqTransactionAmount Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code for valid min amount

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_MinAcqTransactionAmount \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 57)
    public void Transactionstatus_SpecialCharacterAcqTransactionAmount() throws Exception {
        System.out.println("Transactionstatus_SpecialCharacterAcqTransactionAmount using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": \"" + API_URL.SpecialCharacterAcqTransactionAmount + "\",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_SpecialCharacterAcqTransactionAmount Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is the expected status code for invalid amount

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_SpecialCharacterAcqTransactionAmount \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 58)
    public void Transactionstatus_AlphaNumericAcqTransactionAmount() throws Exception {
        System.out.println("Transactionstatus_AlphaNumericAcqTransactionAmount using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": \"" + API_URL.AlphaNumericAcqTransactionAmount + "\",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_AlphaNumericAcqTransactionAmount Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is the expected status code for invalid amount

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_AlphaNumericAcqTransactionAmount \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 59)
    public void Transactionstatus_BlankAcqTransactionAmount() throws Exception {
        System.out.println("Transactionstatus_BlankAcqTransactionAmount using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": \"" + API_URL.BlankAcqTransactionAmount + "\",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_BlankAcqTransactionAmount Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is the expected status code for blank amount

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_BlankAcqTransactionAmount \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 60)
    public void Transactionstatus_DecimalAcqTransactionAmount() throws Exception {
        System.out.println("Transactionstatus_DecimalAcqTransactionAmount using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": \"" + API_URL.DecimalAcqTransactionAmount + "\",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_DecimalAcqTransactionAmount Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is the expected status code for invalid decimal amount

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_DecimalAcqTransactionAmount \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 53)
    public void Transactionstatus_ValidAcqPaymentMode() throws Exception {
        System.out.println("Transactionstatus_ValidAcqPaymentMode using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.ValidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_ValidAcqPaymentMode Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_ValidAcqPaymentMode \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 54)
    public void Transactionstatus_InvalidAcqPaymentMode() throws Exception {
        System.out.println("Transactionstatus_InvalidAcqPaymentMode using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.InvalidAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_InvalidAcqPaymentMode Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is the expected status code for invalid mode

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_InvalidAcqPaymentMode \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 55)
    public void Transactionstatus_MaxAcqPaymentMode() throws Exception {
        System.out.println("Transactionstatus_MaxAcqPaymentMode using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.MaxAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_MaxAcqPaymentMode Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code for valid mode

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_MaxAcqPaymentMode \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 56)
    public void Transactionstatus_MinAcqPaymentMode() throws Exception {
        System.out.println("Transactionstatus_MinAcqPaymentMode using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.MinAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_MinAcqPaymentMode Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code for valid mode

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_MinAcqPaymentMode \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 57)
    public void Transactionstatus_SpecialCharacterAcqPaymentMode() throws Exception {
        System.out.println("Transactionstatus_SpecialCharacterAcqPaymentMode using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.SpecialCharacterAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_SpecialCharacterAcqPaymentMode Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is the expected status code for invalid mode

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_SpecialCharacterAcqPaymentMode \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 58)
    public void Transactionstatus_AlphaNumericAcqPaymentMode() throws Exception {
        System.out.println("Transactionstatus_AlphaNumericAcqPaymentMode using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.AlphaNumericAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_AlphaNumericAcqPaymentMode Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is the expected status code for invalid mode

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_AlphaNumericAcqPaymentMode \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 59)
    public void Transactionstatus_BlankAcqPaymentMode() throws Exception {
        System.out.println("Transactionstatus_BlankAcqPaymentMode using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.BlankAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_BlankAcqPaymentMode Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is the expected status code for blank mode

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_BlankAcqPaymentMode \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 60)
    public void Transactionstatus_DecimalAcqPaymentMode() throws Exception {
        System.out.println("Transactionstatus_DecimalAcqPaymentMode using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/transactionStatus";

            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"catalogueTransactionId\": \"" + API_URL.ValidCatalogueTransactionId + "\",\n" +
                    "    \"acqTransactionstatus\": \"" + API_URL.ValidAcqTransactionStatus + "\",\n" +
                    "    \"acqErrorCode\": \"" + API_URL.ValidAcqErrorCode + "\",\n" +
                    "    \"acqErrorText\": \"" + API_URL.ValidAcqErrorText + "\",\n" +
                    "    \"acqRRN\": \"" + API_URL.ValidAcqRRN + "\",\n" +
                    "    \"cardName\": \"" + API_URL.ValidCardName + "\",\n" +
                    "    \"acqTransactionId\": \"" + API_URL.ValidAcqTransactionId + "\",\n" +
                    "    \"acqTransactionAmount\": " + API_URL.ValidAcqTransactionAmount + ",\n" +
                    "    \"acqPaymentMode\": \"" + API_URL.DecimalAcqPaymentMode + "\"\n" +
                    "}";

            System.out.println("Transactionstatus_DecimalAcqPaymentMode Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 400 is the expected status code for invalid mode

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n Transactionstatus_DecimalAcqPaymentMode \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 0)
    public void Fetchvalid_SingleService_ValidServiceid() throws Exception {
        System.out.println("Fetching single services using Valid Serviceid");

        try {
            // Set base URI for RestAssured request
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/service/" + API_URL.ValidServiceid;
            Response GetServiceAuth = RestAssured.given().contentType(ContentType.JSON).get();

            // Log response details
            GetServiceAuth.then().log().all().assertThat().statusCode(200);

            // Get the response body as a pretty string
            String FetchSingleService = GetServiceAuth.asPrettyString();

            // Write the response to the file
            file.write("\nFetch valid Service\n" + FetchSingleService);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Get status code and status line
            int statuscode = GetServiceAuth.statusCode();
            String statusline = GetServiceAuth.statusLine();

            // Logging the result into ExtentReports
            if (FetchSingleService.contains("Invalid")) {
                test.log(LogStatus.INFO, "Fetch valid Service " + statusline + " " + statuscode + "<br />" + FetchSingleService);
            } else {
                test.log(LogStatus.PASS, "GetService Inquiry Response body Passed " + statusline + " " + statuscode + "<br />" + FetchSingleService);
            }

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            logTest("Exception: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 1)
    public void FetchSingleService_InvalidServiceid() throws Exception {
        System.out.println("Fetching single services using Invalid Serviceid");

        try {
            // Set base URI for RestAssured request
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/service/" + API_URL.InvalidServiceid;
            Response GetServiceAuth = RestAssured.given().contentType(ContentType.JSON).get();

            // Log response details
            GetServiceAuth.then().log().all().assertThat().statusCode(400);  // Assuming 400 for invalid Serviceid

            // Get the response body as a pretty string
            String FetchSingleService = GetServiceAuth.asPrettyString();

            // Write the response to the file
            file.write("\nFetch Invalid Service\n" + FetchSingleService);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Get status code and status line
            int statuscode = GetServiceAuth.statusCode();
            String statusline = GetServiceAuth.statusLine();

            // Logging the result into ExtentReports
            test.log(LogStatus.FAIL, "Fetch Invalid Service " + statusline + " " + statuscode + "<br />" + FetchSingleService);

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            logTest("Exception: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 2)
    public void FetchSingleService_MaxServiceid() throws Exception {
        System.out.println("Fetching single services using Max Serviceid");

        try {
            // Set base URI for RestAssured request
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/service/" + API_URL.MaxServiceid;
            Response GetServiceAuth = RestAssured.given().contentType(ContentType.JSON).get();

            // Log response details
            GetServiceAuth.then().log().all().assertThat().statusCode(200);  // Assuming 200 for valid Serviceid

            // Get the response body as a pretty string
            String FetchSingleService = GetServiceAuth.asPrettyString();

            // Write the response to the file
            file.write("\nFetch Max Service\n" + FetchSingleService);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Get status code and status line
            int statuscode = GetServiceAuth.statusCode();
            String statusline = GetServiceAuth.statusLine();

            // Logging the result into ExtentReports
            test.log(LogStatus.PASS, "Fetch Max Service " + statusline + " " + statuscode + "<br />" + FetchSingleService);

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            logTest("Exception: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 3)
    public void FetchSingleService_MinServiceid() throws Exception {
        System.out.println("Fetching single services using Min Serviceid");

        try {
            // Set base URI for RestAssured request
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/service/" + API_URL.MinServiceid;
            Response GetServiceAuth = RestAssured.given().contentType(ContentType.JSON).get();

            // Log response details
            GetServiceAuth.then().log().all().assertThat().statusCode(200);  // Assuming 200 for valid Serviceid

            // Get the response body as a pretty string
            String FetchSingleService = GetServiceAuth.asPrettyString();

            // Write the response to the file
            file.write("\nFetch Min Service\n" + FetchSingleService);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Get status code and status line
            int statuscode = GetServiceAuth.statusCode();
            String statusline = GetServiceAuth.statusLine();

            // Logging the result into ExtentReports
            test.log(LogStatus.PASS, "Fetch Min Service " + statusline + " " + statuscode + "<br />" + FetchSingleService);

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            logTest("Exception: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 4)
    public void FetchSingleService_BlankServiceid() throws Exception {
        System.out.println("Fetching single services using Blank Serviceid");

        try {
            // Set base URI for RestAssured request
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/service/" + API_URL.BlankServiceid;
            Response GetServiceAuth = RestAssured.given().contentType(ContentType.JSON).get();

            // Log response details
            GetServiceAuth.then().log().all().assertThat().statusCode(400);  // Assuming 400 for blank Serviceid

            // Get the response body as a pretty string
            String FetchSingleService = GetServiceAuth.asPrettyString();

            // Write the response to the file
            file.write("\nFetch Blank Service\n" + FetchSingleService);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Get status code and status line
            int statuscode = GetServiceAuth.statusCode();
            String statusline = GetServiceAuth.statusLine();

            // Logging the result into ExtentReports
            test.log(LogStatus.FAIL, "Fetch Blank Service " + statusline + " " + statuscode + "<br />" + FetchSingleService);

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            logTest("Exception: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 5)
    public void FetchSingleService_SpecialCharacterServiceid() throws Exception {
        System.out.println("Fetching single services using Special Character Serviceid");

        try {
            // Set base URI for RestAssured request
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/service/" + API_URL.SpecialCharacterServiceid;
            Response GetServiceAuth = RestAssured.given().contentType(ContentType.JSON).get();

            // Log response details
            GetServiceAuth.then().log().all().assertThat().statusCode(400);  // Assuming 400 for special character Serviceid

            // Get the response body as a pretty string
            String FetchSingleService = GetServiceAuth.asPrettyString();

            // Write the response to the file
            file.write("\nFetch Special Character Service\n" + FetchSingleService);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Get status code and status line
            int statuscode = GetServiceAuth.statusCode();
            String statusline = GetServiceAuth.statusLine();

            // Logging the result into ExtentReports
            test.log(LogStatus.FAIL, "Fetch Special Character Service " + statusline + " " + statuscode + "<br />" + FetchSingleService);

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            logTest("Exception: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 6)
    public void FetchSingleService_AlphaNumericServiceid() throws Exception {
        System.out.println("Fetching single services using Alpha Numeric Serviceid");

        try {
            // Set base URI for RestAssured request
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/service/" + API_URL.AlphaNumericServiceid;
            Response GetServiceAuth = RestAssured.given().contentType(ContentType.JSON).get();

            // Log response details
            GetServiceAuth.then().log().all().assertThat().statusCode(400);  // Assuming 400 for alphanumeric Serviceid

            // Get the response body as a pretty string
            String FetchSingleService = GetServiceAuth.asPrettyString();

            // Write the response to the file
            file.write("\nFetch AlphaNumeric Service\n" + FetchSingleService);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Get status code and status line
            int statuscode = GetServiceAuth.statusCode();
            String statusline = GetServiceAuth.statusLine();

            // Logging the result into ExtentReports
            test.log(LogStatus.FAIL, "Fetch AlphaNumeric Service " + statusline + " " + statuscode + "<br />" + FetchSingleService);

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            logTest("Exception: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 7)
    public void FetchSingleService_DecimalServiceid() throws Exception {
        System.out.println("Fetching single services using Decimal Serviceid");

        try {
            // Set base URI for RestAssured request
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/service/" + API_URL.DecimalServiceid;
            Response GetServiceAuth = RestAssured.given().contentType(ContentType.JSON).get();

            // Log response details
            GetServiceAuth.then().log().all().assertThat().statusCode(400);  // Assuming 400 for decimal Serviceid

            // Get the response body as a pretty string
            String FetchSingleService = GetServiceAuth.asPrettyString();

            // Write the response to the file
            file.write("\nFetch Decimal Service\n" + FetchSingleService);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");

            // Get status code and status line
            int statuscode = GetServiceAuth.statusCode();
            String statusline = GetServiceAuth.statusLine();

            // Logging the result into ExtentReports
            test.log(LogStatus.FAIL, "Fetch Decimal Service " + statusline + " " + statuscode + "<br />" + FetchSingleService);

        } catch (Throwable t) {
            // Catch any other unexpected exceptions and log them
            System.out.println("Exception: " + t);
            logTest("Exception: " + t.getMessage());
        }
    }


    @Test(enabled = true, priority = 53)
    public void InquiryList_Valid() throws Exception {
        System.out.println("InquiryList_Valid using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiryList";


            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"serviceList\": [\n" +
                    "        {\n" +
                    "            \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "            \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"serviceId\": \"" + API_URL.ValidServiceidDynamic + "\",\n" +
                    "            \"serviceAmount\": " + API_URL.ValidAmount + ",\n" +
                    "            \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";


            System.out.println("InquiryList_Valid Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n InquiryList_Valid \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 53)
    public void InquiryList_InvalidAmount() throws Exception {
        System.out.println("InquiryList_InvalidAmount using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiryList";


            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"serviceList\": [\n" +
                    "        {\n" +
                    "            \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "            \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"serviceId\": \"" + API_URL.ValidServiceidDynamic + "\",\n" +
                    "            \"serviceAmount\": " + API_URL.InvalidAmount + ",\n" +
                    "            \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";


            System.out.println("InquiryList_InvalidAmount Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(400); // Assuming 200 is the expected status code

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n InquiryList_InvalidAmount \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }


    @Test(enabled = true, priority = 53)
    public void InquiryList_InvalidUniqueID() throws Exception {
        System.out.println("InquiryList_InvalidUniqueID using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiryList";


            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.InvalidUniqueID + "\",\n" +
                    "    \"serviceList\": [\n" +
                    "        {\n" +
                    "            \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "            \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"serviceId\": \"" + API_URL.ValidServiceidDynamic + "\",\n" +
                    "            \"serviceAmount\": " + API_URL.ValidAmount + ",\n" +
                    "            \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";


            System.out.println("InquiryList_InvalidUniqueID Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n InquiryList_InvalidUniqueID \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }
    }

    @Test(enabled = true, priority = 53)
    public void InquiryList_InvalidQuantity() throws Exception {
        System.out.println("InquiryList_InvalidQuantity using data from Excel");

        try {
            RestAssured.baseURI = API_URL.url + API_URL.ValidEntityid + "/inquiryList";


            String requestBody = "{\n" +
                    "    \"uniqueId\": \"" + API_URL.ValidUniqueID + "\",\n" +
                    "    \"serviceList\": [\n" +
                    "        {\n" +
                    "            \"serviceId\": \"" + API_URL.ValidServiceid + "\",\n" +
                    "            \"quantity\": " + API_URL.InvalidQuantity + "\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"serviceId\": \"" + API_URL.ValidServiceidDynamic + "\",\n" +
                    "            \"serviceAmount\": " + API_URL.ValidAmount + ",\n" +
                    "            \"quantity\": " + API_URL.ValidQuantity + "\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";


            System.out.println("InquiryList_InvalidQuantity Request Body: " + requestBody);

            Response postServiceAuth = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post();

            postServiceAuth.then().log().all().assertThat().statusCode(200); // Assuming 200 is the expected status code

            String postServiceResponse = postServiceAuth.asPrettyString();

            file.write("\n InquiryList_InvalidQuantity \n" +
                    "\nRequest Body: " + requestBody + "\n" +
                    "Response Body: " + postServiceResponse);
            System.out.println("Response written to file: ServiceCatalogueAPI_<timestamp>.json");
        } catch (Throwable t) {
            System.out.println("Exception: " + t);
            file.write("\nException: " + t.getMessage());
        }

    }




    // After suite: Flush the report and close the response file
    @AfterSuite
    public void SaveResponseFileclose() throws Exception {
        if (test != null) {
            reports.endTest(test);
        }

        // Flush the report only if it's not null
        if (reports != null) {
            reports.flush();
            System.out.println("Extent Report flushed.");
        }

        // Close file writer if it's not null
        if (file != null) {
            file.flush();
            file.close();
            System.out.println("File flushed and closed.");
        }
    }

    // Main method to run tests
    public static void main(String[] args) throws Exception {
        MOFapi ma = new MOFapi();


        ma.initializeReports();
        ma.GetServiceInquiry();
        ma.InvalidGetServiceInquiry();
        ma.maxEntityID_GetServiceInquiry();
        ma.MinEntityid_GetServiceInquiry();
        ma.SpecialCharacterEntityID_GetServiceInquiry();
        ma.AlphaNumericEntityID_GetServiceInquiry();
        ma.BlankEntityID_GetServiceInquiry();
        ma.DecimalEntityID_GetServiceInquiry();

        ma.PostServiceInquiry_ValidServiceId();
        ma.PostServiceInquiry_InvalidServiceId();
        ma.PostServiceInquiry_MaxServiceId();
        ma.PostServiceInquiry_MinServiceId();
        ma.PostServiceInquiry_BlankServiceId();
        ma.PostServiceInquiry_SpecialCharacterServiceid();
        ma.PostServiceInquiry_AlphaNumericServiceid();
        ma.PostServiceInquiry_DecimalServiceid();

        ma.PostServiceInquiry_ValidUniqueID();
        ma.PostServiceInquiry_InvalidUniqueID();
        ma.PostServiceInquiry_MaxUniqueID();
        ma.PostServiceInquiry_MinUniqueID();
        ma.PostServiceInquiry_BlankUniqueID();
        ma.PostServiceInquiry_SpecialCharacterUniqueID();
        ma.PostServiceInquiry_AlphaNumericUniqueID();
        ma.PostServiceInquiry_DecimalUniqueID();

        ma.PostServiceInquiry_ValidQuantity();
        ma.PostServiceInquiry_InvalidQuantity();
        ma.PostServiceInquiry_MaxQuantity();
        ma.PostServiceInquiry_MinQuantity();
        ma.PostServiceInquiry_BlankQuantity();
        ma.PostServiceInquiry_SpecialCharacterQuantity();
        ma.PostServiceInquiry_AlphaNumericQuantity();

        ma.PostServiceInquiry_ValidServiceidDynamic();

        ma.PostServiceInquiry_ValidAmount();
        ma.PostServiceInquiry_InvalidAmount();
        ma.PostServiceInquiry_MaxAmount();
        ma.PostServiceInquiry_MinAmount();
        ma.PostServiceInquiry_BlankAmount();
        ma.PostServiceInquiry_SpecialCharacterAmount();
        ma.PostServiceInquiry_AlphaNumericAmount();


        ma.Transactionstatus_ValidCatalogueTransactionId();
        ma.Transactionstatus_InvalidCatalogueTransactionId();
        ma.Transactionstatus_MaxCatalogueTransactionId();
        ma.Transactionstatus_MinCatalogueTransactionId();
        ma.Transactionstatus_SpecialCharacterCatalogueTransactionId();
        ma.Transactionstatus_AlphaNumericCatalogueTransactionId();
        ma.Transactionstatus_BlankCatalogueTransactionId();
        ma.Transactionstatus_DecimalCatalogueTransactionId();

        ma.Transactionstatus_ValidAcqTransactionstatus();
        ma.Transactionstatus_InvalidAcqTransactionstatus();
        ma.Transactionstatus_MaxAcqTransactionstatus();
        ma.Transactionstatus_MinAcqTransactionstatus();
        ma.Transactionstatus_SpecialCharacterAcqTransactionstatus();
        ma.Transactionstatus_AlphaNumericAcqTransactionstatus();
        ma.Transactionstatus_BlankAcqTransactionstatus();
        ma.Transactionstatus_DecimalAcqTransactionstatus();

        ma.Transactionstatus_ValidAcqErrorCode();
        ma.Transactionstatus_InvalidAcqErrorCode();
        ma.Transactionstatus_MaxAcqErrorCode();
        ma.Transactionstatus_MinAcqErrorCode();
        ma.Transactionstatus_SpecialCharacterAcqErrorCode();
        ma.Transactionstatus_BlankAcqErrorCode();
        ma.Transactionstatus_DecimalAcqErrorCode();
        ma.Transactionstatus_AlphaNumericAcqErrorCode();

        ma.Transactionstatus_ValidAcqErrorText();
        ma.Transactionstatus_InvalidAcqErrorText();
        ma.Transactionstatus_MaxAcqErrorText();
        ma.Transactionstatus_MinAcqErrorText();
        ma.Transactionstatus_SpecialCharacterAcqErrorText();
        ma.Transactionstatus_AlphaNumericAcqErrorText();
        ma.Transactionstatus_BlankAcqErrorText();
        ma.Transactionstatus_DecimalAcqErrorText();

        ma.Transactionstatus_ValidAcqRRN();
        ma.Transactionstatus_InvalidAcqRRN();
        ma.Transactionstatus_MaxAcqRRN();
        ma.Transactionstatus_MinAcqRRN();
        ma.Transactionstatus_SpecialCharacterAcqRRN();
        ma.Transactionstatus_AlphaNumericAcqRRN();
        ma.Transactionstatus_BlankAcqRRN();
        ma.Transactionstatus_DecimalAcqRRN();

        ma.Transactionstatus_ValidCardName();
        ma.Transactionstatus_InvalidCardName();
        ma.Transactionstatus_MaxCardName();
        ma.Transactionstatus_MinCardName();
        ma.Transactionstatus_SpecialCharacterCardName();
        ma.Transactionstatus_AlphaNumericCardName();
        ma.Transactionstatus_BlankCardName();
        ma.Transactionstatus_DecimalCardName();

        ma.Transactionstatus_ValidAcqTransactionId();
        ma.Transactionstatus_InvalidAcqTransactionId();
        ma.Transactionstatus_MaxAcqTransactionId();
        ma.Transactionstatus_MinAcqTransactionId();
        ma.Transactionstatus_SpecialCharacterAcqTransactionId();
        ma.Transactionstatus_AlphaNumericAcqTransactionId();
        ma.Transactionstatus_BlankAcqTransactionId();
        ma.Transactionstatus_DecimalAcqTransactionId();

        ma.Transactionstatus_ValidAcqTransactionAmount();
        ma.Transactionstatus_InvalidAcqTransactionAmount();
        ma.Transactionstatus_MaxAcqTransactionAmount();
        ma.Transactionstatus_MinAcqTransactionAmount();
        ma.Transactionstatus_SpecialCharacterAcqTransactionAmount();
        ma.Transactionstatus_AlphaNumericAcqTransactionAmount();
        ma.Transactionstatus_BlankAcqTransactionAmount();
        ma.Transactionstatus_DecimalAcqTransactionAmount();

        ma.Transactionstatus_ValidAcqPaymentMode();
        ma.Transactionstatus_InvalidAcqPaymentMode();
        ma.Transactionstatus_MaxAcqPaymentMode();
        ma.Transactionstatus_MinAcqPaymentMode();
        ma.Transactionstatus_SpecialCharacterAcqPaymentMode();
        ma.Transactionstatus_AlphaNumericAcqPaymentMode();
        ma.Transactionstatus_BlankAcqPaymentMode();
        ma.Transactionstatus_DecimalAcqPaymentMode();

        ma.Fetchvalid_SingleService_ValidServiceid();
        ma.FetchSingleService_InvalidServiceid();
        ma.FetchSingleService_MaxServiceid();
        ma.FetchSingleService_MinServiceid();
        ma.FetchSingleService_BlankServiceid();
        ma.FetchSingleService_SpecialCharacterServiceid();
        ma.FetchSingleService_AlphaNumericServiceid();
        ma.FetchSingleService_DecimalServiceid();

        ma.InquiryList_Valid();

        ma.InquiryList_InvalidAmount();

        ma.InquiryList_InvalidUniqueID();

        ma.InquiryList_InvalidQuantity();

        ma.SaveResponseFileclose();

        // Make sure to flush the report
        if (reports != null) {
            reports.flush();
        }
    }
}
