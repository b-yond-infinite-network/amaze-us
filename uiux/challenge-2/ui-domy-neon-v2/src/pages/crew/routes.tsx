import React from 'react';
import { Route } from 'react-router-dom';

import { APP_URLS } from '../../utils/constants';
import LazySuspense from '../../components/suspense';

const CrewMembers = React.lazy(() => import('./members'));
const BirthRequests = React.lazy(() => import('./birth-requests'));

const CrewPageRoutes: React.FC = () => (
  <>
    <Route
      path={APP_URLS.CREW_MEMBERS}
      render={() => (
        <LazySuspense>
          <CrewMembers />
        </LazySuspense>
      )}
    />

    <Route
      path={APP_URLS.BIRTH_REQUESTS}
      render={() => (
        <LazySuspense>
          <BirthRequests />
        </LazySuspense>
      )}
    />
  </>
);

export default CrewPageRoutes;
