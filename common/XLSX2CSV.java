package com.efive.agencyonline.agencycommon;

/* ====================================================================
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==================================================================== */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.extractor.XSSFEventBasedExcelExtractor;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
* A rudimentary XLSX -> CSV processor modeled on the
* POI sample program XLS2CSVmra from the package
* org.apache.poi.hssf.eventusermodel.examples.
* As with the HSSF version, this tries to spot missing
*  rows and cells, and output empty entries for them.
* <p>
* Data sheets are read using a SAX parser to keep the
* memory footprint relatively small, so this should be
* able to read enormous workbooks.  The styles table and
* the shared-string table must be kept in memory.  The
* standard POI styles table class is used, but a custom
* (read-only) class is used for the shared string table
* because the standard POI SharedStringsTable grows very
* quickly with the number of unique strings.
* <p>
* For a more advanced implementation of SAX event parsing
* of XLSX files, see {@link XSSFEventBasedExcelExtractor}
* and {@link XSSFSheetXMLHandler}. Note that for many cases,
* it may be possible to simply use those with a custom 
* {@link SheetContentsHandler} and no SAX code needed of
* your own!
*/
public class XLSX2CSV {
 /**
  * Uses the XSSF Event SAX helpers to do most of the work
  *  of parsing the Sheet XML, and outputs the contents
  *  as a (basic) CSV.
  */
 private Map<Integer, ArrayList<String>> sheetData = new LinkedHashMap<Integer, ArrayList<String>>();
 
 private class SheetToCSV implements SheetContentsHandler {
     private boolean firstCellOfRow;
     private int currentRow = -1;
     private int currentCol = -1;
     private ArrayList<String> rowData = null;
     
     private void outputMissingRows(int number) {
         //not required
     }

     @Override
     public void startRow(int rowNum) {
         // If there were gaps, output the missing rows
         outputMissingRows(rowNum-currentRow-1);
         // Prepare for this row
         firstCellOfRow = true;
         currentRow = rowNum;
         currentCol = -1;
     }
     
     @Override
     public void endRow() {
         // Ensure the minimum number of columns
    	 sheetData.put(currentRow, rowData);
    	 rowData = null;
     }

     @Override
     public void cell(String cellReference, String formattedValue) {
         if (firstCellOfRow) {
        	 rowData = new ArrayList<String>();
        	 firstCellOfRow = false;
         }
         // gracefully handle missing CellRef here in a similar way as XSSFCell does
         if(cellReference == null) {
             cellReference = new CellReference(currentRow, currentCol).formatAsString();
         }

         // Did we miss any cells?
         int thisCol = (new CellReference(cellReference)).getCol();
         int missedCols = thisCol - currentCol - 1;
         for (int i=0; i<missedCols; i++) {
             rowData.add("");
         }
         currentCol = thisCol;
         rowData.add(formattedValue);
     }
    

     
	@Override
	public void headerFooter(String arg0, boolean arg1, String arg2) {
		// Not Required
	}
 }


///////////////////////////////////////

 private final OPCPackage xlsxPackage;

 /**
  * Creates a new XLSX -> CSV converter
  *
  * @param pkg        The XLSX package to process
  * @param output     The PrintStream to output the CSV to
  * @param minColumns The minimum number of columns to output, or -1 for no minimum
  */
 public XLSX2CSV(OPCPackage pkg) {
     this.xlsxPackage = pkg;
 }

 /**
  * Parses and shows the content of one sheet
  * using the specified styles and shared-strings tables.
  *
  * @param styles The table of styles that may be referenced by cells in the sheet
  * @param strings The table of strings that may be referenced by cells in the sheet
  * @param sheetInputStream The stream to read the sheet-data from.

  * @exception java.io.IOException An IO exception from the parser,
  *            possibly from a byte stream or character stream
  *            supplied by the application.
  * @throws SAXException if parsing the XML data fails.
  */
 public void processSheet(
         StylesTable styles,
         ReadOnlySharedStringsTable strings,
         SheetContentsHandler sheetHandler, 
         InputStream sheetInputStream) throws IOException, SAXException {
	 
     DataFormatter formatter = new DataFormatter();
     InputSource sheetSource = new InputSource(sheetInputStream);
     try {
         XMLReader sheetParser = XMLReaderFactory.createXMLReader(
					"org.apache.xerces.parsers.SAXParser"
			);
         ContentHandler handler = new XSSFSheetXMLHandler(
               styles, strings, sheetHandler, formatter, false);
         sheetParser.setContentHandler(handler);
         sheetParser.parse(sheetSource);
      } catch(SAXException e) {
         throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
      }
 }

 /**
  * Initiates the processing of the XLS workbook file to CSV.
  *
  * @throws IOException If reading the data from the package fails.
  * @throws SAXException if parsing the XML data fails.
  */
 public void process() throws IOException, OpenXML4JException, SAXException {
     ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
     XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
     StylesTable styles = xssfReader.getStylesTable();
     XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
     int index = 0;
     while (iter.hasNext()) {
         try (InputStream stream = iter.next()) {
             if(index==0)
            	 processSheet(styles, strings, new SheetToCSV(), stream);
         }
         ++index;
     }
 }

	public static Map<Integer, ArrayList<String>> readExcelXlsx(String fileName){
		 
		try {
			
			File xlsxFile = new File(fileName);
		     
			 if (!xlsxFile.exists()) {
		         System.err.println("Not found or not a file: " + xlsxFile.getPath());
		         return null;
		     }
		     
		     // The package open is instantaneous, as it should be.
		     try (OPCPackage p = OPCPackage.open(xlsxFile.getPath())) {
		         XLSX2CSV xlsx2csv = new XLSX2CSV(p);
		         xlsx2csv.process();
		         return xlsx2csv.sheetData;
		     }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	 }
	
}