import { Component, OnInit } from '@angular/core';
import { MoodtrendsService } from '../moodtrends.service';

@Component({
  selector: 'app-topmoods',
  templateUrl: './topmoods.component.html',
  styleUrls: ['./topmoods.component.scss']
})
export class TopmoodsComponent implements OnInit {

  moodtrends = null;

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

  constructor(private moodtrendsService: MoodtrendsService) { }

  ngOnInit() {
    this.moodtrendsService.messages.subscribe(msg => {
      this.barChartLabels = [];
      this.barChartData[0].data = [];
      for (const moodInfo of msg.topMoods) {
        this.barChartLabels.push(moodInfo.mood);
        this.barChartData[0].data.push(moodInfo.count);
      }
      this.moodtrends = msg;
    });
  }

}
