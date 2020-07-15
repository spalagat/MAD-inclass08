package com.example.inclass_08;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowExpenseFragment extends Fragment {
    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    Expense expense;

    public ShowExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_show_expense,container,false);
        t1 =convertView.findViewById(R.id.name123);
        t2 =convertView.findViewById(R.id.category123);
        t3 =convertView.findViewById(R.id.amt123);
        t4 = convertView.findViewById(R.id.date123);
        expense = (Expense)getArguments().getSerializable("EXP_KEY");
        t1.setText(expense.expName);
        t2.setText(expense.expCat);
        t3.setText(expense.expAmount);
        t4.setText(expense.expDate);

        return convertView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getActivity().findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddExpFragment a1 = new AddExpFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Edit_details",expense);
                a1.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.main_container,a1).commit();
            }
        });

        getActivity().findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.main_container,new ExpenseFragment()).commit();
            }
        });
        super.onActivityCreated(savedInstanceState);
    }
}
