package org.mitre.medcafe.restlet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.mitre.medcafe.util.Config;
import org.mitre.medcafe.util.Repository;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class PatientListEventResource extends ServerResource {

    public final static String KEY = PatientListEventResource.class.getName();
    public final static Logger log = Logger.getLogger( KEY );
    static{log.setLevel(Level.FINER);}
    private final static String PATIENT_ID = "id";
    private String id;
    protected Date startDate = new Date();
    protected Date endDate =  new Date();
    
    /** The sequence of characters that identifies the resource. */
    
    /**
     *  Grab the information from the url
     */
    @Override
    protected void doInit() throws ResourceException {
       System.out.println("Found Patient List Event resource: " );
       // Get the "type" attribute value taken from the URI template
       Form form = getRequest().getResourceRef().getQueryAsForm();
       id = (String)getRequest().getAttributes().get(PATIENT_ID);
       System.out.println("PatientImageResource JSON init patientId " +  id );
       
       String startDateStr = form.getFirstValue("start_date");
       if (startDateStr == null)
       	startDateStr = "2006,2,15";
       		
       String endDateStr = form.getFirstValue("end_date");
       if (endDateStr == null)
       	endDateStr = "2012,2,15";
    	
       System.out.println("PatientImageResource JSON init startDate " +  startDateStr + " endDate " + endDateStr );
      
    }

    @Get("json")
    public JsonRepresentation toJson(){
       
    	/* Required JSON format
    	 * {
				'wikiURL': "http://simile.mit.edu/shelf/",
				'wikiSection': "Simile Cubism Timeline",
				
				'events' : [
				       {'start':  new Date(2006,2,15),
				        'title': 'Still Life with a White Dish',
				        'description': 'by Gino Severini, Italian Painter, 1883-1966',
				        'image': 'http://images.allposters.com/images/MCG/FS1254_b.jpg',
				        'link': 'http://www.allposters.com/-sp/Still-Life-with-a-White-Dish-1916-Posters_i366823_.htm'
				        }
				]
				}

    	 */
        //convert to JSON
        try
        {
            JSONObject obj = new JSONObject();
            String server = Config.getServerUrl() ;
            obj.put("wikiURL", "Patient data ");
            obj.put("wikiSection", "Patient Data");
            
        	String[] imageId = new String[]{"assessment","bloodstat","cardioReport" , "chest-xray", "chest-xray2","mri","lab-report"};
        	
        	String[] events = new String[]{"assessment.png","bloodstat.jpg","cardioReport.gif" ,
					"chest-xray.jpg", "chest-xray2.jpg","mri.jpg","bloodstat.jpg"};
        	String[] imageTitles = new String[]{"Assessment","Blood Stats","Cardio Report", "Chest XRay", "Chest XRay","MRI","Lab Results" };
        	
        	
        	String[] dates = new String[]{"2008,1,01","2008,2,03","2008,5,07",
        			"2008,6,08", "2008,8,07","2008,10,01","2008,10,30"};
        	String[] icons = new String[]{"results.png","results.png","hospital-icon.png",
        			"doctor-icon.png", "doctor-icon.png","doctor-icon.png","results.png"};
        	
        	String dir = "patients/" + this.id + "/";
        	String imageDir = "images/" + dir;
        	
        	int i=0;
        	
        	for(String event: events)
            {
        		JSONObject inner_obj = new JSONObject ();
                inner_obj.put("start", "<:startDate" + i + ":>");
                inner_obj.put("title", imageTitles[i]);
                inner_obj.put("image", "http://" + server + "/" + imageDir +  event);
                inner_obj.put("icon", "http://" + server + "/images/" + icons[i]);
                 obj.append("events", inner_obj); 
                i++;
            }
        	String jsonStr = obj.toString();
        	System.out.println("PatientListEventRestlet : toJSON: " + jsonStr);
        	jsonStr = putInDates(jsonStr, dates);
        	JsonRepresentation json = new JsonRepresentation(jsonStr);
            return json;
        }
        catch(org.json.JSONException e)
        {
            log.throwing(KEY, "toJson()", e);
            return new JsonRepresentation("{\"error\": \""+e.getMessage()+"\"}");
        }
    }

    /*Workaround to stop insertion of quotes*/
    private String putInDates(String jsonStr, String[] dates)
    {
    	int i=0;
    	//new Date (" +  dates[i] + ")
    	for (String date: dates)
    	{
    		jsonStr = jsonStr.replaceAll("\"<:startDate" + i + ":>\"", "new Date (" +  dates[i] + ")");
    		i++;
    	}
    	return jsonStr;
    }
    /**
     *  Html representation - this will likely have to changes once full integration is done
     */
    @Get("html")
    public Representation toHtml()
    {


    	StringBuffer buf = new StringBuffer();
    	StringBuffer endBuf = new StringBuffer();
    	endBuf.append("</tbody></table>");

    	StringBuilder ret = new StringBuilder( "Available Patients:<br/>\n<ul>" );
      
        return new StringRepresentation( buf.toString() 
                + endBuf.toString());
    }
}