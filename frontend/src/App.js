import logo from './logo.svg';
import './App.css';
import { Route, BrowserRouter, Switch } from 'react-router-dom'
import Navbar from './components/Navbar'
import Home from './pages/home'
import Engineer from './pages/engineer'
import Manager from './pages/manager'
import HR from './pages/hr'
import CreateEmployee from './components/CreateEmployee';
import history from './history';



// import ListEmployees from './Employees/ListEmployees';
function App() {
  return (
    <div>
        <BrowserRouter history={history}>
        <div className="App">
          <Navbar />
          <Switch>
            <Route exact path='/' component={Home}/>
            <Route path='/engineer' component={Engineer}/>
            <Route exact path='/manager' component={Manager}/>
            <Route path='/hr' component={HR}/>
            <Route exact path='/addemployee' component={CreateEmployee}/>
          </Switch>
        </div>
      </BrowserRouter>
    </div>
  );
}

export default App;
