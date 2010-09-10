var medCafeWidget = 
{ 
			medcafeWidgets : $,
			extSettings : 
			{ 
					/*for convenience*/
					columns : '.column',
			        widgetSelector: '.widget',
        			handleSelector: '.widget-head',
        			contentSelector: '.widget-content',
					widgetDefault : 
					{
							id : 1,
							order : 1,
							tab_num : 1,
							column: 1, 
							repository : 'OurVista',
							type: 'images',
							location : 'center',
							clickUrl :'http://127.0.0.1:8080',
							name : 'widget name',
							server : 'http://127.0.0.1:8080/',
							patient_id :1,
							rep_patient_id :1,
							remove :'false'
				    },
	        		widgetIndSettings : 
					{
			             intro : 
			             {
				            id : 1,
							order : 1,
							tab_num : 1,
							column: 1, 
							repository : 'OurVista',
							type: 'images',
							location : 'center',
							clickUrl :'http://127.0.0.1:8080',
							name : 'widget name',
							server : 'http://127.0.0.1:8080/',
							patient_id :1,
							rep_patient_id :1,
							remove :'false'
						}
					}	
			},
			test: function(txt)
		    {
		    	alert("txt " + txt);
		    },
		    getAllWidgetSettings : function ( id, origSettings) {
    
		        var $ = this.medcafeWidgets,
		            extSettings = this.extSettings;
		       
		       	var rtnSettings =  (id&&extSettings.widgetIndSettings[id]) ? $.extend({},extSettings.widgetDefault,extSettings.widgetIndSettings[id]) : settings.widgetDefault;
    			//Get all the settings
    			return $.extend({},origSettings,rtnSettings);
    			
    		}
    		,
		    getExtWidgetSettings : function ( id) {
    
		        var $ = this.medcafeWidgets,
		            extSettings = this.extSettings;
		       
		       	var rtnSettings =  (id&&extSettings.widgetIndSettings[id]) ? $.extend({},extSettings.widgetDefault,extSettings.widgetIndSettings[id]) : extSettings.widgetDefault;
    			
    			return rtnSettings;
    			
    		}
    		,
		    getAllIds : function () {
    
    			
		        var $ = this.medcafeWidgets,
		            extSettings = this.extSettings;
		        var ids = [];
		        var i=0;
		        $.each (extSettings.widgetIndSettings, function(id, val)
		        {
		        	
		        	ids[i++] = id;
		        });
		        return ids;
    			
    		}
    		,
		    setExtWidgetSettings : function ( id, newSettings) {
    			
    			//Make sure to extend to add the new values
    			//alert (id);
    			this.extSettings.widgetIndSettings[id] = $.extend({}, this.extSettings.widgetIndSettings[id], newSettings);
		       // alert(JSON.stringify(this.extSettings.widgetIndSettings[id]));
    		}
    		,
		    removeWidgetSettings : function ( widget_id) {
    		
    			var id = "yellow-widget" + widget_id;
				var newSettings = this.getExtWidgetSettings(id);
    			if (!newSettings)
    			{
    				alert("medCafe.widget.js removeWidgetSettings Could not set values ");
    			}    			
    			newSettings.remove = 'true';
    			this.extSettings.widgetIndSettings[id] = $.extend({}, this.extSettings.widgetIndSettings[id], newSettings);

    		}
    		,
    		populateExtWidgetSettings : function ( widgetInfo )
    		{   			
    			var id = "yellow-widget" + widgetInfo.id;
		
    			var newSettings = this.getExtWidgetSettings(id);
		
    			if (!newSettings)
    			{
    				alert("Could not set values ");
    			}
    			//Temporarily set id to the tab_num
    			//alert( "id " + widgetInfo.id + "yes this is what widgetInfo.id has");
    		//	alert("order " + widgetInfo.order);
    		//	alert("widgetInfo.type " + widgetInfo.type);
    		//	newSettings.id = widgetInfo.id;
    		//	newSettings.order = widgetInfo.order;
    			newSettings.id = widgetInfo.id;
    			newSettings.repository = widgetInfo.repository;
    			newSettings.type = widgetInfo.type;
    			//extSettings.location = location;
    			//extSettings.clickUrl = clickUrl;
    			newSettings.name = widgetInfo.name;
    			newSettings.server = widgetInfo.server;
    			newSettings.patient_id = widgetInfo.patient_id;
    			newSettings.rep_patient_id = widgetInfo.rep_patient_id;
    			newSettings.clickUrl = widgetInfo.clickUrl;

    			this.setExtWidgetSettings(id,newSettings );   					
				
				
    			/*
							id : 1,
							tab_num : 1, 
							repository : 'OurVista',
							type: 'images',
							location : 'center',
							clickUrl :'http://127.0.0.1:8080',
							name : 'widget name',
							server : 'http://127.0.0.1',
							patient_id :1    			 * 
    			 */
    		}
    		,
		    loadExtWidgetSettings : function () 
		    {
  
    			 var medWidgets = this,
            	 $ = this.medcafeWidgets,
            	 extSettings = this.extSettings;
        		 $(extSettings.widgetSelector, $(extSettings.columns)).each(function () 
        		 {
      
           			var thisWidgetSettings = medWidgets.getExtWidgetSettings(this.id);
           				 
           			//Cycle through the returned values and 
           			$('.settings').each(function ()
           			{
           				   //Goal is to get the following info
           			});
            	 });
        
    		},
    		saveWidget : function (url, id)
    		{
    			url = url + "?";
    			var widgetSettings = this.getExtWidgetSettings(id);
    			if (widgetSettings == "")
    			{
    				alert("medcafe.widget.js widgetSettings no widgets for " + id + " " + widgetSettings.id);
    		
    				return;
    			}
    			$.ajax({
	                url: url,
	                type: 'POST',
	                data: widgetSettings,
	                beforeSend: function() { $("#saveStatus").html("Saving").show(); },
	                success: function(result) {
	                    //alert(result.Result);
	                    //$("#saveStatus").html(result.Result).show();
	                }
            	});
    		},
    		deleteWidgets : function (url)
    		{
    			url = url + "?";
    			//alert("medcafe.widget.js widgetSettings ID " + id + " " + widgetSettings.id);
    			$.ajax({
	                url: url,
	                type: 'POST',
	                beforeSend: function() { $("#saveStatus").html("Saving").show(); },
	                success: function(result) {
	                    //alert(result.Result);
	                    //$("#saveStatus").html(result.Result).show();
	                }
            	});
    		},
    		saveAll : function (url)
    		{
    			
    		}
};

