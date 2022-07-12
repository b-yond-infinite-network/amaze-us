import React from "react";
import "react-date-range/dist/styles.css"; // main style file
import "react-date-range/dist/theme/default.css"; // theme css file
import { DateRange } from "react-date-range";
import { addDays } from "date-fns";
import ExpandableTable from "../table/ExpandableTable";
import RingLoader from "react-spinners/RingLoader";
import { getHTTP } from "../../Utils/Common";

class BusesList extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      data: [],
      areas: [],
      isLoading: true,
      columns: ["Capacity", "Model", "Make"],

      selection: [
        {
          startDate: new Date(),
          endDate: addDays(new Date(), 1),
          key: "selection",
        },
      ],
    };
    this.buses = this.busesSchedules.bind(this);
  }

  componentDidMount() {
    this.busesSchedules();
  }

  busesSchedules() {
    const { selection } = this.state;
    var params = {
      from: selection[0].startDate,
      to: selection[0].endDate,
    };
    this.setState({
      isLoading: true,
    });
    getHTTP("schedules/bus_schedules", params)
      .then((response) => {
        // console.log('response.data',response.data)
        var rows = [];
        if (response.data) {
          var rows = [];

          response.data.forEach((element) => {
            rows.push({
              isOpen: false,
              cells: [element.capacity, element.model, element.make],
            });

            let parent = rows.length;
            element.schedules.forEach((child) => {
              rows.push({
                parent: parent - 1,
                cells: [child.from, child.to],
              });
            });
          });

          this.setState({
            rows: rows,
            isLoading: false,
          });
        }
      })
      .catch((error) => {
        this.setState({ isLoading: true });
      });
  }

  handleSelect(item) {
    this.setState({ selection: [item.selection] });
    this.busesSchedules();
  }

  render() {
    const { isLoading, rows, selection, columns } = this.state;

    return (
      <div>
        <div id="myBtnContainer">
          <div class="wrapper-calander">
            <div class="wrapper-calendar-inner">
              <DateRange
                editableDateInputs={true}
                onChange={(item) => this.handleSelect(item)}
                moveRangeOnFirstSelection={false}
                ranges={selection}
              />
            </div>
            <div></div>
          </div>
        </div>

        <div>
          {!isLoading ? (
            <ExpandableTable rows={rows} columns={columns}></ExpandableTable>
          ) : (
            <div class="wraper-loader">
              <RingLoader size={60} color={"#605664"} loading={isLoading} />
            </div>
          )}
        </div>
      </div>
    );
  }
}

export default BusesList;
