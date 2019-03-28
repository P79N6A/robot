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
<meta name="description" content="机器人接口测试">
<title>机器人接口测试</title>
<!-- Bootstrap -->
<link href="${subctxPath}/resources/app/css/bootstrap.min.css"
	rel="stylesheet"/>
<link href="${subctxPath}/resources/app/css/dashboard.css"
	rel="stylesheet"/>
<style>
	.l{
		float: left;
		
	}
	.tright{
		text-align: right;
	}
	
	.w100{
		width: 100px;
		
	}
	.w400{
		width: 400px;
	}
	.wp100{
		width: 100%;		
	}
	.mt5{
		margin-top: 5px;
	}
	.ml10{
		margin-left: 10px;
	}
</style>
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
				<a class="navbar-brand" href="javascript:void(0);">机器人接口测试</a>
			</div>
		</div>
	</nav>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li ><a href="testPostRobotRoom.jsp" target="_top">查询人员位置 </a></li>
					<li class="active"><a href="testPostRobotOpen.jsp" target="_top">请求远程开门</a></li>
				</ul>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">机器人请求开门</h1>
				<div class="row">
					<div class="col-xs-12 col-sm-12">
						<form  >
						  <div class="form-group l wp100">
						    <label for="J_re_url_id" class="l tright w100 mt5" >接口地址</label>
						    <input type="text" class="form-control l w400 ml10"   name="reUrl" id="J_re_url_id" placeholder="远程开门服务url" value="<%=InitConf.getSelfhost()%>/remote/robot/open">
						  </div>
						  <div class="form-group wp100 l" >
						    <label for="J_accountId_id" class="l tright w100 mt5" >机器人编码</label>
						    <input type="text" class="form-control l w400 ml10"  name="loginId" id="loginId" placeholder="机器人编码" value="belghjde4sgtdqn96gdg">
						  </div>
						  <div class="form-group l wp100" >
						    <label for="J_accountId_id" class="l tright w100 mt5">设备编码</label>
						    <input type="text"  class="form-control l w400 ml10" name="deviceCode" id="deviceCode" placeholder="" value="00020401">
						  </div>
						  <div class="form-group l wp100" >
						    <label for="J_accountId_id" class="l tright w100 mt5" >进出标示</label>
						    <input type="text"  class="form-control l w400 ml10" name="openType" id="openType" placeholder="" value="00">
						  </div>
						  <div class="form-group l wp100" >
						    <label for="J_accountId_id" class="l tright w100 mt5" >开门时长</label>
						    <input type="text"  class="form-control l w400 ml10" name="openTime" id="openTime" placeholder="" value="3"><span class="l mt5">秒</span>
						  </div>
						  <div class="form-group wp100 l" >
						    <label for="J_accountId_id" class="l tright w100 mt5" >接收人id</label>
						    <input type="text"  class="form-control l w400 ml10" name="userId" id="userId" placeholder="" value=13161259974>
						  </div>
						  
						  
						</form>
						<div class="form-group l wp100">
						  	<button  class="btn btn-primary" onclick="button()">发送</button>
						</div>
					</div>
				</div>
				<h2 class="sub-header">请求数据</h2>
				<div class="table-responsive" id="send">
					
				</div>
				
				<h2 class="sub-header">返回结果</h2>
				<div class="table-responsive" id="result">
					
				</div>
			</div>
		</div>
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="${subctxPath}/resources/app/js/app/jquery-1.11.3.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="${subctxPath}/resources/app/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		
			function button(){
				var reUrl=$("#J_re_url_id").val();
				var loginId=$("#loginId").val();
				var deviceCode=$("#deviceCode").val();
				var openType=$("#openType").val();
				var openTime=$("#openTime").val();
				var userId=$("#userId").val();
				var dataJson = {};
				var data={};
				data.loginId=loginId;
				data.deviceCode=deviceCode;
				data.openType=openType;
				data.openTime=openTime;
				data.userId=userId;
				
				data=JSON.stringify(data);
				dataJson.data=data;
				dataJson.checkValue="";
				dataJson.dateTime="1539224620";
				$("#send").html(JSON.stringify(dataJson));
				$.post(reUrl,dataJson,function(result,status){
					
					var html="";
					var result=JSON.parse(result);
					html=html+'<div class="form-group wp100 l" >'+
						    	'<label for="J_accountId_id" class="l mt5" >code:'+result.code+'</label>'+
						  	'</div>'+
							'<div class="form-group wp100 l" >'+
						    	'<label for="J_accountId_id" class="l mt5" >message:'+result.message+'</label>'+
						  	'</div>';
						  	
					$("#result").html(html);
				});
			}
		
		
	</script>
</body>
</html>


