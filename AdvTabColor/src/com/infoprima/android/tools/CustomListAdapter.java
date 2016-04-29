package com.infoprima.android.tools;

import com.infoprima.android.advtabcolor.R;
import com.infoprima.android.tools.AppController;
import com.infoprima.android.tools.Product;
 
import java.util.List;
 
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


 
public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Product> productItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
 
    public CustomListAdapter(Activity activity, List<Product> productItems) {
        this.activity = activity;
        this.productItems = productItems;
    }
 
    @Override
    public int getCount() {
        return productItems.size();
    }
 
    @Override
    public Object getItem(int location) {
        return productItems.get(location);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.tab3_list, null);
 
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView ivImage = (NetworkImageView) convertView
                .findViewById(R.id.ivImage);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView sku = (TextView) convertView.findViewById(R.id.sku);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        TextView quantity = (TextView) convertView.findViewById(R.id.quantity);
        
        // getting product data for the row
        Product p = productItems.get(position);
        
        // thumbnail image
        ivImage.setImageUrl(p.getThumbnailUrl(), imageLoader);
        // name
        name.setText(p.getName());
        // sku
        sku.setText(p.getSku());
        // description
        description.setText(p.getDescription());
        // price
        price.setText(p.getPrice());
        // quantity
        quantity.setText(p.getQuantity());

          
        return convertView;
    }
 
}
