const styles = {
  getMoreResultsButton: {
    margin: {
      top: '3rem',
    },
    textAlign: 'center',
  },
  headerCol: {
    color: 'grey',
    transition: '300ms',
    '&:hover': {
      color: 'black',
      textDecoration: 'underline',
      cursor: 'pointer'
    }
  },
  trackRow: {
    transition: '300ms',
    cursor: 'pointer',
    borderRadius: '5px',
    padding: {
      top: '10px',
      bottom: '10px'
    },
    '&:hover': {
      backgroundColor: 'lightgray'
    }
  },
  lyricsHeader: {
    textDecoration: 'underline',
    fontWeight: 'bold',
  },
  lyrics: {
    whiteSpace: 'pre-wrap',
    margin: {
      bottom: '0.25rem',
    }
  },
  selected: {
    textDecoration: 'underline',
    color: '#0069d9',
  }
}

export default styles;