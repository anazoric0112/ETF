import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from '../services/common.service';
import { User } from '../models/user';
import { AdminService } from "../services/admin.service";
import { Subject } from "../models/subject";

@Component({
    selector: 'app-admin-home',
    templateUrl: './admin-home.component.html',
    styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {

    constructor(private router: Router, private service: CommonService, private admin_service: AdminService) { }

    ngOnInit(): void {
        this.admin = JSON.parse(localStorage.getItem("logged")!);
        this.get_teachers();
        this.get_teacher_requests();
        this.get_subject_requests();
        this.admin_service.get_students().subscribe((data: User[]) => {
            this.students = data;
        });
    }

    admin: User = new User();
    teachers: User[] = [];
    students: User[] = [];
    teacher_requests: User[] = [];
    subject_requests: Subject[] = [];
    subject_name: string = "";
    subject_error_msg: string = "";

    show_requests: boolean = false;
    show_members: boolean = false;
    show_subjects: boolean = false;

    deactivate_teacher(username: string) {
        this.admin_service.delete_teacher(username).subscribe(() => {
            this.get_teachers()
        });
    }

    activate_teacher(username: string) {
        this.admin_service.activate_teacher(username).subscribe(() => {
            this.get_teachers()
        });
    }

    go_to_profile(t: User) {
        localStorage.setItem("chosen_teacher", JSON.stringify(t));
        this.router.navigate(["/teacher_profile"]);
    }

    process_request(username: string, accepted: number) {
        this.admin_service.process_request(username, accepted).subscribe(() => {
            this.get_teacher_requests();
            this.get_teachers();
        });
    }

    accept_subject(name: string) {
        this.admin_service.accept_subject(name).subscribe(() => {
            this.get_subject_requests();
        });
    }

    decline_subject(name: string) {
        this.admin_service.reject_subject(name).subscribe(() => {
            this.get_subject_requests();
        });
    }

    add_subject() {
        if (this.subject_name == "") {
            this.subject_error_msg = "Subject name can't be empty"
            return;
        }
        this.admin_service.add_subject(this.subject_name).subscribe(() => {
            this.subject_name = "";
            this.subject_error_msg = "Successfully added"
        });
    }

    get_teachers() {
        this.admin_service.get_teachers().subscribe((data: User[]) => {
            this.teachers = data;
        });
    }

    get_teacher_requests() {
        this.admin_service.get_requests().subscribe((data: User[]) => {
            this.teacher_requests = data;
        });
    }

    get_subject_requests() {
        this.admin_service.get_subject_requests().subscribe((data: Subject[]) => {
            this.subject_requests = data;
        });
    }

    download_cv(teacher: string) {
        this.admin_service.download_cv(teacher).subscribe(
            response => {
                let dataType = response.type;
                let binaryData = [];
                binaryData.push(response);
                let downloadLink = document.createElement('a');
                downloadLink.href = window.URL.createObjectURL(new Blob(binaryData, { type: dataType }));

                downloadLink.setAttribute('download', teacher + ".pdf");
                document.body.appendChild(downloadLink);
                downloadLink.click();
            }, err => {

            }
        )
    }
}
