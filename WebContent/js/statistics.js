/**
 * Created by eoulu on 2017/4/20.
 */

//时间下拉框
$(function(){
	
var curLocation = window.location.href;
var curLocationBool = curLocation.indexOf("?Year=")>-1?true:false;
var currentYear = parseInt($(".currentYear").html());
var currentMonth = parseInt($(".currentMonth").html());
var Year = parseInt($(".Year").html());
var Month = $(".Month").html();
if(Month == ''){
	Month = parseInt($(".currentMonth").html())+"月";;
}else if(Month == 'All'){
	Month = "所有";
}else{
	 Month = parseInt(Month)+"月";
}

//年份的下拉框
var LD = currentYear - 2016;
	$(".searchBoxContentD").empty();
	var strD = '';
	var str1 = '';
	for(var i = 0;i<=LD;i++){
		str1 += '<option value ="" class="opd" text="'+(2016+i)+'">'+(2016+i)+'</option>';
	}
	console.log(str1);
	strD = '<select  class="selD">'+str1+'</select>';
    $(".searchBoxContentD").append(strD);
    if(curLocationBool==false){
        $(".searchBoxContentD").find('option[text="' + currentYear + '"]').prop("selected", true);
    }else{
        var trueY = curLocation.split("?Year=")[1].split("&Month")[0];
        $(".searchBoxContentD").find('option[text="' + trueY + '"]').prop("selected", true);
    }
	
//月份的下拉框	 刚进来默认是2016年所以显示12个月
	var str2 = '';
	var strM = '';
	for(var i = 1;i<=12;i++){
		  str2 += '<option value ="" class="opm" text="'+(i)+'月">'+(i)+'月</option>'
	}
	strM = '<select class="selM">'+
	   '<option value ="" class="opm" text="所有">所有</option>'+
	     str2
	   +'</select>' 
	$(".searchBoxContentM").empty();
	$(".searchBoxContentM").append(strM);
	$(".searchBoxContentM").find('option[text="' + Month + '"]').prop("selected", true);
})
/*var LM = currentMonth;
//测试完注释
var LM = 4;
var str2 = '';
var strM = '';
	var mydate = $(".searchBoxContentD option:selected").html();
	alert(mydate);
	if(mydate == currentYear){
		for(var i = 1;i<=LM;i++){
			  str2 += '<option value ="" class="opm">'+(i)+'月</option>'
		}
		console.log(str2);
	}else{
		alert(444)
		for(var i = 1;i<=12;i++){
			  str2 += '<option value ="" class="opm">'+(i)+'月</option>'
		}
		console.log(str2);
	}
	strM = '<select class="selM">'+
		   '<option value ="" class="opm">所有</option>'+
	  str2
	'</select>' 
	  $(".searchBoxContentM").empty('');
	 $(".searchBoxContentM").append(strM);*/	

var currentYear = parseInt($(".currentYear").html());
var currentMonth = parseInt($(".currentMonth").html());
var str3 = '';
var LM = currentMonth;
$(".searchBoxContentD .selD").blur(function(){
	var mydate = $(".searchBoxContentD option:selected").html();
	$(".searchBoxContentM .selM").remove();
	if(mydate == currentYear){
		for(var i = 1;i<=LM;i++){
			  str3 += '<option value ="" class="opm" text="'+(i)+'月">'+(i)+'月</option>'
		}
		console.log(str3);
	}else{
		for(var i = 1;i<=12;i++){
			  str3 += '<option value ="" class="opm" text="'+(i)+'月">'+(i)+'月</option>'
		}
		console.log(str3);
	}
	strM = '<select class="selM">'+
		   '<option value ="" class="opm" text="所有">所有</option>'+
	  str3
	  + '</select>'
	  $(".searchBoxContentM .selM").remove();
	 $(".searchBoxContentM").append(strM);	  
})

