import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from '../services/common.service';
import { User } from '../models/user';

@Component({
    selector: 'app-admin-login',
    templateUrl: './admin-login.component.html',
    styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent {

    constructor(private router: Router, private service: CommonService) { }

    msg: string = "";
    user: User = new User();

    login() {
        this.msg = "";

        this.service.get_user_by_username(this.user.username).subscribe(
            data => {
                if (data.type != "admin") {
                    this.msg = "Vi niste admin";
                } else this.service.login(this.user.username, this.user.password).subscribe(
                    data => {
                        if (data == null) {
                            this.msg = "Pogresni kredencijali";
                            return;
                        }

                        localStorage.setItem("logged", JSON.stringify(data));
                        this.service.userType = "admin";
                        this.router.navigate(['admin_home']);
                    }
                )
            }
        )

    }
}
