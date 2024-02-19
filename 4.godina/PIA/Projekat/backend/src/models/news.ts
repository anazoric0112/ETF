import mongoose from "mongoose";
import User from "./user";

const Schema = mongoose.Schema;

let News = new Schema({
    id: { type: Number },
    time: { type: Date },
    description: { type: String, },
    seen: { type: Boolean, },
    student: { type: String }, //fk
    teacher: { type: String }, //fk
});


export default mongoose.model("News", News, "news");
