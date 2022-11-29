package com.example.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
   //initialize variables

    private List<MainData> dataList;
    private Activity context;
    private  RoomDB database;
    int curr_img;
    int[] imgs = {R.drawable.done,R.drawable.done3};

    AlertDialog.Builder builder;

    //create constructor


    public MainAdapter(List<MainData> dataList, Activity context) {
        this.dataList = dataList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //intilaize view
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //int main data
        MainData data=dataList.get(position);
        //init db
        database=RoomDB.getInstance(context);
        //set text in textview
        holder.textView.setText(data.getText());

        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //init main data
                MainData d=dataList.get(holder.getAdapterPosition());
                //get id
                int sID = d.getID();
                //get text
                String sText = d.getText();

                //create dialog
                Dialog dialog = new Dialog(context);
                // set content view

                dialog.setContentView(R.layout.dialog_update);

                //init width

                int width= WindowManager.LayoutParams.MATCH_PARENT;
                //int height

                int height=WindowManager.LayoutParams.WRAP_CONTENT;
                //set layout

                dialog.getWindow().setLayout(width,height);

                //show dialog

                dialog.show();

                //init and assign varable

                EditText editText=dialog.findViewById(R.id.edit_text);
                Button btUpdate=dialog.findViewById(R.id.bt_update);

                //set text on edit text

                editText.setText(sText);

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // dismiss dialog

                        dialog.dismiss();
                        //get upate text from edit text

                        String uText=editText.getText().toString().trim();
                        //update text in db

                        database.mainDao().upate(sID,uText);
                        //notify when data is updated

                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();


                    }
                });




            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder= new AlertDialog.Builder(v.getContext());
                //Setting message manually and performing action on button click
                builder.setMessage("Are you sure you want to delete this task?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                MainData d=dataList.get(holder.getAdapterPosition());
                                //delete text from database

                                database.mainDao().delete(d);
                                //notify when data is eleted
                                int position = holder.getAdapterPosition();
                                dataList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,dataList.size());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Delete Confirmation");
                alert.show();

            }
        });

        holder.btCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                curr_img++;
                curr_img = curr_img % imgs.length;
                holder.btCom.setImageResource(imgs[curr_img]);


            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //init variables

        TextView textView;
        ImageView btEdit,btDelete,btCom;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            //Assign variable
            textView=itemView.findViewById(R.id.text_view);
            btEdit=itemView.findViewById(R.id.bt_edit);
            btDelete=itemView.findViewById(R.id.bt_delete);
            btCom=itemView.findViewById(R.id.bt_com);


        }
    }
}
