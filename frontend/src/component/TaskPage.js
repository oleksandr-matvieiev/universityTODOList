import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

const TaskPage = () => {
    const { subjectName } = useParams();
    const [tasks, setTasks] = useState([]);
    const [filteredTasks, setFilteredTasks] = useState([]);
    const [statusFilter, setStatusFilter] = useState("");
    const [error, setError] = useState(null);
    const [newTask, setNewTask] = useState({ title: "", description: "", deadLine: "" });
    const navigate = useNavigate();

    useEffect(() => {
        fetchTasks();
    }, [subjectName]);

    const fetchTasks = async () => {
        const token = localStorage.getItem("token");

        if (!token) {
            setError("User is not authorized!");
            return;
        }

        try {
            const response = await axios.get(`http://localhost:8080/api/task/${encodeURIComponent(subjectName)}/get-all`, {
                headers: { Authorization: `Bearer ${token}` }
            });

            setTasks(response.data);
            setFilteredTasks(response.data);
        } catch (error) {
            console.error("Error fetching tasks:", error);
            setError("Error fetching tasks!");
        }
    };

    const handleFilterChange = async (status) => {
        setStatusFilter(status);

        if (!status) {
            setFilteredTasks(tasks);
            return;
        }

        try {
            const token = localStorage.getItem("token");
            const response = await axios.get(`http://localhost:8080/api/task/get-all/by-status/${status}`, {
                headers: { Authorization: `Bearer ${token}` }
            });

            setFilteredTasks(response.data);
        } catch (error) {
            console.error("Error while filtering tasks:", error);
            setError("Error while filtering tasks!");
        }
    };

    const handleCreateTask = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem("token");
            const response = await axios.post(
                "http://localhost:8080/api/task/create",
                { ...newTask, subjectName },
                { headers: { Authorization: `Bearer ${token}` } }
            );

            setTasks([...tasks, response.data]);
            setFilteredTasks([...filteredTasks, response.data]);
            setNewTask({ title: "", description: "", deadLine: "" });
        } catch (error) {
            console.error("Error while creating task:", error);
            setError("Error please try again!");
        }
    };

    return (
        <div className="task-container">
            <h2>📝 Tasks for subject: {subjectName}</h2>

            {error && <p className="error">{error}</p>}

            <div className="filter-container">
                <label>Filter by status:</label>
                <select value={statusFilter} onChange={(e) => handleFilterChange(e.target.value)}>
                    <option value="">All</option>
                    <option value="PENDING">Pending</option>
                    <option value="IN_PROGRESS">In progress</option>
                    <option value="COMPLETED">Completed</option>
                </select>
            </div>

            <form onSubmit={handleCreateTask} className="task-form">
                <input
                    type="text"
                    placeholder="Task name"
                    value={newTask.title}
                    onChange={(e) => setNewTask({ ...newTask, title: e.target.value })}
                    required
                />
                <input
                    type="text"
                    placeholder="Task description"
                    value={newTask.description}
                    onChange={(e) => setNewTask({ ...newTask, description: e.target.value })}
                    required
                />
                <input
                    type="datetime-local"
                    value={newTask.deadLine}
                    onChange={(e) => setNewTask({ ...newTask, deadLine: e.target.value })}
                    required
                />
                <button type="submit">Add new task</button>
            </form>

            {filteredTasks.length === 0 && !error ? (
                <p>No available tasks 😢</p>
            ) : (
                <ul className="task-list">
                    {filteredTasks.map((task) => (
                        <li
                            key={task.id}
                            className="task-card"
                            onClick={() => navigate(`/task/${task.id}`)}
                        >
                            <h3>{task.title}</h3>
                            <p>{task.description}</p>
                            <p><strong>📅 Deadline:</strong> {task.deadLine ?? "No"}</p>
                            <p><strong>📌 Status:</strong> {task.status}</p>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default TaskPage;
