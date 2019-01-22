//点击确定刷新页面
/*$(document).on("click", "#mb_btn_ok", function() {
	window.location.reload();
});*/

/*function toFixed (d, n) {
    var s = n + "";
    if (!d) d = 0;
    if (s.indexOf(".") == -1) s += ".";
    s += new Array(d + 1).join("0");
    if (new RegExp("^(-|\\+)?(\\d+(\\.\\d{0," + (d + 1) + "})?)\\d*$").test(s)) {
        var s = "0" + RegExp.$2, pm = RegExp.$1, a = RegExp.$3.length, b = true;
        if (a == d + 2) {
            a = s.match(/\d/g);
            if (parseInt(a[a.length - 1]) > 4) {
                for (var i = a.length - 2; i >= 0; i--) {
                    a[i] = parseInt(a[i]) + 1;
                    if (a[i] == 10) {
                        a[i] = 0;
                        b = i != 1;
                    } else break;
                }
            }
            s = a.join("").replace(new RegExp("(\\d+)(\\d{" + d + "})\\d$"), "$1.$2");
 
        }
        if (b) s = s.substr(1);
        return (pm + s).replace(/\.$/, "");
    }
    return this + "";

}*/


//我的鹅数据
var ajaxGetData_myshares = function(Tab,CurrentPage){
	$.ajax({
		type:'get',
		url:'GooseMarket',
		data:{
			LoadType:'data',
			Tab : Tab, //分为one和all
			CurrentPage  : CurrentPage  , //1或者其他
		},
		dataType:'JSON',
		success:function(data){
			appendStr_myshares(data);
		}
	})
};
var appendStr_myshares =function(data){
			var CurrentPage = data.currentPage;
			var AllPage = data.pageCount;
			var str = '';
			for(var i= 1;i<data.data.length;i++){
				var response = data.data[i];
				str += '<div class="Myshares_list">'+
		                    '<a class="tableCell tablecell_id" href="#" id="'+response.ID+'">'+(i+(CurrentPage-1)*10)+'</a>'+
		                    '<a class="tableCell tablecell_name" href="#">'+response.Name+'</a>'+
		                    '<a class="tableCell tablecell_department" href="#">'+response.Department+'</a>'+
		                    '<a class="tableCell tablecell_shares" href="#">'+response.PossessionNum+'</a>'+
		                    '<a class="tableCell tablecell_change" href="#">'+response.TradableNum+'</a>'+
		                    '<a class="tableCell tablecell_time" href="#">'+response.OperatingTime+'</a>'+
		                '</div>';
			}
			$('.Myshares_table').html('').append(str);
	        $('#pageToolbar2').addClass('hidden');
	        $('#pageToolbar3').addClass('hidden'); 
	        $('#pageToolbar1').removeClass('hidden');  
	        $('#pageToolbar1').empty();
	        $('#pageToolbar2').empty();
	        $('#pageToolbar3').empty();
		    $('#pageToolbar1').Paging({pagesize:"1",current:CurrentPage,count:AllPage,toolbar:true});
}
//贸易鹅数据
var ajaxGetData_Tradeshares = function(CurrentPage){
	$.ajax({
		type:'get',
		url:'GooseTrade',
		data:{
			DataType  :'page',
			CurrentPage  : CurrentPage  , 
		},
		dataType:'JSON',
		success:function(data){
			appendStr_Tradeshares(data);
		}
	})
};
var appendStr_Tradeshares =function(data){
			var CurrentPage = data.currentPage;
			var AllPage = data.pageCount;
			var str = '';
			for(var i= 1;i<data.data.length;i++){
				if(data.data[i].State == '买入'){
					data.data[i].change = '我要卖';
				}else if(data.data[i].State == '卖出'){
					data.data[i].change = '我要买';
				}else{
					data.data[i].change = '不可操作';
				}
				var response = data.data[i];
				str += '<div class="Tradeshares_list">'+
                            '<a class="tableCell tablecell_id" href="#" id="'+response.ID+'">'+(i+(CurrentPage-1)*10)+'</a>'+
                            '<a class="tableCell tablecell_name" href="#">'+response.Name+'</a>'+
                            '<a class="tableCell tablecell_State" href="#">'+response.State+'</a>'+
                            '<a class="tableCell tablecell_Number" href="#">'+response.Number+'</a>'+
                            '<a class="tableCell tablecell_OperatingTime" href="#">'+response.OperatingTime+'</a>'+
                            '<a class="tableCell tablecell_change operate" href="#" change="'+response.change+'">'+response.change+'</a>'+
                            '<a class="tableCell tablecell_OperatingRecord" href="#">点击查看</a>'+
                        '</div>';
			}
				$('.Tradeshares_table').html('').append(str);
				for(var j= 0;j<$('.Tradeshares_list').length;j++){
					if($('.Tradeshares_list').eq(j).find('.tablecell_change').text() == '我要卖'){
						$('.Tradeshares_list').eq(j).find('.tablecell_change').html('').html('<span class="bg_orange">我要卖</span>');
					}else if($('.Tradeshares_list').eq(j).find('.tablecell_change').text() == '我要买'){
						$('.Tradeshares_list').eq(j).find('.tablecell_change').html('').html('<span class="bg_green">我要买</span>');
					}else{
						$('.Tradeshares_list').eq(j).find('.tablecell_change').html('').html('<span class="bg_Prohibit">不可操作</span>');
					}
				}
				
				
			 	$('#pageToolbar1').addClass('hidden');
		        $('#pageToolbar3').addClass('hidden'); 
		        $('#pageToolbar2').removeClass('hidden'); 
		        $('#pageToolbar1').empty();
		        $('#pageToolbar2').empty();
		        $('#pageToolbar3').empty();
		        $('#pageToolbar2').Paging({pagesize:"1",current:CurrentPage,count:AllPage,toolbar:true});
}
//统计
var ajaxGetData_Countshares = function(){
		$.ajax({
			type:'get',
			url:'GoosePrice',
			dataType:'JSON',
			success:function(data){
				//只有1条数据
					var str = '';
					var response = data[1];
					str = '<div class="Countshares_list">'+
	                            '<a class="tableCell tablecell_TodayPrice" href="# id="'+response.ID+'">'+response.TodayPrice+'</a>'+
	                            '<a class="tableCell tablecell_BuyToday" href="#">'+response.BuyToday+'</a>'+
	                            '<a class="tableCell tablecell_SellToday" href="#">'+response.SellToday+'</a>'+
	                            '<a class="tableCell tablecell_OperatingTime" href="#">'+response.OperatingTime+'</a>'+
	                            '<a class="tableCell tablecell_MonthCount" href="#"><img src="./image/month.png" alt="" /></a>'+
	                        '</div>';
					
					$('.Countshares_table').html('').append(str); 
			        $('#pageToolbar1').empty();
			        $('#pageToolbar2').empty();
			        $('#pageToolbar3').empty();
			}
		})
};
var ajaxGetData_MonthCountshares = function(CurrentPage){
		$.ajax({
			type:'get',
			url:'GooseDeal',
			data:{
				CurrentPage:CurrentPage
			},
			dataType:'JSON',
			success:function(data){
				appendStr_MonthCountshares(data);
			}
		})
};
var appendStr_MonthCountshares =function(data){
			var CurrentPage = data.currentPage;
			var AllPage = data.pageCount;
			var str = '';
			for(var i= 1;i<data.data.length;i++){
				var response = data.data[i];
				str += '<div class="MonthCount_list">'+
		                    '<a class="tableCell tablecell_id" href="# id="'+response.ID+'">'+(i+(CurrentPage-1)*10)+'</a>'+
		                    '<a class="tableCell tablecell_Date" href="#">'+response.Date+'</a>'+
		                    '<a class="tableCell tablecell_TodayPrice" href="#">'+response.TodayPrice+'</a>'+
		                    '<a class="tableCell tablecell_BuyToday" href="#">'+response.BuyToday+'</a>'+
		                    '<a class="tableCell tablecell_SellToday" href="#">'+response.SellToday+'</a>'+
		                    '<a class="tableCell tablecell_volumeOfTotal" href="#">'+response.volumeOfTotal+'</a>'+
		                    '<a class="tableCell tablecell_OperatingTime" href="#">'+response.OperatingTime+'</a>'+
		                '</div>';
			}
				$('.MonthCount_table').html('').append(str);
				$('#pageToolbar1').addClass('hidden');
		        $('#pageToolbar2').addClass('hidden'); 
		        $('#pageToolbar3').removeClass('hidden');  
		        $('#pageToolbar1').empty();
		        $('#pageToolbar2').empty();
		        $('#pageToolbar3').empty();
		        $('#pageToolbar3').Paging({pagesize:"1",current:CurrentPage,count:AllPage,toolbar:true});
}

