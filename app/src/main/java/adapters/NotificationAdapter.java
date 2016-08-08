package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codecube.keshav.motif.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import models.NotificationData;
import utils.CommonMethod;
import utils.ImageLoader1;


public class NotificationAdapter extends BaseAdapter {
    Context ctxt;
    LayoutInflater inflater;
    private ImageLoader imgLoader;
    private ImageLoader1 imgload;
    ArrayList<NotificationData> notifyDatas;
    private RelativeLayout relNotify;

    public NotificationAdapter(Context context, ArrayList<NotificationData> notifyData) {
        this.notifyDatas = notifyData;
        ctxt = context;
        inflater = (LayoutInflater) ctxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return notifyDatas.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }
//ImageView iv_Image;
//TextView tv_DishName;

    @Override
    public View getView(int arg0, View view, ViewGroup arg2) {
        // view = inflater.inflate(R.layout.notification_list_item, arg2, false);
        ViewHolder holder;
        if(view == null){
            view = inflater.inflate(R.layout.notification_list_item, arg2, false);
            holder = new ViewHolder();

            holder.txtAlert = (TextView) view.findViewById(R.id.listAlert);
            holder.notification_title = (TextView) view.findViewById(R.id.notification_title);
            holder.txtDate = (TextView) view.findViewById(R.id.listDate);
/*            holder.iv_notImage = (ImageView) view.findViewById(R.id.iv_notImage);*/

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        imgLoader = ImageLoader.getInstance();
        imgLoader.init(ImageLoaderConfiguration.createDefault(ctxt));
        imgload = new ImageLoader1(ctxt);


        String creationdate = notifyDatas.get(arg0).getNotDate();
        long datePrev = 0;
        long now = 0;


        holder.txtAlert.setText(notifyDatas.get(arg0).getNotAlert());
        holder.notification_title.setText(notifyDatas.get(arg0).getNotification_title());
        holder.txtDate.setText(CommonMethod.DateFormatApp(notifyDatas.get(arg0).getNotDate()));
//        imgload.DisplayImage(ctxt, notifyDatas.get(arg0).getImage(), holder.iv_notImage);

        return view;
    }

    public class ViewHolder {
        TextView txtAlert, notification_title, txtDate;
        ImageView iv_notImage;
    }

}