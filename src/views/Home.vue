<template>
  <div class="home">
    <div class="cards-container">
      <el-card class="sdkTotalCard" style="font-size: 45px; text-align: left;">
        <div slot="header" class="clearfix">
          收录SDK数量<br>
          <h2 class="animated-number" id="totalSdkCount">{{ animatedTotalSdkCount }}</h2> 个
        </div>
      </el-card>
      <el-card class="sdkRiskCard" style="font-size: 45px; text-align: left;">
        <div slot="header" class="clearfix">
          共检测出风险<br>
          <h2 class="animated-number" id="riskySdkCount">{{ animatedRiskySdkCount }}</h2> 次
        </div>
      </el-card>
      <el-card class="sdkApprovedCard" style="font-size: 45px; text-align: left;">
        <div slot="header" class="clearfix">
          审核通过数量<br>
          <h2 class="animated-number" id="approvedSdkCount">{{ animatedApprovedSdkCount }}</h2> 个
        </div>
      </el-card>
      <el-card class="sdkRejectedCard" style="font-size: 45px; text-align: left;">
        <div slot="header" class="clearfix">
          审核不通过数量<br>
          <h2 class="animated-number" id="rejectedSdkCount">{{ animatedRejectedSdkCount }}</h2> 个
        </div>
      </el-card>
    </div>
    <div class="chart-container">
      <el-card class="sdkSensitiveTableCard">
        <div slot="header" class="clearfix">
          <span>敏感权限统计</span>
        </div>
        <el-table :data="sdkSensitiveData" stripe @row-click="handleRowClick">
          <el-table-column prop="category" label="SDK种类" width="180"></el-table-column>
          <el-table-column prop="total" label="总数" width="100"></el-table-column>
          <el-table-column prop="risky" label="有风险" width="100"></el-table-column>
          <el-table-column prop="percentage" label="风险比例" width="100"></el-table-column>
          <el-table-column prop="mostSensitivePermission" label="最敏感权限分类" width="180"></el-table-column>
        </el-table>
      </el-card>
      <el-card class="sdkSensitiveChartCard">
        <div slot="header" class="clearfix">
          <span>敏感权限分布</span>
        </div>
        <div ref="sensitivePermissionChart" class="sensitivePermissionChart"></div>
      </el-card>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import axios from 'axios';
import * as echarts from 'echarts';
import anime from 'animejs';

interface ChartDataItem {
  name: string;
  value: number;
}

interface SensitiveDataItem {
  category: string;
  total: number;
  risky: number;
  percentage: string;
  mostSensitivePermission: string;
}

