import React from 'react';
import { withFormik } from 'formik';
import * as yup from 'yup';
import { TextField, Button } from '@material-ui/core';

import validationsForm from './validationSchema';

const form = ({ values, touched, errors, isSubmitting, handleChange, handleBlur, handleSubmit, handleReset }) => {
  return (
    <form onSubmit={handleSubmit}>
      <TextField
        id="first_name"
        label="First Name"
        value={values.first_name}
        onChange={handleChange}
        onBlur={handleBlur}
        helperText={touched.first_name ? errors.first_name : ''}
        error={touched.first_name && Boolean(errors.first_name)}
        margin="dense"
        variant="outlined"
        fullWidth
      />
      <TextField
        id="last_name"
        label="Last Name"
        value={values.last_name}
        onChange={handleChange}
        onBlur={handleBlur}
        helperText={touched.last_name ? errors.last_name : ''}
        error={touched.last_name && Boolean(errors.last_name)}
        margin="dense"
        variant="outlined"
        fullWidth
      />
      <TextField
        id="email"
        label="Email"
        type="email"
        value={values.email}
        onChange={handleChange}
        onBlur={handleBlur}
        helperText={touched.email ? errors.email : ''}
        error={touched.email && Boolean(errors.email)}
        margin="dense"
        variant="outlined"
        fullWidth
      />
      <Button type="submit" color="primary" disabled={isSubmitting}>
        Create driver
      </Button>
      <Button color="secondary" onClick={handleReset}>
        CLEAR
      </Button>
    </form>
  );
};

const NewDriver = withFormik({
  mapPropsToValues: ({ first_name, last_name, email }) => {
    return {
      first_name: first_name || '',
      last_name: last_name || '',
      email: email || ''
    };
  },

  validationSchema: yup.object().shape(validationsForm),

  handleSubmit: (values, { resetForm, props: { createDriver } }) => {
    (async () => {
      await createDriver(values);
    })();
    resetForm();
  }
})(form);

export default NewDriver;
