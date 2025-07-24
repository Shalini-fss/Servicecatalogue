package com.executionCore;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.MOF.constants.Constant;

import com.MOFservice.MOF_Service;
import com.MOFutility.*;

public class Engine {
	
	
	MOF_Service mofService;
	
	Method[] methods;

	public Engine() {
		
		mofService = new MOF_Service();

		methods = mofService.getClass().getMethods();


	}


	public void executekeywords() throws Exception {

		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equalsIgnoreCase(

					ExcelUtilities.keywordColumnValue)) {
				methods[i].invoke(mofService);
				break;

			}
		}
	}

	public static void main(String[] args) throws Exception {
		try {

			ExcelUtilities executionUtilities = new ExcelUtilities();
			// ExcelUtilities.readExcelfile(Constant.EXCEL_LOCATION);
			executionUtilities.readExcelfile(Constant.EXCEL_LOCATION, Constant.EXECUTIONSHEET, true);
			Engine engine = new Engine();

			for (int row = 1; row <= ExcelUtilities.moduleTotalRows; row++) {

				executionUtilities.getExecutionData(row, Constant.MODULE_COLUMN, Constant.MODULEFLAG_COLUMN);

				if (ExcelUtilities.moduleExecutionValue.contains("TRUE")) {

					executionUtilities.readExcelfile(Constant.EXCEL_LOCATION, ExcelUtilities.moduleValue, false);

					for (int datarow = 1; datarow <= ExcelUtilities.totalRows; datarow++) {

						// to do if keyword column is blank will not be executed
						if (ExcelUtilities.keywordColumnValue != null && ExcelUtilities.keywordColumnValue == "") {
							break;
						}

						executionUtilities.getLocatorsKeywordsAndData(datarow, Constant.KEYWORD_COLUMN,
								Constant.DATA_COLUMN, Constant.EXECUTION_COLUMN, Constant.USERNAME_COLUMN,
										Constant.PASSWORD_COLUMN, Constant.ENTER_ROLENAME, Constant.SERVICEID_COLUMN,
								Constant.ENTITYDROP_COLUMN, Constant.ServiceGLCodeColumn, Constant.PGACodeColumn,
								Constant.ServiceNameEnglishColumn, Constant.ServiceNameArabicColumn,
								Constant.CategoryColumn, Constant.ServiceTypeColumn, Constant.ServicePriceColumn,
								Constant.PricePercentColumn, Constant.MinimumPriceColumn, Constant.MaximumPriceColumn,
								Constant.UnitPriceColumn, Constant.MaximumQuantityColumn, Constant.ServiceVATColumn,		
								Constant.ServiceStatusColumn, Constant.FULLNAME, Constant.USERNAME1, Constant.PASSWORD1,
								Constant.CONFIRM_PASSWORD, Constant.EMAILID, Constant.CONTACTNUMBER, Constant.ROLE,
								Constant.enterEntityID, Constant.enterEntityGLcode, Constant.enterEntityNameENG,
								Constant.enterEntityNameARAB, Constant.selectEntityCatagory, Constant.selectEntityType,
								Constant.groupcode, Constant.groupEngName, Constant.groupArabName, Constant.groupEntity,
								Constant.groupservice1, Constant.groupservice2, Constant.groupservice3,
								Constant.groupservice4, Constant.AuditMODULE, Constant.LOGIN_CHNG_USERNAME,
								Constant.OLDPASSWORD, Constant.NEWPASSWORD, Constant.CONFIRMPASSWORD,
								Constant.VERIFYGRID, Constant.TESTCASEDESCRIPTION_COLUMN, Constant.MODULENAME_COLUMN,
								Constant.usernamevaluecolumn ,Constant.oldpasswordvaluecolumn,
								Constant.newpasswordvaluecolumn,Constant.confirmpasswordvaluecolumn,
								Constant.USER_SERVICE_ID,Constant.USER_SERVICE_PRICE,Constant.USER_MAX_QUANTITY);
						// Constant.SubCategoryColumn
						if (ExcelUtilities.executioncolumnvalue.contains("TRUE")) {

							engine.executekeywords();

						}
					}
				}

			}
		} catch (InvocationTargetException e) {
			e.getCause().printStackTrace();
		}

	}
}