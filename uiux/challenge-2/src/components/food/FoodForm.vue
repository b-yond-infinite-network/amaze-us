<template>
  <form class="form food-form" @submit.prevent="save" v-if="show">
    <div class="form-group">
      <label for="" class="control-label">Select Food Type:</label>
      <select 
        @change="selectedFood = null"
        v-model="selectedFoodType" 
        class="form-control" required>
        <option value="null" disabled>Select one</option>
        <option v-for="(value, key) in foodLevels" 
          :value="key" 
          :key="key">{{key.toUpperCase()}}</option>
      </select>
    </div>
    <div class="form-group" 
      v-if="selectedFoodType">
      <label for="" class="control-label">Select Food:</label>
      <select v-model="selectedFood" class="form-control" required>
        <option value="null" disabled>Select one</option>
        <option v-for="(value, key) in foodLevels[selectedFoodType]" 
          :value="key" 
          :key="key">{{key.toUpperCase()}} /{{value}} kgs.</option>
      </select>
    </div>
    <div class="form-group" v-if="selectedFood">
      <label for="" class="control-label">Quantity:</label>
      <input type="number" 
        value="0"
        min="1"
        :max="selectedFood"
        class="form-control" 
        required
        v-model="quantity">
    </div>
    <div class="form-group">
      <button class="dz-button" type="submit">SAVE FOOD OUTPUT</button>
    </div>
  </form>
</template>

<script>
export default {
  props:['type', 'foodLevels', 'show'],
  data() {
    return {
      selectedFoodType:null,
      selectedFood:null,
      quantity:0,
    }
  },
  methods: {
    save() {
      this.$parent.$emit('updateShow')
      
      let levels = {...this.foodLevels};

      if(this.type === 'output') {
        levels[this.selectedFoodType][this.selectedFood] -= this.quantity
      } else {
        levels[this.selectedFoodType][this.selectedFood] -= -this.quantity
      }

      this.$store.dispatch('UPDATE_FOOD_LEVELS', levels)

      this.selectedFoodType = null
      this.selectedFood = null
      this.quantity = 0

      alert('Food Levels Updated!')
    },
  }
}
</script>

<style scoped lang="scss">
.food-form {
  margin-top:15px;
  max-width:90%;
}
</style>

