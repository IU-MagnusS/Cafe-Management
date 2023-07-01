import { Injectable } from '@angular/core';
<<<<<<< Updated upstream
import { Router } from '@angular/router';
=======
>>>>>>> Stashed changes

@Injectable({
  providedIn: 'root'
})
export class AuthService {

<<<<<<< Updated upstream
  constructor(private router:Router) { }

  public isAuthenticated():boolean{
    const token = localStorage.getItem('token');
    if(!token){
      this.router.navigate(['/']);
      return false;
    }
    else {
      return true;
    }
  }
=======
  constructor() { }
>>>>>>> Stashed changes
}
