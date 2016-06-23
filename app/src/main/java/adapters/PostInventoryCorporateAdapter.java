package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.codecube.keshav.motif.R;

import java.util.ArrayList;

import models.PostInventoryCorporatePojo;
import models.UploadRequirementBrokerPojo;
import utils.CommonMethod;

public class PostInventoryCorporateAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Activity mcontext;
    ArrayList<PostInventoryCorporatePojo> postInventoryCorporatePojoArrayList;

    LayoutInflater inflater;

    public PostInventoryCorporateAdapter(Activity mcontext, ArrayList<PostInventoryCorporatePojo> postInventoryCorporatePojoArrayList) {
        this.mcontext = mcontext;
        this.postInventoryCorporatePojoArrayList = postInventoryCorporatePojoArrayList;

        inflater = (LayoutInflater) mcontext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return postInventoryCorporatePojoArrayList.size();
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
            convertView = inflater.inflate(R.layout.corporate_list_item, null, true);
            holder = new ViewHolder();

//            holder.name = (TextView) convertView.findViewById(R.id.tv_corporate_name_broker);
//            holder.mobileNo = (TextView) convertView.findViewById(R.id.tv_corporate_mobile_broker);
            holder.typeOfProperty = (TextView) convertView.findViewById(R.id.tv_type_of_property_broker);
            holder.furnishing = (TextView) convertView.findViewById(R.id.tv_furnishing_broker);
//            holder.floor = (TextView) convertView.findViewById(R.id.floor_broker);
//            holder.rooms = (TextView) convertView.findViewById(R.id.tv_rooms_broker);
//            holder.location = (TextView) convertView.findViewById(R.id.tv_location_broker);
            holder.expRent = (TextView) convertView.findViewById(R.id.tv_exp_rent_broker);
//            holder.specificRequirement = (TextView) convertView.findViewById(R.id.tv_specific_requirement_broker);
            holder.specification = (TextView) convertView.findViewById(R.id.tv_specification_broker);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        holder.name.setText(postInventoryCorporatePojoArrayList.get(position).getName());
//        holder.mobileNo.setText(postInventoryCorporatePojoArrayList.get(position).getMobileNo());

        holder.typeOfProperty.setText(postInventoryCorporatePojoArrayList.get(position).getTypeOfProperty());
        holder.furnishing.setText(postInventoryCorporatePojoArrayList.get(position).getFurnishing());
//        holder.floor.setText(postInventoryCorporatePojoArrayList.get(position).getFloor());
//        holder.rooms.setText(postInventoryCorporatePojoArrayList.get(position).getRooms());
//        holder.location.setText(postInventoryCorporatePojoArrayList.get(position).getLocation());
        holder.expRent.setText(postInventoryCorporatePojoArrayList.get(position).getExpRent());
        holder.specification.setText(postInventoryCorporatePojoArrayList.get(position).getSpecification());
//        holder.specificRequirement.setText(postInventoryCorporatePojoArrayList.get(position).getSpecificRequirement());

        return convertView;
    }

    public class ViewHolder {
        TextView name;
        TextView mobileNo;
        TextView typeOfProperty;
        TextView specification;
        TextView furnishing;
        TextView rooms;
        TextView location;
        TextView specificRequirement;
        TextView expRent;
        TextView floor;

    }
}