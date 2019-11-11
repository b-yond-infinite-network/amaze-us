import React, { useEffect } from 'react';
import Loader from 'react-loader-spinner';

import history from '../../router/history';
import Lyrics from './lyrics';
import HeaderContainer from '../../components/HeaderContainer';
import Logo from './../layout/Header/components/Logo';
import SearchBarContainer from '../../components/searchBar/SearchBarContainer';
import './lyrics.css';

const LyricsContainer = (props) => {
    const artistName = props.match.params.artist_name || "";
    const trackName = props.match.params.track_name || "";
    const trackID = props.match.params.track_id || "";
    const { getLyrics } = props;

    const lyrics = props.lyrics || "";

    const goToHomePage = () => {
        history.push("/")
    }

    useEffect(() => {
        getLyrics(trackID);
    }, [trackID, getLyrics]);

    return (
        <div className="lyrics-container">
            <HeaderContainer>
                <Logo clickTo={goToHomePage} />
                <SearchBarContainer
                    currentValue={artistName}
                    searchBarPosition={"searchBar-container-position"} />
            </HeaderContainer>
            {props.isLoading ?
                <Loader className={"spinner"} color={"#6c7ae0"} type="TailSpin" height={150} width={150} />
                :
                <Lyrics trackName={trackName} lyrics={lyrics} trackID={trackID} artistName={artistName} />
            }
        </div>
    );
}

export default LyricsContainer;