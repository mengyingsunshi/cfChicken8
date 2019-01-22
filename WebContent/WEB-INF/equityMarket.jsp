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
<title>鹅市</title>
<link rel="shortcut icon" href="image/eoulu.ico"/>
<link rel="bookmark" href="image/eoulu.ico"/>
<link rel="stylesheet" type="text/css" href="css/paging.css" />
<link rel="stylesheet" type="text/css" href="css/equityMarket.css?iv=201810151122">
<style>
	.nav-body {
	    overflow-y: hidden!important;
	    height: auto!important;
	    max-height: none!important;
	    min-height: 0!important;
	}
	#originfactory_wrapper{
	    position: fixed;
	    overflow: auto; 
	    width: 100%;
	    height: 100%;
	 /*    min-height:650px; */
	    box-sizing: border-box;
	}
	#originfactory_sticker {
	    width: 100%;
	    min-height: 100%;
	    box-sizing: border-box;
	}
	#originfactory_sticker-con {
	    padding-bottom: 40px;
	    box-sizing: border-box;
	}
	.content {
	    padding-bottom: 5px !important;
	}
	#Transport_footer #eoulu-copy-out{
		position:static!important;
	}
</style>
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
            <span class="breadnav_logo"><img src="./image/breadnav_logo.png" alt="" /></span>
            <span class="staffPart">人事部</span>
            <span class="equityMarketpart">/ 鹅市</span>
        </div>					
        <div class="choose">
            <div class="sell "><img src="./image/sell.png" alt="" /></div>
            <div class="buy "><img src="./image/buy.png" alt="" /></div>
            <div class="addInfo hidden"><img src="./image/add.png" alt="" /></div>
        </div>

        <!--页面表格 -->
        <div id="wrap_table">
        	<ul class="nav">
                <li class="nav-one"><a href="#Tradeshares" class="current Tradeshares">贸易鹅</a></li>
                <li class="nav-two"><a href="#Countshares" class="Countshares">统计鹅</a></li>
                <li class="nav-three"><a href="#Myshares" class=" Myshares">我的鹅</a></li>
                <li class="nav-four last" style="display:none"><a href="#MonthCount" class="MonthCount">统计鹅本月</a></li>
            </ul>
        	<div class="list-wrap">
        		<ul id="Tradeshares">
                    <li class="Tradeshares_firstTitle">
                        <a class="tableCell " href="#">序号</a>
                        <a class="tableCell " href="#">姓名</a>
                        <a class="tableCell " href="#">状态</a>
                        <a class="tableCell " href="#">鹅数</a>
                        <a class="tableCell " href="#">更新时间</a>
                        <a class="tableCell operate" href="#">操作</a>
                        <a class="tableCell " href="#">贸易记录</a>
                    </li>
                    <li class="Tradeshares_table">
                       <!--  <div class="Tradeshares_list">
                            <a class="tableCell " href="#">1</a>
                            <a class="tableCell " href="#">22</a>
                            <a class="tableCell " href="#">买入</a>
                            <a class="tableCell " href="#">500</a>
                            <a class="tableCell " href="#">2018-11-22 12:30:30</a>
                            <a class="tableCell " href="#">卖</a>
                        </div> -->
                        
                    </li>     
        		</ul>
        		<ul id="Countshares" class="hide">
        		   <li class="Countshares_firstTitle">
                        <a class="tableCell " href="#">今日鹅价</a>
                        <a class="tableCell " href="#">今日买入</a>
                        <a class="tableCell " href="#">今日卖出</a>
                        <a class="tableCell " href="#">统计时间</a>
                        <a class="tableCell " href="#">本月统计</a>
                    </li>
                    <li class="Countshares_table">
                        <!-- <div class="Countshares_list">
                            <a class="tableCell " href="#">10</a>
                            <a class="tableCell " href="#">20</a>
                            <a class="tableCell " href="#">30</a>
                            <a class="tableCell " href="#">2018-11-22 12:30:30</a>
                            <a class="tableCell " href="#"><img src="./image/month.png" alt="" /></a>
                        </div> -->
                    </li>     
        		</ul>

                <ul id="Myshares" class="hide">
                    <li class="Myshares_firstTitle">
                        <a class="tableCell " href="#">序号</a>
                        <a class="tableCell " href="#">姓名</a>
                        <a class="tableCell " href="#">部门</a>
                        <a class="tableCell " href="#">拥有鹅数</a>
                        <a class="tableCell " href="#">可交易鹅数</a>
                        <a class="tableCell " href="#">更新时间</a>
                    </li>
                    <li class="Myshares_table">
                       <!--  <div class="Myshares_list">
                            <a class="tableCell tablecell_id" href="#">1</a>
                            <a class="tableCell tablecell_name" href="#">红</a>
                            <a class="tableCell tablecell_department" href="#">商务部</a>
                            <a class="tableCell tablecell_shares" href="#">1000</a>
                            <a class="tableCell tablecell_change" href="#">500</a>
                            <a class="tableCell tablecell_time" href="#">2018-11-22 12:30:30</a>
                        </div> -->
                    </li>       
                    
        		</ul>
        		
                <ul id="MonthCount" class="hide">
                   <li class="MonthCount_firstTitle">
                        <a class="tableCell " href="#">序号</a>
                        <a class="tableCell " href="#">日期</a>
                        <a class="tableCell " href="#">每日鹅价</a>
                        <a class="tableCell " href="#">当日买入</a>
                        <a class="tableCell " href="#">当日卖出</a>
                        <a class="tableCell " href="#">成交量</a>
                        <a class="tableCell " href="#">更新时间</a>
                    </li>
                    <li class="MonthCount_table">
                        <!-- <div class="MonthCount_list">
                            <a class="tableCell " href="#">1</a>
                            <a class="tableCell " href="#">2018-11-22</a>
                            <a class="tableCell " href="#">2222</a>
                            <a class="tableCell " href="#">1000</a>
                            <a class="tableCell " href="#">0</a>
                            <a class="tableCell " href="#">2018-11-22 12:30:30</a>
                        </div> -->
                    </li>     
                </ul>
       	  </div> 
         
        </div> 
	  
		
       <!--添加我的鹅信息 -->
       <div class="addShare_Model">
			<div class="addmodel_top">
				<span class="addmodel_title">我的鹅</span>
				<span class="addmodel_logo"><img src="./image/goose_logo.png" alt="" /></span>
				<span class="addmodel_close"></span>
			</div>
			<ul class="addmodel_content">
				<li class="add_input yourName">
					<span>姓名</span>
					<input type="text"  placeholder="请输入姓名"/>
				</li>
				<li class="add_input yourDepartment">
					<span>部门</span>
					<select name="" id="" class="">
						<option value="请选择部门">请选择部门</option>
 													  <option value="人事部">人事部</option>
													  <option value="销售部">销售部</option>
													  <option value="商务部">商务部</option>
													  <option value="服务部">服务部</option>
													  <option value="物流部">物流部</option>
													  <option value="市场部">市场部</option>
													  <option value="财务部">财务部</option>
													  <option value="软件部">软件部</option>
													  <option value="硬件部">硬件部</option>
													  <option value="标准服务部">标准服务部</option>
													  <option value="应用部">应用部</option>
													  <option value="研发部">研发部</option>
													  <option value="厦门办事处">厦门办事处</option>													  
					</select>
				</li>
				<li class="add_input yourShares">
					<span>拥有鹅数</span>
					<input type="text"  placeholder="请输入拥有鹅数"/>
				</li>
				<li class="add_input yourChangeshares" >
					<span>可交易鹅数</span>
					<input type="text" placeholder="请输入可交易鹅数"/>
				</li>
			</ul>
			<div class="addmodel_btn">
				<span class="addmodel_submit">提交</span>
				<span class="addmodel_cancel">取消</span>
			</div>
       </div>

		<!--修改我的鹅信息 -->
       <div class="editShare_Model">
			<div class="editmodel_top">
				<span class="editmodel_title">我的鹅</span>
				<span class="editmodel_logo"><img src="./image/goose_logo.png" alt="" /></span>
				<span class="editmodel_close"></span>
			</div>
			<ul class="editmodel_content">
				<li class="edit_input yourName">
					<span>姓名</span>
					<input type="text"  placeholder="请输入姓名"/>
				</li>
				<li class="edit_input yourDepartment">
					<span>部门</span>
					<select name="" id="" class="">
						<option value="请选择部门">请选择部门</option>
						 							  <option value="人事部">人事部</option>
													  <option value="销售部">销售部</option>
													  <option value="商务部">商务部</option>
													  <option value="服务部">服务部</option>
													  <option value="物流部">物流部</option>
													  <option value="市场部">市场部</option>
													  <option value="财务部">财务部</option>
													  <option value="软件部">软件部</option>
													  <option value="硬件部">硬件部</option>
													  <option value="标准服务部">标准服务部</option>
													  <option value="应用部">应用部</option>
													  <option value="研发部">研发部</option>
													  <option value="厦门办事处">厦门办事处</option>
					</select>
				</li>
				<li class="edit_input yourShares">
					<span>拥有鹅数</span>
					<input type="text"  placeholder="请输入拥有鹅数"/>
				</li>
				<li class="edit_input yourChangeshares" >
					<span>可交易鹅数</span>
					<input type="text" placeholder="请输入可交易鹅数"/>
				</li>
			</ul>
			<div class="editmodel_btn">
				<span class="editmodel_submit">提交</span>
				<span class="editmodel_cancel">取消</span>
			</div>
       </div>
		

		<!--买入 -->
		<div class="buy_model">
			<div class="buymodel_top">
				<span class="buymodel_title">买入</span>
				<span class="buymodel_logo"><img src="./image/goose_logo.png" alt="" /></span>
				<span class="buymodel_close"></span>
			</div>
			<ul class="buymodel_content">
				<li class="buy_input yourName">
					<span>姓名</span>
					<input type="text"  placeholder="请输入姓名" readonly="readonly"/>
				</li>
				<li class="buy_input SharesNumber">
					<span>可买入鹅数</span>
					<input type="text"  placeholder="可买入鹅数" readonly="readonly"/>
				</li>
				<li class="buy_input yourShares">
					<span>实际买入鹅数</span>
					<input type="text"  placeholder="实际买入鹅数"/>
				</li>
				<li class="buy_input yourChangeshares" >
					<span>实际买入鹅价</span>
					<input type="text" placeholder="实际买入鹅价" readonly="readonly"/>
				</li>
			</ul>
			<div class="buymodel_btn">
				<span class="buymodel_submit">提交</span>
				<span class="buymodel_cancel">取消</span>
			</div>
		</div>

		<!--卖出 -->
		<div class="sell_model">
			<div class="sellmodel_top">
				<span class="sellmodel_title">卖出</span>
				<span class="sellmodel_logo"><img src="./image/goose_logo.png" alt="" /></span>
				<span class="sellmodel_close"></span>
			</div>
			<ul class="sellmodel_content">
				<li class="sell_input yourName">
					<span>姓名</span>
					<input type="text"  placeholder="请输入姓名"  readonly="readonly"/>
				</li>
				<li class="sell_input SharesNumberSell">
					<span class="SharesNumberSell_span1">可卖出鹅数</span>
					<input type="text"  placeholder="可卖出鹅数"  readonly="readonly"/>
				</li>
				<li class="sell_input yourShares">
					<span>实际卖出鹅数</span>
					<input type="text"  placeholder="实际卖出鹅数"/>
				</li>
				<li class="sell_input yourChangeshares" >
					<span>实际卖出鹅价</span>
					<input type="text" placeholder="实际卖出鹅价" readonly="readonly"/>
				</li>
			</ul>
			<div class="sellmodel_btn">
				<span class="sellmodel_submit">提交</span>
				<span class="sellmodel_cancel">取消</span>
			</div>
		</div>
		<!-- 贸易记录 -->
		<div class="TradeRecord_model">
				<div class="TradeRecordmodel_top">
				<span class="TradeRecordmodel_title"></span>
				<span class="TradeRecordmodel_logo"><img src="./image/goose_logo.png" alt="" /></span>
				<span class="TradeRecordmodel_close"></span>
			</div>
			<ul class="TradeRecordmodel_content">
					<li class="TradeRecord_firstTitle">
                        <a class=" " href="#">序号</a>
                        <a class=" " href="#">状态</a>
                        <a class=" TradeRecordTitle_name" href="#">姓名</a>
                        <a class=" " href="#">鹅数</a>
                        <a class=" TradeRecord_OperatingTime" href="#">交易时间</a>
                        <a class=" TradeRecord_Remark" href="#">备注</a>
                    </li>
                    <li class="TradeRecord_table">
                       <div class="Tradeshares_list">
                            <a class="tableCell " href="#">1</a>
                            <a class="tableCell " href="#">22</a>
                            <a class="tableCell " href="#">买入</a>
                            <a class="tableCell " href="#">500</a>
                            <a class="tableCell " href="#">2018-11-22 12:30:30</a>
                        </div>
                    </li> 
			</ul>
		</div>

		<!--实时更新  -->
		<div class="todayPrice">
			<p><span class="todayPrice_title"></span>（以下单位均为：元）</p>
			<p class="todayPrice_p2">
				<span>鹅价</span>
				<span>可交易总鹅</span>
			</p>
			<p class="todayPrice_p3">
				<span class="todayPrice_num" contenteditable="true">未填写</span>
				<span class="todayPrice_sum" ></span>
	            <span class="todayPrice_sure">保存</span>
            </p>
		</div>
     </div> 
     <div class="mask"></div>
     <div id="pageToolbar1"></div>
     <div id="pageToolbar2" class=" hidden"></div>
     <div id="pageToolbar3" class=" hidden"></div>
	</div>
