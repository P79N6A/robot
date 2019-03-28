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
	<title>查看账单</title>
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
	<!-- <div class="ui-footer ui-footer-stable ui-btn-group ui-border-t">
		<button id="J_full_device_id" class="ui-btn-lg ui-btn-primary">全部设备</button>
		<button id="J_ex_device_id" class="ui-btn-lg">异常设备</button>
	</div> -->
	<div id="J_mescroll_div_id" class="mescroll" >
		<input type="hidden" value="${billModel.statusCode}" id="statusCode">
	    <section class="ui-container">
			<section id="form">
				
				<div class="demo-item bill-font " id="billdata"  >
					
				</div>
				
				
				<div class="ui-dialog">
    				<div class="ui-dialog-cnt">
    					<header class="ui-dialog-hd ui-border-b" >
    						<h3>拒签说明</h3>
    					</header>
        				<div class="ui-dialog-bd" style="padding-top:0px;">
           		 			<div>
            					<textarea  style="width: 100%;height: 150px;" id="remark" placeholder="请填写拒签的原因"></textarea>
            				</div>
        				</div>
        				<div class="ui-dialog-ft">
        					<button type="button" data-role="button"  class="select" id="submit">提交</button> 
            				<button type="button" data-role="dismiss"  class="select" id="close">关闭</button> 
        				</div>
    				</div>        
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
		var statusCode=$("#statusCode").val();
		var accountId = "${accountId}";
		var id="${id}";
		$(function(){
			
			loadData();
			$("#submit").click(function(){
					var remark=$("#remark").val();
					if(remark==""){
						cnAlertFn.run("拒签原因不能为空！");
						return false;
					}
					var dataJson = {};
					dataJson.accountId=accountId;
					dataJson.id=id;
					dataJson.remark=remark;
					var el = fronzHorizonLoadingFn.loadingFn();
					jqBBWSendAjax.run("${subctxPath}/myBill/appH5/refuseSignIn", "${token}", dataJson, function(result) {
						$(".ui-dialog").removeClass("show");
						fronzHorizonLoadingFn.hideLoadingFn(el);
						el = null;
						$("#statusCodeDesc").html('已拒签');
						$("#button-sign").remove();
					});
					// 定时关闭
					setTimeout(function(){
						if(el) {
							fronzHorizonLoadingFn.hideLoadingFn(el);
						}
	   	 			},2000);
			
			});
			$("#close").click(function(){
				$(".ui-dialog").removeClass("show");
			});
			
			if(statusCode=='06'){
				$("#button-sign").css("display",true);
			}
			
			
			function loadData(){
				var dataJson = {};
				dataJson.accountId=accountId;
				dataJson.id=id;
				dataJson.token="${token}";
				
				var el = fronzHorizonLoadingFn.loadingFn();
				jqBBWSendAjax.run("${subctxPath}/myBill/appH5/detail", "${token}", dataJson, function(result) {
					fronzHorizonLoadingFn.hideLoadingFn(el);
					renderBilldetailDiv(result.data);
				});
			}
		
			
			
			function renderBilldetailDiv(arr){
				arr = JSON.parse(arr);
				if(arr !=null) {
					var html="";
					var billBeginDate=arr.billBeginDate.split("/");
					var billEndDate=arr.billEndDate.split("/");
					var billPayDate=arr.billPayDate.split("/");
					html='<div class="bill-title-frist" id="billTitle">'+
							'<span class="span-title ml15 " >'+
								arr.projectName+arr.rentUnit+'('+billBeginDate[1]+'.'+billBeginDate[2]+'-'+billEndDate[1]+'.'+billEndDate[2]+')';
							'</span>';
					
					html=html+'<span  class="span-sign fr" id="statusCodeDesc">'+arr.statusCodeDesc+'</span>';
						
					html=html+
						'</div>'+
						'<div  class="bill-div">'+
							'<div >'+
            					'<span class="span-title" >账单金额</span>'+
            					'<div  class="span-title fr">缴费截止日'+billPayDate[0]+'.'+billPayDate[1]+'.'+billPayDate[2]+'</div>'+
            				'</div>'+
            				
            				
							 '<div class="mt10" >'+
								'<div class="div-label bac01 t-center">'+
									'<div class="div-cell">'+
										'<div>应缴</div>'+
										'<div>￥'+arr.billPayAmount+'</div>'+
									'</div>'+
									 
								'</div>'+
								'<div class="div-label bac02 ml5f t-center">'+
									'<div class="div-cell">'+
										'<div>已缴</div>'+
										'<div>￥'+arr.paidAmount+'</div>'+
									'</div>'+
								'</div>'+
								'<div class="div-label bac03 ml5f t-center">'+
									'<div class="div-cell">'+
										'<div>待缴</div>'+
										'<div>￥'+arr.pendAmount+'</div>'+
									'</div>'+
								'</div>'+
							'</div>'+ 
							
						'</div>'+
						'<div class="bill-title-small mt10"  ></div>'+
            			'<div class="bill-div">'+
            				'<div >'+
            					'<span class="span-title">费用明细</span>'+
            				'</div>'+
            				'<table class="w100 mt5">';
            		var currentFees=arr.currentFees;
            		if(currentFees.length>0){
            			for(var i=0;i<currentFees.length;i++){
            				var tab='<tr>'+
			 							'<td align="left"  class="pt5" width="33%">'+
		 									'<span>'+currentFees[i].chargeName+'</span>'+
		 								'</td>'+
		 								'<td align="center" class="pt5" width="33%"> ￥'+currentFees[i].amount+'</td>'+
		 								'<td align="right" class="pt5">'+currentFees[i].beginDateStr+'</td>'+
		 							'</tr>';
            				html=html+tab;
            			}
            			html=html+'<tr style="color: red;"  class="span-title">'+
 						'<td align="left" class="pt5" width="33%">本期费用小计</td>'+
 						'<td align="center" class="pt5" width="33%">￥'+arr.actualAmount+'</td>'+
 						'<td ></td>'+
 					  '</tr>';
            			
            		}
            		
		 				
            		var passFees=arr.passFees;
            		if(passFees.length>0){
            			for(var i=0;i<passFees.length;i++){
            				var tab='<tr>'+
			 							'<td align="left"  class="pt5" width="33%">'+
		 									'<span>'+passFees[i].chargeName+'</span>'+
		 								'</td>'+
		 								'<td align="center" class="pt5" width="33%"> ￥'+passFees[i].amount+'</td>'+
		 								'<td align="right" class="pt5" >'+currentFees[i].beginDateStr+'</td>'+
		 							'</tr>';
            				html=html+tab;
            			}
            			html=html+'<tr style="color: red;"  class="span-title">'+
						'<td align="left" class="pt5" width="33%">欠费总计</td>'+
						'<td align="center" class="pt5" width="33%">￥'+arr.arrearsAmount+'</td>'+
						'<td align="right" class="pt5"></td>'+
			  		  '</tr>';
            			
            		}
            		if(arr.deductAmount>0){
            			var tab='<tr>'+
							'<td align="left"  class="pt5" width="33%">'+
								'<span>余额抵扣</span>'+
							'</td>'+
							'<td align="center" class="pt5" width="33%"> ￥'+arr.deductAmount+'</td>'+
							'<td align="right" class="pt5" ></td>'+
						'</tr>';
			html=html+tab;
            			
            		}
            		
            		
            		html=html+'<tr style="color: red;" class="span-title">'+
								'<td align="left" class="pt5" width="33%">合计</td>'+
								'<td align="center" class="pt5" width="33%">￥'+arr.billPayAmount+'</td>'+
								'<td align="right" class="pt5 "></td>'+
					  		  '</tr>'+		
						'</table>'+
					'</div>';
						
				    html=html+'<div class="bill-title-small mt10"  ></div>'+
        					  '<div class="bill-div">'+
    								'<div >'+
    									'<span class="span-title">房源信息</span>'+
    								'</div>'+
    								'<table class="w100 mt5">';
				    var rmList=arr.rmList;
            		if(rmList.length>0){
            			for(var i=0;i<rmList.length;i++){
            				var tab='<tr>'+
			 							'<td align="left"  class="pt5" width="33%">'+
		 									'<span>'+rmList[i].floorName+'</span>'+
		 								'</td>'+
		 								'<td align="center" class="pt5" width="33%">'+rmList[i].roomName+'</td>'+
		 								'<td align="right" class="pt5">'+rmList[i].floorage+'㎡</td>'+
		 							'</tr>';
            				html=html+tab;
            			}
            			
            		}else{
            			html=html+'<tr>'+
	 								'<td align="left" class="pt5" width="33%">'+
 										'无'+
 									'</td>'+
 									'<td width="33%"></td>'+
 									'<td ></td>'+
 								   '</tr>';
            			
            		}
            		html=html+'</table></div>';
            		html=html+
            			'<div class="bill-title-small mt10"  ></div>'+
            			'<div class="bill-div">'+
							'<div>'+
               	 				'<span class="span-title">汇款账户信息 </span>'+
            	 			'</div>'+
            	 			'<div class="mt10">'+
               	 				'<span class="">收款方 :</span>'+
               	 				'<span class="ml5" >'+arr.accountInfo+'</span>'+
            	 			'</div>'+
            	 			'<div class="mt10" >'+
               	 				'<span class="">开户银行 :</span>'+
               	 				'<span class="ml5" >'+arr.bankInfo+'</span>'+
            				'</div>'+
            	 			'<div class="mt10" >'+
               	 				'<span class="">银行账号 :</span>'+
               	 				'<span class="ml5">'+arr.bankNum+'</span>'+
            	 			'</div>'+
            	 			
						'</div>';
						
						html=html+
            				'<div class="bill-title-small mt10"  ></div>'+
            				'<div class="bill-div">'+
            					'<div>'+
       	 							'<span class="span-title">动态 </span>'+
    	 						'</div>';
    	 						
	 					var infoList=arr.infoList;
	 					if(infoList !=null && infoList.length>0 ){
	 						html=html+
	 							'<table class="w100 mt10">';
	 						var length=infoList.length;
	 						for(var i=0;i<infoList.length;i++){
	 							if((i+1)==length){
	 								html=html+
	 								'<tr>'+
	 									'<td width="45px">'+
	 										'<div class="ui-icon-circular span-title">'+(i+1)+'</div>'+
	 									'</td>'+
	 									'<td class="pl10 pb10" rowspan="2" height="100px">'+
	 										'<div class="div-bill-dynamic">'+
	 											'<div class="div-cell pl10">'+
	 												'<div>'+infoList[i].time+'</div>'+
	 												'<div class="mt10 span-title">'+infoList[i].content+'</div>'+
	 											'</div>'+
	 										'</div>'+
	 									'</td>'+
	 								'</tr>'+
	 								'<tr>'+
 										'<td class="vertical-center"></td>'+
 									'</tr>';
	 							}else{
	 								html=html+
	 								'<tr>'+
	 									'<td width="45px">'+
	 										'<div class="ui-icon-circular span-title">'+(i+1)+'</div>'+
	 									'</td>'+
	 									'<td class="pl10 pb10" rowspan="2" height="100px">'+
	 										'<div class="div-bill-dynamic">'+
	 											'<div class="div-cell pl10">'+
	 												'<div>'+infoList[i].time+'</div>'+
	 												'<div class="mt10 span-title">'+infoList[i].content+'</div>'+
	 											'</div>'+
	 										'</div>'+
	 									'</td>'+
	 								'</tr>'+
	 								'<tr>'+
 										'<td class="vertical-center"><div class="ui-icon-verticalline"></div></td>'+
 									'</tr>';
	 							}
	 							
	 						}
	 						html=html+'</table></div>';
	 					}else{
	 						html=html+
	 							'<div class="mt10">'+
									'<span class="">暂无 </span>'+
								'</div>'+
							'</div>';
	 					}
    	 					
						
					if(arr.statusCode=='06'){
						html=html+
							'<div  class="bill-div"  id="button-sign">'+
								'<button class="bill-but-yellow" id="sign" onclick="sign()">签收</button>'+
								'<button class="bill-but-gray ml5f" id="refuseSign" onclick="refuseSignIn()">拒签</button>'+
							'</div>';
					}
					$("#billdata").html(html);
					
					
				}else{
					$("#billdata").html('<div class="mt30 ml15 span-title">未找到此账单信息</div>');
				}	
				
				
			};
			
		});
		
		
		function sign(){
			//cnAlertFn.run("确定签收？",["确认","取消"],"123",signIn(),true);
			
			var dia = $.dialog({
				title :"",
				content :"<table style='width:100%;'><tr><td align='center'>确定签收？</td></tr></table>",
				button : ["确定","取消"]
			});
			
			dia.on("dialog:action", function(e) {
				if(e.index==0){
					signIn();
				}
				
			});
			
			dia.on("dialog:hide", function(e) {
				
			});
		}
		
		function signIn(){
			var dataJson = {};
			dataJson.accountId=accountId;
			dataJson.id=id;
			var el = fronzHorizonLoadingFn.loadingFn();
			jqBBWSendAjax.run("${subctxPath}/myBill/appH5/signIn", "${token}", dataJson, function(result) {
				fronzHorizonLoadingFn.hideLoadingFn(el);
				el = null;
				$("#statusCodeDesc").html('已签收');
				$("#button-sign").remove();
			});
			// 定时关闭
			setTimeout(function(){
				if(el) {
					fronzHorizonLoadingFn.hideLoadingFn(el);
				}
   	 		},2000);
		}
		
		function refuseSignIn(){
			$("#remark").val("");
			$(".ui-dialog").addClass("show");
    		
					
		}
		
		
	</script>
</body>
</html>
