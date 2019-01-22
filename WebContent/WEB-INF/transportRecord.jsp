<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="UTF-8">
<title>物流统计-历史发货记录</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<style>
	body {
	    width: 100%;
	    height: 100%;
	    background: #e4e8eb;
	}
	.nav-container {
	    width: 100%;
	    height: 50px;
	    box-sizing: border-box;
	}
	.nav-container-in {
	    margin: 20px auto 0 auto;
	    width: 90%;
	    height: 50px;
	    display: flex;
	    box-sizing: border-box !important;
	    border: 1px solid #ccc;
	    background: rgba(255,255,255,0.8);
	    font-size: 15px !important;
	}
	.nav-container-in-l {
	    flex: 5;
	    height: 50px !important;
	    line-height: 50px !important;
	    padding-left: 10px !important;
	}
	.nav-container-in-l ul {
	    display: inline-block;
	}
	.nav-container-in-l li {
	    display: inline-block;
	    list-style: none;
	    height: 50px !important;
	    min-width: 65px;
	    text-align: center;
	}
	.nav-container-in-l li a {
	    text-decoration: none;
	    color: #000 !important;
	}
	.nav-container-in-l span {
	    display: inline-block;
	    width: 20px !important;
	    height: 50px !important;
	    line-height: 50px !important;
	    text-align: center;
	}
	.timeSelect{
		float:right;
		margin-right:5%;	
		margin-top:20px;
		margin-bottom:10px;
	}
	.timeSelect select{
	    float: left;
	    display: block;
	    width: 200px;
	    height: 34px;
	    margin-right:10px;
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
	}
	span.refresh_btn {
	    float: left;
	    background: red;
	    color: #fff;
	    text-align: center;
	    width: 58px;
	    height: 32px;
	    line-height: 32px;
	    box-shadow: 0 2px 5px 0 #65b5d4;
	    cursor: pointer;
	}
	.lineBox{
		clear:both;		
		width:90%;
		margin:0 auto;
		height:500px;
		background:#fff;
		border-radius:3px;
	}
	a {
	    text-decoration: none!important;
	}
</style>
</head>
<body>
	<div id="Transport_wrapper">
	    <div id="Transport_sticker">
	        <div id="Transport_sticker-con">
			
				<!-- 	头部开始 -->
				<%@include file="top.jsp"%>
				<!-- 	头部结束 -->
				<div class="contain">
					<div class="content">
					<!-- 	=======================导航栏   开始 ================================== -->
					
					<div class="nav-container">
				        <div class="nav-container-in" style="opacity: 1;">
				            <div class="nav-container-in-l">位置：
				                <ul>
				                    <li class="nav-container-in-li-1"><a href="Inventory" class="nav-title">物流部</a></li><span class="nav-sub-span" style="display: inline;">&gt;</span>
				                    <li class="nav-container-in-li-2" style=""><a href="Transport?ActualDelivery=no&amp;column=DateOfSign&amp;condition=All" class="nav-sub-tit">物流统计</a></li><span class="nav-dbsub-span" style="display: none;">&gt;</span>
				                    <li class="nav-container-in-li-3" style="color:#18b4f0">&gt; 历史发货记录</li>
				                </ul>
				            </div>
				            <div class="nav-container-in-r"></div>
				        </div>
				    </div>
					<div class="timeSelect">
						<select>
						</select>
						<span class="refresh_btn">刷新</span>
					</div>
					
					<div class="lineBox">
					    	<div class="lineBox_body">
					    	
					    	</div>
				    </div>
				   
					
				</div>
			</div>
		</div>	
	</div>	
</div>







<script src="js/jquery-1.12.3.min.js"></script>
<script src="plugins/echarts/map_can/echarts-all-min.js"></script>
<script src="js/msgbox_unload.js"></script>
<script src="js/msgbox.js"></script>
<script src="js/transportRecord.js"></script>
</body>
</html>