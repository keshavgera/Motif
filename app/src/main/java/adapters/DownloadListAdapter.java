package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codecube.keshav.motif.DownloadFiles;
import com.codecube.keshav.motif.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Set;

import models.DownloadPojo;
import utils.ImageLoader1;

public class DownloadListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Activity mcontext;
    List<DownloadPojo> downloadPojoArrayList;
    private ImageLoader imgLoader;
    private ImageLoader1 imgload;
    public static ImageView newsImage;

    public DownloadListAdapter(Activity mcontext, List<DownloadPojo> downloadPojoArrayList) {
        Log.e("aaaaaaaa", "bbbbbbbbbb");

        this.mcontext = mcontext;
        this.downloadPojoArrayList = downloadPojoArrayList;
        mInflater = (LayoutInflater) mcontext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return downloadPojoArrayList.size();
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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.download_list_item, null, true);

            holder.downloadFilename = (TextView) convertView.findViewById(R.id.downloadFilename);
            holder.img_download = (ImageView) convertView.findViewById(R.id.img_download);
            holder.img_fileType = (ImageView) convertView.findViewById(R.id.img_fileType);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (downloadPojoArrayList.get(position).getFileType().equals("image")) {
            holder.img_fileType.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.image_icon));
        } else if (downloadPojoArrayList.get(position).getFileType().equals("audio")) {
            holder.img_fileType.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.mp3_icon));
        }else if (downloadPojoArrayList.get(position).getFileType().equals("video")) {
            holder.img_fileType.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.mp4_icon));
        }else if (downloadPojoArrayList.get(position).getFileType().equals("pdf")) {
            holder.img_fileType.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.pdf_icon));
        }


        holder.downloadFilename.setText(downloadPojoArrayList.get(position).getFileName());
        holder.img_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //downloadCallback.callDownload(downloadArrayList.get(position).getId());

                Log.e("path is ","" +downloadPojoArrayList.get(position).getDownloadUrl());
                Intent in = new Intent(mcontext, DownloadFiles.class);
                in.putExtra("filename", downloadPojoArrayList.get(position).getFileName());
                in.putExtra("url", downloadPojoArrayList.get(position).getDownloadUrl());
                in.putExtra("id", downloadPojoArrayList.get(position).getId());
                mcontext.startActivity(in);

            }
        });

        SharedPreferences shared;
        shared = mcontext.getSharedPreferences("App_settings", mcontext.MODE_PRIVATE);
        Set<String> downloadList = shared.getStringSet("downloadList", null);

        if(downloadList != null && downloadList.contains(downloadPojoArrayList.get(position).getId())){
            holder.img_download.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.download_icon_green));
        }else{
            holder.img_download.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.download_icon));

        }

        return convertView;
    }

    public class ViewHolder {
        TextView downloadFilename;
        ImageView img_download, img_fileType;
    }
}
