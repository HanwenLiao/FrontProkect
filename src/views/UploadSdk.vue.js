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
            iconFile: null,
            harFile: null,
        });
        const formRef = ref(null);
        const rules = {
            sdkName: [{ required: true, message: 'Please input SDK Name', trigger: 'blur' }],
            category: [{ required: true, message: 'Please select a category', trigger: 'change' }],
            iconFile: [{ required: true, message: 'Please upload an icon file', trigger: 'change' }],
            harFile: [{ required: true, message: 'Please upload a HAR file', trigger: 'change' }],
        };
        const iconFileList = ref([]);
        const harFileList = ref([]);
        const handleIconUpload = (file) => {
            form.value.iconFile = file;
            return false;
        };
        const handleHarUpload = (file) => {
            form.value.harFile = file;
            return false;
        };
        const handleIconRemove = () => {
            form.value.iconFile = null;
        };
        const handleHarRemove = () => {
            form.value.harFile = null;
        };
        const submitForm = () => {
            if (formRef.value) {
                formRef.value.validate(async (valid) => {
                    if (valid) {
                        const formData = new FormData();
                        formData.append('sdkName', form.value.sdkName);
                        formData.append('category', form.value.category);
                        formData.append('icon', form.value.iconFile);
                        formData.append('har', form.value.harFile);
                        try {
                            const response = await axios.post('http://localhost:8080/api/upload', formData, {
                                headers: {
                                    'Content-Type': 'multipart/form-data',
                                },
                            });
                            if (response.data.status === 200) {
                                ElMessage.success('Upload successful');
                            }
                            else {
                                ElMessage.error('Upload failed: ' + response.data.message);
                            }
                        }
                        catch (error) {
                            ElMessage.error('Upload failed: ' + error.message);
                        }
                    }
                    else {
                        ElMessage.error('Please fill in all fields and select files.');
                    }
                });
            }
        };
        const handleMenuSelect = (index) => {
            if (index === 'home') {
                router.push('/');
            }
            else if (index === 'uploadSdk') {
                router.push('/upload-sdk');
            }
            else if (index === 'detectTask') {
                router.push('/detect-tasks');
            }
            else if (index === 'sdkList') {
                router.push('/sdk-list');
            }
            else if (index === 'permissionSettings') {
                router.push('/permission-settings');
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
            handleMenuSelect,
            activeMenu: ref('uploadSdk'),
        };
    },
});
;
function __VLS_template() {
    let __VLS_ctx;
    /* Components */
    let __VLS_otherComponents;
    let __VLS_own;
    let __VLS_localComponents;
    let __VLS_components;
    let __VLS_styleScopedClasses;
    // CSS variable injection 
    // CSS variable injection end 
    let __VLS_resolvedLocalAndGlobalComponents;
    // @ts-ignore
    const __VLS_0 = {}
        .ElContainer;
    ({}.ElContainer);
    ({}.ElContainer);
    __VLS_components.ElContainer;
    __VLS_components.elContainer;
    __VLS_components.ElContainer;
    __VLS_components.elContainer;
    // @ts-ignore
    [ElContainer, ElContainer,];
    // @ts-ignore
    const __VLS_1 = __VLS_asFunctionalComponent(__VLS_0, new __VLS_0({ ...{ style: ({}) }, }));
    const __VLS_2 = __VLS_1({ ...{ style: ({}) }, }, ...__VLS_functionalComponentArgsRest(__VLS_1));
    ({}({ ...{ style: ({}) }, }));
    // @ts-ignore
    const __VLS_6 = {}
        .ElContainer;
    ({}.ElContainer);
    ({}.ElContainer);
    __VLS_components.ElContainer;
    __VLS_components.elContainer;
    __VLS_components.ElContainer;
    __VLS_components.elContainer;
    // @ts-ignore
    [ElContainer, ElContainer,];
    // @ts-ignore
    const __VLS_7 = __VLS_asFunctionalComponent(__VLS_6, new __VLS_6({}));
    const __VLS_8 = __VLS_7({}, ...__VLS_functionalComponentArgsRest(__VLS_7));
    ({}({}));
    // @ts-ignore
    const __VLS_12 = {}
        .ElMain;
    ({}.ElMain);
    ({}.ElMain);
    __VLS_components.ElMain;
    __VLS_components.elMain;
    __VLS_components.ElMain;
    __VLS_components.elMain;
    // @ts-ignore
    [ElMain, ElMain,];
    // @ts-ignore
    const __VLS_13 = __VLS_asFunctionalComponent(__VLS_12, new __VLS_12({}));
    const __VLS_14 = __VLS_13({}, ...__VLS_functionalComponentArgsRest(__VLS_13));
    ({}({}));
    // @ts-ignore
    const __VLS_18 = {}
        .ElForm;
    ({}.ElForm);
    ({}.ElForm);
    __VLS_components.ElForm;
    __VLS_components.elForm;
    __VLS_components.ElForm;
    __VLS_components.elForm;
    // @ts-ignore
    [ElForm, ElForm,];
    // @ts-ignore
    const __VLS_19 = __VLS_asFunctionalComponent(__VLS_18, new __VLS_18({ model: ((__VLS_ctx.form)), rules: ((__VLS_ctx.rules)), ref: ("formRef"), labelWidth: ("120px"), }));
    const __VLS_20 = __VLS_19({ model: ((__VLS_ctx.form)), rules: ((__VLS_ctx.rules)), ref: ("formRef"), labelWidth: ("120px"), }, ...__VLS_functionalComponentArgsRest(__VLS_19));
    ({}({ model: ((__VLS_ctx.form)), rules: ((__VLS_ctx.rules)), ref: ("formRef"), labelWidth: ("120px"), }));
    // @ts-ignore
    (__VLS_ctx.formRef);
    // @ts-ignore
    const __VLS_24 = {}
        .ElFormItem;
    ({}.ElFormItem);
    ({}.ElFormItem);
    __VLS_components.ElFormItem;
    __VLS_components.elFormItem;
    __VLS_components.ElFormItem;
    __VLS_components.elFormItem;
    // @ts-ignore
    [ElFormItem, ElFormItem,];
    // @ts-ignore
    const __VLS_25 = __VLS_asFunctionalComponent(__VLS_24, new __VLS_24({ label: ("SDK Name"), prop: ("sdkName"), }));
    const __VLS_26 = __VLS_25({ label: ("SDK Name"), prop: ("sdkName"), }, ...__VLS_functionalComponentArgsRest(__VLS_25));
    ({}({ label: ("SDK Name"), prop: ("sdkName"), }));
    // @ts-ignore
    const __VLS_30 = {}
        .ElInput;
    ({}.ElInput);
    __VLS_components.ElInput;
    __VLS_components.elInput;
    // @ts-ignore
    [ElInput,];
    // @ts-ignore
    const __VLS_31 = __VLS_asFunctionalComponent(__VLS_30, new __VLS_30({ modelValue: ((__VLS_ctx.form.sdkName)), }));
    const __VLS_32 = __VLS_31({ modelValue: ((__VLS_ctx.form.sdkName)), }, ...__VLS_functionalComponentArgsRest(__VLS_31));
    ({}({ modelValue: ((__VLS_ctx.form.sdkName)), }));
    // @ts-ignore
    [form, form, rules, formRef,];
    const __VLS_35 = __VLS_pickFunctionalComponentCtx(__VLS_30, __VLS_32);
    (__VLS_29.slots).default;
    const __VLS_29 = __VLS_pickFunctionalComponentCtx(__VLS_24, __VLS_26);
    // @ts-ignore
    const __VLS_36 = {}
        .ElFormItem;
    ({}.ElFormItem);
    ({}.ElFormItem);
    __VLS_components.ElFormItem;
    __VLS_components.elFormItem;
    __VLS_components.ElFormItem;
    __VLS_components.elFormItem;
    // @ts-ignore
    [ElFormItem, ElFormItem,];
    // @ts-ignore
    const __VLS_37 = __VLS_asFunctionalComponent(__VLS_36, new __VLS_36({ label: ("Category"), prop: ("category"), }));
    const __VLS_38 = __VLS_37({ label: ("Category"), prop: ("category"), }, ...__VLS_functionalComponentArgsRest(__VLS_37));
    ({}({ label: ("Category"), prop: ("category"), }));
    // @ts-ignore
    const __VLS_42 = {}
        .ElSelect;
    ({}.ElSelect);
    ({}.ElSelect);
    __VLS_components.ElSelect;
    __VLS_components.elSelect;
    __VLS_components.ElSelect;
    __VLS_components.elSelect;
    // @ts-ignore
    [ElSelect, ElSelect,];
    // @ts-ignore
    const __VLS_43 = __VLS_asFunctionalComponent(__VLS_42, new __VLS_42({ modelValue: ((__VLS_ctx.form.category)), placeholder: ("Select Category"), }));
    const __VLS_44 = __VLS_43({ modelValue: ((__VLS_ctx.form.category)), placeholder: ("Select Category"), }, ...__VLS_functionalComponentArgsRest(__VLS_43));
    ({}({ modelValue: ((__VLS_ctx.form.category)), placeholder: ("Select Category"), }));
    // @ts-ignore
    const __VLS_48 = {}
        .ElOption;
    ({}.ElOption);
    __VLS_components.ElOption;
    __VLS_components.elOption;
    // @ts-ignore
    [ElOption,];
    // @ts-ignore
    const __VLS_49 = __VLS_asFunctionalComponent(__VLS_48, new __VLS_48({ label: ("生活服务"), value: ("生活服务"), }));
    const __VLS_50 = __VLS_49({ label: ("生活服务"), value: ("生活服务"), }, ...__VLS_functionalComponentArgsRest(__VLS_49));
    ({}({ label: ("生活服务"), value: ("生活服务"), }));
    // @ts-ignore
    [form,];
    const __VLS_53 = __VLS_pickFunctionalComponentCtx(__VLS_48, __VLS_50);
    // @ts-ignore
    const __VLS_54 = {}
        .ElOption;
    ({}.ElOption);
    __VLS_components.ElOption;
    __VLS_components.elOption;
    // @ts-ignore
    [ElOption,];
    // @ts-ignore
    const __VLS_55 = __VLS_asFunctionalComponent(__VLS_54, new __VLS_54({ label: ("安全"), value: ("安全"), }));
    const __VLS_56 = __VLS_55({ label: ("安全"), value: ("安全"), }, ...__VLS_functionalComponentArgsRest(__VLS_55));
    ({}({ label: ("安全"), value: ("安全"), }));
    const __VLS_59 = __VLS_pickFunctionalComponentCtx(__VLS_54, __VLS_56);
    (__VLS_47.slots).default;
    const __VLS_47 = __VLS_pickFunctionalComponentCtx(__VLS_42, __VLS_44);
    (__VLS_41.slots).default;
    const __VLS_41 = __VLS_pickFunctionalComponentCtx(__VLS_36, __VLS_38);
    // @ts-ignore
    const __VLS_60 = {}
        .ElFormItem;
    ({}.ElFormItem);
    ({}.ElFormItem);
    __VLS_components.ElFormItem;
    __VLS_components.elFormItem;
    __VLS_components.ElFormItem;
    __VLS_components.elFormItem;
    // @ts-ignore
    [ElFormItem, ElFormItem,];
    // @ts-ignore
    const __VLS_61 = __VLS_asFunctionalComponent(__VLS_60, new __VLS_60({ label: ("Icon File"), prop: ("iconFile"), }));
    const __VLS_62 = __VLS_61({ label: ("Icon File"), prop: ("iconFile"), }, ...__VLS_functionalComponentArgsRest(__VLS_61));
    ({}({ label: ("Icon File"), prop: ("iconFile"), }));
    // @ts-ignore
    const __VLS_66 = {}
        .ElUpload;
    ({}.ElUpload);
    ({}.ElUpload);
    __VLS_components.ElUpload;
    __VLS_components.elUpload;
    __VLS_components.ElUpload;
    __VLS_components.elUpload;
    // @ts-ignore
    [ElUpload, ElUpload,];
    // @ts-ignore
    const __VLS_67 = __VLS_asFunctionalComponent(__VLS_66, new __VLS_66({ action: (""), beforeUpload: ((__VLS_ctx.handleIconUpload)), fileList: ((__VLS_ctx.iconFileList)), onRemove: ((__VLS_ctx.handleIconRemove)), limit: ((1)), listType: ("picture"), }));
    const __VLS_68 = __VLS_67({ action: (""), beforeUpload: ((__VLS_ctx.handleIconUpload)), fileList: ((__VLS_ctx.iconFileList)), onRemove: ((__VLS_ctx.handleIconRemove)), limit: ((1)), listType: ("picture"), }, ...__VLS_functionalComponentArgsRest(__VLS_67));
    ({}({ action: (""), beforeUpload: ((__VLS_ctx.handleIconUpload)), fileList: ((__VLS_ctx.iconFileList)), onRemove: ((__VLS_ctx.handleIconRemove)), limit: ((1)), listType: ("picture"), }));
    // @ts-ignore
    const __VLS_72 = {}
        .ElButton;
    ({}.ElButton);
    ({}.ElButton);
    __VLS_components.ElButton;
    __VLS_components.elButton;
    __VLS_components.ElButton;
    __VLS_components.elButton;
    // @ts-ignore
    [ElButton, ElButton,];
    // @ts-ignore
    const __VLS_73 = __VLS_asFunctionalComponent(__VLS_72, new __VLS_72({ size: ("small"), type: ("primary"), }));
    const __VLS_74 = __VLS_73({ size: ("small"), type: ("primary"), }, ...__VLS_functionalComponentArgsRest(__VLS_73));
    ({}({ size: ("small"), type: ("primary"), }));
    // @ts-ignore
    [handleIconUpload, iconFileList, handleIconRemove,];
    (__VLS_77.slots).default;
    const __VLS_77 = __VLS_pickFunctionalComponentCtx(__VLS_72, __VLS_74);
    __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({ slot: ("tip"), ...{ class: ("el-upload__tip") }, });
    (__VLS_71.slots).default;
    const __VLS_71 = __VLS_pickFunctionalComponentCtx(__VLS_66, __VLS_68);
    (__VLS_65.slots).default;
    const __VLS_65 = __VLS_pickFunctionalComponentCtx(__VLS_60, __VLS_62);
    // @ts-ignore
    const __VLS_78 = {}
        .ElFormItem;
    ({}.ElFormItem);
    ({}.ElFormItem);
    __VLS_components.ElFormItem;
    __VLS_components.elFormItem;
    __VLS_components.ElFormItem;
    __VLS_components.elFormItem;
    // @ts-ignore
    [ElFormItem, ElFormItem,];
    // @ts-ignore
    const __VLS_79 = __VLS_asFunctionalComponent(__VLS_78, new __VLS_78({ label: ("HAR File"), prop: ("harFile"), }));
    const __VLS_80 = __VLS_79({ label: ("HAR File"), prop: ("harFile"), }, ...__VLS_functionalComponentArgsRest(__VLS_79));
    ({}({ label: ("HAR File"), prop: ("harFile"), }));
    // @ts-ignore
    const __VLS_84 = {}
        .ElUpload;
    ({}.ElUpload);
    ({}.ElUpload);
    __VLS_components.ElUpload;
    __VLS_components.elUpload;
    __VLS_components.ElUpload;
    __VLS_components.elUpload;
    // @ts-ignore
    [ElUpload, ElUpload,];
    // @ts-ignore
    const __VLS_85 = __VLS_asFunctionalComponent(__VLS_84, new __VLS_84({ action: (""), beforeUpload: ((__VLS_ctx.handleHarUpload)), fileList: ((__VLS_ctx.harFileList)), onRemove: ((__VLS_ctx.handleHarRemove)), limit: ((1)), listType: ("picture"), }));
    const __VLS_86 = __VLS_85({ action: (""), beforeUpload: ((__VLS_ctx.handleHarUpload)), fileList: ((__VLS_ctx.harFileList)), onRemove: ((__VLS_ctx.handleHarRemove)), limit: ((1)), listType: ("picture"), }, ...__VLS_functionalComponentArgsRest(__VLS_85));
    ({}({ action: (""), beforeUpload: ((__VLS_ctx.handleHarUpload)), fileList: ((__VLS_ctx.harFileList)), onRemove: ((__VLS_ctx.handleHarRemove)), limit: ((1)), listType: ("picture"), }));
    // @ts-ignore
    const __VLS_90 = {}
        .ElButton;
    ({}.ElButton);
    ({}.ElButton);
    __VLS_components.ElButton;
    __VLS_components.elButton;
    __VLS_components.ElButton;
    __VLS_components.elButton;
    // @ts-ignore
    [ElButton, ElButton,];
    // @ts-ignore
    const __VLS_91 = __VLS_asFunctionalComponent(__VLS_90, new __VLS_90({ size: ("small"), type: ("primary"), }));
    const __VLS_92 = __VLS_91({ size: ("small"), type: ("primary"), }, ...__VLS_functionalComponentArgsRest(__VLS_91));
    ({}({ size: ("small"), type: ("primary"), }));
    // @ts-ignore
    [handleHarUpload, harFileList, handleHarRemove,];
    (__VLS_95.slots).default;
    const __VLS_95 = __VLS_pickFunctionalComponentCtx(__VLS_90, __VLS_92);
    __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({ slot: ("tip"), ...{ class: ("el-upload__tip") }, });
    (__VLS_89.slots).default;
    const __VLS_89 = __VLS_pickFunctionalComponentCtx(__VLS_84, __VLS_86);
    (__VLS_83.slots).default;
    const __VLS_83 = __VLS_pickFunctionalComponentCtx(__VLS_78, __VLS_80);
    // @ts-ignore
    const __VLS_96 = {}
        .ElFormItem;
    ({}.ElFormItem);
    ({}.ElFormItem);
    __VLS_components.ElFormItem;
    __VLS_components.elFormItem;
    __VLS_components.ElFormItem;
    __VLS_components.elFormItem;
    // @ts-ignore
    [ElFormItem, ElFormItem,];
    // @ts-ignore
    const __VLS_97 = __VLS_asFunctionalComponent(__VLS_96, new __VLS_96({}));
    const __VLS_98 = __VLS_97({}, ...__VLS_functionalComponentArgsRest(__VLS_97));
    ({}({}));
    // @ts-ignore
    const __VLS_102 = {}
        .ElButton;
    ({}.ElButton);
    ({}.ElButton);
    __VLS_components.ElButton;
    __VLS_components.elButton;
    __VLS_components.ElButton;
    __VLS_components.elButton;
    // @ts-ignore
    [ElButton, ElButton,];
    // @ts-ignore
    const __VLS_103 = __VLS_asFunctionalComponent(__VLS_102, new __VLS_102({ ...{ 'onClick': {} }, type: ("primary"), }));
    const __VLS_104 = __VLS_103({ ...{ 'onClick': {} }, type: ("primary"), }, ...__VLS_functionalComponentArgsRest(__VLS_103));
    ({}({ ...{ 'onClick': {} }, type: ("primary"), }));
    let __VLS_108;
    const __VLS_109 = {
        onClick: (__VLS_ctx.submitForm)
    };
    // @ts-ignore
    [submitForm,];
    (__VLS_107.slots).default;
    const __VLS_107 = __VLS_pickFunctionalComponentCtx(__VLS_102, __VLS_104);
    let __VLS_105;
    let __VLS_106;
    (__VLS_101.slots).default;
    const __VLS_101 = __VLS_pickFunctionalComponentCtx(__VLS_96, __VLS_98);
    (__VLS_23.slots).default;
    const __VLS_23 = __VLS_pickFunctionalComponentCtx(__VLS_18, __VLS_20);
    (__VLS_17.slots).default;
    const __VLS_17 = __VLS_pickFunctionalComponentCtx(__VLS_12, __VLS_14);
    (__VLS_11.slots).default;
    const __VLS_11 = __VLS_pickFunctionalComponentCtx(__VLS_6, __VLS_8);
    (__VLS_5.slots).default;
    const __VLS_5 = __VLS_pickFunctionalComponentCtx(__VLS_0, __VLS_2);
    if (typeof __VLS_styleScopedClasses === 'object' && !Array.isArray(__VLS_styleScopedClasses)) {
        __VLS_styleScopedClasses['el-upload__tip'];
        __VLS_styleScopedClasses['el-upload__tip'];
    }
    var __VLS_slots;
    return __VLS_slots;
    const __VLS_componentsOption = {};
    const __VLS_name = 'UploadSdk';
    let __VLS_internalComponent;
}
//# sourceMappingURL=UploadSdk.vue.js.map