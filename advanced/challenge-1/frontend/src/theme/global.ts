import { createGlobalStyle } from 'styled-components'
import './theme.scss'

export const theme = {
  primary: '#41748d',
  secondary: '#7e8a78',
  tertiary: '#2f4c5e'
}

const GlobalStyle = createGlobalStyle`
  body {
    padding: 1.4rem;
    background: #7e8a78;
  }
  td {
    vertical-align: middle;
  }
  .container {
    padding: 1.4rem;
    background: #fff;
  }
`

export default GlobalStyle
