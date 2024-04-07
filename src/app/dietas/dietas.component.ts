import { Component } from '@angular/core';
import { UsuariosService } from '../services/usuarios.service';
import { AppComponent } from '../app.component';
import { RouterOutlet, RouterLink, Router } from '@angular/router';


@Component({
  selector: 'app-dietas',
  standalone: true,
  imports: [AppComponent, RouterOutlet, RouterLink],
  templateUrl: './dietas.component.html',
  styleUrl: './dietas.component.css'
})

/* Creo una clase DietasComponent con un solo atributo de tipo String (_rolDeUsuario). El constructor recibe un objeto de tipo UsuarioService
(usuario.service.ts) que se llama usuariosService. En el cuerpo se crea una constante usuarioSesion que contiene el atributo rolCentro de
usuariosService (es un getter de usuarios.service.ts). Esto devuelve un objeto de tipo RolCentro (login.ts) que a su vez contiene uno de tipo
Rol (login.ts). Ese es el que nos interesa para saber qué pantalla mostrar. */
export class DietasComponent {
  _rolDeUsuario?: String;
  nuevaDieta: Dieta = new Dieta(); // Instancia de Dieta para el formulario

  constructor(private usuariosService: UsuariosService) {
    const usuarioSesion = this.usuariosService.rolCentro;
    if (usuarioSesion) this._rolDeUsuario = usuarioSesion.rol;
  }

  get rolDeUsuario() {
    return this._rolDeUsuario;
  }
}
export class Dieta {
  nombre: String;
  descripcion: String;
  observaciones: String;
  objetivo: String;
  duracionDias: number; 
  alimentos: String[]; 
  recomendaciones: String;
  id: number; 

  constructor() {
    this.nombre = '';
    this.descripcion = '';
    this.observaciones = '';
    this.objetivo = '';
    this.duracionDias = 0;
    this.alimentos = [];
    this.recomendaciones = '';
    this.id = 0;
  }
}

