import React from 'react';
import history from './../../../router/history';
import HeaderContainer from './../../../components/HeaderContainer';
import Logo from './components/Logo';
import './header.css'

const Header = () => {
    return (
        <HeaderContainer>
            <Logo clickTo={() => history.push("/")} />
            <div className="header-text">
                <span>Karaoke</span>
            </div>
        </HeaderContainer>
    );
}

export default Header;
