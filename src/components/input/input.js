import classes from "./input.module.css";

const Input = ({ title }) => {
  return (
    <div class={classes.wrap}>
      <p>{title}</p>
      <input class={classes.input} />
    </div>
  );
};

export default Input;
