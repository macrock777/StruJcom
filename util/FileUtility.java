package com.efive.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.efive.agencyonline.common.EfiveAction;

public class FileUtility  extends EfiveAction{
	
	
// File Upload To server
	public static String uploadFile(File fileObj, String currentFilename ,String dirName, String  filenameSaveAs){
		  try{
				  File theDir =null;
				  String extention  =null;
				  String uploadpath=null;
				  HttpServletRequest request = ServletActionContext.getRequest();
				  String serverPath = request.getRealPath("/");			// or Contex path 
				  
				  	if(null!=fileObj  && fileObj.length()>0){
					  		extention = currentFilename.substring(currentFilename.lastIndexOf("."));
					  		uploadpath = "uploadcontent/"+dirName+"/"+filenameSaveAs+ extention;
					  		
					  	// Now Check Is Dir   Exist  
					  		theDir =  new File("uploadcontent/"+dirName+"/");
					  		if (!theDir.exists()) 
					  					theDir.mkdir();	 	// It will create Directories according to  'theDir' path 
					  		
					  		// Now Check is File already Exist
						  		File  f = new File(uploadpath);
						  		if(f.exists()){	// if File is Exist   then  Existing  file will be delete
									f.delete();			
								}
						  		
						  		//Now File Copy in Dir 
						  		File srcFile = new File(request.getRealPath(uploadpath));
						  		try {
						  				FileUtils.copyFile(fileObj,srcFile);		// File will copy source obj  to Destination Dir
						  				return uploadpath;
									} catch (IOException e) {
										e.printStackTrace();
									}
				  	}
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  return null;
	  }
	
	
	//Delete File  from server
	public boolean fileDelete(File fileObj){
		try{
			if(fileObj.exists())		// if File is Exist   then  Existing  file will be delete
					fileObj.delete();			
					
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	// File Rename
	// File Move  to another Dir
	

}
