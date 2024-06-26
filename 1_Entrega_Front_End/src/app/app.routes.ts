import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ForgottenPasswordComponent } from './forgotten-password/forgotten-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { ListadoUsuarioComponent } from './listado-usuario/listado-usuario.component';
import { DietasComponent } from './dietas/dietas.component';
import { PrincipalComponent } from './principal/principal.component';
import { FormularioDietaComponent } from './formulario-dieta/formulario-dieta.component';
import { ListadoDietaComponent } from './listado-dieta/listado-dieta.component';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'forgotten-password',
    component: ForgottenPasswordComponent
  },
  {
    path: 'reset-password',
    component: ResetPasswordComponent
  },
  {
    path: 'usuarios',
    component: ListadoUsuarioComponent
  },
  {
    path: '',
    component: PrincipalComponent
  },
  {
    path: 'dietas',
    component: DietasComponent
  }, 
  {
    path: 'listadoDietas',
    component: ListadoDietaComponent // AÑADIDO A PRINCIPAL.COMPONENT.HTML
  },
  {
    path: 'formulario-dieta',
    component: FormularioDietaComponent
  }
];
