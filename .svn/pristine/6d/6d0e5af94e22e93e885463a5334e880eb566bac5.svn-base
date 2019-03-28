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
<meta name="description" content="接口测试">
<title>接口测试</title>
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
	.mt2{
		margin-top: 2px;
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
				<a class="navbar-brand" href="javascript:void(0);">接口测试</a>
			</div>
		</div>
	</nav>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li class="active"><a href="testPostRobotRoom.jsp" target="_top">接口测试</a></li>
					
				</ul>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">接口测试</h1>
				<div class="row">
					<div class="col-xs-12 col-sm-12">
						<form  id="from">
						  <div class="form-group l wp100 ">
						    <label for="J_re_url_id" class="l tright w100 mt5 ml10" >接口地址</label>
						    <input type="text" class="form-control l w400 ml10"  name="reUrl" id="J_re_url_id" placeholder="远程开门服务url" >
						  </div>
						  <div class="form-group l wp100 canshu">
						    <input type="text" class="form-control l w100 ml10" name="key" 	 placeholder="参数名">
						    <input type="text" class="form-control l w400 ml10" name="value" placeholder="值">
						    <button  class="btn-warning ml10  btn-sm mt2 "  onclick="del(this)">删除</button>
						  </div>
						  
						</form>
						<div class="form-group l wp100">
							<button  class="btn btn-info" onclick="add()">添加参数</button>
						  	<button  class="btn btn-success" onclick="button()">发送数据</button>
						  	
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
				$("#result").html("");
				var reUrl=$("#J_re_url_id").val();
				var dataJson = {};
				var data='{';
				var boo=false;
				$.each($(".canshu"),function(ele,index){
					var key=$(this).children("input[name='key']").val();
					var value=$(this).children("input[name='value']").val();
					if(key !='' && key!=null && value!=null && value!=null){
						data=data+'"'+key+'":"'+value+'",';
						boo=true;
					}
					
				});
				if(boo){
					data=data.substring(0, data.length-1);
					data=data+'}';
					dataJson.data=data;
				}
				
				dataJson.token="llc";
				$("#send").html(JSON.stringify(dataJson));
				$.post(reUrl,dataJson,function(result,status){
					var html="";
					
					html=html+'<div class="form-group wp100 l" >'+
						    		'<label for="J_accountId_id" class="l mt5" >返回结果:'+result+'</label>'+
						    		
						  	'</div>';
					var result=JSON.parse(result);
					html=html+'<div class="form-group wp100 l" >'+
						    		'<label for="J_accountId_id" class="l mt5" >code:'+result.code+'</label>'+
						    		'<label for="J_accountId_id" class="l mt5 ml10" >message:'+result.message+'</label>'+
						    		'<label for="J_accountId_id" class="l mt5 ml10" >data:'+result.data+'</label>'+
						  	'</div>';
							
				  
						  	
					$("#result").html(html);
				});
			}
		
		 function add(){
		 	$("#from").append('<div class="form-group l wp100 canshu">'+
						        '<input type="text" class="form-control l w100 ml10" name="key" 	 placeholder="参数名">'+
						        '<input type="text" class="form-control l w400 ml10" name="value" placeholder="值">'+
						        '<button  class="btn-warning ml10  btn-sm mt2 "  onclick="del(this)">删除</button>'+
						     '</div>');
		 }
		 
		 function del(_this){
		 	$(_this).parent().remove();
		 }
	</script>
</body>
</html>


