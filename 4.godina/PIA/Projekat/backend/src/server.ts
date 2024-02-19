import express from 'express';
import cors from 'cors';
import mongoose from 'mongoose';
import userRouter from './routers/user.router';
import adminRouter from './routers/admin.router';
import studentRouter from './routers/student.router';
import teacherRouter from './routers/teacher.router';

const app = express();
const fileUpload = require('express-fileupload');
app.use(cors());
app.use(express.json());
app.use(fileUpload())
app.use("/photo", express.static("photo"));

mongoose.connect("mongodb://127.0.0.1:27017/omiljeni_nastavnik");
const connection = mongoose.connection;
connection.once("open", () => {
    console.log("db connection ok");
});

const router = express.Router();
router.use("/users", userRouter);
router.use("/admin", adminRouter);
router.use("/student", studentRouter);
router.use("/teacher", teacherRouter);


app.use('/', router);
app.listen(4000, () => console.log(`Express server running on port 4000`));