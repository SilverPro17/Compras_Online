package com.example.comprasonline;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);

        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice());

        // Verifica o tipo da imagem (URI ou recurso)
        if (product.getImage() instanceof Integer) {
            holder.image.setImageResource((int) product.getImage());
        } else if (product.getImage() instanceof android.net.Uri) {
            holder.image.setImageURI((android.net.Uri) product.getImage());
        }

        // Configura clique no item para editar o produto
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddProductActivity.class);
            intent.putExtra("name", product.getName());
            intent.putExtra("price", product.getPrice());
            if (product.getImage() instanceof android.net.Uri) {
                intent.putExtra("imageUri", product.getImage().toString());
            }
            intent.putExtra("productIndex", position);
            ((MainActivity) context).startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
        }
    }
}
