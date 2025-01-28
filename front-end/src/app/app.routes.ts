import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { UserResolver } from './resolvers/user.resolver';

export const routes: Routes = [
  { path: '', redirectTo: '/library', pathMatch: 'full' },
  { 
    path: 'login', 
    loadComponent: () => import('./components/authentification/login/login.component').then(m => m.LoginComponent)
  },
  { 
    path: 'register', 
    loadComponent: () => import('./components/authentification/register/register.component').then(m => m.RegisterComponent)
  },
  { 
    path: 'library', 
    loadComponent: () => import('./components/library/library.component').then(m => m.LibraryComponent),
    canActivate: [authGuard]
  },
  { 
    path: 'add-track', 
    loadComponent: () => import('./components/track-form/track-form.component').then(m => m.TrackFormComponent),
    canActivate: [authGuard],
    data: { roles: ['ADMIN'] },
    resolve: { user: UserResolver }
  },
  { 
    path: 'track/:id', 
    loadComponent: () => import('./components/track-details/track-details.component').then(m => m.TrackDetailsComponent),
    canActivate: [authGuard],
    resolve: { user: UserResolver }
  },
  { 
    path: 'edit-track/:id', 
    loadComponent: () => import('./components/track-form/track-form.component').then(m => m.TrackFormComponent),
    canActivate: [authGuard],
    resolve: { user: UserResolver }
  },
  { path: '**', redirectTo: '/login' }
];