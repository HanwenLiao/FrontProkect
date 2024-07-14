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
  sdkId: string;
  sdkName: string;
  pkgName: string;
  versionName: string;
  versionCode: number;
  categoryValue: string;
  iconLocation: string;
  size: string;
  sha256Code: string;
  updatetime: string;
}