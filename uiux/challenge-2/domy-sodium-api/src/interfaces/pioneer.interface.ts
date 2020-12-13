import { PioneerStatus } from "../entity/Pioneer.entity";

export interface PioneerInterface {
    id?: number;
    password?: string;
    recognition_number?: string;
    first_name?: string;
    last_name?: string;
    status?: PioneerStatus;
    birthdate?: string;
    created_at?: string;
    updated_at?: string;
}