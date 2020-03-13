import { IArtist, ITrack } from "../../../shared";

type SortElementType = IArtist | ITrack;
export interface ISortFunction {
  [sortIdentifier: string]: (a: SortElementType, b: SortElementType) => number;
}

// Sort alphabetically A to Z
const sortAlphabeticallyAZ = (
  a: SortElementType,
  b: SortElementType
): number => {
  if (a.name == b.name) {
    return 0;
  } else if (a.name > b.name) {
    return 1;
  } else {
    return -1;
  }
};

// Sort alphabetically Z to A
const sortAlphabeticallyZA = (
  a: SortElementType,
  b: SortElementType
): number => {
  if (a.name == b.name) {
    return 0;
  } else if (a.name > b.name) {
    return -1;
  } else {
    return 1;
  }
};

// Sort rating in ascending order
const sortRatingAscending = (
  a: SortElementType,
  b: SortElementType
): number => {
  if (a.rating == b.rating) {
    return 0;
  } else if (a.rating > b.rating) {
    return 1;
  } else {
    return -1;
  }
};

// Sort rating in descending order
const sortRatingDescending = (
  a: SortElementType,
  b: SortElementType
): number => {
  if (a.rating == b.rating) {
    return 0;
  } else if (a.rating > b.rating) {
    return -1;
  } else {
    return 1;
  }
};

const SortFunctions: ISortFunction = {
  NAME_ALPHABETICAL_ASCENDING: sortAlphabeticallyAZ,
  NAME_ALPHABETICAL_DESCENDING: sortAlphabeticallyZA,
  RATING_NUMERICAL_ASCENDING: sortRatingAscending,
  RATING_NUMERICAL_DESCENDING: sortRatingDescending
};

export default SortFunctions;
