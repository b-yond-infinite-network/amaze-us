import React, { Component } from 'react'

import UserService from '../service/UserService';

class UsersComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            users: [],
            message: null
        }

        this.deleteUserClicked = this.deleteUserClicked.bind(this)
        this.updateUserClicked = this.updateUserClicked.bind(this)
        this.addUserClicked = this.addUserClicked.bind(this)
        this.refreshUsers = this.refreshUsers.bind(this)
    }

    componentDidMount() {
        this.refreshUsers();
    }

    refreshUsers() {
        UserService.retrieveUsers()
            .then(
                response => {
                    this.setState({ users: response.data })
                }
            )
    }

    deleteUserClicked(username) {
        UserService.deleteUser(username)
            .then(
                response => {
                    this.setState({ message: `Delete of user ${username} successful` })
                    this.refreshUsers()
                }
            )
    }

    addUserClicked() {
        this.props.history.push(`/users/-1`)
    }

    updateUserClicked(id) {
        console.log('update ' + id)
        this.props.history.push(`/users/${id}`)
    }

    render() {
        return (
            <div>
                {this.state.message && <div className="alert alert-success">{this.state.message}</div>}
                <h3>Users</h3>
                <div className="container">
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Username</th>
                                <th>Update</th>
                                <th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.users.map(
                                    (user, index) =>
                                        <tr key={index}>
                                            <td>{user}</td>
                                            <td><button className="btn btn-success" onClick={() => this.updateUserClicked(user)}>Update</button></td>
                                            <td><button className="btn btn-warning" onClick={() => this.deleteUserClicked(user)}>Delete</button></td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>
                    <div className="row">
                        <button className="btn btn-success" onClick={this.addUserClicked}>Add new user</button>
                        <button className="btn btn-secondary" type="button" onClick={this.props.history.goBack}>Back</button>
                    </div>
                </div>
            </div>
        )
    }
}

export default UsersComponent