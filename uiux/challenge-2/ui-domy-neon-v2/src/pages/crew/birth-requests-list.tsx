import React from 'react';
import { Button, Space, Table, TableColumnProps, Typography } from 'antd';
import { nameOf } from '../../utils/types';
import { formatDateDefault } from '../../utils/date-time';
import { BabyMakingRequest as Request, BabyMakingRequestStatus } from '../../models/baby-making-request';
import { BABY_REQUEST_STATUS_COLORS } from '../../utils/constants';

type ReviewCallback = (req: Request, status: BabyMakingRequestStatus) => void;

interface Props {
  requests: Request[];
  onReview: ReviewCallback;
};

const tableCols: TableColumnProps<Request>[] = [{
  key: nameOf<Request>('requestedByName'),
  title: 'Requested by',
  dataIndex: nameOf<Request>('requestedByName'),
},
{
  key: nameOf<Request>('plannedForYear'),
  title: 'Planned for',
  dataIndex: nameOf<Request>('plannedForYear'),
},
{
  key: nameOf<Request>('status'),
  title: 'Status',
  dataIndex: nameOf<Request>('status'),
  render: (status: BabyMakingRequestStatus) => (
    <Typography.Text style={{ color: BABY_REQUEST_STATUS_COLORS[status] }}>{status}</Typography.Text>
  )
},
{
  key: nameOf<Request>('requestedOn'),
  title: 'Requested on',
  dataIndex: nameOf<Request>('requestedOn'),
  render: (_: any, req: Request) => formatDateDefault(new Date(req.requestedOn)),
},
{
  key: nameOf<Request>('reviewedOn'),
  title: 'Reviewed on',
  dataIndex: nameOf<Request>('reviewedOn'),
  render: (_: any, req: Request) => req.reviewedOn ? formatDateDefault(new Date(req.requestedOn)) : '--',
},
{
  key: nameOf<Request>('reviewedByName'),
  title: 'Reviewed by',
  dataIndex: nameOf<Request>('reviewedByName'),
},
{
  key: nameOf<Request>('notes'),
  title: 'Notes',
  dataIndex: 'notes'
}];

const RowActions: React.FC<{ request: Request, onReview: ReviewCallback }> = ({ request, onReview }) => {
  if (request.status !== BabyMakingRequestStatus.Pending) {
    return null;
  }

  return (
    <div className="visible-hover">
      <Space>
        <Button type="default" onClick={() => onReview(request, BabyMakingRequestStatus.Rejected)}>
          Reject
        </Button>
        <Button type="primary" onClick={() => onReview(request, BabyMakingRequestStatus.Approved)}>
          Approve
        </Button>
      </Space>
    </div>
  )
};

const MembersList: React.FC<Props> = (props: Props) => {
  return (
    <Table
      columns={[
        ...tableCols,
        {
          key: 'actions',
          dataIndex: 'actions',
          render: (_: any, req: Request) => (
            <RowActions request={req} onReview={props.onReview} />
          ),
        }
      ]}
      rowKey={nameOf<Request>('id')}
      dataSource={props.requests}
    />
  );
};


export default MembersList;
