import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { LogReport } from './logReport';

interface myData {
  success: string
}

@Injectable({
  providedIn: 'root'
})
export class LogReportService {
  baseUrl = 'http://localhost/api'

  constructor(private http: HttpClient) { }


  getUserDetails(username: string, password: string) {
    return this.http.post<any>('http://localhost/api/login.php', {
        "Username": username, 
        "Password": password,
    }).pipe(
      map((res: any) => {
        return res['data'];
      })
    );
  }

  getAll() {
    return this.http.get('http://localhost/api/allLogReports').pipe(
      map((res: any) => {
        return res['data']
      })
    )
  }

  addLogReport(userID: any, message: any, type: any, severity: any, date: any) {
    return this.http.post<any>('http://localhost/api/addReport.php', {
      "userID": userID,
      "message": message,
      "type": type,
      "severity": severity,
      "posted_on": date
    }).pipe(
      map((res: any) => {
        return res['data'];
      })
    );
  }

  // addLogReport(logReport: LogReport) {
  //   return this.http.post('http://localhost/api/addReport.php', {
  //     data: logReport
  //   }).pipe(
  //     map((res: any) => {
  //       return res['data'];
  //     })
  //   );
  // }

  getAllTypes() {
    return this.http.get('http://localhost/api/allTypes').pipe(
      map((res: any) => {
        return res['data']
      })
    )
  }

  getAllSeverities() {
    return this.http.get('http://localhost/api/allSeverities').pipe(
      map((res: any) => {
        return res['data']
      })
    )
  }

  filter1(severity: string) {
    return this.http.post<any>('http://localhost/api/filter.php', {
      Filter: severity,
    }).pipe(
      map((res: any) => {
        return res['data'];
      })
    );
  }

  filter2(type: string) {
    return this.http.post<any>('http://localhost/api/filter2.php', {
      Filter: type,
    }).pipe(
      map((res: any) => {
        return res['data'];
      })
    );
  }

  getUserLogs (id: number) {
    return this.http.post<any>('http://localhost/api/userLogs.php', {
      UserId: id,
    }).pipe(
      map((res: any) => {
        return res['data'];
      })
    );
  }

  removeLogReport(id: any, userId: any) {
    const params = new HttpParams()
    .set('id', id.toString())
    .set('userID', userId.toString());

    return this.http.delete('http://localhost/api/removeReport.php', {
      params: params 
    }
      
    );
  }

}


