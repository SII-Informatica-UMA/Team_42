import { Injectable } from "@angular/core";
import { Login, UsuarioSesion, Rol, RolCentro } from "../entities/login";
import { Observable, of, forkJoin, concatMap, lastValueFrom } from "rxjs";
import { map } from 'rxjs/operators';
//import * as jose from 'jose';

import { Dieta } from "../entities/dieta";
import { BackendFakeService } from "./backend.fake.service";
import { BackendService } from "./backend.service";

@Injectable({
  providedIn: 'root'
})
export class DietasService {

  constructor(private backend: BackendFakeService) { }

  getDietas(): Observable<Dieta[]> {
    return this.backend.getDietas();
  }

// Función para obtener la dieta asignada a un usuario por su ID
getDietaByUserId(idCliente: number): Observable<Dieta> {
  console.log('HOLA desde dietas.service.ts');
  const dieta = this.backend.getDietaByUserId(idCliente);
  return of(dieta);
}

  getDietasByClientId(idCliente: number): Observable<Dieta[]> {
    console.log('HOLA desde dietas.service.ts');
    return this.backend.getDietasByClientId(idCliente);
  }

  editarDietas(dieta: Dieta): Observable<Dieta> {
    return this.backend.putDieta(dieta);
  }

  eliminarDieta(id: number): Observable<void> {
    return this.backend.deleteDieta(id);
  }

  aniadirDieta(id: number, dieta: Dieta): Observable<Dieta> {
    return this.backend.postDieta(id, dieta);
  }
}
