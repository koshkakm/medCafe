function addBookmarks(callObj, widgetInfo, data, tab_set)
{
        if (tab_set === undefined)
	{
		tab_set ="tabs";
	}
	var tab_key = tab_set + "-";
	var html = v2js_inettutsHead(widgetInfo) +window["v2js_" + widgetInfo.template](data) + v2js_inettutsTail(widgetInfo);
        var selectedRow=0;
        if (!widgetInfo.tab_num)
            widgetInfo.tab_num = "2";
        if (!widgetInfo.column)
            widgetInfo.column = "1";


    	$("#" + tab_key + widgetInfo.tab_num + " #column" + widgetInfo.column).append(html);	
        //TODO make bookmarks editable
       // setHasContent(widgetInfo.order);

        //set up tooltips
        $('.qtip a[href][title]').qtip({
            content: {
                text: false // Use each elements title attribute
            },
            style: 'cream' // Give it some style
        });
}
