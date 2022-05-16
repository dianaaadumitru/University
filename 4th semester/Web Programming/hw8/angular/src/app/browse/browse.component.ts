import { Component, OnInit } from '@angular/core';
import { LogReportService } from '../log-report.service';

@Component({
  selector: 'app-browse',
  templateUrl: './browse.component.html',
  styleUrls: ['./browse.component.css']
})
export class BrowseComponent implements OnInit {
  // pageEvent: PageEvent
  logs: Array<any>
  totalRecords: number
  page: number = 1

  constructor(private service: LogReportService) {
    this.logs = new Array<any>()
   }

  ngOnInit(): void {
  }

  getLogs() {
    this.service.getAll().subscribe((data) => {
      this.logs = data
      this.totalRecords = this.logs.length
      console.log(data.length)
    })
  }

}