export default defineComponent({
  name: 'Home',
  setup() {
    const totalSdkCount = ref(0);
    const riskySdkCount = ref(0);
    const approvedSdkCount = ref(0);
    const rejectedSdkCount = ref(0);
    const animatedTotalSdkCount = ref(0);
    const animatedRiskySdkCount = ref(0);
    const animatedApprovedSdkCount = ref(0);
    const animatedRejectedSdkCount = ref(0);
    const sensitivePermissionChart = ref<HTMLDivElement | null>(null);
    const sdkSensitiveData = ref<SensitiveDataItem[]>([]);
    const defaultSensitiveData = ref<ChartDataItem[]>([]);

    const categoryMap: Record<string, number> = {
      "生活服务": 1,
      "安全": 2,
      "媒体": 3,
      "AI": 4,
      "平台服务": 5,
      "广告": 6,
      "支付": 7,
      "分析": 8,
      "社交": 9,
      "金融理财": 10,
      "工具": 11,
      "账号登录": 12,
      "游戏": 13,
      "框架": 14,
      "性能监控": 15,
      "网络": 16,
      "推送": 17,
      "地图定位": 18,
      "存储": 19,
      "电商服务": 20,
      "AR/VR": 21,
      "深链": 22,
      "其他": 23,
    };

    const fetchCounts = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/sdk-stats');
        if (response.data && response.data.code === 200) {
          totalSdkCount.value = response.data.data.totalSdkCount;
          riskySdkCount.value = response.data.data.riskySdkCount;
          approvedSdkCount.value = response.data.data.approvedSdkCount;
          rejectedSdkCount.value = response.data.data.rejectedSdkCount;
          animateNumbers();
        } else {
          console.error('Failed to fetch SDK counts:', response.data.message);
        }
      } catch (error) {
        console.error('Error fetching SDK counts:', error);
      }
    };

    const fetchSensitiveData = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/sdk-stats/sensitive-data');
        if (response.data && response.data.code === 200) {
          sdkSensitiveData.value = response.data.data.tableData;
          defaultSensitiveData.value = response.data.data.chartData;
          renderSensitivePermissionChart(defaultSensitiveData.value);
        } else {
          console.error('Failed to fetch sensitive data:', response.data.message);
        }
      } catch (error) {
        console.error('Error fetching sensitive data:', error);
      }
    };

    const fetchPermissionDistribution = async (category: number) => {
      try {
        const response = await axios.get('http://localhost:8080/api/sdk-stats/permission-distribution', {
          params: {
            category
          }
        });
        if (response.data && response.data.code === 200) {
          const data: ChartDataItem[] = Object.keys(response.data.data).map(key => ({
            name: key,
            value: response.data.data[key]
          }));
          renderSensitivePermissionChart(data);
        } else {
          console.error('Failed to fetch permission distribution:', response.data.message);
        }
      } catch (error) {
        console.error('Error fetching permission distribution:', error);
      }
    };

    const animateNumbers = () => {
      anime({
        targets: '#totalSdkCount',
        innerHTML: [0, totalSdkCount.value],
        easing: 'easeOutExpo',
        round: 1,
        duration: 2000
      });

      anime({
        targets: '#riskySdkCount',
        innerHTML: [0, riskySdkCount.value],
        easing: 'easeOutExpo',
        round: 1,
        duration: 2000
      });

      anime({
        targets: '#approvedSdkCount',
        innerHTML: [0, approvedSdkCount.value],
        easing: 'easeOutExpo',
        round: 1,
        duration: 2000
      });

      anime({
        targets: '#rejectedSdkCount',
        innerHTML: [0, rejectedSdkCount.value],
        easing: 'easeOutExpo',
        round: 1,
        duration: 2000
      });
    };

    const renderSensitivePermissionChart = (data: ChartDataItem[]) => {
      if (sensitivePermissionChart.value) {
        const chartInstance = echarts.init(sensitivePermissionChart.value);
        const options = {
          tooltip: {
            trigger: 'item'
          },
          legend: {
            orient: 'vertical',
            right: '10%',
            top: 'center',
            formatter: (name: string) => {
              const item = data.find(d => d.name === name);
              return item ? `${name}: ${item.value}%` : name;
            }
          },
          series: [
            {
              name: '敏感权限',
              type: 'pie',
              radius: ['40%', '70%'],
              avoidLabelOverlap: false,
              label: {
                show: false,
                position: 'center'
              },
              emphasis: {
                label: {
                  show: true,
                  fontSize: '30',
                  fontWeight: 'bold'
                }
              },
              labelLine: {
                show: false
              },
              data
            }
          ]
        };
        chartInstance.setOption(options);
      }
    };

    const handleRowClick = (row: SensitiveDataItem) => {
      const categoryNumber = categoryMap[row.category];
      fetchPermissionDistribution(categoryNumber);
    };

    const handleRowMouseOut = () => {
      renderSensitivePermissionChart(defaultSensitiveData.value);
    };

    onMounted(() => {
      fetchCounts();
      fetchSensitiveData();
    });

    return {
      totalSdkCount,
      riskySdkCount,
      approvedSdkCount,
      rejectedSdkCount,
      animatedTotalSdkCount,
      animatedRiskySdkCount,
      animatedApprovedSdkCount,
      animatedRejectedSdkCount,
      sensitivePermissionChart,
sdkSensitiveData,
handleRowClick,
handleRowMouseOut
};
},
});
</script>
<style scoped>
.home {
  text-align: center;
  padding: 20px;
}

.cards-container {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}

.chart-container {
  display: flex;
  justify-content: center;
  margin-top: 40px;
}

.sdkTotalCard {
  position: fixed;
  top: 70px;
  left: 240px;
  width: 400px;
  height: 200px;
  border-radius: 15px;
  color: #ffffff;
  font-size: 3em;
  background: linear-gradient(145deg, #313131, #202020);
  border: #313131;
}

.sdkRiskCard {
  position: fixed;
  top: 70px;
  left: 650px;
  width: 400px;
  height: 200px;
  border-radius: 15px;
  color: #ffffff;
  font-size: 3em;
  background: linear-gradient(145deg, #313131, #202020);
  border: #313131;
}

.sdkApprovedCard {
  position: fixed;
  top: 70px;
  left: 1060px;
  width: 400px;
  height: 200px;
  border-radius: 15px;
  color: #ffffff;
  font-size: 3em;
  background: linear-gradient(145deg, #313131, #202020);
  border: #313131;
}

.sdkRejectedCard {
  position: fixed;
  top: 70px;
  left: 1470px;
  width: 400px;
  height: 200px;
  border-radius: 15px;
  color: #ffffff;
  font-size: 3em;
  background: linear-gradient(145deg, #313131, #202020);
  border: #313131;
}

.animated-number {
  display: inline-block;
  font-size: 2em;
  color: #ff2d55;
}

.el-card.sdkSensitiveTableCard {
  position: fixed;
  top: 280px;
  left: 240px;
  width: 700px;
  height: 650px;
  color: #ffffff;
  background: linear-gradient(145deg, #313131, #353535);
  border-radius: 15px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border: #313131;
}

.el-card.sdkSensitiveChartCard {
  position: fixed;
  top: 280px;
  left: 950px;
  width: 800px;
  height: 650px;
  color: #ffffff;
  background: linear-gradient(145deg, #313131, #353535);
  border-radius: 15px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border: #313131;
}

.sensitivePermissionChart {
  width: 600px;
  height: 620px;
  color: rgb(255, 255, 255);
}
</style>

