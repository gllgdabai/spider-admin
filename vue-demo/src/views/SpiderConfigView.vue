<template>
  <div style="padding: 10px">
    <!-- 功能区 -->
    <div style="margin: 10px">
      <el-button type="primary" @click="add">新增</el-button>
      <el-button type="primary" disabled>导入</el-button>
      <el-button type="primary" disabled>导出</el-button>
    </div>
    <!-- 搜索区 -->
    <div style="margin: 10px">
      爬虫名称：<el-input v-model="searchByName" placeholder="请输入关键字" style="width: 12%;" clearable/>
      <el-button style="margin-left: 10px" type="primary" @click="load">查询</el-button>
      爬虫编码：<el-input v-model="searchByCode" placeholder="请输入关键字" style="width: 12%;" clearable/>
      <el-button style="margin-left: 10px" type="primary" @click="load">查询</el-button>
    </div>
    <el-table :data="tableData" border stripe style="width: 100%"
              v-loading="loading"
              element-loading-text="拼命加载中"
              element-loading-background="rgba(0, 0, 0, 0.8)"
    >
      <el-table-column prop="spiderCode" label="爬虫编码"  />
      <el-table-column prop="spiderName" label="爬虫名称"  />
      <el-table-column prop="domain" label="网站根域名" />
      <el-table-column prop="charset" label="字符集" />
      <el-table-column prop="sleepTime" label="睡眠时间(ms)" />
      <el-table-column prop="retryTimes" label="重试次数" />
      <el-table-column prop="threadCount" label="线程数量" />
      <el-table-column prop="useProxy" label="使用代理" :formatter="selectDictLabel" />
      <el-table-column fixed="right" label="操作" width="220">
        <template #default="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="primary" size="small" @click="handleFields(scope.row.id)">配置字段</el-button>
          <el-popconfirm title="您确定要删除吗?" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button type="danger" size="small" >删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px">
      <el-pagination
          v-model:currentPage="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
      <el-dialog
          v-model="dialogVisible"
          title="提示"
          width="40%"
          :before-close="handleClose"
      >
        <el-form :model="form" :rules="rules" ref="form" label-width="180px" class="demo-ruleForm">
          <el-form-item label="爬虫编码" prop="spiderCode">
            <el-input v-model="form.spiderCode" placeholder="请输入爬虫编码" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="爬虫名称" prop="spiderName">
            <el-input v-model="form.spiderName" placeholder="请输入爬虫名称" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="入口地址">
            <el-input type="textarea" v-model="form.entryUrls" placeholder="请输入入口地址" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="目标URL正则" prop="targetRegex">
            <el-input v-model="form.targetRegex" placeholder="请输入目标URL正则" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="网站根域名" prop="domain">
            <el-input v-model="form.domain" placeholder="请输入网站根域名" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="字符集">
            <el-input v-model="form.charset" placeholder="请输入字符集" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="睡眠时间(ms)">
            <el-input v-model="form.sleepTime" placeholder="请输入睡眠时间" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="重试次数">
            <el-input v-model="form.retryTimes" placeholder="请输入重试次数" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="线程数量">
            <el-input v-model="form.threadCount" placeholder="请输入线程数量" style="width: 80%"/>
          </el-form-item>
            使用代理：
            <el-switch
                v-model="form.useProxy"
                :active-value="1"
                :inactive-value="0"
                active-color="#13ce66"
                inactive-color="#808080"
                style="width: 30%">
            </el-switch>
            打印日志：
            <el-switch
                v-model="form.showLog"
                :active-value="1"
                :inactive-value="0"
                active-color="#13ce66"
                inactive-color="#808080"
                style="width: 30%">
            </el-switch>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
          <el-button type="primary" @click="save">确定</el-button>
          <el-button @click="dialogVisible = false">取消</el-button>
      </span>
        </template>
      </el-dialog>
      <el-dialog
          v-model="configFieldsDialogVisible"
          title="配置字段"
          width="50%"
          :before-close="handleClose"

      >
        <!-- 功能区 -->
        <div style="margin: 10px">
          <el-button type="primary" @click="addField(configId)">添加字段</el-button>
          <el-button type="danger" @click="batchRemove(row)">批量删除</el-button>
        </div>
        <el-table
            :data="tableFields"
            border stripe style="width: 100%"
            @selection-change="handleSelectionChange">
          <el-table-column
              type="selection"
              width="55">
          </el-table-column>
          <el-table-column prop="field" label="字段"  />
          <el-table-column prop="fieldName" label="字段名称"  />
          <el-table-column prop="extractType" label="提取类型" />
          <el-table-column prop="extractBy" label="提取规则" />
          <el-table-column fixed="right" label="操作" width="150">
            <template #default="scope">
              <el-button type="primary" size="small" @click="handleFieldEdit(scope.row)">编辑</el-button>
              <el-popconfirm title="您确定要删除吗?" @confirm="handleFieldDelete(scope.row.fieldId)">
                <template #reference>
                  <el-button type="danger" size="small" >删除</el-button>
                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>

        <template #footer>
      <span class="dialog-footer">
          <el-button @click="configFieldsDialogVisible = false;">取消</el-button>
      </span>
        </template>
      </el-dialog>

      <el-dialog
          v-model="fieldDiaLogVisible"
          title="字段"
          width="35%"
          :before-close="handleClose"
      >
        <!-- 表单 -->
        <el-form :model="fieldForm" :rules="fieldFormRules" ref="fieldForm"  label-width="120px">
          <el-form-item>
            <el-input v-model="fieldForm.configId" type="hidden" />
          </el-form-item>
          <el-form-item label="字段" prop="field">
            <el-input v-model="fieldForm.field" placeholder="请输入字段" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="字段名称" prop="fieldName">
            <el-input v-model="fieldForm.fieldName" placeholder="请输入字段名称" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="提取类型" prop="extractType">
            <el-radio v-model="fieldForm.extractType" label="xpath" >Xpath</el-radio>
            <el-radio v-model="fieldForm.extractType" label="css" >Css</el-radio>
            <el-radio v-model="fieldForm.extractType" label="constant" >常量</el-radio>
          </el-form-item>
          <el-form-item label="提取规则" prop="extractBy" v-if="fieldForm.extractType != 'constant'">
            <el-input v-model="fieldForm.extractBy" placeholder="请输入Xpath值或Css值"  style="width: 80%"/>
          </el-form-item>
          <el-form-item label="元素的索引" v-if="fieldForm.extractType === 'css'">
            <el-input v-model="fieldForm.extractIndex" placeholder="数字间用逗号分隔" style="width: 80%" />
          </el-form-item>
          <el-form-item label="根据属性取值" v-if="fieldForm.extractType === 'css'">
            <el-switch v-model="fieldForm.extractAttrFlag" active-color="#13ce66" inactive-color="#808080" />
          </el-form-item>
          <el-form-item label="属性名" v-if="fieldForm.extractAttrFlag === true">
            <el-input v-model="fieldForm.extractAttr" placeholder="请输入属性名" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="常量值" prop="constantValue" v-if="fieldForm.extractType === 'constant'">
            <el-input v-model.number="fieldForm.constantValue" style="width: 80%"/>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
          <el-button type="primary" @click="saveField">确定</el-button>
          <el-button @click="fieldDiaLogVisible = false;">取消</el-button>
      </span>
        </template>
      </el-dialog>

    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import request from "@/utils/request";
