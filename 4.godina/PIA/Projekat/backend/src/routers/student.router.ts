import express from "express";
import { StudentController } from "../controllers/student.controller";
const studentRouter = express.Router();

studentRouter
    .route("/request_class")
    .post((req, res) => new StudentController().request_class(req, res));

studentRouter
    .route("/get_students_classes_held")
    .post((req, res) => new StudentController().classes_held(req, res));

studentRouter
    .route("/get_students_classes_incoming")
    .post((req, res) => new StudentController().classes_incoming(req, res));

studentRouter
    .route("/leave_comment_student")
    .post((req, res) => new StudentController().leave_comment(req, res));

studentRouter
    .route("/get_news")
    .post((req, res) => new StudentController().get_news(req, res));

studentRouter
    .route("/mark_as_read")
    .post((req, res) => new StudentController().mark_as_read(req, res));

studentRouter
    .route("/get_classes_held_for_teacher")
    .post((req, res) => new StudentController().get_classes_held_for_teacher(req, res));

studentRouter
    .route("/get_teachers_on_subject")
    .post((req, res) => new StudentController().get_teachers_on_subject(req, res));


export default studentRouter;
