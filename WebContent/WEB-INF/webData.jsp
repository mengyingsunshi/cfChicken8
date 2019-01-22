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
<title>网站数据</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="css/paging.css" />
<link rel="stylesheet" type="text/css" href="css/webData.css">
</head>
<body>
<div id="originfactory_wrapper">
		<div id="originfactory_sticker">
			<div id="originfactory_sticker-con">
				<%@include file="top.jsp"%>
				<div class="contain">
					<div class="content">
					<%@include file="nav.jsp"%>
							<div id="page-wrap">
								<div class="breadBox">
							            <span class="">位置：商务部</span>
							            <span class="" >&gt; 需求统计</span>
							            <span class="" >&gt; 网站数据</span>
						  		</div> 
						  		<%-- <span class="fileName" style="display:none">${fileName}</span> --%>
						  		<div class="uploaderCon">
									<span class="uploader" id="box">
										点击或拖拽文件到此处上传
										
									</span>
									<input type="file" class="flie_button" id="FileUpload">
									
									<p class="p1">
										<span></span>
										<span>上传文件命名格式如：</span>
										<span>Eoulu公司网站数据统计表_190102.xls</span>
									</p>
									
								</div>
								<div class="dataBox_bg"></div>
								<div class="dataBox">
									<table>
										<thead>
											<tr class="dataBox_title">
												<td>序号</td>
												<td>日期</td>
												<td>Eoulu公司网站访问量 </td>
												<td>关键词及消费明细</td>
												<td>百度推广IP明细</td>
												<td>360后台IP明细</td>
											</tr>
										</thead>
										<tbody>
											<!-- <tr class="dataBox_con">
												<td>1</td>
												<td>2019-01-11</td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
											</tr> -->
										</tbody>
									</table>
									
									
									<div id="pageToolbar1"></div>
									
								</div>
								<div class="closeExcel">关闭</div>
								<div class="mask"></div>
								
												  		
													  		
								  		
						  		
						  		
						  		
						  		
						  		
					       </div> 
					      </div> 
					     </div> 
					    </div> 
					   </div> 
					  </div>
	 
	 
	 
	<script src="js/jquery-1.12.3.min.js"></script> 
	<script type="text/javascript" src="js/query.js"></script>
    <script type="text/javascript" src="js/paging.js"></script>
	<script src="js/msgbox_unload.js"></script>
	<script src="js/msgbox.js"></script>
    <script type="text/javascript" src="js/webData.js"></script>
    <script type='text/javascript'>
	    $('.eou-container-l a img').css({
			'display':'block',
	        'margin': '5px 20px'
		});
        
    </script>
</body>
</html>