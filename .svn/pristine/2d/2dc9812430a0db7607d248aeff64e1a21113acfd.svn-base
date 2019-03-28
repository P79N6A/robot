<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.bossbutler.common.InitConf" language="java"%>
<%
	String ctxPath = request.getContextPath();
	request.setAttribute("subctxPath", ctxPath);
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="点融接口测试">
<title>点融接口测试</title>
<!-- Bootstrap -->
<link href="${subctxPath}/resources/app/css/bootstrap.min.css"
	rel="stylesheet"/>
<link href="${subctxPath}/resources/app/css/dashboard.css"
	rel="stylesheet"/>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="${subctxPath}/resources/app/js/app/html5shiv.js"></script>
      <script src="${subctxPath}/resources/app/js/app/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="javascript:void(0);">点融接口测试</a>
			</div>
		</div>
	</nav>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li><a href="testPostDR.jsp" target="_top">包办移动端 授权并Post用户信息到点融应用</a></li>
					<li class="active"><a href="testDRGet.jsp" target="_top">点融应用请求包办服务·Get统计信息</a></li>
				</ul>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">点融应用请求包办服务·Get统计信息</h1>
				<div class="row">
					<div class="col-xs-12 col-sm-12">
						<form action="<%=InitConf.getSelfhost()%>/third/dr/query/detail/transit" method="post" target="detail_name">
						  <div class="form-group">
						    <label>请求的包办服务地址url</label>
						    <p><%=InitConf.getSelfhost()%>/third/serve/auth/query/statistics/inf/6ebd9acf58e84517896ba64b308a90f3</p>
						  </div>
						  <div class="form-group">
						    <label for="J_businessCode_id">查询编号</label>
						    <input type="text" class="form-control" name="businessCode" id="J_businessCode_id" placeholder="查询编号" value="Analyze001" readonly>
						  </div>
						  <div class="form-group">
						    <label for="J_pageNum_id">页码</label>
						    <input type="text" class="form-control" name="pageNum" id="J_pageNum_id" placeholder="页码" value="1">
						  </div>
						  <div class="form-group">
						    <label for="J_accountID_id">用户编号</label>
						    <input type="text" class="form-control" name="accountID" id="J_accountID_id" placeholder="用户编号" value="804506390000700000">
						  </div>
						  <div class="form-group">
						    <label for="J_projectID_id">项目编号</label>
						    <input type="text" class="form-control" name="projectID" id="J_projectID_id" placeholder="项目编号" value="795124065877757952">
						  </div>
						  <div class="form-group">
						    <label for="J_beginDate_id">开始日期</label>
						    <input type="text" class="form-control" name="beginDate" id="J_beginDate_id" placeholder="开始日期" value="2017-10-01">
						  </div>
						  <button type="submit" class="btn btn-primary">提交并获取消息到点融</button>
						</form>
					</div>
				</div>
				<h2 class="sub-header">通信详情</h2>
				<div class="table-responsive">
					<iframe name="detail_name" frameborder="0" width="100%;" height="800px;" style="border:none;"></iframe>
				</div>
			</div>
		</div>
	</div>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="${subctxPath}/resources/app/js/app/jquery-1.11.3.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="${subctxPath}/resources/app/js/bootstrap.min.js"></script>
</body>
</html>
