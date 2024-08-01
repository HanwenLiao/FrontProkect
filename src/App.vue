<template>
  <el-container style="height: 100vh;">
    <el-header class="header">
      <div class="logo-container">
        <img src="/src/assets/HMOS_Logo_Icon.svg.png" alt="Logo 1">
        <img src="/src/assets/HarLogo.png" alt="Logo 2">
        <h1>鸿 蒙 SDK 检 测 中 心 (BETA)</h1>
      </div>
    </el-header>
    <el-container>
      <el-menu :default-active="activeMenu" @select="handleMenuSelect" class="el-menu-vertical" background-color="#2c2c2e" text-color="#ffffff" active-text-color="#2c2c2e">
        <el-menu-item index="home">
          <el-icon><House /></el-icon>
          HOME
        </el-menu-item>
        <el-menu-item index="sdkList">
          <el-icon><Document /></el-icon>
          SDK管理
        </el-menu-item>
        <el-menu-item index="permissionSettings">
          <el-icon><Setting /></el-icon>
          权限设置
        </el-menu-item>
      </el-menu>
      <el-main class="main-container">
        <transition name="fade" mode="out-in">
          <router-view />
        </transition>
      </el-main>
    </el-container>
  </el-container>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useRouter } from 'vue-router';
import { House, Document, Search, Setting } from '@element-plus/icons-vue';

export default defineComponent({
  name: 'App',
  components: {
    House,
    Document,
    Search,
    Setting
  },
  setup() {
    const router = useRouter();
    const activeMenu = ref('home');

    const handleMenuSelect = (index: string) => {
      if (index === 'home') {
        router.push('/');
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
  background-color: #000000;
  color: #ffffff;
  line-height: 60px;
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
  margin-right: 10px;
}

h1 {
  margin: 0;
  padding: 0;
  flex-grow: 1;
  text-align: right;
  font-size: 22px;
}

.el-menu-vertical {
  position: fixed;
  top: 70px;
  left: 10px;
  width: 220px;
  height: 900px;
  background-color: #2c2c2e;
  padding: 0;
  border: none;
  border-radius: 15px;
  overflow-y: auto;
  box-shadow: 2px 2px 12px rgba(0, 0, 0, 0.5);
  transition: all 0.3s ease;
  z-index: 10;
}

.el-menu-vertical:hover {
  box-shadow: 4px 4px 20px rgba(0, 0, 0, 0.7);
}

.el-main {
  position: fixed;
  top: 60px;
  left: 0px;
  width: 100%;
  margin-left: 0px;
  padding-top: 80px;
  /* background-color: #1c1c1e; */
  background-color: #1c1c1e;
  color: #ffffff;
  height: calc(100vh - 60px);
  overflow-y: auto;
}

.main-container {
  flex: 1;
  padding: 20px;
  box-sizing: border-box;
}

.el-menu-item {
  padding-left: 20px !important;
  border-radius: 10px;
  background-color: #2c2c2e !important;
  color: #ffffff !important;
}

.el-menu-item:hover,
.el-menu-item.is-active {
  background-color: #0a84ff !important;
  color: #2c2c2e !important;
  border-radius: 10px;
}

.el-menu-item:hover {
  color: #2c2e2e !important;
}

body {
  margin: 0;
  padding: 0;
  background-color: #1c1c1e;
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

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}

.fade-enter, .fade-leave-to {
  opacity: 0;
}
</style>