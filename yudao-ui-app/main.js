import Vue from 'vue'
import App from './App'

// 引入全局uView
import uView from '@/uni_modules/uview-ui'

// vuex
import store from './store'

Vue.config.productionTip = false
Vue.prototype.$store = store

// wx js sdk
import wechat from './common/wechat'
Vue.prototype.$wx = wechat

App.mpType = 'app'
Vue.use(uView)

import {getOpenId} from './api/index'

const app = new Vue({
	store,
	...App
})

// 引入请求封装
require('./utils/request/index')(app)

if (wechat.isWechat()) {
  // getOpenId({
  //   code: wechat.getUrlParams("code")
  // }).then(res => {
  //   console.log("res", res);
  //   app.$mount()
  // })
  app.$mount()
}

