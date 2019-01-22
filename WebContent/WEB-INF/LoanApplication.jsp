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
<title>借用申请</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="css/libs/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="css/paging.css" />
<link rel="stylesheet" type="text/css" href="css/LoanApplication.css?iv=201811151122">
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
					<!-- 	=======================导航栏   开始 ================================== -->
					<%@include file="nav.jsp"%>
					<!-- 	=======================导航栏   结束 ================================== -->
							<div id="page-wrap">
	
	<div class="breadnav">
            <span class="breadnav_logo"><img src="./image/breadnav_logo.png" alt=""></span>
            <span class="busPart">物流部</span>
            <span class="borrowing" >/ 借用申请</span>
    </div>
    <c:forEach var="authoritiy" items="${authorities}">
						<c:if test="${authoritiy=='LoanIsPass'}"> 
							<span class="LoanIsPass" style="display:none">LoanIsPass</span>
						</c:if>
	</c:forEach>
	<div class="btns">
		<span class="add_btn"></span>
		<span class="export_btn"></span>
	</div>
	
	<div class="searchBox">
		<div class="chooseBtn">
			<span class="singleSearch searchType">单一</span>
			<span class="mixSearch">组合</span>
		</div>
		<div class="singleContent">
			<select name="" id="searchTitle1" class="searchTitle1">
				<option value="区域">区域</option>
				<option value="客户名称">客户名称</option>
				<option value="借用申请编号">借用申请编号</option>
				<option value="申请日期">申请日期</option>
				<option value="货物名称">货物名称</option>
				<option value="是否归还">是否归还</option>
			</select>
			<input type="text" class="searchCon1">
			<div class="search_time1">
				<input type="date" class="searchTime1">-&nbsp; 
				<input type="date" class="searchTime2">
			</div>
		</div>
		<div class="mixContent">
			<select name="" id="searchTitle2" class="searchTitle2">
				<option value="区域">区域</option>
				<option value="客户名称">客户名称</option>
				<option value="借用申请编号">借用申请编号</option>
				<option value="申请日期">申请日期</option>
				<option value="货物名称">货物名称</option>
				<option value="是否归还">是否归还</option>
			</select>
			<input type="text" class="searchCon2">
			<div class="search_time2">
				<input type="date" class="searchTime3">-&nbsp; 
				<input type="date" class="searchTime4">
			</div>
		</div>
		<div class="searchbtn">
			<span class="searchbtn1">搜索</span>
			<span class="searchcancel">取消</span>
		</div>
	</div>
	

	<div class="BorrowInfo">
		<table>
			<thead>
				<tr>
					<td class="thead_tdfirst">序号</td>
					<td>区域</td>
					<td>客户名称</td>
					<td class="thead_turn">借用申<br>请编号</td>
					<td>货物名称</td>
					<td>申请日期</td>
					<td>申请人</td>
					<td class="thead_turn">模板<br>预览</td>
					<td>协议</td>
					<!-- 有权限 -->
					
					<td class="thead_turn">申请是<br>否通过</td>
					<td>借用日期</td>
					<td class="thead_turn">预计归<br>还日期</td>
					<td class="Urge_all"><span>催促</span></td>
					<td class="thead_turn">是否<br>归还</td>
					<td class="thead_turn">实际归<br>还日期</td>
					<td class="thead_tdlast" >备注</td>
					<td style="display:none">产品型号</td>
					<td style="display:none">客户联系人</td>
					<td style="display:none">部门</td>
					<td style="display:none">电话</td>
					<td style="display:none">操作日期就是申请日期</td>
				</tr>
			</thead>
			<tbody>
				<!-- <tr class="tbody_tr">
					<td class="edit_btn">1</td>
					<td class="BorrowInfo_area">北方</td>
					<td class="BorrowInfo_c">上海华为</td>
					<td class="BorrowInfo_applynum">20181123</td>
					<td class="BorrowInfo_goodsname">探针台</td>
					<td class="BorrowInfo_applytime">2018-11-12</td>
					<td class="BorrowInfo_applyperson">王小芳</td>
					<td class="preview_btn"><span></span></td>
					<td class="agreeOrnot">是</td>
					<td class="BorrowInfo_borrowtime">2018-10-10</td>
					<td class="BorrowInfo_returntime1">2018-11-22</td>
					<td class="Urge_btn"><span>发送</span></td>
					<td class="BorrowInfo_returnornot">否</td>
					<td class="BorrowInfo_returntime2">2019-01-22</td>
					<td class="BorrowInfo_remark" title="12312322222222222">无</td>
					<td class="BorrowInfo_goodstype" style="display:none">产品型号</td>
				</tr> -->
			</tbody>
		</table>
		
     	<div id="pageToolbar"></div>
		
	</div>

	<div class="mask"></div>
	<div class="mask2"></div>
	<div class="mask3"></div>
    <div id="pageToolbar1"></div>
	<!-- 添加 -->
	<div class="addBox">
		<div class="addmodel_top">
				<span class="addmodel_title">添加申请单</span>
				<span class="addmodel_logo"><img src="./image/goose_logo.png" alt="" /></span>
				<span class="addmodel_close"></span>
		</div>
		<div class="addmodel_con">
			<span class="add_title1">申请信息</span>
			<ul>
				<li>
					<div>
						<span class="addli_Title">申请日期</span>
						<input type="date" class="applyDate">
					</div>
					<div>
						<span class="addli_Title">申请人</span>
						<input type="text" class="applyMan"  placeholder="必填项" required="required">
						<ul class="applyMan_list">
							<li>乐园</li>
							<li>徐峰</li>	
							<li>罗晓旭</li>	
							<li>赵娜</li>	
							<li>张海洋</li>	
							<li>刘亚楠</li>	
						</ul>
					</div>
					<div>
						<span class="addli_Title">申请编号</span>
						<input type="text" class="applyNum" disabled="disabled">
					</div>
				</li>
				<li>
					<div>
						<span class="addli_Title">是否归还</span>
						<select name="" id="returnInfo">
							<option value="请选择">请选择</option>
							<option value="是">是</option>
							<option value="否">否</option>
						</select>
					</div>
					<div>
						<span class="addli_Title">区域</span>
						<select name="" id="area" class="area">
							<option value="请选择">请选择</option>
							<option value="南方">南方</option>
							<option value="北方">北方</option>
							<option value="西南">西南</option>
							<option value="实验室研发">实验室研发</option>
							<option value="其它">其它</option>
						</select>
					</div>
					<div>
						<span class="addli_Title">借用日期</span>
						<input type="date" class="borrowDate">
					</div>
				</li>
				<li>
					<div>
						<span class="addli_Title">预计归还日期</span>
						<input type="date" class="returnTime_plan">
					</div>
					<div>
						<span class="addli_Title">实际归还日期</span>
						<input type="date" class="returnTime_final">
					</div>
					<div>
						<span class="addli_Title">协议</span>
						<input type="file" class="add_agreement" name="FileUpload" id="FileUpload">
					</div>
				</li>
			</ul>
			<span class="add_title2">客户信息</span>
			<ul>
				<li>
					<div>
						<span class="addli_Title">客户名称</span>
						<input type="text" class="customerName" placeholder="必填项">
					</div>
					<div>
						<span class="addli_Title">部门</span>
						<input type="text" class="customerpartment">
					</div>
					<div>
						<span class="addli_Title">联系人</span>
						<input type="text" class="customerMan"  placeholder="必填项">
					</div>
				</li>
				<li>
					<div>
						<span class="addli_Title">电话</span>
						<input type="text" class="customerPhone"  placeholder="必填项">
					</div>
					<div>
						<span class="addli_Title">备注</span>
						<input type="text" class="Remark" placeholder="不超过15个汉字" maxlength="15">
					</div>
				</li>

			</ul>
		</div>

		<div class="addgoods">
			<span class="addgoods_btn">添加货物信息</span>
			<div class="goods_box">
				<!-- <div class="goodslist">
					<div>
						<span>产品型号</span>
						<input type="text">
					</div>
					
					<div>
						<span>产品描述</span>
						<input type="text">
					</div>
				
					<div>
						<span>数量</span>
						<input type="text">
					</div>
					<span class="deleteBtn">删除<br>本行</span>
				</div> -->
			</div>
			<!-- 添加货物信息列表 -->


		</div>
		
		<!-- 按钮 -->
		<div class="addSubmit">
			<span class="addOK">提交</span>
			<span class="addCancel">取消</span>
		</div>
	</div>

	
	<!-- 修改 -->
	<div class="editBox">
		<div class="editmodel_top">
				<span class="editmodel_title">修改申请单</span>
				<span class="editmodel_logo"><img src="./image/goose_logo.png" alt="" /></span>
				<span class="editmodel_close"></span>
		</div>
		<div class="editmodel_con">
			<span class="edit_title1">申请信息</span>
			<ul>
				<li>
					<div>
						<span class="editli_Title">申请日期</span>
						<input type="date" class="applyDate">
					</div>
					<div>
						<span class="editli_Title">申请人</span>
						<input type="text" class="applyMan" required="required">
						<ul class="applyMan_list">
							<li>乐园</li>
							<li>徐峰</li>	
							<li>罗晓旭</li>	
							<li>赵娜</li>	
							<li>张海洋</li>	
							<li>刘亚楠</li>	
						</ul>
					</div>
					<div>
						<span class="editli_Title">申请编号</span>
						<input type="text" class="applyNum">
					</div>
				</li>
				<li>
					<div>
						<span class="editli_Title">是否归还</span>
						<select name="" id="returnInfo">
							<option value="请选择">请选择</option>
							<option value="是">是</option>
							<option value="否">否</option>
						</select>
					</div>
					<div>
						<span class="editli_Title">区域</span>
						<select name="" id="area" class="area">
							<option value="请选择">请选择</option>
							<option value="南方">南方</option>
							<option value="北方">北方</option>
							<option value="西南">西南</option>
							<option value="实验室研发">实验室研发</option>
							<option value="其它">其它</option>
						</select>
					</div>
					<div>
						<span class="editli_Title">借用日期</span>
						<input type="date" class="borrowDate">
					</div>
				</li>
				<li>
					<div>
						<span class="editli_Title">预计归还日期</span>
						<input type="date" class="returnTime_plan">
					</div>
					<div>
						<span class="editli_Title">实际归还日期</span>
						<input type="date" class="returnTime_final">
					</div>
					<div>
						<span class="editli_Title">协议</span>
						<input type="file" class="edit_agreement" name="FileUpload2" id="FileUpload2">
						<input type="text" class="edit_agreementName"/>
					</div>
				</li>
			</ul>
			<span class="edit_title2">客户信息</span>
			<ul>
				<li>
					<div>
						<span class="editli_Title">客户名称</span>
						<input type="text" class="customerName">
					</div>
					
					<div>
						<span class="editli_Title">部门</span>
						<input type="text" class="customerpartment">
					</div>
					<div>
						<span class="editli_Title">联系人</span>
						<input type="text" class="customerMan">
					</div>
				</li>
				<li>
					<div>
						<span class="addli_Title">电话</span>
						<input type="text" class="customerPhone">
					</div>
					<div>
						<span class="editli_Title">备注</span>
						<input type="text" class="Remark" maxlength="15">
					</div>
				</li>
				
			</ul>
		</div>

		<div class="editgoods">
			<span class="editgoods_btn">添加货物信息</span>
			<div class="goods_editBox"></div>
			<!-- 添加货物信息列表 -->


		</div>
		
		<!-- 按钮 -->
		<div class="editSubmit">
			<span class="editOK">提交</span>
			<span class="editCancel">取消</span>
		</div>
	</div>


	<!-- 带章发送中的邮件模板 -->
	<div class="sendEmail1 ">
		<div class="send_top">
				<span class="sendmodel_title">带章发送邮件模板</span>
				<span class="sendmodel_logo"><img src="./image/goose_logo.png" alt="" /></span>
				<span class="sendmodel_close"></span>
		</div>
		<div class="sendmodel_con1">
			<ul>
				<li class="send_Acc">
					<span>发件人</span>
					<input type="text">
				</li>
				<li class="send_Psd">
					<span>邮箱密码</span>
					<input type="password">
					<i  class="send_Psdeye"></i>
				</li>
				<li class="send_Rec">
					<span>收件人</span>
					<span class="send_receivespan"  contenteditable="true">liuyanan@eoulu.com</span>
					<!-- <span class="send_receivespan"  contenteditable="true"  >sunmengying@eoulu.com</span> -->
				</li>
				<li class="send_Copy">
					<span>抄送</span>
					<span  class="send_copyspan" contenteditable="true">zhaowenzhen@eoulu.com,luwenbo@eoulu.com,jiangyaping@eoulu.com,fangyuanyuan@eoulu.com,zhudanni@eoulu.com,gaona@eoulu.com,chenshanshan@eoulu.com</span>
					<!-- <span class="send_copyspan"  contenteditable="true"  >yeqingliu@eoulu.com</span> -->
				</li>
				<li class="send_Subject">
					<span>主题</span>
					<input type="text">
				</li>
				<li class="send_Con">
					<span class="send_ConspanTitle">正文</span>
					<span class="send_Conspan" contenteditable="true" ></span>
				</li>
			</ul>
		</div>

		<div class="sendBtns">
			<span class="sendOK1">发送邮件</span>
			<span class="sendCancel1">取消发送</span>
		</div>
	</div>
	<!-- 催促发送中的邮件模板 -->
	<div class="sendEmail2">
		<div class="send_top">
				<span class="sendmodel_title">催促邮件模板</span>
				<span class="sendmodel_logo"><img src="./image/goose_logo.png" alt="" /></span>
				<span class="sendmodel_close"></span>
		</div>
		<div class="sendmodel_con2">
			<ul>
				<li class="send_Acc">
					<span>发件人</span>
					<span class="send_account">remind@eoulu.com</span>
				</li>
				<li class="send_Rec" style="display:none">
					<span>收件人</span>
					<span class="send_receivespan"></span>
				</li>
				<li class="send_Copy">
					<span>抄送</span>
					<span class="send_copyspan"  contenteditable="true">liuyanan@eoulu.com,zhaowenzhen@eoulu.com,luwenbo@eoulu.com,jiangyaping@eoulu.com,fangyuanyuan@eoulu.com,zhudanni@eoulu.com,gaona@eoulu.com,chenshanshan@eoulu.com</span>
					<!-- <span class="send_copyspan"  contenteditable="true"  >yeqingliu@eoulu.com</span> -->
					
				</li>
				<li class="searchContentBox"></li>
				<li class="send_Subject">
					<span>主题</span>
					<input type="text">
				</li>
				<li class="send_Con">
					<span class="send_ConspanTitle">正文</span>
					<span class="send_Conspan" contenteditable="true" >刘经理，您好！<br>
			附件： A是BCG的借用申请，请尽快确认是否可以借用。<br>
			非常感谢！</span>
				</li>
			</ul>
		</div>

		<div class="sendBtns">
			<span class="sendOK2">发送邮件</span>
			<span class="sendCancel2">取消发送</span>
		</div>
	</div>
	<!-- 预览模板 -->
	<div class="borrowInfo_model">
		<div class="wordModel">
			<div class="wordModel_top">
				<img src="image/EOULUlogo.png" alt="">
				<span> EOULU Technology</span>
			</div>
			<span class="wordModel_title">Eoulu借用申请单</span>
			<div class="content1">
				<ul>
					<li>
						<span>公司：</span>
						<span class="model_company">XXXXXX</span>
					</li>
					<li>
						<span>借用单号：</span>
						<span class="model_number">XXXXXX</span>
					</li>
				</ul>
				<ul>
					<li>
						<span>部门：</span>
						<span class="model_depart">XXXXXX</span>
					</li>
					<li>
						<span>借用日期：</span>
						<span class="model_borrowDate">XXXXXX</span>
					</li>
				</ul>
				<ul>
					<li>
						<span>联系人：</span>
						<span class="model_concat">XXXXXX</span>
					</li>
					<li>
						<span>预计归还日期：</span>
						<span class="model_returntime">XXXXXX</span>
					</li>
					<li>
						<span>电话：</span>
						<span class="model_phone">XXXXXX</span>
					</li>
				</ul>
			</div>

			<div class="content2">
				<table>
					<tr class="Modeltable_title">
						<td>序号</td>
						<td>型号</td>
						<td>描述</td>
						<td>序列号</td>
						<td>Qty</td>
					</tr>
					<!-- <tr>
						<td>1</td>
						<td>22222</td>
						<td>333333</td>
						<td>4444444</td>
						<td>5</td>
					</tr> -->
					
				</table>
			</div>
			<div class="content3">
				<span>借用须知：</span>
				<p>1.物品的所有权归借出方（苏州伊欧陆系统集成有限公司）所有，借出方有权要求物品按期归还。</p>
				<p>2.申请方需小心使用货品，注意包装保护，确保物品归还时无人为或非正常损坏。如情况严重，申请方需按货物实际价值和损失程度相应赔偿给借出方。</p>
				<p>3.借用放需妥善保存所有货物包装材料，如因特殊原因无法保存，需提前与借出方沟通回收所有包装材料。如货物包装丢失，苏州伊欧陆系统集成有限公司有权要求借用方赔偿包装材料。</p>
				<p>4.若借用方需要延长借用期限，必须在预计归还日期提前2周向借出方提出正式邮件，由借出方审核是否予以延期。</p>
				<p>5.若未提前申请延期，或延期申请审核未通过，借用方须在预计归还日期归还借用货物，如有逾期，每延迟一天，借用方向借出方支付货物总金额的0.1%违约金。</p>
			</div>
		 	<div class="content4">
		 		<div class="content4_f">
					<div>借出方：苏州伊欧陆系统集成有限公司 </div>
					<div><span>签字：</span><span class="content4_img"><img src="image/signName.png" alt=""></span></div>
				</div>
				<div class="content4_r">
					<div>借用方：<span class="content4r_span1" contenteditable="true"></span></div>
					<div>签字：<span class="content4r_span2" contenteditable="true"></span></div>
				</div>
		 	</div>
			<div class="wordModel_bottom">
				<h3>EOULU</h3>
				<span>Shenzhen ● Suzhou ● Beijing ● Shanghai ● Chengdu ● HongKong</span>
				<span>〡Phone: +86-512-62757360〡Web:www.eoulu.com〡Email:info@eoulu.com〡</span>
			</div>





		</div>
		<div class="wordModel_btns">
			<span class="wordModel_close">关闭</span>
			<span class="wordModel_send">带章发送</span>
			<span class="wordModel_down">带章下载</span>
		</div>
	</div>
	<!--审批弹窗  -->
	<div class="pass_box">
		<div class="passBox_top">
				<span class="passBox_title">审核</span>
				<span class="passBox_logo"><img src="./image/goose_logo.png" alt="" /></span>
				<span class="passBox_close"></span>
		</div>
		<div class="passBox_con">
			<div class="passBox_question">审核通过吗？</div>
			<div class="passBox_anwser">
				<span class="Nopass">否</span>
				<span class="pass">是</span>
			</div>
			<div class="nopass_div">
				<div class="nopass_title">不通过原因</div>
				<div class="nopass_reason"><input type="text"></div>
				<div class="nopass_btn">
					<span class="Nopass_cancel">取消</span>
					<span class="pass_ok">提交</span>
				</div>
			</div>
		</div>
	</div>
	<!-- 一键催促的弹窗-->
	<div class="allUrge_box">
		<div class="allUrgeBox_top">
				<span class="allUrgeBox_title">一键催促</span>
				<span class="allUrgeBox_logo"><img src="./image/goose_logo.png" alt="" /></span>
				<span class="allUrgeBox_close"></span>
		</div>
		<div class="allUrgeBox_con">
				<table>
					<thead>
						<tr>
							<td class="choose_btn"><input type="checkbox"/></td>
							<td class="allUrgeBox_tdfirst">序号</td>
							<td>区域</td>
							<td>客户名称</td>
							<td class="allUrgeBox_turn">借用申请编号</td>
							<td>货物名称</td>
							<td>申请日期</td>
							<td>申请人</td>
							<!-- 有权限 -->
							
							<td class="allUrgeBox_pass">申请是否通过</td>
							<td>借用日期</td>
							<td class="">预计归还日期</td>
							<td class="allUrgeBox_returnornot">是否归还</td>
							<td style="display:none">产品型号</td>
							<td style="display:none">客户联系人</td>
							<td style="display:none">部门</td>
							<td style="display:none">电话</td>
							<td style="display:none">操作日期就是申请日期</td>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
				
		</div>
		<div class="allUrgeBox_btn">
			<span class="allUrgeBox_cancel">取消</span>
			<span class="allUrgeBox_ok">确认发送</span>
		</div>
		
		
	</div>
