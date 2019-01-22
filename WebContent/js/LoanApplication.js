//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});
//加载页面
$(function(){
    	   $('.nav-body').css({
    		   'min-height':'720px'
    	   });
    	  var data = {
    			  LoadType : 'data',
    			  CurrentPage:'1' 
    	  };
    	  changePage(data);
    	  
    	  $.ajax({
    			type:'post',
    			url:'PageVisit',
    			data:{
    				PageName: '借用申请',
    				Department: '物流部'
    			},
    			success:function(data){
    				console.log(data)
    			}
    		})
    	  
    	  
});
function changePage(data){
	  $.ajax({
		  type:'get',
		  url:'LoanApplication',
		  dataType:'JSON',
		  data:data,
		  success:function(data){
			  console.log(data);
			  appendStr_BorrowInfo(data);
			  tureRed();
		  }
	  })
}   
//标红变色提示
var tureRed = function(){
	for(var i =0 ;i<$('.BorrowInfo .tbody_tr').length;i++){
		var agreeOrnot = $('.BorrowInfo .tbody_tr').eq(i).find('.agreeOrnot').text();
		if(agreeOrnot == '已通过'){
			var BorrowInfo_returntime1 = $('.BorrowInfo .tbody_tr').eq(i).find('.BorrowInfo_returntime1').text();
			//times是截止期限
			var date1 = new Date(BorrowInfo_returntime1);
	        var date2 = new Date(BorrowInfo_returntime1);
	        date2.setDate(date1.getDate()-15);
	        
			var today = new Date();
			if(today > date2){
				$('.BorrowInfo .tbody_tr').eq(i).css({
					'color':'red'
				});
			}
		}
	}
}
var appendStr_BorrowInfo =function(data){
	var CurrentPage  = data.currentPage;
	var AllPage = data.pageCount;
	var str = '';
	for(var i= 1;i<data.data.length;i++){
		var response = data.data[i];
		str += '<tr class="tbody_tr">'+
			'<td class="edit_btn" id="'+response.ID+'" >'+(i+(CurrentPage-1)*10)+'</td>'+
			'<td class="BorrowInfo_area">'+response.Area+'</td>'+
			'<td class="BorrowInfo_c" title="'+response.CustomerName+'">'+response.CustomerName+'</td>'+
			'<td class="BorrowInfo_applynum" title="'+response.ApplicationNo+'">'+response.ApplicationNo+'</td>'+
			'<td class="BorrowInfo_goodsname" title="'+response.ItemName+'">'+response.ItemName+'</td>'+
			'<td class="BorrowInfo_applytime">'+response.ApplicationDate+'</td>'+
			'<td class="BorrowInfo_applyperson">'+response.Applicant+'</td>'+
			'<td class="preview_btn"><span></span></td>'+
			'<td class="BorrowInfo_agreement" agreement="'+response.LoanAgreement+'">'+response.LoanAgreement+'</td>'+
			'<td class="agreeOrnot" title="点击审批">'+response.IsPass+'</td>'+
			'<td class="BorrowInfo_borrowtime">'+response.LoanDate+'</td>'+
			'<td class="BorrowInfo_returntime1">'+response.ExpectedReturnDate+'</td>'+
			'<td class="Urge_btn"><span>发送</span></td>'+
			'<td class="BorrowInfo_returnornot">'+response.IsReturn+'</td>'+
			'<td class="BorrowInfo_returntime2">'+response.ActualReturnDate+'</td>'+
			'<td class="BorrowInfo_remark"  title="'+response.Remarks+'">'+response.Remarks+'</td>'+
			'<td class="BorrowInfo_goodstype" style="display:none">'+response.Area+'</td>'+
			'<td class="BorrowInfo_Contact" style="display:none">'+response.Contact+'</td>'+
			'<td class="BorrowInfo_Department" style="display:none">'+response.Department+'</td>'+
			'<td class="BorrowInfo_Phone" style="display:none">'+response.Phone+'</td>'+
			'<td class="BorrowInfo_OperatingTime" style="display:none">'+response.OperatingTime+'</td>'+
			'</tr>';
	}
	$('.BorrowInfo tbody').html('').append(str);
	//协议
	for(var i = 0;i<$('.tbody_tr').length;i++){
		var agreement = $('.tbody_tr').eq(i).find('.BorrowInfo_agreement').text();
		if(agreement == '--' || agreement == '' ){
			$('.tbody_tr').eq(i).find('.BorrowInfo_agreement').text('未上传');
			$('.tbody_tr').eq(i).find('.BorrowInfo_agreement').removeClass('agreement_yes').addClass('agreement_no');
		}else{
			$('.tbody_tr').eq(i).find('.BorrowInfo_agreement').html('').html('<img src="image/agreement_down.png" />');
			$('.tbody_tr').eq(i).find('.BorrowInfo_agreement').removeClass('agreement_no').addClass('agreement_yes');
		}
	}
	
	$('#pageToolbar').empty();
    $('#pageToolbar').Paging({pagesize:"1",current:CurrentPage,count:AllPage,toolbar:true});
}        
 
// 导出
	$(document).on('click','.export_btn',function(){
		$.ajax({
			type:'get',
			url:'LoanApplicationExcel',
			
			beforeSend:function(){
				$('.export_btn').attr('disabled','disabled');
				$('.mask3').show();
				$('.progressBox').show();
			},
			success:function(data){
				window.location.href = data;
			},
			complete:function(){
				$('.export_btn').attr('disabled',false);
				$('.mask3').hide();
				$('.progressBox').hide();
			},
			error:function(){

			}
		})
		window.location.href = 'LoanApplicationExcel';
	});
