package vttp2022.assessment.csf.orderbackend.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2022.assessment.csf.orderbackend.models.Order;

@Repository
public class OrderRepository {
    

    @Autowired
    private MongoTemplate template;


    public Document insertOrder(Order newOrder){
        
        Document doc = newOrder.toBsonDocument();
        Document result = template.insert(doc, "Orders");

        System.out.println("Inserted >>> " + result);

        return result;
    }

     public List<Document> getOrder(String email){
        Criteria c = Criteria.where("email").is(email);
        Query q = Query.query(c);
        List<Document> results=template.find(q, Document.class, "Orders")
                                .stream()
                                .toList();
        System.out.println("doc >>>" + results.toString());

        return results;
     }
}