var changePage = function(currentPage){
	var whichPage = $('#wrap_table .nav  a.current').text();
		if(whichPage == '我的鹅'){
			ajaxGetData_myshares('all',currentPage);
		}else if(whichPage == '贸易鹅'){
			ajaxGetData_Tradeshares(currentPage);
		}else if(whichPage == '统计鹅'){
			ajaxGetData_Countshares();
		}else{
			ajaxGetData_MonthCountshares(currentPage);
		};
}
//跳页点击事件
$(document).on('click','.Gotojump',function(){
		var currentPage = Number($('.ui-paging-count').val());
		changePage(currentPage);
		$(".list-wrap").css('height','auto');
})
$(document).on('click','.pagenum',function(){
		var currentPage = Number($(this).text());
		changePage(currentPage);
		$(".list-wrap").css('height','auto');
})
$(document).on('click','.js-page-prev',function(){
		var currentPage = Number($('.focus').text());
		changePage(currentPage);
		$(".list-wrap").css('height','auto');
})
$(document).on('click','.js-page-next',function(){
		var currentPage = Number($('.focus').text());
		changePage(currentPage);
		$(".list-wrap").css('height','auto');
})


//贸易鹅需要默认显示
$(function(){
	/*ajaxGetData_Tradeshares(1);*/
	$.ajax({
		type:'get',
		url:'GooseTrade',
		data:{
			DataType:'buy',
		},
		dataType:'json',
		success:function(data){
			var name = data.name;
			$('.equityMarketpart').attr('name',name);
		}
	});
	$('.Tradeshares').click();
	$(".list-wrap").css('height','auto');

	var EmailAccount = $('.m-admin .u-span').text();
	/*if((EmailAccount ==  'luwenbo@eoulu.com' ) || (EmailAccount == 'liuyanan@eoulu.com')){*/

	if( EmailAccount ==  'luwenbo@eoulu.com' ){
		$('.todayPrice_num').attr('contenteditable',true);
		$('.todayPrice_sure').show();
	}else{
		$('.todayPrice_num').attr('contenteditable',false);
		$('.todayPrice_sure').hide();
	}
});
var MysharesClick = function(pageNum){
	$('#wrap_table ul li .Countshares').removeClass('orange');
	$('#MonthCount').css('display','none');
	$('.addInfo').removeClass('hidden');
	$('.buy').addClass('hidden');
	$('.sell').addClass('hidden');
	$('.CountMonth').addClass('hidden');
	$('.todayPrice').hide();
    ajaxGetData_myshares('all',pageNum);
    var EmailAccount = $('.m-admin .u-span').text();
	if((EmailAccount ==  'luwenbo@eoulu.com' ) || (EmailAccount == 'liuyanan@eoulu.com')){
		$('.choose .addInfo').show();
	}else{
		$('.choose .addInfo').hide();
	}
}
//点击切换
$('.Myshares').click(function(){
	MysharesClick(1);
		
});
var TradesharesClick = function(pageNum){
	$('#wrap_table ul li .Countshares').removeClass('orange');
	$('#MonthCount').css('display','none');
	$('.addInfo').addClass('hidden');
	$('.buy').removeClass('hidden');
	$('.sell').removeClass('hidden');
	$('.CountMonth').addClass('hidden');

	//今日鹅价
	var date = new Date();
	var Y = date.getFullYear();
	var M = date.getMonth()+1;
	var D = date.getDate();
	var time = Y+'年'+M+'月'+D+'日';
	$('.todayPrice .todayPrice_title').text('').text(time);
	$.ajax({
		type:'get',
		url:'GoosePrice',
		dataType:'JSON',
		success:function(data){
			if(data.length == 1){
				$('.todayPrice_num').text('').text('未填写鹅价'); 
				$('.todayPrice_sum').text('').text('0'); 
			}else{
				var TodayPrice = data[1].TodayPrice ;
				var Total = data[1].Total;
				$('.todayPrice').attr('total',Total);
				var todayPrice_sum = TodayPrice * Total +'';
				if(todayPrice_sum.indexOf('.') > -1){
					todayPrice_sum = todayPrice_sum.toString().replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
				}else{
					todayPrice_sum = todayPrice_sum.toString().replace(/(\d{1,3})(?=(\d{3})+$)/g,'$1,');
				}
				$('.todayPrice_num').text('').text(TodayPrice);
				$('.todayPrice_sum').text('').text(todayPrice_sum); 
			}
			$('.todayPrice').show();
		}
	})
        ajaxGetData_Tradeshares(pageNum);
}
$('.Tradeshares').click(function(){
	TradesharesClick(1);
});
$('.Countshares').click(function(){
	$('#MonthCount').css('display','none');
	$('.addInfo').addClass('hidden');
	$('.buy').addClass('hidden');
	$('.sell').addClass('hidden');
	$('.CountMonth').removeClass('hidden');
	$('.todayPrice').hide();
	ajaxGetData_Countshares();
});

