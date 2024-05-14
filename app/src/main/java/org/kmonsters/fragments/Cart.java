package org.kmonsters.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;

import org.kmonsters.Helper;
import org.kmonsters.R;
import org.kmonsters.adapters.CartAdapter;
import org.kmonsters.models.BookModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Cart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cart extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<BookModel> cartItems;
    FirebaseStorage storage;

    public Cart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Order_list.
     */
    // TODO: Rename and change types and number of parameters
    public static Cart newInstance(String param1, String param2) {
        Cart fragment = new Cart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_cart_list, container, false);


        recyclerView = view.findViewById(R.id.recycler_view_cart);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1)); //  columns grid layout
        cartItems = new ArrayList<>();
        cartAdapter =new CartAdapter(getContext(),cartItems);
        recyclerView.setAdapter(cartAdapter);
        Button btn_buy=view.findViewById(R.id.Bbuy);

        retrievePdfItems();
        if ( Helper.get_Added_to_cart().size()==0){
            btn_buy.setVisibility(View.GONE);
        }
        else {
            btn_buy.setVisibility(View.VISIBLE);
        }

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Inside your code where you want to display the dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirm Purchase");
                String total="Do you want to continue with the purchase Total Amount :$";
                total=total.concat(String.valueOf(Helper.getAmount()));
                builder.setMessage(total);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                         Toast.makeText(getContext(), "Purchase confirmed thanks ", Toast.LENGTH_SHORT).show();
                        Helper.setReady_list();
                        Helper.clear_all();
                        cartAdapter.reset_items();
                        btn_buy.setVisibility(View.GONE);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked No, dismiss the dialog or handle any other action
                         dialog.dismiss();
                    }
                });
                builder.show();



            }
        });

        return view;
    }


    private void retrievePdfItems() {
        // Clear previous data in cartItems
        cartItems.clear();

        // Retrieve items from Helper.get_Added_to_cart()
        for (BookModel book : Helper.get_Added_to_cart()) {
            // Check if the item with the same name already exists in cartItems
            boolean itemExists = false;
            for (BookModel existingItem : cartItems) {
                if (existingItem.getName().equals(book.getName())) {
                    itemExists = true;
                    break;
                }
            }

            // If the item does not exist in cartItems, add it
            if (!itemExists) {
                cartItems.add(new BookModel(book.getName(), book.getName(), book.getPrice()));
            }
        }

        // Notify adapter of changes
        cartAdapter.notifyDataSetChanged();
    }








}