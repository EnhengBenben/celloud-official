(function(){
  celloudApp.service("runService",function($resource,$http){
      var self = this;
      self.pageList = function(){
        var options = $.dataManager.options;
        return $http.get("data/dataPageListCondition?random="+new Date().getTime(),{params: {page:options.page,size:options.pageSize,condition:options.condition,sort:options.sort,sortDateType:options.sortDateType,sortNameType:options.sortNameType}});
      }
      self.run = function(){
        var checkedIds = $.dataManager.options.checkedIds;
        var dataIds = [];
        $.each(checkedIds, function(i, el){
            if($.inArray(el, dataIds) === -1) dataIds.push(el);
        });
        return $http.get("data/runWithProject",{params: {dataIds: dataIds.toString()}});
      }
      self.deleteData = function(){
          var checkedIds = $.dataManager.options.checkedIds;
          var dataIds = "";
          for (var i=0;i<checkedIds.length;i++){
            dataIds += checkedIds[i] + ",";
          }
          dataIds = dataIds.substring(0, dataIds.length-1);
          return $http.get("data/delete.action",{params: {dataIds: dataIds}});
      }
      self.toEditData = function(fileId){
        return $http.get("data/toEditData?random=" + new Date(),{params: {dataId: fileId}});
      }
      self.submitEditData = function(dataFile){
        return $http.get("data/updateDataAndTag",{params: {fileId: dataFile.fileId,anotherName: dataFile.anotherName,batch: dataFile.batch,tagName: dataFile.tagName}});
      }
    });
}());
