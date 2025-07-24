package com.MOF.constants;

import com.MOFutility.ExcelUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.executionCore.Engine;

public class Webelements {
	
	
	

	// COMMON XPATHS
	public static final String savebtnARAB = "//button[text()='حفظ']";
	public static final String savebtn = "//button[text()='Save']";
	public static final String chnagepwdsavebtn = "//label[text()='Save']";
	
	public static final String Reset = "//a[text()='Reset']";
	public static final String viewARAB = "//label[text()='عرض	']";
	
	public static final String download = "//button[@name='download']";

	public static final String Servicedownload = "//button[text()='Download']";

	public static final String lang = "//select[@name='lang']";
	public static final String last = "	//label[text()='Last']";
	public static final String ResetARAB = "//a[text()='إعادة الضبط']";
	public static final String nextbtn = "//label[text()='Next']";
	public static final String nextbtnARAB = "//label[text()='التالي']";
	public static final String ENGlang = "//option[text()='En']";
	
	public static final String logo = "/html/body/div[1]/ul/a";
	
	// LOGIN MODULE
	public static final String InputUsername = "//input[@id='exampleInputUsername']";
	public static final String Inputpassword = "//input[@id='exampleInputPassword']";
	public static final String togglePassword = "//i[@id='togglePassword']";
	public static final String ENG_Loginbtn = "//*[@id=\"login\"]/a/label";
	public static final String changelabelLang = "//select[@name='lang']";
	public static final String changeArabLang = "//option[text()='عربى']";
	public static final String ARAB_Loginbtn = "//*[@id=\"login\"]/a/label";
	// ROLE MODULE ARAB
	public static final String RoleTabARAB = "//span[text()='الصلاحيات']";

	// ROLE MODULE ENG
	public static final String RoleTab = "//span[text()='Role']";
	public static final String EnterRoleName = "//input[@id='roleNameValue']";
	public static final String RoleAccess = "(//span[@class='checkmark'])[1]";
	public static final String UserAccess = "(//span[@class='checkmark'])[2]";
	public static final String EntityAccess = "(//span[@class='checkmark'])[3]";
	public static final String ServiceAccess = "(//span[@class='checkmark'])[4]";
	public static final String GroupAccess = "(//span[@class='checkmark'])[5]";
	public static final String ReportAccess = "(//span[@class='checkmark'])[6]";
	public static final String AuditAccess = "(//span[@class='checkmark'])[7]";

	public static final String firstbtn = "//label[text()='First']";
	public static final String firstbtnARAB = "//label[text()='أولاً']";
	// ROLE EDIT AND DELETE
	public static final String roleeditbtn = "//a[@href='/servicecatalogueportal/role/7']";

	public static final String roleeditbtnArab = "//a[@href='/servicecatalogueportal/role/7']";
	public static final String roleDeletebtn = "//a[@href='/servicecatalogueportal/role/delete/7']";

	public static final String roleDeletebtnARAB =  "//a[@href='/servicecatalogueportal/role/delete/7']";




	
	//ENTITY EDIT AND DELETE
	public static final String Entityeditbtn = "//a[@href='/servicecatalogueportal/editEntity/111111']";
	public static final String EntityDeletebtn1 = "//a[@href='/servicecatalogueportal/deleteEntity/111111']";
	public static final String EntityDeletebtn2 = "//a[@href='/servicecatalogueportal/deleteEntity/222222']";
	public static final String EntityDeletebtn3 = "//a[@href='/servicecatalogueportal/deleteEntity/333333']";
	public static final String EntityDeletebtn4 = "//a[@href='/servicecatalogueportal/deleteEntity/444444']";
	public static final String EntityDeletebtn = "//a[@href='/servicecatalogueportal/deleteEntity/111111']";
	
	
	//Group View And Delete
	public static final String GroupDeletebtn ="//a[@href='/servicecatalogueportal/group/delete/1222']";
	
	public static final String GroupViewbtn ="//tr[td/span[normalize-space(text())='"+ExcelUtilities.groupcodecolumn+"']]//a[contains(@href, '/group/') and img[contains(normalize-space(@title), 'View')]]";
	public static final String groupsrvdeletebtn4 = "//button[@formaction='/servicecatalogueportal/group/remove/service/111111']";
	public static final String groupsrvdeletebtn3 = "//button[@formaction='/servicecatalogueportal/group/remove/service/222222']";
	public static final String groupsrvdeletebtn2 = "//button[@formaction='/servicecatalogueportal/group/remove/service/333333']";
	public static final String groupsrvdeletebtn1 = "//button[@formaction='/servicecatalogueportal/group/remove/service/444444']";
	//SERVICE EDIT AND DELETE And View 
	
