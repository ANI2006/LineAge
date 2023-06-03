package com.app.lineage.ui.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.app.lineage6.R;

import java.util.ArrayList;


public class ContactsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ContactModel> contactModelArrayList;

    public ContactsAdapter(Context context, ArrayList<ContactModel> contactModelArrayList) {

        this.context = context;
        this.contactModelArrayList = contactModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return contactModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item, null, true);

            holder.tvname = (TextView) convertView.findViewById(R.id.name);
            holder.tvnumber = (TextView) convertView.findViewById(R.id.number);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvname.setText(contactModelArrayList.get(position).getName());
        holder.tvnumber.setText(contactModelArrayList.get(position).getNumber());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname, tvnumber;

    }

//    private List<String> values;
//
//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
//        public TextView txtHeader;
//        public TextView txtFooter;
//        public View layout;
//
//        public ViewHolder(View v) {
//            super(v);
//            layout = v;
//            txtHeader = (TextView) v.findViewById(R.id.firstLine);
//            txtFooter = (TextView) v.findViewById(R.id.secondLine);
//        }
//    }
//
//    public void add(int position, String item) {
//        values.add(position, item);
//        notifyItemInserted(position);
//    }
//
//    public void remove(int position) {
//        values.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    // Provide a suitable constructor (depends on the kind of dataset)
//    public ContactsAdapter(List<String> myDataset) {
//        values = myDataset;
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
//                                                   int viewType) {
//        // create a new view
//        LayoutInflater inflater = LayoutInflater.from(
//                parent.getContext());
//        View v =
//                inflater.inflate(R.layout.row_layout, parent, false);
//        // set the view's size, margins, paddings and layout parameters
//        ViewHolder vh = new ViewHolder(v);
//        return vh;
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(ViewHolder holder, final int position) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        final String name = values.get(position);
//        holder.txtHeader.setText(name);
//        holder.txtHeader.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                remove(position);
//            }
//        });
//
//        holder.txtFooter.setText("Footer: " + name);
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        return values.size();
//    }

}