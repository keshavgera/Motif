package com.codecube.keshav.motif;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DownloadFiles extends Activity {

    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private Button startBtn;
    private ProgressDialog mProgressDialog;
    String filename;
    String downloadUrl;
    String fileId;
    SharedPreferences shared;
    ArrayList<String> downloadList = new ArrayList<String>();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_files);

        shared = getSharedPreferences("App_settings", MODE_PRIVATE);

        if (getIntent().getStringExtra("filename") != null && getIntent().getStringExtra("url") != null) {
            filename = getIntent().getStringExtra("filename");
            downloadUrl = getIntent().getStringExtra("url");
            fileId = getIntent().getStringExtra("id");

            Log.d("filename", "1 " + filename);
            Log.d("downloadUrl", "1 " + downloadUrl);
            Log.d("fileId", "1 " + fileId);

            startDownload();
        }

    }

    private void startDownload() {
        String url = downloadUrl;
        new DownloadFileAsync().execute(url);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Downloading file..");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;

            try {

                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Length of file: " + lenghtOfFile);

                File SDCardRoot = Environment.getExternalStorageDirectory();
                // create a new file, to save the <span id="IL_AD2"
                // class="IL_AD">downloaded</span> file
                File folder = new File(SDCardRoot.getAbsoluteFile() + "/Motif");
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                Log.d("filename", filename);
                File file = new File(folder.getAbsolutePath() + "/" + filename
                );
                Log.e("Path of Writing", "" + file);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fileOutput = new FileOutputStream(file);

                InputStream input = conexion.getInputStream();
                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    fileOutput.write(data, 0, count);
                }

                fileOutput.flush();
                fileOutput.close();
                input.close();
            } catch (Exception e) {
            }
            return null;

        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC", progress[0]);
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
            SharedPreferences.Editor editor = shared.edit();
            Set<String> oldDownloadList;
            oldDownloadList = shared.getStringSet("downloadList", null);
            if (oldDownloadList == null) {
                oldDownloadList = new HashSet<String>();

            }
            oldDownloadList.add(fileId);
            //setDownloadId.addAll(downloadList);
            editor.putStringSet("downloadList", oldDownloadList);
            editor.apply();
            Log.d("downlist  Preferences", "1: " + downloadList);

            finish();
        }
    }
}