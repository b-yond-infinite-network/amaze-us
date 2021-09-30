import { BabyMakingRequestStatus } from "../models/baby-making-request";

export const ACCESS_TOKEN_STORAGE_KEY = 'usr_token';

export const APP_URLS = {
  DASHBOARD: '/dashboard',
  CREW: '/crew',
  CREW_MEMBERS: '/crew/members',
  BIRTH_REQUESTS: '/crew/birth-requests',
  WATER_SYSTEM: 'life-support/water-system',
  FOOD_LEVELS: '/food/levels',
  FOOD_PLANTS: '/food/plants',
  FOOD_SEEDS: '/food/seeds'
} as const;

export const BABY_REQUEST_STATUS_COLORS: Record<BabyMakingRequestStatus, string> = {
  [BabyMakingRequestStatus.Approved]: '#52c41a',
  [BabyMakingRequestStatus.Pending]: '#096dd9',
  [BabyMakingRequestStatus.Rejected]: '#ff4d4f',
};

export const JOURNEY_CURRENT_YEAR = 2548;
