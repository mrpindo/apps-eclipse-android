package com.infoprima.android.tools;


public class Product {
    private String name, sku, description, thumbnailUrl, imgUrl, price, quantity;
 
    public Product() {
    }
 
    public Product(String name, String thumbnailUrl, String imgUrl , String sku, String description, String quantity) {
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.imgUrl = imgUrl;
        this.sku = sku;
        this.description = description;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }
     public void setName(String name) {
        this.name = name;
    }

     public String getSku() {
         return sku;
     }
      public void setSku(String sku) {
         this.sku = sku;
     } 
     
      public String getDescription() {
          return description;
      }
       public void setDescription(String description) {
          this.description = description;
      } 

      
      
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
	
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public String getPrice() {
        return price;
    }
     public void setPrice(String price) {
        this.price = price;
    } 

     public String getQuantity() {
         return quantity;
     }
      public void setQuantity(String quantity) {
         this.quantity = quantity;
     } 

     
     
}