//sousuo
var btnNum = 0;
$("#btn").click(function () {
	//
    if (btnNum == 0) {
        $(this).val('柱状图');
        $(".pContentleft1").fadeOut(50);
        $(".pContentleft2").fadeIn(50);
        btnNum = 1;
        var dom = document.getElementById("picture1");
        var realData=[[10, 11, 5],[2, 3, 1]];
        Area(dom,realData);
    }
    else if (btnNum == 1) {
        $(this).val('地图');
        $(".pContentleft2").fadeOut(50);
        $(".pContentleft1").fadeIn(50);
        $('body').css("background", "#fff");
        $(".public-top").css("color", "#000");
        btnNum = 0;
    }
});
function showWhichContent(ContentName){
    if(ContentName=='销售信息'){
    	$('.pContent').css({
    		'height':'76%'
    	});
        $('.timeSelect').hide();
        $('.timerefresh').hide();
        $(".SalesData_box").hide();	
    	$(".searchBox").show();
        $(".statistics-export").hide();
    	$('.sale_li').addClass('currentView');
    	$('.sale_li').siblings('li').removeClass('currentView');
    	$(".pContentleft1").fadeOut(50);
        $(".pContentleft2").fadeIn(50);
        $('#picture2').show();
        $('#picture1').hide();
        $('.pC-right').hide();
        $(".orderNumber").hide();
        $(".PriceDataTable").hide();
        var dom = document.getElementById("picture2");
        var realData=new Array(2);
        realData=[salesData[0],dealData(salesData[1])]
        Sales(dom,realData);
    }else if(ContentName=='合同信息'){
    	$('.pContent').css({
    		'height':'76%'
    	});
        $('.timeSelect').hide();
        $('.timerefresh').hide();
        $(".SalesData_box").hide();	
    	$(".searchBox").show();
        $(".statistics-export").hide();
    	 $('.Contant_li').addClass('currentView');
    	 $('.Contant_li').siblings('li').removeClass('currentView');
    	 $(".pContentleft1").fadeIn(50);
         $(".pContentleft2").fadeOut(50);
         $('#picture2').hide();
         $('#picture1').show();
         $('.pC-right').show();
         $(".orderNumber").hide();
         $(".PriceDataTable").hide();
    }else if(ContentName=='个人成单率'){
    	$('.pContent').css({
    		'height':'76%'
    	});
        $('.timeSelect').hide();
        $('.timerefresh').hide();
        $(".SalesData_box").hide();	
    	$(".searchBox").hide();
        $(".statistics-export").hide();
    	$('.successRate_li').addClass('currentView');
    	$('.successRate_li').siblings('li').removeClass('currentView');
    	$(".pContentleft1").fadeOut(50);
        $(".pContentleft2").fadeIn(50);
        $('#picture2').show();
        $('#picture1').hide();
        $('.pC-right').hide();
        $(".orderNumber").hide();
        $(".PriceDataTable").hide();
	     var dom = document.getElementById("picture2");
	     var realData=new Array(2);
	     realData=[chengdanlvData[0],dealData(chengdanlvData[1])]
	     Person(dom,realData);
    }
    else if(ContentName=='月信息'){
    	$('.pContent').css({
    		'height':'76%'
    	});
        $('.timeSelect').hide();
        $('.timerefresh').hide();
        $(".SalesData_box").hide();	
    	$(".searchBox").show();
    	console.log("in");
        $(".statistics-export").show();
    	$('.monthInfo_li').addClass('currentView');
    	$('.monthInfo_li').siblings('li').removeClass('currentView');
    	$(".pContentleft1").fadeOut(50);
        $(".pContentleft2").fadeOut(50);
        $('#picture2').hide();
        $('#picture1').hide();
        $('.pC-right').hide();
        $(".orderNumber").hide();
        $(".PriceDataTable").show();
    }
    else if(ContentName=='历年销售'){
    	$('.pContent').css({
    		'height':'120%'
    	});
    	$(".searchBox").hide();
    	console.log("in");
        $(".SalesData_box").show();
    	$('.SalesData_li').addClass('currentView');
    	$('.SalesData_li').siblings('li').removeClass('currentView');
    	$(".pContentleft1").fadeOut(50);
        $(".pContentleft2").fadeOut(50);
        $('#picture2').hide();
        $('#picture1').hide();
        $('.pC-right').hide();
        $(".orderNumber").hide();
        $(".PriceDataTable").hide();
        $('.timeSelect').show();
        $('.timerefresh').show();
        var dom1 = document.getElementById("SalesData_bar");
        var dom2 = document.getElementById("ContractData_bar");
	        var date = new Date();
	    	var y = date.getFullYear();
	    	var m = date.getMonth()+1;
	    	if(m<10){
	    		m = '0'+m;
	    	}
	    	var d = date.getDate();
	    	if(d < 10){
	    		d= '0'+ d;
	    	}
	    	var timeVal = y + '-' + m + '-' + d;
	    	var time = timeVal.slice(5);
	    	$('.timeSelect').val(timeVal);
    	SalesData(dom1,dom2,time);
        $(".SalesData_box").show();
        
    }
    else if(ContentName=='成单数量'){
    	$('.pContent').css({
    		'height':'76%'
    	});
        $('.timeSelect').hide();
        $('.timerefresh').hide();

        $(".SalesData_box").hide();	
    	$(".searchBox").hide();
    	$('.successNum_li').addClass('currentView');
    	$('.successNum_li').siblings('li').removeClass('currentView');
    	$(".pContentleft1").fadeOut(50);
        $(".pContentleft2").fadeOut(50);
        $('#picture2').hide();
        $('#picture1').hide();
        $('.pC-right').hide();
        $(".PriceDataTable").hide();
        var dom = document.getElementById("orderNumber_bar");
        var volumnData = volumn;
        console.log(volumnData)
        var volumnX = [];
        var volumnY1 = [];
        var volumnY2 = [];
        for(var i = 0;i<volumnData.length;i++){
        	volumnX.push(volumnData[i].Name);
        	volumnY1.push(volumnData[i].Count[0]);
        	volumnY2.push(volumnData[i].Count[1]);
        }
    	console.log(volumnX)
    	console.log(volumnY1)
    	console.log(volumnY2)
    	var volumnData = new Array(3);
    	volumnData[0] = volumnX;
    	volumnData[1] = volumnY1;
    	volumnData[2] = volumnY2;
        OrderNumber(dom,volumnData);
        
        
        $(".orderNumber").show();
        
        
    }

}
$('.nav ul li').click(function () {
	var ContentName = $(this).find('a').html();
	showWhichContent(ContentName);
});

