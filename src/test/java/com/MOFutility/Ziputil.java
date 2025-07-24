package com.MOFutility;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Ziputil {
	 public static void zipDirectory(String sourceDirPath, String zipFilePath) throws IOException {
	        try {
	            File dir = new File(sourceDirPath);
	            FileOutputStream fos = new FileOutputStream(zipFilePath);
	            ZipOutputStream zipOut = new ZipOutputStream(fos);

	            // Get all files from the directory
	            File[] files = dir.listFiles();
	            for (File file : files) {
	                if (file.isFile()) {
	                    zipFile(file, zipOut);
	                }
	            }

	            zipOut.close();
	            fos.close();
	            System.out.println("Zipped directory: " + zipFilePath);
	        } catch (IOException e) {
	            System.out.println("Error while zipping: " + e.getMessage());
	        }
	    }

	    private static void zipFile(File fileToZip, ZipOutputStream zipOut) throws IOException {
	        FileInputStream fis = new FileInputStream(fileToZip);
	        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
	        zipOut.putNextEntry(zipEntry);
	        byte[] bytes = new byte[1024];
	        int length;
	        while ((length = fis.read(bytes)) >= 0) {
	            zipOut.write(bytes, 0, length);
	        }
	        zipOut.closeEntry();
	        fis.close();
	    }
	}


