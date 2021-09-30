import React from "react";
import { Menu, Dropdown, Avatar, Button, Row, Col, Typography } from "antd";

import {
  LogoutOutlined,
  UserOutlined,
} from '@ant-design/icons';
import { User } from "../models/user";

interface Props {
  user: User;
  onLogout: () => void;
}

const AppHeader: React.FC<Props> = (props: Props) => {
  const menu = (
    <Menu mode="inline">
      <Menu.Item key="signout" icon={<LogoutOutlined />} onClick={props.onLogout}>
        Signout
      </Menu.Item>
    </Menu>
  );

  return (
    <Row justify="end">
    <Col>
      <Dropdown overlay={menu} trigger={['click']} placement="bottomLeft">
        <Button type="text">
          <Avatar style={{ backgroundColor: '#87d068', marginRight: 8 }} icon={<UserOutlined />} /> 
          <Typography.Text style={{ color: 'rgba(255, 255, 255, 0.65)' }}>{props.user.firstName} {props.user.lastName}</Typography.Text>
        </Button>
      </Dropdown>
    </Col>
    </Row>
  );
};

export default AppHeader;