import React from "react";
import "react-date-range/dist/styles.css"; // main style file
import "react-date-range/dist/theme/default.css"; // theme css file
import { DateRange } from "react-date-range";
import { addDays } from "date-fns";
import Chart from "../chart/Chart";
import RingLoader from "react-spinners/RingLoader";
import { getHTTP } from "../../Utils/Common";
import { getToken } from "../../Utils/Common";
import { tr } from "date-fns/locale";
class Home extends React.Component {
  constructor(props) {
    super(props);
    const token = getToken();
    if (!token) {
      this.props.history.push("/login");
    }
    this.state = {
      data: [],
      areas: [],
      isLoading: true,
      top: 2,

      selection: [
        {
          startDate: new Date(),
          endDate: addDays(new Date(), 7),
          key: "selection",
        },
      ],
      compare: {
        startDate: new Date(),
        endDate: addDays(new Date(), 3),
        key: "compare",
      },
    };
    this.drivers = this.topDrivers.bind(this);
  }

  componentDidMount() {
    this.topDrivers();
  }

  topDrivers() {
    const { top, selection } = this.state;
    var params = {
      top: top,
      from: selection[0].startDate,
      to: selection[0].endDate,
    };
    this.setState({
      isLoading: true,
    });
    getHTTP("schedules/top_drivers_schedules", params)
      .then((response) => {
        if (response.data) {
          var data = [];
          var areas = [];
          response.data.forEach((element) => {
            var obj = {
              month: element.from.substring(0, 10),
            };
            element.data.forEach((driver) => {
              var name =
                driver.driver.first_name + "_" + driver.driver.last_name;
              obj[name] = driver.scheduls_count;
              if (!areas.includes(name)) {
                areas.push(name);
              }
            });

            data.push(obj);
          });

          this.setState({
            data: data,
            areas: areas,
            isLoading: false,
          });
        }
      })
      .catch((error) => {
        this.setState({ isLoading: true });
      });
  }
  setTop(top) {
    console.log("item.", top);
    this.setState({ top: top });
    this.topDrivers();
  }
  handleSelect(item) {
    console.log("item.selection", item.selection);
    this.setState({ selection: [item.selection] });
    this.topDrivers();
    // {
    //   selection: {
    //     startDate: [native Date Object],
    //     endDate: [native Date Object],
    //   }
    // }
  }

  render() {
    const { isLoading, selection, top } = this.state;

    return (
      <div>
        <div id="myBtnContainer">
          <div class="wrapper-calander">
            <div>
              <form>
                <div class="row">
                  <div class="col-2">
                    <label class="pt-2">Top :</label>
                  </div>
                  <div class="col-10">
                    <input
                      class="form-control form-contrl-genrl text-center"
                      type="number"
                      value={top}
                      onChange={(e) => this.setTop(e.target.value)}
                    />
                  </div>
                </div>
              </form>
            </div>
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

        <div class="1">
          {!isLoading ? (
            <Chart data={this.state.data} areas={this.state.areas}></Chart>
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

export default Home;
