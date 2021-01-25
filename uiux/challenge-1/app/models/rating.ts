export enum Rating {
  ZERO = 0,
  ONE,
  TWO,
  THREE,
  FOUR,
  FIVE,
}

export function parseRatingFromBase(rating: number, base: number): Rating {
  return Math.floor((rating * 5) / base)
}
