<template>
  <div class="detect-tasks">
    <el-row>
      <el-col :span="14">
        <el-table 
          :data="sdkTasks" 
          class="custom-table" 
          @row-click="handleRowClick" 
          :row-class-name="rowClassName"
        >
          <el-table-column prop="detectTaskId" label="Task ID" width="200"/>
          <el-table-column prop="sdkName" label="SDK Name" width="190"/>
          <el-table-column prop="pkgName" label="Package Name" width="205"/>
          <el-table-column prop="startTime" label="Start Time" width="195" :formatter="formatTime"/>
          <el-table-column prop="endTime" label="End Time" width="195" :formatter="formatTime"/>
        </el-table>
        <el-pagination
          background
          layout="prev, pager, next"
          :total="totalTasks"
          :page-size="pageSize"
          @current-change="handlePageChange"
          class="pagination"
        ></el-pagination>
      </el-col>
      <el-col :span="10">
        <detect-report :report="selectedReport" />
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import axios from 'axios';
import DetectReport from '@/components/DetectReport.vue';
import { SdkLatestReportResp } from '@/types/types';

export default defineComponent({
  name: 'TaskList',
  components: {
    DetectReport,
  },
  setup() {
    const sdkTasks = ref([]);
    const totalTasks = ref(0);
    const pageSize = 20;
    const currentPage = ref(1);
    const selectedReport = ref<SdkLatestReportResp | null>(null);
    const selectedTaskId = ref<string | null>(null);

    const fetchSdkTasks = async (page = 1) => {
      try {
        const response = await axios.get(`http://localhost:8080/api/detect-tasks/all?page=${page}&pageSize=${pageSize}`);
        if (response.data && response.data.code === 200 && response.data.data) {
          sdkTasks.value = response.data.data;
          totalTasks.value = response.data.total;
        } else {
          sdkTasks.value = [];
          totalTasks.value = 0;
        }
      } catch (error) {
        console.error('Failed to fetch SDK tasks:', error);
        sdkTasks.value = [];
        totalTasks.value = 0;
      }
    };

    const handlePageChange = (page: number) => {
      currentPage.value = page;
      fetchSdkTasks(page);
    };

    const formatTime = (row: any, column: any, cellValue: number) => {
      const date = new Date(cellValue);
      return date.toLocaleString();
    };

    const handleRowClick = async (row: any) => {
      selectedTaskId.value = row.detectTaskId; // 记录选中的 Task ID
      try {
        const response = await axios.get(`http://localhost:8080/checkreport`, {
          params: {
            detectTaskId: row.detectTaskId
          }
        });
        if (response.data && response.data.code === 200) {
          const data = response.data.data;

          if (data.permission_name_list && Array.isArray(data.permission_name_list)) {
            data.permission_name_list = data.permission_name_list.filter((perm: any) => perm !== null);
          }

          data.report_export_time = new Date(data.report_export_time).toLocaleString();
          data.task_start_time = new Date(data.task_start_time).toLocaleString();

          selectedReport.value = data;
        } else {
          selectedReport.value = null;
        }
      } catch (error) {
        console.error('Failed to fetch report:', error);
        selectedReport.value = null;
      }
    };

    const rowClassName = (row: any) => {
      return row.detectTaskId === selectedTaskId.value ? 'selected-row' : '';
    };

    onMounted(() => {
      fetchSdkTasks();
    });

    return {
      sdkTasks,
      totalTasks,
      pageSize,
      handlePageChange,
      formatTime,
      handleRowClick,
      selectedReport,
      rowClassName,
    };
  }
});
</script>
<style scoped>
.detect-tasks {
  text-align: left;
  padding: 0;
  margin: 0;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.custom-table {
  width: 1000px;
  border: 1px solid #c5c5c5;
  border-radius: 10px;
  background-color: rgba(255, 255, 255, 0.844);
  margin-bottom: 20px;
}

.custom-table .el-table th {
  background-color: #f6f8fa;
  color: #24292e;
  font-weight: 600;
}

.custom-table .el-table td {
  border: 1px solid #e1e4e8;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: left;
}

.selected-row {
  background-color: rgba(3, 102, 214, 0.2) !important; /* 蓝色背景 */
  border-left: 4px solid #0366d6 !important; /* 蓝色边框 */
}
</style>