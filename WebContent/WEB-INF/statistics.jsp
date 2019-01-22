<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>信息统计</title>
    <link rel="stylesheet" type="text/css" href="css/views.css">
    <link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css">
</head>
<style type="text/css">
		.PriceDataTable{
			margin: 25px auto 0;
		    width: 90%;
		    height: auto;
		    background: #f8f8f8;
		    color: #000;
		    box-sizing: border-box;
		    border-collapse: collapse;
		}
		.PriceDataTable th,.PriceDataTable td{
			text-align: center;
		    height: 48px;
		    min-width: 50px;
		    max-width: 150px;
		    word-break: break-all;
		    font-size: 15px;
		    color: #000;
		    font-weight: normal;
		    border: 1px solid #d9d3bf; 
		}
		.PriceDataTable tbody tr:nth-child(odd),.PriceDataTable tfoot tr{
			background:#eae6dd;
		}
		.searchBox{
		 /* margin-top:20px;
		 padding-left:90px */
		display: inline-block;
		height:50px;
		margin-top:10px;
		}
		.searchBoxContentD{
			float:left;
			width:55px;
		}
		.searchBoxContentD .selD{
			float:left;
			width:60px;
			height:30px;
			border:1px solid #333;
		}
		.searchBoxContentD .selD .opd{
			text-align:center;
		}
		.searchBoxContentM{
			float:left;
		}
		.searchBoxContentM .selM{
		border:1px solid #333;
			height:30px;
		}
		.spanD,.spanM{
			float:left;
			width:55px;
			height:28px;
			line-height:28px;
			background-color:#000;
			border:1px solid #333;
			color:#fff;
			margin-right:20px;
			font-size:14px;
			text-align:center;
		}
		.sousuo{
			float:left;
			width:55px;
			height:30px;
			background-color:#000;
			border:1px solid #333;
			color:#fff;
			margin-right:10px;
			margin-left:10px;
			font-size:14px;
			text-align:center;
			cursor:pointer;
			line-height:20px;
			border-radius:5px;
		}

		.statistics-export {
			position: relative;
			top: -6px;
			display: inline-block;
			height: 50px;
			margin: auto 5px auto 10px;
			padding-top: 0;
		}

		.statistics-export span {
			float:left;
			display:block;	
			box-sizing: border-box;
			height: 30px;
			line-height: 28px;
			margin-left: 5px;
			margin-top: 6px;
			padding: 2px 5px;
			font-size: 12px;
			border: 1px solid rgba(0,0,0,0.8);
			color: #fff;
			background-color:#000;
			border-radius:5px;
		}

		.statistics-export span:hover {
			cursor: pointer;
		}
		.pC-right {
		    height: 600px;
		    position: fixed;
		    right: 7%;
		}
		.SalesData_box {
		    float: left;
		    width: 100%;
		    height: 100%;
		    display: none;
		    margin-left: 3%;
		    min-width:1150px;
		}
			
				
				.orderNumber #orderNumber_bar, #picture1, #picture2 {
				    width: 800px;
				    height: 500px;
				    margin: 60px 0;
				    float: left;
				}
				.con1,.con2{
					width:100%;
					height:550px;
				}
				#SalesData_bar{
					 width: 700px;
				     height: 500px;
				     margin: 30px 0 0;
				     float: left;
				}
				#ContractData_bar{
					 width: 700px;
				     height: 500px;
				     margin: 0px 0 0;
				     float: left;
				}
				.timeSelect{
					display:none;
				    width: 235px;
				    height: 20px;
				    padding: 6px 12px;
				    font-size: 14px;
				    line-height: 1.42857143;
				    color: #555;
				    background-color: #fff;
				    background-image: none;
				    border: 1px solid #ccc;
				    border-radius: 4px;
				    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
				    box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
				    -webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
				    -o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
				    transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
				    margin-top:10px;
				    margin-left:50px;
				    font-family: "Arial","microsoft yahei";
    				outline: none;
				}
				.timerefresh{
				    width: 55px;
				    height: 30px;
				    padding: 6px 12px;
				    background-color: #000;
				    border: 1px solid #333;
				    color: #fff;
				    margin-right: 10px;
				    margin-left: 10px;
				    font-size: 14px;
				    text-align: center;
				    cursor: pointer;
				    line-height: 20px;
				    border-radius: 5px;
					display:none;
				}
				.SalesData_title{
						position:relative;
					    height: 30px;
					    line-height: 30px;
					    font-size: 18px;
					    color: #00aeef;
					    text-indent: 40px;
					    margin:20px 20%;
				}
				.ContractData_title{
						position:relative;
					    height: 30px;
					    line-height: 30px;
					    font-size: 18px;
					    color: #00aeef;
					    text-indent: 40px;
					    margin:20px 20%;
				}
				.saleStar{
					float:left;
					width:auto;
					height:auto;
					margin-top:110px;
				}
				.saleStar div{
					width:100%;
					height:95px;
				}
				.saleStar div span{
					float:left;
					display:block;
					width:40px;
					height:64px;
					background:url(./image/star.png)no-repeat;
					background-position:0px 3px;
					background-size:20px 20px;
					line-height:64px;
					font-size:12px;
				}
				.saleStar div i{
					font-size:12px;
					font-style:normal;
					float:left;
					display:block;
					width:40px;
					height:15px;
					color:red;
				}
				.pContent {
				    width: 92%;
				    margin-left: 4%;
				    min-height: 76%;
				    min-width: 1100px;
				    background: #fff;
				}
	</style>
