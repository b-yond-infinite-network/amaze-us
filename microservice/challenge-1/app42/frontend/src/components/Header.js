import React, {Component} from 'react';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

export default class Header extends Component {

  logoStyle = {
    width: "64px"
  };
  rowStyle = {
    backgroundColor: "#ecf0f1"
  };

  render() {
    return (
      <Row>
        <Col md={12} className="text-left" style={this.rowStyle}>
          <img style={this.logoStyle}
               src="http://pluspng.com/img-png/png-user-icon-multy-user-icons-512.png"
               alt=""/>
        </Col>
      </Row>
    )
  }

}
