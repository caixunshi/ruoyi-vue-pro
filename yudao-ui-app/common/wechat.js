//自己封装的 uni.request
const { http } = uni.$u
var jweixin = require('jweixin-module');

export default {
  /*获取url参数*/
  getUrlParams: function(key) {
    let url = document.URL;
    // 去掉定位符之后的数据
    url = url.substring(0, url.indexOf('#'))
    // 通过 ? 分割获取后面的参数字符串
    let urlStr = url.split('?')[1]
      // 创建空对象存储参数
    let obj = {};
      // 再通过 & 将每一个参数单独分割出来
    let paramsArr = urlStr.split('&')
    for(let i = 0,len = paramsArr.length;i < len;i++){
          // 再通过 = 将每一个参数分割为 key:value 的形式
      let arr = paramsArr[i].split('=')
      obj[arr[0]] = arr[1];
    }
    return obj[key]
  },
	/* 判断是否在微信中 */
	isWechat: function() {
		var ua = window.navigator.userAgent.toLowerCase();
    if (ua.match(/micromessenger/i) == 'micromessenger') {
			//console.log('是微信客户端')
			return true;
		} else {
			//console.log('不是微信客户端')
			//以下是我项目中所需要的操作其他，可以自定义
			//alert("请在微信浏览器中打开")
			return true;
		}
	},
	/* 获取sdk初始化配置 */
	initJssdk: function(callback) {
		//获取当前url然后传递给后台获取授权和签名信息
		var url = window.location.href.split('#')[0];
    http.post('/wx/getJsSdkInfo', {
      url: url
    }).then(res => {
			//返回需要的参数appId,timestamp,noncestr,signature等
			//注入config权限配置
      console.log("res", res)
			const { data } = res;
			jweixin.config({
				debug: false,
				appId: data.appId,
				timestamp: data.timestamp,
				nonceStr: data.nonceStr,
				signature: data.signature,
				jsApiList: [ //这里是需要用到的接口名称
					'checkJsApi', //判断当前客户端版本是否支持指定JS接口
					'updateAppMessageShareData', //分享朋友
					'updateTimelineShareData', //分享朋友圈
					'getLocation', //获取位置
					'openLocation', //打开位置
					'scanQRCode', //扫一扫接口
					'chooseWXPay', //微信支付
					'chooseImage', //拍照或从手机相册中选图接口
					'previewImage', //预览图片接口
					'uploadImage' //上传图片
				]
			});
			if (callback) {
				callback(res.data);
			}
    });
	},
	//在需要定位页面调用
	getlocation: function(callback) {
		if (!this.isWechat()) {
			//console.log('不是微信客户端')
			return;
		}
		this.initJssdk(function(res) {
			jweixin.ready(function() {
				jweixin.getLocation({
					type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
					success: function(res) {
						console.log(res);
						callback(res)
					},
					fail: function(res) {
						console.log(res)
					},
				});
			});
		});
	},
	//打开位置
	openlocation: function(data, callback) {
		if (!this.isWechat()) {
			//console.log('不是微信客户端')
			return;
		}
		this.initJssdk(function(res) {
			jweixin.ready(function() {
				jweixin.openLocation({ //根据传入的坐标打开地图
					latitude: data.latitude,
					longitude: data.longitude
				});
			});
		});
	},
	//选择图片
	chooseImage: function(callback) {
		if (!this.isWechat()) {
			//console.log('不是微信客户端')
			return;
		}
		//console.log(data);
		this.initJssdk(function(res) {
			jweixin.ready(function() {
				jweixin.chooseImage({
					count: 1,
					sizeType: ['compressed'],
					sourceType: ['album'],
					success: function(rs) {
						callback(rs)
					}
				})
			});
		});
	},
	//微信支付
	wxpay: function(data, callback) {
		if (!this.isWechat()) {
			//console.log('不是微信客户端')
			return;
		}
		this.initJssdk(function(res) {
			jweixin.ready(function() {
				jweixin.chooseWXPay({
					timestamp: data.timestamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
					nonceStr: data.nonceStr, // 支付签名随机串，不长于 32 位
					package: data.package, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=\*\*\*）
					signType: data.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
					paySign: data.paysign, // 支付签名
					success: function(res) {
						// console.log(res);
						callback(res)
					},
					fail: function(res) {
						callback(res)
					},
				});
			});
		});
	},
	//微信扫码
	scanQRCode: function(callback) {
		if (!this.isWechat()) {
			//console.log('不是微信客户端')
			return;
		}
		this.initJssdk(function(res) {
			jweixin.ready(function() {
				jweixin.scanQRCode({
					needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
					scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
					success: function(res) {
						// console.log(res);
						callback(res);
					},
					fail: function(res) {
						callback(res)
					},
				});
			});
		});
	},
	//自定义分享  这里我统一调用了分享到朋友和朋友圈，可以自行定义
	share: function(callback) {
		if (!this.isWechat()) {
			//console.log('不是微信客户端')
			return;
		}
		this.initJssdk(function(res) {
			jweixin.ready(function() {

				//我的分享配置由后台返回，可以自定义
				http.get({
					url: 'getShareInfo'
				}).then(res => {
					const { shareInfo } = res.data;
					jweixin.updateAppMessageShareData({ //分享给朋友
						title: shareInfo.title,
						desc: shareInfo.description,
						imgUrl: shareInfo.image,
						link: shareInfo.link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
						success: function() {
							// 用户确认分享后执行的回调函数
							callback(res);
						}
					});
					jweixin.updateTimelineShareData({ //分享到朋友圈
						title: shareInfo.title,
						desc: shareInfo.description,
						imgUrl: shareInfo.image,
						link: shareInfo.link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
						success: function() {
							// 用户确认分享后执行的回调函数
							callback(res);
						}
					});
				});
			});
		});
	},
  getDistance: function(lat1, lng1, lat2, lng2) {
      lat1 = lat1 || 0;
      lng1 = lng1 || 0;
      lat2 = lat2 || 0;
      lng2 = lng2 || 0;
  
      var rad1 = lat1 * Math.PI / 180.0;
      var rad2 = lat2 * Math.PI / 180.0;
      var a = rad1 - rad2;
      var b = lng1 * Math.PI / 180.0 - lng2 * Math.PI / 180.0;
      var r = 6378137;
      var distance = r * 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(rad1) * Math.cos(rad2) * Math.pow(Math.sin(b / 2), 2)));
  
      return distance;
  }
}

