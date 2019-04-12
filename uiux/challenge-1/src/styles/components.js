import styled from 'styled-components'
import bg from '../assets/bg.gif'



export const Background = styled.div`
  position: fixed;
  z-index: -1;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url(${bg});
  background-size: 500px 500px;
  background-repeat: no-repeat;
  background-position: center fixed;
  background-size:cover;

  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;  
`
