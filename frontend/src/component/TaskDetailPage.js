import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import NavigationMenu from "./NavigationMenu";

const TaskDetailPage = () => {
    const {taskId} = useParams();
    const navigate = useNavigate();
    const [task, setTask] = useState(null);
    const [error, setError] = useState(null);
    const [file, setFile] = useState(null);
    const [newStatus, setNewStatus] = useState("");
    const [newGrade, setNewGrade] = useState("");

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
            setError("Error while upload file! Please try again");
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

    const handleChangeStatus = async () => {
        if (!newStatus) {
            alert("Choose a status");
            return;
        }

        try {
            const token = localStorage.getItem("token");
            await axios.post(
                `http://localhost:8080/api/task/changeStatus/${taskId}`,
                null,
                {
                    params: {taskStatus: newStatus},
                    headers: {Authorization: `Bearer ${token}`}
                }
            );

            alert("Status successfully updated!");
            fetchTaskDetails();
        } catch (error) {
            console.error("Error while updating status:", error);
            setError("Error while updating status! Please try again");
        }
    };

    const handleGiveGrade = async () => {
        if (!newGrade || isNaN(newGrade) || newGrade < 0 || newGrade > 100) {
            alert("Enter a valid grade (0-100)");
            return;
        }

        try {
            const token = localStorage.getItem("token");
            await axios.post(
                `http://localhost:8080/api/task/giveGrade/${taskId}`,
                null,
                {
                    params: {grade: newGrade},
                    headers: {Authorization: `Bearer ${token}`}
                }
            );

            alert("Grade successfully given!");
            fetchTaskDetails();
        } catch (error) {
            console.error("Error while giving grade:", error);
            setError("Error while giving grade! Please try again");
        }
    };
    const handleDeleteTask = async () => {
        const confirmed = window.confirm("Are you sure you want to delete this task?");
        if (!confirmed) return;

        try {
            const token = localStorage.getItem("token");
            await axios.delete(`http://localhost:8080/api/task/deleteTask/${taskId}`, {
                headers: {Authorization: `Bearer ${token}`}
            });

            alert("Task successfully deleted!");
            navigate("/dashboard");
        }catch (error){
            console.error("Error while deleting task:", error);
            setError("Error while deleting task! Please try again");
        }
    };


    if (error) return <p className="error">{error}</p>;
    if (!task) return <p>Loading...</p>;

    return (
        <div className="task-detail-container">
            <NavigationMenu/>
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

            <div className="task-actions">
                <label>
                    Change Status:
                    <select onChange={(e) => setNewStatus(e.target.value)} value={newStatus}>
                        <option value="">Select status</option>
                        <option value="PENDING">PENDING</option>
                        <option value="IN_PROGRESS">IN PROGRESS</option>
                        <option value="COMPLETED">COMPLETED</option>
                    </select>
                </label>
                <button onClick={handleChangeStatus}>✔️ Update Status</button>

                <label>
                    Give Grade:
                    <input
                        type="number"
                        value={newGrade}
                        onChange={(e) => setNewGrade(e.target.value)}
                        placeholder="Enter grade (0-100)"
                    />
                </label>
                <button onClick={handleGiveGrade}>🏆 Give Grade</button>
            </div>
            <button onClick={handleDeleteTask} className="delete-btn"
                    style={{backgroundColor: "red", color: "white", marginTop: "20px"}}>
                🗑️ Delete Task
            </button>
        </div>
    );
};

export default TaskDetailPage;
