package vttp2022.assessment.csf.orderbackend.models;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.assessment.csf.orderbackend.services.PricingService;

// IMPORTANT: You can add to this class, but you cannot delete its original content

public class OrderSummary {
	private Integer orderId;
	private String name;
	private String email;
	private Float amount;

	public void setOrderId(Integer orderId) { this.orderId = orderId; }
	public Integer getOrderId() { return this.orderId; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return this.email; }

	public void setAmount(Float amount) { this.amount = amount; }
	public Float getAmount() { return this.amount; }

	@Autowired
	private static PricingService priceSvc;

	public static OrderSummary createOrderSummaryFromDocs(Document document){


		Order o = Order.fromDocument(document);
		System.out.println("Gotten the result from repo >>> " + document);

        OrderSummary order = new OrderSummary();
        order.setOrderId(document.getInteger("orderId"));
        order.setName(document.getString("name"));
        order.setEmail(document.getString("email"));
		order.setAmount(calulatePricing(o));
		
		return order;
	}

	public JsonObject toJsonObject(OrderSummary os){

		
		return Json.createObjectBuilder()
					.add("orderId", os.getOrderId())
					.add("name", os.getName())
					.add("email",os.getEmail())
					.add("amount", os.getAmount())
					.build();
	}

	public static Float calulatePricing (Order order){

		PricingService ps = new PricingService();
		System.out.println("Caluclating price >>> " + order.getName());
		
		Float sizeCost;

		switch (order.getSize()) {
			case 6:
				sizeCost=ps.size(0);
				break;
			case 9:
				sizeCost=ps.size(1);
				break;
			case 12:
				sizeCost=ps.size(2);
				break;
			case 15:
				sizeCost=ps.size(3);
				break;
			default:
				sizeCost=0f;
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> wrong size");
				break;
			}

		Float toppingPrice = 0f;
			for (String topping : order.getToppings()) {
				float price = ps.topping(topping);
				if (price != -1000f) {
					toppingPrice += price;
				}
			}
		Float saucePrice = ps.sauce(order.getSauce());
		Float crustPrice = order.isThickCrust()? ps.thickCrust() : ps.thinCrust();

		return saucePrice+sizeCost+toppingPrice+crustPrice;

}
}
