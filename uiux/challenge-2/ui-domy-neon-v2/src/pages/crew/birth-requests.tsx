import React, { useCallback, useEffect, useMemo, useState } from 'react';
import { PageHeader, Button, Descriptions, Spin, Drawer } from 'antd';
import { useDispatch, useSelector } from 'react-redux';
import { AppState } from '../../store/root-reducer';

import { createBirthRequest, fetchBirthRequests, reviewBirthRequest } from './redux/saga';
import { BabyMakingRequest, BabyMakingRequestStatus } from '../../models/baby-making-request';
import Text from 'antd/lib/typography/Text';
import { BABY_REQUEST_STATUS_COLORS, JOURNEY_CURRENT_YEAR } from '../../utils/constants';

import RequestsList from './birth-requests-list';
import NewBirthRequestForm from './birth-request-form';
import { NewBirthRequest } from '../../services/api/models';

const CrewMembers: React.FC = () => {
  const requests = useSelector<AppState, BabyMakingRequest[]>(({ crew: { birthRequests } }) => birthRequests);
  const isLoading = useSelector<AppState, boolean>(({ loading: { crew } }) => crew);
  const [showAddForm, setShowAddForm] = useState(false);

  const pending = useMemo(() => (
    [...requests].filter(r => r.status === BabyMakingRequestStatus.Pending)
  ), [requests]);

  const approved = useMemo(() => (
    [...requests].filter(r => r.status === BabyMakingRequestStatus.Approved)
  ), [requests]);

  const rejected = useMemo(() => (
    [...requests].filter(r => r.status === BabyMakingRequestStatus.Rejected)
  ), [requests]);

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(fetchBirthRequests());
  }, [dispatch]);

  const onReview = useCallback((req: BabyMakingRequest, status: BabyMakingRequestStatus) => {
    dispatch(reviewBirthRequest({
      requestId: req.id,
      status
    }))
  }, [dispatch]);

  const onAddRequest = useCallback((request: NewBirthRequest) => {
    dispatch(createBirthRequest({
      request,
      callback: () => setShowAddForm(false)
    }));
  }, [dispatch]);

  return (
    <Spin spinning={isLoading} tip={"Fetching birth requests"} delay={500}>
      <PageHeader
        ghost={false}
        title="Birth Requests"
        extra={[
          <Button key="add-user" type="primary" onClick={() => setShowAddForm(true)}>
            Add a request
          </Button>,
        ]}
      >
        <Descriptions size="small" column={2}>
          <Descriptions.Item label="Total Requests">{requests.length}</Descriptions.Item>
          <Descriptions.Item
            label={<Text style={{ color: BABY_REQUEST_STATUS_COLORS.Pending }}>Pending Requests</Text>}
          >
            {pending.length}
          </Descriptions.Item>
          <Descriptions.Item 
            label={<Text style={{ color: BABY_REQUEST_STATUS_COLORS.Approved }}>Approved Requests</Text>}
          >
            {approved.length}
          </Descriptions.Item>
          <Descriptions.Item 
            label={<Text style={{ color: BABY_REQUEST_STATUS_COLORS.Rejected }}>Rejected Requests</Text>}
          >
            {rejected.length}
          </Descriptions.Item>
        </Descriptions>
      </PageHeader>
      <RequestsList
        requests={requests}
        onReview={onReview}
      />
      <Drawer
        visible={showAddForm}
        onClose={() => setShowAddForm(false)}
        title="New Birth Request"
        width={400}
      >
        <NewBirthRequestForm
          initialValues={{
            plannedForYear: JOURNEY_CURRENT_YEAR,
            notes: ''
          }}
          isProcessing={isLoading}
          onSubmit={onAddRequest}
        />
      </Drawer>
    </Spin>
  );
};


export default CrewMembers;