</div>
</div>

</div>
			<!-- footer -->
			 <!-- Transport_footer -->
  <div id="Transport_footer" style="margin-top: -39px;">
	        <div id="eoulu-copy-out" style="height:40px;width:calc(100% - 2px);position:static">
	            <div style="width:100%;height:5px;"></div>
	            <div id="eoulu-copy" style="width:100%;height:35px;font-size:12px;color:#888;line-height:35px;z-index: 2;">
	                <hr style="height:1px;color:#999;width: calc(100% - 3px);" />
	                <div style="width:100%;text-align:center;display:inline-block;">Copyright  ©&nbsp;<span class="YEAR">2018</span>&nbsp<a href="http://www.eoulu.com/h-col-268.html" class="EHref" target="_blank" style="color:blue;">Eoulu</a> Tech. Co.,Ltd.</div>
	            </div>
	        </div> 
</div> 
	<!-- Transport_wrapper结束 -->
</div>
   <script src="js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="js/organictabs.jquery.js"></script>
    <script type="text/javascript" src="js/query.js"></script>
    <script type="text/javascript" src="js/paging.js"></script>
	<script src="js/msgbox_unload.js"></script>
	<script src="js/msgbox.js"></script>
    <script type="text/javascript" src="js/equityMarket.js"></script>
    <script type='text/javascript'>
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
    	$('.eou-container-l a').after('').after('<h2>futureE</h2>') */
        $(function() {
    
            $("#wrap_table").organicTabs();
            
            $("#example-two").organicTabs({
                "speed": 200
            });
    
        });
       $(function(){
    	   $('.nav-body').css({
    		   'min-height':'720px'
    	   })
       });
      
        
        
        
    </script>
</body>

</html>