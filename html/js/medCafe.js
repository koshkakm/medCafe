$(document).ready( function() {

		var tabSelectedId;

	    var isiPad = navigator.userAgent.match(/iPad/i) != null;
	   
		// create the OUTER LAYOUT
        outerLayout = $("body").layout({
            west: { closable: true, resizable: true, slidable: true, showOverflowOnHover: true },
            north: { size:120, resizable: true, slidable: true, showOverflowOnHover: true },
            south: { size: 600, initClosed: true, slideTrigger_open: "click" }
		});

		//Initialize dialogs for pop ups
 		$('#dialog').dialog();
 		$('#dialog').dialog('destroy');

 		//Make sure that any cloned draggable objects disappear when dragging ends
 		$("#clone").draggable({

			iframeFix : true,
			stop: function()
			{
				$(this).html("");
				$(this).hide();
			}

    	});

    	//Initialize Accordion with second accordion open
    	$("#accordion").accordion({
       		active: 1,
       		collapsible: true,
       		autoHeight: false
   		});

  		var draggedId;

		//Set up listener for Filter Date
		$(document).bind('FILTER_DATE', function()
		{
		   		 filterDate();
		});

		$(document).bind('FILTER', function()
		{
		   		 filterCategory();
		});

		$('#addTabBtn').click(function()
		{
			var tab_num = addTab("new","chart");
			
			//Make sure that tab is refreshed to add relevant scripts/ events
			iNettuts.refresh("yellow-widget" + tab_num);
			iNettuts.makeSortable();
		});
		
		var medCafe = {

				add : function (server, rep)
				{
			 	//Method to cycle through all summary classes and allow for clicking to get details

			 		$.getScript('js/medCafe.repository.js', function()
					{
			 			listRepository(server, rep);
			 		});
				}
		}

		medCafe.add("127.0.0.1:8080/medcafe/c","OurVista");
		iNettuts.makeSortable();

		$("body").draggable({

			containment: 'window',
			iframeFix : true

    	});

		$('.fg-button').hover(
    		function(){ $(this).removeClass('ui-state-default').addClass('ui-state-focus'); },
    		function(){ $(this).removeClass('ui-state-focus').addClass('ui-state-default'); }
    	);

		$.get('menuContent.html', function(data){
			$('#flat').menu({
				content: data
			});
		});
		extendWidgets();
	});
	//End of code to initialize page

	//Code to create widgets content
	function createWidgetContent(patientId,link, label, type ,tab_num, params, repId, patientRepId)
	{

	 $(this).delay(200,function()
	 {
	 	//alert("medcafe createWidgetContent: type " + type);
		if (type === "Chart")
		{
			addChart(this, link, tab_num, patientId, patientRepId);
		}
		else if  (type == "Images")
		{
			addCoverflow(this, link, tab_num, patientId, patientRepId);
		}
		else if  (type == "Detail")
		{
			addPatientDetail(this, link, tab_num, label, patientId, repId, patientRepId);
		}
		else if  (type == "Repository")
		{

			$.getScript('js/medCafe.repository.js', function()
			{
				addRepository(this, link, tab_num, label, repId);
			});
		}
		else if  (type == "Bookmarks")
		{
			
			$.getScript('js/medCafe.bookmarks.js', function()
			{
				addBookmarks(this, link, tab_num, label, patientId, repId);
			});
		}
		else if  (type == "Medications")
		{

			$.getScript('js/medCafe.medications.js', function()
			{
				addMedications(this, link, tab_num, label, patientId, repId, patientRepId);
			});
		}
		else if  (type == "History")
		{

			if (typeof addHistory == 'undefined')
			{
				$.getScript('js/medCafe.history.js', function()
				{
					addHistory(this, link, tab_num, label, patientId, repId, patientRepId);
				});
			}
			else
			{
				addHistory(this, link, tab_num, label, patientId, repId, patientRepId);
			}
		}
		else if  (type == "Problem")
		{
			if (typeof addProblemList == 'undefined')
			{
				$.getScript('js/medCafe.problemList.js', function()
				{
					addProblemList(this, link, tab_num, label, patientId, repId, patientRepId);
				});
			}
			else
			{
				addProblemList(this, link, tab_num, label, patientId, repId, patientRepId);
			}
		}
		else if  (type == "Allergies")
		{
			if (typeof addAllergies == 'undefined')
			{

				$.getScript('js/medCafe.allergies.js', function()
				{
					addAllergies(this, link, tab_num, label, patientId, repId, patientRepId);
				});
			}
			else
			{
				addAllergies(this, link, tab_num, label, patientId, repId, patientRepId);
			
			}
		}
		else
		{
			
			addChart(this, link, tab_num, patientId, patientRepId);
		}

		populateWidgetSettings(patientId,link, label, type ,tab_num, params, repId, patientRepId);
		});
	}

	function populateWidgetSettings(patientId,link, label, type ,tab_num, params, repId, patientRepId)
	{
		//alert("CREATE WIDGET CONTENT medcafe adding the following widget: label " + label + " " + " type " + type  + " tab order " + tab_num + " rep " + repId + " server " + link);
		
		medCafeWidget.populateExtWidgetSettings(patientId,link, label, type ,tab_num, params, repId, patientRepId);
	}

	function addChart(callObj, server, tab_num, patientId, patientRepId)
	{
		//alert("medCafeTabs " +  tab_num + " about to call setHasContent  set to " + true  );

		//alert("callObj " + callObj);
		//Delay to let the DOM refresh
		$(callObj).delay(200,function()
		{
			//alert("image server " + server);

			iNettuts.refresh("yellow-widget" + tab_num);

			$("#aaa" + tab_num).append('<iframe frameborder="0" id="iframe'+ tab_num+ '" name="iframe'+ tab_num+ '" width="720" height="350"/>');
			$(callObj).delay(100,function()
			{
				$('#iframe'+ tab_num).attr('src', server +"?tab_num=" + tab_num + "&patient_id=" + patientId + "&rep_patient_id="  + patientRepId);
				
			} );

			//iNettuts.makeSortable();
			setHasContent(tab_num);
		} );
	}

	function addCoverflow(callObj, server, tab_num, patientId, patientRepId)
	{

		//Delay to let the DOM refresh
		$(callObj).delay(100,function()
		{
			iNettuts.refresh("yellow-widget" + tab_num);

			$("#aaa" + tab_num).append('<iframe id="iframe'+ tab_num+ '" name="iframe'+ tab_num+ '" width="800" height="400"/>');
			$(callObj).delay(100,function()
			{
				$('#iframe'+ tab_num).attr('src', server +"?tab_num=" + tab_num + "&patient_id=" + patientRepId);
			} );

			//iNettuts.makeSortable();
			setHasContent(tab_num);
		} );
	}

	function imageAnnotate(callObj, server, tab_num)
	{

		//Delay to let the DOM refresh
		$(callObj).delay(100,function()
		{
			iNettuts.refresh("yellow-widget" + tab_num);

			var html = "<img id=\"toAnnotate\" src=\"" + server + "\" alt=\""+  server + "\" width=\"600\" height=\"398\" />";
			var jspSvr = "annotate.jsp";

			$("#aaa" + tab_num).append('<iframe id="annotateiframe" width="800" height="400"/>');
			$('#annotateiframe').attr('src', jspSvr);

			setHasContent(tab_num);

		} );
	}
	function filterDate()
	{
		   //alert("Filter Date");
	}

	function filterCategory()
	{
		   //alert("Filter Date");
	}

	function triggerFilter(startDate, endDate)
	{
		//alert("medCafe.js triggerFilter - start date is " + startDate + " end Date is " + endDate);
		$(document).trigger('FILTER_DATE', [startDate, endDate]);
	}

	function triggerFilterCategory(filterCat)
	{
		$(document).trigger('FILTER_CATEGORY', [filterCat]);
	}

	function triggerFilterAll(startDate, endDate, filterCat)
	{
		$(document).trigger('FILTER_ALL', [startDate, endDate, filterCat]);
	}

	function triggerCloseTab(tabNum)
	{
		$(document).trigger('CLOSE_TAB', [tabNum]);
	}

	function displayDialog( id)
	{
		var text = $("#aaa" + id).html();

		$("#modalaaa" + id).append(text);

		var $link = $('#aaa' + id);
		//Fill the screen
		var marginHDialog = 25; marginWDialog  = 25;
		marginHDialog = $(window).height()-marginHDialog;
		var marginWDialog = $(window.body).width()-marginWDialog;

		$("#dialog" + id).load($link.attr('href') + ' #content')
				.dialog({
						 autoOpen: false,
						 modal:true,
						 resizable: true,
						 title: "Editor Tab",
						 height: marginHDialog,
						 width: marginWDialog,
						 buttons : {
						    "Close" : function() {
						     //Have to Destroy as otherwise
						     //the Dialog will not be reinitialized on open

						     text = $("#modalaaa" + id).html();
						     $("#aaa" + id).load($link.attr('href') + ' #content');
						     $("#modalaaa" + id).empty();
						      //Put in code to goto saveText.jsp Delete
						      $(this).dialog("destroy");
						   }
						}
					});

		$("#dialog" + id).dialog("open");
	}

	function startWidgetDrag(test, frameId, isiPad, e)
	{

		//console.log('medCafe: startWidgetDrag : start isiPad ' + isiPad);	
											
	    var iFramePos = $('#' + frameId).position();
	    //Need to replace this with better way to determine position
	  	//console.log('medCafe: startWidgetDrag : iFramePos ' + iFramePos);	
		
		if (isiPad)
		{
			iFramePos.left = 790;
			iFramePos.top = 200;
		}
		else
		{
			iFramePos.left = 1300;
	  		iFramePos.top = 170;
		}
	  	var cloneLeft = iFramePos.left + $(test).position().left;
	  	var cloneTop = iFramePos.top + $(test).position().top;
	  	$(test).clone().appendTo('#clone');
	  	$(test).clone().remove();
	  	var height = $('#clone').height();
	  	var width = $('#clone').width();
	  	//console.log('medCafe: startWidgetDrag : in here ' );	
		
	  	$('#clone').css( { position: "absolute",  "z-index" : "100", "left": cloneLeft + "px", "top": cloneTop + "px" } );
	  	e.pageX = cloneLeft + width/2;
	   	e.pageY = cloneTop + height/2;
	    //make draggable element draggable
	    if (isiPad)
		{
			//console.log('medCafe.js startWidgetDrag  This is an iPad about to bind touch move ');	
			//$('#clone').ontouchmove = touchMove;
			//$('#clone').addEventListener("touchmove", touchMove, false);
			$('#clone').show();	
			//$('#clone').bind( "touchmove",touchMove);
			$('#clone').medcafeTouch();							
		}
		else
		{
		    $("#clone").draggable().trigger(e);
		    $('#clone').show();
	    }

	}

  function clearWidgets()
  {
  	$('#clone').html("");
	$('#clone').hide();
  }


