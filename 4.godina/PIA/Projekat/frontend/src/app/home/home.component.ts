import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from '../services/common.service';
import { Subject } from '../models/subject';
import { User } from '../models/user';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

    constructor(private router: Router, private service: CommonService) { }



    ngOnInit(): void {
        if (localStorage.getItem("logged") != null) {
            let user: User = JSON.parse(localStorage.getItem("logged")!);
            this.service.userType = user.type;
            if (user.type == "student") this.router.navigate(["student_home"]);
            else if (user.type == "teacher") this.router.navigate(["teacher_home"]);
            else if (user.type == "admin") this.router.navigate(["admin_home"]);
        }
        this.service.get_student_number().subscribe(data => { this.n_students = data; });
        this.service.get_teacher_number().subscribe(data => { this.n_teachers = data; });
        this.service.get_classes_number_week().subscribe(data => { this.n_week = data; });
        this.service.get_classes_number_month().subscribe(data => { this.n_month = data; });
        this.service.get_subjects().subscribe(
            data => {
                this.subjects = data;
                for (let i = 0; i < this.subjects.length; i++) {
                    let subject = this.subjects[i];
                    let teachers = subject.teachers;

                    for (let j = 0; j < teachers.length; j++) {
                        let sh = new SubjectHelper();
                        sh.subject_name = subject.name;
                        this.service.get_user_by_username(teachers[j]).subscribe(
                            data => {
                                sh.teacher_name = data.name;
                                sh.teacher_surname = data.surname;
                                console.log(data.username);
                            }
                        )
                        this.subject_helpers.push(sh);
                    }
                }
                this.old = this.subject_helpers;
            });
    }

    n_students: number = 0;
    n_teachers: number = 0;
    n_week: number = 0;
    n_month: number = 0;
    subjects: Subject[] = [];
    subject_helpers: SubjectHelper[] = [];
    old: SubjectHelper[] = [];
    search_name: string = "";
    search_surname: string = "";
    search_subject: string = "";

    sort(desc: number, col: number) {
        // this.subject_helpers = this.old;
        this.subject_helpers = this.subject_helpers.sort((a, b) => {
            if (col == 0 && desc == 0) return a.subject_name > b.subject_name ? 1 : -1;
            if (col == 1 && desc == 0) return a.teacher_name > b.teacher_name ? 1 : -1;
            if (col == 2 && desc == 0) return a.teacher_surname > b.teacher_surname ? 1 : -1;
            if (col == 0 && desc == 1) return a.subject_name < b.subject_name ? 1 : -1;
            if (col == 1 && desc == 1) return a.teacher_name < b.teacher_name ? 1 : -1;
            if (col == 2 && desc == 1) return a.teacher_surname < b.teacher_surname ? 1 : -1;
            return 0;
        });
    }

    search(col: number) {
        this.subject_helpers = this.old;
        this.subject_helpers = this.subject_helpers.filter((a) => {
            if (col == 0) return a.subject_name.includes(this.search_subject);
            if (col == 1) return a.teacher_name.includes(this.search_name);
            if (col == 2) return a.teacher_surname.includes(this.search_surname);
            return -1;
        })
    }

}

class SubjectHelper {
    subject_name: string = "";
    teacher_name: string = "";
    teacher_surname: string = "";
}