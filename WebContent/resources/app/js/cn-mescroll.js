var cnBBWMescroll = (function() {
	var init = function(domId, successCallbackFun, exOptions) {
		if (!domId) {
			console.error("this domId is null!!!!");
			return;
		}
		var option = {
			down : {
				auto : false, // 是否在初始化完毕之后自动执行下拉回调callback; 默认true
				callback : downCallback
			// 下拉刷新的回调
			},
			up : {
				use : false
			}
		};

		if (exOptions) {
			$.extend(option, exOptions);
		}

		// 创建MeScroll对象
		var mescroll = new MeScroll(domId, option);

		/* 下拉刷新的回调 */
		function downCallback() {
			try {
				successCallbackFun();
				endSuccessFun(mescroll);
			} catch (e) {
				endErrFun(mescroll);
			}
		}

		return mescroll;
	}

	// 联网成功的回调,隐藏下拉刷新的状态
	var endSuccessFun = function(mescroll) {
		if (mescroll) {
			mescroll.endSuccess();
		}
	}

	// 联网失败的回调,隐藏下拉刷新的状态
	var endErrFun = function(mescroll) {
		if (mescroll) {
			mescroll.endErr();
		}
	}
	return {
		init : init,
		endSuccessFun : endSuccessFun,
		endErrFun : endErrFun
	}
})();
