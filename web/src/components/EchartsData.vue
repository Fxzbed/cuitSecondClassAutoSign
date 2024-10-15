<template>
  <e-charts class="chart" :option="option" />
</template>

<script setup>
import { ref } from "vue";

const option = ref({
  title: {
    text: "您的二课分数情况",
    x: "center",
  },
  tooltip: {
    trigger: "item",
    formatter: "{a} <br/>{b} : {c} ({d}%)",
  },
  toolbox: {
    show: true,
    feature: {
      saveAsImage: { show: true },
    },
  },
  calculable: true,
  series: [
    {
      name: "完成情况",
      type: "pie",
      radius: [80, 110],
      center: ["50%", 200],
      roseType: "radius",
      width: "100%", // for funnel
      max: 20, // for funnel
      sort: "ascending",
      data: [
        { value: 10, name: "思想政治与道德修养" },
        { value: 5, name: "学术科技与创新创业" },
        { value: 15, name: "艺术体验与审美修养" },
        { value: 25, name: "传统文化与人文素养" },
        { value: 20, name: "人际交往与沟通能力" },
        { value: 35, name: "心理素质与身体素质" },
        { value: 30, name: "社会实践与志愿服务" },
        { value: 40, name: "社会工作与能力拓展" },
      ],
    },
  ],
});
</script>

<style scoped>
.chart {
  height: 400px;
}
</style>
