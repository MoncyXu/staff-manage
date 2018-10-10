$(function(){
	// 添加记录
	$('#addBtn').click(function(){
		// 设置模态框
		modal.title.html('<i class="fa fa-plus"></i> 添加记录');
		modal.show();
		$('#insertBtn').text('添加');
		// 清空表单
		$('#addForm input,#addForm select').val('');
		
		$('#insertBtn').off().click(function(e){
			e.preventDefault();
			
			var flag = false;
			var addData = $('#addForm').serializeArray();
			
			$.each(addData,function(index,data){
				var obj = data;
				$.each(data,function(key,value){
					if(value == ''){
						alert('您填写的信息不完整！');
						flag = true;
					}
				});
				if(flag)
					return false;
			});
			if(flag)
				return false;
			
			var uid = $('#uId').val();
			var dutytime = getDateTime($('#dutytime').val());
			var undergo = getDateTime($('#undergo').val());
			
			$.ajax({
				type: 'post',
				url: 'punchs',
				data: {
					uId: uid,
					dutytime: dutytime,
					undergo: undergo
				},
				dataType: 'json',
				success: function(data){
					if(data.msg == 'success'){
						var punch = data.punch;
						var tr = '<tr><td>'+punch.id+'</td><td>'+punch.user+'</td><td>'+getDateTime(punch.time)+'</td><td>'+getDateTime(punch.dutytime)+'</td><td>'+getDateTime(punch.undergo)+'</td></tr>';
						$('#tbody').append(tr);
						modal.hide();
					}else{
						alert(data.msg);
					}
				}
			});
		});
	});
	
	// 时间转换
	function getDateTime(ms) {
		var d = new Date(ms);
		var year = d.getFullYear();
		var month = d.getMonth() + 1;
		var day = d.getDate();
		var hh = d.getHours();
		var mm = d.getMinutes();
		var ss = d.getSeconds();
		month = fillZero(month);
		day = fillZero(day);
		hh = fillZero(hh);
		mm = fillZero(mm);
		ss = fillZero(ss);
		return year + '-' + month + '-' + day + ' ' + hh + ':' + mm + ':' + ss;

	}

	function fillZero(t) {
		if (t < 10) {
			t = '0' + t;
		}
		return t;
	}
	
	// 模态框
	var modal = {
			title: $('#modalTitle'),
			body: $('#modalBody'),
			show: function(){$('#myModal').modal('show')},
			hide: function(){$('#myModal').modal('hide')}
	};
	
	// 分页插件
	$('#paginator').pagination({
		maxSize : 8,
		totals : totalPages,
		page : currentPage,
		pageSize : pageSize,
		lastText : '末页',
		firstText : '首页',
		prevText : '前一页',
		nextText : '下一页',
		rotate : true,
		directionLinks : true,
		boundaryLinks : true,
	}).onChangePage(function(e) {
		currentPage = e.page;
		window.location.href = 'punchs/all?page='+currentPage+'&pageSize='+pageSize;
	});
});