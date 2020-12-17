import Joi from "joi";

export const CheckUserPreRegistration = Joi.object({
    recognition_number: Joi.string().regex(/^[0-9a-zA-Z]+$/).required()
});

export const AddPlantation = Joi.object({
    amount: Joi.number().required(),
    readyOnDays: Joi.number().required(),
    seedId: Joi.string().required(),
    seedName: Joi.string().required(),
});