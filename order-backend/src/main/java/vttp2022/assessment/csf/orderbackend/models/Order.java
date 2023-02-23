package vttp2022.assessment.csf.orderbackend.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bson.Document;

import jakarta.json.JsonObject;
import jakarta.json.JsonString;

// IMPORTANT: You can add to this class, but you cannot delete its original content

public class Order {

	private Integer orderId;
	private String name;
	private String email;
	private Integer size;
	private String sauce;
	private Boolean thickCrust;
	private List<String> toppings = new LinkedList<>();
	private String comments;

	public void setOrderId(Integer orderId) { this.orderId = orderId; }
	public Integer getOrderId() { return this.orderId; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return this.email; }

	public void setSize(Integer size) { this.size = size; }
	public Integer getSize() { return this.size; }

	public void setSauce(String sauce) { this.sauce = sauce; }
	public String getSauce() { return this.sauce; }

	public void setThickCrust(Boolean thickCrust) { this.thickCrust = thickCrust; }
	public Boolean isThickCrust() { return this.thickCrust; }

	public void setToppings(List<String> toppings) { this.toppings = toppings; }
	public List<String> getToppings() { return this.toppings; }
	public void addTopping(String topping) { this.toppings.add(topping); }

	public void setComments(String comments) { this.comments = comments; }
	public String getComments() { return this.comments; }


	public static Order fromPayload(JsonObject payload){
		Random random = new Random();
        int randomNum = random.nextInt(90000000) + 10000000;
        System.out.println("Random number: " + randomNum);
    
		Order newOrder= new Order();
		newOrder.setOrderId(randomNum);
		newOrder.setName(payload.getString("name"));
		newOrder.setEmail(payload.getString("email"));
		// newOrder.setSize(payload.getString("size"));
		// Set the size 
		newOrder.setSizeWithPayload(payload.getString("size"));
		newOrder.setSauce(payload.getString("sauce"));

		// Set base of new Order 
		if(payload.getString("base").trim().equalsIgnoreCase("thin")){
			newOrder.setThickCrust(false);
		}else{
			newOrder.setThickCrust(true);
		}

		// Set toppings
		// Converting the individual item to list
		List<String> toppingsList = new LinkedList<>();
        toppingsList= payload.getJsonArray("toppings")
						.stream()
						.map(topping -> (JsonString)topping)
						.map(topping -> topping.getString())
						// .map(topping-> topping.toString())
						.toList();
        newOrder.setToppings(toppingsList);

		if(null == payload.getString("comments")){
			newOrder.setComments("No comments");
		}else{
			newOrder.setComments(payload.getString("comments")); 
		}

		// newOrder.setToppings(payload.getJsonArray("toppings").toArray());

		return newOrder;
	}


	public void setSizeWithPayload(String size) { 
		switch (size) {
            case "Personal - 6 inches":
                this.size = 6;
                break;
            case "Regular - 9 inches":
                this.size = 9;
                break;
            case "Large - 12 inches":
                this.size = 12;
                break;
            case "Extra Large - 15 inches":
                this.size = 15;
                break;
            default:
                this.size = 0;
                break;
        }
	}

	public Document toBsonDocument() {
        Document document = new Document();
        document.append("orderId",this.getOrderId());
        document.append("name", this.getName());
        document.append("email", this.getEmail());
        document.append("size", this.getSize());

		if(this.isThickCrust()){
			document.append("base", "thick crust");
		}else{
			document.append("base", "thin crust");

		}

        document.append("sauce", this.getSauce());
        document.append("toppings", this.getToppings());
        document.append("comments", this.getComments());
        return document;
    }

	
    public static Order fromDocument(Document document) {
        Order order = new Order();
        order.setName(document.getString("name"));
        order.setEmail(document.getString("email"));
        order.setSize(document.getInteger("size"));
		if(document.getString("base").trim()=="thick crust"){
			order.setThickCrust(true);
		}else{
			order.setThickCrust(false);
		}
        order.setSauce(document.getString("sauce"));
        order.setToppings(document.getList("toppings", String.class));
        order.setComments(document.getString("comments"));
        return order;
    }

}
