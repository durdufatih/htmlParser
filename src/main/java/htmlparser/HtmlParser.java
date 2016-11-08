package htmlparser;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.firebase.client.Firebase;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class HtmlParser {

	public static void main(String[] args) throws IOException {
	        // Standard URI format: mongodb://[dbuser:dbpassword@]host:port/dbname
	       
		Firebase firebase = new Firebase("https://utility-canto-384.firebaseio.com/");
		
		
//		String mainUrl = "http://www.nefisyemektarifleri.com/tarifler/";
//
//		Document mainDocument = Jsoup.connect(mainUrl).get();
//
//		List<String> categoryUrls = new ArrayList();
//
//		Elements allCategories = mainDocument.select(".kategori-detay a");
//		for (Element category : allCategories) {
//			String categoryLink = category.attr("href");
//			categoryUrls.add(categoryLink);
//
//		}
//
//		for (String categoryUrl : categoryUrls) {
//			Document document = Jsoup.connect(categoryUrl).get();
//			Elements links = document.select(".post-img-div a[href]");
//			
//			List<String> urls = new ArrayList<String>();
//			for (Element answerer : links) {
//				if (!answerer.attr("title").equals("Profil")) {
//					String hrefs = answerer.attr("href");
//					urls.add(hrefs);
//					System.out.println("Linkler: " + hrefs);
//				}
//			}
//
//			for (String urlString : urls) {
//
//				Document documentUrl = Jsoup.connect(urlString).get();
//				Elements title = documentUrl.select("strong[itemprop = name]");
//
//				Elements linksUrl = documentUrl.select("li[itemprop = ingredients]");
//
//				System.out.println(title.html());
//				System.out.println("Yemek Malzemeleri::::::");
//				System.out.println("---------    ------------");
//				for (Element urlsElement : linksUrl) {
//					System.out.println("Malzemeler: " + urlsElement.html());
//				}
//
//			}
//
//		}
		
		String mainUrl = "http://www.nefisyemektarifleri.com/kategori/tarifler/page/";

		List<String> foodUrls = new ArrayList();
		for (int i = 50; i < 60; i++) {
			Document mainDocument = Jsoup.connect(mainUrl+i).timeout(600000*600000*600000*40*40*40*40*40).get();
			Elements allCategories = mainDocument.select(".post h5 a");
			for (Element category : allCategories) {
				String categoryLink = category.attr("href");
				String categoryName = category.html();
				//cateogryUrlsName.add(categoryName);
				foodUrls.add(categoryLink);

			}
		}
		

		StringBuilder builder=new StringBuilder();
		for (String foodUrl : foodUrls) {

			Document document = Jsoup.connect(foodUrl).get();
			//Elements links = document.select(".post-img-div a[href]");
			//document.select(".wp-pagenavi");

			/*List<String> urls = new ArrayList<String>();
			for (Element answerer : links) {
				if (!answerer.attr("title").equals("Profil")) {
					String hrefs = answerer.attr("href");
					urls.add(hrefs);
					System.out.println("Linkler: " + hrefs);
				}
			}*/

			List<Food> foods=new ArrayList();
			
				
				Food food = new Food();

				//String name = cateogryUrlsName.get(foodUrls.indexOf(foodUrl));
				//food.setCategory(name);

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
				StringBuilder stringBuilder=new StringBuilder();
				for (Element urlsElement : linksUrl) {
					Ingredients ingredients = new Ingredients();
					String ingredientsName = "";
					String count = "";
					String type = "";
					String elements[] = null;

					  if(urlsElement.html().toLowerCase().contains("ince dilimlenmiþ")){
						  elements = urlsElement.html().toLowerCase().split("ince dilimlenmiþ");
							type = "ince dilinlenmiþ";
	                  }

					  else if (urlsElement.html().toLowerCase().contains("yemek kaþýðý")) {
							elements = urlsElement.html().toLowerCase().split("yemek kaþýðý");
							type = "yemek kaþýðý";
						}
	                               	                  	                  	                 	                 
	                  else if(urlsElement.html().toLowerCase().contains("yemek kaþý")){
	                   elements =urlsElement.html().toLowerCase().split("yemek kaþý");
	                   type= "yemek kaþýðý";
	                  }
	                  else if(urlsElement.html().toLowerCase().contains("yemel kaþý")){
	                   elements =urlsElement.html().toLowerCase().split("yemel kaþý");
	                   type= "yemek kaþýðý";
	                  }	                  
	                  else if(urlsElement.html().toLowerCase().contains("çorba kaþýðý")){
	                   elements =urlsElement.html().toLowerCase().split("çorba kaþýðý");
	                   type="çorba kaþýðý";
	                  }
	                  else if(urlsElement.html().toLowerCase().contains("ml")){
	                   elements =urlsElement.html().toLowerCase().split("ml");
	                   type="mililitre";
	                  }	                 
	                  else if(urlsElement.html().toLowerCase().contains("kaþýk")){
	                   elements =urlsElement.html().toLowerCase().split("kaþýk");
	                   type="kaþýk";
	                  }	                 	                  
	                  else if(urlsElement.html().toLowerCase().contains("baþ")){
	                   elements =urlsElement.html().toLowerCase().split("baþ");
	                   type="baþ";
	                  }
	                  else if(urlsElement.html().toLowerCase().contains("litre")){
	                   elements =urlsElement.html().toLowerCase().split("litre");
	                   type="litre";
	                  }
	                  else if(urlsElement.html().toLowerCase().contains("kilo")){
	                   elements =urlsElement.html().toLowerCase().split("kilo");
	                   type="kilo";
	                  }	                  
	                  else if(urlsElement.html().toLowerCase().contains("damla")){
	                   elements =urlsElement.html().toLowerCase().split("damla");
	                   type="damla";
	                  }
	                  else if(urlsElement.html().toLowerCase().contains("fincan")){
	                   elements =urlsElement.html().toLowerCase().split("fincan");
	                   type="fincan";
	                  }
	                 
	                  else if(urlsElement.html().toLowerCase().contains("þiþe")){
	                   elements =urlsElement.html().toLowerCase().split("þiþe");
	                   type="þiþe";
	                  }
	                  else if(urlsElement.html().toLowerCase().contains("avuç")){
	                   elements =urlsElement.html().toLowerCase().split("avuç");
	                   type="avuç";
	                  }
	                  else if(urlsElement.html().toLowerCase().contains("âdet")){
	                   elements =urlsElement.html().toLowerCase().split("âdet");
	                   type="adet";
	                  }
	                  else if(urlsElement.html().toLowerCase().contains("bað")){
	                   elements =urlsElement.html().toLowerCase().split("bað");
	                   type="bað";
	                  }
	                  else if(urlsElement.html().toLowerCase().contains("tutam")){
	                   elements =urlsElement.html().toLowerCase().split("tutam");
	                   type="tutam";
	                  }
	                  else if(urlsElement.html().toLowerCase().contains("lt")){
	                   elements =urlsElement.html().toLowerCase().split("lt");
	                   type="lt";
	                  }
	                  else if(urlsElement.html().toLowerCase().contains("dilim")){
	                   elements =urlsElement.html().toLowerCase().split("dilim");
	                   type="dilim";
	                  }
	                  else if(urlsElement.html().toLowerCase().contains("bardaða yakýn")){
	                   elements =urlsElement.html().toLowerCase().split("bardaða yakýn");
	                   type="bardaða yakýn";
	                  }
	                  else if(urlsElement.html().toLowerCase().contains("yaprak")){
	                   elements =urlsElement.html().toLowerCase().split("yaprak");
	                   type="yaprak";
	                  }	                  
	                  else if (urlsElement.html().toLowerCase().contains("adet")) {
						elements = urlsElement.html().toLowerCase().split("adet");
						type = "adet";
					} else if (urlsElement.html().toLowerCase().contains("kase")) {
						elements = urlsElement.html().toLowerCase().split("kase");
						type = "kase";
					} else if (urlsElement.html().toLowerCase().contains("tane")) {
						elements = urlsElement.html().toLowerCase().split("tane");
						type = "tane";
					} else if (urlsElement.html().toLowerCase().contains("göre")) {
						elements = urlsElement.html().toLowerCase().split("göre");
						type = "göre";
					}  else if (urlsElement.html().toLowerCase().contains("yarým demet")) {
						elements = urlsElement.html().toLowerCase().split("yarým demet");
						type = "yarým demet";
					}else if(urlsElement.html().toLowerCase().contains("demet")){
	                   elements =urlsElement.html().toLowerCase().split("demet");
	                   type="demet";
					}else if (urlsElement.html().toLowerCase().contains("dal")) {
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
					} else if (urlsElement.html().toLowerCase().contains("tatlý kaþýðý")) {
						elements = urlsElement.html().toLowerCase().split("tatlý kaþýðý");
						type = "tatlý kaþýðý";
					} else if (urlsElement.html().toLowerCase().contains("çay kaþýðý")) {
						elements = urlsElement.html().toLowerCase().split("çay kaþýðý");
						type = "çay kaþýðý";
					} else if (urlsElement.html().toLowerCase().contains("kangal")) {
						elements = urlsElement.html().toLowerCase().split("kangal");
						type = "kangal";
					} else if (urlsElement.html().toLowerCase().contains("su bardaðý")) {
						elements = urlsElement.html().toLowerCase().split("su bardaðý");
						type = "su bardaðý";
					}
					 else if(urlsElement.html().toLowerCase().contains("su b")){
		                   elements =urlsElement.html().toLowerCase().split("su b");
		                   type= "su bardaðý";
		                  }	   else if (urlsElement.html().toLowerCase().contains("diþ")) {
						elements = urlsElement.html().toLowerCase().split("diþ");
						type = "diþ";
					} else if (urlsElement.html().toLowerCase().contains("çay bardaðý")) {
						elements = urlsElement.html().toLowerCase().split("çay bardaðý");
						type = "çay bardaðý";
					} else if (urlsElement.html().toLowerCase().contains("orta boy")) {
						elements = urlsElement.html().toLowerCase().split("orta boy");
						type = "orta boy";
					} else if (urlsElement.html().toLowerCase().contains("bardak")) {
						elements = urlsElement.html().toLowerCase().split("bardak");
						type = "bardak";
					} else if (urlsElement.html().toLowerCase().contains("paket")) {
						elements = urlsElement.html().toLowerCase().split("paket");
						type = "paket";
					}
					else if (urlsElement.html().toLowerCase().contains("büyük boy")) {
						elements = urlsElement.html().toLowerCase().split("büyük boy");
						type = "boy";
					}
					else if (urlsElement.html().toLowerCase().contains("küçük")) {
						elements = urlsElement.html().toLowerCase().split("küçük");
						type = "küçük";
					} else if (urlsElement.html().toLowerCase().contains("büyük")) {
						elements = urlsElement.html().toLowerCase().split("büyük");
						type = "büyük";
					}  
					else if (urlsElement.html().toLowerCase().contains("kalýp")) {
						elements = urlsElement.html().toLowerCase().split("kalýp");
						type = "kalýp";
					}  
					else if (!urlsElement.html().toLowerCase().contains(" ")) {
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
					if(food!=null){
						ObjectMapper mapper = new ObjectMapper();
						builder.append(mapper.writeValueAsString(food));
						System.out.println("Normal");
						System.out.println("C:\\food\\"+food.getName().replaceAll("\\s+","").replaceAll("\\*","").replaceAll("\\\\8","")+".json");
						mapper.writeValue(new File("C:\\food\\"+food.getName().replaceAll("\\s+","").replaceAll("\\*","").replaceAll("\\\\8","")+".json"), food);
					}
				} catch (Exception e) {
						ObjectMapper mapper = new ObjectMapper();
						System.out.println("Test");
						mapper.writeValue(new File("C:\\food\\"+e.getMessage().replaceAll("\\s+","").replaceAll("\\*","").replaceAll("\\\\8","")+".json"), food);
					}
				
				
				
				//firebase.getRoot().setValue(food);
				
			}
			
			
				
		


		
	}

	

}
