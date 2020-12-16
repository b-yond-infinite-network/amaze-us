import { Component, Input, OnChanges, OnInit } from '@angular/core';
import * as Chart from 'chart.js';

@Component({
  selector: 'app-doughnut-chart',
  templateUrl: './doughnut-chart.component.html',
  styleUrls: ['./doughnut-chart.component.scss']
})
export class DoughnutChartComponent implements OnInit {

  canvas: any;
  ctx: any;

  @Input() inputs: { [k: string]: any };


  private labels: string[] = [];
  private values: number[] = [];

  private chart;

  constructor() { }

  ngOnInit(): void {
    this.inputs?.items?.forEach((item) => {
      this.values = [...this.values, item.percent];
      this.labels = [...this.labels, item.name];
    });
    this.canvas = document.getElementById('myChart');
    this.ctx = this.canvas.getContext('2d');
    this.chart = this.generateChart();
  }

  private generateChart(): Chart {
    return new Chart(this.ctx, {
      type: 'doughnut',
      data: {
        labels: this.labels,
        datasets: [{
          label: this.inputs?.title,
          data: this.values,
          backgroundColor: this.generateColors(),
          borderWidth: 0
        }]
      },
      options: {
        legend: {
          display: false
        },
        responsive: true,
        display: true
      }
    });
  }

  private generateColors(): string[] {
    let colors = [];
    for (const v in this.values) {
      const color = '#' + (Math.random() * 0xFFFFFF << 0).toString(16);
      colors = [...colors, color];
    }
    return colors;
  }

}