	public static final String serviceDeletebtn1="//a[@href='/servicecatalogueportal/service/deleteServiceValue/30022022']";
	public static final String serviceDeletebtn2="//a[@href='/servicecatalogueportal/service/deleteServiceValue/30022023']";
	public static final String serviceDeletebtn3="//a[@href='/servicecatalogueportal/service/deleteServiceValue/30022024']";
	public static final String serviceDeletebtn4="//a[@href='/servicecatalogueportal/service/deleteServiceValue/30022025']";
	public static final String srvcView="//a[@href='/servicecatalogueportal/viewServiceValue/777']";
	public static final String srvcedit="//a[@href='/servicecatalogueportal/editServiceValue/777']";
	public static final String sndforReview="//a[@href='/servicecatalogueportal/service/review/777']";

	public static final String sndforReview1="//a[@href='/servicecatalogueportal/service/review/30022022']";
	public static final String sndforReview2="//a[@href='/servicecatalogueportal/service/review/30022023']";
	public static final String sndforReview3="//a[@href='/servicecatalogueportal/service/review/30022024']";
	public static final String sndforReview4="//a[@href='/servicecatalogueportal/service/review/30022025']";
	public static final String approve="//a[@href='/servicecatalogueportal/service/approve/777']";
	public static final String approve1="//a[@href='/servicecatalogueportal/service/approve/30022022']";

	public static final String approve2="//a[@href='/servicecatalogueportal/service/approve/30022023']";

	public static final String approve3="//a[@href='/servicecatalogueportal/service/approve/30022024']";
	public static final String approve4="//a[@href='/servicecatalogueportal/service/approve/30022025']";
	public static final String reject="//a[@href='/servicecatalogueportal/service/reject/777']";

	public static final String rejectREASON="//input[@name='notification']";

	public static final String mocydelete="//img[@src='/servicecatalogueportal/img/delete.svg']";

	public static final String cmt="//input[@name='notification']";
	public static final String sndbckbtn="//button[@data-confirm-rework='Are you sure to Send Back?']";
	// SERVICE
	public static final String approvebtn="//button[@data-confirm-rework='Are you sure to Approve?']";
	public static final String servicetabbtnARAB = "//span[text()='الخدمات']";
	public static final String servicetabbtn = "//span[text()='Service']";
	public static final String serviceId = "//input[@id='serviceId']";
	public static final String entitydrop = "//select[@id='entity']";
	public static final String ServiceGLCode = "//input[@id='serviceGlCode']";
	public static final String PGACode = "//input[@id='pgaCode']";
	public static final String ServiceNameEnglish = "//input[@placeholder='Enter Service Name in English']";
	public static final String ServiceNameArabic = "//input[@placeholder='Enter Service Name in Arabic']";
	public static final String Category = "//select[@id='serviceCategories']";
	public static final String SubCategory = "//select[@id='serviceSubCategories']";
	public static final String ServiceType = "//select[@id='serviceTypesForService']";
	public static final String ServicePrice = "//input[@id='servicePrice']";
	public static final String PricePercent = "//input[@id='pricePercent']";
	public static final String MinimumPrice = "//input[@id='minimumPrice']";
	public static final String MaximumPrice = "//input[@id='maximumPrice']";
	public static final String UnitPrice = "//input[@id='unitPrice']";
	public static final String MaximumQuantity = "//input[@id='maximumQuantity']";
	public static final String ServiceVAT = "//select[@id='vatValuesServices']";
	public static final String ServiceStatus = "//select[@id='serviceStatusForServices']";
	public static final String effstrtdte = "//input[@name='start']";
	public static final String effenddte = "//input[@name='end']";
	public static final String servchk = "//input[@placeholder='Service ID ']";
	public static final String servsrh = "//button[text()='Search']";

	public static final String MOCYcreateservicebtn = "//a[text()='Create Service']";

	// USER
	public static final String USERtab = "//span[text()='User']";
	public static final String UserFullname = "//input[@placeholder='Enter Full Name']";
	public static final String Username1 = "//input[@placeholder='Enter Username']";
	public static final String Password1 = "//input[@placeholder='Enter Password']";
	public static final String togglepwd = "//i[@id='togglePassword']";
	public static final String confirmPassword = "//input[@placeholder='Enter Confirm Password']";
	public static final String toggleconfirmPwd = "//i[@id='toggleConfirmPassword']";

	public static final String Mismatch = "//button[@value='Mismatch Report']";

	public static final String emailId = "//input[@placeholder='Enter Email ID ']";
	public static final String contactNo = "//input[@placeholder='Enter Contact Number']";
	public static final String checkmark1 = "(//span[@class='checkmark'])[1]";
	public static final String selectuserRole = "//select[@id='role']";
	public static final String checkmark2 = "(//span[@class='checkmark'])[2]";

	public static final String ReqID = "//input[@name='requestid']";
	public static final String Click_download_button = "//a[text()='Download Report']";
	
