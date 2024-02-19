import mongoose from "mongoose";

const Schema = mongoose.Schema;

let User = new Schema({
    username: { type: String, },
    password: { type: String, },
    sec_question: { type: String, },
    sec_answer: { type: String, },
    name: { type: String, },
    surname: { type: String, },
    gender: { type: String, }, //female/male
    address: { type: String, },
    phone: { type: String, },
    email: { type: String, },
    school_type: { type: String, }, //Primary school/Grammar school/High school - technical/High school - art
    year: { type: Number, }, //1-8
    type: { type: String, }, // student/teacher/admin
    accepted: { type: Number, }, // 0 - inicijalno, 1 - prihvacen, -1 - odbijen, -2 - deaktiviran
    subjects: { type: Array, }, //array of strings
    additional_subject: { type: String },
    ages: { type: Array, }, //array of strings 1-4/5-8/9-12
    heard_from: { type: String, },
    cv: { type: String },
    photo: { type: String },
    avg_grade: { type: Number },
    num_grades: { type: Number },
});


export default mongoose.model("User", User, "user");
