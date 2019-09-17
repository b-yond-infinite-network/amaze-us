import React, { Component } from 'react'
import API from "./API"
class ToDoList extends Component {

  constructor(props) {
    super(props);
    this.state = {
      todos: [],
      todoItem: {}
    }
  }
  getTodos = async ()=>{
    const {data} = await API.get('/')
    const todos = data.todos.slice()
    this.setState({todos})
  }
  componentDidMount(){
    this.getTodos();
  }
  deleteTodo = async (e) =>{
    const cargoId = e.target.value
    const {data} = await API.delete(`/${cargoId}`)
    const todos = data.todos.slice()
    this.setState({todos})
  }
  handleChange = (e) =>{
    this.setState({
      todoItem:e.target.value
    })
  }
  addTodo = async (e) =>{
    e.preventDefault();
    this.refs.field.value = '';
    const todo ={"text":this.state.todoItem}
    const {data} = await API.post('/',todo)
    const todos = data.todos.slice()
    this.setState({todos})
  }
  render() {
    const required =true;
    return (
      <div style={{felx:1,flexDirection: 'row', padding:10, margin:20}}>
        <ul>
          {this.state.todos.map(todo=> <li key={todo._id}>
          <button onClick={this.deleteTodo} value={todo._id} >{todo.text}</button></li>)}
        </ul>
        <div style={{color:"Cayan " ,textAlign: 'center'}}>
          <form onSubmit={this.addTodo}>
            <input type="text"  style= {{width:80,width:320}} onChange={this.handleChange} required={required} ref="field"/>
            <button style={{fontSize: 15, color: 'red', fontWeight: 'normal'}} > Add </button>
          </form>
        </div>
    </div>
    )
  }
}

export default ToDoList;