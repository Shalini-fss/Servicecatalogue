package com.MOFutility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.MOF.constants.Constant;

public class ExcelUtilities {

	static FileInputStream file;
	XSSFWorkbook workBook;
	XSSFSheet sheet;
	XSSFSheet moduleSheet;
	XSSFCell cell;
	public static String VERIFYGRIDcolumn;
	public static String locatorColumnValue;
	public static String rolenameColumnValue;
	public static String locatorName;
	public static String locatorValue;
	public static String keywordColumnValue;
	public static String dataColumnValue;
	public static String usernameColumnValue;
	public static String passwordColumnValue;
	public static String executioncolumnvalue;
	public static int totalRows;
	public static String serviceIDcolumn;
	public static String entitydropcolumn;
	public static String ServiceGLCodecolumn;
	public static String PGACodecolumn;
	public static String ServiceNameEnglishcolumn;
	public static String ServiceNameArabiccolumn;
	public static String Categorycolumn;
	public static String SubCategorycolumn;
	public static String ServiceTypecolumn;
	public static String ServicePricecolumn;
	public static String PricePercentcolumn;
	public static String MinimumPricecolumn;
	public static String MaximumPricecolumn;
	public static String UnitPricecolumn;
	public static String MaximumQuantitycolumn;
	public static String ServiceVATcolumn;
	public static String ServiceStatuscolumn;
	public static String fullnamecolumn;
	public static String username1column;
	public static String password1column;
	public static String confirm_passwordcolumn;
	public static String emailidcolumn;
	public static String contactnumbercolumn;
	public static String rolecolumn;
	public static String enterEntityIDcolumn;
	public static String enterEntityGLcodecolumn;
	public static String enterEntityNameENGcolumn;
	public static String enterEntityNameARABcolumn;
	public static String selectEntityCatagorycolumn;
	public static String selectEntityTypecolumn;
	public static String grouptabcolumn;
	public static String groupcodecolumn;
	public static String groupEngNamecolumn;
	public static String groupArabNamecolumn;
	public static String groupEntitycolumn;
	public static String groupServicecolumn1;
	public static String groupServicecolumn2;
	public static String groupServicecolumn3;
	public static String groupServicecolumn4;
	public static String AuditmoduleColumn;
	public static String LOGIN_CHNG_USERNAMEcolumn;
	public static String OLDPASSWORDcolumn;
	public static String NEWPASSWORDcolumn;
	public static String CONFIRMPASSWORDcolumn;
	public static String ModuleNamecolumnvalue;
	public static String testCaseDesritpioncolumnvalue;
	public static String modulenamecolumnvalue;
	public static String user_login_chng_usernamevalue ;
	public static String user_oldpasswordvalue ;
	public static String user_newpasswordvalue ;
	public static String user_confirmpasswordvalue ;

	public static String userserviceIDcolumn;
	public static String userServicePricecolumn;
	public static String userMaximumQuantitycolumn;
	

	public static String role;

	public static String moduleValue;
	public static String moduleExecutionValue;
	public static int moduleTotalRows;

	public void getExecutionData(int row, int moduleColumn, int flagColumn) {

		moduleValue = moduleSheet.getRow(row).getCell(moduleColumn).toString().trim();
		moduleExecutionValue = moduleSheet.getRow(row).getCell(flagColumn).toString().trim();

		if (moduleValue == "" || moduleExecutionValue == "") {
			moduleValue = "NA";
			moduleExecutionValue = "NA";
		}

		System.out.println("ModuleValue : " + moduleValue + " ExecutionValue :" + moduleExecutionValue);
	}
	
	


	public void readExcelfile(String location, String worksheet, Boolean moduleLoc_f) throws IOException {
		FileInputStream file = new FileInputStream(location);
		workBook = new XSSFWorkbook(file);

		if (moduleLoc_f) {
			moduleSheet = workBook.getSheet(worksheet);
			moduleTotalRows = moduleSheet.getLastRowNum();
		} 
		else {
			sheet = workBook.getSheet(worksheet);
			totalRows = sheet.getLastRowNum();
		}
	}

