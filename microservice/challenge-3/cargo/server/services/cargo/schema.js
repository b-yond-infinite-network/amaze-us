const Joi = require('@hapi/joi')

const cargo = Joi.object()
  .options({ abortEarly: false })
  .keys({
    text: Joi.string().required(),
    loaded: Joi.boolean()
  })
module.exports = {
  cargo
}
