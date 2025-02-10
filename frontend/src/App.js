import {BrowserRouter as Router, Route, Routes} from 'react-router';
import './App.css';
import React from "react";
import LoginPage from "./component/LoginPage";
import HomePage from "./component/HomePage";
import TaskPage from "./component/TaskPage";
import TaskDetailPage from "./component/TaskDetailPage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/auth/login" element={<LoginPage/>}/>
                <Route path="/dashboard" element={<HomePage/>}/>
                <Route path="/subject/:subjectName/tasks" element={<TaskPage/>}/>
                <Route path="/task/:taskId" element={<TaskDetailPage/>}/>

            </Routes>
        </Router>
    );
}

export default App;
