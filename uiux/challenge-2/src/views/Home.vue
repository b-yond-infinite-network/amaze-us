<template>
  <div class="home container">
    <img class="block-center" alt="Vue logo" src="@/assets/logo.png">
    <Welcome/>
  </div>
</template>

<script>
import Welcome from '@/components/Welcome.vue'

export default {
  name: 'home',
  components: {
    Welcome
  }
}
</script>

<style scoped lang="scss">
.home {
  min-height:90vh;
  margin:auto;
  display:flex;
  flex-direction:column;
  justify-content:center;
  align-items:center;
  justify-content:center;
}
</style>
