<template>
  <div>
    <ViewHead viewName="Pioneers Population Management" />
    <div class="container">
      <h3>POPULATION LEVEL: {{$store.state.pioneersPopulation.pioneers}}</h3>
      <h3>TOTAL BIRTHS: {{$store.state.pioneersPopulation.totalBirths}}</h3>
      <h3>TOTAL DEATHS: {{$store.state.pioneersPopulation.totalDeaths}}</h3>
      <hr>

      <div class="dz-births">
        <button class="dz-button">MY BIRTH AUTHORIZATIONS</button>
        <hr>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    manageMyBirthAuthorizations() {
    },
  },
  created() {
    this.$store.dispatch('GET_PIONEERS_POPULATION')
  }
}
</script>
