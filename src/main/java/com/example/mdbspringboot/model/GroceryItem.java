package com.example.mdbspringboot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document("item")
@Builder
@Data
public class GroceryItem {
	@Id
	private String id;

	private String name;
	private int quantity;
	private String category;
}
