import React, {PureComponent} from 'react';
import { connect } from 'react-redux';
import { List, Icon, Button } from 'semantic-ui-react';

import { selectSong, requestSongList, requestLyric, requestSong } from '../../reducers/songs'


class SongList extends PureComponent{
    constructor (props){
        super(props);
        this.state = {
            selectedArtist: ''
        }
    }


    renderList() {
        const { songs, handleTrackSelection} = this.props;
        const buttonColors = ['red','grey', 'brown', 'pink', 'purple', 'violet', 'blue', 'teal', 'green', 'olive', 'yellow', 'orange'];
        return songs.map(song => {
                return (
                    <List.Item 
                        key={song.track.track_id} 
                        >
                        <List.Content>
                            <Button 
                                animated='fade'
                                fluid
                                inverted 
                                color={buttonColors[Math.floor(Math.random()*buttonColors.length)]}
                                size='huge'
                                onClick={() => handleTrackSelection(song.track.track_id)}
                                >
                                <Button.Content visible>{song.track.track_name}</Button.Content>
                                <Button.Content hidden ><Icon inverted name='microphone' />{song.track.track_name}</Button.Content>
                            </Button>
                        </List.Content>
                    </List.Item>
                );
            });
    }
    
    loadData(){
        const artist = this.props.match.params.id;
        if (this.state.selectedArtist !== artist){
            this.setState({ selectedArtist: artist });
            (this.props.requestSongList(artist))
        }
    }
    componentDidMount() {
        this.props.onRef(this);
        this.loadData();

    }
    componentDidUpdate() {
        this.props.onRef(this);
        this.loadData();
    }
    componentWillUnmount() {
        this.props.onRef(undefined)
    }
    
    
    render() {
        return  <List divided inverted relaxed>{this.renderList()}</List>
                
    };
};

const mapDispatchToProps = {
    selectSong,
    requestSongList,
    requestLyric,
    requestSong
}


const mapStateToProps = (state) => {
    const returningState = state.songs.songs.data !== undefined ? state.songs.songs.data.message.body.track_list : [];
    return { songs: returningState };
};

export default connect(
    mapStateToProps, 
    mapDispatchToProps
)(SongList);