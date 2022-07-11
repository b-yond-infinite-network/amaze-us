import * as yup from 'yup';

const validationsForm = {
  driver: yup.object().required('Required'),
  bus: yup.object().required('Required'),
  start: yup.date().required('Required'),
  end: yup
    .date()
    .when('start', (start, schema) => start && schema.min(start))
    .required('Required')
};

export default validationsForm;
