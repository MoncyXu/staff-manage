<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% String basePath = request.getScheme() + "://" + request.getServerName() + 
			":" + request.getServerPort() + request.getContextPath() + "/";%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>通知管理</title>
		<link rel="stylesheet" type="text/css" href="css/am-pagination.css" />
		<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />
		<script src="js/jquery-3.2.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/am-pagination.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/notice.js" type="text/javascript" charset="utf-8"></script>
	</head>
	
	<body>
		<div class="container-fluid" style="padding-top: 80px;">
			<div class="row px-3">
				<button id="addBtn" type="button" class="btn btn-primary my-3"><i class="fa fa-plus"></i> 发布通知</button>
				<form class="col-sm-4 offset-sm-3 my-auto" action="notices/fuzzy">
					<div class="form-group mb-0">
						<div class="input-group">
							<input type="text" name="title" class="form-control" placeholder="通过标题搜索" required />
							<span class="input-group-append">
								<button type="submit" class="btn btn-info">搜索</button>
							</span>
						</div>
					</div>
				</form>
			</div>
			<div class="table-responsive-md">
				<table class="table table-bordered table-hover">
					<thead class="thead-light">
						<tr>
							<th>#ID</th>
							<th>标题</th>
							<th>内容</th>
							<th>时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="tbody">
						<c:forEach items="${data.notices }" var="notice">
							<tr>
								<td>${notice.id }</td>
								<td>${notice.title }</td>
								<td>${notice.content }</td>
								<td><fmt:formatDate value="${notice.time }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
								<td>
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
							<h5 id="modalTitle"><i class="fa fa-plus"></i> 发布通知</h5>
							<button type="button" class="close" data-dismiss="modal">
								<span>&times;</span>
							</button>
						</div>
						<div id="modalBody" class="modal-body">
							<form id="addForm" action="post" method="post" class="px-4">
								<div class="form-group row d-none">
									<label class="col-md-3 col-form-label text-md-center">#ID</label>
									<input class="form-control col-md-9" type="text" id="nId" name="id" required placeholder="请输入标题" />
								</div>
								<div class="form-group row">
									<label class="col-md-3 col-form-label text-md-center">标题</label>
									<input class="form-control col-md-9" type="text" id="title" name="title" required placeholder="请输入标题" />
								</div>
								<div class="form-group row">
									<label class="col-md-3 col-form-label text-md-center">内容</label>
									<textarea name="content" rows="3" id="content" class="form-control col-md-9" placeholder="请输入内容" ></textarea>
								</div>
								<div class="form-group row">
									<label class="col-md-3 col-form-label text-md-center">员工</label>
									<select class="form-control col-md-9" name="staffs" multiple="multiple">
										<option value="" selected>请选择员工...</option>
										<c:forEach items="${data.staffs }" var="staff">
											<option value="${staff.id }" >${staff.name }</option>
										</c:forEach>
									</select>
								</div>
							</form>
						</div>
						<div id="modalFooter" class="modal-footer">
							<button type="button" class="btn btn-primary" id="insertBtn">发布</button>
							<button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
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