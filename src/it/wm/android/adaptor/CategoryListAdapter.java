
package it.wm.android.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import it.wm.perdue.R;

public class CategoryListAdapter extends CustomListAdapter {
    
    private Integer[] _images;
    
    public CategoryListAdapter(Context context, String[] categories, Integer[] images) {
        
        super(context, categories);
        _images = images;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        View v = convertView;
        if (v == null) {
            v = ((LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.category_row, null);
        }
        
        String str = _items[position];
        if (str != null) {
            TextView title = (TextView) v.findViewById(R.id.title);
            if (title != null) {
                title.setText(str);
            }
        }
        Integer image = _images[position];
        ImageView imageRow = (ImageView) v.findViewById(R.id.icon);
        imageRow.setImageResource(image);
        
        return v;
    }
    
}
