import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/service/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  form = new FormGroup({
    lastname: new FormControl(null, Validators.required),
    firstname: new FormControl(null, Validators.required),
    username: new FormControl(null, Validators.required),
    password: new FormControl(null, Validators.required),
    email: new FormControl(null, Validators.required),
  });

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  submitForm() {
    if (this.form.invalid) {
      return;
    }
    this.authService
      .signup(
        this.form.get('lastname')?.value!,
        this.form.get('firstname')?.value!,
        this.form.get('username')?.value!,
        this.form.get('password')?.value!,
        this.form.get('email')?.value!
      )
      .subscribe({
        next: () => {
          this.router.navigate(['/login']);
        }
      });
  }

  ngOnInit(): void {
  }

}
