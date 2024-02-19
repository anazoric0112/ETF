import { Component, OnInit } from '@angular/core';
import { User } from '../models/user';
import { Router } from '@angular/router';
import { CommonService } from '../services/common.service';

@Component({
    selector: 'app-register-t',
    templateUrl: './register-t.component.html',
    styleUrls: ['./register-t.component.css']
})
export class RegisterTComponent implements OnInit {

    constructor(private router: Router, private service: CommonService) { }

    ngOnInit(): void {
        this.service.get_subjects().subscribe(
            data => {
                for (let i = 0; i < data.length; i++)
                    this.subjects.push(data[i].name);
                console.log(this.subjects);
            }
        )
    }

    msg: string = "";
    user: User = new User();
    photo: string = "";
    error: boolean = false;
    photoFile: File = new File([], "");
    step: number = 1;
    subjects: string[] = [];

    cv: string = "";
    cvFile: File = new File([], "");

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

    back() { this.step = 1 }

    go_next() {

        this.msg = "";
        this.user.type = "teacher";

        this.check_data();
        if (this.error) return;

        // this.step = 2;
        // console.log(this.step);
    }

    register() {
        this.error = false;
        this.msg = "";

        if (this.cv == "") {
            this.msg = "You haven't uploaded a resume";
            this.error = true;
            return;
        }
        let sub_checked = document.querySelectorAll('input[name=subjects]:checked');
        let ages_checked = document.querySelectorAll('input[name=ages]:checked');
        sub_checked.forEach(element => {
            this.user.subjects.push((element as any).value);
        });
        ages_checked.forEach(element => {
            this.user.ages.push((element as any).value);
        });

        if (this.photo == "") {
            this.user.photo = "./../../assets/default-user.png";
        }

        this.service.register_teacher(this.user).subscribe(
            data => {
                this.msg = data;
                this.service.userType = "teacher";
                this.step = 0;

                if (this.photo != "") {
                    console.log(this.photo);
                    const formData1 = new FormData();
                    formData1.append("thumbnail", this.photoFile);
                    formData1.append("username", this.user.username);

                    this.service.upload_picture(formData1).subscribe(
                        data => { }
                    )
                }

                if (this.cv != "") {
                    const formData2 = new FormData();
                    formData2.append("thumbnail", this.cvFile);
                    formData2.append("username", this.user.username);
                    this.service.upload_cv(formData2).subscribe(
                        data => { }
                    )
                }
            }
        )
    }


    onCvSelected(event: any) {
        this.error = false;
        if (event == null || event.target == null) return;
        else {
            const file: File = event.target.files[0];
            if (file) {
                if (file.size <= 3000000) {
                    this.cv = file.name;
                    this.cvFile = file;
                    this.msg = "";
                } else {
                    this.error = true;
                    this.msg = "File must be <= 3MB";
                }

            }
        }
    }

    check_data() {
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
            this.user.sec_answer == "" ||
            this.user.sec_question == "") {

            this.msg = "You must fill all fields";
            this.error = true;
            return;
        }


        //provera za jedinstven username
        this.service.get_user_by_username(this.user.username).subscribe(
            data => {
                if (data) {
                    this.error = true;
                    this.msg = "User with that username already exists.";
                    return;
                }
                this.service.get_user_by_email(this.user.email).subscribe(
                    data => {
                        if (data) {
                            this.error = true;
                            this.msg = "User with that email already exists.";
                            return;
                        }
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
                                return;
                            }
                        }
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
                        if (bigLetters < 0) {
                            this.msg = "Password must contain at least 3 uppercase letter";
                            this.error = true;
                            return;
                        }
                        if (numbers == 0) {
                            this.msg = "Password must contain at least 1 number";
                            this.error = true;
                            return;
                        }
                        this.step = 2;
                    }
                )
            }
        )

    }
}
