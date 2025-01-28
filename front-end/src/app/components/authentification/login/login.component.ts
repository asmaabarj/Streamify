import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatIconModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup;
  showPassword = false;
  error: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      login: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  get loginControl() { return this.loginForm.get('login'); }
  get passwordControl() { return this.loginForm.get('password'); }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const credentials = {
        login: this.loginForm.value.login,
        password: this.loginForm.value.password
      };
      
      this.authService.login(credentials).subscribe({
        next: () => {
          this.router.navigate(['/library']);
        },
        error: (err) => {
          console.error('Erreur de connexion:', err);
          this.error = err.error?.message || 'Échec de la connexion. Veuillez vérifier vos identifiants.';
        }
      });
    }
  }
}
