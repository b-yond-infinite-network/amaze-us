import * as React from 'react';
import * as ReactDOM from 'react-dom';
import CssBaseline from "@material-ui/core/CssBaseline";

import Header from './components/header';
import Search from './components/search';

import './assets/styles/main.scss';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core';

const colors = {
    title: '#C3073F',
    text: '#F44336',
    subTitle: '#950740'
}

const theme = createMuiTheme({
    typography: {
        useNextVariants: true,
        h1: {
            color: colors.title
        },
        h2: {
            color: colors.subTitle
        },
        h3: {
            color: colors.text
        },
        h4: {
            color: colors.text
        },
        h5: {
            color: colors.text
        },
        h6: {
            color: colors.text
        }
    },
    palette: {
        background: {

        },
        primary: {
            light: '#C3073F',
            main: '#4E4E50',
            dark: '#1A1A1D',
            contrastText: '#6F2232',
        },
        secondary: {
            light: '#45A29E',
            main: '#1D2833',
            dark: '#0B0C10',
            contrastText: '#66FCF1',
        },
        type: 'dark'
    }
});

ReactDOM.render(<div className="wrapper container-fluid">
    <MuiThemeProvider theme={theme}>
        <CssBaseline />
        <Header /> 
        <Search />
    </MuiThemeProvider>
</div>, document.getElementById('root'));
