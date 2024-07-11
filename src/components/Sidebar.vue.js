import { defineComponent, ref } from 'vue';
import { useRoute } from 'vue-router';
export default defineComponent({
    name: 'Sidebar',
    setup() {
        const route = useRoute();
        const activeMenu = ref(route.path);
        return {
            activeMenu,
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
    let __VLS_resolvedLocalAndGlobalComponents;
    // @ts-ignore
    const __VLS_0 = {}
        .ElMenu;
    ({}.ElMenu);
    ({}.ElMenu);
    __VLS_components.ElMenu;
    __VLS_components.elMenu;
    __VLS_components.ElMenu;
    __VLS_components.elMenu;
    // @ts-ignore
    [ElMenu, ElMenu,];
    // @ts-ignore
    const __VLS_1 = __VLS_asFunctionalComponent(__VLS_0, new __VLS_0({ defaultActive: ((__VLS_ctx.activeMenu)), router: (true), }));
    const __VLS_2 = __VLS_1({ defaultActive: ((__VLS_ctx.activeMenu)), router: (true), }, ...__VLS_functionalComponentArgsRest(__VLS_1));
    ({}({ defaultActive: ((__VLS_ctx.activeMenu)), router: (true), }));
    // @ts-ignore
    const __VLS_6 = {}
        .ElMenuItem;
    ({}.ElMenuItem);
    ({}.ElMenuItem);
    __VLS_components.ElMenuItem;
    __VLS_components.elMenuItem;
    __VLS_components.ElMenuItem;
    __VLS_components.elMenuItem;
    // @ts-ignore
    [ElMenuItem, ElMenuItem,];
    // @ts-ignore
    const __VLS_7 = __VLS_asFunctionalComponent(__VLS_6, new __VLS_6({ index: ("/"), }));
    const __VLS_8 = __VLS_7({ index: ("/"), }, ...__VLS_functionalComponentArgsRest(__VLS_7));
    ({}({ index: ("/"), }));
    // @ts-ignore
    [activeMenu,];
    (__VLS_11.slots).default;
    const __VLS_11 = __VLS_pickFunctionalComponentCtx(__VLS_6, __VLS_8);
    // @ts-ignore
    const __VLS_12 = {}
        .ElMenuItem;
    ({}.ElMenuItem);
    ({}.ElMenuItem);
    __VLS_components.ElMenuItem;
    __VLS_components.elMenuItem;
    __VLS_components.ElMenuItem;
    __VLS_components.elMenuItem;
    // @ts-ignore
    [ElMenuItem, ElMenuItem,];
    // @ts-ignore
    const __VLS_13 = __VLS_asFunctionalComponent(__VLS_12, new __VLS_12({ index: ("/upload-sdk"), }));
    const __VLS_14 = __VLS_13({ index: ("/upload-sdk"), }, ...__VLS_functionalComponentArgsRest(__VLS_13));
    ({}({ index: ("/upload-sdk"), }));
    (__VLS_17.slots).default;
    const __VLS_17 = __VLS_pickFunctionalComponentCtx(__VLS_12, __VLS_14);
    // @ts-ignore
    const __VLS_18 = {}
        .ElMenuItem;
    ({}.ElMenuItem);
    ({}.ElMenuItem);
    __VLS_components.ElMenuItem;
    __VLS_components.elMenuItem;
    __VLS_components.ElMenuItem;
    __VLS_components.elMenuItem;
    // @ts-ignore
    [ElMenuItem, ElMenuItem,];
    // @ts-ignore
    const __VLS_19 = __VLS_asFunctionalComponent(__VLS_18, new __VLS_18({ index: ("/tasks"), }));
    const __VLS_20 = __VLS_19({ index: ("/tasks"), }, ...__VLS_functionalComponentArgsRest(__VLS_19));
    ({}({ index: ("/tasks"), }));
    (__VLS_23.slots).default;
    const __VLS_23 = __VLS_pickFunctionalComponentCtx(__VLS_18, __VLS_20);
    // @ts-ignore
    const __VLS_24 = {}
        .ElMenuItem;
    ({}.ElMenuItem);
    ({}.ElMenuItem);
    __VLS_components.ElMenuItem;
    __VLS_components.elMenuItem;
    __VLS_components.ElMenuItem;
    __VLS_components.elMenuItem;
    // @ts-ignore
    [ElMenuItem, ElMenuItem,];
    // @ts-ignore
    const __VLS_25 = __VLS_asFunctionalComponent(__VLS_24, new __VLS_24({ index: ("/sdk-list"), }));
    const __VLS_26 = __VLS_25({ index: ("/sdk-list"), }, ...__VLS_functionalComponentArgsRest(__VLS_25));
    ({}({ index: ("/sdk-list"), }));
    (__VLS_29.slots).default;
    const __VLS_29 = __VLS_pickFunctionalComponentCtx(__VLS_24, __VLS_26);
    // @ts-ignore
    const __VLS_30 = {}
        .ElMenuItem;
    ({}.ElMenuItem);
    ({}.ElMenuItem);
    __VLS_components.ElMenuItem;
    __VLS_components.elMenuItem;
    __VLS_components.ElMenuItem;
    __VLS_components.elMenuItem;
    // @ts-ignore
    [ElMenuItem, ElMenuItem,];
    // @ts-ignore
    const __VLS_31 = __VLS_asFunctionalComponent(__VLS_30, new __VLS_30({ index: ("/permissions"), }));
    const __VLS_32 = __VLS_31({ index: ("/permissions"), }, ...__VLS_functionalComponentArgsRest(__VLS_31));
    ({}({ index: ("/permissions"), }));
    (__VLS_35.slots).default;
    const __VLS_35 = __VLS_pickFunctionalComponentCtx(__VLS_30, __VLS_32);
    (__VLS_5.slots).default;
    const __VLS_5 = __VLS_pickFunctionalComponentCtx(__VLS_0, __VLS_2);
    if (typeof __VLS_styleScopedClasses === 'object' && !Array.isArray(__VLS_styleScopedClasses)) {
    }
    var __VLS_slots;
    return __VLS_slots;
    const __VLS_componentsOption = {};
    const __VLS_name = 'Sidebar';
    let __VLS_internalComponent;
}
//# sourceMappingURL=Sidebar.vue.js.map