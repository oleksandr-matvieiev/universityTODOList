import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import axios from "axios";

const TaskDetailPage = () => {
    const {taskId} = useParams();
    const [task, setTask] = useState(null);
    const [error, setError] = useState(null);
    const [file, setFile] = useState(null);

    useEffect(() => {
        fetchTaskDetails();
    }, [taskId]);

    const fetchTaskDetails = async () => {
        const token = localStorage.getItem("token");

        if (!token) {
            setError("User is not authorized!");
            return;
        }

        try {
            const response = await axios.get(`http://localhost:8080/api/task/${taskId}`, {
                headers: {Authorization: `Bearer ${token}`}
            });

            setTask(response.data);
        } catch (error) {
            console.error("Error while fetching task:", error);
            setError("Error while fetching task! Please try again");
        }
    };

    const handleFileUpload = async (e) => {
        e.preventDefault();
        if (!file) {
            alert("Choose the file for uploading");
            return;
        }

        const formData = new FormData();
        formData.append("file", file);

        try {
            const token = localStorage.getItem("token");
            await axios.post(`http://localhost:8080/api/task/${taskId}/upload`, formData, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "multipart/form-data"
                }
            });

            alert("Successfully uploaded the file!");
            fetchTaskDetails();
        } catch (error) {
            console.error("Error while upload file:", error);
            setError("Error while download file! Please try again");
        }
    };

    const handleFileDownload = async () => {
        try {
            const token = localStorage.getItem("token");
            const response = await axios.get(`http://localhost:8080/api/task/${taskId}/download`, {
                headers: {Authorization: `Bearer ${token}`},
                responseType: "blob"
            });

            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement("a");
            link.href = url;
            link.setAttribute("download", task.uploadedFile || "file");
            document.body.appendChild(link);
            link.click();
        } catch (error) {
            console.error("Error while download file:", error);
            setError("Error while download file! Please try again");
        }
    };

    if (error) return <p className="error">{error}</p>;
    if (!task) return <p>Loading...</p>;

    return (
        <div className="task-detail-container">
            <NavigationMenu />
            <h2>📌 {task.title}</h2>
            <p><strong>📖 Description:</strong> {task.description}</p>
            <p><strong>📅 Deadline:</strong> {task.deadLine ?? "No"}</p>
            <p><strong>📌 Status:</strong> {task.status}</p>
            <p><strong>📊 Grade:</strong> {task.grade ?? "You have no grade yet"}</p>

            <form onSubmit={handleFileUpload} className="file-upload-form">
                <input type="file" onChange={(e) => setFile(e.target.files[0])}/>
                <button type="submit">📤 Upload file</button>
            </form>

            {task.uploadedFile && (
                <button onClick={handleFileDownload} className="download-btn">
                    ⬇️ Download file
                </button>
            )}
        </div>
    );
};

export default TaskDetailPage;
