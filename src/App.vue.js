import { defineComponent, ref } from 'vue';
import { useRouter } from 'vue-router';
export default defineComponent({
    name: 'App',
    setup() {
        const router = useRouter();
        const activeMenu = ref('home');
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
            activeMenu.value = index;
        };
        return {
            activeMenu,
            handleMenuSelect,
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
        .ElHeader;
    ({}.ElHeader);
    ({}.ElHeader);
    __VLS_components.ElHeader;
    __VLS_components.elHeader;
    __VLS_components.ElHeader;
    __VLS_components.elHeader;
    // @ts-ignore
    [ElHeader, ElHeader,];
    // @ts-ignore
    const __VLS_7 = __VLS_asFunctionalComponent(__VLS_6, new __VLS_6({ ...{ class: ("header") }, }));
    const __VLS_8 = __VLS_7({ ...{ class: ("header") }, }, ...__VLS_functionalComponentArgsRest(__VLS_7));
    ({}({ ...{ class: ("header") }, }));
    __VLS_elementAsFunction(__VLS_intrinsicElements.h1, __VLS_intrinsicElements.h1)({});
    (__VLS_11.slots).default;
    const __VLS_11 = __VLS_pickFunctionalComponentCtx(__VLS_6, __VLS_8);
    // @ts-ignore
    const __VLS_12 = {}
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
    const __VLS_13 = __VLS_asFunctionalComponent(__VLS_12, new __VLS_12({}));
    const __VLS_14 = __VLS_13({}, ...__VLS_functionalComponentArgsRest(__VLS_13));
    ({}({}));
    // @ts-ignore
    const __VLS_18 = {}
        .ElAside;
    ({}.ElAside);
    ({}.ElAside);
    __VLS_components.ElAside;
    __VLS_components.elAside;
    __VLS_components.ElAside;
    __VLS_components.elAside;
    // @ts-ignore
    [ElAside, ElAside,];
    // @ts-ignore
    const __VLS_19 = __VLS_asFunctionalComponent(__VLS_18, new __VLS_18({ width: ("200px"), ...{ class: ("aside") }, }));
    const __VLS_20 = __VLS_19({ width: ("200px"), ...{ class: ("aside") }, }, ...__VLS_functionalComponentArgsRest(__VLS_19));
    ({}({ width: ("200px"), ...{ class: ("aside") }, }));
    // @ts-ignore
    const __VLS_24 = {}
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
    const __VLS_25 = __VLS_asFunctionalComponent(__VLS_24, new __VLS_24({ ...{ 'onSelect': {} }, defaultActive: ((__VLS_ctx.activeMenu)), ...{ class: ("el-menu-vertical") }, }));
    const __VLS_26 = __VLS_25({ ...{ 'onSelect': {} }, defaultActive: ((__VLS_ctx.activeMenu)), ...{ class: ("el-menu-vertical") }, }, ...__VLS_functionalComponentArgsRest(__VLS_25));
    ({}({ ...{ 'onSelect': {} }, defaultActive: ((__VLS_ctx.activeMenu)), ...{ class: ("el-menu-vertical") }, }));
    let __VLS_30;
    const __VLS_31 = {
        onSelect: (__VLS_ctx.handleMenuSelect)
    };
    // @ts-ignore
    const __VLS_32 = {}
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
    const __VLS_33 = __VLS_asFunctionalComponent(__VLS_32, new __VLS_32({ index: ("home"), }));
    const __VLS_34 = __VLS_33({ index: ("home"), }, ...__VLS_functionalComponentArgsRest(__VLS_33));
    ({}({ index: ("home"), }));
    // @ts-ignore
    [activeMenu, handleMenuSelect,];
    (__VLS_37.slots).default;
    const __VLS_37 = __VLS_pickFunctionalComponentCtx(__VLS_32, __VLS_34);
    // @ts-ignore
    const __VLS_38 = {}
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
    const __VLS_39 = __VLS_asFunctionalComponent(__VLS_38, new __VLS_38({ index: ("uploadSdk"), }));
    const __VLS_40 = __VLS_39({ index: ("uploadSdk"), }, ...__VLS_functionalComponentArgsRest(__VLS_39));
    ({}({ index: ("uploadSdk"), }));
    (__VLS_43.slots).default;
    const __VLS_43 = __VLS_pickFunctionalComponentCtx(__VLS_38, __VLS_40);
    // @ts-ignore
    const __VLS_44 = {}
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
    const __VLS_45 = __VLS_asFunctionalComponent(__VLS_44, new __VLS_44({ index: ("detectTask"), }));
    const __VLS_46 = __VLS_45({ index: ("detectTask"), }, ...__VLS_functionalComponentArgsRest(__VLS_45));
    ({}({ index: ("detectTask"), }));
    (__VLS_49.slots).default;
    const __VLS_49 = __VLS_pickFunctionalComponentCtx(__VLS_44, __VLS_46);
    // @ts-ignore
    const __VLS_50 = {}
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
    const __VLS_51 = __VLS_asFunctionalComponent(__VLS_50, new __VLS_50({ index: ("sdkList"), }));
    const __VLS_52 = __VLS_51({ index: ("sdkList"), }, ...__VLS_functionalComponentArgsRest(__VLS_51));
    ({}({ index: ("sdkList"), }));
    (__VLS_55.slots).default;
    const __VLS_55 = __VLS_pickFunctionalComponentCtx(__VLS_50, __VLS_52);
    // @ts-ignore
    const __VLS_56 = {}
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
    const __VLS_57 = __VLS_asFunctionalComponent(__VLS_56, new __VLS_56({ index: ("permissionSettings"), }));
    const __VLS_58 = __VLS_57({ index: ("permissionSettings"), }, ...__VLS_functionalComponentArgsRest(__VLS_57));
    ({}({ index: ("permissionSettings"), }));
    (__VLS_61.slots).default;
    const __VLS_61 = __VLS_pickFunctionalComponentCtx(__VLS_56, __VLS_58);
    (__VLS_29.slots).default;
    const __VLS_29 = __VLS_pickFunctionalComponentCtx(__VLS_24, __VLS_26);
    let __VLS_27;
    let __VLS_28;
    (__VLS_23.slots).default;
    const __VLS_23 = __VLS_pickFunctionalComponentCtx(__VLS_18, __VLS_20);
    // @ts-ignore
    const __VLS_62 = {}
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
    const __VLS_63 = __VLS_asFunctionalComponent(__VLS_62, new __VLS_62({ ...{ class: ("main-container") }, }));
    const __VLS_64 = __VLS_63({ ...{ class: ("main-container") }, }, ...__VLS_functionalComponentArgsRest(__VLS_63));
    ({}({ ...{ class: ("main-container") }, }));
    // @ts-ignore
    const __VLS_68 = {}
        .RouterView;
    ({}.RouterView);
    __VLS_components.RouterView;
    __VLS_components.routerView;
    // @ts-ignore
    [RouterView,];
    // @ts-ignore
    const __VLS_69 = __VLS_asFunctionalComponent(__VLS_68, new __VLS_68({}));
    const __VLS_70 = __VLS_69({}, ...__VLS_functionalComponentArgsRest(__VLS_69));
    ({}({}));
    const __VLS_73 = __VLS_pickFunctionalComponentCtx(__VLS_68, __VLS_70);
    (__VLS_67.slots).default;
    const __VLS_67 = __VLS_pickFunctionalComponentCtx(__VLS_62, __VLS_64);
    (__VLS_17.slots).default;
    const __VLS_17 = __VLS_pickFunctionalComponentCtx(__VLS_12, __VLS_14);
    (__VLS_5.slots).default;
    const __VLS_5 = __VLS_pickFunctionalComponentCtx(__VLS_0, __VLS_2);
    if (typeof __VLS_styleScopedClasses === 'object' && !Array.isArray(__VLS_styleScopedClasses)) {
        __VLS_styleScopedClasses['header'];
        __VLS_styleScopedClasses['aside'];
        __VLS_styleScopedClasses['el-menu-vertical'];
        __VLS_styleScopedClasses['main-container'];
    }
    var __VLS_slots;
    return __VLS_slots;
    const __VLS_componentsOption = {};
    const __VLS_name = 'App';
    let __VLS_internalComponent;
}
//# sourceMappingURL=App.vue.js.map