	public void getLocatorsKeywordsAndData(int row, int keyWordcolumn, int Datacolumn, int Executioncolumn,
			int usernamecolumn, int passwordcolumn, int rolenamecolumn, int serviceID, int entitydrop,
			int ServiceGLCode, int PGACode, int ServiceNameEnglish, int ServiceNameArabic, int Category,
			int ServiceType, int ServicePrice, int PricePercent, int MinimumPrice, int MaximumPrice, int UnitPrice,
			int MaximumQuantity, int ServiceVAT, int ServiceStatus, int fullname, int username1, int password1,
			int confirm_password, int emailid, int contactnumber, int role, int enterEntityID, int enterEntityGLcode,
			int enterEntityNameENG, int enterEntityNameARAB, int selectEntityCatagory, int selectEntityType,
			int groupcode, int groupEngName, int groupArabName, int groupEntity, int groupservice1, int groupservice2,
			int groupservice3, int groupservice4, int auditmodule, int LOGINCHNGUSERNAME, int OLDPASSWORD,
			int NEWPASSWORD, int CONFIRMPASSWORD, int VERIFYGRID, int testCaseDesritpion, int modulename , int usernamevalue ,
			int oldpasswordvalue,int newpasswordvalue,int confirmpasswordvalue , int user_serviceIDcolumn, int user_ServicePricecolumn ,
										   int user_MaximumQuantitycolumn ) {
		// int SubCategory
		// cell = sheet.getRow(row).getCell(column);
		// String cellvalue = cell.getStringCellValue();
		
//		if(moduleValue.contains("ROLE"))
//		 {
//		 System.out.println("ROLE ");
//		
//		 }
		executioncolumnvalue = sheet.getRow(row).getCell(Executioncolumn).toString().trim();
		keywordColumnValue = sheet.getRow(row).getCell(keyWordcolumn).toString().trim();
		dataColumnValue = sheet.getRow(row).getCell(Datacolumn).toString().trim();
		usernameColumnValue = sheet.getRow(row).getCell(usernamecolumn).toString().trim();
		passwordColumnValue = sheet.getRow(row).getCell(passwordcolumn).toString().trim();

		if (moduleValue.contains("CHANGE PASSWORD")) {
			LOGIN_CHNG_USERNAMEcolumn = sheet.getRow(row).getCell(LOGINCHNGUSERNAME).toString().trim();
			OLDPASSWORDcolumn = sheet.getRow(row).getCell(OLDPASSWORD).toString().trim();
			NEWPASSWORDcolumn = sheet.getRow(row).getCell(NEWPASSWORD).toString().trim();
			CONFIRMPASSWORDcolumn = sheet.getRow(row).getCell(CONFIRMPASSWORD).toString().trim();
			testCaseDesritpioncolumnvalue = sheet.getRow(row).getCell(testCaseDesritpion).toString().trim();
			modulenamecolumnvalue = sheet.getRow(row).getCell(modulename).toString().trim();
			System.out.println("   Keyword :" + keywordColumnValue + " Test Case description : " +testCaseDesritpioncolumnvalue);
		}

		if (moduleValue.contains("AUDIT")) {

			AuditmoduleColumn = sheet.getRow(row).getCell(auditmodule).toString().trim();
			VERIFYGRIDcolumn = sheet.getRow(row).getCell(VERIFYGRID).toString().trim();
			testCaseDesritpioncolumnvalue = sheet.getRow(row).getCell(testCaseDesritpion).toString().trim();
			modulenamecolumnvalue = sheet.getRow(row).getCell(modulename).toString().trim();
			System.out.println("   Keyword :" + keywordColumnValue + " Execution Flag: " + executioncolumnvalue
					+ " Data :" + dataColumnValue );

		}

		if (moduleValue.contains("MIS")) {
			testCaseDesritpioncolumnvalue = sheet.getRow(row).getCell(testCaseDesritpion).toString().trim();
			modulenamecolumnvalue = sheet.getRow(row).getCell(modulename).toString().trim();
			System.out.println("   Keyword :" + keywordColumnValue + " Execution Flag: " + executioncolumnvalue
					+ " Data :" + dataColumnValue + " UserName : " + usernameColumnValue + " Password :"
					+ passwordColumnValue);

		}

		if (moduleValue.contains("GROUP")) {

			groupcodecolumn = sheet.getRow(row).getCell(groupcode).toString().trim();
			groupEngNamecolumn = sheet.getRow(row).getCell(groupEngName).toString().trim();
			groupArabNamecolumn = sheet.getRow(row).getCell(groupArabName).toString().trim();
			groupEntitycolumn = sheet.getRow(row).getCell(groupEntity).toString().trim();
			groupServicecolumn1 = sheet.getRow(row).getCell(groupservice1).toString().trim();
			groupServicecolumn2 = sheet.getRow(row).getCell(groupservice2).toString().trim();
			groupServicecolumn3 = sheet.getRow(row).getCell(groupservice3).toString().trim();
			groupServicecolumn4 = sheet.getRow(row).getCell(groupservice4).toString().trim();
			testCaseDesritpioncolumnvalue = sheet.getRow(row).getCell(testCaseDesritpion).toString().trim();
			modulenamecolumnvalue = sheet.getRow(row).getCell(modulename).toString().trim();
			System.out.println(
					"   Keyword :" + keywordColumnValue + " Execution Flag: " + executioncolumnvalue + " Data :"
							+ dataColumnValue + " Groupcode:" + groupcodecolumn + "GroupEntity :" + groupEntitycolumn);

		}

		if (moduleValue.contains("ENTITY")) {
			
			enterEntityIDcolumn = sheet.getRow(row).getCell(enterEntityID).toString().trim();
			enterEntityGLcodecolumn = sheet.getRow(row).getCell(enterEntityGLcode).toString().trim();
			enterEntityNameENGcolumn = sheet.getRow(row).getCell(enterEntityNameENG).toString().trim();
			enterEntityNameARABcolumn = sheet.getRow(row).getCell(enterEntityNameARAB).toString().trim();
			selectEntityCatagorycolumn = sheet.getRow(row).getCell(selectEntityCatagory).toString().trim();
			selectEntityTypecolumn = sheet.getRow(row).getCell(selectEntityType).toString().trim();
			testCaseDesritpioncolumnvalue = sheet.getRow(row).getCell(testCaseDesritpion).toString().trim();
			modulenamecolumnvalue = sheet.getRow(row).getCell(modulename).toString().trim();
			System.out.println("   Keyword :" + keywordColumnValue + " Execution Flag: " + executioncolumnvalue
					+ " Data :" + dataColumnValue + " Entity ID:" + enterEntityIDcolumn + "EntityCatagory :"
					+ selectEntityCatagorycolumn);
		}

		if (moduleValue.contains("USER")) {
			usernameColumnValue = sheet.getRow(row).getCell(usernamecolumn).toString().trim();
			passwordColumnValue = sheet.getRow(row).getCell(passwordcolumn).toString().trim();
			fullnamecolumn = sheet.getRow(row).getCell(fullname).toString().trim();
			username1column = sheet.getRow(row).getCell(username1).toString().trim();
			password1column = sheet.getRow(row).getCell(password1).toString().trim();
			confirm_passwordcolumn = sheet.getRow(row).getCell(confirm_password).toString().trim();
			emailidcolumn = sheet.getRow(row).getCell(emailid).toString().trim();
			contactnumbercolumn = sheet.getRow(row).getCell(contactnumber).toString().trim();
			rolecolumn = sheet.getRow(row).getCell(role).toString().trim();
			testCaseDesritpioncolumnvalue = sheet.getRow(row).getCell(testCaseDesritpion).toString().trim();
			modulenamecolumnvalue = sheet.getRow(row).getCell(modulename).toString().trim();
			
			user_login_chng_usernamevalue= sheet.getRow(row).getCell(usernamevalue ).toString().trim();
			user_oldpasswordvalue = sheet.getRow(row).getCell(oldpasswordvalue).toString().trim();
			user_newpasswordvalue = sheet.getRow(row).getCell(newpasswordvalue).toString().trim();
			user_confirmpasswordvalue = sheet.getRow(row).getCell(confirmpasswordvalue).toString().trim();
			userserviceIDcolumn = sheet.getRow(row).getCell(serviceID).toString().trim();
			userServicePricecolumn = sheet.getRow(row).getCell(ServicePrice).toString().trim();
			userMaximumQuantitycolumn = sheet.getRow(row).getCell(MaximumQuantity).toString().trim();
			
			System.out.println("   Keyword :" + keywordColumnValue + " Execution Flag: " + executioncolumnvalue
					+ " Data :" + dataColumnValue + " UserName:" + username1column + "E-MailID :" + emailidcolumn);

		}

		if (moduleValue.contains("SERVICE")) {
			serviceIDcolumn = sheet.getRow(row).getCell(serviceID).toString().trim();
			entitydropcolumn = sheet.getRow(row).getCell(entitydrop).toString().trim();
			ServiceGLCodecolumn = sheet.getRow(row).getCell(ServiceGLCode).toString().trim();
			PGACodecolumn = sheet.getRow(row).getCell(PGACode).toString().trim();
			ServiceNameEnglishcolumn = sheet.getRow(row).getCell(ServiceNameEnglish).toString().trim();
			ServiceNameArabiccolumn = sheet.getRow(row).getCell(ServiceNameArabic).toString().trim();
			Categorycolumn = sheet.getRow(row).getCell(Category).toString().trim();
			// SubCategorycolumn =
			// sheet.getRow(row).getCell(SubCategory).toString().trim();
			ServiceTypecolumn = sheet.getRow(row).getCell(ServiceType).toString().trim();
			ServicePricecolumn = sheet.getRow(row).getCell(ServicePrice).toString().trim();
			PricePercentcolumn = sheet.getRow(row).getCell(PricePercent).toString().trim();
			MinimumPricecolumn = sheet.getRow(row).getCell(MinimumPrice).toString().trim();
			MaximumPricecolumn = sheet.getRow(row).getCell(MaximumPrice).toString().trim();
			UnitPricecolumn = sheet.getRow(row).getCell(UnitPrice).toString().trim();
			MaximumQuantitycolumn = sheet.getRow(row).getCell(MaximumQuantity).toString().trim();
			ServiceVATcolumn = sheet.getRow(row).getCell(ServiceVAT).toString().trim();
			ServiceStatuscolumn = sheet.getRow(row).getCell(ServiceStatus).toString().trim();
			testCaseDesritpioncolumnvalue = sheet.getRow(row).getCell(testCaseDesritpion).toString().trim();
			modulenamecolumnvalue = sheet.getRow(row).getCell(modulename).toString().trim();

			System.out.println("   Keyword :" + keywordColumnValue + " Execution Flag: " + executioncolumnvalue
					+ " Data :" + dataColumnValue + " UserName : " + usernameColumnValue + " Password: "
					+ passwordColumnValue + " ServiceID:" + serviceIDcolumn);

		}

		if (moduleValue.contains("ROLE")) {

			rolenameColumnValue = sheet.getRow(row).getCell(rolenamecolumn).toString().trim();
			testCaseDesritpioncolumnvalue = sheet.getRow(row).getCell(testCaseDesritpion).toString().trim();
			modulenamecolumnvalue = sheet.getRow(row).getCell(modulename).toString().trim();
			System.out.println("   Keyword :" + keywordColumnValue + " Execution Flag: " + executioncolumnvalue
					+ " Data :" + dataColumnValue + " UserName : " + usernameColumnValue + " Password : "
					+ passwordColumnValue + "ROLENAME :" + rolenameColumnValue);
		}
		if (moduleValue.contains("LOGIN")) {
			testCaseDesritpioncolumnvalue = sheet.getRow(row).getCell(testCaseDesritpion).toString().trim();
			modulenamecolumnvalue = sheet.getRow(row).getCell(modulename).toString().trim();
			keywordColumnValue = sheet.getRow(row).getCell(keyWordcolumn).toString().trim();
			dataColumnValue = sheet.getRow(row).getCell(Datacolumn).toString().trim();
			usernameColumnValue = sheet.getRow(row).getCell(usernamecolumn).toString().trim();
			passwordColumnValue = sheet.getRow(row).getCell(passwordcolumn).toString().trim();
			System.out.println("   Keyword :" + keywordColumnValue + " Execution Flag: " + executioncolumnvalue
					+ " Data :" + dataColumnValue + " UserName : " + usernameColumnValue + " Password :"
					+ passwordColumnValue);
		}

	}

}
