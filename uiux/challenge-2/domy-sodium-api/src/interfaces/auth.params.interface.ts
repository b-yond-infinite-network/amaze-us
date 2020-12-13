import Joi from "joi";

export const RegisterParams = Joi.object({
    recognition_number: Joi.string().regex(/^[0-9a-zA-Z]+$/).required(),
    password: Joi.string().min(5).max(16).required()
});

export const LoginParams = Joi.object({
    recognition_number: Joi.string().regex(/^[0-9a-zA-Z]+$/).required(),
    password: Joi.string().min(5).max(16).required()
});