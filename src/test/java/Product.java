
import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import java.io.File;
import static org.hamcrest.CoreMatchers.containsString;
import org.junit.Rule;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author badri
 */
public class Product {
      @Rule
    public WireMockRule wiremockrule = new WireMockRule(8080);
      
      
    @Test
    public void testProductName(){
        int productId=1;
        mockproduct();
        
        when().
                get("http://localhost:8080/product/{productId}",productId).
        then().
                body("name",containsString("A green door"));
    }
    
    @Test
    public void testProductSchema(){
        int productId=1;
        mockproduct();
            when().
                get("http://localhost:8080/product/{productId}",productId).
            then().
                body(matchesJsonSchema(new File("src/test/resources/__files/ProductSchema.json")));
    }   
      
      
      
      
    public void mockproduct(){
        wiremockrule.stubFor(WireMock.get(urlEqualTo("/product/1"))      
        .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBodyFile("Product.json")));
        wiremockrule.start();
    }
    
    }

