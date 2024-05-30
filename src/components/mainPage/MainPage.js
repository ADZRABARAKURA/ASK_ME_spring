import ContentBlock from "../content/contentBlock.js";
import Header from "../header/header.js";
import classes from "./MainPage.module.css";
import { useParams } from "react-router-dom";

const MainPage = () => {
  const { id } = useParams();
  console.warn(id)
  return (
    <div class={classes.container}>
      <Header /> <ContentBlock userId={id}/>
    </div>
  );
};

export default MainPage;
