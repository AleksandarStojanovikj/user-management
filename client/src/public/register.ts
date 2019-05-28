import { ApiService } from './../shared/ApiService';
import { autoinject, observable } from "aurelia-framework";

@autoinject
export class Register {
  @observable user: User;

  constructor(private api: ApiService) {
    this.user = new User();
  }

  async registerUser() {
    this.api.post('register', this.user);
  }
}

class User {
  email: string;
  fullname: string;
  password: string;
  confirmPassword: string;
  address: string;
  city: string;
}