import axios from "axios";
import moment from 'moment';

export default {
  name: "SpiderMissionView",
  components: {},
  data() {
    return {
      searchByName: '',
      searchByCode: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      configFieldsDialogVisible: false,
      fieldDiaLogVisible: false,
      loading: true,
      configId: '',
      form: {},
      fieldForm: {},
      tableData: [],
      tableFields: [],
      rules: {
        spiderCode: [
          { required: true, message: '请输入爬虫编码', trigger: 'blur' },
        ],
        spiderName: [
          { required: true, message: '请输入爬虫名称', trigger: 'blur' }
        ],
        targetRegex: [
          { required: true, message: '请输入目标URL正则', trigger: 'blur' }
        ],
        domain: [
          { required: true, message: '请输入网站根域名', trigger: 'blur' }
        ]
      },
      fieldFormRules: {
        field: [
          { required: true, message: '请输入字段', trigger: 'blur' },
        ],
        fieldName: [
          { required: true, message: '请输入字段名称', trigger: 'blur' },
        ],
        extractType: [
          { required: true, message: '请选择提取类型', trigger: 'change' }
        ],
        extractBy: [
          { required: true, message: '请输入提取规则', trigger: 'blur' },
        ],
        constantValue: [
          { required: true, message: '常量值不能为空'},
          { type: 'number', message: '常量值必须为数字值'}
        ]
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.loading = true
      request.get("/spider/spiderConfig", {
        params:{
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          searchByName: this.searchByName,
          searchByCode: this.searchByCode
        }
      }).then(res => {
        console.log(res)
        this.loading = false;
        this.tableData = res.data.list
        this.total = res.data.total
      })
    },
    add() {
      this.dialogVisible = true
      this.form = {
        charset: 'utf8',
        sleepTime: 1000,
        retryTimes: 2,
        threadCount: 1,
        useProxy: 0,
        showLog: 1
      }
    },
    addField(id) {
      console.log(id)
      this.fieldDiaLogVisible = true
      this.fieldForm = {
        configId: id,
        extractIndex: 0
      }
    },
    save() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id) {  // 更新
            request.post("/spider/spiderConfig/edit", this.form).then(res => {
              console.log(res)
              if (res.code === 0) {
                this.$message({
                  type: "success",
                  message: "更新成功"
                })
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }
              this.load() // 刷新表格的数据
              this.dialogVisible = false  // 关闭弹窗
            })
          } else {  // 新增
            request.post("/spider/spiderConfig/add", this.form).then(res => {
              console.log(res)
              if (res.code === 0) {
                this.$message({
                  type: "success",
                  message: "新增成功"
                })
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }
              this.load() // 刷新表格的数据
              this.dialogVisible = false  // 关闭弹窗
            })
          }
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      });
    },
    saveField() {
      this.$refs['fieldForm'].validate((valid) => {
        if (valid) {
          if (this.fieldForm.fieldId) {  // 更新
            request.post("/spider/spiderField/edit", this.fieldForm).then(res => {
              console.log(res)
              if (res.code === 0) {
                this.$message({
                  type: "success",
                  message: "更新成功"
                })
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }
              // 刷新表格的数据
              this.fieldDiaLogVisible = false  // 关闭弹窗
              this.configFieldsDialogVisible = false;
            })
          } else {  // 新增
            request.post("/spider/spiderField/add", this.fieldForm).then(res => {
              console.log(res)
              if (res.code === 0) {
                this.$message({
                  type: "success",
                  message: "新增成功"
                })
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }

              // 刷新表格的数据
              this.fieldDiaLogVisible = false  // 关闭弹窗
              this.configFieldsDialogVisible = false;
            })
          }
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      });
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleFieldEdit(row) {
      this.fieldForm = JSON.parse(JSON.stringify(row))
      this.fieldDiaLogVisible = true
    },
    handleFields(id) {
      console.log(id)
      request.get("/spider/spiderField/" + id).then(res => {
        console.log(res)
        this.tableFields = res.data
      })
      this.configId = id;
      this.configFieldsDialogVisible = true
    },
    handleDelete(id) {
      console.log(id)
      request.post("/spider/spiderConfig/remove/" + id).then(res => {
        if (res.code === 0) {
          this.$message({
            type: "success",
            message: "删除成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load()  // 删除之后重新加载表格的数据
      })
    },
    handleFieldDelete(id) {
      console.log(id)
      request.post("/spider/spiderField/remove/" + id).then(res => {
        if (res.code === 0) {
          this.$message({
            type: "success",
            message: "删除成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        // 删除之后重新加载表格的数据
        // 直接关闭弹窗
        this.configFieldsDialogVisible = false;
      })
    },
    handleSizeChange(pageSize) {   // 改变当前每页的个数触发
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {  // 改变当前页码触发
      this.currentPage = pageNum
      this.load()
    },
    selectDictLabel: function (row, column, value) {
      var data = row[column.property];
      if(data == undefined) return '';
      else if(data === 1 || data === '1') return "使用";
      else return "不使用";
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    }
  }

}

</script>
