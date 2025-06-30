import {Component, OnInit} from '@angular/core';
import {RegistrationService} from '../../service/registration-service';
import {LogInService} from '../../service/log-in-service';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';

@Component({
  selector: 'app-registration-component',
  imports: [
    ReactiveFormsModule
  ],
  standalone: true,
  templateUrl: './registration-component.html',
  styleUrl: './registration-component.css'
})
export class RegistrationComponent implements OnInit {

  registrationFormGroup!: FormGroup;

  constructor(private registrationService: RegistrationService,
              private logInService: LogInService,
              private formBuilder: FormBuilder) { }


  ngOnInit(): void {
    this.registrationFormGroup = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      confirmedPassword: ['', Validators.required]
    })
  }

  changeLogInWindowState(): void {
    this.logInService.changeWindowState();
  }
  changeRegistrationWindowState() {
    this.registrationService.changeWindowState();
  }

  test() {
    console.log(this.registrationFormGroup.value)
  }

  registrationUser(): void {
    this.registrationService.registration(this.registrationFormGroup.value).subscribe({
      next: (response) => {
        console.log(response)
        alert('Пользователь был учпешно зарегистрирован!')
      },
      error: (error) => {
        console.log(error)
      }
    })
  }
}
