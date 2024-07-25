<template>
  <div class="sdk-history-page">
    <el-card>
      <el-button class="back-button" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </el-button>
      <div v-if="currentSdk">
        <div class="sdk-info">
          <img :src="getIconUrl(currentSdk.iconLocation)" alt="Icon" class="icon-image-large" />
          <div class="sdk-details">
            <p><strong>SDK 名称：</strong> <span class="detail-value">{{ currentSdk.sdkName }}</span></p>
            <p><strong>包名：</strong> <span class="detail-value">{{ currentSdk.pkgName }}</span></p>
            <p><strong>版本：</strong> <span class="detail-value">{{ currentSdk.versionName }}</span></p>
          </div>
        </div>
      </div>
      <transition name="fade">
        <div class="content" v-if="taskHistory.length > 0">
          <el-table :data="taskHistory" class="custom-table">
            <el-table-column prop="id" label="检测ID" width="200"/>
            <el-table-column prop="startTime" label="开始时间" width="200" :formatter="formatTime"/>
            <el-table-column prop="endTime" label="结束时间" width="200" :formatter="formatTime"/>
            <el-table-column prop="taskStatus" label="检测状态" width="100" :formatter="formatDetectionStatus">
              <template #default="scope">
                <el-icon :style="{ color: getStatusColor(scope.row.taskStatus) }" class="status-dot">●</el-icon>
                {{ getDetectionStatus(scope.row.taskStatus) }}
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button @click="viewReport(scope.row.id)" type="default">查看报告</el-button>
              </template>
            </el-table-column>
          </el-table>
          <transition name="fade">
            <div v-if="reportData">
              <detect-report :report="reportData" @close="handleCloseReport"/>
            </div>
          </transition>
        </div>
      </transition>
    </el-card>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import axios from 'axios';
import { SdkListResp, SdkDetectTaskHistoryResp, SdkLatestReportResp } from '@/types/types';
import DetectReport from '@/components/DetectReport.vue';
import { ArrowLeft } from '@element-plus/icons-vue';

export default defineComponent({
  name: 'SdkHistory',
  components: {
    DetectReport,
    ArrowLeft,
  },
  setup() {
    const router = useRouter();
    const route = useRoute();
    const taskHistory = ref<SdkDetectTaskHistoryResp[]>([]);
    const currentSdk = ref<SdkListResp | null>(null);
    const reportData = ref<SdkLatestReportResp | null>(null);

    const fetchTaskHistory = async (sha256Code: string) => {
      try {
        const response = await axios.get(`http://localhost:8080/api/detect-tasks/history/${sha256Code}`);
        if (response.data && response.data.code === 200) {
          taskHistory.value = response.data.data;
        } else {
          taskHistory.value = [];
        }
      } catch (error) {
        console.error('Failed to fetch task history:', error);
        taskHistory.value = [];
      }
    };

    const viewReport = async (taskId: string) => {
      try {
        const response = await axios.get(`http://localhost:8080/checkreport`, {
          params: {
            detectTaskId: taskId,
          },
        });
        if (response.data && response.data.code === 200) {
          const data = response.data.data;

          if (data.permission_name_list && Array.isArray(data.permission_name_list)) {
            data.permission_name_list = data.permission_name_list.filter((perm: any) => perm !== null);
          }

          data.report_export_time = new Date(data.report_export_time).toLocaleString();
          data.task_start_time = new Date(data.task_start_time).toLocaleString();

          reportData.value = data;
        } else {
          reportData.value = null;
        }
      } catch (error) {
        console.error('Failed to fetch report:', error);
        reportData.value = null;
      }
    };

    const formatTime = (row: any, column: any, cellValue: string) => {
      const date = new Date(cellValue);
      return date.toLocaleString();
    };

    const formatDetectionStatus = (row: any, column: any, cellValue: number) => {
      return getDetectionStatus(cellValue);
    };

    const getIconUrl = (iconLocation: string | undefined) => {
      if (!iconLocation) return '';
      return `http://localhost:8080${iconLocation.replace('/Users/liaohanwen/Documents/workspace/sdkcenter/src/main/resources/static', '')}`;
    };

    const getDetectionStatus = (status: number) => {
      if (typeof status !== 'number') {
        return '未知状态';
      }
      switch (status) {
        case 0: return '检测中';
        case 1: return '通过';
        case 2: return '有风险';
        case 3: return '不通过';
        case 4: return '任务失败';
        default: return '未知状态';
      }
    };

    const getStatusColor = (status: number) => {
      switch (status) {
        case 0: return '#ffca28'; // 检测中 (黄色)
        case 1: return '#66bb6a'; // 通过 (绿色)
        case 2: return '#ffa726'; // 有风险 (橙色)
        case 3: return '#ef5350'; // 不通过 (红色)
        case 4: return '#bdbdbd'; // 任务失败 (灰色)
        default: return '#9e9e9e'; // 未知状态 (灰色)
      }
    };

    const handleCloseReport = () => {
      reportData.value = null;
    };

    const goBack = () => {
      router.push('/sdk-list');
    };

    onMounted(() => {
      const {
        sdkName,
        pkgName,
        versionName,
        iconLocation,
        sha256Code,
      } = route.params;

      currentSdk.value = {
        sdkName: sdkName as string,
        pkgName: pkgName as string,
        versionName: versionName as string,
        iconLocation: iconLocation as string,
        categoryValue: 0,
        size: '',
        updatetime: 0,
        auditStatus: 0,
        detectStatus: 0,
        sha256Code: sha256Code as string,
      };

      fetchTaskHistory(sha256Code as string);
    });

    return {
      taskHistory,
      currentSdk,
      reportData,
      viewReport,
      formatTime,
      formatDetectionStatus,
      getIconUrl,
      getDetectionStatus,
      goBack,
      handleCloseReport,
      getStatusColor,
    };
  },
});
</script>

