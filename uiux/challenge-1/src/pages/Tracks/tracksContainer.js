import React, { useEffect } from 'react';
import Loader from 'react-loader-spinner';

import history from '../../router/history';
import Tracks from './tracks';
import HeaderContainer from '../../components/HeaderContainer';
import Logo from './../layout/Header/components/Logo';
import SearchBarContainer from '../../components/searchBar/SearchBarContainer';
import displayColumns from './../../components/sortableDataTable/dataTablesColumns/tracksTableColumns';
import './tracks.css';

const TracksContainer = (props) => {
    const artistName = props.match.params.artist_name;
    const trackList = props.trackList || [];
    const { getTracksList } = props;

    const goToHomePage = () => {
        history.push("/")
    }

    const searchLyrics = ({ dataItem }) => {
        history.push(`/artists/artist/${dataItem.artist_name}/tracks/${dataItem.track_id}`);
    }

    useEffect(() => {
        getTracksList(artistName);
    }, [artistName, getTracksList]);

    return (
        <div className="tracks-container">
            <HeaderContainer>
                <Logo clickTo={goToHomePage} />
                <SearchBarContainer
                    currentValue={artistName}
                    searchBarPosition={"searchBar-container-position"} />
            </HeaderContainer>
            {props.isLoading ?
                <Loader className={"spinner"} color={"#6c7ae0"} type="TailSpin" height={150} width={150} />
                :
                <Tracks className={"tracks-grid"} tracks={trackList} columns={displayColumns} onRowClick={searchLyrics} />
            }
        </div>
    );
}

export default TracksContainer;