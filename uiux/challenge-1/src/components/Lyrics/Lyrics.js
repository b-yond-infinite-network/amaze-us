import React, {PureComponent} from 'react';
import { connect } from 'react-redux';
import { Modal, Grid } from 'semantic-ui-react'
import { requestSong, requestLyric } from '../../reducers/songs'
import styled from 'styled-components'

const FooterNote = styled.span`
    display: block;
`

class Lyrics extends PureComponent{
    constructor (props){
        super(props);
        this.state = { 
            open: false
          };
    }
    

    open = () => {
        this.setState({ open: true })
    }
    close = () => {
        return this.setState({ open: false })
    };

    handleOpenLyric = trackID => {
        this.props.requestLyric(trackID);
        this.props.requestSong(trackID);
        this.setState({ open: true });
    }  

    componentDidMount() {
        this.props.onRef(this)
    }
    componentWillUnmount() {
        this.props.onRef(undefined)
    }

    renderLyric() {
        const { track, lyric } = this.props;
        const { open } = this.state;

        if(
            track === undefined ||
            lyric === undefined ||
            Object.keys(track).length === 0 ||
            Object.keys(lyric).length === 0 ||
            !open
            ) {
            return <React.Fragment></React.Fragment>
            
        } else {
            return (
                <Modal
                open={open}
                onOpen={this.open}
                onClose={this.close}
                size='fullscreen'
                closeIcon>
                    <Modal.Content>
                    <Modal.Description>
                        <Grid centered columns={1}>
                        <Grid.Row centered>
                                <h1>
                                    {track.track_name} by <span className="text-secondary">{track.artist_name}</span>
                                </h1>
                            </Grid.Row>
                            <Grid.Row centered>
                                <h2>
                                    {lyric.lyrics_body.split('\n').map((item, key) => {
                                        return <span key={key}>{item}<br/></span>
                                    })}
                                </h2>
                            </Grid.Row>
                            <Grid.Row centered>
                                    <p>
                                        <FooterNote><strong>Album</strong>: {track.album_name}</FooterNote>
                                        <FooterNote><strong>Song Genre</strong>: {typeof track.primary_genres.music_genre_list[0] != "undefined" ? track.primary_genres.music_genre_list[0].music_genre.music_genre_name : 'N/A'}</FooterNote>
                                    </p>
                            </Grid.Row>
                        </Grid>
                        
                    </Modal.Description>
                    </Modal.Content>
                </Modal>
            )
        };
    }
    
    
    render() {
        return  <div>{this.renderLyric()}</div>;
    };
};

const mapDispatchToProps = {
    requestLyric,
    requestSong
}


const mapStateToProps = (state) => {
    //get the data from redux store, like getMyState()
    const returningLyricState = state.songs.lyric.data !== undefined ? state.songs.lyric.data.message.body.lyrics : [];
    const returningTrackState = state.songs.track.data !== undefined ? state.songs.track.data.message.body.track : [];
    return { lyric: returningLyricState, track: returningTrackState };
};


export default connect(
    mapStateToProps, 
    {mapDispatchToProps, requestLyric, requestSong}    
)(Lyrics);
