package org.kmonsters.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.storage.FirebaseStorage;

import org.kmonsters.Helper;
import org.kmonsters.R;
import org.kmonsters.models.BookModel;
import org.kmonsters.adapters.DownloadAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Downloads#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Downloads extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView_ready;
    private DownloadAdapter readyAdapter;
    private ArrayList<BookModel> readyItems;
    FirebaseStorage storage;


    public Downloads() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ready.
     */
    // TODO: Rename and change types and number of parameters
    public static Downloads newInstance(String param1, String param2) {
        Downloads fragment = new Downloads();
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
        View view =inflater.inflate(R.layout.downloads_frag, container, false);

        recyclerView_ready = view.findViewById(R.id.recycler_view_ready);
        recyclerView_ready.setLayoutManager(new GridLayoutManager(getContext(), 2)); //  columns grid layout
        readyItems = new ArrayList<>();
        readyAdapter =new DownloadAdapter(getContext(), readyItems);
        recyclerView_ready.setAdapter(readyAdapter);



        retrievePdfItems();




        // Inflate the layout for this fragment
        return view;
    }



    @SuppressLint("NotifyDataSetChanged")
    private void retrievePdfItems() {

        for (BookModel book: Helper.getReady_list()){

            readyItems.add(new BookModel(book.getName(),book.getName(),book.getPrice()));
        }



        readyAdapter.notifyDataSetChanged();

    }


}