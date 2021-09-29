import React from 'react';
import { useSelector } from 'react-redux';
import { Redirect, Route } from 'react-router-dom';
import { AppState } from '../store/root-reducer';

interface Props {
  component: React.ComponentClass | React.FunctionComponent;
  [index: string]: any;
};

const PrivateRoute: React.FC<Props> = ({ component: RenderComponent, ...rest}) => {
  const isAuthenticated = useSelector<AppState, boolean | undefined>(state => state.user.isAuthenticated);

  return (
    <Route
    {...rest}
    render={(props: any) =>
      isAuthenticated
        ? <RenderComponent {...props} />
        : <Redirect
          to={{
            pathname: '/login',
            state: {from: props.location}
          }}
        />}
    />
  );
};

export default PrivateRoute;