<body>
<!-- 	头部开始 -->
	
	<%@include file="top.jsp" %>
<!-- 	头部结束 -->
<!-- 面包屑 -->
		<div class="breadnav" style="margin-top:0px;margin-bottom: 8px;">
			            <span class="breadnav_logo"><img src="./image/breadnav_logo.png" alt=""></span>
			            <span class="BusinessPart">商务部</span>
			            <span class="QuotationSystemPart" >/ 合同统计 / 详细记录</span>
		</div>
<div class="public-top">
		
        <div class="nav" style="background: #fff;height:50px;">
            <ul style="float:left">
                <li class="currentView Contant_li">
                    <a >合同信息</a>
                </li>
                <li class="sale_li">
                    <a >销售信息</a>
                </li>
                <li class="successNum_li">
                    <a >成单数量</a>
                </li>
                <li class="successRate_li">
                    <a >个人成单率</a>
                </li>
               <li class="monthInfo_li">
                    <a>月信息</a>
               </li>
               <li class="SalesData_li">
                    <a>历年销售</a>
               </li>
            </ul>
            <!-- 时间选择框 -->
		<div class="searchBox">
	
			<div style="display:none">
				<span class="currentYear" style="dispaly:none">${currentYear}</span>	
				<span class="currentMonth" style="dispaly:none">${currentMonth}</span>
				<span class="Year" style="dispaly:none">${Year}</span>	
				<span class="Month" style="dispaly:none">${Month}</span>
			</div>	
			<div class="searchBoxContentD">
				<!-- <select>
		  		<option value ="">2016</option>
		  		<option value ="">2017</option>
		  		<option value="">2018</option>
				</select> -->
			</div>
			<span class="spanD">年份</span>
			<div class="searchBoxContentM">
				<!-- <select>
		 		 <option value ="">2016</option>
		  		<option value ="">2017</option>
		 		 <option value="">2018</option>
				</select> -->
			</div>
			<span class="spanM">月份</span>
			<form action="Statistics" method="get">
	  			<input type="text" name="Year" class="inpY" value="" style="display:none"/>
	  			<input type="text" name="Month" class="inpM" value="" style="display:none"/>
	  			<input type="button" value="搜索"  class="sousuo" />
			</form>
			<div class="statistics-export" style="display: none;">
			<c:forEach var="authority" items="${authorities}">
            	<c:if test="${authority=='ExportStatistics'}">
        			<span class="export-current">导出</span>
        			<span class="export-all">导出全部</span>
                </c:if>
            </c:forEach>
			</div>
			
		</div> 
          <!-- 日历 -->
			<input type="date" class="timeSelect" >
			<span class="timerefresh">刷新</span>
        </div>

   <!--  <div class="switch">
        <a class="sales">销售信息</a>
        <a class="sales"><b class="fa fa-hand-o-right">&nbsp;<span>销售信息</span></b></a>
    </div> -->
