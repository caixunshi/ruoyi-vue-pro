<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="门店名称" prop="storeName">
        <el-input v-model="queryParams.storeName" placeholder="请输入门店名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="营业时间" prop="openTime">
        <el-date-picker v-model="queryParams.openTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="营业时间" prop="closeTime">
        <el-date-picker v-model="queryParams.closeTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="联系电话" prop="mobile">
        <el-input v-model="queryParams.mobile" placeholder="请输入联系电话" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="城市名称" prop="cityName">
        <el-input v-model="queryParams.cityName" placeholder="请输入城市名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="区名称" prop="areaName">
        <el-input v-model="queryParams.areaName" placeholder="请输入区名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="经度" prop="longitude">
        <el-input v-model="queryParams.longitude" placeholder="请输入经度" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="标签" prop="labels">
        <el-input v-model="queryParams.labels" placeholder="请输入标签" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="纬度" prop="latitude">
        <el-input v-model="queryParams.latitude" placeholder="请输入纬度" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker v-model="queryParams.createTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['t:store-info:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['t:store-info:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="门店名称" align="center" prop="storeName" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="营业时间" align="center" prop="openTime" />
      <el-table-column label="营业时间" align="center" prop="closeTime" />
      <el-table-column label="联系电话" align="center" prop="mobile" />
      <el-table-column label="城市名称" align="center" prop="cityName" />
      <el-table-column label="区名称" align="center" prop="areaName" />
      <el-table-column label="经度" align="center" prop="longitude" />
      <el-table-column label="标签" align="center" prop="labels" />
      <el-table-column label="纬度" align="center" prop="latitude" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['t:store-info:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['t:store-info:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" v-dialogDrag append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="门店名称" prop="storeName">
          <el-input v-model="form.storeName" placeholder="请输入门店名称" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="营业时间" prop="openTime">
          <el-date-picker clearable v-model="form.openTime" type="date" value-format="timestamp" placeholder="选择营业时间" />
        </el-form-item>
        <el-form-item label="营业时间" prop="closeTime">
          <el-date-picker clearable v-model="form.closeTime" type="date" value-format="timestamp" placeholder="选择营业时间" />
        </el-form-item>
        <el-form-item label="联系电话" prop="mobile">
          <el-input v-model="form.mobile" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="城市名称" prop="cityName">
          <el-input v-model="form.cityName" placeholder="请输入城市名称" />
        </el-form-item>
        <el-form-item label="区名称" prop="areaName">
          <el-input v-model="form.areaName" placeholder="请输入区名称" />
        </el-form-item>
        <el-form-item label="经度" prop="longitude">
          <el-input v-model="form.longitude" placeholder="请输入经度" />
        </el-form-item>
        <el-form-item label="标签" prop="labels">
          <el-input v-model="form.labels" placeholder="请输入标签" />
        </el-form-item>
        <el-form-item label="纬度" prop="latitude">
          <el-input v-model="form.latitude" placeholder="请输入纬度" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { createStoreInfo, updateStoreInfo, deleteStoreInfo, getStoreInfo, getStoreInfoPage, exportStoreInfoExcel } from "@/api/system/storeInfo";

export default {
  name: "StoreInfo",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 门店信息列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        storeName: null,
        status: null,
        openTime: [],
        closeTime: [],
        mobile: null,
        cityName: null,
        areaName: null,
        longitude: null,
        labels: null,
        latitude: null,
        createTime: [],
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        storeName: [{ required: true, message: "门店名称不能为空", trigger: "blur" }],
        openTime: [{ required: true, message: "营业时间不能为空", trigger: "blur" }],
        closeTime: [{ required: true, message: "营业时间不能为空", trigger: "blur" }],
        mobile: [{ required: true, message: "联系电话不能为空", trigger: "blur" }],
        cityName: [{ required: true, message: "城市名称不能为空", trigger: "blur" }],
        areaName: [{ required: true, message: "区名称不能为空", trigger: "blur" }],
        longitude: [{ required: true, message: "经度不能为空", trigger: "blur" }],
        labels: [{ required: true, message: "标签不能为空", trigger: "blur" }],
        latitude: [{ required: true, message: "纬度不能为空", trigger: "blur" }],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 执行查询
      getStoreInfoPage(this.queryParams).then(response => {
        this.list = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 表单重置 */
    reset() {
      this.form = {
        id: undefined,
        storeName: undefined,
        status: undefined,
        openTime: undefined,
        closeTime: undefined,
        mobile: undefined,
        cityName: undefined,
        areaName: undefined,
        longitude: undefined,
        labels: undefined,
        latitude: undefined,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNo = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加门店信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getStoreInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改门店信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }
        // 修改的提交
        if (this.form.id != null) {
          updateStoreInfo(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createStoreInfo(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除门店信息编号为"' + id + '"的数据项?').then(function() {
        return deleteStoreInfo(id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      // 处理查询参数
      let params = {...this.queryParams};
      params.pageNo = undefined;
      params.pageSize = undefined;
      this.$modal.confirm('是否确认导出所有门店信息数据项?').then(() => {
        this.exportLoading = true;
        return exportStoreInfoExcel(params);
      }).then(response => {
        this.$download.excel(response, '门店信息.xls');
        this.exportLoading = false;
      }).catch(() => {});
    }
  }
};
</script>
