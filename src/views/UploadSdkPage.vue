<template>
  <div class="upload-sdk-page">
    <el-card class="custom-card">
      <el-button class="back-button" @click="goBack">
        <el-icon><ArrowLeft /></el-icon>
      </el-button>
      <div class="header">
        <h2>上传SDK</h2>
      </div>
      <div class="form-container">
        <el-card class="form-card">
          <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
            <el-form-item label="SDK 名称" prop="sdkName">
              <el-input v-model="form.sdkName" placeholder="输入SDK名称" />
            </el-form-item>
            <el-form-item label="分类" prop="category">
              <el-select v-model="form.category" placeholder="选择分类">
                <el-option label="生活服务" value="生活服务" />
                <el-option label="安全" value="安全" />
                <!-- Add other categories as needed -->
              </el-select>
            </el-form-item>
            <el-form-item label="图标文件" prop="iconFile">
              <el-upload
                :file-list="iconFileList"
                :before-upload="handleIconUpload"
                :on-remove="handleIconRemove"
                list-type="picture"
                class="upload-demo"
              >
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">只能上传一个文件</div>
              </el-upload>
            </el-form-item>
            <el-form-item label="HAR 文件" prop="harFile">
              <el-upload
                :file-list="harFileList"
                :before-upload="handleHarUpload"
                :on-remove="handleHarRemove"
                class="upload-demo"
              >
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">只能上传一个文件</div>
              </el-upload>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm">提交</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </div>
    </el-card>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { ElForm, ElMessage, ElIcon } from 'element-plus';
import { ArrowLeft } from '@element-plus/icons-vue';

export default defineComponent({
  name: 'UploadSdkPage',
  components: {
    ElIcon,
    ArrowLeft
  },
  setup() {
    const router = useRouter();
    const form = ref({
      sdkName: '',
      category: '',
      iconFile: null as File | null,
      harFile: null as File | null,
    });

    const formRef = ref<InstanceType<typeof ElForm> | null>(null);

    const rules = {
      sdkName: [{ required: true, message: '请输入SDK名称', trigger: 'blur' }],
      category: [{ required: true, message: '请选择分类', trigger: 'change' }],
      iconFile: [{ required: true, message: '请上传图标文件', trigger: 'change' }],
      harFile: [{ required: true, message: '请上传HAR文件', trigger: 'change' }],
    };

    const iconFileList = ref<any[]>([]);
    const harFileList = ref<any[]>([]);

    const handleIconUpload = (file: File) => {
      form.value.iconFile = file;
      iconFileList.value = [{ name: file.name, url: URL.createObjectURL(file) }];
      return false;
    };

    const handleHarUpload = (file: File) => {
      form.value.harFile = file;
      harFileList.value = [{ name: file.name, url: URL.createObjectURL(file) }];
      return false;
    };

    const handleIconRemove = () => {
      form.value.iconFile = null;
      iconFileList.value = [];
    };

    const handleHarRemove = () => {
      form.value.harFile = null;
      harFileList.value = [];
    };

    const submitForm = () => {
      if (formRef.value) {
        formRef.value.validate(async (valid: boolean) => {
          if (valid) {
            const formData = new FormData();
            formData.append('sdkName', form.value.sdkName);
            formData.append('category', form.value.category);
            formData.append('icon', form.value.iconFile as Blob);
            formData.append('har', form.value.harFile as Blob);

            try {
              const response = await axios.post('http://localhost:8080/api/upload', formData, {
                headers: {
                  'Content-Type': 'multipart/form-data',
                },
              });

              if (response.data.status === 200) {
                ElMessage({
                  message: '上传成功',
                  type: 'success',
                  showClose: true,
                });
              } else {
                ElMessage({
                  message: '上传失败: ' + response.data.message,
                  type: 'error',
                  showClose: true,
                });
              }
            } catch (error: any) {
              ElMessage({
                message: '上传失败: ' + error.message,
                type: 'error',
                showClose: true,
              });
            }
          } else {
            ElMessage({
              message: '请填写所有字段并选择文件。',
              type: 'error',
              showClose: true,
            });
          }
        });
      }
    };

    const goBack = () => {
      router.push('/sdk-list');
    };

    return {
      form,
      formRef,
      rules,
      iconFileList,
      harFileList,
      handleIconUpload,
      handleHarUpload,
      handleIconRemove,
      handleHarRemove,
      submitForm,
      goBack,
    };
  },
});
</script>

<style scoped>
.upload-sdk-page {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #1c1c1e;
  color: #ffffff;
}

.custom-card {
  width: 1500px;
  left: 110px;
  top: -60px;
  height: 900px;
  background-color: hsl(240, 2%, 18%);
  color: #ffffff;
  border: none;
  padding: 40px;
  border-radius: 15px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  position: relative;
}

.form-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.form-card {
  width: 500px;
  background-color: hsl(240, 2%, 20%);
  padding: 30px;
  border-radius: 10px;
}

.header {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 30px;
  position: relative;
}

.header h2 {
  color: #ffffff;
  font-size: 40px;
  margin: 0;
}

.el-form-item {
  margin-bottom: 25px;
}

.el-input,
.el-select,
.el-button {
  width: 100%;
  border-radius: 5px;
}

.upload-demo .el-upload__tip {
  font-size: 12px;
  color: #999;
}

.el-button {
  background-color: #409eff;
  color: #ffffff;
  border-radius: 5px;
  font-size: 16px;
}

.el-button--primary {
  background-color: #409eff;
}

.el-button--default {
  background-color: transparent;
  border: none;
  color: #ffffff;
}

.back-button {
  position: absolute;
  left: 10px;
  top: 10px;
  background-color: #409eff;
  color: #ffffff;
  border: none;
  border-radius: 50%;
  font-size: 18px; /* 调整图标大小 */
  width: 35px; /* 调整按钮大小 */
  height: 35px; /* 调整按钮大小 */
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background-color 0.3s;
}

.back-button:hover {
  background-color: #66b1ff; /* 按钮悬停效果 */
}
</style>