//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});
var showTableData = function(CurrentPage){
	$.ajax({
		type:'get',
		url:'SaleFee',
		dataType:'JSON',
		data:{
			LoadType:'data',
			CurrentPage:CurrentPage
		},
		success:function(data){
			console.log(data)
			var currentPage  = data.currentPage;
			var pageCount = data.pageCount;
			var str = '';
			console.log(data.data.length)
			for(var i = 1;i<data.data.length;i++){
				var ID = data.data[i].ID;
				var CustomerName = data.data[i].CustomerName;
				var ServiceItem = data.data[i].ServiceItem;
				var VisitTime = data.data[i].VisitTime;
				var SalesMan = data.data[i].SalesMan;
				var Engineer = data.data[i].Engineer;
				var Fee = data.data[i].Fee;
				var OperatingTime = data.data[i].OperatingTime;

				var IsPass = data.data[i].IsPass;
				var IsPass_span = '';
				if(IsPass == '已通过'){
					IsPass_span = '<span class="green">'+IsPass+'</span>';
				}else if(IsPass == '未通过'){
					IsPass_span = '<span class="noPass">'+IsPass+'</span>';
				}else{
					IsPass_span = '<span class="noDeal">'+IsPass+'</span>';
				}
				
				
				str += '<tr class="tbody_tr">'+
							'<td class="editBtn" id = "'+ID+'" >'+(i+(currentPage-1)*10)+'</td>'+
							'<td class="customer" title = "'+CustomerName+'" >'+CustomerName+'</td>'+
							'<td class="service" title = "'+ServiceItem+'" >'+ServiceItem+'</td>'+
							'<td class="visitDate">'+VisitTime+'</td>'+
							'<td class="saleMan">'+SalesMan+'</td>'+
							'<td class="engineer">'+Engineer+'</td>'+
							'<td class="passBtn">'+IsPass_span+'</td>'+
							'<td class="cost">'+Fee+'</td>'+
							'<td class="Sender" style="display:none">'+data.data[i].Sender+'</td>'+
							'<td class="Recipient" style="display:none">'+data.data[i].Recipient+'</td>'+
							'<td class="CopyList" style="display:none">'+data.data[i].CopyList+'</td>'+
						'</tr>';
			}
			$('.costTable tbody').html('').append(str);
			$('#pageToolbar1').empty();
		    $('#pageToolbar1').Paging({pagesize:"1",current:currentPage,count:pageCount,toolbar:true});
			
		}
	})
}

