export class User {
    username: string = "";
    password: string = "";
    sec_question: string = "";
    sec_answer: string = "";
    name: string = "";
    surname: string = "";
    gender: string = "";
    address: string = "";
    phone: string = "";
    email: string = "";
    school_type: string = "";
    year: number = 0; // 1-8 osnovna, 9-12 srednja
    type: string = ""; // teacher/student
    accepted: number = 0;
    subjects: string[] = [];
    additional_subject: string = "";
    ages: string[] = [];
    heard_from: string = "";
    photo: string = "";
    cv: string = "";
    avg_grade: number = 0;
    num_grades: number = 0;
}