$(function(){
	// 撤销批准
	$('#tbody').on('click','.cancelBtn',function(){
		
		if(confirm('确定要撤销该批准吗？')){
			var id = $(this).parent().siblings().eq(0).text();
			var that = $(this);
			
			$.ajax({
				type: 'post',
				url: 'vacates/countermand/'+id,
				dataType: 'json',
				data: '_method=put',
				success: function(data){
					if(data.msg == 'success'){
						that.addClass('d-none').siblings().removeClass('d-none');
						that.parent().prev().html(getState(data.vacate.approve));
					}else{
						alert(data.msg);
					}
				}
			});
		}
	});
	
	// 否决
	$('#tbody').on('click','.refuseBtn',function(){
		
		if(confirm('确定要否决该请假申请吗？')){
			var id = $(this).parent().siblings().eq(0).text();
			var that = $(this);
			
			$.ajax({
				type: 'post',
				url: 'vacates/decline/'+id,
				dataType: 'json',
				data: '_method=put',
				success: function(data){
					if(data.msg == 'success'){
						that.next().addBack().addClass('d-none').siblings('.dealBtn').removeClass('d-none');
						that.parent().prev().html(getState(data.vacate.approve));
					}else{
						alert(data.msg);
					}
				}
			});
		}
	});
	
	// 批准
	$('#tbody').on('click','.dealBtn',function(){
		
		if(confirm('确定要批准该请假申请吗？')){
			var id = $(this).parent().siblings().eq(0).text();
			var that = $(this);
			
			$.ajax({
				type: 'post',
				url: 'vacates/permission/'+id,
				dataType: 'json',
				data: '_method=put',
				success: function(data){
					if(data.msg == 'success'){
						that.next().addBack().addClass('d-none').siblings('.cancelBtn').removeClass('d-none');
						that.parent().prev().html(getState(data.vacate.approve));
					}else{
						alert(data.msg);
					}
				}
			});
		}
	});
	
	
	// 添加员工
	$('#addBtn').click(function(){
		modal.title.html('<i class="fa fa-plus"></i> 添加记录');
		modal.show();
		$('#insertBtn').text('添加');
		$('#addForm input,#addForm select,#addForm textarea').val('');
		
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
			
			var starttime = getDateTime($('#starttime').val());
			var endtime = getDateTime($('#endtime').val());
			
			$.ajax({
				type: 'post',
				url: 'vacates',
				data: $('#addForm').serialize()+'&starttime='+starttime+'&endtime='+endtime,
				dataType: 'json',
				success: function(data){
					if(data.msg == 'success'){
						var vacate = data.vacate;
						var tr = '<tr><td>'+vacate.id+'</td><td>'+vacate.user+'</td><td>'+getDateTime(vacate.time)+'</td><td>'+vacate.reason+'</td><td>'+getDateTime(vacate.starttime)+'</td>'
						+'<td>'+getDateTime(vacate.endtime)+'</td><td>'+getState(vacate.approve)+'</td><td><button class="dealBtn btn btn-sm btn-success">批准</button>'
						+'<button class="refuseBtn btn btn-sm btn-danger">否决</button><button class="cancelBtn btn btn-sm btn-danger d-none">撤销批准</button></td></tr>';
						$('#tbody').append(tr);
						modal.hide();
					}else{
						alert(data.msg);
					}
				}
			});
		});
	});
	
	// 状态转换
	function getState(state){
		if(state == 0){
			return '<span class="text-warning">待审批</span>';
		}else if(state == 1){
			return '<span class="text-success">已批准</span>';
		}else if(state == -1){
			return '<span class="text-danger">未批准</span>';
		}
	}
	
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
		window.location.href = 'vacates/all?page='+currentPage+'&pageSize='+pageSize;
	});
});