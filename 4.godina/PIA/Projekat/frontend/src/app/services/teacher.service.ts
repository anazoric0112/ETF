import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Class } from '../models/class';
import { User } from '../models/user';
import { ClassHelper } from '../models/classhelper';
import { CommonService } from './common.service';

@Injectable({
    providedIn: 'root'
})
export class TeacherService {

    constructor(private http: HttpClient, private common: CommonService) { }

    url: string = "http://localhost:4000/teacher";

    classes_incoming(u: string) {
        const data = { username: u };
        return this.http.post<ClassHelper[]>(`${this.url}/get_teacher_classes_incoming`, data);
    }

    cancel_class(link: string, u: string, comment: string = "") {
        const data = { link: link, username: u, comment: comment };
        return this.http.post<number>(`${this.url}/cancel_class`, data);
    }

    decline_class(link: string, u: string, comment: string = "") {
        const data = { link: link, username: u, comment: comment };
        return this.http.post<number>(`${this.url}/decline_class`, data);
    }

    accept_class(link: string, u: string, comment: string = "") {
        const data = { link: link, username: u, comment: comment };
        return this.http.post<number>(`${this.url}/accept_class`, data);
    }

    get_class_requests(u: string) {
        const data = { username: u };
        return this.http.post<ClassHelper[]>(`${this.url}/get_class_requests`, data);
    }

    get_students_of_teacher(u: string) {
        const data = { username: u };
        return this.http.post<User[]>(`${this.url}/get_students`, data);
    }

    get_classes_held_of_teacher(u: string) {
        const data = { username: u };
        return this.http.post<ClassHelper[]>(`${this.url}/get_teacher_classes_held`, data);
    }

    leave_comment(grade: number, comment: string, link: string, student: string) {
        const data = {
            link: link,
            grade: grade,
            comment: comment,
            student: student
        }
        return this.http.post<number>(`${this.url}/leave_comment_teacher`, data);
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

    get_teacher_not_available(u: string) {
        const data = { username: u };
        return this.http.post<String[]>(`${this.url}/get_teacher_not_available`, data);
    }
    get_teacher_pending(u: string) {
        const data = { username: u };
        return this.http.post<String[]>(`${this.url}/get_teacher_pending`, data);
    }
    get_teacher_booked(u: string) {
        const data = { username: u };
        return this.http.post<String[]>(`${this.url}/get_teacher_booked`, data);
    }

    get_all_classes_of_teacher(username: string) {
        return this.http.get<ClassHelper[]>(`${this.url}/get_all_classes_of_teacher/${username}`);
    }

    add_busy_time(username: string, start: Date, end: Date) {
        const data = {
            username,
            start,
            end
        }

        return this.http.post<number>(`${this.url}/add_busy_time`, data);
    }
}
