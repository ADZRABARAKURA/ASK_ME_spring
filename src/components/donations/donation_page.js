import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import './donation_page.css';

const DonationPage = () => {
    const { user_url } = useParams();
    const [messages, setMessages] = useState([]);
    const [visibleMessage, setVisibleMessage] = useState(null);

    useEffect(() => {
        console.warn("sheesh")
        const socket = new SockJS('https://askme-donation.ru/backend/ws');
        const stompClient = Stomp.over(socket);

        stompClient.connect({}, (frame) => {
            console.log('Connected: ' + frame);

            stompClient.subscribe('/topic/donations/' + user_url, (message) => {
                try {
                    const donationMessage = JSON.parse(message.body);
                    setMessages((prevMessages) => [...prevMessages, donationMessage]);
                    setVisibleMessage(donationMessage);
                    setTimeout(() => {
                        setVisibleMessage(null);
                    }, 10000); // 10 секунд
                } catch (error) {
                    console.error('Error processing message:', error);
                }
            }, (error) => {
                console.error('Subscription error:', error);
            });

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