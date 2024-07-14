<!-- <template>
  <el-container style="height: 100vh;">
    <el-header class="header">
      
      <div class="logo-container">
        <img src="/src/assets/HMOS_Logo_Icon.svg.png" alt="Logo 1">
        <img src="/src/assets/HarLogo.png" alt="Logo 2">
        <h1> 鸿 蒙 SDK 检 测 中 心 (BETA)</h1>
      </div>
      
    </el-header>
    <el-container>
      <el-aside width="200px" class="aside">
        <el-menu :default-active="activeMenu" @select="handleMenuSelect" class="el-menu-vertical">
          <el-menu-item index="home">HOME</el-menu-item>
          <el-menu-item index="uploadSdk">上传sdk</el-menu-item>
          <el-menu-item index="detectTask">检测任务</el-menu-item>
          <el-menu-item index="sdkList">sdk列表</el-menu-item>
          <el-menu-item index="permissionSettings">权限设置</el-menu-item>
        </el-menu>
      </el-aside>
      <el-main class="main-container">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useRouter } from 'vue-router';

export default defineComponent({
  name: 'App',
  setup() {
    const router = useRouter();
    const activeMenu = ref('home');

    const handleMenuSelect = (index: string) => {
      if (index === 'home') {
        router.push('/');
      } else if (index === 'uploadSdk') {
        router.push('/upload-sdk');
      } else if (index === 'detectTask') {
        router.push('/detect-tasks');
      } else if (index === 'sdkList') {
        router.push('/sdk-list');
      } else if (index === 'permissionSettings') {
        router.push('/permission-settings');
      }
      activeMenu.value = index;
    };

    return {
      activeMenu,
      handleMenuSelect,
    };
  },
});
</script>

<style scoped>
.header {
  line-height: 60px; /* 调整行高以适应 header 高度 */
  position: fixed; /* 设置固定定位 */
  top: 0; /* 从视口的顶部开始 */
  left: 0; /* 从视口的左侧开始 */
  width: 100%; /* 保持原有的宽度设置 */
  background-color: #000103;
  color: #ffffff;
  line-height: 60px;
  text-align: left;
  font-size: 24px;
  margin: 0;
  padding: 0;
}
.logo-container {
  display: flex; /* 设定为 Flex 容器 */
  align-items: left; /* 垂直居中图片 */
}

.logo-container img {
  height: 58px; /* 限制图片高度，保持一致 */
  margin-left: 10px;
  margin-right: 0px; /* 在图片之间添加一些间距 */
}


h1 {
  margin-right: 20px; /* 移除默认的 margin */
  padding: 20; /* 移除默认的 padding */
  flex-grow: 1; /* 允许标题占据剩余空间 */
  text-align: right; /* 标题居中显示 */
  font-size: 22px;
  
}

.aside {
  position: fixed; /* 固定定位 */
  top: 60px; /* 从 header 的底部开始 */
  left: 0; /* 从视口的左侧开始 */
  width: 200px; /* 宽度为 200px */
  height: calc(100vh - 60px); /* 高度为视口高度减去 header 的高度 */
  background-color: #f6f8fa;
  padding: 0;
  border-right: 1px solid #d1d5da;
  overflow-y: auto; /* 超出部分显示滚动条 */
}

.el-main {
  position: fixed; 
  top: 60px; /* 从 header 的底部开始 */
  left: 200px; /* 从视口的左侧开始 */
  width: 100%; /* 宽度为 200px */
  height: calc(100vh - 60px); /* 高度为视口高度减去 header 的高度 */
  margin-left: 0px; /* 与侧边栏宽度一致 */
  padding: 0px;
  padding-top: 0px; /* 顶部留出空间 */
  background-color: #ffffff;
  justify-content: flex-start;
  align-items: flex-start;
  box-sizing: border-box;
}

.main-container {
  flex: 1;
  padding: 20px;
  box-sizing: border-box;
}

.el-container {
  height: 100%;
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;
}

.el-menu-vertical {
  border-right: none;
}

.el-menu-item {
  padding-left: 20px !important;
}

