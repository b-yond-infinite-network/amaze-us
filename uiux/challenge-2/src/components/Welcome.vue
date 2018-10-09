<template>
  <div id="zb-welcome">
    <h1 class="text-center mt-0">Welcome to {{$store.state.appName}} <small>(former Domy-Neon)</small></h1>
    <div id="zb-actions">
      <div class="zb-action__action-item" v-for="action in actions" :key="action.id">
        <router-link :to="action.link">
          <img class="zb-action__action-item__icon" :src="require(`@/assets/${action.icon}.png`)" :alt="action.label">
          <h4 class="zb-action__action-item__title">{{action.label}}</h4>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Welcome',
  data() {
    return {
      actions:[
        {
          id:1,
          label:'Manage Water levels',
          icon:'water',
          link:'/water'
        },
        {
          id:2,
          label:'Manage Food levels',
          icon:'food',
          link:'/food'
        },
        {
          id:3,
          label:'Manage Pioneers population',
          icon:'population',
          link:'/population'
        }
      ]
    }
  }
}
</script>

<style scoped lang="scss">
#zb-actions {
  text-align:center;
  display:flex;
  justify-content:space-around;
  flex-wrap:wrap;

  a {
    display:block;
  }

  .zb-action {
    &__action-item {
      background:#ccc;
      margin:0.5rem;
      padding:0.5rem;
      width:250px;
      cursor:pointer;

      &__icon {
        width:60px;
      }

      &__title {
        margin:0;
      }

      &:hover {
        box-shadow:0 0 5px 0px black;
      }
    }
  }
}
</style>
