//切换单一和组合搜索
function Check(selected) {
	 if (selected == "singleSelect") {
	        $('.select2').hide();
	        $('.select-content').css('margin-left','33%');
	    } else {
	        $('.select2').show();
	        $('.select-content').css('margin-left','23%');
	    }
}
//单一和组合的回显
if($('input[name="selected"]:checked').val() == 'mixSelect'){
		$('.select-content').css('margin-left','23%');
		 $('.select2').show();
}else{
	    $('.select-content').css('margin-left','33%');
	    $('.select2').hide();
}

function INSearch() {
    $('#search').val('search');
    $('#top_text_from').submit();
}
function Cancel() {
   /* $('#search').val('cancel');
    $('input[name="searchContent1"]').val('');
    $('input[name="searchContent2"]').val('');
    $('#top_text_from').();*/
	window.location.href = 'Quality';
}
$('#searchContent1').keypress(function (event) {
    $('#search').val('search');
    var keynum = (event.keyCode ? event.keyCode : event.which);
    if (keynum == '13') {
        $('#top_text_from').submit();
    }
});
$('#searchContent2').keypress(function (event) {
    $('#search').val('search');
    var keynum = (event.keyCode ? event.keyCode : event.which);
    if (keynum == '13') {
        $('#top_text_from').submit();
    }
});