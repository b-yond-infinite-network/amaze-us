<template>
  <div>
    <ViewHead viewName="Water Levels Management" />
    <div class="container">
      <h3>TOTAL WATER LEVEL: {{$store.getters.totalWaterlLevel}} lts.</h3>
      <h3>CURRENT WATER LEVEL: {{$store.getters.currentWaterLevel}} lts.</h3>
      <hr>
      <h3>WATER CONTAINERS:</h3>

      <div>
        <button class="dz-button" @click="showContainers = !showContainers">{{showContainers ? 'HIDE' : 'SHOW'}} CONTAINERS</button>

        <transition 
          enter-active-class="scale-up-ver-top" 
          leave-active-class="scale-down-ver-top" 
          >
          <ul v-if="showContainers">
            <li v-for="container in waterContainers" :key="container.id">
              <h4>NAME: <b>{{container.name}}</b></h4>
              <h4>Max Level: {{container.maxLevel}} lts.</h4>
              <h4>Current Level: {{container.currentLevel}} lts.</h4>
              <hr>
            </li>
          </ul>
        </transition>
      </div>
      <hr>

      <h3>ACTIONS:</h3>

      <button class="dz-button" @click="showOutputForm = true">LOAD WATER OUTPUT</button>

      <div id="water-levels-output">

        <form class="form" @submit.prevent="loadWaterOuput()" v-if="showOutputForm">
          <div class="form-group">
            <label for="" class="control-label">Container: </label>
            <select v-model="output.selectedContainer" class="form-control" required>
              <option value="0" disabled>Select Water Container</option>
              <option v-for="container in waterContainers" :value="container.id">{{container.name}}</option>
            </select>
          </div>
          <div class="form-group" v-if="output.selectedContainer > 0">
            <label for="">Output: </label>
            <input type="number" class="form-control" value="0" min="1" :max="maxOutput" v-model="output.selectedOutput" required/>
            <small> Max output: {{maxOutput}} lts.</small>
          </div>
          <div class="form-group">
            <button class="dz-button" type="submit">SAVE OUTPUT</button>
          </div>
        </form>

      </div>
    </div>

  </div>
</template>

<script>
export default {
  data() {
    return {
      showContainers:false,
      showOutputForm:false,
      output: {
        selectedContainer:0,
        selectedOutput:0,
      }
    }
  },
  computed: {
    waterContainers() {
      return this.$store.state.waterLevels
    },
    maxOutput() {
      const container = this.waterContainers.find(c => {
        return c.id === this.output.selectedContainer
      })

      return container.currentLevel
    }
  },
  methods: {
    loadWaterOuput() {
      this.showOutputForm = false

      this.$store.dispatch('UPDATE_WATER_LEVELS', this.output)

      this.output = {
        selectedContainer:0,
        selectedOutput:0,
      }

      alert('Water Levels Updated!')
    }
  },
  created() {
    this.$store.dispatch('GET_WATER_LEVELS')
  }
}
</script>

<style scoped lang="scss">
#water-levels-output {
  margin:30px 0px;
}
</style>

