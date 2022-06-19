<template>
  <div>
    <el-menu
        style="width: 200px; min-height: calc(100vh - 50px);"
        :default-active="path"
        :default-openeds="['1']"
        router
        class="el-menu-vertical-demo"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b">
      <el-sub-menu index="1">
        <template #title>系统管理</template>
        <el-menu-item index="/user">用户管理</el-menu-item>
        <el-sub-menu index="1-1">
          <template #title>爬虫管理</template>
          <el-menu-item index="/spider/spiderMission">爬虫任务</el-menu-item>
          <el-menu-item index="/spider/spiderConfig">爬虫配置</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/article">文章管理</el-menu-item>
        <el-menu-item index="/bigEvent">大事件管理</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "Aside",
  data() {
    return {
      path: this.$route.path
    }
  },
}
</script>

<style scoped>

</style>