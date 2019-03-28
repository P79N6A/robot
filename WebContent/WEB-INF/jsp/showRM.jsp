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
    <div class="container">

      <div class="page-header">
        <h1>${sendTitle}</h1>
      </div>
      <h3>发送的整体请求数据</h3>
      <p>${requestData}</p>
      <div class="row">
        <div class="col-md-12"><h5>原始data数据：</h5><p>${src_data}</p></div>
        <div class="col-md-12"><h5>原始data数据带盐值：</h5><p>${src_data_salt}</p></div>
        <div class="col-md-12"><h5>原始dateTime数据：</h5><p>${src_dateTime}</p></div>
        <div class="col-md-12">mac签名数据：${src_mac}</p></div>
        <div class="col-md-12">加密后data数据：${enc_data}</p></div>
      </div>

      <div class="page-header">
        <h1>${recTitle}</h1>
      </div>

      <h3>接收的整体响应数据</h3>
      <p>${responseData}</p>
      <div class="row">
        <div class="col-md-12"><h5>解密前data数据：</h5><p>${src_res_data}</p></div>
        <div class="col-md-12"><h5>dateTime数据：</h5><p>${src_res_dateTime}</p></div>
        <div class="col-md-12"><h5>mac签名数据：</h5><p>${src_res_mac}</p></div>
        <div class="col-md-12"><h5>解密后带盐值data数据：</h5><p>${dec_res_data_salt}</p></div>
        <div class="col-md-12"><h5>解密后不带盐值data数据：</h5><p>${dec_res_data}</p></div>
        <div class="col-md-12"><h5>签名验证结果：</h5><p>${dec_mac_status}</p></div>
      </div>
    </div> <!-- /container -->
</body>
</html>
