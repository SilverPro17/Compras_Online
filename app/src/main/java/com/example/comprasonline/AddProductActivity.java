package com.example.comprasonline;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {

    private Uri imageUri; // Armazena o URI da imagem selecionada
    private boolean isEditMode = false; // Indica se estamos no modo de edição
    private int productIndex = -1; // Armazena o índice do produto sendo editado, se aplicável

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Referências aos elementos do layout
        ImageView productImage = findViewById(R.id.product_image);
        Button selectImageButton = findViewById(R.id.select_image_button);
        EditText nameInput = findViewById(R.id.name_input);
        EditText priceInput = findViewById(R.id.price_input);
        Button saveButton = findViewById(R.id.save_product_button);

        // Verifica se os dados de um produto foram enviados para edição
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("productIndex")) {
            isEditMode = true; // Ativa o modo de edição
            productIndex = intent.getIntExtra("productIndex", -1); // Recupera o índice do produto

            // Preenche os campos com os dados recebidos
            nameInput.setText(intent.getStringExtra("name"));
            priceInput.setText(intent.getStringExtra("price"));

            // Configura a imagem, se fornecida
            String imageUriString = intent.getStringExtra("imageUri");
            if (imageUriString != null) {
                imageUri = Uri.parse(imageUriString); // Converte a string para URI
                productImage.setImageURI(imageUri); // Define a imagem no ImageView
            }
        }

        // Lógica para seleção de imagem da galeria
        selectImageButton.setOnClickListener(v -> {
            Intent pickImageIntent = new Intent(Intent.ACTION_PICK); // Ação para selecionar uma imagem
            pickImageIntent.setType("image/*"); // Filtro para imagens
            startActivityForResult(pickImageIntent, 1);
        });

        // Lógica para guardar os dados do produto
        saveButton.setOnClickListener(v -> {
            // Prepara um Intent para retornar os dados do produto
            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", nameInput.getText().toString());
            resultIntent.putExtra("price", priceInput.getText().toString());
            if (imageUri != null) {
                resultIntent.putExtra("imageUri", imageUri.toString());
            }
            resultIntent.putExtra("productIndex", productIndex); // Índice do produto (usado na edição)
            setResult(RESULT_OK, resultIntent); // Retorna os dados para a MainActivity
            finish();
        });
    }

    // Método chamado quando a imagem é selecionada
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verifica se o resultado é da seleção de imagem e se está tudo OK
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData(); // Recupera o URI da imagem selecionada
            ImageView productImage = findViewById(R.id.product_image);
            productImage.setImageURI(imageUri); // Define a imagem selecionada no ImageView
        }
    }
}
