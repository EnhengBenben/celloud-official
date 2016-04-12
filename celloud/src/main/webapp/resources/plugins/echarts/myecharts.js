function showPie(id,title,data) {
	console.log("2" + ", id:" + id + ", title:" + title + ", data:" + data);
    	var echartsPie = echarts.init(document.getElementById(id));
        var option = {
            title : {
                text: title,
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            series : [
                {
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:data,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        }
        console.log("3" + ", id:" + id + ", title:" + title + ", data:" + data);
        echartsPie.setOption(option);
    }