//页面数据加载
$(function(){
	showTableData(1);
	$.ajax({
		type:'post',
		url:'PageVisit',
		data:{
			PageName: '拜访申请',
			Department: '销售部'
		},
		success:function(data){
			console.log(data)
		}
	})
})
//跳页
$(document).on('click','.js-page-prev, .pagenum, .js-page-next, .Gotojump',function(){
	var page = $('.ui-paging-count').val();
	showTableData(page);
})
$('.add_btn').click(function(){
	var Send = $('.m-admin .u-span').text();
	$('.sendApplication_Email1 .Send input').val('').val(Send);
	$('.mask').slideDown(300);
	$('.addBox').slideDown(300);
})
$(document).on('click','.editBtn',function(){
	var id = $(this).attr('id');
    $('.editBox .editBox_title ').attr('id',id);
	var tr = $(this).parent();
	$('.editBox .CustomerName input').val('').val(tr.find('.customer').text());
	$('.editBox .ServiceItem input').val('').val(tr.find('.service').text());
	$('.editBox .VisitTime input').val('').val(tr.find('.visitDate').text());
	$('.editBox .Engineer input').val('').val(tr.find('.engineer').text());
	$('.editBox .Send input').val('').val(tr.find('.Sender').text());
	var SalesMan = tr.find('.saleMan').text();
	$('.editBox .SalesMan select option[text = "'+ SalesMan +'"]').attr('selected','selected');
	var Recipient =  tr.find('.Recipient').text();
	var CopyList =  tr.find('.CopyList').text();
	if(Recipient.slice(-3) =='com'){
		Recipient = Recipient + ';' ;
	}
	if(CopyList.slice(-3) =='com'){
		CopyList = CopyList + ';' ;
	}
	$('.editBox #Tolist2 ').text('').text(Recipient);
	$('.editBox #Copylist2 ').text('').text(CopyList);
	
	
	$('.mask').slideDown(300);
	$('.editBox').slideDown(300);
})
$('.add_cancle,.add_close').click(function(){
	$('.mask').slideUp(300);
	$('.addBox').slideUp(300);
})
$(document).on('click','.edit_cancle,.edit_close',function(){
	$('.mask').slideUp(300);
	$('.editBox').slideUp(300);
})
$(document).on('click','.add_ok',function(){
	var CustomerName =   $('.addBox .CustomerName input').val();
	var ServiceItem =  $('.addBox .ServiceItem input').val();
	var VisitTime = $('.addBox .VisitTime input').val();
	var SalesMan = $('.addBox .SalesMan select option:selected').text();
	var Engineer = $('.addBox .Engineer input').val();
	
	
	var To = $('.sendApplication_Email1 .To #Tolist').text();
	var Copyto = $('.sendApplication_Email1 .Copyto #Copylist').text();
	var Password = $('.sendApplication_Email1 .Password .password').text();
	
	$.ajax({
		type:'post',
		url:'SaleFee',
		dataType:'JSON',
		data:{
			Type:'add',
			CustomerName:CustomerName,
			ServiceItem:ServiceItem,
			VisitTime:VisitTime,
			SalesMan:SalesMan,
			Engineer:Engineer,
			To:To,
			Copyto:Copyto,
			Password:Password
		},
		success:function(data){
			if(data.status == true){
				 $.MsgBox.Alert('提示', data.message);
			}
		},
		error:function(){
			
		}
	})
})
$(document).on('click','.edit_ok',function(){
	var Send = $('.m-admin .u-span').text();
	var Sender = $('.sendApplication_Email2 .Send input').val();
	if(Send != Sender){
		$.MsgBox_Unload.Alert('提示', '只有原申请人可以修改和发送申请邮件！');
		return;
	}
	
	var ID =  $('.editBox .editBox_title ').attr('id');
	var CustomerName =   $('.editBox .CustomerName input').val();
	var ServiceItem =  $('.editBox .ServiceItem input').val();
	var VisitTime = $('.editBox .VisitTime input').val();
	var SalesMan = $('.editBox .SalesMan select option:selected').text();
	var Engineer = $('.editBox .Engineer input').val();
	
	var To = $('.sendApplication_Email2 .To #Tolist2').text();
	var Copyto = $('.sendApplication_Email2 .Copyto #Copylist2').text();
	var Password = $('.sendApplication_Email2 .Password .password').text();
	
	$.ajax({
		type:'post',
		url:'SaleFee',
		dataType:'JSON',
		data:{
			Type:'update',
			ID:ID,
			CustomerName:CustomerName,
			ServiceItem:ServiceItem,
			VisitTime:VisitTime,
			SalesMan:SalesMan,
			Engineer:Engineer,
			To:To,
			Copyto:Copyto,
			Password:Password
		},
		success:function(data){
			if(data.status == true){
				 $.MsgBox_Unload.Alert('提示', data.message);
				 var page = $('.ui-paging-count').val();
				 showTableData(page);
				 $('.mask').slideUp(300);
				 $('.editBox').slideUp(300);
			}
		},
		error:function(){
			
		}
	})
})

