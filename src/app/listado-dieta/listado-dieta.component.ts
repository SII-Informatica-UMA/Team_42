import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { Dieta, DietaImpl } from '../entities/dieta';
import { FormularioDietaComponent } from '../formulario-dieta/formulario-dieta.component';
import { DietasService } from '../services/dietas.service';
import { UsuariosService } from '../services/usuarios.service';
import { Rol, UsuarioSesion } from '../entities/login';


@Component({
  selector: 'app-listado-dieta',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './listado-dieta.component.html',
  styleUrl: './listado-dieta.component.css'
})
export class ListadoDietaComponent {
  dietas: Dieta [] = [];
  dietasCliente: Dieta [] = [];

  constructor(private dietasService: DietasService, private usuariosService: UsuariosService, private modalService: NgbModal) {
    this.actualizarDietas();
  }

  actualizarDietas() {
    this.dietasService.getDietas().subscribe(dietas => {
      this.dietas = dietas;
    });
  }

  getDietasByClientId(inputId: string) {
    // Para evitar problemas con los tipos, ya que recogemos el valor desde el archivo html
    // let inputValue: HTMLInputElement = document.getElementById('inputId') as HTMLInputElement;
    // let numericValue: number = parseFloat(inputValue.value);
    this.dietasService.getDietasByClientId(parseInt(inputId)).subscribe(dietas => {
      this.dietasCliente = dietas;
    });
  }

  private dietaEditada(dieta: Dieta): void {
    this.dietasService.editarDietas(dieta).subscribe(() => {
      this.actualizarDietas();
    });
  }

  editarDieta(dieta: Dieta): void {
    let ref = this.modalService.open(FormularioDietaComponent);
    ref.componentInstance.accion = "Editar";
    ref.componentInstance.dieta = {...dieta};
    ref.result.then((dieta: Dieta) => {
      this.dietaEditada(dieta);
    }, (reason) => {});
  }

  eliminarDieta(id: number): void {
    this.dietasService.eliminarDieta(id).subscribe(() => {
      this.actualizarDietas();
    });
  }


  aniadirDieta(): void {
    let ref = this.modalService.open(FormularioDietaComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.usuario = new DietaImpl();
    ref.result.then((dieta: Dieta) => {
      this.dietasService.aniadirDieta(this.usuariosService.id as number, dieta).subscribe(usuario => {
        this.actualizarDietas();
      });
    }, (reason) => {});
  }
  
  // Estas dos funciones las he hecho basandome en listado-usario.component.ts. Creo que en realidad tendría más sentido poner los roles en servicios si lo vamos a usar en más componentes
  private get rol() {
    return this.usuariosService.rolCentro;
  }

  isAdministrador(): boolean {
    console.log("Pregunta admin: "+this.rol);
    return this.rol?.rol == Rol.ADMINISTRADOR;
  }

  isEntrenador(): boolean {
    console.log("Pregunta admin: "+this.rol);
    return this.rol?.rol == Rol.ENTRENADOR;
  }

  isCliente(): boolean {
    console.log("Pregunta admin: "+this.rol);
    return this.rol?.rol == Rol.CLIENTE;
  }
}
