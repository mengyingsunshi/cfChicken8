$(function(){
	var year = new Date().getFullYear();
	var years = year - 2009;
	var str = '<option>所有</option>';
	for(var i = years;i>=0;i--){
		str += '<option>'+(2009+i)+'</option>';
	}
	$('.timeSelect select').append(str);
	var ajaxData = {
			LoadType:'data'
	};
	showTransportRecord('all',ajaxData);
})
var resizeTimer = null;
$(window).bind('resize', function (){
        if (resizeTimer) clearTimeout(resizeTimer);
        resizeTimer = setTimeout(function(){
        	timeData();
        } , 100);
});
$('.refresh_btn').click(function(){
	timeData();
})
var timeData = function(){
	var ajaxData = new Object();
	var time = $('.timeSelect  option:selected').text();
	var Type = '';
	if(time == '所有'){
		Type = 'all';
		ajaxData = {
				LoadType:'data'
		}
	}else{
		Type = 'month';
		ajaxData = {
				LoadType:'data',
				Year:time
		}
	}
	showTransportRecord(Type,ajaxData);
}

/*折线图*/	
 function showTransportRecord(Type,ajaxData){
	$.ajax({
		type:'get',
		url:'TransportRecord',
		data:ajaxData,
		dataType:'JSON',
		success:function(data){
			if(data.length == 1){
				 $.MsgBox.Alert('提示', '该时间内没有查询到发货记录！');
				 return;
			}else{
				var countArr = [];
				var timeArr = [];
				if(Type == 'all'){
					for(var i = 1;i<data.length;i++){
						timeArr.push(data[i].years);
						countArr.push(data[i].Count);
					}
				}else{
					for(var i = 1;i<data.length;i++){
						data[i].months = data[i].months.replace(/-/g,'/')
						timeArr.push(data[i].months);
						countArr.push(data[i].Count);
					}
				}
				/*console.log(timeArr)
				console.log(countArr)*/	
			}
		  	$(".lineBox_body").html('');
		  	var str = '<div id="main" style="height:460px;width:100%;margin-top:0px;margin-left:0px"></div>';
		  	$(".lineBox_body").append(str);
		var dom = document.getElementById("main");
					var myChart = echarts.init(dom);
					var app = {};
					option = null;
			
		option = {
			    color: ['#3398DB'],
			    title: {
			        text: '',
			        subtext: '',
			        left: 'left',
			        textStyle: {
			        	fontSize: 16,
			            fontFamily:'微软雅黑',
			            fontWeight:'normal',
			            color: '#00aeef'          // 主标题文字颜色
			        },
			    },
			    
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },

			    
           
			    xAxis : [
			        {
			            type : 'category',
                        name:'时间',  
			            data : timeArr,
			            splitLine:{ 
	                        show:false
	                    },
			            axisTick: {
			                alignWithLabel: true
			            },
			            axisLabel: {
			            	interval:0,//横轴信息全部显示  
			            	rotate:0,//-30度角倾斜显示 
                            textStyle: {
                                color: '#333',//坐标值得具体的颜色
         						fontFamily:'微软雅黑',
                            }
                        }
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
                        name:'发货(单)',  
                        position:'center',
			            axisLabel: {
                            textStyle: {
                                color: '#333',//坐标值得具体的颜色
         						fontFamily:'微软雅黑',
                            }
                        }  
			        }
			    ],
			    toolbox: {
			        show: true,
			        orient: 'vertical',
			        left: 'right',
			        top: 'center',
			        feature: {
			        	magicType: {
			        		type: '',
			        		show: true
			        		},
			            dataView: {readOnly: false},
			            restore: {},
			            saveAsImage: {}
			        }
			    },
			    series : [
			        {
			            name:'发货(单)',
			            type:'line',
			            barWidth: '75%',
			            label:{
			            	normal:{
				            	show:true,            //显示数字
				            	position: 'top'        //这里可以自己选择位置
				            	}
			            	},
			            itemStyle: {  
			            	
			                normal:{  
			                	 label:{
	                                 show:true,
	                                 position:'top',
	                                 textStyle: {
	                                     color:'rgba(0,0,0,0.7)',
	                                     fontSize:12
	                                 },
	                                 formatter:function(params){
	                                     if(params.value==0){
	                                         return '';
	                                     }else{
	                                         return params.value;
	                                         }
	                                 }
	                             },
			                }
			            },
			            data:countArr
			        }
			    ]
			};
	
	
	
		if (option && typeof option === "object") {
		    myChart.setOption(option, true);
		}
		
		},
		error:function(){
			$.MsgBox_Unload.Alert("提示", "网络错误请稍候重试！");
		}
		
	})
}

