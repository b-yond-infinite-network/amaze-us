const waterLevels = [
  {
    id:1,
    name:'Prime',
    maxLevel:50000,
    currentLevel:3314,
  },
  {
    id:2,
    name:'The old man',
    maxLevel:50000,
    currentLevel:35699,
  },
  {
    id:3,
    name:'Supernova',
    maxLevel:50000,
    currentLevel:43030,
  },
]

const foodLevels = {
  meat: {
    cow:2321,
    chicken:6135,
    fish:2301,
  },
  vegetable: {
    potatoes:6498,
    tomatoes:7230,
    carrots:3123,
    lettuce:2312,
  },
  other: {
    rice:8703,
    pasta:3232,
    peanuts:5321,
  },
}

const population = {
  pioneers:2000,
  totalBirths:35,
  totalDeaths:52,
}

export default {
  waterLevels,
  foodLevels,
  population,
}
