import { BabyMakingRequest, BabyMakingRequestStatus } from "../../models/baby-making-request";
import { User } from "../../models/user";
import { sendGet, sendPost, sendPut } from "../../utils/api";
import { NewBirthRequest } from "./models";

export function fetchCrewMembers() : Promise<User[]> {
  return sendGet<User[]>('/api/users');
}

export function fetchBirthRequests() : Promise<BabyMakingRequest[]> {
  return sendGet<BabyMakingRequest[]>('/api/babyrequests');
}

export function createBirthRequests(req: NewBirthRequest) : Promise<BabyMakingRequest> {
  return sendPost<BabyMakingRequest>('/api/babyrequests', req);
}

export function reviewBirthRequest(requestId: string, status: BabyMakingRequestStatus, notes?: string) : Promise<void> {
  return sendPut<void>(`/api/babyrequests/${requestId}`, {
    status,
    notes
  });
}
