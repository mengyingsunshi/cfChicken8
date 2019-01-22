<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>装机统计</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="css/installingStatistic.css">
</head>
<body>
<div id="originfactory_wrapper">
		<div id="originfactory_sticker">
			<div id="originfactory_sticker-con">
				<!-- 	头部开始 -->
				<%@include file="top.jsp"%>
				<!-- 	头部结束 -->
				<div class="contain">
					<div class="content">
					<%@include file="nav.jsp"%>
							<div id="page-wrap">
								<div class="breadBox">
							            <span class="">位置：服务部</span>
							            <span class="" >&gt; 标准服务</span>
							            <span class="" >&gt; 装机统计</span>
						  		</div> 
								<div class="machineBarCon">
									<div class="machineBar_title">
										<span class="current" rank='1'>EPS150</span>
										<span rank='2'>T200</span>
										<span rank='3'>SUMMIT</span>
										<span rank='4'>PA200</span>
										<span rank='5'>CM300</span>
									</div>
									<div class="machineBarBox">
										<div id="machineBar1" ></div>
										<div id="machineBar2"></div>
										<div id="machineBar3" ></div>
										<div id="machineBar4" ></div>
										<div id="machineBar5" ></div>
									</div>
									
									
									
								</div>
							</div>
					</div>
				</div>
			</div>	
		</div>	
</div>	
<script src="js/jquery-1.12.3.min.js"></script>
<script src="js/echarts.js" type="text/javascript" charset="utf-8"></script>
<script src="js/msgbox_unload.js"></script>
<script src="js/msgbox.js"></script>
<script type="text/javascript" src="js/installingStatistic.js"></script>  
</body>
</html>