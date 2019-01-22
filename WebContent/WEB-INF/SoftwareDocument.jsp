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
<title>软件部文档</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<!-- <link rel="stylesheet" type="text/css" href="font-awesome-4.5.0/css/font-awesome.min.css"> -->
<!-- <link rel="stylesheet" type="text/css" href="css/swiper-3.4.1.min.css" /> -->
<!-- <link rel="stylesheet" type="text/css" href="css/libs/bootstrap-grid-form-button-res-icon-list.min.css"> -->
<link rel="stylesheet" href="css/libs/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="css/extends/integrationLibs/awesomplete-mailSetting-a439ec29a7.min.css" type="text/css">
<link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" />

<!-- <link rel="stylesheet" type="text/css" href="css/global/global_table_style.css">
<link rel="stylesheet" type="text/css" href="css/SoftwareDocument.css">
<link rel="stylesheet" href="css/global/eoulu_ul_reset.css" type="text/css">
<link rel="stylesheet" href="css/modules/software/time_line.css" type="text/css"> -->
<link rel="stylesheet" href="css/modules/software/SoftwareDocument-ac57588804.min.css" type="text/css">
<!-- <link rel="stylesheet" href="src/css/modules/software/SoftwareDocument.css" type="text/css"> -->
<style>
	.content {
		padding-bottom: 5px !important;
	}
	/*布局*/
	html,body{
	    width:100%;
	    height:100%;
	}

	#Transport_wrapper {
	    position:fixed;
	    overflow:auto;
	    width:100%;
	    height:100%;
	    box-sizing:border-box;
	}

	#Transport_sticker {
	    width:100%;
	    min-height:100%;
	    box-sizing:border-box;
	}

	#Transport_sticker-con {
	    padding-bottom:40px;
	    box-sizing:border-box;
	}

	#Transport_footer {
	    margin-top:-40px;
	}

	.clear_float {
		height: 2px;
		width: calc(100% - 2px);
		clear:both;
	}

	/*导航栏及搜索框各页面自定义*/
	.g-nav-ul li {
		height: 80px !important;
		line-height: 80px !important;
	}

	hr {
		margin-top: 1px;
		margin-bottom: 1px;
	}

	.u-admin img {
		vertical-align: top;
	}

	div.g-nav h5, div.g-nav h6, div.u-admin h5 {
		margin-top: 0px;
		margin-bottom: 0px;
	}

	.content .choose {
		display: inline-block;
		margin-bottom: 10px !important;
	}

	.content .choose input[type="button"] {
		margin-top: 1px !important;
		width: auto !important;
		height: auto !important;
	}

	.content .choose input+input {
		margin-left: 10px !important;
	}

	.m-nav-div0 {
		z-index: 101 !important;
	}

	/*自动填充带来的问题，兼容样式*/
	div.awesomplete {
		display: block;
	}

	/*表格自定义*/
	.gl_table_style th:nth-child(1) {
		max-width: 50px;
		min-width: 50px;
		width: 50px;
	}

	.gl_table_style th:nth-child(2), .gl_table_style th:nth-child(4) {
		min-width: 150px;
		width: 20%;
	}

	.gl_table_style th:nth-child(3), .gl_table_style td:nth-child(3) {
		max-width: 980px;
	}

	/*ul自定义*/
	.VersionManagement_div_tit_r ul {
	    min-width: 500px !important;
	}

	.VersionManagement_div_tit_r ul li a{
	    text-align: center;
	    border-radius: 5px;
	}

	.VersionManagement_div_tit_r ul li.actived a, .VersionManagement_div_tit_r ul li.active a:hover, .VersionManagement_div_tit_r ul li.active a:focus {
	    background: #448bea;
	    color: #fff !important;
	}

	/*邮箱设置框自定义*/
	.mailSetting_div_cover {
		z-index: 101;
	}

	.mailSetting_div {
		z-index: 102;
	}

	.cc_list_cont li, .to_list_cont li {
		font-size: 14px;
	}

	.appendp_p_div textarea+textarea {
		margin-top: 3px;
	}

	.i-content .main .year .list ul li .more {
		font-size: 13px;
	}

	@media screen and (max-width: 1366px) {
	    .gl_table_style th:nth-child(3), .gl_table_style td:nth-child(3) {
	    	max-width: 680px;
	    }
	}

	@media screen and (max-width: 1280px) {
	    .gl_table_style th:nth-child(3), .gl_table_style td:nth-child(3) {
	    	max-width: 640px;
	    }
	}
	.upload_bgcover {
	    z-index: 20;
	}
	.upload_bgcover {
	    width: 100%;
	    height: 100%;
	    background-color: rgba(10%,20%,30%,0.7);
	    position: absolute;
	    top: -10px;
	    left: 0;
	    display: none;
	    overflow: hidden;
	    z-index: 10;
	}
	.upload_wrapper {
	    position: absolute;
	    top: 100px;
	    left: 50%;
	    margin-left: -300px;
	    width: 600px;
	    background-color: #fff;
	    font-size: 15px;
	    z-index: 21;
	    display: none;
	}
	.upload_wrapper_tit {
	    position: relative;
	    background-color: #00aeef;
	    height: 35px;
	    line-height: 35px;
	    color: #fff;
	}
	
	.webuploader-pick {
	    position: relative;
	    display: inline-block;
	    cursor: pointer;
	    background: #00b7ee;
	    padding: 10px 15px;
	    color: #fff;
	    text-align: center;
	    border-radius: 3px;
	    overflow: hidden;
	}
	#picker {
	    width: 350px;
	    display: inline-block;
	    vertical-align: top;
	}
	#ctlBtn {
	    display: inline-block;
	    padding: 6px 12px;
	    margin-bottom: 0;
	    font-size: 14px;
	    font-weight: 400;
	    line-height: 1.42857143;
	    text-align: center;
	    white-space: nowrap;
	    vertical-align: top;
	    -ms-touch-action: manipulation;
	    touch-action: manipulation;
	    cursor: pointer;
	    -webkit-user-select: none;
	    -moz-user-select: none;
	    -ms-user-select: none;
	    user-select: none;
	    background-image: none;
	    border: 1px solid transparent;
	    border-radius: 4px;
	    color: #333;
	    background-color: #fff;
	    border-color: #ccc;
	    height: 40px;
	}
	.versionFile{
		float:right!important;
		margin-left:30px;
	}
	.versionFile img{
		width:100px;
		height:100px;
	}
	.main .year .list ul li .intro {
	    margin-top: 15px;
	}
	.download_now{
		display:block;
		width:100px;
		height:30px;
		border-radius:3px;
		line-height:30px;
		color:#fff;
		background:url("/cfChicken8/image/versionFile_down.png")no-repeat left top;
		background-position:9px 5px;
		background-color:#8fc4ff;
		padding-left:29px;
		margin-top:5px;
		cursor:pointer;
	}
	.download_file{
		display:block;
		width:150px;
		height:auto;
		margin-top:5px;
	}
