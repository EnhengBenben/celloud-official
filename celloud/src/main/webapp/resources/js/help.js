var userHelp=(function(userHelp){
	var self=userHelp||{};
	
	self.openPage=function(page){
		$("#mainDIV").load(page);
	}
	return self;
})(userHelp);

