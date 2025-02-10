import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";

const HomePage = () => {
    const [subjects, setSubjects] = useState([]);
    const [error, setError] = useState(null);
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
    const handleSubjectClick = (subjectName) => {
        navigate(`/subject/${encodeURIComponent(subjectName)}/tasks`);
    };

    return (
        <div className="home-container">
            <h2>📚 My subjects</h2>

            {error && <p className="error">{error}</p>}

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