$('.send_cancle,.sendBox_close ').click(function(){
	$('.mask').slideUp(300);
	$('.sendApplication_Email').slideUp(300);
})
//请求邮箱列表
	var test_list = [];
  	test_list = [
        "fanminmin@eoulu.com",
           "liuyanan@eoulu.com",  
            "fangyuanyuan@eoulu.com", 
             "zhaona@eoulu.com",  
              "gaozhenpeng@eoulu.com",  
               "huangyuanyuan@eoulu.com",   
               "liuwei@eoulu.com",  
                "liuyuan@eoulu.com", 
                 "yueyuan@eoulu.com",  
                 "wangyilian@eoulu.com",   
                 "xiaoyu@eoulu.com",  
                  "zhufei@eoulu.com",  
                   "zhanghaiyang@eoulu.com",  
                    "zhaolili@eoulu.com",   
                    "zhaowenzhen@eoulu.com",   
                    "songdaiao@eoulu.com",  
                     "wangning@eoulu.com",    
                      "jiangyaping@eoulu.com",  
                       "luoxiaoxu@eoulu.com", 
                        "xufeng@eoulu.com",   
                        "mengdi@eoulu.com",    
                         "liensheng@eoulu.com",  
                          "yeqingliu@eoulu.com",  
                           "wuzhiyuan@eoulu.com", 
                             "sunmengying@eoulu.com",  
                              "liujiehui@eoulu.com",   
                              "shiconghua@eoulu.com", 
                                "wangkaixuan@eoulu.com",  
                                 "chenshanshan@eoulu.com", 
                                   "helanhai@eoulu.com",  
                                    "wangyakun@eoulu.com",  
                                     "gaona@eoulu.com",  
                                      "yangjiabao@eoulu.com", 
                                        "yangjing@eoulu.com",  
                                         "wuhuixu@eoulu.com",  
                                          "huangyibing@eoulu.com",  
                                           "zenghuaping@eoulu.com", 
                                             "zhangpengfei@eoulu.com", 
                                             "songcongxi@eoulu.com",  
                                              "xiekuanyu@eoulu.com"

    ];
 /*************自动检索邮箱函数**********/
    
    function AutoComplete(autoBox, search, AllEmailList) {  
       
	        var autoNode = $("#" + autoBox);   //缓存对象（弹出框）  
	        var list = new Array();  
	        var n = 0;  
	        var old_value = $("#" + search).text(); 
	        if(old_value.indexOf(";") >0){
	        	 old_value = old_value.split(";").pop();
	        }
	        for (i in AllEmailList) {  
	            if (AllEmailList[i].split("@eoulu.com")[0].indexOf(old_value) >= 0) {  
	                list[n++] = AllEmailList[i];  
	            }  
	        }  
	        if (list.length > 0) {    //如果返回值有内容就显示出来  
	                autoNode.show();  
	          } else {               //服务器端无内容返回 那么隐藏弹出框  
	                autoNode.hide();  
	        } 
	        autoNode.empty();  //清空上次的记录  
	        for (i in list) {  
	            var wordNode = list[i];   //弹出框里的每一条内容  
	            var newDivNode = $("<div class='newDiv'>").attr("id", i);    //设置每个节点的id值  
	            newDivNode.attr("style", "font:14px/25px arial;height:25px;padding:0 8px;cursor: pointer;");  
	            newDivNode.html(wordNode).appendTo(autoNode);  //追加到弹出框  
	               
	         }  
      		//鼠标点击文字上屏  
	            $('.newDiv').click(function () {  
	                    var comText = $(this).text();
	                    //文本框中的内容变成高亮节点的内容  
	                    if($("#" + search).text().indexOf(";")>0){
	                    	var searchText = $("#" + search).text().split(";");
	                    	searchText.pop();
	                    	searchText = searchText.join(";");
	                   		 $("#" + search).text(searchText+";"+comText+";");  
	                    }
	                    else{
	                    	$("#" + search).text(comText+";");  
	                    }
	                   
	                }) 
        //点击页面隐藏自动补全提示框  
        document.onclick = function (e) {  
            var e = e ? e : window.event;  
            var tar = e.srcElement || e.target;  
            if (tar.id != search) {  
                if ($("#" + autoBox).is(":visible")) {  
                    $("#" + autoBox).css("display", "none")  
                }  
            }  
        }  
    }  	
  	
  	
  	$(document).on('keyup','#Tolist',function(){
    	$("#To_div").css("top",parseFloat($("#Tolist").css("height"))+381+"px");
    	if($(this).text() !=""){
    		AutoComplete("To_div", "Tolist", test_list);  
    	}
    }); 
	$(document).on('keyup','#Copylist',function(){
 		$("#CC_div").css("top",parseFloat($("#Copylist").css("height"))+435+"px");
    	if($(this).text() !=""){
    		AutoComplete("CC_div", "Copylist", test_list);  
    	}
    }); 
	$(document).on('keyup','#Tolist2',function(){
    	$("#To_div2").css("top",parseFloat($("#Tolist2").css("height"))+381+"px");
    	if($(this).text() !=""){
    		AutoComplete("To_div2", "Tolist2", test_list);  
    	}
    }); 
	$(document).on('keyup','#Copylist2',function(){
 		$("#CC_div2").css("top",parseFloat($("#Copylist2").css("height"))+435+"px");
    	if($(this).text() !=""){
    		AutoComplete("CC_div2", "Copylist2", test_list);  
    	}
    });  
 	
  //查询工程师
  $(document).on('keyup','.add_engineer',function(){
	  	var keyword = $(this).val();
  		$.ajax({
  			type:'get',
  			url:'GetStaffApplicationName',
  			dataType:'JSON',
  			data:{
  				keyword:keyword
  			},
  			success:function(data){
  				var str = '';
  				for(var i = 1;i<data.length;i++){
  					console.log(data[i])
  					str += '<li class="engineer_li">'+data[i].StaffName+'</li>'
  				}
  				$('.add_engineer_list').empty().append(str).fadeIn(200);
  			}
  		})
  })
   $(document).on('keyup','.edit_engineer',function(){
	  	var keyword = $(this).val();
  		$.ajax({
  			type:'get',
  			url:'GetStaffApplicationName',
  			dataType:'JSON',
  			data:{
  				keyword:keyword
  			},
  			success:function(data){
  				var str = '';
  				for(var i = 1;i<data.length;i++){
  					console.log(data[i])
  					str += '<li class="engineer_li">'+data[i].StaffName+'</li>'
  				}
  				$('.edit_engineer_list').empty().append(str).fadeIn(200);
  			}
  		})
  })
  $('body').click(function(e) {
  			 var target = $(e.target);
   		 	 if(!target.is('.add_engineer')) {
    			$('.add_engineer_list ').fadeOut(200);
   	 		}
	});
  $('body').click(function(e) {
		 var target = $(e.target);
	 	 if(!target.is('.edit_engineer')) {
			$('.edit_engineer_list ').fadeOut(200);
 		}
});
  $(document).on('click','.addBox .engineer_li',function(){
	  var name = $(this).text();
	  $('.add_engineer').val('').val(name);
	  $('.add_engineer_list').fadeOut(200);
  })
  $(document).on('click','.editBox .engineer_li',function(){
	  var name = $(this).text();
	  $('.edit_engineer').val('').val(name);
	  $('.edit_engineer_list').fadeOut(200);
  })
