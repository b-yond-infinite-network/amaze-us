import * as yup from 'yup';

const validationsForm = {
  capacity: yup.number().required('Required'),
  model: yup.string().required('Required'),
  make: yup.string().required('Required')
};

export default validationsForm;
