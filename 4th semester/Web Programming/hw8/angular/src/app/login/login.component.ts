import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LogReportService } from '../log-report.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private service: LogReportService,
              private router: Router) { }

  ngOnInit() {
  }

  loginUser(event) {
    event.preventDefault()
    const target = event.target
    const username = target.querySelector('#username').value
    const password = target.querySelector('#password').value
    this.service.getUserDetails(username, password).subscribe(
     ( data: any )=> {
        // console.log(data[0].username)
        if (data.length == 1)
        {
          sessionStorage.setItem('loggedUsername', data[0].username);
          sessionStorage.setItem('loggedId', data[0].id);
          this.router.navigate(['user'])
        } else {
          console.log(data)
          window.alert("Invalid credentials")
        }
      }
    )
  }
}