</div>
<div class="pContent">
    <!--1-->
    <div class="pContentleft1">
        <div class="pC-left">
            <ul>
              
            </ul>
        </div>
        <div id="picture"></div>
    </div>
    <!--2-->
    <div class="pContentleft2">
        <div id="picture1"></div>
        <div id="picture2" style="display: none"></div>
    </div>
    <!-- 3 -->
    <div class="orderNumber" >
        <div id="orderNumber_bar"></div>
    </div>
    
    <!-- 4 -->
    <span class="targetValue" style="display:none"><%=request.getAttribute("targetValue") %></span>
    <span class="completeValue" style="display:none"><%=request.getAttribute("completeValue") %></span>
    <div class="pC-right">
        <p style="margin-top:70% ;">总目标：<span class="targetValue_num"><%=request.getAttribute("targetValue") %></span>M</p>
        <p class="UNcompleteValue">
        <span class="UNcomplete_tit">总剩余：</span><span class="UNcomplete_num"><%= Float.parseFloat(request.getAttribute("targetValue").toString())-Float.parseFloat(request.getAttribute("completeValue").toString()) %></span>M</p>
    </div>
    <!--月信息表格  -->
    	<div class="PriceDataBox">
		<table class="PriceDataTable" >
			<thead>
				<tr>
					<th colspan="1" rowspan="2">
						<div id="tabTitle">
							<span class="tabTitle_one"></span>
							<!--<span class="tabTitle_two">区域</span>-->
						</div>
					</th>
					<th colspan="3" >北方区域</th>
					<th colspan="3" >南方区域</th>
					<th colspan="3" >西南区域</th>
				</tr>
				<tr>
					<td>月目标(M)</td>
					<td>月完成(M)</td>
					<td>月剩余(M)</td>
					<td>月目标(M)</td>
					<td>月完成(M)</td>
					<td>月剩余(M)</td>
					<td>月目标(M)</td>
					<td>月完成(M)</td>
					<td>月剩余(M)</td>
				</tr>
			</thead>
			<tbody></tbody>
			<tfoot></tfoot>
		</table>
	</div>
	
	<div class="SalesData_box">
		<div class="con1">
			<!-- <span  class="SalesData_title">Eoulu销售业绩统计</span> -->
			<div id="SalesData_bar"></div>
			<div class="saleStar">
				<div class='year2019'>
					<p>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
					</p>
					<p>
						<span>1M</span>
						<span>2M</span>
						<span>3M</span>
						<span>5M</span>
						<span>7M</span>
						<span>9M</span>
						<span>10M</span>
						<span>12M</span>
						<span>20M</span>
					</p>
				</div>
				<div class='year2018'>
					<p>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
					</p>
					<p>
						<span>1M</span>
						<span>2M</span>
						<span>3M</span>
						<span>5M</span>
						<span>7M</span>
						<span>9M</span>
						<span>10M</span>
						<span>12M</span>
						<span>20M</span>
					</p>
				</div>
				<div class='year2017'>
					<p>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
					</p>
					<p>
						<span>1M</span>
						<span>2M</span>
						<span>3M</span>
						<span>5M</span>
						<span>7M</span>
						<span>9M</span>
						<span>10M</span>
						<span>12M</span>
						<span>20M</span>
					</p>
				</div>
				<div class='year2016'>
					<p>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
						<i></i>
					</p>
					<p>
						<span>1M</span>
						<span>2M</span>
						<span>3M</span>
						<span>5M</span>
						<span>7M</span>
						<span>9M</span>
						<span>10M</span>
						<span>12M</span>
						<span>20M</span>
					</p>
				</div>
			</div>
		</div>
		<!-- 合同数 -->
		<div class="con2">
			<!-- <span  class="ContractData_title">Eoulu合同数统计</span>  -->
			<div id="ContractData_bar"></div>
		<!-- <div class="saleStar">
			<div class='year2019'>
				<p>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
				</p>
				<p>
					<span>1M</span>
					<span>2M</span>
					<span>3M</span>
					<span>5M</span>
					<span>7M</span>
					<span>9M</span>
					<span>10M</span>
					<span>12M</span>
					<span>20M</span>
				</p>
			</div>
			<div class='year2018'>
				<p>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
				</p>
				<p>
					<span>1M</span>
					<span>2M</span>
					<span>3M</span>
					<span>5M</span>
					<span>7M</span>
					<span>9M</span>
					<span>10M</span>
					<span>12M</span>
					<span>20M</span>
				</p>
			</div>
			<div class='year2017'>
				<p>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
				</p>
				<p>
					<span>1M</span>
					<span>2M</span>
					<span>3M</span>
					<span>5M</span>
					<span>7M</span>
					<span>9M</span>
					<span>10M</span>
					<span>12M</span>
					<span>20M</span>
				</p>
			</div>
			<div class='year2016'>
				<p>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
					<i></i>
				</p>
				<p>
					<span>1M</span>
					<span>2M</span>
					<span>3M</span>
					<span>5M</span>
					<span>7M</span>
					<span>9M</span>
					<span>10M</span>
					<span>12M</span>
					<span>20M</span>
				</p>
			</div>
		</div> -->
		</div>
	</div>
