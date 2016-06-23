package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codecube.keshav.motif.MultiTouchActivity;
import com.codecube.keshav.motif.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import models.MessagePojo;

/**
 * Created by bali on 13/4/16.
 */
public class MessageAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    // private ArrayList<TagFriends> mArrListTagFrinds;
    private Activity mcontext;
    ArrayList<MessagePojo> messagePojoArrayList;

    public MessageAdapter(Activity mcontext, ArrayList<MessagePojo> messagePojoArrayList) {
        this.mcontext = mcontext;
        this.messagePojoArrayList = messagePojoArrayList;
        mInflater = (LayoutInflater) mcontext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return messagePojoArrayList.size();
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
            convertView = mInflater.inflate(R.layout.message_list_item, null);
            holder = new ViewHolder();

            holder.tvName = (TextView) convertView.findViewById(R.id.tv_username);
            holder.tv_message=(TextView) convertView.findViewById(R.id.tv_message);
            holder.userimage=(ImageView)convertView.findViewById(R.id.iv_userimge);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        float density = mcontext.getResources().getDisplayMetrics().density;
        float px = 50 * density;
        int b = (int) Math.round(px);

        holder.tvName.setText(messagePojoArrayList.get(position).getUserName());
        holder.tv_message.setText("Last Message:"+messagePojoArrayList.get(position).getMessage());

        Picasso.with(mcontext).load(messagePojoArrayList.get(position).getProfileImage())
                .error(R.color.Gray)
                .placeholder(R.color.Gray)
                .resize(b,b)
                .centerInside()
                .into( holder.userimage);

        holder.userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chatHistoryImage = messagePojoArrayList.get(position).getProfileImage();

                Log.e("chatHistory listview ","clicked");


                if (chatHistoryImage != null) {
                    Log.e("chatHistoryImage","clicked");
                    Intent intent = new Intent(mcontext, MultiTouchActivity.class);
                    intent.putExtra("chatHistoryImage", messagePojoArrayList.get(position).getImage());
                    mcontext.startActivity(intent);
                }
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView tvName, tv_message;
        ImageView userimage;
    }
}