/*$('.sales').click(function () {
    if($(this).find('span').html()=='销售信息'){
        $(this).find('span').html('合同信息');
        $(".pContentleft1").fadeOut(500);
        $(".pContentleft2").fadeIn(500);
        $('#picture2').show();
        $('#picture1').hide();
        $('.pC-right').hide();
        var dom = document.getElementById("picture2");
        var realData=salesData;;
        Sales(dom,realData);
    }else{
        $(this).find('span').html('销售信息');
        $(".pContentleft1").fadeIn(500);
        $(".pContentleft2").fadeOut(500);
        $('#picture2').hide();
        $('#picture1').show();
        $('.pC-right').show();
    }
});*/
function Contact(dom,data) {
    var rest=[];
        for (var i = 0; i < data[0].length; i++) {
            rest[i] = (data[0][i]-data[1][i]).toFixed(2);
            if(rest[i]<0){
                rest[i]=0;
            }
        }
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        title : {
            text: '合同签订情况'
        },
        legend: {
            data: ['未完成', '已完成','总目标']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                // 'stack', 
                magicType: {show: true, type: ['line', 'bar', 'tiled']},
                restore: {show: true}
                //		            saveAsImage : {show: true}
            }
        },
        calculable: true,
        yAxis: [
            {
                type: 'value',
                name: '业绩',
                nameTextStyle:{
                    fontFamily:'微软雅黑'
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        fontSize: 18
                    },
                    formatter: '{value} M'
                }
            }
        ],
        xAxis: [
            {
                type: 'category',
                name: '区域',
                data: ['北方', '南方', '西南'],
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: 'red',
                        fontSize: 26
                    },
                }
            }
        ],
        series: [
            {
                name: '未完成',
                type: 'bar',
                // stack: '总量',
                tiled: '总量',
                itemStyle: {normal: {label: 
                    {show: true, position: 'top',
                    textStyle: {
                        color:'rgba(0,0,0,0.9)',
                        fontSize:10
                    }
                    }
                    }
                },
                data: rest
            },
            {
                name: '已完成',
                type: 'bar',
                // stack: '总量',
                tiled: '总量',
                itemStyle: {normal: {label: 
                    {show: true, position: 'top',
                    textStyle: {
                        color:'rgba(0,0,0,0.9)',
                        fontSize:10
                    }
                    }
                    }
                },
                data: data[1]
            },{
                name: '总目标',
                type: 'bar',
                // stack: '总量',
                tiled: '总量',
                itemStyle: {normal: {label: 
                    {show: true, position: 'top',
                    textStyle: {
                        color:'rgba(0,0,0,0.9)',
                        fontSize:10
                    }
                    }
                    }
                },
                data: data[0]
            }
        ]
    };

    myChart.setOption(option);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
        window.onresize = myChart.resize;
    }
}
function Sales(dom,data) {
    dom.innerHTML = '';
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        title : {
            text: '销售情况'
        },
        legend: {
            data: ['销售量']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: data[0],
                name: '销售人员'
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '业绩',
                nameTextStyle:{
                    fontFamily:'微软雅黑'
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: 'red',
                        fontSize: 26
                    },
                    formatter: '{value} M'
                }
            }
        ],
        series: [
           {
                name: '销售量',
                type: 'bar',
                itemStyle: {normal: {label: {show: true}}},
                data: data[1]
            }
        ]
    };
    myChart.setOption(option);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
        window.onresize = myChart.resize;
    }
}
function Area(dom,data){
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        tooltip: {
            trigger: 'item',
            formatter: function (params) {
                var MAP_VALUE_DIC = {
                    '1': '10M',
                    '2': '2M',
                    '4': '3M'
                };
                if (params.seriesType) {
                    return params.name + ': ' + MAP_VALUE_DIC[params.value];
                } else {
                    return params.name;
                }
            }
        },

        visualMap: {
            type: 'piecewise',
            splitNumber: 3,
            piecewise: [

                {
                    value: 1,
                    label: '北方',
                    color: 'orangered'
                }, {
                    value: 2,
                    label: '南方',
                    color: 'red'
                }, {
                    value: 4,
                    label: '西南',
                    color: 'lightskyblue'
                }

            ],
            min: 0,
            max: 4,
            itemWidth: 40,
            itemHeight: 40,
            align: 'left',
            left: 'left',
            top: 'middle',
            calculable: true,
            show: true,
            seriesIndex: 0,
            inRange: {
                color: ['#0092d7', '#92dce0', '#4ca8a1'],
                symbolSize: [30, 100]
            },
            formatter: function (value) {
                if (value > 2 && value < 4) {
                    return '西南（已完成目标：80%）';
                } else if (value > 1 && value < 2) {
                    return '南方（已完成目标：85%）';
                } else {
                    return '北方（已完成目标：86%）';
                }
            },
            textStyle: {
                color: 'darkcyan',
                fontSize: 20
            }
        },
        series: [{
            name: '中国',
            type: 'map',
            mapType: 'china',

            label: {
                normal: {
                    show: false
                },
                emphasis: {
                    show: false
                }
            },
            data: []

        }, {
            name: '中国',
            type: 'map',
            mapType: 'china',
            selectedMode: 'multiple',
            label: {
                normal: {
                    show: false
                },
                emphasis: {
                    show: true
                }
            },
            data: [],
            markLine: {
                smooth: true,
                symbol: ['none', 'circle'],
                symbolSize: 1,
                itemStyle: {
                    normal: {
                        color: '#c23531',
                        borderWidth: 1,
                        borderColor: 'rgba(30,144,255,0.5)'
                    }
                }

            }
        }, {
            name: '目标',
            type: 'map',
            mapType: 'china',
            label: {
                normal: {
                    show: false
                },
                emphasis: {
                    show: true
                }
            },

            data: [
                {
                name: '重庆',
                value: 4
            }, 
                {
                name: '云南',
                value: 4
            }, {
                name: '辽宁',
                value: 4
            }, {
                name: '黑龙江',
                value: 4
            }, {
                name: '广西',
                value: 4
            }, {
                name: '甘肃',
                value: 4
            }, {
                name: '陕西',
                value: 4
            }, {
                name: '吉林',
                value: 4
            }, {
                name: '贵州',
                value: 4
            }, {
                name: '新疆',
                value: 4
            }, {
                name: '青海',
                value: 4
            }, {
                name: '宁夏',
                value: 4
            }, {
                name: '台湾',
                value: 2
            }, {
                name: '香港',
                value: 2
            }, {
                name: '上海',
                value: 2
            }, {
                name: '安徽',
                value: 4
            }, {
                name: '江苏',
                value: 2
            }, {
                name: '浙江',
                value: 2
            }, {
                name: '北京',
                value: 1
            }, {
                name: '天津',
                value: 1
            }, {
                name: '河北',
                value: 1
            }, {
                name: '河南',
                value: 4
            }, {
                name: '内蒙古',
                value: 4
            }, {
                name: '湖南',
                value: 2
            }, {
                name: '山东',
                value: 4
            }, {
                name: '江西',
                value: 2
            }, {
                name: '湖北',
                value: 2
            }, {
                name: '福建',
                value: 2
            }, {
                name: '广东',
                value: 2
            }, {
                name: '西藏',
                value: 4
            }, {
                name: '四川',
                value: 4
            }, {
                name: '山西',
                value: 2
            }, {
                name: '海南',
                value: 2
            }]
        }]
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
        window.onresize = myChart.resize;
    }
}

//个人成单率
function Person(dom,data) {
    dom.innerHTML = '';
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        title : {
            text: '个人成单率'
        },
        legend: {
            data: ['成单率']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: data[0],
                name: '销售人员'             
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '成单率',
                nameTextStyle:{
                    fontFamily:'微软雅黑'
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: 'red',
                        fontSize: 26
                    },
                    formatter: '{value} %'
                }
            }
        ],
        series: [
           {
                name: '成单率',
                type: 'bar',
                itemStyle: {normal: {label: {show: true}}},
                data: data[1]
            }
        ]
    };
    myChart.setOption(option);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
        window.onresize = myChart.resize;
    }
}
/*保留2位小数*/
function dealData(data){
	var allData=[];
	for(var i=0;i<data.length;i++){
		data[i].toFixed(2);
		allData.push(data[i].toFixed(2))
    }
	return allData;
}

/*判断左边显示图形*/
function Judge(data){
	for(var i=0;i<data[0].length;i++){
		if(i==0){
			if(data[1][i]/data[0][i]>=1){
				$('.pC-left ul').append('<li><img src="image/gsbg4.gif"/></li>')
			}else if(data[1][i]/data[0][i]>=0.5){
				$('.pC-left ul').append('<li><img src="image/gsbg8.gif"/></li>')

			}else{
				$('.pC-left ul').append('<li><img src="image/gsbg3.gif"/></li>')
			}
		}else if(i==1){
			if(data[1][i]/data[0][i]>=1){
				$('.pC-left ul').append('<li><img src="image/20.gif"/></li>')
			}else if(data[1][i]/data[0][i]>=0.5){
				$('.pC-left ul').append('<li><img src="image/10.gif"/></li>')

			}else{
				$('.pC-left ul').append('<li><img src="image/9.gif"/></li>')
			}
		}
		else if(i==2){
			if(data[1][i]/data[0][i]>=1){
				$('.pC-left ul').append('<li><img src="image/22.gif"/></li>')
			}else if(data[1][i]/data[0][i]>=0.5){
				$('.pC-left ul').append('<li><img src="image/5.gif"/></li>')

			}else{
				$('.pC-left ul').append('<li><img src="image/jy.gif"/></li>')
			}
		}
	}
}
//成单数量

