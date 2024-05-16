import classes from "./btn.module.css";

const Btn = ({ title }) => {
  return (
    <div class={classes.btn}>
      <p>{title}</p>
    </div>
  );
};

export default Btn;
