import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StudentService } from '../services/student.service';
import { User } from '../models/user';
import { Class } from '../models/class';
import { ClassHelper } from '../models/classhelper';

@Component({
    selector: 'app-student-classes',
    templateUrl: './student-classes.component.html',
    styleUrl: './student-classes.component.css'
})
export class StudentClassesComponent implements OnInit {

    constructor(private router: Router, private service: StudentService) { }

    ngOnInit(): void {
        this.student = JSON.parse(localStorage.getItem("logged")!);
        this.service.classes_held(this.student.username).subscribe(
            data => {
                data.forEach(element => {
                    this.classes_held.push(this.make_class_row(element));
                });
                this.classes_held = this.classes_held.sort((a, b) => {
                    return a.start < b.start ? 1 : -1;
                })
            }
        );
        this.service.classes_incoming(this.student.username).subscribe(
            data => {
                data.forEach(element => {
                    this.classes_incoming.push(this.make_class_row(element));
                });
                this.classes_incoming = this.classes_incoming.sort((a, b) => {
                    return a.end > b.end ? 1 : -1;
                })
            }
        )
    }

    now: Date = new Date();
    student: User = new User();
    classes_held: ClassRow[] = [];
    classes_incoming: ClassRow[] = [];
    form_opened: boolean = false;

    show_incoming: boolean = true;
    show_held: boolean = true;

    open_form(c: ClassRow) {
        c.form_opened = true;
    }

    close_form(c: ClassRow) {
        c.form_opened = false;
        let stars_new = [];
        for (let j = 1; j <= 5; j++)
            stars_new.push(new StarHelper(j, "../../assets/star_empty.png"));
        c.stars = stars_new;
        c.grade_to_update = -1;
    }

    submit_comment(c: ClassRow) {
        if (c.grade_to_update == -1) {
            c.msg = "You didn't touch the stars :)";
            return;
        } else c.msg = "";
        this.service.leave_comment(c.grade_to_update, c.comment_s, c.link, c.teacher).subscribe(
            data => {
                this.close_form(c);
                location.reload();
            }
        )
    }

    update_stars(i: number, c: ClassRow) {
        let stars_new = [];
        c.grade_to_update = i;
        for (let j = 0; j < i; j++)
            stars_new.push(new StarHelper(j + 1, "../../assets/star_full.png"));

        for (let j = i; j < 5; j++)
            stars_new.push(new StarHelper(j + 1, "../../assets/star_empty.png"));
        c.stars = stars_new;
    }

    join(link: string) {
        localStorage.setItem("link", link);
        this.router.navigate(["jitsi"]);
    }


    make_class_row(c: ClassHelper) {
        let row = new ClassRow();

        row.start = new Date(c.start);
        row.end = new Date(c.end);
        row.start_time = new Date(c.start).toUTCString();
        row.start_time = row.start_time.slice(5, 25);
        row.end_time = new Date(c.end).toUTCString();
        row.end_time = row.end_time.slice(5, 25);

        row.student = c.student;
        row.teacher = c.teacher;
        row.subject = c.subject;
        row.link = c.link;
        row.student_description = c.student_description;
        row.grade_s = c.grade_s;
        row.comment_s = c.comment_s;
        row.grade_t = c.grade_t;
        row.comment_t = c.comment_t;

        if (row.start.getTime() - (new Date()).getTime() <= 15 * 60 * 1000)
            row.show_link = true;

        for (let i = 1; i <= 5; i++)
            row.stars.push(new StarHelper(i, "../../assets/star_empty.png"));

        return row;
    }

    go_to_profile(user: string) {
        localStorage.setItem("user_profile", user);
        this.router.navigate(["profile_t"]);
    }

}

class StarHelper {
    public id: number = 0;
    public path: string = "";

    constructor(id: number, path: string) {
        this.id = id;
        this.path = path;
    }
}

class ClassRow {
    student: string = "";
    teacher: string = "";
    subject: string = "";
    link: string = "";
    student_description: string = "";

    start: Date = new Date();
    end: Date = new Date();
    start_time: string = "";
    end_time: string = "";

    grade_s: number = -1;
    comment_s: string = "";
    grade_t: number = -1;
    comment_t: string = "";
    form_opened: boolean = false;
    grade_to_update: number = -1;
    stars: StarHelper[] = [];
    msg: string = "";

    show_link: boolean = false;
}