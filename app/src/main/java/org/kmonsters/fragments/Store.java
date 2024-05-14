package org.kmonsters.fragments;

import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.kmonsters.Helper;
import org.kmonsters.R;
import org.kmonsters.adapters.PdfAdapter;
import org.kmonsters.models.BookModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Store extends Fragment {



    public Store() {
        // Required empty public constructor
    }


    private RecyclerView productRecyclerView;

    private SearchView searchView;

    ImageView upload;
    Uri imageuri = null;


    private RecyclerView recyclerView;
    private PdfAdapter pdfAdapter;
    public List<BookModel> pdfItems;
    FirebaseStorage storage;
    // Declare DatabaseReference
    private DatabaseReference databaseReference;
    private LinearLayout progressBar;
    private Button  button;
    private  View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.store_frag, container, false);
        // Retrieve PDF names and URLs from Firebase Storage
        progressBar=view.findViewById(R.id.l_prog);
        button=view.findViewById(R.id.refresh_btn);





// Create a ColorStateList with your custom color
        ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.searc));


        searchView=view.findViewById(R.id.searchView);
        // Set hint
        searchView.setQueryHint("Search eBooks");
// Get the EditText inside the SearchView
        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
// Set text color
        searchEditText.setTextColor(getResources().getColor(android.R.color.black));
// Set hint text color
        searchEditText.setHintTextColor(colorStateList);
// Get the search icon ImageView


        recyclerView = view.findViewById(R.id.recycler_view_);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columns grid layout
        pdfItems = new ArrayList<>();
        pdfAdapter = new PdfAdapter(getContext(), pdfItems);
        recyclerView.setAdapter(pdfAdapter);
        // Initialize Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Reference to your database node

        databaseReference = firebaseDatabase.getReference("Books");

        // Call method to retrieve data from Firebase
        fetchDataFromFirebase();


        seacrhEbook();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDataFromFirebase();
            }
        });



        return view;
    }


    private void retrievePdfItems() {
//        Helper.clear_all();

        AssetManager assetManager = getActivity().getAssets();
        String[] pdfFiles;
        try {
            // List all files in the assets folder
            pdfFiles = assetManager.list("");



            if (pdfFiles != null) {
                for (String pdfFile : pdfFiles) {
                    // Check if the file has a .pdf extension
                    if (pdfFile.toLowerCase().endsWith(".pdf")) {
                        // Get the name and path of each PDF file
                        String name = pdfFile;
                        String path = pdfFile; // Path relative to the assets folder
                        // Generate a random price between 100 and 400 (inclusive)
                        int randomPrice = new Random().nextInt(150) + 10;
                        pdfItems.add(new BookModel(name, path,randomPrice));
                        Helper.add_item(new BookModel(name, path,randomPrice));
//                        Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                    }
                }
                pdfAdapter.notifyDataSetChanged();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    public void  seacrhEbook() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String searchText) {
        List<BookModel> filteredList = new ArrayList<>();
        for (BookModel item : pdfItems) {
            if (item.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(item);
            }
        }
        pdfAdapter.filterList(filteredList);
    }


    private void fetchDataFromFirebase() {
        // Show progress bar while fetching data
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        // Check for internet connectivity
        if (!isNetworkAvailable()) {
            // Show Toast immediately if there is no internet connection
            Toast.makeText(getContext(), "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
            return; // Stop further execution
        }else {
            button.setVisibility(View.GONE);
        }

        // Add a listener for fetching data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                // Clear previous data
                pdfItems.clear();

                // Loop through all children nodes
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Extract data and add to your list
                    String name = snapshot.getValue(String.class);

                    Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();



                }

                recyclerView.setVisibility(View.VISIBLE);

                // Notify adapter of changes
                retrievePdfItems();

                // Hide progress bar after data is fetched
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
                // Hide progress bar on error as well

                progressBar.setVisibility(View.GONE);

            }


        });
    }



    // Method to check internet connectivity
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }
}