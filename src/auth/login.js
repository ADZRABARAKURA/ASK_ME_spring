import { useState } from "react";
import axios from 'axios';
import { useNavigate } from "react-router-dom";
import "./login.css"

const Login = (({close})=>{
    const[email, setEmail] = useState()
    const[password, setPassword] = useState()
    const navigate = useNavigate();


    function setEmailf(e){
        setEmail(e.target.value)
      }
    
      function setPasswordf(e){
        setPassword(e.target.value)
      }
    
      function sendLogin(){
          axios.post('https://askme-donation.ru/backend/login?email='+email+'&password='+password, null)
          .then(response => {
            if (response.status === 200 || response.status===302) {
                close()
                navigate(0)
            }
        })
        .catch(error => {
            console.error('Error sending register request: ', error);
        });
      }

    return(
<div className="wrapper">
      <div className="form-container-login">
        <button onClick={close} className="close-button" >X</button>
        <input onChange={setEmailf} placeholder="Email" />
        <input type="password" onChange={setPasswordf} placeholder="Password" />
        <button onClick={sendLogin}>Войти</button>
      </div>
    </div>
    )
});

export default Login;