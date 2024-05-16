import ContentBlock from "../content/contentBlock.js";
import Header from "../header/header.js";
import classes from "./MainPage.module.css";

const MainPage = () => {
  return (
    <div class={classes.container}>
      <Header /> <ContentBlock />
    </div>
  );
};

export default MainPage;