</style>
</head>
<body>
	<div class="loading_div_g_div" style="position: fixed;top: 0;bottom: 0;left: 0;right: 0;z-index: 100;width: 100vw;height: 100vh;background-color: #fff;filter:alpha(opacity=90);-moz-opacity:0.9;-khtml-opacity:0.9;opacity: 0.9;display: -webkit-flex;display: flex;justify-content: center;align-items: center;">
	    <img src="image/loading/Spinner-1s-200px.gif" alt="loading。。。" style="display:block;width:110px;height:110px">
	</div>
	<div id="Transport_wrapper">
	    <div id="Transport_sticker">
	        <div id="Transport_sticker-con">
				<!-- 	头部开始 -->
				<%@include file="top.jsp"%>
				<!-- 	头部结束 -->
				<div class="contain">
					<div class="content">
						<%@include file="nav.jsp"%>
						<div class="show" id="show_MV_CFCs">
							<div class="show_in">
								<!-- 分页标签主体 -->
								<div class="m_pagination">
								    <div class="tab_wrapper changeBox">
								        <ul class="nav nav-tabs" role="tablist">
								            <li role="presentation" class="Norms" data-iidiagram="Norms"><a href="#Norms_a" aria-controls="Norms_a" role="tab" data-toggle="tab" data-idiagram="Norms">软件开发规范</a></li>
								            <li role="presentation" class="CommonProblem" data-iidiagram="CommonProblem"><a href="#CommonProblem_a" aria-controls="CommonProblem_a" role="tab" data-toggle="tab" data-idiagram="CommonProblem">软件开发常见问题库</a></li>
								            <li role="presentation" ID="currentTab" class="Manage active" data-iidiagram="Manage"><a href="#Manage_a" aria-controls="Manage_a" role="tab" data-toggle="tab" data-idiagram="Manage">软件项目实施进度</a></li>
								            <li role="presentation" data-iidiagram="VersionManagement"><a href="#VersionManagement_a" aria-controls="VersionManagement_a" role="tab" data-toggle="tab" data-idiagram="VersionManagement">产品发布记录</a></li>
								        </ul>
										<div class="container-fluid relative_div">
											<input type="button" class="btn btn-info writeEmail" value="上传">
											<input type="button" class="btn btn-info All_upload" id="All_upload" value="批量上传">
											<input type="button" class="btn btn-info mailSetting_open" value="邮箱设置">
									       	<form action="DocumentUpload" method="post" id="search_box">
								            	<input type="text" placeholder="输入关键字查询" class="tsearch" name="content" value="" style="min-width:180px;right: 27%" ><span class="iiisearch" style="right: 27%"><span class="glyphicon glyphicon-search"></span></span>
								            	<span style="display:none" class="scatalog">${catalog}</span>
								            	<span style="display:none" class="scontent">${content}</span>
										    </form>
										</div>
								        <div class="tab-content">
								            <!-- 软件开发规范 -->
								            <div role="tabpanel" class="tab-pane fade" id="Norms_a">
								            	<table id="table1" class="gl_table_style">
								            		<thead>
								            			<tr>
								            				<th>序号</th>
								            				<th>文件类型</th>
								            				<th class="TitleName">文件名称&nbsp;<span class="glyphicon glyphicon-sort-by-attributes" aria-hidden="true"></span></th>
								            				<th>上次更新</th>
								            				<th>下载</th>
								            				<th>删除</th>
								            			</tr>
								            		</thead>
								            		<tbody>
								            			 <c:forEach var="orderInfo" items="${manual}" varStatus="status">
								            				<c:if test="${status.index>0}">
								            					<tr>
								            						<td class="Serial" value="${orderInfo['ID']}">${status.index+(currentPage-1)*10}</td>
								            					    <td><span class="glyphicon glyphicon-file" aria-hidden="true" value="${orderInfo['ID']}"></span></td>
								            						<td  class="ManualWest" title="${orderInfo['FileName']}">${orderInfo['FileName']}</td>
								            						<td>${orderInfo['OperatingTime']}</td>
								            						<td><span class="glyphicon glyphicon-save export" aria-hidden="true"></span></td>
								            						<td><span class="glyphicon glyphicon-trash delete_span" aria-hidden="true" value="${orderInfo['ID']}"></span></td>
								            					</tr>
								            				</c:if>
								            			</c:forEach> 
								            		</tbody>
								            	</table>
								            </div><!-- 软件开发规范 end-->

								            <!-- 软件开发常见问题库 -->
								            <div role="tabpanel" class="tab-pane fade" id="CommonProblem_a">
								                <!-- 表格开始 -->
								                <!--ajax  常见问题文档  -->
								                <table cellspacing="0" cellspadding="0" id="table2" style="min-height:150px;">
								                </table>
								            </div><!-- 软件开发常见问题库 end -->
								                

								            <!-- 软件项目实施进度 -->
								            <div role="tabpanel" class="tab-pane fade in active" id="Manage_a">
								                <!-- 表格开始 -->
								                <table id="table3" class="gl_table_style">
								            		<thead>
								            			<tr>
								            				<th>序号</th>
								            				<th>文件类型</th>
								            				<th class="TitleName">文件名称&nbsp;<span class="glyphicon glyphicon-sort-by-attributes" aria-hidden="true"></span></th>
								            				<th>上次更新</th>
								            				<th>下载</th>
								            				<th style="display:none;">删除数据</th>
								            			</tr>
								            		</thead>
								            		<tbody>
								            			 <c:forEach var="orderInfo" items="${manual}" varStatus="status">
								            				<c:if test="${status.index>0}">
								            					<tr>
								            						<td class="Serial" value="${orderInfo['ID']}">${status.index+(currentPage-1)*10}</td>
								            					    <td><span class="glyphicon glyphicon-file" aria-hidden="true" value="${orderInfo['ID']}"></span></td>
								            						<td class="ManualWest" title="${orderInfo['FileName']}">${orderInfo['FileName']}</td>
								            						<td>${orderInfo['OperatingTime']}</td>
								            						<td><span class="glyphicon glyphicon-save export" aria-hidden="true"></span></td>
								            					</tr>
								            				</c:if>
								            			</c:forEach> 
								            		</tbody>
								            	</table>
								                <!-- 表格结束 -->
								            </div><!-- 软件项目实施进度 end -->

											<!-- 产品发布记录 -->
											<div role="tabpanel" class="tab-pane fade" id="VersionManagement_a">
												<div class="VersionManagement_div">
													<div class="VersionManagement_div_tit">
														<div class="VersionManagement_div_tit_l">
															<div class="VersionManagement_div_tit_add">产品发布记录<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></div>
															<div class="VersionManagement_div_tit_setting"><select id="projectname_select" class="form-control"><option value="futureC_T1">futureC T1</option>
															<option value="futureD_T1">futureD T1</option>
															<option value="futureD_T2">futureD T2</option>
															<option value="cfChicken_T1">futureE T1</option>
															<option value="cfChicken_T2">futureE T2</option>
															<option value="EUCP_T1">EUCP T1</option>
															</select>&nbsp;<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyNpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTQwIDc5LjE2MDQ1MSwgMjAxNy8wNS8wNi0wMTowODoyMSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIChNYWNpbnRvc2gpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOkY2NzAxQTZGQTM5NTExRThCQTFERDU3NTMwNDhCREIzIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOkY2NzAxQTcwQTM5NTExRThCQTFERDU3NTMwNDhCREIzIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6RjY3MDFBNkRBMzk1MTFFOEJBMURENTc1MzA0OEJEQjMiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6RjY3MDFBNkVBMzk1MTFFOEJBMURENTc1MzA0OEJEQjMiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz4a+uraAAACiElEQVR42uyXTUhUURiG74SSposK+qEoodpFkIm0aFeYhihKugqkjQtdtIqsXERI9EPgIlIqCSHMjZRpA7OqXRtJhSBqU+SirQn2LzM93/AeuFzuNKP3NrOwAw/nzr1n5n3Pd777nTOJTCbjlbJt8ErcSm6grG/yp/VNcB0OF0FzHi7dbNuYchEw8WSRxD3pJJl4UzYCcE1GfsMbWIBP/0C4BvbCISiXbsoM1PpCU68BHTBVyK8SyrxjmG0r3YSEZ6RT65YgoXEfIK1BNrg1jmkHxNPS8Zyu/y14B11xmggR75JOztdwLC4TYeIs11ghdcAG9ep6TSYC4tZ6wsT/VojuhZg4WaB4Q0C8F/H7a6mEwwETk3A8j7g9fxYQH45Siv0mKuF5LhMST2qcp+9Ncb8y6l6Q1wQiJ6yoQIVuTavovIWPPO+IuhnlNKGZT/vCbhnfAhehCnbAYBy7YZiJG+pdmGdhq17l07Ad5iwyGC0L3Q1X+Xq7hBqSaF/g+WOSbon+kW955lR2B7juj2rAmViBW7AZMiqrL2EEkf30o/AZzmjTqdeSHIMXcRxIHsA2OOjbSzo1+1Mwrggd4J7V/nMasy/OE9GK6vqyPj9k9tX0T6BZW/p77u1WrniKSuQl8DfL+D0q31Z+u5nxoAy4HLhCdxTuwHm4HKcBOw98QaRHM67j2g4IjfAVXukEtGjijP2lI2B8BtQW1VvStcMmff6uXFgw8Vx1wP0x2BnBgM12QMc6u74Nd/XMEvOqb6zTyeomLjz98Zr+iL48GpYoq2jV6l1iboFv4OK+C86qas4SlTpbgn5tInazu0gn47R0s0uQUtbOF0ncdJrd/4LE//+G697AHwEGAHrKzjKxiXhoAAAAAElFTkSuQmCC" alt="邮箱设置" class="mailSet_open"></div>
														</div>
														<div class="VersionManagement_div_tit_r">
															<ul class="nav nav-tabs" role="tablist">
															    <li role="presentation" class="active actived" data-projectname="futureC_T1"><a href="#futureC_T1_a" aria-controls="futureC_T1_a" role="tab" data-toggle="tab" data-iprojectname="futureC_T1"><img src="" alt="futureC T1" data-isrc="image/softwareDocument/futureC_T1_windows.png"><br>futureC T1</a></li>
															    <li role="presentation" data-projectname="futureD_T1"><a href="#futureD_T1_a" aria-controls="futureD_T1_a" role="tab" data-toggle="tab" data-iprojectname="futureD_T1"><img src="" alt="futureD T1" data-isrc="image/softwareDocument/futureD_T1_windows.png"><br>futureD T1</a></li>
															    <li role="presentation" data-projectname="futureD_T2"><a href="#futureD_T2_a" aria-controls="futureD_T2_a" role="tab" data-toggle="tab" data-iprojectname="futureD_T2"><img src="" alt="futureD T2" data-isrc="image/softwareDocument/futureD_T2_windows.png"><br>futureD T2</a></li>
															    <li role="presentation" data-projectname="cfChicken_T1"><a href="#cfChicken_T1_a" aria-controls="cfChicken_T1_a" role="tab" data-toggle="tab" data-iprojectname="cfChicken_T1"><img src="" alt="futureE T1" data-isrc="image/softwareDocument/cfChicken_T1_windows.png"><br>futureE T1</a></li>
															    <li role="presentation" data-projectname="cfChicken_T2"><a href="#cfChicken_T2_a" aria-controls="cfChicken_T2_a" role="tab" data-toggle="tab" data-iprojectname="cfChicken_T2"><img src="" alt="futureE T2" data-isrc="image/softwareDocument/cfChicken_T2_windows.png"><br>futureE T2</a></li>
															    <li role="presentation" data-projectname="EUCP_T1"><a href="#EUCP_T1_a" aria-controls="EUCP_T1_a" role="tab" data-toggle="tab" data-iprojectname="EUCP_T1"><img src="" alt="EUCP T1" data-isrc="image/softwareDocument/EUCP_T1_windows.png"><br>EUCP T1</a></li>
															</ul>
														</div>
													</div>
													<div class="VersionManagement_div_body">
														<div class="tab-content">
															<div role="tabpanel" class="tab-pane fade in active" id="futureC_T1_a"></div>
															<div role="tabpanel" class="tab-pane fade" id="futureD_T1_a"></div>
															<div role="tabpanel" class="tab-pane fade" id="futureD_T2_a"></div>
															<div role="tabpanel" class="tab-pane fade" id="cfChicken_T1_a"></div>
															<div role="tabpanel" class="tab-pane fade" id="cfChicken_T2_a"></div>
															<div role="tabpanel" class="tab-pane fade" id="EUCP_T1_a"></div>
														</div>
													</div>
												</div>
											</div><!-- 产品发布记录 end -->

								        </div><!-- tab-content end -->

										<c:set var="queryUrl" value="SoftwareDocument?queryType=${queryType}&catalog=${catalog}&Type=${type}&content=${content}&currentPage=">
										</c:set>
							 			<div id="page">
											<div class="pageInfo">
												当前是第&nbsp;<span id="currentPage">${currentPage}</span>&nbsp;页,&nbsp;总计&nbsp;<span id="allPage">${pageCounts}</span>页
											</div>
											<div class="changePage">
												<input type="button" class="btn btn-primary" value="首页" id="fistPage" name="fistPage" onclick="FistPage('${queryUrl}')">
												<input type="button" class="btn btn-primary" value="上一页" id="upPage" onclick="UpPage('${queryUrl}${currentPage-1 }')">
												<input type="button" class="btn btn-primary" value="下一页" id="nextPage" onclick="NextPage('${queryUrl}${currentPage+1 }')"> 跳到第 <span id="jumpNumber" name="jumpNumber" class="jumpNumber" style="display:inline-block;min-width: 30px;height:24px; color: #000;border:1px solid  rgb(84, 84, 84);background-color:#fff;text-align:center;line-heigth:24px" contenteditable="true" onkeyup="change()"></span> 页 <input type="button" class="btn btn-primary" value="GO" id="Gotojump" name="Gotojump" onclick="PageJump('${queryUrl}')" >
												<input type="button" class="btn btn-primary" value="尾页" id="lastPage" name="lastPage" onclick="LastPage('${queryUrl}')" >
											</div> 
										</div> 
										<div class="clear_float"></div>

								    </div><!-- tab_wrapper end -->
								</div><!-- 分页标签主体end -->
							</div><!-- show_in end -->

						</div>
					</div>     
				</div>
				<!-- </div> -->
				<!-- ***************编辑上传文件区域****************** -->
				<div class="MailBar_cover_color"></div>
			 	<div class="MailBar" style="display:none;">
					<div class="operate_title">上传文件</div>
					<div class="MailBar_close">关闭</div>
					<!-- 根据年份上传 -->
					<c:if test="${catalog=='VisitReport'}">
				 		<span class="uptitle">选择年份:</span>
				 		<select class="upyear">
				 			<option value="2010">2010</option>	
				 			<option value="2011">2011</option>	
				 			<option value="2012">2012</option>	
				 			<option value="2013">2013</option>
				 			<option value="2014">2014</option>
				 			<option value="2015">2015</option>	
				 			<option value="2016">2016</option>	
				 			<option value="2017">2017</option>	
				 			<option value="2018">2018</option>
				 		</select>
			 		</c:if>
			 			<!--不影响使用的代码  -->
							<div class="SoftType" style="display:none">
									<span class="uptitle">文档类型:</span>
									<select class="uptype">
							 			<option value="技术攻关">技术攻关</option>	
							 			<option value="技术分享">技术分享</option>	
							 			<option value="技术更新">技术更新</option>	
							 			<option value="BUG修复">BUG修复</option>
							 		</select>
							</div>
							<a href="" class="box">
								<input type="file" name="file" id="Mail_fileToUpload"   value="添加附件"  class="up"/>
								<span class="intitle">点击上传文档、压缩包</span>
								<span class="mask"></span>
							</a>
							<div class="NameFormat">文件名格式：XX问题分析报告-YYYYMMDD-作者.doc</div>
							<div class="PwdCont">邮箱密码：<input type="password" name="password"  class="PwdInput"></div>
						<!-- 修改结束位置 -->
						<input type="button" name="button" value="上传" class="bToggle" id="Mail_Send">
					</div>
				<!-- ***************编辑批量上传文件区域****************** -->
				<div class="MailBar_cover_color"></div>
			 	<div class="MailBar_All" style="display:none;">
					<div class="operate_title">上传文件</div>
					<div class="MailBar_close">关闭</div>
					<!-- 根据年份上传 -->
					<c:if test="${catalog=='VisitReport'}">
				 		<span class="uptitle">选择年份:</span>
				 		<select class="upyear">
				 			<option value="2010">2010</option>	
				 			<option value="2011">2011</option>	
				 			<option value="2012">2012</option>	
				 			<option value="2013">2013</option>
				 			<option value="2014">2014</option>
				 			<option value="2015">2015</option>	
				 			<option value="2016">2016</option>	
				 			<option value="2017">2017</option>	
				 			<option value="2018">2018</option>	
				 		</select>
				 	</c:if>
						<form  enctype="multipart/form-data" id="f">
							<!--不影响使用的代码  -->
				   		<!-- 	<input type="file" name="file" id="Mail_fileToUpload" multiple="multiple"  value="添加附件" /> -->
				   			<div class="SoftType">
									<span class="uptitle">文档类型:</span>
									<select class="uptype">
							 			<option value="技术攻关">技术攻关</option>	
							 			<option value="技术分享">技术分享</option>	
							 			<option value="技术更新">技术更新</option>	
							 			<option value="BUG修复">BUG修复</option>
							 		</select>
							</div>
				   			<!-- 改样式后的代码优化 -->
							<a href="" class="box">
								<input type="file" name="file" id="Mail_fileToUpload" multiple="multiple"  value="添加附件"  class="up"/>
								<span class="intitle">点击上传文档、压缩包</span>
								<span class="mask"></span>
							</a>
							<!-- 修改结束位置 -->
							<div class="NameFormat">文件名格式：XX问题分析报告-YYYYMMDD-作者.doc</div>
				   			<div class="PwdCont">邮箱密码：<input type="password" name="password" style="display:none"><input type="password" name="password"  class="PwdInput"></div>
							<input type="button" name="button" value="批量上传" class="bToggle" id="All_Send" > 
			     			<input type="text" name="Area" value="${area}" class="bToggle" id="All_Send2" style="display:none;" >
			     			<input type="text" name="Year" value="${year}" class="bToggle" id="All_Send3" style="display:none;">
			     			<input type="text" name="Type" value="${type}" class="bToggle" id="All_Send4" style="display:none;" >
			     			<input type="text" name="Time" value="" class="bToggle" id="All_Send5" style="display:none;">
						</form>
						
				</div>  
				<a target="_blank" id="WindowOpen"></a>
				<div class="mailSetting_div_cover"></div>
				<div class="mailSetting_div" id="mailSetting_classify" data-iiclassify="documentUpload">
					<!-- 下面是动态插入内容 -->
					<!-- 上面是动态插入内容 -->
				</div>

				<!-- 添加产品发布记录 -->
				<div class="MailBar_cover_color22"></div>
				<div class="VersionManagement">
					<div class="VersionManagement_tit">
						添加产品发布记录<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
					</div>
					<div class="VersionManagement_body">
						<div class="VersionManagement_body_in">
				    		<fieldset><legend>基本信息</legend>
				    			<div class="container-fluid">
				    				<div class="row">
				    					<div class="col-md-6 col-lg-6">
				    						<div class="form-horizontal">
				    						    <!-- 一个form-group就是一个整体 -->
				    						    <div class="form-group">
				    						        <label for="VM_add_ProjectName" class="col-sm-3 control-label">项目名称</label>
				    						        <div class="col-sm-9">
				    						            <select id="VM_add_ProjectName" class="form-control">
				    						            	<option value="" disabled>请选择</option>
						    						        <option value="futureC_T1">futureC T1</option>
															<option value="futureD_T1">futureD T1</option>
															<option value="futureD_T2">futureD T2</option>
															<option value="cfChicken_T1">futureE T1</option>
															<option value="cfChicken_T2">futureE T2</option>
															<option value="EUCP_T1">EUCP T1</option>
				    						            </select>
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="VM_add_BoardingTime" class="col-sm-3 control-label">登记时间</label>
				    						        <div class="col-sm-9">
				    						            <input type="date" class="form-control" id="VM_add_BoardingTime">
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="VM_add_password" class="col-sm-3 control-label">邮箱密码</label>
				    						        <div class="col-sm-9">
				    						            <input type="password" class="form-control" id="VM_add_password"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
				    						        </div>
				    						    </div>
				    						</div>
				    					</div><!-- col-md-6 end -->
				    					<div class="col-md-6 col-lg-6">
				    						<div class="form-horizontal">
				    						    <!-- 一个form-group就是一个整体 -->
				    						    <div class="form-group">
				    						        <label for="VM_add_Version" class="col-sm-3 control-label">版本号</label>
				    						        <div class="col-sm-9">
				    						            <input type="text" class="form-control" id="VM_add_Version" placeholder="必填项">
				    						        </div>
				    						    </div>
				    						    <div class="form-group">
				    						        <label for="VM_add_Registrant" class="col-sm-3 control-label">登记人</label>
				    						        <div class="col-sm-9">
				    						            <input type="text" class="form-control" id="VM_add_Registrant">
				    						        </div>
				    						    </div>
				    						</div>
				    					</div><!-- col-md-6 end -->
				    				</div>
				    			</div><!-- container-fluid end -->
				    		</fieldset>
				    		<fieldset><legend>版本文件</legend>
				    					<!-- <div class="upload_wrapper_body">
											<div id="uploader" class="wu-example">
											    用来存放文件信息
											    <div id="thelist" class="uploader-list"></div>
											    <div class="btns">
											        <div id="picker">选择文件</div>
											        <button id="ctlBtn" class="btn btn-default">开始上传</button>
											    </div>
											</div>
										</div> -->
				    		
				    		
				    		
				    		
				    		
				    		
				    		
				    		
				    		
				    		
				    		
				    		
				    		
				    		
				    		
				    		
				    		
                                    <div class="form-group">
                                        <label for="add_file_Upload" class="trigger_click"><button type="button" class="btn btn-info"><span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span>&nbsp;选择文件</button></label>
                                        <input type="file" id="add_file_Upload"  >
                                        <label class="add_label_upload"><button type="button" class="btn btn-info"><span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span>&nbsp;上传</button></label>
                                    </div>
                                    <div class="_wrapper">
                                        <div class="add_fileList_info">
                                            上传总进度：
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-info progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;">0%</div>
                                            </div>
                                           
                                        </div>
                                        <ul class="list-group" id="add_fileList_ul">
                                            </ul>
                                    </div> 
                                </fieldset>
				    		<fieldset style="margin-top:20px"><legend>更新内容&nbsp;<span class="glyphicon glyphicon-plus-sign add_line_p" aria-hidden="true"></span></legend>
				    		<div class="container-fluid appendp_p_div">
				    		</div>
				    		</fieldset>
						</div>
					</div>
					<div class="VersionManagement_foot">
						<div class="VersionManagement_foot_in">
							<input type="button" value="提交" class="btn btn-success" id="VersionManagement_submit">
							<input type="button" value="取消" class="btn btn-warning" id="VersionManagement_cancel">
						</div>
					</div>
				</div>

				<div class="gotoPageTop">
					<span class="glyphicon glyphicon-chevron-up" aria-hidden="true"></span>
				</div>

			<!-- Transport_sticker-con结束 -->
			</div>
	    <!-- Transport_sticker结束 -->
	    </div>
	    <!-- Transport_footer -->
	    <div id="Transport_footer">
	        <div id="eoulu-copy-out" style="height:40px;width:calc(100% - 2px);">
	            <div style="width:100%;height:5px;"></div>
	            <div id="eoulu-copy" style="width:100%;height:35px;font-size:12px;color:#888;line-height:35px;z-index: 2;">
	                <hr style="height:1px;color:#999;width: calc(100% - 3px);" />
	                <div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div>
	            </div>
	        </div>
	    </div>
	<!-- Transport_wrapper结束 -->
	</div>
	
	
	<!-- 警告框 -->
                    <div class="warning_alert_wrapper_cover"></div>
                    <div class="warning_alert_wrapper">
                        <div class="container-fluid">
                            
                        </div>
                    </div>
                    <!-- 警告框结束 -->
              
                    
                    
                    
                    
