package adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codecube.keshav.motif.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import models.NewsPojo;
import utils.CommonMethod;
import utils.ImageLoader1;

public class NewsAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private Activity mcontext;
    ArrayList<NewsPojo> newsPojoArrayList;

    LayoutInflater inflater;

    private ImageLoader imgLoader;

    private ImageLoader1 imgload;
    public static ImageView newsImage;



    public NewsAdapter(Activity mcontext, ArrayList<NewsPojo> newsPojoArrayList) {
        Log.e("aaaaaaaa", "bbbbbbbbbb");

        this.mcontext = mcontext;
        this.newsPojoArrayList = newsPojoArrayList;
        inflater = (LayoutInflater) mcontext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imgLoader = ImageLoader.getInstance();
        imgLoader.destroy();
        imgLoader.init(ImageLoaderConfiguration.createDefault(mcontext));
        imgload = new ImageLoader1(mcontext);
    }

    @Override
    public int getCount() {
        return newsPojoArrayList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.company_news_list_item, null, true);

            holder = new ViewHolder();

            holder.newsId = (TextView) convertView.findViewById(R.id.news_id);
            holder.newsHead = (TextView) convertView.findViewById(R.id.txt_launchpro);
            holder.newsDesc = (TextView) convertView.findViewById(R.id.newsdetail);
            holder.newsTo = (TextView) convertView.findViewById(R.id.news_to);
            holder.newsDate = (TextView) convertView.findViewById(R.id.newsday);
            holder.newsFrom = (TextView) convertView.findViewById(R.id.news_from);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        newsImage = (ImageView) convertView.findViewById(R.id.newsimage);


        Log.e("Image ","is " +newsPojoArrayList.get(position).newsImgage);

        Picasso.with(mcontext).load(newsPojoArrayList.get(position).newsImgage)
                .error(R.color.light_grey)
                .placeholder(R.color.light_grey)
                .into(newsImage);

        imgload.DisplayImage(mcontext, newsPojoArrayList.get(position).newsImgage, newsImage);


        holder.newsId.setText(newsPojoArrayList.get(position).getNewsId());
        holder.newsHead.setText(newsPojoArrayList.get(position).getNewsHead());
        holder.newsDesc.setText(newsPojoArrayList.get(position).getNewsDesc());
        holder.newsTo.setText(newsPojoArrayList.get(position).getNewsTo());
        holder.newsDate.setText(CommonMethod.DateFormatApp(newsPojoArrayList.get(position).getNewsDate()));
        holder.newsFrom.setText(newsPojoArrayList.get(position).getNewsFrom());

        return convertView;
    }

    public class ViewHolder {

        TextView newsId;
        TextView newsHead;
        TextView newsDesc;
        TextView newsTo;
        TextView newsDate;
        TextView newsFrom;

    }

}
