<template>
  <div class="sdk-list">
    <h1>SDK列表</h1>
    <el-table :data="sdkList" class="custom-table">
      <el-table-column label="Icon" width="150">
        <template #default="scope">
          <img :src="getIconUrl(scope.row.iconLocation)" alt="Icon" class="icon-image" />
        </template>
      </el-table-column>
      <el-table-column prop="sdkId" label="SDK ID" width="100"/>
      <el-table-column prop="sdkName" label="SDK Name" width="100"/>
      <el-table-column prop="pkgName" label="Package Name" width="180"/>
      <el-table-column prop="versionName" label="Version Name" width="120"/>
      <el-table-column prop="versionCode" label="Version Code" width="120"/>
      <el-table-column prop="categoryValue" label="Category" width="120"/>
      <el-table-column prop="size" label="Size" width="100"/>
      <el-table-column prop="sha256Code" label="SHA256 Code" width="200"/>
      <el-table-column prop="updatetime" label="Update Time" width="200" :formatter="formatTime"/>
    </el-table>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import axios from 'axios';
import { SdkListResp } from '@/types/types';

export default defineComponent({
  name: 'SdkList',
  setup() {
    const sdkList = ref<SdkListResp[]>([]);

    const fetchSdkList = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/sdk-list/all');
        if (response.data && response.data.code === 200) {
          sdkList.value = response.data.data;
        } else {
          sdkList.value = [];
        }
      } catch (error) {
        console.error('Failed to fetch SDK list:', error);
        sdkList.value = [];
      }
    };

    const formatTime = (row: any, column: any, cellValue: string) => {
      const date = new Date(cellValue);
      return date.toLocaleString();
    };

    const getIconUrl = (iconLocation: string) => {
      return `http://localhost:8080${iconLocation.replace('/Users/liaohanwen/Documents/workspace/sdkcenter/src/main/resources/static', '')}`;
    };

    onMounted(() => {
      fetchSdkList();
    });

    return {
      sdkList,
      formatTime,
      getIconUrl,
    };
  }
});
</script>

<style scoped>
.sdk-list {
  text-align: center;
  padding: 20px;
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

.icon-image {
  width: 100px;
  height: 100px;
  object-fit: contain;
}
</style>