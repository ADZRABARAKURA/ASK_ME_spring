import classes from "./input.module.css";

const Input = ({ title, value, inputChange }) => {

  function changeInput(e){
    inputChange(e.target.value)
  }

  return (
    <div class={classes.wrap}>
      <p>{title}</p>
      <input onChange={changeInput} class={classes.input} value={value} />
    </div>
  );
};

export default Input;
