import classes from "./header.module.css";
import logo from "../../assets/logo.png";
import Btn from "../btn/btn.js";

const Header = () => {
  return (
    <div class={classes.container}>
      <div class={classes.wrapper}>
        <div>
          <img src={logo} />
        </div>
        <div class={classes.nav}>
          {/* <Btn title="Поиск авторов" />
          <Btn title="Войти" /> */}
        </div>
      </div>
    </div>
  );
};

export default Header;
