import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { Dieta, DietaImpl } from '../entities/dieta';
import { FormularioUsuarioComponent } from '../formulario-usuario/formulario-usuario.component';
import { DietasService } from '../services/dietas.service';


@Component({
  selector: 'app-listado-dieta',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './listado-dieta.component.html',
  styleUrl: './listado-dieta.component.css'
})
export class ListadoDietaComponent {
  dietas: Dieta [] = [];

  constructor(private dietasService: DietasService, private modalService: NgbModal) {
    
  }
}
