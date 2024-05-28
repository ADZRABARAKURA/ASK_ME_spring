import "./App.css";
import MainPage from "./components/mainPage/MainPage";
import DonationPage from "./components/donations/donation_page";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

function App() {
  return (
        <Router>
          <Routes>
                <Route exact path="/" element={<MainPage/>} />
                <Route path="/donations/:user_url" element={<DonationPage/>} />
          </Routes>
        </Router>
  );
}

export default App;
