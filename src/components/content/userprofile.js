import React from 'react';
import './userprofile.css';
import classes from "./contentBlock.module.css";

const UserProfile = ({ userData }) => {
    return (
        <div className="user-profile">
            <div className="user-info">
                <div className="user-name-info">
                    <img className="user-img" src={userData.img} alt="User" />
                    <div className="user-text">
                        <div className="user-name">{userData.name}</div>
                        <div className="user-description">{userData.description}</div>
                    </div>
                </div>
                </div>
                <div className="user-detailed-info">
                    {userData && (
                        <>
                            <div class={classes.item}>{userData.block1}</div>
                            <div class={classes.item}>{userData.block2}</div>
                            <div class={classes.item}>{userData.block3}</div>
                            <div class={classes.item}>{userData.block4}</div>
                        </>
                    )}
                <div/>
            </div>
        </div>
    );
};

export default UserProfile;