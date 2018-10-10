$(function() {
	//删除
	$('#staffsTable').on('click','.deleteBtn',function(){
		if(confirm('您确定要删除该用户吗？')){
			var id = $(this).parent().siblings().eq(0).text();
			var that = $(this);
			
			$.ajax({
				type: 'post',
				url: 'staffs/'+id,
				data: '_method=delete',
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
	
	// 修改
	$('#staffsTable').on('click','.updateBtn',function(){
		// 设置模态框
		modal.title.html('<i class="fa fa-edit"></i> 修改信息');
		modal.show();
		$('#insertBtn').text('修改');
		$('#newPassword').parent().hide();
		// 清空表单
		$('#addForm input').val('');
		
		var id = $(this).parent().siblings().eq(0).text();
		var name = $(this).parent().siblings().eq(1).text();
		var position = $(this).parent().siblings().eq(2).text();
		var tel = $(this).parent().siblings().eq(3).text();
		var that = $(this);
		
		$('#theId').val(id);
		$('#newName').val(name);
		$('#newPosition').val(position);
		$('#newTel').val(tel);
		
		$('#insertBtn').off().click(function(e){
			e.preventDefault();
			
			if($('#theId').val() == '' || $('#newName').val() == '' || $('#newPosition').val() == '' || $('#newTel').val() == ''){
				alert('您填写的信息不完整！')
				return false;
			}
			
			if(confirm('您确定要进行修改吗？')){
				$.ajax({
					type: 'post',
					url: 'staffs',
					data: $('#addForm').serialize()+'&_method=put',
					dataType: 'json',
					success: function(data){
						if(data.msg == '修改成功'){
							modal.hide();
							var user = data.user;
							that.parent().siblings().eq(1).text(user.name);
							that.parent().siblings().eq(2).text(user.position);
							that.parent().siblings().eq(3).text(user.tel);
						}else{
							alert(data.msg);
						}
					}
				});
			}
		});
	});
	
	// 重置密码
	$('#staffsTable').on('click','.resetBtn',function(){
		if(confirm('您确定要重置该用户的密码吗？')){
			var id = $(this).parent().siblings().eq(0).text();
			
			$.ajax({
				type: 'post',
				url: 'staffs/pwd',
				data: '_method=put&id='+id,
				dataType: 'json',
				success: function(data){
					alert(data.msg);
				}
			});
		}
	});
	
	
	// 添加员工
	$('#addBtn').click(function(){
		modal.title.html('<i class="fa fa-plus"></i> 添加员工');
		modal.show();
		$('#insertBtn').text('添加');
		$('#newPassword').parent().show();
		$('#addForm input').val('');
		
		$('#insertBtn').off().click(function(e){
			e.preventDefault();
			
			var flag = false;
			var addData = $('#addForm').serializeArray();
			
			$.each(addData,function(index,data){
				var obj = data;
				if(index == 0)
					return true;
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
				url: 'staffs',
				data: $('#addForm').serialize(),
				dataType: 'json',
				success: function(data){
					if(data.msg == '添加成功'){
						var user = data.user;
						var tr = '<tr><td>'+user.id+'</td><td>'+user.name+'</td><td>'+user.position+'</td><td>'+user.tel+'</td><td>'+user.time+'</td>'
						+'<td><button type="button" class="resetBtn btn btn-sm btn-info">重置密码</button> <button type="button" class="updateBtn btn btn-sm btn-warning text-white">修改</button>'
						+' <button type="button" class="deleteBtn btn btn-sm btn-danger">删除</button></td></tr>';
						$('#tbody').append(tr);
						modal.hide();
					}else{
						alert(data.msg);
					}
				}
			});
		});
	});
	
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
		window.location.href = 'staffs/all?page='+currentPage+'&pageSize='+pageSize;
	});
});