</body>
<script src="js/libs/integrationLibs/jquery-cookie-ajaxfile-77692a8173.min.js"></script>
<script type="text/javascript" src="plugins/webuploader/webuploader.min.js"></script>
<script src="js/libs/bootstrap.min.js"></script>
<script>
//上传文件
function uploadFiles(Folder, fileObj, curTr, OperateType, OldAttachment){                                               
    var formData = new FormData();
    formData.enctype="multipart/form-data";
    formData.append("Folder",Folder);
    formData.append("file",$("#add_file_Upload")[0].files[0]);//append()里面的第一个参数file对应permission/upload里面的参数file         
    formData.append("Folder","VersionFile");
    $.ajax({
        type: "POST",
        async: true,  //这里要设置异步上传，才能成功调用myXhr.upload.addEventListener('progress',function(e){}),progress的回掉函数
        accept: 'application/json; charset=utf-8',
        data: formData,
        url: "BatchUpload",
        processData: false, // 告诉jQuery不要去处理发送的数据
        contentType: false, // 告诉jQuery不要去设置Content-Type请求头
        cache: false,
        dataType: "json", 
        xhr: function(){                        
            myXhr = $.ajaxSettings.xhr();
            if(myXhr.upload){ // check if upload property exists
                myXhr.upload.addEventListener('progress',function(e){                           
                    var loaded = e.loaded;                  //已经上传大小情况 
                    var total = e.total;                      //附件总大小 
                    var percent = (Math.floor(1000*loaded/total)/10)+"%";     //已经上传的百分比  
                    console.log("已经上传了："+percent);
                    var newWidthFloat =  globalToPoint(percent);
                    var newWidth = (newWidthFloat*100).toFixed(0);
                    $("div.add_fileList_info>div:nth-child(1)>div.progress-bar").attr("aria-valuenow",newWidth).css("width",percent).text(percent);
                    
                }, false); // for handling the progress of the upload
            }
            return myXhr;
        },  
        success: function(data){
        	if(data.length > 0){
        		if(data[0].Message == "上传成功"){
	        		$.Response_Load.After("上传成功", 1000);
        			$('#add_file_Upload').val('');
        		}else if(data[0].Message.indexOf("失败") > -1){
        			$.Response_Load.After("上传失败", 1000);
        			$('#add_file_Upload').val('');
        		}else{
        			$.Response_Load.After("文件已存在", 1000);
        			$('#add_file_Upload').val('');
        		}
        	}else if(data.length == 0){
        		$.Response_Load.After("文件读取至服务器失败！", 1000);
        	}
        },
        error: function(){
        	$.Response_Load.After("网络繁忙！上传失败！", 1000);
        },
		beforeSend: function(XMLHttpRequest){
            $.Response_Load.Before("上传提示","正在提交...", 300);
        },
		complete: function(XMLHttpRequest, textStatus){
		}
    });                           
}

