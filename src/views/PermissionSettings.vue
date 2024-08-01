<template>
  <div class="permission-settings">
    <el-button class="add-button" @click="showAddCard">
      <el-icon><Plus /></el-icon>
    </el-button>
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
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button type="text" @click="showEditCard(scope.row)">
            <el-icon><Edit /></el-icon> 编辑
          </el-button>
          <el-button type="text" @click="showDeleteCard(scope.row)">
            <el-icon><Delete /></el-icon> 删除
          </el-button>
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

    <!-- 添加权限卡片 -->
    <el-card class="box-card" v-if="addCardVisible">
      <div slot="header" class="clearfix">
        <span>添加权限</span>
        <el-button @click="addCardVisible = false" type="text" class="close-button">×</el-button>
      </div>
      <el-form :model="addForm" label-width="100px">
        <el-form-item label="权限名称">
          <el-input v-model="addForm.permissionName"></el-input>
        </el-form-item>
        <el-form-item label="是否敏感">
          <el-switch v-model="addForm.isSensitive" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
        <el-form-item label="描述">
          <el-input type="textarea" v-model="addForm.description"></el-input>
        </el-form-item>
        <el-form-item label="权限类型">
          <el-select v-model="addForm.permissionType" placeholder="请选择权限类型">
            <el-option
              v-for="type in permissionTypes"
              :key="type.value"
              :label="type.description"
              :value="type.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div class="dialog-footer">
        <el-button @click="addCardVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAdd">保存</el-button>
      </div>
    </el-card>

    <!-- 编辑卡片 -->
    <el-card class="box-card" v-if="editCardVisible">
      <div slot="header" class="clearfix">
        <span>编辑权限</span>
        <el-button @click="editCardVisible = false" type="text" class="close-button">×</el-button>
      </div>
      <el-form :model="editForm" label-width="100px">
        <el-form-item label="权限名称">
          <el-input v-model="editForm.permissionName"></el-input>
        </el-form-item>
        <el-form-item label="是否敏感">
          <el-switch v-model="editForm.isSensitive" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
        <el-form-item label="描述">
          <el-input type="textarea" v-model="editForm.description"></el-input>
        </el-form-item>
        <el-form-item label="权限类型">
          <el-select v-model="editForm.permissionType" placeholder="请选择权限类型">
            <el-option
              v-for="type in permissionTypes"
              :key="type.value"
              :label="type.description"
              :value="type.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div class="dialog-footer">
        <el-button @click="editCardVisible = false">取消</el-button>
        <el-button type="primary" @click="submitEdit">保存</el-button>
      </div>
    </el-card>

    <!-- 删除卡片 -->
    <el-card class="box-card" v-if="deleteCardVisible">
      <div slot="header" class="clearfix">
        <span>确认删除</span>
        <el-button @click="deleteCardVisible = false" type="text" class="close-button">×</el-button>
      </div>
      <span>确定要删除这个权限吗？</span>
      <div class="dialog-footer">
        <el-button @click="deleteCardVisible = false">取消</el-button>
        <el-button type="primary" @click="submitDelete">删除</el-button>
      </div>
    </el-card>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import axios from 'axios';
import { Edit, Delete, Plus, ArrowDown } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';

interface Permission {
  id: number;
  permissionName: string;
  isSensitive: number;
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
    Delete,
    Plus,
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

    const editCardVisible = ref(false);
    const addCardVisible = ref(false);
    const deleteCardVisible = ref(false);
    const editForm = ref<Permission>({
      id: 0,
      permissionName: '',
      isSensitive: 0,
      description: '',
      permissionType: '',
    });
    const addForm = ref<Permission>({
      id: 0,
      permissionName: '',
      isSensitive: 0,
      description: '',
      permissionType: '',
    });
    const currentPermission = ref<Permission | null>(null);

