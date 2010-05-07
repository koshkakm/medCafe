package org.mitre.medcafe.restlet;

import org.restlet.data .*;
import java.util.*;
import org.mitre.medcafe.util.*;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


public class RepositoryListResource extends ServerResource {

    public final static String KEY = RepositoryListResource.class.getName();
    public final static Logger log = Logger.getLogger( KEY );
    // static{log.setLevel(Level.FINER);}

    @Get("html")
    public Representation toHtml(){
        StringBuilder ret = new StringBuilder( "Available Repositories:<br/>\n<ul>" );
        for( String name : Repositories.getRepositoryNames())
        {
            ret.append( "<li>" );
            ret.append( "<a href=\"browseRepository.jsp?repo=" + name + "\">" + name + "</a>" );
            ret.append( "</li>\n" );
        }
        ret.append( "</ul>" );
        return new StringRepresentation( ret.toString() );
    }

    @Get("json")
    public JsonRepresentation toJson(){
        try
        {

            Map<String, Repository> reps = Repositories.getRepositories();
            if( reps == null )
            {
                return new JsonRepresentation(WebUtils.buildErrorJson( "No repositories currently exist."));
            }

            JSONObject obj = new JSONObject();
            for(Repository r: Repositories.getRepositories().values() )
            {
                JSONObject inner_obj = new JSONObject ();
                inner_obj.put("name", r.getName());
                inner_obj.put("type", r.getType());
                obj.append("repositories", inner_obj);  //append creates an array for you
            }
            return new JsonRepresentation(obj);

        }
        catch(JSONException e)
        {

            log.throwing(KEY, "toJson", e);
            return new JsonRepresentation(WebUtils.buildErrorJson( "The repositories were found, however there was an error constructing the return data."));
        }
        catch(Exception e)
        {
            log.throwing(KEY, "toJson()", e);
            return null;
        }
    }

    //public Representation handle()
    /*{
        System.out.println(getClientInfo().getAgent());
        System.out.println(getClientInfo().getAgentName());
        List<Preference<MediaType>> mediaTypes = getClientInfo().getAcceptedMediaTypes();
        for(Preference<MediaType> pref : mediaTypes)
            System.out.println( String.valueOf(pref) );
        System.out.println(getPreferredVariant(getVariants()));
        return super.handle();
    }*/
}
