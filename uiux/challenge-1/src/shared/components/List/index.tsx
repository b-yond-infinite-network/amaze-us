import React, { ReactNode } from "react";

import styles from "./List.module.css";

type ListItemProps = {
  children?: ReactNode;
  key: number;
  style: any;
};

const ListItem: React.FC<ListItemProps> = ({
  children,
  ...rest
}: ListItemProps) => {
  return (
    <li data-testid="List-listItem" {...rest}>
      {children}
    </li>
  );
};

type ListProps = {
  children?: ReactNode;
};

const List: React.FC<ListProps> = ({ children }: ListProps) => {
  return (
    <ul className={styles.list} data-testid="List-list">
      {children}
    </ul>
  );
};

export default List;
export { ListItem };
