<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();
	request.setAttribute("subctxPath", ctxPath);
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>设置通行密码</title>

<!-- Bootstrap -->
<link href="${subctxPath}/resources/app/css/bootstrap.min.css"
	rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="${subctxPath}/resources/app/js/app/html5shiv.js"></script>
      <script src="${subctxPath}/resources/app/js/app/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<ul class="breadcrumb">
				<li class="active">通行密码</li>
			</ul>
			<div class="col-md-12 column">
				<p>token:${token}</p>
				<p>accountId:${accountId}</p>
			</div>
		</div>
	</div>
	<div id="message"></div>
	<a id="submit" class="button button-success">测试交互</a>
	<script type="text/javascript">
		var submitButton = document.getElementById('submit');
		submitButton.addEventListener('click', function(event) {
			var message = document.getElementById('message');
			postMessage('bbwAccountId', '*');
		});
		document.addEventListener('message', function(e) {
			alert(e.data)
			// console.log(e.data);
		});
	</script>
</body>
</html>
