package com.efive.agencyonline.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
 
public class ZipFiles {
     
    List<String> filesListInDir = new ArrayList<String>();
 
    public static void main(String[] args) {
      //  File file = new File("D:\\testzip");
       // String zipFileName = "/Users/pankaj/sitemap.zip";
         
        File dir = new File("D:\\testzip");
        String zipDirName = "D:\\MyFile.zip";
         
      //  zipSingleFile(file, zipFileName);
         
        ZipFiles zipFiles = new ZipFiles();
        zipFiles.zipDirectory(dir, zipDirName);
    }
 
    public void zipDirectory(File dir, String zipDirName) {
        try {
            populateFilesList(dir);

            FileOutputStream fos = new FileOutputStream(zipDirName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for(String filePath : filesListInDir){
                System.out.println("Zipping "+filePath);
                //for ZipEntry we need to keep only relative file path, so we used substring on absolute path
                ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length()+1, filePath.length()));
                zos.putNextEntry(ze);
                //read the file and write to ZipOutputStream
                FileInputStream fis = new FileInputStream(filePath);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    private void populateFilesList(File dir) throws IOException {
        File[] files = dir.listFiles();
        for(File file : files){
            if(file.isFile()) filesListInDir.add(file.getAbsolutePath());
            else populateFilesList(file);
        }
    }

    private static void zipSingleFile(File file, String zipFileName) {
        try {
            //create ZipOutputStream to write to the zip file
            FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            //add a new Zip Entry to the ZipOutputStream
            ZipEntry ze = new ZipEntry(file.getName());
            zos.putNextEntry(ze);
            //read the file and write to ZipOutputStream
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
             
            //Close the zip entry to write to zip file
            zos.closeEntry();
            //Close resources
            zos.close();
            fis.close();
            fos.close();
            System.out.println(file.getCanonicalPath()+" is zipped to "+zipFileName);
             
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
    
    //following code  for  testing
    public void m1(String zipFile, String srcDir) {
    	
    	try {
            byte[] buffer = new byte[1024];
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            File dir = new File(srcDir);
            File[] files = dir.listFiles();

            for (int i = 0; i < files.length; i++) {
                System.out.println("Adding file: " + files[i].getName());
                FileInputStream fis = new FileInputStream(files[i]);
                // begin writing a new ZIP entry, positions the stream to the start of the entry data

                zos.putNextEntry(new ZipEntry(files[i].getName()));        
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);

                }
                zos.closeEntry();
                // close the InputStream
                fis.close();
            }
            // close the ZipOutputStream
            zos.close();

        }
        catch (IOException ioe) {
            System.out.println("Error creating zip file" + ioe);
        }
    }
    
    
    
    
    
 
}