import * as React from 'react';

import Grid from '@material-ui/core/Grid';
import { Typography, withStyles } from '@material-ui/core';

const Title = withStyles({
    root: {
        textAlign: 'center',
        width: '100%'
    }
})(Typography);

export default class Header extends React.Component {
    render() {
        return (
            <Grid container>
                <Title variant='h1' color='secondary'>
                    Birthday-oke
                </Title>
            </Grid>
        );
    }
}
