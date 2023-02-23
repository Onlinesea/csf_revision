// Implement the methods in PizzaService for Task 3
// Add appropriate parameter and return type 

import { HttpClient } from "@angular/common/http";
import { Order, OrderSummary } from "./models";
import { firstValueFrom, lastValueFrom } from 'rxjs';
import { Injectable } from "@angular/core";


@Injectable()
export class PizzaService {

  
  constructor(private http:HttpClient) { }

  // POST /api/order
  // Add any required parameters or return type
  createOrder(newOrder:Order) { 

    
    return firstValueFrom(
          this.http.post('/api/order', newOrder));




  }

  // GET /api/order/<email>/all
  // Add any required parameters or return type
  getOrders(email: string): Promise<OrderSummary[]> { 


    return firstValueFrom(
      this.http.get<OrderSummary[]>(`/api/order/${email}/all`));
  }

}
