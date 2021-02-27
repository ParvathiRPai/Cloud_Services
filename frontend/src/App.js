import React from 'react';
import SecuredApp from './SecuredApp';
import './App.css';
import { BrowserRouter as Router } from 'react-router-dom';

function App() {
  return (
    <div className="App">
          <Router>
            <SecuredApp/>
          </Router>
    </div>
  );
}

export default App;