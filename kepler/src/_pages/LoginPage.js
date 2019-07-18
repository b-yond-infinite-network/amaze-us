import React, { Component } from 'react'
import axios from 'axios';
import { BrowserRouter as Router, Route, Link, Redirect, withRouter} from "react-router-dom";

import EnterButton from '../Components/EnterButton/EnterButton'
import LoginVideo from '../Components/LoginVideo/LoginVideo'
import LoginForm from '../Components/LoginForm/LoginForm'
import ForgotForm from '../Components/ForgotForm/ForgotForm'
import NotLoginLabel from '../Components/NotLoginLabel/NotLoginLabel'

class LoginPage extends Component {

    constructor (props) {
        super(props);

        /* bind the handle function to the object -> IMPORTANT */
        this.handleEnterApplicationClick = this.handleEnterApplicationClick.bind(this);
        this.handleLoginFormAuthentification = this.handleLoginFormAuthentification.bind(this);

        this.handleDisplayForgotFormClick = this.handleDisplayForgotFormClick.bind(this);
        this.handleDisplayLoginFormBackClick = this.handleDisplayLoginFormBackClick.bind(this);

        this.requestAuthentification = this.requestAuthentification.bind(this);

        this.state = {

            withButton : true,
            displayForgotForm : false,
            forceHiddenLoginForm : false,
            playBackGroundVideo : false,

            buttonLabel : 'enter',

            loginMessage : 'You have to login before entering the application.',
            loginMessageDisplay : false,

            redirectToHome : false,
            pioneerAuthentificated : null,
        }
    }
    
    handleEnterApplicationClick() {
        this.setState({withButton: false});
        this.setState({displayForgotForm: false});
    }

    handleDisplayForgotFormClick() {
        this.setState({withButton: false});
        this.setState({displayForgotForm: true});
    }

    handleDisplayLoginFormBackClick() {
        this.setState({withButton: false});
        this.setState({displayForgotForm: false});
    }
    
    handleLoginFormAuthentification() {

        if (!this.props.ping.lastPing) {

            this.setState({loginMessage: 'Server is OFFLINE. Impossible to login, we are sorry.'})
            this.setState({loginMessageDisplay: true});

            console.log("Authentification can't be done because the server is OFFLINE.");
            return 1;
        }

        this.requestAuthentification("jeantornier@gmail.com", "jto_pwd")
    }

    requestAuthentification(login, password) {

       /* return axios.get('http://localhost:8080/pioneer/authentification/' + login + '/' + password)
        .then((reponse) => {
            return reponse.data.content[0];
        });*/

        fetch('http://localhost:8080/pioneer/authentification/' + login + '/' + password)
        .then(reponse => reponse.json())
        .then(data => {

            this.props.authentificationObtained.pioneer = data.content[0];

            if (data.content[0].userApplication.userApplicationProfile === 'administrator') {
                this.props.authentificationObtained.authenticate(3);
            } else if (data.content[0].userApplication.userApplicationProfile === 'checker') {
                this.props.authentificationObtained.authenticate(2);
            } else {
                this.props.authentificationObtained.authenticate(1);
            }

            console.log(this.props.authentificationObtained)

            console.log(this.state.loginMessage)

            this.setState({loginMessage: 'authentification is a success - welcome !'})
            this.setState({loginMessageDisplay: true});
            this.setState({forceHiddenLoginForm: true});
            this.setState({playBackGroundVideo: true});
    
            console.log(this.state.loginMessage)

            this.setState({redirectToHome: true});

        }).catch(error => {

            this.setState({loginMessage: 'authentification failed !'})
            this.setState({loginMessageDisplay: true});

            console.log("Authentification is a failure - retry");
            
            console.log(error);
        });
        

        /*if (pioneerReturned !== undefined) {
        
            this.props.authentificationObtained.pioneer = pioneerReturned.data.content[0];

            if (this.props.authentificationObtained.pioneer.userApplication.userApplicationProfile === 'administrator') {
                this.props.authentificationObtained.authenticate(3);
            } else if (this.props.authentificationObtained.pioneer.userApplication.userApplicationProfile === 'checker') {
                this.props.authentificationObtained.authenticate(2);
            } else {
                this.props.authentificationObtained.authenticate(1);
            }

        } else {

            this.props.authentificationObtained.reset();
        }*/
    }

    handleStartVideo() {
        console.log("starting the video as background");
    }

    render() {

        if ( this.state.redirectToHome) {
            return (<Redirect to='/home' />)
        } 

        return (

            <div className='loginPage'>
                
                <EnterButton 
                    label={this.state.buttonLabel} 
                    display={this.state.withButton} 
                    forceHidden={this.state.forceHiddenLoginForm}

                    onClick={this.handleEnterApplicationClick}
                />
                
                <NotLoginLabel
                    display={this.state.withButton} 
                    forceHidden={this.state.forceHiddenLoginForm}
                    hasTried={true}
                />

                <LoginForm 
                    display={!this.state.withButton} 
                    displayForgotForm={!this.state.displayForgotForm} 
                    forceHidden={this.state.forceHiddenLoginForm}
                    
                    loginMessage={this.state.loginMessage}
                    loginMessageDisplay={this.state.loginMessageDisplay}

                    onLoginButtonClick={this.handleLoginFormAuthentification} 
                    onForgotTextClick={this.handleDisplayForgotFormClick}
                />

                <ForgotForm 
                    display={!this.state.withButton} 
                    displayForgotForm={this.state.displayForgotForm}
                    forceHidden={this.state.forceHiddenLoginForm}

                    onLoginFormBack={this.handleDisplayLoginFormBackClick}
                />

                <LoginVideo 
                    playVideo={this.state.playBackGroundVideo}
                    forceHidden={this.state.forceHiddenLoginForm}
                />
   
            </div>
        )
    }
}

export default LoginPage