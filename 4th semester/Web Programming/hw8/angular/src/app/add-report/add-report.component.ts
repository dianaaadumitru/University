import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LogReportService } from '../log-report.service';
import { LogReport } from '../logReport';

@Component({
  selector: 'app-add-report',
  templateUrl: './add-report.component.html',
  styleUrls: ['./add-report.component.css']
})
export class AddReportComponent implements OnInit {

  logReports: LogReport[] = [];

  userId: number = 0;

  constructor(private service: LogReportService, private router: Router) { }

  ngOnInit(): void {
    this.getLogs();
    this.userId = Number(sessionStorage.getItem('loggedId'));
  }

  getLogs() {
    this.service.getAll().subscribe(
      data => {
        this.logReports = data;
      },
    );
  }

  addItem(event) {
    event.preventDefault()
    const target = event.target
    const message = target.querySelector('#message').value
    const type = target.querySelector('#type').value
    const severity = target.querySelector('#severity').value
    const date = target.querySelector('#date').value
    console.log(message, type, severity, date)
    this.service.addLogReport(this.userId, message, type, severity, date).subscribe(
      data => {
        console.log(data.length)
        console.log(this.logReports.length)
        const lenBefore = this.logReports.length
        const lenAfter = data.length
        if (lenAfter != lenBefore) {
          this.logReports = data
          window.alert("Log report added successfully!")

        } else {
          window.alert("Log report could not be added!")
        }
        this.router.navigate(['user'])
      }
    )
  }

}
