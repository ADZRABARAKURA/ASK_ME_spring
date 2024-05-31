import { useState } from "react";
import axios from 'axios';
import "./register.css"

const Register = (({change, close})=>{
    const[email, setEmail] = useState()
    const[password, setPassword] = useState()
    const[name, setName] = useState()
    const[description, setDescription] = useState()
    const[block1, setBlock1] = useState()
    const[block2, setBlock2] = useState()
    const[block3, setBlock3] = useState()
    const[block4, setBlock4] = useState()
    const[imgUrl, setImgUrl] = useState()


    function setEmailf(e){
        setEmail(e.target.value)
      }
    
      function setPasswordf(e){
        setPassword(e.target.value)
      }

      function sendRegister(){
        const register = {
            email: email,
            password: password,
            name: name,
            description: description,
            block1: block1,
            block2: block2,
            block3: block3,
            block4: block4,
            imgUrl: imgUrl
          };
    
          axios.post('https://askme-donation.ru/backend/api/users/register', register)
          .then(response => {
            if (response.status === 200) {
                change()
            }
        })
        .catch(error => {
            console.error('Error sending register request: ', error);
        });
      }

    return(
<div className="wrapper">
      <div className="form-container-register">
        <button onClick={close} className="close-button" >X</button>
        <input onChange={setEmailf} placeholder="Email" />
        <input type="password" onChange={setPasswordf} placeholder="Password" />
        <button onClick={sendRegister}>Зарегистрироваться</button>
      </div>
    </div>
    )
});

export default Register;