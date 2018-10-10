<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% String basePath = request.getScheme() + "://" + request.getServerName() + 
			":" + request.getServerPort() + request.getContextPath() + "/";%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>员工页面</title>
		<link rel="stylesheet" type="text/css" href="css/am-pagination.css" />
		<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />
		<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
		<script src="js/jquery-3.2.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/am-pagination.min.js" type="text/javascript" charset="utf-8"></script>
	</head>
	
	<body>
		<div class="card text-center">
			<div class="card-header">
				<ul class="nav nav-pills card-header-pills">
					<li class="nav-item">
						<a data-toggle='list' href="#myInfo" class="nav-link active"><i class="fa fa-user-circle-o"></i> 我的信息</a>
					</li>
					<li class="nav-item">
						<a data-toggle='list' href="#notice" class="nav-link"><i class="fa fa-bullhorn"></i> 通知</a>
					</li>
					<li class="nav-item">
						<a data-toggle='list' href="#punch" class="nav-link"><i class="fa fa-id-card"></i> 打 卡</a>
					</li>
					<li class="nav-item">
						<a data-toggle='list' href="#vacate" class="nav-link"><i class="fa fa-bookmark"></i> 请 假</a>
					</li>
					<li class="nav-item ml-auto">
						<a data-toggle='list' href="#" class="nav-link"><i class="fa fa-user"></i> 你好</a>
					</li>
					<li class="nav-item">
						<a data-toggle='list' href="javascript:void(0);" class="nav-link"><i class="fa fa-sign-out"></i> 注销</a>
					</li>
				</ul>
			</div>
			<div class="card-body">
				<div class="tab-content">
					<div id="myInfo" class="tab-pane fade show active">
						<div class="mx-auto my-5 col-md-4 h-75 px-5">
							<div class="card border">
								<div class="card-header bg-primary text-white">个人信息</div>
								<div class="card-body">
									<form method="post">
										<div class="form-group">
											<div class="input-group">
												<div class="input-group-prepend">
													<label class="input-group-text bg-light" for="username">姓&emsp;名</label>
												</div>
												<input class="form-control" type="text" name="name" id="username" value="${user.name }" disabled required />
											</div>
										</div>
										<div class="form-group">
											<div class="input-group">
												<div class="input-group-prepend">
													<label class="input-group-text bg-light" for="position">职&emsp;位</label>
												</div>
												<input class="form-control" type="text" name="position" id="position" value="${user.position }" disabled required />
											</div>
										</div>
										<div class="form-group">
											<div class="input-group">
												<div class="input-group-prepend">
													<label class="input-group-text bg-light" for="tel">手机号</label>
												</div>
												<input class="form-control" type="text" name="tel" id="tel" value="${user.tel }" disabled required />
											</div>
										</div>
										<div class="form-group">
											<div class="input-group">
												<div class="input-group-prepend">
													<label class="input-group-text bg-light" for="tel">入职时间</label>
												</div>
												<input class="form-control" type="text" name="tel" id="tel" value='<fmt:formatDate value="${user.time }" pattern="yyyy-MM-dd HH:mm:ss" />' disabled />
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<div id="notice" class="tab-pane fade">
						<div class="table-responsive">
							<table class="table table-striped table-hover table-bordered">
								<thead>
									<tr>
										<th class="d-none">#ID</th>
										<th>标题</th>
										<th>内容</th>
										<th>时间</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="d-none">asdf</td>
										<td>title</td>
										<td>content</td>
										<td>time</td>
									</tr>
								</tbody>
							</table>
							<div class="text-center">
								<div id="noticePaginator"></div>
							</div>
						</div>
					</div>
					<div id="punch" class="tab-pane fade">
						<button type="button" class="btn btn-lg btn-primary col-md-5"><i class="fa fa-calendar"></i> 到岗打卡</button>
						<div class="d-sm-block d-md-none pt-3"></div>
						<button type="button" class="btn btn-lg btn-warning col-md-5 text-white"><i class="fa fa-calendar"></i> 离岗打卡</button>
						<div class="table-responsive mt-4">
							<table class="table table-striped table-hover table-bordered table-sm">
								<thead>
									<tr>
										<th class="d-none">#ID</th>
										<th>到岗时间</th>
										<th>离岗时间</th>
										<th>日期</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="d-none">asdf</td>
										<td>2018-7-27 00:08:32</td>
										<td>2018-7-27 00:08:35</td>
										<td>2018-7-27</td>
									</tr>
								</tbody>
							</table>
							<div class="text-center">
								<div id="punchPaginator"></div>
							</div>
						</div>
					</div>
					<div id="vacate" class="tab-pane fade">
						<form class="col-md-4 mx-auto text-left" action="#" method="post">
							<div class="form-row">
								<div class="col-md-6">
									<label for="starttime">离岗时间</label>
									<input class="form-control" type="date" name="starttime" id="starttime" value="" required />
								</div>
								<div class="col-md-6">
									<label for="starttime">返岗时间</label>
									<input class="form-control" type="date" name="endtime" id="endtime" value="" required />
								</div>
							</div>
							<div class="form-group mt-2">
								<label for="">请假事因</label>
								<textarea style="resize: none;" required name="reason" rows="5" maxlength="60" class="form-control"></textarea>
							</div>
							<input class="btn btn-lg btn-block btn-primary" type="submit" id="submitBtn" value="提交申请" />
						</form>
						<div class="container mt-5">
							<div class="table-responsive">
								<table class="table table-striped table-hover table-bordered">
									<thead>
										<tr>
											<th class="d-none">#ID</th>
											<th>请假事因</th>
											<th>开始时间</th>
											<th>结束时间</th>
											<th>日期</th>
											<th>状态</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="d-none">asdf</td>
											<td>title</td>
											<td>content</td>
											<td>time</td>
											<td>title</td>
											<td>content</td>
											<td>
												<button class="cancelApply btn btn-sm btn-warning text-white">撤销申请</button>
											</td>
										</tr>
									</tbody>
								</table>
								<div class="text-center">
									<div id="vacatePaginator"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		if(window.history && window.history.pushState){
			$(window).on('popstate',function(){
				window.history.pushState('forward',null,'employee');
				window.history.forward(1);
			});
		}
		window.history.pushState('forward',null,'employee');
		window.history.forward(1);
		
		$('#noticePaginator,#punchPaginator,#vacatePaginator').pagination({
			maxSize: 7,
			totals: 100,
			page: 1,
			pageSize: 10,
			lastText: '末页',
			firstText: '首页',
			prevText: '前一页',
			nextText: '下一页',
			rotate: true,
			directionLinks: true,
			boundaryLinks: true,
		});
	</script>
</html>