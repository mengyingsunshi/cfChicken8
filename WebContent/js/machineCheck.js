$(function(){
	$.ajax({
		type:'get',
		url:'MachineDetailsOperate',
		dataType:'JSON',
		data:{
			loadType : 'data'
		},
		success:function(data){
			var yearArr = [];
			var CountArr = [];
			var RateArr = [];
			for(var i = 0;i < data.length;i++){
				yearArr.push(data[i].Years);
				CountArr.push(data[i].TotalNum);
				RateArr.push(((data[i].Percent*100)+'').slice(0,5));
			}
			console.log(data.length)
			var dom1 = document.getElementById('machineCheck_Count');
			machineCheckBar_Count(dom1,yearArr,CountArr);
			
			var dom2 = document.getElementById('machineCheck_Rate');
			machineCheckBar_rate(dom2,yearArr,RateArr);
			
		}
	})
})

//柱状图函数1
var machineCheckBar_Count = function(dom1,yearArr,CountArr){
	    dom1.innerHTML = '';
	    var myChart = echarts.init(dom1);
	    var app = {};
	    option = null;
	    
	    option = {
	    	    title : {
	    	        text: '',
	    	        subtext: ''
	    	    },
	    	    tooltip : {
	    	        trigger: 'axis'
	    	    },
	    	    legend: {
	    	        data:['装机总数(项)']
	    	    },
	    	    toolbox: {
	    	        show : true,
	    	        feature : {
	    	            mark : {show: true},
	    	            dataView : {show: true, readOnly: false},
	    	            magicType : {show: true, type: ['line', 'bar']},
	    	            restore : {show: true},
	    	            saveAsImage : {show: true}
	    	        }
	    	    },
	    	    calculable : true,
	    	    xAxis : [
	    	        {
	    	            type : 'category',
	    	            name: '年份',
	    	            data : yearArr
	    	        }
	    	    ],
	    	    yAxis : [
	    	        {
	    	            type : 'value',
	        	        name: '装机总数(项)',
	    	        }
	    	    ],
	    	    series : [
	    	        {
	    	            name:'装机总数(项)',
	    	            type:'bar',
	    	            data:CountArr,
	    	            itemStyle: {
							normal: {
								label: {
									show: true, //开启显示
									position: 'top', //在上方显示
									textStyle: { //数值样式
										color: 'black',
										fontSize: 12
									}
								}
							}
						},
	    	        }
	    	    ]
	    	};
	    	                    
	    myChart.setOption(option);
	    if (option && typeof option === "object") {
	        myChart.setOption(option, true);
	       
	    }
	    window.addEventListener("resize",function(){
	    	myChart.resize();
	    });
	
}
//柱状图函数2
var machineCheckBar_rate = function(dom,yearArr,RateArr){
	    dom.innerHTML = '';
	    var myChart = echarts.init(dom);
	    var app = {};
	    option = null;
	    
	    option = {
	    	    title : {
	    	        text: '',
	    	        subtext: ''
	    	    },
	    	    tooltip : {
	    	        trigger: 'axis'
	    	    },
	    	    legend: {
	    	        data:['验收比例(%)']
	    	    },
	    	    toolbox: {
	    	        show : true,
	    	        feature : {
	    	            mark : {show: true},
	    	            dataView : {show: true, readOnly: false},
	    	            magicType : {show: true, type: ['line', 'bar']},
	    	            restore : {show: true},
	    	            saveAsImage : {show: true}
	    	        }
	    	    },
	    	    calculable : true,
	    	    xAxis : [
	    	        {
	    	            type : 'category',
	    	            name: '年份',
	    	            data : yearArr
	    	        }
	    	    ],
	    	    yAxis : [
	    	        {
	    	            type : 'value',
	    	            min:0,
	    	            max:100,
	        	        name:'验收比例(%)' ,
	    	        }
	    	    ],
	    	    series : [
	    	        {
	    	            name:'验收比例(%)',
	    	            type:'bar',
	    	            data:RateArr,
	    	            itemStyle: {
							normal: {
								label: {
									show: true, //开启显示
									position: 'top', //在上方显示
									textStyle: { //数值样式
										color: 'black',
										fontSize: 12
									}
								}
							}
						},
	    	        }
	    	    ]
	    	};
	    	                    
	    myChart.setOption(option);
	    if (option && typeof option === "object") {
	        myChart.setOption(option, true);
	        window.onresize = myChart.resize;
	    }
	
}