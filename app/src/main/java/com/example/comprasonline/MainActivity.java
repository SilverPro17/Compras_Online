package com.example.comprasonline;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView; // RecyclerView para exibir os produtos
    private ProductAdapter adapter; // Adaptador para conectar os dados à RecyclerView
    private List<Product> products; // Lista de produtos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa a lista de produtos
        products = new ArrayList<>();

        // Configura o RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Define o layout como lista vertical

        // Inicializa o adaptador
        adapter = new ProductAdapter(this, products);
        recyclerView.setAdapter(adapter);

        // Configura o botão "Adicionar Produto"
        findViewById(R.id.add_product_button).setOnClickListener(v -> {
            // Abre a AddProductActivity para adicionar um novo produto
            Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    // Método chamado quando a AddProductActivity retorna um resultado
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verifica se o resultado vem da AddProductActivity
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Recupera os dados do produto retornados pela AddProductActivity
            String name = data.getStringExtra("name");
            String price = data.getStringExtra("price");
            String imageUriString = data.getStringExtra("imageUri");
            Uri imageUri = imageUriString != null ? Uri.parse(imageUriString) : null; // Converte o URI da imagem para o formato correto

            // Adiciona o novo produto à lista
            products.add(new Product(name, price, imageUri != null ? R.drawable.ic_launcher_foreground : R.drawable.ic_launcher_background));
            adapter.notifyDataSetChanged(); // Atualiza o RecyclerView
        }
    }
}
