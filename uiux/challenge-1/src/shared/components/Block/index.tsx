import React, { ReactNode } from "react";

import styles from "./Block.module.css";

type BlockProps = {
  children?: ReactNode;
  variation?: "light" | "dark";
};

const Block: React.FC<BlockProps> = ({
  children,
  variation = "light"
}: BlockProps) => {
  const classNames = [styles.block, styles["block-" + variation]];

  return <section className={classNames.join(" ")}>{children}</section>;
};

export default Block;
