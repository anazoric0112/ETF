import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Class } from '../models/class';
import { News } from '../models/news';
import { ClassHelper } from '../models/classhelper';
import { CommonService } from './common.service';
import { User } from '../models/user';

@Injectable({
    providedIn: 'root'
})
export class StudentService {

    constructor(private http: HttpClient, private common: CommonService) { }

    url: string = "http://localhost:4000/student";

    request_class(c: ClassHelper) {
        const data = {
            student: c.student,
            teacher: c.teacher,
            subject: c.subject,
            link: c.link,
            student_description: c.student_description,
            double: c.double,
            status: c.status,
            start: c.start,
            end: c.end,
            grade_s: c.grade_s,
            comment_s: c.comment_s,
            grade_t: c.grade_t,
            comment_t: c.comment_t
        }
        return this.http.post<number>(`${this.url}/request_class`, data);
    }

    classes_held(u: string) {
        const data = { username: u };
        return this.http.post<ClassHelper[]>(`${this.url}/get_students_classes_held`, data);
    }

    classes_incoming(u: string) {
        const data = { username: u };
        return this.http.post<ClassHelper[]>(`${this.url}/get_students_classes_incoming`, data);
    }

    leave_comment(grade: number, comment: string, link: string, teacher: string) {
        const data = {
            link: link,
            grade: grade,
            comment: comment,
            teacher: teacher
        }
        return this.http.post<number>(`${this.url}/leave_comment_student`, data);
    }

    get_classes_held_for_teacher(u: string) {
        return this.http.post<ClassHelper[]>(`${this.url}/get_classes_held_for_teacher`, { username: u });
    }

    get_news(u: string) {
        const data = { username: u };
        return this.http.post<News[]>(`${this.url}/get_news`, data);
    }

    mark_as_read(id: number) {
        return this.http.post<number>(`${this.url}/mark_as_read`, { id: id });
    }

    make_cls(cls: ClassHelper) {
        let c = new Class();
        this.common.get_user_by_username(cls.student).subscribe(data => { c.student = data });
        this.common.get_user_by_username(cls.teacher).subscribe(data => { c.teacher = data });
        c.subject = cls.subject;
        c.link = cls.link;
        c.student_description = cls.student_description;
        c.double = cls.double;
        c.status = cls.status;
        c.start = cls.start;
        c.end = cls.end;
        c.grade_s = cls.grade_s;
        c.comment_s = cls.comment_s;
        c.grade_t = cls.grade_t;
        c.comment_t = cls.comment_t
        return c;
    }

    get_teachers_on_subject(s: string) {
        return this.http.post<User[]>(`${this.url}/get_teachers_on_subject`, { subject: s });
    }

}
