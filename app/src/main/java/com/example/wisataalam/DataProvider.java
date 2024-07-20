package com.example.wisataalam;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    public static List<Hewan> getAllHewan(Context context) {
        List<Hewan> hewans = new ArrayList<>();
        hewans.add(new Hewan("Kucing", "Anggora", "Indonesia", "Aslinya berasal dari Ankara, Turki dengan ciri khas berbulu panjang dan lembut", R.drawable.cat_angora));
        hewans.add(new Hewan("Anjing", "bulldog", "Indonesia", "Anjing populer yang dikenal dengan badan yang gemuk berotot, wajah seperti kain kusut", R.drawable.dog_bulldog));
        // Tambahkan data hewan lainnya jika perlu
        return hewans;
    }
}
