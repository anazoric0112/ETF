import express from "express";
import { TeacherController } from "../controllers/teacher.controller";
const teacherRouter = express.Router();

teacherRouter
    .route("/get_teacher_classes_incoming")
    .post((req, res) => new TeacherController().classes_incoming(req, res));

teacherRouter
    .route("/get_teacher_available")
    .post((req, res) => new TeacherController().get_availability(req, res));

teacherRouter
    .route("/cancel_class")
    .post((req, res) => new TeacherController().set_class_status(req, res, -2));

teacherRouter
    .route("/decline_class")
    .post((req, res) => new TeacherController().set_class_status(req, res, -1));

teacherRouter
    .route("/accept_class")
    .post((req, res) => new TeacherController().set_class_status(req, res, 1));

teacherRouter
    .route("/get_class_requests")
    .post((req, res) => new TeacherController().class_requests(req, res));

teacherRouter
    .route("/get_students")
    .post((req, res) => new TeacherController().students(req, res));

teacherRouter
    .route("/get_teacher_classes_held")
    .post((req, res) => new TeacherController().get_teacher_classes_held(req, res));

teacherRouter
    .route("/leave_comment_teacher")
    .post((req, res) => new TeacherController().leave_comment(req, res));

teacherRouter
    .route("/get_teacher_not_available")
    .post((req, res) => new TeacherController().get_teacher_not_available(req, res));

teacherRouter
    .route("/get_teacher_pending")
    .post((req, res) => new TeacherController().get_teacher_pending(req, res));

teacherRouter
    .route("/get_teacher_booked")
    .post((req, res) => new TeacherController().get_teacher_booked(req, res));

teacherRouter
    .route("/get_all_classes_of_teacher/:username")
    .get((req, res) => new TeacherController().get_all_classes_of_teacher(req, res));

teacherRouter
    .route("/add_busy_time")
    .post((req, res) => new TeacherController().add_busy_time(req, res));

export default teacherRouter;
