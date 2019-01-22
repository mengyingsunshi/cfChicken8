<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<!-- 为移动设备添加 viewport -->
	<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
	<title>页面访问-数据统计</title>
	<link rel="shortcut icon" href="image/eoulu.ico" />
	<link rel="bookmark" href="image/eoulu.ico" />
	<link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
	<!-- <link rel="stylesheet" href="../css/global/eouluCustom.css" type="text/css"> -->
	<link rel="stylesheet" href="css/PageVisit.css" type="text/css">
	<style>
	    .u-admin a {
	        vertical-align: top !important;
	    }

	    .u-admin .u-i-2, .u-admin .u-i-3, .u-admin .u-i-4 {
	        vertical-align: top !important;
	    }

	    .lava_with_border li a {
	        vertical-align: top !important;
	    }

	    h5, h6 {
	        margin-top: 0px;
	        margin-bottom: 0px;
	    }

	    hr {
	        margin-top: 0px;
	        margin-bottom: 0px;
	        border: 0;
	        border-top: 1px solid #999;
	    }
	</style>
</head>
<body>
	<%@include file="top.jsp"%>
	<div class="container-fluid">
		<!-- 面包屑 -->
		<div class="breadnav" style="margin-top:0px;margin-bottom: 8px;">
			            <span class="breadnav_logo"><img src="./image/breadnav_logo.png" alt=""></span>
			            <span class="BusinessPart">人事部</span>
			            <span class="QuotationSystemPart" >/ 页面统计</span>
		</div>
		<div class="ToTop">
			<span class="ToTop_btn"></span>
		</div>
		<div class="g_container">
			<div class="g_container_info">
				<div class="g_container_info_l">
					<ul>
					    <li class="current_tab" data-classify="bar_statistics_tab" style="display:none"><span>柱状图统计</span></li>
					    <li data-classify="pie_statistics_tab" style="display:none"><span>饼状图统计</span></li>
					    <!-- <li data-classify="exam_detail_tab1"><span>考核详细信息1</span></li> -->
					    <!-- 快速访问 -->
					    <li><a href="#logistics">物流部</a></li>
					    <li><a href="#business ">商务部</a></li>
					    <li><a href="#server">服务部</a></li>
					    <li><a href="#software">软件部</a></li>
					    <li><a href="#staff">人事部</a></li>
					    <li><a href="#sale">销售部</a></li>
					    <li><a href="#lab">实验室</a></li>
					    
					    
					    
					    
					</ul>
				</div>
			</div>
			<div class="g_container_body">
				<div class="g_container_body_in">
					<div class="bar_statistics_wrapper">
						<div class="bar_statistics_wrapper_all">
							<div class="bar_statistics_wrapper_all_title">全部页面</div>
							<div class="bar_statistics_wrapper_all_cont" id="bar_statis_all"></div>
						</div>
						<div class="bar_statistics_wrapper_0">
							<div class="bar_statistics_wrapper_0_title"><a name="logistics">物流部</a></div>
							<div class="bar_statistics_wrapper_0_cont" id="bar_statis_0"></div>
						</div>
						<div class="bar_statistics_wrapper_1">
							<div class="bar_statistics_wrapper_1_title"><a name="business">商务部</a></div>
							<div class="bar_statistics_wrapper_1_cont" id="bar_statis_1"></div>
						</div>
						<div class="bar_statistics_wrapper_2">
							<div class="bar_statistics_wrapper_2_title"><a name="server">服务部</a></div>
							<div class="bar_statistics_wrapper_2_cont" id="bar_statis_2"></div>
						</div>
						<div class="bar_statistics_wrapper_3">
							<div class="bar_statistics_wrapper_3_title"><a name="software">软件部</a></div>
							<div class="bar_statistics_wrapper_3_cont" id="bar_statis_3"></div>
						</div>
						<div class="bar_statistics_wrapper_4">
							<div class="bar_statistics_wrapper_4_title"><a name="staff">人事部</a></div>
							<div class="bar_statistics_wrapper_4_cont" id="bar_statis_4"></div>
						</div>
						<div class="bar_statistics_wrapper_5">
							<div class="bar_statistics_wrapper_5_title"><a name="sale">销售部</a></div>
							<div class="bar_statistics_wrapper_5_cont" id="bar_statis_5"></div>
						</div>
						<div class="bar_statistics_wrapper_6">
							<div class="bar_statistics_wrapper_6_title"><a name="lab">实验室</a></div>
							<div class="bar_statistics_wrapper_6_cont" id="bar_statis_6"></div>
						</div>
					</div><!-- bar_statistics_wrapper end -->
					<div class="pie_statistics_wrapper"></div><!-- pie_statistics_wrapper end -->
				</div>
			</div>
		</div>
	</div>
<!-- <script src="js/libs/jquery-3.3.1.min.js"></script> -->
<!-- <script src="js/libs/echarts-all.js"></script> -->
<script src="js/libs/echarts/echarts-4.1.0.rc2.min.js"></script>
<!-- <script src="https://cdn.bootcss.com/lodash.js/4.17.10/lodash.min.js"></script> -->
<script src="js/msgbox_unload.js"></script>
<script src="js/PageVisit.js"></script>
<script>
/* 页面头部和导航条 */
/* $('.eou-container-l a img').attr('src','image/goose_logo.png');
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
$('.eou-container-l a').after('').after('<h2 style="font-size:18px;font-weight:bold">futureE</h2>') */


$('.ToTop').click(function(){
	$('html , body').animate({scrollTop: 0},'300');
})
</script>
</body>
</html>