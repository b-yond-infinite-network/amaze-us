import React, { useCallback } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Row, Col, Form, Input, Button } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';

import { login } from '../../store/sagas/auth';
import { LoginRequest } from '../../services/api/models';
import logo from '../../static/images/logo.svg';
import { AppState } from '../../store/root-reducer';

function LoginPage() {
  const dispatch = useDispatch();
  const onLogin = useCallback((loginRequest: LoginRequest) => {
    dispatch(login(loginRequest));
  }, [dispatch]);

  const isLoading = useSelector<AppState, boolean>(({ loading: { auth } }) => auth);

  return (
    <Row className="h-100" align="middle" justify="center">
      <Col sm={0} md={16} xl={18} className="h-100 login-bg"></Col>
      <Col sm={24} md={8} xl={6}>
        <div className="login-form-wrapper">
          <img src={logo} alt="Domy Neon" style={{ height: 92, marginBottom: '1.5rem' }} />
          <Form
            name="login"
            className="login-form"
            initialValues={{}}
            onFinish={onLogin}
          >
            <Form.Item
              name="username"
              rules={[{ required: true, message: 'Please input your Username!' }]}
            >
              <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Username" />
            </Form.Item>
            <Form.Item
              name="password"
              rules={[{ required: true, message: 'Please input your Password!' }]}
            >
              <Input
                prefix={<LockOutlined className="site-form-item-icon" />}
                type="password"
                placeholder="Password"
              />
            </Form.Item>
            
            <Form.Item>
              <Button
                type="primary"
                htmlType="submit"
                className="login-form-button"
                loading={isLoading}
                block
              >
                Log in
              </Button>
            </Form.Item>
          </Form>
        </div>
      </Col>
    </Row>
  )
}

export default LoginPage;