import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Btn from "../btn/btn";
import Input from "../input/input";
import classes from "./contentBlock.module.css";

const ContentBlock = ({ userId }) => {
  const [userData, setUserData] = useState(null);

  useEffect(() => {
    // Отправить запрос на ваш бэкенд для получения данных о пользователе с указанным ID
    axios.get(`http://localhost:8080/users/${userId}`)
        .then(response => {
          setUserData(response.data);
        })
        .catch(error => {
          console.error('Error fetching user data:', error);
        });
  }, [userId]);

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
            <div class={classes.cell}></div>
            <div class={classes.cell}></div>
          </div>
          <div class={classes.downBlock}>
            <div class={classes.controller}>
              <p class={classes.title}>Поддержать стримера</p>
              <div class={classes.inputs}>
                <Input title="Ваше имя" />
                <Input title="Валюта" />
              </div>
              <div>
                <Input title="Сумма пожертвования" />
              </div>
            </div>
            <div class={classes.blockMsg}>
              <p class={classes.title}>Сообщение стримеру</p>
              <div class={classes.msg}>
                <textarea row="4"></textarea>
              </div>
              <button>Поддержать</button>
            </div>
          </div>
        </div>
      </div>
  );
};

export default ContentBlock;