import { Injectable } from "@angular/core";

export interface Menu {
  state: string;
  name: string;
  type: string;
  icon: string;
  role: string;
}

const MENUITEMS: Menu[] = [
  { state: 'dashboard', name: 'Dashboard', type: 'link', icon: 'dashboard', role: 'admin' },
  { state: 'category', name: 'Category', type: 'link', icon: 'category', role: 'admin' }

];

@Injectable()
export class MenuItems {
  getMenuitem(): Menu[] {
    return MENUITEMS;
  }
}
