package ice_pbru.kittipongnuanyai.ice_database;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by kittipongnuanyai on 4/28/16 AD.
 */
public class ProductAdapter extends BaseAdapter {

    //Explicit
    private Context context;
    private String[] iconStrings, titleStrings , priceStrings;

    public ProductAdapter(Context context, String[] iconStrings, String[] titleStrings, String[] priceStrings) {
        this.context = context;
        this.iconStrings = iconStrings;
        this.titleStrings = titleStrings;
        this.priceStrings = priceStrings;

        Log.d("Kittipong -->", "Pic -->" + iconStrings[2].toString());
        Log.d("Kittipong -->", "name -->" + titleStrings[2].toString());
        Log.d("Kittipong -->", "price -->" + priceStrings[2].toString());

    }


    @Override
    public int getCount() {
        return iconStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.product_listview, viewGroup, false);

        TextView titleTextView = (TextView) view1.findViewById(R.id.editText7);
        titleTextView.setText(titleStrings[i]);

        TextView priceTextView = (TextView) view1.findViewById(R.id.textView8);
        priceTextView.setText(priceStrings[i]);

        ImageView iconImageView = (ImageView) view1.findViewById(R.id.imageView2);
        Picasso.with(context).load(iconStrings[i]).resize(100, 140).into(iconImageView);


        return view1;
    }
}
