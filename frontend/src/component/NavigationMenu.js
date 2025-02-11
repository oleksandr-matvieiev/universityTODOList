import React, {useEffect, useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import './NavigationMenu.css';

const NavigationMenu = () => {

    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate("/auth/login");
    }

    return (
        <div className="navigation-container">
            <nav className="navigation-menu">
                <ul>
                    <li>
                        <Link to="/dashboard">Home</Link>
                    </li>
                        <li>
                            <Link to={`/auth/login`}>Registration / Login</Link>
                        </li>

                </ul>
            </nav>

        </div>
    );
};

export default NavigationMenu;
