package com.example.inclass_08;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddExpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AddExpFragment extends Fragment {
    String[] categories_selection={"Groceries","Invoice","Transportation","Shopping","Rent","Trips","Utilities","Other"};
    String ExpenseAmount,ExpenseName,ExpenseCategory;
    EditText category;
    EditText expenseName,expenseAmount;
    Button addExpenseButton,cancelExpButton;
    FirebaseDatabase mDatabase1 =  FirebaseDatabase.getInstance();
    DatabaseReference mDatabase=mDatabase1.getReference("expenses");
    String keyofobj="new";
    String text;





    private OnFragmentInteractionListener mListener;

    public AddExpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(R.string.show_exp_title);
        Log.d("status","entered AddFragment");

        View view = inflater.inflate(R.layout.fragment_add_exp, container, false);

        category=view.findViewById(R.id.catSel_etext);
        expenseName=view.findViewById(R.id.expName_etext);
        expenseAmount=view.findViewById(R.id.amount_etext);
        addExpenseButton=view.findViewById(R.id.add_exp_button);
        cancelExpButton=view.findViewById(R.id.cancel_exp_button);
        if(getArguments()!=null){

            Log.d("status","Arguemts are null");
            Expense expense2 = (Expense)getArguments().getSerializable("Edit_details");
            expenseName.setText(expense2.expName);
            category.setText(expense2.expCat);
            expenseAmount.setText(expense2.expAmount);
            keyofobj = expense2.obj_key;


        }

        view.findViewById(R.id.catSel_etext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Choose Category")
                        .setSingleChoiceItems(categories_selection, categories_selection.length, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ListView lw = ((AlertDialog) dialogInterface).getListView();
                                Object checkedItem = lw.getAdapter().getItem(i);
                                category.setText(checkedItem.toString());

                            }
                        });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("status","adding the expenses");
                ExpenseFragment expenseFragment = new ExpenseFragment();
                Expense expense = new Expense();
                if ((expenseName.getText().toString()).equals("")) {
                    expenseName.setError("name");}
                else if ((category.getText().toString()).equals("")) {
                    category.setError("choose a category");
                }

             else if ((expenseAmount.getText().toString()).equals("")) {
                    expenseAmount.setError("amount");
                } else {
                    ExpenseName = expenseName.getText().toString();
                    ExpenseCategory = category.getText().toString();
                    ExpenseAmount = expenseAmount.getText().toString();
                    Date exp_date = new Date();
                    String strDateFormat = "MM/dd/YYYY";
                    DateFormat d1 = new SimpleDateFormat(strDateFormat);
                    String Expense_Date = d1.format(exp_date);

                    expense.expName = ExpenseName;
                    expense.expAmount = ExpenseAmount;

                    /*Bundle bundle = new Bundle();
                    bundle.putString("EXP_NAME_KEY", ExpenseName);
                    bundle.putString("EXP_CAT_KEY", ExpenseCategory);
                    bundle.putString("EXP_AMT_KEY", ExpenseAmount);
                    bundle.putString("EXP_DATE_KEY", formattedDate);
                    bundle.putSerializable("EXP_TOTAL", expense);
                    //expenseFragment.getDataFromOther(bundle);
                    expenseFragment.setArguments(bundle);*/
                    Log.d("Status","Expense objected created");
                    Expense expense1 = new Expense(ExpenseName,ExpenseCategory,ExpenseAmount,Expense_Date);
                    /*if(keyofobj!=null){
                        Log.d("key value is ",keyofobj.toString());
                        mDatabase.child(keyofobj).setValue(expense1);
                        getFragmentManager().beginTransaction().replace(R.id.main_container, new ExpenseFragment()).commit();

                    }*/

                        //Log.d("key value is ",keyofobj);
                    if(keyofobj.equals("new")){

                      mListener.onFragmentInteraction(expense1);
                    }
                    else {
                        expense1.obj_key=keyofobj;
                        mListener.onFragmentInteraction(expense1);
                    }}


            }
        });



        cancelExpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("status","cancel clicked");
                getFragmentManager().beginTransaction().replace(R.id.main_container,new ExpenseFragment()).commit();
                /*FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    Log.i("MainActivity", "popping backstack"+fm.popBackStackImmediate());
                    fm.popBackStack();
                } else {
                    Log.i("MainActivity", "nothing on backstack, calling super");
                    //super.onBackPressed();
                }*/
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Expense expense) {
        if (mListener != null) {
            mListener.onFragmentInteraction(expense);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {



        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Expense expense);
    }
}
