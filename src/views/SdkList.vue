<!-- <template>
  <div class="sdk-list">
    <el-button class="upload-button" @click="goToUploadPage">
      <el-icon><Plus /></el-icon>
    </el-button>
    <transition-group name="fade" tag="div">
      <el-table :data="pagedData" class="custom-table" key="table">
        <el-table-column label="NO." width="50">
          <template #default="scope">
            {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="图标" width="80">
          <template #default="scope">
            <img :src="getIconUrl(scope.row.iconLocation)" alt="Icon" class="icon-image" />
          </template>
        </el-table-column>
        <el-table-column prop="sdkName" label="SDK名称" width="190"/>
        <el-table-column prop="pkgName" label="包名" width="200"/>
        <el-table-column prop="versionName" label="版本" width="80"/>
        <el-table-column label="分类" width="100">
          <template #default="scope">
            <span>{{ getCategory(scope.row.categoryValue) }}</span>
          </template>
          <template #header>
            <div class="header-filter">
              <span>分类</span>
              <el-popover placement="bottom-start" width="200" trigger="click" class="filter-popover">
                <el-checkbox-group v-model="categoryFilter" @change="applyFilters" class="filter-group">
                  <el-checkbox v-for="category in categories" :key="category.value" :label="category.value" class="filter-option">
                    {{ category.label }}
                  </el-checkbox>
                </el-checkbox-group>
                <template #reference>
                  <el-button type="text" class="filter-button">
                    <el-icon><ArrowDown /></el-icon>
                  </el-button>
                </template>
              </el-popover>
              
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="size" label="大小" width="80"/>
        <el-table-column label="检测状态" width="280">
          <template #default="scope">
            <div class="status-container">
              <span>权限检测：</span>
              <el-icon :style="{ color: getStatusColor(scope.row.detectStatus) }" class="status-dot">●</el-icon>
              {{ getDetectionStatus(scope.row.detectStatus) }}
              <div class="button-container">
                <el-button @click="retryDetection(scope.row)" type="default">重新检测</el-button>
                <el-button @click="goToHistoryPage(scope.row)" type="default">查看</el-button>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="审核状态" width="120">
          <template #default="scope">
            <el-icon :style="{ color: getAuditStatusColor(scope.row.auditStatus) }" class="status-dot">●</el-icon>
            {{ getAuditStatus(scope.row.auditStatus) }}
          </template>
          <template #header>
            <div class="header-filter">
              <span>审核状态</span>
              <el-popover placement="bottom-start" width="200" trigger="click" class="filter-popover">
                <el-checkbox-group v-model="auditStatusFilter" @change="applyFilters" class="filter-group">
                  <el-checkbox v-for="status in auditStatuses" :key="status.value" :label="status.value" class="filter-option">
                    {{ status.label }}
                  </el-checkbox>
                </el-checkbox-group>
                <template #reference>
                  <el-button type="text" class="filter-button">
                    <el-icon><ArrowDown /></el-icon>
                  </el-button>
                </template>
              </el-popover>
              
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="updatetime" label="更新时间" width="180" :formatter="formatTime"/>
        <el-table-column label="操作" width="250">
          <el-button type="default">详情</el-button>
          <el-button type="default">审核</el-button>
        </el-table-column>
      </el-table>
    </transition-group>
    <el-pagination
      background
      layout="prev, pager, next"
      :total="sdkList.length"
      :page-size="pageSize"
      :current-page.sync="currentPage"
      @current-change="handlePageChange"
      class="pagination"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { SdkListResp } from '@/types/types';
import { Plus, ArrowDown } from '@element-plus/icons-vue';

export default defineComponent({
  name: 'SdkList',
  components: {
    Plus,
    ArrowDown,
  },
  setup() {
    const router = useRouter();
    const sdkList = ref<SdkListResp[]>([]);
    const pagedData = ref<SdkListResp[]>([]);
    const loading = ref(true);
    const pageSize = 8;
    const currentPage = ref(1);

    const categoryFilter = ref<number[]>([]);
    const auditStatusFilter = ref<number[]>([]);

    const categories = [
      { value: 1, label: '生活服务' },
      { value: 2, label: '安全' },
      { value: 3, label: '媒体' },
      { value: 4, label: 'AI' },
      { value: 5, label: '平台服务' },
      { value: 6, label: '广告' },
      { value: 7, label: '支付' },
      { value: 8, label: '分析' },
      { value: 9, label: '社交' },
      { value: 10, label: '金融理财' },
      { value: 11, label: '工具' },
      { value: 12, label: '账号登录' },
      { value: 13, label: '游戏' },
      { value: 14, label: '框架' },
      { value: 15, label: '性能监控' },
      { value: 16, label: '网络' },
      { value: 17, label: '推送' },
      { value: 18, label: '地图定位' },
      { value: 19, label: '存储' },
      { value: 20, label: '电商服务' },
      { value: 21, label: 'AR/VR' },
      { value: 22, label: '深链' },
      { value: 23, label: '其他' },
    ];

    const auditStatuses = [
      { value: 0, label: '待审核' },
      { value: 1, label: '通过' },
      { value: 2, label: '不通过' },
    ];

    const fetchSdkList = async () => {
      loading.value = true;
      try {
        const response = await axios.get('http://localhost:8080/api/sdk-list/all');
        if (response.data && response.data.code === 200) {
          sdkList.value = response.data.data;
          handlePageChange(1);
        } else {
          sdkList.value = [];
        }
      } catch (error) {
        console.error('Failed to fetch SDK list:', error);
        sdkList.value = [];
      }
      loading.value = false;
    };

    const goToUploadPage = () => {
      router.push('/upload-sdk');
    };

    const goToHistoryPage = (sdk: SdkListResp) => {
      router.push({
        name: 'SdkHistory',
        params: {
          sha256Code: sdk.sha256Code,
          sdkName: sdk.sdkName,
          pkgName: sdk.pkgName,
          versionName: sdk.versionName,
          iconLocation: sdk.iconLocation,
        },
      });
    };

    const retryDetection = async (sdk: SdkListResp) => {
      try {                               
        const response = await axios.post(`http://localhost:8080/api/detect-tasks/retry-detection/${sdk.sha256Code}`);
        if (response.data && response.data.code === 200) {
          console.log('重新检测成功');
          fetchSdkList();
        } else {
          console.error('重新检测失败:', response.data.message);
        }
      } catch (error) {
        console.error('重新检测请求失败:', error);
      }
    };

    const formatTime = (row: any, column: any, cellValue: string) => {
      const date = new Date(cellValue);
      return date.toLocaleString();
    };

    const getIconUrl = (iconLocation: string) => {
      return `http://localhost:8080${iconLocation.replace('/Users/liaohanwen/Documents/workspace/sdkcenter/src/main/resources/static', '')}`;
    };

    const getCategory = (status: number) => {
      const category = categories.find(cat => cat.value === status);
      return category ? category.label : '其他';
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

    const getAuditStatus = (status: number) => {
      const auditStatus = auditStatuses.find(audit => audit.value === status);
      return auditStatus ? auditStatus.label : '未知状态';
    };

    const getAuditStatusColor = (status: number) => {
      switch (status) {
        case 0: return '#ffca28'; // 待审核 (黄色)
        case 1: return '#66bb6a'; // 通过 (绿色)
        case 2: return '#ef5350'; // 不通过 (红色)
        default: return '#9e9e9e'; // 未知状态 (灰色)
      }
    };

    const handlePageChange = (page: number) => {
      currentPage.value = page;
      applyFilters();
    };

    const applyFilters = () => {
      let filtered = sdkList.value;

      if (categoryFilter.value.length > 0) {
        filtered = filtered.filter(sdk => categoryFilter.value.includes(sdk.categoryValue));
      }

      if (auditStatusFilter.value.length > 0) {
        filtered = filtered.filter(sdk => auditStatusFilter.value.includes(sdk.auditStatus));
      }

      const start = (currentPage.value - 1) * pageSize;
      const end = start + pageSize;
      pagedData.value = filtered.slice(start, end);
    };

    onMounted(() => {
      fetchSdkList();
    });

    watch(sdkList, () => {
      applyFilters();
    });

    return {
      sdkList,
      pagedData,
      loading,
      pageSize,
      currentPage,
      formatTime,
      getIconUrl,
      getCategory,
      getDetectionStatus,
      getAuditStatus, 
      getStatusColor,
      getAuditStatusColor,
      goToUploadPage,
      goToHistoryPage,
      handlePageChange,
      retryDetection,
      categoryFilter,
      auditStatusFilter,
      categories,
      auditStatuses,
      applyFilters,
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

.el-button {
  width: 40px;
  background-color: #1caf2800;
  border-color: #ffffff00;
  color: #0077ff;
}

.upload-button {
  position: fixed;
  top: 75px;
  left: 1680px;
  width: 40px;
  height: 40px;
  background-color: #1caf29;
  border-color: #ffffff00;
  color: #e7e7e7;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.custom-table {
  top: 30px;
  left: 200px;
  width: 1500px;
  color: #e7e7e7f1;
  border: 1px solid #333;
  border-radius: 15px;
  background-color: #2c2c2e;
  margin-bottom: 20px;
  text-align: left;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.custom-table .el-table th {
  color: #ffffff;
  font-weight: 600;
}

.custom-table .el-table td {
  background-color: #333;
  border: 1px solid #444;
  color: #ffffff;
}

.icon-image {
  width: 60px;
  height: 60px;
  object-fit: contain;
}

.el-dialog {
  z-index: 9999;
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

.pagination {
  position: fixed;
  top: 910px;
  left: 260px;
  text-align: center;
  margin-top: 20px;
}

.pagination .el-pagination__prev,
.pagination .el-pagination__next,
.pagination .el-pager li {
  background-color: #2c2c2e;
  color: #ffffff;
}

.pagination .el-pagination__prev:hover,
.pagination .el-pagination__next:hover,
.pagination .el-pager li:hover {
  background-color: #444;
}

.status-dot {
  margin-right: 5px;
  font-size: 12px;
}

.status-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.button-container {
  margin-left: auto;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s, transform 0.5s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
  transform: translateY(10px);
}

.header-filter {
  display: flex;
  align-items: center;
}

.filter-popover {
  background-color: #2c2c2e;
  color: #ffffff;
  border: 1px solid #444;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.filter-group {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.filter-option {
  background-color: #2c2c2e;
  color: #ffffff;
  border: none;
  margin: 5px 0;
}

.filter-option:hover {
  background-color: #444;
}

.filter-button {
  color: #ffffff;
}

.filter-button:hover {
  color: #66b1ff;
}
</style> -->

<template>
  <div class="sdk-list">
    <el-button class="upload-button" @click="goToUploadPage">
      <el-icon><Plus /></el-icon>
    </el-button>
    <transition-group name="fade" tag="div">
      <el-table :data="pagedData" class="custom-table" key="table">
        <el-table-column label="NO." width="50">
          <template #default="scope">
            {{ (currentPage - 1) * pageSize + scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="图标" width="80">
          <template #default="scope">
            <img :src="getIconUrl(scope.row.iconLocation)" alt="Icon" class="icon-image" />
          </template>
        </el-table-column>
        <el-table-column prop="sdkName" label="SDK名称" width="190"/>
        <el-table-column prop="pkgName" label="包名" width="200"/>
        <el-table-column prop="versionName" label="版本" width="80"/>
        <el-table-column label="分类" width="100">
          <template #default="scope">
            <span>{{ getCategory(scope.row.categoryValue) }}</span>
          </template>
          <template #header>
            <div class="header-filter">
              <span>分类</span>
              <el-popover placement="bottom-start" width="200" trigger="click" class="filter-popover">
                <el-checkbox-group v-model="categoryFilter" @change="applyFilters" class="filter-group">
                  <el-checkbox v-for="category in categories" :key="category.value" :label="category.value" class="filter-option">
                    {{ category.label }}
                  </el-checkbox>
                </el-checkbox-group>
                <template #reference>
                  <el-button type="text" class="filter-button">
                    <el-icon><ArrowDown /></el-icon>
                  </el-button>
                </template>
              </el-popover>
              
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="size" label="大小" width="80"/>
        <el-table-column label="检测状态" width="280">
          <template #default="scope">
            <div class="status-container">
              <span>权限检测：</span>
              <el-icon :style="{ color: getStatusColor(scope.row.detectStatus) }" class="status-dot">●</el-icon>
              {{ getDetectionStatus(scope.row.detectStatus) }}
              <div class="button-container">
                <el-button @click="retryDetection(scope.row)" type="default">重新检测</el-button>
                <el-button @click="goToHistoryPage(scope.row)" type="default">查看</el-button>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="审核状态" width="120">
          <template #default="scope">
            <el-icon :style="{ color: getAuditStatusColor(scope.row.auditStatus) }" class="status-dot">●</el-icon>
            {{ getAuditStatus(scope.row.auditStatus) }}
          </template>
          <template #header>
            <div class="header-filter">
              <span>审核状态</span>
              <el-popover placement="bottom-start" width="200" trigger="click" class="filter-popover">
                <el-checkbox-group v-model="auditStatusFilter" @change="applyFilters" class="filter-group">
                  <el-checkbox v-for="status in auditStatuses" :key="status.value" :label="status.value" class="filter-option">
                    {{ status.label }}
                  </el-checkbox>
                </el-checkbox-group>
                <template #reference>
                  <el-button type="text" class="filter-button">
                    <el-icon><ArrowDown /></el-icon>
                  </el-button>
                </template>
              </el-popover>
              
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="updatetime" label="更新时间" width="180" :formatter="formatTime"/>
        <el-table-column label="操作" width="250">
          <el-button type="default">详情</el-button>
          <el-button type="default">审核</el-button>
        </el-table-column>
      </el-table>
    </transition-group>
    <el-pagination
      background
      layout="prev, pager, next"
      :total="sdkList.length"
      :page-size="pageSize"
      :current-page.sync="currentPage"
      @current-change="handlePageChange"
      class="pagination"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { SdkListResp } from '@/types/types';
import { Plus, ArrowDown } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';

export default defineComponent({
  name: 'SdkList',
  components: {
    Plus,
    ArrowDown,
  },
  setup() {
    const router = useRouter();
    const sdkList = ref<SdkListResp[]>([]);
    const pagedData = ref<SdkListResp[]>([]);
    const loading = ref(true);
    const pageSize = 8;
    const currentPage = ref(1);

    const categoryFilter = ref<number[]>([]);
    const auditStatusFilter = ref<number[]>([]);

    const categories = [
      { value: 1, label: '生活服务' },
      { value: 2, label: '安全' },
      { value: 3, label: '媒体' },
      { value: 4, label: 'AI' },
      { value: 5, label: '平台服务' },
      { value: 6, label: '广告' },
      { value: 7, label: '支付' },
      { value: 8, label: '分析' },
      { value: 9, label: '社交' },
      { value: 10, label: '金融理财' },
      { value: 11, label: '工具' },
      { value: 12, label: '账号登录' },
      { value: 13, label: '游戏' },
      { value: 14, label: '框架' },
      { value: 15, label: '性能监控' },
      { value: 16, label: '网络' },
      { value: 17, label: '推送' },
      { value: 18, label: '地图定位' },
      { value: 19, label: '存储' },
      { value: 20, label: '电商服务' },
      { value: 21, label: 'AR/VR' },
      { value: 22, label: '深链' },
      { value: 23, label: '其他' },
    ];

    const auditStatuses = [
      { value: 0, label: '待审核' },
      { value: 1, label: '通过' },
      { value: 2, label: '不通过' },
    ];

    const fetchSdkList = async () => {
      loading.value = true;
      try {
        const response = await axios.get('http://localhost:8080/api/sdk-list/all');
        if (response.data && response.data.code === 200) {
          sdkList.value = response.data.data;
          handlePageChange(1);
        } else {
          sdkList.value = [];
        }
      } catch (error) {
        console.error('Failed to fetch SDK list:', error);
        sdkList.value = [];
      }
      loading.value = false;
    };

    const goToUploadPage = () => {
      router.push('/upload-sdk');
    };

    const goToHistoryPage = (sdk: SdkListResp) => {
      router.push({
        name: 'SdkHistory',
        params: {
          sha256Code: sdk.sha256Code,
          sdkName: sdk.sdkName,
          pkgName: sdk.pkgName,
          versionName: sdk.versionName,
          iconLocation: sdk.iconLocation,
        },
      });
    };

    const retryDetection = async (sdk: SdkListResp) => {
      try {                               
        const response = await axios.post(`http://localhost:8080/api/detect-tasks/retry-detection/${sdk.sha256Code}`);
        if (response.data && response.data.code === 200) {
          ElMessage.success('重新检测成功');
          fetchSdkList();
        } else {
          ElMessage.error('重新检测失败: ' + response.data.message);
        }
      } catch (error) {
        if (axios.isAxiosError(error) && error.response) {
          ElMessage.error('重新检测请求失败: ' + error.response.data.message);
        } else if (error instanceof Error) {
          ElMessage.error('重新检测请求失败: ' + error.message);
        } else {
          ElMessage.error('重新检测请求失败');
        }
      }
    };

    const formatTime = (row: any, column: any, cellValue: string) => {
      const date= new Date(cellValue);
return date.toLocaleString();
};
const getIconUrl = (iconLocation: string) => {
  return `http://localhost:8080${iconLocation.replace('/Users/liaohanwen/Documents/workspace/sdkcenter/src/main/resources/static', '')}`;
};

const getCategory = (status: number) => {
  const category = categories.find(cat => cat.value === status);
  return category ? category.label : '其他';
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

const getAuditStatus = (status: number) => {
  const auditStatus = auditStatuses.find(audit => audit.value === status);
  return auditStatus ? auditStatus.label : '未知状态';
};

const getAuditStatusColor = (status: number) => {
  switch (status) {
    case 0: return '#ffca28'; // 待审核 (黄色)
    case 1: return '#66bb6a'; // 通过 (绿色)
    case 2: return '#ef5350'; // 不通过 (红色)
    default: return '#9e9e9e'; // 未知状态 (灰色)
  }
};

const handlePageChange = (page: number) => {
  currentPage.value = page;
  applyFilters();
};

const applyFilters = () => {
  let filtered = sdkList.value;

  if (categoryFilter.value.length > 0) {
    filtered = filtered.filter(sdk => categoryFilter.value.includes(sdk.categoryValue));
  }

  if (auditStatusFilter.value.length > 0) {
    filtered = filtered.filter(sdk => auditStatusFilter.value.includes(sdk.auditStatus));
  }

  const start = (currentPage.value - 1) * pageSize;
  const end = start + pageSize;
  pagedData.value = filtered.slice(start, end);
};

onMounted(() => {
  fetchSdkList();
});

watch(sdkList, () => {
  applyFilters();
});

return {
  sdkList,
  pagedData,
  loading,
  pageSize,
  currentPage,
  formatTime,
  getIconUrl,
  getCategory,
  getDetectionStatus,
  getAuditStatus, 
  getStatusColor,
  getAuditStatusColor,
  goToUploadPage,
  goToHistoryPage,
  handlePageChange,
  retryDetection,
  categoryFilter,
  auditStatusFilter,
  categories,
  auditStatuses,
  applyFilters,
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

.el-button {
  width: 40px;
  background-color: #1caf2800;
  border-color: #ffffff00;
  color: #0077ff;
}

.upload-button {
  position: fixed;
  top: 75px;
  left: 1680px;
  width: 40px;
  height: 40px;
  background-color: #1caf29;
  border-color: #ffffff00;
  color: #e7e7e7;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.custom-table {
  top: 30px;
  left: 200px;
  width: 1500px;
  color: #e7e7e7f1;
  border: 1px solid #333;
  border-radius: 15px;
  background-color: #2c2c2e;
  margin-bottom: 20px;
  text-align: left;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.custom-table .el-table th {
  color: #ffffff;
  font-weight: 600;
}

.custom-table .el-table td {
  background-color: #333;
  border: 1px solid #444;
  color: #ffffff;
}

.icon-image {
  width: 60px;
  height: 60px;
  object-fit: contain;
}

.el-dialog {
  z-index: 9999;
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

.pagination {
  position: fixed;
  top: 910px;
  left: 260px;
  text-align: center;
  margin-top: 20px;
}

.pagination .el-pagination__prev,
.pagination .el-pagination__next,
.pagination .el-pager li {
  background-color: #2c2c2e;
  color: #ffffff;
}

.pagination .el-pagination__prev:hover,
.pagination .el-pagination__next:hover,
.pagination .el-pager li:hover {
  background-color: #444;
}

.status-dot {
  margin-right: 5px;
  font-size: 12px;
}

.status-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.button-container {
  margin-left: auto;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s, transform 0.5s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
  transform: translateY(10px);
}

.header-filter {
  display: flex;
  align-items: center;
}

.filter-popover {
  background-color: #2c2c2e;
  color: #ffffff;
  border: 1px solid #444;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.filter-group {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.filter-option {
  background-color: #2c2c2e;
  color: #ffffff;
  border: none;
  margin: 5px 0;
}

.filter-option:hover {
  background-color: #444;
}

.filter-button {
  color: #ffffff;
}

.filter-button:hover {
  color: #66b1ff;
}
</style>
