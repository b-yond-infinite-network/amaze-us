import { Component, OnInit, OnChanges, Input } from '@angular/core';
import { TopMood } from '../moodtrends.service';

@Component({
  selector: 'app-topmoods',
  templateUrl: './topmoods.component.html',
  styleUrls: ['./topmoods.component.scss']
})
export class TopmoodsComponent implements OnInit, OnChanges {

  @Input() topMoods: TopMood[]

  public barChartOptions = {
    scaleShowVerticalLines: false,
    responsive: true
  };

  public barChartLabels = [];
  public barChartType = 'horizontalBar';
  public barChartLegend = true;
  public barChartData = [
    {data: [], label: 'Cat Mood Trends'}
  ];

  constructor() { }

  ngOnInit() {}

  ngOnChanges() {
    if (this.topMoods) {
      this.barChartLabels = [];
      this.barChartData[0].data = [];
      for (const moodInfo of this.topMoods) {
        this.barChartLabels.push(moodInfo.mood);
        this.barChartData[0].data.push(moodInfo.count);
      }      
    }
  }
}
