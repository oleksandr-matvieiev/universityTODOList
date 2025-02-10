import {BrowserRouter as Router, Route, Routes} from 'react-router';
import './App.css';
import React from "react";
import LoginPage from "./component/LoginPage";
import HomePage from "./component/HomePage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/auth/login" element={<LoginPage/>}/>
                <Route path="/dashboard" element={<HomePage/>}/>

            </Routes>
        </Router>
    );
}

export default App;