$(document).on('click','.passBtn .noPass, .passBtn .noDeal',function(){
	  var id = $(this).parent().parent().find('.editBtn').attr('id');
	 $('.passBox .passBox_title').attr('id',id);
	$('.mask').slideDown(300);
	$('.passBox').slideDown(300);
});
  var Deal_application = function(data){
	  $.ajax({
		  type:'get',
		  url:'SaleFeeMail',
		  data:data,
		  dataType:'JSON',
		  beforeSend:function(XMLHttpRequest){  
	            $(".send_ok,.pass_ok").css({
	                'background': '#eff5f8',
	                'border': '1px solid #eff5f8',
	                'color':'#000'
	            });   
	     },  
		  success:function(data){
			  if(data.status){
				  $.MsgBox_Unload.Alert('提示', data.message);
			  }else{
				  $.MsgBox_Unload.Alert('提示', data.message);
			  }
		  },
		  complete:function(){
				 $(".send_ok,.pass_ok").css({
					 'background': '#ffb445',
				   ' border': '1px solid #ffb445',
				    'color': '#fff'
		            });   
			}
	  })
  }
$(document).on('click','.passYes',function(){
	  var ID = $('.passBox .passBox_title').attr('id');
	  var IsPass = '已通过';
	  var Reason = '';
	  var data = {
			  'ID' : ID,
			  'IsPass' : IsPass,
			  'Reason' : Reason
	  };
	  Deal_application(data);
})
$(document).on('click','.pass_ok',function(){
	var ID = $('.passBox .passBox_title').attr('id');
	  var IsPass = '未通过';
	  var Reason = $('.passNo_reason input').val();
	  if(Reason == ''){
		  $.MsgBox_Unload.Alert('提示','请填写申请不通过的原因！');
		  return;
	  }
	  var data = {
			  'ID' : ID,
			  'IsPass' : IsPass,
			  'Reason' : Reason
	  };
	  Deal_application(data);
})
$(document).on('click','.passNo',function(){
	$('.passNoBox').slideDown(300);
})
$(document).on('click','.Nopass_cancel',function(){
	$('.passNoBox').slideUp(50);
})
$(document).on('click','.passBox_close ',function(){
	$('.mask').slideUp(300);
	$('.passBox').slideUp(300);
	$('.passNoBox').slideUp(300);
})
/*$(document).on('click','.applyBtn span',function(){
	var tr = $(this).parent().parent();
	var id = tr.find('.editBtn').attr('id');
	$('.sendApplication_Email .sendBox_title').attr('id',id);
	
	$('.sendApplication_Email .Password .password').text('');
	$('.sendApplication_Email #Tolist').html('');
	$('.sendApplication_Email #Copylist').html('');
	
	
	var customer = tr.find('.customer').text();
	var service = tr.find('.service').text();
	var visitDate = tr.find('.visitDate').text();
	var engineer = tr.find('.engineer').text();
	
	
	var Subject = 'Eoulu：拜访申请：'+visitDate+customer+service+engineer;
	$('.sendApplication_Email .Subject input').val('').val(Subject);

	var Content1 = engineer +',你好！<br>';
	var Content2 = '请查收以下服务申请：<br>';
	var Content3 = visitDate+customer+service+engineer+'。<br>';
	var Content4 = '十分感谢。';
	var Content  = Content1+Content2+Content3+Content4;
	$('.sendApplication_Email .email_con div').html('').html(Content);
	$('.mask').slideDown(300);
	$('.sendApplication_Email').slideDown(300);
})*/
$(document).on('click','.sendApplication_Email .send_ok',function(){
	var ID = $('.sendApplication_Email .sendBox_title').attr('id');
	var To = $('.sendApplication_Email .To #Tolist').text();
	var Copyto = $('.sendApplication_Email .Copyto #Copylist').text();
	var Password = $('.sendApplication_Email .Password .password').text();
	$.ajax({
		type:'post',
		url:'SaleFeeMail',
		dataType:'JSON',
		data:{
			ID:ID,
			To:To,
			Copyto:Copyto,
			Password:Password
		},
		beforeSend:function(XMLHttpRequest){  
	            $(".send_ok").css({
	                'background': '#eff5f8',
	                'border': '1px solid #eff5f8',
	                'color':'#000'
	            });   
	     },  
		success:function(data){
			if(data){
				 $.MsgBox_Unload.Alert('提示', '申请邮件已发出！');
			}else{
				 $.MsgBox_Unload.Alert('提示', '发送失败，请检查您的邮箱密码！');
			}
		},
		complete:function(){
			 $(".send_ok").css({
				 'background': '#ffb445',
			   ' border': '1px solid #ffb445',
			    'color': '#fff'
	            });   
		},
		error:function(){
			
		}
	})
})
$(document).on('click','.eye',function(){
	if($(this).parent().find('input').attr('type') == 'password'){
		$(this).parent().find('input').attr('type','text');
		$(this).css({
			'background-image':'url(./image/passeye.png)'
		})
	}else{
		$(this).parent().find('input').attr('type','password');
		$(this).css({
			'background-image':'url(./image/passeye_psd.png)'
		})
	}
	
})


	