import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from '../services/common.service';
import { User } from '../models/user';

@Component({
    selector: 'app-password-reset',
    templateUrl: './password-reset.component.html',
    styleUrls: ['./password-reset.component.css']
})
export class PasswordResetComponent implements OnInit {

    constructor(private router: Router, private service: CommonService) { }

    ngOnInit(): void {
        this.user = JSON.parse(localStorage.getItem("logged")!);
    }

    @Input()
    user: User = new User();

    msg: string = "";
    old: string = "";
    newp: string = "";
    newp2: string = "";

    reset_password() {
        this.msg = "";

        this.service.login(this.user.username, this.old).subscribe(
            data => {
                if (data != null) {
                    if (this.newp != this.newp2) {
                        this.msg = "Passwords don't match";
                        return;
                    }

                    if (!this.check_pw(this.newp)) return;

                    this.service.reset_password(this.user.username, this.newp).subscribe(
                        data => {
                            if (data == "1") {
                                this.msg = "Password changed successfully - you will be redirected to login in 3s";
                                let r = this.router, s = this.service;
                                setTimeout(function () {
                                    localStorage.removeItem("logged");
                                    s.userType = "guest";
                                    r.navigate(["login"]);
                                }, 3000)
                            }
                            else this.msg = "Error - please try again";
                        }
                    )
                }

                this.msg = "Old password does not match";
            }
        )
    }

    check_pw(p: string) {
        if (p.length < 6 || p.length > 10) {
            this.msg = "Password must have between 6 and 10 characters";
            return false;
        }
        let smallLetters = 0, bigLetters = 0, numbers = 0, spec = 0;
        for (let i = 0; i < p.length; i++) {
            let c = p[i];
            if (c >= 'a' && c <= 'z') smallLetters++;
            else if (c >= 'A' && c <= 'Z') bigLetters++;
            else if (c >= '0' && c <= "9") numbers++;
            else spec++;

            if (i == 0 && smallLetters == 0 && bigLetters == 0) {
                this.msg = "Password must start with a letter";
                return false;
            }
        }
        if (spec == 0) {
            this.msg = "Password must contain at least 1 special character";
            return false;
        }
        if (smallLetters < 3) {
            this.msg = "Password must contain at least 3 lowercase letter";
            return false;
        }
        if (bigLetters < 0) {
            this.msg = "Password must contain at least 3 uppercase letter";
            return false;
        }
        if (numbers == 0) {
            this.msg = "Password must contain at least 1 number";
            return false;
        }
        return true;
    }
}
