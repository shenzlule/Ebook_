package org.kmonsters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.kmonsters.Helper;
import org.kmonsters.R;
import org.kmonsters.models.BookModel;

import java.util.ArrayList;
import java.util.List;


/*
 * this class is responsible for creating the store and managing its items
 * */
public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewHolder> {
    private List<BookModel> pdfItems;
    private Context context;
    private List<BookModel> filteredPdfItems;

    public PdfAdapter(Context context, List<BookModel> pdfItems) {
        this.context = context;
        this.pdfItems = pdfItems;
        this.filteredPdfItems = new ArrayList<>(pdfItems);
    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pdf_item, parent, false);
        return new PdfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {
        final BookModel pdfItem = pdfItems.get(position);
        holder.pdfName.setText(pdfItem.getName());
        String price="Price: $";
        price=price.concat(String.valueOf(pdfItem.getPrice()));
        holder.price.setText(price);

        holder.btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the selected PDF item
                BookModel selectedPdf = pdfItems.get(holder.getAdapterPosition());
                Helper.add_item_on_click(selectedPdf);
                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();

                // Create an Intent to start the target Activity

            }
        });

    }

    public void filterList(List<BookModel> filteredList) {
        pdfItems = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pdfItems.size();
    }

    public static class PdfViewHolder extends RecyclerView.ViewHolder {
        TextView pdfName;
        TextView price;
        Button btn_add_to_cart;


        public PdfViewHolder(@NonNull View itemView) {
            super(itemView);
            pdfName = itemView.findViewById(R.id.pdf_name);
            price = itemView.findViewById(R.id.amount);
            btn_add_to_cart = itemView.findViewById(R.id.add_dash_cart);

        }
    }
}
