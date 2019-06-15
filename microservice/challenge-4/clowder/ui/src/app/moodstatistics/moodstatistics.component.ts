import { Component, OnInit, Input } from '@angular/core';
import { MoodStatistic } from '../moodtrends.service';
import { Chart } from 'chart.js';

@Component({
  selector: 'app-moodstatistics',
  templateUrl: './moodstatistics.component.html',
  styleUrls: ['./moodstatistics.component.scss']
})
export class MoodstatisticsComponent implements OnInit {

  @Input() moodStatistics: MoodStatistic[] = null;
  chart = null;

  public multiLineChartOptions = {
    type: 'line',
    data: {},
    options: {
      responsive: true,
      hoverMode: 'index',
      stacked: false,
      title: {
        display: true,
        text: 'Mood Statistics'
      },
      scales: {
        yAxes: [{
          type: 'linear', // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
          display: true,
          position: 'left',
          id: 'y-axis-1',
        }, {
          type: 'linear', // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
          display: true,
          position: 'right',
          id: 'y-axis-2',

          // grid line settings
          gridLines: {
            drawOnChartArea: false, // only want the grid lines for one axis to show up
          },
        }],
      }
    }
  }

  constructor() { }

  ngOnInit() {}

  ngOnChanges() {
		var lineChartData = {
			labels: [],
			datasets: [{
				label: 'Mean',
				borderColor: '#1b568e',
				backgroundColor: '#1b568e',
				fill: false,
				data: [
				],
				yAxisID: 'y-axis-1',
			}, {
				label: 'Variance',
				borderColor: '#d63769',
				backgroundColor: '#d63769',
				fill: false,
				data: [
				],
				yAxisID: 'y-axis-2'
			}]
		};

    if (this.moodStatistics) {
      for (const moodStatistic of this.moodStatistics) {
        lineChartData.labels.push(moodStatistic.mood);
        lineChartData.datasets[0].data.push(moodStatistic.mean);
        lineChartData.datasets[1].data.push(moodStatistic.variance);
      }
      this.multiLineChartOptions.data = lineChartData;
      this.chart = new Chart('canvas', this.multiLineChartOptions);
    }
  }
}
