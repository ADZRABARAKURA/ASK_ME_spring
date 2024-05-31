import React, { useState } from 'react';
import classes from "../content/contentBlock.module.css";
import mainClasses from "../mainPage/MainPage.module.css";
import Header from "../header/header";
import "./profile.css"; // Подключаем CSS для компонента Profile
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Profile = ({ }) => {
    const navigate = useNavigate();
    const[userData, setUserData] = useState(null)
    const[username, setUsername] = useState()
    const[donations, setDonations] = useState(null)
    const[description, setDescription] = useState()
    const[block1, setBlock1] = useState()
    const[block2, setBlock2] = useState()
    const[block3, setBlock3] = useState()
    const[block4, setBlock4] = useState()
    const[img, setImage] = useState()
    const [isEditing, setIsEditing] = useState(false);

    function handleSave(){
        const changes={
            id: userData.id,
            name: username,
            description: description,
            block1: block1,
            block2: block2,
            block3: block3,
            block4: block4,
            img: img,
            uniqUrl: userData.uniqUrl
        }

        axios.patch("https://askme-donation.ru/backend/api/users/"+userData.id, changes)
        .then((res)=>{
            if(res.status===200){
                navigate(0)
            }
        })

        setIsEditing(false)
    }

    function setUsernamef(e){
        setUsername(e.target.value)
    }

    function setDescriptionf(e){
        setDescription(e.target.value)
      }
    
      function setBlock1f(e){
        setBlock1(e.target.value)
      }
    
      function setBlock2f(e){
        setBlock2(e.target.value)
      }
      function setBlock3f(e){
        setBlock3(e.target.value)
      }
    
      function setBlock4f(e){
        setBlock4(e.target.value)
      }

      function setImagef(e){
        setImage(e.target.value)
      }

      function setUserDataf(e){
        setUserData(e)
        setUsername(e.name)
        setDescription(e.description)
        setBlock1(e.block1)
        setBlock2(e.block2)
        setBlock3(e.block3)
        setBlock4(e.block4)
        axios.get("https://askme-donation.ru/backend/api/users/donations/"+e.id).then((res)=>{
            if(res.status===200){
                setDonations(res.data)
            }
        })
      }

      function calculateDonationsSumm(){
        let sum = 0
            donations.forEach(donation => {
                sum+=donation.total
            });
        return sum;
      }

    return (
        <div className={mainClasses.container}>
            <Header setUserDataExt={setUserDataf} />
            {userData!=null?
            <div className={classes.wrapper}>
                <div className={classes.container}>
                    <div className={classes.cell}>
                        <div className="profile-header">
                        <div class="mainInfoWrapper">
                            <img className="profile-image" src={userData.img} alt={userData.name} />
                            <input
                                onChange={setUsernamef}
                                className="profile-name"
                                type="text"
                                name="name"
                                defaultValue={userData.name}
                                readOnly={!isEditing}
                            />
                            {donations!=null?<div className="donationsSum">Сумма донатов: {calculateDonationsSumm()}₽</div>:null}
                     </div>
                    </div>
                    <div className="profile-details">
                        <div className="inputWrapper">
                        <div className="fieldName">О стримере:</div>
                            <input
                                onChange={setDescriptionf}
                                className="profile-detail"
                                type="text"
                                name="description"
                                defaultValue={userData.description}
                                readOnly={!isEditing}
                            />
                        </div>
                        <div className="inputWrapper">
                        <div className="fieldName">Информация о стриме 1:</div>
                            <input
                            onChange={setBlock1f}
                                className="profile-detail"
                                type="text"
                                name="block1"
                                defaultValue={userData.block1}
                                readOnly={!isEditing}
                            />
                        </div>
                        <div className="inputWrapper">
                        <div className="fieldName">Информация о стриме 2:</div>
                            <input
                            onChange={setBlock2f}
                                className="profile-detail"
                                type="text"
                                name="block2"
                                defaultValue={userData.block2}
                                readOnly={!isEditing}
                            />
                        </div>
                        <div className="inputWrapper">
                        <div className="fieldName">Информация о стриме 3:</div>
                            <input
                            onChange={setBlock3f}
                                className="profile-detail"
                                type="text"
                                name="block3"
                                defaultValue={userData.block3}
                                readOnly={!isEditing}
                            />
                        </div>
                        <div className="inputWrapper">
                        <div className="fieldName">Информация о стриме 4:</div>
                            <input
                            onChange={setBlock4f}
                                className="profile-detail"
                                type="text"
                                name="block4"
                                defaultValue={userData.block4}
                                readOnly={!isEditing}
                            />
                        </div>
                        <div className="inputWrapper">
                            <div className="fieldName">Ссылка на изображение:</div>
                            <input
                            onChange={setImagef}
                                className="profile-detail"
                                type="text"
                                name="Ссылка на изображение"
                                defaultValue={userData.img}
                                readOnly={!isEditing}
                            />
                        </div>
                        <div className="inputWrapper">
                            <div className="fieldName">Ссылка для OBS:</div>
                            <input
                                className="profile-detail"
                                type="text"
                                name="Уникальная ссылка"
                                defaultValue={"https://askme-donation.ru/donations/" + userData.uniqUrl}
                                readOnly={true}
                            />
                        </div>
                        <div className="inputWrapper">
                            <div className="fieldName">Ссылка для доната:</div>
                            <input
                                className="profile-detail"
                                type="text"
                                name="Ссылка для доната:"
                                defaultValue={"https://askme-donation.ru/" + userData.id}
                                readOnly={true}
                            />
                        </div>
                        {isEditing ? (
                        <button className="save-button" onClick={handleSave}>Сохранить</button>
                    ) : (
                        <button className="edit-button" onClick={() => setIsEditing(true)}>Редактировать</button>
                    )}
                    </div>
                </div>
                {donations!=null?<div className={classes.cell}>
                    <div className="donationsWrapper">
                            <div>Ваши донаты</div>
                        {donations.map((donation)=>{
                            return(
                                <div className='donationWrapper'>
                                    <div>{donation.senderName}</div>
                                    <div className="message">{donation.message}</div>
                                    <div>{donation.total}</div>
                                </div>
                            )
                        })}
                         </div>
                </div>:null}
            </div>
            </div>
:null}
        </div>
    );
};

export default Profile;