//成单数量的柱状图
function OrderNumber(dom,data){
    dom.innerHTML = '';
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        title : {
            text: '订单数(单位:单)'
        },
        legend: {
            data: ['接单量','成单量']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: data[0],
                name: '销售人员'             
            }
        ],
        yAxis: [
            {
                type: 'value',
                nameTextStyle:{
                    fontFamily:'微软雅黑'
                }
            }
        ],
        series: [
            {
                     name: '接单量',
                     type: 'bar',
                     itemStyle: {normal: {
                    	 label: {show: true},
                    	 color:'#58a0a8'
                     }
            },
                     data: data[2]
            },
            {
                name:'成单量',
                type:'bar',
                itemStyle: {normal: {label: {show: true}}},
                data:data[1]
            }
        ]
    };
    myChart.setOption(option);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
        window.onresize = myChart.resize;
    }
}




//初始化页面
$(function(){
	//合同
    var dom = document.getElementById("picture");
//    var realData=[[10, 11, 5],[2, 10, 3]];
    var realData=new Array(2);
    realData=[dealData(areaData[0]),dealData(areaData[1])]
    Judge(areaData)
    Contact(dom,realData);

    // 导出月信息
    $(document).on("click",".export-current",function(){
        var year = $(".searchBoxContentD option:selected").html().toString();
        // alert(year);
        window.location.href = 'ExportStatistics?year='+ year;
    });
    $(document).on("click",".export-all",function(){
        window.location.href = 'ExportStatistics?year=All';
    });
     
});


//历年销售的柱状图
function getSale(dom1,pastArr){
    dom1.innerHTML = '';
    var myChart = echarts.init(dom1);
    var app = {};
    option = null;
    option = {
    		
    	    tooltip : {
    	        trigger: 'axis',
    	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    	        }
    	    },
    	    legend: {
    	        data:['今日销售额']
    	    },
    	    toolbox: {
    	        show : true,
    	        feature : {
    	            mark : {show: true}
    	        }
    	    },
    	    calculable : true,
    	    xAxis : [
    	        {
    	            type : 'value',
    	            	min:0,
        	            max:30,
                   name: '业绩(M)'  ,
                   splitLine:{
                	　　　　show:false
                	　　}
    	        }
    	    ],
    	    yAxis : [
    	        {
    	            type : 'category',
    	            splitLine:{
                	　　　　show:true
                	　　},
    	            data : ['2016','2017','2018','2019']
    	        }
    	    ],
    	    series : [
    	        {
    	            name:'今日销售额',
    	            type:'bar',
    	            stack: '业绩',
    	            itemStyle : { normal: 
    	            	{
    	            		label : {show: true, 
    	            				position: 'right',
    	            				textStyle: {	    
    	                                color: 'black',
    	                                fontSize: 14
    	                            }
    	            					}
    	            	}
    	        },
    	            data:pastArr
    	        }
    	        
    	    ]
    	};
    	                    
    myChart.setOption(option);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
        window.onresize = myChart.resize;
    }
}
var SalesData = function(dom1,dom2,time){
	 $.ajax({
		 type:'get',
		 url:'Statistics',
		 dataType:'JSON',
		 data:{
			 loadType   : 'yearSales',
			 date  :time 
		 },
		 success:function(data){
			 var pastArr = [];
			 var todayArr = [];
			 var str = JSON.stringify(data);
			 str = str.replace(/2016/,'year2016');
			 str = str.replace(/2017/,'year2017');
			 str = str.replace(/2018/,'year2018');
			 str = str.replace(/2019/,'year2019');
			 str = str.replace(/1M/g,'M1');
			 str = str.replace(/2M/g,'M2');
			 str = str.replace(/3M/g,'M3');
			 str = str.replace(/5M/g,'M5');
			 str = str.replace(/7M/g,'M7');
			 str = str.replace(/9M/g,'M9');
			 str = str.replace(/10M/g,'M10');
			 str = str.replace(/1M2/g,'M12');
			 str = str.replace(/20M/g,'M20');
			  var arrList = JSON.parse(str);
			  console.log(arrList)
			 var past2016 = arrList.year2016.sales;
			 var past2017 = arrList.year2017.sales;
			 var past2018 = arrList.year2018.sales;
			 var past2019 = arrList.year2019.sales;
			 starMark(past2016,past2017,past2018,past2019);
			
			 
			var stage2016 = arrList.year2016.stage;
			var stage2017 = arrList.year2017.stage;
			var stage2018 = arrList.year2018.stage;
			
			var stage2016_attrCon = $('.SalesData_li').attr('stage201678');
			if(stage2016_attrCon == undefined){
				$('.SalesData_li ').attr('stage201678','exit');
				finishTime_Past(stage2016,stage2017,stage2018);
			}
			
			var stage2019 = arrList.year2019.stage;
			finishTime_thisYear(stage2019);
			 pastArr.push(past2016);
			 pastArr.push(past2017);
			 pastArr.push(past2018);
			 pastArr.push(past2019);
			 getSale(dom1,pastArr);
			 
			 
			 	var contract2016 = arrList.year2016.contract;
				var contract2017 = arrList.year2017.contract;
				var contract2018 = arrList.year2018.contract;
				var contract2019 = arrList.year2019.contract;
				var contractArr = [];
				contractArr.push(contract2016);
				contractArr.push(contract2017);
				contractArr.push(contract2018);
				contractArr.push(contract2019);
			 getContract(dom2,contractArr)
		 }
	 })
	 
	 
 }    
$('.timeSelect').change(function(){
	if($(this).val().slice(0,4) != '2019'){
		  $.MsgBox_Unload.Alert("提示","请选择当前年的某一天");
		  $(this).val('');
	}
})
$('.timerefresh').click(function(){
	var time = $('.timeSelect').val().slice(5);
	var dom1 = document.getElementById("SalesData_bar");
	var dom2 = document.getElementById("ContractData_bar");
	SalesData(dom1,dom2,time);
})