$(document).on('click','.Countshares_list .tablecell_MonthCount',function(){
	$('.nav .last a.MonthCount').click();
	$('#wrap_table ul li .Countshares').addClass('orange');
	ajaxGetData_MonthCountshares(1);
})

//添加
$('.addInfo').click(function(){
		$('.mask').slideDown(300);
		$('.addShare_Model').slideDown(300);
})

$('.addmodel_close,.addmodel_cancel').click(function(){
	$('.mask').slideUp(300);
	$('.addShare_Model').slideUp(300);
});
$('.addmodel_submit').click(function(){
	var name = $('.addShare_Model .yourName input').val();
	var yourDepartment = $('.addShare_Model .yourDepartment select').find('option:selected').attr('value');
	var yourShares = $('.addShare_Model .yourShares input').val();
	var yourChangeshares = $('.addShare_Model .yourChangeshares input').val();
	$.ajax({
		type:'post',
		url:'GooseMarket',
		data:{
			Type:'add',
			Name:name,
			Department:yourDepartment,
			PossessionNum:yourShares,
			TradableNum:yourChangeshares
		},
		dataType:'JSON',
		beforeSend:function(XMLHttpRequest){  
			$('.addmodel_submit').attr('disabled',true) ;
        },  
		success:function(data){
			if(data.status == true){
				$.MsgBox.Alert('提示',data.message);
			}else{
				$.MsgBox.Alert('提示',data.message)
			}
			$('.mask').slideUp(300);
			$('.addShare_Model').slideUp(300);
			$('.Myshares').click();
		}, 
        complete:function(XMLHttpRequest,textStatus){  
        	$('.addmodel_submit').attr('disabled',false) ;
        }
	})
})


