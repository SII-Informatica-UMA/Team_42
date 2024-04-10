import { Component } from '@angular/core';
import { UsuariosService } from '../services/usuarios.service';
import { AppComponent } from '../app.component';
import { RouterOutlet, RouterLink, Router } from '@angular/router';
import { CommonModule } from '@angular/common'; // Importa CommonModule
import { NgbModal } from '@ng-bootstrap/ng-bootstrap'; // Importa NgbModal
import { FormularioDietaComponent } from '../formulario-dieta/formulario-dieta.component';
import { DietasService } from '../services/dietas.service';
import { DietaImpl } from '../entities/dieta';
import { Rol } from '../entities/login';


@Component({
  selector: 'app-dietas',
  standalone: true,
  imports: [AppComponent, RouterOutlet, RouterLink , CommonModule],
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
  dietas: Dieta [] = [];

  //Le he añadido al constructor el dietasService para poder implementar la funcion de añadirDietas con el formulario
  
  constructor(private usuariosService: UsuariosService , private router: Router , private modalService: NgbModal , 
    private dietasService: DietasService
  ) {
    const usuarioSesion = this.usuariosService.rolCentro;
    this.actualizarDietas();
    if (usuarioSesion) this._rolDeUsuario = usuarioSesion.rol;
  }

  /*
  //funcion creada para el redireccionamiento
  redirectToFormulario() :void{
    this.router.navigate(['/formulario-dieta']);
  }
  */

  actualizarDietas() {
    this.dietasService.getDietas().subscribe(dietas => {
      this.dietas = dietas;
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

  get rolDeUsuario() {
    return this._rolDeUsuario;
  }
  
  //metodo para añadir una dieta (la utilizo cuando se hace click para acceder al formulario de dietas)
  aniadirDieta(): void{
    let ref = this.modalService.open(FormularioDietaComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.dieta = new DietaImpl();
    ref.result.then((dieta: Dieta) => {
      this.dietasService.aniadirDieta(dieta);
    }, (reason) => {});
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

