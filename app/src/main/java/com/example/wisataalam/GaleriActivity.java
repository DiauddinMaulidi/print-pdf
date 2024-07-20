package com.example.wisataalam;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class GaleriActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 101;
    private Hewan selectedHewan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeri);

        // Mendapatkan data dari Intent
        String jenisGaleri = getIntent().getStringExtra(MainActivity.JENIS_GALERI_KEY);

        // Mendapatkan referensi ke UI elements
        TextView txJudul = findViewById(R.id.txJudul);
        TextView txJenis = findViewById(R.id.txJenis);
        TextView txAsal = findViewById(R.id.txAsal);
        TextView txDeskripsi = findViewById(R.id.txDeskripsi);
        ImageView gambarHewan = findViewById(R.id.gambarHewan);
        Button btnPrintPDF = findViewById(R.id.btnPrintPDF);

        // Mengambil data hewan dari DataProvider
        List<Hewan> hewans = DataProvider.getAllHewan(this);
        for (Hewan hewan : hewans) {
            if (hewan.getNama().equals(jenisGaleri)) {
                selectedHewan = hewan;
                break;
            }
        }

        if (selectedHewan != null) {
            // Menampilkan detail berdasarkan hewan yang dipilih
            txJudul.setText("Jenis Galeri: " + selectedHewan.getNama());
            txJenis.setText("Jenis Hewan: " + selectedHewan.getJenis());
            txAsal.setText("Asal: " + selectedHewan.getAsal());
            txDeskripsi.setText("Deskripsi: " + selectedHewan.getDeskripsi());
            gambarHewan.setImageResource(selectedHewan.getImageResId());
        } else {
            // Menangani kasus jika hewan tidak ditemukan
            txJudul.setText("Jenis Galeri: Tidak ditemukan");
            txJenis.setText("Jenis Hewan: Tidak ditemukan");
            txAsal.setText("Asal: Tidak ditemukan");
            txDeskripsi.setText("Deskripsi: Tidak ditemukan");
            gambarHewan.setImageResource(R.drawable.hiu); // Gambar default
        }

        btnPrintPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)) {
                    createPDF(selectedHewan);
                }
            }
        });
    }

    public boolean checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(GaleriActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(GaleriActivity.this, new String[]{permission}, requestCode);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(GaleriActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
                // You might want to handle this case separately
            } else {
                Toast.makeText(GaleriActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createPDF(Hewan hewan) {
        if (hewan == null) {
            Toast.makeText(this, "Data hewan tidak ditemukan", Toast.LENGTH_SHORT).show();
            return;
        }

        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "BinatangDetails.pdf");

        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(file));
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Binatang Details"));
            document.add(new Paragraph("Jenis Galeri: " + hewan.getNama()));
            document.add(new Paragraph("Jenis Hewan: " + hewan.getJenis()));
            document.add(new Paragraph("Asal: " + hewan.getAsal()));
            document.add(new Paragraph("Deskripsi: " + hewan.getDeskripsi()));

            document.close();
            Toast.makeText(this, "PDF berhasil", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error Creating PDF", Toast.LENGTH_SHORT).show();
        }
    }
}
