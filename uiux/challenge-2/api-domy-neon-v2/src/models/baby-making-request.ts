import { BaseEntity } from "./base-entity";

export enum BabyMakingRequestStatus {
  Pending = "Pending",
  Approved = "Approved",
  Rejected = "Rejected"
};

export class BabyMakingRequest extends BaseEntity {
  requestedBy: string;
  // denormalizing
  requestedByName: string;
  requestedOn: Date;
  plannedForYear: number;
  status: BabyMakingRequestStatus;
  reviewedOn?: Date;
  reviewedBy?: string;
  reviewedByName?: string;
  notes?: string;
  reviewNotes?: string;

  constructor(
    requestedBy: string,
    requestedByName: string,
    requestedOn: Date,
    plannedForYear: number,
    status: BabyMakingRequestStatus,
    id?: string,
  ) {
    super(id);
    this.requestedBy = requestedBy;
    this.requestedByName = requestedByName;
    this.requestedOn = requestedOn;
    this.plannedForYear = plannedForYear;
    this.status = status;
  }
}