//修改
	$(document).on('click','.Myshares_table .Myshares_list .tablecell_id',function(){
		 var pageNum = $('#pageToolbar1 li.focus').text();
		 var EmailAccount = $('.m-admin .u-span').text();
			if((EmailAccount ==  'luwenbo@eoulu.com' ) || (EmailAccount == 'liuyanan@eoulu.com')){
				
			}else{
				return;
			}
		
		var id = $(this).attr('id');
		var yourname = $(this).parent().find('.tablecell_name').text();
		var department = $(this).parent().find('.tablecell_department').text();
		var shares = $(this).parent().find('.tablecell_shares').text();
		var change = $(this).parent().find('.tablecell_change').text();
		$('.editShare_Model').find('.editmodel_title ').attr('id',id);
		$('.editShare_Model').find('.editmodel_title ').attr('pageNum',pageNum);
		$('.editShare_Model').find('.yourName input').val(yourname);
		$('.editShare_Model').find('.yourDepartment select').find('option[value="'+department+'"]').attr('selected',true);
		$('.editShare_Model').find('.yourShares input').val(shares);
		$('.editShare_Model').find('.yourChangeshares input').val(change);
		$('.mask').slideDown(300);
		$('.editShare_Model').slideDown(300);
})
$('.editmodel_close,.editmodel_cancel').click(function(){
	$('.mask').slideUp(300);
	$('.editShare_Model').slideUp(300);
});
$('.editmodel_submit').click(function(){
	var pageNum = $('.editShare_Model').find('.editmodel_title ').attr('pageNum');
	var id = $('.editShare_Model').find('.editmodel_title ').attr('id');
	var name = $('.editShare_Model .yourName input').val();
	var yourDepartment = $('.editShare_Model .yourDepartment select').find('option:selected').attr('value');
	var yourShares = $('.editShare_Model .yourShares input').val();
	var yourChangeshares = $('.editShare_Model .yourChangeshares input').val();
	$.ajax({
		type:'post',
		url:'GooseMarket',
		data:{
			Type:'update',
			ID : id,
			Name : name,
			Department : yourDepartment,
			PossessionNum : yourShares,
			TradableNum : yourChangeshares
		},
		dataType:'JSON',
		beforeSend:function(XMLHttpRequest){  
			$('.editmodel_submit').attr('disabled',true) ;
        },  
		success:function(data){
			if(data.status == true){
				$.MsgBox.Alert('提示',data.message);
			}else{
				$.MsgBox.Alert('提示',data.message);
			}

			$('.mask').slideUp(300);
			$('.editShare_Model').slideUp(300);
			MysharesClick(pageNum);
		}, 
        complete:function(XMLHttpRequest,textStatus){  
        	$('.editmodel_submit').attr('disabled',false) ;
        }
	})
})
//这是挂出的buy
$('.buy').click(function(){
	var todayPrice_num = $('.todayPrice_num').text();
	$('.buymodel_title').attr('todayPrice_num',todayPrice_num);
	$('.mask').slideDown(300);
	$('.buy_model .yourShares input').val('');
	$('.buy_model .yourChangeshares input').val('');
	$.ajax({
		type:'get',
		url:'GooseTrade',
		data:{
			DataType:'buy',
		},
		dataType:'json',
		success:function(data){
			var name = data.name;
			var canBuyNum = data.canBuyNum;
			console.log(name,canBuyNum)
			$('.buy_model .yourName input').val(name);
			$('.buy_model .SharesNumber input').val(canBuyNum);
		}
	})
	$('.buy_model').slideDown(300);
	$('.buy_model').addClass('virtual_buy');
});
$('.buymodel_close,.buymodel_cancel').click(function(){
	$('.mask').slideUp(300);
	$('.buy_model').slideUp(300);
});
$('.sell').click(function(){
	var todayPrice_num = $('.todayPrice_num').text();
	$('.sellmodel_title').attr('todayPrice_num',todayPrice_num);

	$('.sell_model .yourShares input').val('');
	$('.sell_model .yourChangeshares input').val('');
	//获取账号和卖出数
		$.ajax({
			type:'get',
			url:'GooseTrade',
			data:{
				DataType:'sell',
			},
			dataType:'json',
			success:function(data){
				var name = data.name;
				var canSellNum = data.tradableNum;
				console.log(name,canSellNum);
				$('.sell_model .yourName input').val(name);
				$('.sell_model .SharesNumberSell input').val(canSellNum);
			}
		})

	$('.mask').slideDown(300);
	$('.sell_model').slideDown(300);
	$('.sell_model').addClass('virtual_sell');
});
$('.sellmodel_close,.sellmodel_cancel').click(function(){
	$('.mask').slideUp(300);
	$('.sell_model').slideUp(300);
});
//失去焦点
/*$(document).on('blur','.buymodel_content .yourShares input',function(){
	var SharesNumber = Number($('.buymodel_content .SharesNumber input').val());
	var number = Number($(this).val());
	if(SharesNumber < number){$.MsgBox_Unload.Alert('提示','超出您可购买的鹅数'); return};
	var yourChangeshares = Number($(this).val())*(Number($('.buymodel_title').attr('todayPrice_num')));
	$('.buymodel_content .yourChangeshares input').val(yourChangeshares);
	var yourChangeshares2 = $('.buymodel_content .yourChangeshares input').val();
	if( yourChangeshares2 == 'NaN'){
		$.MsgBox_Unload.Alert('提示','请您回贸易鹅填写今日鹅价');
		$('.mask').slideUp(500);
		$('.buy_model').slideUp(500);
	}
})*/
$(document).on('blur','.sellmodel_content .yourShares input',function(){
	var SharesNumberSell = Number($('.sellmodel_content .SharesNumberSell input').val());
	var number = Number($(this).val());
	if(SharesNumberSell < number){$.MsgBox_Unload.Alert('提示','超出对方求购鹅数'); return};
	
	
	
	if( Number($('.sellmodel_title').attr('todayPrice_num')) == 'NaN'){$.MsgBox_Unload.Alert('提示','今日鹅价还未公布，请稍候再来~'); return};
	var todayPrice_num = Number($('.sellmodel_title').attr('todayPrice_num'))*1000;
	var yourChangeshares = Number($(this).val()*(todayPrice_num)/1000);
	$('.sellmodel_content .yourChangeshares input').val(yourChangeshares);
})
$(document).on('blur','.buymodel_content .yourShares input',function(){
	if( Number($('.buymodel_title').attr('todayPrice_num')) == 'NaN'){$.MsgBox_Unload.Alert('提示','今日鹅价还未公布，请稍候再来~'); return};
	var todayPrice_num = Number($('.buymodel_title').attr('todayPrice_num'))*1000;
	var yourChangeshares = Number($(this).val()*(todayPrice_num)/1000);
	$('.buymodel_content .yourChangeshares input').val(yourChangeshares);
})
/*$(document).on('blur','.sellmodel_content .yourShares input',function(){
	if( Number($('.sellmodel_title').attr('todayPrice_num')) == 'NaN'){$.MsgBox_Unload.Alert('提示','今日鹅价还未公布，请稍候再来~'); return};
	var yourChangeshares = Number($(this).val())*(Number($('.sellmodel_title').attr('todayPrice_num')));
	$('.sellmodel_content .yourChangeshares input').val(yourChangeshares);
})*/
//修改鹅价

