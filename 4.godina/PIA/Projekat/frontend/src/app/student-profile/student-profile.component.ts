import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from '../services/common.service';
import { User } from '../models/user';

@Component({
    selector: 'app-student-profile',
    templateUrl: './student-profile.component.html',
    styleUrl: './student-profile.component.css'
})
export class StudentProfileComponent implements OnInit {


    constructor(private router: Router, private service: CommonService) { }

    ngOnInit(): void {
        this.student = JSON.parse(localStorage.getItem("logged")!);
        this.service.get_user_by_username(this.student.username).subscribe(
            data => {
                this.student = data;
                this.prevYear = this.student.year;
                this.prevSchool = this.student.school_type;
                if (this.student.photo == "") this.path = "../../assets/default-user.png";
                else this.path = "http://localhost:4000/photo/" + this.student.username + ".png";
            }
        )
    }

    student: User = new User();
    change: boolean = false;
    prevYear: number = 0;
    prevSchool: string = "";
    msg: string = "";
    path: string = "";
    photoSelected: boolean = false;

    error: boolean = false;
    newPhoto: File = new File([], "");

    show_pw1: boolean = false;
    show_pw2: boolean = false;

    onFileSelected(event: any) {
        this.error = false;
        if (event == null || event.target == null) return;
        else {
            const file: File = event.target.files[0];
            if (file) {
                if (file.size <= 270000 && file.size >= 30000) {
                    this.newPhoto = file;
                    this.msg = "";
                    this.photoSelected = true;
                } else {
                    this.error = true;
                    this.msg = "File must be between 100x100px and 300x300px";
                }
            }
        }
    }


    changeData() {
        if (this.error) return;
        this.msg = "";
        this.error = false;

        if (this.prevYear != this.student.year) {
            if (!(this.prevYear == 8 && this.student.year == 1
                && this.prevSchool == "Primary school"
                && this.student.school_type != "Primary school"
            ) && this.prevYear != this.student.year - 1) {
                this.msg = "Invalid year update";
                return;
            }
        }
        this.service.change_data(this.student).subscribe(
            data => {
                if (!data) {
                    this.msg = "Error occured";
                    return;
                }
                if (this.photoSelected) {
                    const formData = new FormData();
                    formData.append("thumbnail", this.newPhoto);
                    formData.append("username", this.student.username);
                    this.service.upload_picture(formData).subscribe(
                        data => {
                            this.retrieve_user();
                        }
                    )
                } else {
                    this.retrieve_user();
                }
            }
        )
    }

    retrieve_user() {
        this.service.get_user_by_username(this.student.username).subscribe(
            data => {
                if (data == null) return;

                localStorage.setItem("logged", JSON.stringify(data));
                this.student = data;
                this.prevYear = this.student.year;
                this.prevSchool = this.student.school_type;
                if (this.student.photo == "") this.path = "../../assets/default-user.png";
                else this.path = "http://localhost:4000/photo/" + this.student.username + ".png";
                this.change = false;
                this.photoSelected = false;
            }
        )
    }
}