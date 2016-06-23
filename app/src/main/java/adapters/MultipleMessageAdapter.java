package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codecube.keshav.motif.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import models.MultipleMessagePojo;

/**
 * Created by Vartulz on 4/15/2016.
 */
public class MultipleMessageAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Activity mcontext;
    ArrayList<MultipleMessagePojo> multipleMessagePojoArrayList;

    public MultipleMessageAdapter(Activity mcontext, ArrayList<MultipleMessagePojo> multipleMessagePojoArrayList) {
        this.mcontext = mcontext;
        this.multipleMessagePojoArrayList = multipleMessagePojoArrayList;
        mInflater = (LayoutInflater) mcontext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return multipleMessagePojoArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.multiple_message_list_item, null);
            holder = new ViewHolder();

            holder.tvName = (TextView) convertView.findViewById(R.id.tv_username);
            holder.tv_message=(TextView) convertView.findViewById(R.id.tv_message);
            holder.userimage=(ImageView)convertView.findViewById(R.id.iv_userimge);
           holder.chek_value = (CheckBox)convertView.findViewById(R.id.chek_value_msg);
            holder.parent = (RelativeLayout)convertView.findViewById(R.id.parent);
            holder.rl_bottom = (RelativeLayout)convertView.findViewById(R.id.rl_bottomlaout);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        float density = mcontext.getResources().getDisplayMetrics().density;
        float px = 50 * density;
        int b = (int) Math.round(px);

        holder.tvName.setText(multipleMessagePojoArrayList.get(position).getUserName());

        holder.tv_message.setText(multipleMessagePojoArrayList.get(position).getDesignation());

        Picasso.with(mcontext).load(multipleMessagePojoArrayList.get(position).getProfileImage())
                .error(R.color.Gray)
                .placeholder(R.color.Gray)
                .resize(b,b)
                .centerInside()
                .into(holder.userimage);



/*
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.chek_value.isChecked()){
                 //  holder.rl_bottom.setVisibility(View.VISIBLE);
                    holder.chek_value.setChecked(false);
                }
                else{
                    holder.chek_value.setChecked(true);
                }

            }
        });*/


        return convertView;
    }

    class ViewHolder {
        TextView tvName, tv_message;
        ImageView userimage;
        RelativeLayout parent;
         CheckBox chek_value;
        RelativeLayout rl_bottom;

    }
}
