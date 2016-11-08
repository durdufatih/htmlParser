package htmlparser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MyTask extends TimerTask {

	@Override
	public void run() {
		try {
			String mainUrl = "http://www.nefisyemektarifleri.com/kategori/tarifler/page/";

			List<String> foodUrls = new ArrayList();
			for (int i = 30; i < 40; i++) {
				Document mainDocument = Jsoup.connect(mainUrl + i)
						.timeout(600000 * 600000 * 600000 * 40 * 40 * 40 * 40 * 40).get();
				Elements allCategories = mainDocument.select(".post h5 a");
				for (Element category : allCategories) {
					String categoryLink = category.attr("href");
					String categoryName = category.html();
					// cateogryUrlsName.add(categoryName);
					foodUrls.add(categoryLink);

				}
			}

			StringBuilder builder = new StringBuilder();
			for (String foodUrl : foodUrls) {

				Document document = Jsoup.connect(foodUrl).get();
				// Elements links = document.select(".post-img-div a[href]");
				// document.select(".wp-pagenavi");

				/*
				 * List<String> urls = new ArrayList<String>(); for (Element
				 * answerer : links) { if (!answerer.attr("title").equals("Profil"))
				 * { String hrefs = answerer.attr("href"); urls.add(hrefs);
				 * System.out.println("Linkler: " + hrefs); } }
				 */

				List<Food> foods = new ArrayList();

				Food food = new Food();

				// String name = cateogryUrlsName.get(foodUrls.indexOf(foodUrl));
				// food.setCategory(name);

				String cTime = "";
				String time = "";

				Elements title = document.select("strong[itemprop = name]");
				food.setName(title.html());

				Elements recipeYield = document.select("span[itemprop = recipeYield] strong");
				food.setCookingPerson(recipeYield.html());
				Elements prepTime = document.select("meta[itemprop = prepTime]");
				if (!prepTime.isEmpty())
					time = prepTime.attr("content").substring(2, prepTime.attr("content").length());

				food.setPreTime(time);
				Elements cookTime = document.select("meta[itemprop = cookTime]");
				if (!cookTime.isEmpty())
					cTime = cookTime.attr("content").substring(2, cookTime.attr("content").length());
				food.setCookTime(cTime);
				Elements linksUrl = document.select("li[itemprop = ingredients]");

				Elements entry_content = document.select("div.entry_content p");
				StringBuilder make = new StringBuilder();
				for (Element element : entry_content) {
					Elements elements = element.getElementsByTag("img");
					if (element.getElementsByTag("img").isEmpty())
						make.append(element.html());
				}
				food.setMaking(make.toString());

				List<Ingredients> ingredientList = new ArrayList();
				StringBuilder stringBuilder = new StringBuilder();
				for (Element urlsElement : linksUrl) {
					Ingredients ingredients = new Ingredients();
					String ingredientsName = "";
					String count = "";
					String type = "";
					String elements[] = null;

					if (urlsElement.html().toLowerCase().contains("ince dilimlenmi�")) {
						elements = urlsElement.html().toLowerCase().split("ince dilimlenmi�");
						type = "ince dilinlenmi�";
					}

					else if (urlsElement.html().toLowerCase().contains("yemek ka����")) {
						elements = urlsElement.html().toLowerCase().split("yemek ka����");
						type = "yemek ka����";
					}

					else if (urlsElement.html().toLowerCase().contains("yemek ka��")) {
						elements = urlsElement.html().toLowerCase().split("yemek ka��");
						type = "yemek ka����";
					} else if (urlsElement.html().toLowerCase().contains("yemel ka��")) {
						elements = urlsElement.html().toLowerCase().split("yemel ka��");
						type = "yemek ka����";
					} else if (urlsElement.html().toLowerCase().contains("�orba ka����")) {
						elements = urlsElement.html().toLowerCase().split("�orba ka����");
						type = "�orba ka����";
					} else if (urlsElement.html().toLowerCase().contains("ml")) {
						elements = urlsElement.html().toLowerCase().split("ml");
						type = "mililitre";
					} else if (urlsElement.html().toLowerCase().contains("ka��k")) {
						elements = urlsElement.html().toLowerCase().split("ka��k");
						type = "ka��k";
					} else if (urlsElement.html().toLowerCase().contains("ba�")) {
						elements = urlsElement.html().toLowerCase().split("ba�");
						type = "ba�";
					} else if (urlsElement.html().toLowerCase().contains("litre")) {
						elements = urlsElement.html().toLowerCase().split("litre");
						type = "litre";
					} else if (urlsElement.html().toLowerCase().contains("kilo")) {
						elements = urlsElement.html().toLowerCase().split("kilo");
						type = "kilo";
					} else if (urlsElement.html().toLowerCase().contains("damla")) {
						elements = urlsElement.html().toLowerCase().split("damla");
						type = "damla";
					} else if (urlsElement.html().toLowerCase().contains("fincan")) {
						elements = urlsElement.html().toLowerCase().split("fincan");
						type = "fincan";
					}

					else if (urlsElement.html().toLowerCase().contains("�i�e")) {
						elements = urlsElement.html().toLowerCase().split("�i�e");
						type = "�i�e";
					} else if (urlsElement.html().toLowerCase().contains("avu�")) {
						elements = urlsElement.html().toLowerCase().split("avu�");
						type = "avu�";
					} else if (urlsElement.html().toLowerCase().contains("�det")) {
						elements = urlsElement.html().toLowerCase().split("�det");
						type = "adet";
					} else if (urlsElement.html().toLowerCase().contains("ba�")) {
						elements = urlsElement.html().toLowerCase().split("ba�");
						type = "ba�";
					} else if (urlsElement.html().toLowerCase().contains("tutam")) {
						elements = urlsElement.html().toLowerCase().split("tutam");
						type = "tutam";
					} else if (urlsElement.html().toLowerCase().contains("lt")) {
						elements = urlsElement.html().toLowerCase().split("lt");
						type = "lt";
					} else if (urlsElement.html().toLowerCase().contains("dilim")) {
						elements = urlsElement.html().toLowerCase().split("dilim");
						type = "dilim";
					} else if (urlsElement.html().toLowerCase().contains("barda�a yak�n")) {
						elements = urlsElement.html().toLowerCase().split("barda�a yak�n");
						type = "barda�a yak�n";
					} else if (urlsElement.html().toLowerCase().contains("yaprak")) {
						elements = urlsElement.html().toLowerCase().split("yaprak");
						type = "yaprak";
					} else if (urlsElement.html().toLowerCase().contains("adet")) {
						elements = urlsElement.html().toLowerCase().split("adet");
						type = "adet";
					} else if (urlsElement.html().toLowerCase().contains("kase")) {
						elements = urlsElement.html().toLowerCase().split("kase");
						type = "kase";
					} else if (urlsElement.html().toLowerCase().contains("tane")) {
						elements = urlsElement.html().toLowerCase().split("tane");
						type = "tane";
					} else if (urlsElement.html().toLowerCase().contains("g�re")) {
						elements = urlsElement.html().toLowerCase().split("g�re");
						type = "g�re";
					} else if (urlsElement.html().toLowerCase().contains("yar�m demet")) {
						elements = urlsElement.html().toLowerCase().split("yar�m demet");
						type = "yar�m demet";
					} else if (urlsElement.html().toLowerCase().contains("demet")) {
						elements = urlsElement.html().toLowerCase().split("demet");
						type = "demet";
					} else if (urlsElement.html().toLowerCase().contains("dal")) {
						elements = urlsElement.html().toLowerCase().split("dal");
						type = "dal";
					} else if (urlsElement.html().toLowerCase().contains("kg")) {
						elements = urlsElement.html().toLowerCase().split("kg");
						type = "kg";
					} else if (urlsElement.html().toLowerCase().contains("gram")) {
						elements = urlsElement.html().toLowerCase().split("gram");
						type = "gram";
					} else if (urlsElement.html().toLowerCase().contains("gr")) {
						elements = urlsElement.html().toLowerCase().split("gr");
						type = "gr";
					} else if (urlsElement.html().toLowerCase().contains("tatl� ka����")) {
						elements = urlsElement.html().toLowerCase().split("tatl� ka����");
						type = "tatl� ka����";
					} else if (urlsElement.html().toLowerCase().contains("�ay ka����")) {
						elements = urlsElement.html().toLowerCase().split("�ay ka����");
						type = "�ay ka����";
					} else if (urlsElement.html().toLowerCase().contains("kangal")) {
						elements = urlsElement.html().toLowerCase().split("kangal");
						type = "kangal";
					} else if (urlsElement.html().toLowerCase().contains("su barda��")) {
						elements = urlsElement.html().toLowerCase().split("su barda��");
						type = "su barda��";
					} else if (urlsElement.html().toLowerCase().contains("su b")) {
						elements = urlsElement.html().toLowerCase().split("su b");
						type = "su barda��";
					} else if (urlsElement.html().toLowerCase().contains("di�")) {
						elements = urlsElement.html().toLowerCase().split("di�");
						type = "di�";
					} else if (urlsElement.html().toLowerCase().contains("�ay barda��")) {
						elements = urlsElement.html().toLowerCase().split("�ay barda��");
						type = "�ay barda��";
					} else if (urlsElement.html().toLowerCase().contains("orta boy")) {
						elements = urlsElement.html().toLowerCase().split("orta boy");
						type = "orta boy";
					} else if (urlsElement.html().toLowerCase().contains("bardak")) {
						elements = urlsElement.html().toLowerCase().split("bardak");
						type = "bardak";
					} else if (urlsElement.html().toLowerCase().contains("paket")) {
						elements = urlsElement.html().toLowerCase().split("paket");
						type = "paket";
					} else if (urlsElement.html().toLowerCase().contains("b�y�k boy")) {
						elements = urlsElement.html().toLowerCase().split("b�y�k boy");
						type = "boy";
					} else if (urlsElement.html().toLowerCase().contains("k���k")) {
						elements = urlsElement.html().toLowerCase().split("k���k");
						type = "k���k";
					} else if (urlsElement.html().toLowerCase().contains("b�y�k")) {
						elements = urlsElement.html().toLowerCase().split("b�y�k");
						type = "b�y�k";
					} else if (urlsElement.html().toLowerCase().contains("kal�p")) {
						elements = urlsElement.html().toLowerCase().split("kal�p");
						type = "kal�p";
					} else if (!urlsElement.html().toLowerCase().contains(" ")) {
						elements = urlsElement.html().split(" ");
						type = " ";
					} else if (urlsElement.html().toLowerCase().contains(",")) {
						ingredientsName = urlsElement.html();
					}

					else
						ingredientsName = urlsElement.html().replaceFirst("\\s+", "");

					if (elements != null) {
						ingredientsName = elements[elements.length - 1].replaceFirst("\\s+", "");
						if (elements.length == 2)
							count = elements[0].replaceFirst("\\s+", "");
					}
					ingredients.setName(ingredientsName.toLowerCase());
					stringBuilder.append(ingredientsName.toLowerCase());
					stringBuilder.append(" ");

					ingredients.setType(type);
					ingredients.setCount(count);

					ingredientList.add(ingredients);

				}
				food.setSearchText(stringBuilder.toString());
				food.setIngredients(ingredientList);

				try {
					if (food != null) {
						ObjectMapper mapper = new ObjectMapper();
						builder.append(mapper.writeValueAsString(food));
						System.out.println("Normal");
						System.out.println("C:\\food\\"
								+ food.getName().replaceAll("\\s+", "").replaceAll("\\*", "").replaceAll("\\\\8", "")
								+ ".json");
						mapper.writeValue(new File("C:\\food\\"
								+ food.getName().replaceAll("\\s+", "").replaceAll("\\*", "").replaceAll("\\\\8", "")
								+ ".json"), food);
					}
				} catch (Exception e) {
					ObjectMapper mapper = new ObjectMapper();
					System.out.println("Test");
					mapper.writeValue(new File("C:\\food\\"
							+ e.getMessage().replaceAll("\\s+", "").replaceAll("\\*", "").replaceAll("\\\\8", "")
							+ ".json"), food);
				}

				// firebase.getRoot().setValue(food);

			}
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
