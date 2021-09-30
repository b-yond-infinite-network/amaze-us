import { Spin } from 'antd';
import React from 'react';

interface Props extends React.PropsWithChildren<any> {
  loadingMessage?: string;
}

const LazySuspense: React.FC<Props> = (props: Props) => {
  return (
    <React.Suspense fallback={<Spin tip={props.loadingMessage || 'Loading...'} />}>
      {props.children}
    </React.Suspense>
  );
}

export default LazySuspense;
