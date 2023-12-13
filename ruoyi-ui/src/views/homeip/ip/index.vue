<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="家庭名称" prop="homeName">
        <el-input
          v-model="queryParams.homeName"
          placeholder="请输入家庭名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="IP地址" prop="homeIp">
        <el-input
          v-model="queryParams.homeIp"
          placeholder="请输入IP地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="访问UA" prop="homeUa">
        <el-input
          v-model="queryParams.homeUa"
          placeholder="请输入访问UA"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属地址" prop="homeLocation">
        <el-input
          v-model="queryParams.homeLocation"
          placeholder="请输入所属地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="gmtCreat">
        <el-date-picker clearable
          v-model="queryParams.gmtCreat"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="更新时间" prop="gmtModified">
        <el-date-picker clearable
          v-model="queryParams.gmtModified"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择更新时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['homeip:ip:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['homeip:ip:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['homeip:ip:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['homeip:ip:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ipList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="无业务意义自增ID" align="center" prop="id" />
      <el-table-column label="家庭名称" align="center" prop="homeName" />
      <el-table-column label="IP地址" align="center" prop="homeIp" />
      <el-table-column label="访问UA" align="center" prop="homeUa" />
      <el-table-column label="所属地址" align="center" prop="homeLocation" />
      <el-table-column label="创建时间" align="center" prop="gmtCreat" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.gmtCreat, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="gmtModified" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.gmtModified, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['homeip:ip:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['homeip:ip:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改IP地址对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="家庭名称" prop="homeName">
          <el-input v-model="form.homeName" placeholder="请输入家庭名称" />
        </el-form-item>
        <el-form-item label="IP地址" prop="homeIp">
          <el-input v-model="form.homeIp" placeholder="请输入IP地址" />
        </el-form-item>
        <el-form-item label="访问UA" prop="homeUa">
          <el-input v-model="form.homeUa" placeholder="请输入访问UA" />
        </el-form-item>
        <el-form-item label="所属地址" prop="homeLocation">
          <el-input v-model="form.homeLocation" placeholder="请输入所属地址" />
        </el-form-item>
        <el-form-item label="创建时间" prop="gmtCreat">
          <el-date-picker clearable
            v-model="form.gmtCreat"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="更新时间" prop="gmtModified">
          <el-date-picker clearable
            v-model="form.gmtModified"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择更新时间">
          </el-date-picker>
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
import { listIp, getIp, delIp, addIp, updateIp } from "@/api/homeip/ip";

export default {
  name: "Ip",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // IP地址表格数据
      ipList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        homeName: null,
        homeIp: null,
        homeUa: null,
        homeLocation: null,
        gmtCreat: null,
        gmtModified: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询IP地址列表 */
    getList() {
      this.loading = true;
      listIp(this.queryParams).then(response => {
        this.ipList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        homeName: null,
        homeIp: null,
        homeUa: null,
        homeLocation: null,
        gmtCreat: null,
        gmtModified: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加IP地址";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getIp(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改IP地址";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateIp(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addIp(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除IP地址编号为"' + ids + '"的数据项？').then(function() {
        return delIp(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('homeip/ip/export', {
        ...this.queryParams
      }, `ip_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
