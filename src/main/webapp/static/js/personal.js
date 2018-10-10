$(function(){
	
	//点击确认更新按钮
	$('#readyUpdateBtn').off().click(function(e){
		e.preventDefault();
		
		if($('#username').val() == '' || $('#position').val() == ''
			|| $('#tel').val() == ''){
			alert('您填写的信息不完整！');
			return false;
		};
		
		$.ajax({
			type: 'post',
			url: 'user',
			data: $('#infoForm').serialize()+'&_method=put',
			dataType: 'json',
			success: function(data) {
				if(data.msg == '修改成功'){
					alert("修改成功，请重新登录！");
					//调用父窗口的reload();方法
					window.top.toReloadPage();
				}else{
					alert(data.msg);
				}
			}
		});
		
	});
	
	//点击修改按钮显示其他按钮
	$('#updateBtn').click(function(){
		$(this).fadeOut(300,function(){
			$(this).next().removeClass('d-none');
		});
		$('#username,#position,#tel').removeAttr('readonly');
	});
	$('#cancelUpdate').click(function(){
		readonlyChange();
	});
	function readonlyChange(){
		$('#cancelUpdate').parent().addClass('d-none').prev().fadeIn(300);
		$('#username,#position,#tel').attr('readonly','readonly');
	}
});
