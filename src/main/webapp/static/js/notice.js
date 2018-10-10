$(function(){
	// 删除通知
	$('#tbody').on('click','.deleteBtn',function(){
		if(confirm('您确定要删除该通知吗？')){
			var id = $(this).parent().siblings().eq(0).text();
			var that = $(this);
			
			$.ajax({
				type: 'post',
				url: 'notices/'+id,
				data: '_method=delete',
				dataType: 'json',
				success: function(data){
					if(data.msg == 'success'){
						that.parent().parent().remove();
					}else{
						alert(data.msg);
					}
				}
			});
		}
		
	});
	
	// 修改通知
	$('#tbody').on('click','.updateBtn',function(){
		// 设置模态框
		modal.title.html('<i class="fa fa-plus"></i> 修改通知');
		modal.show();
		$('#insertBtn').text('修改');
		// 清空表单
		$('#addForm input,#addForm select,#addForm textarea').val('');
		var id = $(this).parent().siblings().eq(0).text();
		var title = $(this).parent().siblings().eq(1).text();
		var content = $(this).parent().siblings().eq(2).text();
		$('#nId').val(id);
		$('#title').val(title);
		$('#content').val(content);
		
		$('#insertBtn').off().click(function(e){
			e.preventDefault();
			
			var flag = false;
			var addData = $('#addForm').serializeArray();
			
			$.each(addData,function(index,data){
				var obj = data;
				if(index == 0 || addData.length-1) return true;
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
			
			$.ajax({
				type: 'post',
				url: 'notices',
				data: $('#addForm').serialize()+'&_method=put',
				dataType: 'json',
				success: function(data){
					if(data.msg == 'success'){
						var notice = data.notice;
						modal.hide();
						$(this).parent().siblings().eq(1).text(notice.title);
						$(this).parent().siblings().eq(2).text(notice.content);
					}else{
						alert(data.msg);
					}
				}
			});
		});
	});
	
	// 发布通知
	$('#addBtn').click(function(){
		// 设置模态框
		modal.title.html('<i class="fa fa-plus"></i> 发布通知');
		modal.show();
		$('#insertBtn').text('发布');
		// 清空表单
		$('#addForm input,#addForm select,#addForm textarea').val('');
		
		$('#insertBtn').off().click(function(e){
			e.preventDefault();
			
			var flag = false;
			var addData = $('#addForm').serializeArray();
			
			$.each(addData,function(index,data){
				var obj = data;
				if(index == 0 || addData.length-1) return true;
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
			
			$.ajax({
				type: 'post',
				url: 'notices',
				data: $('#addForm').serialize(),
				dataType: 'json',
				success: function(data){
					if(data.msg == 'success'){
						var notice = data.notice;
						var tr = '<tr><td>'+notice.id+'</td><td>'+notice.title+'</td><td>'+notice.content+'</td><td>'+getDateTime(notice.time)+'</td>'
						+'<td><button type="button" class="updateBtn btn btn-sm btn-warning text-white">修改</button><button type="button" class="deleteBtn btn btn-sm btn-danger">删除</button></td></tr>';
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