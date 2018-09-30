package ie.app.heartwise.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ie.app.heartwise.R;
import ie.app.heartwise.activities.DetailedList;
import ie.app.heartwise.database.DatabaseHelper;
import ie.app.heartwise.model.Service;

/**
 * Created by Ian on 17/04/2017.
 */                              //Adapter class created to bind the data from SQL to the RecyclerList

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {  //base class for RecyclerView

    EditText editTextID, editTextLocation, editTextDate, editTextTime, editTextLatitude, editTextLongitude;
    RadioGroup radioGroup;

    public static Context context;
    List<Service> serviceList;
    DatabaseHelper helper = new DatabaseHelper(context);
    RecyclerAdapter adapter;    //adapter provides the binding from the data to the views


    public RecyclerAdapter(Context context, List<Service> ServiceList)    //constructor
    {
        this.serviceList = new ArrayList<Service>();
        this.context = context;
        this.serviceList = ServiceList;
        this.adapter = this;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {  //viewHolder describes the item view

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {  //updates the RecyclerView.ViewHolder contents with the item at the given position

        final Service service = serviceList.get(position);
        holder.placeholderID.setText(service.getID());

        holder.serviceDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){

                deleteAlert(service);
            }
        });

        holder.serviceEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editAlert(service);
            }
        });
    }

    public void deleteAlert(final Service service)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("Delete Warning!");
        builder.setMessage("Are you sure you want to delete?");

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                helper = new DatabaseHelper(context);
                helper.deleteItem(service.getID());

                serviceList.remove(service);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void editAlert(final Service service){

        LayoutInflater inflater = LayoutInflater.from(context);
        final View subView = inflater.inflate(R.layout.add_screen, null);

        editTextID = (EditText) subView.findViewById(R.id.idET);
        editTextLocation = (EditText) subView.findViewById(R.id.locationET);
        radioGroup =(RadioGroup)subView.findViewById(R.id.radio_group);
        editTextDate = (EditText) subView.findViewById(R.id.dateET);
        editTextTime = (EditText) subView.findViewById(R.id.timeET);
        editTextLatitude = (EditText) subView.findViewById(R.id.latitudeET);
        editTextLongitude = (EditText) subView.findViewById(R.id.longitudeET);

        if(service != null)
        {
            editTextID.setText(service.getID());
            editTextLocation.setText(service.getLocation());
            editTextDate.setText(service.getDate());
            editTextTime.setText(service.getTime());
            editTextLatitude.setText(service.getLatitude());
            editTextLongitude.setText(service.getLongitude());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit service details");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String id = editTextID.getText().toString();
                final String location = editTextLocation.getText().toString();
                final String radioValue= ((RadioButton)subView.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                final String date = editTextDate.getText().toString();
                final String time = editTextTime.getText().toString();
                final String latitude = editTextLatitude.getText().toString();
                final String longitude = editTextLongitude.getText().toString();

                if (id.length() == 0) {
                    editTextID.requestFocus();
                    editTextID.setError("Please enter the AED ID...");
                } else if (location.length() == 0) {
                    editTextLocation.requestFocus();
                    editTextLocation.setError("Please enter the AED location...");
                } else if (date.length() == 0) {
                    editTextDate.requestFocus();
                    editTextDate.setError("Please enter the date...");
                } else if (time.length() == 0) {
                    editTextTime.requestFocus();
                    editTextTime.setError("Please enter the time...");
                }else if (latitude.length() == 0){
                    editTextLatitude.requestFocus();
                    editTextLatitude.setError("Please enter location latitude...");
                } else if (longitude.length() == 0){
                    editTextLongitude.requestFocus();
                    editTextLongitude.setError("Please enter location longitude...");
                }
                else
                {
                    helper = new DatabaseHelper(context);

                    service.setID(id);
                    service.setLocation(location);
                    service.setCondition(radioValue);
                    service.setDate(date);
                    service.setTime(time);
                    service.setLatitude(latitude);
                    service.setLongitude(longitude);

                    helper.updateServiceData(service);

                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
                editTextID.setText("");
                editTextLocation.setText("");
                editTextDate.setText("");
                editTextTime.setText("");
                editTextLatitude.setText("");
                editTextLongitude.setText("");
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Update cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView placeholderID, serviceEdit, serviceDelete;

        public ViewHolder(View itemLayoutView)
        {
            super(itemLayoutView);
            placeholderID = (TextView)itemView.findViewById(R.id.idPlaceholder);
            serviceEdit = (TextView) itemView.findViewById(R.id.editService);
            serviceDelete = (TextView) itemView.findViewById(R.id.deleteService);
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(context,DetailedList.class);
            Bundle extras = new Bundle();     //sending data to detailed list page
            extras.putInt("position",getAdapterPosition());
            intent.putExtras(extras);
            context.startActivity(intent);
        }
    }
}
