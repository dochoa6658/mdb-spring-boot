package com.example.mdbspringboot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.example.mdbspringboot.model.GroceryItem;
import com.example.mdbspringboot.repository.CustomItemRepository;
import com.example.mdbspringboot.repository.ItemRepository;

@SpringBootApplication
@EnableMongoRepositories
public class MdbSpringBootApplication implements CommandLineRunner {

	@Autowired
	ItemRepository groceryItemRepo;

	@Autowired
	CustomItemRepository customRepo;

	public static void main(String[] args) {
		SpringApplication.run(MdbSpringBootApplication.class, args);
	}

	@Override
	public void run(String... args) {

		System.out.println("\n-------------CREATE GROCERY ITEMS-------------------------------\n");

		createGroceryItems();

		System.out.println("\n----------------SHOW ALL GROCERY ITEMS---------------------------\n");

		showAllGroceryItems();

		System.out.println("\n--------------GET ITEM BY NAME-----------------------------------\n");

		getGroceryItemByName("Whole Wheat Biscuit");

		System.out.println("\n-----------GET ITEMS BY CATEGORY---------------------------------\n");

		getItemsByCategory("millets");

		System.out.println("\n-----------UPDATE CATEGORY NAME OF SNACKS CATEGORY----------------\n");

		updateCategoryName("snacks");

		System.out.println("\n----------DELETE A GROCERY ITEM----------------------------------\n");

		deleteGroceryItem("Kodo Millet");

		System.out.println("\n------------FINAL COUNT OF GROCERY ITEMS-------------------------\n");

		findCountOfGroceryItems();

		System.out.println("\n-----------UPDATE QUANTITY OF A GROCERY ITEM------------------------\n");

		updateItemQuantity("Bonny Cheese Crackers Plain", 10);

		System.out.println("\n-------------------THANK YOU---------------------------");

	}

	private void createGroceryItems() {
		System.out.println("Data creation started...");
		List<GroceryItem> groceryItemList = List.of(
				GroceryItem.builder().id("Whole Wheat Biscuit").name("Whole Wheat Biscuit").quantity(5)
						.category("snacks").build(),
				GroceryItem.builder().id("Kodo Millet").name("XYZ Kodo Millet healthy").quantity(2).category("millets")
						.build(),
				GroceryItem.builder().id("Dried Red Chilli").name("Dried Whole Red Chilli").quantity(2)
						.category("spices").build(),
				GroceryItem.builder().id("Pearl Millet").name("Healthy Pearl Millet").quantity(1).category("millets")
						.build(),
				GroceryItem.builder().id("Cheese Crackers").name("Bonny Cheese Crackers Plain").quantity(6)
						.category("snacks").build());
		groceryItemRepo.saveAll(groceryItemList);
		System.out.println("Data creation complete...");
	}

	private void showAllGroceryItems() {
		groceryItemRepo.findAll().forEach(item -> getItemDetails(item));
	}

	private void getGroceryItemByName(String name) {
		System.out.format("Getting item by name: %s\n", name);
		getItemDetails(groceryItemRepo.findItemByName(name));
	}

	private void getItemsByCategory(String category) {
		System.out.format("Getting items for the category %s\n", category);
		groceryItemRepo.findAllByCategory(category)
				.forEach(item -> System.out.format("Name: %s, Quantity: %s\n", item.getName(), item.getQuantity()));
	}

	private void findCountOfGroceryItems() {
		System.out.format("Number of documents in the collection: %s\n", groceryItemRepo.count());
	}

	private void updateCategoryName(String category) {
		String newCategory = "munchies";

		List<GroceryItem> groceryItemList = groceryItemRepo.findAllByCategory(category);
		groceryItemList.forEach(item -> item.setCategory(newCategory));

		List<GroceryItem> updatedItems = groceryItemRepo.saveAll(groceryItemList);
		if (updatedItems != null) {
			System.out.format("Successfully updated %s items.\n", updatedItems.size());
		}
	}

	private void deleteGroceryItem(String id) {
		groceryItemRepo.deleteById(id);
		System.out.format("Item with id %s deleted...\n", id);
	}

	private void updateItemQuantity(String name, float newQuantity) {
		System.out.format("Updating quantity for %s\n", name);
		customRepo.updateItemQuantity(name, newQuantity);
	}

	private void getItemDetails(GroceryItem item) {
		System.out.format("Item Name: %s,\n" + "Quantity: %s,\n" + "Item Category: %s\n\n", item.getName(),
				item.getQuantity(), item.getCategory());
	}
}
