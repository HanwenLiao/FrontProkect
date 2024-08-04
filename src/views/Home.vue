<!-- <template>
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
      <el-card class="sdkHeatmapCard">
        <div style="margin: 20px;">
          <el-switch v-model="sensitiveOnly" active-text="只统计敏感权限" inactive-text="统计所有权限" @change="fetchHeatmapData"></el-switch>
        </div>
        <div ref="heatmap" class="heatchart"></div>
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

interface HeatmapDataItem {
  [category: string]: {
    [permissionType: string]: number;
  };
}

const categories = [
  "生活服务", "安全", "媒体", "AI", "平台服务", "广告", "支付", "分析", "社交", 
  "金融理财", "工具", "账号登录", "游戏", "框架", "性能监控", "网络", "推送", 
  "地图定位", "存储", "电商服务", "AR/VR", "深链", "其他"
];

const permissionTypes = [
  "蓝牙", "网络", "音频", "通知", "电话", "能力", "存储", "系统", "下载", "事件", 
  "窗口", "硬件", "输入", "显示", "广播", "隐私", "媒体", "安全", "健康", "位置", 
  "相机", "日历", "账户", "无障碍", "麦克风", "联系人", "传感器", "NFC", "健身运动", 
  "分布式数据管理"
];

export default defineComponent({
  name: 'Home',
  setup() {
    const totalSdkCount = ref(0);
    const riskySdkCount = ref(0);
    const animatedTotalSdkCount = ref(0);
    const animatedRiskySdkCount = ref(0);
    const sensitiveOnly = ref(false);
    const chart = ref<HTMLDivElement | null>(null);
    const heatmap = ref<HTMLDivElement | null>(null);

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

    const fetchHeatmapData = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/sdk-stats/heatmap', {
          params: {
            sensitiveOnly: sensitiveOnly.value
          }
        });
        if (response.data && response.data.code === 200) {
          const data: HeatmapDataItem = response.data.data;
          
          // Initialize heatmapData with all possible coordinates
          const heatmapData: [number, number, number][] = [];
          categories.forEach((category, rowIndex) => {
            permissionTypes.forEach((permissionType, colIndex) => {
              const count = data[category]?.[permissionType] || 0;
              heatmapData.push([colIndex, rowIndex, count]);
            });
          });

          renderHeatmap(permissionTypes, categories, heatmapData);
        } else {
          console.error('Failed to fetch heatmap data:', response.data.message);
        }
      } catch (error) {
        console.error('Error fetching heatmap data:', error);
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

    const renderHeatmap = (xData: string[], yData: string[], heatmapData: [number, number, number][]) => {
      if (heatmap.value) {
        const chartInstance = echarts.init(heatmap.value);
        const options = {
          tooltip: {
            position: 'top'
          },
          grid: {
            height: '60%',
            top: '5%'
          },
          xAxis: {
            type: 'category',
            data: xData,
            splitArea: {
              show: true
            },
            axisLabel: {
              rotate: 90,
              interval: 0
            }
          },
          yAxis: {
            type: 'category',
            data: yData,
            splitArea: {
              show: true
            }
          },
          visualMap: {
            min: 0,
            max: Math.max(...heatmapData.map((item) => item[2])),
            calculable: true,
            orient: 'vertical',
            right: '5%',
            top: '10%',
            bottom: '10%',
            inRange: {
              color: ['#ebedf0', '#c6e48b', '#7bc96f', '#239a3b', '#196127'] // GitHub 热力图绿色
            }
          },
          series: [{
            name: '热力图',
            type: 'heatmap',
            data: heatmapData,
            label: {
              show: true
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }]
        };
        chartInstance.setOption(options);
      }
    };

    onMounted(() => {
      fetchCounts();
      fetchCategoryDistribution();
      fetchHeatmapData();
    });

    return {
      totalSdkCount,
      riskySdkCount,
      animatedTotalSdkCount,
      animatedRiskySdkCount,
      chart,
      heatmap,
      sensitiveOnly,
      fetchHeatmapData
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
  border:#313131
}

.el-card.sdkHeatmapCard {
  position: fixed;
  top: 280px;
  left: 650px;
  width: 1220px;
  height: 650px;
  color: #ffffff;
  background: linear-gradient(145deg, #313131, #353535);
  border-radius: 15px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border:#313131
}
.chart {
  width: 360px;
  height: 520px;
  color: rgb(255,255,255);
}
.heatchart{
  width: 1200px;
  height: 820px;
  margin-left: 0px;
}
.el-switch{
  position: fixed;
  top: 310px;
  left: 1600px;
}
</style> -->



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
      <el-card class="sdkHeatmapCard">
        <div style="margin: 20px;">
          <el-switch v-model="sensitiveOnly" active-text="只统计敏感权限" inactive-text="统计所有权限" @change="fetchHeatmapData"></el-switch>
        </div>
        <div ref="heatmap" class="heatchart"></div>
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

interface HeatmapDataItem {
  [category: string]: {
    [permissionType: string]: number;
  };
}

const categories = [
  "生活服务", "安全", "媒体", "AI", "平台服务", "广告", "支付", "分析", "社交", 
  "金融理财", "工具", "账号登录", "游戏", "框架", "性能监控", "网络", "推送", 
  "地图定位", "存储", "电商服务", "AR/VR", "深链", "其他"
];

const permissionTypes = [
  "蓝牙", "网络", "音频", "通知", "电话", "能力", "存储", "系统", "下载", "事件", 
  "窗口", "硬件", "输入", "显示", "广播", "隐私", "媒体", "安全", "健康", "位置", 
  "相机", "日历", "账户", "无障碍", "麦克风", "联系人", "传感器", "NFC", "健身运动", 
  "分布式数据管理"
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
    const sensitiveOnly = ref(false);
    const chart = ref<HTMLDivElement | null>(null);
    const heatmap = ref<HTMLDivElement | null>(null);

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
          renderChart(data);
        } else {
          console.error('Failed to fetch category distribution:', response.data.message);
        }
      } catch (error) {
        console.error('Error fetching category distribution:', error);
      }
    };

    const fetchHeatmapData = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/sdk-stats/heatmap', {
          params: {
            sensitiveOnly: sensitiveOnly.value
          }
        });
        if (response.data && response.data.code === 200) {
          const data: HeatmapDataItem = response.data.data;
          
          // Initialize heatmapData with all possible coordinates
          const heatmapData: [number, number, number][] = [];
          categories.forEach((category, rowIndex) => {
            permissionTypes.forEach((permissionType, colIndex) => {
              const count = data[category]?.[permissionType] || 0;
              heatmapData.push([colIndex, rowIndex, count]);
            });
          });

          renderHeatmap(permissionTypes, categories, heatmapData);
        } else {
          console.error('Failed to fetch heatmap data:', response.data.message);
        }
      } catch (error) {
        console.error('Error fetching heatmap data:', error);
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

    const renderHeatmap = (xData: string[], yData: string[], heatmapData: [number, number, number][]) => {
      if (heatmap.value) {
        const chartInstance = echarts.init(heatmap.value);
        const options = {
          tooltip: {
            position: 'top'
          },
          grid: {
            height: '60%',
            top: '5%'
          },
          xAxis: {
            type: 'category',
            data: xData,
            splitArea: {
              show: true
            },
            axisLabel: {
              rotate: 90,
              interval: 0
            }
          },
          yAxis: {
            type: 'category',
            data: yData,
            splitArea: {
              show: true
            }
          },
          visualMap: {
            min: 0,
            max: Math.max(...heatmapData.map((item) => item[2])),
            calculable: true,
            orient: 'vertical',
            right: '5%',
            top: '10%',
            bottom: '10%',
            inRange: {
              color: ['#ebedf0', '#c6e48b', '#7bc96f', '#239a3b', '#196127'] // GitHub 热力图绿色
            }
          },
          series: [{
            name: '热力图',
            type: 'heatmap',
            data: heatmapData,
            label: {
              show: true
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }]
        };
        chartInstance.setOption(options);
      }
    };

    onMounted(() => {
      fetchCounts();
      fetchCategoryDistribution();
      fetchHeatmapData();
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
      heatmap,
      sensitiveOnly,
      fetchHeatmapData
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

.el-card.sdkHeatmapCard {
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

.chart {
  width: 360px;
  height: 520px;
  color: rgb(255, 255, 255);
}

.heatchart {
  width: 1200px;
  height: 820px;
  margin-left: 0px;
}

.el-switch {
  position: fixed;
  top: 310px;
  left: 1600px;
}
</style>