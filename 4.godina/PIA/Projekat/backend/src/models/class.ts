import mongoose from "mongoose";
import User from "./user";
import Subject from "./subject";

const Schema = mongoose.Schema;

let Class = new Schema({

    student: { type: String }, //fk
    teacher: { type: String }, //fk
    subject: { type: String }, //fk
    link: { type: String },
    student_description: { type: String },
    double: { type: Boolean },
    status: { type: Number }, //0-pending, -1 no, 1 yes
    start: { type: Date },
    end: { type: Date },
    grade_s: { type: Number },
    comment_s: { type: String },
    grade_t: { type: Number },
    comment_t: { type: String },
});


export default mongoose.model("Class", Class, "class");
