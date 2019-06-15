import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { Subject } from "rxjs";
import { map } from "rxjs/operators";
import { WebsocketService } from "./websocket.service";
import { Timestamp } from 'rxjs/internal/operators/timestamp';

export interface MoodStatistic {
  timestamp: string;
  mood: string;
  mean: number;
  variance: number
}

export interface TopMood {
  mood: string;
  count: number;
}

export interface Message {
  type: string;
  topMoods?: TopMood[];
  moodStatistics?: MoodStatistic[]
}

@Injectable({
  providedIn: 'root'
})
export class MoodtrendsService {

  moodTrendsUrl = environment.clowderservice.url + environment.clowderservice.routes.moodtrends;

  public messages: Subject<Message>;

  constructor(private wsService: WebsocketService) {
    this.startMoodTrendStream();
  }

  startMoodTrendStream() {
    this.messages = <Subject<Message>>this.wsService.connect(this.moodTrendsUrl).pipe(map(x => {
      let data = JSON.parse(x.data);
      return {
        type: data.type,
        topMoods: data.topMoods,
        moodStatistics: data.moodStatistics
      };
    }));
  }
}
