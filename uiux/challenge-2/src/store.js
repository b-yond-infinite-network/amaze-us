import Vue from 'vue'
import Vuex from 'vuex'

import mockedData from './mockedData'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    appName:'Domy ZorgBlug',
    waterLevels:[],
    foodLevels:{},
    pioneersPopulation:{},
    babyMakingAuthorizations:[],
  },
  mutations: {
    'SET_WATER_LEVELS'(state, payload) {
      state.waterLevels = payload
    },
    'SET_FOOD_LEVELS'(state, payload) {
      state.foodLevels = payload
    },
    'SET_PIONEERS_POPULATION'(state, payload) {
      state.pioneersPopulation = payload
    }

  },
  actions: {
    'GET_WATER_LEVELS'({ commit }) {
      //we should use some http client here like axios and get data from a real DB
      commit('SET_WATER_LEVELS', mockedData.waterLevels)
    },
    'GET_FOOD_LEVELS'({ commit }) {
      commit('SET_FOOD_LEVELS', mockedData.foodLevels)
    },
    'GET_PIONEERS_POPULATION'({ commit }) {
      commit('SET_PIONEERS_POPULATION', mockedData.population)
    },
    'UPDATE_WATER_LEVELS'({ state, commit }, output) {
      const container  = state.waterLevels.find(c => c.id === output.selectedContainer)

      container.currentLevel -= output.selectedOutput

      commit('SET_WATER_LEVELS', state.waterLevels)
    },
    'UPDATE_FOOD_LEVELS'({ commit }, levels) {
      commit('SET_FOOD_LEVELS', levels)
    },
  },
  getters: {
    totalWaterlLevel(state) {
      let totalLevel = 0

      state.waterLevels.forEach((waterContainer) => {
        totalLevel+=waterContainer.maxLevel
      })

      return totalLevel 
    },
    currentWaterLevel(state) {
      let level = 0

      state.waterLevels.forEach((waterContainer) => {
        level+=waterContainer.currentLevel
      })

      return level
    },
  }
})
