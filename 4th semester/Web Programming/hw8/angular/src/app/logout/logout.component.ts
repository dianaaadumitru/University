import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LogReportService } from '../log-report.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private service: LogReportService, private router: Router) { }

  ngOnInit(): void {
    
  }

}