/*url:   GoosePrice   post
参数：TodayPrice
返回：键值对json
status    true/false  作为是否更新数据的依据
message    文本  用于提示信息 */
$('.todayPrice_sure').click(function(){
	var TodayPrice = $('.todayPrice_num').text();
		$.ajax({
			type:'post',
			url:'GoosePrice',
			dataType:'JSON',
			data:{
				TodayPrice:TodayPrice
			},
			success:function(data){
				if(data.status == true){
					$.MsgBox_Unload.Alert('提示',data.message);
				}else if(data.status == false){
					$.MsgBox_Unload.Alert('提示',data.message);
				}
			}
		})
})
//点击实际的买或卖（列表中的）
$(document).on('click','.Tradeshares_list .tablecell_change',function(){
	var pageNum =  $('#pageToolbar2 li.focus').text();
	var todayPrice_num = $('.todayPrice_num').text();
	$('.buymodel_title').attr('todayPrice_num',todayPrice_num);
	$('.sellmodel_title').attr('todayPrice_num',todayPrice_num);
	var ID = $(this).parent().find('.tablecell_id').attr('id');
	$('.buymodel_title').attr('id',ID);
	$('.sellmodel_title').attr('id',ID);
	$('.buymodel_title').attr('pageNum',pageNum);
	$('.sellmodel_title').attr('pageNum',pageNum);
	
	var name = $('.equityMarketpart').attr('name');
	var Number = $(this).parent().find('.tablecell_Number').text();
	if($(this).attr('change') == '我要买'){
		$('.mask').slideDown(300);
		$('.buy_model .yourName input').val('').val(name);
		$('.buy_model .SharesNumber input').val('').val(Number);
		$('.buy_model .yourShares input').val('');
		$('.buy_model .yourChangeshares input').val('');
		$('.buy_model').slideDown(300);
		$('.buy_model').removeClass('virtual_buy');
		
	}else if($(this).attr('change') == '我要卖'){
		$('.mask').slideDown(300);
		$('.sell_model .yourName input').val('').val(name);
		$('.sell_model .SharesNumberSell input').val(Number);
		$('.sell_model .yourShares input').val('');
		$('.sell_model .yourChangeshares input').val('');
		$('.sell_model').slideDown(300);
		$('.sell_model').removeClass('virtual_sell');
		
		//求购鹅数
		$('.sell_model .SharesNumberSell span.SharesNumberSell_span1').text('').text('求购鹅数');
		//您有的可交易鹅数
		$.ajax({
			type:'get',
			url:'GooseTrade',
			data:{
				DataType:'sell',
			},
			dataType:'json',
			success:function(data){
				var name = data.name;
				var canSellNum = data.tradableNum;
				console.log(name,canSellNum);
				$('.sell_model .SharesNumberSell input').after('<span class="canSellNum">您拥有的可交易鹅数是'+canSellNum+'</span>');
			}
		})
	}
})	

