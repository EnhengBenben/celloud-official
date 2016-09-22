/**
 * 总览
 */
// 路径配置
require.config({
    paths: {
        echarts: '//cdn.bootcss.com/echarts/2.2.7/'
    }
});
//
var userCount=(function(userCount){
  var self=userCount||{};
  
  self.lineModal=function(id,legend,xdata,ydata,yname,unit){
    require(
        [
            'echarts',
            'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById(id)); 
            
            var option = {
                tooltip: {
                  trigger: 'axis',
                    show: true
                },
                legend: {
                    data:[yname]
                },
                xAxis : [
                    {
                        type : 'category',
                        data : xdata
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        axisLabel : {
                            formatter: '{value}'+(typeof(unit)=='undefined'?'':unit)
                        },
                    }
                ],
                series : [
                    {
                        "name":yname,
                        "type":"line",
                        "data":ydata,
                        "markPoint" : {  
                              data : [  
                                  {type : 'max', name: '最大值'},  
                                  {type : 'min', name: '最小值'}  
                              ]  
                          }
                    }
                ]
            };
            // 为echarts对象加载数据 
            myChart.setOption(option); 
        }
    );
  };
  self.fileDayCount=function(){
    $.get("count/fileDayCount",function(data){
      var x = "[";
      var y = [];
      $.each(data,function(index,map){
        if (typeof(map.time) != "undefined") {
          x += "'" + map.time + "',";
          y[y.length]=Number(map.num);
        }
      })
      x = x.substring(0,x.length-1);
      x += "]";
      self.lineModal("count-data-day-chart","日上传文件统计",eval(x),y,"日上传文件数量");
    });
  };
  self.fileMonthCount=function(){
    $.get("count/fileMonthCount",function(data){
      var x = "[";
      var y = [];
      $.each(data,function(index,map){
        if (typeof(map.time) != "undefined") {
        x += "'" + map.time + "',";
        y[y.length]=Number(map.num);
        }
      })
      x = x.length>1?x.substring(0,x.length-1):x;
      x += "]";
      if(x!="[]"){
        self.lineModal("count-data-month-chart","月上传文件统计",eval(x),y,"月上传文件数量");
      }else{
        $("#count-data-charts").hide();
      }
    });
  };
  self.fileSizeDayCount=function(){
    $.get("count/fileSizeDayCount",function(data){
      var x = "[";
      var y = [];
      $.each(data,function(index,map){
        if (typeof(map.time) != "undefined") {
          x += "'" + map.time + "',";
          y[y.length]=Number(map.size);
        }
      })
      var max=Math.max.apply(null,y);
      var unit="";
      var divisor=1;
      if(max>1073741824){
        divisor=1073741824;
        unit="GB";
      }else if(max>1048576){
        divisor=1048576;
        unit="MB";
      }else{
        divisor=1024;
        unit="kB";
      }
      for(var i=0;i<y.length;i++){
        y[i]=Number((y[i]/divisor).toFixed(2));
      }
      x = x.substring(0,x.length-1);
      x += "]";
      self.lineModal("count-source-day-chart","日资源占用量统计",eval(x),y,"日上传文件大小",unit);
    });
  };
  self.fileSizeMonthCount=function(){
    $.get("count/fileSizeMonthCount",function(data){
      var x = "[";
      var y = [];
      $.each(data,function(index,map){
        if (typeof(map.time) != "undefined") {
        x += "'" + map.time + "',";
        y[y.length]=Number(map.size);
        }
      })
      var max=Math.max.apply(null,y);
      var unit="";
      var divisor=1;
      if(max>1073741824){
        divisor=1073741824;
        unit="GB";
      }else if(max>1048576){
        divisor=1048576;
        unit="MB";
      }else{
        divisor=1024;
        unit="kB";
      }
      for(var i=0;i<y.length;i++){
        y[i]=Number((y[i]/divisor).toFixed(2));
      }
      x = x.length>1?x.substring(0,x.length-1):x;
      x += "]";
      if(x!="[]"){
        self.lineModal("count-source-month-chart","月资源占用量统计",eval(x),y,"月上传文件大小",unit);
      }else{
        $("#count-source-charts").hide();
      }
    });
  };

  self.reportDayCount=function(){
    $.get("count/reportDayCount",function(data){
      var x = "[";
      var y = [];
      $.each(data,function(index,map){
        if (typeof(map.time) != "undefined") {
          x += "'" + map.time + "',";
          y[y.length]=Number(map.size);
        }
      })
      x = x.substring(0,x.length-1);
      x += "]";
      self.lineModal("count-report-day-chart","日报告统计",eval(x),y,"日生成报告个数");
    });
  };

  self.reportMonthCount=function(){
    $.get("count/reportMonthCount",function(data){
      var x = "[";
      var y = [];
      $.each(data,function(index,map){
        if (typeof(map.time) != "undefined") {
        x += "'" + map.time + "',";
        y[y.length]=Number(map.size);
        }
      })
      x = x.length>1?x.substring(0,x.length-1):x;
      x += "]";
      if(x!="[]"){
        self.lineModal("count-report-month-chart","月报告统计",eval(x),y,"月生成报告个数");
      }else{
        $("#count-report-charts").hide();
      }
    });
  };

  self.appDayCount=function(){
    $.get("count/appDayCount",function(data){
      var x = "[";
      var y = [];
      $.each(data,function(index,map){
        if (typeof(map.time) != "undefined") {
          x += "'" + map.time + "',";
          y[y.length]=Number(map.num);
        }
      });
      x = x.substring(0,x.length-1);
      x += "]";
      self.lineModal("count-app-day-chart","日已添加APP运行次数",eval(x),y,"日APP运行次数");
    });
  };
  
  self.appMonthCount=function(){
    $.get("count/appMonthCount",function(data){
      var x = "[";
      var y = [];
      $.each(data,function(index,map){
        if (typeof(map.time) != "undefined") {
        x += "'" + map.time + "',";
        y[y.length]=Number(map.num);
        }
      })
      x = x.length>1?x.substring(0,x.length-1):x;
      x += "]";
      if(x!="[]"){
        self.lineModal("count-app-month-chart","月已添加APP运行次数",eval(x),y,"月APP运行次数");
      }else{
        $("#count-app-charts").hide();
      }
    });
  };
  /**
   * idShow:show
   * idHide:hide
   */
  self.dayMonthSwitch=function(idShow,idHide){
    $("#count-"+idShow+"-chart").removeClass("hide");
    $("#"+idShow+"-span").addClass("active");
    $("#count-"+idHide+"-chart").addClass("hide");
    $("#"+idHide+"-span").removeClass("active");
  }
  return self;
})(userCount);
