import logo from './logo.svg';
import './App.css';
import { Route, BrowserRouter, Switch } from 'react-router-dom'
import Navbar from './components/Navbar'
import Home from './pages/home'
import Engineer from './pages/engineer'
import Manager from './pages/manager'
import HR from './pages/hr'


// import ListEmployees from './Employees/ListEmployees';
function App() {
  return (
    <div>
        <BrowserRouter>
        <div className="App">
          <Navbar />
          <Switch>
            <Route exact path='/' component={Home}/>
            <Route exact path='/engineer' component={Engineer}/>
            <Route exact path='/manager' component={Manager}/>
            <Route exact path='/hr' component={HR}/>
          </Switch>
        </div>
      </BrowserRouter>
    </div>
  );
}

export default App;
