import { Router } from 'aurelia-router';
import { ApiService } from 'shared/ApiService';
import { autoinject } from "aurelia-framework";

@autoinject
export class Login {
  user: User;
  rememberMeChecked: boolean;

  constructor(private api: ApiService, private router: Router) { }

  async signInUser() {
    this.api.persistStorage = this.rememberMeChecked;

    // let response = await this.api.authenticate(this.user);

    let response = await this.api.login(this.user);
    if (response === undefined){

    }
    else {
      this.api._currentUserId = response.id;
      this.router.navigate("app");
    }
  }
}

class User {
  email: string;
  password: string;
}
