import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TeacherService } from '../services/teacher.service';
import { User } from '../models/user';
import { ClassHelper } from '../models/classhelper';

@Component({
    selector: 'app-teacher-mystudents',
    templateUrl: './teacher-mystudents.component.html',
    styleUrl: './teacher-mystudents.component.css'
})
export class TeacherMystudentsComponent implements OnInit {

    constructor(private router: Router, private service: TeacherService) { }

    ngOnInit(): void {
        this.teacher = JSON.parse(localStorage.getItem("logged")!);
        this.service.get_classes_held_of_teacher(this.teacher.username).subscribe(
            data => {
                data.forEach(element => {
                    this.classes_held.push(this.make_class_row(element));
                });
                this.classes_held = this.classes_held.sort((a, b) => {
                    return a.start < b.start ? 1 : -1;
                })
            }
        );
    }

    now: Date = new Date();
    teacher: User = new User();
    classes_held: ClassRow[] = [];
    form_opened: boolean = false;

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
        console.log(c.student);
        console.log(c.grade_to_update);
        if (c.grade_to_update == -1) {
            c.msg = "You didn't touch the stars :)";
            return;
        } else c.msg = "";
        this.service.leave_comment(c.grade_to_update, c.comment_t, c.link, c.student).subscribe(
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

        if (c.status == 1 && row.end.getTime() <= (new Date()).getTime() && row.grade_t == -1) row.can_comment = true;

        for (let i = 1; i <= 5; i++)
            row.stars.push(new StarHelper(i, "../../assets/star_empty.png"));

        return row;
    }

    go_to_profile(user: string) {
        localStorage.setItem("user_profile", user);
        this.router.navigate(["profile_s"]);
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

    can_comment: boolean = false;
}