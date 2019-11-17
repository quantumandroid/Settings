import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class adp_$list_act_name extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;

    private List<HashMap<String, String>> list = null;
    //this is used for filter
    private ArrayList<HashMap<String, String>> arrayList;
    private String mSearchText;
    private ValueFilter valueFilter;

    // create constructor
    public adp_$list_act_name(Context context, List<HashMap<String, String>> list) {

        mContext = context;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<HashMap<String, String>>();
        this.arrayList.addAll(list);

    }

    // declare list item view's
    public class ViewHolder {
        TextView textView1;
        TextView textView2;
        LinearLayout layout;
        // todo add here
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public HashMap<String, String> getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.row_$list_act_name, null);
            // Locate the TextViews in listview_item.xml
            holder.textView1 = (TextView) view.findViewById(R.id.textView.);
            holder.textView2 = (TextView) view.findViewById(R.id.textView.);
            holder.layout = (LinearLayout) view.findViewById(R.id.ll1.);
            // todo add here

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Set the results into TextViews
        final HashMap<String, String> item = list.get(position);

        holder.textView1.setText(item.get("$T1_search"));
        holder.textView2.setText(item.get("$T2"));
        // todo add here

        holder.layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

              
                // TODO Auto-generated method stub
                mContext.startActivity(new Intent(mContext, $DetailActivity .class).putExtra("ID1", item.get("ID1")));
                ((Activity)mContext).finish();
            }
        });

        //  Highlight searched text
        String fullText = item.get("$T1_search");
        if (mSearchText != null && !mSearchText.isEmpty()) {
            int startPos = fullText.toLowerCase(Locale.US).indexOf(mSearchText.toLowerCase(Locale.US));
            int endPos = startPos + mSearchText.length();

            if (startPos != -1) {
                Spannable spannable = new SpannableString(fullText);
                ColorStateList blueColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.BLUE});
                TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null);
                spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.textView1.setText(spannable);
            } else {
                holder.textView1.setText(fullText);
            }
        } else {
            holder.textView1.setText(fullText);
        }
//  End highlights

        return view;
    }

    // method for search list
    public Filter getFilter() {

        if (valueFilter == null) {

            valueFilter = new ValueFilter();
        }

        return valueFilter;
    }

 private class ValueFilter extends Filter {

        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            FilterResults results = new FilterResults();

            if (charSequence != null && charSequence.length() > 0) {

                ArrayList<HashMap<String, String>> filterList = new ArrayList<>();

                for (int i = 0; i < arrayList.size(); i++) {

                    if ((arrayList.get(i).get("$T1_search").toUpperCase()).contains(charSequence.toString().toUpperCase())) {

                        HashMap<String, String> hashMap = new HashMap<>();

                        for (Object obj : arrayList.get(i).entrySet()) {
                            Map.Entry entry = (Map.Entry) obj;

                            String k = (String) entry.getKey();

                            hashMap.put(k, arrayList.get(i).get(k));

                        }

                        filterList.add(hashMap);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = arrayList.size();
                results.values = arrayList;
            }
            return results;
        }

        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mSearchText = constraint.toString();
            list = (ArrayList<HashMap<String, String>>) results.values;
            notifyDataSetChanged();

        }
    }
    
       
     public void setSearch(EditText search) {

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                getFilter().filter(arg0);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });
    }

}