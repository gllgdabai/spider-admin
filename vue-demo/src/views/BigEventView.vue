<template>
  <div style="padding: 10px">
    <!-- 功能区 -->
    <div style="margin: 10px">
      <el-button type="primary" @click="add">新增</el-button>
      <el-button type="primary" disabled>导出</el-button>
    </div>
    <!-- 搜索区 -->
    <div style="margin: 10px">
      <el-select v-model="value_year" clearable placeholder="选择年份" style="width: 100px">
        <el-option
            v-for="item in options"
            :key="item.value_year"
            :label="item.label"
            :value="item.value_year">
        </el-option>
      </el-select>
    </div>
    <div style="margin: 10px">
      大事件检索： <el-input v-model="search" placeholder="请输入关键词" style="width: 150px;" clearable/>
      <el-button style="margin-left: 10px" type="primary" @click="load">查询</el-button>
    </div>
    <el-table :data="tableData" border stripe style="width: 100%"
              v-loading="loading"
              max-height="700"
              element-loading-text="拼命加载中"
              element-loading-background="rgba(0, 0, 0, 0.8)"
    >
      <el-table-column prop="year" label="年份" width="80" />
      <el-table-column prop="date" label="日期" width="150"  />
      <el-table-column prop="event" label="事件"  />
      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
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
          title="大事件信息"
          width="40%"
          :before-close="handleClose"
      >
        <el-form :model="form" :rules="rules" ref="form" label-width="120px">
          <el-form-item label="年份" prop="year">
            <el-input v-model.number="form.year" placeholder="请输入年份" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="日期" prop="date">
            <el-input v-model="form.date" placeholder="请输入日期" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="事件" prop="event">
            <el-input v-model.number="form.event" placeholder="请输入事件信息" style="width: 80%"/>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
          <el-button type="primary" @click="save('form')">确定</el-button>
          <el-button @click="dialogVisible = false">取消</el-button>
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

export default {
  name: "BigEvent",
  components: {},
  data() {
    var checkYear = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('年份不能为空'));
      }
      setTimeout(() => {
        if (!Number.isInteger(parseInt(value))) {
          callback(new Error('请输入数字值'));
        } else {
          if (value < 0) {
            callback(new Error('年份不能为负数'));
          } else if(value < 2000 || value > 2022 ) {
            callback(new Error('年份只能在2000至2022区间'));
          } else{
            callback();
          }
        }
      }, 1000);
    };
    return {
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      loading: true,
      form: {},
      tableData: [],
      rules: {
        year: [
          { required: true, validator: checkYear, trigger: 'blur' }
        ],
        date: [
          { required: true, message: '日期不能为空', trigger: 'blur' },
        ],
        event: [
          { required: true, message: '事件信息不能为空', trigger: 'blur' },
        ],
      },
      options: [{
        value_year: '2000',
        label: '2000'
      }, {
        value_year: '2001',
        label: '2001'
      }, {
        value_year: '2002',
        label: '2002'
      }, {
        value_year: '2003',
        label: '2003'
      }, {
        value_year: '2004',
        label: '2004'
      }, {
        value_year: '2005',
        label: '2005'
      }, {
        value_year: '2006',
        label: '2006'
      }, {
        value_year: '2007',
        label: '2007'
      }, {
        value_year: '2008',
        label: '2008'
      }, {
        value_year: '2009',
        label: '2009'
      }, {
        value_year: '2010',
        label: '2010'
      }, {
        value_year: '2011',
        label: '2011'
      }, {
        value_year: '2012',
        label: '2012'
      }, {
        value_year: '2013',
        label: '2013'
      }, {
        value_year: '2014',
        label: '2014'
      }, {
        value_year: '2015',
        label: '2015'
      }, {
        value_year: '2016',
        label: '2016'
      }, {
        value_year: '2017',
        label: '2017'
      }, {
        value_year: '2018',
        label: '2018'
      }, {
        value_year: '2019',
        label: '2019'
      }, {
        value_year: '2020',
        label: '2020'
      }, {
        value_year: '2021',
        label: '2021'
      }, {
        value_year: '2022',
        label: '2022'
      }],
      value_year: ''
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.loading = true
      request.get("/cms/bigEvent", {
        params:{
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          year: this.value_year
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
      this.form = {}
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if(valid) {
          if (this.form.id) {  // 更新
            request.post("/cms/bigEvent/edit", this.form).then(res => {
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
            request.post("/cms/bigEvent/add", this.form).then(res => {
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
          console.log('error submit!!');
          return false;
        }
      });
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },handleDelete(id) {
      console.log(id)
      request.post("/cms/bigEvent/remove/" + id).then(res => {
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
    handleSizeChange(pageSize) {   // 改变当前每页的个数触发
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {  // 改变当前页码触发
      this.currentPage = pageNum
      this.load()
    }
  }

}

</script>
