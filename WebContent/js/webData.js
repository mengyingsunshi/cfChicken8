		var hostname = window.location.hostname;
		var port = window.location.port;
		var filename = $('.fileName').text();
		
		
	$(document).on('click','.preview',function(){
		var date = $(this).parent().find('.date').text();
		var sheet = '';
		if($(this).hasClass('preview1')){
			sheet = 'Eoulu公司网站访问量统计表 ';
		}else if($(this).hasClass('preview2')){
			sheet = '关键词及消费明细 ';
		}else if($(this).hasClass('preview3')){
			sheet = '百度推广IP明细 ';
		}else if($(this).hasClass('preview4')){
			sheet = '360后台IP明细 ';
		}
		$.ajax({
			type:'get',
			url:'PromotionData',
			data:{
				loadType : 'preview',
				date  :  date,
				sheet : sheet
			},
			success:function(data){
				window.open('/'+data);
			}
		})

	})
		
	
	$('.closeExcel').click(function(){
		$('.mask').hide();
		$('.bar1').hide();
		$('.bar2').hide();
		$('.topline').hide();
		$('.iframeBox').hide();
		$('.closeExcel').hide();
	})	

	$('.flie_button').change(function(){
		if($(this).val() == ''){
			$('#box').text('点击或拖拽文件到此处上传');
		}else{
			 $('#box').text($(this).val().slice(12));
		}
	   
	});

 window.onload = function () {
  var oBox = document.getElementById('box');
  var oM = document.getElementById('m1');
  var timer = null;
  document.ondragover = function(){
   clearTimeout(timer);
   timer = setTimeout(function(){
    oBox.style.display = 'block';
   },200);
   oBox.style.display = 'block';
  };
  //进入子集的时候 会触发ondragover 频繁触发 不给ondrop机会
  oBox.ondragenter = function(){
   oBox.innerHTML = '请释放鼠标';
  };
  oBox.ondragover = function(){
   return false;
  };
  oBox.ondragleave = function(){
   oBox.innerHTML = '请将文件拖拽到此区域';
  };
  oBox.ondrop = function(ev){
   var oFile = ev.dataTransfer.files[0];
   var reader = new FileReader();
   //读取成功
   reader.onload = function(){
    console.log(reader);
   };
   reader.onloadstart = function(){
    alert('读取开始');
   };
   reader.onloadend = function(){
    alert('读取结束');
   };
   reader.onabort = function(){
    alert('中断');
   };
   reader.onerror = function(){
    alert('读取失败');
   };
   reader.onprogress = function(ev){
    var scale = ev.loaded/ev.total;
    if(scale>=0.5){
     alert(1);
     reader.abort();
    }
    oM.value = scale*100;
   };
   reader.readAsDataURL(oFile,'base64');
   return false;
  };
 };

 
 //获取日期列表
 var DateList = function(currentPage){
	 $.ajax({
		 type:'get',
		 url:'PromotionData',
		 dataType:'JSON',
		 data:{
			 loadType : 'date',
			 currentPage:currentPage
		 },
		 success:function(data){
			 console.log(data.data.length)
			 if(data.data.length == 1){
				 $('.dataBox_bg').show();
			 }else{
				 $('.dataBox_bg').hide();
				 var currentPage = data.currentPage;
				 var pageCount = data.pageCount;
				 var str = '';
					for(var i=1;i<data.data.length;i++){
						 str += '<tr class="dataBox_con">'+
									'<td id="'+data.data[i].ID+'">'+ (i+(currentPage-1)*10) +'</td>'+
									'<td class="date">'+ data.data[i].Date +'</td>'+
									'<td class="preview  preview1"></td>'+
									'<td class="preview  preview2"></td>'+
									'<td class="preview  preview3"></td>'+
									'<td class="preview  preview4"></td>'+
								'</tr>';
					 }
					  $('.dataBox tbody').empty().append(str);
					  $('#pageToolbar1').empty();
					  $('#pageToolbar1').Paging({pagesize:"1",current:currentPage,count:pageCount,toolbar:true});
					  $('.dataBox').show(); 
			 }
			
		 }
	 })
	
 }
 
$(function () {
	 	DateList(1) ;
		
           $("#FileUpload").change(function () {

               var fileObj = document.getElementById("FileUpload").files[0]; // js 获取文件对象
               if (typeof (fileObj) == "undefined" || fileObj.size <= 0) {
                   alert("请选择图片");
                   return;
               }
               var formFile = new FormData();
               formFile.append("file", fileObj); //加入文件对象
              
 
               var data = formFile;
               $.ajax({
                   url: "PromotionData",
                   data: data,
                   type: "Post",
                   dataType: "json",
                   cache: false,//上传文件无需缓存
                   processData: false,//用于对data参数进行序列化处理 这里必须false
                   contentType: false, //必须
                   success: function (result) {
                       console.log(result)
                       if(result == true){
                          	$.MsgBox.Alert("提示", "文件上传成功！");
                       }else{
                    		$.MsgBox.Alert("提示", "文件上传失败！");
                       }
                      
                   },
               })
           })
       })

//点击确定刷新页面
$(document).on("click", "#mb_btn_ok", function () {
    window.location.reload();
});
$(document).on('click','.js-page-prev,.pagenum,.js-page-next,.Gotojump',function(){
	var currentPage = $('.ui-paging-count').val();
	DateList(currentPage) ;
})
