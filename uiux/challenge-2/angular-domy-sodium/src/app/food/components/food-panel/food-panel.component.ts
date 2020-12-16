import { Component, OnDestroy, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { TranslateService } from '@ngx-translate/core';
import { Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import * as fromPlantation from 'src/app/food/store/reducers';
import { loadPlantations } from '../../store/actions/plantation.action';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PlantationFormComponent } from '../plantation-form/plantation-form.component';
import { faArrowCircleDown } from '@fortawesome/free-solid-svg-icons';
import { loadFoodCollector } from '../../store/actions/food.action';

@Component({
  selector: 'app-food-panel',
  templateUrl: './food-panel.component.html',
  styleUrls: ['./food-panel.component.scss']
})
export class FoodPanelComponent implements OnInit, OnDestroy {

  private plantation$: Observable<any> = this.store.pipe(select(fromPlantation.getPlantations));
  private collector$: Observable<any> = this.store.pipe(select(fromPlantation.getCollector));
  private ngUnsuscribe = new Subject();

  private plantations;
  public chartData;
  public storagePercent: number;
  public farmPercent = 0;
  //Icons
  public faArrowCircleDown = faArrowCircleDown;

  constructor(
    private store: Store,
    private translateService: TranslateService,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.plantation$.pipe(takeUntil(this.ngUnsuscribe)).subscribe((plantations) => {
      if (!plantations) { return this.store.dispatch(loadPlantations()); }
      this.plantations = plantations;
      this.chartData = this.formatPlantationToChart(this.plantations);
    });
    this.collector$.pipe(takeUntil(this.ngUnsuscribe)).subscribe((collector) => {
      if(!collector) { return this.store.dispatch(loadFoodCollector()); }
      this.storagePercent = collector.available_capacity;
    });
  }

  async openPlantationModal() {
    await this.modalService.open(PlantationFormComponent, {
      size: 'lg'
    });
  }

  private formatPlantationToChart(plantations: any[]) {
    let data = {
      title: this.translateService.instant('plantation'),
      items: []
    }
    this.farmPercent = 0;
    plantations.forEach((plantation) => {
      const item = { name: plantation.seed_name, percent: plantation.plantation_percent };
      data.items = [...data.items, item];
      this.addPercent(plantation.plantation_percent);
    });
    return data;
  }

  addPercent(percent) {
    this.farmPercent += percent; 
  }

  ngOnDestroy(): void {
    this.ngUnsuscribe.next();
  }
}