    const permissionTypes: PermissionType[] = [
      { value: 'BLUETOOTH', description: '蓝牙' },
      { value: 'NETWORK', description: '网络' },
      { value: 'AUDIO', description: '音频' },
      { value: 'NOTIFICATION', description: '通知' },
      { value: 'TELEPHONE', description: '电话' },
      { value: 'ABILITY', description: '能力' },
      { value: 'STORAGE', description: '存储' },
      { value: 'SYSTEM', description: '系统' },
      { value: 'DOWNLOAD', description: '下载' },
      { value: 'EVENT', description: '事件' },
      { value: 'WINDOW', description: '窗口' },
      { value: 'HARDWARE', description: '硬件' },
      { value: 'INPUT', description: '输入' },
      { value: 'DISPLAY', description: '显示' },
      { value: 'BROADCAST', description: '广播' },
      { value: 'PRIVACY', description: '隐私' },
      { value: 'MEDIA', description: '媒体' },
      { value: 'SECURITY', description: '安全' },
      { value: 'HEALTH', description: '健康' },
      { value: 'LOCATION', description: '位置' },
      { value: 'CAMERA', description: '相机' },
      { value: 'CALENDAR', description: '日历' },
      { value: 'ACCOUNT', description: '账户' },
      { value: 'ACCESSIBILITY', description: '无障碍' },
      { value: 'MICROPHONE', description: '麦克风' },
      { value: 'CONTACTS', description: '联系人' },
      { value: 'SENSOR', description: '传感器' },
      { value: 'NFC', description: 'NFC' },
      { value: 'FITNESS', description: '健身运动' },
      { value: 'DISTRIBUTED_DATA_MANAGEMENT', description: '分布式数据管理' }
    ];
    const fetchPermissions = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/permissions');
    if (response.data && response.data.code === 200) {
      permissions.value = response.data.data;
      totalPermissions.value = response.data.total;
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
    const response = await axios.put(`http://localhost:8080/api/permissions/${permission.id}/toggle-sensitive`);
    if (response.data && response.data.code === 200) {
      const updatedPermission = response.data.data;
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

const showAddCard = () => {
  addForm.value = {
    id: 0,
    permissionName: '',
    isSensitive: 0,
    description: '',
    permissionType: '',
  };
  addCardVisible.value = true;
};

const submitAdd = async () => {
  try {
    const response = await axios.post('http://localhost:8080/api/permissions/add', addForm.value);
    if (response.data && response.data.code === 200) {
      permissions.value.push(response.data.data);
      applyFilters();
      addCardVisible.value = false;
      ElMessage.success('添加成功');
    }
  } catch (error) {
    console.error('Failed to add permission:', error);
    ElMessage.error('添加失败');
  }
};

const showEditCard = (permission: Permission) => {
  currentPermission.value = { ...permission };
  editForm.value = { ...permission };
  editCardVisible.value = true;
};

const submitEdit = async () => {
  if (!currentPermission.value) return;
  try {
    const response = await axios.put(`http://localhost:8080/api/permissions/${currentPermission.value.id}/update`, editForm.value);
    if (response.data && response.data.code === 200) {
      const index = permissions.value.findIndex((p) => p.id === currentPermission.value!.id);
      if (index !== -1) {
        permissions.value[index] = response.data.data;
        applyFilters();
        editCardVisible.value = false;
        ElMessage.success('编辑成功');
      }
    }
  } catch (error) {
    console.error('Failed to update permission:', error);
    ElMessage.error('编辑失败');
  }
};

const showDeleteCard = (permission: Permission) => {
  currentPermission.value = { ...permission };
  deleteCardVisible.value = true;
};

const submitDelete = async () => {
  if (!currentPermission.value) return;
  try {
    const response = await axios.delete(`http://localhost:8080/api/permissions/${currentPermission.value.id}/delete`);
    if (response.data && response.data.code === 200) {
      permissions.value = permissions.value.filter(p => p.id !== currentPermission.value!.id);
      applyFilters();
      deleteCardVisible.value = false;
      ElMessage.success('删除成功');
    }
  } catch (error) {
    console.error('Failed to delete permission:', error);
    ElMessage.error('删除失败');
  }
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
  addCardVisible,
  addForm,
  submitAdd,
  showAddCard,
  editCardVisible,
  editForm,
  submitEdit,
  showEditCard,
  deleteCardVisible,
  showDeleteCard,
  submitDelete,
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

.add-button {
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

.pagination {
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

.el-card {
  position: fixed;
  top: 50%;
  left: 50%;
  width: 400px;
  transform: translate(-50%, -50%);
  background-color: #2c2c2e;
  color: #ffffff;
  border: 1px solid #444;
  border-radius: 15px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.5);
  z-index: 1000;
}

.dialog-footer {
  text-align: right;
  padding: 10px 0;
}

.close-button {
  float: right;
  font-size: 20px;
  cursor: pointer;
}
</style>