</div>	
</div>	
</div>	
	 <div class="progressBox">
		   <div class="logo"><img src="./image/goose_logo.png" alt=""></div>
	 		<div class="top">提示您：</div>
	 		<div class="tip">操作正在处理中……</div>
			<div class="progress">
				<div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
				    <span class="sr-only">100% Complete</span>
				</div>
			</div>
	 </div>
	 
	 
	 
	 
	 
	<script src="js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="js/organictabs.jquery.js"></script>
    <script type="text/javascript" src="js/query.js"></script>
    <script type="text/javascript" src="js/paging.js"></script>
    <script type="text/javascript" src="js/libs/bootstrap.min.js"></script>
	<script src="js/msgbox_unload.js"></script>
	<script src="js/msgbox.js"></script>
    <script type="text/javascript" src="js/LoanApplication.js"></script>
    <script type='text/javascript'>
    $('.eou-container-l a img').css({
		'display':'block',
        'margin': '5px 20px'
	});
    /* 
    	$('.eou-container-l a').css({
    		'width' : '50px',
    		'float' : 'left',
    		'margin-right':'30px'
    	});
    	$('.eou-container-l a').after('').after('<h2>futureE</h2>') */
        $(function() {
    
            $("#wrap_table").organicTabs();
            
            $("#example-two").organicTabs({
                "speed": 200
            });
    
        });
      
    </script>
</body>
</html>