export const padDatePartWithZero = (num: number) : string => {
  if (!Number.isInteger(num)) {
    throw new Error('The number must be an int');
  }
  
  if (num <= 0) {
    throw new Error('The number must be positive');
  }

  return num < 10 ? `0${num}` : `${num}`;
}

export function formatDateDefault(date: Date) {
  const month = date.getMonth() + 1;
  return `${date.getFullYear()}-${padDatePartWithZero(month)}-${padDatePartWithZero(date.getDate())}`;
}