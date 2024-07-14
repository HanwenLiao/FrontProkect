<template>
  <div class="report-container" v-if="report">
    <h3>检测报告</h3>
    <el-card class="report-card">
      <div class="info">
        <div class="info-item">
          <div class="title">SDK Name</div>
          <div class="value">{{ report.sdk_name }}</div>
        </div>
        <div class="info-item">
          <div class="title">Package Name</div>
          <div class="value">{{ report.pkg_name }}</div>
        </div>
        <div class="info-item">
          <div class="title">Version Name</div>
          <div class="value">{{ report.version_name }}</div>
        </div>
        <div class="info-item">
          <div class="title">SHA256 Code</div>
          <div class="value">{{ report.sha256_code }}</div>
        </div>
        <div class="info-item">
          <div class="title">Report Export Time</div>
          <div class="value">{{ report.report_export_time }}</div>
        </div>
        <div class="info-item">
          <div class="title">Task Start Time</div>
          <div class="value">{{ report.task_start_time }}</div>
        </div>
      </div>
      <div class="permissions">
        <h4>敏感权限</h4>
        <ul v-if="sensitivePermissions.length > 0">
          <li v-for="permission in sensitivePermissions" :key="permission.permissionId" class="permission-item">
            <div class="title sensitive-title">{{ permission.permissionName }}</div>
            <div class="value">{{ permission.description }}</div>
          </li>
        </ul>
        <div v-else class="no-sensitive-permissions">无</div>
        <h4>所有权限</h4>
        <ul>
          <li v-for="permission in allPermissions" :key="permission.permissionId" class="permission-item">
            <div class="title">{{ permission.permissionName }}</div>
            <div class="value">{{ permission.description }}</div>
          </li>
        </ul>
      </div>
    </el-card>
  </div>
</template>

<script lang="ts">
import { defineComponent, PropType, computed } from 'vue';
import { SdkLatestResp, Permission } from '@/types/types';

export default defineComponent({
  name: 'DetectReport',
  props: {
    report: {
      type: Object as PropType<SdkLatestResp | null>,
      required: false,
    },
  },
  setup(props) {
    const sensitivePermissions = computed(() => {
      return props.report?.permission_name_list.filter((perm: Permission) => perm && perm.isSensitive === 1) || [];
    });

    const allPermissions = computed(() => {
      return props.report?.permission_name_list.filter((perm: Permission) => perm) || [];
    });

    return {
      sensitivePermissions,
      allPermissions,
    };
  },
});
</script>

<style scoped>
.report-container {
  padding: 20px;
  background-color: rgba(246, 248, 250, 0.9); /* 毛玻璃效果 */
  border: 1px solid #d1d5da;
  border-radius: 6px;
  margin-top: 20px;
  backdrop-filter: blur(10px); /* 毛玻璃效果 */
}

.report-card {
  background-color: rgba(255, 255, 255, 0.7);
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

h3 {
  color: #0366d6;
  font-weight: 600;
}

h4 {
  color: #d73a49;
  font-weight: 600;
  margin-top: 20px;
}

.info {
  margin-bottom: 20px;
}

.info-item {
  margin-bottom: 10px;
}

.title {
  font-weight: bold;
  color: #0366d6;
}

.value {
  margin-left: 10px;
  color: #24292e;
}

.permissions {
  margin-top: 20px;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  padding: 5px 0;
}

li:not(:last-child) {
  border-bottom: 1px solid #e1e4e8;
}

.permission-item .title {
  color: #24292e; /* 所有权限的标题颜色改为黑色 */
}

.permission-item .sensitive-title {
  color: #24292e; /* 敏感权限的标题颜色保持红色 */
}

.no-sensitive-permissions {
  color: #24292e; /* "无"字颜色改为黑色 */
  font-weight: bold;
}

strong {
  color: #24292e;
}
</style>