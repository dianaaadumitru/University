import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LogReportService } from '../log-report.service';
import { LogReport } from '../logReport';

@Component({
  selector: 'app-remove-report',
  templateUrl: './remove-report.component.html',
  styleUrls: ['./remove-report.component.css']
})
export class RemoveReportComponent implements OnInit {

  logs: LogReport[] = []
  userId = 0;
  // currentLength: number = 0

  constructor(private service: LogReportService, private router: Router) { }

  ngOnInit(): void {
    this.getLogs();
    this.userId = Number(sessionStorage.getItem('loggedId'));
  }

  getLogs() {
    this.service.getAll().subscribe(
      data => {
        this.logs = data;
      }
    );
  }

  removeItem(event) {
    event.preventDefault()
    const target = event.target
    const id = target.querySelector('#id').value
    // console.log(id)

    this.service.removeLogReport(id, this.userId).subscribe(
      ( data: any ) => {
        // console.log("data: ", data.length)
        console.log("logs before: ", this.logs.length)
        const lenBefore = this.logs.length;
        this.logs = this.logs.filter(function (item) {
          return item['id'] && +item['id'] !== +id;
        })

        console.log("logs after: ", this.logs.length)
        const lenAfter = this.logs.length;

        if (lenAfter != lenBefore){
          window.alert('Log report deleted successfully!');
        } else {
          window.alert('Log report does not exist or it does not belong to the current user!');
        }
        
        
        this.router.navigate(['user'])
      }
    )
  }
}
