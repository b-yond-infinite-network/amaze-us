import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchLyricsComponent } from './search-lyrics.component';

describe('SearchLyricsComponent', () => {
  let component: SearchLyricsComponent;
  let fixture: ComponentFixture<SearchLyricsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchLyricsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchLyricsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
