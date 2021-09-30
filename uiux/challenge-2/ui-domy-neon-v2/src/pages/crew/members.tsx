import React, { useEffect } from 'react';
import { PageHeader, Button, Descriptions, Spin } from 'antd';
import { useDispatch, useSelector } from 'react-redux';
import { AppState } from '../../store/root-reducer';
import { User } from '../../models/user';

import MembersList from './members-list';
import { fetchCrewMembers } from './redux/saga';

const CrewMembers: React.FC = () => {
  const crewMembers = useSelector<AppState, User[]>(({ crew: { members } }) => members);
  const isLoading = useSelector<AppState, boolean>(({ loading: { crew } }) => crew);
  
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(fetchCrewMembers());
  }, [dispatch]);

  return (
    <Spin spinning={isLoading} tip={"Fetching members"} delay={500}>
      <PageHeader
        ghost={false}
        title="Crew Members"
        extra={[
          <Button key="add-user" type="primary" disabled>
            Add a member
          </Button>,
        ]}
      >
        <Descriptions size="small" column={3}>
          <Descriptions.Item label="Total Population">{crewMembers.length}</Descriptions.Item>
        </Descriptions>
      </PageHeader>
      <MembersList members={crewMembers} />
    </Spin>
  );
};


export default CrewMembers;