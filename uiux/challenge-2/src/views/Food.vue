<template>
  <div>
    <ViewHead viewName="Food Levels Management" />
    <div class="container">
      <div>
        <h3>FOOD TOTALS:</h3>

        <ul class="food-levels-list">
          <li class="food-levels-list__food-type" v-for="(value, key) in foodLevels" :key="key">
            <h4 class="text-uppercase">{{key}}
              <ul>
                <li v-for="(quantity, food) in value" :key="food">
                  <h5 class="text-capitalize">{{food}}: <b>{{quantity}} Kgs.</b></h5>
                </li>
              </ul>
            </h4>
          </li>
        </ul>
        <hr>
      </div>

      <div>
        <h3>FOOD PRODUCTION FARM:</h3>
        <div class="food-farm">
          <div class="food-farm__section">
            <button class="dz-button" @click="showOutputForm = true">LOAD FOOD OUTPUT</button>

            <FoodForm type="output" 
              :show="showOutputForm"
              :foodLevels="foodLevels"/>
            <hr>
          </div>
          <div class="food-farm__section">
            <button class="dz-button" @click="showInputForm = true">LOAD FOOD INPUT</button>

            <FoodForm type="input" 
              :show="showInputForm"
              :foodLevels="foodLevels"/>
            <hr>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import FoodForm from '@/components/food/FoodForm'

export default {
  data() {
    return {
      showOutputForm:false,
      showInputForm:false,
    }
  },
  components: {
    FoodForm,
  },
  computed: {
    foodLevels() {
      return this.$store.state.foodLevels
    }
  },
  methods: {
    updateShow() {
      this.showOutputForm = false
      this.showInputForm = false
    }
  },
  created() {
    this.$on('updateShow', this.updateShow)
    this.$store.dispatch('GET_FOOD_LEVELS')
  }
}
</script>

<style scoped lang="scss">
.food-levels-list {
  display:flex;
  flex-wrap:wrap;

  .food-levels-list__food-type {
    margin-right:45px;

  }
}

.food-farm {
  display:flex;
  flex-wrap:wrap;
  margin:15px 0px;

  &__section {
    padding:10px;
    max-width:50%;
    min-width:300px;
    flex-grow:1;

    @media(min-width:630px) {
      &:first-child {
        border-right:1px solid #aaaaaa;
      }
    }
  }
}
</style>

