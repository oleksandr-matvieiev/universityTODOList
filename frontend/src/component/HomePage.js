import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";

import "./HomePage.css";

const HomePage = () => {
    const [subjects, setSubjects] = useState([]);
    const [error, setError] = useState(null);
    const [newSubject, setNewSubject] = useState({name: "", description: ""});
    const navigate = useNavigate();

    useEffect(() => {
        const fetchSubjects = async () => {
            const token = localStorage.getItem("token");

            if (!token) {
                setError("User is not authorized!");
                return;
            }

            try {
                const response = await axios.get("http://localhost:8080/api/subject/get-all", {
                    headers: {Authorization: `Bearer ${token}`}
                });

                setSubjects(response.data);
            } catch (error) {
                console.error("Error fetching subjects:", error);
                setError("Error fetching subjects!");
            }
        };

        fetchSubjects();
    }, []);

    const handleCreateSubject = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem("token");

        if (!newSubject.name.trim() || !newSubject.description.trim()) {
            alert("Назва та опис не можуть бути порожніми!");
            return;
        }

        try {
            const response = await axios.post("http://localhost:8080/api/subject/create", newSubject, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json"
                }
            });

            setSubjects([...subjects, response.data]);
            setNewSubject({name: "", description: ""});
            alert("Successfully created the subject!");
        } catch (error) {
            console.error("Error while creating subject:", error);
            setError("Error while creating subject! Please try again");
        }
    };


    const handleSubjectClick = (subjectName) => {
        navigate(`/subject/${encodeURIComponent(subjectName)}/tasks`);
    };


    return (
        <div className="home-container">
            <h2>📚 My subjects</h2>

            {error && <p className="error">{error}</p>}

            <form onSubmit={handleCreateSubject} className="subject-form">
                <input
                    type="text"
                    placeholder="Subject name"
                    value={newSubject.name}
                    onChange={(e) => setNewSubject({...newSubject, name: e.target.value})}
                    required
                />
                <input
                    type="text"
                    placeholder="Subject description"
                    value={newSubject.description}
                    onChange={(e) => setNewSubject({...newSubject, description: e.target.value})}
                    required
                />
                <button type="submit">➕ Add subject</button>
            </form>

            {subjects.length === 0 && !error ? (
                <p>No subjects yet 😢</p>
            ) : (
                <ul className="subject-list">
                    {subjects.map((subject) => (
                        <li key={subject.id} className="subject-card" onClick={() => handleSubjectClick(subject.name)}>
                            <h3>{subject.name}</h3>
                            <p>{subject.description}</p>
                            <p><strong>📊 Average grade:</strong> {subject.averageGrade ?? "Not avaliable"}</p>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default HomePage;
