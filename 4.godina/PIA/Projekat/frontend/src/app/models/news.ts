import { User } from "./user";

export class News {
    id: number = 0;
    description: string = "";
    seen: boolean = false;
    student: string = "";
    teacher: string = "";
    time: Date = new Date();
}