</div>

</body>
<script type="text/javascript">

$(".sousuo").click(function(){
	searchClick();
})
function searchClick(){
	var whichContent = $('.public-top .nav .currentView a').html();
	localStorage.whichcontent = '';
	localStorage.whichcontent = whichContent;
	console.log('whichContent='+whichContent)
	var Year = $(".searchBoxContentD option:selected").html();
	var Month = $(".searchBoxContentM option:selected").html();	
	Month = Month.substring(0,Month.length-1);
	
	if(Month == "所"){
		Month = 'All' ;
	}else if(Month < 10 ){
		Month = '0'+ Month;
	}else{
		Month = Month;
	}
	 window.location.href = 'Statistics?Year='+ Year +'&Month='+ Month; 
}
$(document).ready(function(){
	
	var whichcontent = localStorage.whichcontent;
	showWhichContent(whichcontent);
	});
var areaData = <%=request.getAttribute("area")%>;
var salesData = <%=request.getAttribute("sales")%>;
console.log('salesData='+salesData)
var volumn = <%=request.getAttribute("volumn")%>;
console.log(volumn)
var chengdanlvData = <%=request.getAttribute("salesStatics")%>;
var AreaStatisticsPerMonth = [];
console.log("areaData");
console.log(areaData);

<%List<Map<String, List<Map<String, Object>>>> AreaStatisticsPerMonth = (List<Map<String, List<Map<String, Object>>>>)request.getAttribute("AreaStatisticsPerMonth"); 
	int i=0;
for(Map<String, List<Map<String, Object>>> map:AreaStatisticsPerMonth){
	i++;
		%>
		var map = [];
		<%
		System.out.println("hehele:"+map);

			String key = "StatisticsByAreaPer";
			if(i<10){
				key = key+"0"+i;
			}else{
				key = key+i;
			}
			System.out.println(key);
			%>
			<%
			List<Map<String, Object>> hehe = (List<Map<String, Object>>)map.get(key);
			System.out.println(hehe);
		for(Map<String, Object> map2:hehe){
			%>
			var map2 = [];
			map2.push('<%=map2.get("AreaName")%>');
			map2.push('<%=map2.get("CompletValue")%>');
			map2.push('<%=map2.get("ID")%>');
			map2.push('<%=map2.get("RNumbers")%>');
			map2.push('<%=map2.get("TargetValue")%>');
			map.push(map2);
			<%
			System.out.println(map2);
		}
			
		
		%>
		AreaStatisticsPerMonth.push(map);
		<%
		
	}
