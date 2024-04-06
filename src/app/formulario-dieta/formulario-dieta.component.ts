import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Dieta } from '../dietas/dietas.component';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-formulario-dieta',
  templateUrl: './formulario-dieta.component.html',
  styleUrls: ['./formulario-dieta.component.css']
})
export class FormularioDietaComponent {
  accion?: "Añadir" | "Editar";
  nuevaDieta: Dieta = {nombre: '', descripcion: '', observaciones: '', objetivo: '', duracionDias: 0, alimentos: [], recomendaciones: '', id: 0};

  constructor(public modal: NgbActiveModal) { }

  guardarDieta(): void {
    this.modal.close(this.nuevaDieta);
  }

}