/*$('.SalesData_li ').click(function(){*/
	
	
/*})*/
/*var resizeTimer = null;
$(window).bind('resize', function (){
if (resizeTimer) clearTimeout(resizeTimer);
resizeTimer = setTimeout(function(){
	var dom = document.getElementById("SalesData_bar");
	var time = $('.timeSelect').val().slice(5);
 	SalesData(dom,time);
} , 100);
});*/
var finishTime_Past = function(stage2016,stage2017,stage2018){
	console.log(stage2017)
		//1
		var stage2016_M1 = stage2016.M1.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2016_M1 == ''){
			var stage2016_M1_title = '未完成';
		}else{
			var stage2016_M1_title = stage2016_M1 + '完成';
		}
		$('.saleStar .year2016 i:nth-child(1)').append(stage2016_M1);
		$('.saleStar .year2016 span:nth-child(1)').attr('title',stage2016_M1_title) ;
		
		
		//2
		var stage2016_M2 = stage2016.M2.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2016_M2 == ''){
			var stage2016_M2_title = '未完成';
		}else{
			var stage2016_M2_title = stage2016_M2 + '完成';
		}
		$('.saleStar .year2016 i:nth-child(2)').append(stage2016_M2);
		$('.saleStar .year2016 span:nth-child(2)').attr('title',stage2016_M2_title) ;
		
		//3
		var stage2016_M3 = stage2016.M3.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2016_M3 == ''){
			var stage2016_M3_title = '未完成';
		}else{
			var stage2016_M3_title = stage2016_M3 + '完成';
		}
		$('.saleStar .year2016 i:nth-child(3)').append(stage2016_M3);
		$('.saleStar .year2016 span:nth-child(3)').attr('title',stage2016_M3_title) ;
		
		//5
		var stage2016_M5 = stage2016.M5.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2016_M5 == ''){
			var stage2016_M5_title = '未完成';
		}else{
			var stage2016_M5_title = stage2016_M5 + '完成';
		}
		$('.saleStar .year2016 i:nth-child(4)').append(stage2016_M5);
		$('.saleStar .year2016 span:nth-child(4)').attr('title',stage2016_M5_title) ;
		
		//7
		var stage2016_M7 = stage2016.M7.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2016_M7 == ''){
			var stage2016_M7_title = '未完成';
		}else{
			var stage2016_M7_title = stage2016_M7 + '完成';
		}
		$('.saleStar .year2016 i:nth-child(5)').append(stage2016_M7);
		$('.saleStar .year2016 span:nth-child(5)').attr('title',stage2016_M7_title) ;
		
		//9
		var stage2016_M9 = stage2016.M9.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2016_M9 == ''){
			var stage2016_M9_title = '未完成';
		}else{
			var stage2016_M9_title = stage2016_M9 + '完成';
		}
		$('.saleStar .year2016 i:nth-child(6)').append(stage2016_M9);
		$('.saleStar .year2016 span:nth-child(6)').attr('title',stage2016_M9_title) ;
		//10
		var stage2016_M10 = stage2016.M10.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2016_M10 == ''){
			var stage2016_M10_title = '未完成';
		}else{
			var stage2016_M10_title = stage2016_M10 + '完成';
		}
		$('.saleStar .year2016 i:nth-child(7)').append(stage2016_M10);
		$('.saleStar .year2016 span:nth-child(7)').attr('title',stage2016_M10_title) ;
		//12
		var stage2016_M12 = stage2016.M12.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2016_M12 == ''){
			var stage2016_M12_title = '未完成';
		}else{
			var stage2016_M12_title = stage2016_M12 + '完成';
		}
		$('.saleStar .year2016 i:nth-child(8)').append(stage2016_M12);
		$('.saleStar .year2016 span:nth-child(8)').attr('title',stage2016_M12_title) ;
		
		//20
		var stage2016_M20 = stage2016.M20.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2016_M20 == ''){
			var stage2016_M20_title = '未完成';
		}else{
			var stage2016_M20_title = stage2016_M20 + '完成';
		}
		$('.saleStar .year2016 i:nth-child(9)').append(stage2016_M20);
		$('.saleStar .year2016 span:nth-child(9)').attr('title',stage2016_M20_title) ;
	
		
		
		//2017
		var stage2017_M1 = stage2017.M1.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2017_M1 == ''){
			var stage2017_M1_title = '未完成';
		}else{
			var stage2017_M1_title = stage2017_M1 + '完成';
		}
		$('.saleStar .year2017 i:nth-child(1)').append(stage2017_M1);
		$('.saleStar .year2017 span:nth-child(1)').attr('title',stage2017_M1_title) ;
		//2
		var stage2017_M2 = stage2017.M2.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2017_M2 == ''){
			var stage2017_M2_title = '未完成';
		}else{
			var stage2017_M2_title = stage2017_M2 + '完成';
		}
		$('.saleStar .year2017 i:nth-child(2)').append(stage2017_M2);
		$('.saleStar .year2017 span:nth-child(2)').attr('title',stage2017_M2_title) ;
		
		//3
		var stage2017_M3 = stage2017.M3.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2017_M3 == ''){
			var stage2017_M3_title = '未完成';
		}else{
			var stage2017_M3_title = stage2017_M3 + '完成';
		}
		$('.saleStar .year2017 i:nth-child(3)').append(stage2017_M3);
		$('.saleStar .year2017 span:nth-child(3)').attr('title',stage2017_M3_title) ;
		//5
		var stage2017_M5 = stage2017.M5.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2017_M5 == ''){
			var stage2017_M5_title = '未完成';
		}else{
			var stage2017_M5_title = stage2017_M5 + '完成';
		}
		$('.saleStar .year2017 i:nth-child(4)').append(stage2017_M5);
		$('.saleStar .year2017 span:nth-child(4)').attr('title',stage2017_M5_title) ;
		
		//7
		var stage2017_M7 = stage2017.M7.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2017_M7 == ''){
			var stage2017_M7_title = '未完成';
		}else{
			var stage2017_M7_title = stage2017_M7 + '完成';
		}
		$('.saleStar .year2017 i:nth-child(5)').append(stage2017_M7);
		$('.saleStar .year2017 span:nth-child(5)').attr('title',stage2017_M7_title) ;
		
		//9
		var stage2017_M9 = stage2017.M9.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2017_M9 == ''){
			var stage2017_M9_title = '未完成';
		}else{
			var stage2017_M9_title = stage2017_M9 + '完成';
		}
		$('.saleStar .year2017 i:nth-child(6)').append(stage2017_M9);
		$('.saleStar .year2017 span:nth-child(6)').attr('title',stage2017_M9_title) ;
		
		//10
		var stage2017_M10 = stage2017.M10.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2017_M10 == ''){
			var stage2017_M10_title = '未完成';
		}else{
			var stage2017_M10_title = stage2017_M10 + '完成';
		}
		$('.saleStar .year2017 i:nth-child(7)').append(stage2017_M10);
		$('.saleStar .year2017 span:nth-child(7)').attr('title',stage2017_M10_title) ;
		//12
		var stage2017_M12 = stage2017.M12.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2017_M12 == ''){
			var stage2017_M12_title = '未完成';
		}else{
			var stage2017_M12_title = stage2017_M12 + '完成';
		}
		$('.saleStar .year2017 i:nth-child(8)').append(stage2017_M12);
		$('.saleStar .year2017 span:nth-child(8)').attr('title',stage2017_M12_title) ;
		
		//20
		var stage2017_M20 = stage2017.M20.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2017_M20 == ''){
			var stage2017_M20_title = '未完成';
		}else{
			var stage2017_M20_title = stage2017_M20 + '完成';
		}
		$('.saleStar .year2017 i:nth-child(9)').append(stage2017_M20);
		$('.saleStar .year2017 span:nth-child(9)').attr('title',stage2017_M20_title) ;
	
		var stage2018_M1 = stage2018.M1.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2018_M1 == ''){
			var stage2018_M1_title = '未完成';
		}else{
			var stage2018_M1_title = stage2018_M1 + '完成';
		}
		$('.saleStar .year2018 i:nth-child(1)').append(stage2018_M1);
		$('.saleStar .year2018 span:nth-child(1)').attr('title',stage2018_M1_title) ;

		//2
		var stage2018_M2 = stage2018.M2.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2018_M2 == ''){
			var stage2018_M2_title = '未完成';
		}else{
			var stage2018_M2_title = stage2018_M2 + '完成';
		}
		$('.saleStar .year2018 i:nth-child(2)').append(stage2018_M2);
		$('.saleStar .year2018 span:nth-child(2)').attr('title',stage2018_M2_title) ;
		//3
		var stage2018_M3 = stage2018.M3.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2018_M3 == ''){
			var stage2018_M3_title = '未完成';
		}else{
			var stage2018_M3_title = stage2018_M3 + '完成';
		}
		$('.saleStar .year2018 i:nth-child(3)').append(stage2018_M3);
		$('.saleStar .year2018 span:nth-child(3)').attr('title',stage2018_M3_title) ;
		//5
		var stage2018_M5 = stage2018.M5.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2018_M5 == ''){
			var stage2018_M5_title = '未完成';
		}else{
			var stage2018_M5_title = stage2018_M5 + '完成';
		}
		$('.saleStar .year2018 i:nth-child(4)').append(stage2018_M5);
		$('.saleStar .year2018 span:nth-child(4)').attr('title',stage2018_M5_title) ;
		//7
		var stage2018_M7 = stage2018.M7.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2018_M7 == ''){
			var stage2018_M7_title = '未完成';
		}else{
			var stage2018_M7_title = stage2018_M7 + '完成';
		}
		$('.saleStar .year2018 i:nth-child(5)').append(stage2018_M7);
		$('.saleStar .year2018 span:nth-child(5)').attr('title',stage2018_M7_title) ;
		//9
		var stage2018_M9 = stage2018.M9.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2018_M9 == ''){
			var stage2018_M9_title = '未完成';
		}else{
			var stage2018_M9_title = stage2018_M9 + '完成';
		}
		$('.saleStar .year2018 i:nth-child(6)').append(stage2018_M9);
		$('.saleStar .year2018 span:nth-child(6)').attr('title',stage2018_M9_title) ;
		//10
		var stage2018_M10 = stage2018.M10.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2018_M10 == ''){
			var stage2018_M10_title = '未完成';
		}else{
			var stage2018_M10_title = stage2018_M10 + '完成';
		}
		$('.saleStar .year2018 i:nth-child(7)').append(stage2018_M10);
		$('.saleStar .year2018 span:nth-child(7)').attr('title',stage2018_M10_title) ;
		//12
		var stage2018_M12 = stage2018.M12.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2018_M12 == ''){
			var stage2018_M12_title = '未完成';
		}else{
			var stage2018_M12_title = stage2018_M12 + '完成';
		}
		$('.saleStar .year2018 i:nth-child(8)').append(stage2018_M12);
		$('.saleStar .year2018 span:nth-child(8)').attr('title',stage2018_M12_title) ;
		
		//20
		var stage2018_M20 = stage2018.M20.slice(5).replace(/-/,'.').replace(/^[0]/,'');
		if(stage2018_M20 == ''){
			var stage2018_M20_title = '未完成';
		}else{
			var stage2018_M20_title = stage2018_M20 + '完成';
		}
		$('.saleStar .year2018 i:nth-child(9)').append(stage2018_M20);
		$('.saleStar .year2018 span:nth-child(9)').attr('title',stage2018_M20_title) ;
		
		
}
var finishTime_thisYear = function(stage2019){
	$('.saleStar .year2019 i').html('');
	$('.saleStar .year2019 span').attr('title','') ;
	
	var stage2019_M1 = stage2019.M1.slice(5).replace(/-/,'.').replace(/^[0]/,'');
	if(stage2019_M1 == ''){
		var stage2019_M1_title = '未完成';
	}else{
		var stage2019_M1_title = stage2019_M1 + '完成';
	}
	$('.saleStar .year2019 i:nth-child(1)').append(stage2019_M1);
	$('.saleStar .year2019 span:nth-child(1)').attr('title',stage2019_M1_title) ;
	//2
	var stage2019_M2 = stage2019.M2.slice(5).replace(/-/,'.').replace(/^[0]/,'');
	if(stage2019_M2 == ''){
		var stage2019_M2_title = '未完成';
	}else{
		var stage2019_M2_title = stage2019_M2 + '完成';
	}
	$('.saleStar .year2019 i:nth-child(2)').append(stage2019_M2);
	$('.saleStar .year2019 span:nth-child(2)').attr('title',stage2019_M2_title) ;
	//3
	var stage2019_M3 = stage2019.M3.slice(5).replace(/-/,'.').replace(/^[0]/,'');
	if(stage2019_M3 == ''){
		var stage2019_M3_title = '未完成';
	}else{
		var stage2019_M3_title = stage2019_M3 + '完成';
	}
	$('.saleStar .year2019 i:nth-child(3)').append(stage2019_M3);
	$('.saleStar .year2019 span:nth-child(3)').attr('title',stage2019_M3_title) ;
	//5
	var stage2019_M5 = stage2019.M5.slice(5).replace(/-/,'.').replace(/^[0]/,'');
	if(stage2019_M5 == ''){
		var stage2019_M5_title = '未完成';
	}else{
		var stage2019_M5_title = stage2019_M5 + '完成';
	}
	$('.saleStar .year2019 i:nth-child(4)').append(stage2019_M5);
	$('.saleStar .year2019 span:nth-child(4)').attr('title',stage2019_M5_title) ;
	//7
	var stage2019_M7 = stage2019.M7.slice(5).replace(/-/,'.').replace(/^[0]/,'');
	if(stage2019_M7 == ''){
		var stage2019_M7_title = '未完成';
	}else{
		var stage2019_M7_title = stage2019_M7 + '完成';
	}
	$('.saleStar .year2019 i:nth-child(5)').append(stage2019_M7);
	$('.saleStar .year2019 span:nth-child(5)').attr('title',stage2019_M7_title) ;
	//9
	var stage2019_M9 = stage2019.M9.slice(5).replace(/-/,'.').replace(/^[0]/,'');
	if(stage2019_M9 == ''){
		var stage2019_M9_title = '未完成';
	}else{
		var stage2019_M9_title = stage2019_M9 + '完成';
	}
	$('.saleStar .year2019 i:nth-child(6)').append(stage2019_M9);
	$('.saleStar .year2019 span:nth-child(6)').attr('title',stage2019_M9_title) ;
	//10
	var stage2019_M10 = stage2019.M10.slice(5).replace(/-/,'.').replace(/^[0]/,'');
	if(stage2019_M10 == ''){
		var stage2019_M10_title = '未完成';
	}else{
		var stage2019_M10_title = stage2019_M10 + '完成';
	}
	$('.saleStar .year2019 i:nth-child(7)').append(stage2019_M10);
	$('.saleStar .year2019 span:nth-child(7)').attr('title',stage2019_M10_title) ;
	//12
	var stage2019_M12 = stage2019.M12.slice(5).replace(/-/,'.').replace(/^[0]/,'');
	if(stage2019_M12 == ''){
		var stage2019_M12_title = '未完成';
	}else{
		var stage2019_M12_title = stage2019_M12 + '完成';
	}
	$('.saleStar .year2019 i:nth-child(8)').append(stage2019_M12);
	$('.saleStar .year2019 span:nth-child(8)').attr('title',stage2019_M12_title) ;
	
	//20
	var stage2019_M20 = stage2019.M20.slice(5).replace(/-/,'.').replace(/^[0]/,'');
	if(stage2019_M20 == ''){
		var stage2019_M20_title = '未完成';
	}else{
		var stage2019_M20_title = stage2019_M20 + '完成';
	}
	$('.saleStar .year2019 i:nth-child(9)').append(stage2019_M20);
	$('.saleStar .year2019 span:nth-child(9)').attr('title',stage2019_M20_title) ;
}


