import { UsuarioSesion } from './../entities/login';
import { Component } from '@angular/core';
import { UsuariosService } from '../services/usuarios.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { Usuario, UsuarioImpl } from '../entities/usuario';
import { Rol } from '../entities/login';
import { FormularioUsuarioComponent } from '../formulario-usuario/formulario-usuario.component';

@Component({
  selector: 'app-listado-usuario',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './listado-usuario.component.html',
  styleUrl: './listado-usuario.component.css'
})
export class ListadoUsuarioComponent {
  usuarios: Usuario [] = [];
  clientes: Usuario [] = [];

  constructor(private usuariosService: UsuariosService, private modalService: NgbModal) {
    this.actualizarUsuarios();
    if(this.isEntrenador()) this.getClientesEntrenador();
  }

  getClientesEntrenador () {
      let entrenadorSesion = this.getUsuarioSesion();
      let idEntrenador: number;
      // En este array almacenaremos los identificadores correspondientes a los clientes que el entrenador en la sesión actual entrena
      let arrayIdClientes: number[];
      if(entrenadorSesion) {
        idEntrenador = entrenadorSesion.id;
        // Iteramos sobre todas las dietas que tenemos
        this.usuarios.forEach((usuario: Usuario) => {
          if (usuario.id == idEntrenador) {
            arrayIdClientes = usuario.clientes;
            this.crearArrayClientes(arrayIdClientes);
          }
        });
      }
  }

  crearArrayClientes(arrayIdClientes: number[]) {
    this.usuarios.forEach((usuario: Usuario) => {
      if (arrayIdClientes.includes(usuario.id)) this.clientes.push(usuario);
    });
  }

  getUsuarioSesion() {
   return this.usuariosService.getUsuarioSesion();
  }

  private get rol() {
    return this.usuariosService.rolCentro;
  }

  isAdministrador(): boolean {
    return this.rol?.rol == Rol.ADMINISTRADOR;
  }

  isEntrenador(): boolean {
    return this.rol?.rol == Rol.ENTRENADOR;
  }

  ngOnInit(): void {
    this.actualizarUsuarios();
  }

  actualizarUsuarios() {
    this.usuariosService.getUsuarios().subscribe(usuarios => {
      this.usuarios = usuarios;
    });
  }

  aniadirUsuario(): void {
    let ref = this.modalService.open(FormularioUsuarioComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.usuario = new UsuarioImpl();
    ref.result.then((usuario: Usuario) => {
      this.usuariosService.aniadirUsuario(usuario).subscribe(usuario => {
        this.actualizarUsuarios();
      });
    }, (reason) => {});

  }
  
  private usuarioEditado(usuario: Usuario): void {
    this.usuariosService.editarUsuario(usuario).subscribe(() => {
      this.actualizarUsuarios();
    });
  }

  eliminarUsuario(id: number): void {
    this.usuariosService.eliminarUsuario(id).subscribe(() => {
      this.actualizarUsuarios();
    });
  }

  editarUsuario(usuario: Usuario): void {
    let ref = this.modalService.open(FormularioUsuarioComponent);
    ref.componentInstance.accion = "Editar";
    ref.componentInstance.usuario = {...usuario};
    ref.result.then((usuario: Usuario) => {
      this.usuarioEditado(usuario);
    }, (reason) => {});
  }
}
