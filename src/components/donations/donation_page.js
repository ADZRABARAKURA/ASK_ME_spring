import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import './donation_page.css';
import axios from 'axios';

const DonationPage = () => {
    const { user_url } = useParams();
    const [visibleMessage, setVisibleMessage] = useState(null);

    function playSound(message){
                // остановим все, что уже синтезируется в речь
        window.speechSynthesis.cancel();

        // прочитать текст
        const utterance = new SpeechSynthesisUtterance(message);
        utterance.pitch = 0.7;  // пониже
        utterance.rate = 1.4;   // побыстрее
        utterance.volume = 0.8; // потише
        window.speechSynthesis.speak(utterance);
    }

    function setVisibleMessageAndCheckNext(){
        setVisibleMessage(null)
        axios.post(`https://askme-donation.ru/backend/api/donations/queue/${user_url}`)
        .then(message => {
            if(message.data != ""){
                const donationMessage = message.data;
                    setVisibleMessage(donationMessage);
                    playSound(donationMessage.message)
                    setTimeout(() => {
                        setVisibleMessageAndCheckNext(null);
                    }, 10000); // 10 секунд
            }
        })
        .catch(error => {
          console.error('Error fetching user data:', error);
        });
    }

    useEffect(() => {
        const socket = new SockJS('https://askme-donation.ru/backend/ws');
        const stompClient = Stomp.over(socket);

        stompClient.connect({}, (frame) => {
            console.log('Connected: ' + frame);
    
            stompClient.subscribe('/topic/donations/' + user_url, (message) => {
                try {
                    const donationMessage = JSON.parse(message.body);
                    setVisibleMessage(donationMessage);
                    playSound(donationMessage.message);
                    setTimeout(() => {
                        setVisibleMessageAndCheckNext(null);
                    }, 10000); // 10 секунд
                } catch (error) {
                    console.error('Error processing message:', error);
                }
            }, (error) => {
                console.error('Subscription error:', error);
            });
    
            // Sending message after subscription is done
            stompClient.send('/app/subscribe', {}, JSON.stringify({ user_url }));
        }, (error) => {
            console.error('Connection error:', error);
        });
    
        socket.onclose = (event) => {
            console.error('WebSocket connection closed:', event);
        };
    
        return () => {
            if (stompClient) {
                stompClient.disconnect(() => {
                    console.log('Disconnected');
                });
            }
        };
    }, [user_url]);

    return (
        <div className="overlay">
            <div className="text-container">
                {visibleMessage && (
                    <div className="donation-message">
                        <p className="red-text">{visibleMessage.senderName} - {visibleMessage.total}</p>
                        <p className="red-text">{visibleMessage.message}</p>
                    </div>
                )}
            </div>
        </div>
    );
};

export default DonationPage;