// 定义状态集
var LabState = new Object();
LabState.uploadFileNo = 0;
LabState.uploadFileList = {};
LabState.deleteFileID = "0";
LabState.deleteFileLiDOM = null;
LabState.deleteFileNeedRefresh = false;
//input file域触发点击
$(".trigger_click").click(function(e){
	e.preventDefault();
	$(this).next().trigger("click");
});
//点击浏览切换文件
$("#add_file_Upload").on("change",function(){
	var hideShowDOM;
	var insertDOM;
	var curFileInput;
	curFileInput = $("#add_file_Upload");
	hideShowDOM = $(".add_fileList_info");
	insertDOM = $("#add_fileList_ul");
	if(!hideShowDOM.is(':visible')){
		hideShowDOM.slideDown(150);
	}
	
	var curFileList = $(this)[0].files;
	var curFileListStr = '';
	$.each(curFileList, function(iname, ivalue){
		LabState.uploadFileNo++;
		curFileListStr+='<li class="list-group-item" title="'+ivalue.name+'" value="'+LabState.uploadFileNo+'">'+ivalue.name+'</li>';
		LabState.uploadFileList[LabState.uploadFileNo] = ivalue;
	});
	insertDOM.empty();
	insertDOM.append(curFileListStr);
	$("div.add_fileList_info>div>div.progress-bar").prop("aria-valuenow","0").css("width","0%").text("0%");
});
//添加修改文件上传
$(".add_label_upload>button").click(function(){
	if(Object.keys(LabState.uploadFileList).length == 0){
		$.MsgBox_Unload.Alert("上传提示","请选择文件！");
		return false;
	}
	
	var file_name = $('#add_fileList_ul .list-group-item').html();
	var VM_add_Version = $('#VM_add_Version').val();
	if(VM_add_Version == ''){
		$.MsgBox.Alert("提示", "上传文件前请填写版本号！");
		return;
	}
	if(file_name.indexOf(VM_add_Version) < 0){
		$.MsgBox_Unload.Alert("提示", "文件名中需包含版本号！如：futureC-v1.0.0.180101.exe");
		return;
	}
	
	
	var Folder = "VersionFile";
	var iThat = $(this);
	uploadFiles(Folder, iThat);
});






