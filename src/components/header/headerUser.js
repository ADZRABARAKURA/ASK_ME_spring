import axios from "axios"
import "./headerUser.css"
import { useNavigate } from "react-router-dom"

const HeaderUser = ({userData}) => {
    const navigate = useNavigate()

    function logout(){
        axios.post("https://askme-donation.ru/backend/logout").then((res)=>{
            if(res.status === 200)
                navigate(0)
        })
    }

    function openProfile(){
        navigate("/profile")
    }

    return(
        <div className="header-user">
            <span onClick={openProfile} className="header-user__name">{userData.name}</span>
            <img onClick={openProfile} className="header-user__photo" src={userData.img} alt="User" />
            <button className="header-user__logout" onClick={logout}>Выйти</button>
        </div>
    )
}
export default HeaderUser