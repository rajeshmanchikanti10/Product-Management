package com.cloudbees.productmanagement;

import com.cloudbees.productmanagement.DBEntities.Product;
import com.cloudbees.productmanagement.enums.PriceModificationType;
import com.cloudbees.productmanagement.models.ProductRequest;
import com.cloudbees.productmanagement.repository.ProductRepository;
import com.cloudbees.productmanagement.resources.ProductResource;
import com.cloudbees.productmanagement.response.ProductResponse;
import com.cloudbees.productmanagement.service.ProductService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductmanagementApplicationTests {
	@Autowired
	private ProductRepository productRepository;
	private ProductService productService;
	private ProductResource productResource;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		productService = new ProductService(productRepository);
		productResource = new ProductResource(productService);
	}

	@Test
	void testCreateProduct() {
		ProductRequest product = ProductRequest.builder()
				.name("Test Product")
				.description("Test description")
				.price(50)
				.modificationType(PriceModificationType.NONE)
				.build();
		ProductResponse<ProductRequest> response = productResource.createProduct(product);
		response.getData().setModificationType(product.getModificationType());
		assertEquals(product, response.getData());
	}

	@Test
	void applyDiscountOnCreation() {
		ProductRequest productRequest = ProductRequest.builder()
				.modificationType(PriceModificationType.DISCOUNT)
				.name("Test Product")
				.description("Test description")
				.price(50)
				.quantity(6)
				.percentage(3)
				.build();
		ProductResponse<ProductRequest> response = productResource.createProduct(productRequest);
		response.getData().setModificationType(productRequest.getModificationType());
		response.getData().setPercentage(productRequest.getPercentage());
		productRequest.setPrice(48.5f);
		assertEquals(productRequest, response.getData());

	}

	@Test
	void applyTaxOnCreation() {
		ProductRequest productRequest = ProductRequest.builder()
				.modificationType(PriceModificationType.TAX)
				.name("Test Product")
				.description("Test description")
				.price(50)
				.quantity(6)
				.percentage(3)
				.build();
		ProductResponse<ProductRequest> response = productResource.createProduct(productRequest);
		response.getData().setModificationType(productRequest.getModificationType());
		response.getData().setPercentage(productRequest.getPercentage());
		productRequest.setPrice(51.5f);
		assertEquals(productRequest, response.getData());
	}

	@Test
	void updateProduct() {
		ProductRequest product = ProductRequest.builder()
				.name("Test Product")
				.description("Test description")
				.price(50)
				.modificationType(PriceModificationType.NONE)
				.build();
		ProductResponse<ProductRequest> response = productResource.createProduct(product);

		ProductRequest getProduct = productResource.getProduct(response.getData().getProductId()).getData();
		getProduct.setDescription("Updated description");
		getProduct.setPrice(40);
		getProduct.setQuantity(12);
		getProduct.setModificationType(PriceModificationType.NONE);

		ProductResponse<ProductRequest> updateResponse = productResource.updateProduct(getProduct);
		updateResponse.getData().setModificationType(getProduct.getModificationType());
		assertEquals(getProduct, updateResponse.getData());

	}

	@Test
	void updateProductWithDiscount() {
		ProductRequest product = ProductRequest.builder()
				.name("Test Product")
				.description("Test description")
				.price(50)
				.modificationType(PriceModificationType.NONE)
				.build();
		ProductResponse<ProductRequest> response = productResource.createProduct(product);

		ProductRequest getProduct = productResource.getProduct(response.getData().getProductId()).getData();
		getProduct.setPrice(50);
		getProduct.setModificationType(PriceModificationType.DISCOUNT);
		getProduct.setPercentage(5);

		ProductResponse<ProductRequest> updateResponse = productResource.updateProduct(getProduct);
		getProduct.setPrice(47.5f);
		updateResponse.getData().setModificationType(getProduct.getModificationType());
		updateResponse.getData().setPercentage(5);
		assertEquals(getProduct, updateResponse.getData());


	}

	@Test
	void updateProductWithTax() {
		ProductRequest product = ProductRequest.builder()
				.name("Test Product")
				.description("Test description")
				.price(50)
				.modificationType(PriceModificationType.NONE)
				.build();
		ProductResponse<ProductRequest> response = productResource.createProduct(product);

		ProductRequest getProduct = productResource.getProduct(response.getData().getProductId()).getData();
		getProduct.setPrice(50);
		getProduct.setModificationType(PriceModificationType.TAX);
		getProduct.setPercentage(5);

		ProductResponse<ProductRequest> updateResponse = productResource.updateProduct(getProduct);
		getProduct.setPrice(52.499996f);
		updateResponse.getData().setModificationType(getProduct.getModificationType());
		updateResponse.getData().setPercentage(5);
		assertEquals(getProduct, updateResponse.getData());


	}

}
