package com.MOFutility;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.model.Test;
import com.relevantcodes.extentreports.view.ScreenshotHtml;

public class Screenshot {
	

	 public static String captureScreenShot(WebDriver driver, String ScreenshotName) {
		 
		    TakesScreenshot ts = (TakesScreenshot) driver;
		    Date d = new Date();
		    String fileName = ScreenshotName + d.toString().replace(":", "_").replace(" ", "_") + ".png";

		    // Ensure the directory exists
		    String directoryPath = "C:\\Users\\roshanbabu\\Desktop\\AUTO_SS\\";
		    File dir = new File(directoryPath);
		    if (!dir.exists()) {
		        dir.mkdirs();
		    }

		    // Save the screenshot in the correct path
		    String imagePath = directoryPath + fileName;
		    File srcFile = ts.getScreenshotAs(OutputType.FILE);
		    File destFile = new File(imagePath);
		    try {
		        FileUtils.copyFile(srcFile, destFile);
		        System.out.println("Screenshot saved at: " + imagePath); // Log the saved path for debugging
		    } catch (IOException e) {
		        System.out.println("Error while saving screenshot: " + e.getMessage());
		        return null;  // Return null in case of failure
		    }

		    return imagePath;
		}
	 
	  public static String encodeImageToBase64(String imagePath) {
	        try {
	            File file = new File(imagePath);
	            byte[] fileContent = Files.readAllBytes(file.toPath());
	            return Base64.getEncoder().encodeToString(fileContent);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	  
	

}


