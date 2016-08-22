(function(){
  celloudApp.service("runService",function($resource,$http){
      var self = this;
      self.list = function(){
        return $resource("data/dataPageList").get();
      }
      self.run = function(){
        var checkedIds = $.dataManager.options.checkedIds;
        alert(checkedIds);
        var dataIds = [];
        $.each(checkedIds, function(i, el){
            if($.inArray(el, dataIds) === -1) dataIds.push(el);
        });
        if(dataIds.length==0){
          alert("请选择数据");
          return;
        }
        return $http.get("data/runWithProject",{params: {dataIds: dataIds.toString()}});
      }
    });
    
}());