.el-menu-item:hover,
.el-menu-item.is-active {
  background-color: #0366d6 !important;
  color: #ffffff !important;
}

.el-menu-item:hover {
  color: #ffffff !important;
}

body {
  margin: 0;
  padding: 0;
}

html, body {
  height: 100%;
  margin: 0;
  padding: 0;
  overflow-x: hidden; /* 防止横向滚动 */
}

#app {
  height: 100%;
}
@media (max-width: 768px) {
  .aside {
    position: static; /* 非固定定位 */
    width: 100%; /* 宽度为全屏 */
    top: 0; /* 从顶部开始 */
  }
  .el-main {
    margin-left: 0; /* 左边距取消 */
    padding-top: 20px; /* 适当的顶部填充 */
  }
}
</style> -->

<template>
  <el-container style="height: 100vh;">
    <el-header class="header">
      <div class="logo-container">
        <img src="/src/assets/HMOS_Logo_Icon.svg.png" alt="Logo 1">
        <img src="/src/assets/HarLogo.png" alt="Logo 2">
        <h1> 鸿 蒙 SDK 检 测 中 心 (BETA)</h1>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px" class="aside">
        <el-menu :default-active="activeMenu" @select="handleMenuSelect" class="el-menu-vertical">
          <el-menu-item index="home">HOME</el-menu-item>
          <el-menu-item index="uploadSdk">上传sdk</el-menu-item>
          <el-menu-item index="detectTask">检测任务</el-menu-item>
          <el-menu-item index="sdkList">sdk列表</el-menu-item>
          <el-menu-item index="permissionSettings">权限设置</el-menu-item>
        </el-menu>
      </el-aside>
      <el-main class="main-container">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useRouter } from 'vue-router';

export default defineComponent({
  name: 'App',
  setup() {
    const router = useRouter();
    const activeMenu = ref('home');

    const handleMenuSelect = (index: string) => {
      if (index === 'home') {
        router.push('/');
      } else if (index === 'uploadSdk') {
        router.push('/upload-sdk');
      } else if (index === 'detectTask') {
        router.push('/detect-tasks');
      } else if (index === 'sdkList') {
        router.push('/sdk-list');
      } else if (index === 'permissionSettings') {
        router.push('/permission-settings');
      }
      activeMenu.value = index;
    };

    return {
      activeMenu,
      handleMenuSelect,
    };
  },
});
</script>

<style scoped>
.header {
  line-height: 60px;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #000103;
  color: #ffffff;
  text-align: left;
  font-size: 24px;
  margin: 0;
  padding: 0;
}
.logo-container {
  display: flex;
  align-items: center;
}
.logo-container img {
  height: 58px;
  margin-left: 10px;
}
h1 {
  margin-right: 20px;
  flex-grow: 1;
  text-align: right;
  font-size: 22px;
}
.aside {
position: fixed;
top: 60px;
left: 0;
width: 200px;
height: calc(100vh - 60px);
background-color: #f6f8fa;
padding: 0;
border-right: 1px solid #d1d5da;
overflow-y: auto;
}
.el-main {
position: fixed;
top: 60px;
left: 200px;
width: calc(100% - 200px);
height: calc(100vh - 60px);
margin-left: 0;
padding: 20px;
background-color: #ffffff;
box-sizing: border-box;
}
.main-container {
display: flex;
flex-direction: column;
}
.el-container {
height: 100%;
margin: 0;
padding: 0;
display: flex;
flex-direction: column;
}
.el-menu-vertical {
border-right: none;
}
.el-menu-item {
padding-left: 20px !important;
}
.el-menu-item:hover,
.el-menu-item.is-active {
background-color: #0366d6 !important;
color: #ffffff 
}
.el-menu-item:hover {
color: #ffffff !important;
}
body {
margin: 0;
padding: 0;
}
html, body {
height: 100%;
margin: 0;
padding: 0;
overflow-x: hidden;
}
#app {
height: 100%;
}
@media (max-width: 768px) {
.aside {
position: static;
width: 100%;
top: 0;
}
.el-main {
margin-left: 0;
padding-top: 20px;
}
}
</style>