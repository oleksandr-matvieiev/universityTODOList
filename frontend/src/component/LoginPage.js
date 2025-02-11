import {useState} from "react";
import axios from "axios";

import "./LoginPage.css";
import NavigationMenu from "./NavigationMenu";

const LoginPage = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);

    const handleLogin = async (e) => {
        e.preventDefault();
        setError(null);

        try {
            const formData = new FormData();
            formData.append("username", username);
            formData.append("password", password);

            const response = await axios.post("http://localhost:8080/api/auth/login", formData);
            const token = response.data;

            if (token) {
                localStorage.setItem("token", token);
                alert("Login successful!");
                console.log(token);
            } else {
                setError('Invalid response from server. No token provided.');
            }
        } catch (error) {
            console.error('Login failed:', error);
            setError('Login failed. Please check your credentials.');
        }
    }
    return (
        <div className="login-container">
            <NavigationMenu />
            <h2>Login</h2>
            {error && <p className="error">{error}</p>}
            <form onSubmit={handleLogin}>
                <input
                    type="text"
                    placeholder="Login"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <button type="submit">login</button>
            </form>
        </div>
    );
};
export default LoginPage;
