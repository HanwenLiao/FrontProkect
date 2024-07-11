<template>
  <div class="detect-tasks">
    <el-table :data="sdkTasks" class="custom-table">
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
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import axios from 'axios';

export default defineComponent({
  name: 'TaskList',
  setup() {
    const sdkTasks = ref([]);
    const totalTasks = ref(0);
    const pageSize = 20;
    const currentPage = ref(1);

    const fetchSdkTasks = async (page = 1) => {
      try {
        const response = await axios.get(`http://localhost:8080/api/detect-tasks/all?page=${page}&pageSize=${pageSize}`);
        if (response.data && response.data.code === 200 && response.data.data) {
          sdkTasks.value = response.data.data;
          totalTasks.value = response.data.total;  // 获取总任务数
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

    onMounted(() => {
      fetchSdkTasks();
    });

    return {
      sdkTasks,
      totalTasks,
      pageSize,
      handlePageChange,
      formatTime,
    };
  }
});
</script>

<style scoped>
.detect-tasks {
  text-align: left;
  padding: 0; /* 移除默认的 padding */
  margin: 0; /* 移除默认的 margin */
  height: 100%; /* 确保填满视口的高度 */
  flex-direction: column;
}

.custom-table {
  width: 1000px; /* 固定宽度 */
  border: 1px solid #c5c5c5c5;
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

</style>