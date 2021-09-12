import React, { Component } from "react";
import { Switch, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AddUser from "./components/add-user.component";
import User from "./components/user.component";
import UsersList from "./components/users-list.component";

class App extends Component {
  render() {
    return (
      <div>
        <nav className="navbar navbar-expand navbar-dark bg-info">
          <Link to={"/Users"} className="navbar-brand">
            Users
          </Link>
          <div className="navbar-nav mr-auto">
            <li className="nav-item">
              <Link to={"/Users"} className="nav-link">
                Users
              </Link>
            </li>
            <li className="nav-item">
              <Link to={"/add"} className="nav-link">
                Add
              </Link>
            </li>
          </div>
        </nav>

        <div className="container mt-3">
          <Switch>
            <Route exact path={["/", "/Users"]} component={UsersList} />
            <Route exact path="/add" component={AddUser} />
            <Route path="/Users/:id" component={User} />
          </Switch>
        </div>
      </div>
    );
  }
}

export default App;
