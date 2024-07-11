<template>
  <el-container style="height: 100vh; background-color: #f6f8fa;">
    <el-main style="display: flex; justify-content: center; align-items: center;">
      <div class="upload-form">
        <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
          <el-form-item label="SDK Name" prop="sdkName">
            <el-input v-model="form.sdkName" placeholder="Enter SDK Name" />
          </el-form-item>
          <el-form-item label="Category" prop="category">
            <el-select v-model="form.category" placeholder="Select Category">
              <el-option label="生活服务" value="生活服务" />
              <el-option label="安全" value="安全" />
              <!-- Add other categories as needed -->
            </el-select>
          </el-form-item>
          <el-form-item label="Icon File" prop="iconFile">
            <el-upload
              :file-list="iconFileList"
              :before-upload="handleIconUpload"
              :on-remove="handleIconRemove"
              list-type="picture"
              class="upload-demo"
            >
              <el-button size="small" type="primary">  Click to Upload</el-button>
              <div slot="tip" class="el-upload__tip">  Only one file can be uploaded</div>
            </el-upload>
          </el-form-item>
          <el-form-item label="HAR File" prop="harFile">
            <el-upload
              :file-list="harFileList"
              :before-upload="handleHarUpload"
              :on-remove="handleHarRemove"
              class="upload-demo"
            >
              <el-button size="small" type="primary">Click to Upload</el-button>
              <div slot="tip" class="el-upload__tip">Only one file can be uploaded</div>
            </el-upload>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm">Submit</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-main>
  </el-container>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import axios from 'axios';
import { ElForm, ElMessage } from 'element-plus';
import router from '@/router';

export default defineComponent({
  name: 'UploadSdk',
  setup() {
    const form = ref({
      sdkName: '',
      category: '',
      iconFile: null as File | null,
      harFile: null as File | null,
    });

    const formRef = ref<InstanceType<typeof ElForm> | null>(null);

    const rules = {
      sdkName: [{ required: true, message: 'Please input SDK Name', trigger: 'blur' }],
      category: [{ required: true, message: 'Please select a category', trigger: 'change' }],
      iconFile: [{ required: true, message: 'Please upload an icon file', trigger: 'change' }],
      harFile: [{ required: true, message: 'Please upload a HAR file', trigger: 'change' }],
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
                  message: 'Upload successful',
                  type: 'success',
                  showClose: true,
                });
              } else {
                ElMessage({
                  message: 'Upload failed: ' + response.data.message,
                  type: 'error',
                  showClose: true,
                });
              }
            } catch (error: any) {
              ElMessage({
                message: 'Upload failed: ' + error.message,
                type: 'error',
                showClose: true,
              });
            }
          } else {
            ElMessage({
              message: 'Please fill in all fields and select files.',
              type: 'error',
              showClose: true,
            });
          }
        });
      }
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
    };
  },
});
</script>

<style scoped>
.el-header {
  background-color: #24292e;
  color: #ffffff;
  text-align: center;
  font-size: 24px;
  padding: 10px;
}

.el-main {
  display: flex;
  justify-content: left;
  align-items: center;
  background-color: #f6f8fa;
  padding: 10px;
  height: 100%;
}

.upload-form {
  background: #ffffff;
  border: 1px solid #e1e4e8;
  border-radius: 6px;
  padding: 60px;
  box-shadow: 0 1px 3px rgba(27, 31, 35, 0.12), 0 8px 24px rgba(27, 31, 35, 0.12);
  width: 100%;
  max-width: 800px;
}

.el-form-item {
  margin-bottom: 20px;
}

.el-form-item label {
  color: #586069;
}

.el-button {
  background-color: #2ea44f;
  border-color: #2ea44f;
  color: #ffffff;
}

.el-button:hover {
  background-color: #269f42;
  border-color: #269f42;
}

.el-upload__tip {
  color: #586069;
  font-size: 12px;
}
</style>