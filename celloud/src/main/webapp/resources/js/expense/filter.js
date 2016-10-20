celloudApp.filter("substring",function(){
  return function(input){
	  return input>50?(input.substring(0,50) + "..."):input;
  };
});
celloudApp.filter("emptyText",function(){
	return function(input,other){
		if(input==null||input==""){
			return other;
		}else{
			return input;
		}
	}
});
