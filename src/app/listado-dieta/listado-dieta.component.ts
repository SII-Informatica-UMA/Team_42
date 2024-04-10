import { Component, OnInit } from '@angular/core';
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

  getDietasByClientId() {
    let clienteActual = this.usuarioSesion?.id;
    if(typeof(clienteActual) == undefined) {
      // Si el cliente no está logeado, usamos -1
      clienteActual = -1;
    }
    this.dietasService.getDietasByClientId(clienteActual as number).subscribe(dietas => {
      this.dietasCliente = dietas;
    });
  }
  
  // Función necesaria para poder obtener el id del usuario logeado
  get usuarioSesion() {
    return this.usuariosService.getUsuarioSesion();
  }

  get dietasClientess() {
    this.getDietasByClientId();
    console.log(this.dietasCliente);
    return this.dietasCliente;
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
      this.dietasService.aniadirDieta(this.usuariosService._id as number, dieta).subscribe(usuario => {
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
    console.log("Pregunta entrenador: "+this.rol);
    return this.rol?.rol == Rol.ENTRENADOR;
  }

  isCliente(): boolean {
    console.log("Pregunta cliente: "+this.rol);
    return this.rol?.rol == Rol.CLIENTE;
  }
}
