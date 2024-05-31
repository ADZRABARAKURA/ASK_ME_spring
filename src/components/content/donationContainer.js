import { useEffect, useState } from 'react';
import './donationContainer.css'

const DonationContainer = ({sendDonation, changeMessageInput, changeNameInput, changeTotalInput}) => {
    const[symbolsCount, setSymbolsCount] = useState(0);

    useEffect(()=>{

    }, symbolsCount)

    function changeName(e){
        changeNameInput(e.target.value)
    }

    function changeTotal(e){
        changeTotalInput(e.target.value)
    }

    function changeSymbolsCount(e){
        if(e.target.value.length>=150){
            e.target.value = e.target.value.slice(0, 150);
        }
        changeMessageInput(e.target.value)
        setSymbolsCount(e.target.value.length)
    }

    return(
        <div className='donation-wrapper'>
            <div className='donation-form-wrapper'>
                <div class="streamer-support-text">Поддержать стримера </div>
                <div class="form-container">
                        <input onChange={changeName} className="nameInput" placeholder="Ваше имя" />
                        <input onChange={changeTotal} className="totalInput" placeholder="Сумма поддержки (в ₽)" />
                        <textarea onChange={changeSymbolsCount} placeholder="Ваше сообщение" rows="4"></textarea>
                        <div id="char-counter" class="char-counter">{symbolsCount}/150</div>
                </div>
                <div className='buttonWrapper'>
                    <button className='sendButton' onClick={sendDonation}>Поддержать</button>
                </div>
            </div>
        <img className="backgroundImage"></img>
    </div>
    )
};

export default DonationContainer