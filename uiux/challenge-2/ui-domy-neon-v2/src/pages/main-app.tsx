import React, { useCallback, useMemo } from 'react';
import { Link, Switch, Route, Redirect } from 'react-router-dom';
import { Layout } from 'antd';

import SideNav from '../components/side-nav';
import logo from '../static/images/logo.svg';
import { useDispatch, useSelector } from 'react-redux';
import { AppState } from '../store/root-reducer';
import { User } from '../models/user';
import AppHeader from '../components/app-header';
import { logout } from '../store/sagas/auth';
import { buildAppNav } from '../utils/app-menu';

import CrewPageRoutes from './crew/routes';
import { APP_URLS } from '../utils/constants';

const { Header, Footer, Sider, Content } = Layout;

export default function MainApp() {
  const user = useSelector<AppState, User>(({ user }) => user.data!);
  const dispatch = useDispatch();
  const onLogout = useCallback(() => {
    dispatch(logout());
  }, [dispatch]);

  const appNav = useMemo(buildAppNav, []);

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Sider
        theme="dark"
        collapsible
      >
        <Link to="/">
          <img src={logo} alt="Domy Neon" className="sidebar-logo" />
        </Link>
        <SideNav
          selectedItemKey={'crew-members'}
          items={appNav}
        />
      </Sider>
      <Layout>
        <Header>
          <AppHeader user={user} onLogout={onLogout} />
        </Header>
        <Content style={{ padding: 24 }}>
          <Switch>
            <Route path={APP_URLS.CREW} component={CrewPageRoutes} />
            <Route exact path={"/"} render={() => <Redirect to={APP_URLS.CREW_MEMBERS} />} />
          </Switch>
        </Content>
        <Footer>
          Brought to you by the awesome Domy Neon Tech Team &copy; {new Date().getFullYear()}
        </Footer>
      </Layout>
    </Layout>
  );
}