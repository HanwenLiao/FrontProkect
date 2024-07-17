<!-- <template>
  <div class="sdk-list">
    <h1>SDK列表</h1>
    <el-table :data="sdkList" class="custom-table">
      <el-table-column label="Icon" width="150">
        <template #default="scope">
          <img :src="getIconUrl(scope.row.iconLocation)" alt="Icon" class="icon-image" />
        </template>
      </el-table-column>
      <el-table-column prop="sdkName" label="SDK Name" width="150"/>
      <el-table-column prop="pkgName" label="Package Name" width="180"/>
      <el-table-column prop="versionName" label="Version Name" width="120"/>
      <el-table-column prop="categoryValue" label="Category" width="120"/>
      <el-table-column prop="size" label="Size" width="100"/>
      <el-table-column label="Detection Status" width="150">
        <template #default="scope">
          <span>权限检测：{{ getDetectionStatus(scope.row.detectStatus) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Audit Status" width="150">
        <template #default="scope">
          <span>{{ getAuditStatus(scope.row.auditStatus) }}</span>
        </template>
      </el-table-column>
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

    const getAuditStatus = (status: number) => {
      switch (status) {
        case 0: return '待审核';
        case 1: return '通过';
        case 2: return '不通过';
        default: return '未知状态';
      }
    };

    onMounted(() => {
      fetchSdkList();
    });

    return {
      sdkList,
      formatTime,
      getIconUrl,
      getDetectionStatus,
      getAuditStatus,
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
</style>   -->
<template>
  <div class="sdk-list">
    <h1>SDK列表</h1>
    <el-table :data="sdkList" class="custom-table">
      <el-table-column label="Icon" width="150">
        <template #default="scope">
          <img :src="getIconUrl(scope.row.iconLocation)" alt="Icon" class="icon-image" />
        </template>
      </el-table-column>
      <el-table-column prop="sdkName" label="SDK Name" width="150"/>
      <el-table-column prop="pkgName" label="Package Name" width="180"/>
      <el-table-column prop="versionName" label="Version Name" width="120"/>
      <el-table-column prop="categoryValue" label="Category" width="120"/>
      <el-table-column prop="size" label="Size" width="100"/>
      <el-table-column label="Detection Status" width="150">
        <template #default="scope">
          <span>权限检测：{{ getDetectionStatus(scope.row.detectStatus) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Audit Status" width="150">
        <template #default="scope">
          <span>{{ getAuditStatus(scope.row.auditStatus) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="updatetime" label="Update Time" width="200" :formatter="formatTime"/>
      <el-table-column label="Actions" width="150">
        <template #default="scope">
          <el-button @click="showHistory(scope.row)">查看历史</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="dialogVisible" width="80%" :close-on-click-modal="false" :z-index="2000">
      <el-card>
        <h2>SDK检测历史</h2>
        <div v-if="selectedSdk">
          <p><strong>SDK Name:</strong> {{ selectedSdk.sdkName }}</p>
          <p><strong>Package Name:</strong> {{ selectedSdk.pkgName }}</p>
          <p><strong>Version Name:</strong> {{ selectedSdk.versionName }}</p>
        </div>
        <el-table :data="historyList" class="custom-table">
          <el-table-column prop="startTime" label="Start Time" width="150" :formatter="formatTime"/>
          <el-table-column prop="endTime" label="End Time" width="150" :formatter="formatTime"/>
          <el-table-column prop="taskStatus" label="Status" width="120" :formatter="formatStatus"/>
        </el-table>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">关闭</el-button>
        </span>
      </el-card>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import axios from 'axios';
import { SdkListResp, SdkDetectTaskHistoryResp } from '@/types/types';

export default defineComponent({
  name: 'SdkList',
  setup() {
    const sdkList = ref<SdkListResp[]>([]);
    const dialogVisible = ref(false);
    const selectedSdk = ref<SdkListResp | null>(null);
    const historyList = ref<SdkDetectTaskHistoryResp[]>([]);

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

    const fetchHistoryList = async (sha256Code: string) => {
      try {
        const response = await axios.get(`http://localhost:8080/api/detect-tasks/history/${sha256Code}`);
        if (response.data && response.data.code === 200) {
          historyList.value = response.data.data;
        } else {
          historyList.value = [];
        }
      } catch (error) {
        console.error('Failed to fetch history list:', error);
        historyList.value = [];
      }
    };

    const showHistory = (sdk: SdkListResp) => {
      selectedSdk.value = sdk;
      fetchHistoryList(sdk.sha256Code);
      dialogVisible.value = true;
    };

    const formatTime = (row: any, column: any, cellValue: string) => {
      const date = new Date(cellValue);
      return date.toLocaleString();
    };

    const getIconUrl = (iconLocation: string) => {
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

    const getAuditStatus = (status: number) => {
      switch (status) {
        case 0: return '待审核';
        case 1: return '通过';
        case 2: return '不通过';
        default: return '未知状态';
      }
    };

    const formatStatus = (row: any, column: any, cellValue: number) => {
      return getDetectionStatus(cellValue);
    };

    onMounted(() => {
      fetchSdkList();
    });

    return {
      sdkList,
      dialogVisible,
      selectedSdk,
      historyList,
      formatTime,
      getIconUrl,
      getDetectionStatus,
      getAuditStatus,
      showHistory,
      formatStatus
    };
  }
});
</script>

<style scoped>
.sdk-list {
  text-align: center;
  padding: 20px;
  background-color: #1c1c1e;
  color: #ffffff;
}

.custom-table {
  width: 100%;
  border: 1px solid #333;
  border-radius: 10px;
  background-color: #2c2c2e;
  margin-bottom: 20px;
  text-align: left;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.custom-table .el-table th {
  background-color: #444;
  color: #ffffff;
  font-weight: 600;
}

.custom-table .el-table td {
  background-color: #333;
  border: 1px solid #444;
  color: #ffffff;
}

.icon-image {
  width: 100px;
  height: 100px;
  object-fit: contain;
}

.el-dialog {
  background-color: #1c1c1e;
  color: #ffffff;
}

.el-card {
  background-color: #2c2c2e;
  color: #ffffff;
}

.dialog-footer {
  text-align: right;
  background-color: #2c2c2e;
}
</style>