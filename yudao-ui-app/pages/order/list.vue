<template>
  <view class="container">
    <u-sticky style="top: 0" offset-top="0">
      <u-tabs :list="tabArray" :current="tabIndex" itemStyle="padding-left: 18px; padding-right: 18px; height: 36px;" @change="handleStatusChange"></u-tabs>
    </u-sticky>
    <view class="order-list">
      <view v-for="order in orderList" :key="order.no" class="order-item">
        <view class="order-header">
          <view class="store-name">服务门店：{{ order.storeName }}</view>
          <view class="order-status">{{ order.status | getStatusName }}</view>
        </view>
        <view class="order-detail">
          <view class="order-no">排队号码：{{ order.no }}</view>
          <view class="create-time">取号时间：{{ order.createTime}}</view>
        </view>
        <view v-if="order.items.length === 1" class="order-single-item" @click="handleOrderClick(order.id)">
          <view class="item-wrap" v-for="item in order.items" :key="item.id">
            <view class="item-info">
              <image class="item-cover" :src="item.picUrl"></image>
              <u--text :lines="2" size="15px" color="#333333" :text="item.spuName"></u--text>
            </view>
            <view class="item-count">共{{ item.count }}件</view>
          </view>
        </view>
        <view v-else class="order-multi-item" @click="handleOrderClick(order.id)">
          <u-scroll-list :indicator="false">
            <view class="item-wrap" v-for="item in order.items" :key="item.id">
              <image class="item-image" :src="item.picUrl"></image>
            </view>
          </u-scroll-list>
          <view class="product-count">共{{ order.productCount }}件</view>
        </view>
        <view class="order-summary">
          <view>合计金额：￥{{ order.summary}}</view>
        </view>
        <view class="order-btn-group">
          <view class="order-btn">再次购买</view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import { getOrderPage } from '../../api/order'
import orderStatus from '@/common/orderStatus'

export default {
  name: 'orderList',
  filters: {
    getStatusName(status) {
      return orderStatus[status + ''].name
    }
  },
  data() {
    return {
      pageNo: 1,
      tabIndex: 0,
      orderList: [{
        storeName: "华侨新村店",
        no:"1111",
        id:121212,
        productCount: 3,
        status: '30',
        createTime: "2022-09-27 10:33:21",
        summary: "34",
        items: [
            {
              picUrl:"https://p9.toutiaoimg.com/origin/tos-cn-i-qvj2lq49k0/4ba5b06b183146f39f35db5c56a6a543?from=pc",
              spuName: "男士单剪",
              count:3,
              id:1111
            }
        ]
      },{
        storeName: "麻布新村店",
        no:"1111",
        id:121212,
        productCount: 2,
        status: '0',
        createTime: "2022-09-27 10:33:21",
        summary: "68",
        items: [
            {
              picUrl:"https://p9.toutiaoimg.com/origin/tos-cn-i-qvj2lq49k0/4ba5b06b183146f39f35db5c56a6a543?from=pc",
              spuName: "男士单剪",
              count:1,
              id:1111
            },
            {
              picUrl:"https://p9.toutiaoimg.com/origin/tos-cn-i-qvj2lq49k0/4ba5b06b183146f39f35db5c56a6a543?from=pc",
              spuName: "洗头发",
              count:1,
              id:1111
            }
        ]
      }]
    }
  },
  computed: {
    tabArray() {
      let tabArray = [{ name: '全部', status: 'all' }]
      for (let status in orderStatus) {
        if (status !== '40') {
          tabArray.push({ name: orderStatus[status].name, status: status })
        }
      }
      return tabArray
    }
  },
  onLoad(e) {
    const status = e.status
    if (status !== undefined) {
      this.tabArray.forEach((item, index) => {
        if (item.status === status) {
          this.tabIndex = index
        }
      })
    }
    //  this.loadOrderPageData()
  },
  methods: {
    handleStatusChange({ index }) {
      this.tabIndex = index
      this.loadOrderPageData()
    },
    loadOrderPageData() {
      let params = { pageNo: this.pageNo }
      const status = this.tabArray[this.tabIndex].status
      if (status !== 'all') {
        params.orderStatus = status
      }
      getOrderPage(params)
        .then(res => {
          this.orderList = res.data.list || []
        })
        .catch(err => {
          console.log(err)
        })
    },
    handleOrderClick(orderId) {
      uni.$u.route('/pages/order/detail', {
        orderId: orderId
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.order-list {
  background-color: #f3f3f3;

  .order-item {
    padding: 20rpx;
    background-color: #ffffff;
    border-bottom: $custom-border-style;

    .order-header {
      @include flex-space-between;
      height: 80rpx;
      .store-name {
        font-size: 28rpx;
        color: #333;
        font-weight: 600;
      }

      .order-status {
        font-size: 24rpx;
        color: #999;
      }
    }
    .order-detail {
      font-size: 24rpx;
      color: #333;
      .order-no {
        height: 24px;
      }
      .create-time {
        height: 24px;
      }
    }
    .order-single-item {
      .item-wrap {
        @include flex-space-between();

        .item-info {
          @include flex-left();

          .item-cover {
            width: 100rpx;
            height: 100rpx;
            border-radius: 10rpx;
            margin-right: 15rpx;
          }
        }

        .item-count {
          color: #999;
          font-size: 24rpx;
          width: 120rpx;
          text-align: right;
        }
      }
    }

    .order-multi-item {
      @include flex-space-between();

      .item-wrap {
        margin-right: 20rpx;

        .item-image {
          width: 100rpx;
          height: 100rpx;
          border-radius: 10rpx;
        }
      }

      .product-count {
        color: #999;
        font-size: 24rpx;
        width: 120rpx;
        text-align: right;
      }
    }

    .order-btn-group {
      margin-top: 10rpx;
      @include flex-right();

      .order-btn {
        width: 120rpx;
        height: 36rpx;
        line-height: 36rpx;
        border-radius: 36rpx;
        border: 1px solid #777;
        color: #777;
        font-size: 22rpx;
        text-align: center;
        margin-left: 15rpx;
      }
    }

    .order-summary {
      margin-top: 10rpx;
      @include flex-right();
      font-size: 28rpx;
      color: #333;
      font-weight: 600;
    }
  }
}
</style>
