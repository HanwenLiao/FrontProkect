<template>
  <div class="home">
    <h1>Welcome to SDK Center</h1>
    <div class="cards-container">
      <el-card class="sdkTotalCard">
        <div slot="header" class="clearfix">
          <span>共检测SDK</span>
        </div>
        <div>
          <h2 class="animated-number" id="totalSdkCount">{{ animatedTotalSdkCount }}</h2> 个
        </div>
      </el-card>
      <el-card class="sdkRiskCard">
        <div slot="header" class="clearfix">
          <span>检出风险SDK</span>
        </div>
        <div>
          <h2 class="animated-number" id="riskySdkCount">{{ animatedRiskySdkCount }}</h2> 个
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
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import axios from 'axios';
import * as echarts from 'echarts';

interface ChartDataItem {
  name: string;
  value: number;
}

export default defineComponent({
  name: 'Home',
  setup() {
    const totalSdkCount = ref(0);
    const riskySdkCount = ref(0);
    const animatedTotalSdkCount = ref(0);
    const animatedRiskySdkCount = ref(0);
    const chart = ref<HTMLDivElement | null>(null);

    const fetchCounts = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/sdk-stats');
        if (response.data && response.data.code === 200) {
          totalSdkCount.value = response.data.data.totalSdkCount;
          riskySdkCount.value = response.data.data.riskySdkCount;
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
          renderChart(data);
        } else {
          console.error('Failed to fetch category distribution:', response.data.message);
        }
      } catch (error) {
        console.error('Error fetching category distribution:', error);
      }
    };

    const animateNumbers = () => {
      // @ts-ignore
      anime({
        targets: '#totalSdkCount',
        innerHTML: [0, totalSdkCount.value],
        easing: 'easeOutExpo',
        round: 1,
        duration: 2000
      });

      // @ts-ignore
      anime({
        targets: '#riskySdkCount',
        innerHTML: [0, riskySdkCount.value],
        easing: 'easeOutExpo',
        round: 1,
        duration: 2000
      });
    };

    const renderChart = (data: ChartDataItem[]) => {
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

    onMounted(() => {
      fetchCounts();
      fetchCategoryDistribution();
    });

    return {
      totalSdkCount,
      riskySdkCount,
      animatedTotalSdkCount,
      animatedRiskySdkCount,
      chart
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

.sdkTotalCard,
.sdkRiskCard {
  width: 400px;
  height: 200px;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 3em;
  font-weight: bold;
  color: #007aff;
  background-color: #f7f7f7;
  border-radius: 15px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.sdkTotalCard {
  background: linear-gradient(145deg, #f7f7f7, #e6e6e6);
}

.sdkRiskCard {
  background: linear-gradient(145deg, #f7f7f7, #e6e6e6);
}

.animated-number {
  display: inline-block;
  font-size: 2em;
  color: #ff2d55;
}

.sdkCategoryChartCard {
  width: 800px;
  height: 400px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f7f7f7;
  border-radius: 15px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.chart {
  width: 100%;
  height: 100%;
}
</style>