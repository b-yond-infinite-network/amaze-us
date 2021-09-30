import { Button, Form, Input, InputNumber } from 'antd';
import React from 'react';
import { NewBirthRequest } from '../../services/api/models';
import { JOURNEY_CURRENT_YEAR } from '../../utils/constants';
import { nameOf } from '../../utils/types';

interface Props {
  initialValues: NewBirthRequest;
  isProcessing: boolean;
  onSubmit: (newRequest: NewBirthRequest) => void;
};

const NewBirthRequestForm: React.FC<Props> = (props: Props) => {
  const {
    initialValues,
    isProcessing,
    onSubmit
  } = props;
  return (
    <Form
      name="new-birth-request"
      initialValues={initialValues}
      onFinish={onSubmit}
      labelCol={{ span: 8 }}
      wrapperCol={{ span: 16 }}
    >
      <Form.Item
        name={nameOf<NewBirthRequest>('plannedForYear')}
        label="Planed for year"
        rules={[{
          required: true,
          message: 'Please specify the year the birth is planned for'
        }]}
      >
        <InputNumber min={JOURNEY_CURRENT_YEAR} />
      </Form.Item>
      <Form.Item
        name={nameOf<NewBirthRequest>('notes')}
        label="Notes"
      >
        <Input.TextArea />
      </Form.Item>

      <Form.Item
        wrapperCol={{ span: 24 }}
      >
        <Button
          type="primary"
          htmlType="submit"
          loading={isProcessing}
          block
        >
          Save
        </Button>
      </Form.Item>
    </Form>
  );
}

export default NewBirthRequestForm;
