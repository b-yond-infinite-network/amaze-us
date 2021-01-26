export enum Rating {
  ZERO = 0,
  ONE,
  TWO,
  THREE,
  FOUR,
  FIVE,
}

export function parseRating(rating: number, base = 5): Rating {
  return Math.floor((rating * 5) / base)
}
