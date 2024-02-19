import { Component, OnInit } from '@angular/core';
import { User } from '../models/user';
import { CommonService } from '../services/common.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-student-profile-for-others',
    templateUrl: './student-profile-for-others.component.html',
    styleUrl: './student-profile-for-others.component.css'
})
export class StudentProfileForOthersComponent implements OnInit {


    constructor(private router: Router, private service: CommonService) { }

    ngOnInit(): void {
        let s = localStorage.getItem("user_profile")!;

        this.service.get_user_by_username(s).subscribe(
            data => {
                this.student = data;
                if (this.student.photo == "") this.path = "../../assets/default-user.png";
                else this.path = "http://localhost:4000/photo/" + this.student.username + ".png";
                this.student.avg_grade = Math.round(this.student.avg_grade * 100) / 100;
            }
        )
    }

    student: User = new User();
    msg: string = "";
    path: string = "";
}