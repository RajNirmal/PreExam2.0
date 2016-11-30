package com.spintum.preexam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.HashSet;
import java.util.List;

import foldingcell.FoldingCell;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
public class FoldingCellListAdapter extends ArrayAdapter<Item> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;


    public FoldingCellListAdapter(Context context, List<Item> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get item for selected view
        Item item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view holder
            viewHolder.price = (TextView) cell.findViewById(R.id.title_price);
            viewHolder.sub= (TextView) cell.findViewById(R.id.sb);
           viewHolder.u1 = (TextView) cell.findViewById(R.id.u1);
            viewHolder.u2 = (TextView) cell.findViewById(R.id.u2);
            viewHolder.u3 = (TextView) cell.findViewById(R.id.u3);
            viewHolder.u4 = (TextView) cell.findViewById(R.id.u4);
            viewHolder.u5 = (TextView) cell.findViewById(R.id.u5);
            viewHolder.c1 = (TextView) cell.findViewById(R.id.c1);
            viewHolder.c2 = (TextView) cell.findViewById(R.id.c2);
            viewHolder.c3 = (TextView) cell.findViewById(R.id.c3);
            viewHolder.c4 = (TextView) cell.findViewById(R.id.c4);
            viewHolder.c5 = (TextView) cell.findViewById(R.id.c5);
            viewHolder.pt = (ImageView) cell.findViewById(R.id.pt);
           // viewHolder.date = (TextView) cell.findViewById(R.id.title_date_label);
            viewHolder.fromAddress = (TextView) cell.findViewById(R.id.title_from_address);
           // viewHolder.toAddress = (TextView) cell.findViewById(R.id.title_to_address);
           //viewHolder.requestsCount = (TextView) cell.findViewById(R.id.title_requests_count);
           // viewHolder.pledgePrice = (TextView) cell.findViewById(R.id.title_pledge);
            //viewHolder.contentRequestBtn = (TextView) cell.findViewById(R.id.content_request_btn);
            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        // bind data from selected element to view through view holder
        viewHolder.price.setText(item.getPrice());
        viewHolder.sub.setText(item.getSub());
       viewHolder.u1.setText(item.getU1());
        viewHolder.u2.setText(item.getU2());
        viewHolder.u3.setText(item.getU3());
        viewHolder.u4.setText(item.getU4());
        viewHolder.u5.setText(item.getU5());
        viewHolder.c1.setText(item.getC1());
        viewHolder.c2.setText(item.getC2());
        viewHolder.c3.setText(item.getC3());
        viewHolder.c4.setText(item.getC4());
        viewHolder.c5.setText(item.getC5());
        viewHolder.pt.setImageResource(item.getPt());
        //viewHolder.date.setText(item.getDate());
        viewHolder.fromAddress.setText(item.getFromAddress());
       // viewHolder.toAddress.setText(item.getToAddress());
       // viewHolder.requestsCount.setText(String.valueOf(item.getRequestsCount()));
       // viewHolder.pledgePrice.setText(item.getPledgePrice());

      /*  // set custom btn handler for list item from that item
        if (item.getRequestBtnClickListener() != null) {
            viewHolder.contentRequestBtn.setOnClickListener(item.getRequestBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in item
            viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
        }
*/

        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);

        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView price;
        TextView fromAddress;
        TextView sub;
        TextView u1;
        TextView u2;
        TextView u3;
        TextView u4;
        TextView u5;
        TextView c1;
        TextView c2;
        TextView c3;
        TextView c4;
        TextView c5;
        ImageView pt;
        //TextView time;
    }
}
