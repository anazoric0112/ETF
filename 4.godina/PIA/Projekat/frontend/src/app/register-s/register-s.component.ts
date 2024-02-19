import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from '../services/common.service';
import { User } from '../models/user';

@Component({
    selector: 'app-register-s',
    templateUrl: './register-s.component.html',
    styleUrls: ['./register-s.component.css']
})
export class RegisterSComponent {

    constructor(private router: Router, private service: CommonService) { }

    msg: string = "";
    user: User = new User();
    photo: string = "";
    error: boolean = false;
    photoFile: File = new File([], "");

    onFileSelected(event: any) {
        this.error = false;
        if (event == null || event.target == null) return;
        else {
            const file: File = event.target.files[0];
            if (file) {
                if (file.size <= 270000 && file.size >= 30000) {
                    this.photo = file.name;
                    this.photoFile = file;
                    this.msg = "";
                } else {
                    this.error = true;
                    this.msg = "File must be between 100x100px and 300x300px";
                }
            }
        }
    }

    register() {
        this.user.type = "student";
        this.user.accepted = 1;
        this.msg = "";

        this.check_fields();
        if (this.error) return;

        if (this.photo == "") {
            this.user.photo = "./../../assets/default-user.png";
        }

        this.service.register_student(this.user).subscribe(
            data => {
                this.msg = data;
                if (data != "Successful registration") return;

                const formData = new FormData();
                formData.append("thumbnail", this.photoFile);
                formData.append("username", this.user.username);
                this.service.upload_picture(formData).subscribe(
                    data => {
                        this.service.get_user_by_username(this.user.username).subscribe(
                            data => {
                                if (data != null) localStorage.setItem("logged", JSON.stringify(data));
                                this.service.userType = "student";
                                this.router.navigate(["student_home"]);
                            }
                        )
                    }
                )
            }
        )
    }

    check_fields() {
        this.error = false;

        //provera praznik polja
        if (this.user.username == "" ||
            this.user.password == "" ||
            this.user.name == "" ||
            this.user.surname == "" ||
            this.user.phone == "" ||
            this.user.email == "" ||
            this.user.address == "" ||
            this.user.gender == "" ||
            this.user.school_type == "" ||
            this.user.sec_answer == "" ||
            this.user.sec_question == "" ||
            this.user.year == 0) {

            this.msg = "You must fill all fields";
            this.error = true;
            return;
        }

        //provera za jedinstven username
        this.service.get_user_by_username(this.user.username).subscribe(
            data => {
                if (data != null) {
                    this.error = true;
                    this.msg = "User with that username already exists.";
                }
            }
        )
        if (this.error) return;
        //provera za jedinstven email
        this.service.get_user_by_email(this.user.email).subscribe(
            data => {
                if (data != null) {
                    this.error = true;
                    this.msg = "User with that email already exists.";
                }
            }
        )
        if (this.error) return;

        //provera za lozinku
        if (this.user.password.length < 6 || this.user.password.length > 10) {
            this.msg = "Password must have between 6 and 10 characters";
            this.error = true;
            return;
        }
        let smallLetters = 0, bigLetters = 0, numbers = 0, spec = 0;
        for (let i = 0; i < this.user.password.length; i++) {
            let c = this.user.password[i];
            if (c >= 'a' && c <= 'z') smallLetters++;
            else if (c >= 'A' && c <= 'Z') bigLetters++;
            else if (c >= '0' && c <= "9") numbers++;
            else spec++;

            if (i == 0 && smallLetters == 0 && bigLetters == 0) {
                this.error = true;
                this.msg = "Password must start with a letter";
                break;
            }
        }
        if (this.error) return;
        if (spec == 0) {
            this.msg = "Password must contain at least 1 special character";
            this.error = true;
            return;
        }
        if (smallLetters < 3) {
            this.msg = "Password must contain at least 3 lowercase letter";
            this.error = true;
            return;
        }
        if (bigLetters == 0) {
            this.msg = "Password must contain at least 1 uppercase letter";
            this.error = true;
            return;
        }
        if (numbers == 0) {
            this.msg = "Password must contain at least 1 number";
            this.error = true;
            return;
        }
    }
}

