import { Component, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from '../services/common.service';
import { User } from '../models/user';
import { AppComponent } from '../app.component';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent {

    constructor(public router: Router, private service: CommonService) { }

    msg: string = "";
    user: User = new User();

    login() {
        this.service.login(this.user.username, this.user.password).subscribe(
            data => {
                if (data == null) {
                    this.msg = "Wrong credentials";
                    return;
                }
                this.msg = "";
                if (data.type == "student") {
                    localStorage.setItem("logged", JSON.stringify(data));
                    this.service.userType = "student";
                    this.router.navigate(['student_profile']);
                }
                else if (data.accepted == -1) this.msg = "Your request has been denied";
                else if (data.accepted == 0) this.msg = "Your request is pending approval";
                else {
                    localStorage.setItem("logged", JSON.stringify(data));
                    this.service.userType = "teacher";
                    this.router.navigate(['teacher_profile']);
                }
            }
        )
    }
}
