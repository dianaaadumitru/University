import { Component, OnInit } from '@angular/core';
import { LogReportService } from '../log-report.service';
import { LogReport } from '../logReport';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  username = '';
  id = 0;
  logs = [];

  constructor(private service: LogReportService) { }

  ngOnInit(): void {
    this.username = sessionStorage.getItem('loggedUsername');
    this.id = Number(sessionStorage.getItem('loggedId'));
    // this.getAll();
    // this.logs = this.getUserLogs();
    this.getUserLogs()
    // console.log(this.id)
  }

  getAll () {
    this.service.getAll().subscribe(
      data => {
        this.logs = data;
      }
    );
  }

  getUserLogs() {
    // this.logs = this.logs.filter(res => {
    //   console.log(res.userID)
    //   return res.userID == this.id;
    // })
    this.service.getUserLogs(this.id).subscribe(
      (data: LogReport[]) => {
        this.logs = data;
      }
    )
    
  }

}
