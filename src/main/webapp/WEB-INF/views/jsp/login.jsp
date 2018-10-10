<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% String basePath = request.getScheme() + "://" + request.getServerName() + 
			":" + request.getServerPort() + request.getContextPath() + "/";%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
	<head>
		<base href="<%=basePath%>">
		<meta name="viewport" content="width=device-width, initial-scale=1"/>
		<meta charset="UTF-8">
		<title>员工管理系统</title>
		<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="css/login.css"/>
		<script src="js/jquery-3.2.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
	</head>
  
	<body class="bg-light">
		<div class="center-box">
			<h4 class="text-center text-white mt-3">员工管理系统</h4>
			<form class="px-5 pt-4" action="user/login" method="post">
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-prepend">
							<label class="input-group-text" for="tel"><i class="fa fa-user"></i></label>
						</div>
						<input id="tel" name="tel" type="tel" class="form-control <c:if test="${info.msg == 'fail'}">is-invalid</c:if>" required placeholder="手机号" />
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-prepend">
							<label class="input-group-text" for="pwd"><i class="fa fa-lock"></i></label>
						</div>
						<input id="pwd" name="pwd" type="password" class="form-control <c:if test="${info.msg == 'fail'}">is-invalid</c:if>" required placeholder="密码" />
						<div class="invalid-feedback text-white text-center mt-2">
							<strong>用户名或密码错误！</strong>
						</div>
					</div>
				</div>
				<div class="form-group text-center pt-1">
					<button type="submit" class="btn btn-success px-4">登&emsp;录</button>
					<button type="button" class="btn btn-primary px-3 ml-4">手机登录</button>
				</div>
			</form>
		</div>
	</body>
	<script type="text/javascript">
		//防止页面后退
		if(window.history && window.history.pushState){
			$(window).on('popstate',function(){
				window.history.pushState('forward',null,'login');
				window.history.forward(1);
			});
		}
		window.history.pushState('forward',null,'login');
		window.history.forward(1);
		
		$('#tel,#pwd').click(function(){
			$(this).removeClass('is-invalid');
		});
	</script>
</html>