import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { Subject } from "../models/subject";

@Injectable({
    providedIn: 'root'
})
export class AdminService {

    constructor(private http: HttpClient) { }

    url: string = "http://localhost:4000/admin";

    get_requests() {
        return this.http.get<User[]>(`${this.url}/get_teacher_requests`);
    }

    get_students() {
        return this.http.get<User[]>(`${this.url}/get_students`);
    }

    get_teachers() {
        return this.http.get<User[]>(`${this.url}/get_teachers`);
    }

    get_classes() {
        return this.http.get<any[]>(`${this.url}/get_classes`);
    }

    get_subject_requests() {
        return this.http.get<Subject[]>(`${this.url}/get_subject_requests`);
    }

    process_request(username: string, accepted: number) {
        const data = {
            username,
            accepted
        }
        return this.http.post<number>(`${this.url}/process_request`, data);
    }

    delete_teacher(username: string) {
        return this.http.delete<number>(`${this.url}/delete_teacher/${username}`);
    }

    activate_teacher(username: string) {
        return this.http.patch<number>(`${this.url}/activate_teacher/${username}`, {});
    }

    accept_subject(name: string) {
        return this.http.patch<number>(`${this.url}/accept_subject/${name}`, {});
    }

    reject_subject(name: string) {
        return this.http.delete<number>(`${this.url}/reject_subject/${name}`);
    }

    add_subject(name: string) {
        return this.http.post<number>(`${this.url}/add_subject`, { name });
    }

    download_cv(teacher: string) {
        let headers = new HttpHeaders();
        return this.http.get<Blob>(`${this.url}/get_cv/${teacher}`, { headers: headers, responseType: 'blob' as 'json' });
    }
}
