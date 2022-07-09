import React from 'react';
import Table from 'components/Table';
import { headTitles } from './data';

const BusList = ({ Bus: { buses } }) => <Table data={buses} headTitles={headTitles} caption={'List of buses'} />;

export default BusList;
