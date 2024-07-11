import axios from 'axios';

// 使用 Vite 的 import.meta.env 来获取环境变量
const instance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL as string || 'http://localhost:8080',
  timeout: 1000,
  headers: { 'Content-Type': 'application/json' },
});

export default instance;