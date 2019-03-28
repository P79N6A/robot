var jqBBWSendAjax = (function(cnAlertFn) {
	function getSixTKey(shortKey) {
		var sk = 16 - (shortKey + "").length;
		if (sk > 0) {
			while (sk > 0) {
				shortKey = "0" + shortKey;
				sk--;
			}
			return shortKey;
		} else {
			return (shortKey + "").substr(0, 16);
		}
	}
	// AES 加密
	function encryptByAES(message, key) {
		if (!message || !key) {
			return null;
		}
		var key = CryptoJS.enc.Utf8.parse(getSixTKey(key));
		var srcs = CryptoJS.enc.Utf8.parse(message);
		var encrypted = CryptoJS.AES.encrypt(srcs, key, {
			mode : CryptoJS.mode.ECB,
			padding : CryptoJS.pad.Pkcs7
		});
		return encrypted.toString();
	}
	// AES 解密
	function decryptByAES(ciphertext, key) {
		if (!ciphertext || !key) {
			return null;
		}
		var key = CryptoJS.enc.Utf8.parse(getSixTKey(key));
		var decrypt = CryptoJS.AES.decrypt(ciphertext, key, {
			mode : CryptoJS.mode.ECB,
			padding : CryptoJS.pad.Pkcs7
		});
		return CryptoJS.enc.Utf8.stringify(decrypt).toString();
	}
	var run = function(url, token, dataJson, successCallbackFun, exOptions) {
		var transitHeaderKey = "TokenP";
		if (token && url && dataJson) {
			var option = {};
			if (exOptions) {
				$.extend(option, exOptions);
			}
			var dt = new Date().getTime();
			option.type = "POST";
			option.url = url;
			option.beforeSend = function(request) {
				request.setRequestHeader(transitHeaderKey, dt);
			};

			dataJson.token = encryptByAES(token, dt);
			option.data = {};
			option.data.data = Base.encode(JSON.stringify(dataJson));
			option.success = function(result, status, xhr) {
				// 获取指定响应头参数信息
				var tokenP = xhr.getResponseHeader(transitHeaderKey);
				if (result.code == "FFFFFFFFF") {
					cnAlertFn.run("网络请求异常请稍后再试！");
				} else {
					if (result.code != "000000000") {
						cnAlertFn.run(result.msg);
					} else {
						var resultData = Base.decode(result.data);
						if (resultData) {
							resultData = JSON.parse(resultData);
							resultData.code="000000000";
							resultData.msg=result.msg;
							resultData.token = decryptByAES(resultData.token,
									tokenP);
							successCallbackFun(resultData);
						} else {
							cnAlertFn.run("网络响应异常请稍后再试！");
						}
					}
				}
			};

			$.ajax(option);
		}
	}
	return {
		run : run
	};
})(cnAlertFn);
