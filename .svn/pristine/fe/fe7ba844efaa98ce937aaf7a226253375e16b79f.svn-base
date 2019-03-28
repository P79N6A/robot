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
					<li class="active"><a href="testPostDR.jsp" target="_top">包办移动端 授权并Post用户信息到点融应用</a></li>
					<li><a href="testDRGet.jsp" target="_top">点融应用请求包办服务·Get统计信息</a></li>
				</ul>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">包办移动端 授权并Post用户信息到点融应用</h1>
				<div class="row">
					<div class="col-xs-12 col-sm-12">
						<form action="<%=InitConf.getSelfhost()%>/third/serve/auth/send/message/6ebd9acf58e84517896ba64b308a90f3" method="post" target="detail_name">
						  <div class="form-group">
						    <label for="J_re_url_id">接收用户信息服务url</label>
						    <input type="text" class="form-control" name="reUrl" id="J_re_url_id" placeholder="接收用户信息服务url" value="<%=InitConf.getSelfhost()%>/third/dr/receive">
						  </div>
						  <div class="form-group">
						    <label for="J_accountId_id">用户编码</label>
						    <input type="text" class="form-control" name="accountId" id="J_accountId_id" placeholder="用户编码" value="804506390000700000">
						  </div>
						  <div class="form-group" id="J_isauth_id">
							<label class="radio-inline">
							  <input type="radio" name="isAuth" id="isauth1" value="1" checked="checked"> 同意授权
							</label>
							<label class="radio-inline">
							  <input type="radio" name="isAuth" id="isauth0" value="0"> 拒绝授权
							</label>
						  </div>
						  <button type="submit" class="btn btn-primary">提交并推送用户消息到点融</button>
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
