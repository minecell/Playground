package ch.philnet.playground;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.util.List;

import ch.philnet.foodlocation.FLHandler;
import ch.philnet.foodlocation.FoodLocation;

import static android.content.ContentValues.TAG;

public class FoodLocationsAdapter extends ArrayAdapter<FoodLocation> {
    private final Context context;
    private final List<FoodLocation> locs;
    private FLHandler flHandler;

    public FoodLocationsAdapter(Context context, int resource, List<FoodLocation> objects, FLHandler _flHandler) {
        super(context, resource, objects);
        this.context = context;
        this.locs = objects;
        this.flHandler = _flHandler;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listviewitem, parent, false);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.liTitle);
        TextView txtComment = (TextView) rowView.findViewById(R.id.liComment);
        ImageView ivFavorite = (ImageView) rowView.findViewById(R.id.liFavorite);
        FoodLocation Location = locs.get(position);
        txtTitle.setText(Location.getShop());
        txtComment.setText(Location.getDesc());
        ivFavorite.setImageResource(R.drawable.ic_star_border_accent_24dp);
        if(Location.getFavorite()) {
            ivFavorite.setImageResource(R.drawable.ic_star_accent_24dp);
        }
        return rowView;
    }
}
