import React, { Component } from 'react';
import ReactTable from 'react-table';
import { getLyrics } from '../redux/actions/artistsActions';
import { connect } from 'react-redux';

class ReactTableSimple extends Component {
    render(props) {
        const columns = [
            {
                Header: 'Track id',
                show: false,
                accessor: 'track.track_id'
            },
            {
                Header: 'Track title',
                accessor: 'track.track_name',
                Cell: props => <a onClick={ () => {
                    this.props.onGetLyrics(props.original.track.track_id);
                }}> { props.value }</a>
            },
            {
                Header: 'Duration (second)',
                accessor: 'track.track_length',
                // format track time from seconds
                Cell: props => {
                    var track_length_seconds = props.original.track.track_length;
                    var seconds = track_length_seconds % 60;
                    var minutes = Math.floor(track_length_seconds / 60);
            
                    if (minutes < 10)
                        minutes="0"+minutes
            
                    if (seconds < 10)
                        seconds="0"+seconds
            
                    return `${minutes}:${seconds}`;
                }
            }
        ];

        return (
            <ReactTable
                showPageSizeOptions={ false }
                showPagination={ false }
                noDataText="No rows found"
                style={{
                    width: "100%",
                    height: "50%"
                }}
                data={ this.props.songInfo }
                columns={ columns }
                minRows="0"
          />
        )
    }
}

const mapStateToProps = (state, props) => {
    return {
        songInfo: state.artists.songInfo,
    }
};
  
const mapActionsToProps = {
    onGetLyrics: getLyrics,
};

export default connect(mapStateToProps, mapActionsToProps)(ReactTableSimple);