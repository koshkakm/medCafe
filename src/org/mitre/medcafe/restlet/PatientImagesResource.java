package org.mitre.medcafe.restlet;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.json.JSONObject;
import org.mitre.medcafe.util.Config;
import org.mitre.medcafe.util.Constants;
import org.mitre.medcafe.util.ImageProcesses;
import org.mitre.medcafe.util.Repository;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.ResourceException;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;


public class PatientImagesResource extends ServerResource {

	 /** The underlying Item object. */
    //Patient item;

    /** The sequence of characters that identifies the resource. */
    private String id;
    String repository;
    
    private final static String PATIENT_ID = "id";
    public final static String KEY = PatientImagesResource.class.getName();
    public final static Logger log = Logger.getLogger( KEY );
    
    protected Date startDate = new Date();
    protected Date endDate =  new Date();
    
    static{log.setLevel(Level.FINER);}
    
    protected void doInit() throws ResourceException {
        // Get the "type" attribute value taken from the URI template
        Form form = getRequest().getResourceRef().getQueryAsForm();
        id = (String)getRequest().getAttributes().get(PATIENT_ID);
        System.out.println("PatientImageResource JSON init patientId " +  id );
        
        String startDateStr = form.getFirstValue("start_date");
        if (startDateStr == null)
        	startDateStr = "01/01/1950";
        		
        String endDateStr = form.getFirstValue("end_date");
        if (endDateStr == null)
        	endDateStr = "01/01/2012";
          	
        	
        System.out.println("PatientImageResource JSON init startDate " +  startDateStr + " endDate " + endDateStr );
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
			startDate = df.parse(startDateStr);
			endDate = df.parse(endDateStr);
        } 
        catch (ParseException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    @Get("html")
    public Representation toHtml(){
    	
    	System.out.println("Found PatientResource html ");
        
    	StringBuffer startBuf = new StringBuffer();
    	StringBuffer patientImages = new StringBuffer();
    	StringBuffer endBuf = new StringBuffer();
    	
    	//<img src="imgs/cover1.jpg" alt="The Beatles - Abbey Road"/>
    	
    	String[] values = new String[]{this.id,"", "", " ", "",
				"", "", " ","","", "" };

    	String[] images = new String[]{"assessment.png","bloodstat.jpg","cardioReport.gif" ,
    									"chest-xray.jpg", "chest-xray2.jpg","mri.jpg"};
    	String[] imageTitles = new String[]{"Assessment","Blood Stats","Cardio Report", "Chest XRay", "Chest XRay","MRI" };
    	int i=0;
    	
    	String dir = "patients/" + this.id;
    		
    	for (String image: images)
    	{
    	
    		patientImages.append("<img src=\"../" + dir +"/" + image + "\" alt=\"" + imageTitles[i] + "\"/>" );	
    		i++;
    	}
    	return new StringRepresentation( startBuf.toString() + patientImages.toString() 
                 + endBuf.toString()); 
             
    }

   
    @Get("json")
    public JsonRepresentation toJson(){
        try
        {
        	String server = Config.getServerUrl() ;
        	System.out.println("PatientImageResource JSON start");
            
        	String[] imageId = new String[]{"assessment","bloodstat","cardioReport" , "chest-xray", "chest-xray2","mri"};
        	String[] images = new String[]{"assessment.png","bloodstat.jpg","cardioReport.gif" ,
					"chest-xray.jpg", "chest-xray2.jpg","mri.jpg"};
        	String[] imageTitles = new String[]{"Assessment","Blood Stats","Cardio Report", "Chest XRay", "Chest XRay","MRI" };
        	
        	String[] params = new String[]{"assessment.png","bloodstat.jpg","cardioReport.gif", "chest-xray.jpg", "chest-xray2.jpg","mri.jpg"};
        	
        	String[] dates = new String[]{"01/01/2008","02/03/2008","05/07/2008",
        			"06/08/2008", "07/08/2008","10/01/2008"};
        	
        	GregorianCalendar start = new GregorianCalendar();
        	start.setTime(startDate);
        	GregorianCalendar end = new GregorianCalendar();
        	end.setTime(endDate);
        	
        	int i=0;
        	
        	String dir = "patients/" + this.id + "/";
        	String imageDir = "images/" + dir;
        	String tempDir = "../../" +  imageDir;
        	
        	 String imageFileDir = Constants.BASE_PATH + "/" + imageDir;
            JSONObject obj = new JSONObject();
            System.out.println("PatientImageResource JSON start 1");
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            
            for(String image: images)
            {
            	  try {
          			Date imageDate = df.parse(dates[i]);
          			GregorianCalendar imageCal =  new GregorianCalendar();
          			imageCal.setTime(imageDate);
          			
          			//If this date is before the start time
          			if ((imageCal.compareTo(start) < 0))
          			{
          				i++;
          				continue;
          			}
          			
          			//If this date is after the start time
          			if ((imageCal.compareTo(end) > 0))
          			{
          				i++;
          				continue;
          			}
          			
                  } 
                  catch (ParseException e) 
                  {
          			// TODO Auto-generated catch block
          			e.printStackTrace();
          		}
                JSONObject inner_obj = new JSONObject ();
                inner_obj.put("id", imageId[i]);
                inner_obj.put("source", tempDir + image);
                inner_obj.put("name", imageTitles[i]);
                inner_obj.put("param", server + "/" + imageDir +  params[i]);
                obj.append("images", inner_obj);  //append creates an array for you
                System.out.println("PatientImagesResource: toJSON : image directory " + imageFileDir);
                
                i++;
            }
            log.finer( obj.toString());
            System.out.println("PatientImageResource JSON " +  obj.toString());
            return new JsonRepresentation(obj);
        }
        catch(Exception e)
        {
            log.throwing(KEY, "toJson()", e);
            return null;
        }
    }
    
    @Post("json")
    public String acceptJson(String value)
    {
    	System.out.println("PatientImagesResource: POST: In acceptJson");
    	return "POST finished";
    }
    
    @Put("json")
    public String storeJson(String value)
    {
    	System.out.println("PatientImagesResource: PUT: In storeJson");
    	return "json";
    }

    @Delete()
    public void removeAll(){}
    
    @Delete("json")
    public Representation deleteJson()
    {
    	System.out.println("PatientImagesResource: In deleteJSON");
    	JSONObject obj = new JSONObject();
    	return new JsonRepresentation(obj);
    }

    
    public void createThumbnail(String uri, String fileLabel, String dir)
    {
    	try {
    		System.out.println("ParentImageResource: getThumbnail :uri:  " + uri);
	    	File file = new File(uri);
	    	String thumbfileName = fileLabel + "_thumbnail.png";
	    	
	    	File newFile = new File(dir + "/" + thumbfileName);
	    	if (file.exists())
	    	{
	    		
	    		if (!newFile.exists())
	    		{
					BufferedImage rtnImage = ImageProcesses.createThumbnail(file);
					System.out.println("ParentImageResource: getThumbnail : success");
					ImageIO.write(rtnImage, "png", newFile);
	    		}
	    	}
	    	else
	    	{
	    		System.out.println("PatientImagesResource: createThumbnail: File doesn't exist ");
	    	}
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}