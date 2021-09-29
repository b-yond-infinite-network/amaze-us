export enum BabyMakingRequestStatus {
  Pending = "Pending",
  Approved = "Approved",
  Rejected = "Rejected"
};

export interface BabyMakingRequest {
  requestedBy: string;
  requestedByName: string;
  requestedOn: Date;
  plannedForYear: number;
  status: BabyMakingRequestStatus;
  reviewedOn?: Date;
  reviewedBy?: string;
  reviewedByName?: string;
  notes?: string;
  reviewNotes?: string;
}