<style scoped>
.sdk-history-page {
  position: fixed;
  top: 100px;
  left: 220px;
  width: calc(100% - 220px);
  text-align: center;
  padding: 20px;
  border-color: #ffffff00;
  background-color: #1c1c1e;
  color: #ffffff;
}

.el-button {
  width: 60px;
  background-color: #1caf2800;
  border-color: #ffffff00;
  color: #008cff;
}

.el-button--primary {
  position: fixed;
  top: 75px;
  left: 240px;
  width: 80px;
  height: 40px;
  margin-right: 15px;
  background-color: #1caf29;
  border-color: #ffffff00;
  color: #e7e7e7;
}

.back-button {
  position: absolute;
  top: 10px;
  left: 10px;
  background-color: #409eff;
  color: #ffffff;
  border: none;
  border-radius: 50%;
  font-size: 18px;
  width: 35px;
  height: 35px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background-color 0.3s;
}

.back-button:hover {
  background-color: #66b1ff;
}

.sdk-info {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.icon-image-large {
  width: 150px;
  height: 150px;
  object-fit: contain;
  margin-right: 20px;
  margin-top: 40px;
}

.sdk-details {
  text-align: left;
}

.sdk-details p {
  font-size: 20px;
  margin: 5px 0;
}

.detail-value {
  color: #66b1ff;
  font-weight: bold;
}

.content {
  display: flex;
}

.custom-table {
  width: 55%;
  border: 1px #c5c5c500;
  border-radius: 15px;
  background-color: rgba(255, 255, 255, 0.844);
margin-bottom: 20px;
text-align: left;
color: #e7e7e7f1;
}

.custom-table .el-table th {
background-color: #f6f8fa;
color: #24292e;
font-weight: 600;
}

.custom-table .el-table td {
border: 1px solid #e1e4e8;
}

.el-card {
border: 1px #c5c5c500;
border-radius: 15px;
background-color: #2c2c2e00;
color: #ffffff;
width: 1500px;
left: 0px;
top: -50px;
height: 900px;
background-color: hsla(240, 2%, 18%, 0.25);
color: #ffffff;
border: none;
border-radius: 15px;
box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
position: relative;
}

.report-card {
width: 35%;
padding: 20px;
background-color: #2c2c2e;
border: 1px solid #d1d5da;
border-radius: 6px;
margin-left: 20px;
backdrop-filter: blur(10px);
}

.status-dot {
margin-right: 5px;
font-size: 12px;
}

.fade-enter-active, .fade-leave-active {
transition: opacity 0.5s;
}

.fade-enter, .fade-leave-to {
opacity: 0;
}

</style>