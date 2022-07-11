import React from 'react';
import { withFormik } from 'formik';
import * as yup from 'yup';
import { TextField, Button } from '@material-ui/core';
import MenuItem from '@mui/material/MenuItem';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

import validationsForm from './validationSchema';

const form = ({
  values,
  touched,
  errors,
  isSubmitting,
  handleChange,
  handleBlur,
  handleSubmit,
  handleReset,
  setFieldValue,
  setFieldTouched,
  Driver: { drivers },
  Bus: { buses }
}) => {
  return (
    <form onSubmit={handleSubmit}>
      <TextField
        id="driver"
        select
        label="Driver"
        value={values.driver}
        onChange={handleChange('driver')}
        onBlur={handleBlur('driver')}
        margin="dense"
        variant="outlined"
        fullWidth
        helperText={touched.driver ? errors.driver : ''}
        error={touched.driver && Boolean(errors.driver)}
      >
        {drivers.map(driver => (
          <MenuItem key={driver.id} value={driver}>
            {driver.first_name + ' ' + driver.last_name}
          </MenuItem>
        ))}
      </TextField>
      <TextField
        id="bus"
        select
        label="Bus"
        value={values.bus}
        onChange={handleChange('bus')}
        onBlur={handleBlur('bus')}
        margin="dense"
        variant="outlined"
        fullWidth
        helperText={touched.bus ? errors.bus : ''}
        error={touched.bus && Boolean(errors.bus)}
      >
        {buses.map(bus => (
          <MenuItem key={bus.id} value={bus}>
            {bus.model}
          </MenuItem>
        ))}
      </TextField>
      <p>Start date</p>
      <DatePicker
        selected={values.start}
        dateFormat="MM/dd/yyyy  EE hh:mm a"
        showTimeSelect
        name="start"
        className="form-control"
        minDate={values.start}
        onChange={date => setFieldValue('start', date)}
      />
      <p>End date</p>
      <DatePicker
        selected={values.end}
        dateFormat="MM/dd/yyyy  EE hh:mm a"
        showTimeSelect
        name="end"
        className="form-control"
        minDate={values.start}
        onChange={date => setFieldValue('end', date)}
      />

      <Button type="submit" color="primary" disabled={isSubmitting}>
        Create schedule
      </Button>
      <Button color="secondary" onClick={handleReset}>
        CLEAR
      </Button>
    </form>
  );
};

const NewSchedule = withFormik({
  mapPropsToValues: ({ driver, bus, start, end }) => {
    return {
      driver: driver || '',
      bus: bus || '',
      start: start || '',
      end: end || ''
    };
  },

  validationSchema: yup.object().shape(validationsForm),

  handleSubmit: ({ driver, bus, start, end }, { resetForm, props: { createSchedule } }) => {
    (async () => {
      await createSchedule({
        driver_id: driver.id,
        bus_id: bus.id,
        start,
        end
      });
    })();
    resetForm();
  }
})(form);

export default NewSchedule;
