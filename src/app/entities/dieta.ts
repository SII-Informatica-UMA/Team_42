export interface Dieta {
    nombre: String;
    descripcion: String;
    observaciones: String;
    objetivo: String;
    duracionDias: number;
    alimentos: String[];
    recomendaciones: String;
    id: number;
    idEntrenador: number;
}

export class DietaImpl implements Dieta {
    nombre: String;
    descripcion: String;
    observaciones: String;
    objetivo: String;
    duracionDias: number;
    alimentos: String[];
    recomendaciones: String;
    id: number;
    idEntrenador: number;
    
    constructor() {
        this.nombre = '';
        this.descripcion = '';
        this.observaciones = '';
        this.objetivo = '';
        this.duracionDias = 0;
        this.alimentos = [''];
        this.recomendaciones = '';
        this.id = 0;
        this.idEntrenador = 0;
    }
}