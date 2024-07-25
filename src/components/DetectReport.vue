<template>
  <div class="report-container" v-if="report">
    <button class="close-button" @click="closeReport">×</button>
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
          <div class="value" :title="report.sha256_code">{{ truncateCode(report.sha256_code) }}</div>
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
import { SdkLatestReportResp, Permission } from '@/types/types';

export default defineComponent({
  name: 'DetectReport',
  props: {
    report: {
      type: Object as PropType<SdkLatestReportResp | null>,
      required: false,
    },
  },
  setup(props, { emit }) {
    const sensitivePermissions = computed(() => {
      return props.report?.permission_name_list?.filter((perm: Permission) => perm && perm.isSensitive === 1) || [];
    });

    const allPermissions = computed(() => {
      return props.report?.permission_name_list?.filter((perm: Permission) => perm) || [];
    });

    const truncateCode = (code: string) => {
      return code.length > 20 ? `${code.slice(0, 20)}...` : code;
    };

    const closeReport = () => {
      emit('close');
    };

    return {
      sensitivePermissions,
      allPermissions,
      truncateCode,
      closeReport,
    };
  },
});
</script>

<style scoped>
.report-container {
  margin-left: 15px;
  padding: 15px;
  background-color: #2c2c2e;
  border: 1px solid #444;
  border-radius: 15px;
  margin-top: 0px;
  backdrop-filter: blur(10px);
  position: relative;
}

.report-card {
  width: 550px; /* 适当增加宽度 */
  background: linear-gradient(145deg, #333, #222);
  border-radius: 15px;
  border: 1px solid #444;
  padding: 20px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background-color: #ff5f56;
  color: white;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: none;
  font-size: 12px;
  cursor: pointer;
}

.close-button:hover {
  background-color: #e0443e;
}

h3 {
  color: #ffffff;
  font-weight: 600;
  margin-bottom: 15px;
  border-bottom: 1px solid #444;
  padding-bottom: 10px;
}

h4 {
  color: #f76c6c;
  font-weight: 600;
  margin-top: 20px;
  border-bottom: 1px solid #444;
  padding-bottom: 10px;
}

.info {
  margin-bottom: 20px;
}

.info-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.title {
  font-weight: bold;
  color: #a0a0a0;
}

.value {
  color: #ffffff;
  word-break: break-all; /* 强制单词换行 */
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
  border-bottom: 1px solid #444;
}

.permission-item .title {
  color: #a0a0a0;
}

.permission-item .sensitive-title {
  color: #f76c6c;
}

.no-sensitive-permissions {
  color: #ffffff;
  font-weight: bold;
}

strong {
  color: #ffffff;
}
</style>