var starMark = function(past2016,past2017,past2018,past2019){
	$('.saleStar span').css({
		 'background-image':'url(./image/star.png)',
		 'background-size':'20px 20px',
		 'background-position':' 0px 3px'
	 })
	 if(past2016 < 1){
	 }else if(past2016 < 2){
		 $('.saleStar .year2016 span:nth-child(1)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2016 < 3){
		 $('.saleStar .year2016 span:nth-child(1),.saleStar .year2016 span:nth-child(2)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		 
	 }else if(past2016 < 5){
		 $('.saleStar .year2016 span:nth-child(1),.saleStar .year2016 span:nth-child(2),.saleStar .year2016 span:nth-child(3)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2016 < 7){
		 $('.saleStar .year2016 span:nth-child(1),.saleStar .year2016 span:nth-child(2),.saleStar .year2016 span:nth-child(3),.saleStar .year2016 span:nth-child(4)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2016 < 9){
		 $('.saleStar .year2016 span:nth-child(1),.saleStar .year2016 span:nth-child(2),.saleStar .year2016 span:nth-child(3),.saleStar .year2016 span:nth-child(4),.saleStar .year2016 span:nth-child(5)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2016 < 10){
		 $('.saleStar .year2016 span:nth-child(1),.saleStar .year2016 span:nth-child(2),.saleStar .year2016 span:nth-child(3),.saleStar .year2016 span:nth-child(4),.saleStar .year2016 span:nth-child(5),.saleStar .year2016 span:nth-child(6)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2016 < 12){
		 $('.saleStar .year2016 span:nth-child(1),.saleStar .year2016 span:nth-child(2),.saleStar .year2016 span:nth-child(3),.saleStar .year2016 span:nth-child(4),.saleStar .year2016 span:nth-child(5),.saleStar .year2016 span:nth-child(6),.saleStar .year2016 span:nth-child(7)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2016 < 12){
		 $('.saleStar .year2016 span:nth-child(1),.saleStar .year2016 span:nth-child(2),.saleStar .year2016 span:nth-child(3),.saleStar .year2016 span:nth-child(4),.saleStar .year2016 span:nth-child(5),.saleStar .year2016 span:nth-child(6),.saleStar .year2016 span:nth-child(7),.saleStar .year2016 span:nth-child(8)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }
	 else if( past2016 >= 20){
		 $('.saleStar .year2016 span').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
	 }
	 //2017
	 if(past2017 < 1){
	 }else if(past2017 < 2){
		 $('.saleStar .year2017 span:nth-child(1)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2017 < 3){
		 $('.saleStar .year2017 span:nth-child(1),.saleStar .year2017 span:nth-child(2)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		 
	 }else if(past2017 < 5){
		 $('.saleStar .year2017 span:nth-child(1),.saleStar .year2017 span:nth-child(2),.saleStar .year2017 span:nth-child(3)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2017 < 7){
		 $('.saleStar .year2017 span:nth-child(1),.saleStar .year2017 span:nth-child(2),.saleStar .year2017 span:nth-child(3),.saleStar .year2017 span:nth-child(4)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2017 < 9){
		 $('.saleStar .year2017 span:nth-child(1),.saleStar .year2017 span:nth-child(2),.saleStar .year2017 span:nth-child(3),.saleStar .year2017 span:nth-child(4),.saleStar .year2017 span:nth-child(5)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2017 < 10){
		 $('.saleStar .year2017 span:nth-child(1),.saleStar .year2017 span:nth-child(2),.saleStar .year2017 span:nth-child(3),.saleStar .year2017 span:nth-child(4),.saleStar .year2017 span:nth-child(5),.saleStar .year2017 span:nth-child(6)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2017 < 12){
		 $('.saleStar .year2017 span:nth-child(1),.saleStar .year2017 span:nth-child(2),.saleStar .year2017 span:nth-child(3),.saleStar .year2017 span:nth-child(4),.saleStar .year2017 span:nth-child(5),.saleStar .year2017 span:nth-child(6),.saleStar .year2017 span:nth-child(7)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2017 < 20){
		 $('.saleStar .year2017 span:nth-child(1),.saleStar .year2017 span:nth-child(2),.saleStar .year2017 span:nth-child(3),.saleStar .year2017 span:nth-child(4),.saleStar .year2017 span:nth-child(5),.saleStar .year2017 span:nth-child(6),.saleStar .year2017 span:nth-child(7),.saleStar .year2017 span:nth-child(8)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if( past2017 >= 20){
		 $('.saleStar .year2017 span').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
	 }
	 
	 //2018
	 if(past2018 < 1){
	 }else if(past2018 < 2){
		 $('.saleStar .year2018 span:nth-child(1)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2018 < 3){
		 $('.saleStar .year2018 span:nth-child(1),.saleStar .year2018 span:nth-child(2)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		 
	 }else if(past2018 < 5){
		 $('.saleStar .year2018 span:nth-child(1),.saleStar .year2018 span:nth-child(2),.saleStar .year2018 span:nth-child(3)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2018 < 7){
		 $('.saleStar .year2018 span:nth-child(1),.saleStar .year2018 span:nth-child(2),.saleStar .year2018 span:nth-child(3),.saleStar .year2018 span:nth-child(4)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2018 < 9){
		 $('.saleStar .year2018 span:nth-child(1),.saleStar .year2018 span:nth-child(2),.saleStar .year2018 span:nth-child(3),.saleStar .year2018 span:nth-child(4),.saleStar .year2018 span:nth-child(5)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2018 < 10){
		 $('.saleStar .year2018 span:nth-child(1),.saleStar .year2018 span:nth-child(2),.saleStar .year2018 span:nth-child(3),.saleStar .year2018 span:nth-child(4),.saleStar .year2018 span:nth-child(5),.saleStar .year2018 span:nth-child(6)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2018 < 12){
		 $('.saleStar .year2018 span:nth-child(1),.saleStar .year2018 span:nth-child(2),.saleStar .year2018 span:nth-child(3),.saleStar .year2018 span:nth-child(4),.saleStar .year2018 span:nth-child(5),.saleStar .year2018 span:nth-child(6),.saleStar .year2018 span:nth-child(7)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2018 < 20){
		 $('.saleStar .year2018 span:nth-child(1),.saleStar .year2018 span:nth-child(2),.saleStar .year2018 span:nth-child(3),.saleStar .year2018 span:nth-child(4),.saleStar .year2018 span:nth-child(5),.saleStar .year2018 span:nth-child(6),.saleStar .year2018 span:nth-child(7),.saleStar .year2018 span:nth-child(8)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if( past2018 >= 20){
		 $('.saleStar .year2018 span').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
	 }
	 
	 //2019
	 if(past2019 < 1){
	 }else if(past2019 < 2){
		 $('.saleStar .year2019 span:nth-child(1)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2019 < 3){
		 $('.saleStar .year2019 span:nth-child(1),.saleStar .year2019 span:nth-child(2)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		 
	 }else if(past2019 < 5){
		 $('.saleStar .year2019 span:nth-child(1),.saleStar .year2019 span:nth-child(2),.saleStar .year2019 span:nth-child(3)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2019 < 7){
		 $('.saleStar .year2019 span:nth-child(1),.saleStar .year2019 span:nth-child(2),.saleStar .year2019 span:nth-child(3),.saleStar .year2019 span:nth-child(4)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2019 < 9){
		 $('.saleStar .year2019 span:nth-child(1),.saleStar .year2019 span:nth-child(2),.saleStar .year2019 span:nth-child(3),.saleStar .year2019 span:nth-child(4),.saleStar .year2019 span:nth-child(5)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2019 < 10){
		 $('.saleStar .year2019 span:nth-child(1),.saleStar .year2019 span:nth-child(2),.saleStar .year2019 span:nth-child(3),.saleStar .year2019 span:nth-child(4),.saleStar .year2019 span:nth-child(5),.saleStar .year2019 span:nth-child(6)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2019 < 12){
		 $('.saleStar .year2019 span:nth-child(1),.saleStar .year2019 span:nth-child(2),.saleStar .year2019 span:nth-child(3),.saleStar .year2019 span:nth-child(4),.saleStar .year2019 span:nth-child(5),.saleStar .year2019 span:nth-child(6),.saleStar .year2019 span:nth-child(7)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if(past2019 < 20){
		 $('.saleStar .year2019 span:nth-child(1),.saleStar .year2019 span:nth-child(2),.saleStar .year2019 span:nth-child(3),.saleStar .year2019 span:nth-child(4),.saleStar .year2019 span:nth-child(5),.saleStar .year2019 span:nth-child(6),.saleStar .year2019 span:nth-child(7),.saleStar .year2019 span:nth-child(8)').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
		
	 }else if( past2019 >= 20){
		 $('.saleStar .year2019 span').css({
			 'background-image':'url(./image/star_yellow.png)',
			 'background-size':'30px 30px',
			 'background-position':' -4px 0px'
		 })
	 }
	 
}

//历年销售的柱状图
function getContract(dom2,contractArr){
    dom2.innerHTML = '';
    var myChart = echarts.init(dom2);
    var app = {};
    option = null;
    option = {
    		
    	    tooltip : {
    	        trigger: 'axis',
    	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    	        }
    	    },
    	    legend: {
    	        data:['今日合同数']
    	    },
    	    toolbox: {
    	        show : true,
    	        feature : {
    	            mark : {show: true}
    	        }
    	    },
    	    calculable : true,
    	    xAxis : [
    	        {
    	            type : 'value',
    	            	min:0,
        	            max:600,
                   name: '合同(单)'  ,
                   splitLine:{
                	　　　　show:false
                	　　}
    	        }
    	    ],
    	    yAxis : [
    	        {
    	            type : 'category',
    	            splitLine:{
                	　　　　show:true
                	　　},
    	            data : ['2016','2017','2018','2019']
    	        }
    	    ],
    	    series : [
    	        {
    	            name:'今日合同数',
    	            type:'bar',
    	            stack: '业绩',
    	            itemStyle : { normal: 
    	            	{
    	            		label : {show: true, 
    	            				position: 'right',
    	            				textStyle: {	    
    	                                color: 'black',
    	                                fontSize: 14
    	                            }
    	            					}
    	            	}
    	        },
    	            data:contractArr
    	        }
    	        
    	    ]
    	};
    	                    
    myChart.setOption(option);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
        window.onresize = myChart.resize;
    }
}









