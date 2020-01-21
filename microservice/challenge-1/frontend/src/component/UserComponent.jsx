import { ErrorMessage, Field, Form, Formik } from 'formik';
import React, { Component } from 'react'

import UserService from '../service/UserService';

class UserComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            username: this.props.match.params.username === '-1' ? '' : this.props.match.params.username,
            password: '',
            new: this.props.match.params.username === '-1'
        }

        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this)
    }

    componentDidMount() {
        if (this.state.new) {
            return
        }

        UserService.retrieveUser(this.state.username)
            .then(response => this.setState({
                username: this.state.username,
                password: response.data.pass
            }))
    }

    validate(values) {
        let errors = {}
        if (!values.password) {
            errors.password = 'Enter a Content'
        } else if (values.password.length < 5) {
            errors.password = 'Enter at least 5 Characters in Content'
        }

        return errors

    }

    onSubmit(values) {
        if (this.state.new) {
            let user = {
                name: values.username,
                pass: values.password
            }
            UserService.createUser(user)
                .then(() => this.props.history.push('/users'))
        } else {
            let user = {
                name: this.state.username,
                pass: values.password
            }
            UserService.updateUser(this.state.username, user)
                .then(() => this.props.history.push('/users'))
        }
    }

    render() {

        let { password, username } = this.state

        return (
            <div>
                <h3>User</h3>
                <div className="container">
                    <Formik
                        initialValues={{ username, password }}
                        onSubmit={this.onSubmit}
                        validateOnChange={false}
                        validateOnBlur={false}
                        validate={this.validate}
                        enableReinitialize={true}
                    >
                        {
                            (props) => (
                                <Form>
                                    <ErrorMessage name="password" component="div" className="alert alert-warning" />
                                    <fieldset className="form-group">
                                        <label>Username</label>
                                        <Field className="form-control" type="text" name="username" placeholder="Username" disabled={!this.state.new} />
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Password</label>
                                        <Field className="form-control" type="password" name="password" placeholder="Password"/>
                                    </fieldset>
                                    <button className="btn btn-success" type="submit">Save</button>
                                    <button className="btn btn-secondary" type="button" onClick={this.props.history.goBack}>Back</button>
                                </Form>
                            )
                        }
                    </Formik>

                </div>
            </div>
        )
    }
}

export default UserComponent