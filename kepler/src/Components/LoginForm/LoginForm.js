'use strict';
import React, {Component} from 'react';

import './LoginForm.css'

class LoginForm extends Component {
    constructor (props) {
        super(props);

        this.state = {
            imagePath : '_media/image/kepler_login.png',
            loginTitle : 'Pioneer Login',

            loginDefaultTextLoginInput : 'login or email',
            loginDefaultTextPasswordInput : 'password',
            loginDefaultTextFormButton : 'enter',

            loginForgotText1 : 'Did you forgot your ',
            loginForgotText2 : 'login / email ',
            loginForgotText3 : '?',
        }
    }
    
    render () {
        return (
            <div className={`login_form_limiter ${this.props.display && this.props.displayForgotForm && !this.props.forceHidden ? 'active' : ''}`}>
                <div className='login_form_sub_limiter'>
                    <div className='login_form_container'>

                        <div className='login_form_icon'>
                            <img src={this.state.imagePath} alt='IMG'></img>
                            <div className={`login_form_icon_message ${!this.props.loginMessageDisplay ? 'active' : ''}`}>
                                <hr></hr>
                                <span>{this.props.loginMessage}</span>
                            </div>
                        </div>

                        <div className='login_form'>

                            <span className='login_form_title'>{this.state.loginTitle}</span>

                            <div className='login_form_input_field_container'>

                                <input className='login_form_input_field' type='text' name='login' placeholder={this.state.loginDefaultTextLoginInput}></input>
                                <span className='login_form_input_field_focus'></span>
                                <span className='login_form_input_field_symbol'><i className='fa fa-envelope' aria-hidden='true'></i></span>

                            </div>

                            <div className='login_form_input_field_container'>

                                <input className='login_form_input_field' type='password' name='password' placeholder={this.state.loginDefaultTextPasswordInput}></input>
                                <span className='login_form_input_field_focus'></span>
                                <span className='login_form_input_field_symbol'><i className='fa fa-lock' aria-hidden='true'></i></span>

                            </div>
                            
                            <div className='login_form_button_container'>

                                <button className='login_form_button' onClick={() => this.props.onLoginButtonClick()}>{this.state.loginDefaultTextFormButton}</button>

                            </div>

                            <div className='login_form_missing_container'>

                                <span className='login_form_text_1'>{this.state.loginForgotText1}</span>
                                <span className='login_form_text_2' onClick={() => this.props.onForgotTextClick()}>{this.state.loginForgotText2}</span>
                                <span className='login_form_text_1'>{this.state.loginForgotText3}</span>

                            </div>

                        </div>

                    </div>
                </div>
            </div>
        )
    }
};

export default LoginForm;
