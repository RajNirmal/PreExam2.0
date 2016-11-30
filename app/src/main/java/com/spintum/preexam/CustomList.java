package com.spintum.preexam;
 
import java.util.List;
import android.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class CustomList extends ArrayAdapter<DrawerItem> {
       Context context;
      List<DrawerItem> drawerItemList;
      int layoutResID;
       public CustomList(Context context, int layoutResourceID,
                  List<DrawerItem> listItems) {
            super(context, layoutResourceID, listItems);
            this.context = context;
            this.drawerItemList = listItems;
            this.layoutResID = layoutResourceID;
       }
       @Override
      public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
 
            DrawerItemHolder drawerHolder;
            View view = convertView;
             if (view == null) {
                  LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                  drawerHolder = new DrawerItemHolder();
                  view = inflater.inflate(layoutResID, parent, false);
                  drawerHolder.ItemName = (TextView) view.findViewById(R.id.drawer_item_text);
                  drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_item_icon);
                  view.setTag(drawerHolder);
             } else {
               drawerHolder = (DrawerItemHolder) view.getTag();
           }
             DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);
            drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(dItem.getImgResID()));
            drawerHolder.ItemName.setText(dItem.getItemName());
             return view;
      }
 
      public static class DrawerItemHolder {
            TextView ItemName;
            ImageView icon;
      }
}