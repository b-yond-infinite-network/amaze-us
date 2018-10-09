<template>
  <div class="view-head">
    <router-link to="/">
      <img class="view-head__logo" src="@/assets/logo.png" :alt="$store.state.appName">
    </router-link>
    <span>{{viewName}}</span>
  </div>
</template>

<script>
export default {
  props:['viewName']
}
</script>

<style scoped lang="scss">
.view-head {
  display:flex;
  align-items:center;
  border-bottom:1px solid black;

  &__logo {
    width:70px;
    margin-left:15px;
  }

  span {
    font-size:20px;
    margin-left:15px;
  }
}
</style>
