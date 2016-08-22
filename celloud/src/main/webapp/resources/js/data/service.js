(function(){
  celloudApp.service("runService",function($resource,$http){
      var self = this;
      self.list = function(){
        return $resource("data/dataPageList").get();
      }
      self.conditionList = function(){
        var options = $.dataManager.options;
        return $http.get("data/dataPageListCondition",{params: {condition:options.condition,size:options.pageSize,sort:options.sort,sortDateType:options.sortDateType,sortNameType:options.sortNameType}});
      }
      self.run = function(){
        var checkedIds = $.dataManager.options.checkedIds;
        if(checkedIds.length==0){
          alert("请选择数据");
          return;
        }
        var dataIds = [];
        $.each(checkedIds, function(i, el){
            if($.inArray(el, dataIds) === -1) dataIds.push(el);
        });
        return $http.get("data/runWithProject",{params: {dataIds: dataIds.toString()}});
      }
      self.delete = function(){
        if(confirm("确定要归档选中数据吗？")){
          var checkedIds = $.dataManager.options.checkedIds;
          if(checkedIds.length==0){
            alert("请选择数据");
            return;
          }
          var dataIds = "";
          for (var i=0;i<checkedIds.length;i++){
            dataIds += checkedIds[i] + ",";
          }
          dataIds = dataIds.substring(0, dataIds.length-1);
          return $http.get("data/delete.action",{params: {dataIds: dataIds}});
        }
      }
    });
    
}());
