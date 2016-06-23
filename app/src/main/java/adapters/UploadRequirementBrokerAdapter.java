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

import models.UploadRequirementBrokerPojo;
import utils.CommonMethod;


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

//            holder.name = (TextView) convertView.findViewById(R.id.tv_corporate_name_list);
//            holder.mobileNo = (TextView) convertView.findViewById(R.id.tv_corporate_mobile_list);
//            holder.dateOfShifting = (TextView) convertView.findViewById(R.id.tv_date_of_shifting);
//            holder.typeOfProperty = (TextView) convertView.findViewById(R.id.tv_type_of_property);
            holder.furnishing = (TextView) convertView.findViewById(R.id.tv_furnishing);
            holder.rooms = (TextView) convertView.findViewById(R.id.tv_rooms);
//            holder.budgetFrom = (TextView) convertView.findViewById(R.id.tv_budget_rom);
//            holder.budgetTo = (TextView) convertView.findViewById(R.id.tv_budget_to);
            holder.preferredLocation = (TextView) convertView.findViewById(R.id.tv_preferred_location);
            holder.distanceFromOffice = (TextView) convertView.findViewById(R.id.tv_distance_from_office);
//            holder.specificRequirement = (TextView) convertView.findViewById(R.id.tv_specific_requirement);
//            holder.specification = (TextView) convertView.findViewById(R.id.tv_specification);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        holder.name.setText(uploadRequirementBrokerPojoArrayList.get(position).getName());
//        holder.mobileNo.setText(uploadRequirementBrokerPojoArrayList.get(position).getMobileNo());
//        if(uploadRequirementBrokerPojoArrayList.get(position).getDateOfShifting()!=null)
//        holder.dateOfShifting.setText(CommonMethod.DateFormatApp(uploadRequirementBrokerPojoArrayList.get(position).getDateOfShifting()));
//        holder.typeOfProperty.setText(uploadRequirementBrokerPojoArrayList.get(position).getTypeOfProperty());
        holder.furnishing.setText(uploadRequirementBrokerPojoArrayList.get(position).getFurnishing());
        holder.rooms.setText(uploadRequirementBrokerPojoArrayList.get(position).getRooms());
        holder.preferredLocation.setText(uploadRequirementBrokerPojoArrayList.get(position).getPreferredLocation());
//        holder.budgetFrom.setText(uploadRequirementBrokerPojoArrayList.get(position).getBudgetFrom());
//        holder.budgetTo.setText(uploadRequirementBrokerPojoArrayList.get(position).getBudgetTo());
        holder.distanceFromOffice.setText(uploadRequirementBrokerPojoArrayList.get(position).getDistanceFromOffice());
//        holder.specificRequirement.setText(uploadRequirementBrokerPojoArrayList.get(position).getSpecificRequirement());
//        holder.specification.setText(uploadRequirementBrokerPojoArrayList.get(position).getSpecification());

        return convertView;
    }

    public class ViewHolder {
        TextView name;
        TextView mobileNo;
        TextView dateOfShifting;
        TextView typeOfProperty;
        TextView specification;
        TextView furnishing;
        TextView rooms;
        TextView preferredLocation;
        TextView budgetFrom;
        TextView budgetTo;
        TextView specificRequirement;
        TextView distanceFromOffice;

    }
}