//搜索
$('.singleSearch').click(function(){
	$(this).addClass('searchType');
	$('.mixSearch').removeClass('searchType');
	$('.mixContent').hide();
})
$('.mixSearch').click(function(){
	$(this).addClass('searchType');
	$('.singleSearch').removeClass('searchType');
	$('.mixContent').css({
			'display':'inline-block'
		});
})
$(document).on('change','.searchTitle1',function(){
	var searchTitle1 = $('.singleContent .searchTitle1').find('option:selected').text();
	if(searchTitle1 == '申请日期'){
		$('.searchCon1').hide();
		$('.search_time1').css({
			'display':'inline-block'
		});
	}else{
		$('.searchCon1').show();
		$('.search_time1').hide();
	}
})
$(document).on('change','.searchTitle2',function(){
	var searchTitle2 = $('.mixContent .searchTitle2').find('option:selected').text();
	if(searchTitle2 == '申请日期'){
		$('.searchCon2').hide();
		$('.search_time2').css({
			'display':'inline-block'
		});
	}else{
		$('.searchCon2').show();
		$('.search_time2').hide();
	}
})
$(document).on('click','.searchbtn1',function(){
	var CurrentPage = '1';
	var Column1 = $('#searchTitle1').find('option:selected').attr('value');
	var Content1 = $('.searchCon1').val();
	if(Column1 == '申请日期'){
		var time1 = $('.searchTime1').val();
		var time2 = $('.searchTime2').val();
		Content1 = time1+'--'+time2;
	}
	var data = new Object;
	if($('.singleSearch').hasClass('searchType')){
		 data = {
			 	LoadType : 'data',
			 	CurrentPage:CurrentPage,
			 	Column1:Column1,
			 	Content1:Content1,
		};
	}else if($('.mixSearch').hasClass('searchType')){
		var Column2 = $('#searchTitle2').find('option:selected').attr('value');
		var Content2 = $('.searchCon2').val();
		if(Column2 == '申请日期'){
			var time1 = $('.searchTime3').val();
			var time2 = $('.searchTime4').val();
			Content2 = time1+'--'+time2;
		}
		 data = {
			 	LoadType : 'data',
			 	CurrentPage:CurrentPage,
			 	Column1:Column1,
			 	Content1:Content1,
			 	Column2:Column2,
			 	Content2:Content2
		};
	}
	  changePage(data);
	
})
$(document).on('click','.searchcancel',function(){
	var data = {
			  LoadType : 'data',
			  CurrentPage:'1' 
	  };
    changePage(data);
	window.location.reload();
})


	//添加
	$(document).on('click','.add_btn',function(){
		$('.addBox .goods_box').empty();
		$('.addBox input').val('');
		$('.addBox #returnInfo option[value="请选择"]').prop('selected','selected');
		$('.addBox #area option[value="请选择"]').prop('selected','selected');

		//发送请求获得上一次的序号是多少，然后编号进行新的自动填充
		//申请编号
		$.ajax({
			type:'get',
			url:'LoanApplicationDocument',
			success:function(data){
				var applyNum = data;
				$('.addBox').find('.applyNum').val(applyNum);
			}
		})

		
		$('.mask').slideDown(300);
		$('.addBox').slideDown(300);
	});
	$(document).on('change','.addBox .returnTime_plan',function(){
			var borrowDate = $('.addBox .borrowDate').val();
			if(borrowDate == ''){
				$.MsgBox_Unload.Alert('提示','请先选择借用日期！');
				$('.addBox .returnTime_plan').val('');
			};
		        //times是截止期限
				var date1 = new Date(borrowDate);
		        var date2 = new Date(borrowDate);
		        date2.setDate(date1.getDate()+60);
		        var times = date2.getFullYear()+"-"+(date2.getMonth()+1)+"-"+date2.getDate();

				var returnTime_plan = $('.addBox .returnTime_plan').val();
				if(returnTime_plan > times){
					$.MsgBox_Unload.Alert('提示','预计归还日期不允许大于借用日期+60天！');
					/*alert("预计归还日期不允许大于借用日期+60天！");*/
					$('.addBox .returnTime_plan').val('');
				}
	});
	$(document).on('change','.editBox .returnTime_plan',function(){
		var borrowDate = $('.editBox .borrowDate').val();
		if(borrowDate == ''){
			$.MsgBox_Unload.Alert('提示','请先选择借用日期！');
			$('.editBox .returnTime_plan').val('');
		};
	        //times是截止期限
			var date1 = new Date(borrowDate);
	        var date2 = new Date(borrowDate);
	        date2.setDate(date1.getDate()+60);
	        var times = date2.getFullYear()+"-"+(date2.getMonth()+1)+"-"+date2.getDate();

			var returnTime_plan = $('.editBox .returnTime_plan').val();
			if(returnTime_plan > times){
				$.MsgBox_Unload.Alert('提示','预计归还日期不允许大于借用日期+60天！');
				$('.editBox .returnTime_plan').val('');
			}
});
	$(document).on('click','.addmodel_close,.addCancel',function(){
		$('.mask').slideUp(300);
		$('.addBox').slideUp(300);
	});

	$(document).on('click','.addgoods_btn',function(){
		var str = '<div class="goodslist">'+
					'<div>'+
						'<span>产品型号</span>'+
						'<input type="text" class="goods_type">'+
					'</div>'+
					'<div>'+
						'<span>产品描述</span>'+
						'<input type="text" class="goods_description">'+
					'</div>'+
					'<div>'+
						'<span>数量</span>'+
						'<input type="text" class="goods_num">'+
					'</div>'+
					'<span class="deleteBtn">删除<br>本行</span>'+
				'</div>';
		if($('.goods_box').find('.goodslist').html() == undefined){
			$('.goods_box').append(str);
		}else{
			$('.goodslist:last-child').after(str);
		}
	})
	//删除
	$(document).on('click','.addBox .deleteBtn',function(){
		$(this).parent().remove();
	});

	//添加的提交
	$(document).on('click','.addOK',function(){
		var Type = 'add';
		var ApplicationDate = $('.addBox .applyDate').val();
		var Applicant = $('.addBox .applyMan').val(); 
		var ApplicationNo = $('.addBox .applyNum').val(); 
		var IsReturn = $('.addBox #returnInfo').find('option:selected').attr('value');
		var Area = $('.addBox #area').find('option:selected').attr('value');
		var LoanDate = $('.addBox .borrowDate').val();
		var ExpectedReturnDate = $('.addBox .returnTime_plan').val();
		var ActualReturnDate = $('.addBox .returnTime_final').val();
		var CustomerName = $('.addBox .customerName').val();
		var Department = $('.addBox .customerpartment').val();
		var Contact = $('.addBox .customerMan').val();
		var Phone = $('.addBox .customerPhone').val();
		var Remarks = $('.addBox .Remark').val();
		var LoanAgreement = $('.addBox .add_agreement').val();
		var index = LoanAgreement.lastIndexOf('\\');
		LoanAgreement = LoanAgreement.slice(index+1,LoanAgreement.length)	;
		var GoodsJsonArr = [];
		var GoodsStr = '' ;
		for(var i=0;i<$('.addBox .goodslist').length;i++){
			var Application_Model = $('.addBox .goodslist').eq(i).find('.goods_type').val();
			var Application_Description= $('.addBox .goodslist').eq(i).find('.goods_description').val();
			var ApplicationDate_Qty = $('.addBox .goodslist').eq(i).find('.goods_num').val();
			GoodsStr = '{"Description":"'+Application_Description+'","Qty":"'+ApplicationDate_Qty+'","Model":"'+Application_Model+'"}';
			GoodsJsonArr.push(GoodsStr);
		}
		var GoodsJson = '['+ GoodsJsonArr.join(',') +']';
		$.ajax({
			type:'post',
			url:'LoanApplication',
			dataType:'JSON',
			data:{
				Type:Type,
				ApplicationDate:ApplicationDate,
				Applicant:Applicant,
				ApplicationNo:ApplicationNo,
				IsReturn:IsReturn,
				Area:Area,
				LoanDate:LoanDate,
				ExpectedReturnDate:ExpectedReturnDate,
				ActualReturnDate:ActualReturnDate,
				CustomerName:CustomerName,
				Department:Department,
				Contact:Contact,
				Phone:Phone,
				Remarks:Remarks,
				GoodsJson:GoodsJson,
				LoanAgreement:LoanAgreement
			},
			beforeSend:function(XMLHttpRequest){  
				$('.addOK').attr('disabled',true) ;
	        }, 
			success:function(data){
				if(data.status == true){
					$.MsgBox.Alert('提示',data.message);
				}else if(data.status == false){
					$.MsgBox.Alert('提示',data.message);
				}
			},
	        complete:function(XMLHttpRequest,textStatus){  
	        	$('.addOK').attr('disabled',false) ;
	        }  
		})
		
	});


	//修改
	$(document).on('click','.edit_btn,.agreement_no',function(){
		var ID = $(this).parent().find('.edit_btn').attr('id');
		var tr = $(this).parent();
		$('.editBox').find('.editmodel_title').attr('id',ID);
		$('.editBox').find('.applyDate').val(tr.find('.BorrowInfo_applytime').text());
		$('.editBox').find('.applyMan').val(tr.find('.BorrowInfo_applyperson').text());
		var returnInfo = tr.find('.BorrowInfo_returnornot').text();
		$('.editBox').find('#returnInfo').find('option[value="'+returnInfo+'"]').prop('selected',true);
		var area = tr.find('.BorrowInfo_area').text();
		$('.editBox').find('.area').find('option[value="'+area+'"]').prop('selected',true);
		
		$('.editBox').find('.borrowDate').val(tr.find('.BorrowInfo_borrowtime').text());
		$('.editBox').find('.returnTime_plan').val(tr.find('.BorrowInfo_returntime1').text());
		$('.editBox').find('.returnTime_final').val(tr.find('.BorrowInfo_returntime2').text());
		$('.editBox').find('.customerName').val(tr.find('.BorrowInfo_c').text());
		$('.editBox').find('.customerpartment').val(tr.find('.BorrowInfo_Department').text());
		$('.editBox').find('.customerMan').val(tr.find('.BorrowInfo_Contact').text());
		$('.editBox').find('.customerPhone').val(tr.find('.BorrowInfo_Phone').text());
		$('.editBox').find('.applyDate').val(tr.find('.BorrowInfo_applytime').text());
		$('.editBox').find('.Remark').val(tr.find('.BorrowInfo_remark').text());
		var edit_agreementName = tr.find('.BorrowInfo_agreement ').attr('agreement');
		if(edit_agreementName == '--' || edit_agreementName == ''){
			$('.edit_agreementName').hide();
			$('.edit_agreementName').addClass('hidden');
			$('.editBox').find('.edit_agreement').show();
			$('.editBox').find('.edit_agreement').removeClass('hidden');
		}else{
			$('.editBox').find('.edit_agreement').hide();
			$('.editBox').find('.edit_agreement').addClass('hidden');
			$('.edit_agreementName').val(edit_agreementName);
			$('.edit_agreementName').show();
			$('.edit_agreementName').removeClass('hidden');
		}
		
		
		
		$('.editBox').find('.applyNum').val(tr.find('.BorrowInfo_applynum').text());
		
		//货物信息
		$.ajax({
			type:'get',
			url:'LoanApplicationDocument',
			data:{
				ID:ID,
				dataType:'goods'
			},
			dataType:'JSON',
			success:function(data){
				var str = '';
				for(var i= 1;i<data.length;i++){
					 str += '<div class="goodslist2">'+
									'<div>'+
									'<span>产品型号</span>'+
									'<input type="text" class="goods_type" value="'+data[i].Model +'">'+
								'</div>'+
								'<div>'+
									'<span>产品描述</span>'+
									'<input type="text" class="goods_description" value="'+data[i].Description +'">'+
								'</div>'+
								'<div>'+
									'<span>数量</span>'+
									'<input type="text" class="goods_num" value="'+data[i].Qty +'">'+
								'</div>'+
								'<div style="display:none">'+
									'<span>序列号</span>'+
									'<input type="text" class="goods_SerialNumber" value="'+data[i].SerialNumber +'">'+
								'</div>'+
								'<span class="deleteBtn">删除<br>本行</span>'+
							'</div>';
					
				}
				$('.goods_editBox').empty().append(str);
		}
	})
		$('.mask').slideDown(300);
		$('.editBox').slideDown(300);
	});
	$(document).on('click','.editmodel_close,.editCancel',function(){
		$('.mask').slideUp(300);
		$('.editBox').slideUp(300);
	})
	
	$(document).on('click','.editgoods_btn',function(){
		var str = '<div class="goodslist2">'+
						'<div>'+
						'<span>产品型号</span>'+
						'<input type="text" class="goods_type">'+
					'</div>'+
					'<div>'+
						'<span>产品描述</span>'+
						'<input type="text" class="goods_description">'+
					'</div>'+
					'<div>'+
						'<span>数量</span>'+
						'<input type="text" class="goods_num">'+
					'</div>'+
					'<div style="display:none">'+
						'<span>序列号</span>'+
						'<input type="text" class="goods_SerialNumber">'+
					'</div>'+
					'<span class="deleteBtn">删除<br>本行</span>'+
				'</div>';
		if($('.goods_editBox').find('.goodslist2').html() == undefined){
			$('.goods_editBox').append(str);
		}else{
			$('.goodslist2:last-child').after(str);
		}
	})
	//删除
	$(document).on('click','.editBox .deleteBtn',function(){
		$(this).parent().remove();
	});
	//修改的提交
	$(document).on('click','.editOK',function(){
		var ID = $('.editBox .editmodel_title').attr('id');
		var Type = 'update';
		var ApplicationDate = $('.editBox .applyDate').val();
		var Applicant = $('.editBox .applyMan').val(); 
		var ApplicationNo = $('.editBox .applyNum').val(); 
		var IsReturn = $('.editBox #returnInfo').find('option:selected').attr('value');
		var Area = $('.editBox #area').find('option:selected').attr('value');
		var LoanDate = $('.editBox .borrowDate').val();
		var ExpectedReturnDate = $('.editBox .returnTime_plan').val();
		var ActualReturnDate = $('.editBox .returnTime_final').val();
		var CustomerName = $('.editBox .customerName').val();
		var Department = $('.editBox .customerpartment').val();
		var Contact = $('.editBox .customerMan').val();
		var Phone = $('.editBox .customerPhone').val();
		var Remarks = $('.editBox .Remark').val();
		var LoanAgreement = '';
		if($('.editBox .edit_agreementName').hasClass('hidden')){
			var LoanAgreement = $('.editBox .edit_agreement').val();
			var index = LoanAgreement.lastIndexOf('\\');
			LoanAgreement = LoanAgreement.slice(index+1,LoanAgreement.length);
		}else{
			LoanAgreement = $('.editBox .edit_agreementName').val();
		}
		var GoodsJsonArr = [];
		var GoodsStr = '' ;
		for(var i=0;i<$('.editBox .goodslist2').length;i++){
			var Application_Model = $('.editBox .goodslist2').eq(i).find('.goods_type').val();
			var Application_Description= $('.editBox .goodslist2').eq(i).find('.goods_description').val();
			var ApplicationDate_Qty = $('.editBox .goodslist2').eq(i).find('.goods_num').val();
			var ApplicationDate_SerialNumber = $('.editBox .goodslist2').eq(i).find('.goods_SerialNumber').val();
			GoodsStr = '{"Description":"'+Application_Description+'","Qty":"'+ApplicationDate_Qty+'","SerialNumber":"'+ApplicationDate_SerialNumber+'","Model":"'+Application_Model+'"}';
			GoodsJsonArr.push(GoodsStr);
		}
		var GoodsJson = '['+ GoodsJsonArr.join(',') +']';
		
		
		$.ajax({
			type:'post',
			url:'LoanApplication',
			dataType:'JSON',
			data:{
				ID:ID,
				Type:Type,
				ApplicationDate:ApplicationDate,
				Applicant:Applicant,
				ApplicationNo:ApplicationNo,
				IsReturn:IsReturn,
				Area:Area,
				LoanDate:LoanDate,
				ExpectedReturnDate:ExpectedReturnDate,
				ActualReturnDate:ActualReturnDate,
				CustomerName:CustomerName,
				Department:Department,
				Contact:Contact,
				Phone:Phone,
				Remarks:Remarks,
				GoodsJson:GoodsJson,
				LoanAgreement:LoanAgreement
			},
			beforeSend:function(XMLHttpRequest){  
				$('.editOK').attr('disabled',true) ;
	        }, 
			success:function(data){
				if(data.status == true){
					$.MsgBox.Alert('提示',data.message);
				}else if(data.status == false){
					$.MsgBox.Alert('提示',data.message);
				}
				$('.mask').slideUp(300);
				$('.editBox').slideUp(300);
			},
	        complete:function(XMLHttpRequest,textStatus){  
	        	$('.editBox').attr('disabled',false) ;
	        }  
		})
		
	});

	
	//预览
	$(document).on('click','.preview_btn',function(){
		var account = $('.eou-container-r .m-admin .u-span').text();
		$('.sendEmail1  .sendmodel_title').attr('account',account);
		
		var tr = $(this).parent();
		var ID = tr.find('.edit_btn').attr('id');
		$('.borrowInfo_model').find('.wordModel_title').attr('id',ID);
		
		$('.borrowInfo_model').find('.model_company').text(tr.find('.BorrowInfo_c').text());
		$('.borrowInfo_model').find('.model_depart').text(tr.find('.BorrowInfo_Department').text());
		$('.borrowInfo_model').find('.model_concat').text(tr.find('.BorrowInfo_Contact').text());
		$('.borrowInfo_model').find('.model_phone').text(tr.find('.BorrowInfo_Phone').text());

		$('.borrowInfo_model').find('.model_number').text(tr.find('.BorrowInfo_applynum').text());
		$('.borrowInfo_model').find('.model_borrowDate').text(tr.find('.BorrowInfo_borrowtime').text());
		$('.borrowInfo_model').find('.model_returntime').text(tr.find('.BorrowInfo_returntime1').text());

		$('.sendEmail1').find('.sendmodel_title').attr('id',ID);
		$('.sendEmail1').find('.sendmodel_title').attr('company',tr.find('.BorrowInfo_c').text());
		$('.sendEmail1').find('.sendmodel_title').attr('depart',tr.find('.BorrowInfo_Department').text());
		$('.sendEmail1').find('.sendmodel_title').attr('concat',tr.find('.BorrowInfo_Contact').text());
		$('.sendEmail1').find('.sendmodel_title').attr('phone',tr.find('.BorrowInfo_Phone').text());
		$('.sendEmail1').find('.sendmodel_title').attr('number',tr.find('.BorrowInfo_applynum').text());
		$('.sendEmail1').find('.sendmodel_title').attr('borrowDate',tr.find('.BorrowInfo_borrowtime').text());
		$('.sendEmail1').find('.sendmodel_title').attr('returntime',tr.find('.BorrowInfo_returntime1').text());
		
		
		var applytime = tr.find('.BorrowInfo_applytime').text().replace(/-/g,'');
		var company = tr.find('.BorrowInfo_c').text();
		var concat = tr.find('.BorrowInfo_Contact').text();
		var BorrowInfo_goodstype = tr.find('.BorrowInfo_goodsname').text();
		var BorrowInfo_applynum = tr.find('.BorrowInfo_applynum').text();
		var str = 'Eoulu：'+applytime + company + concat +BorrowInfo_goodstype+'的借用申请('+BorrowInfo_applynum+')';
		$('.sendEmail1 .send_Subject input').val(str);
		var  contentStr = '<span class="send_Conspan" contenteditable="true">刘经理，您好！<br>'+
		'附件： '+BorrowInfo_applynum+'是'+company+concat+BorrowInfo_goodstype+'的借用申请，请尽快确认是否可以借用。<br>'+
		'非常感谢！</span>';
		
		$('.sendEmail1 .send_Conspan').remove();
		$('.sendEmail1 .send_ConspanTitle').after(contentStr);
		
		
		
		
		//邮件发送的信息存储
		/*③主题：Eoulu：申请日期+客户名+联系人+产品型号的借用申请（借用申请编号），例如：20180514中电13所卢素伟101-190的借用申请（LOAN20180514-1）*/
		var applytime = tr.find('.BorrowInfo_applytime').text().replace(/-/g,'');
		var company = tr.find('.BorrowInfo_c').text();
		var concat = tr.find('.BorrowInfo_Contact').text();
		var BorrowInfo_goodstype = tr.find('.BorrowInfo_goodstype').text();
		var BorrowInfo_applynum = tr.find('.BorrowInfo_applynum').text();
		
		$('.borrowInfo_model').find('.wordModel_title').attr('applytimed',applytime);
		$('.borrowInfo_model').find('.wordModel_title').attr('company',company);
		$('.borrowInfo_model').find('.wordModel_title').attr('concat',concat);
		$('.borrowInfo_model').find('.wordModel_title').attr('BorrowInfo_goodstype',BorrowInfo_goodstype);
		$('.borrowInfo_model').find('.wordModel_title').attr('BorrowInfo_applynum',BorrowInfo_applynum);
		
		
		
		//货物信息
		$.ajax({
			type:'get',
			url:'LoanApplicationDocument',
			data:{
				ID:ID,
				dataType:'goods'
			},
			dataType:'JSON',
			success:function(data){
				var str = '';
				for(var i= 1;i<data.length;i++){
					if(data[i].SerialNumber == 'undefined' || data[i].SerialNumber == '' ){
						data[i].SerialNumber == '未填写';
					}
					 str += '<tr class="Modeltable_tr">'+
								'<td>'+i+'</td>'+  
								'<td contenteditable="true" class="Modeltable_model">'+data[i].Model+'</td>'+
								'<td contenteditable="true" class="Modeltable_Description">'+data[i].Description+'</td>'+
								'<td contenteditable="true" class="Modeltable_number">'+data[i].SerialNumber+'</td>'+
								'<td contenteditable="true" class="Modeltable_Qty">'+data[i].Qty+'</td>'+
							'</tr>';
					
				}
				
				$('.content2 .Modeltable_tr').remove();
				$('.content2 .Modeltable_title').after(str);
		}
	})
		$('.mask').fadeIn();
		$('.borrowInfo_model').fadeIn();
	});
	//预览中的关闭
	$(document).on('click','.wordModel_btns .wordModel_close',function(){
		$('.mask').fadeOut();
		$('.borrowInfo_model').fadeOut();
	})
	
	
	//点击带章发送中弹出邮件模板
	$(document).on('click','.wordModel_btns .wordModel_send',function(){
		var account = $('.eou-container-r .m-admin .u-span').text();
		$('.sendmodel_con1 .send_Acc input').val(account);
		
		/*③	主题：
		④	正文格式如下：
		申请人（XXX），您好！
		附件：借用申请编号，是客户名+联系人+产品型号的借用申请，预计归还日期为xxxx-xx-xx（自动关联系统中登记的预计归还日期），烦请告知客户及时归还，如不能及时归还，请确认客户是否采购，若不能按时归还且不能按时采购，将按照当月的标准售价扣在个人account账户里。
		以上请知悉，非常感谢！ */
		var str1 = '';
		
		$('.mask2').slideDown(300);
		$('.sendEmail1').slideDown(300);
	});
	$(document).on('click','.sendEmail1 .sendmodel_close,.sendCancel1',function(){
		$('.mask2').slideUp(300);
		$('.sendEmail1').slideUp(300);
	});
	//预览中的邮件发送确认提交
	$(document).on('click','.sendOK1',function(){
		
		var ID = $('.borrowInfo_model').find('.wordModel_title').attr('id');
		var account = $('.sendmodel_con1 .send_Acc input').val();
		var password = $('.send_Psd').find('input').val();
		
		
		var CustomerName = $('.sendEmail1').find('.sendmodel_title').attr('company');
		var Department = $('.sendEmail1').find('.sendmodel_title').attr('depart');
		var Contact = $('.sendEmail1').find('.sendmodel_title').attr('concat');
		var Phone = $('.sendEmail1').find('.sendmodel_title').attr('phone');
		var ApplicationNo = $('.sendEmail1').find('.sendmodel_title').attr('number');
		var LoanDate = $('.sendEmail1').find('.sendmodel_title').attr('borrowDate');
		var ExpectedReturnDate = $('.sendEmail1').find('.sendmodel_title').attr('returntime');
		var copyList = $('.sendEmail1').find('.send_copyspan').text().replace(/,/g,';');;
		var toList = $('.sendEmail1').find('.send_receivespan').text().replace(/,/g,';');
		var content = $('.sendEmail1').find('.send_Conspan').text();
		var con1 = content.replace(/您好！/,'您好！<br>');
		var con2 = con1.replace(/非常感谢！/,'<br>非常感谢！');
		
		
		
		var subject =  $('.sendEmail1').find('.send_Subject input').val();
		var pwd =  $('.sendEmail1').find('.send_Psd input').val();
		
		var GoodsJsonArr = [];
		var GoodsStr = '' ;
		for(var i=0;i<$('.content2 .Modeltable_tr').length;i++){
			var Application_Model = $('.content2 .Modeltable_tr').eq(i).find('.Modeltable_model').text();
			var Application_Description= $('.content2 .Modeltable_tr').eq(i).find('.Modeltable_Description').text();
			var Application_SerialNumber= $('.content2 .Modeltable_tr').eq(i).find('.Modeltable_number').text();
			var ApplicationDate_Qty = $('.content2 .Modeltable_tr').eq(i).find('.Modeltable_Qty').text();
			GoodsStr = '{"Description":"'+Application_Description+'","Qty":"'+ApplicationDate_Qty+'","SerialNumber":"'+Application_SerialNumber+'","Model":"'+Application_Model+'"}';
			GoodsJsonArr.push(GoodsStr);
		}
		var GoodsJson = '['+ GoodsJsonArr.join(',') +']';
		
		$.ajax({
			type:'post',
			url:'LoanApplicationDocument',
			data:{
				Type:'send',
				ID:ID,
				CustomerName:CustomerName,
				Department:Department,
				Contact:Contact,
				Phone:Phone,
				ApplicationNo:ApplicationNo,
				LoanDate:LoanDate,
				ExpectedReturnDate:ExpectedReturnDate,
				GoodsJson:GoodsJson,
				copyList:copyList,
				toList:toList,
				content:con2,
				subject:subject,
				pwd:password
			},
			beforeSend:function(){
				$('.sendOK1').attr('disabled',true);
				$('.mask3').show();
				$('.progressBox').show();
			},
			success:function(data){
				if(data == 'false'){
					$.MsgBox.Alert('提示','邮件发送失败！');
				}else{
					$.MsgBox.Alert('提示','邮件发送成功！');
				}
			},
			complete:function(){
				$('.sendOK1').attr('disabled',false);
				$('.mask3').hide();
				$('.progressBox').hide();
			},
			error:function(){
				$.MsgBox.Alert('提示','网络错误，请稍候再试！');
			}
		})


	});
	$(document).on('click','.sendmodel_con1 .send_Psd .send_Psdeye',function(){
		var Dom = '.sendmodel_con1 .send_Psd .send_Psdeye';
		Clickeye(Dom);
	});

	function Clickeye(Dom){
		if($(Dom).siblings('input').attr('type') == 'password'){
			$(Dom).css({
			'background-image':'url(./image/passeye.png)'
			});
			$(Dom).siblings('input').attr('type','text');
		}else{
			$(Dom).css({
			'background-image':'url(./image/passeye_psd.png)'
			});
			$(Dom).siblings('input').attr('type','password');
		}
	}
	


	//预览中的带章下载
	$(document).on('click','.wordModel_btns .wordModel_down',function(){
		var ID = $('.borrowInfo_model').find('.wordModel_title').attr('id');
		var CustomerName = $('.sendEmail1').find('.sendmodel_title').attr('company');
		var Department = $('.sendEmail1').find('.sendmodel_title').attr('depart');
		var Contact = $('.sendEmail1').find('.sendmodel_title').attr('concat');
		var Phone = $('.sendEmail1').find('.sendmodel_title').attr('phone');
		var ApplicationNo = $('.sendEmail1').find('.sendmodel_title').attr('number');
		var LoanDate = $('.sendEmail1').find('.sendmodel_title').attr('borrowDate');
		var ExpectedReturnDate = $('.sendEmail1').find('.sendmodel_title').attr('returntime');
		
		
		var GoodsJsonArr = [];
		var GoodsStr = '' ;
		for(var i=0;i<$('.content2 .Modeltable_tr').length;i++){
			var Application_Model = $('.content2 .Modeltable_tr').eq(i).find('.Modeltable_model').text();
			var Application_Description= $('.content2 .Modeltable_tr').eq(i).find('.Modeltable_Description').text();
			var Application_SerialNumber= $('.content2 .Modeltable_tr').eq(i).find('.Modeltable_number').text();
			var ApplicationDate_Qty = $('.content2 .Modeltable_tr').eq(i).find('.Modeltable_Qty').text();
			GoodsStr = '{"Description":"'+Application_Description+'","Qty":"'+ApplicationDate_Qty+'","SerialNumber":"'+Application_SerialNumber+'","Model":"'+Application_Model+'"}';
			GoodsJsonArr.push(GoodsStr);
		}
		var GoodsJson = '['+ GoodsJsonArr.join(',') +']';
		
		$.ajax({
			type:'post',
			url:'LoanApplicationDocument',
			data:{
				Type:'download',
				ID:ID,
				CustomerName:CustomerName,
				Department:Department,
				Contact:Contact,
				Phone:Phone,
				ApplicationNo:ApplicationNo,
				LoanDate:LoanDate,
				ExpectedReturnDate:ExpectedReturnDate,
				GoodsJson:GoodsJson
			},
			beforeSend:function(){
				$('.wordModel_btns .wordModel_down').attr('disabled','disabled');
				$('.mask2').show();
				$('.progressBox').show();
			},
			success:function(data){
				window.location.href=data;
			},
			complete:function(){
				$('.wordModel_btns .wordModel_down').attr('disabled',false);
				$('.mask2').hide();
				$('.progressBox').hide();
			},
			error:function(){
				$.MsgBox.Alert('提示','网络错误，请稍候再试！');
			}
		})
		
	})
	
	//催促发送
	$(document).on('click','.Urge_btn',function(){
		var Applicant = $('.sendEmail2 .sendmodel_title').attr('Applicant');
		var account = $('.eou-container-r .m-admin .u-span').text();
		var tr = $(this).parent();
		var ID = tr.find('.edit_btn').attr('id');
		
		var isPASS = tr.find('.agreeOrnot').text();
		if(isPASS == '未通过'){
			$.MsgBox.Alert('提示','申请未通过，请您先审批！');
			return
		}
		if($('.LoanIsPass').text() == 'LoanIsPass'){
			
		}else{
			$.MsgBox.Alert('提示','您没有发送催促邮件权限！');
			return
		}
		
		$('.sendEmail2').find('.sendmodel_title').attr('id',ID);
		$('.sendEmail2').find('.send_receivespan').text(account);
	
		var applytime = tr.find('.BorrowInfo_applytime').text().replace(/-/g,'');
		var company = tr.find('.BorrowInfo_c').text();
		var concat = tr.find('.BorrowInfo_Contact').text();
		var BorrowInfo_goodstype = tr.find('.BorrowInfo_goodstype').text();
		var BorrowInfo_applynum = tr.find('.BorrowInfo_applynum').text();
		var BorrowInfo_returntime1 = tr.find('.BorrowInfo_returntime1').text();
		var Applicant = tr.find('.BorrowInfo_applyperson').text();
		$('.sendEmail2 .sendmodel_title').attr('Applicant',Applicant);
		var str = 'Eoulu：借用归还提醒—'+applytime + company + concat +BorrowInfo_goodstype+'的借用申请('+BorrowInfo_applynum+')';
		$('.sendEmail2 .send_Subject input').val(str);
		
		
		var  contentStr = '<span class="send_Conspan" contenteditable="true">'+Applicant+'，您好！<br>'+
		'附件： '+BorrowInfo_applynum+'是'+company+concat+BorrowInfo_goodstype+'的借用申请，预计归还日期为'+BorrowInfo_returntime1+'烦请告知客户及时归还，如不能及时归还，请确认客户是否采购，若不能按时归还且不能按时采购，将按照当月的标准售价扣在个人account账户里。<br>'+
		'以上请知悉，非常感谢！</span>';
		
		$('.sendEmail2 .send_Conspan').remove();
		$('.sendEmail2 .send_ConspanTitle').after(contentStr);
		
		
		$('.mask').slideDown(300);
		$('.sendEmail2').slideDown(300);

	});
	$(document).on('click','.sendEmail2 .sendOK2',function(){
		var ID = $('.sendEmail2').find('.sendmodel_title').attr('id');
		var Applicant = $('.sendEmail2 .sendmodel_title').attr('Applicant');
		var copy = $('.sendmodel_con2 .send_copyspan').text();
		if(copy.indexOf('，')>-1){
			copy = copy.replace(/，/g,',');
		}
		var copyList = copy.replace(/,/g,';');
		var content = $('.sendEmail2').find('.send_Conspan').text();
		
		var con1 = content.replace(/您好！/,'您好!<br>');
		var con2 = con1.replace(/以上请知悉，非常感谢！/,'<br>以上请知悉，非常感谢！');
		var subject =  $('.sendEmail2').find('.send_Subject input').val();
		
		$.ajax({
			type:'post',
			url:'LoanApplicationReview',
			data:{
				ID:ID,
				copyList:copyList,
				Applicant:Applicant,
				content:con2,
				subject:subject
			},
			beforeSend:function(){
				$('.sendOK2').attr('disabled','disabled');
				$('.mask2').show();
				$('.progressBox').show();
			},
			success:function(data){
					if(data == 'false'){
						$.MsgBox.Alert('提示','邮件发送失败！');
					}else{
						$.MsgBox.Alert('提示','邮件发送成功！');
					}
			},
			complete:function(){
				$('.sendOK2').attr('disabled',false);
				$('.mask2').hide();
				$('.progressBox').hide();
			},
			error:function(){

			}
		});
		$('.mask').slideUp(300);
		$('.sendEmail2').slideUp(300);
	})
	$(document).on('click','.sendEmail2 .sendmodel_close,.sendCancel2',function(){
		$('.mask').slideUp(300);
		$('.sendEmail2').slideUp(300);
	})
	$(document).on('click','.pass_box .passBox_close',function(){
		$('.mask').slideUp(300);
		$('.pass_box').slideUp(300);
	})
	$(document).on('click','.allUrge_box .allUrgeBox_close,.allUrgeBox_cancel',function(){
		$('.mask').slideUp(300);
		$('.allUrge_box').slideUp(300);
	})
	//点击审批
	$(document).on('click','.agreeOrnot',function(){
		$('.nopass_div .nopass_reason input').val('');
		$('.nopass_div').hide();
		var ID = $(this).parent().find('.edit_btn').attr('id');
		$('.pass_box .passBox_title').attr('id',ID);
		if($(this).text() == '已通过'){
			
		}else{
			if($('.LoanIsPass').text() == 'LoanIsPass'){
				$('.mask').slideDown(300);
				$('.pass_box').slideDown(300);
				
			}else{
				$.MsgBox.Alert('提示','您没有审批权限！');
			}
		}
	})
	//审批同意
	$(document).on('click','.pass_box .pass',function(){
		var ID = $(this).parent().parent().parent().find('.passBox_title').attr('id');
		var isPass = '已通过';
		$.ajax({
			type:'get',
			url:'LoanApplicationReview',
			data:{
				ID:ID,
				isPass:isPass
			},
			beforeSend:function(){
				$('.pass_box .pass').attr('disabled','disabled');
				$('mask3').show();
				$('.progressBox').show();
			},
			success:function(data){
				if(data == 'false'){
					$.MsgBox.Alert('提示','审批失败！');
				}else if(data == 'true'){
					$.MsgBox.Alert('提示','审批成功！');
				}
			},
			complete:function(){
				$('.pass_box .pass').attr('disabled',false);
				$('mask3').hide();
				$('.progressBox').hide();
			}
		})
	})
	//审批不同意
	$(document).on('click','.pass_box .Nopass',function(){
		$('.nopass_div').slideDown(200);
		
	})
	//审批不同意的取消
	$(document).on('click','.Nopass_cancel',function(){
		$('.nopass_div').slideUp(200);
	})
	//审批不同意的提交
	$(document).on('click','.pass_ok',function(){
		var ID = $(this).parents('.pass_box').find('.passBox_title').attr('id');
		var isPass = '未通过';
		var reason = $(this).parents('.pass_box').find('.nopass_reason input').val();
		$.ajax({
			type:'get',
			url:'LoanApplicationReview',
			data:{
				ID:ID,
				isPass:isPass,
				reason:reason
			},
			success:function(data){
				if(data == 'false'){
					$.MsgBox.Alert('提示','审批失败！');
				}else if(data == 'true'){
					$.MsgBox.Alert('提示','审批成功！');
				}
			}
		})
	})
	//跳页点击事件
	$(document).on('click','.Gotojump',function(){
			var currentPage = Number($('.ui-paging-count').val());
			var data = {
	    			  LoadType : 'data',
	    			  CurrentPage:currentPage 
	    	  };
	    	  changePage(data);
	})
	$(document).on('click','.pagenum',function(){
			var currentPage = Number($(this).text());
			var data = {
	    			  LoadType : 'data',
	    			  CurrentPage:currentPage 
	    	  };
	    	  changePage(data);
			$(".list-wrap").css('height','auto');
	})
	$(document).on('click','.js-page-prev',function(){
			var currentPage = Number($('.focus').text());
			var data = {
	    			  LoadType : 'data',
	    			  CurrentPage:currentPage 
	    	  };
	    	  changePage(data);
			$(".list-wrap").css('height','auto');
	})
	$(document).on('click','.js-page-next',function(){
			var currentPage = Number($('.focus').text());
			var data = {
	    			  LoadType : 'data',
	    			  CurrentPage:currentPage 
	    	  };
	    	changePage(data);
			$(".list-wrap").css('height','auto');
	})
	/*//收件人抄送人关联
	//催促
	$(document).on('keyup','.sendEmail2 .send_copyspan',function(){
		var searchCon = $(this).text();
		if(searchCon.indexOf(',') > -1){
			var index = searchCon.lastIndexOf(',');
			searchCon = searchCon.substring(index+1);
		}
		if(searchCon == '' || searchCon == ' '){
			return
		}
		$.ajax({
			type:'get',
			url:'GetStaffApplicationToList',
			dataType:'JSON',
			data:{
				keyword:searchCon
			},
			success:function(data){
				var str = '';
				for(var i = 1;i<data.length;i++){
					var StaffMail = data[i].StaffMail;
					str += '<span>'+StaffMail+'</span>';
				}
				$('.searchContentBox').empty().append(str);
				$('.sendEmail2 .searchContentBox').fadeIn(200);
			}
		});
		
	})
	$(document).on('click','.searchContentBox span',function(){
		var str = $('.sendEmail2 .send_copyspan').text();
		$('.sendEmail2 .send_copyspan').text(str+$(this).text()+',');
		$('.searchContentBox').fadeOut(200);
		
	})*/
	
	/*添加中 申请人可选可添*/
	$(document).on('click','.addBox .applyMan ',function(){
		$('.addBox .applyMan_list').slideDown(200);
	});
	$(document).on('click','.addBox .applyMan_list  li',function(){
		$('.addBox .applyMan').val('').val($(this).text());
		$('.addBox .applyMan_list').slideUp(200);
	});
	$('body').click(function(e) {
	    var target = $(e.target);
	    if(!target.is('.addBox .applyMan')) {
	    	$('.addBox .applyMan_list').slideUp(200);
	    }
	});
	
	/*修改中 申请人可选可添*/
	$(document).on('click','.editBox .applyMan ',function(){
		$('.editBox .applyMan_list').slideDown(200);
	});
	$(document).on('click','.editBox .applyMan_list  li',function(){
		$('.editBox .applyMan').val('').val($(this).text());
		$('.editBox .applyMan_list').slideUp(200);
	});
	$('body').click(function(e) {
	    var target = $(e.target);
	    if(!target.is('.editBox .applyMan')) {
	    	$('.editBox .applyMan_list').slideUp(200);
	    }
	});
	//上传协议
	$(document).on('change','.addBox .add_agreement',function(){
        var fileObj = document.getElementById("FileUpload").files[0]; // js 获取文件对象
        if (typeof (fileObj) == "undefined" || fileObj.size <= 0) {
                 $.MsgBox_Unload.Alert('提示','请选择文件！');
                 return;
             }
             var formFile = new FormData();
             formFile.append("action", "UploadFile");  
             formFile.append("file", fileObj); //加入文件对象
             var data = formFile;
             $.ajax({
                 url: "UploadFile",
                 data: data,
                 type: "Post",
                 cache: false,
                 processData: false,
                 contentType: false, 
                 success: function (data) {
                    console.log(data)
                    /*$.MsgBox_Unload.Alert('提示',data);*/
                 }
             })
	})
	$(document).on('click','.editBox .edit_agreementName',function(){
		$(this).hide();
		$(this).addClass('hidden');
		$('.editBox').find('.edit_agreement').removeClass('hidden');
		$('.editBox').find('.edit_agreement').show();
	})
	//修改中的上传协议
	$(document).on('change','.editBox .edit_agreement',function(){
        var fileObj = document.getElementById("FileUpload2").files[0]; // js 获取文件对象
        if (typeof (fileObj) == "undefined" || fileObj.size <= 0) {
                 $.MsgBox_Unload.Alert('提示','请选择文件！');
                 return;
             }
             var formFile = new FormData();
             formFile.append("action", "UploadFile");  
             formFile.append("file", fileObj); //加入文件对象
             var data = formFile;
             $.ajax({
                 url: "UploadFile",
                 data: data,
                 type: "Post",
                 cache: false,
                 processData: false,
                 contentType: false, 
                 success: function (data) {
                    console.log(data)
                    /*$.MsgBox_Unload.Alert('提示',data);*/
                 }
             })
	})
	//协议的下载
	$(document).on('click','.agreement_yes',function(){
		var agreement = $(this).attr('agreement');
		window.location.href = "/LogisticsFile/File/"+ agreement;
	})
	//一键催促
	$(document).on('click','.Urge_all',function(){
			var CurrentPage = $('.focus').text();
			var Column1 = $('#searchTitle1').find('option:selected').attr('value');
			var Content1 = $('.searchCon1').val();
			if(Column1 == '申请日期'){
				var time1 = $('.searchTime1').val();
				var time2 = $('.searchTime2').val();
				Content1 = time1+'--'+time2;
			}
			var data = new Object;
			if($('.singleSearch').hasClass('searchType')){
				 data = {
					 	CurrentPage:CurrentPage,
					 	Column1:Column1,
					 	Content1:Content1,
				};
			}else if($('.mixSearch').hasClass('searchType')){
				var Column2 = $('#searchTitle2').find('option:selected').attr('value');
				var Content2 = $('.searchCon2').val();
				if(Column2 == '申请日期'){
					var time1 = $('.searchTime3').val();
					var time2 = $('.searchTime4').val();
					Content2 = time1+'--'+time2;
				}
				 data = {
					 	CurrentPage:CurrentPage,
					 	Column1:Column1,
					 	Content1:Content1,
					 	Column2:Column2,
					 	Content2:Content2
				};
			}
			 $.ajax({
				 type:'get',
				 url:'LoanApplicationReviewList',
				 data:data,
				 dataType:'JSON',
				 success:function(data){
					 console.log(data)
						var str = '';
						for(var i= 1;i<data.length;i++){
							var response = data[i];
							str += '<tr class="tbody_tr">'+
								'<td class="choose_td"><input type="checkbox"/></td>'+
								'<td class="edit_btn" id="'+response.ID+'" >'+(i)+'</td>'+
								'<td class="allUrgeBox_area">'+response.Area+'</td>'+
								'<td class="allUrgeBox_c" title="'+response.CustomerName+'">'+response.CustomerName+'</td>'+
								'<td class="allUrgeBox_applynum">'+response.ApplicationNo+'</td>'+
								'<td class="allUrgeBox_goodsname">'+response.ItemName+'</td>'+
								'<td class="allUrgeBox_applytime">'+response.ApplicationDate+'</td>'+
								'<td class="allUrgeBox_applyperson">'+response.Applicant+'</td>'+
								'<td class="allUrgeBox_agreeOrnot">'+'已通过'+'</td>'+
								'<td class="allUrgeBox_borrowtime">'+response.LoanDate+'</td>'+
								'<td class="allUrgeBox_returntime1">'+response.ExpectedReturnDate+'</td>'+
								'<td class="BorrowInfo_returnornot">'+response.IsReturn+'</td>'+
								'<td class="BorrowInfo_goodstype" style="display:none">'+response.Area+'</td>'+
								'<td class="BorrowInfo_Contact" style="display:none">'+response.Contact+'</td>'+
								'<td class="BorrowInfo_Department" style="display:none">'+response.Department+'</td>'+
								'<td class="BorrowInfo_Phone" style="display:none">'+response.Phone+'</td>'+
								'<td class="BorrowInfo_OperatingTime" style="display:none">'+response.OperatingTime+'</td>'+
								'</tr>';
						}
						$('.allUrgeBox_con tbody').html('').append(str);
				 }
			 })
			
		$('.mask').slideDown(300);
		$('.allUrge_box').slideDown(300);
	})
	//一键发送的全选
	$(document).on('click','.choose_btn',function(){
		if($(this).find('input').prop('checked') == true){
			$('.allUrge_box .tbody_tr .choose_td input').prop('checked',true);
		}else{
			$('.allUrge_box .tbody_tr .choose_td input').prop('checked',false);
		}
	})
	//一键催促的确定发送
	$(document).on('click','.allUrgeBox_ok',function(){
		var ids = '';
		var ids_arr = [];
		for(var i = 0;i< $('.allUrge_box .tbody_tr').length;i++){
			if( $('.allUrge_box .tbody_tr').eq(i).find('.choose_td input').prop('checked') == true){
				var id = $('.allUrge_box .tbody_tr').eq(i).find('.edit_btn').attr('id');
				ids_arr.push(id);
			}
		};
		ids = ids_arr.join(',');
		if(ids.length == 0){
			 $.MsgBox_Unload.Alert('提示','请至少选择一条申请信息进行催促！');
			 return;
		}
		$.ajax({
			type:'post',
			url:'LoanApplicationReviewList',
			data:{
				ids:ids	
			},
			beforeSend:function(){
				$('.allUrgeBox_ok').attr('disabled','disabled');
				$('.mask3').show();
				$('.progressBox').show();
			},
			success:function(data){
				 $.MsgBox.Alert('提示',data);
			},
			complete:function(){
				$('.allUrgeBox_ok').attr('disabled',false);
				$('.mask3').hide();
				$('.progressBox').hide();
			}
		})
	})
	
	
	
	