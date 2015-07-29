package com.mikepenz.materialdrawer.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.materialdrawer.R;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.util.UIUtils;

/**
 * Created by mikepenz on 03.02.15.
 */
public class DividerDrawerItem implements IDrawerItem {

    private int visibility = View.VISIBLE;
    private int dividerHeight = 1;
    private int dividerColor = -1;

    public DividerDrawerItem() {
    }

    @Override
    public int getIdentifier() {
        return -1;
    }

    @Override
    public Object getTag() {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getType() {
        return "DIVIDER_ITEM";
    }

    @Override
    public int getLayoutRes() {
        return R.layout.material_drawer_item_divider;
    }

    @Override
    public int getVisibility() {
        return visibility;
    }

    public DividerDrawerItem withDividerHeight(int dividerHeight) {
        this.dividerHeight = dividerHeight;
        return this;
    }

    public DividerDrawerItem withVisibility(int visibility) {
        this.visibility = visibility;
        return this;
    }

    public DividerDrawerItem withDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        return this;
    }

    @Override
    public View convertView(LayoutInflater inflater, View convertView, ViewGroup parent) {
        if (dividerColor == -1) {
            dividerColor = UIUtils.getThemeColorFromAttrOrRes(parent.getContext(), R.attr.material_drawer_divider, R.color.material_drawer_divider);
        }

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(getLayoutRes(), parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.view.setMinimumHeight(dividerHeight);

        if (this.getVisibility() != View.VISIBLE) {
            ViewGroup.LayoutParams params = convertView.getLayoutParams();
            params.height = 0;
            convertView.setLayoutParams(params);
            convertView.setVisibility(this.getVisibility());
            return convertView;
        }

        viewHolder.view.setClickable(false);
        viewHolder.view.setEnabled(false);

        //set the color for the divider
        viewHolder.divider.setBackgroundColor(dividerColor);

        return convertView;
    }

    private static class ViewHolder {
        private View view;
        private View divider;

        private ViewHolder(View view) {
            this.view = view;
            this.divider = view.findViewById(R.id.divider);
        }
    }
}
