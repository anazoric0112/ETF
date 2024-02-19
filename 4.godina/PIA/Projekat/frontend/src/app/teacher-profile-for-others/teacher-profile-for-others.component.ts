import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from '../services/common.service';
import { User } from '../models/user';
import { TeacherService } from '../services/teacher.service';
import { StudentService } from '../services/student.service';

@Component({
    selector: 'app-teacher-profile-for-others',
    templateUrl: './teacher-profile-for-others.component.html',
    styleUrl: './teacher-profile-for-others.component.css'
})
export class TeacherProfileForOthersComponent implements OnInit {


    constructor(private router: Router, private service: CommonService, private studentService: StudentService) { }

    ngOnInit(): void {
        let username = localStorage.getItem("user_profile")!;
        this.service.get_user_by_username(username).subscribe(
            data => {
                this.teacher = data;
                if (this.teacher.photo == "") this.path = "../../assets/default-user.png";
                else this.path = "http://localhost:4000/photo/" + this.teacher.photo;
                this.service.get_subjects().subscribe(
                    data => {
                        for (let i = 0; i < data.length; i++)
                            this.subjects.push(data[i].name);
                        console.log(this.subjects);
                    }
                )
                this.studentService.get_classes_held_for_teacher(username).subscribe(
                    data => {
                        data.forEach(class_held => {
                            if (class_held.grade_s != -1) {
                                let comment = new CommentHelper();
                                comment.grade = class_held.grade_s;
                                comment.by = class_held.student;
                                comment.comment = class_held.comment_s;
                                for (let i = 0; i < comment.grade; i++)
                                    comment.stars.push("../../assets/star_full.png");

                                for (let i = comment.grade; i < 5; i++)
                                    comment.stars.push("../../assets/star_empty.png");

                                this.comments.push(comment);
                            }
                        });
                    }
                )
            }
        )
    }

    teacher: User = new User();
    subjects: string[] = [];
    path: string = "";
    comments: CommentHelper[] = [];

}

class CommentHelper {
    comment: string = "";
    by: string = "";
    grade: number = 0;
    stars: string[] = [];
}