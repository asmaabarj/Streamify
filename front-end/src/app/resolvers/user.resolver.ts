import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, map } from 'rxjs/operators';
import { of } from 'rxjs';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { User } from '../models/user.model';

export class UserResolver {
  private authService = inject(AuthService);
  private router = inject(Router);

  resolve(): Observable<User> {
    return this.authService.getCurrentUser().pipe(
      map(user => {
        if (!user) {
          this.router.navigate(['/login']);
          throw new Error('User not found');
        }
        return user;
      }),
      catchError(() => {
        this.router.navigate(['/login']);
        return of({} as User);
      })
    );
  }
} 