	// USER EDIT @ Delete
	public static final String userEdit = "//a[@href=/servicecatalogueportal/user/8]";
	public static final String EditUsername = "//a[@href=/servicecatalogueportal/user/8]";
	public static final String EditPassword = "//a[@href=/servicecatalogueportal/user/8]";
	public static final String EditRole = "//a[@href=/servicecatalogueportal/user/8]";
	public static final String EditEmailID = "//a[@href=/servicecatalogueportal/user/8]";
	public static final String EditCheckerbox = "//a[@href=/servicecatalogueportal/user/8]";
	public static final String EditDeselectCheckerbox = "//a[@href=/servicecatalogueportal/user/8]";
	public static final String EditUserActiveCheckbox = "//a[@href=/servicecatalogueportal/user/8]";
	public static final String userDeletebtn ="//a[@href=/servicecatalogueportal/user/8]";

	// ENTITY
	public static final String EntityTab = "//span[text()='Entity']";
	public static final String entityID = "//input[@id='entityId']";
	public static final String entityGLcode = "//input[@id='entityGlCode']";
	public static final String entitynameEng = "//input[@id='entityNameEng']";
	public static final String entitynameArab = "//input[@id='entityNameArab']";
	public static final String entityCatagory = "//select[@id='entityCategories']";
	public static final String entityType = "//select[@id='entityType']";
	public static final String EntityTabARAB = "//span[text()='الجهات']";

	public static final String mocyCreateservicebtn = "//a[text()='Create Service']";


	// GROUP
	
	public static final String grouptab = "//span[text()='Group']";
	public static final String groupcode = "//input[@id='serviceGroupCodeValue']";
	public static final String groupEngName = "//input[@name='serviceGroupName']";
	public static final String groupArabName = "//input[@name='serviceGroupNameArabic']";
	public static final String groupEntity = "//select[@id='entityId']";
	public static final String groupservice = "//select[@name='serviceId']";
	public static final String groupserviceadd = "//i[@class='fas fa-plus fa-sm fa-fw']";
	
	public static final String grouptabARAB = "//span[text()='باقة الخدمات']";
	// MIS
	public static final String MIStab = "//span[text()='MIS']";
    public static final String View = "//button[@name='submit']";
    public static final String MISviewArbbtn = "//button[text()='عرض	']";
	public static final String viewMISAR = "//a[contains(@class, 'nav-link') and span='ادارة نظام المعلومات']";
	public static final String MisDownloadbtn = "//button[text()='Download']";
	public static final String MISDownloadArbbtn = "//*[@id=\"content\"]/div/div[2]/div[2]/div/div[2]/div/form/button";
	public static final String MISReportbtn ="//button[text()='MIS Report']";
	public static final String MISReportbtnARAB ="//button[@value='إنشاء MIS']";
	
	
	
	//AUDIT
	public static final String AuditTab = "//span[text()='Audit']";
	public static final String AuditTabArab = "//span[text()='التدقيق والمراجعة']";
	public static final String AuditModule = "//select[@name='modules']";
	
	

	// LOGOUT
	//public static final String userLogout = "//img[@src='/servicecatalogueportal/img/undraw_profile.svg']";

	public static final String userLogout = "//span[text()='Logout']";
	public static final String userLogoutbtnENG = "//label[text()='Logout']";
	public static final String userLogoutbtnARAB = "//label[text()='Ø§Ù„Ø®Ø±ÙˆØ¬']";
	public static final String alertLogoutbtnENG = "(//span[text()='Logout'])[2]";
	public static final String alertLogoutbtnARAB = "(//span[text()='Ø§Ù„Ø®Ø±ÙˆØ¬'])[2]";
	public static final String AuthlogoutENG = "//span[text()='Logout']";
	public static final String AuthlogoutARAB = "//span[text()='Ø§Ù„Ø®Ø±ÙˆØ¬']";
	
	//Change_Password
	public static final String ChangePassword = "//label[text()='Change Password']";
    public static final String changepwdsavebtn = "//a[text()='Save']";
	public static final String ChangePassword1 = "//span[text()='Change Password']";
	public static final String oldPassword = "//input[@id='oldPassword']";
	public static final String oldPassword1 = "//input[@id='exampleInputOldPassword']";
	public static final String loginchngUsername = "//input[@id='exampleInputUsername']";
	public static final String toggleOldPassword = "//i[@id='toggleOldPassword']";
	public static final String password = "//input[@id='password']";
	public static final String toggleNewPassword = "//i[@id='toggleNewPassword']";
	public static final String confirmchngPassword = "//input[@id='confirmPassword']";
	public static final String toggleConfirmPassword = "//i[@id='toggleConfirmPassword']";
	public static final String loginsbt = "//label[text()='Login']";
	public static final String confirmchngPassword1 ="//input[@id='exampleInputNewPassword']";
	public static final String eyeoldpwd ="//i[@id='togglePassword']";
	public static final String eyeNewpwd ="//i[@id='toggleNewPassword']";
	public static final String cnfrimpwd1 ="//input[@id='exampleInputConfirmNewPassword']";
	public static final String eyecnfrimpwd1 ="//i[@id='toggleConfirmNewPassword']";
	public static final String chngpwdsave ="//label[text()='Save']";
	
	
	//ARABIC


}
