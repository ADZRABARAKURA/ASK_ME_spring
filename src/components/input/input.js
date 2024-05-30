import classes from "./input.module.css";

const Input = ({ title, value, inputChange, down }) => {

  function changeInput(e){
    inputChange(e.target.value)
  }

  if(down){
    return (
    <div class={classes.Wrap}>
      <p>{title}</p>
      <input onChange={changeInput} class={classes.input} value={value}/>
    </div>
  );
  }
  else{
    return (
      <div class={classes.wrap}>
        <p>{title}</p>
        <input onChange={changeInput} class={classes.input} value={value}/>
      </div>
    );
  }
};

export default Input;
