import React from 'react';
import { Table, TableColumnProps } from 'antd';
import { User } from '../../models/user';
import { nameOf } from '../../utils/types';
import { formatDateDefault } from '../../utils/date-time';

interface Props {
  members: User[];
};

const tableCols: TableColumnProps<User>[] = [{
  key: 'name',
  title: 'Name',
  dataIndex: 'name',
  render: (_value: any, user: User) => `${user.firstName} ${user.lastName}`,
},
{
  key: nameOf<User>('birthDate'),
  title: 'Birthdate',
  dataIndex: nameOf<User>('birthDate'),
  render: (dateStr: string) => formatDateDefault(new Date(dateStr))
},
{
  key: nameOf<User>('occupation'),
  title: 'Occupation',
  dataIndex: nameOf<User>('occupation'),
}];

const MembersList: React.FC<Props> = (props: Props) => {
  return (
    <Table
      columns={tableCols}
      dataSource={props.members}
      rowKey={nameOf<User>('id')}
    />
  );
};


export default MembersList;
