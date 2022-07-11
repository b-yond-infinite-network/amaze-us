import * as yup from 'yup';

const validationsForm = {
  first_name: yup.string().required('Required'),
  last_name: yup.string().required('Required'),
  ssn: yup.string().required('Required'),
  email: yup.string().email('Enter a valid email').required('Email is required')
};

export default validationsForm;
