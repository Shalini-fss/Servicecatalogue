package com.MOFutility;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class JSONresponse {

	public void resposefile() throws Exception {
		Date d = new Date();
		String FileName = d.toString().replace(":", "_").replace(" ", "_") + ".txt";

		FileWriter file = new FileWriter(
				"D:\\Automation\\EDB JSON\\" + " " + FileName + ".txt");
		
		
	}

}
