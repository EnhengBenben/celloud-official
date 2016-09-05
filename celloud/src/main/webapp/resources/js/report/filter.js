(function(){
  celloudApp.filter("hcvShowMore",function(){
    return function(input){
    	if(input!="1b"&&input!="2a"&&input!="3a"&&input!="3b"&&input!="6a"){
    		$("#_change").parent().css("display","");
    		return "其他";
    	}else{
    		return input;
    	}
    }
  });
})();