function displayImage(imageName)
{
	//Delay to let the DOM refresh

	 var server = "http://" + imageName ;

	 var imageTitle = server;
	 var pos = server.lastIndexOf("/") + 1;
	 if (pos > 0)
	 {
	 	imageTitle = imageTitle.substring(pos, imageTitle.length);

	 }
	 var tab_num = addTab(imageTitle, "Image");

	 var html =$.ajax({
      url: server,
      global: false,
      type: "POST",
      dataType: "html",
      success: function(msg)
      {
      	 var text = "<div id=\"content\">\n<input id=\"viewerButton" + tab_num + "\" type=\"button\" value=\"Viewer\"/>\n" +
					 "<div id=\"content\">\n<input id=\"editButton" + tab_num + "\" type=\"button\" value=\"Annotate\"/>\n" +
					"<a href=\"" + server +"\" class=\"jqzoom" + tab_num + "\" style=\"\" title=\"" + imageTitle +"\">\n" +
					"<img src=\"" + server + "\"  title=\""+ imageTitle + "\" width=\"300\" style=\"border: 1px solid #666;\">\n" +
					"</a>" + "</div>\n";


        var viewerText =  "\n<div id=\"viewer\" class=\"viewer\"></div>\n";

         var viewerFrame = "<iframe height=\"400\" width=\"680\" name=\"imageFrame" + imageTitle + "\" id=\"frame" + imageTitle+ "\" src=\"viewer.jsp?image=" + server + "\"></iframe>";


         iNettuts.refresh("yellow-widget" + tab_num);
		 //$("#aaa" + tab_num).append("<img src='" + server+ "?image=<%=server%>' alt='"+ imageName+ "' width='400'/>");
		 $("#aaa" + tab_num).append( text );

		 $(this).delay(100,function()
		 {
		 	setHasContent(tab_num);
			//Code for zoom
		 	var options =
            {
                zoomWidth: 300,
                zoomHeight: 200,
                position : 'right',
                yOffset :100,
                xOffset :100,
                title :false

            }

			$(".jqzoom" + tab_num).jqzoom(options);

			$("#viewerButton" + tab_num).bind("click",{},
			function(e)
			{

				var tab_num = addTab(imageTitle + "Viewer","Viewer");

				var link = "viewer.jsp?image=" + server;
				addChart(this, link, tab_num);
			});

			$("#editButton" + tab_num).bind("click",{},
			function(e)
			{

				var tab_num = addTab(imageTitle + "Annotate", "Annotate");

				var link = server;
				imageAnnotate(this, link, tab_num);
			});
		  } );
		}
      }).responseText;

}

