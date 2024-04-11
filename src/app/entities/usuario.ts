export interface Usuario {
  id: number;
  nombre: string;
  apellido1: string;
  apellido2: string;
  email: string;
  password: string;
  administrador: boolean;
  entrenador: boolean;
  // Si es un entrenador, en este atributo almacenará un array con los id de los clientes que entrena
  // Si es un cliente (o un administrador, pues los roles son mutualmente excluyentes) la lista estará vacía
  clientes: number [];
}

export class UsuarioImpl implements Usuario {
  id: number;
  nombre: string;
  apellido1: string;
  apellido2: string;
  email: string;
  password: string;
  administrador: boolean;
  entrenador: boolean;
  clientes: number[];

  constructor() {
    this.id = 0;
    this.nombre = '';
    this.apellido1 = '';
    this.apellido2 = '';
    this.email = '';
    this.password = '';
    this.administrador = false;
    this.entrenador = false;
    this.clientes = [];
  }
}
