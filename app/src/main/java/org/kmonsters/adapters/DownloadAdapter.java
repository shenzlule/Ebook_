package org.kmonsters.adapters;

import android.content.Context;
import android.os.Environment;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


/*
 * this class is responsible for creating the download  and managing its items
 * */
public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.PdfViewHolder> {
    private List<BookModel> pdfItems;
    private Context context;
    private List<BookModel> filteredPdfItems;

    public DownloadAdapter(Context context, List<BookModel> pdfItems) {
        this.context = context;
        this.pdfItems = pdfItems;
        this.filteredPdfItems = new ArrayList<>(pdfItems);
    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ready_item_, parent, false);
        return new PdfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {
        final BookModel pdfItem = pdfItems.get(position);
        holder.pdfName.setText(pdfItem.getName());

        holder.btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the selected PDF item
                BookModel selectedPdf = pdfItems.get(holder.getAdapterPosition());
                Helper.add_item_on_click(selectedPdf);
                downloadPdf(selectedPdf.getName());
                Toast.makeText(context, "Book has been downloaded", Toast.LENGTH_SHORT).show();

                // Create an Intent to start the target Activity

            }
        });

    }

    private void downloadPdf(String pdfFileName) {
        try {
            // Get InputStream of the PDF file from the assets folder
            InputStream inputStream = context.getAssets().open(pdfFileName);

            // Create a File object for the Downloads directory
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            // Create a FileOutputStream to write the PDF data to a file in the Downloads directory
            File outputFile = new File(downloadsDir, pdfFileName);
            OutputStream outputStream = new FileOutputStream(outputFile);

            // Read the PDF data from the InputStream and write it to the FileOutputStream
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            // Close the streams
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void  update_reset(){
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pdfItems.size();
    }

    public static class PdfViewHolder extends RecyclerView.ViewHolder {
        TextView pdfName;
        Button btn_download;


        public PdfViewHolder(@NonNull View itemView) {
            super(itemView);
            pdfName = itemView.findViewById(R.id.pdf_name_);
            btn_download = itemView.findViewById(R.id.dowload_btn);

        }
    }
}
