<template>
  <view class="container">
    <u-sticky offset-top="0">
      <!--轮播图-->
      <yd-banner :banner-list="bannerList"></yd-banner>
      <!--搜索栏-->
      <view class="search-wrap">
        <u-search :label="addressText" bgColor="" border-color="#f2f2f2" :searchIcon="searchIcon" searchIconSize="10"
          placeholder="请搜索" :show-action="true"  @clickIcon="hideKeyboard()">
        </u-search>
        <w-picker :visible.sync="regionVisible" mode="region" :value="defaultRegion" default-type="value"
          :hide-area="false" @confirm="onConfirm($event, 'region')" @cancel="onCancel" ref="region"></w-picker>
      </view>
    </u-sticky>
    <u-list>
      <u-list-item>
        <u-cell v-for="(item, index) in storeList" :key="index">
          <view slot="title">
            <u-row customStyle="">
              <u-col span="10">
                <u--text size="14" :bold="true" :text="item.storeName"></u--text>
              </u-col>
              <u-col span="2">
                <u--text size="12" color="red" align="center" :text="item.distance"></u--text>
              </u-col>
            </u-row>
            <u-row style="margin-top: 5px;">
              <span v-for="(labelItem, labelIndex) in item.labels" :key="labelIndex">
                <u-tag style="margin-right: 5px;" shape="circle" size="mini" :text="labelItem" color="#ccc"
                  borderColor="#ccc" plain></u-tag>
              </span>
            </u-row>
            <u-row style="margin-top: 10px;">
              <u-tag :text="item.status" color="#007aff" borderColor="#ccc" bgColor="#ccc" size="mini" plain plainFill>
              </u-tag>
              <span style="font-size: 12px;margin-left: 5px;color:orangered">营业时间</span>
              <span style="font-size: 12px;margin-left: 5px;color:orangered">{{item.openTime}}</span>
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
      </u-list-item>
    </u-list>

    <u-gap height="50px"></u-gap>
  </view>
</template>

<script>
  import {
    getBannerData,
    getNoticeData
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
        addressText: "深圳市 宝安区",
        addressCode: [],
        searchIcon: "arrow-down-fill",
        bannerList: [{
            id: 1,
            title: '山不在高，有仙则名',
            url: 'https://img1.baidu.com/it/u=3298304243,1273560633&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500'
          },
          {
            id: 2,
            title: '水不在深，有龙则灵',
            url: 'https://cdn.uviewui.com/uview/swiper/swiper3.png'
          },
          {
            id: 3,
            title: '斯是陋室，惟吾德馨',
            url: 'https://cdn.uviewui.com/uview/swiper/swiper3.png'
          }
        ],
        storeList: [{
            storeName: "华侨新春工作室1",
            distance: "889m",
            labels: ["VVIP", "V次卡", "代金券"],
            status: "休息中",
            openTime: "11:33 -23:00",
            mobile: "18124671230",
            address: "深圳市宝安区华侨新春西堤三巷21号"
          }
        ],
        defaultRegion: ["440000", "440300", "440306"]
      }
    },
    onLoad() {
      // 加载banner数据
      // this.loadBannerData()
    },
    methods: {
      openLocation() {
        console.log("xxxxxx");
        // wx.getLocation({
        //  type: 'gcj02', //Returns the latitude and longitude that can be used for wx.openLocation
        //  success (res) {
        //    const latitude = res.latitude
        //    const longitude = res.longitude
        //    wx.openLocation({
        //      latitude,
        //      longitude,
        //      scale: 18
        //    })
        //  }
        // })
      },
      loadBannerData() {
        getBannerData().then(res => {
          this.bannerList = res.data
        })
      },
      handleSearchClick(e) {
        uni.$u.route('/pages/search/search')
      },
      onConfirm(res) {
        console.log("confirm", res);
        this.searchIcon = "arrow-down-fill"
        this.addressCode = res.value;
        this.addressText = res.obj.city.label + " " + res.obj.area.label
      },
      onCancel() {
        this.searchIcon = "arrow-down-fill"
      },
      hideKeyboard() {
        this.searchIcon = "arrow-up-fill"
        this.regionVisible = true;
        console.log("hiden key board")
        uni.hideKeyboard()
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
