import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StudentService } from '../services/student.service';
import { User } from '../models/user';
import { News } from '../models/news';

@Component({
    selector: 'app-student-news',
    templateUrl: './student-news.component.html',
    styleUrl: './student-news.component.css'
})
export class StudentNewsComponent implements OnInit {

    constructor(private router: Router, private service: StudentService) { }

    ngOnInit(): void {
        this.student = JSON.parse(localStorage.getItem("logged")!);
        this.service.get_news(this.student.username).subscribe(
            data => {
                this.news = data.sort((a, b) => {
                    return a.time < b.time ? 1 : -1;
                })
                this.news.forEach(element => {
                    element.student = (new Date(element.time)).toUTCString().slice(5, 25);
                });
                console.log(this.news);
            }
        )
    }

    student: User = new User();
    news: News[] = [];

    mark_as_read(id: number) {
        this.service.mark_as_read(id).subscribe(data => { });
        location.reload();
    }
}
