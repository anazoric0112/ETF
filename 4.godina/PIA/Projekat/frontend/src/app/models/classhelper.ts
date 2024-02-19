import { Subject } from "./subject";
import { User } from "./user";

export class ClassHelper {
    student: string = "";
    teacher: string = "";
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