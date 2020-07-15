package com.example.inclass_08;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Exp_adapter extends ArrayAdapter<Expense> {

    public Exp_adapter(@NonNull FragmentActivity context, int resource, @NonNull ArrayList<Expense> objects) {
        super(context,resource,objects);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Expense expense=getItem(position);


        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exp_details,parent,false);
        }
        TextView ename=convertView.findViewById(R.id.exp_name_list);
        TextView eamount=convertView.findViewById(R.id.exp_amount_list);

        ename.setText(expense.expName);
        //Log.d("Inside adapter",expense.expName);
        eamount.setText(expense.expAmount);


        return convertView;
    }

}
