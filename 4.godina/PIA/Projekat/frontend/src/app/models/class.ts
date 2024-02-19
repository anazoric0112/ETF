import { Subject } from "./subject";
import { User } from "./user";

export class Class {
    student: User = new User();
    teacher: User = new User();
    subject: string = "";
    link: string = "";
    student_description: string = "";
    double: boolean = false;
    status: number = 0; //0-pending, -1 no, 1 yes
    start: Date = new Date();
    end: Date = new Date();
    grade_s: number = -1;
    comment_s: string = "";
    grade_t: number = -1;
    comment_t: string = "";
}