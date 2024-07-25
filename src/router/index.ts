import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router';
import Home from '../views/Home.vue';
import UploadSdkPage from '../views/UploadSdkPage.vue';
import DetectTasks from '../views/DetectTasks.vue';
import SdkList from '../views/SdkList.vue';
import SdkHistory from '../views/SdkHistory.vue';
import PermissionSettings from '../views/PermissionSettings.vue';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/upload-sdk',
    name: 'UploadSdkPage',
    component: UploadSdkPage
  },
  {
    path: '/detect-tasks',
    name: 'DetectTasks',
    component: DetectTasks
  },
  {
    path: '/sdk-list',
    name: 'SdkList',
    component: SdkList
  },
  {
    path: '/sdk-history/:sha256Code/:sdkName/:pkgName/:versionName/:iconLocation',
    name: 'SdkHistory',
    component: SdkHistory,
    props: true
  },
  {
    path: '/permission-settings',
    name: 'PermissionSettings',
    component: PermissionSettings,
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

export default router;