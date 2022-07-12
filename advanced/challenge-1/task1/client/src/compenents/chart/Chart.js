import React from "react";

import {
  AreaChart,
  Area,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
} from "recharts";
class Chart extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      areas: [],
      isLoading: true,
      isSubLoading: true,
      error: null,
    };
  }

  toPercent = (decimal, fixed = 0) => `${(decimal * 100).toFixed(fixed)}%`;

  getPercent = (value, total) => {
    const ratio = total > 0 ? value / total : 0;

    return this.toPercent(ratio, 2);
  };

  renderTooltipContent = (o) => {
    // const { payload = [], label } = o;
    const payload = o.payload ?? [];
    const label = o.label ?? "";
    const total = payload.reduce((result, entry) => result + entry.value, 0);

    return (
      <div className="customized-tooltip-content">
        <p className="total">{`${label} (Total: ${total})`}</p>
        <ul className="list">
          {payload.map((entry, index) => (
            <li key={`item-${index}`} style={{ color: entry.color }}>
              {`${entry.name}: ${entry.value}(${this.getPercent(
                entry.value,
                total
              )})`}
            </li>
          ))}
        </ul>
      </div>
    );
  };
  render() {
    const { data, areas } = this.props;

    return (
      // <div>Hi Ahmad</div>
      <div>
        <div align="center">
          <AreaChart
            width={800}
            height={500}
            data={data}
            stackOffset="expand"
            margin={{
              top: 10,
              right: 30,
              left: 0,
              bottom: 0,
            }}
          >
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="month" />
            <YAxis tickFormatter={this.toPercent} />
            <Tooltip content={this.renderTooltipContent} />
            {areas.map((elem) => {
              var color = Math.floor(Math.random() * 16777215).toString(16);
              return (
                <Area
                  type="monotone"
                  dataKey={elem}
                  stackId="2"
                  stroke={"#" + color}
                  fill={"#" + color}
                ></Area>
              );
            })}
          </AreaChart>
        </div>
      </div>
    );
  }
}

export default Chart;