//买入提交
$('.buymodel_submit').click(function(){
	if($(this).parent().parent().hasClass('virtual_buy')){
		//挂出提交
		var SharesNumber = $('.buy_model .SharesNumber input').val();
		if(SharesNumber == '无信息'){
				$.MsgBox.Alert('提示','您没有在我的鹅中录入信息，请先录入~');	
				$('.Myshares').click();
				return;
		}
		var Name = $('.buy_model .yourName input').val();
		var yourShares = $('.buy_model .yourShares input').val();
		$.ajax({
			type:'post',
			url:'GooseTrade',
			data:{
				OperateType:'buy',
				Name:Name,
				Number:yourShares
			},
			dataType:'JSON',
			beforeSend:function(XMLHttpRequest){  
				$('.buymodel_submit').attr('disabled',true) ;
	        },  
			success:function(data){
				if(data.status == true){
					$.MsgBox.Alert('提示',data.message);
				}else if(data.status == false){
					$.MsgBox.Alert('提示',data.message);
				}
				$('.mask').slideUp(300);
				$('.buy_model').slideUp(300);
				$('.Tradeshares').click();
			}, 
	        complete:function(XMLHttpRequest,textStatus){  
	        	$('.buymodel_submit').attr('disabled',false) ;
	        }  
		})
	}else{
		//实际买提交

		var pageNum = $('.buy_model').find('.buymodel_title').attr('pageNum');
		var ID = $('.buy_model').find('.buymodel_title').attr('id');
		var Name =  $('.buy_model').find('.yourName input').val();
		var number =  $('.buy_model').find('.yourShares input').val();
		$.ajax({
			type:'post',
			url:'GooseDeal',
			data:{
				DealType:'realBuy',
				ID:ID,
				Name :Name , //当前帐号姓名 ！！！ 不是记录姓名（可在我的鹅读取）
				Number:number,
			},
			dataType:'JSON',
			beforeSend:function(XMLHttpRequest){  
				$('.buymodel_submit').attr('disabled',true) ;
	        },  
			success:function(data){
				if(data.status == true){
					$.MsgBox.Alert('提示',data.message);
				}else{
					$.MsgBox.Alert('提示',data.message);
				}
				$('.mask').slideUp(300);
				$('.buy_model').slideUp(300);
				$('.Tradeshares').click();
			},
	        complete:function(XMLHttpRequest,textStatus){  
	        	$('.buymodel_submit').attr('disabled',false) ;
	        } 
		});
	}
	
})	
//卖出提交
$('.sellmodel_submit').click(function(){
	if($(this).parent().parent().hasClass('virtual_sell')){
		var SharesNumberSell = $('.sell_model .SharesNumberSell input').val();
		if(SharesNumberSell == '无信息'){
				$.MsgBox.Alert('提示','您没有在我的鹅中录入信息，请先录入~');	
				$('.Myshares').click();
				return;
		}
		var Name = $('.sell_model .yourName input').val();
		var yourShares = $('.sell_model .yourShares input').val();
		$.ajax({
			type:'post',
			url:'GooseTrade',
			data:{
				OperateType:'sell',
				Name:Name,
				Number:yourShares
			},
			dataType:'JSON',
			beforeSend:function(XMLHttpRequest){  
				$('.sellmodel_submit').attr('disabled',true) ;
	        },  
			success:function(data){
				if(data.status == 'true'){
					$.MsgBox.Alert('提示',data.message);
				}else if(data.status == 'false'){
					$.MsgBox.Alert('提示',data.message);
				}
				$('.mask').slideUp(300);
				$('.sell_model').slideUp(300);
				$('.Tradeshares').click();
			},
	        complete:function(XMLHttpRequest,textStatus){  
	        	$('.sellmodel_submit').attr('disabled',false) ;
	        } 
		})
	}else{

		var pageNum = $('.sell_model').find('.sellmodel_title').attr('pageNum');
		var ID = $('.sell_model').find('.sellmodel_title').attr('id');
		var Name =  $('.sell_model').find('.yourName input').val();
		var number =  $('.sell_model').find('.yourShares input').val();
		$.ajax({
			type:'post',
			url:'GooseDeal',
			data:{
				DealType:'realSell',
				ID:ID,
				Name :Name , //当前帐号姓名 ！！！ 不是记录姓名（可在我的鹅读取）
				Number:number,
			},
			dataType:'JSON',
			beforeSend:function(XMLHttpRequest){  
				$('.sellmodel_submit').attr('disabled',true) ;
	        },  
			success:function(data){
				if(data.status == true){
					$.MsgBox.Alert('提示',data.message);
				}else{
					$.MsgBox.Alert('提示',data.message);
				}
				$('.mask').slideUp(300);
				$('.sell_model').slideUp(300);
				$('.Tradeshares').click();
			},
	        complete:function(XMLHttpRequest,textStatus){  
	        	$('.sellmodel_submit').attr('disabled',false) ;
	        } 
		});	
	}
})
//贸易鹅交易记录
$(document).on('click','.tablecell_OperatingRecord',function(){
	var TradeID = $(this).parent().find('.tablecell_id').attr('id');
	var FirstName = $(this).parent().find('.tablecell_name').text();
	$.ajax({
		type:'get',
		url:'GooseDealRecord',
		dataType:'JSON',
		data:{
			TradeID:TradeID
		},
		success:function(data){
			if(data.length < 2){
				$.MsgBox_Unload.Alert('提示','当前条还没有交易记录');
				return;
			}
			if(data[1].OperateType == "买入"){
				TradeRecordState = "卖出";
			}else if(data[1].OperateType == "卖出"){
				TradeRecordState = "买入";
			};
			var str = '';
			for(var i= 1;i<data.length;i++){
				
				var response = data[i];
				str += '<div class="TradeRecord_list">'+
		                    '<a class="tableCell TradeRecord_id" href="#" id="'+response.ID+'">'+(i)+'</a>'+
		                    '<a class="tableCell TradeRecord_state" href="#">'+TradeRecordState+'</a>'+
		                    '<a class="tableCell TradeRecord_name" href="#">'+response.Name+'</a>'+
		                    '<a class="tableCell TradeRecord_number" href="#">'+response.Number+'</a>'+
		                    '<a class="tableCell TradeRecord_OperatingTime" href="#">'+response.OperatingTime+'</a>'+
		                    '<a class="tableCell TradeRecord_Remark" href="#">'+response.Name+data[1].OperateType+response.Number+'鹅</a>'+
		                '</div>';
			}
			$('.TradeRecord_table').html('').append(str);
			if($('.TradeRecord_list .TradeRecord_state').eq(0).text() == '卖出'){
				$('.TradeRecordTitle_name').text('').text('买方');
			}else{
				$('.TradeRecordTitle_name').text('').text('卖方');
			}

			$('.mask').slideDown(300);
			$('.TradeRecord_model').slideDown(300);
		}
		
	})
});
$('.TradeRecordmodel_close').click(function(){
	$('.mask').slideUp(300);
	$('.TradeRecord_model').slideUp(300);
});

//修改今日鹅价
$(document).on('keyup','.todayPrice_num',function(){
				var TodayPrice = Number($('.todayPrice_num').text()); 
				var Total = $('.todayPrice').attr('total');
				var todayPrice_sum = TodayPrice * Total +'';
				if(todayPrice_sum.indexOf('.') > -1){
					todayPrice_sum = todayPrice_sum.toString().replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
				}else{
					todayPrice_sum = todayPrice_sum.toString().replace(/(\d{1,3})(?=(\d{3})+$)/g,'$1,');
				}
				$('.todayPrice_sum').text('').text(todayPrice_sum); 
		
})