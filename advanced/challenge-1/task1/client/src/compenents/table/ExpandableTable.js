import "@patternfly/react-core/dist/styles/base.css";
import React from "react";
import {
  Table,
  TableHeader,
  TableBody,
  TableVariant,
  expandable,
} from "@patternfly/react-table";

 class ExpandableTable extends React.Component {
  constructor(props) {
    super(props);
    const { rows } = this.props;
    this.state = {
      isCompact: true,
      rows
    };
    this.onCollapse = this.onCollapse.bind(this);
  }

  onCollapse(event, rowKey, isOpen) {
      console.log('rowKey',rowKey)
    const { rows } = this.state;
    rows[rowKey].isOpen = isOpen;
    this.setState({
      rows,
    });
  }

  toggleCompact(checked) {
    this.setState({
      isCompact: checked,
    });
  }

  render() {
    const { columns, rows } = this.props;

    return (
      <React.Fragment>
        <Table
          aria-label="Expandable table"
          variant={TableVariant.compact}
          onCollapse={this.onCollapse}
          rows={rows}
          cells={columns}
        >
          <TableHeader />
          <TableBody />
        </Table>
      </React.Fragment>
    );
  }
}

export default ExpandableTable
