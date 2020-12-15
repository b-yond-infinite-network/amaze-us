declare namespace Express {
    export interface Request {
        recognition_number?: number;
        features?: any[];
    }
}