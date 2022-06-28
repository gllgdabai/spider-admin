<template>
  <div style="padding: 10px">
    <!-- 功能区 -->
    <div style="margin: 10px">

    </div>
    <!-- 搜索区 -->
    <div style="margin: 10px">
      标题检索： <el-input v-model="searchByTitle" placeholder="请输入关键字" style="width: 12%;" clearable/>
      <el-button style="margin-left: 10px" type="primary" @click="load">查询</el-button>
      全文检索： <el-input v-model="search" placeholder="请输入关键字" style="width: 12%;" clearable/>
      <el-button style="margin-left: 10px" type="success" @click="loadByEs">查询</el-button>
    </div>
    <el-table :data="tableData" border stripe style="width: 100%"
              v-loading="loading"
              max-height="700"
              element-loading-text="拼命加载中"
              element-loading-background="rgba(0, 0, 0, 0.8)"
    >
      <el-table-column prop="title" label="文章标题"  />
      <el-table-column prop="link" label="原文链接"  width="100" >
        <template #default="scope">
          <el-link type="primary" :href="scope.row.link">查看原文</el-link>
        </template>
      </el-table-column>
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
          :page-sizes="[5, 10, 20, 50, 100]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
      <el-dialog
          v-model="dialogVisible"
          title="文章详情"
          width="60%"
          :before-close="handleClose"
      >
        <el-form :model="form" label-width="120px">
          <el-form-item label="文章标题" >
            <el-input v-model="form.title" style="width: 80%" disabled/>
          </el-form-item>
          <div id="div1">
            <p v-html="form.content" />
          </div>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
          <el-button type="primary" @click="save" >确定</el-button>
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
import E from 'wangeditor'

let editor;

export default {
  name: "ArticleView",
  components: {},
  data() {
    return {
      searchByTitle: '',
      search: '',
      last_click: 0,  // 0表示最近点击的是标题检索按钮，1表示最近点击的是全文检索按钮
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      loading: true,
      form: {},
      tableData: []
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.loading = true
      this.last_click = 0
      this.search = ''
      if(this.searchByTitle === '' && this.search === '') {
        request.get("/cms/article/findPageByAll", {
          params:{
            pageNum: this.currentPage,
            pageSize: this.pageSize,
          }
        }).then(res => {
          console.log(res)
          this.loading = false;
          this.tableData = res.data.list
          this.total = res.data.total
        })
      } else {
        request.get("/cms/article/findTitleByKeywordByPage", {
          params:{
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            searchByTitle: this.searchByTitle
          }
        }).then(res => {
          console.log(res)
          this.loading = false;
          this.tableData = res.data.list
          this.total = res.data.total
        })
      }

    },
    loadByEs() {
      this.loading = true
      this.last_click = 1
      this.searchByTitle = ''
      if (this.search === '') {
        request.get("/cms/article", {
          params:{
            pageNum: this.currentPage,
            pageSize: this.pageSize,
          }
        }).then(res => {
          console.log(res)
          this.loading = false;
          this.tableData = res.data.list
          this.total = res.data.total
        })
      } else {
        request.get("/cms/article/findPageByKeyword", {
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
      }

    },
    save() {
      // 获取富文本组件中的内容
      this.form.content = editor.txt.html();
      if (this.form.id) {  // 更新
        request.post("/cms/article/edit", this.form).then(res => {
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
      }
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
      this.$nextTick(() => {
        // 加载wangEditor对象
        editor = new E('#div1')
        editor.create()
      })

    },
    handleDelete(id) {
      console.log(id)
      request.post("/cms/article/remove/" + id).then(res => {
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
      if(this.last_click === 0 || this.searchByTitle === '' && this.search === '') {
        this.load()
      }else {
        this.loadByEs()
      }

    },
    handleCurrentChange(pageNum) {  // 改变当前页码触发
      this.currentPage = pageNum
      if(this.last_click === 0 || this.searchByTitle === '' && this.search === '') {
        this.load()
      }else {
        this.loadByEs()
      }
    }
  }
}

</script>
