import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { StudentHomeComponent } from './student-home/student-home.component';
import { TeacherHomeComponent } from './teacher-home/teacher-home.component';
import { RegisterTComponent } from './register-t/register-t.component';
import { RegisterSComponent } from './register-s/register-s.component';
import { PasswordResetComponent } from './password-reset/password-reset.component';
import { PasswordResetHomeComponent } from './password-reset-home/password-reset-home.component';
import { AdminLoginComponent } from './admin-login/admin-login.component';
import { AdminHomeComponent } from './admin-home/admin-home.component'
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { StudentClassesComponent } from './student-classes/student-classes.component';
import { StudentProfileComponent } from './student-profile/student-profile.component';
import { StudentNewsComponent } from './student-news/student-news.component';
import { AdminStatisticsComponent } from './admin-statistics/admin-statistics.component';
import { TeacherMystudentsComponent } from './teacher-mystudents/teacher-mystudents.component';
import { TeacherProfileComponent } from './teacher-profile/teacher-profile.component';
import { StudentProfileForOthersComponent } from './student-profile-for-others/student-profile-for-others.component';
import { TeacherProfileForOthersComponent } from './teacher-profile-for-others/teacher-profile-for-others.component';
import { CanvasJSAngularChartsModule } from "@canvasjs/angular-charts";
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        HomeComponent,
        RegisterTComponent,
        RegisterSComponent,
        PasswordResetComponent,
        PasswordResetHomeComponent,
        AdminLoginComponent,
        AdminHomeComponent,
        AdminStatisticsComponent,
        TeacherHomeComponent,
        TeacherMystudentsComponent,
        TeacherProfileComponent,
        TeacherProfileForOthersComponent,
        StudentHomeComponent,
        StudentClassesComponent,
        StudentProfileComponent,
        StudentNewsComponent,
        StudentProfileForOthersComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        NgbModule,
        CanvasJSAngularChartsModule,
        CalendarModule.forRoot({ provide: DateAdapter, useFactory: adapterFactory })
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }
