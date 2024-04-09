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

  getDietas(dieta: Dieta): Observable<Dieta[]> {
    return this.backend.getDietas();
  }

  editarDietas(dieta: Dieta): Observable<Dieta> {
    return this.backend.putDieta(dieta);
  }

  eliminarDieta(id: number): Observable<void> {
    return this.backend.deleteDieta(id);
  }

  aniadirDieta(dieta: Dieta): Observable<Dieta> {
    return this.backend.postDieta(dieta);
  }
}
