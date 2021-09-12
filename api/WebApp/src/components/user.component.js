import React, { Component } from "react";
import UserDataService from "../services/user.service";

export default class User extends Component {
  constructor(props) {
    super(props);
    this.onChangeSurname = this.onChangeSurname.bind(this);
    this.onChangeName = this.onChangeName.bind(this);
    this.getUser = this.getUser.bind(this);
    this.updateUser = this.updateUser.bind(this);
    this.deleteUser = this.deleteUser.bind(this);

    this.state = {
      currentUser: {
        id: null,
        name: "",
        surname: "",
        email: ""
      },
      message: ""
    };
  }

  componentDidMount() {
    this.getUser(this.props.match.params.id);
  }

  onChangeName(e) {
    const name = e.target.value;

    this.setState(function(prevState) {
      return {
        currentUser: {
          ...prevState.currentUser,
          name: name
        }
      };
    });
  }

  onChangeSurname(e) {
    const surname = e.target.value;
    
    this.setState(prevState => ({
      currentUser: {
        ...prevState.currentUser,
        surname:surname
      }
    }));
  }

  getUser(id) {
    UserDataService.get(id)
      .then(response => {
        this.setState({
          currentUser: response.data
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }



  updateUser() {
    UserDataService.update(
      this.state.currentUser.id,
      this.state.currentUser
    )
      .then(response => {
        console.log(response.data);
        this.setState({
          message: "The User was updated successfully!"
        });
      })
      .catch(e => {
        console.log(e);
      });
  }

  deleteUser() {
    UserDataService.delete(this.state.currentUser.id)
      .then(response => {
        console.log(response.data);
        this.props.history.push('/Users')
      })
      .catch(e => {
        console.log(e);
      });
  }

  render() {
    const { currentUser } = this.state;

    return (
      <div>
        {currentUser ? (
          <div className="edit-form">
            <h4>User</h4>
            <form>
              <div className="form-group">
                <label htmlFor="name">Name</label>
                <input
                  type="text"
                  className="form-control"
                  id="title"
                  value={currentUser.name}
                  onChange={this.onChangeName}
                />
              </div>
              <div className="form-group">
                <label htmlFor="surname">Surname</label>
                <input
                  type="text"
                  className="form-control"
                  id="surname"
                  value={currentUser.surname}
                  onChange={this.onChangeSurname}
                />
              </div>
            </form>



            <button
              type="submit"
              className="badge badge-success mr-2"
              onClick={this.updateUser}
            >
              Update
            </button>

            <button
                className="badge badge-danger "
                onClick={this.deleteUser}
            >
               Delete
            </button>
            <p>{this.state.message}</p>

          </div>
        ) : (
          <div>
            <br />
            <p>Please click on a User...</p>
          </div>
        )}
      </div>
    );
  }
}
