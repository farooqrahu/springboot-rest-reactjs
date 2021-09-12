import React, { Component } from "react";
import UserDataService from "../services/user.service";

export default class AddUser extends Component {
  constructor(props) {
    super(props);
    this.onChangeName = this.onChangeName.bind(this);
    this.onChangeSurname = this.onChangeSurname.bind(this);
    this.saveUser = this.saveUser.bind(this);
    this.newUser = this.newUser.bind(this);

    this.state = {
      id: null,
      name: "",
      surname: "",
      email:"",

      submitted: false
    };
  }

  onChangeName(e) {
    this.setState({
      name: e.target.value
    });
  }

  onChangeSurname(e) {
    this.setState({
      surname: e.target.value
    });
  }

  saveUser() {
    var data = {
      name: this.state.name,
      surname: this.state.surname,
      email: this.state.email,
      birthDate: '2021-09-12',
      identity:"newIdentity",
      passportNumber:"passportNumber",
      password:"xxxxxx",
      phone:"phone"
    };

    UserDataService.create(data)
      .then(response => {
        this.setState({
          id: response.data.id,
          name: response.data.name,
          surname: response.data.surname,
          email: response.data.email,
          birthDate:"",
          submitted: true
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  newUser() {
    this.setState({
      id: null,
      name: "",
      surname: "",
      email: "",
      birthDate:"",
      submitted: false
    });
  }

  render() {
    return (
      <div className="submit-form">
        {this.state.submitted ? (
          <div>
            <h4>You submitted successfully!</h4>
            <button className="btn btn-success" onClick={this.newUser}>
              Add
            </button>
          </div>
        ) : (
          <div>
            <div className="form-group">
              <label htmlFor="Name">Name</label>
              <input
                type="text"
                className="form-control"
                id="Name"
                required
                value={this.state.name}
                onChange={this.onChangeName}
                name="Name"
              />
            </div>

            <div className="form-group">
              <label htmlFor="Surname">Surname</label>
              <input
                type="text"
                className="form-control"
                id="Surname"
                required
                value={this.state.surname}
                onChange={this.onChangeSurname}
                name="Surname"
              />
            </div>

            <button onClick={this.saveUser} className="btn btn-success">
              Submit
            </button>
          </div>
        )}
      </div>
    );
  }
}
