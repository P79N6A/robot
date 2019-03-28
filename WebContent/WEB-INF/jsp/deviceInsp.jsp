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
	<title>设备巡检</title>
	<link rel="stylesheet" href="${subctxPath}/resources/app/frozenui-1.3.0/css/frozen.css">
	<link rel="stylesheet" href="${subctxPath}/resources/app/frozenui-1.3.0/css/demo.css">
	<link rel="stylesheet" href="${subctxPath}/resources/app/mescroll/mescroll.min.css">
	<style>
		.ui-badge{background-color:#89e659;}
		.ui-badge-muted{background-color:#dc7692;}
	</style>
</head>
<body ontouchstart>
	<div class="ui-footer ui-footer-stable ui-btn-group ui-border-t">
		<button id="J_full_device_id" class="ui-btn-lg ui-btn-primary">全部设备</button>
		<button id="J_ex_device_id" class="ui-btn-lg">异常设备</button>
	</div>
	<div id="J_mescroll_div_id" class="mescroll">
	    <section class="ui-container">
			<section id="form">
				<div class="demo-item">
					<div class="demo-block">
	                    <div class="ui-form-item ui-border-b">
	                        <label>所属项目</label>
	                        <div class="ui-select">
	                            <select id="J_project_combo_id">
		                            <c:forEach items="${comboProjectIdNameList}" var="item">
		                                <option value="${item.projectID}">${item.projectName}</option>
		                            </c:forEach>
	                            </select>
	                        </div>
	                    </div>
					</div>
				</div>
				<div class="demo-item">
					<div class="demo-desc" style="margin-left:15px;">
						<label id="J_label_status_tab_id" class="ui-label">全部设备</label>
						<div class="ui-badge-muted">掉线</div>
						<div class="ui-badge">在线</div>
					</div>
					<div class="demo-block" id="J_horizon_device_status_list_id">
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
<%-- 													<c:when test="${item.ty == 1}">
														<div class="ui-btn-wrap" style="display:inline-block;float:right;">
	                                           				<label class="ui-switch">
	                                                   			<button class="ui-btn ui-btn-primary" id="J_normal_check_online_status_id" cndata="${item.deviceCode}">检测在线</button>
	                                           				</label>
	                                   					</div>
													</c:when> --%>
												</c:choose>
											</div>
										</li>
									</c:forEach>
								</ul>
							</c:otherwise>
						</c:choose>
					</div>
					<div style="height:35px;"></div>
				</div>
			</section>
	    </section><!-- /.ui-container-->
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
		$(function(){
			var globalStatus = 1;
			var globalAccountId = "${accountId}";
			var globalProjectId = $("#J_project_combo_id").val();
			
			//创建下拉刷新对象
			var mescroll = cnBBWMescroll.init("J_mescroll_div_id", function() {
				var dataJson = {};
				dataJson.status = globalStatus;
				dataJson.accountId = globalAccountId;
				dataJson.projectId = globalProjectId;
				jqBBWSendAjax.run("${subctxPath}/app/transitcode/manager/device/inspection", "${token}", dataJson, function(result) {
					renderDeviceStatusDiv(result.data);
				}, function(e){
					cnBBWMescroll.endErrFun(mescroll);
				});
			});
			
			$("#J_project_combo_id").change(function(e) {
				var dataJson = {};
				var accountId = globalAccountId;
				if(accountId) {
					dataJson.status = globalStatus;
					dataJson.accountId = accountId;
					var tmpProjectId = $(this).val();
					dataJson.projectId = tmpProjectId;
					var el = fronzHorizonLoadingFn.loadingFn();
					jqBBWSendAjax.run("${subctxPath}/app/transitcode/manager/device/inspection", "${token}", dataJson, function(result) {
						fronzHorizonLoadingFn.hideLoadingFn(el);
						el = null;
						renderDeviceStatusDiv(result.data);
						globalProjectId = tmpProjectId;
					});
					// 定时关闭
					setTimeout(function(){
						if(el) {
							fronzHorizonLoadingFn.hideLoadingFn(el);
						}
				    },2000);
				} else {
					cnAlertFn.run("网络异常，请稍后再试！");
				}
			});
			$("#J_full_device_id").click(function(){// 1
				var dataJson = {};
				var accountId = globalAccountId;
				if(accountId) {
					dataJson.status = 1;
					dataJson.accountId = globalAccountId;
					dataJson.projectId = globalProjectId;
					var el = fronzHorizonLoadingFn.loadingFn();
					jqBBWSendAjax.run("${subctxPath}/app/transitcode/manager/device/inspection", "${token}", dataJson, function(result) {
						fronzHorizonLoadingFn.hideLoadingFn(el);
						el = null;
						renderDeviceStatusDiv(result.data);
						changeLabel(1);
					});
					// 定时关闭
					setTimeout(function(){
						if(el) {
							fronzHorizonLoadingFn.hideLoadingFn(el);
						}
				    },2000);
				} else {
					cnAlertFn.run("网络异常，请稍后再试！");
				}
			});
			$("#J_ex_device_id").click(function(){// 0
				var dataJson = {};
				var accountId = globalAccountId;
				if(accountId) {
					dataJson.status = 0;
					dataJson.accountId = globalAccountId;
					dataJson.projectId = globalProjectId;
					var el = fronzHorizonLoadingFn.loadingFn();
					jqBBWSendAjax.run("${subctxPath}/app/transitcode/manager/device/inspection", "${token}", dataJson, function(result) {
						fronzHorizonLoadingFn.hideLoadingFn(el);
						el = null;
						renderDeviceStatusDiv(result.data);
						changeLabel(0);
					});
					// 定时关闭
					setTimeout(function(){
						if(el) {
							fronzHorizonLoadingFn.hideLoadingFn(el);
						}
				    },2000);
				} else {
					cnAlertFn.run("网络异常，请稍后再试！");
				}
			});

			// 切换标签
			function changeLabel(status) {
				if(status == 0) {
					globalStatus = 0;
					$("#J_label_status_tab_id").text("异常设备");
				} else if(status == 1) {
					globalStatus = 1;
					$("#J_label_status_tab_id").text("全部设备");
				}
			}

			// 渲染设备巡检列表
			function renderDeviceStatusDiv(arr) {
				arr = JSON.parse(arr);
				if(arr && arr.length > 0) {
					$("#J_horizon_device_status_list_id").html('<ul class="ui-list ui-list-text ui-border-tb" id="J_horizon_device_status_list_id_ul"></ul>');
					$.each(arr, function(idx, item) {
						var itemLi = '<li class="ui-border-t"';
						var ty = item.ty;
						if(ty == 1) {
							itemLi += ' style="background-color: #b6cae0;"><div class="ui-list-thumb-s"><span style="background-image:url(${subctxPath}/resources/app/img/controller.png)"></span>';
						} else if(item.deviceType == '01') {
							itemLi += '><div class="ui-list-thumb-s"><span style="background-image:url(${subctxPath}/resources/app/img/gateway.png)"></span>';
						} else {
							itemLi += '><div class="ui-list-thumb-s"><span style="background-image:url(${subctxPath}/resources/app/img/device.png)"></span>';
						}
						itemLi += '</div><div class="ui-list-info"><h4 class="ui-nowrap">[' + item.deviceCode + '][' + item.dName + ']</h4>';

						if(item.statusCode == '02') {
							itemLi += '<div class="ui-badge">已启用</div>';
						} else if(item.statusCode == '03') {
							itemLi += '<div class="ui-badge-muted" style="color:yellow;">挂起</div>';
						} else if(item.statusCode == '04') {
							itemLi += '<div class="ui-badge-muted">故障</div>';
						}
						
						if(item.synFailFlag != 0) {
							itemLi += ('<div class="ui-badge-muted">' + item.synFailFlag + '</div>');
						} else {
							itemLi += '<div class="ui-badge">0</div>';
						}
						
						if(item.uplinkStatus == '02') {
							itemLi += '<div class="ui-badge-muted">上行</div>';
						} else if(item.uplinkStatus == '01') {
							itemLi += '<div class="ui-badge">上行</div>';
						}

						if(item.downlinkStatus == '02') {
							itemLi += '<div class="ui-badge-muted">下行</div>';
						} else if(item.downlinkStatus == '01') {
							itemLi += '<div class="ui-badge">下行</div>';
						}

						if(item.magnetStatus != 'FF') {
							if(item.magnetStatus == '01') {
								itemLi += '<div class="ui-badge-muted">门磁</div>';
							} else if(item.magnetStatus == '00') {
								itemLi += '<div class="ui-badge">门磁</div>';
							}
						}

						if(item.ty == 0) {
							itemLi += '<div class="ui-btn-wrap" style="display:inline-block;float:right;"><label class="ui-switch"><input type="checkbox" class="J_checkbox_change_open"cndata="' + item.deviceCode + '"';
							if(item.isKeep == 1) {
								itemLi += 'checked="true" ';
							}
							itemLi += '/></label></div>';
						} else if(item.ty == -1){
							itemLi += '<div class="ui-btn-wrap" style="display:inline-block;float:right;"><label class="ui-switch">';
							itemLi += '<input type="checkbox" disabled="disabled"/></label></div>';
						}

						itemLi += '</div></li>';
						$("#J_horizon_device_status_list_id_ul").append(itemLi);
					});
					bindCheckBoxEvent();
				} else {
					$("#J_horizon_device_status_list_id").html('<section class="ui-notice"><i></i><p>没有可巡检的设备！</p></section>');
				}
			}
			function bindCheckBoxEvent() {
				$("#J_horizon_device_status_list_id").find(".J_checkbox_change_open").change(function(){
					var $it = $(this);
					var cnflag = $it.attr("cnflag");
					if(cnflag != "on") {
						$it.attr("cnflag", "on");
						var deviceCode = $it.attr("cndata");
						var ck = $it.prop("checked") || false;
						if(ck && deviceCode) {// 常开
							showKeepSecondsFn($it, ck, deviceCode);
						} else if(deviceCode) {// 取消
							var dataJson = {};
							dataJson.deviceCode = deviceCode;
							dataJson.accountId = globalAccountId;
							dataJson.isOpen = 0;
							dataJson.openMinute = 0;

							var el = fronzHorizonLoadingFn.loadingFn();
							jqBBWSendAjax.run("${subctxPath}/app/transitcode/manager/dahao/change/normalopen", "${token}", dataJson, function(result) {
								fronzHorizonLoadingFn.hideLoadingFn(el);
								el = null;
								$it.attr("cnflag", "off");
							});
							// 定时关闭
							setTimeout(function(){
								if(el) {
									fronzHorizonLoadingFn.hideLoadingFn(el);
								}
								if($it.attr("cnflag") == "on") {
									$it.attr("cnflag", "off");
								}
						    },3000);
						} else {
							$it.prop("checked", !ck);
						}
					} else {
						$it.attr("cnflag", "off");
					}
				});
			}
			bindCheckBoxEvent();
			var global_it = null;
			var global_ck = null;
			var global_deviceCode = null;
			function resetItCkKeepOpen() {
				global_it = null;
				global_ck = null;
				global_deviceCode = null;
			}
			function showKeepSecondsFn(it, ck, deviceCode) {
				global_it = it;
				global_ck = ck;
				global_deviceCode = deviceCode;
				var dia = $.dialog({
			        title:'设置时长',
			        content:'<form action="#"><div class="ui-form-item ui-form-item-r ui-border-b"><input id="J_keep_open_minutes" type="text" placeholder="0-360分"><a href="#" class="ui-icon-close"></a></div></form>',
			        button:["确认","取消"]
			    });

			    dia.on("dialog:action", function(e) {
			    	if(e.index == 0) {
			    		dialogButtonOk();
			    	} else {
				    	global_it.prop("checked", !global_ck);
				    	global_it.attr("cnflag", "off");
			        	resetItCkKeepOpen();
			    	}
			    });
			}
		    function dialogButtonOk() {
		    	var minutes = $("#J_keep_open_minutes").val();
		        if(!minutes || minutes < 0 || minutes > 360) {
		        	cnAlertFn.run("数值应该在0到360之间");
		        	global_it.prop("checked", !global_ck);
		        	global_it.attr("cnflag", "off");
		        	resetItCkKeepOpen();
		        	return;
		        }
				var dataJson = {};
				dataJson.deviceCode = global_deviceCode;
				dataJson.accountId = globalAccountId;
				dataJson.isOpen = 1;
				dataJson.openMinute = minutes;

				var el = fronzHorizonLoadingFn.loadingFn();
				jqBBWSendAjax.run("${subctxPath}/app/transitcode/manager/dahao/change/normalopen", "${token}", dataJson, function(result) {
					fronzHorizonLoadingFn.hideLoadingFn(el);
					el = null;
					if(global_it && global_it.attr("cnflag") == "on") {
						global_it.attr("cnflag", "off");
					}
				});
				// 定时关闭
				setTimeout(function(){
					if(el) {
						fronzHorizonLoadingFn.hideLoadingFn(el);
					}
					if(global_it && global_it.attr("cnflag") == "on") {
						global_it.attr("cnflag", "off");
					}
			    },3000);
		    }
		});
	</script>
</body>
</html>
