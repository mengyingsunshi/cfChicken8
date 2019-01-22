$(function(){
	$('.machineBar_title span').click(function(){
		$(this).siblings().removeClass('current');
		$(this).addClass('current');
		var rank = $(this).attr('rank');
		machineBarData();
		$('.machineBarBox div').hide();
		$('.machineBarBox #machineBar'+rank+'').show();
	})
	
	
	 
})

var machineBarData = function(){
		 $.ajax({
			 type:'get',
			 url:'MachineStatistics',
			 dataType:'JSON',
			 data:{
				 loadType   : 'data'
			 },
			 success:function(data){
				 console.log(data)
					
				for(var key1 in data){ 
					var dataArr = [];
					var nameArr = [];
					var dayArr = [];
						if(key1 == 'EPS150'){
							dataArr = data[key1];
								for(var key2 in dataArr){
									nameArr.push(key2);
									dayArr.push(dataArr[key2]);
								}
								var dom1 = document.getElementById("machineBar1");
								DrawBar(dom1,nameArr,dayArr);
						}else if(key1 == 'T200'){
							dataArr = data[key1];
							for(var key2 in dataArr){
								nameArr.push(key2);
								dayArr.push(dataArr[key2]);
							}
							var dom2 = document.getElementById("machineBar2");
							DrawBar(dom2,nameArr,dayArr);
						}else if(key1 == 'SUMMIT'){
							dataArr = data[key1];
							for(var key2 in dataArr){
								nameArr.push(key2);
								dayArr.push(dataArr[key2]);
							}
							var dom3 = document.getElementById("machineBar3");
							DrawBar(dom3,nameArr,dayArr);
						}else if(key1 == 'PA200'){
							dataArr = data[key1];
							for(var key2 in dataArr){
								nameArr.push(key2);
								dayArr.push(dataArr[key2]);
							}
							var dom4 = document.getElementById("machineBar4");
							DrawBar(dom4,nameArr,dayArr);
						}else if(key1 == 'CM300'){
							dataArr = data[key1];
							for(var key2 in dataArr){
								nameArr.push(key2);
								dayArr.push(dataArr[key2]);
							}
							var dom5 = document.getElementById("machineBar5");
							DrawBar(dom5,nameArr,dayArr);
						}
				    } 
				console.log(nameArr)  
				console.log(dayArr)    
			 }
		 })
 }
machineBarData();

//历年销售的柱状图
function DrawBar(dom,nameArr,dayArr){
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
    	        data:['装机时间(天)']
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
    	            name: '员工姓名',
    	            data : nameArr
    	        }
    	    ],
    	    yAxis : [
    	        {
    	            type : 'value',
        	        name: '时间(天)',
    	        }
    	    ],
    	    series : [
    	        {
    	            name:'装机时间(天)',
    	            type:'bar',
    	            data:dayArr,
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