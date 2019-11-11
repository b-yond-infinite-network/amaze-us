import React, { useEffect } from 'react';
import Loader from 'react-loader-spinner';
import history from './../../router/history';
import Artists from './artists';
import HeaderContainer from './../../components/HeaderContainer';
import Logo from './../layout/Header/components/Logo';
import SearchBarContainer from '../../components/searchBar/SearchBarContainer';
import displayColumns from './../../components/sortableDataTable/dataTablesColumns/artistsTableColumns';
import './artists.css';

const ArtistsContainer = (props) => {
    let artistName =  "";
    const artistList = props.artistList || [];
    const { getArtistsList } = props;
    
    if (props.match)
        artistName = props.match.params.keyword;
        
    const goToHomePage = () => {
        history.push("/")
    }

    const searchTracks = ({ dataItem }) => {
        history.push(`/artists/artist/${dataItem.artist_name}/tracks`)
    }

    useEffect(() => {
        getArtistsList(artistName);
        history.push("/artists/" + artistName);
    }, [artistName, getArtistsList]);

    return (
        <div className="artists-container">
            <HeaderContainer>
                <Logo clickTo={goToHomePage} />
                <SearchBarContainer
                    currentValue={artistName}
                    searchBarPosition={"searchBar-container-position"} />
            </HeaderContainer>
            {props.isLoading ?
                <Loader className={"spinner"} color={"#6c7ae0"} type="TailSpin" height={150} width={150} />
                :
                <Artists className={"artist-grid"} artists={artistList} columns={displayColumns} onRowClick={searchTracks} />
            }
        </div>
    );
}
export default ArtistsContainer;