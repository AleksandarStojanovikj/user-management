import { Router } from 'aurelia-router';
import { ApiService } from './../shared/ApiService';
import { autoinject } from "aurelia-framework";

@autoinject
export class EditProfile {
  user: User;

  constructor(private api: ApiService, private router: Router) { }

  async created() {
    this.fetchUserDetails();
  }
  async fetchUserDetails() {
    let userId = localStorage.getItem('userId');
    this.user = new User(await this.api.get(`user/${userId}`));
    console.log(this.user);
  }

  async editDetails() {
    this.api.post("edit-details", this.user);
  }
}

class User {
  id: number;
  fullName: string;
  city: string;
  address: string;
  email: string

  constructor(json: any) {
    this.id = json.id;
    this.fullName = json.fullname;
    this.email = json.email;
    this.city = json.city;
    this.address = json.address;
  }
}
