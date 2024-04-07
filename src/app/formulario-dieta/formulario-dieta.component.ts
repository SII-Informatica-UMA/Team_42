import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Dieta } from '../dietas/dietas.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { dietaImplm } from '../entities/dieta';

@Component({
  selector: 'app-formulario-dieta',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './formulario-dieta.component.html',
  styleUrls: ['./formulario-dieta.component.css']
})
export class FormularioDietaComponent {
  accion?: "Añadir" | "Editar";
  _dieta: Dieta = new dietaImplm; // {nombre: '', descripcion: '', observaciones: '', objetivo: '', duracionDias: 0, alimentos: [], recomendaciones: '', id: 0}

  constructor(public modal: NgbActiveModal) { }

  get dieta() {
    return this._dieta;
  }

  guardarDieta(): void {
    this.modal.close(this._dieta);
  }
}
