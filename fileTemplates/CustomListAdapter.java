import android.content.Context;
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
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class $Adapter_Class_Name extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;

    private List<$ListModelClass> list = null;
    //this is used for filter
    private ArrayList<$ListModelClass> arrayList;
    private String mSearchText;
    private ValueFilter valueFilter;

    // create constructor
    public $Adapter_Class_Name(Context context, List<$ListModelClass> list) {

        mContext = context;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<$ListModelClass>();
        this.arrayList .addAll(list);

    }

    // declare list item view's
    public class ViewHolder {
        TextView textView1;
        TextView textView2;
        // todo add here
    }

    @Override
    public int getCount() {
        return list .size();
    }

    @Override
    public $ListModelClass getItem(int position) {
        return list .get(position);
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
            view = inflater.inflate(R.layout.$List_Item_Layout , null);
            // Locate the TextViews in listview_item.xml
            holder.textView1 = (TextView) view.findViewById(R.id.textView1_id);
            holder.textView2 = (TextView) view.findViewById(R.id.textView2_id);
            // todo add here

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Set the results into TextViews
        $ListModelClass item = list .get(position);
        holder.textView1.setText(item.get$Model_String1_First_Caps ());
        holder.textView2.setText(item.get$Model_String2_First_Caps());
        // todo add here

        //  Highlight searched text
        String fullText = item.get$Model_String1_First_Caps();
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

        if(valueFilter==null) {

            valueFilter=new ValueFilter();
        }

        return valueFilter;
    }

    private class ValueFilter extends Filter {

        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            FilterResults results=new FilterResults();

            if(charSequence!=null && charSequence.length()>0){

                ArrayList<$ListModelClass> filterList=new ArrayList<$ListModelClass>();

                for(int i=0;i<arrayList .size();i++){

                    if((arrayList .get(i).get$Model_Search_String_First_Caps().toUpperCase())
                            .contains(charSequence.toString().toUpperCase())) {
                        $ListModelClass listModel = new $ListModelClass();
                        listModel.set$Model_String1_First_Caps(arrayList .get(i).get$Model_String1_First_Caps());
                        listModel.set$Model_String2_First_Caps(arrayList .get(i).get$Model_String2_First_Caps());
                        filterList.add(listModel);
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            }else{
                results.count=arrayList .size();
                results.values=arrayList;
            }
            return results;
        }


        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mSearchText = constraint.toString();
            list=(ArrayList<$ListModelClass>) results.values;
            notifyDataSetChanged();

        }
    }

}