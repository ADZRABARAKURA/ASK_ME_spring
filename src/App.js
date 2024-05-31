import "./styles/App.css";
import MainPage from "./components/mainPage/MainPage";
import DonationPage from "./components/donations/donation_page";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Profile from "./components/profile/profile";

function App() {
  return (
        <Router>
          <Routes>
                <Route exact path="/:id" element={<MainPage/>} />
                <Route path="/donations/:user_url" element={<DonationPage/>} />
                <Route path="/profile" element={<Profile/>} />
          </Routes>
        </Router>
  );
}

export default App;
