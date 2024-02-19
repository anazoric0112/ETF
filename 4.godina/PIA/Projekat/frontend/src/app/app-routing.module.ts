import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { StudentHomeComponent } from './student-home/student-home.component';
import { TeacherHomeComponent } from './teacher-home/teacher-home.component';
import { RegisterSComponent } from './register-s/register-s.component';
import { RegisterTComponent } from './register-t/register-t.component';
import { PasswordResetComponent } from './password-reset/password-reset.component';
import { PasswordResetHomeComponent } from './password-reset-home/password-reset-home.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminLoginComponent } from './admin-login/admin-login.component';
import { AdminStatisticsComponent } from './admin-statistics/admin-statistics.component';
import { StudentClassesComponent } from './student-classes/student-classes.component';
import { StudentNewsComponent } from './student-news/student-news.component';
import { StudentProfileComponent } from './student-profile/student-profile.component';
import { TeacherMystudentsComponent } from './teacher-mystudents/teacher-mystudents.component';
import { TeacherProfileComponent } from './teacher-profile/teacher-profile.component';
import { TeacherProfileForOthersComponent } from './teacher-profile-for-others/teacher-profile-for-others.component';
import { StudentProfileForOthersComponent } from './student-profile-for-others/student-profile-for-others.component';
import { JitsiComponent } from './jitsi/jitsi.component';

const routes: Routes = [
    { path: "", component: HomeComponent },
    { path: "login", component: LoginComponent },
    { path: "password_reset", component: PasswordResetComponent },
    { path: "password_reset_home", component: PasswordResetHomeComponent },
    { path: "register_student", component: RegisterSComponent },
    { path: "register_teacher", component: RegisterTComponent },

    { path: "admin_login", component: AdminLoginComponent },
    { path: "admin_home", component: AdminHomeComponent },
    { path: "admin_statistics", component: AdminStatisticsComponent },

    { path: "student_home", component: StudentHomeComponent },
    { path: "student_classes", component: StudentClassesComponent },
    { path: "student_news", component: StudentNewsComponent },
    { path: "student_profile", component: StudentProfileComponent },
    { path: "teacher_home", component: TeacherHomeComponent },
    { path: "teacher_mystudents", component: TeacherMystudentsComponent },
    { path: "teacher_profile", component: TeacherProfileComponent },

    { path: "profile_t", component: TeacherProfileForOthersComponent },
    { path: "profile_s", component: StudentProfileForOthersComponent },

    { path: "jitsi", component: JitsiComponent }

];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
