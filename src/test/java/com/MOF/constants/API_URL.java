package com.MOF.constants;

import com.MOFutility.API_Excelutilities;

public class API_URL extends API_Excelutilities {

	// Define the file path for the Excel sheet
	public static String FilepathAPI = "D:\\Automation\\Automation test data\\API_MOF_TEST_DATA.xlsx";

	// Define constants with default values
	public static String url;
	public static String ValidEntityid;
	public static String InvalidEntityid;
	public static String MaxEntityid;
	public static String MinEntityid;
	public static String SpecialCharacterEntityID;
	public static String AlphaNumericEntityID;
	public static String BlankEntityID;
	public static String DecimalEntityID;

	public static String ValidServiceid;
	public static String InvalidServiceid;
	public static String MaxServiceid;
	public static String MinServiceid;
	public static String BlankServiceid;
	public static String SpecialCharacterServiceid;
	public static String AlphaNumericServiceid;
	public static String DecimalServiceid;

	public static String ValidUniqueID;
	public static String InvalidUniqueID;
	public static String MaxUniqueID;
	public static String MinUniqueID;
	public static String BlankUniqueID;
	public static String SpecialCharacterUniqueID;
	public static String AlphaNumericUniqueID;
	public static String DecimalUniqueID;

	public static String ValidQuantity;
	public static String InvalidQuantity;
	public static String MaxQuantity;
	public static String MinQuantity;
	public static String BlankQuantity;
	public static String SpecialCharacterQuantity;
	public static String AlphaNumericQuantity;
	public static String DecimalQuantity;

	public static String ValidAmount;
	public static String InvalidAmount;
	public static String MaxAmount;
	public static String MinAmount;
	public static String BlankAmount;
	public static String SpecialCharacterAmount;
	public static String AlphaNumericAmount;
	public static String DecimalAmount;

	public static String ValidServiceidDynamic;

	public static String ValidCatalogueTransactionId;
	public static String InvalidCatalogueTransactionId;
	public static String MaxCatalogueTransactionId;
	public static String MinCatalogueTransactionId;
	public static String SpecialCharacterCatalogueTransactionId;
	public static String AlphaNumericCatalogueTransactionId;
	public static String BlankCatalogueTransactionId;
	public static String DecimalCatalogueTransactionId;

	public static String ValidAcqTransactionStatus;
	public static String InvalidAcqTransactionStatus;
	public static String MaxAcqTransactionStatus;
	public static String MinAcqTransactionStatus;
	public static String SpecialCharacterAcqTransactionStatus;
	public static String AlphaNumericAcqTransactionStatus;
	public static String BlankAcqTransactionStatus;
	public static String DecimalAcqTransactionStatus;

	public static String ValidAcqErrorCode;
	public static String InvalidAcqErrorCode;
	public static String MaxAcqErrorCode;
	public static String MinAcqErrorCode;
	public static String SpecialCharacterAcqErrorCode;
	public static String AlphaNumericAcqErrorCode;
	public static String BlankAcqErrorCode;
	public static String DecimalAcqErrorCode;

	public static String ValidAcqErrorText;
	public static String InvalidAcqErrorText;
	public static String MaxAcqErrorText;
	public static String MinAcqErrorText;
	public static String SpecialCharacterAcqErrorText;
	public static String AlphaNumericAcqErrorText;
	public static String BlankAcqErrorText;
	public static String DecimalAcqErrorText;

	public static String ValidAcqRRN;
	public static String InvalidAcqRRN;
	public static String MaxAcqRRN;
	public static String MinAcqRRN;
	public static String SpecialCharacterAcqRRN;
	public static String AlphaNumericAcqRRN;
	public static String BlankAcqRRN;
	public static String DecimalAcqRRN;

	public static String ValidCardName;
	public static String InvalidCardName;
	public static String MaxCardName;
	public static String MinCardName;
	public static String SpecialCharacterCardName;
	public static String AlphaNumericCardName;
	public static String BlankCardName;
	public static String DecimalCardName;

	public static String ValidAcqTransactionId;
	public static String InvalidAcqTransactionId;
	public static String MaxAcqTransactionId;
	public static String MinAcqTransactionId;
	public static String SpecialCharacterAcqTransactionId;
	public static String AlphaNumericAcqTransactionId;
	public static String BlankAcqTransactionId;
	public static String DecimalAcqTransactionId;

	public static String ValidAcqTransactionAmount;
	public static String InvalidAcqTransactionAmount;
	public static String MaxAcqTransactionAmount;
	public static String MinAcqTransactionAmount;
	public static String SpecialCharacterAcqTransactionAmount;
	public static String AlphaNumericAcqTransactionAmount;
	public static String BlankAcqTransactionAmount;
	public static String DecimalAcqTransactionAmount;

	public static String ValidAcqPaymentMode;
	public static String InvalidAcqPaymentMode;
	public static String MaxAcqPaymentMode;
	public static String MinAcqPaymentMode;
	public static String SpecialCharacterAcqPaymentMode;
	public static String AlphaNumericAcqPaymentMode;
	public static String BlankAcqPaymentMode;
	public static String DecimalAcqPaymentMode;



	// Static initialization block to read values from Excel
	static {
		try {
			url = readServiceIdFromExcel(FilepathAPI, "API", 0, 1);
			ValidEntityid = readServiceIdFromExcel(FilepathAPI, "API", 1, 1);
			InvalidEntityid = readServiceIdFromExcel(FilepathAPI, "API", 2, 1);
			MaxEntityid = readServiceIdFromExcel(FilepathAPI, "API", 3, 1);
			MinEntityid = readServiceIdFromExcel(FilepathAPI, "API", 4, 1);
			SpecialCharacterEntityID = readServiceIdFromExcel(FilepathAPI, "API", 5, 1);
			AlphaNumericEntityID = readServiceIdFromExcel(FilepathAPI, "API", 6, 1);
			BlankEntityID = readServiceIdFromExcel(FilepathAPI, "API", 7, 1);
			DecimalEntityID = readServiceIdFromExcel(FilepathAPI, "API", 8, 1);

			ValidServiceid = readServiceIdFromExcel(FilepathAPI, "API", 9, 1);
			InvalidServiceid = readServiceIdFromExcel(FilepathAPI, "API", 10, 1);
			MaxServiceid = readServiceIdFromExcel(FilepathAPI, "API", 11, 1);
			MinServiceid = readServiceIdFromExcel(FilepathAPI, "API", 12, 1);
			BlankServiceid = readServiceIdFromExcel(FilepathAPI, "API", 13, 1);
			SpecialCharacterServiceid = readServiceIdFromExcel(FilepathAPI, "API", 14, 1);
			AlphaNumericServiceid = readServiceIdFromExcel(FilepathAPI, "API", 15, 1);
			DecimalServiceid = readServiceIdFromExcel(FilepathAPI, "API", 16, 1);

			ValidUniqueID = readServiceIdFromExcel(FilepathAPI, "API", 17, 1);
			InvalidUniqueID = readServiceIdFromExcel(FilepathAPI, "API", 18, 1);
			MaxUniqueID = readServiceIdFromExcel(FilepathAPI, "API", 19, 1);
			MinUniqueID = readServiceIdFromExcel(FilepathAPI, "API", 20, 1);
			BlankUniqueID = readServiceIdFromExcel(FilepathAPI, "API", 21, 1);
			SpecialCharacterUniqueID = readServiceIdFromExcel(FilepathAPI, "API", 22, 1);
			AlphaNumericUniqueID = readServiceIdFromExcel(FilepathAPI, "API", 23, 1);
			DecimalUniqueID = readServiceIdFromExcel(FilepathAPI, "API", 24, 1);

			ValidQuantity = readServiceIdFromExcel(FilepathAPI, "API", 25, 1);
			InvalidQuantity = readServiceIdFromExcel(FilepathAPI, "API", 26, 1);
			MaxQuantity = readServiceIdFromExcel(FilepathAPI, "API", 27, 1);
			MinQuantity = readServiceIdFromExcel(FilepathAPI, "API", 28, 1);
			BlankQuantity = readServiceIdFromExcel(FilepathAPI, "API", 29, 1);
			SpecialCharacterQuantity = readServiceIdFromExcel(FilepathAPI, "API", 30, 1);
			AlphaNumericQuantity = readServiceIdFromExcel(FilepathAPI, "API", 31, 1);
			DecimalQuantity = readServiceIdFromExcel(FilepathAPI, "API", 32, 1);

			ValidAmount = readServiceIdFromExcel(FilepathAPI, "API", 33, 1);
			InvalidAmount = readServiceIdFromExcel(FilepathAPI, "API", 34, 1);
			MaxAmount = readServiceIdFromExcel(FilepathAPI, "API", 35, 1);
			MinAmount = readServiceIdFromExcel(FilepathAPI, "API", 36, 1);
			BlankAmount = readServiceIdFromExcel(FilepathAPI, "API", 37, 1);
			SpecialCharacterAmount = readServiceIdFromExcel(FilepathAPI, "API", 38, 1);
			AlphaNumericAmount = readServiceIdFromExcel(FilepathAPI, "API", 39, 1);
			DecimalAmount = readServiceIdFromExcel(FilepathAPI, "API", 40, 1);

			ValidServiceidDynamic= readServiceIdFromExcel(FilepathAPI, "API", 41, 1);

			ValidCatalogueTransactionId = readServiceIdFromExcel(FilepathAPI, "API", 42, 1);
			System.out.println("Reading from row 42, column 1: " + readServiceIdFromExcel(FilepathAPI, "API", 42, 1));

			InvalidCatalogueTransactionId = readServiceIdFromExcel(FilepathAPI, "API", 43, 1);
			MaxCatalogueTransactionId = readServiceIdFromExcel(FilepathAPI, "API", 44, 1);
			MinCatalogueTransactionId = readServiceIdFromExcel(FilepathAPI, "API", 45, 1);
			SpecialCharacterCatalogueTransactionId = readServiceIdFromExcel(FilepathAPI, "API", 46, 1);
			AlphaNumericCatalogueTransactionId = readServiceIdFromExcel(FilepathAPI, "API", 47, 1);
			BlankCatalogueTransactionId = readServiceIdFromExcel(FilepathAPI, "API", 48, 1);
			DecimalCatalogueTransactionId = readServiceIdFromExcel(FilepathAPI, "API", 49, 1);

			ValidAcqTransactionStatus = readServiceIdFromExcel(FilepathAPI, "API", 50, 1);
			InvalidAcqTransactionStatus = readServiceIdFromExcel(FilepathAPI, "API", 51, 1);
			MaxAcqTransactionStatus = readServiceIdFromExcel(FilepathAPI, "API", 52, 1);
			MinAcqTransactionStatus = readServiceIdFromExcel(FilepathAPI, "API", 53, 1);
			SpecialCharacterAcqTransactionStatus = readServiceIdFromExcel(FilepathAPI, "API", 54, 1);
			AlphaNumericAcqTransactionStatus = readServiceIdFromExcel(FilepathAPI, "API", 55, 1);
			BlankAcqTransactionStatus = readServiceIdFromExcel(FilepathAPI, "API", 56, 1);
			DecimalAcqTransactionStatus = readServiceIdFromExcel(FilepathAPI, "API", 57, 1);

			ValidAcqErrorCode = readServiceIdFromExcel(FilepathAPI, "API", 58, 1);
			InvalidAcqErrorCode = readServiceIdFromExcel(FilepathAPI, "API", 59, 1);
			MaxAcqErrorCode = readServiceIdFromExcel(FilepathAPI, "API", 60, 1);
			MinAcqErrorCode = readServiceIdFromExcel(FilepathAPI, "API", 61, 1);
			SpecialCharacterAcqErrorCode = readServiceIdFromExcel(FilepathAPI, "API", 62, 1);
			AlphaNumericAcqErrorCode = readServiceIdFromExcel(FilepathAPI, "API", 63, 1);
			BlankAcqErrorCode = readServiceIdFromExcel(FilepathAPI, "API", 64, 1);
			DecimalAcqErrorCode = readServiceIdFromExcel(FilepathAPI, "API", 65, 1);

			ValidAcqErrorText = readServiceIdFromExcel(FilepathAPI, "API", 66, 1);
			InvalidAcqErrorText = readServiceIdFromExcel(FilepathAPI, "API", 67, 1);
			MaxAcqErrorText = readServiceIdFromExcel(FilepathAPI, "API", 68, 1);
			MinAcqErrorText = readServiceIdFromExcel(FilepathAPI, "API", 69, 1);
			SpecialCharacterAcqErrorText = readServiceIdFromExcel(FilepathAPI, "API", 70, 1);
			AlphaNumericAcqErrorText = readServiceIdFromExcel(FilepathAPI, "API", 71, 1);
			BlankAcqErrorText = readServiceIdFromExcel(FilepathAPI, "API", 72, 1);
			DecimalAcqErrorText = readServiceIdFromExcel(FilepathAPI, "API", 73, 1);

			ValidAcqRRN = readServiceIdFromExcel(FilepathAPI, "API", 74, 1);
			InvalidAcqRRN = readServiceIdFromExcel(FilepathAPI, "API", 75, 1);
			MaxAcqRRN = readServiceIdFromExcel(FilepathAPI, "API", 76, 1);
			MinAcqRRN = readServiceIdFromExcel(FilepathAPI, "API", 77, 1);
			SpecialCharacterAcqRRN = readServiceIdFromExcel(FilepathAPI, "API", 78, 1);
			AlphaNumericAcqRRN = readServiceIdFromExcel(FilepathAPI, "API", 79, 1);
			BlankAcqRRN = readServiceIdFromExcel(FilepathAPI, "API", 80, 1);
			DecimalAcqRRN = readServiceIdFromExcel(FilepathAPI, "API", 81, 1);

			ValidCardName = readServiceIdFromExcel(FilepathAPI, "API", 82, 1);
			InvalidCardName = readServiceIdFromExcel(FilepathAPI, "API", 83, 1);
			MaxCardName = readServiceIdFromExcel(FilepathAPI, "API", 84, 1);
			MinCardName = readServiceIdFromExcel(FilepathAPI, "API", 85, 1);
			SpecialCharacterCardName = readServiceIdFromExcel(FilepathAPI, "API", 86, 1);
			AlphaNumericCardName = readServiceIdFromExcel(FilepathAPI, "API", 87, 1);
			BlankCardName = readServiceIdFromExcel(FilepathAPI, "API", 88, 1);
			DecimalCardName = readServiceIdFromExcel(FilepathAPI, "API", 89, 1);

			ValidAcqTransactionId = readServiceIdFromExcel(FilepathAPI, "API", 90, 1);
			InvalidAcqTransactionId = readServiceIdFromExcel(FilepathAPI, "API", 91, 1);
			MaxAcqTransactionId = readServiceIdFromExcel(FilepathAPI, "API", 92, 1);
			MinAcqTransactionId = readServiceIdFromExcel(FilepathAPI, "API", 93, 1);
			SpecialCharacterAcqTransactionId = readServiceIdFromExcel(FilepathAPI, "API", 94, 1);
			AlphaNumericAcqTransactionId = readServiceIdFromExcel(FilepathAPI, "API", 95, 1);
			BlankAcqTransactionId = readServiceIdFromExcel(FilepathAPI, "API", 96, 1);
			DecimalAcqTransactionId = readServiceIdFromExcel(FilepathAPI, "API", 97, 1);

			ValidAcqTransactionAmount = readServiceIdFromExcel(FilepathAPI, "API", 98, 1);
			InvalidAcqTransactionAmount = readServiceIdFromExcel(FilepathAPI, "API", 99, 1);
			MaxAcqTransactionAmount = readServiceIdFromExcel(FilepathAPI, "API", 100, 1);
			MinAcqTransactionAmount = readServiceIdFromExcel(FilepathAPI, "API", 101, 1);
			SpecialCharacterAcqTransactionAmount = readServiceIdFromExcel(FilepathAPI, "API", 102, 1);
			AlphaNumericAcqTransactionAmount = readServiceIdFromExcel(FilepathAPI, "API", 103, 1);
			BlankAcqTransactionAmount = readServiceIdFromExcel(FilepathAPI, "API", 104, 1);
			DecimalAcqTransactionAmount = readServiceIdFromExcel(FilepathAPI, "API", 105, 1);

			ValidAcqPaymentMode = readServiceIdFromExcel(FilepathAPI, "API", 106, 1);
			InvalidAcqPaymentMode = readServiceIdFromExcel(FilepathAPI, "API", 107, 1);
			MaxAcqPaymentMode = readServiceIdFromExcel(FilepathAPI, "API", 108, 1);
			MinAcqPaymentMode = readServiceIdFromExcel(FilepathAPI, "API", 109, 1);
			SpecialCharacterAcqPaymentMode = readServiceIdFromExcel(FilepathAPI, "API", 110, 1);
			AlphaNumericAcqPaymentMode = readServiceIdFromExcel(FilepathAPI, "API", 111, 1);
			BlankAcqPaymentMode = readServiceIdFromExcel(FilepathAPI, "API", 112, 1);
			DecimalAcqPaymentMode = readServiceIdFromExcel(FilepathAPI, "API", 113, 1);


		} catch (Exception e) {
			// Log the exception and provide default values
			System.err.println("Error initializing API_URL constants: " + e.getMessage());
			e.printStackTrace();

			// Provide default values to avoid NullPointerException
			url = "http://default-url.com";
			ValidEntityid = "default-valid-entity-id";
			InvalidEntityid = "default-invalid-entity-id";
			MaxEntityid = "default-max-entity-id";
			MinEntityid = "default-min-entity-id";
			SpecialCharacterEntityID = "default-special-char-entity-id";
			AlphaNumericEntityID = "default-alphanumeric-entity-id";
			BlankEntityID = "default-blank-entity-id";
			DecimalEntityID = "default-decimal-entity-id";

			ValidServiceid = "default-valid-service-id";
			InvalidServiceid = "default-invalid-service-id";
			MaxServiceid = "default-max-service-id";
			MinServiceid = "default-min-service-id";
			BlankServiceid = "default-blank-service-id";
			SpecialCharacterServiceid = "default-special-char-service-id";
			AlphaNumericServiceid = "default-alphanumeric-service-id";
			DecimalServiceid = "default-decimal-service-id";

			ValidUniqueID = "default-valid-unique-id";
			InvalidUniqueID = "default-invalid-unique-id";
			MaxUniqueID = "default-max-unique-id";
			MinUniqueID = "default-min-unique-id";
			BlankUniqueID = "default-blank-unique-id";
			SpecialCharacterUniqueID = "default-special-char-unique-id";
			AlphaNumericUniqueID = "default-alphanumeric-unique-id";
			DecimalUniqueID = "default-decimal-unique-id";

			ValidQuantity = "default-valid-quantity";
			InvalidQuantity = "default-invalid-quantity";
			MaxQuantity = "default-max-quantity";
			MinQuantity = "default-min-quantity";
			BlankQuantity = "default-blank-quantity";
			SpecialCharacterQuantity = "default-special-char-quantity";
			AlphaNumericQuantity = "default-alphanumeric-quantity";
			DecimalQuantity = "default-decimal-quantity";

			ValidAmount = "default-valid-amount";
			InvalidAmount = "default-invalid-amount";
			MaxAmount = "default-max-amount";
			MinAmount = "default-min-amount";
			BlankAmount = "default-blank-amount";
			SpecialCharacterAmount = "default-special-char-amount";
			AlphaNumericAmount = "default-alphanumeric-amount";
			DecimalAmount = "default-decimal-amount";

			ValidCatalogueTransactionId = "default-valid-catalogue-transaction-id";
			InvalidCatalogueTransactionId = "default-invalid-catalogue-transaction-id";
			MaxCatalogueTransactionId = "default-max-catalogue-transaction-id";
			MinCatalogueTransactionId = "default-min-catalogue-transaction-id";
			SpecialCharacterCatalogueTransactionId = "default-special-char-catalogue-transaction-id";
			AlphaNumericCatalogueTransactionId = "default-alphanumeric-catalogue-transaction-id";
			BlankCatalogueTransactionId = "default-blank-catalogue-transaction-id";
			DecimalCatalogueTransactionId = "default-decimal-catalogue-transaction-id";

			ValidAcqTransactionStatus = "default-valid-acq-transaction-status";
			InvalidAcqTransactionStatus = "default-invalid-acq-transaction-status";
			MaxAcqTransactionStatus = "default-max-acq-transaction-status";
			MinAcqTransactionStatus = "default-min-acq-transaction-status";
			SpecialCharacterAcqTransactionStatus = "default-special-char-acq-transaction-status";
			AlphaNumericAcqTransactionStatus = "default-alphanumeric-acq-transaction-status";
			BlankAcqTransactionStatus = "default-blank-acq-transaction-status";
			DecimalAcqTransactionStatus = "default-decimal-acq-transaction-status";

			ValidAcqErrorCode = "default-valid-acq-error-code";
			InvalidAcqErrorCode = "default-invalid-acq-error-code";
			MaxAcqErrorCode = "default-max-acq-error-code";
			MinAcqErrorCode = "default-min-acq-error-code";
			SpecialCharacterAcqErrorCode = "default-special-char-acq-error-code";
			AlphaNumericAcqErrorCode = "default-alphanumeric-acq-error-code";
			BlankAcqErrorCode = "default-blank-acq-error-code";
			DecimalAcqErrorCode = "default-decimal-acq-error-code";

			ValidAcqErrorText = "default-valid-acq-error-text";
			InvalidAcqErrorText = "default-invalid-acq-error-text";
			MaxAcqErrorText = "default-max-acq-error-text";
			MinAcqErrorText = "default-min-acq-error-text";
			SpecialCharacterAcqErrorText = "default-special-char-acq-error-text";
			AlphaNumericAcqErrorText = "default-alphanumeric-acq-error-text";
			BlankAcqErrorText = "default-blank-acq-error-text";
			DecimalAcqErrorText = "default-decimal-acq-error-text";

			ValidAcqRRN = "default-valid-acq-rrn";
			InvalidAcqRRN = "default-invalid-acq-rrn";
			MaxAcqRRN = "default-max-acq-rrn";
			MinAcqRRN = "default-min-acq-rrn";
			SpecialCharacterAcqRRN = "default-special-char-acq-rrn";
			AlphaNumericAcqRRN = "default-alphanumeric-acq-rrn";
			BlankAcqRRN = "default-blank-acq-rrn";
			DecimalAcqRRN = "default-decimal-acq-rrn";

			ValidCardName = "default-valid-card-name";
			InvalidCardName = "default-invalid-card-name";
			MaxCardName = "default-max-card-name";
			MinCardName = "default-min-card-name";
			SpecialCharacterCardName = "default-special-char-card-name";
			AlphaNumericCardName = "default-alphanumeric-card-name";
			BlankCardName = "default-blank-card-name";
			DecimalCardName = "default-decimal-card-name";

			ValidAcqTransactionId = "default-valid-acq-transaction-id";
			InvalidAcqTransactionId = "default-invalid-acq-transaction-id";
			MaxAcqTransactionId = "default-max-acq-transaction-id";
			MinAcqTransactionId = "default-min-acq-transaction-id";
			SpecialCharacterAcqTransactionId = "default-special-char-acq-transaction-id";
			AlphaNumericAcqTransactionId = "default-alphanumeric-acq-transaction-id";
			BlankAcqTransactionId = "default-blank-acq-transaction-id";
			DecimalAcqTransactionId = "default-decimal-acq-transaction-id";

			ValidAcqTransactionAmount = "default-valid-acq-transaction-amount";
			InvalidAcqTransactionAmount = "default-invalid-acq-transaction-amount";
			MaxAcqTransactionAmount = "default-max-acq-transaction-amount";
			MinAcqTransactionAmount = "default-min-acq-transaction-amount";
			SpecialCharacterAcqTransactionAmount = "default-special-char-acq-transaction-amount";
			AlphaNumericAcqTransactionAmount = "default-alphanumeric-acq-transaction-amount";
			BlankAcqTransactionAmount = "default-blank-acq-transaction-amount";
			DecimalAcqTransactionAmount = "default-decimal-acq-transaction-amount";

			ValidAcqPaymentMode = "default-valid-acq-payment-mode";
			InvalidAcqPaymentMode = "default-invalid-acq-payment-mode";
			MaxAcqPaymentMode = "default-max-acq-payment-mode";
			MinAcqPaymentMode = "default-min-acq-payment-mode";
			SpecialCharacterAcqPaymentMode = "default-special-char-acq-payment-mode";
			AlphaNumericAcqPaymentMode = "default-alphanumeric-acq-payment-mode";
			BlankAcqPaymentMode = "default-blank-acq-payment-mode";
			DecimalAcqPaymentMode = "default-decimal-acq-payment-mode";


			ValidServiceidDynamic = "default-valid-service-id-Dynamic";
		}
	}
}
