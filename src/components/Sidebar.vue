<template>
    <el-menu :default-active="activeMenu" router>
      <el-menu-item index="/">Home</el-menu-item>
      <el-menu-item index="/upload-sdk">Upload SDK</el-menu-item>
      <el-menu-item index="/tasks">检测任务</el-menu-item>
      <el-menu-item index="/sdk-list">SDK列表</el-menu-item>
      <el-menu-item index="/permissions">权限设置</el-menu-item>
    </el-menu>
  </template>
  
  <script lang="ts">
  import { defineComponent, ref } from 'vue';
  import { useRoute } from 'vue-router';
  
  export default defineComponent({
    name: 'Sidebar',
    setup() {
      const route = useRoute();
      const activeMenu = ref(route.path);
  
      return {
        activeMenu,
      };
    },
  });
  </script>