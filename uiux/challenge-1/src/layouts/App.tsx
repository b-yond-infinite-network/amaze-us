import { Link } from "@reach/router";
import React, { ReactNode } from "react";
import styles from "./App.module.css";

/**
 * Header component (Main layout)
 */
const Header = () => (
  <header className={styles.header}>
    <img
      alt="Musixmatch"
      className={styles.logo}
      src="//s.mxmcdn.net/site/images/logo_icon.svg"
    />
    <h1>
      <Link to="/">Karaoke needs words</Link>
    </h1>
  </header>
);

type Props = {
  children?: ReactNode;
};

/**
 * Main layout
 */
const App: React.FC<Props> = ({ children }: Props): JSX.Element => (
  <>
    <Header />
    <main>{children}</main>
  </>
);

export default App;
