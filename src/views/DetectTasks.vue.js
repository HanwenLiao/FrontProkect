import { defineComponent, ref, onMounted } from 'vue';
import axios from 'axios';
export default defineComponent({
    name: 'TaskList',
    setup() {
        const sdkTasks = ref([]);
        const totalTasks = ref(0);
        const pageSize = 15;
        const currentPage = ref(1);
        const fetchSdkTasks = async (page = 1) => {
            try {
                const response = await axios.get(`http://localhost:8080/api/detect-tasks/all?page=${page}&pageSize=${pageSize}`);
                if (response.data && response.data.code === 200 && response.data.data) {
                    sdkTasks.value = response.data.data;
                    totalTasks.value = response.data.total; // 获取总任务数
                }
                else {
                    sdkTasks.value = [];
                    totalTasks.value = 0;
                }
            }
            catch (error) {
                console.error('Failed to fetch SDK tasks:', error);
                sdkTasks.value = [];
                totalTasks.value = 0;
            }
        };
        const handlePageChange = (page) => {
            currentPage.value = page;
            fetchSdkTasks(page);
        };
        const formatTime = (row, column, cellValue) => {
            const date = new Date(cellValue);
            return date.toLocaleString();
        };
        onMounted(() => {
            fetchSdkTasks();
        });
        return {
            sdkTasks,
            totalTasks,
            pageSize,
            handlePageChange,
            formatTime,
        };
    }
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
    __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({ ...{ class: ("detect-tasks") }, });
    __VLS_elementAsFunction(__VLS_intrinsicElements.h1, __VLS_intrinsicElements.h1)({});
    // @ts-ignore
    const __VLS_0 = {}
        .ElTable;
    ({}.ElTable);
    ({}.ElTable);
    __VLS_components.ElTable;
    __VLS_components.elTable;
    __VLS_components.ElTable;
    __VLS_components.elTable;
    // @ts-ignore
    [ElTable, ElTable,];
    // @ts-ignore
    const __VLS_1 = __VLS_asFunctionalComponent(__VLS_0, new __VLS_0({ data: ((__VLS_ctx.sdkTasks)), ...{ class: ("custom-table") }, }));
    const __VLS_2 = __VLS_1({ data: ((__VLS_ctx.sdkTasks)), ...{ class: ("custom-table") }, }, ...__VLS_functionalComponentArgsRest(__VLS_1));
    ({}({ data: ((__VLS_ctx.sdkTasks)), ...{ class: ("custom-table") }, }));
    // @ts-ignore
    const __VLS_6 = {}
        .ElTableColumn;
    ({}.ElTableColumn);
    __VLS_components.ElTableColumn;
    __VLS_components.elTableColumn;
    // @ts-ignore
    [ElTableColumn,];
    // @ts-ignore
    const __VLS_7 = __VLS_asFunctionalComponent(__VLS_6, new __VLS_6({ prop: ("detectTaskId"), label: ("Task ID"), width: ("200"), }));
    const __VLS_8 = __VLS_7({ prop: ("detectTaskId"), label: ("Task ID"), width: ("200"), }, ...__VLS_functionalComponentArgsRest(__VLS_7));
    ({}({ prop: ("detectTaskId"), label: ("Task ID"), width: ("200"), }));
    // @ts-ignore
    [sdkTasks,];
    const __VLS_11 = __VLS_pickFunctionalComponentCtx(__VLS_6, __VLS_8);
    // @ts-ignore
    const __VLS_12 = {}
        .ElTableColumn;
    ({}.ElTableColumn);
    __VLS_components.ElTableColumn;
    __VLS_components.elTableColumn;
    // @ts-ignore
    [ElTableColumn,];
    // @ts-ignore
    const __VLS_13 = __VLS_asFunctionalComponent(__VLS_12, new __VLS_12({ prop: ("sdkName"), label: ("SDK Name"), width: ("180"), }));
    const __VLS_14 = __VLS_13({ prop: ("sdkName"), label: ("SDK Name"), width: ("180"), }, ...__VLS_functionalComponentArgsRest(__VLS_13));
    ({}({ prop: ("sdkName"), label: ("SDK Name"), width: ("180"), }));
    const __VLS_17 = __VLS_pickFunctionalComponentCtx(__VLS_12, __VLS_14);
    // @ts-ignore
    const __VLS_18 = {}
        .ElTableColumn;
    ({}.ElTableColumn);
    __VLS_components.ElTableColumn;
    __VLS_components.elTableColumn;
    // @ts-ignore
    [ElTableColumn,];
    // @ts-ignore
    const __VLS_19 = __VLS_asFunctionalComponent(__VLS_18, new __VLS_18({ prop: ("pkgName"), label: ("Package Name"), width: ("180"), }));
    const __VLS_20 = __VLS_19({ prop: ("pkgName"), label: ("Package Name"), width: ("180"), }, ...__VLS_functionalComponentArgsRest(__VLS_19));
    ({}({ prop: ("pkgName"), label: ("Package Name"), width: ("180"), }));
    const __VLS_23 = __VLS_pickFunctionalComponentCtx(__VLS_18, __VLS_20);
    // @ts-ignore
    const __VLS_24 = {}
        .ElTableColumn;
    ({}.ElTableColumn);
    __VLS_components.ElTableColumn;
    __VLS_components.elTableColumn;
    // @ts-ignore
    [ElTableColumn,];
    // @ts-ignore
    const __VLS_25 = __VLS_asFunctionalComponent(__VLS_24, new __VLS_24({ prop: ("startTime"), label: ("Start Time"), width: ("180"), formatter: ((__VLS_ctx.formatTime)), }));
    const __VLS_26 = __VLS_25({ prop: ("startTime"), label: ("Start Time"), width: ("180"), formatter: ((__VLS_ctx.formatTime)), }, ...__VLS_functionalComponentArgsRest(__VLS_25));
    ({}({ prop: ("startTime"), label: ("Start Time"), width: ("180"), formatter: ((__VLS_ctx.formatTime)), }));
    // @ts-ignore
    [formatTime,];
    const __VLS_29 = __VLS_pickFunctionalComponentCtx(__VLS_24, __VLS_26);
    // @ts-ignore
    const __VLS_30 = {}
        .ElTableColumn;
    ({}.ElTableColumn);
    __VLS_components.ElTableColumn;
    __VLS_components.elTableColumn;
    // @ts-ignore
    [ElTableColumn,];
    // @ts-ignore
    const __VLS_31 = __VLS_asFunctionalComponent(__VLS_30, new __VLS_30({ prop: ("endTime"), label: ("End Time"), width: ("180"), formatter: ((__VLS_ctx.formatTime)), }));
    const __VLS_32 = __VLS_31({ prop: ("endTime"), label: ("End Time"), width: ("180"), formatter: ((__VLS_ctx.formatTime)), }, ...__VLS_functionalComponentArgsRest(__VLS_31));
    ({}({ prop: ("endTime"), label: ("End Time"), width: ("180"), formatter: ((__VLS_ctx.formatTime)), }));
    // @ts-ignore
    [formatTime,];
    const __VLS_35 = __VLS_pickFunctionalComponentCtx(__VLS_30, __VLS_32);
    (__VLS_5.slots).default;
    const __VLS_5 = __VLS_pickFunctionalComponentCtx(__VLS_0, __VLS_2);
    // @ts-ignore
    const __VLS_36 = {}
        .ElPagination;
    ({}.ElPagination);
    ({}.ElPagination);
    __VLS_components.ElPagination;
    __VLS_components.elPagination;
    __VLS_components.ElPagination;
    __VLS_components.elPagination;
    // @ts-ignore
    [ElPagination, ElPagination,];
    // @ts-ignore
    const __VLS_37 = __VLS_asFunctionalComponent(__VLS_36, new __VLS_36({ ...{ 'onCurrentChange': {} }, background: (true), layout: ("prev, pager, next"), total: ((__VLS_ctx.totalTasks)), pageSize: ((__VLS_ctx.pageSize)), ...{ class: ("pagination") }, }));
    const __VLS_38 = __VLS_37({ ...{ 'onCurrentChange': {} }, background: (true), layout: ("prev, pager, next"), total: ((__VLS_ctx.totalTasks)), pageSize: ((__VLS_ctx.pageSize)), ...{ class: ("pagination") }, }, ...__VLS_functionalComponentArgsRest(__VLS_37));
    ({}({ ...{ 'onCurrentChange': {} }, background: (true), layout: ("prev, pager, next"), total: ((__VLS_ctx.totalTasks)), pageSize: ((__VLS_ctx.pageSize)), ...{ class: ("pagination") }, }));
    let __VLS_42;
    const __VLS_43 = {
        onCurrentChange: (__VLS_ctx.handlePageChange)
    };
    // @ts-ignore
    [totalTasks, pageSize, handlePageChange,];
    const __VLS_41 = __VLS_pickFunctionalComponentCtx(__VLS_36, __VLS_38);
    let __VLS_39;
    let __VLS_40;
    if (typeof __VLS_styleScopedClasses === 'object' && !Array.isArray(__VLS_styleScopedClasses)) {
        __VLS_styleScopedClasses['detect-tasks'];
        __VLS_styleScopedClasses['custom-table'];
        __VLS_styleScopedClasses['pagination'];
    }
    var __VLS_slots;
    return __VLS_slots;
    const __VLS_componentsOption = {};
    const __VLS_name = 'TaskList';
    let __VLS_internalComponent;
}
//# sourceMappingURL=DetectTasks.vue.js.map