function initClose()
{
	 $("#dialog").dialog({
		autoOpen: false,
		modal:true,
		resizable: true,
		title: "Close Tab",
		buttons : {
			"Yes" : function() {
			//Have to Destroy as otherwise
			//the Dialog will not be reinitialized on open
			$(this).dialog("destroy");
					closeAllTabs("tabs");
			},
			"No" : function() {
				$(this).dialog("destroy");
			}
		}
	});
	$("#dialog").dialog("open");

}


function closeAllTabs(tab_num)
{

		var indexList = new Array();
		$("#tabs").find("li:has(a)").each(function(i)
		{
			indexList[i] = $(this).attr('custom:index');

		});

		//Make sure that the last tab is closed first
		for (i=indexList.length-1; i > -1 ;i--)
		{
			$("#tabs").tabs("remove",indexList[i]);
		}


}

function updateAnnouncements(data)
{

    if(data.announce)
    {
    	/*$.each(data.announce, function(i, item){
            alert("item " + i + " value " + item);
        });*/
    	var html = v2js_announcements(data);
        $('#announcements').html(html);
    }
    else
    {
        $('#announcements').html("");
    }
}

//Given a JSON Object with all the listings of assoc repositories
//Initialize a JSON Object holding all information about patients and the corresponding
//associated id in the host repository

function getAssocPatientRepositories(patientId)
{	
	var serverLink = "retrievePatientRepositoryAssoc.jsp?patient_id=" + patientId;
	var repPatientJSON;
	$.getJSON(serverLink,function(data)
	{		      	  	  
			repPatientJSON = data;	    
	});
	return repPatientJSON;
}  	

function touchMove(event) 
{
			console.log('medCafe touchMove: Touch move..start');
            event.preventDefault();
			var finalCoord = { x: 0, y: 0 };
           
            finalCoord.x = event.targetTouches[0].pageX // Updated X,Y coordinates
            finalCoord.y = event.targetTouches[0].pageY
            console.log('Touch move..position x: ' + finalCoord.x +' y : ' + finalCoord.y);
            $("#clone").css( { position: "absolute",  "z-index" : "100", "left": finalCoord.x + "px", "top": finalCoord.y + "px" } );
	  	
 }