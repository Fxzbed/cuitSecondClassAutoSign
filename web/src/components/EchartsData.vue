<template>
  <e-charts class="chart" :option="option" />
  <div>
    总分：{{ (parseFloat(data.get("scoreSD")) + parseFloat(data.get("scoreXS")) + parseFloat(data.get("scoreYS")) + parseFloat(data.get("scoreCT")) + parseFloat(data.get("scoreRJ")) + parseFloat(data.get("scoreXL")) + parseFloat(data.get("scoreSH")) + parseFloat(data.get("scoreSHG"))) }}
    <br>
    思想政治与道德修养：{{ data.get("scoreSD") }} &nbsp;&nbsp;&nbsp; <a v-if="parseFloat(data.get('scoreSD')) >= 12"> ✅ </a> <a v-if="parseFloat(data.get('scoreSD')) < 12"> ❌ 还需 {{ 12 - parseFloat(data.get("scoreSD")) }}</a>
    <br>
    学术科技与创新创业：{{ data.get("scoreXS") }} &nbsp;&nbsp;&nbsp; <a v-if="parseFloat(data.get('scoreXS')) >= 8"> ✅ </a> <a v-if="parseFloat(data.get('scoreXS')) < 8"> ❌ 还需 {{ 8 - parseFloat(data.get("scoreXS")) }}</a>
    <br>
    艺术体验与审美休养：{{ data.get("scoreYS") }}&nbsp;&nbsp;&nbsp; <a v-if="parseFloat(data.get('scoreYS')) >= 12"> ✅ </a> <a v-if="parseFloat(data.get('scoreYS')) < 12"> ❌ 还需 {{ 12 - parseFloat(data.get("scoreYS")) }}</a>
    <br>
    传统文化与人文素养：{{ data.get("scoreCT") }}&nbsp;&nbsp;&nbsp; <a v-if="parseFloat(data.get('scoreCT')) >= 8"> ✅ </a> <a v-if="parseFloat(data.get('scoreCT')) < 8"> ❌ 还需 {{ 8 - parseFloat(data.get("scoreCT")) }}</a>
    <br>
    人际交往与沟通能力：{{ data.get("scoreRJ") }} &nbsp;&nbsp;&nbsp; <a v-if="parseFloat(data.get('scoreRJ')) >= 8"> ✅ </a> <a v-if="parseFloat(data.get('scoreRJ')) < 8"> ❌ 还需 {{ 8 - parseFloat(data.get("scoreRJ")) }}</a>
    <br>
    心理素质与身体素质：{{ data.get("scoreXL") }}&nbsp;&nbsp;&nbsp; <a v-if="parseFloat(data.get('scoreXL')) >= 12"> ✅ </a> <a v-if="parseFloat(data.get('scoreXL')) < 12"> ❌ 还需 {{ 12 - parseFloat(data.get("scoreXL")) }}</a>
    <br>
    社会实践与志愿服务：{{ data.get("scoreSH") }}&nbsp;&nbsp;&nbsp; <a v-if="parseFloat(data.get('scoreSH')) >= 12"> ✅ </a> <a v-if="parseFloat(data.get('scoreSH')) < 12"> ❌ 还需 {{ 12 - parseFloat(data.get("scoreSH")) }}</a>
    <br>
    社会工作与能力拓展：{{ data.get("scoreSHG") }}&nbsp;&nbsp;&nbsp; <a v-if="parseFloat(data.get('scoreSHG')) >= 8"> ✅ </a> <a v-if="parseFloat(data.get('scoreSHG')) < 8"> ❌ 还需 {{ 8 - parseFloat(data.get("scoreSHG")) }}</a>
  </div>
</template>

<script setup>
import { ref } from "vue";
import store from "@/store";

const data = ref(new Map());

store.dispatch("getScore", {
  access_token: localStorage.getItem("access_token"),
  success(resp) {
    data.value.set("scoreCT", resp.scoreCT);
    data.value.set("scoreRJ", resp.scoreRJ);
    data.value.set("scoreSD", resp.scoreSD);
    data.value.set("scoreSH", resp.scoreSH);
    data.value.set("scoreSHG", resp.scoreSHG);
    data.value.set("scoreXL", resp.scoreXL);
    data.value.set("scoreXS", resp.scoreXS);
    data.value.set("scoreYS", resp.scoreYS);
  },
  error() {
    alert("获取分数失败！");
  }
})

console.log(data);

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
        { value: data.value.get("scoreSD"), name: "思想政治与道德修养" },
        { value: data.value.get("scoreXS"), name: "学术科技与创新创业" },
        { value: data.value.get("scoreYS"), name: "艺术体验与审美修养" },
        { value: data.value.get("scoreCT"), name: "传统文化与人文素养" },
        { value: data.value.get("scoreRJ"), name: "人际交往与沟通能力" },
        { value: data.value.get("scoreXL"), name: "心理素质与身体素质" },
        { value: data.value.get("scoreSH"), name: "社会实践与志愿服务" },
        { value: data.value.get("scoreSHG"), name: "社会工作与能力拓展" },
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
