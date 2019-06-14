import { Component, OnInit, OnChanges, Input } from '@angular/core';
import { MoodtrendsService, TopMood } from '../moodtrends.service';

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

  constructor(private moodtrendsService: MoodtrendsService) { }

  ngOnInit() {
/*     this.moodtrendsService.messages.subscribe(msg => {
      console.log('msg', msg);
      if (msg.topMoods) {
        console.log('setting count....');
        this.barChartLabels = [];
        this.barChartData[0].data = [];
        for (const moodInfo of msg.topMoods) {
          this.barChartLabels.push(moodInfo.mood);
          this.barChartData[0].data.push(moodInfo.count);
        }
      }
      this.moodtrends = msg;
    }); */
  }

  ngOnChanges() {
    console.log('top moods', this.topMoods);
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
