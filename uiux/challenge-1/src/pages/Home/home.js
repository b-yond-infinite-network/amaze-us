import React from 'react';
import Header from './../layout/Header';
// import Footer from './../../layout/Footer';
import SearchBarContainer from './../../components/searchBar/SearchBarContainer'
import './home.css'

const Home = (props) => {

    return (
        <div className="home-container">
            <Header />
            <SearchBarContainer
                searchBarPosition={"searchBar-container-position"} />
            {/* <Footer /> */}
        </div>
    );
}

export default Home;