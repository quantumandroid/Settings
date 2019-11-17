
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;

    private int layoutResource;

    private ArrayList<DataModel> dataSet;

    public RecyclerViewAdapter(Context context, int layoutResource, ArrayList<DataModel> dataSet) {

       this.context = context;

       this.layoutResource = layoutResource;

       this.dataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        final View view = LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent,false);
//
        // create ViewHolder
        return new ViewHolder(view);
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    //TODO START CHANGES FROM HERE..................................................................
    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Declare Views of item layout

        public TextView name;
        public TextView version;
        public ImageView menu;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            // Initialize views of item layout
            name = itemLayoutView.findViewById(R.id.name);
            version = itemLayoutView.findViewById(R.id.version);
            menu = itemLayoutView.findViewById(R.id.popup_menu);
        }
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your dataSet at this position
        // - replace the contents of the view with that dataSet

        viewHolder.name.setText(dataSet.get(position).getName());
        viewHolder.version.setText(dataSet.get(position).getVersion());

        //TODO enable item click
        new ViewItemClickListener(viewHolder.itemView, position);

    }

    /**
     * Click listener for item view
     */
    class ViewItemClickListener implements View.OnClickListener {

        private int position;

        private View itemView;

        public ViewItemClickListener(View itemView, final int position) {

            this.position = position;

            this.itemView = itemView;

            //TODO initialize click listener to item sub views

            itemView.findViewById(R.id.name).setOnClickListener(this);

            itemView.findViewById(R.id.popup_menu).setOnClickListener(this);

            itemView.findViewById(R.id.card_view).setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            //TODO switch between item sub views
            switch (view.getId()){
                case R.id.name:
                    String nm = dataSet.get(position).getName();
                    Toast.makeText(context, nm, Toast.LENGTH_SHORT).show();
                    break;
                    case R.id.popup_menu:

                        //TODO enable menu click
                        showPopupMenu(getView(R.id.popup_menu), position, R.menu.menu_main);
                        break;
                case
                R.id.card_view:
                    Toast.makeText(context, "Position:- " + position, Toast.LENGTH_SHORT).show();
                    break;
            }

        }

        private View getView(int id) {
            return itemView.findViewById(id);
        }
    }




    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view, int position, int menuResource) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(menuResource, popup.getMenu());
        popup.setOnMenuItemClickListener(new MenuItemClickListener(position));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int position;

        public MenuItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_name:
                    String name = dataSet.get(position).getName();
                    Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_version:
                    String version = dataSet.get(position).getVersion();
                    Toast.makeText(context, version, Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }


}

