import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Store } from '@ngrx/store';
import { TranslateModule } from '@ngx-translate/core';
import { from } from 'rxjs';

import { WaterPanelComponent } from './water-panel.component';

describe('WaterPanelComponent', () => {
  let component: WaterPanelComponent;
  let fixture: ComponentFixture<WaterPanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WaterPanelComponent ],
      providers: [
        { provide: Store, useValue: {
          dispatch: jasmine.createSpy('dispatch'),
          pipe: jasmine.createSpy('pipe').and.returnValue(from([])),
          select: jasmine.createSpy('select')
        } }
      ],
      imports: [
        TranslateModule.forRoot()
      ],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WaterPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
