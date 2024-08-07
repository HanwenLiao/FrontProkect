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
      <el-card class="sdkCategoryChartCard">
        <div slot="header" class="clearfix">
          <span>SDK 种类分布</span>
        </div>
        <div ref="chart" class="chart"></div>
      </el-card>
      <el-card class="sdkBarChartCard">
        <div slot="header" class="clearfix">
          <span>SDK 分类条形图</span>
        </div>
        <div ref="barChart" class="barchart"></div>
      </el-card>
      <el-card class="sdkPermissionDistributionCard">
        <div slot="header" class="clearfix">
          <span>权限种类分布</span>
        </div>
        <div ref="permissionChart" class="permissionchart"></div>
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

const categories = [
  "生活服务", "安全", "媒体", "AI", "平台服务", "广告", "支付", "分析", "社交", 
  "金融理财", "工具", "账号登录", "游戏", "框架", "性能监控", "网络", "推送", 
  "地图定位", "存储", "电商服务", "AR/VR", "深链", "其他"
];

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
    const chart = ref<HTMLDivElement | null>(null);
    const barChart = ref<HTMLDivElement | null>(null);
    const permissionChart = ref<HTMLDivElement | null>(null);

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

    const fetchCategoryDistribution = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/sdk-stats/category-distribution');
        if (response.data && response.data.code === 200) {
          const data: ChartDataItem[] = Object.keys(response.data.data).map(key => ({
            name: key,
            value: response.data.data[key]
          }));
          renderPieChart(data);
        } else {
          console.error('Failed to fetch category distribution:', response.data.message);
        }
      } catch (error) {
        console.error('Error fetching category distribution:', error);
      }
    };

    const fetchBarChartData = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/sdk-stats/category-distribution');
        if (response.data && response.data.code === 200) {
          // Include all categories, even those with zero count
          const data: ChartDataItem[] = categories.map(category => ({
            name: category,
            value: response.data.data[category] || 0
          }));
          renderBarChart(data);
        } else {
          console.error('Failed to fetch bar chart data:', response.data.message);
        }
      } catch (error) {
        console.error('Error fetching bar chart data:', error);
      }
    };

    const fetchPermissionDistribution = async (category: string) => {
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
          renderPermissionChart(data);
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

    const renderPieChart = (data: ChartDataItem[]) => {
      if (chart.value) {
        const chartInstance = echarts.init(chart.value);
        const options = {
          tooltip: {
            trigger: 'item'
          },
          legend: {
            top: '5%',
            left: 'center'
          },
          series: [
            {
              name: 'SDK 种类',
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

    const renderBarChart = (data: ChartDataItem[]) => {
      if (barChart.value) {
        const chartInstance = echarts.init(barChart.value);
        const options = {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          xAxis: {
            type: 'category',
            data: data.map(item => item.name),
            axisLabel: {
              rotate: 90,
              interval: 0
            },
            axisTick: {
              alignWithLabel: true
            }
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              name: '数量',
              type: 'bar',
              barWidth: '60%',
              data: data.map(item => item.value),
              emphasis: {
                itemStyle: {
                  color: '#ff2d55'
                }
              }
            }
          ]
        };
        chartInstance.on('mouseover', params => {
          if (params.componentType === 'series') {
            const category = params.name;
            fetchPermissionDistribution(category);
          }
        });
        
        chartInstance.setOption(options);

      }
    };

    const renderPermissionChart = (data: ChartDataItem[]) => {
      if (permissionChart.value) {
        const chartInstance = echarts.init(permissionChart.value);
        const options = {
          tooltip: {
            trigger: 'item'
          },
          legend: {
            top: '5%',
            left: 'center'
          },
          series: [
            {
              name: '权限种类',
              type: 'pie',
              radius: ['40%', '70%'],
              avoidLabelOverlap:false,
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
onMounted(() => {
  fetchCounts();
  fetchCategoryDistribution();
  fetchBarChartData();
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
  chart,
  barChart,
  permissionChart
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

.permission-distribution-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
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

.el-card.sdkCategoryChartCard {
  position: fixed;
  top: 280px;
  left: 240px;
  width: 400px;
  height: 650px;
  color: #ffffff;
  background: linear-gradient(145deg, #313131, #353535);
  border-radius: 15px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border:#313131;
}

.el-card.sdkBarChartCard {
  position: fixed;
  top: 280px;
  left: 650px;
  width: 1220px;
  height: 650px;
  color: #ffffff;
  background: linear-gradient(145deg, #313131, #353535);
  border-radius: 15px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border:#313131;
}

.el-card.sdkPermissionDistributionCard {
  position: fixed;
  top: 280px;
  left: 50px;
  width: 120px;
  height: 650px;
  color: #ffffff;
  background: linear-gradient(145deg, #313131, #353535);
  border-radius: 15px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border:#313131;
}

.chart {
  width: 380px;
  height: 620px;
  color: rgb(255, 255, 255);
}

.barchart {
  width: 1200px;
  height: 620px;
  color: rgb(255, 255, 255);
}

.permission-chart {
  width: 1180px;
  height: 620px;
  color: rgb(255, 255, 255);
}
</style>
