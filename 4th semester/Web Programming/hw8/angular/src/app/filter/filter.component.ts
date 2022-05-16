import { Component, OnInit } from '@angular/core';
import { LogReportService } from '../log-report.service';
import { LogReport } from '../logReport';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css']
})
export class FilterComponent implements OnInit {
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

  getTypes(): void {
    this.logsService.getAllTypes().subscribe(
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

  filterType(type: string): void {
    if (type == "choose type") {
      this.getLogs()
    } else {
    this.logsService.filter2(type).subscribe(
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


