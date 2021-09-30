export enum BabyMakingRequestStatus {
  Pending = "Pending",
  Approved = "Approved",
  Rejected = "Rejected"
};

export interface BabyMakingRequest {
  id: string;
  requestedBy: string;
  requestedByName: string;
  requestedOn: string;
  plannedForYear: number;
  status: BabyMakingRequestStatus;
  reviewedOn?: string;
  reviewedBy?: string;
  reviewedByName?: string;
  notes?: string;
  reviewNotes?: string;
}