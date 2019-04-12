import React from 'react'
import { connect } from 'react-redux'
import { withRouter } from 'react-router-dom'
import styled from 'styled-components'
import { Background, InvisibleDiv } from '../styles/components'
import Loading from '../components/Loading'
import GoogleFontLoader from 'react-google-font-loader';
import { Container, Header, Grid } from 'semantic-ui-react';


const Main = styled.main`
  position: relative;
`
export { default as Home } from './Home'
export { default as Lyric } from './Lyric'
export { default as Artist } from './Artist'


const Index = ({ children, loading }) => {
  return(<Main>
    <Background />
    <GoogleFontLoader
      fonts={[
        {
          font: 'Cookie',
          weights: ['400i', 400],
        },
        {
          font: 'Arvo',
          weights: [400, 400],
        },    
      ]}
    />

    <Grid padded verticalAlign="middle" columns={1} centered style={{height: '100vh'}}>
      <Grid.Row>
        <Grid.Column width={16} textAlign="center" verticalAlign="middle">
          <Container style={{ margin: 20 }}>
            <Header inverted as='h2'>
              <Container textAlign='center' style={{ fontFamily: 'Arvo', fontSize: '15px', marginBottom: '15px', paddingRight: '150px' }}>Rapha's</Container>
              <Container textAlign='center' style={{ fontFamily: 'Cookie', fontSize: '80px' }}>Birthday</Container>
              <Container textAlign='center' style={{ fontFamily: 'Arvo', fontSize: '15px' }}>Karaoke</Container>
              <Header.Subheader style={{ fontFamily: 'Arvo, monospaced', fontSize: '12px' }}>Just like singing in the shower, except that you're not alone!</Header.Subheader>
            </Header>
            {children}
            <Loading visible={loading} />
          </Container>
        </Grid.Column>
      </Grid.Row>
    </Grid>

  </Main>
)};


export default withRouter(
  connect(
    ({ ui: { loading } }) => ({loading}), 
    {}
  )
  (Index))
