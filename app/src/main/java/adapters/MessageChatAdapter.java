package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codecube.keshav.motif.R;

import java.util.List;

import models.MessageChatPojo;

public class MessageChatAdapter extends BaseAdapter {

    private final List<MessageChatPojo> messageChatPojoList;

    private Activity mcontext;

    LayoutInflater inflater;

    public MessageChatAdapter(List<MessageChatPojo> messageChatPojoList, Activity context) {
        this.messageChatPojoList = messageChatPojoList;
        this.mcontext = context;

        inflater = (LayoutInflater) mcontext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return messageChatPojoList.size();
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
            convertView = inflater.inflate(R.layout.message_chat_list_item, null, true);
            holder = new ViewHolder();

            holder.messageText = (TextView) convertView.findViewById(R.id.message_text);
            holder.messageTime = (TextView) convertView.findViewById(R.id.message_time);
            holder.content = (LinearLayout) convertView.findViewById(R.id.content);
            holder.contentWithBG = (LinearLayout) convertView.findViewById(R.id.contentWithBackground);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(messageChatPojoList.get(position).getType().equals("adminTocorporate"))
        {
            holder.contentWithBG.setBackgroundResource(R.drawable.msg_in);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.contentWithBG.setLayoutParams(layoutParams);

//            holder.iv_message_chat.setImageResource(R.drawable.frow_logo_nav);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            lp.leftMargin=10;
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.messageText.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.messageText.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.messageTime.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.messageTime.setLayoutParams(layoutParams);
        } else if(messageChatPojoList.get(position).getType().equals("corporateToadmin")) {
            holder.contentWithBG.setBackgroundResource(R.drawable.msg_out);
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.contentWithBG.setLayoutParams(layoutParams);
//            holder.iv_message_chat.setImageResource(R.drawable.avatar_male);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            lp.leftMargin=150;
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.messageText.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.messageText.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.messageTime.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.messageTime.setLayoutParams(layoutParams);
        }



        /*holder.messageId.setText(messageChatPojoList.get(position).getMessageId());
        holder.type.setText(messageChatPojoList.get(position).getType());
        holder.name.setText(messageChatPojoList.get(position).getName());*/

        holder.messageText.setText(messageChatPojoList.get(position).getMessageText());
        holder.messageTime.setText(messageChatPojoList.get(position).getMessageTime());

        return convertView;
    }

    public class ViewHolder {
        public TextView messageId;
        public TextView type;
        public TextView messageText;
        public TextView messageTime;
        public TextView name;
        public ImageView iv_message_chat;

        public LinearLayout content;
        public LinearLayout contentWithBG;

    }
}