$(function(){
	$(".VersionManagement_div_tit_add span.glyphicon-remove-circle").click(function(){
		var ih = '40';
		var iw = '90';
		$("input.webuploader-element-invisible").parent().css({"width":iw+"px","height":ih+"px"});
		$("input.webuploader-element-invisible").siblings("label").css({"width":iw+"px","height":ih+"px"});
		$('#thelist').empty();
		
	});
	/* $("input.webuploader-element-invisible").parent().css({"width":"350px","height":"40px"});
	$("input.webuploader-element-invisible").siblings("label").css({"width":"350px","height":"40px"}); */
})
/* // 分片上传
var $list = $('#thelist'),
    $btn = $('#ctlBtn'),
    state = 'pending';
var uploader = WebUploader.create({
    // swf文件路径
    swf: 'plugins/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: 'VersionFileUpload',
    // server: 'http://webuploader.duapp.com/server/fileupload.php',
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#picker',
    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
    resize: false,
    // 是否允许在文件传输时提前把下一个文件准备好
    prepareNextFile: true,
    // 是否要分片处理大文件上传
    chunked: true,
    // 如果某个分片由于网络问题出错，允许自动重传多少次
    chunkRetry: 1,
    // 上传并发数
    threads: 3,
    // 文件上传请求的参数表，每次发送都会发送此对象中的参数
    formData: {
    	md5: ''
    },
    // 设置文件上传域的name
    fileVal: 'file',
    method: 'POST',
    // 是否以二进制的流的方式发送文件，这样整个上传内容php://input都为文件内容， 其他参数在$_GET数组中
    sendAsBinary: false,
    // 去重， 根据文件名字、文件大小和最后修改时间来生成hash Key
    // duplicate: undefined
});

// 当有文件被添加进队列的时候
uploader.on( 'fileQueued', function( file ) {
	    $list.empty().append( '<div id="' + file.id + '" class="item">' +
	        '<h4 class="uploader_info">' + file.name + '</h4>' +
	        '<p class="state">等待上传...</p>' +
	    '</div>' );
	    var _file = $("#" + file.id);
	    uploader.md5File( file )
	    // 及时显示进度
	        .progress(function(percentage) {
	            //console.log('Percentage:', percentage);
	            _file.find("p").html("准备中:"+ percentage * 100 + "%");
	        })
	        // 完成
	        .then(function(val) {
	            uploader.options.formData.md5 = val;
	            _file.find("p").html("准备完成,等待上传.");
	        });
	    console.log(uploader.getStats());
	    $('.VersionManagement_tit').attr('up','uped');
	
	
});

// 文件上传过程中创建进度条实时显示。
uploader.on( 'uploadProgress', function( file, percentage ) {
    var $li = $( '#'+file.id ),
        $percent = $li.find('.progress .progress-bar');
    // 避免重复创建
    if ( !$percent.length ) {
        $percent = $('<div class="progress progress-striped active">' +
          '<div class="progress-bar" role="progressbar" style="width: 0%">0%' +
          '</div>' +
        '</div>').appendTo( $li ).find('.progress-bar');
    }
    $li.find('p.state').text('上传中');
    $percent.css( 'width', percentage * 100 + '%' );
    $percent.text((Math.floor(percentage * 1000))/10 + '%');
});

uploader.on( 'uploadSuccess', function( file , data ) {
	console.log(file)
	var filePath = file.name;
	$('.VersionManagement_tit').attr('filePath',filePath);
    $( '#'+file.id ).find('p.state').text('已上传');
    
    var _file = $("#" + file.id);
    uploader.md5File( file )
        .progress(function(percentage) {
        })
        .then(function(val) {
            uploader.options.formData.md5 = val;
            $('.VersionManagement_tit').attr('md5',uploader.options.formData.md5);
        });
    console.log(uploader.options.formData.md5);
    $.MsgBox.Alert("提示", "上传成功！");
});

uploader.on( 'uploadError', function( file ) {
    $( '#'+file.id ).find('p.state').text('上传出错');
    for (var i = 0; i < uploader.getFiles().length; i++) {
        uploader.removeFile(uploader.getFiles()[i]);
    }
   uploader.reset();
});

uploader.on( 'uploadComplete', function( file ) {
    $( '#'+file.id ).find('.progress').fadeOut();
    for (var i = 0; i < uploader.getFiles().length; i++) {
        uploader.removeFile(uploader.getFiles()[i]);
    }
   uploader.reset();
});

uploader.on( 'all', function( type ) {
    if ( type === 'startUpload' ) {
        state = 'uploading';
    } else if ( type === 'stopUpload' ) {
        state = 'paused';
    } else if ( type === 'uploadFinished' ) {
        state = 'done';
    }

    if ( state === 'uploading' ) {
        $btn.text('暂停上传');
    } else {
        $btn.text('开始上传');
    }
});

$btn.on( 'click', function() {
	var file_name = $('.uploader_info').html();
	var VM_add_Version = $('#VM_add_Version').val();
	if(VM_add_Version == ''){
		$.MsgBox.Alert("提示", "上传文件前请填写版本号！");
		return;
	}
	if((file_name).indexOf(VM_add_Version) < 0){
		$.MsgBox_Unload.Alert("提示", "文件名中需包含版本号！如：futureC-v1.0.0.180101.exe");
		return;
	}
	
    if ( state === 'uploading' ) {
        uploader.stop(true);
    } else {
        uploader.upload();
    }
    $('.VersionManagement_tit').attr('click','clicked');
}); */
//下载文件
$(document).on('click','.download_now',function(){
	var VersionFile = $(this).parent().find('.download_file').text();
	$.ajax({
		type:'GET',
		url:'VersionFileUpload',
		data:{
			VersionFile:VersionFile
		},
		success:function(data){
			 window.location.href=data; 
		},
		error:function(data){
			$.MsgBox.Alert("提示", "服务器繁忙，请稍候再试！");
		}
		
	})
	
})
</script>
</html>