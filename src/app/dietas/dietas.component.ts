import { Component } from '@angular/core';
import { UsuariosService } from '../services/usuarios.service';

@Component({
  selector: 'app-dietas',
  standalone: true,
  imports: [],
  templateUrl: './dietas.component.html',
  styleUrl: './dietas.component.css'
})

/* Creo una clase DietasComponent con un solo atributo de tipo String (_rolDeUsuario). El constructor recibe un objeto de tipo UsuarioService
(usuario.service.ts) que se llama usuariosService. En el cuerpo se crea una constante usuarioSesion que contiene el atributo rolCentro de
usuariosService (es un getter de usuarios.service.ts). Esto devuelve un objeto de tipo RolCentro (login.ts) que a su vez contiene uno de tipo
Rol (login.ts). Ese es el que nos interesa para saber qué pantalla mostrar. */
export class DietasComponent {
  _rolDeUsuario?: String;
  constructor(private usuariosService: UsuariosService) {
    const usuarioSesion = this.usuariosService.rolCentro;
    if (usuarioSesion) this._rolDeUsuario = usuarioSesion.rol;
  }

  get rolDeUsuario() {
    return this._rolDeUsuario;
  }
}
