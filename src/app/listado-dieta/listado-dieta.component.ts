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
  dietaUsuario: Dieta | undefined;

  constructor(private dietasService: DietasService, private usuariosService: UsuariosService, private modalService: NgbModal) {
    this.actualizarDietas();
  }

  actualizarDietas() {
    this.dietasService.getDietas().subscribe(dietas => {
      this.dietas = dietas;
    });
  }

  ngOnInit(): void {
    // Automáticamente, lanzamos la función getDietaByUserId para calcular cuales son las dietas que le corresponde al usuario
    // Posteriormente, las mostramos en el html correspondiente (listado-dieta.component.html)
    let clienteActual = this.usuarioSesion?.id;
    if(clienteActual == undefined) {
      // Si el cliente no está logeado, usamos -1 para evitar errores
      clienteActual = -1;
    }
      this.dietasService.getDietaByUserId(clienteActual as number).subscribe(dieta => {
      this.dietaUsuario = dieta;
    });
  }

  // Función necesaria para poder obtener el id del usuario logeado
  get usuarioSesion() {
    return this.usuariosService.getUsuarioSesion();
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
    let usuarioActual = this.usuarioSesion?.id;
    if(typeof(usuarioActual) == undefined) {
      // Si el cliente no está logeado, usamos -1
      usuarioActual = -1;
    }
    let ref = this.modalService.open(FormularioDietaComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.usuario = new DietaImpl();
    ref.result.then((dieta: Dieta) => {
      this.dietasService.aniadirDieta(usuarioActual as number, dieta).subscribe(usuario => {
        this.actualizarDietas();
      });
    }, (reason) => {});
  }
  
  // Estas dos funciones las he hecho basandome en listado-usario.component.ts. Creo que en realidad tendría más sentido poner los roles en servicios si lo vamos a usar en más componentes
  private get rol() {
    return this.usuariosService.rolCentro;
  }

  isDietaOfEntrenador(diet: Dieta): boolean {
    return diet.idEntrenador == this.usuarioSesion?.id as number;
  }

  isAdministrador(): boolean {
    return this.rol?.rol == Rol.ADMINISTRADOR;
  }

  isEntrenador(): boolean {
    return this.rol?.rol == Rol.ENTRENADOR;
  }

  isCliente(): boolean {
    return this.rol?.rol == Rol.CLIENTE;
  }
}
