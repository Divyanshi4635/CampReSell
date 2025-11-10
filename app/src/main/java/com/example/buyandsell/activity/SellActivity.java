package com.example.buyandsell.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyandsell.R;
import com.example.buyandsell.adapters.ImagePreviewAdapter;
import com.example.buyandsell.models.Customer;
import com.example.buyandsell.utils.UserManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SellActivity extends AppCompatActivity {

    // Constants
    private static final int MAX_IMAGE_SIZE_KB = 5120; // 5 MB
    private static final int MAX_INVOICE_SIZE_MB = 1; // 1 MB
    private static final int MAX_DESC_WORDS = 500;
    private static final int MIN_RATING_TO_SELL = 3;
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private static final int REQUEST_CODE_INVOICE = 2001;
    private static final int REQUEST_CODE_IMAGES = 2002;

    public static Intent newIntent(Context context) {
        return new Intent(context, SellActivity.class);
    }

    // UI Components
    private EditText etProductTitle;
    private EditText etProductDescription;
    private TextView tvDescWordCount;
    private EditText etBuyingDate;
    private Button btnSelectDate;
    private EditText etUsedYears;
    private TextView tvInvoiceStatus;
    private Button btnUploadInvoice;
    private EditText etDamages;
    private Button btnUploadImages;
    private TextView tvImagesCount;
    private RecyclerView rvImagePreview;
    private Spinner spinnerAddress;
    private Button btnAddAddress;
    private Spinner spinnerPaymentMethod;
    private Button btnSubmit;

    // Data
    private List<Uri> selectedImages;
    private Uri selectedInvoiceUri;
    private Calendar selectedDate;
    private ArrayAdapter<String> addressAdapter;
    private ArrayAdapter<String> paymentMethodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        // Check if user is logged in and has valid rating
        if (!checkSellerEligibility()) {
            finish();
            return;
        }

        initializeViews();
        setupClickListeners();
        setupWordCountWatcher();
        setupDatePicker();
        initializeSpinners();
    }

    private void initializeViews() {
        etProductTitle = findViewById(R.id.et_product_title);
        etProductDescription = findViewById(R.id.et_product_description);
        tvDescWordCount = findViewById(R.id.tv_desc_word_count);
        etBuyingDate = findViewById(R.id.et_buying_date);
        btnSelectDate = findViewById(R.id.btn_select_date);
        etUsedYears = findViewById(R.id.et_used_years);
        tvInvoiceStatus = findViewById(R.id.tv_invoice_status);
        btnUploadInvoice = findViewById(R.id.btn_upload_invoice);
        etDamages = findViewById(R.id.et_damages);
        btnUploadImages = findViewById(R.id.btn_upload_images);
        tvImagesCount = findViewById(R.id.tv_images_count);
        rvImagePreview = findViewById(R.id.rv_image_preview);
        spinnerAddress = findViewById(R.id.spinner_address);
        btnAddAddress = findViewById(R.id.btn_add_address);
        spinnerPaymentMethod = findViewById(R.id.spinner_payment_method);
        btnSubmit = findViewById(R.id.btn_submit);

        selectedImages = new ArrayList<>();
        selectedDate = Calendar.getInstance();
    }

    private void setupClickListeners() {
        btnSelectDate.setOnClickListener(v -> showDatePicker());
        btnUploadInvoice.setOnClickListener(v -> uploadInvoice());
        btnUploadImages.setOnClickListener(v -> uploadImages());
        btnAddAddress.setOnClickListener(v -> showAddAddressDialog());
        btnSubmit.setOnClickListener(v -> submitProduct());
    }

    private void setupWordCountWatcher() {
        etProductDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int wordCount = countWords(s.toString());
                tvDescWordCount.setText(String.format(Locale.getDefault(), "%d/500 words", wordCount));
                
                // Mark as red if exceeds limit
                if (wordCount > 500) {
                    etProductDescription.setBackgroundColor(Color.parseColor("#FFEBEE"));
                    tvDescWordCount.setTextColor(Color.RED);
                } else {
                    etProductDescription.setBackgroundColor(Color.TRANSPARENT);
                    tvDescWordCount.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private int countWords(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        return text.trim().split("\\s+").length;
    }

    private void setupDatePicker() {
        Calendar maxDate = Calendar.getInstance();
        
        btnSelectDate.setOnClickListener(v -> showDatePicker());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
            this,
            (view, year, month, dayOfMonth) -> {
                selectedDate.set(year, month, dayOfMonth);
                etBuyingDate.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d", 
                    dayOfMonth, month + 1, year));
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        );
        
        // Set max date to today
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void initializeSpinners() {
        // Initialize address spinner
        List<String> addresses = new ArrayList<>();
        addresses.add("123 Main Street, City, State 12345");
        addresses.add("456 Oak Avenue, City, State 67890");
        
        addressAdapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_spinner_item, addresses);
        addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAddress.setAdapter(addressAdapter);

        // Initialize payment method spinner
        List<String> paymentMethods = new ArrayList<>();
        paymentMethods.add("Cash on Delivery");
        paymentMethods.add("Bank Transfer");
        paymentMethods.add("Credit Card");
        paymentMethods.add("Digital Wallet");
        
        paymentMethodAdapter = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_item, paymentMethods);
        paymentMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaymentMethod.setAdapter(paymentMethodAdapter);
    }

    private void uploadInvoice() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select Invoice PDF"), 
            REQUEST_CODE_INVOICE);
    }

    private void uploadImages() {
        // Use SAF; no read permission required for returned URIs
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Images"),
            REQUEST_CODE_IMAGES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode != RESULT_OK) return;

        if (requestCode == REQUEST_CODE_INVOICE) {
            if (data != null && data.getData() != null) {
                Uri fileUri = data.getData();
                if (validateInvoice(fileUri)) {
                    selectedInvoiceUri = fileUri;
                    String fileName = getFileName(fileUri);
                    tvInvoiceStatus.setText("Invoice: " + fileName);
                } else {
                    Toast.makeText(this, "Invoice must be PDF and max 1MB", 
                        Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == REQUEST_CODE_IMAGES) {
            if (data.getClipData() != null) {
                // Multiple images selected
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    if (validateImage(imageUri)) {
                        selectedImages.add(imageUri);
                    } else {
                        Toast.makeText(this, 
                            String.format("Image %d rejected. Must be JPG/PNG and max 50KB", i + 1),
                            Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (data.getData() != null) {
                // Single image selected
                Uri imageUri = data.getData();
                if (validateImage(imageUri)) {
                    selectedImages.add(imageUri);
                } else {
                    Toast.makeText(this, "Image must be JPG/PNG and max 50KB",
                        Toast.LENGTH_SHORT).show();
                }
            }
            updateImageCount();
        }
    }

    private boolean validateInvoice(Uri uri) {
        try {
            String mime = getContentResolver().getType(uri);
            if (mime == null) mime = "";
            if (!"application/pdf".equalsIgnoreCase(mime)) {
                return false;
            }
            long size = getContentLength(uri);
            if (size < 0) return true; // unknown, allow
            long sizeMb = size / (1024 * 1024);
            return sizeMb <= MAX_INVOICE_SIZE_MB;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validateImage(Uri uri) {
        try {
            String mime = getContentResolver().getType(uri);
            if (mime == null || !mime.startsWith("image/")) return false;
            long size = getContentLength(uri);
            if (size < 0) return true; // unknown, allow
            long sizeKb = size / 1024;
            return sizeKb <= MAX_IMAGE_SIZE_KB;
        } catch (Exception e) {
            return false;
        }
    }

    private String getFileName(Uri uri) {
        try (android.database.Cursor cursor = getContentResolver().query(uri, new String[]{android.provider.OpenableColumns.DISPLAY_NAME}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int idx = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME);
                if (idx >= 0) return cursor.getString(idx);
            }
        } catch (Exception ignored) {}
        return "file";
    }

    private long getContentLength(Uri uri) {
        try (android.content.res.AssetFileDescriptor afd = getContentResolver().openAssetFileDescriptor(uri, "r")) {
            if (afd != null) return afd.getLength();
        } catch (Exception ignored) {}
        return -1;
    }

    private void updateImageCount() {
        int count = selectedImages.size();
        tvImagesCount.setText(count + " images uploaded");
        
        if (count > 0) {
            rvImagePreview.setVisibility(View.VISIBLE);
            // Setup RecyclerView for image preview
            ImagePreviewAdapter adapter = new ImagePreviewAdapter(selectedImages);
            rvImagePreview.setLayoutManager(new LinearLayoutManager(this, 
                LinearLayoutManager.HORIZONTAL, false));
            rvImagePreview.setAdapter(adapter);
        } else {
            rvImagePreview.setVisibility(View.GONE);
        }
    }

    private void showAddAddressDialog() {
        // Simple dialog to add address (can be extended)
        Toast.makeText(this, "Feature coming soon", Toast.LENGTH_SHORT).show();
    }

    private void submitProduct() {
        if (!validateAllFields()) {
            return;
        }

        // Persist product as PENDING for admin review
        final com.example.buyandsell.utils.UserManager userManager = com.example.buyandsell.utils.UserManager.getInstance();
        final com.example.buyandsell.models.Customer current = userManager.getCurrentUser();
        if (current == null) {
            Toast.makeText(this, "You must be logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        final com.example.buyandsell.data.ProductEntity entity = new com.example.buyandsell.data.ProductEntity();
        entity.productId = java.util.UUID.randomUUID().toString();
        entity.sellerId = current.getCustomerId();
        entity.title = etProductTitle.getText().toString().trim();
        entity.description = etProductDescription.getText().toString().trim();
        entity.buyDate = etBuyingDate.getText().toString().trim();
        try {
            entity.usedYears = Integer.parseInt(etUsedYears.getText().toString().trim());
        } catch (Exception e) {
            entity.usedYears = 0;
        }
        entity.invoiceUri = selectedInvoiceUri != null ? selectedInvoiceUri.toString() : null;
        entity.damages = etDamages.getText().toString().trim();
        java.util.List<String> imageUris = saveImagesToInternalStorage(selectedImages);
        entity.imageUrisJson = com.example.buyandsell.data.Converters.fromStringList(imageUris);
        Object address = spinnerAddress.getSelectedItem();
        String addr = address != null ? address.toString() : "";
        entity.pickupAddress = addr;
        entity.deliveryAddress = addr;
        Object payment = spinnerPaymentMethod.getSelectedItem();
        entity.paymentMethod = payment != null ? payment.toString() : "";
        entity.adminRating = 0f;
        entity.status = "pending";
        entity.bidEndTimeEpochMs = 0L;

        android.os.AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                com.example.buyandsell.data.AppDatabase db = com.example.buyandsell.data.AppDatabase.getInstance(SellActivity.this);
                db.productDao().insert(entity);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SellActivity.this, "Product submitted for admin review", Toast.LENGTH_LONG).show();
                        clearForm();
                        finish();
                    }
                });
            }
        });
    }

    private java.util.List<String> saveImagesToInternalStorage(java.util.List<android.net.Uri> sourceUris) {
        java.util.List<String> stored = new java.util.ArrayList<>();
        java.io.File dir = new java.io.File(getFilesDir(), "product_images");
        if (!dir.exists()) dir.mkdirs();
        int index = 0;
        for (android.net.Uri uri : sourceUris) {
            java.io.InputStream in = null;
            java.io.FileOutputStream out = null;
            try {
                String ext = ".jpg";
                String name = "img_" + System.currentTimeMillis() + "_" + (index++) + ext;
                java.io.File dst = new java.io.File(dir, name);
                in = getContentResolver().openInputStream(uri);
                out = new java.io.FileOutputStream(dst);
                copyStream(in, out);
                stored.add(android.net.Uri.fromFile(dst).toString());
            } catch (Exception ignore) {
                // Skip failed copy
            } finally {
                try { if (in != null) in.close(); } catch (Exception ignored) {}
                try { if (out != null) out.close(); } catch (Exception ignored) {}
            }
        }
        return stored;
    }

    private void copyStream(java.io.InputStream in, java.io.OutputStream out) throws java.io.IOException {
        byte[] buffer = new byte[8192];
        int len;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();
    }

    private boolean validateAllFields() {
        boolean isValid = true;
        
        // Reset all backgrounds
        etProductTitle.setBackgroundColor(Color.TRANSPARENT);
        etProductDescription.setBackgroundColor(Color.TRANSPARENT);
        etUsedYears.setBackgroundColor(Color.TRANSPARENT);
        spinnerAddress.setBackgroundColor(Color.TRANSPARENT);
        spinnerPaymentMethod.setBackgroundColor(Color.TRANSPARENT);

        // Validate Product Title
        String title = etProductTitle.getText().toString().trim();
        if (title.isEmpty()) {
            etProductTitle.setBackgroundColor(Color.parseColor("#FFEBEE"));
            isValid = false;
        }

        // Validate Description
        String description = etProductDescription.getText().toString().trim();
        int wordCount = countWords(description);
        if (description.isEmpty() || wordCount > MAX_DESC_WORDS) {
            etProductDescription.setBackgroundColor(Color.parseColor("#FFEBEE"));
            isValid = false;
        }

        // Validate Used Years
        String usedYears = etUsedYears.getText().toString().trim();
        if (usedYears.isEmpty()) {
            etUsedYears.setBackgroundColor(Color.parseColor("#FFEBEE"));
            isValid = false;
        }

        // Validate Images
        if (selectedImages.isEmpty()) {
            Toast.makeText(this, "Please upload at least one product image",
                Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        // Validate Address
        if (spinnerAddress.getSelectedItem() == null) {
            spinnerAddress.setBackgroundColor(Color.parseColor("#FFEBEE"));
            isValid = false;
        }

        // Validate Payment Method
        if (spinnerPaymentMethod.getSelectedItem() == null) {
            spinnerPaymentMethod.setBackgroundColor(Color.parseColor("#FFEBEE"));
            isValid = false;
        }

        if (!isValid) {
            Toast.makeText(this, "Please fill all required fields correctly",
                Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    private void clearForm() {
        etProductTitle.setText("");
        etProductDescription.setText("");
        etBuyingDate.setText("");
        etUsedYears.setText("");
        etDamages.setText("");
        tvInvoiceStatus.setText("No invoice uploaded");
        tvImagesCount.setText("0 images uploaded");
        rvImagePreview.setVisibility(View.GONE);
        selectedImages.clear();
        selectedInvoiceUri = null;
        selectedDate = Calendar.getInstance();
    }

    private boolean checkSellerEligibility() {
        UserManager userManager = UserManager.getInstance();
        
        // Check if user is logged in
        if (!userManager.isLoggedIn()) {
            Toast.makeText(this, "You must be logged in to sell products",
                Toast.LENGTH_LONG).show();
            startActivity(LoginActivity.newIntent(this));
            return false;
        }

        // Check if user rating is above 3
        Customer currentUser = userManager.getCurrentUser();
        if (currentUser != null) {
            float rating = currentUser.getRating();
            if (rating < MIN_RATING_TO_SELL) {
                Toast.makeText(this,
                    String.format("Your rating (%.1f) is too low to sell. You need a rating of at least %.0f",
                        rating, (float)MIN_RATING_TO_SELL),
                    Toast.LENGTH_LONG).show();
                
                // Show dialog
                new AlertDialog.Builder(this)
                    .setTitle("Unable to Sell")
                    .setMessage(String.format("Your customer rating is %.1f stars. " +
                        "You need a minimum rating of %d stars to become a seller.",
                        rating, MIN_RATING_TO_SELL))
                    .setPositiveButton("OK", (dialog, which) -> finish())
                    .show();
                return false;
            }
        }

        return true;
    }
}
