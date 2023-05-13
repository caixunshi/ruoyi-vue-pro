<template>
  <view class="container">
    <view>
      <!--轮播图-->
      <yd-banner :banner-list="bannerList"></yd-banner>
      <!--搜索栏-->
      <view class="search-wrap">
        <u-search :label="addressCity + ' ' + addressArea" bgColor="" border-color="#f2f2f2" :searchIcon="searchIcon"
          v-model="storeName" @search="onSearch()" @custom="onSearch()" @clickIcon="hideKeyboard()"
          searchIconSize="10" placeholder="请输入门店名称" :show-action="true">
        </u-search>
        <w-picker :visible.sync="regionVisible" mode="region" :value="defaultRegion" default-type="value"
          :hide-area="false" @confirm="onConfirm($event, 'region')" @cancel="onCancel" ref="region"></w-picker>
      </view>
    </view>
    <u-list>
      <u-list-item>
        <u-cell v-for="(item, index) in storeList" :key="index">
          <view slot="title">
            <u-row customStyle="">
              <u-col span="10">
                <u--text size="14" :bold="true" :text="item.storeName"></u--text>
              </u-col>
              <u-col span="2">
                <u--text size="12" color="red" align="center" :text="distanceGenetate(item)"></u--text>
              </u-col>
            </u-row>
            <u-row style="margin-top: 5px;">
              <u-notice-bar style="padding: 2px 12px" v-if="!!item.labels" fontSize="12" :text="item.labels"></u-notice-bar>
            </u-row>
            <!--营业状态-->
            <u-row v-if="statusGenerate(item) === 0" style="margin-top: 10px;">
              <u-icon name="cut" color="#5ac725" style="margin-right: 2px;"></u-icon>
              <u--text type="success" size="12" text="营业中"></u--text>
              <span style="font-size: 12px;margin-left: 5px;color:#5ac725">营业时间</span>
              <span style="font-size: 12px;margin-left: 5px;color:#5ac725">{{openTimeGenerate(item)}}</span>
            </u-row>
            <u-row v-else style="margin-top: 10px;">
              <u-icon name="clock" color="#f56c6c" style="margin-right: 2px;"></u-icon>
              <u--text type="error" size="12" text="休息中"></u--text>
              <span style="font-size: 12px;margin-left: 5px;color:#f56c6c">营业时间</span>
              <span style="font-size: 12px;margin-left: 5px;color:#f56c6c">{{openTimeGenerate(item)}}</span>
            </u-row>
            <u-row style="margin-top: 10px;">
              <u-icon size="20" name="phone" color="#ccc"></u-icon>
              <u--text :text="item.mobile" size="12"></u--text>
            </u-row>
            <u-row style="margin-top: 10px;">
              <u-col span="10" @click="openLocation()">
                <u--text :lines="1" prefixIcon="map" :text="item.address" size="12"></u--text>
              </u-col>
              <u-col span="2">
                <navigator class="fixed-btn-box" url="/pages/store/store" open-type="navigate" hover-class="none">
                  <u-button size="mini" class="custom-style" shape="circle" color="red" text="去剪发"></u-button>
                </navigator>
              </u-col>
            </u-row>
          </view>
        </u-cell>
        <u-gap height="220"></u-gap>

      </u-list-item>
    </u-list>

    <u-gap height="50px"></u-gap>
  </view>
</template>

<script>
  import {
    getBannerData,
    getStoreList
  } from '../../api/index'
  import {
    getAddressById,
    updateAddress
  } from '../../api/address'
  export default {
    components: {},
    data() {
      return {
        regionVisible: false,
        addressCity: "深圳市",
        addressArea: "宝安区",
        storeName: "",
        searchIcon: "arrow-down-fill",
        latitude: 0,
        longitude: 0,
        bannerList: [{
            id: 1,
            title: '山不在高，有仙则名',
            url: 'https://big.justeasy.cn/effect/201808/20180820095319_5b7a1f0fada77.jpg'
          },
          {
            id: 2,
            title: '水不在深，有龙则灵',
            url: 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.alicdn.com%2Fbao%2Fuploaded%2Fi3%2F2208039127442%2FO1CN01eUZIjo24qUGCU2A0I_%21%210-item_pic.jpg&refer=http%3A%2F%2Fimg.alicdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1685313520&t=581dcbc8d5580225f209b641e862224d'
          },
          {
            id: 3,
            title: '斯是陋室，惟吾德馨',
            url: 'https://img.zx123.cn/Resources/zx123cn/uploadfile/2015/0526/a56f4a17a1a1580cdd93c259b77b8ca8.jpg'
          }
        ],
        storeList: [],
        defaultRegion: ["440000", "440300", "440306"]
      }
    },
    onLoad() {
      // 加载banner数据
      // this.loadBannerData()
      this.loadStoreInfo()
      this.$wx.getlocation((data) => {
        console.log("获取到经纬度信息:", data)
        this.latitude = data.latitude;
        this.longitude = data.longitude;
      })
    },
    methods: {
      openLocation() {
        wx.openLocation({
          latitude: this.latitude,
          longitude: this.longitude,
          scale: 18
        })
      },
      loadBannerData() {
        getBannerData().then(res => {
          this.bannerList = res.data
        })
      },
      loadStoreInfo() {
        getStoreList({
          cityName: this.addressCity,
          areaName: this.addressArea,
          storeName: this.storeName
        }).then(res => {
          this.storeList = res.data.list
        })
      },
      handleSearchClick(e) {
        uni.$u.route('/pages/search/search')
      },
      onConfirm(res) {
        console.log("confirm", res);
        this.searchIcon = "arrow-down-fill"
        this.addressCity = res.obj.city.label;
        this.addressArea = res.obj.area.label;
        // 重新加载门店数据
        this.loadStoreInfo();
      },
      onCancel() {
        this.searchIcon = "arrow-down-fill"
      },
      hideKeyboard() {
        this.searchIcon = "arrow-up-fill"
        this.regionVisible = true;
        uni.hideKeyboard()
      },
      onSearch() {
        // 重新加载门店数据
        this.loadStoreInfo();
      },
      // 计算营业时间
      openTimeGenerate(item) {
          let openTimeStr = new String(item.openTime);
          let closeTimeStr = new String(item.closeTime);
          return openTimeStr.substring(0, 2) + ":" + openTimeStr.substring(2)
          + "~" + closeTimeStr.substring(0, 2) + ":" + closeTimeStr.substring(2)
      },
      // 计算当前状态
      statusGenerate(item) {
          if (item.status == 1) {
            return 1;
          } else {
            let now = Number(new Date().getHours() + "" + new Date().getMinutes());
            if (now < item.openTime || now > item.closeTime) {
              return 1;
            } else {
              return 0;
            }
          }
      },
      // 根据经纬度与当前定位计算距离
      distanceGenetate(item) {
        return this.$wx.getDistance(this.latitude, this.longitude, item.latitude, item.longitude)
      }
    },
    computed: {
    }
  }
</script>

<style lang="scss" scoped>
  .search-wrap {
    background: $custom-bg-color;
    padding: 20rpx;
  }

  .grid-title {
    line-height: 50rpx;
    font-size: 26rpx;
  }
</style>
