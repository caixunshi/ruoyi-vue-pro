<template>
  <view>
    <u-sticky offset-top="0">
      <u--image style="filter:brightness(0.7)" width="100%" height="200px"
        src="https://pic.tugou.com/hotword/3371e21d-15aa-0e62-a4b8-69d6e52cb86e.jpg@815h_1e_1c"></u--image>
    </u-sticky>
    <view class="store-detail">
      <view class="store-name">华侨新春工作室</view>
      <view class="open-time">11:30 - 23:00</view>
      <view class="store-address">宝安区西乡街道华侨新春西提3巷21号 ></view>
    </view>
  </view>
</template>

<script>
</script>

<style>
  .store-detail {
    position: absolute;
    left: 10px;
    top: 100px;
    font-size: 28rpx;
    color: white;
    z-index: 980;
    .store-name {
      font-size: 32rpx;
      font-weight: 600;
    }
  }
</style>
