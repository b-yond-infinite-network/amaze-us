export class FormModel {
    
    name: string;
    email: string;
    description: string;

    public constructor(init?: Partial<FormModel>){
        Object.assign(this, init);
    }
}
