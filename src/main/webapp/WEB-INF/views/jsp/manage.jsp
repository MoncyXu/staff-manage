<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% String basePath = request.getScheme() + "://" + request.getServerName() + 
			":" + request.getServerPort() + request.getContextPath() + "/";%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
	<head>
		<base href="<%=basePath%>">
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1"/>
		<title>管理页面</title>
		<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />
		<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
		<script src="js/jquery-3.2.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/manage.js" type="text/javascript" charset="utf-8"></script>
		<style type="text/css">
			* {
				font-family: "microsoft yahei"
			}
			
			html,
			body {
				height: 100%;
				width: 100%;
				overflow: hidden;
			}
			
			iframe {
				height: 100%;
				width: 100%;
				border: none;
				border-left: 1px solid #eee;
			}
		</style>
	</head>
	
	<body>
		<nav class="navbar navbar-expand-sm navbar-light bg-light fixed-top">
			<a href="#" class="navbar-brand"><i class="fa fa-users"></i>&nbsp;员工管理系统</a>
			<button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarContent">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div id="navbarContent" class="navbar-collapse collapse">
				<div class="navbar-nav ml-auto">
					<a href="javascript:void(0);" class="nav-item nav-link"><i class="fa fa-user"></i>&nbsp;${user.name }</a>
					<a id="signOutBtn" href="javascript:void(0);" class="nav-item nav-link ml-sm-3"><i class="fa fa-sign-out"></i>&nbsp;注销</a>
				</div>
			</div>
		</nav>
		<div class="container-fluid" style="height: 100%;">
			<div class="row" style="height: 100%;">
				<div class="col-md-3 col-lg-2">
					<div id="menu" class="list-group" style="padding-top: 80px;">
						<a href="javascript:void(0);" class="list-group-item list-group-item-action active"><i class="fa fa-fw fa-user-circle-o"></i>&nbsp;个人信息</a>
						<a id="staff" href="javascript:void(0);" class="list-group-item list-group-item-action"><i class="fa fa-fw fa-child"></i>&nbsp;员工管理</a>
						<a id="punch" href="javascript:void(0);" class="list-group-item list-group-item-action"><i class="fa fa-fw fa-vcard"></i>&nbsp;打卡管理</a>
						<a id="vacate" href="javascript:void(0);" class="list-group-item list-group-item-action"><i class="fa fa-fw fa-list-alt"></i>&nbsp;请假管理</a>
						<a id="notice" href="javascript:void(0);" class="list-group-item list-group-item-action"><i class="fa fa-fw fa-bullhorn"></i>&nbsp;通知管理</a>
						<a id="pwdBtn" href="javascript:void(0);" class="list-group-item list-group-item-action"><i class="fa fa-fw fa-lock"></i>&nbsp;修改密码</a>
					</div>
				</div>
				<div class="col-md-9 col-lg-10" style="height: 100%;">
					<iframe id="pages" src="personal" width="100%" height="100%"></iframe>
				</div>
			</div>
		</div>
		<div id="myModal" class="modal fade">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title"><i class="fa fa-edit"></i> 修改密码</h5>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body">
						<form id="pwdForm" class="px-5" method="post">
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-prepend">
										<label for="pwd" class="input-group-text">旧密码</label>
									</div>
									<input class="form-control" type="password" name="pwd" id="pwd" placeholder="请输入旧密码" />
								</div>
							</div>
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-prepend">
										<label for="newpwd" class="input-group-text">新密码</label>
									</div>
									<input class="form-control" type="password" name="newpwd" id="newpwd" placeholder="请输入新密码" />
								</div>
							</div>
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-prepend">
										<label for="repwd" class="input-group-text">确认密码</label>
									</div>
									<input class="form-control" type="password" name="repwd" id="repwd" placeholder="请输入新密码" />
								</div>
							</div>
							<div class="form-group text-right">
								<button id="updateBtn" class="btn btn-primary" type="submit">确认修改</button>
								<button class="btn btn-danger" type="button" data-dismiss="modal">取消</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>