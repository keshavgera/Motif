package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codecube.keshav.motif.R;

import java.util.ArrayList;

import models.NewsDataList;
import utils.CommonMethod;
import utils.ImageLoader1;

/**
 * Created by sunil on 26/3/16.
 */
public class NewsListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Activity mcontext;
    ArrayList<NewsDataList> newsListDatasArrayList;
    private ImageLoader1 imgload;
    public static ImageView newsImage;
    FbShare fbShare;

    public NewsListAdapter(Activity mcontext, ArrayList<NewsDataList> newsListDatasArrayList, FbShare fbShare) {
        Log.e("aaaaaaaa", "bbbbbbbbbb");

        this.mcontext = mcontext;
        this.newsListDatasArrayList = newsListDatasArrayList;
        mInflater = (LayoutInflater) mcontext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.fbShare = fbShare;
        imgload = new ImageLoader1(mcontext);

    }

    @Override
    public int getCount() {
        return newsListDatasArrayList.size();
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
        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) mcontext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.company_news_list_item, null, true);
        holder = new ViewHolder();

        holder.shareimage = (ImageView) convertView.findViewById(R.id.Imge_share);

        holder.shareimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbShare.postImageonWall();

                String text = "Look at my awesome picture";
                Uri pictureUri = Uri.parse("file://my_picture");
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, text);

                shareIntent.setType("image/jpeg");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                mcontext.startActivity(Intent.createChooser(shareIntent, "Share With..."));


            }
        });

        newsImage = (ImageView) convertView.findViewById(R.id.newsimage);
        imgload.DisplayImage(mcontext, newsListDatasArrayList.get(position).newsimage, newsImage);

        Log.e("imagepathhhhhhhhh", newsListDatasArrayList.get(position).newsimage);
        holder.newshead = (TextView) convertView.findViewById(R.id.txt_launchpro);
        holder.newshead.setText(newsListDatasArrayList.get(position).newshead);
        holder.newsdesc = (TextView) convertView.findViewById(R.id.newsdetail);
        holder.newsdesc.setText(newsListDatasArrayList.get(position).newdesc);
        holder.newsdate = (TextView) convertView.findViewById(R.id.newsday);
        holder.newsdate.setText(CommonMethod.DateFormatApp(newsListDatasArrayList.get(position).newsdate));


        return convertView;
    }

    public class ViewHolder {

        ImageView shareimage;

        TextView newsdesc, newshead;
        TextView newsdate;


    }

    public interface FbShare {
        void postImageonWall();
    }


}
