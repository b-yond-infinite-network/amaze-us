import * as React from 'react';

import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableSortLabel from '@material-ui/core/TableSortLabel';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';

import IconButton from '@material-ui/core/IconButton';
import BackIcon from '@material-ui/icons/ArrowBack';
import CloseIcon from '@material-ui/icons/Close';

import Track from '../models/track';
import { Modal, Typography, Grid, TablePagination, withStyles } from '@material-ui/core';
import Empty from './empty';

const desc = (a: any, b: any, orderBy: string) => {
    if (b[orderBy] < a[orderBy]) {
        return -1;
    }
    if (b[orderBy] > a[orderBy]) {
        return 1;
    }
    return 0;
};

const getModalStyle = () => {
    const top = 40, left = 40;
    return {
        top: `${top}%`,
        left: `${left}%`,
        transform: `translate(-${top}%, -${left}%)`
    };
};

const getSorting = (order: 'asc' | 'desc', orderBy: string) => {
    return order === 'desc' ? (a: any, b: any) => desc(a, b, orderBy) : (a: any, b: any) => -desc(a, b, orderBy);
};

const stableSort = (array: any[], cmp: Function) => {
    const stabilizedThis = array.map((el, index) => [el, index]);
    stabilizedThis.sort((a, b) => {
        const order = cmp(a[0], b[0]);
        if (order !== 0) return order;
        return a[1] - b[1];
    });
    return stabilizedThis.map(el => el[0]);
};

const TrackTitleCell = withStyles({
    root: {
        fontSize: 16,
        fontWeight: 'bold',
        '&:active': {
            color: '#FFF'
        },
        '&:focus': {
            color: '#950740'
        },
        '&:hover': {
            color: '#FFF'
        },
        '&:visited': {
            color: '#FFF'
        }
    }
})(TableSortLabel);

const Lyrics = withStyles({
    root: {
        fontSize: 16
    }
})(Typography);

const GoBack = withStyles({
    root: {
        position: 'absolute',
        right: '48px',
        top: '12px'
    }
})(IconButton);

const CloseButton = withStyles({
    root: {
        position: 'absolute',
        right: '12px',
        top: '12px'
    }
})(IconButton);

const columns = [
    { id: 'name', numeric: false, disablePadding: true, label: 'Track name' },
    { id: 'length', numeric: true, disablePadding: false, label: 'length' }
];

let counter = 0;

class TracksHeader extends React.Component<{ order: 'asc' | 'desc', orderBy: string, onRequestSort: Function }> {
    createSortHandler = (property: any) => (event: React.MouseEvent<HTMLElement>) => {
        this.props.onRequestSort(event, property);
    };

    render() {
        const { order, orderBy } = this.props;

        return (
            <TableHead>
                <TableRow>
                    {
                        columns.map(column => {
                            return <TableCell key={column.id} numeric={column.numeric} padding={column.disablePadding ? 'none' : 'default'}>
                                <TrackTitleCell active={orderBy === column.id} direction={order} onClick={this.createSortHandler(column.id)}>
                                    <Typography variant='h6'>{column.label}</Typography>
                                </TrackTitleCell>
                            </TableCell>
                        })
                    }
                </TableRow>
            </TableHead>
        );
    }
}

export default class Tracks extends React.Component<{ tracks:Track[], closeModal: Function, open: boolean }> {
    sort: object;
    state:TrackState = {
        order: 'asc',
        orderBy: 'name',
        lyrics: null,
        page: 0,
        rowsPerPage: 10,
        showLyrics: false,
        trackName: ''
    };

    async showLyrics(track: Track) {
        return track.getLyric().then(lyrics => {
            const l = lyrics.replace(/[\r\n]/g, '<br>');
            this.setState({ lyrics: l, showLyrics: true, trackName: track.name });
        })
    }

    closeModal(e: Event) {
        e.stopPropagation();
        e.preventDefault();
        this.setState({ showLyrics: false });
        this.props.closeModal();
    }

    handleChangePage = (event: React.MouseEvent<HTMLButtonElement>, page: number) => {
        this.setState({ page });
    }
    
    handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLElement>) => {
        this.setState({ rowsPerPage: event.target.value });
    }

    handleRequestSort = (event: Event, property: string) => {
        const orderBy = property;
        let order = 'desc';

        if (this.state.orderBy === property && this.state.order === 'desc') {
            order = 'asc';
        }

        this.setState({ order, orderBy });
    }

    hideLyrics() {
        this.setState({ showLyrics: false });
    }

    render() {
        const tracks = this.props.tracks;
        const { order, orderBy, rowsPerPage, page } = this.state;
        const emptyRows = rowsPerPage - Math.min(rowsPerPage, tracks.length - page * rowsPerPage);

        return (
            <Modal open={this.props.open} onClose={this.closeModal.bind(this)}>
                <div style={getModalStyle()} className='track-modal'>
                    <Grid container>
                        <Grid item xs={10}>
                            <Typography variant='h6'>
                                {
                                    !this.state.showLyrics ? 'Tracks' : this.state['trackName']
                                }
                            </Typography>
                        </Grid>
                        <Grid item xs={1}>
                            {
                                this.state.showLyrics && (
                                    <GoBack onClick={this.hideLyrics.bind(this)}><BackIcon /></GoBack>
                                )
                            }
                            <CloseButton onClick={this.closeModal.bind(this)} aria-label='Close'>
                                <CloseIcon />
                            </CloseButton>
                        </Grid>
                    </Grid>
                    {
                        !this.state.showLyrics && this.props.tracks.length > 0 && (
                            <Grid item xs={12}>
                                <Table>
                                    <TracksHeader order={order} orderBy={orderBy} onRequestSort={this.handleRequestSort.bind(this)}></TracksHeader>
                                    <TableBody>
                                        {
                                            tracks.length > 0 && (
                                                stableSort(tracks, getSorting(order, orderBy))
                                                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                                .map(track => {
                                                    counter++;
                                                    return <TableRow key={counter} onClick={this.showLyrics.bind(this, track)}>
                                                        {
                                                        columns.map(column => {
                                                            return <TableCell key={`${column.id}-${counter}`} numeric={column.numeric}>
                                                                <Typography variant='h6'>{track[column.id]}</Typography>
                                                            </TableCell>
                                                        })
                                                        }
                                                    </TableRow>
                                                        
                                                    })
                                                    )
                                                }
                                        {
                                            emptyRows > 0 && (
                                                <TableRow style={{ height: 49 * emptyRows }}>
                                                    <TableCell colSpan={6} />
                                                </TableRow>
                                            )
                                        }
                                    </TableBody>
                                </Table>
                                <TablePagination
                                    component="div"
                                    count={tracks.length}
                                    rowsPerPage={rowsPerPage}
                                    rowsPerPageOptions={[0]}
                                    page={page}
                                    backIconButtonProps={{
                                        'aria-label': 'Previous Page',
                                    }}
                                    nextIconButtonProps={{
                                        'aria-label': 'Next Page',
                                    }}
                                    onChangePage={this.handleChangePage}
                                    onChangeRowsPerPage={this.handleChangeRowsPerPage}
                                    />  
                            </Grid>
                        )
                    }
                    {
                        this.state.showLyrics && (
                            <Lyrics variant="body1" dangerouslySetInnerHTML={{ __html: this.state.lyrics}} />
                        )
                    }
                    {
                        this.props.tracks.length < 1 && (
                            <Empty message="No tracks" />
                        )
                    }
                </div>
            </Modal>
        );
    }
};
