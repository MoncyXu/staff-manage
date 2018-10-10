<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% String basePath = request.getScheme() + "://" + request.getServerName() + 
			":" + request.getServerPort() + request.getContextPath() + "/";%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html lang="zh-CN">
	<head>
		<base href="<%=basePath%>">
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1"/>
		<title>个人信息</title>
		<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css"/>
		<script src="js/jquery-3.2.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/personal.js" type="text/javascript" charset="utf-8"></script>
		<style type="text/css">
			*{
				font-family: "microsoft yahei"
			}
			.center-box{
				position: absolute;
				top: 0;
				right: 0;
				bottom: 0;
				left: 0;
				margin: auto;
			}
		</style>

	</head>
	
	<body>
		<div class="center-box col-md-4 h-75 px-5">
			<div class="card border">
				<div class="card-header bg-primary text-white">个人信息</div>
				<div class="card-body">
					<form id="infoForm" method="post">
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-prepend">
									<label class="input-group-text bg-light" for="username">姓&emsp;名</label>
								</div>
								<input class="form-control" type="text" name="name" id="username" value="${user.name }" readonly required />
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-prepend">
									<label class="input-group-text bg-light" for="position">职&emsp;位</label>
								</div>
								<input class="form-control" type="text" name="position" id="position" value="${user.position }" readonly required />
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-prepend">
									<label class="input-group-text bg-light" for="tel">手机号</label>
								</div>
								<input class="form-control" type="text" name="tel" id="tel" value="${user.tel }" readonly required />
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-prepend">
									<label class="input-group-text bg-light" for="tel">入职时间</label>
								</div>
								<input class="form-control" type="text" id="time" value='<fmt:formatDate value="${user.time }" pattern="yyyy-MM-dd HH:mm:ss" /> ' readonly required />
							</div>
						</div>
						<button id="updateBtn" type="button" class="btn btn-warning text-white btn-block">修改</button>
						<div class="form-group text-center d-none">
							<button id="readyUpdateBtn" type="submit" class="btn btn-primary px-md-4">修改</button>
							<button id="cancelUpdate" type="button" class="btn btn-danger px-md-4 ml-2">取消</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>