import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalLyricsComponent } from './modal-lyrics.component';

describe('ModalLyricsComponent', () => {
  let component: ModalLyricsComponent;
  let fixture: ComponentFixture<ModalLyricsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModalLyricsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalLyricsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it(`should close the modal`, async(() => {
    component.showModal = true;
    component.hide();
    expect(component.showModal).toEqual(false);
  }));

});
