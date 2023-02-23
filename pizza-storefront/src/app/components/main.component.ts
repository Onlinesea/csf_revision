import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Order } from '../models';
import { PizzaService } from '../pizza.service';


const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

const PizzaToppings: string[] = [
    'chicken', 'seafood', 'beef', 'vegetables',
    'cheese', 'arugula', 'pineapple'
]

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  orderForm!: FormGroup;
  order!:Order
  selectedToppings:string[] =[]
  email!:string

  constructor(public fb: FormBuilder, private svc:PizzaService, private router:Router) {}

  pizzaSize = SIZES[0]


  ngOnInit(): void {
    this.orderForm = this.createForm()
    console.log(this.orderForm.valid)
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }

  private createForm(): FormGroup {
    return this.fb.group({
      name: this.fb.control<string>('fred', [ Validators.required] ),
      email: this.fb.control<string>('fred@gmail.com', [ Validators.required,Validators.email]),
      base: this.fb.control<string>('', [ Validators.required] ),
      sauce: this.fb.control<string>('', [ Validators.required] ),
      comments: this.fb.control<string>('')
    })
  }
  
  placeOrder(){
    this.email= this.orderForm.value['email']
    let newOrder:Order={
      name:this.orderForm.value['name'],
      email:this.orderForm.value['email'],
      size:this.pizzaSize,
      base:this.orderForm.value['base'],
      sauce:this.orderForm.value['sauce'],
      toppings:this.selectedToppings,
      comments:this.orderForm.value['comments'],
      }

      this.svc.createOrder(newOrder).then(response =>{
        console.log(response);

      })
      this.router.navigate([`orders/${this.email}`])

      
    // console.info(this.orderForm)
    // console.log("name >>> " + newOrder.name)
    // console.log("email >>> " + newOrder.email)
    // console.log("size >>> " + newOrder.size)
    // console.log("base >>> " + newOrder.base)
    // console.log("sauce >>> " + newOrder.sauce)
    // console.log("toppings >>> " + newOrder.toppings)
    // console.log("comments >>> " + newOrder.comments)

  }

  updateSelectedToppings(event: any) {
    if (event.target.checked) {
      this.selectedToppings.push(event.target.value);
    } else {
      const index = this.selectedToppings.indexOf(event.target.value);
      if (index !== -1) {
        this.selectedToppings.splice(index, 1);
      }
    }
    console.log("name error >>>>" + this.orderForm.get('name')?.invalid)
    console.log("email error >>>>" + this.orderForm.get('email')?.invalid)
    console.log("base error >>>>" + this.orderForm.get('base')?.invalid)
    console.log("sauce error >>>>" + this.orderForm.get('sauce')?.invalid)
    console.log("comments error >>>>" + this.orderForm.get('comments')?.invalid)
    console.log("toppings >>>" + this.selectedToppings)
    console.log(this.orderForm.valid)

  }


}
