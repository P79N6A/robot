var cnAlertFn = {
	run : function(content, btnNameArr, title, actionCallFn, hideCallFn) {
		var dia = $.dialog({
			title : (title || '提示'),
			content : (content || ''),
			button : btnNameArr || [ "确认" ]
		});
		if (actionCallFn) {
			dia.on("dialog:action", function(e) {
				actionCallFn(e.index);
			});
		}
		if (hideCallFn) {
			dia.on("dialog:hide", function(e) {
				hideCallFn(e);
			});
		}
	}
}
