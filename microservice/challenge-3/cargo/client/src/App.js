import React from 'react'
import ToDoList from './ToDoList.js'

function App () {
  const backgroundColors = {
    Default: '#81b71a',
    Cyan: '#37BC9B',
    Green: '#8CC152',
    Red: '#E9573F',
    Yellow: '#F6BB42'
  }
  return (
    <div className="App">
      <div class="top-container" style= {{ backgroundColor: backgroundColors.Cyan }} >
        <h1 style={{ color: backgroundColors.Red, textAlign: 'center' }}>Load that Cargo!!</h1>
        <div style= {{ backgroundColor: backgroundColors.Cyan, fontSize: 22, textAlign: 'center' }}>Add and Delete Cargos using text area.</div>
      </div>
      <ToDoList/>
    </div>
  )
}

export default App