function extendWidgets(id){
	
	//$.extend($.fn.inettuts, {/*your methods/properties*/});
	
	var test = $.extend( {}, medCafeWidget , iNettuts);	
	var settings = test.getSettings();	
	//var allSettings = $.extend( {}, medCafeWidget.getExtWidgetSettings('intro') , settings);	
	var allSettings = test.getExtWidgetSettings( id, test.getWidgetSettings(id));
	
}

//Make sure that the delete event happens before all other events
function saveWidgets(oldPatient)
{
		var deleteUrl = "deleteWidget.jsp?patient_id=" + oldPatient;
    	//
    	//Get these first before attempting delete
  //  	var ids = medCafeWidget.getAllIds();
    	$.ajax({
	           url: deleteUrl,
	           type: 'POST',
	           beforeSend: function() { $("#saveStatus").html("Saving").show(); },
	           success: function(result) {
	      
					
					//Cycle through each to save
	//				$.each (ids, function(i, val)
	//				{
	//				 if (val != "intro")
	//				 {
	//					medCafeWidget.saveWidget("saveWidget.jsp", val);
	//				}
	//				});
					    	var widgetSettings;

            			//url = url + "?";
            			order = 1;
            			tab = 1;
			$('.tabs').parent().find(".tabContent").each(function(i)
			{	
				var newTab = true;
				var tabName = $(this).find('.id').attr("id");
				$(this).find('.widget').each(function()
				{
    				
    				var widgetSettings = medCafeWidget.getExtWidgetSettings($(this).attr("id"));
    				if (widgetSettings == "")
    				{
    					alert("medcafe.widget.js widgetSettings no widgets for " + id + " " + widgetSettings.id);
    		
    					return;
    				}
    				if (newTab)
    				{
    					widgetTabSettings = {
						"name" : tabName,
						"type" : "tab",
						"order" : order,
						"tab_num" : tab,
						"id" : order,
						"patient_id" : widgetSettings.patient_id,
						"remove" : false
				};
			    $.ajax({
	                url: "saveWidget.jsp?",
	                type: 'POST',
	                data: widgetTabSettings,

       
	                beforeSend: function() { $("#saveStatus").html("Saving").show(); },
	                success: function(result) {
	                    //alert(result.Result);
	                    //$("#saveStatus").html(result.Result).show();
	                }
            	});

				order ++;
				newTab = false;
    				}
    				
    				var column = $(this).closest(".column").attr("id").substring(6);
    				widgetSettings.tab_num = tab;
    				widgetSettings.column = column;
    				widgetSettings.order = order;
    				widgetSettings.id = order;
    				    				
    			$.ajax({
	                url: "saveWidget.jsp",
	                type: 'POST',
	                data: widgetSettings,

	                beforeSend: function() { $("#saveStatus").html("Saving").show(); },
	                success: function(result) {
	                    //alert(result.Result);
	                    //$("#saveStatus").html(result.Result).show();
	                }
            	});
   
    				order++;
    				});
    				tab++;
    				});
    				

		//Code to cycle through the widgets and save
	
		
					
	           }
        });
    
		//Code to cycle through the widgets and save
	
		
}

function removeWidget(id)
{ 			
	medCafeWidget.removeWidgetSettings(id);
}

function loadWidgetData( divId, type)
{
		
			var isiPad = navigator.userAgent.match(/iPad/i) != null;
			//console.log('Is this an iPad...' + isiPad);
			var serverUrl = "widgets-listJSON.jsp?type=" + type;
			
		    $.getJSON(serverUrl, function(data)
		    {
		    	
  				var html = v2js_listWidgets( data );  			
    	
    			$("#" + divId).append(html);
    			$('#' + divId).each(function()
	        	{
	            	 this.addEventListener("touchmove", stopTouchMove, false);
	        	});
	        	
    			$(this).delay(500,function()
				{						
		    			var imageButton = $("#" + divId).find('.imageContain');

						$(imageButton).mousedown(function(event) {
								
								clearWidgets();		
	  							startWidgetDrag($(this),divId,  isiPad, event );
	  							return false;
							});	
							
						$("#" + divId).jScrollTouch({height:'380',width:'140'});
				});  		
			});
}
