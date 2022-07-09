import React from 'react';
import Table from 'components/Table';
import { headTitles } from './data';

const DriverList = ({ Driver: { drivers } }) => (
  <Table data={drivers} headTitles={headTitles} caption={'List of drivers'} />
);

export default DriverList;
