package com.example.inclass_08;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpenseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ExpenseFragment extends Fragment {
public static Expense e=null;
     ArrayList<Expense> displayList=new ArrayList<>();
     Exp_adapter t1;
     ListView expdetailsList;
     TextView nodata;
    FirebaseDatabase mDatabase1 =  FirebaseDatabase.getInstance();
    DatabaseReference mDatabase=mDatabase1.getReference("expenses");


    private OnFragmentInteractionListener mListener;

    public ExpenseFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.d("Status","Entered Expense Fragment");
        System.out.println("sandeep"+getActivity());
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        Button addButton = view.findViewById(R.id.add_button);
        nodata = view.findViewById(R.id.expense_text);
        nodata.setVisibility(View.INVISIBLE);
        expdetailsList = view.findViewById(R.id.mainListView);
        if(getArguments()==null){
            Log.d("status","arguements are null");
            mListener.onFragmentInteraction();

        }
        else {
            Log.d("status", "Data recieved from mainactivity successfully");
            displayList = (ArrayList<Expense>) getArguments().getSerializable("data");
//            Log.d("LastItem in expense fragment",displayList.get(displayList.size()-1).expName);
            Log.d("Data size", String.valueOf(displayList.size()));

            if (displayList.size() > 0) {

                Log.d("status", "Displaying the data of size" + displayList.size());
                t1 = new Exp_adapter((FragmentActivity) getActivity(), R.layout.exp_details, displayList);
                expdetailsList.setAdapter(t1);
                Log.d("status", "Displayed the data");
            } else {
                Log.d("status", "No data to display");
                Log.d("status", nodata.getText().toString());
                nodata.setVisibility(View.VISIBLE);
                expdetailsList.setVisibility(View.INVISIBLE);
            }
        }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Button text2","Inside add button listener");
                getFragmentManager().beginTransaction().replace(R.id.main_container, new AddExpFragment()).commit();
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
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
        //super.onActivityCreated(savedInstanceState);

        //nodata = getActivity().findViewById(R.id.)
        //expdetailsList=getActivity().findViewById(R.id.mainListView);
        /*if (getArguments() != null) {
            Log.d("status", "Data Sent successfully");
            displayList = (ArrayList<Expense>) getArguments().getSerializable("data");
            Log.d("Data size", String.valueOf(displayList.size()));
        }


        if (displayList.size() > 0) {


            t1 = new Exp_adapter((FragmentActivity)getActivity(), R.layout.exp_details, displayList);
            expdetailsList.setAdapter(t1);
        } else {
            Log.d("status", nodata.getText().toString());
            nodata.setVisibility(View.VISIBLE);
            expdetailsList.setVisibility(View.INVISIBLE);
        }
        Log.d("pos","Above Add button listener");*/

        expdetailsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Expense expense = displayList.get(position);
                System.out.println(expense.obj_key);
                mDatabase.child(expense.obj_key).setValue(null);
                Log.d("on remove","we removed it");
                displayList.remove(position);
                mDatabase.removeEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Log.d("display_size",String.valueOf(displayList.size()));

                Toast.makeText(getContext(),"Expense deleted",Toast.LENGTH_SHORT).show();
                if(displayList.size()==0){
                    nodata.setVisibility(View.VISIBLE);
                    expdetailsList.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
        expdetailsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                t1.notifyDataSetChanged();
                Expense expense = displayList.get(position);
                String expense_key = expense.obj_key;
                ShowExpenseFragment showExpenseFragment=new ShowExpenseFragment();

                Bundle bundle=new Bundle();
                //e=expense;
                bundle.putSerializable("EXP_KEY",expense);
                showExpenseFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container,showExpenseFragment,"ShowExpense").commit();
            }
        });


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
        void onFragmentInteraction();
    }
}
