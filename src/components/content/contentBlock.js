import React, { useState, useEffect } from 'react';
import axios from 'axios';
import classes from "./contentBlock.module.css";
import UserProfile from './userprofile';
import DonationContainer from './donationContainer';

const ContentBlock = ({ userId }) => {
  const [userData, setUserData] = useState(null);
  const [senderName, setSenderName] = useState(null);
  const [senderMessage, setSenderMessage] = useState(null);
  const [senderTotal, setSenderTotal] = useState(null);

  useEffect(() => {
    // Отправить запрос на ваш бэкенд для получения данных о пользователе с указанным ID
    axios.get(`https://askme-donation.ru/backend/api/users/${userId}`)
        .then(response => {
          setUserData(response.data);
        })
        .catch(error => {
          console.error('Error fetching user data:', error);
        });
  }, [userId]);

  function setSenderMessageWrapper(e){
    setSenderMessage(e)
  }

  function setSenderTotalWrapper(e){
    setSenderTotal(e)
  }

  function setSenderNameWrapper(e){
    setSenderName(e)
  }


  function sendDonation(){
    const donation = {
      message: senderMessage,
      total: senderTotal,
      senderName: senderName
    };

    console.warn(donation)

    axios.post('https://askme-donation.ru/backend/api/donations/'+userId, donation)
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
            <div class={classes.cell}>
             <UserProfile userData={userData}/>
            </div>
          </div>
          <div class={classes.cell}>
            <div class={classes.imgCell}>
                <DonationContainer sendDonation={sendDonation} changeMessageInput={setSenderMessageWrapper} changeNameInput={setSenderNameWrapper} changeTotalInput={setSenderTotalWrapper}/>
              </div>
              </div>
        </div>
        <p class={classes.contact}>Связаться с нами: askme-donation@yandex.ru</p>
        <p class={classes.contact}>Данные самозанятого:</p>
        <p class={classes.contact}>ФИО: Усов Алексей Андреевич</p>
        <p class={classes.contact}>ИНН: 662304382536</p>
      </div>
  );
}
};

export default ContentBlock;