import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgxPaginationModule } from 'ngx-pagination';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { UserComponent } from './user/user.component';
import { FilterComponent } from './filter/filter.component';
import { BrowseComponent } from './browse/browse.component';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Filter2Component } from './filter2/filter2.component';
import { AddReportComponent } from './add-report/add-report.component';
import { RemoveReportComponent } from './remove-report/remove-report.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    LogoutComponent,
    UserComponent,
    FilterComponent,
    BrowseComponent,
    Filter2Component,
    AddReportComponent,
    RemoveReportComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgxPaginationModule,
    RouterModule.forRoot([
      {
        path: '',
        component: HomeComponent
      },
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'logout',
        component: LogoutComponent
      },
      {
        path: 'user',
        component: UserComponent
      },
      {
        path: 'filter_type',
        component: FilterComponent
      },
      {
        path: 'filter_severity',
        component: Filter2Component
      },
      {
        path: 'browse',
        component: BrowseComponent
      },
      {
        path: 'add_report',
        component: AddReportComponent
      },
      {
        path: 'remove_report',
        component: RemoveReportComponent
      },
    ])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