%> 
$(function(){
	
	for(var i = 0; i < AreaStatisticsPerMonth.length;i++){
		var td3 = (AreaStatisticsPerMonth[i][1][4]*1000 -AreaStatisticsPerMonth[i][1][1]*1000)  / 1000;
		var td6 = (AreaStatisticsPerMonth[i][2][4]*1000-AreaStatisticsPerMonth[i][2][1]*1000)  / 1000;
		var td9 = (AreaStatisticsPerMonth[i][3][4]*1000 -AreaStatisticsPerMonth[i][3][1]*1000)  / 1000;
		/* if(td3 < 0){
			td3 == 0
		}		
		if(td6 < 0){
			td6 == 0
		}
		if(td9 < 0){
			td9 == 0
		} */
		var str ='<tr>'+
				'<td>'+(i+1)+'</td>'+
				'<td>'+AreaStatisticsPerMonth[i][1][4] *1000 / 1000+'</td>'+
				'<td>'+AreaStatisticsPerMonth[i][1][1] *1000 / 1000+'</td>'+
				'<td>'+td3+'</td>'+
				'<td>'+AreaStatisticsPerMonth[i][2][4] *1000 / 1000+'</td>'+
				'<td>'+AreaStatisticsPerMonth[i][2][1] *1000 / 1000+'</td>'+
				'<td>'+td6+'</td>'+
				'<td>'+AreaStatisticsPerMonth[i][3][4] *1000 / 1000+'</td>'+
				'<td>'+AreaStatisticsPerMonth[i][3][1] *1000 / 1000+'</td>'+
				'<td>'+td9+'</td>'+
			'</tr>';
		 $(".PriceDataTable tbody").append(str); 
		
	}
	var colNum = $(".PriceDataTable tbody tr:nth-child(1)").find("td").length;
	var tfooeStr = "<tr><td>合计</td>"
	for(var ii = 1;ii<colNum;ii++){
		var isum = 0;
		$(".PriceDataTable tbody tr").each(function(){
			isum+=Number($(this).find("td").eq(ii).text());
		});
		tfooeStr+="<td>"+isum.toFixed(2)+"</td>";
	}
	tfooeStr+="</tr>";
	$(".PriceDataTable tfoot").empty().append(tfooeStr);
	var MonthTdH = (parseFloat($(".pContentleft1").height()) - 30) / 17 + "px";
	$(".PriceDataTable th").css("height",MonthTdH);
	$(".PriceDataTable td").css("height",MonthTdH);
})

</script>
<!-- <script src="js/jquery-1.11.3.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/echarts.js" type="text/javascript" charset="utf-8"></script>
<script src="js/china.js" type="text/javascript" charset="utf-8">
</script><script src="js/msgbox.js"></script>
<script src="js/msgbox_unload.js"></script>
<!--<script src="js/zhu.js" type="text/javascript" charset="utf-8"></script>-->
<script src="js/statistics.js" type="text/javascript" charset="utf-8"></script>
<script>
	/* 页面头部和导航条 */
	$('.eou-container-l a img').attr('src','image/goose_logo.png');
	$('.eou-container-l a img').css({
		'display':'block',
		'width' : '50px',
	    'margin': '2px 20px'
	});
	$('.eou-container-l a').css({
		'width' : '50px',
		'float' : 'left',
		'margin-right':'30px'
	});
	$('.eou-container-l a').after('').after('<h2 style="font-size:18px">futureE</h2>')
	
	//总剩余显示
	$(function(){
		var UNcompleteValue = $('.UNcompleteValue .UNcomplete_num').text();
		var index = UNcompleteValue.indexOf('.');
		if(index > 0){
			UNcompleteValue = UNcompleteValue.substring(0,index+3);
		}
		if( UNcompleteValue < 0){
			$('.UNcompleteValue .UNcomplete_tit').text('').text('超额完成：');
			UNcompleteValue = Math.abs(UNcompleteValue);
		}
		$('.UNcompleteValue .UNcomplete_num').text('').text(UNcompleteValue);
	})
	
</script>
</html>
