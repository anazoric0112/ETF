import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { Class } from '../models/class';
import { Subject } from '../models/subject';

@Injectable({
    providedIn: 'root'
})
export class CommonService {

    constructor(private http: HttpClient) { }

    url: string = "http://localhost:4000/users";

    userType: string = "guest";

    login(u: string, p: string) {
        const data = {
            username: u,
            password: p
        };
        return this.http.post<User>(`${this.url}/login`, data);
    }

    register_student(u: User) {
        const data = {
            username: u.username,
            password: u.password,
            sec_question: u.sec_question,
            sec_answer: u.sec_answer,
            name: u.name,
            surname: u.surname,
            gender: u.gender,
            address: u.address,
            phone: u.phone,
            email: u.email,
            type: u.type,
            accepted: true,
            school_type: u.school_type,
            year: u.year,
            cv: u.cv,
            photo: u.photo,
        };
        return this.http.post<string>(`${this.url}/register`, data)
    }

    register_teacher(u: User) {
        const data = {
            username: u.username,
            password: u.password,
            sec_question: u.sec_question,
            sec_answer: u.sec_answer,
            name: u.name,
            surname: u.surname,
            gender: u.gender,
            address: u.address,
            phone: u.phone,
            email: u.email,
            type: u.type,
            accepted: false,
            subjects: u.subjects,
            ages: u.ages,
            heard_from: u.heard_from,
            cv: u.cv,
            photo: u.photo,
            additional_subject: u.additional_subject
        };
        return this.http.post<string>(`${this.url}/register`, data)
    }

    get_user_by_username(u: string) {
        const data = { username: u };
        return this.http.post<User>(`${this.url}/get_user_by_username`, data);
    }

    get_user_by_email(u: string) {
        const data = { email: u };
        return this.http.post<User>(`${this.url}/get_user_by_email`, data);
    }

    reset_password(u: string, p: string) {
        const data = {
            username: u,
            password: p
        };
        return this.http.post<string>(`${this.url}/reset_password`, data);
    }

    change_data(u: User) {
        const data = {
            username: u.username,
            name: u.name,
            surname: u.surname,
            school_type: u.school_type,
            year: u.year,
            email: u.email,
            phone: u.phone,
            address: u.address,
            ages: u.ages,
            subjects: u.subjects
            //photo: u.photo
        }
        return this.http.post<User>(`${this.url}/change_data`, data);
    }

    get_student_number() {
        return this.http.get<number>(`${this.url}/get_student_number`);
    }

    get_teacher_number() {
        return this.http.get<number>(`${this.url}/get_teacher_number`);
    }

    get_classes_number_week() {
        return this.http.get<number>(`${this.url}/get_last_week_classes_number`);
    }

    get_classes_number_month() {
        return this.http.get<number>(`${this.url}/get_last_month_classes_number`);
    }

    get_subjects() {
        return this.http.get<Subject[]>(`${this.url}/subjects`);
    }

    get_subjects_number() {
        return this.http.get<Subject[]>(`${this.url}/subjects_number`);
    }

    upload_picture(fd: any) {
        return this.http.post<number>(`${this.url}/upload_photo`, fd);
    }

    upload_cv(fd: any) {
        return this.http.post<number>(`${this.url}/upload_cv`, fd);
    }
}
