import http from "../http-common";

class UserDataService {
  getAll() {
    return http.get("users/");
  }

  get(id) {
    return http.get(`user/${id}`);
  }

  create(data) {
    return http.post("/add", data);
  }

  update(id, data) {
    return http.put(`user/${id}`, data);
  }

  delete(id) {
    return http.delete(`user/${id}`);
  }

  deleteAll() {
    return http.delete(`deleteAll/`);
  }

  findByTitle(title) {
    return http.get(`/users?name=${title}`);
  }
}

export default new UserDataService();