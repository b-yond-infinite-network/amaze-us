'use strict';
import React, {Component} from 'react';
import Checkbox from '../Checkbox/Checkbox';

import './ForgotForm.css'

class ForgotForm extends Component {
    constructor (props) {
        super(props);

        //this.handleLoginFormAuthentification = this.handleLoginFormAuthentification.bind(this);

        this.state = {
            forgotMessage : 'Reset the email or the login or the password.',
            forgotTitle : 'Pioneer Account',
            forgotDisplay : false,

            forgotDefaultTextEmailInput : 'email',
            forgotDefaultTextButtonSend : 'send',
            forgotDefaultTextButtonBack : 'back',

            checkboxes : ['I need to get my login', 'I need to reset my password'],
        }
    }

    componentWillMount = () => {
        this.selectedCheckboxes = new Set();
    }

    toggleCheckbox = label => {
        if (this.selectedCheckboxes.has(label)) {
            this.selectedCheckboxes.delete(label);
        } else {
            this.selectedCheckboxes.add(label);
        }
    }

    handleFormSubmit = formSubmitEvent => {

            formSubmitEvent.preventDefault();

            for (const checkbox of this.selectedCheckboxes) {
                console.log(checkbox, 'is selected.');
            }

    }

    createCheckbox = label => (
        <Checkbox
            label={label}
            handleCheckboxChange={this.toggleCheckbox}
            key={label}
        />
    )

    createCheckboxes = () => (
        this.state.checkboxes.map(this.createCheckbox)
    )
    
    render () {
        return (
            <div className={`forgot_limiter ${this.props.display && this.props.displayForgotForm && !this.props.forceHidden ? 'active' : ''}`}>
                <div className='forgot_sub_limiter'>
                    <div className='forgot_sub_limiter_container'>
                        <form className='forgot_form' onSubmit={this.handleFormSubmit}>

                            <span className='forgot_form_title'>{this.state.forgotTitle}</span>

                            <div className='forgot_form_input_field_container'>

                                <input className='forgot_form_input_field' type='text' name='email' placeholder={this.state.forgotDefaultTextEmailInput}></input>
                                <span className='forgot_form_input_focus'></span>
                                <span className='forgot_form_input_symbol'><i className='fa fa-envelope' aria-hidden='true'></i></span>

                            </div>
                            
                            <div className='forgot_form_checks_container'>
                                {this.createCheckboxes()}
                            </div>

                            <div className='forgot_form_button_container'>

                                <button className='forgot_form_button'>{this.state.forgotDefaultTextButtonSend}</button>
                                <button className='forgot_form_button' onClick={() => this.props.onLoginFormBack()}>{this.state.forgotDefaultTextButtonBack}</button>

                            </div>

                        </form>

                    </div>
                </div>
            </div>
        )
    }
};

export default ForgotForm;