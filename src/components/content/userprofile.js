import React from 'react';
import './userprofile.css';

const UserProfile = ({ userData }) => {
    return (
        <div className="user-profile">
            <div className="user-info">
                <div className="user-text">
                    <div className="user-name">{userData.name}</div>
                    <div className="user-description">{userData.description}</div>
                </div>
                <img className="user-img" src={userData.img} alt="User" />
            </div>
        </div>
    );
};

export default UserProfile;