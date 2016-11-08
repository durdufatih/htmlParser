package htmlparser;

import java.util.List;

public class Food {

	private String id;

	private String name;

	private List<Ingredients> ingredients;

	private String cookTime;

	private String preTime;

	private String category;

	private String making;

	private String cookingPerson;
	
	private String searchText;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Ingredients> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredients> ingredients) {
		this.ingredients = ingredients;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCookTime() {
		return cookTime;
	}

	public void setCookTime(String cookTime) {
		this.cookTime = cookTime;
	}

	public String getPreTime() {
		return preTime;
	}

	public void setPreTime(String preTime) {
		this.preTime = preTime;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMaking() {
		return making;
	}

	public void setMaking(String making) {
		this.making = making;
	}

	public String getCookingPerson() {
		return cookingPerson;
	}

	public void setCookingPerson(String cookingPerson) {
		this.cookingPerson = cookingPerson;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
}
