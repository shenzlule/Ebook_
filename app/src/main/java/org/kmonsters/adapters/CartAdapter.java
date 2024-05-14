package org.kmonsters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.kmonsters.Helper;
import org.kmonsters.R;
import org.kmonsters.models.BookModel;

import java.util.ArrayList;
import java.util.List;



/*
* this class is responsible for creating the cart and managing its items
* */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.PdfViewHolder> {
    private List<BookModel> pdfItems;
    private Context context;
    private List<BookModel> filteredPdfItems;

    public CartAdapter(Context context, List<BookModel> pdfItems) {
        this.context = context;
        this.pdfItems = pdfItems;
        this.filteredPdfItems = new ArrayList<>(pdfItems);
    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        return new PdfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {
        final BookModel pdfItem = pdfItems.get(position);
        holder.pdfName.setText(pdfItem.getName());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the selected PDF item
                BookModel selectedPdf = pdfItems.get(holder.getAdapterPosition());
                remove_item(position);

            }
        });


    }

    public void filterList(List<BookModel> filteredList) {
        pdfItems = filteredList;
        notifyDataSetChanged();
    }


    public void reset_items() {
        pdfItems =Helper.get_Added_to_cart();
        notifyDataSetChanged();
    }

    public void remove_item(int pos) {

        Helper.remove_item(pos);
        pdfItems.remove(pos);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return pdfItems.size();
    }

    public static class PdfViewHolder extends RecyclerView.ViewHolder {
        TextView pdfName;
        Button btn;


        public PdfViewHolder(@NonNull View itemView) {
            super(itemView);
            pdfName = itemView.findViewById(R.id.pdf_name_cart);
            btn=itemView.findViewById(R.id.cart_btn);

        }
    }
}
