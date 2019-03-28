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
	<title>设置通行密码</title>
	<link rel="stylesheet" href="${subctxPath}/resources/app/frozenui-1.3.0/css/frozen.css">
	<link rel="stylesheet" href="${subctxPath}/resources/app/frozenui-1.3.0/css/demo.css">
</head>
<body ontouchstart>
    <section class="ui-container">
		<section id="form">
			<div class="demo-item">
				<div class="demo-block">
					<div class="ui-whitespace">
						<div id="pwd_is_not_empty" style="display:none;text-align:center;padding-top:10px;">
							<p class="ui-txt-info">您当前设置的通行密码为：<strong class="J_pwd_id">${pwd}</strong></p>
						</div>
						<div id="J_pwd_show" style="display:none;text-align:center;padding-top:10px;">
							<p class="ui-txt-info">您的通行时输入密码为：
						    <br/>${acode}<strong class="J_pwd_id">${pwd}</strong>或者${phone}<strong class="J_pwd_id">${pwd}</strong></p>
						</div>
						<div id="pwd_is_empty" style="display:none;padding-top:10px;">
							<p class="ui-txt-info">
							 　　您目前<strong><em>尚未设置通行密码</em></strong>，可以通过下方的<strong>设置</strong> 功能来设置自己的通行密码，
							 密码为 <strong>4位数字</strong> 格式。
							</p>
						</div>
					</div>
				</div>
			</div>
			<div class="demo-item" style="padding-top:10px;">
				<p class="demo-desc">设置（4位数字密码）</p>
				<div class="demo-block">
					<div class="ui-form ui-border-t">
						<div class="ui-form-item ui-form-item-pure ui-border-b">
							<input type="text" id="J_transit_pwd"  placeholder="输入4位新数字密码">
							<a href="javascript:void(0)" id="J_transit_pwd_btn_close" class="ui-icon-close"></a>
						</div>
						<div class="ui-btn-wrap">
							<button class="ui-btn-lg ui-btn-primary" id="J_transit_pwd_to_change_submit">
								确定
							</button>
						</div>
					</div>
					</div>
			</div>
			<div class="demo-item">
				<div class="ui-whitespace" id="J_how_to_use" style="display:none">
					<p class="demo-desc"><strong>使用说明</strong></p>
					<div class="demo-block">
						<div style="padding-left:26px;">
							<ul>
								<li style="list-style:decimal"><p class="ui-txt-info">如果您的账号为200001，手机号为13800138000，密码为1234。</p></li>
								<li style="list-style:decimal"><p class="ui-txt-info">您通行时输入的密码为：2000011234 或者 138001380001234。</p></li>
							</ul>
						</div>
					</div>
				   	<p class="demo-desc"><small>提示：您的账号可以在“我的-账号与安全-账户ID”中找到。</small></p>
				</div>
			</div>
		</section>
    </section><!-- /.ui-container-->
	<script src="${subctxPath}/resources/app/frozenui-1.3.0/lib/zepto.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${subctxPath}/resources/app/frozenui-1.3.0/js/frozen.js" type="text/javascript" charset="utf-8"></script>
	<script src="${subctxPath}/resources/app/js/bs64.js" type="text/javascript" charset="utf-8"></script>
	<script src="${subctxPath}/resources/app/js/aes.js" type="text/javascript" charset="utf-8"></script>
	<script src="${subctxPath}/resources/app/js/mode-ecb.js" type="text/javascript" charset="utf-8"></script>
	<script src="${subctxPath}/resources/app/js/cn-loading.js" type="text/javascript" charset="utf-8"></script>
	<script src="${subctxPath}/resources/app/js/cn-alert.js" type="text/javascript" charset="utf-8"></script>
	<script src="${subctxPath}/resources/app/js/cn_ajax.js" type="text/javascript" charset="utf-8"></script>
	<script>
		$(function() {
			function setDisplayDivStyle(pd) {
				if(pd) {
					$("#pwd_is_not_empty").css("display", "block");
					$("#pwd_is_empty").css("display", "none");

					$("#J_how_to_use").css("display", "block");
					$("#J_pwd_show").css("display", "block");
				} else {
					$("#pwd_is_not_empty").css("display", "none");
					$("#pwd_is_empty").css("display", "block");

					$("#J_how_to_use").css("display", "none");
					$("#J_pwd_show").css("display", "none");
				}
			}
			var pdFlag = "${not empty pwd}";
			setDisplayDivStyle(pdFlag == "true");

			var pwdReg = new RegExp("^[0-9]{4}$");
			$("#J_transit_pwd_to_change_submit").click(function() {
				var dataJson = {};
				var accountId = "${accountId}";
				if(accountId) {
					var pwd = $("#J_transit_pwd").val();
					if(!pwdReg.test(pwd)) {
						cnAlertFn.run("请输入4位数字！", null, "提示", function(result) {
							$("#J_transit_pwd").val("");
						});
						return;
					}
					dataJson.accountId = accountId;
					dataJson.pwd = pwd;
					var el = fronzHorizonLoadingFn.loadingFn();
					jqBBWSendAjax.run("${subctxPath}/app/transitcode/manager/pwd/reset", "${token}", dataJson, function(result) {
						fronzHorizonLoadingFn.hideLoadingFn(el);
						el = null;
						$(".J_pwd_id").text(result.data.pwd);
						setDisplayDivStyle(true);
						cnAlertFn.run("操作成功！当前密码为：" + result.data.pwd);
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
			$("#J_transit_pwd_btn_close").click(function() {
				$("#J_transit_pwd").val("");
			});
		});
	</script>
</body>
</html>
