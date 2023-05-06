<template>
  <view class="container">
    <view>
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
              <u-col span="10">
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
        storeList: [{
            storeName: "华侨新春工作室0",
            distance: "889m",
            labels: ["VVIP", "V次卡", "代金券"],
            status: "休息中",
            openTime: "11:33 -23:00",
            mobile: "18124671230",
            address: "深圳市宝安区华侨新春西堤三巷21号"
          },{
            storeName: "华侨新春工作室1",
            distance: "889m",
            labels: ["VVIP", "V次卡", "代金券"],
            status: "休息中",
            openTime: "11:33 -23:00",
            mobile: "18124671230",
            address: "深圳市宝安区华侨新春西堤三巷21号"
          },{
            storeName: "华侨新春工作室1",
            distance: "889m",
            labels: ["VVIP", "V次卡", "代金券"],
            status: "休息中",
            openTime: "11:33 -23:00",
            mobile: "18124671230",
            address: "深圳市宝安区华侨新春西堤三巷21号"
          },{
            storeName: "华侨新春工作室1",
            distance: "889m",
            labels: ["VVIP", "V次卡", "代金券"],
            status: "休息中",
            openTime: "11:33 -23:00",
            mobile: "18124671230",
            address: "深圳市宝安区华侨新春西堤三巷21号"
          },{
            storeName: "华侨新春工作室1",
            distance: "889m",
            labels: ["VVIP", "V次卡", "代金券"],
            status: "休息中",
            openTime: "11:33 -23:00",
            mobile: "18124671230",
            address: "深圳市宝安区华侨新春西堤三巷21号"
          },{
            storeName: "华侨新春工作室1",
            distance: "889m",
            labels: ["VVIP", "V次卡", "代金券"],
            status: "休息中",
            openTime: "11:33 -23:00",
            mobile: "18124671230",
            address: "深圳市宝安区华侨新春西堤三巷21号"
          },{
            storeName: "华侨新春工作室1",
            distance: "889m",
            labels: ["VVIP", "V次卡", "代金券"],
            status: "休息中",
            openTime: "11:33 -23:00",
            mobile: "18124671230",
            address: "深圳市宝安区华侨新春西堤三巷21号"
          },{
            storeName: "华侨新春工作室1",
            distance: "889m",
            labels: ["VVIP", "V次卡", "代金券"],
            status: "休息中",
            openTime: "11:33 -23:00",
            mobile: "18124671230",
            address: "深圳市宝安区华侨新春西堤三巷21号"
          },{
            storeName: "华侨新春工作室10",
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
      this.loadBannerData()
    },
    methods: {
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
