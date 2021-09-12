import React, { Component } from "react";
import UserDataService from "../services/user.service";
import { Link } from "react-router-dom";

export default class UsersList extends Component {
  constructor(props) {
    super(props);
    this.onChangeSearchTitle = this.onChangeSearchTitle.bind(this);
    this.retrieveUsers = this.retrieveUsers.bind(this);
    this.refreshList = this.refreshList.bind(this);
    this.setActiveUser = this.setActiveUser.bind(this);
    this.removeAllUsers = this.removeAllUsers.bind(this);
    this.searchTitle = this.searchTitle.bind(this);

    this.state = {
      users: [],
      currentUsers: null,
      currentIndex: -1,
      searchTitle: ""
    };
  }

  componentDidMount() {
    this.retrieveUsers();
  }

  onChangeSearchTitle(e) {
    const searchTitle = e.target.value;

    this.setState({
      searchTitle: searchTitle
    });
  }

  retrieveUsers() {
    UserDataService.getAll()
      .then(response => {
        this.setState({
          users: response.data
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  refreshList() {
    this.retrieveUsers();
    this.setState({
      currentUsers: null,
      currentIndex: -1
    });
  }

  setActiveUser(User, index) {
    this.setState({
      currentUsers: User,
      currentIndex: index
    });
  }

  removeAllUsers() {
    UserDataService.deleteAll()
      .then(response => {
        console.log(response.data);
        this.refreshList();
      })
      .catch(e => {
        console.log(e);
      });
  }

  searchTitle() {
    this.setState({
      currentUsers: null,
      currentIndex: -1
    });

    UserDataService.findByTitle(this.state.searchTitle)
      .then(response => {
        this.setState({
          users: response.data
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  render() {
    const { searchTitle, users, currentUsers, currentIndex } = this.state;

    return (
      <div className="list row">
        <div className="col-md-8">
          <div className="input-group mb-3">
            <input
              type="text"
              className="form-control"
              placeholder="Search by title"
              value={searchTitle}
              onChange={this.onChangeSearchTitle}
            />
            <div className="input-group-append">
              <button
                className="btn btn-outline-secondary"
                type="button"
                onClick={this.searchTitle}
              >
                Search
              </button>
            </div>
          </div>
        </div>
        <div className="col-md-6 sub-menu">
          <h4>Users List</h4>

          <ul className="list-group">
            {users &&
              users.map((User, index) => (
                <li
                  className={
                    "list-group-item " +
                    (index === currentIndex ? "active" : "")
                  }
                  onClick={() => this.setActiveUser(User, index)}
                  key={index}>
                  {User.name}
                </li>
              ))}
          </ul>


        </div>

        <div className="col-md-6">
          <button
              className="m-3 btn btn-sm btn-danger"
              onClick={this.removeAllUsers}
          >
            Remove All
          </button>
          {currentUsers ? (
            <div>
              <h4>Users</h4>
              <div>
                <label>
                  <strong>Name:</strong>
                </label>{" "}
                {currentUsers.name}
              </div>
              <div>
                <label>
                  <strong>Surname:</strong>
                </label>{" "}
                {currentUsers.surname}
              </div>
              <div>
                <label>
                  <strong>Email:</strong>
                </label>{" "}
                {currentUsers.email}
              </div>

              <Link
                to={"/users/" + currentUsers.id}
                className="badge badge-warning"
              >
                Edit
              </Link>
            </div>
          ) : (
            <div>
              <br />
              <p>Please click on a Users...</p>
            </div>
          )}
        </div>
      </div>
    );
  }
}
