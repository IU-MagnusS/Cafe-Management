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
  { state: 'category', name: 'Category', type: 'link', icon: 'category', role: 'admin' },
  { state: 'product', name: 'Product', type: 'link', icon: 'inventory', role: 'admin' },
  { state: 'order', name: 'Order', type: 'link', icon: 'shopping_cart', role: 'admin' },
  { state: 'bill', name: 'Bill', type: 'link', icon: 'backup_table', role: 'admin' }

];

@Injectable()
export class MenuItems {
  getMenuitem(): Menu[] {
    return MENUITEMS;
  }
}

