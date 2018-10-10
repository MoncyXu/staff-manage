<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% String basePath = request.getScheme() + "://" + request.getServerName() + 
			":" + request.getServerPort() + request.getContextPath() + "/";%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
	<head>
		<base href="<%=basePath%>">
		<title>First.jsp</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1"/>
		<title>员工信息</title>
		<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />
		<link rel="stylesheet" type="text/css" href="css/am-pagination.css" />
		<script src="js/jquery-3.2.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/am-pagination.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/staffs.js" type="text/javascript" charset="utf-8"></script>
		<style type="text/css">
			* {
				font-family: "microsoft yahei"
			}
		</style>
	</head>
	
	<body>
		<div class="container-fluid" style="padding-top: 80px;">
			<div class="row px-3">
				<button id="addBtn" type="button" class="btn btn-primary my-3"><i class="fa fa-plus"></i> 添加员工</button>
				<form class="col-sm-4 offset-sm-3 my-auto" action="staffs/fuzzy">
					<div class="form-group mb-0">
						<div class="input-group">
							<input type="text" name="name" class="form-control" placeholder="通过员工名搜索" required />
							<span class="input-group-append">
								<button type="submit" class="btn btn-info">搜索</button>
							</span>
						</div>
					</div>
				</form>
			</div>
			<div class="table-responsive-md">
				<table id="staffsTable" class="table table-bordered table-hover">
					<thead class="thead-light">
						<tr>
							<th>#ID</th>
							<th>姓名</th>
							<th>职位</th>
							<th>电话</th>
							<th>入职时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="tbody">
						<c:forEach var="staff" items="${data.staffs }" >
							<tr>
								<td>${staff.id }</td>
								<td>${staff.name }</td>
								<td>${staff.position }</td>
								<td>${staff.tel }</td>
								<td><fmt:formatDate value="${staff.time }" pattern="yyyy-MM-dd HH:mm:ss" /> </td>
								<td>
									<button type="button" class="resetBtn btn btn-sm btn-info">重置密码</button>
									<button type="button" class="updateBtn btn btn-sm btn-warning text-white">修改</button>
									<button type="button" class="deleteBtn btn btn-sm btn-danger">删除</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="text-center">
					<div id="paginator"></div>
				</div>
			</div>
			<div id="myModal" class="modal fade">
				<div class="modal-dialog modal-dialog-centered">
					<div class="modal-content">
						<div class="modal-header">
							<h5 id="modalTitle">添加员工</h5>
							<button type="button" class="close" data-dismiss="modal">
								<span>&times;</span>
							</button>
						</div>
						<div id="modalBody" class="modal-body border-bottom pb-2">
							<form id="addForm" method="post" class="px-4">
								<div class="form-group row d-none">
									<label class="col-md-3 col-form-label text-md-center">#ID</label>
									<input class="form-control col-md-9" id="theId" type="text" name="id" required />
								</div>
								<div class="form-group row">
									<label class="col-md-3 col-form-label text-md-center">姓名</label>
									<input class="form-control col-md-9" id="newName" type="text" name="name" required placeholder="请输入姓名" />
								</div>
								<div class="form-group row">
									<label class="col-md-3 col-form-label text-md-center">职位</label>
									<input class="form-control col-md-9" id="newPosition" type="text" name="position" required placeholder="请输入职位" />
								</div>
								<div class="form-group row">
									<label class="col-md-3 col-form-label text-md-center">电话</label>
									<input class="form-control col-md-9" id="newTel" type="text" name="tel" required placeholder="请输入电话" />
								</div>
								<div class="form-group row">
									<label class="col-md-3 col-form-label text-md-center">密码</label>
									<input class="form-control col-md-9" id="newPassword" type="password" name="pwd" required placeholder="请输入密码" />
								</div>
								<div class="form-group row">
									<button type="submit" class="btn btn-primary ml-auto mr-2" id="insertBtn">添加</button>
									<button type="button" class="btn btn-danger" data-dismiss="modal" >取消</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		var currentPage = '${data.page.currentPage }',totalPages = '${data.page.totalPages }',pageSize = '${data.page.pageSize }';
	</script>
</html>