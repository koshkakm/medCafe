/*
 *  Copyright 2008 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mitre.medcafe.util;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

/**
 *  Class to allow for saving of the information in each of the widgets
 *
 * @author     ghamilton	
 * @created    Feb 24, 2010
 */
public class TextProcesses
{
   private static DbConnection dbConn;
     
   private HashMap<String,Text> textList = new HashMap<String, Text>();
   
   public TextProcesses()
   {
	   
   }
   
   public void init()
   {
	   try {
		   System.out.println("SaveData: init. Start " );
			if (dbConn == null)
				dbConn= new DbConnection();
			
			System.out.println("SaveData: init. Got connection " );
			
		} 
	     catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} 
   }
   
   public Text getTextObject(String title) throws SQLException
   {
	   Text textObj = textList.get(title);
	   
	   return textObj;
   }
   
   public Text populateTextObject(String userid,  String patientId, String title) throws SQLException
   {
	   init();
	   System.out.println("SaveData: populateTextObjects: start "  );
	 		
	   Text textObj =  new Text(userid, patientId);
	   PreparedStatement prep = dbConn.prepareStatement(Text.SAVE_TEXT_SELECT);
	   
	   prep.setString(1, userid);
	   prep.setString(2, patientId);
	   prep.setString(3, title);
	   System.out.println("SaveData: InsertText: select query  " + prep.toString() );
		
	   ResultSet rs = prep.executeQuery();
	   if (rs.next())
	   {
		   System.out.println("SaveData: populateTextObjects: result set "  );
		 	
		   String note = rs.getString(1);
		   textObj =  new Text(userid, patientId, title, note);
		  
	   }
		
	   return textObj;
   }
   
   public TextTemplate populateTemplateObject(String userid,  String title) throws SQLException
   {
	   init();
	   System.out.println("SaveData: populateTextObjects: start "  );
	 		
	   TextTemplate templateObj =  new TextTemplate(userid);
	   PreparedStatement prep = dbConn.prepareStatement(TextTemplate.SAVE_TEXT_TEMPLATE_SELECT);
	   
	   prep.setString(1, userid);
	   prep.setString(2, title);
	   System.out.println("TextProcesses: populateTemplateObject: select query  " + prep.toString() );
		
	   ResultSet rs = prep.executeQuery();
	   if (rs.next())
	   {
		   System.out.println("TextProcesses: populateTemplateObject: result set "  );
		 	
		   String note = rs.getString(1);
		   templateObj =  new TextTemplate(userid, title,  note);
		  
	   }
		
	   return templateObj;
   }
   
   public HashMap<String,Text> populateTextObjects(String userid,  String patientId) throws SQLException
   {
	   init();
	   System.out.println("SaveData: populateTextObjects: start "  );
	 		
	   Text textObj =  new Text(userid, patientId);
	   PreparedStatement prep = dbConn.prepareStatement(Text.SAVE_TEXTS_SELECT);
	   
	   prep.setString(1, userid);
	   prep.setString(2, patientId);
	   System.out.println("SaveData: InsertText: select query  " + prep.toString() );
		
	   ResultSet rs = prep.executeQuery();
	   while (rs.next())
	   {
		   System.out.println("SaveData: populateTextObjects: result set "  );
		 	
		   String title = rs.getString(1);
		   String note = rs.getString(2);
		   textObj =  new Text(userid, patientId, title, note);
		   textList.put(title, textObj);
	   }
		
	   return textList;
   }
   
   public HashMap<String,TextTemplate> populateTemplateObjects(String userid) throws SQLException
   {
	   HashMap<String,TextTemplate> templateList = new HashMap<String, TextTemplate>();
	   
	   init();
	   System.out.println("TextProcesses: populateTemplateObjects: start "  );
	 		
	   TextTemplate templateObj =  new TextTemplate(userid);
	   PreparedStatement prep = dbConn.prepareStatement(TextTemplate.SAVE_TEXT_TEMPLATES_SELECT);
	   
	   prep.setString(1, userid);
	   System.out.println("TextProcesses: populateTemplateObjects: select query  " + prep.toString() );
		
	   ResultSet rs = prep.executeQuery();
	   while (rs.next())
	   {
		   System.out.println("TextProcesses: populateTemplateObjects: result set "  );
		 	
		   String title = rs.getString(1);
		   String note = rs.getString(2);
		   templateObj =  new TextTemplate(userid,  title, note);
		   templateList.put(title, templateObj);
	   }
		
	   return templateList;
   }
   
   public void saveText(String userid,  String patientId, String title, String text) throws SQLException
   {
	   init();
	   System.out.println("SaveData: SaveText: start "  );
	   System.out.println("SaveData: SaveText: title "  + title );
			
	   PreparedStatement prep = dbConn.prepareStatement(Text.SAVE_TEXT_SELECT_CNT);
	   
	   prep.setString(1, userid);
	   prep.setString(2, patientId);
	   prep.setString(3, title);
	   int numberOfRecords = 0;
	   System.out.println("SaveData: InsertText: select query  " + prep.toString() );
		
	   ResultSet rs = prep.executeQuery();
	   if (rs.next())
	   {
		   numberOfRecords = rs.getInt(1);
	   }
	   System.out.println("SaveData: InsertText: number of records  " + numberOfRecords );
		
	   if (numberOfRecords == 0)
		   insertText(userid, patientId, title, text);
	   else
		   updateText(userid, patientId, title, text);
   }
   
   
   private void insertText(String userid,  String patientId, String title, String text) throws SQLException
   {
	   init();
	   System.out.println("SaveData: InsertText: start "  );
		
	   PreparedStatement prep = dbConn.prepareStatement(Text.SAVE_TEXT_INSERT);
	   prep.setString(1, userid);
	   prep.setString(2, patientId);
	   prep.setString(3, title);
	   prep.setString(4, text);
	   System.out.println("SaveData: InsertText: insert query  " + prep.toString() );
		   
	   int noUpdated = prep.executeUpdate();
	   System.out.println("SaveData: InsertText: number updated  " + noUpdated );
   }
   
   private void updateText(String userid,  String patientId, String title, String text) throws SQLException
   {
	   init();
	   System.out.println("SaveData: UpdateText: start "  );
		
	   PreparedStatement prep = dbConn.prepareStatement(Text.SAVE_TEXT_UPDATE);

	   prep.setString(1, text);
	   prep.setString(2, userid);
	   prep.setString(3, patientId);
	   prep.setString(4, title);
	   System.out.println("SaveData: UpdateText: update query  " + prep.toString() );
		   
	   int noUpdated = prep.executeUpdate();
	   System.out.println("SaveData: UpdateText: update: number updated  " + noUpdated );
   }
   
   public void deleteText(String userid,  String patientId, String title, String text) throws SQLException
   {
	   init();
	   System.out.println("SaveData: DeleteText: start "  );
		
	   PreparedStatement prep = dbConn.prepareStatement(Text.SAVE_TEXT_DELETE);

	   prep.setString(1, userid);
	   prep.setString(2, patientId);
	   prep.setString(3, title);
	   System.out.println("SaveData: DeleteText: delete query  " + prep.toString() );
		   
	   int noUpdated = prep.executeUpdate();
	   System.out.println("SaveData: DeleteText: delete: number updated  " + noUpdated );
   }
}

