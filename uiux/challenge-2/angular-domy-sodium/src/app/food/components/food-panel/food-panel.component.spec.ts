import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Store } from '@ngrx/store';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { from, of } from 'rxjs';

import { FoodPanelComponent } from './food-panel.component';

describe('FoodPanelComponent', () => {
  let component: FoodPanelComponent;
  let fixture: ComponentFixture<FoodPanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FoodPanelComponent],
      providers: [
        {
          provide: Store,
          useValue: {
            dispatch: jasmine.createSpy('dispatch'),
            pipe: jasmine.createSpy('pipe').and.returnValue(from([])),
            select: jasmine.createSpy('select')
          }
        },
        {
          provide: TranslateService,
          useValue: {
            instant: jasmine.createSpy('instant').and.returnValue('')
          }
        },
        { useValue: {}, provide: NgbModal }
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FoodPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
