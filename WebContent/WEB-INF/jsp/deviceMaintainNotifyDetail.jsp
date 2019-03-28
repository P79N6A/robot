<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String ctxPath = request.getContextPath();
	request.setAttribute("subctxPath", ctxPath);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<title>运维通知详情</title>
	<link rel="stylesheet" href="${subctxPath}/resources/app/frozenui-1.3.0/css/frozen.css">
	<link rel="stylesheet" href="${subctxPath}/resources/app/frozenui-1.3.0/css/demo.css">
	<link rel="stylesheet" href="${subctxPath}/resources/app/mescroll/mescroll.min.css">
	<style>
		.ui-badge{background-color:#89e659;}
		.ui-badge-muted{background-color:#dc7692;}
	</style>
</head>
<body>
<p>accountId:${accountId}</p>
<h1>运维通知详情页面</h1>
</body>
</html>
