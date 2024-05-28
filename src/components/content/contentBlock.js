import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Btn from "../btn/btn";
import Input from "../input/input";
import classes from "./contentBlock.module.css";
import { Buffer } from 'buffer';
import UserProfile from './userprofile';

const ContentBlock = ({ userId }) => {
  const [userData, setUserData] = useState(null);
  const [senderName, setSenderName] = useState(null);
  const [senderMessage, setSenderMessage] = useState(null);
  const [senderTotal, setSenderTotal] = useState(null);


  useEffect(() => {
    // Отправить запрос на ваш бэкенд для получения данных о пользователе с указанным ID
    axios.get(`https://askme-donation.ru/backend/api/users/${1}`)
        .then(response => {
          setUserData(response.data);
        })
        .catch(error => {
          console.error('Error fetching user data:', error);
        });
  }, [userId]);

  function setSenderMessageWrapper(e){
    setSenderMessage(e.target.value)
  }

  function sendDonation(){

    const donation = {
      message: senderMessage,
      total: senderTotal,
      senderName: senderName
    };

    axios.post('https://askme-donation.ru/backend/api/donations/1', donation)
    .then(response => {
      if (response.data && response.data.redirectUrl) {
          // Если в ответе есть поле redirectUrl, делаем редирект
          window.location.href = response.data.redirectUrl;
      } else {
          console.error('Redirect URL not found in response');
      }
  })
  .catch(error => {
      console.error('Error sending donation: ', error);
  });
  }

  if(userData!=null){
    return (
      <div class={classes.wrapper}>
        <div class={classes.container}>
          <div class={classes.upBlock}>
            {/* Заполните блоки данными о пользователе */}
            {userData && (
                <>
                  <div class={classes.item}>{userData.block1}</div>
                  <div class={classes.item}>{userData.block2}</div>
                  <div class={classes.item}>{userData.block3}</div>
                  <div class={classes.item}>{userData.block4}</div>
                </>
            )}
          </div>
          <div class={classes.middleBlock}>
            <div class={classes.cell}>
             <UserProfile userData={userData}/>
            </div>
            <div class={classes.imgCell}>
              <div className={classes.backgroundImage}></div>
            </div>
          </div>
          <div class={classes.downBlock}>
            <div class={classes.controller}>
              <p class={classes.title}>Поддержать стримера</p>
              <div class={classes.inputs}>
                <Input title="Ваше имя" inputChange={setSenderName}/>
                <Input title="Валюта" value="RUB"/>
              </div>
              <Input down={true} title="Сумма пожертвования" inputChange={setSenderTotal}/>
             
            </div>
            <div class={classes.blockMsg}>
              <p class={classes.title}>Сообщение стримеру</p>
              <div class={classes.msg}>
                <textarea onChange={setSenderMessageWrapper} row="4"></textarea>
              </div>
              <button onClick={sendDonation}>Поддержать</button>
            </div>
          </div>
        </div>
      </div>
  );
}
};

export default ContentBlock;