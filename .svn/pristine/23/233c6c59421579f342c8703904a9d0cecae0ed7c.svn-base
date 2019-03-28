<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
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
	<title>名单维护</title>
	<link rel="stylesheet" href="${subctxPath}/resources/app/frozenui-1.3.0/css/frozen.css">
	<link rel="stylesheet" href="${subctxPath}/resources/app/frozenui-1.3.0/css/demo.css">
	<link rel="stylesheet" href="${subctxPath}/resources/app/frozenui-1.3.0/css/billFind.css">
	<link rel="stylesheet" href="${subctxPath}/resources/app/mescroll/mescroll.min.css">
	<style>
		.ui-badge{background-color:#89e659;}
		.ui-badge-muted{background-color:#dc7692;}
	</style>
</head>
<body ontouchstart>
	<div id="J_mescroll_div_id" class="mescroll">
	<form action="">
		<div class="ui-form-item ui-border-b" >
        	<label>所属项目</label>
        	
            	<div class="ui-select">
                	<select id="projectId" name="projectId">
                		<c:forEach items="${comboProjectIdNameList}" var="item">
		       				<option value="${item.projectID}">${item.projectName}</option>
		          		</c:forEach>
            		</select>
           		</div>
            	
       	</div>
       	<div class="demo-block" id="J_horizon_device_status_list_id" >
       		
       	</div>
       	
		<%-- <div class="demo-block" id="J_horizon_device_status_list_id">
						<c:choose>
							<c:when test="${empty deviceStatusList}">
					            <section class="ui-notice"><i></i><p>没有可巡检的设备！</p></section>
							</c:when>
							<c:otherwise>
								<ul class="ui-list ui-list-text ui-border-tb">
									<c:forEach items="${deviceStatusList}" var="item">
										<li class="ui-border-t"
										<c:if test="${item.ty == 1}">
											style="background-color: #b6cae0;"
										</c:if>
										>
											<div class="ui-list-thumb-s">
												<c:choose>
													<c:when test="${item.ty == 1}">
														<span style="background-image:url(${subctxPath}/resources/app/img/controller.png)"></span>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${item.deviceType eq '01'}">
																<span style="background-image:url(${subctxPath}/resources/app/img/gateway.png)"></span>
															</c:when>
															<c:otherwise>
																<span style="background-image:url(${subctxPath}/resources/app/img/device.png)"></span>
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</div>
											<div class="ui-list-info">
												<h4 class="ui-nowrap">[${item.deviceCode}][${item.dName}]</h4>
												<c:choose>
													<c:when test="${item.statusCode eq '02'}">
														<div class="ui-badge">已启用</div>
													</c:when>
													<c:when test="${item.statusCode eq '03'}">
														<div class="ui-badge-muted" style="color:yellow;">挂起</div>
													</c:when>
													<c:when test="${item.statusCode eq '04'}">
														<div class="ui-badge-muted">故障</div>
													</c:when>
												</c:choose>
												<c:if test="${item.synFailFlag != 0}"><div class="ui-badge-muted">${item.synFailFlag}</div></c:if>
												<c:if test="${item.synFailFlag == 0}"><div class="ui-badge">${item.synFailFlag}</div></c:if>
												<c:choose>
													<c:when test="${item.uplinkStatus eq '02'}">
														<div class="ui-badge-muted">上行</div>
													</c:when>
													<c:when test="${item.uplinkStatus eq '01'}">
														<div class="ui-badge">上行</div>
													</c:when>
												</c:choose>
												<c:choose>
													<c:when test="${item.downlinkStatus eq '02'}">
														<div class="ui-badge-muted">下行</div>
													</c:when>
													<c:when test="${item.downlinkStatus eq '01'}">
														<div class="ui-badge">下行</div>
													</c:when>
												</c:choose>
												<c:if test="${item.magnetStatus ne 'FF'}">
													<c:choose>
														<c:when test="${item.magnetStatus eq '01'}">
															<div class="ui-badge-muted">门磁</div>
														</c:when>
														<c:when test="${item.magnetStatus eq '00'}">
															<div class="ui-badge">门磁</div>
														</c:when>
													</c:choose>
												</c:if>
												<c:choose>
													<c:when test="${item.ty == 0}">
														<div class="ui-btn-wrap" style="display:inline-block;float:right;">
	                                           				<label class="ui-switch">
	                                                   			<input type="checkbox" class="J_checkbox_change_open" cndata="${item.deviceCode}"
	                                                   			<c:if test="${item.isKeep == 1}">
	                                                   			checked="true"
	                                                   			</c:if>
	                                                   			/>
	                                           				</label>
	                                   					</div>
													</c:when>
													<c:when test="${item.ty == -1}">
														<div class="ui-btn-wrap" style="display:inline-block;float:right;">
	                                           				<label class="ui-switch">
	                                                   			<input type="checkbox" disabled="disabled"/>
	                                           				</label>
	                                   					</div>
													</c:when>
													<c:when test="${item.ty == 1}">
														<div class="ui-btn-wrap" style="display:inline-block;float:right;">
	                                           				<label class="ui-switch">
	                                                   			<button class="ui-btn ui-btn-primary" id="J_normal_check_online_status_id" cndata="${item.deviceCode}">检测在线</button>
	                                           				</label>
	                                   					</div>
													</c:when>
												</c:choose>
											</div>
										</li>
									</c:forEach>
								</ul>
							</c:otherwise>
						</c:choose>
					</div> --%>
	</form>
	</div>
	<script src="${subctxPath}/resources/app/frozenui-1.3.0/lib/zepto.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${subctxPath}/resources/app/frozenui-1.3.0/js/frozen.js" type="text/javascript" charset="utf-8"></script>
	<script src="${subctxPath}/resources/app/mescroll/mescroll.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${subctxPath}/resources/app/js/bs64.js" type="text/javascript" charset="utf-8"></script>
	<script src="${subctxPath}/resources/app/js/aes.js" type="text/javascript" charset="utf-8"></script>
	<script src="${subctxPath}/resources/app/js/mode-ecb.js" type="text/javascript" charset="utf-8"></script>
	<script src="${subctxPath}/resources/app/js/cn-loading.js" type="text/javascript" charset="utf-8"></script>
	<script src="${subctxPath}/resources/app/js/cn-alert.js" type="text/javascript" charset="utf-8"></script>
	<script src="${subctxPath}/resources/app/js/cn_ajax.js" type="text/javascript" charset="utf-8"></script>
	<script src="${subctxPath}/resources/app/js/cn-mescroll.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" charset="utf-8">
	
		var accountId = "${accountId}";
	    var pId=$("#projectId").val();
		$(function(){
			if(pId!='' && pId !=null){
				var dataJson = {};
				dataJson.pId=$("#projectId").val();
				dataJson.token="${token}";
				$.post("${subctxPath}/mingdan/appH5/list",dataJson,function(result,status){
					
					var data =JSON.parse(JSON.parse(result).data);
					renderBilldetailDiv(data);
					
					
				});
				
			}
			
			
			$("#projectId").change(function(){
				var dataJson = {};
				
				dataJson.pId=$("#projectId").val();
				
				dataJson.token="${token}";
				$.post("${subctxPath}/mingdan/appH5/list",dataJson,function(result,status){
					
					var data =JSON.parse(JSON.parse(result).data);
					renderBilldetailDiv(data);
					
					
				});
			});
			
			
			//创建下拉刷新对象
			var mescroll = cnBBWMescroll.init("J_mescroll_div_id", function() {
				
				var dataJson = {};
				
				dataJson.pId=$("#projectId").val();
				
				dataJson.token="${token}";
				$.post("${subctxPath}/mingdan/appH5/list",dataJson,function(result,status){
					
					var data =JSON.parse(JSON.parse(result).data);
					renderBilldetailDiv(data);
					
				}, function(e){
					cnBBWMescroll.endErrFun(mescroll);
				});
				
				
				
			});
			
			function renderBilldetailDiv(arr){
				
				var html="";
				if(arr !=null) {
					if(arr==null || arr.length==0){
						html='<section class="ui-notice"><i></i><p>没有名单！</p></section>';
					}else{
						
						html='<ul class="ui-list ui-list-text ui-border-tb">';
						var data=arr;
						for(var i=0;i<data.length;i++){
							html=html+
									'<li class="ui-border-t" style="background-color: #b6cae0;">'+
										'<div class="ui-list-thumb-s">'+
											'<span style="background-image:url(${subctxPath}/resources/app/img/controller.png)"></span>'+
										'</div>'+
										'<div class="ui-list-info">'+
											'<h4 class="ui-nowrap">['+data[i].arrangeName+']</h4>';
							if(data[i].shouldSynchNum==data[i].synchNum){
								html=html+'<div class="ui-badge">应同步'+data[i].shouldSynchNum+'</div>'+
								/* '<div class="ui-badge">'+data[i].shouldSynchNum+'</div>'+ */
								'<div class="ui-badge">已同步'+data[i].synchNum+'</div>';
								/* '<div class="ui-badge">'+data[i].synchNum+'</div>'+ */
							}else{
								html=html+'<div class="ui-badge-muted">应同步'+data[i].shouldSynchNum+'</div>'+
								/* '<div class="ui-badge">'+data[i].shouldSynchNum+'</div>'+ */
								'<div class="ui-badge-muted">已同步'+data[i].synchNum+'</div>';
								/* '<div class="ui-badge">'+data[i].synchNum+'</div>'+ */
							}
							if(data[i].toBeSynchNum==0){
								html=html+'<div class="ui-badge">待同步'+data[i].toBeSynchNum+'</div>';
								/* '<div class="ui-badge-muted">'+data[i].toBeSynchNum+'</div>'+ */
							}else{
								html=html+'<div class="ui-badge-muted">待同步'+data[i].toBeSynchNum+'</div>';
							}				
							if(data[i].failSynchNum==0){
								html=html+'<div class="ui-badge">同步失败'+data[i].failSynchNum+'</div>';
								/* '<div class="ui-badge-muted">'+data[i].failSynchNum+'</div>'+ */
							}else{
								html=html+'<div class="ui-badge-muted">同步失败'+data[i].failSynchNum+'</div>';
								/* '<div class="ui-badge-muted">'+data[i].failSynchNum+'</div>'+ */
							}				
											
											
							html=html+'</div></li>';
											
							var data2=arr[i].list;
							
							for(var j=0;j<data2.length;j++){
								html=html+
								'<li class="ui-border-t" >'+
									'<div class="ui-list-thumb-s">'+
										'<span style="background-image:url(${subctxPath}/resources/app/img/device.png)"></span>'+
									'</div>'+
									'<div class="ui-list-info">'+
										'<h4 class="ui-nowrap">['+data2[j].deviceCode+']['+data2[j].deviceName+']</h4>';
										
										if(data2[j].shouldSynchNum==data2[j].synchNum){
											html=html+'<div class="ui-badge">应同步'+data2[j].shouldSynchNum+'</div>'+
											/* '<div class="ui-badge">'+data[i].shouldSynchNum+'</div>'+ */
											'<div class="ui-badge">已同步'+data2[j].synchNum+'</div>';
											/* '<div class="ui-badge">'+data[i].synchNum+'</div>'+ */
										}else{
											html=html+'<div class="ui-badge-muted">应同步'+data2[j].shouldSynchNum+'</div>'+
											/* '<div class="ui-badge">'+data[i].shouldSynchNum+'</div>'+ */
											'<div class="ui-badge-muted">已同步'+data2[j].synchNum+'</div>';
											/* '<div class="ui-badge">'+data[i].synchNum+'</div>'+ */
										}
										if(data2[j].toBeSynchNum==0){
											html=html+'<div class="ui-badge">待同步'+data2[j].toBeSynchNum+'</div>';
											/* '<div class="ui-badge-muted">'+data[i].toBeSynchNum+'</div>'+ */
										}else{
											html=html+'<div class="ui-badge-muted">待同步'+data2[j].toBeSynchNum+'</div>';
										}				
										if(data2[j].failSynchNum==0){
											html=html+'<div class="ui-badge">同步失败'+data2[j].failSynchNum+'</div>';
											/* '<div class="ui-badge-muted">'+data[i].failSynchNum+'</div>'+ */
										}else{
											html=html+'<div class="ui-badge-muted">同步失败'+data2[j].failSynchNum+'</div>';
											/* '<div class="ui-badge-muted">'+data[i].failSynchNum+'</div>'+ */
										}				
														
										
									/* 	'<div class="ui-badge">应同步'+data2[j].shouldSynchNum+'</div>'+
										
										'<div class="ui-badge">已同步'+data2[j].synchNum+'</div>'+
										
										'<div class="ui-badge-muted">待同步'+data2[j].toBeSynchNum+'</div>'+
										
										'<div class="ui-badge-muted">同步失败'+data[i].failSynchNum+'</div>'+
										 */
								html=html+'</div></li>';
										
							}
							
											
						}
						html=html+'</ul>';
					}
				}
				
				$("#J_horizon_device_status_list_id").html(html);
			};
			
		});
		
		function refuseSignIn(){
			$("#remark").val("");
			$(".ui-dialog").addClass("show");
    				
		}
		
		
	</script>
</body>
</html>
