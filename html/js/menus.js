function processMenuClick(menuLabel)
{
	
	if (menuLabel == "Add Tab")
	{
		//Code to add an empty tab
	
		
		var tab_num = addTab("new");
		
		//Make sure that tab is refreshed to add relevant scripts/ events
		iNettuts.refresh("yellow-widget" + tab_num);
		iNettuts.makeSortable();
	}
	else if (menuLabel == "Save")
	{
		//Code to cycle through the widgets and save
		medCafeWidget.deleteWidgets("deleteWidget.jsp");
		var ids = medCafeWidget.getAllIds();
		//Cycle through each to save
		 $.each (ids, function(i, val)
		 {
		     medCafeWidget.saveWidget("saveWidget.jsp", val);
		 });
	}
	else if (menuLabel == "Test")
	{
		//Code to cycle through the widgets and save
		
		var ids = medCafeWidget.getAllIds();
		//Cycle through each to save
		 $.each (ids, function(i, val)
		 {
		 	 
		     var settings = medCafeWidget.getExtWidgetSettings(val);
		     alert("menus.js settings val " + val  + " tab num " + settings.tab_num);
		 });
	}
}
