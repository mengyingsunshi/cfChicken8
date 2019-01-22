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
<title>拜访申请</title>
<link rel="shortcut icon" href="image/eoulu.ico" />
<link rel="stylesheet" type="text/css" href="css/paging.css" />
<link rel="stylesheet" href="css/CostStatistics.css">
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
					<!-- 	=======================导航栏   结束 ================================== -->
							<div id="page-wrap">
	<div class="indexPageBox">
		<div class="breadNav">
			<span class="breadL">位置：</span>
			<span class="breadR">销售部 &nbsp; >  &nbsp;</span>
			<span style="color:#18b4f0">拜访申请</span>
		</div>
		<div class="addBtn">
			<span class="add_btn"></span>
		</div>
		<table class="costTable">
			<thead>
				<tr>
					<td>序号</td>
					<td>客户单位</td>
					<td>服务事宜</td>
					<td>拜访时间</td>
					<td>销售负责人</td>
					<td>申请工程师</td>
					<!-- <td>申请</td> -->
					<td>是否通过</td>
					<td>费用统计(￥)</td>
					<td style="display:none">收件人</td>
					<td style="display:none">抄送人</td>
				</tr>
			</thead>
			<tbody>
					<!-- <tr>
						<td class="editBtn">1</td>
						<td class="customer">上海华为</td>
						<td class="service">售后维修</td>
						<td class="visitDate">2019-01-01</td>
						<td class="saleMan">张晓华</td>
						<td class="engineer">王刚</td>
						<td class="applyBtn"><span>申请</span></td>
						<td class="passBtn"><span>待审批</span></td>
						<td class="cost">10000</td>
					</tr>
					<tr>
						<td class="editBtn">1</td>
						<td class="customer">上海华为</td>
						<td class="service">售后维修</td>
						<td class="visitDate">2019-01-01</td>
						<td class="saleMan">张晓华</td>
						<td class="engineer">王刚</td>
						<td class="applyBtn"><span>申请</span></td>
						<td class="passBtn"><span>通过</span></td>
						<td class="cost">10000</td>
					</tr>
					<tr>
						<td class="editBtn">1</td>
						<td class="customer">上海华为</td>
						<td class="service">售后维修</td>
						<td class="visitDate">2019-01-01</td>
						<td class="saleMan">张晓华</td>
						<td class="engineer">王刚</td>
						<td class="applyBtn"><span>申请</span></td>
						<td class="passBtn"><span>未通过</span></td>
						<td class="cost">10000</td>
					</tr> -->
			</tbody>
			
		</table>


		<!-- 分页条 -->
		<div id="pageToolbar1"></div>
	</div>
	<div class="addBox">
			<div class="addBox_title">
				<span class="add_txt">添加-费用统计</span>
				<span class="add_close "></span>
			</div>
			<span class="addCon_title">拜访信息</span>
			<ul class="addBox_content">
				<li class="CustomerName">
					<span>客户单位</span>
					<input type="text" >
				</li>
				<li class="ServiceItem">
					<span>服务事宜</span>
					<input type="text" >
				</li>
				<li class="VisitTime">
					<span>拜访时间</span>
					<input type="date" style="font-family:'微软雅黑'">
				</li>
				<li class="SalesMan">
					<span>销售负责人</span>
					<select name="sales_man" >
					<!-- 	<option text="叶青柳">叶青柳</option>
						<option text="孙梦颖">孙梦颖</option> -->
						<option text="乐园">乐园</option>
						<option text="赵文珍">赵文珍</option>
						<option text="赵娜">赵娜</option>
						<option text="肖宇">肖宇</option>
						<option text="徐峰">徐峰</option>
						<option text="方源媛">方源媛</option>
						<option text="宋代鳌">宋代鳌</option>
						<option text="刘渊">刘渊</option>
						<option text="朱丹妮">朱丹妮</option>
						<option text="蒋亚平">蒋亚平</option>
						<option text="罗晓旭">罗晓旭</option>
						<option text="刘伟">刘伟</option>
						<option text="刘亚楠">刘亚楠</option>
						<option text="高振鹏">高振鹏</option>
						<option text="吕文波">吕文波</option>
						<option text="张海洋">张海洋</option>
					</select>
				</li>
				<li class="Engineer">
					<span>申请工程师</span>
					<input type="search" class="add_engineer" >
					<ul class="add_engineer_list"></ul>
					
				</li>
			</ul>
			
			<span class="addCon_title2">申请邮件模板</span>
			<div class="sendApplication_Email1 ">
				<div id="To_div"></div>
				<div id="CC_div"></div>
				<ul class="sendBox_content">
					<li class="Send">
						<span>发件人</span>
						<input type="text" readonly>
					</li>
					<li class="Password">
						<span>密码</span>
						<span class="password" contenteditable="true"></span><!-- <i class="eye"></i> -->
					</li>
					<li class="To">
						<span>收件人</span>
						<div  id="Tolist" contenteditable="true"></div>
					</li>
					<li class="Copyto">
						<span>抄送人</span>
						<div  id="Copylist" contenteditable="true"></div>
					</li>
				</ul>
			</div>
			
			<div class="addBtns">
				<span class="add_cancle">取消</span>
				<span class="add_ok">提交</span>
			</div>
	</div>
	<div class="editBox">
			<div class="editBox_title">
				<span class="edit_txt">修改-费用统计</span>
				<span class="edit_close "></span>
			</div>
			<span class="editCon_title">拜访信息</span>
			<ul class="editBox_content">
				<li class="CustomerName">
					<span>客户单位</span>
					<input type="text" >
				</li>
				<li class="ServiceItem">
					<span>服务事宜</span>
					<input type="text" >
				</li>
				<li class="VisitTime">
					<span>拜访时间</span>
					<input type="date" style="font-family:'微软雅黑'">
				</li>
				<li class="SalesMan">
					<span>销售负责人</span>
					<select name="sales_man" >
						<!-- <option text="叶青柳">叶青柳</option>
						<option text="孙梦颖">孙梦颖</option> -->
						<option text="乐园">乐园</option>
						<option text="赵文珍">赵文珍</option>
						<option text="赵娜">赵娜</option>
						<option text="肖宇">肖宇</option>
						<option text="徐峰">徐峰</option>
						<option text="方源媛">方源媛</option>
						<option text="宋代鳌">宋代鳌</option>
						<option text="刘渊">刘渊</option>
						<option text="朱丹妮">朱丹妮</option>
						<option text="蒋亚平">蒋亚平</option>
						<option text="罗晓旭">罗晓旭</option>
						<option text="刘伟">刘伟</option>
						<option text="刘亚楠">刘亚楠</option>
						<option text="高振鹏">高振鹏</option>
						<option text="吕文波">吕文波</option>
						<option text="张海洋">张海洋</option>
					</select>
				</li>
				<li class="Engineer">
					<span>申请工程师</span>
					<input type="search" class="edit_engineer" >
					<ul class="edit_engineer_list"></ul>
					
				</li>
			</ul>
			<span class="editCon_title2">申请邮件模板</span>
			<div class="sendApplication_Email2 ">
				<div id="To_div2"></div>
				<div id="CC_div2"></div>
				<ul class="sendBox_content">
					<li class="Send">
						<span>发件人</span>
						<input type="text" readonly>
					</li>
					<li class="Password">
						<span>密码</span>
						<span class="password" contenteditable="true"></span><!-- <i class="eye"></i> -->
					</li>
					<li class="To">
						<span>收件人</span>
						<div  id="Tolist2" contenteditable="true"></div>
					</li>
					<li class="Copyto">
						<span>抄送人</span>
						<div  id="Copylist2" contenteditable="true"></div>
					</li>
				</ul>
			</div>
			<div class="editBtns">
				<span class="edit_cancle">取消</span>
				<span class="edit_ok">提交</span>
			</div>
	</div>

	<div class="passBox">
		<div class="passBox_title">
			<span class="passBox_txt">申请是否通过</span>
			<span class="passBox_close glyphicon glyphicon-remove-circle"></span>
		</div>
		<div class="passBox_content">
			<p><span class=""></span>您好,请选择是否通过此申请！</p>
			<div class="pass_btns">
				<span class="passNo">否</span>
				<span class="passYes">是</span>
			</div>
			<div class="passNoBox">
				<div class="passNo_reason">
					<span>不通过的原因：</span>
					<input type="text">
				</div>
				<div class="nopass_btn">
						<span class="Nopass_cancel">取消</span>
						<span class="pass_ok">提交</span>
				</div>
			</div>
		</div>
	</div>	
	<!-- <div class="sendApplication_Email">
		<div class="sendBox_title">
			<span class="sendBox_txt">发送申请邮件</span>
			<span class="sendBox_close "></span>
		</div>
		<div id="To_div"></div>
		<div id="CC_div"></div>
		<ul class="sendBox_content">
			<li class="Send">
				<span>发件人</span>
				<input type="text" readonly>
			</li>
			<li class="Password">
				<span>密码</span>
				<span class="password" contenteditable="true"></span><i class="eye"></i>
			</li>
			<li class="To">
				<span>收件人</span>
				<div  id="Tolist" contenteditable="true"></div>
			</li>
			<li class="Copyto">
				<span>抄送人</span>
				<div  id="Copylist" contenteditable="true"></div>
			</li>
			<li class="Subject">
				<span>主题</span>
				<input type="text"   readonly>
			</li>
			<li  class="email_con">
				<span>内容</span>
				<div ></div>
			</li>
		</ul>
		
		<div class="sendBox_btn">
			<span class="send_cancle">取消</span>
			<span class="send_ok">发送</span>
		</div>
	</div> -->
	
	
</div>	</div>	</div>	</div>	</div>	
	<div class="mask"></div>
	<script src="js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="js/query.js"></script>
    <script type="text/javascript" src="js/paging.js"></script>
    <script src="js/msgbox.js"></script>
	<script src="js/msgbox_unload.js"></script>
	<script src="js/CostStatistics.js"></script>
</body>
</html>