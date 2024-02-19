import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from '../services/common.service';
import { User } from '../models/user';

@Component({
    selector: 'app-teacher-profile',
    templateUrl: './teacher-profile.component.html',
    styleUrl: './teacher-profile.component.css'
})
export class TeacherProfileComponent implements OnInit {


    constructor(private router: Router, private service: CommonService) { }

    ngOnInit(): void {
        this.teacher = JSON.parse(localStorage.getItem("logged")!);
        if (this.teacher.type == "admin") {
            this.admin = true;
            this.teacher = JSON.parse(localStorage.getItem("chosen_teacher")!);
        }
        this.service.get_user_by_username(this.teacher.username).subscribe(
            data => {
                this.teacher = data;
                if (this.teacher.photo == "") this.path = "../../assets/default-user.png";
                else this.path = "http://localhost:4000/photo/" + this.teacher.username + ".png";
            }
        )
        this.service.get_subjects().subscribe(
            data => {
                for (let i = 0; i < data.length; i++)
                    this.subjects.push(data[i].name);
            }
        )
    }

    admin: boolean = false;
    teacher: User = new User();
    subjects: string[] = [];
    change: boolean = false;
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

        let sub_checked = document.querySelectorAll('input[name=subjects]:checked');
        let ages_checked = document.querySelectorAll('input[name=ages]:checked');
        this.teacher.subjects = [];
        sub_checked.forEach(element => {
            this.teacher.subjects.push((element as any).value);
        });
        this.teacher.ages = [];
        ages_checked.forEach(element => {
            this.teacher.ages.push((element as any).value);
        });

        this.service.change_data(this.teacher).subscribe(
            data => {
                if (!data) {
                    this.msg = "Error occured";
                    return;
                }
                if (this.photoSelected) {
                    const formData = new FormData();
                    formData.append("thumbnail", this.newPhoto);
                    formData.append("username", this.teacher.username);
                    this.service.upload_picture(formData).subscribe(
                        data => {
                            this.retrieve_user();
                        }
                    )
                } else this.retrieve_user();
            }
        )
    }

    retrieve_user() {
        this.service.get_user_by_username(this.teacher.username).subscribe(
            data => {
                if (this.admin) {
                    localStorage.setItem("chosen_teacher", JSON.stringify(data));
                } else {
                    localStorage.setItem("logged", JSON.stringify(data));
                }
                this.teacher = data;
                if (this.teacher.photo == "") this.path = "../../assets/default-user.png";
                else this.path = "http://localhost:4000/photo/" + this.teacher.username + ".png";
                this.change = false;
            }
        )
    }
}