import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { OrderSummary } from 'src/app/models';
import { PizzaService } from 'src/app/pizza.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  OrderList!:OrderSummary[]
  routeSub$!: Subscription;
  email!:string


  constructor(private activatedRoute:ActivatedRoute,
              private svc:PizzaService){}


  ngOnInit(): void {
    this.routeSub$= this.activatedRoute.params.subscribe(params=> {
      this.email=params['email'];
    }
    )
    this.svc.getOrders(this.email).then(reponse => {
      this.OrderList=reponse;
    }).catch(error => {
      console.log(error);
    })
      
  }




}
