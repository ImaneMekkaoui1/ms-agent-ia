import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotUiComponent } from './bot-ui.component';

describe('BotUiComponent', () => {
  let component: BotUiComponent;
  let fixture: ComponentFixture<BotUiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotUiComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotUiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
