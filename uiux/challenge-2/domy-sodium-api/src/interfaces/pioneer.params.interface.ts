import Joi from "joi";

export const CheckUserPreRegistration = Joi.object({
    recognition_number: Joi.string().regex(/^[0-9a-zA-Z]+$/).required()
});

export const AddUserPreRegistration = Joi.object({
    first_name: Joi.string().required(),
    last_name: Joi.string().required(),
    birthdate: Joi.date().required(),
    recognition_number: Joi.string().regex(/^[0-9a-zA-Z]+$/).required()
});