import { Component, OnInit } from '@angular/core';
import { LogReportService } from '../log-report.service';
import { LogReport } from '../logReport';


@Component({
  selector: 'app-filter2',
  templateUrl: './filter2.component.html',
  styleUrls: ['./filter2.component.css']
})
export class Filter2Component implements OnInit {

  title = 'lab8';
  logs: LogReport[] = [];
  severities: any = [];
  types: any = [];
  sev: any;
  type: any;


  error = '';
  success = '';
  currentFilter = 'None';
  prevFilter = 'None';
  filtered = false;

  constructor(private logsService: LogReportService) { }

  ngOnInit() {

  }

  getLogs() {
    this.logsService.getAll().subscribe(
      data => {
        this.logs = data;
        this.success = 'successful retrieval of the list';
        console.log(this.success)
      },
      (err) => {
        console.log(err);
        this.error = err;
      }
    );
  }

  getSeverities(): void {
    this.logsService.getAllSeverities().subscribe(
      data => {
        this.logs = data;
        this.success = 'successful retrieval of the list';
        console.log(this.success)
      },
      (err) => {
        console.log(err);
        this.error = err;
      }
    );
  }

  filterSeverity (severity: string): void {
    if (severity == "choose severity") {
      this.getLogs()
    } else {
    this.logsService.filter1(severity).subscribe(
      (data: LogReport[]) => {
        this.logs = data;
        this.prevFilter = this.currentFilter;
      this.currentFilter = severity;
      this.filtered = true;
      },
      (err) => {
        console.log(err);
        this.error = err;
      }
    );
    }
  }

  filterType(type: string): void {
    if (type == "choose type") {
      this.logs = []
    } else {
    this.logsService.filter1(type).subscribe(
      (data: LogReport[]) => {
        this.logs = data;
        this.prevFilter = this.currentFilter;
      this.currentFilter = type;
      this.filtered = true;
      },
      (err) => {
        console.log(err);
        this.error = err;
      }
    );
    }
  }

}
