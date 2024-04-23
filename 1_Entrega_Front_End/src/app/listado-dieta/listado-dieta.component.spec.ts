import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListadoDietaComponent } from './listado-dieta.component';

describe('ListadoDietaComponent', () => {
  let component: ListadoDietaComponent;
  let fixture: ComponentFixture<ListadoDietaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListadoDietaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListadoDietaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
