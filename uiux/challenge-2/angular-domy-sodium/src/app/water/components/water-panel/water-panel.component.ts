import { Component, OnDestroy, OnInit } from '@angular/core';
import { faArrowCircleDown, faArrowCircleUp } from '@fortawesome/free-solid-svg-icons';
import { select, Store } from '@ngrx/store';
import { Observable, Subject } from 'rxjs';
import { fromWaterCollector } from '../../store/actions';
import * as fromWater from 'src/app/water/store/reducers';
import { takeUntil } from 'rxjs/operators';
import { loadWaterCollector } from '../../store/actions/water.action';

@Component({
  selector: 'app-water-panel',
  templateUrl: './water-panel.component.html',
  styleUrls: ['./water-panel.component.scss']
})
export class WaterPanelComponent implements OnInit, OnDestroy {

  private collector$: Observable<any> = this.store.pipe(select(fromWater.getCollector));
  private ngUnsuscribe = new Subject();  

  //Icons
  public faArrowCircleDown = faArrowCircleDown;
  public faArrowCircleUp = faArrowCircleUp;

  inputLevel: number;
  outputLevel: number;
  waterStorage: number;
  constructor(
    private store: Store
  ) { }

  ngOnInit(): void {
    this.collector$.pipe(takeUntil(this.ngUnsuscribe)).subscribe((collector) => {
      if (!collector) { return this.store.dispatch(loadWaterCollector()); }

      this.inputLevel = collector.input;
      this.outputLevel = collector.output;
      this.waterStorage = collector.available_capacity;
    });
  }

  increaseOutput(): void {
    try {
      
    } catch(e) {

    }
  }

  decreaseOutput(): void {
    try {

    } catch(e) {
      
    }
  }

  ngOnDestroy(): void {
    this.ngUnsuscribe.next();
  }

}
