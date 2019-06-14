import { Component, Output, EventEmitter } from '@angular/core';
import { MoodtrendsService, TopMood, MoodStatistic } from './moodtrends.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'loki';

  moodtrends = null;
  //@Output() topMoods = new EventEmitter<TopMood[]>();
  //@Output() moodStatistics = new EventEmitter<MoodStatistic[]>();
  topMoods = null;
  moodStatistics = null;

  constructor(private moodtrendsService: MoodtrendsService) { }

  ngOnInit() {
    this.moodtrendsService.messages.subscribe(msg => {
      console.log('msg', msg);
      this.moodtrends = msg;
      if (msg.topMoods) {
        //this.topMoods.emit(msg.topMoods);
        this.topMoods = msg.topMoods;
      } else if (msg.moodStatistics) {
        //this.moodStatistics.emit(msg.moodStatistics);
        this.moodStatistics = msg.moodStatistics;
      }
    });
  }
}
