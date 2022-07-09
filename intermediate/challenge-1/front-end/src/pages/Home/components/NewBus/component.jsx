import React from 'react';
import { withFormik } from 'formik';
import * as yup from 'yup';
import { TextField, Button } from '@material-ui/core';

import validationsForm from './validationSchema';

const form = ({ values, touched, errors, isSubmitting, handleChange, handleBlur, handleSubmit, handleReset }) => {
  return (
    <form onSubmit={handleSubmit}>
      <TextField
        id="capacity"
        label="Capacity"
        value={values.capacity}
        onChange={handleChange}
        onBlur={handleBlur}
        helperText={touched.capacity ? errors.capacity : ''}
        error={touched.capacity && Boolean(errors.capacity)}
        margin="dense"
        variant="outlined"
        fullWidth
      />
      <TextField
        id="model"
        label="Model"
        value={values.model}
        onChange={handleChange}
        onBlur={handleBlur}
        helperText={touched.model ? errors.model : ''}
        error={touched.model && Boolean(errors.model)}
        margin="dense"
        variant="outlined"
        fullWidth
      />
      <TextField
        id="make"
        label="Make"
        type="make"
        value={values.make}
        onChange={handleChange}
        onBlur={handleBlur}
        helperText={touched.make ? errors.make : ''}
        error={touched.make && Boolean(errors.make)}
        margin="dense"
        variant="outlined"
        fullWidth
      />
      <Button type="submit" color="primary" disabled={isSubmitting}>
        Create bus
      </Button>
      <Button color="secondary" onClick={handleReset}>
        CLEAR
      </Button>
    </form>
  );
};

const NewBus = withFormik({
  mapPropsToValues: ({ capacity, model, make }) => {
    return {
      capacity: capacity || '',
      model: model || '',
      make: make || ''
    };
  },

  validationSchema: yup.object().shape(validationsForm),

  handleSubmit: (values, { resetForm, props: { createBus } }) => {
    (async () => {
      await createBus(values);
    })();
    resetForm();
  }
})(form);

export default NewBus;
