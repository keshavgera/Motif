package adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codecube.keshav.motif.R;

import java.util.ArrayList;

import MyPreference.LoginPreferences;
import models.UploadRequirementBrokerPojo;


public class UploadRequirementBrokerAdapter extends BaseAdapter {
    private Activity mcontext;
    ArrayList<UploadRequirementBrokerPojo> uploadRequirementBrokerPojoArrayList;

    LayoutInflater inflater;

    public UploadRequirementBrokerAdapter(Activity mcontext, ArrayList<UploadRequirementBrokerPojo> uploadRequirementBrokerPojoArrayList) {
        this.mcontext = mcontext;
        this.uploadRequirementBrokerPojoArrayList = uploadRequirementBrokerPojoArrayList;

        inflater = (LayoutInflater) mcontext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return uploadRequirementBrokerPojoArrayList.size();
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
            convertView = inflater.inflate(R.layout.broker_list_item, null, true);
            holder = new ViewHolder();

            holder.typeOfProperty = (TextView) convertView.findViewById(R.id.tv_type_of_property);
            holder.furnishing = (TextView) convertView.findViewById(R.id.tv_furnishing);
            holder.rooms = (TextView) convertView.findViewById(R.id.tv_rooms);
            holder.preferredLocation = (TextView) convertView.findViewById(R.id.tv_preferred_location);
            holder.responseStatus = (TextView) convertView.findViewById(R.id.tv_response_status);
            holder.lly_response = (LinearLayout) convertView.findViewById(R.id.lly_response);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.typeOfProperty.setText(uploadRequirementBrokerPojoArrayList.get(position).getTypeOfProperty() + " Available For Rent");
        holder.furnishing.setText(uploadRequirementBrokerPojoArrayList.get(position).getFurnishing()+" Furnish");

        Log.d("typeOfProperty","-> " + uploadRequirementBrokerPojoArrayList.get(position).getTypeOfProperty());

        if(uploadRequirementBrokerPojoArrayList.get(position).getRooms().equals("1")) {
            if(uploadRequirementBrokerPojoArrayList.get(position).getTypeOfProperty().equals("PG")
                    ||uploadRequirementBrokerPojoArrayList.get(position).getTypeOfProperty().equals("Guest House")) {

                Log.e("kkk","Bed");
                holder.rooms.setText(uploadRequirementBrokerPojoArrayList.get(position).getRooms() + " Bed");
            }
            if(uploadRequirementBrokerPojoArrayList.get(position).getTypeOfProperty().equals("Builder Floor")
                    ||uploadRequirementBrokerPojoArrayList.get(position).getTypeOfProperty().equals("Apartments")
                ||uploadRequirementBrokerPojoArrayList.get(position).getTypeOfProperty().equals("Villa")){


                Log.e("kkk","Room");
                holder.rooms.setText(uploadRequirementBrokerPojoArrayList.get(position).getRooms() + " Room");
            }
        }
        else {
            if(uploadRequirementBrokerPojoArrayList.get(position).getTypeOfProperty().equals("PG")
                    ||uploadRequirementBrokerPojoArrayList.get(position).getTypeOfProperty().equals("Guest House")) {

                Log.e("kkk","Beds");
                holder.rooms.setText(uploadRequirementBrokerPojoArrayList.get(position).getRooms() + " Beds");
            }
            if(uploadRequirementBrokerPojoArrayList.get(position).getTypeOfProperty().equals("Builder Floor")
                    ||uploadRequirementBrokerPojoArrayList.get(position).getTypeOfProperty().equals("Apartments")
                    ||uploadRequirementBrokerPojoArrayList.get(position).getTypeOfProperty().equals("Villa")){

                Log.e("kkk","Rooms");
                holder.rooms.setText(uploadRequirementBrokerPojoArrayList.get(position).getRooms() + " Rooms");
            }
        }

        holder.preferredLocation.setText(uploadRequirementBrokerPojoArrayList.get(position).getPreferredLocation());
        holder.responseStatus.setText(uploadRequirementBrokerPojoArrayList.get(position).getResponseStatus());

        if(LoginPreferences.getActiveInstance(mcontext).getUserType().equals("Employee")){

            Log.e("keshav","hide responded in Employee");
            holder.lly_response.setVisibility(View.GONE);
        }

        return convertView;
    }

    public class ViewHolder {
        TextView typeOfProperty;
        TextView furnishing;
        TextView rooms;
        TextView preferredLocation;
        TextView responseStatus;
        LinearLayout lly_response;
    }
}