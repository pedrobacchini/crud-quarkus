package com.github.pedrobacchini;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @GET
    public List<Product> listAllProducts() {
        return Product.listAll();
    }

    @POST
    @Transactional
    public void createProduct(ProductInput productInput) {
        Product p = new Product();
        p.setName(productInput.getNome());
        p.setPrice(productInput.getPrice());
        p.persist();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void updateProduct(@PathParam("id") Long id, ProductInput productInput) {
        Optional<Product> productOp = Product.findByIdOptional(id);
        if (productOp.isPresent()) {
            Product product = productOp.get();
            product.setName(productInput.getNome());
            product.setPrice(productInput.getPrice());
            product.persist();
        } else {
            throw new NotFoundException();
        }
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deleteProduct(@PathParam("id") Long id) {
        Optional<Product> productOp = Product.findByIdOptional(id);
        productOp.ifPresentOrElse(Product::delete, () -> {throw new NotFoundException();});
    }
}
