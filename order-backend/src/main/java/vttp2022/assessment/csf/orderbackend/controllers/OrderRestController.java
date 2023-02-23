package vttp2022.assessment.csf.orderbackend.controllers;


import java.io.StringReader;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.repositories.OrderRepository;
import vttp2022.assessment.csf.orderbackend.services.OrderService;

@RestController
@RequestMapping(path="/api")
public class OrderRestController {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderService orderSvc;

    @PostMapping(path="/order")
    public ResponseEntity<String> postFromAngular(@RequestBody String payload){
        
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject json = reader.readObject();

        System.out.println("newOrder >>>> " + payload);
        Order newOrder = Order.fromPayload(json);
        Document inserted = orderRepo.insertOrder(newOrder);
        // System.out.println("ObjectId >>> " + inserted.getString("_id"));


        return ResponseEntity.ok().body(inserted.toJson().toString());
    }

    @GetMapping(path="/order/{email}/all")
    public ResponseEntity<String> getAllOrdersSummary(@PathVariable String email){

        List<OrderSummary> orders = orderSvc.getOrdersByEmail(email);
		System.out.println("Gotten the result from repo >>> " + email);

        String result= orders.stream()
                        .map(os-> os.toJsonObject(os))
                        .toList()
                        .toString();

        return ResponseEntity.ok().body(result);

    }


}
