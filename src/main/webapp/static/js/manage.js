$(function() {

	// 利用h5的history实现不后退效果
	if (window.history && window.history.pushState) {
		$(window).on('popstate', function() {
			window.history.pushState('forward', null, 'manage');
			window.history.forward(1);
		});
	}
	window.history.pushState('forward', null, 'manage');
	window.history.forward(1);

	// 注销
	$('#signOutBtn').click(function() {
		if (confirm('您确定要注销吗？')) {
			$.ajax({
				type : 'post',
				url : 'user/logout',
				dataType : 'json',
				success : function(data) {
					window.location.href = 'login';
				}
			});
		}
	});

	// 左边菜单切换效果
	$('#menu a').click(
			function() {
				$(this).addClass('active').siblings().not($(this)).removeClass(
						'active');
				var id = $(this).attr('id');
				var src = '';
				if (id == 'staff') {
					src = 'staffs/all?page=1&pageSize=8';
				} else if (id == 'punch') {
					src = 'punchs/all?page=1&pageSize=8';
				} else if (id == 'vacate') {
					src = 'vacates/all?page=1&pageSize=8';
				} else if (id == 'notice') {
					src = 'notices/all?page=1&pageSize=8';
				} else if (id == 'pwdBtn') {
					changePwd();
				}else {
					window.location.reload();
				}

				$('#pages').fadeOut(100, function() {
					$(this).attr('src', src).fadeIn(500)
				});
			});
});

// 修改密码
function changePwd(){
	$('#myModal').modal('show');
	$('#pwdForm input').val('');

	$('#updateBtn').off().click(function(e) {
		e.preventDefault();

		var pwd = $('#pwd').val();
		var newpwd = $('#newpwd').val();
		var repwd = $('#repwd').val();
		if (pwd == '' || newpwd == '' || repwd == '') {
			alert('您填写的信息不完整！');
			return false;
		}
		if (newpwd != repwd) {
			alert('确认密码与新密码不一致！')
			return false;
		}
		if (confirm('您确定要修改吗？')) {
			$.ajax({
				type : 'post',
				url : 'user/pwd',
				data : $('#pwdForm').serialize() + '&_method=put',
				dataType : 'json',
				success : function(data) {
					if (data.msg == 'success') {
						alert('修改成功！');
						window.location.href = 'login';
					} else {
						alert(data.msg);
					}
				}
			});
		}

	});
}

function toReloadPage() {
	window.location.reload();
}