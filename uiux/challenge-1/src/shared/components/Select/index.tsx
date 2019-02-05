import React, { ChangeEventHandler } from "react";

import styles from "./Select.module.css";

type Option = {
  value: string;
  label: string;
};

type Props = {
  label: string;
  id: string;
  options: Option[];
  value: string;
  onChange: ChangeEventHandler<HTMLSelectElement>;
};

const Select: React.FC<Props> = ({
  id,
  label,
  options,
  value,
  onChange
}: Props): JSX.Element => {
  return (
    <label htmlFor={id} data-testid="Select-container">
      {label}
      <select
        name={id}
        id={id}
        onChange={onChange}
        value={value}
        className={styles.select}
        data-testid="Select-field"
      >
        {options.map((option: Option, i: number) => (
          <option key={i} value={option.value}>
            {option.label}
          </option>
        ))}
      </select>
    </label>
  );
};

export default Select;
