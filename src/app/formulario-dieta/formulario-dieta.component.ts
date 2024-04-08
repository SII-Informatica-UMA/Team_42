import { Component } from '@angular/core';
import { Dieta, DietaImpl } from '../entities/dieta';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-formulario-dieta',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './formulario-dieta.component.html',
  styleUrls: ['./formulario-dieta.component.css']
})
export class FormularioDietaComponent {
  accion?: "Añadir" | "Editar";
  _dieta: Dieta = new DietaImpl(); // {nombre: '', descripcion: '', observaciones: '', objetivo: '', duracionDias: 0, alimentos: [], recomendaciones: '', id: 0}

  constructor(public modal: NgbActiveModal) { }

  get dieta() {
    return this._dieta;
  }

  set dieta(dieta: Dieta){
    this._dieta = dieta;
  }

  guardarDieta(): void {
    this.modal.close(this._dieta);
  }
}
