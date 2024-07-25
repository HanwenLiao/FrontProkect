<template>
  <div class="permission-settings">
    <el-table :data="pagedData" class="custom-table">
      <el-table-column prop="id" label="ID" width="80"/>
      <el-table-column prop="permissionName" label="权限名称" width="500"/>
      <el-table-column label="是否敏感" width="200">
        <template #default="scope">
          <el-icon :style="{ color: scope.row.isSensitive ? '#ef5350' : '#66bb6a' }">
            <span v-if="scope.row.isSensitive">●</span>
            <span v-else>●</span>
          </el-icon>
          {{ scope.row.isSensitive ? '敏感' : '不敏感' }}
          <el-button type="text" @click="toggleSensitive(scope.row)">
            <el-icon><Edit /></el-icon>
          </el-button>
        </template>
        <template #header>
          <div class="header-filter">
            <span>是否敏感</span>
            <el-popover placement="bottom-start" width="150" trigger="click" class="filter-popover">
              <el-checkbox-group v-model="sensitiveFilter" @change="applyFilters" class="filter-group">
                <el-checkbox label="sensitive" class="filter-option">敏感</el-checkbox>
                <el-checkbox label="non-sensitive" class="filter-option">不敏感</el-checkbox>
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
      <el-table-column prop="description" label="描述" width="600"/>
      <el-table-column label="权限类型" width="200">
        <template #default="scope">
          {{ getPermissionTypeDescription(scope.row.permissionType) }}
        </template>
        <template #header>
          <div class="header-filter">
            <span>权限类型</span>
            <el-popover placement="bottom-start" width="200" trigger="click" class="filter-popover">
              <el-checkbox-group v-model="typeFilter" @change="applyFilters" class="filter-group">
                <el-checkbox v-for="type in permissionTypes" :key="type.value" :label="type.value" class="filter-option">
                  {{ type.description }}
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
    </el-table>
    <el-pagination
      background
      layout="prev, pager, next"
      :total="totalPermissions"
      :page-size="pageSize"
      :current-page.sync="currentPage"
      @current-change="handlePageChange"
      class="pagination"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import axios from 'axios';
import { Edit, ArrowDown } from '@element-plus/icons-vue';

interface Permission {
  id: number;
  permissionName: string;
  isSensitive: boolean;
  description: string;
  permissionType: string;
}

interface PermissionType {
  value: string;
  description: string;
}

export default defineComponent({
  name: 'PermissionSettings',
  components: {
    Edit,
    ArrowDown,
  },
  setup() {
    const permissions = ref<Permission[]>([]);
    const pagedData = ref<Permission[]>([]);
    const totalPermissions = ref(0);
    const pageSize = 15;
    const currentPage = ref(1);

    const sensitiveFilter = ref<string[]>([]);
    const typeFilter = ref<string[]>([]);

    const permissionTypes: PermissionType[] = [
      { value: '蓝牙', description: '蓝牙' },
      { value: '网络', description: '网络' },
      { value: '音频', description: '音频' },
      // Add more types as needed
    ];

    const fetchPermissions = async () => {
      try {
        const response = await axios.get<Permission[]>('http://localhost:8080/permissions');
        if (response.data) {
          permissions.value = response.data;
          totalPermissions.value = response.data.length;
          handlePageChange(1);
        }
      } catch (error) {
        console.error('Failed to fetch permissions:', error);
      }
    };

    const handlePageChange = (page: number) => {
      currentPage.value = page;
      applyFilters();
    };

    const toggleSensitive = async (permission: Permission) => {
      try {
        const response = await axios.put<Permission>(`http://localhost:8080/permissions/${permission.id}/toggle-sensitive`);
        if (response.data) {
          const updatedPermission = response.data;
          const index = permissions.value.findIndex((p) => p.id === updatedPermission.id);
          if (index !== -1) {
            permissions.value[index] = updatedPermission;
            applyFilters();
          }
        }
      } catch (error) {
        console.error('Failed to toggle sensitive:', error);
      }
    };

    const getPermissionTypeDescription = (type: string) => {
      const foundType = permissionTypes.find(t => t.value === type);
      return foundType ? foundType.description : type;
    };

    const applyFilters = () => {
      let filtered = permissions.value;

      if (sensitiveFilter.value.length > 0) {
        filtered = filtered.filter(permission =>
          sensitiveFilter.value.includes('sensitive') && permission.isSensitive ||
          sensitiveFilter.value.includes('non-sensitive') && !permission.isSensitive
        );
      }

      if (typeFilter.value.length > 0) {
        filtered = filtered.filter(permission =>
          typeFilter.value.includes(permission.permissionType)
        );
      }

      totalPermissions.value = filtered.length;
      const start = (currentPage.value - 1) * pageSize;
      const end = start + pageSize;
      pagedData.value = filtered.slice(start, end);
    };

    onMounted(() => {
      fetchPermissions();
    });

    return {
      pagedData,
      totalPermissions,
      pageSize,
      currentPage,
      handlePageChange,
      toggleSensitive,
      sensitiveFilter,
      typeFilter,
      permissionTypes,
      applyFilters,
      getPermissionTypeDescription,
    };
  },
});
</script>

<style scoped>
.permission-settings {
  text-align: center;
  padding: 20px;
  background-color: #1c1c1e;
  color: #ffffff;
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

.pagination {
  position: fixed;
  top: 910px;
  left: 260px;
  text-align: center;
  margin-top: 20px;
}

.el-pagination__prev,
.el-pagination__next,
.el-pager li {
  background-color: #2c2c2e;
  color: #ffffff;
}

.el-pagination__prev:hover,
.el-pagination__next:hover,
.el-pager li:hover {
  background-color: #444;
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