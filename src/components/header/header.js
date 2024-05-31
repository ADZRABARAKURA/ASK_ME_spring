import classes from "./header.module.css";
import logo from "../../assets/logo.png";
import Btn from "../btn/btn.js";
import { useEffect, useState } from "react";
import Register from "../../auth/register.js";
import Login from "../../auth/login.js";
import axios from "axios";
import HeaderUser from "./headerUser.js";

const Header = ({setUserDataExt}) => {

  const[openRegister, setOpenRegister] = useState(false)
  const[openLogin, setOpenLogin] = useState(false)
  const[userData, setUserData] = useState(null)
  const[fetchLock, setFetchLock] = useState(false)

  function getCurrentUserData(){
    axios.get("https://askme-donation.ru/backend/api/users/current").then((res)=>{
      if(res.status === 200 && res.data!=''){
        setUserData(res.data)
        console.warn(setUserDataExt)
        setUserDataExt(res.data)
      }
    })
      .catch((e)=>
      console.warn(e)
    )
    setFetchLock(true)
  }

  function openRegistrationWindow(){
    setOpenRegister(true)
  }

  function closeRegistrationWindow(){
    setOpenRegister(false)
  }

  function changeToLogin(){
    setOpenRegister(false)
    setOpenLogin(true)
  }

  function openLoginWindow(){
    setOpenLogin(true)
  }

  function closeLoginWindow(){
    setOpenLogin(false)
  }

  useEffect(()=>{
      if(!fetchLock){
        getCurrentUserData()
      }
  }, [openRegister, openLogin, userData])

  if(userData==null){
  return (
    <div class={classes.container}>
      <div class={classes.wrapper}>
      {openRegister?<Register change={changeToLogin} close={closeRegistrationWindow}/>:null}
      {openLogin?<Login close={closeLoginWindow}/>:null}
        <div>
          <img src={logo} />
        </div>
        <div class={classes.nav}>
          <button onClick={openRegistrationWindow} title="Регистрация">Регистрация</button>
          <button title="Войти" onClick={openLoginWindow}>Войти</button>
        </div>
      </div>
    </div>
  );
}
else{
  return(
    <div class={classes.container}>
      <div class={classes.wrapper}>
      <div>
          <img src={logo} />
        </div>
        <HeaderUser userData={userData}/>
        </div>
      </div>
  )
}
};

export default Header;
