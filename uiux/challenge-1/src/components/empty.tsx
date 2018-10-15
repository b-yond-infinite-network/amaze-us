import * as React from 'react';
import { withStyles, Typography } from '@material-ui/core';

const EmptyMessage = withStyles({
    root: {
        textAlign: 'center',
        width: '100%'
    }
})(Typography);

export default class Empty extends React.Component<{ message: string }> {
    render() {
        return <EmptyMessage variant='h4'>{this.props.message}</EmptyMessage>
    }
}
