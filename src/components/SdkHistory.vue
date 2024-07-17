<template>
    <el-dialog :visible.sync="visible" title="SDK检测任务历史" width="80%" :modal="true" :close-on-click-modal="false" :z-index="2000">
      <div>
        <div class="sdk-info">
          <img :src="getIconUrl(currentSdk?.iconLocation)" alt="Icon" class="icon-image-large" />
          <div class="sdk-details">
            <p><strong>SDK名称：</strong>{{ currentSdk?.sdkName }}</p>
            <p><strong>包名：</strong>{{ currentSdk?.pkgName }}</p>
            <p><strong>版本：</strong>{{ currentSdk?.versionName }}</p>
          </div>
        </div>
        <el-table :data="taskHistory" class="custom-table">
          <el-table-column prop="taskStatus" label="检测状态" width="150"/>
          <el-table-column prop="startTime" label="开始时间" width="200" :formatter="formatTime"/>
          <el-table-column prop="endTime" label="结束时间" width="200" :formatter="formatTime"/>
        </el-table>
        <el-button @click="$emit('close')">返回</el-button>
      </div>
    </el-dialog>
  </template>
  
  <script lang="ts">
  import { defineComponent, ref, watch } from 'vue';
  import axios from 'axios';
  import { SdkListResp, SdkDetectTaskHistoryResp } from '@/types/types';
  
  export default defineComponent({
    name: 'SdkHistory',
    props: {
      visible: Boolean,
      sdk: {
        type: Object as () => SdkListResp | null,
        default: null,
      },
    },
    emits: ['close'],
    setup(props, { emit }) {
      const taskHistory = ref<SdkDetectTaskHistoryResp[]>([]);
      const currentSdk = ref<SdkListResp | null>(props.sdk);
  
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
  
      const formatTime = (row: any, column: any, cellValue: string) => {
        const date = new Date(cellValue);
        return date.toLocaleString();
      };
  
      const getIconUrl = (iconLocation: string | undefined) => {
        if (!iconLocation) return '';
        return `http://localhost:8080${iconLocation.replace('/Users/liaohanwen/Documents/workspace/sdkcenter/src/main/resources/static', '')}`;
      };
  
      watch(() => props.sdk, (newSdk) => {
        if (newSdk) {
          currentSdk.value = newSdk;
          fetchTaskHistory(newSdk.sha256Code);
        }
      });
  
      return {
        taskHistory,
        currentSdk,
        formatTime,
        getIconUrl,
      };
    },
  });
  </script>
  
  <style scoped>
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
  }
  
  .sdk-details {
    text-align: left;
  }
  
  .custom-table {
    width: 100%;
    border: 1px solid #c5c5c5;
    border-radius: 10px;
    background-color: rgba(255, 255, 255, 0.844);
    margin-bottom: 20px;
    text-align: left;
  }
  
  .custom-table .el-table th {
    background-color: #f6f8fa;
    color: #24292e;
    font-weight: 600;
  }
  
  .custom-table .el-table td {
    border: 1px solid #e1e4e8;
  }
  </style>