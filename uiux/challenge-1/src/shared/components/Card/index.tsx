import React, { ReactNode } from "react";

import styles from "./Card.module.css";

type CardProps = {
  title: string;
  text?: string;
  children?: ReactNode;
  size?: "small" | "big";
};

const Card: React.FC<CardProps> = ({
  title,
  text,
  children,
  size = "small"
}: CardProps) => {
  const Heading = size === "big" ? "h1" : "h3";

  return (
    <div className={styles.card}>
      <Heading data-testid="Card-title">{title}</Heading>
      {text && <p>{text}</p>}

      {children}
    </div>
  );
};

export default Card;
