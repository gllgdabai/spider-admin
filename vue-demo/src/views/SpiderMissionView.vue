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
      爬虫名称：<el-input v-model="search" placeholder="请输入关键字" style="width: 12%;" clearable/>
      <el-button style="margin-left: 10px" type="primary" @click="load">查询</el-button>
    </div>
    <el-table :data="tableData" border stripe style="width: 100%"
              v-loading="loading"
              element-loading-text="拼命加载中"
              element-loading-background="rgba(0, 0, 0, 0.8)"
    >
      <el-table-column prop="missionName" label="任务名称"  />
      <el-table-column prop="status" label="任务状态" :formatter="selectDictLabel" />
      <el-table-column prop="startTime" label="开始时间" :formatter="dateFormat" />
      <el-table-column prop="endTime" label="结束时间" :formatter="dateFormat" />
      <el-table-column prop="timeCost" label="爬取时间(秒)" />
      <el-table-column prop="exitWay" label="退出方式" :formatter="selectDictLabel" />
      <el-table-column fixed="right" label="操作" width="220">
        <template #default="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="primary" size="small" @click="handleRun(scope.row.missionId)">运行爬虫</el-button>
          <el-popconfirm title="您确定要删除吗?" @confirm="handleDelete(scope.row.missionId)">
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
          title="任务信息"
          width="40%"
          :before-close="handleClose"
      >
        <el-form :model="form" ref="form" :rules="rules" label-width="120px">
          <el-form-item label="任务名称" prop="missionName">
            <el-input v-model="form.missionName" placeholder="请输入任务名称" style="width: 80%"/>
          </el-form-item>

          <el-form-item label="任务配置" prop="spiderConfigIdName">
            <el-autocomplete
                v-model="form.spiderConfigIdName"
                :fetch-suggestions="querySearch"
                @select="handleSelect"
                placeholder="请选择任务配置"
                style="width: 80%"
                :plain="true"
                @click="open"
            >
              <template #suffix>
                <el-icon class="el-input__icon"><Search /></el-icon>
              </template>
            </el-autocomplete>
          </el-form-item>
          <el-form-item label="入口地址">
            <el-input v-model="form.entryUrls" placeholder="请输入入口地址" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="Cookie">
            <el-input v-model="form.cookieStr" placeholder="请输入Cookie" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="Header">
            <el-input v-model="form.headerStr" placeholder="请输入Header" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="退出方式">
            <el-radio v-model="form.exitWay" label="DEFAULT" >默认</el-radio>
            <el-radio v-model="form.exitWay" label="DURATION" >持续时间</el-radio>
            <el-radio v-model="form.exitWay" label="URL_COUNT" >链接计数</el-radio>
          </el-form-item>
          <el-form-item label="退出值" v-if="form.exitWay != 'DEFAULT'">
            <el-input v-model="form.exitWayCount" style="width: 80%"/>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
          <el-button type="primary" @click="save">确定</el-button>
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
import moment from 'moment';
import { Search } from '@element-plus/icons-vue'
export default {
  name: "SpiderMissionView",
  components: {
    Search
  },
  data() {
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
        missionName: [
          { required: true, message: '必填', trigger: 'blur' },
        ],
      },
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
        this.loading = true
        request.get("/spider/spiderMission", {
          params:{
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            search: this.search
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
        exitWay: 'DEFAULT'
      }
    },
    save() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.missionId) {  // 更新
            request.post("/spider/spiderMission/edit", this.form).then(res => {
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
            request.post("/spider/spiderMission/add", this.form).then(res => {
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
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleRun(id) {
      console.log(id)
      request.post("/spider/spiderMission/runSpiderMission/" + id).then(res => {
        if (res.code === 0) {
          this.$message({
            type: "success",
            message: "运行成功"
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
    handleDelete(id) {
      console.log(id)
      request.post("/spider/spiderMission/remove/" + id).then(res => {
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
    },
    dateFormat: function(row,column){
      var date = row[column.property];
      if(date == undefined){return ''};
      return moment(date).format("yyyy-MM-DD HH:mm:ss")
    },
    selectDictLabel: function (row, column, value) {
      var data = row[column.property];
      if(data == undefined) return '';
      else if(data === 'DEFAULT') return '默认';
      else if(data === 'DURATION') return '持续时间';
      else if(data === 'URL_COUNT') return '链接计数';
      else if(data === 'done') return '已完成';
      else if(data === 'running') return '执行中';
      else if(data === 'wait') return '未执行';
    },
    querySearch(queryString,callback){
      //方法接收两个参数，
      //queryString接收用户输入的值，即用户输入的内容，callback是回调函数，处理请求回来的参数
      var list = [{}];
      //从后台获取到对象数组
      request.get("/spider/spiderConfig/searchByName", queryString).then(res => {
        //在这里为这个数组中每一个对象加一个value字段, 因为autocomplete只识别value字段并在下拉列中显示
        for(let i of res.data){
          i.value = i.id + "|" + i.spiderName;  //将想要展示的数据作为value
        }
        list = res.data;
        callback(list);
      }, err => {
        console.log(err);
      })
    },
    //@select=handleSelect()
    handleSelect(item){
      console.log(item)     //item包含了你选择的人员的所有属性
      // this.sdgs_user_id = item.id  //这里我根据后端要求需返回查找的人员ID
    },open() {
      this.$message('请根据爬虫配置的名字确定编号');
    }
  }
}

</script>
