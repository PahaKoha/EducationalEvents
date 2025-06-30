import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {LogInService} from '../../service/log-in-service';
import {RegistrationService} from '../../service/registration-service';
import {AuthService} from '../../service/auth-service';
import {Router, RouterLink} from '@angular/router';
import {switchMap, tap} from 'rxjs';

@Component({
  selector: 'app-log-in-component',
  imports: [
    ReactiveFormsModule
  ],
  standalone: true,
  templateUrl: './log-in-component.html',
  styleUrl: './log-in-component.css'
})
export class LogInComponent implements OnInit {
  loginFormGroup!: FormGroup;

  constructor(
    private loginDialog: LogInService,
    private regDialog: RegistrationService,
    private formBuilder: FormBuilder,
    private auth: AuthService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.loginFormGroup = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  authUser(): void {
    if (this.loginFormGroup.invalid) {
      return;
    }

    this.auth.login(this.loginFormGroup.value).pipe(
      tap(() => this.loginDialog.changeWindowState()),
      switchMap(() => this.router.navigateByUrl('/dashboard')),
    ).subscribe({
      next: ok => {
        if (!ok) console.warn('Navigation cancelled');
      },
      error: err => console.error(err),
    });
  }

  changeLogInWindowState(): void {
    this.loginDialog.changeWindowState();
  }

  changeRegistrationWindowState(): void {
    this.regDialog.changeWindowState();
  }

}
