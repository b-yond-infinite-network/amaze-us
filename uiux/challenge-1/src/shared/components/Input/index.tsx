import React, { ChangeEventHandler } from "react";

import styles from "./Input.module.css";

type InputProps = {
  placeholder: string;
  value: string;
  onChange: ChangeEventHandler<HTMLInputElement>;
};

const Input: React.FC<InputProps> = ({
  placeholder,
  value,
  onChange
}: InputProps) => {
  return (
    <input
      placeholder={placeholder}
      type="text"
      value={value}
      onChange={onChange}
      className={styles.input}
      data-testid="Input-field"
    />
  );
};

export default Input;
