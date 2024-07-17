export interface Permission {
  permissionId: number;
  permissionName: string;
  isSensitive: number;
  permissionTypeValue: string | null;
  description: string;
}

export interface SdkLatestResp {
  detect_report_id: string;
  detect_task_id: string;
  sdk_name: string;
  pkg_name: string;
  version_name: string;
  sha256_code: string;
  report_export_time: string;
  task_start_time: string;
  permission_name_list: Permission[];
}

export interface SdkListResp {
  sdkName: string;
  pkgName: string;
  versionName: string;
  categoryValue: string;
  iconLocation: string;
  size: string;
  updatetime: number;
  auditStatus: number;
  detectStatus: number;
  sha256Code: string;
}

export interface SdkDetectTaskHistoryResp {
  id: string;
  sdkName: string;
  pkgName: string;
  versionName: string;
  taskStatus: number;
  startTime: string;
  endTime: string;
}