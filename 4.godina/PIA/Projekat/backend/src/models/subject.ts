import mongoose from "mongoose";

const Schema = mongoose.Schema;

let Subject = new Schema({
    name: { type: String, },
    teachers: { type: Array, }, //array of stirngs - usernames
    accepted: { type: Boolean }
});


export default mongoose.model("Subject", Subject, "subject");
