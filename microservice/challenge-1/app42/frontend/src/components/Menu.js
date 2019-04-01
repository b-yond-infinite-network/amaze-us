import Button from "react-bootstrap/Button";
import React, {Component} from 'react';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

export default class Menu extends Component {

  menuStyle = {
    backgroundColor: "#27ae60"
  };

  render() {
    return (
      <Row>
        <Col style={this.menuStyle} className="p-2">
          <Button size="sm" alt="not implemented">
            <i className="fa fa-plus"/>
          </Button>
        </Col>
      </Row>
    )
  }

}
