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
<title>参数异常</title>

<!-- Bootstrap -->
<link href="${subctxPath}/resources/app/css/bootstrap.min.css"
	rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="${subctxPath}/resources/app/js/app/html5shiv.js"></script>
      <script src="${subctxPath}/resources/app/js/app/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<p>
					您目前请求的<strong><em>　${title}　</em></strong>发生<strong>异常</strong>， 请稍后再试。
				</p>
			</div>
		</div>
	</div>
</body>
</html>