import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import UploadSdk from '../components/UploadSdk.vue';
import DetectTasks from '../views/DetectTasks.vue';
import SdkList from '../views/SdkList.vue';
import PermissionSettings from '../views/PermissionSettings.vue';
const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/upload-sdk',
        name: 'UploadSdk',
        component: UploadSdk
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
        path: '/permission-settings',
        name: 'PermissionSettings',
        component: PermissionSettings
    }
];
const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});
export default router;
//# sourceMappingURL=index.js.map