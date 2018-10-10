<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% String basePath = request.getScheme() + "://" + request.getServerName() + 
			":" + request.getServerPort() + request.getContextPath() + "/";%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
	<head>
		<base href="<%=basePath%>">
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1"/>
		<title>打卡信息</title>
		<link rel="stylesheet" type="text/css" href="css/am-pagination.css" />
		<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />
		<script src="js/jquery-3.2.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/am-pagination.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/punchs.js" type="text/javascript" charset="utf-8"></script>
	</head>
	
	<body>
		<div class="container-fluid" style="padding-top: 80px;">
			<div class="row px-3">
				<button id="addBtn" type="button" class="btn btn-primary my-3"><i class="fa fa-plus"></i> 添加记录</button>
				<form class="col-sm-4 offset-sm-3 my-auto" action="punchs/fuzzy">
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
				<table class="table table-bordered table-hover">
					<thead class="thead-light">
						<tr>
							<th>#ID</th>
							<th>员工</th>
							<th>日期</th>
							<th>到岗时间</th>
							<th>离岗时间</th>
						</tr>
					</thead>
					<tbody id="tbody">
						<c:forEach items="${data.punchs }" var="punch" >
							<tr>
								<td>${punch.id }</td>
								<td>${punch.user }</td>
								<td><fmt:formatDate value="${punch.time }" pattern="yyyy-MM-dd" /></td>
								<td><fmt:formatDate value="${punch.dutytime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value="${punch.undergo }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
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
							<h5 id="modalTitle">添加记录</h5>
							<button type="button" class="close" data-dismiss="modal">
								<span>&times;</span>
							</button>
						</div>
						<div id="modalBody" class="modal-body border-bottom pb-2">
							<form id="addForm" method="post" class="px-4">
								<div class="form-group row">
									<label class="col-md-3 col-form-label text-md-center">用户</label>
									<select id="uId" name="uId" class="form-control col-md-9">
										<option value="" selected>请选择...</option>
										<c:forEach items="${data.staffs }" var="staff" >
											<option value="${staff.id }">${staff.name }</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group row">
									<label class="col-md-3 col-form-label text-md-center">到岗时间</label>
									<input class="form-control col-md-9" type="datetime-local" id="dutytime" name="dutytime" required placeholder="请输入到岗时间" />
								</div>
								<div class="form-group row">
									<label class="col-md-3 col-form-label text-md-center">离岗时间</label>
									<input class="form-control col-md-9" type="datetime-local" id="undergo" name="undergo" required placeholder="请输入离岗时间" />
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