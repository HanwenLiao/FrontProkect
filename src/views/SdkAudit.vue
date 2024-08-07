<template>
    <div v-if="sdk" class="audit-card">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>审核SDK</span>
          <el-button @click="closeAudit" type="text" class="close-button">×</el-button>
        </div>
        <div>
          <div class="info">
            <div class="info-item">
              <div class="title">SDK 名称</div>
              <div class="value">{{ sdk.sdkName }}</div>
            </div>
            <div class="info-item">
              <div class="title">包名</div>
              <div class="value">{{ sdk.pkgName }}</div>
            </div>
            <div class="info-item">
              <div class="title">版本名称</div>
              <div class="value">{{ sdk.versionName }}</div>
            </div>
            <div class="info-item">
              <div class="title">SHA256 码</div>
              <div class="value">{{ sdk.sha256Code }}</div>
            </div>
          </div>
          <div>
            <span>请选择审核结果：</span>
            <el-radio-group v-model="selectedAuditStatus">
              <el-radio :label="1">通过</el-radio>
              <el-radio :label="2">不通过</el-radio>
            </el-radio-group>
          </div>
          <div class="info-item">
            <el-input type="textarea" v-model="auditStatement" placeholder="请输入审核说明"></el-input>
          </div>
          <div>
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
        </div>
        <div class="dialog-footer">
          <el-button @click="closeAudit">取消</el-button>
          <el-button type="primary" @click="submitAudit">提交</el-button>
        </div>
      </el-card>
    </div>
  </template>

<script lang="ts">
import { defineComponent, PropType, ref, computed, onMounted } from 'vue';
import axios from 'axios';
import { SdkListResp, Permission } from '@/types/types';
import { ElMessage } from 'element-plus';

export default defineComponent({
  name: 'SdkAudit',
  props: {
    sdk: {
      type: Object as PropType<SdkListResp | null>,
      required: true,
    },
    onClose: {
      type: Function as PropType<() => void>,
      required: true,
    },
  },
  setup(props) {
    const selectedAuditStatus = ref(0);
    const auditStatement = ref('');
    const permissions = ref<Permission[]>([]);

    const sensitivePermissions = computed(() => {
      return permissions.value.filter(perm => perm.isSensitive === 1);
    });

    const allPermissions = computed(() => {
      return permissions.value;
    });

    const fetchPermissions = async () => {
      if (!props.sdk) return;
      try {
        const response = await axios.get(`http://localhost:8080/api/sdk/${props.sdk.sha256Code}/permissions`);
        if (response.data && response.data.code === 200) {
          permissions.value = response.data.data;
        } else {
          permissions.value = [];
        }
      } catch (error) {
        console.error('Failed to fetch permissions:', error);
        permissions.value = [];
      }
    };

    const submitAudit = async () => {
      if (!props.sdk) return;
      try {
        const response = await axios.post('http://localhost:8080/api/sdk-list/update-audit-status', null, {
          params: {
            sha256Code: props.sdk.sha256Code,
            auditStatus: selectedAuditStatus.value,
            auditStatement: auditStatement.value,
          },
        });
        if (response.data && response.data.code === 200) {
          ElMessage.success('审核状态更新成功');
          props.onClose();
        } else {
          ElMessage.error('审核状态更新失败:' + response.data.message);
        }
      } catch (error) {
        if (axios.isAxiosError(error) && error.response) {
          ElMessage.error('审核状态更新请求失败:' + error.response.data.message);
        } else if (error instanceof Error) {
          ElMessage.error('审核状态更新请求失败:' + error.message);
        } else {
          ElMessage.error('审核状态更新请求失败');
        }
      }
    };

    const closeAudit = () => {
      props.onClose();
    };

    onMounted(fetchPermissions);

    return {
      selectedAuditStatus,
      auditStatement,
      sensitivePermissions,
      allPermissions,
      submitAudit,
      closeAudit,
    };
  },
});
</script>

<style scoped>
.audit-card {
  position: fixed;
  top: 100px;
  right: 100px;
  z-index: 1000;
  width: 600px;
}

.close-button {
  float: right;
  font-size: 18px;
  cursor: pointer;
}

.box-card {
  background-color: #2c2c2e;
  color: #ffffff;
}

.dialog-footer {
  text-align: right;
  background-color: #2c2c2e;
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
  word-break: break-all;
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
</style>