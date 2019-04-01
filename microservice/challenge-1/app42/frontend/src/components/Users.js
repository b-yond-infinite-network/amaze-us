import React, {Component} from 'react';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

export default class Users extends Component {

  render() {
    return (
      <Row>
        <Col className="p-2">
          <div className='table-responsive'>
            <table className="table table-striped table-hover">
              <thead>
              <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Email</th>
                <th>Desc</th>
              </tr>
              </thead>
              <tbody>
              {this.props.users.map(user => (
                <tr key={user.id}>
                  <td>{user.id}</td>
                  <td>{user.name}</td>
                  <td>{user.email}</td>
                  <td>{user.description}</td>
                </tr>
              ))}
              </tbody>
            </table>
          </div>
        </Col>
      </Row>
    )
  }

}
